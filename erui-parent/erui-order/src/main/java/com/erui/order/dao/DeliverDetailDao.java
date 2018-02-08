package com.erui.order.dao;

import com.erui.order.entity.DeliverDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface DeliverDetailDao extends JpaRepository<DeliverDetail, Serializable>  , JpaSpecificationExecutor<DeliverDetail>{

    DeliverDetail findByDeliverDetailNo(String deliverDetailNo);

    @Query(value = "select t.deliver_detail_no from deliver_detail t order by t.deliver_detail_no desc LIMIT 1",nativeQuery = true)
    String findDeliverDetailNo();
}
