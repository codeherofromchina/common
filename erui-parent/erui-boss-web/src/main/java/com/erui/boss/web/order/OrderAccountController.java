package com.erui.boss.web.order;


import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderAccount;
import com.erui.order.requestVo.OrderAcciuntAdd;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.service.OrderAccountService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 收款管理
 */

@RestController
@RequestMapping(value = "/order/financeManage")
public class OrderAccountController {
    private final static Logger logger = LoggerFactory.getLogger(PurchRequisitionController.class);

    @Autowired
    private OrderAccountService orderAccountService;


    /**
     * 收款记录查询   (根据订单)
     * @param     map
     * @return
     */
    @RequestMapping(value="queryGatheringRecord", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> queryGatheringRecord(@RequestBody Map<String,Integer> map){
        List<OrderAccount> orderAccountList = orderAccountService.queryGatheringRecordAll(map.get("id"));
        for (OrderAccount orderAccount :orderAccountList){
            orderAccount.setOrder(null);
        }
            return new Result<>(orderAccountList);
    }


    /**
     *  根据收款记录id 逻辑删除
     * @param orderAcciuntAdd       收款信息id
     */
    @RequestMapping(value="delGatheringRecord", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public  Result<Object> delGatheringRecord(@RequestBody OrderAcciuntAdd orderAcciuntAdd,ServletRequest request){
        if(orderAcciuntAdd == null || orderAcciuntAdd.getId() == null){
            return new Result<>(ResultStatusEnum.FAIL);
        }
        orderAccountService.delGatheringRecord(request,orderAcciuntAdd.getId());
        return new Result<>();
    }


    /**
     *  添加一条收款记录
     * @param orderAcciuntAdd  收款信息
     * @return
     */
    @RequestMapping(value = "addGatheringRecord",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public  Result<Object> addGatheringRecord(@RequestBody OrderAcciuntAdd orderAcciuntAdd,ServletRequest request){
        Result<Object> result = new Result<>();
        if(orderAcciuntAdd == null ){
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        OrderAccount orderAccount = new OrderAccount();
        Order order = new Order();
        if (orderAcciuntAdd.getOrderId()==null) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("订单id为空");
        }else if (StringUtils.isBlank(orderAcciuntAdd.getDesc()) || StringUtils.equals(orderAcciuntAdd.getDesc(), "")) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("描述不能为空");
        }else if (orderAcciuntAdd.getMoney() == null) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("回款金额不能为空");
        }else if (orderAcciuntAdd.getPaymentDate() == null) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("回款时间不能为空");
        } /*else if (orderAcciuntAdd.getGoodsPrice() == null) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("发货金额不能为空");
        }else if (orderAcciuntAdd.getDeliverDate() == null) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("发货时间不能为空");
        }*/
        else{
            order.setId(orderAcciuntAdd.getOrderId());
            orderAccount.setId(null);
            orderAccount.setDesc(orderAcciuntAdd.getDesc());   //描述
            orderAccount.setMoney(orderAcciuntAdd.getMoney());  //回款金额
            orderAccount.setPaymentDate(orderAcciuntAdd.getPaymentDate());  //回款时间
            orderAccount.setGoodsPrice(orderAcciuntAdd.getGoodsPrice());    //发货金额
            orderAccount.setDeliverDate(orderAcciuntAdd.getDeliverDate());  //发货时间
            orderAccount.setDiscount(orderAcciuntAdd.getDiscount());    //其他扣款金额
            orderAccount.setOrder(order);
            try {
                orderAccountService.addGatheringRecord(orderAccount,request);
            }catch (Exception e){
                logger.error("收款记录添加失败：{}", orderAccount, e);
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg(e.getMessage());
            }
        }
        return result;
    }

    /**
     * 根据id查询订单收款记录 (编辑收款订单查询数据)
     * @param orderAcciuntAdd    收款信息id
     * @return
     */
    @RequestMapping(value="findById", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> findById(@RequestBody OrderAcciuntAdd orderAcciuntAdd){
        Result<Object> result = new Result<>();
        if(orderAcciuntAdd == null || orderAcciuntAdd.getId() == null){
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("订单收款信息id为空");
        }else{
            try {
                OrderAccount orderAccount = orderAccountService.findById(orderAcciuntAdd.getId());
                orderAccount.setOrder(null);
            }catch (Exception e){
                logger.error("（根据id）编辑收款订单查询数据：", orderAcciuntAdd.getId(), e);
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("编辑收款订单查询数据失败");
            }
        }
        return result;
    }

    /**
     * 编辑收款记录
     * @param orderAccount
     * @return
     */
    @RequestMapping(value = "updateGatheringRecord",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public  Result<Object> updateGatheringRecord(@RequestBody OrderAcciuntAdd orderAccount,ServletRequest request){
        Result<Object> result = new Result<>();
        if(orderAccount == null ){
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        if (orderAccount.getId()==null) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("收款id为空");
        }else if (StringUtils.isBlank(orderAccount.getDesc()) || StringUtils.equals(orderAccount.getDesc(), "")) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("描述不能为空");
        }else if (orderAccount.getMoney() == null) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("回款金额不能为空");
        }else if (orderAccount.getPaymentDate() == null) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("回款时间不能为空");
        }else {
            orderAccountService.updateGatheringRecord(request,orderAccount);
        }
        return new Result<>();
    }



    /**
     * 确认全部收款完成
     * @return
     */
    @RequestMapping(value = "endGatheringRecord",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> endGatheringRecord(@RequestBody OrderAcciuntAdd orderAccount){
        Result<Object> result = new Result<>();
        if(orderAccount == null || orderAccount.getId() == null){
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("订单收款信息id不能为空");
        }else{
            try {
                orderAccountService.endGatheringRecord(orderAccount.getId());
            }catch (Exception e){
                logger.error("(根据id)确认全部收款完成失败：", orderAccount.getId(), e);
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg(e.getMessage());
            }
        }
        return result;
    }


    /**
     * 收款信息
     * @param order
     * @return
     */
    @RequestMapping(value = "gatheringMessage",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> gatheringMessage(@RequestBody Order order){
        if(order == null || order.getId() == null){
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
       Order orderbyId= orderAccountService.gatheringMessage(order.getId());
        orderbyId.setAttachmentSet(null);
        orderbyId.setGoodsList(null);
        orderbyId.setOrderPayments(null);
        return new Result<>(orderbyId);
    }


    /**
     * 收款管理
     * @param condition
     * @return
     */
   @RequestMapping(value = "gatheringManage",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> gatheringManage(@RequestBody OrderListCondition condition){
       Page<Order> orderbyId= orderAccountService.gatheringManage(condition);
       for (Order order1 : orderbyId){
           order1.setAttachmentSet(null);
           order1.setGoodsList(null);
           order1.setOrderPayments(null);
       }
       return new Result<>(orderbyId);
    }

}
