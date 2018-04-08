package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.order.model.RegionTotalAmount;
import com.erui.order.model.SaleStatistics;
import com.erui.order.service.StatisticsService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/4/2.
 */
@RestController
@RequestMapping("/order/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    // 销售业绩统计
    @RequestMapping("/saleStatistics")
    public Result<Object> saleStatistics(@RequestBody Map<String,String> params) {
        SaleStatistics saleStatistics = new SaleStatistics();
        saleStatistics.setRegion(params.get("region"));
        saleStatistics.setCountry(params.get("country"));
        saleStatistics.setStartDate(DateUtil.str2Date(params.get("startDate")));
        saleStatistics.setEndDate(DateUtil.str2Date(params.get("endDate")));

        // 获取统计数据
        List<SaleStatistics> data = statisticsService.findSaleStatisticsByDate(saleStatistics);
        // 计算各个大区的总金额
        Map<String,RegionTotalAmount> regionTotalAmountMap = new HashedMap();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (SaleStatistics statistics:data) {
            String region = StringUtils.defaultString(statistics.getRegion(),"");
            BigDecimal orderAmount = statistics.getOrderAmount();

            if (regionTotalAmountMap.containsKey(region)) {
                RegionTotalAmount regionTotalAmount = regionTotalAmountMap.get(region);
                regionTotalAmount.setTotalAmount(regionTotalAmount.getTotalAmount().add(orderAmount));
            } else {
                RegionTotalAmount regionTotalAmount = new RegionTotalAmount(region,0);
                regionTotalAmount.setTotalAmount(orderAmount);
                regionTotalAmountMap.put(region,regionTotalAmount);
            }
            totalAmount = totalAmount.add(orderAmount);
        }
        RegionTotalAmount regionTotalAmount = new RegionTotalAmount("总计",0);
        regionTotalAmount.setTotalAmount(totalAmount);

        // 整合统计信息和总计信息
        Map<String,Object> resultData = new HashedMap();
        resultData.put("statistics",data);
        Collection<RegionTotalAmount> values = new ArrayList<>(regionTotalAmountMap.values());
        values.add(regionTotalAmount);
        resultData.put("totalAmount",values);

        return new Result<>(resultData);
    }


}
