package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.Instock;
import com.erui.order.entity.Purch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface InstockDao extends JpaRepository<Instock, Serializable>, JpaSpecificationExecutor<Instock> {
    /**
     * 通过采购单查询主报检单列表
     * @param parchId 采购单ID
     * @return
     */
    /**
     * 根据报检单id查询质检单列表
     *
     * @param inspectApplyNos
     * @return
     */
    List<Instock> findByInspectApplyNo(List<String> inspectApplyNos);
}
