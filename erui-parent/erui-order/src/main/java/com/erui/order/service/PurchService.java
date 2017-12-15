package com.erui.order.service;

import com.erui.order.entity.Goods;
import com.erui.order.entity.Purch;
import com.erui.order.entity.PurchGoods;
import com.erui.order.requestVo.PurchListCondition;
import com.erui.order.requestVo.PurchSaveVo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface PurchService {
    /**
     * 根据id查询采购信息
     * @param id
     * @return
     */
    Purch findById(Integer id);


    /**
     * 根据条件分页查询采购信息列表
     * @param condition
     * @return
     */
    Page<Purch> findByPage(PurchListCondition condition) ;

    boolean update(PurchSaveVo purchSaveVo);

    boolean insert(PurchSaveVo purchSaveVo);

    /**
     * 查询详情页面信息
     * @param id
     * @return
     */
    PurchSaveVo findByIdForDetailPage(Integer id);

    /**
     * 查询采购中可以新增报检单的商品列表
     * @param id
     * @return
     */
    List<PurchGoods> findInspectGoodsByPurch(Integer id);
}
