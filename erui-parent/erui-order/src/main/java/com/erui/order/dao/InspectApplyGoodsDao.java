package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.InspectApplyGoods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/15.
 */
public interface InspectApplyGoodsDao extends JpaRepository<InspectApplyGoods, Serializable> {
    /**
     * 根据id和商品id还有报检单id查找唯一的报检单商品
     * @param inspectApplyGoodsId
     * @param goodsId
     * @param id
     * @return
     */
    InspectApplyGoods findByIdAndGoodsIdAndInspectApplyId(Integer id,Integer goodsId,Integer inspectApplyGoodsId);

    /**
     * 通过报检单ID查询报检单下的所有商品列表
     * @param InspectApplyId
     * @return
     */
    List<InspectApplyGoods> findByInspectApplyId(Integer InspectApplyId);
}
