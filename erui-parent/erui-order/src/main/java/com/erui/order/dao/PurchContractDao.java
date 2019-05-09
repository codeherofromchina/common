package com.erui.order.dao;

import com.erui.order.entity.PurchContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;

public interface PurchContractDao extends JpaRepository<PurchContract, Serializable>, JpaSpecificationExecutor<PurchContract> {
    @Query("select count(t.purchContractNo) from PurchContract t where t.purchContractNo=:purchContractNo")
    Long findCountByPurchContractNo(@Param("purchContractNo") String purchContractNo);

    @Query(value = "SELECT t.purch_contract_no FROM purch_contract t ORDER BY t.id DESC LIMIT 1",nativeQuery=true)
    String findLastedByPurchContractNo();
}
