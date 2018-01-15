package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.OrderDao;
import com.erui.order.dao.ProjectDao;
import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import com.erui.order.requestVo.ProjectListCondition;
import com.erui.order.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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

    @Transactional
    @Override
    public Project findById(Integer id) {
        return projectDao.findOne(id);
    }

    @Override
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


    @Transactional
    @Override
    public boolean updateProject(Project project) {
        Project projectUpdate = projectDao.findOne(project.getId());
        project.copyProjectDesc(projectUpdate);
        if (project.getProjectStatus() == Project.projectStatusEnum.EXECUTING.getCode().toString()) {

        }
        projectUpdate.setProjectStatus(project.getProjectStatus());
        projectUpdate.setUpdateTime(new Date());
        Project project1 = projectDao.saveAndFlush(projectUpdate);
        if (StringUtils.equals(project1.getProjectStatus(), "EXECUTING")) {
            Order order = project1.getOrder();
            order.setStatus(3);
            orderDao.save(order);
        }
        return true;
    }

    @Transactional
    @Override
    public Page<Project> findByPage(ProjectListCondition condition) {
        PageRequest pageRequest = new PageRequest(condition.getPage() - 1, condition.getRows(), null);
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
                //根据汇款状态
                if (StringUtil.isNotBlank(condition.getProjectStatus())) {
                    list.add(cb.equal(root.get("projectStatus").as(String.class), condition.getProjectStatus()));
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
    public List<Project> purchAbleList(List<String> projectNoList) {
        List<Project> list = projectDao.findByPurchReqCreateAndPurchDone(Project.PurchReqCreateEnum.SUBMITED.getCode(), Boolean.FALSE);
        if (list == null) {
            list = new ArrayList<>();
        } else if (list != null && projectNoList != null) {
            // TODO 省略数据库查询，先用程序过滤
            list = list.stream().filter(project -> {
                String projectNo = project.getProjectNo();
                for (String pStr : projectNoList) {
                    if (StringUtils.contains(projectNo, pStr)) {
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());
        }
        return list;
    }

    @Transactional
    @Override
    public Project findDesc(Integer id) {
        Project project = projectDao.findOne(id);
        if (project != null) {
            project.getOrder().getGoodsList().size();
        }
        return project;
    }

    @Transactional
    @Override
    public Project findByIdOrOrderId(Integer id, Integer orderId) {
        Project project = projectDao.findByIdOrOrderId(id, orderId);
        if (project != null) {
            return project;
        }
        return null;
    }


}
