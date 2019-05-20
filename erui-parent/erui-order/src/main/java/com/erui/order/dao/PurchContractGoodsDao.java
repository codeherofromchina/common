package com.erui.order.dao;

import com.erui.order.entity.PurchContractGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.List;

public interface PurchContractGoodsDao extends JpaRepository<PurchContractGoods, Serializable>, JpaSpecificationExecutor<PurchContractGoods> {
    // 给定采购单和父采购商品，查找替换后的采购商品
    PurchContractGoods findByPurchContractIdAndParentId(Integer purchContractId, Integer parentId);

    //根据销售合同号查询采购合同商品
    List<PurchContractGoods> findByContractNo(String contractNo);
}
