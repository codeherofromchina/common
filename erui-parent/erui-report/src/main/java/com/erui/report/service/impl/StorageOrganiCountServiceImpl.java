package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.erui.comm.NewDateUtil;
import com.erui.comm.RateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.StorageOrganiCountMapper;
import com.erui.report.model.StorageOrganiCount;
import com.erui.report.service.StorageOrganiCountService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class StorageOrganiCountServiceImpl extends BaseService<StorageOrganiCountMapper>
        implements StorageOrganiCountService {
    private final static Logger logger = LoggerFactory.getLogger(StorageOrganiCountServiceImpl.class);

    @Override
    public Date selectStart() {
        return readMapper.selectStart();
    }

    @Override
    public Date selectEnd() {
        return readMapper.selectEnd() ;
    }

    @Override
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

        ImportDataResponse response = new ImportDataResponse(
            new String[]{"productNum","trayNum"}
        );
        response.setOtherMsg(NewDateUtil.getBeforeSaturdayWeekStr(null));
        Set<String> orgs=new HashSet<>();
        Map<String, BigDecimal> sumMap = response.getSumMap();
        int size = datas.size();
        StorageOrganiCount soc = null;
        if (!testOnly) {
            writeMapper.truncateTable();
        }
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2;
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.STORAGE_ORGANI_COUNT, response, cellIndex)) {
                continue;
            }
            soc = new StorageOrganiCount();
            try {
                soc.setCreateAt(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
                        DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.STORAGE_ORGANI_COUNT.getTable(), cellIndex, "时间字段格式错误");
                continue;
            }
            soc.setOrganiName(strArr[1]);
            try {
                soc.setTrayNum(new BigDecimal(strArr[2]).intValue());
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.STORAGE_ORGANI_COUNT.getTable(), cellIndex, "托盘数量不是数字");
                continue;
            }

            try {
                soc.setProductNum(new BigDecimal(strArr[3]).intValue());
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.STORAGE_ORGANI_COUNT.getTable(), cellIndex, "产品数量不是数字");
                continue;
            }

            soc.setRemark(strArr[4]);
            soc.setDocType(strArr[5]);

            try {
                if (!testOnly) {
                    writeMapper.insertSelective(soc);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.STORAGE_ORGANI_COUNT.getTable(), cellIndex, e.getMessage());
                continue;
            }
            //统计事业部数量
            if(NewDateUtil.inSaturdayWeek(soc.getCreateAt())){
                response.sumData(soc);
                orgs.add(soc.getOrganiName());
            }
            response.incrSuccess();
        }
        response.getSumMap().put("orgCount",new BigDecimal(orgs.size()));
        response.setDone(true);

        return response;
    }

    @Override
    public Map<String, Object> selectStorageSummaryData(Map<String, String> params) {
        //查询入库量和出库量
        Map<String, Object> outEntryData = readMapper.selectEntryAndOutData(params);
        double eAmount = Double.parseDouble(outEntryData.get("entryAmount").toString());
        double oAmount = Double.parseDouble(outEntryData.get("outAmount").toString());
        outEntryData.put("entryAmount",RateUtil.doubleChainRateTwo(eAmount,1));
        outEntryData.put("outAmount",RateUtil.doubleChainRateTwo(oAmount,1));
        //查询库存总量
        Map<String, Object> totalMap = readMapper.selectTotalStack(params);
        int totalCount = Integer.parseInt(totalMap.get("totalCount").toString());
        double totalAmount = Double.parseDouble(totalMap.get("totalAmount").toString());
        outEntryData.put("totalCount",totalCount);
        if(totalAmount>0){
            outEntryData.put("totalAmount",RateUtil.doubleChainRateTwo(totalAmount,1));
        }else {
            outEntryData.put("totalAmount",0.00);
        }
        return outEntryData;
    }

    @Override
    public Map<String, Object> selectStorageTrend(Date startTime, Date endTime) {

        //1.构建一个标准的时间集合
        List<String> dates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int days = DateUtil.getDayBetween(startTime, endTime);
        for (int i = 0; i < days; i++) {
            Date datetime = DateUtil.sometimeCalendar(startTime, -i);
            dates.add(dateFormat.format(datetime));
        }

        //2.获取数据
        Map<String,String> params=new HashMap<>();
        String fullStartTime=DateUtil.formatDateToString(startTime, DateUtil.FULL_FORMAT_STR2);
        String fullEndTime=DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2);
        params.put("startTime",fullStartTime);
        params.put("endTime",fullEndTime);
        //入库趋势图数据
        List<Map<String,Object>> entryList=readMapper.selectEntryDataGroupByTime(params);
        //出库趋势图数据
        List<Map<String,Object>> outList=readMapper.selectOutDataGroupByTime(params);
       //获取库存数据
        List<Integer> totalCounts=new ArrayList<>();//库存量列表
        Map<String,Integer> inventory=new HashMap<>(); //封装每天库存数据
        for (int i = 0; i < days; i++) {
            Date datetime = DateUtil.sometimeCalendar(startTime, -i);
            String eTime = DateUtil.getEndTime(datetime,DateUtil.FULL_FORMAT_STR);
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.SHORT_FORMAT_STR);
            String time=sdf.format(datetime);
            params.put("endTime",eTime);
            Map<String, Object> totalMap = readMapper.selectTotalStack(params);
            if(totalMap!=null&&totalMap.get("totalCount")!=null){
                int totalCount = Integer.parseInt(totalMap.get("totalCount").toString());
                totalCounts.add(totalCount);
                inventory.put(time,totalCount);
            }else {
                totalCounts.add(0);
                inventory.put(time,0);
            }
        }
        //3.处理数据
        List<Integer> entryCounts=new ArrayList<>();//入库数量列表
        List<Integer> outCounts=new ArrayList<>();//出库数量列表
        Map<String, Map<String, Object>> entryMap = entryList.stream()
                .collect(Collectors.toMap(vo -> vo.get("entryDate").toString(), vo -> vo));
        Map<String, Map<String, Object>> outMap = outList.stream()
                .collect(Collectors.toMap(vo -> vo.get("outDate").toString(), vo -> vo));
        for (String date:dates) {
            if(entryMap.containsKey(date)){
                Map<String, Object> data = entryMap.get(date);
                int entryCount = Integer.parseInt(data.get("entryCount").toString());
                entryCounts.add(entryCount);
            }else {
                entryCounts.add(0);
            }
            if(outMap.containsKey(date)){
                Map<String, Object> data = outMap.get(date);
                int outCount = Integer.parseInt(data.get("outCount").toString());
                outCounts.add(outCount);

            }else {
                outCounts.add(0);
            }


        }

        //封装数据
        Map<String, Object> datas = new HashMap<>();
        datas.put("xAxis", dates);
        datas.put("entryCounts", entryCounts);
        datas.put("outCounts", outCounts);
        datas.put("totalCounts", totalCounts);
        datas.put("inventory", inventory);
        return datas;
    }

    @Override
    public Map<String, Object> selectCountryOutStoreSummary(Map<String, String> params) {

       List<Map<String,Object>> dataList= readMapper.selectOutDataGroupByCountry(params);
       //排序
        dataList.sort((Map<String,Object> m1,Map<String,Object> m2)->
                Integer.parseInt(m2.get("outCount").toString())-Integer.parseInt(m1.get("outCount").toString()));
       List<String> countrys=new ArrayList<>();//目的国家列表
        List<Integer> outCountList=new ArrayList<>();//出库量列表
        List<String> proportionList=new ArrayList<>();//占比列表
       dataList.stream().forEach(m->{
            String country=m.get("country").toString();
           int outCount = Integer.parseInt(m.get("outCount").toString());
           countrys.add(country);
           outCountList.add(outCount);
       });

      Integer totalCount= outCountList.stream().reduce(0,(a,b)->a+b);
        if(totalCount!=null&&totalCount>0){
            outCountList.stream().forEach(count->{
                proportionList.add("占比"+RateUtil.intChainRateTwo(count*100,totalCount)+"%");
            });
        }
        Map<String,Object> data=new HashMap<>();
        data.put("countryList",countrys);
        data.put("outCountList",outCountList);
        data.put("outProportionList",proportionList);
        return data;
    }

    @Override
    public Map<String, Object> orgStocks(Map<String,String> params) {
       List<Map<String,Object>> orgDataList= readMapper.orgStocks(params);
       List<String> orgList=new ArrayList<>();
       List<Integer> trayList=new ArrayList<>();
       List<Integer> productList=new ArrayList<>();
       if(CollectionUtils.isNotEmpty(orgDataList)){
           for (Map<String,Object> map: orgDataList) {
               String org =map.get("orgName").toString();
               int proCount = Integer.parseInt(map.get("proCount").toString());
               int trayCount = Integer.parseInt(map.get("trayCount").toString());
               orgList.add(org);
               trayList.add(trayCount);
               productList.add(proCount);
           }
       }
        Map<String,Object> data =new HashMap<>();
       data.put("orgs",orgList);
       data.put("trayCounts",trayList);
       data.put("proCounts",productList);
        return data;
    }
}