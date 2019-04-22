package com.erui.order.dao;

import com.erui.order.entity.PurchContractStandard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface PurchContractStandardDao extends JpaRepository<PurchContractStandard, Serializable>, JpaSpecificationExecutor<PurchContractStandard> {

}
