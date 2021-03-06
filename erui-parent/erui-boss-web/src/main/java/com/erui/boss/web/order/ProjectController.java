package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import com.erui.order.requestVo.ProjectListCondition;
import com.erui.order.service.ProjectService;
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
        String contractNo = params.get("contractNo");
        String projectName = params.get("projectName");
        // 初始化页码信息
        int pageNum = 1;
        int pageSize = 10;
        String pageNumStr = params.get("pageNum");
        String pageSizeStr = params.get("pageSize");
        if (StringUtils.isNumeric(pageNumStr) && StringUtils.isNumeric(pageSizeStr)) {
            try {
                pageNum = Integer.parseInt(pageNumStr);
                pageSize = Integer.parseInt(pageSizeStr);
            } catch (NumberFormatException ex) {
                logger.error("页面转换错误", ex);
            }
        }

        //List<Project> projectList = null;
        String errMsg = null;
        try {
            //projectList = projectService.purchAbleList(projectNoList, purchaseUid);
            // 分页查询可采购项目
            Page<Map<String, Object>> projectPage = projectService.purchAbleByPage(projectNoList, purchaseUid, pageNum, pageSize, contractNo, projectName);

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
                project.setAttachmentList(null);
            }
            return new Result<>(projectPage);
        }
        return new Result<>(ResultStatusEnum.DATA_NULL);
    }

    /**
     * 审核项目
     * param  params type 审核类型：-1：驳回（驳回必须存在驳回原因参数） 其他或空：正常审核
     * param  params auditingReason 审核或驳回原因参数
     * param  params id 要审核或驳回的项目ID
     * param  params checkLogId 驳回到的流程ID
     *
     * @return
     */
    @RequestMapping(value = "auditProject", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> auditProject(HttpServletRequest request, @RequestBody Project pProject) throws Exception {
        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);
        Integer projectId = pProject.getId(); // 项目ID
        String reason = pProject.getAuditingReason(); // 驳回原因
        String type = pProject.getAuditingType(); // 驳回or审核
        Integer checkLogId = pProject.getCheckLogId();
        // 判断项目是否存在，
        Project project = projectService.findDesc(projectId);
        if (project == null) {
            return new Result<>(ResultStatusEnum.PROJECT_NOT_EXIST);
        }
        // 获取当前登录用户ID并比较是否是当前用户审核
        Object userId = request.getSession().getAttribute("userid");
        Object realname = request.getSession().getAttribute("realname");
        // 判断是否是驳回并判断原因参数
        boolean rejectFlag = "-1".equals(type);
        if (rejectFlag && (StringUtils.isBlank(reason) || checkLogId == null)) {
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR).setMsg("驳回原因和驳回步骤为必填信息");
        }
        // 修改项目的商品风险等级
        if(!StringUtils.isBlank(pProject.getQualityInspectType())){
            // 设置品控负责人
            if (userId != null) {
                if (StringUtils.isNumeric(String.valueOf(userId))) {
                    pProject.setQualityUid(Integer.parseInt(String.valueOf(userId)));
                    pProject.setQualityName((String)realname);
                }
            }
            projectService.updateProjectQualityInspectType(pProject);
            if (StringUtils.isNotBlank(pProject.getTaskId())) {
                return new Result<>();
            }
        }
//        project.setCheckLogId(checkLogId);

        // 判断通过，审核项目并返回是否审核成功
//        boolean flag = projectService.audit(project, String.valueOf(userId), String.valueOf(realname), pProject);
        boolean flag = projectService.audit(projectId, String.valueOf(userId), String.valueOf(realname), pProject);
        if (flag) {
            return new Result<>();
        }
        return new Result<>(ResultStatusEnum.FAIL);
    }

    /**
     * 办理项目
     *
     * @param project
     * @return
     */
    @RequestMapping(value = "handleProject", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> handleProject(@RequestBody @Valid Project project, HttpServletRequest request) {
        Project proStatus = projectService.findById(project.getId());
        String errorMsg = null;
        try {

            if (proStatus == null) {
                errorMsg = "项目不存在";
                return new Result<>(ResultStatusEnum.FAIL).setMsg(errorMsg);
            }
            String eruiToken = CookiesUtil.getEruiToken(request);
            ThreadLocalUtil.setObject(eruiToken);
            Object sessionUserIdObj = request.getSession().getAttribute("userid");
            Integer userId = null;
            if (sessionUserIdObj != null && StringUtils.isNumeric(String.valueOf(sessionUserIdObj))) {
                userId = Integer.parseInt(String.valueOf(sessionUserIdObj));
            }


            // 2019-05-19 订单流程和项目流程合并，取消状态检查
//            // 审核流出添加代码 2018-08-27
//            Order order = proStatus.getOrder();
//            if (order.getAuditingStatus() == null || order.getAuditingStatus() != Order.AuditingStatusEnum.THROUGH.getStatus()) {
//                /// 订单的审核状态未通过，则项目办理失败
//                return new Result<>(ResultStatusEnum.ORDER_AUDIT_NOT_DONE_ERROR);
//            }

            if (projectService.updateProject(project, userId)) {
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
            List<Integer> teachnalIds = projectService.findByProjectNos(projectNos);
            return new Result<>(teachnalIds);
        }
        return new Result<>(ResultStatusEnum.DATA_NULL);
    }
}
