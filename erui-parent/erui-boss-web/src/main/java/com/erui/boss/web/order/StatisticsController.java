package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.order.model.*;
import com.erui.order.service.StatisticsService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wangxiaodan on 2018/4/2.
 */
@RestController
@RequestMapping("/order/statistics")
public class StatisticsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private StatisticsService statisticsService;


    // 销售业绩统计
    @RequestMapping("/saleStatistics")
    public Result<Object> saleStatistics(@RequestBody Map<String, String> params) {
        SaleStatistics saleStatistics = new SaleStatistics();
        saleStatistics.setRegion(params.get("region"));
        saleStatistics.setCountry(params.get("country"));
        String countriesStr = params.get("countries");
        Set<String> countries = null;
        if (StringUtils.isNotBlank(countriesStr)) {
            String[] split = countriesStr.split(",");
            countries = new HashSet<>(Arrays.asList(split));
        }
        saleStatistics.setStartDate(DateUtil.str2Date(params.get("startDate")));
        saleStatistics.setEndDate(DateUtil.str2Date(params.get("endDate")));
        Map<String, Object> resultData = new HashedMap();
        // 获取统计数据
        List<SaleStatistics> data = statisticsService.findSaleStatistics(saleStatistics,countries);
        resultData.put("statistics", data);
        // 计算各个大区的总金额
        Map<String, RegionTotalAmount> regionTotalAmountMap = new HashedMap();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (SaleStatistics statistics : data) {
            String region = StringUtils.defaultString(statistics.getRegionZh(), "");
            BigDecimal orderAmount = statistics.getOrderAmount() == null ? BigDecimal.valueOf(0) : statistics.getOrderAmount();
            if (regionTotalAmountMap.containsKey(region)) {
                RegionTotalAmount regionTotalAmount = regionTotalAmountMap.get(region);
                regionTotalAmount.setTotalAmount(regionTotalAmount.getTotalAmount().add(orderAmount));
            } else {
                RegionTotalAmount regionTotalAmount = new RegionTotalAmount(region, 0);
                regionTotalAmount.setTotalAmount(orderAmount);
                regionTotalAmountMap.put(region, regionTotalAmount);
            }
            totalAmount = totalAmount.add(orderAmount);
        }
        // 整合统计信息和总计信息
        Collection<RegionTotalAmount> values = new ArrayList<>(regionTotalAmountMap.values());
        RegionTotalAmount regionTotalAmount = new RegionTotalAmount("总计", 0);
        regionTotalAmount.setTotalAmount(totalAmount);
        values.add(regionTotalAmount);
        resultData.put("totalAmount", values);
        return new Result<>(resultData);
    }


    // 商品（产品）统计信息
    @RequestMapping("/goodsStatistics")
    public Result<Object> goodsStatistics(@RequestBody Map<String,String> params) {
        GoodsStatistics goodsStatistics = new GoodsStatistics();
        goodsStatistics.setRegion(params.get("region"));
        goodsStatistics.setCountry(params.get("country"));
        goodsStatistics.setProType(params.get("proType"));
        goodsStatistics.setSku(params.get("sku"));
        goodsStatistics.setBrand(params.get("brand"));
        goodsStatistics.setStartDate(DateUtil.str2Date(params.get("startDate")));
        goodsStatistics.setEndDate(DateUtil.str2Date(params.get("endDate")));
        String countriesStr = params.get("countries");
        Set<String> countries = null;
        if (StringUtils.isNotBlank(countriesStr)) {
            String[] split = countriesStr.split(",");
            countries = new HashSet<>(Arrays.asList(split));
        }
        int pageNum = 1;
        int pageSize = 50;
        String pageNumStr = params.get("page");
        String pageSizeStr = params.get("rows");
        if (StringUtils.isNumeric(pageNumStr)) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (StringUtils.isNumeric(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if (pageSize < 1) {
            pageSize = 50;
        }
        // 获取统计数据
        Page<GoodsStatistics> data = statisticsService.findGoodsStatistics(goodsStatistics,countries,pageNum,pageSize);
        return new Result<>(data);
    }

    // 项目统计信息
    @RequestMapping("/projectStatistics")
    public Result<Object> projectStatistics(@RequestBody Map<String,String> condition) {
        // 获取统计数据
        Page<ProjectStatistics> data = statisticsService.findProjectStatisticsByPage(condition);
        return new Result<>(data);
    }


    // 商品台账详情
    @RequestMapping("/goodsBookDetail")
    public Result<Object> goodsBookDetail(@RequestBody Map<String,Integer> params) {
        Integer orderId = params.get("orderId");
        if (orderId == null || orderId < 1) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        String errMsg = null;
        try {
            List<GoodsBookDetail> data = statisticsService.goodsBookDetail(orderId);
            return new Result<>(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("异常 ： {}" ,ex);
            errMsg = ex.getMessage();
        }
        return new Result<>(ResultStatusEnum.FAIL).setMsg(errMsg);
    }


    /**
     * 订单主流程监控
     * @param params
     * @return
     */
    @RequestMapping("/orderMainProcess")
    public Result<Object> orderMainProcess(@RequestBody Map<String,String> params) {
        String errMsg = null;
        try {
            Map<String, Object> data =  statisticsService.findOrderMainProcess(params);
            return new Result<>(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("异常 ： {}" ,ex);
            errMsg = ex.getMessage();
        }

        return new Result<>(ResultStatusEnum.FAIL).setMsg(errMsg);
    }


}
