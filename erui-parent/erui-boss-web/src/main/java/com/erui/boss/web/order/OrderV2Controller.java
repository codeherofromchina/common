package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.order.entity.Order;
import com.erui.order.requestVo.AddOrderV2Vo;
import com.erui.order.requestVo.OrderV2ListRequest;
import com.erui.order.service.OrderV2Service;
import com.erui.report.service.BpmTaskRuntimeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;


/**
 * 版本v2的订单操作控制类
 */
@RestController
@RequestMapping("order/v2/orderManage")
public class OrderV2Controller {
    private final static Logger logger = LoggerFactory.getLogger(OrderV2Controller.class);
    @Resource(name = "orderV2ServiceImpl")
    private OrderV2Service orderService;


    @RequestMapping(value = "addOrder", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> addOrder(@RequestBody @Valid AddOrderV2Vo addOrderVo, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Result<Object> result = new Result<>(ResultStatusEnum.FAIL);
        logger.info("OrderController.addOrder()");
        boolean continueFlag = false;
        if (addOrderVo.getStatus() == Order.StatusEnum.UNEXECUTED.getCode()) { // 提交
            if (addOrderVo.getOrderType() == null) {
                result.setMsg("订单类型不能为空");
                result.setEnMsg("Order type must be filled in");
            } else if (addOrderVo.getOrderCategory() == null){
                result.setMsg("订单类别不能为空");
                result.setEnMsg("Order category cannot be empty");
            } else if (addOrderVo.getSigningDate() == null && addOrderVo.getOrderCategory() != 1 && addOrderVo.getOrderCategory() != 3) {
                result.setMsg("订单签约日期不能为空");
                result.setEnMsg("Order contract date must be filled in");
            } else if (addOrderVo.getDeliveryDate() == null) {
                result.setMsg("合同交货日期不能为空");
                result.setEnMsg("Contract delivery date must be filled in");
            } else if (addOrderVo.getAgentId() == null) {
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
            } else if (StringUtils.isBlank(addOrderVo.getPerLiableRepay()) && !addOrderVo.getOrderCategory().equals(6)) {
                result.setMsg("回款责任人不能为空");
                result.setEnMsg("Collection manager must be filled in");
            } else if (addOrderVo.getBusinessUnitId() == null) {
                result.setMsg("事业部不能为空");
                result.setEnMsg("Distribution must be filled in");
            } else if (addOrderVo.getTechnicalId() == null) {
                result.setMsg("商务技术经办人不能为空");
                result.setEnMsg("Business technical manager must be filled in");
            } else if (addOrderVo.getGoodDesc().parallelStream().anyMatch(vo -> vo.getContractGoodsNum() == null)) {
                result.setMsg("合同数量不能为空");
                result.setEnMsg("Quantity must be filled in");
            } else if (addOrderVo.getOrderCategory() != 2 && addOrderVo.getOrderCategory() != 3 && (addOrderVo.getTotalPrice() == null || addOrderVo.getTotalPrice().compareTo(BigDecimal.ZERO) != 1)) {
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
        String userid = (String) session.getAttribute("userid");
        if (StringUtils.isNumeric(userid)) {
            addOrderVo.setCreateUserId(Integer.parseInt(userid));
        }
        addOrderVo.setCreateUserName((String) session.getAttribute("realname"));
        if (addOrderVo.getId() != null) {
            id = orderService.updateOrder(addOrderVo.getId(), addOrderVo);
        } else {
            id = orderService.addOrder(addOrderVo);
        }
        if (id != null) {
            return new Result<>(id);
        }
        return result;
    }

}
