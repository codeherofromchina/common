package com.erui.quartz.web;

import com.erui.quartz.executor.ReportQuartz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
