package com.erui.report.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.RequestCreditMapper;
import com.erui.report.dao.RequestReceiveMapper;
import com.erui.report.model.RequestCredit;
import com.erui.report.model.RequestCreditExample;
import com.erui.report.model.RequestReceive;
import com.erui.report.model.RequestReceiveExample;
import com.erui.report.service.RequestReceiveService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.ptg.NotEqualPtg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RequestReceiveServiceImpl extends  BaseService<RequestReceiveMapper> implements RequestReceiveService{
    private  final static Logger logger= LoggerFactory.getLogger(RequestReceiveServiceImpl.class);
    @Override
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {
        // 回款金额 - backAmount、 应收未收金额 - receiveAmount
        ImportDataResponse response = new ImportDataResponse(new String[]{"backAmount"});
        response.setOtherMsg(NewDateUtil.getBeforeSaturdayWeekStr(null));
        int size = datas.size();
        RequestReceive receive = null;
        if (!testOnly) {
            writeMapper.truncateTable();
        }

        BigDecimal nextMouthReceiveAmount = BigDecimal.ZERO; // 定义下月应收金额
        BigDecimal receivedAmount = BigDecimal.ZERO; // 定义应收余额
        // 遍历数据
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2;
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.REQUEST_RECEIVE, response, cellIndex)) {
                continue;
            }
            receive = new RequestReceive();
            receive.setSerialNum(strArr[0]);
            receive.setOrderNum(strArr[1]);
            receive.setSalesMainCompany(strArr[2]);
            receive.setSalesArea(strArr[3]);
            receive.setSalesCountry(strArr[4]);
            receive.setOrganization(strArr[5]);
            receive.setCustomerCode(strArr[6]);
            receive.setExportProName(strArr[7]);
            receive.setTradeTerms(strArr[8]);
            try {
                receive.setCreateAt(
                        DateUtil.parseString2Date(strArr[9], DateUtil.SHORT_FORMAT_STR, "yyyy/M/d", "yyyy/M/d hh:mm:ss", DateUtil.FULL_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.REQUEST_RECEIVE.getTable(), cellIndex, "创建日期格式错误");
                continue;
            }
            try {
                receive.setOrderAmount(new BigDecimal(strArr[10]));
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.REQUEST_RECEIVE.getTable(), cellIndex, "订单金额字段非数字类型");
                continue;
            }
            receive.setCreditCurrency(strArr[11]);
            try {
                receive.setReceiveAmount(new BigDecimal(strArr[12]));
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.REQUEST_RECEIVE.getTable(), cellIndex, "应收账款余额字段非数字类型");
                continue;
            }

            receive.setBackResponsePerson(strArr[13]);
            try {
                receive.setNextBackDate(
                        DateUtil.parseString2Date(strArr[14], DateUtil.FULL_FORMAT_STR, "yyyy/M/d", "yyyy/M/d hh:mm:ss", DateUtil.SHORT_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.REQUEST_RECEIVE.getTable(), cellIndex, "下次回款日期格式错误");
                continue;
            }

            try {
                receive.setBackDate(
                        DateUtil.parseString2Date(strArr[15], DateUtil.FULL_FORMAT_STR, "yyyy/M/d", "yyyy/M/d hh:mm:ss", DateUtil.SHORT_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.REQUEST_RECEIVE.getTable(), cellIndex, "回款日期格式错误");
                continue;
            }
            try {
                receive.setBackAmount(new BigDecimal(strArr[16]));

            } catch (Exception ex) {
                logger.error(ex.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.REQUEST_RECEIVE.getTable(), cellIndex, "回款金额字段非数字类型");
                continue;
            }

            try {
                receive.setOtherAmount(new BigDecimal(strArr[17]));
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.REQUEST_RECEIVE.getTable(), cellIndex, "其他扣费金额字段非数字类型");
                continue;
            }
            try {
                if (!testOnly) {
                    writeMapper.insertSelective(receive);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.REQUEST_RECEIVE.getTable(), cellIndex, e.getMessage());
                continue;
            }
            // 在上周范围内的数据做统计
            if (NewDateUtil.inSaturdayWeek(receive.getBackDate())) {
                response.sumData(receive);
            }
            // 下自然月应收金额
//            if (NewDateUtil.inNextMonth(receive.getNextBackDate()) && receive.getReceiveAmount() != null) {
//                nextMouthReceiveAmount = nextMouthReceiveAmount.add(receive.getReceiveAmount());
//            }
            response.incrSuccess();

        }
        //只要本期回款金额
        RequestCreditMapper creditMapper = readerSession.getMapper(RequestCreditMapper.class);
        Date[] dates = NewDateUtil.getBeforeSaturdayWeek(null);
        Date startTime=dates[0];
        Date end=dates[1];
        String endTime1 = DateUtil.getEndTime(end, DateUtil.FULL_FORMAT_STR);
        Date endTime = DateUtil.parseString2DateNoException(endTime1, DateUtil.FULL_FORMAT_STR);
        RequestCreditExample creditExample = new RequestCreditExample();
        RequestCreditExample.Criteria creditCriteria = creditExample.createCriteria();
        if(startTime!=null){
            creditCriteria.andBackDateGreaterThanOrEqualTo(startTime);
        }
        if(endTime!=null){
            creditCriteria.andBackDateLessThan(endTime);
        }
        List<RequestCredit> credits = creditMapper.selectByExample(creditExample);
        if(credits!=null&&credits.size()>0){
            for (RequestCredit cr:credits ) {
                if(cr.getReceiveAmount()!=null){
                    receivedAmount=receivedAmount.add(cr.getReceiveAmount());
                }
                if (NewDateUtil.inNextMonth(cr.getBackDate()) && cr.getReceiveAmount() != null) {
                nextMouthReceiveAmount = nextMouthReceiveAmount.add(cr.getReceiveAmount());
            }
            }
        }
        Map<String, BigDecimal> map = response.getSumMap();
        map.put("hasReceivedAmount", map.get("backAmount"));
        map.put("receiveAmount", receivedAmount);
        map.put("orderAmount", receivedAmount.add(map.get("backAmount")));
        map.put("nextMouthReceiveAmount", nextMouthReceiveAmount);
        response.setDone(true);
        return response;
    }

    @Override
    public Double selectBackAmount(Date startTime, Date endTime, String company, String org, String area, String country) {
        RequestReceiveExample example = new RequestReceiveExample();
        RequestReceiveExample.Criteria criteria = example.createCriteria();
        if(startTime!=null){
            criteria.andBackDateGreaterThanOrEqualTo(startTime);
        }
        if(endTime!=null){
            criteria.andBackDateLessThan(endTime);
        }
        if(StringUtils.isNotBlank(company)){
            if(company.equals("除易瑞全部")){
                criteria.andSalesMainCompanyNotLike("%易瑞%");
            }else {
                criteria.andSalesMainCompanyEqualTo(company);
            }
        }
        if(StringUtils.isNotBlank(org)){
            if(org.equals("除易瑞全部")){
                criteria.andOrganizationNotLike("%易瑞%");
            }else {
                criteria.andOrganizationEqualTo(org);
            }
        }
        if(StringUtils.isNotBlank(area)){
            criteria.andSalesAreaEqualTo(area);
        }
        if(StringUtils.isNotBlank(country)){
            criteria.andSalesCountryEqualTo(country);
        }
        return readMapper.selectBackAmount(example);
    }

    @Override
    public Double selectReceiveBalance(Date startTime, Date endTime) {
        RequestReceiveExample example = new RequestReceiveExample();
        RequestReceiveExample.Criteria criteria = example.createCriteria();
        if(startTime!=null){
            criteria.andBackDateGreaterThanOrEqualTo(startTime);
        }
        if(endTime!=null){
            criteria.andBackDateLessThan(endTime);
        }

        return readMapper.selectReceiveBalance(example);
    }

    @Override
    public List<Map<String, Object>> selectBackAmountGroupByArea(Date startTime, Date endTime) {
        RequestReceiveExample example = new RequestReceiveExample();
        RequestReceiveExample.Criteria criteria = example.createCriteria();
        if(startTime!=null){
            criteria.andBackDateGreaterThanOrEqualTo(startTime);
        }
        if(endTime!=null){
            criteria.andBackDateLessThan(endTime);
        }

        return readMapper.selectBackAmountGroupByArea(example);
    }

    @Override
    public List<Map<String, Object>> selectBackAmountGroupByCompany(Date startTime, Date endTime) {
        RequestReceiveExample example = new RequestReceiveExample();
        RequestReceiveExample.Criteria criteria = example.createCriteria();
        if(startTime!=null){
            criteria.andBackDateGreaterThanOrEqualTo(startTime);
        }
        if(endTime!=null){
            criteria.andBackDateLessThan(endTime);
        }
        return this.readMapper.selectBackAmountGroupByCompany(example);
    }

    @Override
    public List<Map<String, Object>> selectBackAmountGroupByOrg(Date startTime, Date endTime) {
        RequestReceiveExample example = new RequestReceiveExample();
        RequestReceiveExample.Criteria criteria = example.createCriteria();
        if(startTime!=null){
            criteria.andBackDateGreaterThanOrEqualTo(startTime);
        }
        if(endTime!=null){
            criteria.andBackDateLessThan(endTime);
        }
        return readMapper.selectBackAmoutGroupByOrg(example);
    }

}
