package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.Goods;
import com.erui.order.entity.Project;
import com.erui.order.requestVo.ProjectListCondition;
import com.erui.order.service.ProjectService;
import com.erui.order.util.GoodsUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
        // 初始化页码信息
        int pageNum = 1;
        int pageSize = 10;
        String pageNumStr = params.get("pageNum");
        String pageSizeStr = params.get("pageSize");
        if (StringUtils.isNumeric(pageNumStr) && StringUtils.isNumeric(pageSizeStr)) {
            try {
                pageNum = Integer.parseInt(pageNumStr);
                pageSize = Integer.parseInt(pageSizeStr);
            }catch (NumberFormatException ex) {
                logger.error("页面转换错误",ex);
            }
        }

        //List<Project> projectList = null;
        String errMsg = null;
        try {
            //projectList = projectService.purchAbleList(projectNoList, purchaseUid);
            // 分页查询可采购项目
            Page<Map<String,Object>> projectPage = projectService.purchAbleByPage(projectNoList, purchaseUid,pageNum,pageSize);

            return new Result<>(projectPage);
        } catch (Exception e) {
            errMsg = e.getMessage();
            e.printStackTrace();
        }

        return new Result<>(ResultStatusEnum.FAIL).setMsg(errMsg);
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
        String errorMsg = null;
        try {
            if (proStatus != null && projectService.updateProject(project)) {
                return new Result<>();
            } else {
                errorMsg = "项目状态错误";
            }
        } catch (Exception ex) {
            errorMsg = ex.getMessage();
            logger.error("异常错误", ex);
        }
        return new Result<>(ResultStatusEnum.FAIL).setMsg(errorMsg);
    }

    /**
     * 获取项目详情
     *
     * @return
     */
    @RequestMapping(value = "queryProject", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Project> queryProject(@RequestBody Map<String, Integer> map) {
        Integer id = map.get("id");
        if (id == null) {
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR);
        }
        Project project = projectService.findDesc(id);
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

            // 按照父子商品排序
            GoodsUtils.sortGoodsByParentAndSon(project.getGoodsList());

            return new Result<>(project);
        }
        return new Result<>(ResultStatusEnum.DATA_NULL);
    }
}
