package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.erui.comm.RateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.report.model.CreditExtensionExample;
import com.erui.report.util.InquiryAreaVO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.CreditExtensionMapper;
import com.erui.report.model.CreditExtension;
import com.erui.report.service.CreditExtensionService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class CreditExtensionServiceImpl extends BaseService<CreditExtensionMapper> implements CreditExtensionService {
    private final static Logger logger = LoggerFactory.getLogger(CreditExtensionServiceImpl.class);


    @Override
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

        ImportDataResponse response = new ImportDataResponse();
        int size = datas.size();
        CreditExtension ce = null;
        if (!testOnly) {
            writeMapper.truncateTable();
        }
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2;
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.CREDIT_EXTENSION, response, cellIndex)) {
                continue;
            }
            ce = new CreditExtension();
            try {
                ce.setCreditDate(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
                        DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.CREDIT_EXTENSION.getTable(), cellIndex, "时间字段格式错误");
                continue;
            }
            ce.setCreditArea(strArr[1]);
            ce.setCreditCountry(strArr[2]);
            ce.setMailCode(strArr[3]);
            ce.setCustomerName(strArr[4]);
            try {
                ce.setRelyCredit(new BigDecimal(strArr[5]));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.CREDIT_EXTENSION.getTable(), cellIndex, "批复信用额度不是数字");
                continue;
            }
            try {
                ce.setEffectiveDate(DateUtil.parseString2Date(strArr[6], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
                        DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.CREDIT_EXTENSION.getTable(), cellIndex, "限额生效日期格式错误");
                continue;
            }

            if (strArr[7] != null && !"无期使用".equals(strArr[7])) {
                try {
                    ce.setExpiryDate(DateUtil.parseString2Date(strArr[7], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
                            DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.CREDIT_EXTENSION.getTable(), cellIndex, "限额失效日期格式错误");
                    continue;
                }
            }
            try {
                ce.setUsedAmount(new BigDecimal(strArr[8]));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.CREDIT_EXTENSION.getTable(), cellIndex, "已用额度不是数字");
                continue;
            }
            try {
                ce.setAvailableAmount(new BigDecimal(strArr[9]));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.CREDIT_EXTENSION.getTable(), cellIndex, "可用额度不是数字");
                continue;
            }

            try {
                if (!testOnly) {
                    writeMapper.insertSelective(ce);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.CREDIT_EXTENSION.getTable(), cellIndex, e.getMessage());
                continue;
            }
            response.incrSuccess();

        }
        response.setDone(true);

        return response;
    }

    @Override
    public Map<String, Object> creditPandect(Map<String, String> params) {

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> creditData = new HashMap<>();
        Map<String, Object> usedCredit = new HashMap<>();
        Map<String, Object> addCredit = new HashMap<>();

        //获取当前数据
        Map<String, Object> datas = readMapper.selectCreditSummary(params);
        //获取环比数据
        String chainStartTime = params.get("chainStartTime");
        String startTime = params.get("startTime");
        if (StringUtil.isNotBlank(chainStartTime)) {
            params.put("startTime", chainStartTime);
            params.put("endTime", startTime);
        }
        Map<String, Object> chainDatas = readMapper.selectCreditSummary(params);
        //处理数据
        Double usedRate = 0.0;//使用率
        Double usedChainRate = 0.0;//使用环比
        Double lossRate = 0.0;//流失率
        Double incrCreditRate = 0.0;//新增授信环比
        Integer creditCount = Integer.parseInt(datas.get("creditCount").toString());
        Double creditAmount = Double.parseDouble(datas.get("creditAmount").toString());
        Double usedAmount = Double.parseDouble(datas.get("usedAmount").toString());
        Double availAmount = Double.parseDouble(datas.get("availAmount").toString());
        int incrCredit = Integer.parseInt(datas.get("incrCredit").toString());
        int elimiCredit = Integer.parseInt(datas.get("elimiCredit").toString());
        if (usedAmount + availAmount > 0) {
            usedRate = RateUtil.doubleChainRate(usedAmount, usedAmount + availAmount);
        }
        Double chainUsedAmount = Double.parseDouble(chainDatas.get("usedAmount").toString());
        if (chainUsedAmount > 0) {
            usedChainRate = RateUtil.doubleChainRate(usedAmount - chainUsedAmount, chainUsedAmount);
        }
        if (incrCredit > 0) {
            lossRate = RateUtil.intChainRate(elimiCredit, incrCredit);
        }
        int chainIncrCredit = Integer.parseInt(chainDatas.get("incrCredit").toString());
        if (chainIncrCredit > 0) {
            incrCreditRate = RateUtil.intChainRate(incrCredit - chainIncrCredit, chainIncrCredit);
        }

        //封装数据
        creditData.put("creditCount", creditCount);
        creditData.put("creditAmount", creditAmount);
        usedCredit.put("usedCreditAmount", usedAmount);
        usedCredit.put("availableCreditAmount", availAmount);
        usedCredit.put("usedRate", usedRate);
        usedCredit.put("chainRate", usedChainRate);
        addCredit.put("addCreditCount", incrCredit);
        addCredit.put("eliminateCreditCount", elimiCredit);
        addCredit.put("lossRate", lossRate);
        addCredit.put("chainRate", incrCreditRate);
        data.put("credit", creditData);
        data.put("usedCredit", usedCredit);
        data.put("addCredit", addCredit);
        return data;
    }

    @Override
    public Map<String, Object> creditTrend(Date startTime, Date endTime, String creditType) {

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
        //授信数量、可用授信、已用授信
        List<Map<String, Object>> creditList = readMapper.selectCreditGroupByCreditDate(params);
        //新增授信
        List<Map<String, Object>> incrList = readMapper.selectIncrCreditGroupByEffectiveDate(params);
        //淘汰授信
        List<Map<String, Object>> elimiList = readMapper.selectElimiCreditGroupByExpiryDate(params);
        //3.处理数据
        List<Integer> creditCountList = new ArrayList<>(); //授信数量列表
        List<Double> usedCreditList = new ArrayList<>(); //已用授信列表
        List<Double> availCreditList = new ArrayList<>();//可用授信列表
        List<Integer> incrCreditList = new ArrayList<>();//新增授信列表
        List<Integer> elimiCreditList = new ArrayList<>();//淘汰授信列表
        Map<String, Map<String, Object>> creditMap =
                creditList.stream().collect(Collectors.toMap(vo -> vo.get("creditDate").toString(), vo -> vo));

        Map<String, Map<String, Object>> incrMap =
                incrList.stream().collect(Collectors.toMap(vo -> vo.get("creditDate").toString(), vo -> vo));

        Map<String, Map<String, Object>> elimiMap =
                elimiList.stream().collect(Collectors.toMap(vo -> vo.get("creditDate").toString(), vo -> vo));

        for (String date:dates) {
            if(creditMap.containsKey(date)){
                Map<String, Object> creditData = creditMap.get(date);
                int creditCount = Integer.parseInt(creditData.get("creditCount").toString());
                double usedAmount =
                        RateUtil.doubleChainRateTwo(Double.parseDouble(creditData.get("usedAmount").toString()),1);
                double availAmount =
                        RateUtil.doubleChainRateTwo( Double.parseDouble(creditData.get("availAmount").toString()),1);
                creditCountList.add(creditCount);
                usedCreditList.add(usedAmount);
                availCreditList.add(availAmount);
            }else {
                creditCountList.add(0);
                usedCreditList.add(0.00);
                availCreditList.add(0.00);
            }
            if(incrMap.containsKey(date)){
                Map<String, Object> incrData = incrMap.get(date);
                int incrCount = Integer.parseInt(incrData.get("incrCount").toString());
                incrCreditList.add(incrCount);
            }else {
                incrCreditList.add(0);
            }
            if(elimiMap.containsKey(date)){
                Map<String, Object> elimiData = elimiMap.get(date);
                int elimiCount = Integer.parseInt(elimiData.get("elimiCount").toString());
                elimiCreditList.add(elimiCount);
            }else {
                elimiCreditList.add(0);
            }
        }

        Map<String, Object> datas = new HashMap<>();
        datas.put("xAxis", dates);
        if(creditType.equals(CREDIT_COUNT)){
            datas.put("legend",CREDIT_COUNT);
            datas.put("yAxis", creditCountList);
        }
        if(creditType.equals(USED_CREDIT)){
            datas.put("legend",USED_CREDIT);
            datas.put("yAxis", usedCreditList);
        }
        if(creditType.equals(AVAILABLE_CREDIT)){
            datas.put("legend",AVAILABLE_CREDIT);
            datas.put("yAxis", availCreditList);
        }
        if(creditType.equals(INCR_CREDIT)){
            datas.put("legend",INCR_CREDIT);
            datas.put("yAxis", incrCreditList);
        }
        if(creditType.equals(ELIMINATE_CREDIT)){
            datas.put("legend",ELIMINATE_CREDIT);
            datas.put("yAxis", elimiCreditList);
        }
        return datas;
    }

    @Override
    public List<InquiryAreaVO> selectAllAreaAndCountryList() {

        List<InquiryAreaVO> result =new ArrayList<>();
        // 查询所有询单的大区和国家信息 {'area':'大区名称','country':'城市名称'}
        List<Map<String, String>> areaAndCountryList = readMapper.selectAllAreaAndCountryList();
        Map<String, InquiryAreaVO> resultMap =
                result.stream().collect(Collectors.toMap(vo -> vo.getAreaName(), vo -> vo));
        areaAndCountryList.stream().forEach(map->{
            InquiryAreaVO vo=null;
            String area = map.get("area");
            if(resultMap.containsKey(area)){
                vo= resultMap.get(area);
            }else {
                vo=new InquiryAreaVO();
                vo.setAreaName(area);
                result.add(vo);
                resultMap.put(area,vo);
            }
            vo.pushCountry(map.get("country"));
        });

        return result;
    }

    @Override
    public Map<String, Object> areaDetail(Map<String,String> params) {

        Map<String,Object> result=new HashMap<>();//总览数据
        Map<String,Object> pandect=new HashMap<>();//总览数据
        Map<String,Object> chart=new HashMap<>();//图表数据数据
        Map<String, Object> datas = readMapper.selectCreditSummary(params);
        int creditCount = Integer.parseInt(datas.get("creditCount").toString());
        Double usedAmount = Double.parseDouble(datas.get("usedAmount").toString());
        Double availAmount = Double.parseDouble(datas.get("availAmount").toString());
        int incrCredit = Integer.parseInt(datas.get("incrCredit").toString());
        int elimiCredit = Integer.parseInt(datas.get("elimiCredit").toString());
        pandect.put("creditCount",creditCount);
        pandect.put("usedCredit",usedAmount);
        pandect.put("vailableCredit",availAmount);
        pandect.put("addCredit",incrCredit);
        pandect.put("eliminateCredit",elimiCredit);

        //查询 授信数量、 已用额度 、可用额度占比
        params.put("area",null);
        params.put("country",null);
        Map<String, Object> totalDatas = readMapper.selectCreditSummary(params);
        int totalCount = Integer.parseInt(datas.get("creditCount").toString());
        Double totalUsedAmount = Double.parseDouble(datas.get("usedAmount").toString());
        Double totalAvailAmount = Double.parseDouble(datas.get("availAmount").toString());
        Double countRate=0.00,usedRate=0.00,availRate=0.00;
        if(totalCount>0){
            countRate= RateUtil.intChainRate(creditCount,totalCount);
        }
        if(totalUsedAmount>0){
            usedRate= RateUtil.doubleChainRate(usedAmount,totalUsedAmount);
        }
        if(totalAvailAmount>0){
            availRate= RateUtil.doubleChainRate(availAmount,totalAvailAmount);
        }
        List<String> xList = new ArrayList<>();
        List<Object> yList = new ArrayList<>();
        List<String> rateList = new ArrayList<>();
        xList.add("授信数量");
        xList.add("已用额度");
        xList.add("可用额度");
        yList.add(creditCount);
        yList.add(usedAmount);
        yList.add(availAmount);
        rateList.add("占比"+countRate*100+"%");
        rateList.add("占比"+usedRate*100+"%");
        rateList.add("占比"+availRate*100+"%");
        chart.put("xAxis",xList);
        chart.put("yAxis",yList);
        chart.put("rate",rateList);
        result.put("pandect",pandect);
        result.put("chart",chart);
        return result;
    }

    private final static String CREDIT_COUNT = "creditCount";
    private final static String USED_CREDIT = "usedCredit";
    private final static String AVAILABLE_CREDIT = "availableCredit";
    private final static String INCR_CREDIT = "addCredit";
    private final static String ELIMINATE_CREDIT = "eliminateCredit";
}