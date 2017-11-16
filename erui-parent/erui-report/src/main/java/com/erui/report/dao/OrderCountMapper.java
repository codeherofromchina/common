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

}