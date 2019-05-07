package com.erui.boss.web.order;

import com.alibaba.fastjson.JSON;
import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.excel.BuildExcel;
import com.erui.comm.util.excel.BuildExcelImpl;
import com.erui.comm.util.excel.ExcelCustomStyle;
import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import com.erui.order.model.GoodsStatistics;
import com.erui.order.model.SaleStatistics;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.requestVo.ProjectListCondition;
import com.erui.order.service.*;
import com.erui.report.util.ExcelUploadTypeEnum;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * 导出数据控制器
 */
@Controller("orderExportDataController")
@RequestMapping("/order/exportData")
public class ExportDataController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportDataController.class);

    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private PurchService purchService;
    @Autowired
    private PurchContractService purchContractService;

    /**
     * 导出销售业绩统计信息
     *
     * @return
     */

    @RequestMapping(value = "/saleStatistics")
    public void saleStatistics(HttpServletResponse response, HttpServletRequest request) {
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
        downExcel(workbook, response, "销售业绩统计");
    }

    // 商品（产品）统计信息
    @RequestMapping("/goodsStatistics")
    public void goodsStatistics(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = getParameters(request);
        GoodsStatistics goodsStatistics = new GoodsStatistics();
        goodsStatistics.setRegion(params.get("region"));
        goodsStatistics.setCountry(params.get("country"));
        goodsStatistics.setProType(params.get("proType"));
        goodsStatistics.setSku(params.get("sku"));
        goodsStatistics.setBrand(params.get("brand"));
        goodsStatistics.setStartDate(DateUtil.str2Date(params.get("startDate")));
        goodsStatistics.setEndDate(DateUtil.str2Date(params.get("endDate")));
        String countriesStr = params.get("countries");
        Set<String> countries = null;
        if (StringUtils.isNotBlank(countriesStr)) {
            String[] split = countriesStr.split(",");
            countries = new HashSet<>(Arrays.asList(split));
        }
        // 获取统计数据
        HSSFWorkbook data = statisticsService.generateGoodsStatisticsExcel(goodsStatistics, countries);
        downExcel(data, response, "产品统计");
    }


    // 项目统计信息
    @RequestMapping("/projectStatistics")
    public void projectStatistics(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> condition = getParameters(request);
        // 获取统计数据
        HSSFWorkbook data = statisticsService.generateProjectStatisticsExcel(condition);
        downExcel(data, response, "项目执行统计");
    }

    // 项目详情统计信息导出
    @RequestMapping("/projectDescStatistics")
    public void projectDescStatistics(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> condition = getParameters(request);
        // 获取统计数据
        HSSFWorkbook data = statisticsService.generateProjectDescStatisticsExcel(condition);
        downExcel(data, response, "项目商品统计");
    }


    // 商品台账详情excel
    @RequestMapping("/goodsBookDetail")
    public void goodsBookDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderIdStr = request.getParameter("orderId");
        Integer orderId = null;
        if (StringUtils.isNumeric(orderIdStr)) {
            orderId = Integer.parseInt(orderIdStr);
        }
        Result<Object> result = null;
        if (orderId != null && orderId > 0) {
            try {
                HSSFWorkbook workbook = statisticsService.generateGoodsBookDetailExcel(orderId);
                downExcel(workbook, response, "项目详情信息");
                return;
            } catch (Exception ex) {
                LOGGER.error("异常 ： {}", ex);
                result = new Result<>(ResultStatusEnum.FAIL).setMsg(ex.getMessage());
            }
        } else {
            result = new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        if (result != null) {
            OutputStream out = null;
            try {
                response.setContentType("application/json;charset=UTF-8");
                out = response.getOutputStream();
                result.printResult(out);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }

    }

    /**
     * @Author:SHIGS
     * @Description 订单列表导出
     * @Date:18:15 2018/4/19
     * @modified By
     */

    @RequestMapping(value = "/orderExport")
    public ModelAndView orderExport(HttpServletResponse response, HttpServletRequest request) throws Exception {
        Map<String, String> params = getParameters(request);
        try {
            String lang = CookiesUtil.getLang(request);
            //service获取token信息
            String eruiToken = CookiesUtil.getEruiToken(request);
            ThreadLocalUtil.setObject(eruiToken);
            OrderListCondition obj = JSON.parseObject(JSON.toJSONString(params), OrderListCondition.class);
            List<Order> orderList = orderService.findOrderExport(obj);
            if (orderList.size() > 0) {
                orderList.forEach(vo -> {
                    vo.setOrderPayments(null);
                    vo.setAttachmentSet(null);
                    vo.setGoodsList(null);
                    vo.setProject(null);
                    vo.setOrderAccounts(null);
                    vo.setDeliverConsign(null);
                    vo.setOrderAccountDelivers(null);
                    vo.setOrderPayments(null);
                });
            }
          /*  String[] header = new String[]{"销售合同号", "项目号", "Po号", "询单号", "市场经办人", "商务技术经办人", "合同交货日期", "订单签约日期",
                    "CRM客户代码", "订单类型", "合同总价(USD)", "款项状态", "订单来源", "订单状态", "流程进度"};*/
            String[] header = new String[]{"销售合同号", "项目号", "询单号", "商务技术经办人", "执行事业部", "合同交货日期",
                    "CRM客户代码", "合同总价(USD)", "款项状态", "订单状态", "流程进度"};
           /* String[] enHeader = new String[]{"Contract No.", "Project number", "PO No", "Inquiry No", "Market manager", "Agent from business technology department", "Delivery date in the contract", "Signing date of the order",
                    "CRM ID", "Order type",  "Total value(USD)", "Payment status", "Order origin", "Order status", "Project progress"};*/
            String[] enHeader = new String[]{"Contract No.", "Project No", "Inquiry No", "Agent from business technology department", "Executive Division", "Delivery date in the contract",
                    "CRM ID", "Total value(USD)", "Payment status", "Order status", "Project progress"};
           /* String[] keys = new String[]{"contractNo", "projectNo", "poNo", "inquiryNo", "agentName", "businessName", "deliveryDate", "signingDate",
                    "crmCode", "orderTypeName", "totalPriceUsdSplit", "payStatusName", "orderSourceName", "orderStatusName", "processProgressName"};*/
            String[] keys = new String[]{"contractNo", "projectNo", "inquiryNo", "businessName", "businessUnitName", "deliveryDate",
                    "crmCode", "totalPriceUsd", "payStatusName", "orderStatusName", "processProgressName"};
           /* String[] enKeys = new String[]{"contractNo", "projectNo", "poNo", "inquiryNo", "agentName", "businessName", "deliveryDate", "signingDate",
                    "crmCode", "enOrderTypeName", "totalPriceUsdSplit", "enPayStatusName", "enOrderSourceName", "enOrderStatusName", "enProcessProgressName"};*/
            String[] enKeys = new String[]{"contractNo", "projectNo", "inquiryNo", "businessName", "businessUnitName", "deliveryDate",
                    "crmCode", "totalPriceUsd", "enPayStatusName", "enOrderStatusName", "enProcessProgressName"};
            BuildExcel buildExcel = new BuildExcelImpl();
            Object objArr = JSON.toJSON(orderList);
            HSSFWorkbook workbook;
            if (StringUtils.equals(lang, "en")) {
                workbook = buildExcel.buildExcel((List) objArr, enHeader, enKeys, "Order List");
                ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
                ExcelCustomStyle.setContextStyle(workbook, 0, 1, -1);
                downExcel(workbook, response, "Order List");
            } else {
                workbook = buildExcel.buildExcel((List) objArr, header, keys, "订单列表");
                ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
                ExcelCustomStyle.setContextStyle(workbook, 0, 1, -1);
                downExcel(workbook, response, "订单列表");
            }
            //  }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Author:SHIGS
     * @Description 项目导出
     * @Date:18:16 2018/4/19
     * @modified By
     */
    @RequestMapping(value = "/projectExport")
    public ModelAndView projectExport(HttpServletResponse response, HttpServletRequest request) throws Exception {
        Map<String, String> params = getParameters(request);
        ProjectListCondition projectListCondition = JSON.parseObject(JSON.toJSONString(params), ProjectListCondition.class);
        List<Project> projectList = projectService.findProjectExport(projectListCondition);
        Map<String, String> bnMapZhRegion = statisticsService.findBnMapZhRegion();
        if (projectList.size() > 0) {
            for (Project project : projectList) {
                project.setGoodsList(null);
                project.setPurchRequisition(null);
                project.setPurchs(null);
                project.setProjectProfit(null);
                project.setRegion(project.getOrder()==null || project.getOrder().getRegion()==null || project.getOrder().getRegion().length() == 0 ? "" :bnMapZhRegion.get(project.getOrder().getRegion()));
                project.setOrder(null);
            }

        }
      /*  String[] header = new String[]{"销售合同号", "项目号", "合同标的", "执行分公司", "分销部", "事业部", "商务技术经办人", "所属地区",
                "项目创建日期","项目开始日期", "执行单约定交付日期", "执行单变更后日期", "要求采购到货日期", "项目状态", "流程进度"};*/
        String[] header = new String[]{"销售合同号", "项目号", "合同标的", "执行事业部", "商务技术经办人", "项目创建日期", "项目开始日期", "项目状态", "流程进度"};
   /*     String[] keys = new String[]{"contractNo", "projectNo", "projectName", "execCoName", "distributionDeptName", "businessUnitName", "businessName", "region",
                "createTime", "startDate", "deliveryDate", "exeChgDate", "requirePurchaseDate", "projectStatusName", "processProgressName"};*/
        String[] keys = new String[]{"contractNo", "projectNo", "projectName", "businessUnitName", "businessName", "createTime", "startDate", "projectStatusName", "processProgressName"};
        BuildExcel buildExcel = new BuildExcelImpl();
        Object objArr = JSON.toJSON(projectList);
        HSSFWorkbook workbook = buildExcel.buildExcel((List) objArr, header, keys, "项目列表");
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, -1);
        downExcel(workbook, response, "项目列表");
        return null;
    }

    /**
     * @Author:SHIGS
     * @Description 项目核算利润导出
     * @Date:18:16 2018
     * @modified By
     */
    @RequestMapping(value = "/exportCheckProfit")
    public ModelAndView exportCheckProfit(HttpServletResponse response, HttpServletRequest request) {
        Map<String, String> params = getParameters(request);
        Integer id = null;
        if (params.containsKey("id") && params.get("id") != null) {
            id = Integer.parseInt(params.get("id"));
        }
        Integer orderId = null;
        if (params.containsKey("orderId") && params.get("orderId") != null) {
            orderId = Integer.parseInt(params.get("orderId"));
        }
        OutputStream out = null;
        try {
            // 获取数据
            Project project = null;
            if (id != null || orderId != null) {
                project = projectService.findDesc(id);
            }
            Map<String, Object> results = new HashMap<>();
            if (project != null) {
                results.put("projectDec", project);
            } else {
                return null;
            }

            // 拿到模板文件
            // 获取模板文件内容
            String tempPath = request.getSession().getServletContext().getRealPath(EXCEL_TEMPLATE_PATH);
            String suffix = EXCEL_SUFFIX;
            File file = new File(tempPath, ExcelUploadTypeEnum.getByType(16).getTable() + suffix);
            byte[] data = FileUtils.readFileToByteArray(file);
            FileInputStream tps = new FileInputStream(file);
            final XSSFWorkbook tpWorkbook = new XSSFWorkbook(tps);
            out = response.getOutputStream();
            // 输出到客户端
            response.reset();
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"%e6%8a%a5%e4%bb%b7%e5%88%a9%e6%b6%a6%e6%a0%b8%e7%ae%97%e5%8d%95" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, new Date()) + suffix + "\"");
            // 新建一个Excel的工作空间
            XSSFWorkbook workbook = new XSSFWorkbook();
            // 把模板复制到新建的Excel
            workbook = tpWorkbook;
            // 填充数据
            this.projectService.addProfitData(workbook, results);
            // 输出Excel内容，生成Excel文件
            if (workbook != null) {
                workbook.write(out);
            }

        } catch (final Exception e) {
            LOGGER.error("异常" + e.getMessage(), e);
        } finally {
            try {
                // 最后记得关闭输出流
                response.flushBuffer();
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (final IOException e) {
                LOGGER.error("异常" + e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * @Author:SHIGS
     * @Description 国际销售合同签订审批表V3.0导出
     * @Date:18:16 2018
     * @modified By
     */
    @RequestMapping(value = "/exportOrderContract")
    public ModelAndView exportOrderContract(HttpServletResponse response, HttpServletRequest request) {
        Map<String, String> params = getParameters(request);
        Integer id = null;
        if (params.containsKey("id") && params.get("id") != null) {
            id = Integer.parseInt(params.get("id"));
        }
        OutputStream out = null;
        try {
            // 获取数据
            Order order = this.orderService.findById(id);
            Map<String, Object> results = new HashMap<>();
            if (order != null) {
                results.put("orderDesc", order);
            } else {
                return null;
            }
            // 拿到模板文件
            // 获取模板文件内容
            String tempPath = request.getSession().getServletContext().getRealPath(EXCEL_TEMPLATE_PATH);
            String suffix = EXCEL_SUFFIX;
            File file = new File(tempPath, ExcelUploadTypeEnum.getByType(17).getTable() + suffix);
            byte[] data = FileUtils.readFileToByteArray(file);
            FileInputStream tps = new FileInputStream(file);
            final XSSFWorkbook tpWorkbook = new XSSFWorkbook(tps);
            out = response.getOutputStream();
            String encode = URLEncoder.encode(ExcelUploadTypeEnum.getByType(17).getTable(), "UTF-8");
            // 输出到客户端
            response.reset();
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" +
                            "" + encode + "" + DateUtil.format(DateUtil.SHORT_FORMAT_STR, new Date()) + suffix + "\"");
            // 新建一个Excel的工作空间
            XSSFWorkbook workbook = new XSSFWorkbook();
            // 把模板复制到新建的Excel
            workbook = tpWorkbook;
            // 填充数据
            this.orderService.addOrderContract(workbook, results);
            // 输出Excel内容，生成Excel文件
            if (workbook != null) {
                workbook.write(out);
            }

        } catch (final Exception e) {
            LOGGER.error("异常" + e.getMessage(), e);
        } finally {
            try {
                // 最后记得关闭输出流
                response.flushBuffer();
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (final IOException e) {
                LOGGER.error("异常" + e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 下载excel到客户端
     *
     * @param workbook
     * @param response
     * @param title
     */
    private void downExcel(HSSFWorkbook workbook, HttpServletResponse response, String title) {
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, -1);
        //ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        //ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, title);
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
     *
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

    /**
     * 导出采购合同excel
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/exportPurchContract", method = RequestMethod.GET)
    public void exportPurchContract(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id) || !StringUtils.isNumeric(id)) {
            LOGGER.error("参数不正确 {}", id);
            return; // 参数错误，无法下载
        }
        OutputStream out = null;
        try {
            // 拿到模板文件
            // 获取模板文件内容
            String fileName = ExcelUploadTypeEnum.getByType(18).getTable();
            String contextRealPath = request.getSession().getServletContext().getRealPath(EXCEL_TEMPLATE_PATH);
            File file = new File(contextRealPath, fileName + EXCEL_SUFFIX);
            FileInputStream tps = new FileInputStream(file);
            final XSSFWorkbook workbook = new XSSFWorkbook(tps);
            out = response.getOutputStream();
            String encode = URLEncoder.encode(fileName, "UTF-8");
            // 输出到客户端
            response.reset();
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encode
                    + DateUtil.format(DateUtil.SHORT_FORMAT_STR, new Date()) + EXCEL_SUFFIX + "\"");
            // 填充数据
            purchService.fillTempExcelData(workbook, Integer.parseInt(id));
            // 输出Excel内容，生成Excel文件
            if (workbook != null) {
                workbook.write(out);
            }

        } catch (final Exception e) {
            LOGGER.error("异常" + e.getMessage(), e);
        } finally {
            try {
                // 最后记得关闭输出流
                response.flushBuffer();
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (final IOException e) {
                LOGGER.error("异常" + e.getMessage(), e);
            }
        }
    }



    /**
     * 导出简易采购合同excel
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/exportPurchContractSimple", method = RequestMethod.GET)
    public void exportPurchContractSimple(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id) || !StringUtils.isNumeric(id)) {
            LOGGER.error("参数不正确 {}", id);
            return; // 参数错误，无法下载
        }
        OutputStream out = null;
        try {
            // 拿到模板文件
            // 获取模板文件内容
            String fileName = ExcelUploadTypeEnum.getByType(20).getTable();
            String contextRealPath = request.getSession().getServletContext().getRealPath(EXCEL_TEMPLATE_PATH);
            File file = new File(contextRealPath, fileName + EXCEL_SUFFIX);
            FileInputStream tps = new FileInputStream(file);
            final XSSFWorkbook workbook = new XSSFWorkbook(tps);
            out = response.getOutputStream();
            String encode = URLEncoder.encode(fileName, "UTF-8");
            // 输出到客户端
            response.reset();
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encode
                    + DateUtil.format(DateUtil.SHORT_FORMAT_STR, new Date()) + EXCEL_SUFFIX + "\"");
            // 填充数据
            purchContractService.simpleContractExcelData(workbook, Integer.parseInt(id));
            // 输出Excel内容，生成Excel文件
            if (workbook != null) {
                workbook.write(out);
            }

        } catch (final Exception e) {
            LOGGER.error("异常" + e.getMessage(), e);
        } finally {
            try {
                // 最后记得关闭输出流
                response.flushBuffer();
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (final IOException e) {
                LOGGER.error("异常" + e.getMessage(), e);
            }
        }
    }

    private static final String EXCEL_TEMPLATE_PATH = "/WEB-INF/template/excel";
    private static final String EXCEL_SUFFIX = ".xlsx";
    private static final String EXCEL_SUFFIX02 = ".xls";
}
