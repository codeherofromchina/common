package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderLog;
import com.erui.order.requestVo.AddOrderVo;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    public Result<Object> addOrder(@RequestBody AddOrderVo addOrderVo) {
        Result<Object> result = new Result<>(ResultStatusEnum.FAIL);
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
            } else if (addOrderVo.getTotalPrice() == null) {
                result.setMsg("合同总价不能为空");
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
            boolean flag;
            if (addOrderVo.getId() != null) {
                flag = orderService.updateOrder(addOrderVo);
            } else {
                flag = orderService.addOrder(addOrderVo);
            }
            if (flag) {
                return new Result<>();
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
        if (flag){
            result.setCode(ResultStatusEnum.SUCCESS.getCode());
            result.setMsg(ResultStatusEnum.SUCCESS.getMsg());
            return result;
        }
        return result;
    }
}
