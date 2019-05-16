package com.erui.order.dao;

import com.erui.order.entity.PurchRequisition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface PurchRequisitionDao extends JpaRepository<PurchRequisition, Serializable> ,JpaSpecificationExecutor<PurchRequisition> {
    /**
     * 根据采购申请单ID和订单id查找采购申请单详情
     * @param
     * @return
     */
    PurchRequisition findByIdOrOrderId(@Param(value = "id") Integer id, @Param(value = "orderId") Integer orderId);
    @Query(value = "select count(t1) from PurchRequisition t1 where t1.projectNo = :projectNo")
    int countByProjectNo(@Param("projectNo") String projectNo);
}
