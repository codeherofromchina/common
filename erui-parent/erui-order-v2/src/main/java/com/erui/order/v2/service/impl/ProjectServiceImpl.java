package com.erui.order.v2.service.impl;

import com.erui.order.v2.dao.ProjectMapper;
import com.erui.order.v2.model.*;
import com.erui.order.v2.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther 王晓丹
 * @Date 2019/4/28 下午2:20
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;


    public Project findProjectByProcessId(String processId) {
        ProjectExample example = new ProjectExample();
        ProjectExample.Criteria criteria = example.createCriteria();
        criteria.andProcessIdEqualTo(processId);
        List<Project> projects = projectMapper.selectByExample(example);
        if (projects != null || projects.size() > 0) {
            return projects.get(0);
        }
        return null;
    }

    @Override
    public void updateAuditProcessDone(String processInstanceId, String auditingProcess, String assignee) {
        // 查询项目
        Project project = findProjectByProcessId(processInstanceId);
        if (project == null) {
            return;
        }
        // 更新审核进度，如果审核进度为空，则更新审核状态为通过
        Integer auditingStatus = project.getAuditingStatus(); // 2:审核中  4：审核完成
        String auditingProcess2 = project.getAuditingProcess();
        String audiRemark = project.getAudiRemark();
        if (StringUtils.isNotBlank(auditingProcess2)) {
            if (auditingProcess2.equals(auditingProcess)) {
                auditingStatus = 4;
            } else {
                auditingProcess2 = auditingProcess2.replace(auditingProcess, "");
                while (auditingProcess2.indexOf(",,") != -1) {
                    auditingProcess2 = auditingProcess2.replace(",,", ",");
                }
                auditingProcess2 = StringUtils.strip(auditingProcess2, ",");
            }
        } else {
            auditingStatus = 4;
        }
        // 设置审核人
        if (StringUtils.isNotBlank(audiRemark)) {
            if (StringUtils.isNotBlank(assignee)) {
                audiRemark += "," + assignee + ",";
            }
        } else if (StringUtils.isNotBlank(assignee)) {
            audiRemark = "," + assignee + ",";
        }
        // 更新修正后的状态
        Project projectSelective = new Project();
        projectSelective.setId(project.getId());
        projectSelective.setAuditingStatus(auditingStatus);
        projectSelective.setAuditingProcess(auditingProcess2);
        projectSelective.setAudiRemark(audiRemark);
        projectMapper.updateByPrimaryKeySelective(projectSelective);
    }

    @Override
    public void updateAuditProcessDoing(String processInstanceId, String auditingProcess, String taskId) {
        // 查询项目
        Project project = findProjectByProcessId(processInstanceId);
        if (project == null) {
            return;
        }
        // 处理订单的审核状态和审核进度
        Integer auditingStatus = 2; // 2:审核中
        String auditingProcess2 = project.getAuditingProcess();
        if (StringUtils.isNotBlank(auditingProcess2)) {
            auditingProcess2 = auditingProcess2 + "," + auditingProcess;
        } else {
            auditingProcess2 = auditingProcess;
        }
        // 更新项目的审核状态、审核进度和当前任务
        Project projectSelective = new Project();
        projectSelective.setId(project.getId());
        projectSelective.setAuditingStatus(auditingStatus);
        projectSelective.setAuditingProcess(auditingProcess2);
        projectSelective.setTaskId(taskId);
        projectMapper.updateByPrimaryKeySelective(projectSelective);
    }


}
