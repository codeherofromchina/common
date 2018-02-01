package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.Project;
import com.erui.order.requestVo.ProjectListCondition;
import com.erui.order.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
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
     * @param params {projectNos:"项目号列表"}
     * @return
     */
    @RequestMapping(value = "purchAbleList", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> purchAbleList(@RequestBody Map<String, String> params) {
        String projectNos = params.get("projectNos");
        List<String> projectNoList = null;
        if (StringUtils.isNotBlank(projectNos)) {
            String[] split = projectNos.split(",");
            projectNoList = Arrays.asList(split);
        }

        String purchaseUid = params.get("purchaseUid");


        List<Project> projectList = projectService.purchAbleList(projectNoList,purchaseUid);

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
    public Result<Object> projectManage(@RequestBody ProjectListCondition condition) {
        int page = condition.getPage();
        if (page < 1) {
            return new Result<>(ResultStatusEnum.PAGE_ERROR);
        }
        Page<Project> projectPage = projectService.findByPage(condition);
        if (projectPage != null) {
            for (Project project : projectPage) {
                project.setGoodsList(null);
                project.setPurchRequisition(null);
            }
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
        Result<Object> result = new Result<>();
        Project proStatus = projectService.findById(project.getId());
        if (StringUtils.equals(proStatus.getProjectStatus(), "SUBMIT")) {
            if (project.getStartDate() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("项目开始日期不能为空");
            } else if (StringUtils.isBlank(project.getProjectName()) || StringUtils.equals(project.getProjectName(), "")) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("项目名称不能为空");
            } else if (project.getDeliveryDate() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("执行单约定交付日期不能为空");
            } else if (project.getDeliveryDate() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("执行单约定交付日期不能为空");
            } else if (project.getProfit() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("利润额不能为空");
            } else if (project.getProfitPercent() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("初步利润不能为空");
            } else if (project.getHasManager() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("有无项目经理不能为空");
            } else if (project.getRequirePurchaseDate() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("要求采购到货日期不能为空");
            } else if (StringUtils.isBlank(project.getProjectStatus()) || StringUtils.equals(project.getProjectStatus(), "")) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("项目状态不能为空");
            }/* else if (project.getManagerUid() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("交付配送中心项目经理不能为空");
            } */else {
                try {
                    boolean flag = false;
                    flag = projectService.updateProject(project);
                    if (flag) {
                        return result;
                    }
                } catch (Exception ex) {
                    logger.error("办理项目操作失败：{}", project, ex);
                    result.setCode(ResultStatusEnum.FAIL.getCode());
                    result.setMsg(ex.getMessage());
                }
            }
        } else if (StringUtils.equals(proStatus.getProjectStatus(), "HASMANAGER") && StringUtils.equals(project.getProjectStatus(), "EXECUTING")) {
            if (project.getStartDate() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("项目开始日期不能为空");
            } else if (StringUtils.isBlank(project.getProjectName()) || StringUtils.equals(project.getProjectName(), "")) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("项目名称不能为空");
            } else if (project.getDeliveryDate() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("执行单约定交付日期不能为空");
            } else if (project.getDeliveryDate() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("执行单约定交付日期不能为空");
            } else if (project.getProfit() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("利润额不能为空");
            } else if (project.getProfitPercent() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("初步利润不能为空");
            } else if (project.getHasManager() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("有无项目经理不能为空");
            } else if (project.getRequirePurchaseDate() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("要求采购到货日期不能为空");
            } else if (StringUtils.isBlank(project.getProjectStatus()) || StringUtils.equals(project.getProjectStatus(), "")) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("项目状态不能为空");
            } else if (project.getPurchaseUid() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("采购经办人不能为空");
            } else if (project.getQualityUid() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("品控经办人不能为空");
            } else if (project.getBusinessUid() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("商务技术经办人不能为空");
            } else if (project.getLogisticsUid() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("国际物流经办人不能为空");
            } else if (project.getWarehouseUid() == null) {
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg("仓库经办人不能为空");
            } else {
                try {
                    boolean flag = false;
                    flag = projectService.updateProject(project);
                    if (flag) {
                        return result;
                    }
                } catch (Exception ex) {
                    logger.error("办理项目操作失败：{}", project, ex);
                    result.setCode(ResultStatusEnum.FAIL.getCode());
                    result.setMsg(ex.getMessage());
                }
            }
        } else {
            try {
                boolean flag = false;
                flag = projectService.updateProject(project);
                if (flag) {
                    return result;
                }
            } catch (Exception ex) {
                logger.error("办理项目操作失败：{}", project, ex);
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg(ex.getMessage());
            }
        }
        return result;

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
        if (map.get("id") == null && map.get("orderId") == null) {
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR);
        }
        Project project = projectService.findByIdOrOrderId(map.get("id"), map.get("orderId"));
        if (project != null) {
            if (project.getPurchRequisition() != null) {
                project.getPurchRequisition().setGoodsList(null);
            }
            return new Result<>(project);
        }
        return new Result<>(ResultStatusEnum.DATA_NULL);
    }
}
