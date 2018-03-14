package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.erui.comm.NewDateUtil;
import com.erui.comm.RateUtil;
import com.erui.report.util.InquiryAreaVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.MarketerCountMapper;
import com.erui.report.model.MarketerCount;
import com.erui.report.service.MarketerCountService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class MarketerCountServiceImpl extends BaseService<MarketerCountMapper> implements MarketerCountService {
    private final static Logger logger = LoggerFactory.getLogger(InquiryCountServiceImpl.class);

    @Override
    public Date selectStart() {
        return readMapper.selectStart();
    }

    @Override
    public Date selectEnd() {
        return readMapper.selectEnd();
    }

    @Override
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

        ImportDataResponse response = new ImportDataResponse(
                new String[]{"inquiryCount","quoteCount","bargainCount",
                        "bargainAmount","inquiryAmount","newMemberCount"}
        );
        response.setOtherMsg(NewDateUtil.getBeforeSaturdayWeekStr(null));
        int size = datas.size();
        MarketerCount mc = null;
        if (!testOnly) {
            writeMapper.truncateTable();
        }
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2;
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.MARKETER_COUNT, response, cellIndex)) {
                continue;
            }
            mc = new MarketerCount();
            try {
                mc.setCreateTime(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
                        DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.MARKETER_COUNT.getTable(), cellIndex, "时间字段格式错误");
                continue;
            }
            mc.setArea(strArr[1]);
            mc.setCountry(strArr[2]);
            mc.setMarketer(strArr[3]);
            if (strArr[9] != null) {
                try {
                    mc.setInquiryCount(new BigDecimal(strArr[4]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.MARKETER_COUNT.getTable(), cellIndex, "询单数量不是数字");
                    continue;
                }
            }
            if (strArr[9] != null) {
                try {
                    mc.setQuoteCount(new BigDecimal(strArr[5]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.MARKETER_COUNT.getTable(), cellIndex, "报价数量不是数字");
                    continue;
                }
            }
            if (strArr[9] != null) {
                try {
                    mc.setBargainCount(new BigDecimal(strArr[6]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.MARKETER_COUNT.getTable(), cellIndex, "成单数量不是数字");
                    continue;
                }
            }
            if (strArr[9] != null) {
                try {
                    mc.setBargainAmount(new BigDecimal(strArr[7]));
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.MARKETER_COUNT.getTable(), cellIndex, "成单金额不是数字");
                    continue;
                }
            }
            if (strArr[9] != null) {
                try {
                    mc.setInquiryAmount(new BigDecimal(strArr[8]));
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.MARKETER_COUNT.getTable(), cellIndex, "询单金额不是数字");
                    continue;
                }
            }
            if (strArr[9] != null) {
                try {
                    mc.setNewMemberCount(new BigDecimal(strArr[9]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.MARKETER_COUNT.getTable(), cellIndex, "新增会员不是数字");
                    continue;
                }
            }

            try {
                if (!testOnly) {
                    writeMapper.insertSelective(mc);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.MARKETER_COUNT.getTable(), cellIndex, e.getMessage());
                continue;
            }

            //统计数据
            if(NewDateUtil.inSaturdayWeek(mc.getCreateTime())){
                response.sumData(mc);
            }
            response.incrSuccess();

        }
        response.setDone(true);

        return response;

    }

    @Override
    public Map<String, Object> countryTopPandect(Map<String, String> params) {
       Map<String,Object> inqMap= readMapper.marketInqTop1(params);
        Map<String, Object> ordMap = readMapper.marketSucessOrdTop1(params);
        Map<String, Object> memberMap = readMapper.marketIncrMemberTop1(params);
        //获取环比参数
        String chainTime = params.get("chainTime");
        String startTime = params.get("startTime");
        params.put("startTime",chainTime);
        params.put("endTime",startTime);
        if(inqMap!=null){
            String inqCountry = inqMap.get("country").toString();
            params.put("country", inqCountry);
            Map<String,Object> chainMap= readMapper.marketInqTop1(params);
           pushChainData(inqMap,chainMap,INQUIRY);
        }
        if(ordMap!=null){
            String ordCountry = ordMap.get("country").toString();
            params.put("country", ordCountry);
            Map<String, Object> chainMap = readMapper.marketSucessOrdTop1(params);
            pushChainData(ordMap,chainMap,SUCCESS_ORDER);
        }
        if(memberMap!=null){
            String country = memberMap.get("country").toString();
            params.put("country", country);
            Map<String, Object> chainMap = readMapper.marketIncrMemberTop1(params);
            pushChainData(memberMap,chainMap,MEMBER);
        }

        Map<String,Object> data=new HashMap<>();
        data.put("inqTop1",inqMap);
        data.put("ordTop1",ordMap);
        data.put("memberTop1",memberMap);
        return data;
    }

    @Override
    public Map<String, Object> areaMarketerTopPandect(Map<String, String> params) {
        Map<String,Object> inqMap= readMapper.areaMarketerInqTop1(params);
        Map<String, Object> ordMap = readMapper.areaMarketerSucessOrdTop1(params);
        Map<String, Object> memberMap = readMapper.areaMarketerIncrMemberTop1(params);
        if(ordMap!=null) {
            ordMap.put("ordAmount", RateUtil.doubleChainRateTwo(
                    Double.parseDouble(ordMap.get("ordAmount").toString()), 1
            ));
        }
        if(inqMap!=null) {
            inqMap.put("inqAmount", RateUtil.doubleChainRateTwo(
                    Double.parseDouble(inqMap.get("inqAmount").toString()), 1
            ));
        }
        //获取环比参数
        String chainTime = params.get("chainTime");
        String startTime = params.get("startTime");
        params.put("startTime",chainTime);
        params.put("endTime",startTime);
        if(inqMap!=null){
            String area = inqMap.get("area").toString();
            String marketer = inqMap.get("marketer").toString();
            params.put("area", area);
            params.put("marketer", marketer);
            Map<String,Object> chainMap= readMapper.areaMarketerInqTop1(params);
            pushChainData(inqMap,chainMap,INQUIRY);
        }
        if(ordMap!=null){
            String area = ordMap.get("area").toString();
            String marketer = ordMap.get("marketer").toString();
            params.put("area", area);
            params.put("marketer", marketer);
            Map<String, Object> chainMap = readMapper.areaMarketerSucessOrdTop1(params);
            pushChainData(ordMap,chainMap,SUCCESS_ORDER);
        }
        if(memberMap!=null){
            String area = memberMap.get("area").toString();
            String marketer = memberMap.get("marketer").toString();
            params.put("area", area);
            params.put("marketer", marketer);
            Map<String, Object> chainMap = readMapper.areaMarketerIncrMemberTop1(params);
             pushChainData(memberMap, chainMap, MEMBER);
        }
        Map<String,Object> data=new HashMap<>();
        data.put("inqTop1",inqMap);
        data.put("ordTop1",ordMap);
        data.put("memberTop1",memberMap);
        return data;
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
    public Map<String, Object> areaDetail(Map<String, String> params) {
        Map<String,Object> result=new HashMap<>();
        Map<String,Object> chart=new HashMap<>();//图表数据数据
       Map<String,Object> pandect= readMapper.selectMarketerCountSummary(params);
        double inqAmount = Double.parseDouble(pandect.get("inqAmount").toString());
        double ordAmount = Double.parseDouble(pandect.get("ordAmount").toString());
        pandect.put("inqAmount",RateUtil.doubleChainRate(inqAmount,1));
        pandect.put("ordAmount",RateUtil.doubleChainRate(ordAmount,1));
        //获取图表数据 询单数量、报价数量、成单数量 占比
        int inqCount = Integer.parseInt(pandect.get("inqCount").toString());
        int ordCount = Integer.parseInt(pandect.get("ordCount").toString());
        int quoteCount = Integer.parseInt(pandect.get("quoteCount").toString());
        params.put("area",null);
        params.put("country",null);
        Map<String, Object> totalDatas = readMapper.selectMarketerCountSummary(params);
        int tInqCount = Integer.parseInt(totalDatas.get("inqCount").toString());//总询单数量
        int tOrdCount = Integer.parseInt(totalDatas.get("ordCount").toString());//总订单数量
        int tQuoteCount = Integer.parseInt(totalDatas.get("quoteCount").toString());//总报价数量
        double inqProportion=0.00,ordProportion=0.00,quoteProportion=0.00;
        if(tInqCount>0){
            inqProportion=RateUtil.intChainRate(inqCount,tInqCount);
        }
        if(tOrdCount>0){
            ordProportion=RateUtil.intChainRate(ordCount,tOrdCount);
        }
        if(tQuoteCount>0){
            quoteProportion=RateUtil.intChainRate(quoteCount,tQuoteCount);
        }
        List<String> xList = new ArrayList<>();
        List<Object> yList = new ArrayList<>();
        List<String> rateList = new ArrayList<>();
        xList.add("询单数量");
        xList.add("报价数量");
        xList.add("成单数量");
        yList.add(inqCount);
        yList.add(quoteCount);
        yList.add(ordCount);
        rateList.add("占比"+RateUtil.doubleChainRateTwo(inqProportion*100,1)+"%");
        rateList.add("占比"+RateUtil.doubleChainRateTwo(ordProportion*100,1)+"%");
        rateList.add("占比"+RateUtil.doubleChainRateTwo(quoteProportion*100,1)+"%");
        chart.put("xAxis",xList);
        chart.put("yAxis",yList);
        chart.put("rate",rateList);
        result.put("pandect",pandect);
        result.put("chart",chart);
        return result;
    }

    private void pushChainData(Map<String,Object> dataMap,Map<String,Object> chainMap,String type){
        if(dataMap!=null) {
            int count=0;
            if(type.equals(MEMBER)) {
                count= Integer.parseInt(dataMap.get("memberCount").toString());
            }else if(type.equals(INQUIRY)){
                count= Integer.parseInt(dataMap.get("inqCount").toString());
                dataMap.put("inqAmount",RateUtil.doubleChainRateTwo(
                        Double.parseDouble(dataMap.get("inqAmount").toString()),1
                ));
            }else if(type.equals(SUCCESS_ORDER)){
                count= Integer.parseInt(dataMap.get("ordCount").toString());
                dataMap.put("ordAmount",RateUtil.doubleChainRateTwo(
                        Double.parseDouble(dataMap.get("ordAmount").toString()),1
                ));
            }
            if (chainMap != null) {
                int chainCount =0;
                if(type.equals(SUCCESS_ORDER)){
                    chainCount= Integer.parseInt(dataMap.get("ordCount").toString());
                }else if(type.equals(MEMBER)) {
                    chainCount= Integer.parseInt(dataMap.get("memberCount").toString());
                }else if(type.equals(INQUIRY)){
                    chainCount= Integer.parseInt(dataMap.get("inqCount").toString());
                }
                int chainAdd = count - chainCount;
                double chainRate = 0.00;
                if (chainCount > 0) {
                    chainRate = RateUtil.doubleChainRate(chainAdd, chainCount);
                }
                dataMap.put("chainAdd", chainAdd);
                dataMap.put("chainRate", chainRate);
            } else {
                dataMap.put("chainAdd", 0.00);
                dataMap.put("chainRate", 0.00);
            }
        }
    }
    private  final  static  String INQUIRY="inquiry";
    private  final  static  String SUCCESS_ORDER="successOrder";
    private  final  static  String MEMBER="member";
}