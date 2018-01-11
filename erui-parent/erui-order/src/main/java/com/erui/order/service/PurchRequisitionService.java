package com.erui.order.service;

import com.erui.order.entity.Area;
import com.erui.order.entity.PurchRequisition;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface PurchRequisitionService {
    /**
     * 根据id查询采购申请单信息
     * @param id
     * @return
     */
    PurchRequisition findById(Integer id,Integer orderId);

    boolean updatePurchRequisition(PurchRequisition purchRequisition);

    boolean insertPurchRequisition(PurchRequisition purchRequisition);
}
