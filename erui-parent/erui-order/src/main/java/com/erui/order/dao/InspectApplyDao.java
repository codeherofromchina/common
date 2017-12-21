package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.InspectApply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface InspectApplyDao extends JpaRepository<InspectApply, Serializable> {
    /**
     * 通过采购单查询主报检单列表
     * @param parchId 采购单ID
     * @return
     */
    List<InspectApply> findByPurchIdAndMasterOrderByCreateTimeDesc(Integer parchId,Boolean master);

    /**
     * 获取主报检单的所有子报检单
     * @param id
     * @return
     */
    List<InspectApply> findByParentIdOrderByIdAsc(Integer id);
}
