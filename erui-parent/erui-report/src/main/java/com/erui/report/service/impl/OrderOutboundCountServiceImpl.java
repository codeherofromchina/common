package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.erui.comm.NewDateUtil;
import com.erui.report.dao.StorageOrganiCountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.OrderOutboundCountMapper;
import com.erui.report.model.OrderOutboundCount;
import com.erui.report.service.OrderOutboundCountService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class OrderOutboundCountServiceImpl extends BaseService<OrderOutboundCountMapper>
        implements OrderOutboundCountService {
    private final static Logger logger = LoggerFactory.getLogger(StorageOrganiCountServiceImpl.class);

    @Override
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

        ImportDataResponse response = new ImportDataResponse(
                new  String[]{"totalCount","entryCount","outCount"}
        );

        response.setOtherMsg(NewDateUtil.getBeforeSaturdayWeekStr(null));
        Map<String, BigDecimal> sumMap = response.getSumMap();
        int size = datas.size();
        OrderOutboundCount ooc = null;
        if (!testOnly) {
            writeMapper.truncateTable();
        }
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2;
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.ORDER_OUTBOUND_COUNT, response, cellIndex)) {
                continue;
            }
            ooc = new OrderOutboundCount();

            try {
                ooc.setCreateAt(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
                        DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_OUTBOUND_COUNT.getTable(), cellIndex, "时间字段格式错误");
                continue;
            }
            ooc.setOutboundNum(strArr[1]);
            ooc.setContractNum(strArr[2]);
            ooc.setDestination(strArr[3]);
            ooc.setExecuteNum(strArr[4]);

            try {
                ooc.setPackCount(new BigDecimal(strArr[5]).intValue());
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_OUTBOUND_COUNT.getTable(), cellIndex, "包装件数字段不是数字");
                continue;
            }

            try {
                ooc.setOutboundDate(DateUtil.parseString2Date(strArr[6], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
                        DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_OUTBOUND_COUNT.getTable(), cellIndex, "出库时间字段格式错误");
                continue;
            }
            try {
                ooc.setAmounts(new BigDecimal(strArr[7]));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_OUTBOUND_COUNT.getTable(), cellIndex, "金额字段不是数字");
                continue;
            }
            ooc.setRemark(strArr[8]);

            try {
                if (!testOnly) {
                    writeMapper.insertSelective(ooc);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_OUTBOUND_COUNT.getTable(), cellIndex, e.getMessage());
                continue;
            }
            response.incrSuccess();

        }
        StorageOrganiCountMapper orgMapper = readerSession.getMapper(StorageOrganiCountMapper.class);
        Map<String, Object> data = OrderEntryCountServiceImpl.countStack(orgMapper);
        int totalCount = Integer.parseInt(data.get("totalCount").toString());
        int entryCount = Integer.parseInt(data.get("entryCount").toString());
        int outCount = Integer.parseInt(data.get("outCount").toString());
        sumMap.put("totalCount",new BigDecimal(totalCount));
        sumMap.put("entryCount",new BigDecimal(entryCount));
        sumMap.put("outCount",new BigDecimal(outCount));

        response.setDone(true);

        return response;
    }
}