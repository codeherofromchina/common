package com.erui.report.service;
import com.erui.report.model.SupplyChainRead;
import com.erui.report.model.SupplyTrendVo;

import java.io.IOException;
import java.util.Date;

public interface SupplyChainReadService {

    void supplyChainReadData(String  startTime,String endTime) throws Exception;
    /**
    * 根据时间获取供应链数据（sku\spu\supplier）
     * return SupplyChainRead
    * */
    SupplyChainRead getSupplyChainReadDataByTime(Date startTime,Date endTime);
    /**
     * 根据时间获取（sku\spu\supplier）完成量趋势图数据
     * return SupplyTrendVo
     * */
    SupplyTrendVo supplyTrend(Date startTime,Date endTime);
}
