package com.erui.order.dao;

import com.erui.order.entity.DeliverDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface DeliverDetailDao extends JpaRepository<DeliverDetail, Serializable>  , JpaSpecificationExecutor<DeliverDetail>{

    DeliverDetail findByDeliverDetailNo(String deliverDetailNo);
}
