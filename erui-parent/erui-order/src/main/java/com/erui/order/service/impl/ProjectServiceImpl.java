package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.ProjectDao;
import com.erui.order.entity.Project;
import com.erui.order.requestVo.ProjectListCondition;
import com.erui.order.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;
    @Transactional
    @Override
    public Project findById(Integer id) {
        return projectDao.findOne(id);
    }
    @Transactional
    @Override
    public boolean updateProject(Project project) {
        Project projectUpdate = projectDao.findOne(project.getId());
      /*  if (!project.getProjectStatus().equals("SUBMIT")) {
        }*/
        projectUpdate.setUpdateTime(new Date());
        project.copyProjectDesc(projectUpdate);
        projectUpdate.setProjectStatus(project.getProjectStatus());
        projectDao.saveAndFlush(projectUpdate);
        return true;
    }

    @Override
    public Page<Project> findByPage(ProjectListCondition condition) {
        PageRequest pageRequest = new PageRequest(condition.getPage(), condition.getRows(), null);
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
                if (condition.getStartDate()!=null) {
                    list.add(cb.equal(root.get("startDate").as(Date.class),  NewDateUtil.getDate(condition.getStartDate())));
                }
                //根据执行分公司查询
                if (StringUtil.isNotBlank(condition.getExecCoName())) {
                    list.add(cb.like(root.get("execCoName").as(String.class),"%" + condition.getExecCoName() + "%"));
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
                if (condition.getProjectStatus() != null) {
                    list.add(cb.equal(root.get("projectStatus").as(String.class), condition.getProjectStatus()));
                }
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, pageRequest);
        return pageList;
    }


    @Override
    public List<Project> purchAbleList() {
        List<Project> list = projectDao.findByPurchReqCreateAndPurchDone(Project.PurchReqCreateEnum.SUBMITED.getCode(),Boolean.TRUE);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }


}
