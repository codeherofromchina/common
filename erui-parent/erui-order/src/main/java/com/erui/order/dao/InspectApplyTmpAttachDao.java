package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.InspectApplyTmpAttach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface InspectApplyTmpAttachDao extends JpaRepository<InspectApplyTmpAttach, Serializable> {
    @Query("delete from InspectApplyTmpAttach t where t.inspectApplyId = :inspectApplyId")
    void deleteByInspectApplyId(Integer inspectApplyId);

    // 通过报检ID获取重新报检时临时保存的附件信息
    List<InspectApplyTmpAttach> findByInspectApplyId(Integer inspectApplyId);
}
