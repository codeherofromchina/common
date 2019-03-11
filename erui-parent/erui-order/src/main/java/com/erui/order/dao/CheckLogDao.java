package com.erui.order.dao;

import com.erui.order.entity.CheckLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

/**
 * @Author:SHIGS
 * @Description 审批流日志表
 * @Date Created in 15:45 2018/8/27
 * @modified By
 */
public interface CheckLogDao extends JpaRepository<CheckLog, Serializable>, JpaSpecificationExecutor<CheckLog> {
    List<CheckLog> findByOrderIdOrderByCreateTimeDesc(Integer orderId);

    List<CheckLog> findByOrderIdAndType(Integer orderId, Integer type);

    List<CheckLog> findByJoinIdAndCategoryOrderByCreateTime(Integer joinId);
}
