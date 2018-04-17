package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
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

            ooc.setOutboundNum(strArr[0]);
            ooc.setContractNum(strArr[1]);
            ooc.setDestination(strArr[2]);
            ooc.setExecuteNum(strArr[3]);

            try {
                ooc.setPackCount(new BigDecimal(strArr[4]).intValue());
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_OUTBOUND_COUNT.getTable(), cellIndex, "包装件数字段不是数字");
                continue;
            }

            try {
                ooc.setOutboundDate(DateUtil.parseString2Date(strArr[5], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
                        DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_OUTBOUND_COUNT.getTable(), cellIndex, "出库时间字段格式错误");
                continue;
            }
            try {
                ooc.setAmounts(new BigDecimal(strArr[6]));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_OUTBOUND_COUNT.getTable(), cellIndex, "金额字段不是数字");
                continue;
            }
            ooc.setRemark(strArr[7]);
            ooc.setDocType(strArr[8]);
            try {
                if (!testOnly) {
                    writeMapper.insertSelective(ooc);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_OUTBOUND_COUNT.getTable(), cellIndex, e.getMessage());
                continue;
            }

            if(NewDateUtil.inSaturdayWeek(ooc.getOutboundDate())){
                sumMap.put("outCount",sumMap.get("outCount").add(new BigDecimal(ooc.getPackCount())));
            }
            if(NewDateUtil.lessFridayWeek(ooc.getOutboundDate())){
                sumMap.put("totalCount",sumMap.get("totalCount").add(new BigDecimal(-ooc.getPackCount())));
            }

            response.incrSuccess();

        }
        //查询库存
        try {
            StorageOrganiCountMapper orgMapper = readerSession.getMapper(StorageOrganiCountMapper.class);
            Date start = DateUtil.parseString2Date(OrderEntryCountServiceImpl.STARTTIME, DateUtil.FULL_FORMAT_STR2);
            Map<String, Object> totalMap = OrderEntryCountServiceImpl.countStack(start,orgMapper);
            int tentryCount = Integer.parseInt(totalMap.get("entryCount").toString());
            sumMap.put("totalCount",sumMap.get("totalCount").add(new BigDecimal(tentryCount)));
            //入库
            Date[] week = NewDateUtil.getBeforeSaturdayWeek(new Date());
            Map<String, Object> entryMap = OrderEntryCountServiceImpl.countStack(week[0],orgMapper);
            sumMap.put("entryCount",new BigDecimal(entryMap.get("entryCount").toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        response.setDone(true);
        return response;
    }
}