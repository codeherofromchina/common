package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.OrderDao;
import com.erui.order.dao.ProjectDao;
import com.erui.order.entity.Goods;
import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import com.erui.order.entity.Purch;
import com.erui.order.event.MyEvent;
import com.erui.order.event.OrderProgressEvent;
import com.erui.order.requestVo.ProjectListCondition;
import com.erui.order.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ApplicationContext applicationContext;
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
        //项目未执行状态 驳回项目 订单置为待确认状态 删除项目
        if (nowProjectStatusEnum.getNum() == 1 && paramProjectStatusEnum.getNum() == 11) {
            Order order = projectUpdate.getOrder();
            order.setStatus(Order.StatusEnum.UNEXECUTED.getCode());
            orderDao.save(order);
            projectDao.delete(projectUpdate);
        } else {
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
                    projectUpdate.setPurchaseName(null);
                }
            } else if (nowProjectStatusEnum == Project.ProjectStatusEnum.HASMANAGER) {
                if (paramProjectStatusEnum == Project.ProjectStatusEnum.TURNDOWN) {
                    projectUpdate.setProjectStatus(Project.ProjectStatusEnum.SUBMIT.getCode());
                    projectDao.save(projectUpdate);
                    return true;
                } else {
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
                    projectUpdate.setPurchaseName(project.getPurchaseName());
                    // 修改备注和执行单变更日期
                    projectUpdate.setRemarks(project.getRemarks());
                    projectUpdate.setExeChgDate(project.getExeChgDate());
                }
            } else {
                // 其他分支，错误
                throw new Exception("项目状态数据错误");
            }
            // 修改状态
            projectUpdate.setProjectStatus(paramProjectStatusEnum.getCode());
            //修改备注  在项目完成前商务技术可以修改项目备注
            if (nowProjectStatusEnum != Project.ProjectStatusEnum.DONE) {
                projectUpdate.setRemarks(project.getRemarks());
            }
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
                applicationContext.publishEvent(new OrderProgressEvent(order, 2));
                orderDao.save(order);
            }
            projectUpdate.setUpdateTime(new Date());
            projectDao.save(projectUpdate);
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
                    list.add(cb.like(root.get("processProgress").as(String.class), "%" + condition.getProcessProgress() + "%"));
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
                //
                if (condition.getManagerUid() != null) {
                    list.add(cb.equal(root.get("managerUid").as(Integer.class), condition.getManagerUid()));

                }
                //根据物流经办人
                if (condition.getLogisticsUid() != null) {
                    list.add(cb.equal(root.get("logisticsUid").as(Integer.class), condition.getLogisticsUid()));
                }
                //根据仓库经办人
                if (condition.getWarehouseUid() != null) {
                    list.add(cb.equal(root.get("warehouseUid").as(Integer.class), condition.getWarehouseUid()));
                }
                //根据商务技术经办人
                if (condition.getBusinessUid() != null) {
                    list.add(cb.equal(root.get("businessUid").as(Integer.class), condition.getBusinessUid()));
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
    public Page<Map<String, Object>> purchAbleByPage(List<String> projectNoList, String purchaseUid, int pageNum, int pageSize) throws Exception {
        Integer intPurchaseUid = null;
        if (StringUtils.isNotBlank(purchaseUid) && !StringUtils.isNumeric(purchaseUid)) {
            throw new Exception("采购经办人参数错误");
        } else if (StringUtils.isNotBlank(purchaseUid)) {
            intPurchaseUid = Integer.parseInt(purchaseUid);
        }

        List<Integer> projectIds = findAllPurchAbleProjectId(projectNoList, intPurchaseUid);

        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, new Sort(Sort.Direction.DESC, "updateTime"));
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
