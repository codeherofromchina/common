package com.erui.boss.web.report.v2;

import com.erui.boss.web.util.HttpUtils;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.BuyerStatisticsService;
import com.erui.report.service.OrderStatisticsService;
import com.erui.report.service.QuoteStatisticsService;
import com.erui.report.util.ParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 导出Excel控制器
 * Created by wangxiaodan on 2019/3/17.
 */
@Controller
@RequestMapping("/report/v2/exportExcel")
public class ExportExcelController {
    @Autowired
    private BuyerStatisticsService buyerStatisticsService;
    @Autowired
    private QuoteStatisticsService quoteStatisticsService;
    @Autowired
    private OrderStatisticsService orderStatisticsService;

    /**
     * 开发会员统计
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/orderBuyerStatistics")
    public void orderBuyerStatistics(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(startTime)) {
            params.put("startTime", startTime);
        }
        if (StringUtils.isNotBlank(endTime)) {
            params.put("endTime", endTime);
        }
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);

        HSSFWorkbook wb = buyerStatisticsService.genOrderBuyerStatisticsExcel(params);
        String fileName = "开发会员统计" + System.currentTimeMillis() + ".xls";
        HttpUtils.setExcelResponseHeader(response, fileName.toString());
        wb.write(response.getOutputStream());
    }


    /**
     * 报检单成单统计
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/quotePerformance")
    public void quotePerformance(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(startTime)) {
            params.put("startTime", startTime);
        }
        if (StringUtils.isNotBlank(endTime)) {
            params.put("endTime", endTime);
        }
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        HSSFWorkbook wb = quoteStatisticsService.genQuotePerformanceExcel(params);
        String fileName = "报检单成单统计" + System.currentTimeMillis() + ".xls";
        HttpUtils.setExcelResponseHeader(response, fileName.toString());
        wb.write(response.getOutputStream());
    }




    /**
     * 业绩统计-业务业绩统计 导出excel
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/projectList")
    public void projectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> params = coverRequestTimeParam(request);
        HSSFWorkbook wb = orderStatisticsService.genProjectListExcel(params);
        String fileName = "业绩统计-业务业绩统计" + System.currentTimeMillis() + ".xls";
        HttpUtils.setExcelResponseHeader(response, fileName.toString());
        wb.write(response.getOutputStream());
    }


    /**
     * 业绩统计-会员统计(注册会员)导出excel
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/buyerStatistics/registerBuyerList")
    public void registerBuyerList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> params = coverRequestTimeParam(request);
        HSSFWorkbook wb = buyerStatisticsService.genRegisterBuyerListExcel(params);
        String fileName = "业绩统计-会员统计" + System.currentTimeMillis() + ".xls";
        HttpUtils.setExcelResponseHeader(response, fileName);
        wb.write(response.getOutputStream());
    }


    /**
     * 业绩统计-交易会员统计导出excel
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/buyerStatistics/membershipBuyerList")
    public void membershipBuyerList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> params = coverRequestTimeParam(request);
        HSSFWorkbook wb = buyerStatisticsService.genMembershipBuyerListExcel(params);
        String fileName = "业绩统计-交易会员统计" + System.currentTimeMillis() + ".xls";
        HttpUtils.setExcelResponseHeader(response, fileName);
        wb.write(response.getOutputStream());
    }


    /**
     * 业绩统计-入网会员统计导出excel
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/buyerStatistics/applyBuyerList")
    public void applyBuyerList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> params = coverRequestTimeParam(request);
        HSSFWorkbook wb = buyerStatisticsService.genApplyBuyerListExcel(params);
        String fileName = "业绩统计-入网会员统计" + System.currentTimeMillis() + ".xls";
        HttpUtils.setExcelResponseHeader(response, fileName);
        wb.write(response.getOutputStream());
    }



    private Map<String, String> coverRequestTimeParam(HttpServletRequest request) {
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Map<String, String> params = new HashMap<>();
        if (StringUtils.isNotBlank(startTime)) {
            params.put("startTime", startTime);
        }
        if (StringUtils.isNotBlank(endTime)) {
            params.put("endTime", endTime);
        }
        return params;
    }
}
