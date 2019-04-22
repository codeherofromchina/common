package com.erui.order.service;

import com.erui.order.entity.PurchContract;
import org.springframework.data.domain.Page;

public interface PurchContractService {
    /**
     * 根据条件分页查询采购合同信息列表
     *
     * @param condition
     * @return
     */
    Page<PurchContract> findByPage(PurchContract condition);

    /**
     * 修改采购合同
     *
     * @param purchContract 采购合同
     * @return
     */
    boolean update(PurchContract purchContract) throws Exception;

    /**
     * 新增采购合同
     *
     * @param purchContract 采购合同
     * @return
     */
    boolean insert(PurchContract purchContract) throws Exception;

    /**
     * 查询采购合同详情信息
     *
     * @param id 采购合同ID
     * @return
     */
    PurchContract findDetailInfo(Integer id);
}
