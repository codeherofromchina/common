package com.erui.order.service.impl;

import com.erui.order.dao.CheckLogDao;
import com.erui.order.entity.CheckLog;
import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import com.erui.order.service.CheckLogService;
import com.erui.order.service.OrderService;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author:SHIGS
 * @Description
 * @Date Created in 15:50 2018/8/27
 * @modified By
 */
@Service
public class CheckLogServiceImpl implements CheckLogService {
    @Autowired
    private CheckLogDao checkLogDao;
    @Autowired
    private OrderService orderService;

    /**
     * 查找最近的一个审核记录
     *
     * @param type  1:订单  2：项目
     * @param refId 项目或订单ID，根据type
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public CheckLog findLastLog(int type, Integer refId) {
        PageRequest request = new PageRequest(0, 1, Sort.Direction.DESC, "createTime");
        CheckLog example = new CheckLog();
        example.setType(type);
        example.setOrderId(refId);
        Page<CheckLog> all = checkLogDao.findAll(Example.of(example), request);
        if (all != null && all.getContent().size() > 0) {
            return all.getContent().get(0);
        }
        return null;
    }

    @Override
    public CheckLog findLogOne(Integer refId) {
        PageRequest request = new PageRequest(0, 1, Sort.Direction.DESC, "createTime");
        CheckLog example = new CheckLog();
        example.setOrderId(refId);
        Page<CheckLog> all = checkLogDao.findAll(Example.of(example), request);
        if (all != null && all.getContent().size() > 0) {
            return all.getContent().get(0);
        }
        return null;
    }

    @Override
    public void insert(CheckLog checkLog) {
        checkLogDao.save(checkLog);
    }

    @Override
    public List<CheckLog> findListByOrderId(Integer orderId) {
        if (orderId != null) {
            List<CheckLog> checkLogList = checkLogDao.findByOrderId(orderId);
            if (checkLogList != null && checkLogList.size() > 0) {
                return checkLogList;
            }
        }
        return null;
    }

    /**
     * 根据时间倒叙排序订单的所有审核
     *
     * @param orderId
     * @return
     */
    private List<CheckLog> findListByOrderIdOrderByCreateTimeDesc(Integer orderId) {
        List<CheckLog> checkLogList = null;
        if (orderId != null) {
            checkLogList = checkLogDao.findAll(new Specification<CheckLog>() {
                @Override
                public Predicate toPredicate(Root<CheckLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    return cb.equal(root.get("orderId").as(Integer.class), orderId);
                }
            }, new Sort(Sort.Direction.DESC, "createTime"));
        }
        if (checkLogList == null) {
            checkLogList = new ArrayList<>();
        }
        return checkLogList;
    }


    /**
     * 根据类型和审核状态从前到后排序订单的所有审核
     *
     * @param orderId
     * @return
     */
    private List<CheckLog> findListByOrderIdOrderByTypeAndAuditingProcess(Integer orderId) {
        List<CheckLog> checkLogList = null;
        if (orderId != null) {
            checkLogList = checkLogDao.findAll(new Specification<CheckLog>() {
                @Override
                public Predicate toPredicate(Root<CheckLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    return cb.equal(root.get("orderId").as(Integer.class), orderId);
                }
            }, new Sort(Sort.Direction.DESC, "type", "auditingProcess"));
        }
        if (checkLogList == null) {
            checkLogList = new ArrayList<>();
        }
        return checkLogList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CheckLog> findPassed(Integer orderId) {
        List<CheckLog> resultCheckLogs = new ArrayList<>();
        if (orderId != null) {
            List<CheckLog> checkLogList = findListByOrderIdOrderByTypeAndAuditingProcess(orderId);
//            List<CheckLog> checkLogList = findListByOrderIdOrderByCreateTimeDesc(orderId);
            Order order = orderService.findById(orderId);
            Integer orderAuditingStatus = order.getAuditingStatus();// 订单审核状态，如果为空说明没有任何审核进度
            if (orderAuditingStatus == null) {
                return resultCheckLogs;
            }
            Project project = order.getProject();
            // 通过项目和订单判断可以驳回到之前的步骤列表
            for (CheckLog checkLog : checkLogList) {
                // 只查找通过和立项的审核
                if (checkLog.getOperation() == "-1") {
                    continue;
                }
                if (orderAuditingStatus == 4 && checkLog.getType() == 1) {
                    // 如果订单审核通过，则所有订单的日志需要返回
                    resultCheckLogs.add(checkLog);
                } else if (orderAuditingStatus < 4) {
                    // 如果订单还没有审核通过，则只能返回订单的审核列表 因需求修改，法务审核在订单审核里为case： 8
                    if (checkLog.getType() == 1 && (checkLog.getAuditingProcess() < order.getAuditingProcess() || checkLog.getAuditingProcess() == 8)) {
                        if (order.getAuditingProcess() > 2 && order.getAuditingProcess() != 8) {
                            resultCheckLogs.add(checkLog);
                        }
                    }
                    continue;
                }

                if (project != null && checkLog.getType() == 2) {
                    String auditingProcess = project.getAuditingProcess();
                    if (StringUtils.isBlank(auditingProcess)) {
                        break;
                    }
                    String[] split = auditingProcess.split(",");
                    int maxAuditingProcess = 0;
                    for (String s : split) {
                        int i = Integer.parseInt(s);
                        if (i > maxAuditingProcess) {
                            maxAuditingProcess = i;
                        }
                    }
                    if (checkLog.getAuditingProcess() < maxAuditingProcess) {
                        resultCheckLogs.add(checkLog);
                    }
                }
            }
        }
        Map<String, CheckLog> map = new LinkedMap<>();
        for (CheckLog cLog : resultCheckLogs) {
            map.put(cLog.getAuditingProcess() + "_" + cLog.getType(), cLog);
        }
        List<CheckLog> cList = map.values().stream().collect(Collectors.toList());
        List<CheckLog> collect = cList.stream().sorted(Comparator.comparing(CheckLog::getCreateTime).reversed()).collect(Collectors.toList());
        return collect;
    }

    @Deprecated
    @Transactional(readOnly = true)
    public List<CheckLog> findPassed2(Integer orderId) {
        PageRequest request = new PageRequest(0, 100, Sort.Direction.DESC, "createTime");
        Page<CheckLog> all = checkLogDao.findAll(new Specification<CheckLog>() {
            List<Predicate> list = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<CheckLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                // 根据销售同号模糊查询
                if (orderId != null) {
                    list.add(cb.equal(root.get("orderId").as(Integer.class), orderId));
                }
//                list.add(cb.equal(root.get("type").as(Integer.class), 1));
                list.add(cb.between(root.get("operation").as(String.class), "1", "2"));
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);
        List<CheckLog> checkLogList = all.getContent();
        Map<Integer, CheckLog> map = new HashMap<>();
        if (checkLogList != null && checkLogList.size() > 0) {
            for (CheckLog cLog : checkLogList) {
                map.put(cLog.getAuditingProcess(), cLog);
            }
            List<CheckLog> cList = map.values().stream().collect(Collectors.toList());
            return cList;
        }
        return null;
    }
}
