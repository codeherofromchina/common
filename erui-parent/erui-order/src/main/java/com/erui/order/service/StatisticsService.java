package com.erui.order.service;

import com.erui.order.model.GoodsStatistics;
import com.erui.order.model.SaleStatistics;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/4/2.
 */
public interface StatisticsService {

    /**
     * 查询销售业绩统计信息
     * @param condition {"startDate":"订单签约日期开始时间","endDate":"订单签约日期结束时间","region":"地区","country":"国家"}
     * @return
     */
    public List<SaleStatistics> findSaleStatistics(SaleStatistics condition);

    /**
     * 查询商品统计信息
     * @param goodsStatistics
     * @return
     */
    List<GoodsStatistics> findGoodsStatistics(GoodsStatistics goodsStatistics);
}
