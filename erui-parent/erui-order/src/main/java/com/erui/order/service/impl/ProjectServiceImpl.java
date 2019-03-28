package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.erui.comm.NewDateUtil;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.event.OrderProgressEvent;
import com.erui.order.event.PurchDoneCheckEvent;
import com.erui.order.event.TasksAddEvent;
import com.erui.order.requestVo.ProjectListCondition;
import com.erui.order.service.*;
import com.erui.order.util.exception.MyException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private static Logger logger = LoggerFactory.getLogger(DeliverDetailServiceImpl.class);

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private BackLogService backLogService;
    @Autowired
    private BackLogDao backLogDao;
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private CheckLogService checkLogService;
    @Autowired
    private CheckLogDao checkLogDao;
    @Autowired
    private ProjectProfitDao projectProfitDao;
    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口
    @Value("#{orderProp[DING_SEND_SMS]}")
    private String dingSendSms;  //发钉钉通知接口

    @Value("#{orderProp[SEND_SMS]}")
    private String sendSms;  //发短信接口
    @Autowired
    private AttachmentDao attachmentDao;
    @Autowired
    AttachmentService attachmentService;

    @Override
    @Transactional(readOnly = true)
    public Project findById(Integer id) {
        Project project = projectDao.findOne(id);
        if (project != null) {
            project.getOrder();
        }
        List<Attachment> orderAttachment = attachmentDao.findByRelObjIdAndCategory(id, Attachment.AttachmentCategory.PROJECT.getCode());
        if (orderAttachment != null && orderAttachment.size() > 0) {
            project.setAttachmentList(orderAttachment);
        }
        project.getAttachmentList().size();
        return project;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Project> findByIds(List<Integer> ids) {
        List<Project> projects = null;
        if (ids != null && ids.size() > 0) {
            projects = projectDao.findByIdIn(ids);
        }
        if (projects == null) {
            projects = new ArrayList<>();
        }
        return projects;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> findByProjectNos(List<String> projectNos) {
        List<Project> projects = null;
        List<Integer> teachnalsList = new ArrayList<>();
        if (projectNos != null && projectNos.size() > 0) {
            projects = projectDao.findByProjectNoIn(projectNos);
        }
        if (projects != null && projects.size() > 0) {
            for (Project p : projects) {
                teachnalsList.add(p.getBusinessUid());
            }
            return teachnalsList;
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateProject(Project project) throws Exception {
        String eruiToken = (String) ThreadLocalUtil.getObject();
        Project projectUpdate = findById(project.getId());
        Order order = projectUpdate.getOrder();
        Project.ProjectStatusEnum nowProjectStatusEnum = Project.ProjectStatusEnum.fromCode(projectUpdate.getProjectStatus());
        Project.ProjectStatusEnum paramProjectStatusEnum = Project.ProjectStatusEnum.fromCode(project.getProjectStatus());
        //项目未执行状态 驳回项目 订单置为待确认状态 删除项目
        if (nowProjectStatusEnum == Project.ProjectStatusEnum.SUBMIT && paramProjectStatusEnum == Project.ProjectStatusEnum.TURNDOWN) {
            //Order order = projectUpdate.getOrder();
            order.setStatus(Order.StatusEnum.INIT.getCode());
            order.setProject(null);
            orderDao.save(order);
            projectDao.delete(projectUpdate.getId());

            //项目驳回，删除   “办理项目”  待办提示
            BackLog backLog = new BackLog();
            backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.TRANSACTIONORDER.getNum());    //功能访问路径标识
            backLog.setHostId(order.getId());
//            backLogService.updateBackLogByDelYn(backLog);

            //项目驳回，删除    如果有项目经理驳回信息删除
            BackLog backLog2 = new BackLog();
            backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.REJECTPROJRCT.getNum());    //功能访问路径标识
            backLog2.setHostId(projectUpdate.getId());
            backLogService.updateBackLogByDelYns(backLog, backLog2);


            //订单中推送“项目驳回”待办信息

            //获取推送人信息
            Set<Integer> userIdS = new HashSet<>();
            Integer agentId = order.getAgentId();   //市场经办人id
            if (agentId != null) {
                userIdS.add(agentId);
            }
            Integer createUserId = order.getCreateUserId();   //创建人id
            if (createUserId != null) {
                userIdS.add(createUserId);
            }
            if (userIdS.size() > 0) {
                for (Integer userId : userIdS) {
                    BackLog newBackLog = new BackLog();
                    newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.REJECTORDER.getMsg());  //功能名称
                    newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.REJECTORDER.getNum());    //功能访问路径标识
                    String contractNo = order.getContractNo();  //销售合同号
                    newBackLog.setReturnNo(contractNo);  // 返回单号
                    String region = order.getRegion();//地区
                    Map<String, String> bnMapZhRegion = statisticsService.findBnMapZhRegion();
                    String country = order.getCountry();//国家
                    Map<String, String> bnMapZhCountry = statisticsService.findBnMapZhCountry();
                    newBackLog.setInformTheContent(bnMapZhRegion.get(region) + " | " + bnMapZhCountry.get(country));  //提示内容
                    newBackLog.setHostId(order.getId());    //父ID，列表页id    订单id
                    newBackLog.setUid(userId);   ////经办人id
                    backLogService.addBackLogByDelYn(newBackLog);
                }
            }

            return true;
        } else {
            if ((new Integer(4).equals(project.getOrderCategory()) || new Integer(3).equals(project.getOverseasSales()))
                    && paramProjectStatusEnum == Project.ProjectStatusEnum.DONE) {
                //Order order = projectUpdate.getOrder();
                //projectUpdate.setProjectStatus(paramProjectStatusEnum.getCode());
                ProjectProfit projectProfit = project.getProjectProfit();
                projectProfit.setProject(project);
                projectProfitDao.save(projectProfit);
                // 处理附件信息 attachmentList 库里存在附件列表 dbAttahmentsMap前端传来参数附件列表
                //projectUpdate.setAttachmentList(project.getAttachmentList());
                List<Attachment> attachmentList = project.getAttachmentList();
                Map<Integer, Attachment> dbAttahmentsMap = projectUpdate.getAttachmentList().parallelStream().collect(Collectors.toMap(Attachment::getId, vo -> vo));
                attachmentService.updateAttachments(attachmentList, dbAttahmentsMap, projectUpdate.getId(), Attachment.AttachmentCategory.PROJECT.getCode());

                project.copyProjectDescTo(projectUpdate);
                //order.setStatus(Order.StatusEnum.DONE.getCode());
                //applicationContext.publishEvent(new OrderProgressEvent(order, 2));

                //现货的情况直接完成 ，删除 “办理项目/驳回”  待办提示
                BackLog backLog = new BackLog();
                backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.TRANSACTIONORDER.getNum());    //功能访问路径标识
                backLog.setHostId(projectUpdate.getId());
                backLogService.updateBackLogByDelYn(backLog);

            } else {

                // 项目一旦执行，则只能修改项目的状态，且状态必须是执行后的状态
                if (nowProjectStatusEnum.getNum() >= Project.ProjectStatusEnum.EXECUTING.getNum()) {
                    if (paramProjectStatusEnum.getNum() < Project.ProjectStatusEnum.EXECUTING.getNum()) {
                        throw new MyException(String.format("%s%s%s", "参数状态错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Parameter state error"));
                    }
                    projectUpdate.setProjectStatus(paramProjectStatusEnum.getCode());
                } else if (nowProjectStatusEnum == Project.ProjectStatusEnum.SUBMIT) {
                    // 之前只保存了项目，则流程可以是提交到项目经理和执行
                    if (paramProjectStatusEnum.getNum() > Project.ProjectStatusEnum.EXECUTING.getNum()) {
                        throw new MyException(String.format("%s%s%s", "参数状态错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Parameter state error"));
                    }
                    ProjectProfit projectProfit = project.getProjectProfit();
                    projectProfit.setProject(project);
                    projectProfitDao.save(projectProfit);
                    project.copyProjectDescTo(projectUpdate);
                    // 处理附件信息 attachmentList 库里存在附件列表 dbAttahmentsMap前端传来参数附件列表
                    //projectUpdate.setAttachmentList(project.getAttachmentList());
                    List<Attachment> attachmentList = project.getAttachmentList();
                    Map<Integer, Attachment> dbAttahmentsMap = projectUpdate.getAttachmentList().parallelStream().collect(Collectors.toMap(Attachment::getId, vo -> vo));
                    attachmentService.updateAttachments(attachmentList, dbAttahmentsMap, projectUpdate.getId(), Attachment.AttachmentCategory.PROJECT.getCode());

                    Integer auditingLevel = project.getAuditingLevel();
                    Integer orderCategory = order.getOrderCategory();
                    if (orderCategory != null && orderCategory == 1) { // 预投
                        auditingLevel = 4; // 四级审核
                    } else if (orderCategory != null && orderCategory == 3) { // 试用
                        auditingLevel = 2; // 二级审核
                    } else if (auditingLevel == null || (auditingLevel < 0 || auditingLevel > 3)) {
                        // 既不是预投。又不是试用，则需要检查参数
                        throw new MyException(String.format("%s%s%s", "参数错误，审批等级参数错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Parameter error, approval level parameter error."));
                    }
                    projectUpdate.setBuVpAuditer(project.getBuVpAuditer());
                    projectUpdate.setBuVpAuditerId(project.getBuVpAuditerId());
                    projectUpdate.setCeo(project.getCeo());
                    projectUpdate.setCeoId(project.getCeoId());
                    projectUpdate.setChairman(project.getChairman());
                    projectUpdate.setChairmanId(project.getChairmanId());
                    projectUpdate.setAuditingLevel(auditingLevel);
                    if (paramProjectStatusEnum == Project.ProjectStatusEnum.HASMANAGER) {
                        // 提交到项目经理，则项目成员不能设置
                        projectUpdate.setPurchaseUid(null);
                        projectUpdate.setQualityName(null);
                        projectUpdate.setQualityUid(null);
                        projectUpdate.setLogisticsUid(null);
                        projectUpdate.setLogisticsName(null);
                        projectUpdate.setWarehouseName(null);
                        projectUpdate.setWarehouseUid(null);
                        projectUpdate.setPurchaseName(null);
                        projectUpdate.setBuAuditerId(project.getBuAuditerId());
                        projectUpdate.setBuAuditer(project.getBuAuditer());
                        projectUpdate.setLogisticsAuditerId(project.getLogisticsAuditerId());
                        projectUpdate.setLogisticsAuditer(project.getLogisticsAuditer());

                        //项目驳回 商务技术经办人办理项目指定项目经理以后  ，删除经办人是商务技术经办人的  执行项目  待办提示
                        BackLog backLog = new BackLog();
                        backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.TRANSACTIONORDER.getNum());    //功能访问路径标识
                        backLog.setHostId(order.getId());
//                        backLogService.updateBackLogByDelYn(backLog);

                        //项目驳回 商务技术经办人办理项目指定项目经理以后    如果有项目经理驳回信息删除
                        BackLog backLog2 = new BackLog();
                        backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.REJECTPROJRCT.getNum());    //功能访问路径标识
                        backLog2.setHostId(projectUpdate.getId());
                        backLogService.updateBackLogByDelYns(backLog, backLog2);


                        //商务技术经办人办理项目指定项目经理以后  需要添加待办事项   执行项目  。提示项目经理办理
                        BackLog newBackLog = new BackLog();
                        newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.EXECUTEPROJECT.getMsg());  //功能名称
                        newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.EXECUTEPROJECT.getNum());    //功能访问路径标识
                        newBackLog.setReturnNo(projectUpdate.getContractNo());  //返回单号    销售合同号
                        String region = projectUpdate.getOrder().getRegion();   //所属地区
                        Map<String, String> bnMapZhRegion = statisticsService.findBnMapZhRegion();
                        String country = projectUpdate.getOrder().getCountry();  //国家
                        Map<String, String> bnMapZhCountry = statisticsService.findBnMapZhCountry();
                        newBackLog.setInformTheContent(bnMapZhRegion.get(region) + " | " + bnMapZhCountry.get(country));  //提示内容
                        newBackLog.setHostId(order.getId());    //父ID，列表页id
                        Integer managerUid = projectUpdate.getManagerUid();//项目经理
                        newBackLog.setUid(managerUid);   //项目经理id
                        backLogService.addBackLogByDelYn(newBackLog);
                        //当参数项目状态为 AUDIT为提交审核状态 状态不入库
                    } else if (Project.ProjectStatusEnum.AUDIT.equals(paramProjectStatusEnum)) {
                        // 无项目经理提交项目时检查审核信息参数 2018-08-28
                        submitProjectProcessCheckAuditParams(project, projectUpdate, order);
                        projectUpdate.getOrder().setAuditingProcess(13);
                        projectUpdate.getOrder().setAuditingUserId("39552");

                    }
                } else if (nowProjectStatusEnum == Project.ProjectStatusEnum.HASMANAGER) {
                    if (paramProjectStatusEnum == Project.ProjectStatusEnum.TURNDOWN) {
                        projectUpdate.setProjectStatus(Project.ProjectStatusEnum.SUBMIT.getCode());
                        projectDao.save(projectUpdate);

                        //项目驳回 商务技术经办人办理项目指定项目经理以后  ，删除经办人是商务技术经办人的  执行项目  待办提示
                        BackLog backLog2 = new BackLog();
                        backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.TRANSACTIONORDER.getNum());    //功能访问路径标识
                        backLog2.setHostId(projectUpdate.getId());
//                        backLogService.updateBackLogByDelYn(backLog2);

                        //项目经理办理的时候 如果是驳回状态  删除 项目经理的  执行项目  待办信息
                        BackLog backLog = new BackLog();
                        backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.EXECUTEPROJECT.getNum());    //功能访问路径标识
                        backLog.setHostId(projectUpdate.getId());
                        backLogService.updateBackLogByDelYns(backLog, backLog2);


                        //项目经理办理的时候 如果是驳回状态  添加 驳回项目 待办事项
                        BackLog newBackLog = new BackLog();
                        newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.REJECTPROJRCT.getMsg());  //功能名称
                        newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.REJECTPROJRCT.getNum());    //功能访问路径标识
                        String contractNo = order.getContractNo();  //销售合同号
                        newBackLog.setReturnNo(contractNo);  //返回单号
                        String region = order.getRegion();//地区
                        Map<String, String> bnMapZhRegion = statisticsService.findBnMapZhRegion();
                        String country = order.getCountry();//国家
                        Map<String, String> bnMapZhCountry = statisticsService.findBnMapZhCountry();
                        newBackLog.setInformTheContent(bnMapZhRegion.get(region) + " | " + bnMapZhCountry.get(country));  //提示内容
                        newBackLog.setHostId(projectUpdate.getId());    //父ID，列表页id    项目id
                        Integer technicalId = order.getTechnicalId();   //商务技术经办人id
                        newBackLog.setUid(technicalId);   ////经办人id
                        backLogService.addBackLogByDelYn(newBackLog);


                        return true;
                    } else {
                        // 交付配送中心项目经理只能保存后者执行
                        if (paramProjectStatusEnum != Project.ProjectStatusEnum.EXECUTING && paramProjectStatusEnum != Project.ProjectStatusEnum.HASMANAGER) {
                            throw new MyException(String.format("%s%s%s", "参数状态错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Parameter state error"));
                        }
                        // 只设置项目成员
                        projectUpdate.setPurchaseUid(project.getPurchaseUid());
                        projectUpdate.setQualityName(project.getQualityName());
                        projectUpdate.setQualityUid(project.getQualityUid());
                        projectUpdate.setLogisticsUid(project.getLogisticsUid());
                        projectUpdate.setLogisticsName(project.getLogisticsName());
                        projectUpdate.setWarehouseName(project.getWarehouseName());
                        projectUpdate.setWarehouseUid(project.getWarehouseUid());
                        projectUpdate.setPurchaseName(project.getPurchaseName());
                        // 修改备注和执行单变更日期
                        projectUpdate.setRemarks(project.getRemarks());
                        projectUpdate.setExeChgDate(project.getExeChgDate());

                        /*//有项目经理提交   10月19日流程修改为先审核再填写项目经理信息
                        if (paramProjectStatusEnum == Project.ProjectStatusEnum.EXECUTING) {
                            // 2018-08-28 审核添加，有项目经理，项目经理需要填写 是否需要物流审核、物流审核人、事业部审核人、审批分级等信息
                            submitProjectProcessCheckAuditParams(project, projectUpdate, order);
                        }*/
                        //项目经理 指定经办人完成以后，  需要让  商务技术执行项目
                        BackLog backLogs = backLogDao.findByFunctionExplainIdAndUid(BackLog.ProjectStatusEnum.EXECUTEPROJECT.getNum(), projectUpdate.getId());
                        if (backLogs != null) {
                            backLogs.setUid(projectUpdate.getBusinessUid()); //商务技术经办人id
                            backLogDao.save(backLogs);
                        }

                    }
                } else {
                    // 其他分支，错误
                    throw new MyException(String.format("%s%s%s", "项目状态数据错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Project status data error"));
                }
                //修改备注  在项目完成前商务技术可以修改项目备注
                if (nowProjectStatusEnum != Project.ProjectStatusEnum.DONE) {
                    projectUpdate.setRemarks(project.getRemarks());
                }
                // 操作相关订单信息
                if (paramProjectStatusEnum == Project.ProjectStatusEnum.EXECUTING && !Project.ProjectStatusEnum.AUDIT.equals(paramProjectStatusEnum)) {
                    if (nowProjectStatusEnum.getNum() < Project.ProjectStatusEnum.EXECUTING.getNum() && paramProjectStatusEnum == Project.ProjectStatusEnum.EXECUTING) {
                        try {
                            order.getGoodsList().forEach(gd -> {
                                        gd.setStartDate(project.getStartDate());
                                        gd.setDeliveryDate(project.getDeliveryDate());
                                        gd.setProjectRequirePurchaseDate(project.getRequirePurchaseDate());
                                        gd.setExeChgDate(project.getExeChgDate());
                                    }
                            );

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    projectUpdate.setProjectStatus(paramProjectStatusEnum.getCode());
                    projectUpdate.setRequirePurchaseDate(project.getRequirePurchaseDate());
                    if (projectUpdate.getExeChgDate() == null) {
                        // 只有为空才能设置，就是只可以设置一次
                        projectUpdate.setExeChgDate(project.getExeChgDate());
                    }
                    order.setStatus(Order.StatusEnum.EXECUTING.getCode());
                    applicationContext.publishEvent(new OrderProgressEvent(order, 2, eruiToken));
                    //现货出库和海外销（当地采购）的单子流程状态改为 已发运
                    applicationContext.publishEvent(new OrderProgressEvent(order, 10, eruiToken));
                    orderDao.save(order);

                    //如果是直接执行项目，删除   “执行项目”  待办提示信息
                    BackLog backLog = new BackLog();
                    backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.EXECUTEPROJECT.getNum());    //功能访问路径标识
                    backLog.setHostId(projectUpdate.getOrder().getId());
//                    backLogService.updateBackLogByDelYn(backLog);

                    //如果项目是提交状态    如果有项目经理驳回信息删除
                    BackLog backLog2 = new BackLog();
                    backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.REJECTPROJRCT.getNum());    //功能访问路径标识
                    backLog2.setHostId(projectUpdate.getOrder().getId());
                    backLogService.updateBackLogByDelYns(backLog, backLog2);

                    //项目状态是提交状态  通知商务技术经办人办理采购申请
                    BackLog newBackLog = new BackLog();
                    newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.PURCHREQUISITION.getMsg());  //功能名称
                    newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.PURCHREQUISITION.getNum());    //功能访问路径标识
                    newBackLog.setReturnNo(projectUpdate.getContractNo());  //返回单号    销售合同号
                    String region = projectUpdate.getOrder().getRegion();   //所属地区
                    Map<String, String> bnMapZhRegion = statisticsService.findBnMapZhRegion();
                    String country = projectUpdate.getOrder().getCountry();  //国家
                    Map<String, String> bnMapZhCountry = statisticsService.findBnMapZhCountry();
                    newBackLog.setInformTheContent(bnMapZhRegion.get(region) + " | " + bnMapZhCountry.get(country));  //提示内容
                    newBackLog.setHostId(order.getId());    //父ID，列表页id
                    newBackLog.setFollowId(projectUpdate.getId());    //子ID，详情中列表页id
                    Integer businessUid = projectUpdate.getBusinessUid(); //商务技术经办人id
                    newBackLog.setUid(businessUid);   ////经办人id
                    backLogService.addBackLogByDelYn(newBackLog);
                }
            }
            projectUpdate.setUpdateTime(new Date());
            Project project1 = projectDao.save(projectUpdate);

            //项目管理：办理项目的时候，如果指定了项目经理，需要短信通知
            if (Project.ProjectStatusEnum.HASMANAGER.getCode().equals(project1.getProjectStatus())) {
                Integer managerUid = project1.getManagerUid();      //交付配送中心项目经理ID
                sendSms(project1, managerUid);
            }

        }

        return true;
    }


    /**
     * 提交项目过程中检查审核相关参数
     */
    private void submitProjectProcessCheckAuditParams(Project project, Project projectUpdate, Order order) throws MyException {
        Integer logisticsAudit = project.getLogisticsAudit();
        Integer logisticsAuditerId = project.getLogisticsAuditerId();
        String logisticsAuditer = project.getLogisticsAuditer();
        String buAuditer = project.getBuAuditer();
        Integer buAuditerId = project.getBuAuditerId();
        Integer auditingLevel = project.getAuditingLevel();
        if (logisticsAudit == null) {
            throw new MyException(String.format("%s%s%s", "参数错误，是否需要物流审核", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Parameter error, do we need logistics audit?"));
        }
        logisticsAudit = logisticsAudit == 2 ? 2 : 1;
        if (logisticsAudit == 2 && (StringUtils.isBlank(logisticsAuditer) || logisticsAuditerId == null)) {
            throw new MyException(String.format("%s%s%s", "参数错误，物流审核人不可为空", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Parameter error, logistics auditor should not be empty."));
        }
        if (buAuditerId == null || StringUtils.isBlank(buAuditer)) {
            throw new MyException(String.format("%s%s%s", "参数错误，事业部审核人不可为空", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Parameter error, business auditor can not be empty."));
        }
        Integer orderCategory = order.getOrderCategory();
        if (orderCategory != null && orderCategory == 1) { // 预投
            auditingLevel = 4; // 四级审核
        } else if (orderCategory != null && orderCategory == 3) { // 试用
            auditingLevel = 2; // 二级审核
        } else if (auditingLevel == null || (auditingLevel < 0 || auditingLevel > 3)) {
            // 既不是预投。又不是试用，则需要检查参数
            throw new MyException(String.format("%s%s%s", "参数错误，审批等级参数错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Parameter error, approval level parameter error."));
        }
        projectUpdate.setBuVpAuditer(project.getBuVpAuditer());
        projectUpdate.setBuVpAuditerId(project.getBuVpAuditerId());
        projectUpdate.setCeo(project.getCeo());
        projectUpdate.setCeoId(project.getCeoId());
        projectUpdate.setChairman(project.getChairman());
        projectUpdate.setChairmanId(project.getChairmanId());
        projectUpdate.setAuditingLevel(auditingLevel);
        /*projectUpdate.setAuditingProcess("2,3"); // 2.法务审核、3.财务审核
        projectUpdate.setAuditingUserId("31025,39552"); // 崔荣光、田万全*/
        projectUpdate.setAuditingProcess("13"); // 3.财务审核
        projectUpdate.setAuditingUserId("39552"); // 田万全
        //sendDingtalk(projectUpdate.getOrder(), "31025", false);
        sendDingtalk(projectUpdate.getOrder(), "39552", false);
        projectUpdate.setAuditingStatus(2); // 审核中

        auditBackLogHandle(projectUpdate, false, "39552");
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Project> findByPage(ProjectListCondition condition) {
        PageRequest pageRequest = new PageRequest(condition.getPage() - 1, condition.getRows(), new Sort(Sort.Direction.DESC, "createTime"));
        Page<Project> pageList = projectDao.findAll(new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> searchList = new ArrayList<>(); // 前端查询条件的AND关系列表
                List<Predicate> backList = new ArrayList<>(); // 隐藏的背后过滤条件AND关系列表
                // 根据销售同号模糊查询
                if (StringUtil.isNotBlank(condition.getContractNo())) {
                    searchList.add(cb.like(root.get("contractNo").as(String.class), "%" + condition.getContractNo() + "%"));
                }
                // 根据审核进度
                if (condition.getAuditingProcess() != null) {
                    searchList.add(cb.equal(root.get("auditingProcess").as(String.class), condition.getAuditingProcess()));
                }
                //根据项目号
                if (StringUtil.isNotBlank(condition.getProjectNo())) {
                    searchList.add(cb.like(root.get("projectNo").as(String.class), "%" + condition.getProjectNo() + "%"));
                }
                //根据项目名称模糊查询
                if (StringUtil.isNotBlank(condition.getProjectName())) {
                    searchList.add(cb.like(root.get("projectName").as(String.class), "%" + condition.getProjectName() + "%"));
                }
                //根据项目开始时间查询
                if (condition.getStartDate() != null) {
                    String startDateStr = condition.getStartDate().toString();
                    String endDateStr = condition.getStartDate().toString();
                    if (StringUtils.isNotBlank(startDateStr)) {
                        Date startT = DateUtil.getOperationTime(condition.getStartDate(), 0, 0, 0);
                        searchList.add(cb.greaterThanOrEqualTo(root.get("startDate").as(Date.class), startT));
                    }
                    if (StringUtils.isNotBlank(endDateStr)) {
                        Date endT = DateUtil.getOperationTime(condition.getStartDate(), 23, 59, 59);
                        searchList.add(cb.lessThan(root.get("startDate").as(Date.class), endT));
                    }
                }
                //根据项目状态
                String[] projectStatus = null;
                if (StringUtil.isNotBlank(condition.getProjectStatus())) {
                    projectStatus = condition.getProjectStatus().split(",");
                    searchList.add(root.get("projectStatus").in(projectStatus));
                }
                //根据流程进度
                if (StringUtil.isNotBlank(condition.getProcessProgress())) {
                    if (StringUtils.equals("1", condition.getProcessProgress())) {
                        searchList.add(cb.equal(root.get("processProgress").as(String.class), condition.getProcessProgress()));
                    } else {
                        searchList.add(cb.greaterThanOrEqualTo(root.get("processProgress").as(String.class), condition.getProcessProgress()));
                    }
                }
                //根据事业部
                if (StringUtil.isNotBlank(condition.getBusinessUnitName())) {
                    searchList.add(cb.like(root.get("businessUnitName").as(String.class), "%" + condition.getBusinessUnitName() + "%"));
                }
                //根据商务技术经办人
                if (condition.getBusinessUid02() != null) {
                    searchList.add(cb.equal(root.get("businessUid").as(Integer.class), condition.getBusinessUid02()));
                }
                //根据项目创建查询 开始时间
                if (condition.getStartTime() != null) {
                    Date startT = DateUtil.getOperationTime(condition.getStartTime(), 0, 0, 0);
                    Predicate startTime = cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), startT);
                    searchList.add(startTime);
                }
                //根据项目创建查询 结束时间
                if (condition.getEndTime() != null) {
                    Date endT = DateUtil.getOperationTime(condition.getEndTime(), 23, 59, 59);
                    Predicate endTime = cb.lessThanOrEqualTo(root.get("createTime").as(Date.class), endT);
                    searchList.add(endTime);
                }


                // 审核状态查询
                if (null != condition.getAuditingStatus()) {
                    backList.add(cb.equal(root.get("auditingStatus").as(Integer.class), condition.getAuditingStatus()));
                }
                //根据执行分公司查询
                if (StringUtil.isNotBlank(condition.getExecCoName())) {
                    backList.add(cb.like(root.get("execCoName").as(String.class), "%" + condition.getExecCoName() + "%"));
                }
                //执行单约定交付日期 NewDateUtil.getDate(condition.getDeliveryDate())
                if (StringUtil.isNotBlank(condition.getDeliveryDate())) {
                    backList.add(cb.like(root.get("deliveryDate").as(String.class), condition.getDeliveryDate()));
                }
                //要求采购到货日期
                if (condition.getRequirePurchaseDate() != null) {
                    backList.add(cb.equal(root.get("requirePurchaseDate").as(Date.class), NewDateUtil.getDate(condition.getRequirePurchaseDate())));
                }
                //执行单变更后日期
                if (condition.getExeChgDate() != null) {
                    backList.add(cb.equal(root.get("exeChgDate").as(Date.class), NewDateUtil.getDate(condition.getExeChgDate())));
                }
                //根据分销部
                if (StringUtil.isNotBlank(condition.getDistributionDeptName())) {
                    backList.add(cb.like(root.get("distributionDeptName").as(String.class), "%" + condition.getDistributionDeptName() + "%"));
                }
                //根据是否已生成出口通知单
                if (condition.getDeliverConsignHas() != null) {
                    backList.add(cb.equal(root.get("deliverConsignHas").as(Integer.class), condition.getDeliverConsignHas()));
                }
                //根据采购经办人   purchaseUid     qualityUid    managerUid logisticsUid warehouseUid
                if (condition.getPurchaseUid() != null) {
                    backList.add(cb.equal(root.get("purchaseUid").as(Integer.class), condition.getPurchaseUid()));
                }
                //根据品控经办人
                if (condition.getQualityUid() != null) {
                    backList.add(cb.equal(root.get("qualityUid").as(Integer.class), condition.getQualityUid()));
                }
                //根据下发部门
                if (StringUtils.isNotBlank(condition.getSendDeptId02())) {
                    backList.add(cb.equal(root.get("sendDeptId").as(String.class), condition.getSendDeptId02()));
                }
                //下发部门
                String[] bid = null;
                if (StringUtils.isNotBlank(condition.getSendDeptId())) {
                    bid = condition.getSendDeptId().split(",");
                }
                Predicate sendDeptId = null;
                if (bid != null) {
                    sendDeptId = root.get("sendDeptId").in(bid);
                }
                Predicate managerUid = null;
                if (condition.getManagerUid() != null) {
                    managerUid = cb.equal(root.get("managerUid").as(Integer.class), condition.getManagerUid());
                }
                Predicate businessUid = null;
                if (condition.getBusinessUid() != null) {
                    businessUid = cb.equal(root.get("businessUid").as(Integer.class), condition.getBusinessUid());
                } else {
                    businessUid = cb.isNull(root.get("businessUid").as(Integer.class));
                }
                //项目经理
                Predicate or = null;
                if (managerUid != null && businessUid != null && sendDeptId != null) {
                    or = cb.or(managerUid, businessUid, sendDeptId);
                } else if (managerUid != null && businessUid != null) {
                    or = cb.or(managerUid, businessUid);
                } else if (managerUid != null && sendDeptId != null) {
                    or = cb.or(managerUid, sendDeptId);
                } else if (businessUid != null && sendDeptId != null) {
                    or = cb.or(businessUid, sendDeptId);
                }
                if (or != null) {
                    backList.add(or);
                } else {
                    if (sendDeptId != null) {
                        backList.add(sendDeptId);
                    } else if (managerUid != null) {
                        backList.add(managerUid);
                    } else if (businessUid != null) {
                        backList.add(businessUid);
                    }
                }
                //根据物流经办人
                if (condition.getLogisticsUid() != null) {
                    backList.add(cb.equal(root.get("logisticsUid").as(Integer.class), condition.getLogisticsUid()));
                }
                //根据仓库经办人
                if (condition.getWarehouseUid() != null) {
                    backList.add(cb.equal(root.get("warehouseUid").as(Integer.class), condition.getWarehouseUid()));
                }
                String[] country = null;
                if (StringUtils.isNotBlank(condition.getCountry())) {
                    country = condition.getCountry().split(",");
                }
                if (country != null) {
                    Join<Project, Order> orderRoot = root.join("order");
                    backList.add(orderRoot.get("country").in(country));
                }
                // 审核人查询,和其他关系是or，所有写在最后
                Predicate[] backPredicates = new Predicate[backList.size()];
                backPredicates = backList.toArray(backPredicates);
                Predicate and = cb.and(backPredicates);
                if (StringUtils.isNotBlank(condition.getAuditingUserId())) {
                    Predicate auditingUserIdP = cb.like(root.get("auditingUserId").as(String.class), "%" + condition.getAuditingUserId() + "%");
                    Predicate or1 = cb.or(and, auditingUserIdP);
                    Predicate auditingUserId02 = cb.like(root.get("audiRemark").as(String.class), "%," + condition.getAuditingUserId() + ",%");
                    searchList.add(cb.or(or1, auditingUserId02));
                } else {
                    searchList.add(and);
                }
                Predicate[] predicates = new Predicate[searchList.size()];
                predicates = searchList.toArray(predicates);
                return cb.and(predicates);
            }
        }, pageRequest);

        for (Project project : pageList) {
            if (project.getOrder() != null) {
                project.setoId(project.getOrder().getId());
                project.setPurchs(null);
            }
        }
        return pageList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> purchAbleList(List<String> projectNoList, String purchaseUid) throws Exception {

        List<Project> list = null;
        if (StringUtils.isBlank(purchaseUid)) {
            list = projectDao.findByPurchReqCreateAndPurchDone(Project.PurchReqCreateEnum.SUBMITED.getCode(), Boolean.FALSE);
        } else {
            if (!StringUtils.isNumeric(purchaseUid)) {
                throw new MyException(String.format("%s%s%s", "采购经办人参数错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Purchasing manager's error in purchasing"));
            }
            list = projectDao.findByPurchReqCreateAndPurchDoneAndPurchaseUid(Project.PurchReqCreateEnum.SUBMITED.getCode(), Boolean.FALSE, Integer.parseInt(purchaseUid));
        }


        if (list == null) {
            list = new ArrayList<>();
        } else {
            // 用程序过滤
            list = list.stream().filter(project -> {
                if (projectNoList != null && projectNoList.size() > 0) {
                    String projectNo = project.getProjectNo();
                    for (String pStr : projectNoList) {
                        if (StringUtils.contains(projectNo, pStr)) {
                            return true;
                        }
                    }
                    return false;
                }
                return true;
            }).filter(project -> {
                List<Goods> goodsList = project.getGoodsList();
                // 存在还可以采购的商品
                return goodsList.parallelStream().anyMatch(goods -> {
                    return goods.getPrePurchsedNum() < goods.getContractGoodsNum();
                });
            }).sorted((o1, o2) -> {
                return o2.getUpdateTime().compareTo(o1.getUpdateTime());
            }).collect(Collectors.toList());

        }
        return list;
    }

    /**
     * 查询分页的可采购项目信息
     *
     * @param projectNoList 项目号列表
     * @param purchaseUid   采购经办人Id
     * @param pageNum       页码
     * @param pageSize      页大小
     * @return
     * @throws Exception
     */
    public Page<Map<String, Object>> purchAbleByPage(List<String> projectNoList, String purchaseUid, int pageNum, int pageSize, String contractNo, String projectName) throws Exception {
        Integer intPurchaseUid = null;
        if (StringUtils.isNotBlank(purchaseUid) && !StringUtils.isNumeric(purchaseUid)) {
            throw new MyException(String.format("%s%s%s", "采购经办人参数错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Purchasing manager's error in purchasing"));
        } else if (StringUtils.isNotBlank(purchaseUid)) {
            intPurchaseUid = Integer.parseInt(purchaseUid);
        }

        List<Integer> projectIds = findAllPurchAbleProjectId(projectNoList, intPurchaseUid);

        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, new Sort(Sort.Direction.DESC, "id"));
        Page<Map<String, Object>> result = null;
        if (projectIds.size() > 0) {
            Page<Project> pageList = projectDao.findAll(new Specification<Project>() {
                @Override
                public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<>();

                    list.add(cb.equal(root.get("purchReqCreate").as(Integer.class), Project.PurchReqCreateEnum.SUBMITED.getCode()));
                    list.add(cb.equal(root.get("purchDone").as(Boolean.class), Boolean.FALSE));
                    if (StringUtils.isNotBlank(purchaseUid)) {
                        list.add(cb.equal(root.get("purchaseUid").as(Integer.class), Integer.parseInt(purchaseUid)));
                    }

                    if (projectNoList != null && projectNoList.size() > 0) {
                        Predicate[] orPredicate = new Predicate[projectNoList.size()];
                        int i = 0;
                        for (String projectNo : projectNoList) {
                            orPredicate[i] = cb.like(root.get("projectNo").as(String.class), "%" + projectNo + "%");
                            i++;
                        }
                        list.add(cb.or(orPredicate));
                    }
                    list.add(cb.like(root.get("contractNo").as(String.class), "%" + contractNo + "%"));
                    list.add(cb.like(root.get("projectName").as(String.class), "%" + projectName + "%"));
                    list.add(root.get("id").in(projectIds.toArray(new Integer[projectIds.size()])));

                    Predicate[] predicates = new Predicate[list.size()];
                    predicates = list.toArray(predicates);
                    return cb.and(predicates);
                }
            }, pageRequest);

            List<Map<String, Object>> list = new ArrayList<>();
            for (Project project : pageList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", project.getId());
                map.put("projectNo", project.getProjectNo());
                map.put("projectName", project.getProjectName());
                map.put("contractNo", project.getContractNo());
                list.add(map);
            }
            result = new PageImpl<Map<String, Object>>(list, pageRequest, pageList.getTotalElements());
        } else {
            result = new PageImpl<Map<String, Object>>(new ArrayList<>(), pageRequest, 0);
        }

        return result;
    }

    /**
     * 查询所有可采购项目的id列表
     *
     * @param projectNoList
     * @param purchaseUid
     * @return
     */
    private List<Integer> findAllPurchAbleProjectId(List<String> projectNoList, Integer purchaseUid) {
        List<Project> list = projectDao.findAll(new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                list.add(cb.equal(root.get("purchReqCreate").as(Integer.class), Project.PurchReqCreateEnum.SUBMITED.getCode()));
                list.add(cb.equal(root.get("purchDone").as(Boolean.class), Boolean.FALSE));
                if (purchaseUid != null) {
                    list.add(cb.equal(root.get("purchaseUid").as(Integer.class), purchaseUid));
                }

                if (projectNoList != null && projectNoList.size() > 0) {
                    Predicate[] orPredicate = new Predicate[projectNoList.size()];
                    int i = 0;
                    for (String projectNo : projectNoList) {
                        orPredicate[i] = cb.like(root.get("projectNo").as(String.class), "%" + projectNo + "%");
                        i++;
                    }
                    list.add(cb.or(orPredicate));
                }

                Join<Project, Goods> goodsJoin = root.join("goodsList");
                list.add(cb.lt(goodsJoin.get("prePurchsedNum").as(Integer.class), goodsJoin.get("contractGoodsNum").as(Integer.class)));

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        });

        return list.parallelStream().map(po -> po.getId()).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    @Override
    public Project findDesc(Integer id) {
        Project project = projectDao.findOne(id);
        if (project != null) {
            project.getOrder().getGoodsList().size();
            List<Goods> goodsList = project.getOrder().getGoodsList();
            if (goodsList.size() > 0) {
                for (Goods goods : goodsList) {
                    goods.setPurchGoods(null);
                }
            }
            List<Attachment> orderAttachment = attachmentDao.findByRelObjIdAndCategory(id, Attachment.AttachmentCategory.PROJECT.getCode());
            if (orderAttachment != null && orderAttachment.size() > 0) {
                project.setAttachmentList(orderAttachment);
            }
            project.getAttachmentList().size();

        }
        return project;
    }

    @Transactional(readOnly = true)
    @Override
    public Project findByIdOrOrderId(Integer id, Integer orderId) {
        Project project = projectDao.findByIdOrOrderId(id, orderId);
        if (project != null) {
            project.setPurchs(null);
            List<Goods> goodsList = project.getGoodsList();
            if (goodsList.size() > 0) {
                for (Goods goods : goodsList) {
                    goods.setPurchGoods(null);
                }
            }
            List<Attachment> orderAttachment = attachmentDao.findByRelObjIdAndCategory(project.getId(), Attachment.AttachmentCategory.PROJECT.getCode());
            if (orderAttachment != null && orderAttachment.size() > 0) {
                project.setAttachmentList(orderAttachment);
            }
            project.getAttachmentList().size();

            return project;
        }
        return null;
    }

    @Override
    public List<Project> findProjectExport(ProjectListCondition condition) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<Project> proList = projectDao.findAll(new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据销售同号模糊查询
                if (StringUtil.isNotBlank(condition.getContractNo())) {
                    list.add(cb.like(root.get("contractNo").as(String.class), "%" + condition.getContractNo() + "%"));
                }
                //根据项目名称模糊查询
                if (StringUtil.isNotBlank(condition.getProjectName())) {
                    list.add(cb.like(root.get("projectName").as(String.class), "%" + condition.getProjectName() + "%"));
                }
                //根据项目开始时间查询
                if (condition.getStartDate() != null) {
                    list.add(cb.equal(root.get("startDate").as(Date.class), NewDateUtil.getDate(condition.getStartDate())));
                }
                //根据执行分公司查询
                if (StringUtil.isNotBlank(condition.getExecCoName())) {
                    list.add(cb.like(root.get("execCoName").as(String.class), "%" + condition.getExecCoName() + "%"));
                }
                //执行单约定交付日期 NewDateUtil.getDate(condition.getDeliveryDate())
                if (StringUtil.isNotBlank(condition.getDeliveryDate())) {
                    list.add(cb.like(root.get("deliveryDate").as(String.class), condition.getDeliveryDate()));
                }
                //要求采购到货日期
                if (condition.getRequirePurchaseDate() != null) {
                    list.add(cb.equal(root.get("requirePurchaseDate").as(Date.class), NewDateUtil.getDate(condition.getRequirePurchaseDate())));
                }
                //执行单变更后日期
                if (condition.getExeChgDate() != null) {
                    list.add(cb.equal(root.get("exeChgDate").as(Date.class), NewDateUtil.getDate(condition.getExeChgDate())));
                }
                //根据事业部
                if (StringUtil.isNotBlank(condition.getBusinessUnitName())) {
                    list.add(cb.like(root.get("businessUnitName").as(String.class), "%" + condition.getBusinessUnitName() + "%"));
                }
                //根据分销部
                if (StringUtil.isNotBlank(condition.getDistributionDeptName())) {
                    list.add(cb.like(root.get("distributionDeptName").as(String.class), "%" + condition.getDistributionDeptName() + "%"));
                }
                String[] projectStatus = null;
                //根据项目状态
                if (StringUtil.isNotBlank(condition.getProjectStatus())) {
                    projectStatus = condition.getProjectStatus().split(",");
                    list.add(root.get("projectStatus").in(projectStatus));
                }
                //根据项目号
                if (StringUtil.isNotBlank(condition.getProjectNo())) {
                    list.add(cb.like(root.get("projectNo").as(String.class), "%" + condition.getProjectNo() + "%"));
                }
                //根据流程进度
                if (StringUtil.isNotBlank(condition.getProcessProgress())) {
                    if (StringUtils.equals("1", condition.getProcessProgress())) {
                        list.add(cb.equal(root.get("processProgress").as(String.class), condition.getProcessProgress()));
                    } else {
                        list.add(cb.greaterThanOrEqualTo(root.get("processProgress").as(String.class), condition.getProcessProgress()));
                    }
                }
                //根据是否已生成出口通知单
                if (condition.getDeliverConsignHas() != null) {
                    list.add(cb.equal(root.get("deliverConsignHas").as(Integer.class), condition.getDeliverConsignHas()));
                }
                //根据采购经办人   purchaseUid     qualityUid    managerUid logisticsUid warehouseUid
                if (condition.getPurchaseUid() != null) {
                    list.add(cb.equal(root.get("purchaseUid").as(Integer.class), condition.getPurchaseUid()));
                }
                //根据品控经办人
                if (condition.getQualityUid() != null) {
                    list.add(cb.equal(root.get("qualityUid").as(Integer.class), condition.getQualityUid()));
                }
                //根据商务技术经办人
                if (condition.getBusinessUid02() != null) {
                    list.add(cb.equal(root.get("businessUid").as(Integer.class), condition.getBusinessUid02()));
                }
                //根据下发部门
                if (StringUtils.isNotBlank(condition.getSendDeptId02())) {
                    list.add(cb.equal(root.get("sendDeptId").as(String.class), condition.getSendDeptId02()));
                }
                //下发部门
                String[] bid = null;
                if (StringUtils.isNotBlank(condition.getSendDeptId())) {
                    bid = condition.getSendDeptId().split(",");
                }
                Predicate sendDeptId = null;
                if (bid != null) {
                    sendDeptId = root.get("sendDeptId").in(bid);
                }
                Predicate managerUid = null;
                if (condition.getManagerUid() != null) {
                    managerUid = cb.equal(root.get("managerUid").as(Integer.class), condition.getManagerUid());
                }
                Predicate businessUid = null;
                if (condition.getBusinessUid() != null) {
                    businessUid = cb.equal(root.get("businessUid").as(Integer.class), condition.getBusinessUid());
                }
                //项目经理
                Predicate or = null;
                if (managerUid != null && businessUid != null && sendDeptId != null) {
                    or = cb.or(managerUid, businessUid, sendDeptId);
                } else if (managerUid != null && businessUid != null) {
                    or = cb.or(managerUid, businessUid);
                } else if (managerUid != null && sendDeptId != null) {
                    or = cb.or(managerUid, sendDeptId);
                } else if (businessUid != null && sendDeptId != null) {
                    or = cb.or(businessUid, sendDeptId);
                }
                if (or != null) {
                    list.add(or);
                } else {
                    if (sendDeptId != null) {
                        list.add(sendDeptId);
                    } else if (managerUid != null) {
                        list.add(managerUid);
                    } else if (businessUid != null) {
                        list.add(businessUid);
                    }
                }
                //根据物流经办人
                if (condition.getLogisticsUid() != null) {
                    list.add(cb.equal(root.get("logisticsUid").as(Integer.class), condition.getLogisticsUid()));
                }
                //根据仓库经办人
                if (condition.getWarehouseUid() != null) {
                    list.add(cb.equal(root.get("warehouseUid").as(Integer.class), condition.getWarehouseUid()));
                }
                //根据项目创建查询 开始时间
                if (condition.getStartTime() != null) {
                    Date startT = DateUtil.getOperationTime(condition.getStartTime(), 0, 0, 0);
                    Predicate startTime = cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), startT);
                    list.add(startTime);
                }
                //根据项目创建查询 结束时间
                if (condition.getEndTime() != null) {
                    Date endT = DateUtil.getOperationTime(condition.getEndTime(), 23, 59, 59);
                    Predicate endTime = cb.lessThanOrEqualTo(root.get("createTime").as(Date.class), endT);
                    list.add(endTime);
                }
                String[] country = null;
                if (StringUtils.isNotBlank(condition.getCountry())) {
                    country = condition.getCountry().split(",");
                }
                if (country != null) {
                    Join<Project, Order> orderRoot = root.join("order");
                    list.add(orderRoot.get("country").in(country));
                }
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, sort);
        List<Project> proListRemove = new ArrayList<Project>();
        for (Project project : proList) {
            if (project.getOrder() != null) {
                try {
                    project.setoId(project.getOrder().getId());
                }catch (Exception e){
                    proListRemove.add(project);
                }
            }
        }
        if(proListRemove != null && proListRemove.size() > 0)
            proList.removeAll(proListRemove);
        return proList;
    }


    //出库短信通知
    public void sendSms(Project project, Integer managerUid) throws Exception {
        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (StringUtils.isNotBlank(eruiToken)) {
            try {
                //请求头信息
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");

                // 根据id获取人员信息
                String jsonParam = "{\"id\":\"" + managerUid + "\"}";   //交付配送中心项目经理ID
                String s = HttpRequest.sendPost(memberInformation, jsonParam, header);
                logger.info("人员详情返回信息：" + s);
                JSONObject jsonObject = JSONObject.parseObject(s);
                Integer code = jsonObject.getInteger("code");
                if (code == 1) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    //发送短信
                    Map<String, String> map = new HashMap();
                    map.put("areaCode", "86");
                    map.put("to", "[\"" + data.getString("mobile") + "\"]");    //项目经理手机号
                    map.put("content", "您好，项目名称：" + project.getProjectName() + "，商务技术经办人：" + project.getBusinessName() + "，已申请项目执行，请及时处理。感谢您对我们的支持与信任！");
                    map.put("subType", "0");
                    map.put("groupSending", "0");
                    map.put("useType", "订单");
                    String s1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                    logger.info("发送短信返回状态" + s1);
                }
            } catch (Exception e) {
                throw new MyException(String.format("%s%s%s", "发送短信失败", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Failure to send SMS"));
            }

        }
    }

    //钉钉通知 审批人
    private void sendDingtalk(Order order, String user, boolean rejectFlag) {
        //获取token
        final String eruiToken = (String) ThreadLocalUtil.getObject();
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("发送短信的用户token:" + eruiToken);
                //if (StringUtils.isNotBlank(eruiToken)) {
                //try {
                // 根据id获取商务经办人信息
                String jsonParam = "{\"id\":\"" + user + "\"}";
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                String userInfo = HttpRequest.sendPost(memberInformation, jsonParam, header);
                logger.info("人员详情返回信息：" + userInfo);
                //钉钉通知接口头信息
                Map<String, String> header2 = new HashMap<>();
                header2.put("Cookie", eruiToken);
                header2.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                JSONObject jsonObject = JSONObject.parseObject(userInfo);
                Integer code = jsonObject.getInteger("code");
                String userNo = null;
                String userName = null;  //商务经办人手机号
                if (code == 1) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    //获取通知者姓名员工编号
                    //userName = data.getString("name");
                    userNo = data.getString("user_no");
                    Long startTime = System.currentTimeMillis();
                    Date sendTime = new Date(startTime);
                    String sendTime02 = DateUtil.format(DateUtil.FULL_FORMAT_STR, sendTime);
                    //发送钉钉通知
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("toUser=").append(userNo);
                    if (!rejectFlag) {
                        stringBuffer.append("&message=您好！" + order.getProject().getBusinessName() + "的项目，已申请项目审批。项目名称：" + order.getProject().getProjectName() + "，请您登录BOSS系统及时处理。感谢您对我们的支持与信任！" +
                                "" + sendTime02 + "");
                    } else {
                        stringBuffer.append("&message=您好！" + order.getProject().getBusinessName() + "的项目，已申请的项目审核未通过。项目名称：" + order.getProject().getProjectName() + "，请您登录BOSS系统及时处理。感谢您对我们的支持与信任！" +
                                "" + sendTime02 + "");
                    }
                    stringBuffer.append("&type=userNo");
                    String s1 = HttpRequest.sendPost(dingSendSms, stringBuffer.toString(), header2);
                    Long endTime = System.currentTimeMillis();
                    System.out.println("发送通知耗费时间：" + (endTime - startTime) / 1000);
                    logger.info("发送钉钉通知返回状态" + s1);
                }
                // }
            }
        }).start();

    }

    /**
     * 审核项目操作
     *
     * @param project
     * @param auditorId
     * @param paramProject 参数项目
     * @return
     */
    @Transactional
    @Override
    public boolean audit(Project project, String auditorId, String auditorName, Project paramProject) {
        //@param rejectFlag true:驳回项目   false:审核项目
        //@param reason
        StringBuilder auditorIds = null;
        if (project.getAudiRemark() != null) {
            auditorIds = new StringBuilder(project.getAudiRemark());
        } else {
            auditorIds = new StringBuilder("");
        }
        boolean rejectFlag = "-1".equals(paramProject.getAuditingType());
        String reason = paramProject.getAuditingReason();

        Order order = project.getOrder();
        // 获取当前审核进度
        String auditingProcess = project.getAuditingProcess();
        String auditingUserId = project.getAuditingUserId();
        Integer curAuditProcess = null;
        if (auditorId.equals(auditingUserId)) {
            curAuditProcess = Integer.parseInt(auditingProcess);
        } else {
            String[] split = auditingUserId.split(",");
            String[] split1 = auditingProcess.split(",");
            for (int n = 0; n < split.length; n++) {
                if (auditorId.equals(split[n])) {
                    curAuditProcess = Integer.parseInt(split1[n]);
                    break;
                }
            }
        }
        if (curAuditProcess == null) {
            return false;
        }
        auditorIds.append("," + auditorId + ",");

        // 定义最后处理结果变量，最后统一操作
        Integer auditingStatus_i = 2; // 默认状态为审核中
        String auditingProcess_i = null; // 项目审核当前进度
        String auditingUserId_i = null; // 项目审核当前人
        CheckLog checkLog_i = null; // 审核日志
        if (rejectFlag) { // 如果是驳回，则直接记录日志，修改审核进度
            CheckLog checkLog = checkLogDao.findOne(paramProject.getCheckLogId());
            auditingStatus_i = 3;
            if (checkLog.getType() == 1) { // 驳回到订单
                Integer auditingProcess_order = checkLog.getAuditingProcess(); //驳回给哪一步骤
                String auditingUserId_order = String.valueOf(checkLog.getAuditingUserId()); //要驳回给谁
                if (auditingProcess_order != null && auditingProcess_order == 0) {
                    project.getOrder().setStatus(1);
                }
                project.getOrder().setAuditingUserId(auditingUserId_order);
                project.getOrder().setAuditingStatus(auditingStatus_i);
                project.getOrder().setAuditingProcess(auditingProcess_order);
                project.setAuditingStatus(0);

                // 推送待办事件
                String infoContent = String.format("%s | %s", project.getOrder().getRegion(), project.getOrder().getCountry());
                String crmCode = project.getOrder().getCrmCode();
                BackLog.ProjectStatusEnum pse = null;
                if (auditingProcess_order != null && auditingProcess_order == 0) {
                    pse = BackLog.ProjectStatusEnum.ORDER_REJECT2;
                } else if (auditingProcess_order != null && auditingProcess_order == 6) {
                    pse = BackLog.ProjectStatusEnum.ORDER_REJECT3;
                } else {
                    pse = BackLog.ProjectStatusEnum.ORDER_REJECT;
                }
                applicationContext.publishEvent(new TasksAddEvent(applicationContext, backLogService,
                        pse, crmCode, infoContent,
                        project.getOrder().getId(),
                        Integer.parseInt(auditingUserId_order)));

            } else { // 驳回到项目
                auditingProcess_i = checkLog.getAuditingProcess().toString(); // 事业部利润核算 处理
                auditingUserId_i = String.valueOf(checkLog.getAuditingUserId()); // 要驳回给谁
                project.getOrder().setAuditingProcess(Integer.parseInt(auditingProcess_i));
                project.getOrder().setAuditingUserId(auditingUserId_i);
                // 设置项目为SUBMIT:未执行
                project.setProjectStatus("SUBMIT");
            }
            // 驳回的日志记录的下一处理流程和节点是当前要处理的节点信息
            checkLog_i = fullCheckLogInfo(order.getId(), curAuditProcess, Integer.parseInt(auditorId), auditorName, project.getAuditingProcess(), project.getAuditingUserId(), reason, "-1", 2);
        } else {
            Integer auditingLevel = project.getAuditingLevel();
            Integer logistics_audit = project.getLogisticsAudit();
            // 处理进度
            CheckLog checkLog = checkLogService.findLastLog(2, order.getId());
            //if (checkLog != null && "-1".equals(checkLog.getOperation())) { // 驳回后的处理
            if (false) { // 驳回后的处理
                auditingProcess_i = checkLog.getNextAuditingProcess();
                auditingUserId_i = checkLog.getNextAuditingUserId();
                // 驳回后的修改
                paramProject.copyProjectDescTo(project); // 只修改基本信息
                paramProject.setProjectStatus("SUBMIT"); // 驳回处理后设置状态为执行中

            } else {
                switch (curAuditProcess) {
                    case 11: // 事业部利润核算
                        throw new MyException(String.format("%s%s%s", "审核流程错误，无事业部利润核算审核", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Audit process error, no profit accounting audit."));
                    case 12: // 法务审核
                        /*String replace = auditingUserId.replace("31025", "");

                        if ("".equals(replace)) { // 跟他并行审核的都已经审核完成
                            if (logistics_audit != null && logistics_audit == 2) { // 需要物流审核
                                auditingProcess_i = "5"; //
                                auditingUserId_i = String.valueOf(project.getLogisticsAuditerId()); //
                            } else {
                                // 直接事业部审核
                                auditingProcess_i = "6"; //
                                auditingUserId_i = String.valueOf(project.getBuAuditerId()); //
                            }
                        } else {
                            auditingProcess_i = StringUtils.strip(auditingProcess.replace("2", ""), ",");
                            auditingUserId_i = StringUtils.strip(replace, ",");
                        }
                        // 添加销售合同号和海外销售合同号
                        String contractNo = paramProject.getContractNo();
                        String contractNoOs = paramProject.getContractNoOs();
                       *//* if (project.getOrderCategory() != 3 && StringUtils.isBlank(contractNo)) {
                            // 销售合同号不能为空
                            return false;
                        }*//*
                        // 判断销售合同号不能重复
                        List<Integer> contractNoProjectIds = projectDao.findByContractNo(contractNo);
                        if (contractNoProjectIds != null && contractNoProjectIds.size() > 0) {
                            Integer projectId = project.getId();
                            for (Integer proId : contractNoProjectIds) {
                                if (proId.intValue() != projectId.intValue()) {
                                    return false;
                                }
                            }
                        }
                        order.setContractNoOs(contractNoOs);
                        order.setContractNo(contractNo);
                        project.setContractNo(contractNo);*/
                        break;
                    case 13:
                        auditingProcess_i = auditingProcess.replace("13", "14");
                        auditingUserId_i = auditingUserId.replace("39552", "39252"); // 直接进入到下一步结算审核
                        break;
                    case 14:
                        String replace2 = StringUtils.strip(auditingUserId.replace("39252", ""));
                        if ("".equals(replace2)) { // 跟他并行审核的都已经审核完成
                            if (logistics_audit != null && logistics_audit == 2) { // 需要物流审核
                                auditingProcess_i = "15"; //
                                auditingUserId_i = String.valueOf(project.getLogisticsAuditerId()); //
                            } else {
                                // 直接事业部审核
                                auditingProcess_i = "16"; //
                                auditingUserId_i = String.valueOf(project.getBuAuditerId()); //
                            }
                        } else {
                            String replaceProcess = auditingProcess.replace("4", "");
                            auditingProcess_i = StringUtils.strip(replaceProcess, ",");
                            auditingUserId_i = StringUtils.strip(replace2, ",");
                        }
                        break;
                    case 15: // 物流审核
                        auditingProcess_i = "16"; //
                        auditingUserId_i = String.valueOf(project.getBuAuditerId()); //
                        break;
                    case 16:
                        if (auditingLevel > 1) {
                            auditingProcess_i = "17"; //
                            auditingUserId_i = project.getBuVpAuditerId().toString();
                            break;
                        }
                    case 17:
                        if (auditingLevel > 2) {
                            auditingProcess_i = "18"; //
                            auditingUserId_i = "32035"; //宋伟
                            break;
                        }
                    case 18:
                        if (auditingLevel > 3) {
                            auditingProcess_i = "19"; //
                            auditingUserId_i = "32046"; //冷成志
                            break;
                        }
                    case 19:
                        auditingStatus_i = 4; // 完成
                        auditingProcess_i = "999"; // 无下一审核进度和审核人
                        auditingUserId_i = null;
                        break;
                    default:
                        return false;
                }
            }
            checkLog_i = fullCheckLogInfo(order.getId(), curAuditProcess, Integer.parseInt(auditorId), auditorName, auditingProcess_i, auditingUserId_i, reason, "2", 2);
        }
        checkLogService.insert(checkLog_i);
        if (!paramProject.getAuditingType().equals("-1")) {
            if (StringUtils.isNotBlank(auditingProcess_i)) {
                project.getOrder().setAuditingProcess(Integer.parseInt(auditingProcess_i));
            } else {
                project.getOrder().setAuditingProcess(null);
            }
            if (StringUtils.isNotBlank(auditingUserId_i)) {
                project.getOrder().setAuditingUserId(auditingUserId_i);
            } else {
                project.getOrder().setAuditingUserId(null);
            }
            project.setAuditingStatus(auditingStatus_i);
        }
        project.setAuditingProcess(auditingProcess_i);
        project.setAuditingUserId(auditingUserId_i);
        sendDingtalk(project.getOrder(), auditingUserId_i, rejectFlag);
        project.setAudiRemark(auditorIds.toString());
        projectDao.save(project);

        auditBackLogHandle(project, rejectFlag, auditingUserId_i);

        return true;
    }

    private void auditBackLogHandle(Project project, boolean rejectFlag, String auditingUserId) {
        try {
            // 删除上一个待办，上一个待办可能是以下几种情况
            BackLog backLog2 = new BackLog();
            backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.PROJECT_AUDIT.getNum());
            backLog2.setHostId(project.getOrder().getId());
            backLogService.updateBackLogByDelYn(backLog2);
            backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.PROJECT_REJECT.getNum());
            backLogService.updateBackLogByDelYn(backLog2);
            backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.PROJECT_REJECT2.getNum());
            backLogService.updateBackLogByDelYn(backLog2);

            if (StringUtils.isNotBlank(auditingUserId)) {
                Integer[] userIdArr = Arrays.stream(auditingUserId.split(",")).map(vo -> Integer.parseInt(vo)).toArray(Integer[]::new);
                // 推送待办事件
                String infoContent = project.getProjectName();
                String projectNo = project.getProjectNo();
                String processProgress = project.getProcessProgress();
                BackLog.ProjectStatusEnum pse = null;
                if (rejectFlag && StringUtils.equals(processProgress, "6")) {
                    // 是项目驳回，且驳回到商务技术，则需要编辑页面的地址
                    pse = BackLog.ProjectStatusEnum.PROJECT_REJECT2;
                } else if (rejectFlag) {
                    pse = BackLog.ProjectStatusEnum.PROJECT_REJECT;
                } else {
                    pse = BackLog.ProjectStatusEnum.PROJECT_AUDIT;
                }
                applicationContext.publishEvent(new TasksAddEvent(applicationContext, backLogService,
                        pse, projectNo,
                        infoContent,
                        project.getOrder().getId(),
                        0,
                        "项目",
                        userIdArr));
            } else if ("999".equals(project.getAuditingProcess())) {
                // 所有审核完成，推送消息到项目商务技术经办人

                String region = project.getOrder().getRegion();   //所属地区
                Map<String, String> bnMapZhRegion = statisticsService.findBnMapZhRegion();
                String country = project.getOrder().getCountry();  //国家
                Map<String, String> bnMapZhCountry = statisticsService.findBnMapZhCountry();
                String infoContent = bnMapZhRegion.get(region) + " | " + bnMapZhCountry.get(country);  //提示内容
                applicationContext.publishEvent(new TasksAddEvent(applicationContext, backLogService,
                        BackLog.ProjectStatusEnum.EXECUTEPROJECT, project.getProjectNo(),
                        infoContent,
                        project.getOrder().getId(),
                        0,
                        "项目",
                        project.getBusinessUid()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addProfitData(XSSFWorkbook workbook, Map<String, Object> results) {
        Project projectDec = (Project) results.get("projectDec");
        // 获取第二个sheet页
        Sheet sheet1 = workbook.getSheetAt(0);
        Row row4 = sheet1.getRow(1);
        if (projectDec.getOrder().getCountry() != null) {//国家：
            Map<String, String> bnMapZhCountry = statisticsService.findBnMapZhCountry();
            row4.getCell(1).setCellValue(bnMapZhCountry.get(projectDec.getOrder().getCountry()));
        }
        if (projectDec.getBusinessUnitName() != null) {//单位：
            row4.getCell(4).setCellValue(projectDec.getBusinessUnitName());
        }
        if (projectDec.getContractNo() != null) {//项目编号：
            sheet1.getRow(2).getCell(1).setCellValue(projectDec.getContractNo());
        }
        if (projectDec.getProjectName() != null) {//项目内容
            //7行
            sheet1.getRow(3).getCell(1).setCellValue(projectDec.getProjectName());
        }
        if (projectDec.getProjectProfit() != null) {
            ProjectProfit projectProfit = projectDec.getProjectProfit();
            if (projectProfit.getTradeTerm() != null) {//贸易术语
                sheet1.getRow(2).getCell(4).setCellValue(projectProfit.getTradeTerm());
            }
            if (projectProfit.getProjectType() != null) {
                //8行
                String projectType = null;
                switch (projectProfit.getProjectType()) {//项目类型
                    case "2":
                        projectType = "加工贸易";
                        sheet1.getRow(4).getCell(1).setCellValue(sheet1.getRow(7).getCell(4).getStringCellValue() + projectType);
                        break;
                    case "1":
                        projectType = "一般贸易";
                        sheet1.getRow(4).getCell(2).setCellValue(sheet1.getRow(7).getCell(4).getStringCellValue() + projectType);

                        break;
                    case "3":
                        projectType = "转口贸易";
                        sheet1.getRow(4).getCell(3).setCellValue(sheet1.getRow(7).getCell(4).getStringCellValue() + projectType);

                        break;
                    default:
                        projectType = "";
                }

            }
            //sheet1.getRow(5) 类别	明细	含税金额	不含税金额	备注

            if (projectProfit.getContractAmountUsd() != null) {//合同金额（美元）
                sheet1.getRow(6).getCell(2).setCellValue(projectProfit.getContractAmountUsd().toString());
            }
            if (projectProfit.getExchangeRate() != null) {//汇率
                sheet1.getRow(7).getCell(2).setCellValue(projectProfit.getExchangeRate().toString());
            }
            if (projectProfit.getContractAmount() != null) {//合同金额（人民币）
                sheet1.getRow(8).getCell(2).setCellValue(projectProfit.getContractAmount().toString());
            }
            if (projectProfit.getPurchasingCostsD() != null) {//采购成本-国内
                sheet1.getRow(9).getCell(2).setCellValue(projectProfit.getPurchasingCostsD().toString());
            }
            if (projectProfit.getPurchasingCostsF() != null) {//采购成本-国外
                sheet1.getRow(10).getCell(2).setCellValue(projectProfit.getPurchasingCostsF().toString());
            }
//            if (projectProfit.getDirectLabor() != null) {//直接人工
//                sheet1.getRow(14).getCell(2).setCellValue(projectProfit.getDirectLabor().toString());
//            }
//            if (projectProfit.getManufacturingCosts() != null) {//制造费用
//                sheet1.getRow(15).getCell(2).setCellValue(projectProfit.getManufacturingCosts().toString());
//            }
            if (projectProfit.getTaxRefund() != null) {//退税
                sheet1.getRow(11).getCell(2).setCellValue(projectProfit.getTaxRefund().toString());
            }
            if (projectProfit.getLandFreight() != null) {//陆运费
                sheet1.getRow(12).getCell(2).setCellValue(projectProfit.getLandFreight().toString());
            }
            if (projectProfit.getLandInsurance() != null) {//陆运险
                sheet1.getRow(13).getCell(2).setCellValue(projectProfit.getLandInsurance().toString());
            }
            if (projectProfit.getPortCharges() != null) {//港杂费
                sheet1.getRow(14).getCell(2).setCellValue(projectProfit.getPortCharges().toString());
            }
            if (projectProfit.getInspectionFee() != null) {//商检费
                sheet1.getRow(15).getCell(2).setCellValue(projectProfit.getInspectionFee().toString());
            }
            if (projectProfit.getInternationalTransport() != null) {//国际运输
                sheet1.getRow(16).getCell(2).setCellValue(projectProfit.getInternationalTransport().toString());
            }
            if (projectProfit.getTariff() != null) {//关税
                sheet1.getRow(17).getCell(2).setCellValue(projectProfit.getTariff().toString());
            }
            if (projectProfit.getVat() != null) {//增值税
                sheet1.getRow(18).getCell(2).setCellValue(projectProfit.getVat().toString());
            }
            if (projectProfit.getCustomsClearFee() != null) {//清关杂税
                sheet1.getRow(19).getCell(2).setCellValue(projectProfit.getCustomsClearFee().toString());
            }
            if (projectProfit.getCustomsAgentFee() != null) {//清关代理费
                sheet1.getRow(20).getCell(2).setCellValue(projectProfit.getCustomsAgentFee().toString());
            }
            if (projectProfit.getTransportionInsurance() != null) {//货物运输保险
                sheet1.getRow(21).getCell(2).setCellValue(projectProfit.getTransportionInsurance().toString());
            }
            if (projectProfit.getExportCreditInsurance() != null) {//出口信用险
                sheet1.getRow(22).getCell(2).setCellValue(projectProfit.getExportCreditInsurance().toString());
            }
            if (projectProfit.getOtherCredit() != null) {//其他保险
                sheet1.getRow(23).getCell(2).setCellValue(projectProfit.getOtherCredit().toString());
            }
            if (projectProfit.getTravelExpenses() != null) {//差旅费、业务费等
                sheet1.getRow(24).getCell(2).setCellValue(projectProfit.getTravelExpenses().toString());
            }
            if (projectProfit.getAgentFee() != null) {//代理费用->项目佣金
                sheet1.getRow(25).getCell(2).setCellValue(projectProfit.getAgentFee().toString());
            }
            if (projectProfit.getProjectCost() != null) {//主营业务成本小计
                sheet1.getRow(26).getCell(2).setCellFormula(null);
                sheet1.getRow(26).getCell(2).setCellValue(projectProfit.getProjectCost().toString());
            }
            if (projectProfit.getManageFee() != null) {//八、管理费用+销售费用
                sheet1.getRow(27).getCell(2).setCellFormula(null);
                sheet1.getRow(27).getCell(2).setCellValue(projectProfit.getManageFee().toString());
            }
            if (projectProfit.getGuaranceFee() != null) {//信用证、保函费用
                sheet1.getRow(28).getCell(2).setCellValue(projectProfit.getGuaranceFee().toString());
            }
            if (projectProfit.getFinancingInterest() != null) {//融资利息
                sheet1.getRow(29).getCell(2).setCellFormula(null);
                sheet1.getRow(29).getCell(2).setCellValue(projectProfit.getFinancingInterest().toString());
            }
//            if (projectProfit.getBankFees() != null) {//银行手续费
//                sheet1.getRow(32).getCell(2).setCellFormula(null);
//                sheet1.getRow(32).getCell(2).setCellValue(projectProfit.getBankFees().toString());
//            }
            if (projectProfit.getDomesticTaxs() != null) {//国内税费
                sheet1.getRow(30).getCell(2).setCellFormula(null);
                sheet1.getRow(30).getCell(2).setCellValue(projectProfit.getDomesticTaxs().toString());
            }
            if (projectProfit.getForeignTaxes() != null) {//国外税费
                sheet1.getRow(31).getCell(2).setCellFormula(null);
                sheet1.getRow(31).getCell(2).setCellValue(projectProfit.getForeignTaxes().toString());
            }
            if (projectProfit.getRearFee() != null) {//十、后方提点费用
                sheet1.getRow(32).getCell(2).setCellFormula(null);
                sheet1.getRow(32).getCell(2).setCellValue(projectProfit.getRearFee().toString());
            }
            if (projectProfit.getTotalProjectCost() != null) {//项目成本总计
                sheet1.getRow(33).getCell(2).setCellFormula(null);
                sheet1.getRow(33).getCell(2).setCellValue(projectProfit.getTotalProjectCost().toString());
            }
            if (projectProfit.getBeforeProfit() != null) {//市场提点前报价利润
                sheet1.getRow(34).getCell(2).setCellFormula(null);
                sheet1.getRow(34).getCell(2).setCellValue(projectProfit.getBeforeProfit().toString());
            }
            if (projectProfit.getRaiseFee() != null) {//提点费用
                sheet1.getRow(35).getCell(2).setCellFormula(null);
                sheet1.getRow(35).getCell(2).setCellValue(projectProfit.getRaiseFee().toString());
            }
            if (projectProfit.getPlatformAgentCost() != null) {//平台代理费用
                sheet1.getRow(36).getCell(2).setCellFormula(null);
                sheet1.getRow(36).getCell(2).setCellValue(projectProfit.getPlatformAgentCost().toString());
            }
            if (projectProfit.getAfterProfit() != null) {//市场提点后报价利润
                sheet1.getRow(37).getCell(2).setCellFormula(null);
                sheet1.getRow(37).getCell(2).setCellValue(projectProfit.getAfterProfit().toString());
            }
            if (projectProfit.getQuotationProfit() != null) {//报价利润率
                sheet1.getRow(38).getCell(2).setCellFormula(null);
                sheet1.getRow(38).getCell(2).setCellValue(projectProfit.getQuotationProfit().toString());
            }
        }
        if (projectDec.getBusinessName() != null) {//制单人:
            sheet1.getRow(39).getCell(1).setCellValue(projectDec.getBusinessName());
        }
        if (projectDec.getBuAuditer() != null) {//审核人
            sheet1.getRow(39).getCell(3).setCellValue(projectDec.getBuAuditer());
        }
        String stringR44C4 = sheet1.getRow(43).getCell(4).getStringCellValue().replace("项目财务：", "项目财务：田万全");
        sheet1.getRow(39).getCell(4).setCellValue(stringR44C4);
        sheet1.getRow(4).getCell(4).setCellValue("");
    }

    // 处理日志
    private CheckLog fullCheckLogInfo(Integer orderId, Integer auditingProcess, Integer auditorId, String auditorName, String nextAuditingProcess, String nextAuditingUserId,
                                      String auditingMsg, String operation, int type) {
        CheckLog checkLog = new CheckLog();
        checkLog.setOrderId(orderId);
        checkLog.setCreateTime(new Date());
        checkLog.setAuditingProcess(auditingProcess);
        checkLog.setAuditingUserId(auditorId);
        checkLog.setAuditingUserName(auditorName);
        checkLog.setNextAuditingProcess(nextAuditingProcess);
        checkLog.setNextAuditingUserId(nextAuditingUserId);
        checkLog.setAuditingMsg(auditingMsg);
        checkLog.setOperation(operation);
        checkLog.setType(type);
        CheckLog.AuditProcessingEnum ape = CheckLog.AuditProcessingEnum.findEnum(type, auditingProcess);
        checkLog.setAuditSeq(ape.getAuditSeq());

        return checkLog;
    }

}
