package com.erui.report.quartz;

import com.erui.report.dao.QuartzMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 询报价需要的任务
 * Created by wangxiaodan on 2018/11/8.
 */
@Component
public class InquiryQuoteQuartz {
    /**
     * 定时任务的辅助数据库操作mapper
     */
    @Autowired
    private QuartzMapper quartzMapper;

    /**
     * 定时任务执行 每天1点执行
     * @throws Exception
     */
    public void execute() throws Exception {

//        ReportBaseQuartz reportBaseQuartz = new ReportBaseQuartz();
//        reportBaseQuartz.start();

    }


}
