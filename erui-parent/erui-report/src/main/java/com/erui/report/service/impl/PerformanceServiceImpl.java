package com.erui.report.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.PerformanceCountMapper;
import com.erui.report.model.PerformanceCount;
import com.erui.report.service.PerformanceService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PerformanceServiceImpl extends BaseService<PerformanceCountMapper> implements PerformanceService  {

    private final static Logger logger = LoggerFactory.getLogger(PerformanceServiceImpl.class);

    @Override
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

        ImportDataResponse response = new ImportDataResponse();
        Map<String, BigDecimal> sumMap=new HashMap<>();//和数据
        response.setOtherMsg(NewDateUtil.getBeforeSaturdayWeekStr(null));
        PerformanceCount pc = null;
        if (!testOnly) {
            writeMapper.truncateTable();
        }

        for (int index = 0; index < datas.size(); index++) {
            int cellIndex = index + 2; // 数据从第二行开始
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.SALES_PERFORMANCE, response, cellIndex)) {
                continue;
            }
            pc = new PerformanceCount();

            if (strArr[0] != null) {
                try {
                 pc.setSerialNumber(new BigDecimal(strArr[0]).intValue());
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), cellIndex, "序号非数字");
                    continue;
                }
            }

            pc.setExecuteNum(strArr[1]);
            pc.setOverseasSalesNum(strArr[2]);
            pc.setSignCompany(strArr[3]);
            pc.setNotEruiReason(strArr[4]);
            pc.setIsThroughAgent(strArr[5]);
            pc.setAgentCode(strArr[6]);
            pc.setOrganization(strArr[7]);
            pc.setExecuteCompany(strArr[8]);
            pc.setArea(strArr[9]);
            pc.setBuyerCode(strArr[10]);
            pc.setGoodsCh(strArr[11]);
            pc.setCategory(strArr[12]);

            if (strArr[13] != null) {
                try {
                    pc.setGoodsCount(new BigDecimal(strArr[13]).intValue());
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), cellIndex, "商品数量非数字");
                    continue;
                }
            }

            pc.setUnit(strArr[14]);

            if (strArr[15] != null) {
                try {
                    pc.setProjectAmount(new BigDecimal(strArr[15]));
                    if(strArr[8] !=null){ // 统计各个国家的总业绩
                        if(sumMap.containsKey(strArr[8])){
                            sumMap.put(strArr[8],sumMap.get(strArr[8]).add(new BigDecimal(strArr[15])));
                        }else {
                            sumMap.put(strArr[8],new BigDecimal(strArr[15]));
                        }
                    }
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), cellIndex, "项目金额(美元)非数字");
                    continue;
                }
            }

            if (strArr[16] != null) {
                try {
                    pc.setInitialProfitMargin(new BigDecimal(strArr[16]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), cellIndex, "初步利润率非数字");
                    continue;
                }
            }

            pc.setReceivedMode(strArr[17]);

            if (strArr[18] != null) {
                try {
                    pc.setStartTime(DateUtil.parseString2Date(strArr[18], DateUtil.SHORT_FORMAT_STR, "yyyy/M/d", "yyyy/M/d hh:mm:ss", DateUtil.FULL_FORMAT_STR));
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), cellIndex, "开始时间格式错误");
                    continue;
                }
            }

            pc.setObtainMainCompany(strArr[19]);
            pc.setObtainer(strArr[20]);
            pc.setMarketer(strArr[21]);
            pc.setBusinesser(strArr[22]);
            pc.setRemark(strArr[23]);

            try {
                if (!testOnly) {
                    writeMapper.insertSelective(pc);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.SALES_PERFORMANCE.getTable(), cellIndex, e.getMessage());
                continue;
            }


            response.incrSuccess();
        }
        response.setSumMap(sumMap);
        response.setDone(true);
        return response;
    }
}
