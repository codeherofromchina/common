package com.erui.order.service;

import com.erui.order.entity.Area;
import com.erui.order.entity.Instock;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface InstockService {
    /**
     * 根据id查询出库信息
     *
     * @param id
     * @return
     */
    Instock findById(Integer id);

    /**
     * 根据条件分页查询入库数据列表
     *
     * @param condition {"inspectApplyNo":"报检单号","contractNo":"销售合同号","projectNo":"项目号",
     *                  "supplierName":"供应商名称","instockDate":"入库日期","name":"仓库经办人"}
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Map<String, Object>> listByPage(Map<String, String> condition, int pageNum, int pageSize);

    /**
     * 保存/提交入库信息
     * @param instock
     * @return
     */
    boolean save(Instock instock) throws Exception;

    /**
     * 查询入库单详情
     * @param id
     * @return
     */
    Instock detail(Integer id);


    /**
     * 入库详情信息   转交经办人
     *
     * @param instock
     * @return
     */
    boolean instockDeliverAgent(Instock instock) throws Exception;
}
