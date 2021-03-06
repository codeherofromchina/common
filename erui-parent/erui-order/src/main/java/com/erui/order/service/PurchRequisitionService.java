package com.erui.order.service;

import com.erui.order.entity.PurchRequisition;
import org.springframework.data.domain.Page;

import java.util.List;
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
    PurchRequisition findById(Integer id, Integer orderId);

    boolean updatePurchRequisition(PurchRequisition purchRequisition) throws Exception;

    boolean insertPurchRequisition(PurchRequisition purchRequisition) throws Exception;

    /**
     * 查询采购申请列表
     * @param condition
     * { 销售合同号：contractNo,项目号：projectNo,合同标的：projectName,项目开始日期：startDate,下发采购日期：submitDate,
     *      要求采购到货日期：requirePurchaseDate,商务技术经办人：businessName,页码：page,页大小：rows}
     * @return
     */
    Page<Map<String,Object>> listByPage(Map<String, String> condition);
     /**
      * @Author:SHIGS
      * @Description
      * @Date:11:37 2018/8/2
      * @modified By
      */
    int checkProjectNo(String projectNo, Integer id);
    /**
     * 采购单分单采购经办人
     *
     * @param list
     * @return
     * @throws Exception
     */
    boolean updatePurchaseUid(List<PurchRequisition> list) throws Exception;
}
