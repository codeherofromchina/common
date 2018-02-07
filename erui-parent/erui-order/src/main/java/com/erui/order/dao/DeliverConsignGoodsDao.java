package com.erui.order.dao;

import com.erui.order.entity.DeliverConsignGoods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface DeliverConsignGoodsDao extends JpaRepository<DeliverConsignGoods, Serializable> {

}
