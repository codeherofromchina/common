package com.erui.order.dao;

import com.erui.order.entity.InspectApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface InspectApplyDao extends JpaRepository<InspectApply, Serializable>,JpaSpecificationExecutor<InspectApply> {
    /**
     * 通过采购单查询主报检单列表
     * @param parchId 采购单ID
     * @return
     */
    List<InspectApply> findByPurchIdAndMasterOrderByCreateTimeAsc(Integer parchId, Boolean master);

    /**
     * 获取主报检单的所有子报检单
     * @param id
     * @return
     */
    List<InspectApply> findByParentIdOrderByIdAsc(Integer id);

    InspectApply findByParentIdAndPubStatusAndStatus(Integer parentId, int pubStatus, int status);

    @Query(value = "select t.inspect_apply_no from inspect_apply t where master = 1 order by t.inspect_apply_no desc LIMIT 1",nativeQuery = true)
    String findLastApplyNo();

    /**
     * 通过报检单号查找报检单
     * @param inspectApplyNo
     * @return
     */
    InspectApply findByInspectApplyNo(String inspectApplyNo);
}
