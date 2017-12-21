package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.DeliverConsign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Repository("deliverConsignDao")
public interface DeliverConsignDao extends JpaRepository<DeliverConsign, Serializable> {
    @Query("select dc.id,dc.deptId,dc.coId,dc.writeDate,dc.createUserId,dc.status from DeliverConsign dc where dc.orderId =:orderId order by id asc")
    List<DeliverConsign> findByOrderId(@Param(value = "orderId") Integer orderId);
}
