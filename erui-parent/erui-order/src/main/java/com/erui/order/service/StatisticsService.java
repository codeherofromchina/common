package com.erui.order.service;

import com.erui.order.model.GoodsBookDetail;
import com.erui.order.model.GoodsStatistics;
import com.erui.order.model.ProjectStatistics;
import com.erui.order.model.SaleStatistics;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangxiaodan on 2018/4/2.
 */
public interface StatisticsService {

    /**
     * 查询销售业绩统计信息
     * @param condition {"startDate":"订单签约日期开始时间","endDate":"订单签约日期结束时间","region":"地区","country":"国家"}
     * @return
     */
    List<SaleStatistics> findSaleStatistics(SaleStatistics condition,Set<String> countries);
    HSSFWorkbook generateSaleStatisticsExcel(SaleStatistics condition, Set<String> countries);

    /**
     * 查询商品统计信息
     * @param goodsStatistics
     * @return
     */
    Page<GoodsStatistics> findGoodsStatistics(GoodsStatistics goodsStatistics,Set<String> countries,int pageNum,int pageSize);
    HSSFWorkbook generateGoodsStatisticsExcel(GoodsStatistics goodsStatistics,Set<String> countries);

    /**
     * 查询商品统计信息
     * @param condition
     * @return
     */
    List<ProjectStatistics> findProjectStatistics(Map<String,String> condition);
    Page<ProjectStatistics> findProjectStatisticsByPage(Map<String,String> condition);
    HSSFWorkbook generateProjectStatisticsExcel(Map<String, String> condition);

    /**
     * 查询订单下商品的台账
     * @param orderId
     * @return
     */
    List<GoodsBookDetail> goodsBookDetail(Integer orderId) throws Exception;
    HSSFWorkbook generateGoodsBookDetailExcel(Integer orderId) throws Exception;

    /**
     * 通过编码获取中文国家的映射
     * @return
     */
    Map<String,String> findBnMapZhCountry();

    /**
     * 通过编码获取中文地区的映射
     * @return
     */
    Map<String,String> findBnMapZhRegion();


}
