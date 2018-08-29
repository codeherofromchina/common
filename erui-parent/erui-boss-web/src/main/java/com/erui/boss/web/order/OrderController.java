package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderLog;
import com.erui.order.requestVo.AddOrderVo;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;


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
    public Result<Object> orderManage(@RequestBody OrderListCondition condition, HttpServletRequest request) {
        //页数不能小于1
        if (condition.getPage() < 1) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        // 设置请求语言
        String lang = CookiesUtil.getLang(request);
        condition.setLang(lang);

        Page<Order> orderPage = orderService.findByPage(condition);
        if (orderPage.hasContent()) {
            orderPage.getContent().forEach(vo -> {
                vo.setAttachmentSet(null);
                vo.setOrderPayments(null);
                vo.setGoodsList(null);
                vo.setOrderAccountDelivers(null);
                vo.setOrderAccounts(null);
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
    public Result<Object> addOrder(@RequestBody @Valid AddOrderVo addOrderVo, HttpServletRequest request) throws Exception {
        Result<Object> result = new Result<>(ResultStatusEnum.FAIL);
        logger.info("OrderController.addOrder()");
        boolean continueFlag = false;
        if (addOrderVo.getStatus() != Order.StatusEnum.INIT.getCode() && addOrderVo.getStatus() != Order.StatusEnum.UNEXECUTED.getCode()
                && addOrderVo.getOrderCategory() != 3 && addOrderVo.getOverseasSales() != 3) {
            result.setMsg("销售合同号不能为空");
            result.setEnMsg("The order No. must be filled in");
        } else if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) { // 提交
            if (StringUtils.isBlank(addOrderVo.getContractNo()) && addOrderVo.getOrderCategory() != 3 && addOrderVo.getOverseasSales() != 3) {
                result.setMsg("销售合同号不能为空");
                result.setEnMsg("The order No. must be filled in");
            } else if (StringUtils.isBlank(addOrderVo.getContractNoOs()) && !addOrderVo.getOverseasSales().equals(4) && !addOrderVo.getOverseasSales().equals(5) && !addOrderVo.getOrderCategory().equals(6)) {
                result.setMsg("海外销售合同号不能为空");
                result.setEnMsg("The order No. must be filled in");
            } else if (StringUtils.isBlank(addOrderVo.getLogiQuoteNo()) && !addOrderVo.getOrderCategory().equals(6)) {
                result.setMsg("物流报价单号不能为空");
                result.setEnMsg("Logistics quotation No. must be filled in");
            } else if (addOrderVo.getOrderType() == null) {
                result.setMsg("订单类型不能为空");
                result.setEnMsg("Order type must be filled in");
            } else if (addOrderVo.getSigningDate() == null && addOrderVo.getOrderCategory() != 1 && addOrderVo.getOrderCategory() != 3) {
                result.setMsg("订单签约日期不能为空");
                result.setEnMsg("Order contract date must be filled in");
            } else if (addOrderVo.getDeliveryDate() == null) {
                result.setMsg("合同交货日期不能为空");
                result.setEnMsg("Contract delivery date must be filled in");
            } /*else if (addOrderVo.getSigningCo() == null) {
                result.setMsg("签约主体公司不能为空");
                result.setEnMsg("Order contract company must be filled in");
            }*/ else if (addOrderVo.getAgentId() == null) {
                result.setMsg("市场经办人不能为空");
                result.setEnMsg("Market manager must be filled in");
            } else if (addOrderVo.getExecCoId() == null) {
                result.setMsg("执行分公司不能为空");
                result.setEnMsg("Executing company must be filled in");
            } else if (StringUtils.isBlank(addOrderVo.getRegion())) {
                result.setMsg("所属地区不能为空");
                result.setEnMsg("Affiliating area must be filled in");
            } else if (StringUtils.isBlank(addOrderVo.getDistributionDeptName())) {
                result.setMsg("分销部不能为空");
                result.setEnMsg("Distribution must be filled in");
            } else if (StringUtils.isBlank(addOrderVo.getCountry())) {
                result.setMsg("国家不能为空");
                result.setEnMsg("Country name must be filled in");
            } else if (StringUtils.isBlank(addOrderVo.getCrmCode()) && addOrderVo.getOrderCategory() != 1) {
                result.setMsg("CRM客户代码不能为空");
                result.setEnMsg("CRM No. must be filled in");
            } else if (addOrderVo.getCustomerType() == null) {
                result.setMsg("客户类型不能为空");
                result.setEnMsg("Customer type must be filled in");
            } else if (StringUtils.isBlank(addOrderVo.getPerLiableRepay()) && !addOrderVo.getOrderCategory().equals(1) && !addOrderVo.getOrderCategory().equals(6) && !addOrderVo.getOrderCategory().equals(3)) {
                result.setMsg("回款责任人不能为空");
                result.setEnMsg("Collection manager must be filled in");
            } else if (addOrderVo.getBusinessUnitId() == null) {
                result.setMsg("事业部不能为空");
                result.setEnMsg("Distribution must be filled in");
            } else if (addOrderVo.getTechnicalId() == null) {
                result.setMsg("商务技术经办人不能为空");
                result.setEnMsg("Business technical manager must be filled in");
            } else if (addOrderVo.getGoodDesc().isEmpty()) {
                result.setMsg("商品不能为空");
                result.setEnMsg("Product must be filled in");
            } else if (addOrderVo.getGoodDesc().parallelStream().anyMatch(vo -> vo.getContractGoodsNum() == null)) {
                result.setMsg("合同数量不能为空");
                result.setEnMsg("Quantity must be filled in");
            } else if (addOrderVo.getTotalPrice() == null || addOrderVo.getTotalPrice().compareTo(BigDecimal.ZERO) != 1) {
                result.setMsg("合同总价错误");
                result.setEnMsg("Contract price error");
            } else if (addOrderVo.getTaxBearing() == null) {
                result.setMsg("是否含税不能为空");
                result.setEnMsg("Tax-inclusive or not must be filled in ");
            } else if (StringUtils.isBlank(addOrderVo.getPaymentModeBn()) && addOrderVo.getOrderCategory() != 1 && addOrderVo.getOrderCategory() != 3) {
                result.setMsg("收款方式不能为空");
                result.setEnMsg("Payment term must be filled in");
            } else if (addOrderVo.getAcquireId() == null) {
                result.setMsg("获取人不能为空");
                result.setEnMsg("Obtainer must be filled in");
            } else if (StringUtils.isBlank(addOrderVo.getCurrencyBn())) {
                result.setMsg("货币类型不能为空");
                result.setEnMsg("Currency type must be filled in");
            } else {
                continueFlag = true;
            }
        } else {
            continueFlag = true;
        }

        if (!continueFlag) {
            return result;
        }
        Integer id;
        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);
        if (addOrderVo.getId() != null) {
            id = orderService.updateOrder(addOrderVo);
        } else {
            id = orderService.addOrder(addOrderVo);
        }
        if (id != null) {
            return new Result<>(id);
        }

        return result;

    }

    /**
     * 审核项目
     *
     * @param params type 审核类型：-1：驳回（驳回必须存在驳回原因参数） 其他或空：正常审核
     * @param params reason 驳回原因参数
     * @param params orderId 要审核或驳回的项目ID
     * @return
     */
    @RequestMapping(value = "auditProject", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> auditProject(HttpServletRequest request, Map<String, String> params) {
        Integer orderId = Integer.parseInt(params.get("orderId")); // 订单ID
        String reason = params.get("reason"); // 驳回原因
        String type = params.get("type"); // 驳回or审核

        // 判断订单是否存在，
        Order order = orderService.findById(orderId);
        if (order == null) {
            return new Result<>(ResultStatusEnum.PROJECT_NOT_EXIST);
        }
        // 获取当前登录用户ID并比较是否是当前用户审核
        Object userId = request.getSession().getAttribute("userid");
        Object userName = request.getSession().getAttribute("realname");
        String auditingUserIds = order.getAuditingUserId();
        if (auditingUserIds == null || !StringUtils.equals(String.valueOf(userId), auditingUserIds)) {
            return new Result<>(ResultStatusEnum.NOT_NOW_AUDITOR);
        }
        // 判断是否是驳回并判断原因参数
        boolean rejectFlag = "-1".equals(type);
        if (rejectFlag && StringUtils.isBlank(reason)) {
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR);
        }

        // 判断通过，审核项目并返回是否审核成功
        boolean flag = orderService.audit(order, String.valueOf(userId), String.valueOf(userName), rejectFlag, reason);
        if (flag) {
            return new Result<>();
        }
        return new Result<>(ResultStatusEnum.FAIL);
    }

    /**
     * 获取订单详情
     *
     * @return
     */
    @RequestMapping(value = "queryOrderDesc", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Order> queryOrderDesc(@RequestBody Map<String, Integer> map, HttpServletRequest request) {

        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);

        Order order = orderService.findByIdLang(map.get("id"), CookiesUtil.getLang(request));
        if (order != null) {
            order.setOrderAccountDelivers(null);
            order.setOrderAccounts(null);
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

    //检测销售合同号
    @RequestMapping(value = "checkContract", method = RequestMethod.GET)
    public Result<Object> checkContract(String contractNo, Integer id) {
        Result<Object> result = new Result<>(ResultStatusEnum.FAIL);
        Integer i = orderService.checkContractNo(contractNo, id);
        if (i.equals(0)) {
            result.setCode(ResultStatusEnum.SUCCESS.getCode());
            result.setMsg(ResultStatusEnum.SUCCESS.getMsg());
            result.setEnMsg(ResultStatusEnum.SUCCESS.getEnMsg());
            result.setData(i);
        } else {
            result.setData(i);
        }
        return result;
    }
}
