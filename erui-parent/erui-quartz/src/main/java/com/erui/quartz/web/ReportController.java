package com.erui.quartz.web;

import com.erui.quartz.executor.ReportQuartz;
import com.erui.report.service.InquiryCountService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
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

    /**
     * 验证
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public Object hello() {
       Map<String,Object> map =new HashMap<>();
        map.put("validate","seccess");
        return map;
    }

}
