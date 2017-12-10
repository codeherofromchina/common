package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:SHIGS
 * @Description
 * @Date Created in 16:29 2017/11/14
 * @modified By
 */
@Controller
@RequestMapping("/report/totalTime")
public class TotalTimeController {
    @Autowired
    private HrCountService hrCountService;
    @Autowired
    private InquiryCountService inquiryCountService;
    @Autowired
    private OrderCountService orderCountService;
    @Autowired
    private RequestCreditService requestCreditService;
    @Autowired
    private SupplyChainService supplyChainService;

    @RequestMapping(value = "totalTime", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public Object totalTime() {
        Date hrStartTime = hrCountService.selectStart();
        Date hrEndTime = hrCountService.selectEnd();
        Date inquiryStartTime = inquiryCountService.selectStart();
        Date inquiryEndTime = inquiryCountService.selectEnd();
        Date orderStartTime = orderCountService.selectStart();
        Date orderEndTime = orderCountService.selectEnd();
        Date requestStartTime = requestCreditService.selectStart();
        Date requestEndTime = requestCreditService.selectEnd();
        Date supplyStartTime = supplyChainService.selectStart();
        Date supplyEndTime = supplyChainService.selectEnd();
        Date totalStartTime = DateUtil.minTime(hrStartTime, inquiryStartTime, orderStartTime, requestStartTime, supplyStartTime);
        Date totalEndTime = DateUtil.maxTime(hrEndTime, inquiryEndTime, orderEndTime, requestEndTime, supplyEndTime);
        Date customStartTime = DateUtil.minTime(inquiryStartTime,orderStartTime);
        Date customEndTime = DateUtil.maxTime(inquiryEndTime,inquiryEndTime);
        Map<String, Object> data = new HashMap<>();
        data.put("hrStartTime", DateUtil.formatDate2String(hrStartTime,"yyyy/MM/dd"));
        data.put("hrEndTime", DateUtil.formatDate2String(hrEndTime,"yyyy/MM/dd"));
        data.put("customStartTime", DateUtil.formatDate2String(customStartTime,"yyyy/MM/dd"));
        data.put("customEndTime", DateUtil.formatDate2String(customEndTime,"yyyy/MM/dd"));
        data.put("requestStartTime", DateUtil.formatDate2String(requestStartTime,"yyyy/MM/dd"));
        data.put("requestEndTime", DateUtil.formatDate2String(requestEndTime,"yyyy/MM/dd"));
        data.put("supplyStartTime", DateUtil.formatDate2String(supplyStartTime,"yyyy/MM/dd"));
        data.put("supplyEndTime", DateUtil.formatDate2String(supplyEndTime,"yyyy/MM/dd"));
        data.put("totalStartTime", DateUtil.formatDate2String(totalStartTime,"yyyy/MM/dd"));
        data.put("totalEndTime", DateUtil.formatDate2String(totalEndTime,"yyyy/MM/dd"));
        Result<Map<String, Object>> result = new Result<>(data);
        return result;
    }
}
