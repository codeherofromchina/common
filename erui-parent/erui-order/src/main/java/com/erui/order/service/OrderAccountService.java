package com.erui.order.service;

import com.erui.order.entity.Order;
import com.erui.order.entity.OrderAccount;
import com.erui.order.entity.OrderAccountDeliver;
import com.erui.order.requestVo.OrderAcciuntAdd;
import com.erui.order.requestVo.OrderListCondition;
import org.springframework.data.domain.Page;

import javax.servlet.ServletRequest;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface OrderAccountService {
    /**
     * 根据id查询订单收款信息
     * @param id
     * @return
     */
    OrderAccount findById(Integer id);


    /**
     * 收款记录查询   (根据订单)
     * @param id    订单id
     * @return
     */
    List<OrderAccount> queryGatheringRecordAll(Integer id);


    /**
     *  根据收款信息id 逻辑删除
     * @param id       收款信息id
     */
    void delGatheringRecord(ServletRequest request, Integer id) throws Exception;


    /**
     *  添加一条增加一条
     * @param orderAccount  收款信息
     * @return
     */
    void addGatheringRecord(OrderAccount orderAccount, ServletRequest request) throws  Exception;


    /**
     * 编辑收款订单
     * @param orderAccount
     * @return
     */
    void updateGatheringRecord(ServletRequest request, OrderAcciuntAdd orderAccount) throws Exception;



    /**
     * 确认全部收款完成
     * @return
     */
    void endGatheringRecord(Integer id) throws  Exception;


    /**
     * 收款信息
     * @param id
     * @return
     */
     Order gatheringMessage(Integer id);


    /**
     * 收款管理
     * @param condition
     * @return
     */

  Page<Order> gatheringManage(OrderListCondition condition);

    /**
     * 发货信息查询   (根据订单)
     * @param id
     * @return
     */
    List<OrderAccountDeliver> queryOrderAccountDeliver(Integer id);

    /**
     * 发货信息查询id  逻辑删除
     * @param request
     * @param id
     */
    void delOrderAccountDeliver(ServletRequest request, Integer id) throws Exception;

    /**
     *  添加一条发货信息
     * @param orderAccountDeliver  收款信息
     * @return
     */
    void addOrderAccountDeliver(OrderAccountDeliver orderAccountDeliver, ServletRequest request) throws Exception;

    /**
     * 编辑发货信息
     *
     * @param request
     * @param orderAccount
     */
    void updateOrderAccountDeliver(ServletRequest request, OrderAcciuntAdd orderAccount) throws Exception;
}
