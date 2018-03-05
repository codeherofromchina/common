package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.MarketerCountService;
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
 * @Description: 市场人员
 * @Author: lirb
 * @CreateDate: 2018/3/2 17:36
 */
@Controller
@RequestMapping("/report/marketer")
public class MarketerController {

    @Autowired
    private MarketerCountService marketerService;


    /**
     * @Description: 国家top总览
     * @Author: lirb
     * @CreateDate: 2018/3/4 17:36
     */
    @ResponseBody
    @RequestMapping(value = "/countryTopPandect",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Result marketPandect(@RequestBody(required = true) Map<String,String> params ){
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
        params.put("startTime",fullStartTime);
        params.put("endTime",fullEndTime);
        params.put("chainTime",chain);
         Map<String,Object> datas=  marketerService.countryTopPandect(params);
        return  result.setData(datas);
    }
    /**
     * @Description: 区域人员top总览
     * @Author: lirb
     * @CreateDate: 2018/3/4 17:36
     */
    @ResponseBody
    @RequestMapping(value = "/areaMarketerTopPandect",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Result staffTopPandect(@RequestBody(required = true) Map<String,String> params ){
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
        params.put("startTime",fullStartTime);
        params.put("endTime",fullEndTime);
        params.put("chainTime",chain);
        Map<String, Object> data = marketerService.areaMarketerTopPandect(params);
        return  result.setData(data);
    }

    /**
     * @Description: 区域明细
     * @Author: lirb
     * @CreateDate: 2018/3/4 17:36
     */
    @ResponseBody
    @RequestMapping(value = "/areaDetail",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Result areaDetail(@RequestBody(required = true) Map<String,String> params ){
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
        Map<String,Object> data= marketerService.areaDetail(params);
        return  result.setData(data);
    }

    /**
     * 获取区域明细中的所有大区和大区中的所有国家列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/areaList")
    public Object areaList(String areaName) {
        Result<Object> result = new Result<>();

        List<InquiryAreaVO> arayList = marketerService.selectAllAreaAndCountryList();
        if (StringUtils.isNotBlank(areaName)){
            List<InquiryAreaVO> ll = arayList.parallelStream().filter(vo -> vo.getAreaName().equals(areaName))
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
