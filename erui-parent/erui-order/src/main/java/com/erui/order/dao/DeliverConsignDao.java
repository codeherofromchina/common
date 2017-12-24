package com.erui.order.dao;

import com.erui.order.entity.DeliverConsign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface DeliverConsignDao extends JpaRepository<DeliverConsign, Serializable>, JpaSpecificationExecutor<DeliverConsign> {
    DeliverConsign findByDeliverConsignNoAndStatus(String deliverNoticeNos, String s);

    List<DeliverConsign> findByIdInAndStatus(Integer[] deliverNoticeNos, Integer s);

    List<DeliverConsign> findByStatusAndDeliverYn(int i, int i1);

    List<DeliverConsign> findByOrderId(Integer orderId);

}