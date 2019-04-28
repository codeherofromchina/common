package com.erui.order.service;

import com.erui.order.entity.PurchContract;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface PurchContractService {
    /**
     * 根据条件分页查询采购合同信息列表
     *
     * @param condition
     * @return
     */
    Page<PurchContract> findByPage(PurchContract condition);

    /**
     * 修改采购合同状态
     *
     * @param purchContract 采购合同
     * @return
     */
    boolean updateStatus(PurchContract purchContract);

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

    /**
     * 获取可采购的合同列表
     *
     * @param agentId         采购经办人
     * @param pageNum
     * @param pageSizeString
     * @param purchContractNo 采购合同号
     * @param supplierId      供货商ID
     * @param supplierName    供货商名称
     * @param type            合同类型 1:简易合同 2:标准合同 3:非标合同
     * @param
     * @return
     * @throws Exception
     */
    Page<Map<String, Object>> purchAbleByPage(String agentId, int pageNum, int pageSizeString, String purchContractNo, Integer supplierId, String supplierName, Integer type) throws Exception;

}
