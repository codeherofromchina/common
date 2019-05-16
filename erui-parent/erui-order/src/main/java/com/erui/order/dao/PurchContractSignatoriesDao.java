package com.erui.order.dao;

import com.erui.order.entity.PurchContractSignatories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface PurchContractSignatoriesDao extends JpaRepository<PurchContractSignatories, Serializable>, JpaSpecificationExecutor<PurchContractSignatories> {

}
