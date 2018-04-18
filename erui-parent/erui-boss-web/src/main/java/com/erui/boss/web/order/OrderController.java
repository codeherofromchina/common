package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.EruitokenUtil;
import com.erui.comm.util.excel.BuildExcel;
import com.erui.comm.util.excel.BuildExcelImpl;
import com.erui.comm.util.excel.ExcelCustomStyle;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderLog;
import com.erui.order.entity.Project;
import com.erui.order.requestVo.AddOrderVo;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by GS on 2017/12/15 0015.
 */
@RestController
@RequestMapping("order/orderManage")
public class OrderController {
    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    /**
     * 获取单列表
     *
     * @return
     */
    @RequestMapping(value = "orderManage", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderManage(@RequestBody OrderListCondition condition) {
        //页数不能小于1
        if (condition.getPage() < 1) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        Page<Order> orderPage = orderService.findByPage(condition);
        if (orderPage.hasContent()) {
            orderPage.getContent().forEach(vo -> {
                vo.setAttachmentSet(null);
                vo.setOrderPayments(null);
                vo.setGoodsList(null);
            });
        }
        return new Result<>(orderPage);
    }

    /**
     * 删除订单
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "deleteOrder", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderDelete(@RequestBody Map<String, Integer[]> ids) {
        orderService.deleteOrder(ids.get("ids"));
        return new Result<>();
    }

    /**
     * 新增订单
     *
     * @param addOrderVo
     * @return
     */
    @RequestMapping(value = "addOrder", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> addOrder(@RequestBody AddOrderVo addOrderVo, HttpServletRequest request) {
        Result<Object> result = new Result<>(ResultStatusEnum.FAIL);
        logger.info("OrderController.addOrder()");
        boolean continueFlag = false;
        if (addOrderVo.getStatus() != Order.StatusEnum.INIT.getCode() && addOrderVo.getStatus() != Order.StatusEnum.UNEXECUTED.getCode()) {
            result.setMsg("销售合同号不能为空");
        } else if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) { // 提交
            if (StringUtils.isBlank(addOrderVo.getContractNo())) {
                result.setMsg("销售合同号不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getLogiQuoteNo())) {
                result.setMsg("物流报价单号不能为空");
            } else if (addOrderVo.getOrderType() == null) {
                result.setMsg("订单类型不能为空");
            } else if (addOrderVo.getSigningDate() == null) {
                result.setMsg("订单签约日期不能为空");
            } else if (addOrderVo.getDeliveryDate() == null) {
                result.setMsg("合同交货日期不能为空");
            } else if (addOrderVo.getSigningCo() == null) {
                result.setMsg("签约主体公司不能为空");
            } else if (addOrderVo.getAgentId() == null) {
                result.setMsg("市场经办人不能为空");
            } else if (addOrderVo.getExecCoId() == null) {
                result.setMsg("执行分公司不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getRegion())) {
                result.setMsg("所属地区不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getDistributionDeptName())) {
                result.setMsg("分销部不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getCountry())) {
                result.setMsg("国家不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getCrmCode())) {
                result.setMsg("CRM客户代码不能为空");
            } else if (addOrderVo.getCustomerType() == null) {
                result.setMsg("客户类型不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getPerLiableRepay())) {
                result.setMsg("回款责任人不能为空");
            } else if (addOrderVo.getBusinessUnitId() == null) {
                result.setMsg("事业部不能为空");
            } else if (addOrderVo.getTechnicalId() == null) {
                result.setMsg("商务技术经办人不能为空");
            } else if (addOrderVo.getGoodDesc().isEmpty()) {
                result.setMsg("商品不能为空");
            } else if (addOrderVo.getGoodDesc().parallelStream().anyMatch(vo -> vo.getContractGoodsNum() == null)) {
                result.setMsg("合同数量不能为空");
            } else if (addOrderVo.getTotalPrice() == null || addOrderVo.getTotalPrice().compareTo(BigDecimal.ZERO) != 1) {
                result.setMsg("合同总价错误");
            } else if (addOrderVo.getTaxBearing() == null) {
                result.setMsg("是否含税不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getPaymentModeBn())) {
                result.setMsg("收款方式不能为空");
            } else if (addOrderVo.getAcquireId() == null) {
                result.setMsg("获取人不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getCurrencyBn())) {
                result.setMsg("货币类型不能为空");
            } else {
                continueFlag = true;
            }
        } else {
            continueFlag = true;
        }

        if (!continueFlag) {
            return result;
        }

        try {
            Integer id;
            String eruiToken = EruitokenUtil.getEruiToken(request);
            ThreadLocalUtil.setObject(eruiToken);
            if (addOrderVo.getId() != null) {
                id = orderService.updateOrder(addOrderVo);
            } else {
                id = orderService.addOrder(addOrderVo);
            }
            if (id != null) {
                return new Result<>(id);
            }
        } catch (Exception ex) {
            logger.error("订单操作失败：{}", addOrderVo, ex);
            result.setMsg(ex.getMessage());
        }

        return result;

    }

    /**
     * 获取订单详情
     *
     * @return
     */
    @RequestMapping(value = "queryOrderDesc", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Order> queryOrderDesc(@RequestBody Map<String, Integer> map) {
        Order order = orderService.findById(map.get("id"));
        if (order != null) {
            if (order.getDeliverConsignC() && order.getStatus() == Order.StatusEnum.EXECUTING.getCode()) {
                boolean flag = order.getGoodsList().parallelStream().anyMatch(vo -> vo.getOutstockApplyNum() < vo.getContractGoodsNum());
                order.setDeliverConsignC(flag);
            } else {
                order.setDeliverConsignC(Boolean.FALSE);
            }
        }
        return new Result<>(order);
    }

    /**
     * 获取订单跟踪
     *
     * @return
     */
    @RequestMapping(value = "queryOrderLog", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> queryOrderLog(@RequestBody Map<String, Integer> map) {
        Integer orderId = map.get("orderId");
        Result<Object> result = new Result<>();
        if (orderId == null || orderId <= 0) {
            return result.setStatus(ResultStatusEnum.FAIL).setMsg("订单号错误");
        }
        List<OrderLog> logList = orderService.orderLog(orderId);
        if (logList.size() == 0) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        return new Result<>(logList);
    }

    /**
     * 确认订单
     *
     * @param order
     * @return
     */
    @RequestMapping(value = "orderFinish", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderFinish(@RequestBody Order order) {
        Result<Object> result = new Result<>(ResultStatusEnum.FAIL);
        boolean flag;
        flag = orderService.orderFinish(order);
        if (flag) {
            result.setCode(ResultStatusEnum.SUCCESS.getCode());
            result.setMsg(ResultStatusEnum.SUCCESS.getMsg());
            return result;
        }
        return result;
    }

    /**
     * 订单列表导出
     *
     * @return
     */
    @RequestMapping(value = "orderListExport", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderListExport(@RequestBody OrderListCondition condition) {
        //页数不能小于1
        if (condition.getPage() < 1) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        // 准备数据容器
        String[] header = new String[]{"ID", "销售合同号", "项目号", "Po号", "询单号", "市场经办人", "商务技术经办人", "合同交货日期", "订单签约日期",
                "CRM客户代码", "订单类型", "合同总价", "款项状态", "订单来源", "订单状态", "项目状态", "流程进度"};
        List<Object[]> datas = new ArrayList<Object[]>();

        Page<Order> orderPage = orderService.findByPage(condition);
        if (orderPage.hasContent()) {
            orderPage.getContent().forEach(vo -> {
                vo.setAttachmentSet(null);
                vo.setOrderPayments(null);
                vo.setGoodsList(null);
                Object[] rowData = new Object[header.length];
                rowData[0] = vo.getId();
                rowData[1] = vo.getContractNo();
                rowData[2] = vo.getProjectNo();
                rowData[3] = vo.getPoNo();
                rowData[4] = vo.getInquiryNo();
                rowData[5] = vo.getAgentName();
                rowData[7] = vo.getBusinessName();
                rowData[8] = vo.getDeliveryDate();
                rowData[9] = vo.getSigningDate();
                rowData[10] = vo.getCrmCode();
                if (vo.getOrderType() == 1) {
                    rowData[11] = "油气";
                } else {
                    rowData[11] = "非油气";
                }
                rowData[12] = vo.getTotalPrice();
                // 1:未付款 2:部分付款 3:收款完成
                if (vo.getPayStatus() == 1) {
                    rowData[13] = "未付款";
                } else if (vo.getPayStatus() == 2) {
                    rowData[13] = "部分付款";
                } else {
                    rowData[13] = "收款完成";
                }


                // 1 门户订单 2 门户询单 3 线下订单',
                if (vo.getOrderSource() == 1) {
                    rowData[14] = "门户订单";
                } else if (vo.getOrderSource() == 2) {
                    rowData[14] = "门户询单";
                } else {
                    rowData[14] = "线下订单";
                }
                rowData[15] = Order.fromCode(vo.getStatus()).getMsg();
                rowData[16] = Project.ProjectProgressEnum.ProjectProgressFromCode(vo.getProcessProgress()).getMsg();
                if (vo.getOrderType() == 1) {
                    rowData[17] = "油气";
                } else {
                    rowData[17] = "非油气";
                }
                rowData[18] = vo.getTotalPrice();
                datas.add(rowData);
            });
        }


        BuildExcel buildExcel = new BuildExcelImpl();


        HSSFWorkbook workbook = buildExcel.buildExcel(datas, header, null,
                "订单列表");
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, -1);
        // 如果要加入标题
       /* ExcelCustomStyle.insertRow(workbook, 0, 0 , 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "提现审核列表");*/
        FileOutputStream out = null;
        try {
            HSSFSheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            System.out.println(lastRowNum);
            out = new FileOutputStream("E:/order/order/订单列表.xls");
            workbook.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new Result<>(orderPage);
    }
}
