package com.erui.report.service.impl;

import com.erui.report.dao.CommonMapper;
import com.erui.report.model.BpmTaskRuntime;
import com.erui.report.service.BpmTaskRuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
@Transactional
public class BpmTaskRuntimeServiceImpl implements BpmTaskRuntimeService {
    @Autowired
    private CommonMapper commonMapper;

    @Override
    public void delBpmTaskRuntime(String processInstanceId, String taskId) {
        commonMapper.delBpmTaskRuntimeByPiIdAndTaskId(processInstanceId, taskId);
    }


    @Override
    public void addBpmTaskRuntime(BpmTaskRuntime bpmTaskRuntime) {
        bpmTaskRuntime.setCreatedAt(new Date());
        commonMapper.addBpmTaskRuntime(bpmTaskRuntime);
    }


    @Override
    public List<Long> findBizObjIdList(String actId, String bizType) {
        List<Long> list = commonMapper.findBizObjIdList(actId,bizType);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
