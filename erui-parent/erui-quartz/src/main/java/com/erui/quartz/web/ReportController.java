package com.erui.quartz.web;

import com.erui.quartz.executor.ReportQuartz;
import com.erui.report.service.InquiryCountService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/quartz/report")
public class ReportController {

    @Autowired
    private ReportQuartz reportQuartz;

    @RequestMapping(value = "/data")
    public Object data()throws  Exception{
        reportQuartz.totalData();
        return null;
    }


}
