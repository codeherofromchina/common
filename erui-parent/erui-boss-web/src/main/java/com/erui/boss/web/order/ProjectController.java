package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.Project;
import com.erui.order.requestVo.ProjectListCondition;
import com.erui.order.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目控制器
 * Created by wangxiaodan on 2017/12/19.
 */
@RestController
@RequestMapping("order/project")
public class ProjectController {
    private final static Logger logger = LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    private ProjectService projectService;

    /**
     * 可以采购的项目列表
     *
     * @return
     */
    @RequestMapping(value = "purchAbleList", method = RequestMethod.POST)
    public Result<Object> purchAbleList() {

        List<Project> projectList = projectService.purchAbleList();

        List<Map<String, Object>> data = projectList.stream().map(project -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", project.getId());
            map.put("projectNo", project.getProjectNo());
            map.put("projectName", project.getProjectName());

            return map;
        }).collect(Collectors.toList());

        return new Result<>(data);
    }

    /**
     * 获取项目列表
     *
     * @return
     */
    @RequestMapping(value = "projectManage", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public Result<Object> projectManage(@RequestBody ProjectListCondition condition) {
        Page<Project> projectPage = projectService.findByPage(condition);
        for (Project project:projectPage) {
            project.setOrder(null);
        }
        /*if (projectPage.hasContent()){
            projectPage.getContent().forEach(vo -> {
                vo.setOrder(null);
            });
        }*/
           /* Map<String, Object> projectMap = new HashMap<>();
            projectMap.put("id",vo.getId());
            projectMap.put("contractNo",vo.getContractNo());
            projectMap.put("projectName",vo.getProjectName());
            projectMap.put("execCoId",vo.getExecCoName());
            projectMap.put("startDate",vo.getStartDate());
            projectMap.put("distributionDeptId",vo.getDistributionDeptName());
            projectMap.put("businessUnitId",vo.getBusinessUid());
            projectMap.put("region",vo.getRegion());
            projectMap.put("deliveryDate",vo.getDeliveryDate());
            projectMap.put("exeChgDate",vo.getExeChgDate());
            projectMap.put("requireDurchaseDate",vo.getRequirePurchaseDate());
            projectMap.put("projectStatus",vo.getProjectStatus());
            list.add(projectMap);*/
        return new Result<>(projectPage);
    }
    /**
     * 办理项目
     *
     * @param project
     * @return
     */
    @RequestMapping(value = "handleProject", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> handleProject(@RequestBody Project project) {
        // TODO参数检查略过
        try {
            boolean flag = false;
            flag = projectService.updateProject(project);
            if (flag) {
                return new Result<>();
            }
        } catch (Exception ex) {
            logger.error("办理订单操作失败：{}", project, ex);
        }
        return new Result<>(ResultStatusEnum.FAIL);
    }
    /**
     * 获取项目详情
     *
     * @return
     */
    @RequestMapping(value = "queryProject", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Project> queryProject(@RequestBody Map<String,Integer> map) {
        Project project = projectService.findDesc(map.get("id"));
        return new Result<>(project);
    }
}
