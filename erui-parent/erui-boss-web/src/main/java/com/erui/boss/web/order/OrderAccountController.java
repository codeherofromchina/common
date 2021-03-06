package com.erui.boss.web.order;


import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderAccount;
import com.erui.order.entity.OrderAccountDeliver;
import com.erui.order.requestVo.OrderAcciuntAdd;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.service.OrderAccountService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public  Result<Object> delGatheringRecord(@RequestBody OrderAcciuntAdd orderAcciuntAdd,HttpServletRequest request){

        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);

        if(orderAcciuntAdd == null || orderAcciuntAdd.getId() == null){
            return new Result<>(ResultStatusEnum.FAIL);
        }
        try {
            orderAccountService.delGatheringRecord(request,orderAcciuntAdd.getId());
            return new Result<>();
        }catch (Exception e){
            return new Result<>().setMsg(e.getMessage());
        }
    }


    /**
     *  添加一条收款记录
     * @param orderAcciuntAdd  收款信息
     * @return
     */
    @RequestMapping(value = "addGatheringRecord",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public  Result<Object> addGatheringRecord(@RequestBody OrderAcciuntAdd orderAcciuntAdd,HttpServletRequest request){

        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);

        Result<Object> result = new Result<>();
        if(orderAcciuntAdd == null ){
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        OrderAccount orderAccount = new OrderAccount();
        Order order = new Order();
        if (orderAcciuntAdd.getOrderId()==null) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("订单id为空");
        }else if (orderAcciuntAdd.getMoney() == null) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("回款金额不能为空");
        }else if (orderAcciuntAdd.getPaymentDate() == null) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("回款时间不能为空");
        }
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
    public Result<Object> findById(@RequestBody OrderAcciuntAdd orderAcciuntAdd , HttpServletRequest request){

        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);


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
    public  Result<Object> updateGatheringRecord(@RequestBody OrderAcciuntAdd orderAccount,HttpServletRequest request){

        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);

        Result<Object> result = new Result<>();
        if(orderAccount == null ){
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        if (orderAccount.getId()==null) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("收款id为空");
        }else if (orderAccount.getMoney() == null) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("回款金额不能为空");
        }else if (orderAccount.getPaymentDate() == null) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("回款时间不能为空");
        }else {
            try {
                orderAccountService.updateGatheringRecord(request,orderAccount);
            }catch (Exception e){
                return new Result<>(ResultStatusEnum.FAIL).setMsg(e.getMessage());
            }
        }
        return new Result<>();
    }



    /**
     * 确认全部收款完成
     * @return
     */
    @RequestMapping(value = "endGatheringRecord", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> endGatheringRecord(@RequestBody OrderAcciuntAdd orderAccount, HttpServletRequest request) {

        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);
        Result<Object> result = new Result<>();
        if (orderAccount == null || orderAccount.getId() == null) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("订单收款信息id不能为空");
        } else {
            try {
                orderAccountService.endGatheringRecord(orderAccount.getId());
            } catch (Exception e) {
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
    public Result<Object> gatheringMessage(@RequestBody Order order ,HttpServletRequest request){

        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);

        if(order == null || order.getId() == null){
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
       Order orderbyId= orderAccountService.gatheringMessage(order.getId());
        orderbyId.setAttachmentSet(null);
        orderbyId.setGoodsList(null);
        orderbyId.setOrderPayments(null);
        orderbyId.setOrderAccounts(null);
        orderbyId.setOrderAccountDelivers(null);
        return new Result<>(orderbyId);
    }


    /**
     * 收款管理
     * @param condition
     * @return
     */
   @RequestMapping(value = "gatheringManage",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> gatheringManage(@RequestBody OrderListCondition condition,HttpServletRequest request){
       String eruiToken = CookiesUtil.getEruiToken(request);
       ThreadLocalUtil.setObject(eruiToken);

       Page<Order> orderbyId= orderAccountService.gatheringManage(condition);
       for (Order order1 : orderbyId){
           order1.setAttachmentSet(null);
           order1.setGoodsList(null);
           order1.setOrderPayments(null);
           order1.setOrderAccounts(null);
           order1.setOrderAccountDelivers(null);
       }
       return new Result<>(orderbyId);
    }


    /**
     * 发货信息查询   (根据订单)
     * @param     map
     * @return
     */
    @RequestMapping(value="queryOrderAccountDeliver", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> queryOrderAccountDeliver(@RequestBody Map<String,Integer> map,HttpServletRequest request){

        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);

        List<OrderAccountDeliver> orderAccountDeliversList = orderAccountService.queryOrderAccountDeliver(map.get("id"));
        for (OrderAccountDeliver orderAccountDeliver :orderAccountDeliversList){
            orderAccountDeliver.setOrder(null);
        }
        return new Result<>(orderAccountDeliversList);
    }

    /**
     *  发货信息查询id  逻辑删除
     * @param orderAcciuntAdd       发货信息id
     */
    @RequestMapping(value="delOrderAccountDeliver", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public  Result<Object> delOrderAccountDeliver(@RequestBody OrderAcciuntAdd orderAcciuntAdd,HttpServletRequest request){
        if(orderAcciuntAdd == null || orderAcciuntAdd.getId() == null){
            return new Result<>(ResultStatusEnum.FAIL).setMsg("发货信息id不能为空");
        }
        try {
            orderAccountService.delOrderAccountDeliver(request,orderAcciuntAdd.getId());
            return new Result<>();
        }catch (Exception e){
            return new Result<>().setMsg(e.getMessage());
        }

    }


    /**
     *  添加一条发货信息
     * @param orderAcciuntAdd  收款信息
     * @return
     */
    @RequestMapping(value = "addOrderAccountDeliver",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public  Result<Object> addOrderAccountDeliver(@RequestBody OrderAcciuntAdd orderAcciuntAdd,HttpServletRequest request){
        Result<Object> result = new Result<>();
        if(orderAcciuntAdd == null ){
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }


        OrderAccountDeliver orderAccountDeliver = new OrderAccountDeliver();
        Order order = new Order();
        if (orderAcciuntAdd.getOrderId()==null) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg("订单id为空");
        }else if (orderAcciuntAdd.getGoodsPrice() == null) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg("发货金额不能为空");
        }else if (orderAcciuntAdd.getDeliverDate() == null) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg("发货时间不能为空");
        }else{
            order.setId(orderAcciuntAdd.getOrderId());
            orderAccountDeliver.setId(null);
            orderAccountDeliver.setDesc(orderAcciuntAdd.getDesc());   //描述
            orderAccountDeliver.setGoodsPrice(orderAcciuntAdd.getGoodsPrice());    //发货金额
            orderAccountDeliver.setDeliverDate(orderAcciuntAdd.getDeliverDate());  //发货时间
            orderAccountDeliver.setOrder(order);
            try {
                orderAccountService.addOrderAccountDeliver(orderAccountDeliver,request);
            }catch (Exception e){
                logger.error("发货信息添加失败：{}", orderAccountDeliver, e);
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg(e.getMessage());
            }
        }
        return result;
    }


    /**
     * 编辑发货信息
     * @param orderAccount
     * @return
     */
    @RequestMapping(value = "updateOrderAccountDeliver",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public  Result<Object> updateOrderAccountDeliver(@RequestBody OrderAcciuntAdd orderAccount,HttpServletRequest request){
        Result<Object> result = new Result<>();
        if(orderAccount == null ){
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        if (orderAccount.getId()==null) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg("收款id为空");
        }else if (orderAccount.getGoodsPrice() == null) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg("发货金额不能为空");
        }else if (orderAccount.getDeliverDate() == null) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg("发货时间不能为空");
        }else {
            try {
                orderAccountService.updateOrderAccountDeliver(request, orderAccount);
                return new Result<>();
            }catch (Exception e){
                return new Result<>().setMsg(e.getMessage());
            }
        }

    }

}
