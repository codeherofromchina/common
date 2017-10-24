package com.erui.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.erui.report.model.OrderCount;
import com.erui.report.model.OrderCountExample;
import com.erui.report.util.NumSummaryVO;

public interface OrderCountMapper {
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
	NumSummaryVO selectNumSummaryByExample(OrderCountExample example);

}