package com.erui.boss.web.order;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.excel.ExcelCustomStyle;
import com.erui.order.model.SaleStatistics;
import com.erui.order.service.StatisticsService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 导出数据控制器
 */
@Controller("orderExportDataController")
@RequestMapping("/order/exportData")
public class ExportDataController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExportDataController.class);

    @Autowired
    private StatisticsService statisticsService;


    /**
     * 导出销售业绩统计信息
     *
     * @return
     */

    @RequestMapping(value = "/saleStatistics")
    public ModelAndView saleStatistics(HttpServletResponse response, HttpServletRequest request) {
        Map<String, String> params = getParameters(request);
        SaleStatistics saleStatistics = new SaleStatistics();
        saleStatistics.setRegion(params.get("region"));
        saleStatistics.setCountry(params.get("country"));
        String countriesStr = params.get("countries");
        Set<String> countries = null;
        if (StringUtils.isNotBlank(countriesStr)) {
            String[] split = countriesStr.split(",");
            countries = new HashSet<>(Arrays.asList(split));
        }
        saleStatistics.setStartDate(DateUtil.str2Date(params.get("startDate")));
        saleStatistics.setEndDate(DateUtil.str2Date(params.get("endDate")));
        Map<String, Object> resultData = new HashedMap();
        // 获取统计数据
        HSSFWorkbook workbook = statisticsService.generateSaleStatisticsExcel(saleStatistics, countries);
        downExcel(workbook,response,"销售业绩统计");
        return null;
    }


    /**
     * 下载excel到客户端
     * @param workbook
     * @param response
     * @param title
     */
    private void downExcel(HSSFWorkbook workbook, HttpServletResponse response, String title) {
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, -1);
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "提现审核列表");
        OutputStream out = null;
        try {
            response.setContentType("application/ms-excel;charset=UTF-8");
            String fn = new String((title + "_" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, new Date())).getBytes("GBK"), "ISO-8859-1");
            response.setHeader("Content-disposition", "attachment;filename=" + fn + ".xls");
            out = response.getOutputStream();
            if (workbook != null) {
                workbook.write(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("下载excel{}", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 获取request中的参数
     * @param request
     * @return
     */
    private Map<String, String> getParameters(HttpServletRequest request) {
        Map<String, Object> params = WebUtils.getParametersStartingWith(request, "");
        Map<String, String> result = new HashMap<>();
        Set<Map.Entry<String, Object>> entries = params.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String v = null;
            if (value instanceof String[]) {
                v = StringUtils.join((String[]) value, ",");
            } else {
                v = (String) value;
            }
            result.put(key, v);
        }
        return result;
    }

}
