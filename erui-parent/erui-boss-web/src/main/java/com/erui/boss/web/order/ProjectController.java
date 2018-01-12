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
        int page = condition.getPage();
        if (page < 1) {
            return new Result<>(ResultStatusEnum.PAGE_ERROR);
        }
        Page<Project> projectPage = projectService.findByPage(condition);
        for (Project project : projectPage) {
          /*  project.getOrder().setAttachmentSet(null);
            project.getOrder().setOrderPayments(null);
            project.getOrder().setGoodsList(null);*/
            //  project.setPurchRequisition(null);
        }
        if (projectPage != null) {
            return new Result<>(projectPage);
        }
        return new Result<>(ResultStatusEnum.DATA_NULL);
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
    public Result<Project> queryProject(@RequestBody Map<String, Integer> map) {
        if (map.get("id") == null) {
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR);
        }
        Project project = projectService.findDesc(map.get("id"));
        if (project != null) {
            return new Result<>(project);
        }
        return new Result<>(ResultStatusEnum.DATA_NULL);
    }

    /**
     * 根据订单id或者项目id获取项目详情
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "queryByIdOrOrderId", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Project> queryByIdOrOrderId(@RequestBody Map<String, Integer> map) {
        if (map.get("id")==null&&map.get("orderId")==null){
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR);
        }
        Project project = projectService.findByIdOrOrderId(map.get("id"), map.get("orderId"));
        if (project!=null){
            return new Result<>(project);
        }
        return new Result<>(ResultStatusEnum.DATA_NULL);
    }
}
