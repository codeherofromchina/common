package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 运营数据
 * @Author: lirb
 * @CreateDate: 2018/3/6 15:52
 */

@Controller
@RequestMapping("/report/member")
public class OperateController {

    @Autowired
    private MemberService memberService;
    /**
     * 运营数据总览
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/operatePandect",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Result operatePandect(@RequestBody(required = true) Map<String,String> params){
        // 获取参数并转换成时间格式
        Result<Object> result = new Result<>();
        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date end = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startTime == null || end == null || startTime.after(end)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        String fullStartTime=DateUtil.formatDateToString(startTime,"yyyy/MM/dd HH:mm:ss");
        String fullEndTime=DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2);
        params.put("startTime",fullStartTime);
        params.put("endTime",fullEndTime);
        Map<String,Object> data= memberService.selectOperateSummaryData(params);
        return result.setData(data);
    }
}
