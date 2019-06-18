package com.erui.order.v2.service.impl;

import com.erui.order.v2.dao.GoodsMapper;
import com.erui.order.v2.model.Goods;
import com.erui.order.v2.model.GoodsExample;
import com.erui.order.v2.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther 王晓丹
 * @Date 2019/5/6 下午7:20
 */
@Service("goodsServiceImplV2")
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 判断订单商品是否已经全部出库
     *
     * @param orderId
     * @return
     */
    @Override
    public boolean isAllOrderGoodsOutstock(Integer orderId) {
        GoodsExample example = new GoodsExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<Goods> goods = goodsMapper.selectByExample(example);
        if (goods != null && goods.size() > 0) {
            return goods.parallelStream().allMatch(vo -> vo.getContractGoodsNum() == vo.getOutstockNum());
        }
        return true;
    }

    @Override
    public List<Goods> findGoodsByOrderId(Integer orderId) {
        GoodsExample example = new GoodsExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<Goods> goods = goodsMapper.selectByExample(example);
        if (goods != null && goods.size() > 0) {
            return goods;
        }
        return null;
    }
}
