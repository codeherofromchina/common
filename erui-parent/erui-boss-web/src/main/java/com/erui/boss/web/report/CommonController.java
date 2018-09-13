package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.report.model.PerformanceIndicators;
import com.erui.report.service.CommonService;
import com.erui.report.service.PerformanceIndicatorsService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/12.
 * 公共信息控制器
 */
@Controller
@RequestMapping("/report/CommonController")
public class CommonController {
    @Autowired
    private CommonService commonService;


}
