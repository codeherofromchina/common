package com.erui.order.service;

import com.erui.order.entity.DeliverConsign;
import com.erui.order.entity.DeliverNotice;
import com.erui.order.entity.Order;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface DeliverConsignService {

    /**
     * 查找出口发货通知单
     *
     * @param id
     * @return
     */
    DeliverConsign findById(Integer id) throws Exception;

    /**
     * 根据出口发货通知单 查询信息
     *
     * @param deliverNoticeId 看货通知单号  字符串
     * @return
     */
    List<DeliverConsign> querExitInformMessage(Integer[] deliverNoticeId) throws Exception;


    /**
     * 看货通知管理   查询出口发货通知单
     *
     * @return
     */
    Page<DeliverConsign> queryExitAdvice(DeliverNotice deliverNotice);


    /**
     * 修改出口发货通知单
     *
     * @param deliverConsign
     * @return
     */
    boolean updateDeliverConsign(DeliverConsign deliverConsign) throws Exception;

    /**
     * 新增出口发货通知单
     *
     * @param deliverConsign
     * @return
     */
    boolean addDeliverConsign(DeliverConsign deliverConsign) throws Exception;

    /**
     * 根据订单id查找出口发货通知单
     *
     * @param orderId
     * @return
     */
    List<DeliverConsign> findByOrderId(Integer orderId);


    DeliverConsign queryCreditData(Order order) throws Exception;

    void  buyerCreditPaymentByOrder(Order order ,Integer flag,BigDecimal orderMoney) throws Exception;
}
