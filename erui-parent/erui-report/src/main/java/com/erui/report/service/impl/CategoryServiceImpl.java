package com.erui.report.service.impl;

import com.erui.report.dao.CategoryMapper;
import com.erui.report.dao.CategoryQualityMapper;
import com.erui.report.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品类业务实现类
 */
@Service
public class CategoryServiceImpl extends BaseService<CategoryQualityMapper> implements CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询品类的询单数量
     * @param areaBn	大区编码
     * @param countryBn	国家编码
     * @param tradeTerms	贸易术语
     * @param startTime	开始时间 格式为：yyyy-MM-dd HH:mm:ss
     * @param endTime	结束时间 格式为：yyyy-MM-dd HH:mm:ss
     * @return    {"names":["品类1","品类2", ... ],"values":[n1,n2, ... ]}}
     */
    @Override
    public Map<String, List<Object>> selectCategoryInquiryNumByAreaAndCountryAndTradeTerms(String areaBn, String countryBn, String tradeTerms, String startTime, String endTime) {
        LOGGER.info("查询品类的询单数量信息 [areaBn:{},countryBn:{},startTime:{},endTime:{}]", areaBn, countryBn, startTime, endTime);
        List<Map<String, Object>> categoryInquiryNumList = categoryMapper.selectCategoryInquiryNumByAreaAndCountry(areaBn, countryBn, tradeTerms, startTime, endTime);
        Map<String, List<Object>> result = handlerDbCategoryResult(categoryInquiryNumList);
        LOGGER.debug("查询品类的询单数量信息结果 [{}]", result);
        return result;
    }



    /**
     * 查询品类的报价数量
     * @param areaBn	大区编码
     * @param countryBn	国家编码
     * @param tradeTerms	贸易术语
     * @param startTime	开始时间 格式为：yyyy-MM-dd HH:mm:ss
     * @param endTime	结束时间 格式为：yyyy-MM-dd HH:mm:ss
     * @return    {"names":["品类1","品类2", ... ],"values":[n1,n2, ... ]}}
     */
    @Override
    public Map<String, List<Object>> selectCategoryQuoteNumByAreaAndCountryAndTradeTerms(String areaBn, String countryBn, String tradeTerms, String startTime, String endTime) {
        LOGGER.info("查询品类的报价数量信息 [areaBn:{},countryBn:{},startTime:{},endTime:{}]", areaBn, countryBn, startTime, endTime);
        List<Map<String, Object>> categoryQuoteNumList = categoryMapper.selectCategoryQuoteNumByAreaAndCountry(areaBn, countryBn, tradeTerms, startTime, endTime);
        Map<String, List<Object>> result = handlerDbCategoryResult(categoryQuoteNumList);
        LOGGER.debug("查询品类的报价数量信息结果 [{}]", result);
        return result;
    }


    /**
     * 查询品类的报价金额
     * @param areaBn	大区编码
     * @param countryBn	国家编码
     * @param tradeTerms	贸易术语
     * @param startTime	开始时间 格式为：yyyy-MM-dd HH:mm:ss
     * @param endTime	结束时间 格式为：yyyy-MM-dd HH:mm:ss
     * @return    {"names":["品类1","品类2", ... ],"values":[n1,n2, ... ]}}
     */
    @Override
    public Map<String, List<Object>> selectCategoryQuoteAmountByAreaAndCountryAndTradeTerms(String areaBn, String countryBn, String tradeTerms, String startTime, String endTime) {
        LOGGER.info("查询品类的报价金额信息 [areaBn:{},countryBn:{},startTime:{},endTime:{}]", areaBn, countryBn, startTime, endTime);
        List<Map<String, Object>> categoryQuoteNumList = categoryMapper.selectCategoryQuoteAmountByAreaAndCountry(areaBn, countryBn, tradeTerms, startTime, endTime);
        Map<String, List<Object>> result = handlerDbCategoryResult(categoryQuoteNumList);
        LOGGER.debug("查询品类的报价金额信息结果 [{}]", result);
        return result;
    }


    /**
     * 直接出来数据库层查询出来的简单品类列表信息
     * @param categoryList
     * @return
     */
    public  Map<String, List<Object>> handlerDbCategoryResult(List<Map<String, Object>> categoryList) {
        Map<String, List<Object>> result = null;
        if (categoryList != null && categoryList.size() > 0) {
            // 声明存放结果的容器
            result = new HashMap<>();
            List<Object> categoryNameList = new ArrayList<>();
            List<Object> categoryValueList  = new ArrayList<>();

            for (Map<String, Object> categoryObj : categoryList) {
                String categoryName = (String) categoryObj.get("category");
                Number value = (Number) categoryObj.get("num");

                if (value instanceof BigDecimal) {
                    BigDecimal oneDouble = new BigDecimal("0.01");
                    BigDecimal bigDecimal = (BigDecimal) value;
                    BigDecimal bigDecimal2 = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
                    if (oneDouble.compareTo(bigDecimal2) == 1) {
                        continue;
                    }
                    value = bigDecimal2;
                }
                categoryNameList.add(categoryName);
                categoryValueList.add(value);
            }
            result.put("names", categoryNameList);
            result.put("values", categoryValueList);
        }
        return result;
    }
}