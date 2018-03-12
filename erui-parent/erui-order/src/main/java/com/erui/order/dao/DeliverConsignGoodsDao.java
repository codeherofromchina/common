package com.erui.order.dao;

import com.erui.order.entity.DeliverConsignGoods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface DeliverConsignGoodsDao extends JpaRepository<DeliverConsignGoods, Serializable> {


    Iterable<? extends DeliverConsignGoods> findByGoodsIdAndDeliverDetailId(Integer id, Integer id1);
}
