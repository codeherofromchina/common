package com.erui.order.service;

import com.erui.order.entity.Order;
import com.erui.order.entity.OrderAccount;
import org.springframework.data.domain.Page;
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
    void delGatheringRecord(Integer id);


    /**
     *  添加一条增加一条
     * @param orderAccount  收款信息
     * @return
     */
    void addGatheringRecord(OrderAccount orderAccount);


    /**
     * 编辑收款订单
     * @param orderAccount
     * @return
     */
    void updateGatheringRecord(OrderAccount orderAccount);



    /**
     * 确认全部收款完成
     * @return
     */
    void endGatheringRecord(Integer id);


    /**
     * 收款信息
     * @param id
     * @return
     */
     Order gatheringMessage(Integer id);


    /**
     * 收款管理
     * @param order
     * @param page
     * @param rows
     * @return
     */

  Page<Order> gatheringManage(Order order, Integer page, Integer rows);
}
