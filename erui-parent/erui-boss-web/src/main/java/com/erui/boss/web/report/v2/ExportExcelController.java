package com.erui.boss.web.report.v2;

import com.erui.boss.web.util.HttpUtils;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.BuyerStatisticsService;
import com.erui.report.service.OrderStatisticsService;
import com.erui.report.service.QuoteStatisticsService;
import com.erui.report.util.ParamsUtils;
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
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);

        HSSFWorkbook wb = buyerStatisticsService.getOrderBuyerStatisticsExcel(params);
        String fileName = "开发会员统计" + System.currentTimeMillis() + ".xlsx";
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
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        HSSFWorkbook wb = quoteStatisticsService.genQuotePerformanceExcel(params);
        String fileName = "报检单成单统计" + System.currentTimeMillis() + ".xlsx";
        HttpUtils.setExcelResponseHeader(response, fileName.toString());
        wb.write(response.getOutputStream());
    }




    /**
     * 业绩统计-业务业绩统计 导出excep
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/projectList")
    public void projectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Map<String, String> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        HSSFWorkbook wb = orderStatisticsService.genProjectListExcel(params);
        String fileName = "业绩统计-业务业绩统计" + System.currentTimeMillis() + ".xlsx";
        HttpUtils.setExcelResponseHeader(response, fileName.toString());
        wb.write(response.getOutputStream());
    }
}
