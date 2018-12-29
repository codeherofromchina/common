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
    private SupplyChainReadService supplyChainReadService;
    @Autowired
    private ProcurementCountService procurementCountService;
    @Autowired
    private MarketerCountService marketerService;
    @Autowired
    private CategoryQualityService qualityService;
    @Autowired
    private CreditExtensionService creditService;
    @Autowired
    private StorageOrganiCountService storageService;

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
        Date supplyStartTime = supplyChainReadService.selectStart();
        Date supplyEndTime = supplyChainReadService.selectEnd();
        Date procurStartTime = procurementCountService.selectStart();
        Date procurEndTime = procurementCountService.selectEnd();
        Date marketerStartTime = marketerService.selectStart();
        Date marketerEndTime = marketerService.selectEnd();
        Date qualiltyStartTime = qualityService.selectStart();
        Date qualityEndTime = qualityService.selectEnd();
        Date creditStartTime = creditService.selectStart();
        Date creditEndTime = creditService.selectEnd();
        Date storageStartTime = storageService.selectStart();
        Date storageEndTime = storageService.selectEnd();
        Date totalStartTime = DateUtil.minTime(hrStartTime, inquiryStartTime, orderStartTime, requestStartTime,
                supplyStartTime,marketerStartTime,qualiltyStartTime,creditStartTime,storageStartTime);
        Date totalEndTime = DateUtil.maxTime(hrEndTime, inquiryEndTime, orderEndTime, requestEndTime,
                supplyEndTime,marketerEndTime,qualityEndTime,creditEndTime,storageEndTime);
        Date customStartTime = DateUtil.minTime(inquiryStartTime,orderStartTime);
        Date customEndTime = DateUtil.maxTime(inquiryEndTime,inquiryEndTime);
        Map<String, Object> data = new HashMap<>();
        if(hrStartTime!=null) {
            data.put("hrStartTime", DateUtil.formatDate2String(hrStartTime, "yyyy/MM/dd"));
        }
        if(hrEndTime!=null) {
            data.put("hrEndTime", DateUtil.formatDate2String(hrEndTime, "yyyy/MM/dd"));
        }
        if(customStartTime!=null) {
            data.put("customStartTime", DateUtil.formatDate2String(customStartTime, "yyyy/MM/dd"));
        }
        if(customEndTime!=null) {
            data.put("customEndTime", DateUtil.formatDate2String(customEndTime, "yyyy/MM/dd"));
        }
        if(requestStartTime!=null) {
            data.put("requestStartTime", DateUtil.formatDate2String(requestStartTime, "yyyy/MM/dd"));
        }
        if(requestEndTime!=null) {
            data.put("requestEndTime", DateUtil.formatDate2String(requestEndTime, "yyyy/MM/dd"));
        }
        if(supplyStartTime!=null) {
            data.put("supplyStartTime", DateUtil.formatDate2String(supplyStartTime, "yyyy/MM/dd"));
        }
        if(supplyEndTime!=null) {
            data.put("supplyEndTime", DateUtil.formatDate2String(supplyEndTime, "yyyy/MM/dd"));
        }
        if(procurStartTime!=null) {
            data.put("procurStartTime", DateUtil.formatDate2String(procurStartTime, "yyyy/MM/dd"));
        }
        if(procurEndTime!=null) {
            data.put("procurEndTime", DateUtil.formatDate2String(procurEndTime, "yyyy/MM/dd"));
        }
        if(marketerStartTime!=null) {
            data.put("marketerStartTime", DateUtil.formatDate2String(marketerStartTime, "yyyy/MM/dd"));
        }
        if(marketerEndTime!=null) {
            data.put("marketerEndTime", DateUtil.formatDate2String(marketerEndTime, "yyyy/MM/dd"));
        }
        if(creditStartTime!=null) {
            data.put("creditStartTime", DateUtil.formatDate2String(creditStartTime, "yyyy/MM/dd"));
        }
        if(creditEndTime!=null) {
            data.put("creditEndTime", DateUtil.formatDate2String(creditEndTime, "yyyy/MM/dd"));
        }
        if(qualiltyStartTime!=null) {
            data.put("qualiltyStartTime", DateUtil.formatDate2String(qualiltyStartTime, "yyyy/MM/dd"));
        }
        if(qualityEndTime!=null) {
            data.put("qualityEndTime", DateUtil.formatDate2String(qualityEndTime, "yyyy/MM/dd"));
        }
        if(storageStartTime!=null) {
            data.put("storageStartTime", DateUtil.formatDate2String(storageStartTime, "yyyy/MM/dd"));
        }
        if(storageEndTime!=null) {
            data.put("storageEndTime", DateUtil.formatDate2String(storageEndTime, "yyyy/MM/dd"));
        }
        if(totalStartTime!=null) {
            data.put("totalStartTime", DateUtil.formatDate2String(totalStartTime, "yyyy/MM/dd"));
        }
        if(totalEndTime!=null) {
            data.put("totalEndTime", DateUtil.formatDate2String(totalEndTime, "yyyy/MM/dd"));
        }
        Result<Map<String, Object>> result = new Result<>(data);
        return result;
    }
}
