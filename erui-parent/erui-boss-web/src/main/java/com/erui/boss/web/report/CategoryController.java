package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.CategoryService;
import com.erui.report.util.AnalyzeTypeEnum;
import com.erui.report.util.ParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 品类数据统计
 */
@RestController
@RequestMapping("/report/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 统计大区、国家的品类询单数量、报价数量、报价金额占比
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/categoryByArea", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Result<Object> selectCategoryNumByArea(@RequestBody(required = true) Map<String, Object> params) {
        //处理参数
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        Map<String, List<Object>> data = null;
        String analyzeType = String.valueOf(params.get("type"));
        String areaBn = String.valueOf(params.get("areaBn"));
        String countryBn = String.valueOf(params.get("countryBn"));
        String startTime = String.valueOf(params.get("startTime"));
        String endTime = String.valueOf(params.get("endTime"));
        if (AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName().equalsIgnoreCase(analyzeType)) { // 询单数量
            // 查询大区或国家的
            data = categoryService.selectCategoryInquiryNumByAreaAndCountryAndTradeTerms(areaBn, countryBn, null, startTime, endTime);
        } else if (AnalyzeTypeEnum.QUOTE_COUNT.getTypeName().equalsIgnoreCase(analyzeType)) {  // 报价数量
            data = categoryService.selectCategoryQuoteNumByAreaAndCountryAndTradeTerms(areaBn, countryBn, null, startTime, endTime);
        } else if (AnalyzeTypeEnum.QUOTE_AMOUNT.getTypeName().equalsIgnoreCase(analyzeType)) { // 报价金额
            data = categoryService.selectCategoryQuoteAmountByAreaAndCountryAndTradeTerms(areaBn, countryBn, null, startTime, endTime);
        }

        if (data == null || data.size() == 0 || ((List) data.get("names")).size() == 0) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        return new Result<>(data);
    }


    /**
     * 统计贸易术语的品类询单数量、报价数量、报价金额占比
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/categoryByTradeTerms", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Result<Object> selectCategoryNumByTradeTerms(@RequestBody(required = true) Map<String, Object> params) {
        //处理参数
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        Map<String, List<Object>> data = null;
        String analyzeType = String.valueOf(params.get("type"));
        String tradeTerms = String.valueOf(params.get("tradeTerms"));
        String startTime = String.valueOf(params.get("startTime"));
        String endTime = String.valueOf(params.get("endTime"));
        if (AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName().equalsIgnoreCase(analyzeType)) { // 询单数量
            // 查询大区或国家的
            data = categoryService.selectCategoryInquiryNumByAreaAndCountryAndTradeTerms(null, null, tradeTerms, startTime, endTime);
        } else if (AnalyzeTypeEnum.QUOTE_COUNT.getTypeName().equalsIgnoreCase(analyzeType)) {  // 报价数量
            data = categoryService.selectCategoryQuoteNumByAreaAndCountryAndTradeTerms(null, null, tradeTerms, startTime, endTime);
        } else if (AnalyzeTypeEnum.QUOTE_AMOUNT.getTypeName().equalsIgnoreCase(analyzeType)) { // 报价金额
            data = categoryService.selectCategoryQuoteAmountByAreaAndCountryAndTradeTerms(null, null, tradeTerms, startTime, endTime);
        }

        if (data == null || data.size() == 0 || ((List) data.get("names")).size() == 0) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        return new Result<>(data);
    }



    /**
     * 国家的询单数量、报价数量、报价金额信息
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/countryInqueryAndQuoteInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Result<Object> countryInqueryAndQuoteInfo(@RequestBody(required = true) Map<String, Object> params) {
        //处理参数
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        Map<String, List<Object>> data = null;
        String analyzeType = String.valueOf(params.get("type"));
        String startTime = String.valueOf(params.get("startTime"));
        String endTime = String.valueOf(params.get("endTime"));
        if (AnalyzeTypeEnum.INQUIRY_COUNT.getTypeName().equalsIgnoreCase(analyzeType)) { // 询单数量
            data = categoryService.selectCountryInqueryCountInfo(startTime, endTime);
        } else if (AnalyzeTypeEnum.QUOTE_COUNT.getTypeName().equalsIgnoreCase(analyzeType)) {  // 报价数量
            data = categoryService.selectCountryQuoteCountInfo(startTime, endTime);
        } else if (AnalyzeTypeEnum.QUOTE_AMOUNT.getTypeName().equalsIgnoreCase(analyzeType)) { // 报价金额
            data = categoryService.selectCountryQuoteAmountInfo(startTime, endTime);
        }
        if (data == null || data.size() == 0 || ((List) data.get("names")).size() == 0) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        return new Result<>(data);
    }

}






