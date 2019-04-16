package com.erui.order.dao;

import com.erui.order.entity.BackLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.List;

public interface BackLogDao extends JpaRepository<BackLog, Serializable>, JpaSpecificationExecutor<BackLog> {
    BackLog findByFunctionExplainIdAndUid(Integer num, Integer id);

    List<BackLog> findByFunctionExplainIdAndHostIdAndDelYn(Integer functionExplainId, Integer hostId, int i);

    List<BackLog> findByFunctionExplainIdAndFollowIdAndDelYn(Integer functionExplainId, Integer followId, int i);

    List<BackLog> findByFunctionExplainIdAndHostIdAndFollowIdAndUidAndDelYnAndInformTheContent(Integer functionExplainId, Integer hostId, Integer followId, Integer uid, int i, String informTheContent);

    List<BackLog> findByFunctionExplainIdAndHostIdAndUidAndDelYnAndInformTheContent(Integer functionExplainId, Integer hostId, Integer uid, int i, String informTheContent);

    List<BackLog> findByHostIdAndDelYn(Integer hostId, int i);
}
