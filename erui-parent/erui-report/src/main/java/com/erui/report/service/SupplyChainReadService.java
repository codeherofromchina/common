package com.erui.report.service;
import com.erui.report.model.SupplyChainRead;

import java.io.IOException;
import java.util.Date;

public interface SupplyChainReadService {

    void getSupplyChainReadData(String  startTime,String endTime) throws Exception;
    /**
    * 根据时间获取供应链数据（sku\spu\supplier）
     * return SupplyChainRead
    * */
    SupplyChainRead getSupplyChainReadDataByTime(Date startTime,Date endTime);
}
