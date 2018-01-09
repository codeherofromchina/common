package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.model.ProcurementCountExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.ProcurementCountMapper;
import com.erui.report.model.ProcurementCount;
import com.erui.report.service.ProcurementCountService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class ProcurementCountServiceImpl extends BaseService<ProcurementCountMapper>
        implements ProcurementCountService {
    private final static Logger logger = LoggerFactory.getLogger(ProcurementCountServiceImpl.class);

    /**
     * 具体采购数据的导入实现
     */
    @Override
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

        ImportDataResponse response = new ImportDataResponse();
        int size = datas.size();
        ProcurementCount pc = null;
        if (!testOnly) {
            writeMapper.truncateTable();
        }
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2;
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.PROCUREMENT_COUNT, response, cellIndex)) {
                continue;
            }
            pc = new ProcurementCount();
            try {
                pc.setAssignTime(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d", DateUtil.FULL_FORMAT_STR,
                        DateUtil.SHORT_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), cellIndex, "采购下达时间格式错误");
                continue;
            }
            pc.setPlanNum(strArr[1]);
            pc.setExecuteNum(strArr[2]);
            try {
                pc.setAmmount(new BigDecimal(strArr[3]));
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), cellIndex, "金额字段非数字");
                continue;
            }
            pc.setCategory(strArr[4]);
            pc.setArea(strArr[5]);
            pc.setCountry(strArr[6]);
            pc.setOil(strArr[7]);
            try {
                if (!testOnly) {
                    writeMapper.insertSelective(pc);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), cellIndex, e.getMessage());
                continue;
            }
            response.incrSuccess();
        }
        response.setDone(true);

        return response;
    }
    /**
     * 获取采购总览数据实现
     */
    @Override
    public List<Map<String, Object>> selectProcurPandent(Date startTime, Date endTime) {
        ProcurementCountExample example = new ProcurementCountExample();
        ProcurementCountExample.Criteria criteria = example.createCriteria();
        if(startTime!=null){
            criteria.andAssignTimeGreaterThanOrEqualTo(startTime);
        }
        if(endTime!=null){
            criteria.andAssignTimeLessThan(endTime);
        }
        return   this.readMapper.selectProcurPandent(example);
    }
    /**
     * 获取采购趋势图实现
     */
    @Override
    public Map<String, Object> procurementTrend(Date startTime, Date endTime, String queryType) {
        //虚拟一个标准的时间集合
        List<String> dates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int days = DateUtil.getDayBetween(startTime, endTime);
        for (int i = 0; i < days; i++) {
            Date datetime = DateUtil.sometimeCalendar(startTime, -i);
            dates.add(dateFormat.format(datetime));
        }
        ProcurementCountExample example = new ProcurementCountExample();
        ProcurementCountExample.Criteria criteria = example.createCriteria();
        if(startTime!=null){
            criteria.andAssignTimeGreaterThanOrEqualTo(startTime);
        }
        if(endTime!=null){
            criteria.andAssignTimeLessThan(endTime);
        }
     //List<Map<String,Object>> list=   readMapper.selectProcurTrend(example);
        return null;
    }
}