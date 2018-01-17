package com.erui.report.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.model.CateDetailVo;
import org.apache.ibatis.annotations.Param;

import com.erui.report.model.OrderCount;
import com.erui.report.model.OrderCountExample;
import com.erui.report.util.CustomerNumSummaryVO;

public interface OrderCountMapper {
    Date selectStart();

    Date selectEnd();
    int countByExample(OrderCountExample example);

    int deleteByExample(OrderCountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderCount record);

    int insertSelective(OrderCount record);

    List<OrderCount> selectByExample(OrderCountExample example);

    OrderCount selectByPrimaryKey(Long id);

    Double selectTotalAmountByExample(OrderCountExample example);

    List<Map<String, Object>> selectOrderProTop3(Map<String, Object> params);

    Double selectProfitRateByExample(OrderCountExample example);

    int updateByExampleSelective(@Param("record") OrderCount record, @Param("example") OrderCountExample example);

    int updateByExample(@Param("record") OrderCount record, @Param("example") OrderCountExample example);

    int updateByPrimaryKeySelective(OrderCount record);

    int updateByPrimaryKey(OrderCount record);

    List<CateDetailVo> selecOrdDetailByCategoryByExample(OrderCountExample example);

    /**
     * 查询订单的所有大区和国家列表
     * @return Map<String, String> -> {'area':'大区名称','country':'城市名称'}
     */
	List<Map<String, String>> selectAllAreaAndCountryList();
    /**
     * 查询订单的所有大区和事业部列表
     * @return Map<String, String> -> {'area':'大区名称','country':'事业部名称'}
     */
	List<Map<String, String>> selectAllAreaAndOrgList();

	/**
	 * 查询订单的数量汇总
	 * @param example
	 * @return {total:'总订单数量',oil:'油气订单数量',nonoil:'非油气订单数量'}
	 */
	CustomerNumSummaryVO selectNumSummaryByExample(OrderCountExample example);
    //产品数量
    int selectProCountByExample();
    
    void truncateTable();


    /**
     * 按条件统计事业部的订单数量和金额
     * @param example
     * @return {"totalAmount":'总订单金额',"totalNum":'总订单数量',"organization":'事业部'}
     */
    List<Map<String,Object>> findCountAndAmountByExampleGroupOrigation(OrderCountExample example);

    /**
     * 订单趋势图
     * @param example
     * @return
     */
    List<Map<String,Object>> ordTrendByTime(OrderCountExample example);
    /**
     * 按条件统计区域的订单数量和金额
     * @param example
     * @return {"totalAmount":'总订单金额--BigDecimal',"totalNum":'总订单数量--Long',"area":'区域--String'}
     */
    List<Map<String,Object>> findCountAndAmountByRangProjectStartGroupArea(OrderCountExample example);
    /**
     * 查询油气非油气的复购客户数量
     * @param example
     * @return {"oil":'油气非油气',"custCount":'客户数量'}
     */
    List<Map<String, Object>> selectRePurchaseCustGroupByCustCategory(OrderCountExample example);
    /**
     * 查询油气非油气的客户数量
     * @param example
     * @return {"oil":'油气非油气',"custCount":'客户数量'}
     */
    List<Map<String, Object>> selectCustCountGroupByCustCategory(OrderCountExample example);

    /**
     * 查询客户复购数据明细
     * @param example
     * @return {"custName":'客户名称',"buyCount":'购买次数'}
     */
    List<Map<String, Object>> selectRePurchaseDetail(OrderCountExample example);
    /**
     * 查询各地区的复购客户数量
     * @param example
     * @return {"area":'地区',"custCount":'客户数量'}
     */
    List<Map<String, Object>> selectRePurchaseCustGroupByArea(OrderCountExample example);
    /**
     * 查询各地区的客户数量
     * @param example
     * @return {"area":'地区',"custCount":'客户数量'}
     */
    List<Map<String, Object>> selectCustCountGroupByArea(OrderCountExample example);
    /**
     * 查询各事业部的复购客户数量
     * @param example
     * @return {"org":'事业部',"custCount":'客户数量'}
     */
    List<Map<String, Object>> selectRePurchaseCustGroupByOrg(OrderCountExample example);
    /**
     * 查询各事业部的客户数量
     * @param example
     * @return {"org":'事业部',"custCount":'客户数量'}
     */
    List<Map<String, Object>> selectCustCountGroupByOrg(OrderCountExample example);
    /**
     * 查询各地区的订单数量和金额
     * @param example
     * @return {"area":'地区',"ordCount":'订单数量',"ordAmmount":'订单金额'}
     */
    List<Map<String, Object>> selectDataGroupByArea(OrderCountExample example);
    /**
     * 查询各事业部的订单数量和金额
     * @param example
     * @return {"org":'事业部',"ordCount":'订单数量',"ordAmmount":'订单金额'}
     */
    List<Map<String, Object>> selectDataGroupByOrg(OrderCountExample example);
    /**
     * 查询订单品类明细的数据
     * @param example
     * @return {"itemClass":'事业部',"ordCount":'订单数量',"ordAmmount":'订单金额',"profit":'初步利润率'}
     */
    List<Map<String, Object>> selecOrdDetailGroupByCategory(OrderCountExample example);
}