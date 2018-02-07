package com.erui.order.dao;

import com.erui.order.entity.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface OrderLogDao extends JpaRepository<OrderLog, Serializable>, JpaSpecificationExecutor<OrderLog>  {
    List<OrderLog> findByOrderIdOrderByCreateTimeAsc(Integer orderId);

    OrderLog findByOrderAccountId(Integer id);
   /* List<Order> findByIdIn(Integer[] ids);
    @Query(value = "select count(t1) from Order t1 where t1.contractNo = :contractNo")
    Long countByContractNo(@Param("contractNo") String contractNo);*/
}
