package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.StorageOrganiCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

/**
 * @Description:仓储物流
 * @Author: lirb
 * @CreateDate: 2018/3/5 11:17
 */
@Controller
@RequestMapping(value = "/report/storage")
public class StorageController {

    @Autowired
    private StorageOrganiCountService storageService;
    /**
     * @Description:库存总览
     * @Author: lirb
     * @CreateDate: 2018/3/7 11:17
     */
    @ResponseBody
    @RequestMapping(value = "/storagePandect",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Result storagePandect(@RequestBody(required = true) Map<String,String> params){
        Result<Object> result = new Result<>();
        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date end = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startTime == null || end == null || startTime.after(end)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);

        String fullStartTime=DateUtil.formatDateToString(startTime, DateUtil.FULL_FORMAT_STR2);
        String fullEndTime=DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2);
        params.put("startTime",fullStartTime);
        params.put("endTime",fullEndTime);
       Map<String,Object> data= storageService.selectStorageSummaryData(params);
        return result.setData(data);
    }
    /**
     * @Description:仓储物流趋势图
     * @Author: lirb
     * @CreateDate: 2018/3/7 11:17
     */
    @ResponseBody
    @RequestMapping(value = "/storageTrend",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Result storageTrend(@RequestBody(required = true) Map<String,String> params){
        Result<Object> result = new Result<>();
        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date end = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startTime == null || end == null || startTime.after(end)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
       Map<String,Object> data= storageService.selectStorageTrend(startTime,endTime);
        return result.setData(data);
    }
    /**
     * @Description:出库目的国分析
     * @Author: lirb
     * @CreateDate: 2018/3/7 11:17
     */
    @ResponseBody
    @RequestMapping(value = "/outStoreDestinationDetail",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Result outStoreDestinationDetail(@RequestBody(required = true) Map<String,String> params){
        Result<Object> result = new Result<>();
        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date end = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startTime == null || end == null || startTime.after(end)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);

        String fullStartTime=DateUtil.formatDateToString(startTime, DateUtil.FULL_FORMAT_STR2);
        String fullEndTime=DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2);
        params.put("startTime",fullStartTime);
        params.put("endTime",fullEndTime);
        Map<String,Object> data= storageService.selectCountryOutStoreSummary(params);
        return result.setData(data);
    }
    /**
     * @Description:事业部库存
     * @Author: lirb
     * @CreateDate: 2018/3/7 11:17
     */
    @ResponseBody
    @RequestMapping(value = "/orgStocks",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Result orgStocks(@RequestBody(required = true) Map<String,String> params){
        Result<Object> result = new Result<>();
        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date end = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startTime == null || end == null || startTime.after(end)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);

        String fullStartTime=DateUtil.formatDateToString(startTime, DateUtil.FULL_FORMAT_STR2);
        String fullEndTime=DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2);
        params.put("startTime",fullStartTime);
        params.put("endTime",fullEndTime);
       Map<String,Object> data= storageService.orgStocks(params);
        return result.setData(data);
    }


}
