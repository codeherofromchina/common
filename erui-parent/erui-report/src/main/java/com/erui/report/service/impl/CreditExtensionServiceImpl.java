package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.erui.comm.RateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.report.model.CreditExtensionExample;
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
        Integer creditCount =Integer.parseInt(datas.get("creditCount").toString());
        Double creditAmount = Double.parseDouble(datas.get("creditAmount").toString());
        Double usedAmount = Double.parseDouble(datas.get("usedAmount").toString());
        Double availAmount = Double.parseDouble(datas.get("availAmount").toString());
        int incrCredit = Integer.parseInt(datas.get("incrCredit").toString());
        int elimiCredit = Integer.parseInt(datas.get("elimiCredit").toString());
        if (usedAmount + availAmount > 0) {
            usedRate = RateUtil.doubleChainRate(usedAmount , usedAmount + availAmount);
        }
        Double chainUsedAmount = Double.parseDouble(chainDatas.get("usedAmount").toString());
        if (chainUsedAmount > 0) {
            usedChainRate =RateUtil.doubleChainRate(usedAmount - chainUsedAmount , chainUsedAmount);
        }
        if (incrCredit > 0) {
            lossRate = RateUtil.intChainRate(elimiCredit, incrCredit);
        }
        int chainIncrCredit = Integer.parseInt(chainDatas.get("incrCredit").toString());
        if (chainIncrCredit > 0) {
            incrCreditRate = RateUtil.intChainRate(incrCredit - chainIncrCredit, chainIncrCredit);
        }

        //封装数据
        creditData.put("creditCount",creditCount);
        creditData.put("creditAmount",creditAmount);
        usedCredit.put("usedCreditAmount",usedAmount);
        usedCredit.put("availableCreditAmount",availAmount);
        usedCredit.put("usedRate",usedRate);
        usedCredit.put("chainRate",usedChainRate);
        addCredit.put("addCreditCount",incrCredit);
        addCredit.put("eliminateCreditCount",elimiCredit);
        addCredit.put("lossRate",lossRate);
        addCredit.put("chainRate",incrCreditRate);
        data.put("credit",creditData);
        data.put("usedCredit",usedCredit);
        data.put("addCredit",addCredit);
        return data;
    }
}