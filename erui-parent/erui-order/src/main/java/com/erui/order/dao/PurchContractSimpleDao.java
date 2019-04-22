package com.erui.order.dao;

import com.erui.order.entity.PurchContractSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface PurchContractSimpleDao extends JpaRepository<PurchContractSimple, Serializable>, JpaSpecificationExecutor<PurchContractSimple> {

}
