package com.erui.order.service;

import com.erui.order.entity.Purch;
import com.erui.order.entity.PurchGoods;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface PurchService {
    /**
     * 根据id查询采购基本信息
     * @param id
     * @return
     */
    Purch findBaseInfo(Integer id);

    /**
     * 根据条件分页查询采购信息列表
     * @param condition
     * @return
     */
    Page<Purch> findByPage(Purch condition) ;

    boolean update(Purch purchVo) throws Exception;

    boolean insert(Purch purchVo) throws Exception;

    /**
     * 查询采购详情信息
     * @param id    采购ID
     * @return
     */
    Purch findDetailInfo(Integer id);


    /**
     * 查询采购信息采购中的商品信息
     * @param id    采购ID
     * @return
     */
    Purch findPurchAndGoodsInfo(Integer id);

    /**
     *根据订单id查询(进行中/已完成)采购列表
     * @param orderId
     * @return
     */
    List<Map<String,Object>> listByOrderId(Integer orderId) throws Exception;
}
