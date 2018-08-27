package com.erui.order.dao;

import com.erui.order.entity.CheckLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * @Author:SHIGS
 * @Description 审批流日志表
 * @Date Created in 15:45 2018/8/27
 * @modified By
 */
public interface checkLogDao extends JpaRepository<CheckLog, Serializable> {
}
