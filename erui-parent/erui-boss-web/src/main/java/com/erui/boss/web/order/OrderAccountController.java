package com.erui.boss.web.order;


import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderAccount;
import com.erui.order.requestVo.OrderAcciuntAdd;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.service.OrderAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 收款管理
 */

@RestController
@RequestMapping(value = "/order/financeManage")
public class OrderAccountController {


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
    public  Result<Object> delGatheringRecord(@RequestBody OrderAcciuntAdd orderAcciuntAdd){
        if(orderAcciuntAdd == null || orderAcciuntAdd.getId() == null){
            return new Result<>(ResultStatusEnum.FAIL);
        }
        orderAccountService.delGatheringRecord(orderAcciuntAdd.getId());
        return new Result<>();
    }


    /**
     *  添加一条收款记录
     * @param orderAcciuntAdd  收款信息
     * @return
     */
    @RequestMapping(value = "addGatheringRecord",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public  Result<Object> addGatheringRecord(@RequestBody OrderAcciuntAdd orderAcciuntAdd){
        if(orderAcciuntAdd == null ){
            return new Result<>(ResultStatusEnum.FAIL);
        }
        OrderAccount orderAccount = new OrderAccount();
        Order order = new Order();
        order.setId(orderAcciuntAdd.getOrderId());
        orderAccount.setId(null);
        orderAccount.setDesc(orderAcciuntAdd.getDesc());
        orderAccount.setMoney(orderAcciuntAdd.getMoney());
        orderAccount.setDiscount(orderAcciuntAdd.getDiscount());
        orderAccount.setPaymentDate(orderAcciuntAdd.getPaymentDate());
        orderAccount.setGoodsPrice(orderAcciuntAdd.getGoodsPrice());
        orderAccount.setDeliverDate(orderAcciuntAdd.getDeliverDate());
        orderAccount.setOrder(order);
        orderAccountService.addGatheringRecord(orderAccount);
        return new Result<>();
    }

    /**
     * 根据id查询订单收款记录 (编辑收款订单查询数据)
     * @param orderAcciuntAdd    收款信息id
     * @return
     */
    @RequestMapping(value="findById", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> findById(@RequestBody OrderAcciuntAdd orderAcciuntAdd){
        if(orderAcciuntAdd == null || orderAcciuntAdd.getId() == null){
            return new Result<>(ResultStatusEnum.FAIL);
        }
        OrderAccount orderAccount = orderAccountService.findById(orderAcciuntAdd.getId());
        orderAccount.setOrder(null);
        return new Result<>(orderAccount);
    }

    /**
     * 编辑收款记录
     * @param orderAccount
     * @return
     */
    @RequestMapping(value = "updateGatheringRecord",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public  Result<Object> updateGatheringRecord(@RequestBody OrderAcciuntAdd orderAccount){
        orderAccountService.updateGatheringRecord(orderAccount);
        return new Result<>();
    }



    /**
     * 确认全部收款完成
     * @return
     */
    @RequestMapping(value = "endGatheringRecord",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> endGatheringRecord(@RequestBody OrderAcciuntAdd orderAccount){
        if(orderAccount == null || orderAccount.getId() == null){
            return new Result<>(ResultStatusEnum.FAIL);
        }
        orderAccountService.endGatheringRecord(orderAccount.getId());
        return new Result<>();
    }


    /**
     * 收款信息
     * @param order
     * @return
     */
    @RequestMapping(value = "gatheringMessage",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> gatheringMessage(Order order){
        if(order == null || order.getId() == null){
            return new Result<>(ResultStatusEnum.FAIL);
        }
       Order orderbyId= orderAccountService.gatheringMessage(order.getId());
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
