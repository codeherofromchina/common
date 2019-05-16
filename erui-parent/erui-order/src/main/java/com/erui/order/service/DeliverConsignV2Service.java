package com.erui.order.service;

import com.alibaba.fastjson.JSONObject;
import com.erui.order.entity.DeliverConsign;
import com.erui.order.entity.DeliverNotice;
import com.erui.order.entity.Order;
import com.erui.order.requestVo.DeliverConsignListCondition;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface DeliverConsignV2Service {
    /**
     * 查找出口发货通知单
     *
     * @param id
     * @return
     */
    DeliverConsign findById(Integer id) throws Exception;

    /**
     * 修改出口发货通知单
     *
     * @param deliverConsign
     * @return
     */
    boolean updateDeliverConsign(DeliverConsign deliverConsign) throws Exception;

    /**
     * 新增出口发货通知单
     *
     * @param deliverConsign
     * @return
     */
    boolean addDeliverConsign(DeliverConsign deliverConsign) throws Exception;


    DeliverConsign queryCreditData(Order order) throws Exception;
}
