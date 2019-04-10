package com.erui.boss.web.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.report.service.WeeklyReportService;
import com.erui.report.util.ParamsUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 周报
 */
@Controller
@RequestMapping("/report/weeklyReport")
public class WeeklyReportController {

    @Autowired
    private WeeklyReportService weeklyReportService;

    /**
     * 地区明细
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/areaDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object areaDetail(@RequestBody(required = true) Map<String, Object> params) {
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        } else {
            //处理时间参数
            params = ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR, null);
            if (params == null) {
                return new Result<>(ResultStatusEnum.PARAM_ERROR);
            }
            //环比为前7天的数据
            Date start = DateUtil.parseString2DateNoException(String.valueOf(params.get("startTime")), DateUtil.FULL_FORMAT_STR);
            Date chainStartTime = DateUtil.sometimeCalendar(start, 7);
            params.put("chainStartTime", DateUtil.formatDateToString(chainStartTime, DateUtil.FULL_FORMAT_STR));
        }

        //查询各地区的时间段内新用户注册数，中国算一个地区
        Map<String, Object> registerData = weeklyReportService.selectBuyerRegistCountGroupByArea(params);
        //查询各地区的时间段内会员数 中国算一个地区
        // Map<String, Object> buyerData = weeklyReportService.selectBuyerCountGroupByArea(params);

        //查询各地区的时间段内普通会员Erui数、普通会员ERUI&KERUI数、高级会员Erui数、高级会员ERUI&KERUI数，中国算一个地区
        Map<String, Object> buyerData = weeklyReportService.selectBuyerCountDetail(params);
        // 查询各地区时间段内询单数
        Map<String, Object> inqNumInfoData = weeklyReportService.selectInqNumGroupByArea(params);
        // 查询各个地区时间段内的报价数量和金额信息
        Map<String, Object> quoteInfoData = weeklyReportService.selectQuoteInfoGroupByArea(params);
        // 查询各个地区时间段内的订单数量和金额信息
        Map<String, Object> orderInfoData = weeklyReportService.selectOrderInfoGroupByArea(params);


        Map<String, Map> data = new HashMap<>();
        data.put("registerInfo", registerData); // 新用户注册数据信息
        data.put("memberNumInfo", buyerData); // //普通会员Erui数、普通会员ERUI&KERUI数、高级会员Erui数、高级会员ERUI&KERUI数
        data.put("inqNumInfo", inqNumInfoData); // 询单数量数据信息
        data.put("quoteInfo", quoteInfoData); // 报价数量/金额数据信息
        data.put("orderInfo", orderInfoData); // 订单数量/金额数据信息

        return new Result<>(data);
    }


    /**
     * 导出地区明细
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/exportAreaDetail")
    public Object exportAreaDetail(HttpServletResponse response, String startTime, String endTime) {
        Map<String, Object> params = new HashMap();
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        } else {
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            //处理时间参数
            params = ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR, null);
            if (params == null) {
                return new Result<>(ResultStatusEnum.PARAM_ERROR);
            }
            //环比为前7天的数据
            Date start = DateUtil.parseString2DateNoException(String.valueOf(params.get("startTime")), DateUtil.FULL_FORMAT_STR);
            Date chainStartTime = DateUtil.sometimeCalendar(start, 7);
            params.put("chainStartTime", DateUtil.formatDateToString(chainStartTime, DateUtil.FULL_FORMAT_STR));
        }

        HSSFWorkbook wb = weeklyReportService.genAreaDetailExcel(params);
        //excel文件名
        String fileName = "地区周报明细" + System.currentTimeMillis() + ".xls";
        try {
            RequestCreditController.setResponseHeader(response, fileName);
            wb.write(response.getOutputStream());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 事业部明细
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/orgDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object orgDetail(@RequestBody(required = true) Map<String, Object> params, HttpServletRequest request) throws IOException {
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        } else {
            //处理时间参数
            params = ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR, null);
            if (params == null) {
                return new Result<>(ResultStatusEnum.PARAM_ERROR);
            }
            //环比为前7天的数据
            Date start = DateUtil.parseString2DateNoException(String.valueOf(params.get("startTime")), DateUtil.FULL_FORMAT_STR);
            Date chainStartTime = DateUtil.sometimeCalendar(start, 7);
            params.put("chainStartTime", DateUtil.formatDateToString(chainStartTime, DateUtil.FULL_FORMAT_STR));
        }

        // 询单数量信息
        Map<String, Object> inqNumInfoData = weeklyReportService.selectInqNumGroupByOrg(params);
        // 报价数量、金额、用时
        Map<String, Object> quoteInfoData = weeklyReportService.selectQuoteInfoGroupByOrg(params);
        // 查询订单数量、金额
        Map<String, Object> orderInfoData = weeklyReportService.selectOrderInfoGroupByOrg(params);
        // 查询合格供应商数量信息
        Map<String, Object> supplierNumInfoData = weeklyReportService.selectSupplierNumInfoGroupByOrg(params);
//        // 查询事业部spu和sku数量信息
//        Map<String, Object> spuSkuNumInfoData = weeklyReportService.selectSpuAndSkuNumInfoGroupByOrg(params);
        Cookie[] cookies = request.getCookies();
        Map<String, Object> spuSkuNumInfoData = getSpuSkuNumGroupByOrgToES(params, cookies);
        if (spuSkuNumInfoData == null) {
            return new Result<>(ResultStatusEnum.GET_USERINFO_ERROR);
        }
        Map<String, Map> data = new HashMap<>();
        data.put("inqNumInfo", inqNumInfoData); // 询单数量数据信息
        data.put("quoteInfo", quoteInfoData); // 报价数据信息
        data.put("orderInfo", orderInfoData); // 订单数据信息
        data.put("supplierNumInfo", supplierNumInfoData); // 供应商数量数据信息
        data.put("spuSkuNumInfoData", spuSkuNumInfoData); // spu/sku数量数据信息

        return new Result<>(data);
    }


    /**
     * 导出事业部明细
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/exportOrgDetail")
    public Object exportOrgDetail(HttpServletRequest request, HttpServletResponse response, String startTime, String endTime) throws IOException {
        Map<String, Object> params = new HashMap();
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        } else {
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            //处理时间参数
            params = ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR, null);
            if (params == null) {
                return new Result<>(ResultStatusEnum.PARAM_ERROR);
            }
            //环比为前7天的数据
            Date start = DateUtil.parseString2DateNoException(String.valueOf(params.get("startTime")), DateUtil.FULL_FORMAT_STR);
            Date chainStartTime = DateUtil.sometimeCalendar(start, 7);
            params.put("chainStartTime", DateUtil.formatDateToString(chainStartTime, DateUtil.FULL_FORMAT_STR));
        }
        Cookie[] cookies = request.getCookies();
        params.put("pageSize", "10");
        params.put("currentPage", "1");
        params.put("lang", "zh");
        Map<String, Object> spuSkuNumInfoData = getSpuSkuNumGroupByOrgToES(params, cookies);
        if (spuSkuNumInfoData == null) {
            Integer[] zeroArr = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};
            spuSkuNumInfoData = new HashMap<>();
            spuSkuNumInfoData.put("currentWeekSpuCounts", zeroArr);
            spuSkuNumInfoData.put("currentWeekSkuCounts", zeroArr);
            spuSkuNumInfoData.put("historySpuCounts", zeroArr);
            spuSkuNumInfoData.put("historySkuCounts", zeroArr);
        }

        HSSFWorkbook wb = weeklyReportService.genOrgDetailExcel(params, spuSkuNumInfoData);
        //excel文件名
        String fileName = "事业部周报明细" + System.currentTimeMillis() + ".xls";
        try {
            RequestCreditController.setResponseHeader(response, fileName);
            wb.write(response.getOutputStream());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 平台数据分析
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/platformDataDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object platformDataDetail(@RequestBody(required = true) Map<String, Object> params) {
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        } else {
            //处理时间参数
            params = ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR, null);
            if (params == null) {
                return new Result<>(ResultStatusEnum.PARAM_ERROR);
            }
            //环比为前7天的数据
            Date start = DateUtil.parseString2DateNoException(String.valueOf(params.get("startTime")), DateUtil.FULL_FORMAT_STR);
            Date chainStartTime = DateUtil.sometimeCalendar(start, 7);
            params.put("chainStartTime", DateUtil.formatDateToString(chainStartTime, DateUtil.FULL_FORMAT_STR));
        }

        // 查询时间段内询单数
        Map<String, Object> inqNumInfoData = weeklyReportService.selectInqNumGroupByAreaTotal(params);
        // 查询时间段内的报价数量
        Map<String, Object> quoteInfoData = weeklyReportService.selectQuoteInfoGroupByAreaTotal(params);
        // 查询时间段内的订单数量
        Map<String, Object> orderInfoData = weeklyReportService.selectOrderInfoGroupByAreaTotal(params);
        // 谷歌统计信息
        Map<String, Object> googleStatistics = weeklyReportService.googleStatisticsInfo(params);

        Map<String, Map> data = new HashMap<>();
        data.put("googleStatistics", googleStatistics); // 谷歌统计信息
        data.put("inqNumInfo", inqNumInfoData); // 询单数量数据信息
        data.put("quoteInfo", quoteInfoData); // 报价数量数据信息
        data.put("orderInfo", orderInfoData); // 订单数量数据信息


        return new Result<>(data);
    }


    /**
     * 导出平台数据分析
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/exportPlatformDataDetail")
    public Object exportPlatformDataDetail(HttpServletResponse response, String startTime, String endTime) {
        Map<String, Object> params = new HashMap();
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        } else {
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            //处理时间参数
            params = ParamsUtils.verifyParam(params, DateUtil.FULL_FORMAT_STR, null);
            if (params == null) {
                return new Result<>(ResultStatusEnum.PARAM_ERROR);
            }
            //环比为前7天的数据
            Date start = DateUtil.parseString2DateNoException(String.valueOf(params.get("startTime")), DateUtil.FULL_FORMAT_STR);
            Date chainStartTime = DateUtil.sometimeCalendar(start, 7);
            params.put("chainStartTime", DateUtil.formatDateToString(chainStartTime, DateUtil.FULL_FORMAT_STR));
        }

        HSSFWorkbook wb = weeklyReportService.genPlatformDataDetail(params);
        //excel文件名
        String fileName = "平台数据分析" + System.currentTimeMillis() + ".xls";
        try {
            RequestCreditController.setResponseHeader(response, fileName);
            wb.write(response.getOutputStream());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Value("#{webProp[esproduct_url]}")
    private String esproductUrl;

    @Value("#{webProp[esgoods_url]}")
    private String esgoodsUrl;

    /**
     * 从es中获取各事业部的spu、sku数量
     *
     * @return
     */
    public Map<String, Object> getSpuSkuNumGroupByOrgToES(Map<String, Object> params, Cookie[] cookies) throws IOException {
        String token = PerformanceController.getToken(cookies);
        String eruiToken = "eruitoken=" + token;
        if (StringUtil.isEmpty(token)) {
            return null;
        }
        params.put("onshelf_at_start", params.get("startTime").toString());
        params.put("onshelf_at_end", params.get("endTime").toString());
        List<Map<String, Object>> spuList = sendPutToES(params, eruiToken, esproductUrl);
        if (spuList == null) {
            return null;
        }
        List<Map<String, Object>> skuList = sendPutToES(params, eruiToken, esgoodsUrl);
        if (skuList == null) {
            return null;
        }
        //获取历史数据
        params.put("onshelf_at_start", "2019-01-01 00:00:00");
        params.put("onshelf_at_end", params.get("endTime").toString());
//        Date start = DateUtil.parseString2DateNoException("2018-01-01 00:00:00", DateUtil.FULL_FORMAT_STR);
//        Date endDate = DateUtil.parseString2DateNoException(String.valueOf(params.get("onshelf_at_end")), DateUtil.FULL_FORMAT_STR);
//        if (start.after(endDate)) {
//           params.put("onshelf_at_end","2018-01-01 00:00:01");
//        }
        List<Map<String, Object>> spuHistoryList = sendPutToES(params, eruiToken, esproductUrl);
        if (spuHistoryList == null) {
            return null;
        }
        List<Map<String, Object>> skuHistoryList = sendPutToES(params, eruiToken, esgoodsUrl);
        if (skuHistoryList == null) {
            return null;
        }
        Map<String, Object> result = weeklyReportService.handleSpuSkuResult(spuList, skuList, spuHistoryList, skuHistoryList);
        return result;
    }

    List<Map<String, Object>> sendPutToES(Map<String, Object> params, String eruiToken, String url) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut putMethod = new HttpPut(url);
        putMethod.setHeader("Cookie", eruiToken);
        StringEntity paramEntity = new StringEntity(JSON.toJSONString(params), "utf-8");
        putMethod.setEntity(paramEntity);
        CloseableHttpResponse execute = httpClient.execute(putMethod);
        HttpEntity entity = execute.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, "UTF-8");
        }
        Map<String, Object> spuMap = JSON.parseObject(result, Map.class);
        List<Map<String, Object>> dataList = null;
        if (spuMap != null) {
            dataList = (List<Map<String, Object>>) spuMap.get("data");
            if ((int) spuMap.get("code") != 403 && dataList == null) {
                dataList = new ArrayList<>();
            }
        }
        return dataList;
    }
}
