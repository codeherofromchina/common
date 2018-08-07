package com.erui.order.service;



import com.erui.order.entity.BackLog;
import org.springframework.data.domain.Page;

public interface BackLogService {

    Page<BackLog> findBackLogByList(BackLog backLog);

    void addBackLogByDelYn(BackLog backLog) throws Exception;

    void updateBackLogByDelYn(BackLog backLog) throws Exception;

}
