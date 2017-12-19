package com.erui.report.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.RequestReceiveMapper;
import com.erui.report.model.RequestReceive;
import com.erui.report.service.RequestReceiveService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class RequestReceiveServiceImpl extends  BaseService<RequestReceiveMapper> implements RequestReceiveService{
    private  final static Logger logger= LoggerFactory.getLogger(RequestReceiveServiceImpl.class);
    @Override
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {
        // 回款金额 - backAmount、 应收未收金额 - receiveAmount
        ImportDataResponse response = new ImportDataResponse(new String[]{"receiveAmount", "backAmount"});
        response.setOtherMsg(NewDateUtil.getBeforeSaturdayWeekStr(null));
        int size = datas.size();
        RequestReceive receive = null;
        if (!testOnly) {
            writeMapper.truncateTable();
        }
        // 定义下月应收金额
        BigDecimal nextMouthReceiveAmount = BigDecimal.ZERO;
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
            if (NewDateUtil.inSaturdayWeek(receive.getCreateAt())) {
                response.sumData(receive);
            }
            // 下自然月应收金额
            if (NewDateUtil.inNextMonth(receive.getNextBackDate()) && receive.getReceiveAmount() != null) {
                nextMouthReceiveAmount = nextMouthReceiveAmount.add(receive.getReceiveAmount());
            }
            response.incrSuccess();

        }
        // 计算应收已收金额，放置到键 hasReceivedAmount 中
        Map<String, BigDecimal> map = response.getSumMap();
        map.put("hasReceivedAmount", map.get("back_amount"));
        map.put("nextMouthReceiveAmount", nextMouthReceiveAmount);
        response.setDone(true);

        return response;
    }
}
