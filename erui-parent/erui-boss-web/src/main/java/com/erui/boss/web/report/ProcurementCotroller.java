package com.erui.boss.web.report;


import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.NewDateUtil;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.model.CateDetailVo;
import com.erui.report.model.InquiryCount;
import com.erui.report.service.InquiryCountService;
import com.erui.report.service.InquirySKUService;
import com.erui.report.service.OrderCountService;
import com.erui.report.service.ProcurementCountService;
import com.erui.report.service.impl.DataServiceImpl;
import com.erui.report.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 采购 Created by lirb on 2018/1/9.
 */
@Controller
@RequestMapping("/report/procurement")
public class ProcurementCotroller {


    @Autowired
    private ProcurementCountService procurementService;
    /**
     *采购总览
     * @param map
     * @return
     */
    @RequestMapping(value = "/procurementPadent", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Object inqDetailPie(@RequestBody Map<String, Object> map) throws Exception {

        Result<Object> result = new Result<>();

        if (!map.containsKey("startTime") || !map.containsKey("endTime")) {
            result.setStatus(ResultStatusEnum.PARAM_TYPE_ERROR);
            return result;
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);

        //查询采购数据
        List<Map<String,Object>> dataList=procurementService.selectProcurPandent(startTime,endTime);
        if(dataList!=null&&dataList.size()>0) {
            return result.setData(dataList.get(0));
        }
        return result.setStatus(ResultStatusEnum.DATA_NULL);

    }

    /**
     *采购趋势图
     * @param map
     * @return
     */
    @RequestMapping(value = "/procurementTrend", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Object procurementTrend(@RequestBody Map<String, Object> map) throws Exception {

        Result<Object> result = new Result<>();

        if (!map.containsKey("startTime") || !map.containsKey("endTime")|| !map.containsKey("queryType")) {
            result.setStatus(ResultStatusEnum.PARAM_TYPE_ERROR);
            return result;
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);

        Map<String,Object> trendData=this.procurementService.procurementTrend(startTime,endTime,map.get("queryType").toString());
        Map<String,Object> data=new HashMap<>();
        data.put("procurementCount",230);
        data.put("signingContractCount",158);
        data.put("signingContractAmount",561516.56);
        return result.setData(data);

    }
}
