package com.erui.boss.web.report;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.model.PerformanceAssign;
import com.erui.report.service.PerformanceService;
import com.erui.report.util.InquiryAreaVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 销售业绩统计
 * Created by lirb on 2018/5/3
 */
@Controller
@RequestMapping("/report/salesPerformance")
public class PerformanceController {

    private static Logger logger = LoggerFactory.getLogger(PerformanceController.class);
//    private static final String url = "http://sso.eruidev.com/api/checkToken";
    @Autowired
    PerformanceService performanceService;

    /**
     * @Author: lirb
     * @Description: 获取 日期月份列表
     * @Date:10:55 2018/5/3
     * @modified By
     */
    @ResponseBody
    @RequestMapping("/dateList")
    public Object dateList() {
        List<String> dateList = performanceService.selectDateList();
        return new Result<>(dateList);
    }

    /**
     * @Author: lirb
     * @Description: 获取所有大区和大区中的所有国家列表
     * @Date:10:55 2018/5/3
     * @modified By
     */
    @RequestMapping(value = "/areaList", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public Object areaList(@RequestBody Map<String,String> params) {
        Result<Object> result = new Result<>();
        List<InquiryAreaVO> arayList = performanceService.selectAllAreaAndCountryList();
        if (MapUtils.isNotEmpty(params)&&StringUtils.isNotEmpty(params.get("areaName"))) {
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


    /**
     * @Author: lirb
     * @Description: 新增会员统计
     * @Date:10:55 2018/5/3
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "/incrBuyer", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object incrBuyerCount(@RequestBody Map<String, String> params) throws ParseException {
        if (!params.containsKey("date")) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        clearUpParams(params);
        List<Map<String, Object>> dataList = performanceService.selectIncrBuyerDetail(params);
        return new Result<>(dataList);

    }


    /**
     * 销售业绩统计
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/salesPerformance", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object salesPerformance(@RequestBody(required = true) Map<String, String> params) throws ParseException {
        if (!params.containsKey("date")) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        clearUpParams(params);
        //查询销售人员业绩明细
        List<Map<String, Object>> dataList = performanceService.obtainerPerformance(params);
        return new Result<>(dataList);


    }

    /**
     * 导出 新增会员统计数据
     *
     * @param date
     * @param area
     * @param country
     * @param response
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/exportIncrBuyer")
    public Object exportIncrBuyer(String date, String area, String country, HttpServletResponse response) throws ParseException {
        if (StringUtils.isEmpty(date)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Map<String, String> params = new HashMap<>();
        params.put("date", date);
        params.put("area", area);
        params.put("country", country);
        clearUpParams(params);
        List<Map<String, Object>> dataList = performanceService.selectIncrBuyerDetail(params);
        XSSFWorkbook wb = performanceService.exportIncrBuyer(dataList);
        //excel文件名
        String fileName = "新增会员统计" + System.currentTimeMillis() + ".xlsx";
        try {
            RequestCreditController.setResponseHeader(response, fileName);
            wb.write(response.getOutputStream());
            return null;
        } catch (Exception e) {
            logger.debug("异常:" + e.getMessage(), e);
            return null;
        }

    }

    /**
     * 导出 销售业绩统计数据
     *
     * @param date
     * @param area
     * @param country
     * @param response
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/exportSalesPerformance")
    public Object exportSalesPerformance(String date, String area, String country, HttpServletResponse response) throws ParseException {
        if (StringUtils.isEmpty(date)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Map<String, String> params = new HashMap<>();
        params.put("date", date);
        params.put("area", area);
        params.put("country", country);
        clearUpParams(params);
        List<Map<String, Object>> dataList = performanceService.obtainerPerformance(params);
        XSSFWorkbook wb = performanceService.exportSalesPerformance(dataList);
        //excel文件名
        String fileName = "销售业绩统计" + System.currentTimeMillis() + ".xlsx";
        try {
            RequestCreditController.setResponseHeader(response, fileName);
            wb.write(response.getOutputStream());
            return null;
        } catch (Exception e) {
            logger.debug("异常:" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取用户所负责的国家列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserCountry")
    public Object getUserCountry(HttpServletRequest request) {
        //获取用户信息
        Cookie[] cookies = request.getCookies();
        String token = getToken(cookies);
        if (StringUtils.isEmpty(token)) {
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR);
        }

        try {
            Integer userId = null;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);
            String userJson = getUserInfo(jsonObject.toString());
            //解析json
            if (StringUtils.isNotEmpty(userJson)) {
                Map<String, Object> userMap = JSON.parseObject(userJson, Map.class);
                int code = Integer.parseInt(userMap.get("code").toString());
                if (code == 200) {
                    userId = Integer.parseInt(userMap.get("id").toString());
                }
            }
            //查询用户负责的国家
            if (userId != null) {
                List<String> countryList = performanceService.selectCountryByUserId(userId);
                return new Result<>(countryList);
            }

            return new Result<>(ResultStatusEnum.GET_USERINFO_ERROR);
        } catch (Exception e) {
            logger.debug("异常:" + e, e);
            return new Result<>(ResultStatusEnum.GET_USERINFO_ERROR);
        }

    }

    /**
     * 指定国家的销售人员业绩明细
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/countrySalesPerformance", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object countrySalesPerformance(@RequestBody(required = true) Map<String, String> params) throws ParseException {
        if (!params.containsKey("date") || !params.containsKey("country")) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        clearUpParams(params);
        Map<String, Object> data = new HashMap<>();
        List<PerformanceAssign> dataList = performanceService.countryAssignDetailByTime(params);
        double countryPerformance = 0d;
        String rejectReason = null;
        Integer assignStatus = null;
        int marketerCount = 0;
        if (CollectionUtils.isNotEmpty(dataList)) {
            if (dataList.get(0).getCountryPerformance() != null) {
                countryPerformance = Double.parseDouble(dataList.get(0).getCountryPerformance().toString());
            }
            rejectReason = dataList.get(0).getRejectReason();
            assignStatus = dataList.get(0).getAssignStatus();
            marketerCount = dataList.size();
        }
        //获取几月份
        int month = DateUtil.getMonth(DateUtil.parseChDateStrToEnDateStr(params.get("date")));
        data.put("assignStatus", assignStatus);
        data.put("totalPerformance", countryPerformance);
        data.put("rejectReason", rejectReason);
        data.put("marketerCount", marketerCount);
        data.put("month", month);
        data.put("marketers", dataList);
        return new Result<>(data);
    }

    /**
     * 分配业绩
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/assignPerformance", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object assignPerformance(@RequestBody(required = true) Map<String,List<PerformanceAssign>> params) {

        if(!params.containsKey("marketers")){
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }

        if (CollectionUtils.isNotEmpty(params.get("marketers"))) {
            for (PerformanceAssign p : params.get("marketers")) {
                p.setAssignStatus(1);
            }
            performanceService.updatePerformanceAssign(params.get("marketers"));
            return new Result<>(ResultStatusEnum.SUCCESS);
        }

        return new Result<>(ResultStatusEnum.DATA_NULL);
    }

    /**
     * 查看指定国家 审核中的 业绩分配
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findAuditingPerformance", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object findAuditingPerformance(@RequestBody(required = true) Map<String, String> params)throws Exception {
        if (!params.containsKey("date")||!params.containsKey("country")) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        clearUpParams(params);
        List<PerformanceAssign> dataList = performanceService.selectAuditingPerformanceByCountry(params);
        return new Result<>(dataList);
    }

    /**
     * 审核指定国家的业绩 分配  ： 通过 、驳回
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/auditPerformance", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object auditPerformance(@RequestBody(required = true) Map<String, String> params)throws Exception {
        if (!params.containsKey("date") ||!params.containsKey("country")||!params.containsKey("state")) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        clearUpParams(params);
        performanceService.auditPerformance(params);
        return new Result<>(ResultStatusEnum.SUCCESS);
    }

    /**
     * 获取token
     *
     * @param cookies
     * @return
     */
    private String getToken(Cookie[] cookies) {
        String token = null;
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("eruitoken")) {
                    token = cookies[i].getValue();
                }
            }
        }
        return token;
    }

    /**
     * 根据token获取用户信息 sso
     *
     * @param params
     * @return
     */
    private String getUserInfo(String params) throws Exception {
        //httpClient 实例,声明httpPost请求
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //获取路径
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.perperties");
        String  url = (String) applicationContext.getBean("sso.url");
        HttpPost httpPost = new HttpPost(url);
        StringEntity entitys = new StringEntity(params.toString(), "utf-8");
        httpPost.setEntity(entitys);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String result = EntityUtils.toString(entity, "UTF-8");
            return result;
        }
        return null;
    }

    /**
     * 整理参数
     *
     * @param params
     */
    private void clearUpParams(Map<String, String> params) throws ParseException {
        //获取 开始时间 和结束时间
        Date start = DateUtil.parseChDateStrToEnDateStr(params.get("date"));
        String startTime = DateUtil.getStartTime(start, DateUtil.FULL_FORMAT_STR);
        String endTime = DateUtil.getEndTime(DateUtil.getMonthLastDay(start), DateUtil.FULL_FORMAT_STR);
        //获取 几月
        int mouth = DateUtil.getMonth(start);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("month", String.valueOf(mouth));
    }


}