package com.erui.order.dao;

import com.erui.order.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface GoodsDao extends JpaRepository<Goods, Serializable> {

    List<Goods> findByProjectIdIn(List<Integer> projectIds);

    List<Goods> findByOrderId(Integer orderId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Goods findById(Integer goodsId);

    List<Goods> findByContractNo(@Param("contractNo") String contractNo);

    Goods findByModel(@Param("model") String model);
}
