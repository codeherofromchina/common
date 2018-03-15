package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.erui.comm.NewDateUtil;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.report.dao.CategoryQualityMapper;
import com.erui.report.model.CategoryQuality;
import com.erui.report.service.CategoryQualityService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class CategoryQualityServiceImpl extends BaseService<CategoryQualityMapper> implements CategoryQualityService {
    private final static Logger logger = LoggerFactory.getLogger(CategoryQualityServiceImpl.class);

    @Override
    public Date selectStart() {
        return  readMapper.selectStart();
    }

    @Override
    public Date selectEnd() {
        return readMapper.selectEnd();
    }

    @Override
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

        ImportDataResponse response = new ImportDataResponse(
                new String[]{"inspectionTotal","proInfactoryCheckPassCount","proOutfactoryTotal",
                        "proOutfactoryCheckCount","assignmentsTotal","productsQualifiedCount"}
        );
        response.setOtherMsg(NewDateUtil.getBeforeSaturdayWeekStr(null));
        int size = datas.size();
        CategoryQuality cq = null;
        if (!testOnly) {
            writeMapper.truncateTable();
        }
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2;
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.CATEGORY_QUALITY, response, cellIndex)) {
                continue;
            }

            cq = new CategoryQuality();
            if(strArr[0]!=null) {
                try {
                    cq.setQualityControlDate(DateUtil.parseString2Date(strArr[0], DateUtil.SHORT_SLASH_FORMAT_STR, DateUtil.FULL_FORMAT_STR2,
                            DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "时间字段格式错误");
                    continue;
                }
            }
            if (strArr[1] != null) {
                try {
                    cq.setInspectionTotal(new BigDecimal(strArr[1]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "报检总数不是数字");
                    continue;
                }
            }
            if (strArr[2] != null) {
                try {
                    cq.setProInfactoryCheckPassCount(new BigDecimal(strArr[2]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "产品入厂首检合格数不是数字");
                    continue;
                }
            }
            if (strArr[3] != null) {
                try {
                    cq.setProInfactoryCheckPassRate(new BigDecimal(strArr[3]));
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "产品入厂首检合格率不是数字");
                    continue;
                }
            }
            if (strArr[4] != null) {
                try {
                    cq.setProOutfactoryTotal(new BigDecimal(strArr[4]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "产品出厂总数不是数字");
                    continue;
                }
            }
            if (strArr[5] != null) {
                try {
                    cq.setProOutfactoryCheckCount(new BigDecimal(strArr[5]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "出厂检验合格数不是数字");
                    continue;
                }
            }
            if (strArr[6] != null) {
                try {
                    cq.setProOutfactoryCheckRate(new BigDecimal(strArr[6]));
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "产品出厂检验合格率不是数字");
                    continue;
                }
            }
            if (strArr[7] != null) {
                try {
                    cq.setAssignmentsTotal(new BigDecimal(strArr[7]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "外派监造总数不是数字");
                    continue;
                }
            }
            if (strArr[8] != null) {
                try {
                    cq.setProductsQualifiedCount(new BigDecimal(strArr[8]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "监造产品出厂合格数不是数字");
                    continue;
                }
            }
            if (strArr[9] != null) {
                try {
                    cq.setProductsAssignmentsQualifiedRate(new BigDecimal(strArr[9]));
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.CATEGORY_QUALITY.getTable(), cellIndex, "产品外派监造合格率不是数字");
                    continue;
                }
            }

            try {
                if (!testOnly) {
                    writeMapper.insertSelective(cq);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.INQUIRY_COUNT.getTable(), cellIndex, e.getMessage());
                continue;
            }
            // 在上周范围内则统计
            if (NewDateUtil.inSaturdayWeek(cq.getQualityControlDate())) {
                response.sumData(cq);
            }
            response.incrSuccess();


        }
        response.setDone(true);

        return response;
    }

    @Override
    public Map<String, Object> selectQualitySummaryData(Map<String, String> params) {
        Map<String, Object> data = readMapper.selectQualitySummaryData(params);

        if(data.get("inspectPassRate")!=null){
            double inspectPassRate = Double.parseDouble(data.get("inspectPassRate").toString());
            data.put("inspectPassRate",RateUtil.doubleChainRate(inspectPassRate,1));
        }else {
            data.put("inspectPassRate",0.00);
        }
        if(data.get("outfactoryPassRate")!=null){
            double outfactoryPassRate = Double.parseDouble(data.get("outfactoryPassRate").toString());
            data.put("outfactoryPassRate",RateUtil.doubleChainRate(outfactoryPassRate,1));
        }else {
            data.put("outfactoryPassRate",0.00);
        }
        if(data.get("assignPassRate")!=null){
            double assignPassRate = Double.parseDouble(data.get("assignPassRate").toString());
            data.put("assignPassRate",RateUtil.doubleChainRate(assignPassRate,1));
        }else {
            data.put("assignPassRate",0.00);
        }
        return data;
    }

    @Override
    public Map<String, Object> selectQualityTrendData(Date startTime, Date endTime, String type) {
        //1.构建一个标准的时间集合
        List<String> dates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int days = DateUtil.getDayBetween(startTime, endTime);
        for (int i = 0; i < days; i++) {
            Date datetime = DateUtil.sometimeCalendar(startTime, -i);
            dates.add(dateFormat.format(datetime));
        }
        //2.获取趋势图数据
        Map<String, String> params = new HashMap<>();
        params.put("startTime", DateUtil.formatDateToString(startTime, DateUtil.FULL_FORMAT_STR2));
        params.put("endTime", DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2));
        List<Map<String, Object>> trendList = readMapper.selectTrendData(params);
        //3.处理数据
        List<Integer> inspectList = new ArrayList<>(); //报检总数列表
        List<Integer> inspectPassList = new ArrayList<>(); //首检合格数列表
        List<Integer> factoryList = new ArrayList<>(); //出厂总数列表
        List<Integer> factoryPassList = new ArrayList<>(); //出厂合格数列表
        List<Integer> assignList = new ArrayList<>(); //外派监造总数列表
        List<Integer> assignPassList = new ArrayList<>(); //外派监造合格数列表
        Map<String, Map<String, Object>> trendMap =
                trendList.stream().collect(Collectors.toMap(vo -> vo.get("qualityDate").toString(), vo -> vo));
        for (String date :dates) {
            if(trendMap.containsKey(date)){
                Map<String, Object> data = trendMap.get(date);
                inspectList.add(Integer.parseInt(data.get("inspectTotal").toString()));
                inspectPassList.add(Integer.parseInt(data.get("inspectPassCount").toString()));
                factoryList.add(Integer.parseInt(data.get("outfactoryTotal").toString()));
                factoryPassList.add(Integer.parseInt(data.get("outfactoryPassCount").toString()));
                assignList.add(Integer.parseInt(data.get("assignTotal").toString()));
                assignPassList.add(Integer.parseInt(data.get("assignPassCount").toString()));
            }else {
                inspectList.add(0);
                inspectPassList.add(0);
                factoryList.add(0);
                factoryPassList.add(0);
                assignList.add(0);
                assignPassList.add(0);
            }
        }
        Map<String, Object> datas = new HashMap<>();
        datas.put("xAxis", dates);
        if(type.equals(INSPECTION)){
            datas.put("legend",INSPECTION);
            datas.put("yAxis", inspectList);
            datas.put("passList", inspectPassList);
        }
        if(type.equals(FACTORY)){
            datas.put("legend",FACTORY);
            datas.put("yAxis", factoryList);
            datas.put("passList", factoryPassList);
        }
        if(type.equals(FOREIGN)){
            datas.put("legend",FOREIGN);
            datas.put("yAxis", assignList);
            datas.put("passList", assignPassList);
        }

        return datas;
    }

    private  final static  String INSPECTION ="inspection";
    private  final static  String FACTORY ="factory";
    private  final static  String FOREIGN ="foreign";
}