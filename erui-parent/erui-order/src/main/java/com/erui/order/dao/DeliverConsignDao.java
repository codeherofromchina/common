package com.erui.order.dao;

import com.erui.order.entity.DeliverConsign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface DeliverConsignDao extends JpaRepository<DeliverConsign, Serializable> ,JpaSpecificationExecutor<DeliverConsign> {
    //@Query("select dc.id,dc.deptId,dc.coId,dc.writeDate,dc.createUserId,dc.status from DeliverConsign dc where dc.orderId =:orderId order by dc.id desc ")
    List<DeliverConsign> findByOrderId(@Param(value = "orderId") Integer orderId);

    List<DeliverConsign> findByIdInAndStatus(Integer[] deliverNoticeNos, Integer s);

    List<DeliverConsign> findByIdIn(Integer[] arr);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    DeliverConsign findById(Integer id);

    @Query(value = "SELECT deliver_consign_no FROM deliver_consign ORDER BY id DESC LIMIT 1",nativeQuery=true)
    String findLaseDeliverConsignNo();

}

