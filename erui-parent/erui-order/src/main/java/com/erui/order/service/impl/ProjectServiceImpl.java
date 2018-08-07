package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.erui.comm.NewDateUtil;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.BackLogDao;
import com.erui.order.dao.OrderDao;
import com.erui.order.dao.ProjectDao;
import com.erui.order.entity.BackLog;
import com.erui.order.entity.Goods;
import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import com.erui.order.event.OrderProgressEvent;
import com.erui.order.requestVo.ProjectListCondition;
import com.erui.order.service.BackLogService;
import com.erui.order.service.ProjectService;
import com.erui.order.util.exception.MyException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
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


    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口

    @Value("#{orderProp[SEND_SMS]}")
    private String sendSms;  //发短信接口

    @Override
    @Transactional(readOnly = true)
    public Project findById(Integer id) {
        return projectDao.findOne(id);
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateProject(Project project) throws Exception {
        Project projectUpdate = projectDao.findOne(project.getId());
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

            //项目驳回，删除   “办理项目/驳回”  待办提示
            BackLog backLog = new BackLog();
            backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.TRANSACTIONPROJECT.getNum());    //功能访问路径标识
            backLog.setHostId(projectUpdate.getId());
            backLogService.updateBackLogByDelYn(backLog);

            return true;
        } else {
            if ((new Integer(4).equals(project.getOrderCategory()) || new Integer(3).equals(project.getOverseasSales())) && paramProjectStatusEnum == Project.ProjectStatusEnum.DONE) {
                //Order order = projectUpdate.getOrder();
                projectUpdate.setProjectStatus(paramProjectStatusEnum.getCode());
                project.copyProjectDescTo(projectUpdate);
                order.setStatus(Order.StatusEnum.DONE.getCode());
                applicationContext.publishEvent(new OrderProgressEvent(order, 2));

                //现货的情况直接完成 ，删除 “办理项目/驳回”  待办提示
                BackLog backLog = new BackLog();
                backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.TRANSACTIONPROJECT.getNum());    //功能访问路径标识
                backLog.setHostId(projectUpdate.getId());
                backLogService.updateBackLogByDelYn(backLog);

            } else {
                // 项目一旦执行，则只能修改项目的状态，且状态必须是执行后的状态
                if (nowProjectStatusEnum.getNum() >= Project.ProjectStatusEnum.EXECUTING.getNum()) {
                    if (paramProjectStatusEnum.getNum() < Project.ProjectStatusEnum.EXECUTING.getNum()) {
                        throw new MyException(String.format("%s%s%s", "参数状态错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Parameter state error"));
                    }
                } else if (nowProjectStatusEnum == Project.ProjectStatusEnum.SUBMIT) {
                    // 之前只保存了项目，则流程可以是提交到项目经理和执行
                    if (paramProjectStatusEnum.getNum() > Project.ProjectStatusEnum.EXECUTING.getNum()) {
                        throw new MyException(String.format("%s%s%s", "参数状态错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Parameter state error"));
                    }
                    project.copyProjectDescTo(projectUpdate);
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

                        //项目驳回 商务技术经办人办理项目指定项目经理以后  ，删除 “办理项目/驳回”  待办提示   删除 “办理项目/驳回”  待办提示
                        BackLog backLog = new BackLog();
                        backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.TRANSACTIONPROJECT.getNum());    //功能访问路径标识
                        backLog.setHostId(projectUpdate.getId());
                        backLogService.updateBackLogByDelYn(backLog);


                        //商务技术经办人办理项目指定项目经理以后  需要添加待办事项   执行项目/驳回   。提示项目经理办理
                        BackLog newBackLog = new BackLog();
                        newBackLog.setCreateDate(new SimpleDateFormat("yyyyMMdd").format(new Date())); //提交时间
                        newBackLog.setPlaceSystem("订单");   //所在系统
                        newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.EXECUTEPROJECT.getMsg());  //功能名称
                        newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.EXECUTEPROJECT.getNum());    //功能访问路径标识
                        newBackLog.setReturnNo(projectUpdate.getContractNo());  //返回单号    销售合同号
                        String region = projectUpdate.getRegion();   //所属地区
                        String country = projectUpdate.getCountry();  //国家
                        newBackLog.setInformTheContent(region+" | "+country);  //提示内容
                        newBackLog.setHostId(projectUpdate.getId());    //父ID，列表页id
                        Integer managerUid = projectUpdate.getManagerUid();//项目经理
                        newBackLog.setUid(managerUid);   //项目经理id
                        newBackLog.setDelYn(1);
                        backLogDao.save(newBackLog);

                    }
                } else if (nowProjectStatusEnum == Project.ProjectStatusEnum.HASMANAGER) {
                    if (paramProjectStatusEnum == Project.ProjectStatusEnum.TURNDOWN) {
                        projectUpdate.setProjectStatus(Project.ProjectStatusEnum.SUBMIT.getCode());
                        projectDao.save(projectUpdate);
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


                        //项目经理指定经办人完成以后，删除   “执行项目/驳回”  待办提示
                        BackLog backLog = new BackLog();
                        backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.EXECUTEPROJECT.getNum());    //功能访问路径标识
                        backLog.setHostId(projectUpdate.getId());
                        backLogService.updateBackLogByDelYn(backLog);
                    }
                } else {
                    // 其他分支，错误
                    throw new MyException(String.format("%s%s%s", "项目状态数据错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Project status data error"));
                }
                // 修改状态
                projectUpdate.setProjectStatus(paramProjectStatusEnum.getCode());
                //修改备注  在项目完成前商务技术可以修改项目备注
                if (nowProjectStatusEnum != Project.ProjectStatusEnum.DONE) {
                    projectUpdate.setRemarks(project.getRemarks());
                }
                // 操作相关订单信息
                if (paramProjectStatusEnum == Project.ProjectStatusEnum.EXECUTING) {
                    //Order order = projectUpdate.getOrder();
                    try {
                        order.getGoodsList().forEach(gd -> {
                                    gd.setStartDate(projectUpdate.getStartDate());
                                    gd.setDeliveryDate(projectUpdate.getDeliveryDate());
                                    gd.setProjectRequirePurchaseDate(projectUpdate.getRequirePurchaseDate());
                                    gd.setExeChgDate(projectUpdate.getExeChgDate());
                                }
                        );

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    order.setStatus(Order.StatusEnum.EXECUTING.getCode());
                    applicationContext.publishEvent(new OrderProgressEvent(order, 2));
                    orderDao.save(order);

                    //项目驳回，删除   “办理项目/驳回”  待办提示
                    BackLog backLog = new BackLog();
                    backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.TRANSACTIONPROJECT.getNum());    //功能访问路径标识
                    backLog.setHostId(projectUpdate.getId());
                    backLogService.updateBackLogByDelYn(backLog);


                    //如果项目状态是提交状态  通知商务技术经办人办理采购申请
                    BackLog newBackLog = new BackLog();
                    newBackLog.setCreateDate(new SimpleDateFormat("yyyyMMdd").format(new Date())); //提交时间
                    newBackLog.setPlaceSystem("订单");   //所在系统
                    newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.PURCHREQUISITION.getMsg());  //功能名称
                    newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.PURCHREQUISITION.getNum());    //功能访问路径标识
                    newBackLog.setReturnNo(projectUpdate.getContractNo());  //返回单号    销售合同号
                    String region = projectUpdate.getRegion();   //所属地区
                    String country = projectUpdate.getCountry();  //国家
                    newBackLog.setInformTheContent(region+" | "+country);  //提示内容
                    newBackLog.setHostId(projectUpdate.getId());    //父ID，列表页id
                    Integer businessUid = projectUpdate.getBusinessUid();//商务技术经办人id
                    newBackLog.setUid(businessUid);   ////经办人id
                    newBackLog.setDelYn(1);
                    backLogDao.save(newBackLog);
                }
            }
            projectUpdate.setUpdateTime(new Date());
            Project project1 = projectDao.save(projectUpdate);

            //项目管理：办理项目的时候，如果指定了项目经理，需要短信通知
            if (project1.getProjectStatus() == Project.ProjectStatusEnum.HASMANAGER.getCode()) {
                Integer managerUid = project1.getManagerUid();      //交付配送中心项目经理ID
                sendSms(project1, managerUid);
            }

        }

        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Project> findByPage(ProjectListCondition condition) {
        PageRequest pageRequest = new PageRequest(condition.getPage() - 1, condition.getRows(), new Sort(Sort.Direction.DESC, "id"));
        Page<Project> pageList = projectDao.findAll(new Specification<Project>() {
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
            if(goodsList.size() > 0){
                for (Goods goods : goodsList){
                    goods.setPurchGoods(null);
                }
            }

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
        for (Project project : proList) {
            if (project.getOrder() != null) {
                project.setoId(project.getOrder().getId());
            }
        }
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

}
