package com.erui.order.service;

import com.erui.order.entity.PurchRequisition;
import org.springframework.data.domain.Page;

import java.util.Map;

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

    boolean updatePurchRequisition(PurchRequisition purchRequisition) throws Exception;

    boolean insertPurchRequisition(PurchRequisition purchRequisition);

    /**
     * 查询采购申请列表
     * @param condition
     * { 销售合同号：contractNo,项目号：projectNo,项目名称：projectName,项目开始日期：startDate,下发采购日期：submitDate,
     *      要求采购到货日期：requirePurchaseDate,商务技术经办人：businessName,页码：page,页大小：rows}
     * @return
     */
    Page<Map<String,Object>> listByPage(Map<String, String> condition);
}
