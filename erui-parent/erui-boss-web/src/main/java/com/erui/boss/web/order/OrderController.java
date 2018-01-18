package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.Order;
import com.erui.order.requestVo.AddOrderVo;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.service.OrderService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
      /*  Integer id = map.get("ids");
        Integer [] ids = new Integer[1];
        ids[0] = id;*/
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
        Result<Object> result = new Result<>();
        // TODO参数检查略过
        if (addOrderVo.getStatus() == 1) {
            if (StringUtils.isBlank(addOrderVo.getContractNo()) || StringUtils.equals(addOrderVo.getContractNo(), "")) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("销售合同号不能为空");
            } else {
                try {
                    boolean flag = false;
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
                    if (ex instanceof DataIntegrityViolationException) {
                        result.setCode(ResultStatusEnum.DUPLICATE_ERROR.getCode());
                        result.setMsg("销售合同号已存在");
                        return result;
                    }
                }
            }
        } else if (addOrderVo.getStatus() == 2) {
            if (StringUtils.isBlank(addOrderVo.getContractNo()) || StringUtils.equals(addOrderVo.getContractNo(), "")) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("销售合同号不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getLogiQuoteNo()) || StringUtils.equals(addOrderVo.getLogiQuoteNo(), "")) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("物流报价单号不能为空");
            } else if (addOrderVo.getOrderType() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("订单类型不能为空");
            } else if (addOrderVo.getSigningDate() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("订单签约日期不能为空");
            } else if (addOrderVo.getDeliveryDate() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("合同交货日期不能为空");
            } else if (addOrderVo.getSigningCo() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("签约主体公司不能为空");
            } else if (addOrderVo.getAgentId() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("市场经办人不能为空");
            } else if (addOrderVo.getExecCoId() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("执行分公司不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getRegion()) || StringUtils.equals(addOrderVo.getRegion(), "")) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("所属地区不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getDistributionDeptName()) || StringUtils.equals(addOrderVo.getDistributionDeptName(), "")) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("分销部不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getCountry()) || StringUtils.equals(addOrderVo.getCountry(), "")) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("国家不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getCrmCode()) || StringUtils.equals(addOrderVo.getCrmCode(), "")) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("CRM客户代码不能为空");
            } else if (addOrderVo.getCustomerType() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("客户类型不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getPerLiableRepay()) || StringUtils.equals(addOrderVo.getPerLiableRepay(), "")) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("回款责任人不能为空");
            } else if (addOrderVo.getBusinessUnitId() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("事业部不能为空");
            } else if (addOrderVo.getTechnicalId() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("商务技术经办人不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getTradeTerms()) || StringUtils.equals(addOrderVo.getTradeTerms(), "")) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("贸易术语不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getTransportType()) || StringUtils.equals(addOrderVo.getTransportType(), "")) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("运输方式不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getToCountry()) || StringUtils.equals(addOrderVo.getToCountry(), "")) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("目的国不能为空");
            } else if (addOrderVo.getGoodDesc().parallelStream().anyMatch(vo -> vo.getContractGoodsNum() == null)) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("合同数量不能为空");
            } else if (addOrderVo.getTotalPrice() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("合同总价不能为空");
            } else if (addOrderVo.getTaxBearing() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("是否含税不能为空");
            } else if (StringUtils.isBlank(addOrderVo.getPaymentModeBn()) || StringUtils.equals(addOrderVo.getPaymentModeBn(), "")) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("收款方式不能为空");
            } else {
                try {
                    boolean flag = false;
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
                    if (ex instanceof DataIntegrityViolationException) {
                        result.setCode(ResultStatusEnum.DUPLICATE_ERROR.getCode());
                        result.setMsg("销售合同号已存在");
                        return result;
                    }
                }
            }
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
        return new Result<>(order);
    }
}
