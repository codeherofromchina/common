package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.DeliverNotice;
import com.erui.order.entity.Purch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface DeliverNoticeDao extends JpaRepository<DeliverNotice, Serializable> , JpaSpecificationExecutor<DeliverNotice> {

    @Query(value = "select t.deliver_notice_no from deliver_notice t order by t.deliver_notice_no desc LIMIT 1",nativeQuery = true)
    String findDeliverNoticeNo();
}
