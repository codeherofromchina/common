package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import com.erui.order.requestVo.ProjectListCondition;
import com.erui.order.service.ProjectService;
import com.erui.order.service.ProjectV2Service;
import com.erui.order.util.GoodsUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 项目控制器
 * Created by wangxiaodan on 2017/12/19.
 */
@RestController
@RequestMapping("order/v2/project")
public class ProjectV2Controller {
    private final static Logger logger = LoggerFactory.getLogger(ProjectV2Controller.class);
    @Autowired
    private ProjectV2Service projectV2Service;

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
        Page<Project> projectPage = projectV2Service.findByPage(condition);
        if (projectPage != null) {
            for (Project project : projectPage) {
                project.setGoodsList(null);
                project.setPurchRequisition(null);
                project.setAttachmentList(null);
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
    public Result<Object> handleProject(@RequestBody @Valid Project project, HttpServletRequest request) {
        Project proStatus = projectV2Service.findById(project.getId());
        String errorMsg = null;
        try {

            if (proStatus == null) {
                errorMsg = "项目不存在";
                return new Result<>(ResultStatusEnum.FAIL).setMsg(errorMsg);
            }
            String eruiToken = CookiesUtil.getEruiToken(request);
            ThreadLocalUtil.setObject(eruiToken);

            Order order = proStatus.getOrder();
            if (!"task_pm".equals(order.getAuditingProcess())) {
                // 订单未在项目负责人审批，则项目办理失败
                return new Result<>(ResultStatusEnum.ORDER_AUDIT_NOT_DONE_ERROR);
            }

            if (projectV2Service.updateProject(project)) {
                return new Result<>();
            } else {
                errorMsg = "项目状态错误";
                return new Result<>(ResultStatusEnum.FAIL).setMsg(errorMsg);
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
        Project project = projectV2Service.findDesc(id);
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
        Project project = projectV2Service.findByIdOrOrderId(map.get("id"), map.get("orderId"));
        if (project != null) {
            if (project.getPurchRequisition() != null) {
                project.getPurchRequisition().setGoodsList(null);
                project.setPurchs(null);
            }

            // 按照父子商品排序
            GoodsUtils.sortGoodsByParentAndSon(project.getGoodsList());

            return new Result<>(project);
        }
        return new Result<>(ResultStatusEnum.DATA_NULL);
    }


    private boolean equalsAny(String src, String searchStr) {
        String[] strings = searchStr.split(",");
        for (String search : strings) {
            if (StringUtils.equals(src, search)) {
                return true;
            }
        }
        return false;
    }

    @RequestMapping("listByTeachnalIds")
    public Result<Object> listByProjectForPurch(@RequestBody List<String> projectNos) {
        if (projectNos != null) {
            List<Integer> teachnalIds = projectV2Service.findByProjectNos(projectNos);
            return new Result<>(teachnalIds);
        }
        return new Result<>(ResultStatusEnum.DATA_NULL);
    }



}
