package com.erui.report.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 品类dao层接口
 */
public interface CategoryMapper {

    /**
     * 查询品类的询单数量，并按照数量降序排列
     *
     * @param areaBn
     * @param countryBn
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> selectCategoryInquiryNumByAreaAndCountry(@Param("areaBn") String areaBn,
                                                                       @Param("countryBn") String countryBn,
                                                                       @Param("tradeTerms") String tradeTerms,
                                                                       @Param("startTime") String startTime,
                                                                       @Param("endTime") String endTime);


    /**
     * 查询品类的报价数量，并按照数量降序排列
     *
     * @param areaBn
     * @param countryBn
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> selectCategoryQuoteNumByAreaAndCountry(@Param("areaBn") String areaBn,
                                                                       @Param("countryBn") String countryBn,
                                                                       @Param("tradeTerms") String tradeTerms,
                                                                       @Param("startTime") String startTime,
                                                                       @Param("endTime") String endTime);


    /**
     * 查询品类的报价金额，并按照金额降序排列
     *
     * @param areaBn
     * @param countryBn
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> selectCategoryQuoteAmountByAreaAndCountry(@Param("areaBn") String areaBn,
                                                                     @Param("countryBn") String countryBn,
                                                                     @Param("tradeTerms") String tradeTerms,
                                                                     @Param("startTime") String startTime,
                                                                     @Param("endTime") String endTime);
    /**
     * 查询国家的询单数量
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> selectCountryInqueryCountInfo(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 查询国家的报价数量
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> selectCountryQuoteCountInfo(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 查询国家的报价金额
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> selectCountryQuoteAmountInfo(@Param("startTime") String startTime, @Param("endTime") String endTime);
}