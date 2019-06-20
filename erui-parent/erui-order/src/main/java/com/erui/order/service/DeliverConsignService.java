package com.erui.order.service;

import com.alibaba.fastjson.JSONObject;
import com.erui.order.entity.DeliverConsign;
import com.erui.order.entity.DeliverNotice;
import com.erui.order.entity.Order;
import com.erui.order.requestVo.DeliverConsignListCondition;
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
     * 修改出口发货通知单
     *
     * @param deliverConsign
     * @return
     */
    Integer updateDeliverConsign(DeliverConsign deliverConsign) throws Exception;

    /**
     * 新增出口发货通知单
     *
     * @param deliverConsign
     * @return
     */
    Integer addDeliverConsign(DeliverConsign deliverConsign) throws Exception;

    /**
     * 根据订单id查找出口发货通知单
     *
     * @param orderId
     * @return
     */
    List<DeliverConsign> findByOrderId(Integer orderId);

    /**
     * 根据条件分页查询订舱列表
     *
     * @param condition
     * @return
     */
    Page<DeliverConsign> findByPage(DeliverConsignListCondition condition);


    DeliverConsign queryCreditData(Order order) throws Exception;

    JSONObject buyerCreditPaymentByOrder(Order order, Integer flag, BigDecimal orderMoney) throws Exception;

    /**
     * 审核出口发货通知单
     *
     * @param deliverConsign
     * @param auditorId
     * @param auditorName
     * @param rDeliverConsign  请求的参数
     * @return
     */
    boolean audit(DeliverConsign deliverConsign, String auditorId, String auditorName, DeliverConsign rDeliverConsign) throws Exception;


}
