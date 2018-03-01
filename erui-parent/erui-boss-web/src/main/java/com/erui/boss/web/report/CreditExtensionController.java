package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.CreditExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

/**
 * 授信数据 Created by lirb on 2018/03/01.
 */
@Controller
@RequestMapping("/report/credit")
public class CreditExtensionController {

    @Autowired
    private CreditExtensionService creditService;

    @ResponseBody
    @RequestMapping(value = "/creditPandect",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Object creditPandect(@RequestBody(required =true)Map<String,String> params){
        // 获取参数并转换成时间格式
        Result<Object> result = new Result<>();
        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date end = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startTime == null || end == null || startTime.after(end)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        // 获取需要环比的开始时间
        int days = DateUtil.getDayBetween(startTime, endTime);
        Date chainStartTime = DateUtil.sometimeCalendar(startTime, days);
        //封装参数
        String chain = DateUtil.formatDateToString(chainStartTime, DateUtil.FULL_FORMAT_STR2);
        String fullStartTime=DateUtil.formatDateToString(startTime, DateUtil.FULL_FORMAT_STR2);
        String fullEndTime=DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2);
        params.put("chainStartTime",chain);
        params.put("startTime",fullStartTime);
        params.put("endTime",fullEndTime);
        //获取总览数据
       Map<String,Object> data= creditService.creditPandect(params);
        return  result.setData(data);
    }
}
