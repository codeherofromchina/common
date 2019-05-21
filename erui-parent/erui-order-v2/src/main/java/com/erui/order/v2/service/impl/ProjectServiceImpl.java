package com.erui.order.v2.service.impl;

import com.erui.order.v2.dao.ProjectMapper;
import com.erui.order.v2.dao.PurchProjectMapper;
import com.erui.order.v2.model.*;
import com.erui.order.v2.service.UserService;
import com.erui.order.v2.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther 王晓丹
 * @Date 2019/4/28 下午2:20
 */
@Service("projectServiceImplV2")
@Transactional
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private PurchProjectMapper purchProjectMapper;
    @Autowired
    private UserService userService;


    public Project findProjectByProcessId(String processId) {
        ProjectExample example = new ProjectExample();
        ProjectExample.Criteria criteria = example.createCriteria();
        criteria.andProcessIdEqualTo(processId);
        List<Project> projects = projectMapper.selectByExample(example);
        if (projects != null && projects.size() > 0) {
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
        String auditingProcess2 = project.getAuditingProcess();
        String audiRemark = project.getAudiRemark();
        if (StringUtils.isNotBlank(auditingProcess2)) {
            if (auditingProcess2.equals(auditingProcess)) {
                auditingProcess2 = "";
            } else {
                auditingProcess2 = auditingProcess2.replace(auditingProcess, "");
                while (auditingProcess2.indexOf(",,") != -1) {
                    auditingProcess2 = auditingProcess2.replace(",,", ",");
                }
                auditingProcess2 = StringUtils.strip(auditingProcess2, ",");
            }
        }
        // 设置审核人
        // 通过工号查找用户ID
        Long userId = userService.findIdByUserNo(assignee);
        if (StringUtils.isNotBlank(audiRemark)) {
            if (userId != null && !audiRemark.contains("," + userId + ",")) {
                audiRemark += "," + userId + ",";
            }
        } else if (userId != null) {
            audiRemark = "," + userId + ",";
        }
        // 更新修正后的状态
        Project projectSelective = new Project();
        projectSelective.setId(project.getId());
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
        // 1:未审核（待审核） 2：审核中  3：驳回  4：审核完成
        int auditingStatus = 2;
        if ("task_pm".equals(auditingProcess)) {
            // 如果是进入到项目负责人审核，需要设置是驳回还是
            auditingStatus = 3; // 正常按照驳回走
            if (Project.ProjectStatusEnum.SUBMIT.getCode().equals(project.getProjectStatus())) {
                // 这里是从上走过来的
                auditingStatus = 1; // 1:未审核（待审核） 2：审核中  3：驳回  4：审核完成
            }
        }

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


    @Override
    public void updateProcessCompleted(String processInstanceId) {
        // 查询项目
        Project project = findProjectByProcessId(processInstanceId);
        if (project == null) {
            return;
        }
        // 更新项目的审核状态、审核进度和当前任务
        Project projectSelective = new Project();
        projectSelective.setId(project.getId());
        projectSelective.setAuditingStatus(4);
        projectSelective.setAuditingProcess("999"); // 999为审核进度审核完成
        projectMapper.updateByPrimaryKeySelective(projectSelective);
    }

    @Override
    public Project findProjectByOrderId(Integer orderId) {
        ProjectExample example = new ProjectExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<Project> projects = projectMapper.selectByExample(example);
        if (projects != null && projects.size() > 0){
            return projects.get(0);
        }
        return null;
    }

    /**
     * 查找所有采购相关的项目列表
     * @param purchId
     * @return
     */
    @Override
    public List<Project> findProjectsByPurchId(Integer purchId) {
        List<Project> projects = null;
        PurchProjectExample purchProjectExample = new PurchProjectExample();
        purchProjectExample.createCriteria().andPurchIdEqualTo(purchId);
        List<PurchProject> purchProjects = purchProjectMapper.selectByExample(purchProjectExample);
        if (purchProjects != null && purchProjects.size() > 0) {
            List<Integer> projectIds = purchProjects.stream().map(vo -> vo.getProjectId()).collect(Collectors.toList());
            ProjectExample projectExample = new ProjectExample();
            projectExample.createCriteria().andIdIn(projectIds);
            projects = projectMapper.selectByExample(projectExample);
        }

        if (projects == null) {
            projects = new ArrayList<>();
        }
        return projects;
    }
}
