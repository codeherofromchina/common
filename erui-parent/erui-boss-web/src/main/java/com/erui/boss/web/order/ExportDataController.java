package com.erui.boss.web.order;

import com.alibaba.fastjson.JSON;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.excel.BuildExcel;
import com.erui.comm.util.excel.BuildExcelImpl;
import com.erui.comm.util.excel.ExcelCustomStyle;
import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import com.erui.order.model.SaleStatistics;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.requestVo.ProjectListCondition;
import com.erui.order.service.OrderService;
import com.erui.order.service.ProjectService;
import com.erui.order.service.StatisticsService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
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
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProjectService projectService;

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
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, -1);
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "提现审核列表");
        downExcel(workbook,response,"销售业绩统计");
        return null;
    }
    /**
     * 导出订单列表信息
     *
     * @return
     */

    @RequestMapping(value = "/orderExport")
    public ModelAndView orderExport(HttpServletResponse response, HttpServletRequest request) throws Exception {
        Map<String, String> params = getParameters(request);
        try{
        OrderListCondition orderListCondition = new OrderListCondition();
        OrderListCondition obj = orderListCondition.getClass().newInstance();
        BeanUtils.populate(obj, params);
        obj.setType(1);
        obj.setOrderType(1);
        List<Order> orderList = orderService.findOrderExport(obj);
        if (orderList.size()>0) {
            orderList.forEach(vo -> {
                vo.setAttachmentSet(null);
                vo.setOrderPayments(null);
                vo.setGoodsList(null);
                vo.setProject(null);
            });
        }
        String[] header = new String[]{"销售合同号", "项目号", "Po号", "询单号", "市场经办人", "商务技术经办人", "合同交货日期", "订单签约日期",
                "CRM客户代码", "订单类型", "合同总价", "款项状态", "订单来源", "订单状态", "流程进度"};
        String[] keys = new String[] {"contractNo", "projectNo","poNo","inquiryNo","agentName","businessName","deliveryDate","signingDate",
                "crmCode","orderTypeName","totalPrice","payStatusName","orderSourceName","orderStatusName","processProgressName"};
        BuildExcel buildExcel = new BuildExcelImpl();
        Object objArr = JSON.toJSON(orderList);
        HSSFWorkbook workbook = buildExcel.buildExcel((List)objArr, header, keys,"订单列表");
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, -1);
        downExcel(workbook, response, "订单列表");
        //  }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 导出项目列表信息
     *
     * @return
     */
    @RequestMapping(value = "/projectExport")
    public ModelAndView projectExport(HttpServletResponse response, HttpServletRequest request) throws Exception {
        Map<String, String> params = getParameters(request);
        ProjectListCondition projectListCondition = new ProjectListCondition();
        //将map转化为Object
        ProjectListCondition projectCon = projectListCondition.getClass().newInstance();
        BeanUtils.populate(projectCon, params);
        List<Project> projectList = projectService.findProjectExport(projectCon);
        if (projectList.size()>0) {
            for (Project project:projectList) {
                project.setGoodsList(null);
                project.setPurchRequisition(null);
                project.setOrder(null);
            }

        }
        String[] header = new String[]{"销售合同号", "项目号", "项目名称", "执行分公司", "分销部", "事业部", "商务技术经办人", "所属地区",
                "项目开始日期", "执行单约定交付日期","执行单变更后日期", "要求采购到货日期", "项目状态", "流程进度"};
        String[] keys = new String[] {"contractNo", "projectNo","projectName","execCoName","distributionDeptName","businessUnitName","businessName","region",
                "startDate","deliveryDate","exeChgDate","requirePurchaseDate","projectStatusName","processProgressName"};
        BuildExcel buildExcel = new BuildExcelImpl();
        Object objArr = JSON.toJSON(projectList);
        HSSFWorkbook workbook = buildExcel.buildExcel((List)objArr, header, keys,"项目列表");
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, -1);
        downExcel(workbook, response, "项目列表");
        return null;
    }
    /**
     * 下载excel到客户端
     * @param workbook
     * @param response
     * @param title
     */
    private void downExcel(HSSFWorkbook workbook, HttpServletResponse response, String title) {
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
                    out.flush();
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
