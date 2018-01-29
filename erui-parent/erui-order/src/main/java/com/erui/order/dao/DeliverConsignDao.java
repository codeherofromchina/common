package com.erui.order.dao;

import com.erui.order.entity.DeliverConsign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface DeliverConsignDao extends JpaRepository<DeliverConsign, Serializable> {
    //@Query("select dc.id,dc.deptId,dc.coId,dc.writeDate,dc.createUserId,dc.status from DeliverConsign dc where dc.orderId =:orderId order by dc.id desc ")
    List<DeliverConsign> findByOrderId(@Param(value = "orderId") Integer orderId);

    List<DeliverConsign> findByIdInAndStatus(Integer[] deliverNoticeNos, Integer s);

    List<DeliverConsign> findByIdIn(Integer[] arr);

    @Query(value = "SELECT deliver_consign_no FROM deliver_consign ORDER BY id DESC LIMIT 1",nativeQuery=true)
    String findDeliverConsignNo();

    List<DeliverConsign> findByStatusAndDeliverYnAndCountryAndDeliverConsignNo(int i, int i1, String country, String deliverConsignNo);

    List<DeliverConsign> findByStatusAndDeliverYnAndDeliverConsignNo(int i, int i1, String deliverConsignNo);
}

