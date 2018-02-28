package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.OrderDao;
import com.erui.order.dao.ProjectDao;
import com.erui.order.entity.Goods;
import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import com.erui.order.requestVo.ProjectListCondition;
import com.erui.order.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private OrderDao orderDao;

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

        Project.ProjectStatusEnum nowProjectStatusEnum = Project.ProjectStatusEnum.fromCode(projectUpdate.getProjectStatus());
        Project.ProjectStatusEnum paramProjectStatusEnum = Project.ProjectStatusEnum.fromCode(project.getProjectStatus());
        // 项目一旦执行，则只能修改项目的状态，且状态必须是执行后的状态
        if (nowProjectStatusEnum.getNum() >= Project.ProjectStatusEnum.EXECUTING.getNum()) {
            if (paramProjectStatusEnum.getNum() < Project.ProjectStatusEnum.EXECUTING.getNum()) {
                throw new Exception("参数状态错误");
            }
        } else if (nowProjectStatusEnum == Project.ProjectStatusEnum.SUBMIT) {
            // 之前只保存了项目，则流程可以是提交到项目经理和执行
            if (paramProjectStatusEnum.getNum() > Project.ProjectStatusEnum.EXECUTING.getNum()) {
                throw new Exception("参数状态错误");
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
            }
        } else if (nowProjectStatusEnum == Project.ProjectStatusEnum.HASMANAGER) {
            // 交付配送中心项目经理只能保存后者执行
            if (paramProjectStatusEnum != Project.ProjectStatusEnum.EXECUTING && paramProjectStatusEnum != Project.ProjectStatusEnum.HASMANAGER) {
                throw new Exception("参数状态错误");
            }
            // 只设置项目成员
            projectUpdate.setPurchaseUid(project.getPurchaseUid());
            projectUpdate.setQualityName(project.getQualityName());
            projectUpdate.setQualityUid(project.getQualityUid());
            projectUpdate.setLogisticsUid(project.getLogisticsUid());
            projectUpdate.setLogisticsName(project.getLogisticsName());
            projectUpdate.setWarehouseName(project.getWarehouseName());
            projectUpdate.setWarehouseUid(project.getWarehouseUid());
            // 修改备注和执行单变更日期
            projectUpdate.setRemarks(project.getRemarks());
            projectUpdate.setExeChgDate(project.getExeChgDate());
        } else {
            // 其他分支，错误
            throw new Exception("项目状态数据错误");
        }
        // 修改状态
        projectUpdate.setProjectStatus(paramProjectStatusEnum.getCode());
        // 操作相关订单信息
        if (paramProjectStatusEnum == Project.ProjectStatusEnum.EXECUTING) {
            Order order = projectUpdate.getOrder();
            order.getGoodsList().forEach(gd -> {
                        gd.setStartDate(projectUpdate.getStartDate());
                        gd.setDeliveryDate(projectUpdate.getDeliveryDate());
                        gd.setProjectRequirePurchaseDate(projectUpdate.getRequirePurchaseDate());
                        gd.setExeChgDate(projectUpdate.getExeChgDate());
                    }
            );
            order.setStatus(Order.StatusEnum.EXECUTING.getCode());
            orderDao.save(order);
        }
        projectUpdate.setUpdateTime(new Date());
        projectDao.save(projectUpdate);
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
                //执行单约定交付日期
                if (condition.getDeliveryDate() != null) {
                    list.add(cb.equal(root.get("deliveryDate").as(Date.class), NewDateUtil.getDate(condition.getDeliveryDate())));
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
                //根据项目状态
                if (StringUtil.isNotBlank(condition.getProjectStatus())) {
                    list.add(cb.equal(root.get("projectStatus").as(String.class), condition.getProjectStatus()));
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
            project.setoId(project.getOrder().getId());
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
                throw new Exception("采购经办人参数错误");
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
            }).collect(Collectors.toList());
            // 反序
            Collections.reverse(list);
        }
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public Project findDesc(Integer id) {
        Project project = projectDao.findOne(id);
        if (project != null) {
            project.getOrder().getGoodsList().size();
        }
        return project;
    }

    @Transactional(readOnly = true)
    @Override
    public Project findByIdOrOrderId(Integer id, Integer orderId) {
        Project project = projectDao.findByIdOrOrderId(id, orderId);
        if (project != null) {
            project.getGoodsList().size();
            return project;
        }
        return null;
    }


}
