package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.Purch;
import com.erui.order.entity.PurchGoods;
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
public interface PurchDao extends JpaRepository<Purch, Serializable>, JpaSpecificationExecutor<Purch> {
    List<Purch> findByIdIn(Integer[] ids);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Purch findById(Integer id);

    List<Purch> findByPurchNo(String purchNo);

    @Query("select count(t.purchNo) from Purch t where t.purchNo=:purchNo")
    Long findCountByPurchNo(@Param("purchNo") String purchNo);

    @Query(value = "SELECT t.purch_no FROM purch t ORDER BY t.id DESC LIMIT 1", nativeQuery = true)
    String findLastedByPurchNo();
}
