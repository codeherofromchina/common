package com.erui.boss.web.order;


import com.erui.boss.web.util.Result;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderAccount;
import com.erui.order.service.OrderAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;


@RestController
@RequestMapping(value = "/order/financeManage")
public class OrderAccountController {


    @Autowired
    private OrderAccountService orderAccountService;


    /**
     * 收款记录查询   (根据订单)
     * @param id    订单id
     * @return
     */
    @RequestMapping(value="queryGatheringRecord", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> queryGatheringRecord(@RequestParam(name = "id") Integer id ){
        List<OrderAccount> orderAccountList = orderAccountService.queryGatheringRecordAll(id);
        return new Result<>(orderAccountList);
    }


    /**
     *  根据收款记录id 逻辑删除
     * @param id       收款信息id
     */
    @RequestMapping(value="delGatheringRecord", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public  Result<Object> delGatheringRecord(Integer id){
        orderAccountService.delGatheringRecord(id);
        return new Result<>();
    }


    /**
     *  添加一条收款记录
     * @param orderAccount  收款信息
     * @return
     */
    @RequestMapping(value = "addGatheringRecord",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public  Result<Object> addGatheringRecord(@RequestBody OrderAccount orderAccount){
        Order order = new Order();
        order.setId(orderAccount.getId());
        orderAccount.setId(null);
        orderAccount.setOrder(order);
        orderAccountService.addGatheringRecord(orderAccount);
        return new Result<>();
    }

    /**
     * 根据id查询订单收款记录 (编辑收款订单查询数据)
     * @param id    收款信息id
     * @return
     */
    @RequestMapping(value="findById", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> findById(@RequestParam(name = "id") Integer id ){
        OrderAccount orderAccount = orderAccountService.findById(id);
        return new Result<>(orderAccount);
    }

    /**
     * 编辑收款记录
     * @param orderAccount
     * @return
     */
    @RequestMapping(value = "updateGatheringRecord",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public  Result<Object> updateGatheringRecord( OrderAccount orderAccount){
        orderAccountService.updateGatheringRecord(orderAccount);
        return new Result<>();
    }



    /**
     * 确认全部收款完成
     * @return
     */
    @RequestMapping(value = "endGatheringRecord",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> endGatheringRecord(@RequestParam(name = "id") Integer id ){
        orderAccountService.endGatheringRecord(id);
        return new Result<>();
    }


    /**
     * 收款信息
     * @param order
     * @return
     */
    @RequestMapping(value = "gatheringMessage",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> gatheringMessage(Order order){
       Order orderbyId= orderAccountService.gatheringMessage(order.getId());
        return new Result<>(orderbyId);
    }


    /**
     * 收款管理
     * @param order
     * @return
     */
   @RequestMapping(value = "gatheringManage",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> gatheringManage( Order order,Integer page, Integer rows){
       Page<Order> orderbyId= orderAccountService.gatheringManage(order,page,rows);
       return new Result<>(orderbyId);
    }




}
