package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface OrderDao extends JpaRepository<Order, Serializable>, JpaSpecificationExecutor<Order> {
    List<Order> findByIdIn(Integer[] ids);

    @Query(value = "select count(t1) from Order t1 where t1.contractNo = :contractNo AND t1.deleteFlag = false")
    Long countByContractNo(@Param("contractNo") String contractNo);

    List<Order> findByDeleteFlag(boolean b);

    @Modifying
    @Query("update Order o set o.status = 4 ,o.processProgress = 9 where o.id in :ids")
    void updateOrderStatus(@Param(value = "ids") List<Integer> ids);

    /**
     * 判断销售合同号是否重复
     *
     * @param contractNo
     * @return Long
     */
    @Query(value = "select t1.id from Order t1 where t1.contractNo= :contractNo")
    List<Integer> findByContractNo(@Param("contractNo") String contractNo);
    /**
     * 查询易瑞订单最近一条销售合同
     *
     * @param
     * @return Long
     */
    @Query(value = "SELECT t1.contract_no from `order` t1 where t1.contract_no LIKE :contractNo% ORDER BY t1.contract_no DESC LIMIT 1",nativeQuery=true)
    String findLastContractNo(@Param("contractNo") String contractNo);

    Order findByContractNoOrId(@Param("contractNo") String contractNo, @Param(value = "id") Integer id);
}
