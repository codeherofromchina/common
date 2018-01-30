package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.erui.comm.NewDateUtil;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.report.model.ProcurementCountExample;
import com.erui.report.util.InquiryAreaVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.ProcurementCountMapper;
import com.erui.report.model.ProcurementCount;
import com.erui.report.service.ProcurementCountService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class ProcurementCountServiceImpl extends BaseService<ProcurementCountMapper>
        implements ProcurementCountService {
    private final static Logger logger = LoggerFactory.getLogger(ProcurementCountServiceImpl.class);
    private static DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public Map<String, Object> selectArea() {
        List<Map> areaMap = readMapper.selectArea();
        List<String> areaList = new ArrayList<>();
        for (Map map : areaMap) {
            String area = map.get("area").toString();
            areaList.add(area);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("area", areaList);
        return result;
    }

    @Override
    public Map<String, Object> selectCountry(String area) {
        ProcurementCountExample requestCreditExample = new ProcurementCountExample();
        if (StringUtil.isNotBlank(area)) {
            requestCreditExample.createCriteria().andAreaEqualTo(area);
        }
        List<Map> areaCountry = readMapper.selectCountry(requestCreditExample);
        List<String> countryList = new ArrayList<>();
        for (Map map2 : areaCountry) {
            String country = map2.get("country").toString();
            countryList.add(country);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("country", countryList);
        return result;
    }

    /**
     * 具体采购数据的导入实现
     */
    @Override
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

        ImportDataResponse response = new ImportDataResponse();
        int size = datas.size();
        ProcurementCount pc = null;
        if (!testOnly) {
            writeMapper.truncateTable();
        }

        // 用于计算询单导入时的统计数据
        Map<String, BigDecimal> sumDataMap = new HashMap<>();
        sumDataMap.put("planNum", BigDecimal.ZERO); // 采购申请单数量
        sumDataMap.put("executeNum", BigDecimal.ZERO);// 签约合同数量
        sumDataMap.put("executeAmount", BigDecimal.ZERO);// 签约合同金额


        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2;
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.PROCUREMENT_COUNT, response, cellIndex)) {
                continue;
            }
            pc = new ProcurementCount();
            try {
                pc.setAssignTime(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d", DateUtil.FULL_FORMAT_STR,
                        DateUtil.SHORT_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), cellIndex, "采购申请单下达时间格式错误");
                continue;
            }
            try {
                pc.setExecuteTime(DateUtil.parseString2Date(strArr[1], "yyyy/M/d", "yyyy/M/d", DateUtil.FULL_FORMAT_STR,
                        DateUtil.SHORT_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), cellIndex, "合同签订时间格式错误");
                continue;
            }
            pc.setPlanNum(strArr[2]);
            pc.setExecuteNum(strArr[3]);
            try {
                pc.setAmmount(new BigDecimal(strArr[4]));
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), cellIndex, "金额字段非数字");
                continue;
            }
            pc.setCategory(strArr[5]);
            pc.setArea(strArr[6]);
            pc.setCountry(strArr[7]);
            pc.setOil(strArr[8]);
            try {
                if (!testOnly) {
                    writeMapper.insertSelective(pc);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), cellIndex, e.getMessage());
                continue;
            }

            // 根据日期判断是否需要统计
            if (NewDateUtil.inSaturdayWeek(pc.getAssignTime())) {
                String planNum = pc.getPlanNum();
                String executeNum = pc.getExecuteNum();
                BigDecimal ammount = pc.getAmmount();
                if (StringUtil.isNotBlank(planNum)) {
                    sumDataMap.put("planNum", sumDataMap.get("planNum").add(BigDecimal.ONE));// 采购申请单数量
                }
                if (StringUtil.isNotBlank(executeNum)) {
                    sumDataMap.put("executeNum", sumDataMap.get("executeNum").add(BigDecimal.ONE));// 签约合同数量
                }
                if (ammount != null) {
                    sumDataMap.put("executeAmount", sumDataMap.get("executeAmount").add(ammount));// 签约金额
                }
            }
            response.incrSuccess();
        }

        response.setSumMap(sumDataMap);
        response.setDone(true);

        return response;
    }

    /**
     * 获取采购总览数据实现
     */
    @Override
    public List<Map<String, Object>> selectProcurPandent(Date startTime, Date endTime) {
        //签约合同条件
        ProcurementCountExample exeExample = new ProcurementCountExample();
        ProcurementCountExample.Criteria exeCriteria = exeExample.createCriteria();
        //计划采购条件
        ProcurementCountExample planExample = new ProcurementCountExample();
        ProcurementCountExample.Criteria planCriteria = planExample.createCriteria();
        if (startTime != null) {
            exeCriteria.andExecuteTimeGreaterThanOrEqualTo(startTime);
            planCriteria.andAssignTimeGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            exeCriteria.andExecuteTimeLessThan(endTime);
            planCriteria.andAssignTimeLessThan(endTime);
        }
        //获取签约合同的数量和金额
        List<Map<String, Object>> executeList = readMapper.selectExecutePandent(exeExample);
        //获取计划执行单数量
        int planCount = readMapper.selectPlanCount(planExample);
        if (CollectionUtils.isNotEmpty(executeList)) {
            for (Map<String, Object> map : executeList) {
                map.put("procurementCount", planCount);
            }
        }
        return executeList;
    }

    /**
     * 获取采购趋势图实现
     */
    @Override
    public Map<String, Object> procurementTrend(Date startTime, Date endTime, String queryType) {
        //1.虚拟一个标准的时间集合
        List<String> dates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int days = DateUtil.getDayBetween(startTime, endTime);
        for (int i = 0; i < days; i++) {
            Date datetime = DateUtil.sometimeCalendar(startTime, -i);
            dates.add(dateFormat.format(datetime));
        }
        //2.获取趋势图数据

        ProcurementCountExample exeExample = new ProcurementCountExample();
        ProcurementCountExample.Criteria exeCriteria = exeExample.createCriteria();
        ProcurementCountExample planExample = new ProcurementCountExample();
        ProcurementCountExample.Criteria planCriteria = planExample.createCriteria();
        if (startTime != null) {
            planCriteria.andAssignTimeGreaterThanOrEqualTo(startTime);
            exeCriteria.andExecuteTimeGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            planCriteria.andAssignTimeLessThan(endTime);
            exeCriteria.andExecuteTimeLessThan(endTime);
        }
        //获取签约合同数据
        List<Map<String, Object>> exeList = readMapper.selectExecuteDataTrend(exeExample);
        //获取采购单数量数据
        List<Map<String, Object>> planList = readMapper.selectPlanDataTrend(exeExample);
        //整合数据
        Map<String, Map<String, Object>> planMap = planList.parallelStream().collect(Collectors.toMap(vo -> vo.get("assignTime").toString(), vo -> vo));
        Map<String, Map<String, Object>> exeMap = exeList.parallelStream().collect(Collectors.toMap(vo -> vo.get("assignTime").toString(), vo -> vo));

        exeList.stream().forEach(map->{
            if(planMap.containsKey(map.get("assignTime").toString())){
                int planCount = Integer.parseInt(planMap.get(map.get("assignTime").toString()).get("procurementCount").toString());
                map.put("procurementCount",planCount);
            }else {
                map.put("procurementCount",0);
            }
        });
        planList.stream().forEach(map->{
            if(!exeMap.containsKey(map.get("assignTime").toString())){
               map.put("signingContractCount",0);
               map.put("signingContractAmount",0d);
               exeList.add(map);
            }
        });
        //3.处理数据
        Map<String, Map<String, Object>> dataMap = exeList.parallelStream().collect(Collectors.toMap(vo -> vo.get("assignTime").toString(), vo -> vo));
        List<Integer> procurementList = new ArrayList<>();
        List<Integer> signingContractList = new ArrayList<>();
        List<Double> signingContractAmountList = new ArrayList<>();

        for (String date : dates) {
            if (dataMap.containsKey(date)) {
                procurementList.add(Integer.parseInt(dataMap.get(date).get("procurementCount").toString()));
                signingContractList.add(Integer.parseInt(dataMap.get(date).get("signingContractCount").toString()));
                signingContractAmountList.add(RateUtil.doubleChainRateTwo(Double.parseDouble(dataMap.get(date).get("signingContractAmount").toString()), 1d));
            } else {
                procurementList.add(0);
                signingContractList.add(0);
                signingContractAmountList.add(0d);
            }
        }
        Map<String, Object> datas = new HashMap<>();
        String[] types = {"采购申请单", "签约合同", "合同金额"};
        if (queryType.equals(types[0])) {
            datas.put("legend", types[0]);
            datas.put("xAxis", dates);
            datas.put("yAxis", procurementList);
        }
        if (queryType.equals(types[1])) {
            datas.put("legend", types[1]);
            datas.put("xAxis", dates);
            datas.put("yAxis", signingContractList);
        }
        if (queryType.equals(types[2])) {
            datas.put("legend", types[2]);
            datas.put("xAxis", dates);
            datas.put("yAxis", signingContractAmountList);
        }
        return datas;
    }

    /**
     * 获取大区和国家列表实现
     */
    @Override
    public List<InquiryAreaVO> selectAllAreaAndCountryList() {
        List<Map<String, String>> dataList = this.readMapper.selectAllAreaAndCountryList();
        List<InquiryAreaVO> list = new ArrayList<>();
        if (dataList != null && dataList.size() > 0) {
            Map<String, InquiryAreaVO> map = list.parallelStream()
                    .collect(Collectors.toMap(InquiryAreaVO::getAreaName, vo -> vo));
            for (Map<String, String> data : dataList) {
                String area = data.get("area");
                String country = data.get("country");
                InquiryAreaVO vo = null;
                if (map.containsKey(area)) {
                    vo = map.get(area);
                } else {
                    vo = new InquiryAreaVO();
                    vo.setAreaName(area);
                    list.add(vo);
                    map.put(area, vo);
                }
                vo.pushCountry(country);
            }

        }
        return list;
    }

    /**
     * 区域明细实现
     */
    @Override
    public Map<String, Object> selectAreaDetailData(Date startTime, Date endTime, String area, Object country) {
        //区域 签约合同条件
        ProcurementCountExample exeExample = new ProcurementCountExample();
        ProcurementCountExample.Criteria exeCriteria = exeExample.createCriteria();
        //区域 计划采购单条件
        ProcurementCountExample planExample = new ProcurementCountExample();
        ProcurementCountExample.Criteria planCriteria = planExample.createCriteria();

        if (startTime != null) {
            planCriteria.andAssignTimeGreaterThanOrEqualTo(startTime);
            exeCriteria.andExecuteTimeGreaterThanOrEqualTo(startTime);

        }
        if (endTime != null) {
            planCriteria.andAssignTimeLessThan(endTime);
            exeCriteria.andExecuteTimeLessThan(endTime);
        }
        if (StringUtil.isNotBlank(area)) {
            exeCriteria.andAreaEqualTo(area);
            planCriteria.andAreaEqualTo(area);
        }
        if (country != null) {
            if (StringUtil.isNotBlank(country.toString())) {
                exeCriteria.andCountryEqualTo(country.toString());
                planCriteria.andCountryEqualTo(country.toString());
            }
        }
        //查询当前区域的计划采购单数量、签约合同数量 、签约合同金额
        List<Map<String, Object>> maps = readMapper.selectExecutePandent(exeExample);
        int planCount = readMapper.selectPlanCount(planExample);
        if (CollectionUtils.isNotEmpty(maps)) {
            for (Map<String, Object> map : maps) {
                map.put("procurementCount", planCount);
            }
        }
        //查询总的计划采购单数量、签约合同数量 、签约合同金额
        List<Map<String, Object>> totalMaps = this.selectProcurPandent(startTime, endTime);
        int procurementCount = 0;
        int signingContractCount = 0;
        double signingContractAmount = 0d;
        int proCount = 0;
        int signCount = 0;
        double signAmount = 0d;
        double proProportion = 0d;
        double signCountProportion = 0d;
        double signAmountProportion = 0d;
        if (maps != null && maps.size() > 0) {
            Map<String, Object> data = maps.get(0);
            procurementCount = Integer.parseInt(data.get("procurementCount").toString());
            signingContractCount = Integer.parseInt(data.get("signingContractCount").toString());
            if (data.get("signingContractAmount") != null) {
                signingContractAmount = RateUtil.doubleChainRateTwo(Double.parseDouble(data.get("signingContractAmount").toString()), 1d);
            }
        }
        if (totalMaps != null && totalMaps.size() > 0) {
            Map<String, Object> data = totalMaps.get(0);
            proCount = Integer.parseInt(data.get("procurementCount").toString());
            signCount = Integer.parseInt(data.get("signingContractCount").toString());
            if (data.get("signingContractAmount") != null) {
                signAmount = Double.parseDouble(data.get("signingContractAmount").toString());
            }
        }
        if (proCount > 0) {
            proProportion = RateUtil.intChainRate(procurementCount, proCount);
        }
        if (signCount > 0) {
            signCountProportion = RateUtil.intChainRate(signingContractCount, signCount);
        }
        if (signAmount > 0) {
            signAmountProportion = RateUtil.doubleChainRate(signingContractAmount, signAmount);
        }

        //整理数据
        List<Object> dataList = new ArrayList<>();
        List<String> xList = new ArrayList<>();
        dataList.add(procurementCount);
        dataList.add(signingContractCount);
        dataList.add(signingContractAmount);
        xList.add("采购申请单数量-占比" + df.format(proProportion * 100) + "%");
        xList.add("签约合同数-占比" + df.format(signCountProportion * 100) + "%");
        xList.add("签约合同金额-占比" + df.format(signAmountProportion * 100) + "%");
        Map<String, Object> data = new HashMap<>();
        data.put("yAxis", dataList);
        data.put("xAxis", xList);
        return data;
    }

    /**
     * 获取分类数据实现
     */
    @Override
    public List<ProcurementCount> selectCategoryDetail(Date startTime, Date endTime) {
        //签约合同条件
        ProcurementCountExample exeExample = new ProcurementCountExample();
        ProcurementCountExample.Criteria exeCriteria = exeExample.createCriteria();
        //计划采购单条件
        ProcurementCountExample planExample = new ProcurementCountExample();
        ProcurementCountExample.Criteria planCriteria = planExample.createCriteria();
        if (startTime != null) {
            planCriteria.andAssignTimeGreaterThanOrEqualTo(startTime);
            exeCriteria.andExecuteTimeGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            planCriteria.andAssignTimeLessThan(endTime);
            exeCriteria.andExecuteTimeLessThan(endTime);
        }

        //获取各分类签约合同数量和金额
        List<ProcurementCount> exeList = readMapper.selectCategoryExecuteData(exeExample);
        //获取各分类计划单数量
        List<ProcurementCount> planList = readMapper.selectCategoryPlanData(planExample);
        //整合数据
        Map<String, ProcurementCount> planMap = planList.stream().collect(Collectors.toMap(vo -> vo.getCategory(), vo -> vo));
        Map<String, ProcurementCount> exeMap = exeList.stream().collect(Collectors.toMap(vo -> vo.getCategory(), vo -> vo));
        exeList.stream().forEach(vo -> {
            if (planMap.containsKey(vo.getCategory())) {
                vo.setPlanNum(planMap.get(vo.getCategory()).getPlanNum());
            } else {
                vo.setPlanNum(String.valueOf(0));
            }
        });

        planList.stream().forEach(vo -> {
            if (!exeMap.containsKey(vo.getCategory())) {
                vo.setExecuteNum(String.valueOf(0));
                vo.setAmmount(BigDecimal.ZERO);
                exeList.add(vo);
            }
        });
        return exeList;
    }
}