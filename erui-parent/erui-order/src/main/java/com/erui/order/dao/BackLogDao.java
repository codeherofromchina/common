package com.erui.order.dao;

import com.erui.order.entity.BackLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.List;

public interface BackLogDao extends JpaRepository<BackLog, Serializable>,JpaSpecificationExecutor<BackLog> {
    List<BackLog> findByFunctionExplainIdAndHostId(Integer functionExplainId, Integer hostId);
}
