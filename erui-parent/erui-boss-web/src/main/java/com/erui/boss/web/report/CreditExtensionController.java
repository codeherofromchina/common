package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.CreditExtensionService;
import com.erui.report.util.InquiryAreaVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 授信数据 Created by lirb on 2018/03/01.
 */
@Controller
@RequestMapping("/report/credit")
public class CreditExtensionController {

    @Autowired
    private CreditExtensionService creditService;
    /**
     * 数据总览
     */
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
    /**
     * 授信趋势图
     */
    @ResponseBody
    @RequestMapping(value = "/creditTrend",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Object creditTrend(@RequestBody(required = true)Map<String,String> params){
        // 获取参数并转换成时间格式
        Result<Object> result = new Result<>();
        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date end = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (params.get("creditType")==null||startTime == null || end == null || startTime.after(end)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        Map<String,Object> datas=  creditService.creditTrend(startTime,endTime ,params.get("creditType"));
        return  result.setData(datas);
    }

    /**
     * 授信区域明细
     */
    @ResponseBody
    @RequestMapping(value = "/areaDetail",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Object areaDetail(@RequestBody(required = true)Map<String,String> params){
        // 获取参数并转换成时间格式
        Result<Object> result = new Result<>();
        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        Date end = DateUtil.parseString2DateNoException(params.get("endTime"), DateUtil.SHORT_SLASH_FORMAT_STR);
        if (startTime == null || end == null || startTime.after(end)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);

        String fullStartTime=DateUtil.formatDateToString(startTime, DateUtil.FULL_FORMAT_STR2);
        String fullEndTime=DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2);
        params.put("startTime",fullStartTime);
        params.put("endTime",fullEndTime);
        Map<String,Object> data= creditService.areaDetail(params);
        return  result.setData(data);
    }

    /**
     * 获取区域明细中的所有大区和大区中的所有国家列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/areaList")
    public Object areaList(@RequestBody Map<String,String> params) {
        Result<Object> result = new Result<>();

        List<InquiryAreaVO> arayList = creditService.selectAllAreaAndCountryList();
        if (StringUtils.isNotBlank(params.get("areaName"))){
            List<InquiryAreaVO> ll = arayList.parallelStream().filter(vo -> vo.getAreaName().equals(params.get("areaName")))
                    .collect(Collectors.toList());
            if (ll.size() > 0) {
                result.setData(ll.get(0).getCountries());
            } else {
                return result.setStatus(ResultStatusEnum.AREA_NOT_EXIST);
            }
        } else {
            List<String> areaList = arayList.parallelStream().map(InquiryAreaVO::getAreaName)
                    .collect(Collectors.toList());
            result.setData(areaList);
        }
        return result;
    }
}
