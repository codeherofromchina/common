package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.erui.comm.NewDateUtil;
import com.erui.report.dao.StorageOrganiCountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.OrderEntryCountMapper;
import com.erui.report.model.OrderEntryCount;
import com.erui.report.service.OrderEntryCountService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class OrderEntryCountServiceImpl extends BaseService<OrderEntryCountMapper> implements OrderEntryCountService {
    private final static Logger logger = LoggerFactory.getLogger(StorageOrganiCountServiceImpl.class);

    @Override
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

        ImportDataResponse response = new ImportDataResponse(
               new  String[]{"totalCount","entryCount","outCount"}
        );

        response.setOtherMsg(NewDateUtil.getBeforeSaturdayWeekStr(null));
        Map<String, BigDecimal> sumMap = response.getSumMap();
        int size = datas.size();
        OrderEntryCount oec = null;
        if (!testOnly) {
            writeMapper.truncateTable();
        }
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2;
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.ORDER_ENTRY_COUNT, response, cellIndex)) {
                continue;
            }
            oec = new OrderEntryCount();

            try {
                oec.setCreateAt(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
                        DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_ENTRY_COUNT.getTable(), cellIndex, "时间字段格式错误");
                continue;
            }
            oec.setEntryNum(strArr[1]);
            oec.setExecuteNum(strArr[2]);

            oec.setContractNum(strArr[3]);

            try {
                oec.setEntryCount(new BigDecimal(strArr[4]).intValue());
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_ENTRY_COUNT.getTable(), cellIndex, "数量字段不是数字");
                continue;
            }
            try {
                oec.setAmounts(new BigDecimal(strArr[5]));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_ENTRY_COUNT.getTable(), cellIndex, "金额字段不是数字");
                continue;
            }
            try {
                oec.setStorageDate(DateUtil.parseString2Date(strArr[6], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
                        DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_ENTRY_COUNT.getTable(), cellIndex, "入库时间格式错误");
                continue;
            }
            oec.setBuyer(strArr[7]);
            oec.setSuppliName(strArr[8]);
            oec.setRemark(strArr[9]);

            try {
                if (!testOnly) {
                    writeMapper.insertSelective(oec);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.ORDER_ENTRY_COUNT.getTable(), cellIndex, e.getMessage());
                continue;
            }

            if(NewDateUtil.inSaturdayWeek(oec.getStorageDate())){
                sumMap.put("entryCount",sumMap.get("entryCount").add(new BigDecimal(oec.getEntryCount())));
            }
            response.incrSuccess();

        }
        //查询出库量
        StorageOrganiCountMapper mapper = readerSession.getMapper(StorageOrganiCountMapper.class);
        Map<String, Object> entryAndOut = countStack(mapper);
        int outCount = Integer.parseInt(entryAndOut.get("outCount").toString());
        sumMap.put("outCount",new BigDecimal(outCount));
        sumMap.put("totalCount",sumMap.get("entryCount").add(new BigDecimal(-outCount)));
        return response;
    }
/*
* 统计导入数据返回库存数据
* */
    public static Map<String,Object> countStack(StorageOrganiCountMapper orgMapper){
        //统计数据
        Map<String,String> params=new HashMap<>();
        Date[] weekDates = NewDateUtil.getBeforeSaturdayWeek(new Date());
        String startTime = DateUtil.formatDateToString(weekDates[0], DateUtil.FULL_FORMAT_STR2);
        Date end = DateUtil.getOperationTime(weekDates[1], 23, 59, 59);
        String endTime=DateUtil.formatDateToString(end, DateUtil.FULL_FORMAT_STR2);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        Map<String, Object> entryAndOut = orgMapper.selectEntryAndOutData(params);
        return  entryAndOut;
    }
}