package com.erui.order.service;


import com.erui.order.entity.DeliverDetail;
import com.erui.order.entity.Iogistics;
import com.erui.order.entity.IogisticsData;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface IogisticsDataService {

    Page<IogisticsData> trackingList(IogisticsData iogisticsData);

    IogisticsData iogisticsDataById(Integer id) throws Exception;

    void logisticsActionAddOrSave(IogisticsData iogisticsData);

    IogisticsData queryByTheAwbNo(String theAwbNo);

    void confirmTheGoodsByTheAwbNo(IogisticsData iogisticsData) throws Exception;

   Boolean findStatusAndNumber(Integer orderId);
}
