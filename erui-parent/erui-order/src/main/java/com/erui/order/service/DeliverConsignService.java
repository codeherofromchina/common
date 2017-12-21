package com.erui.order.service;

import com.erui.order.entity.DeliverConsign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
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
    DeliverConsign findById(Integer id);

    /**
     * 修改出口发货通知单
     *
     * @param deliverConsign
     * @return
     */
    boolean updateDeliverConsign(DeliverConsign deliverConsign);

    /**
     * 新增出口发货通知单
     *
     * @param deliverConsign
     * @return
     */
    boolean addDeliverConsign(DeliverConsign deliverConsign);
    /**
     * 根据订单id查找出口发货通知单
     *
     * @param orderId
     * @return
     */
    List<DeliverConsign> findByOrderId(Integer orderId);
}
