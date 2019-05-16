package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.erui.comm.ThreadLocalUtil;
import com.erui.order.dao.CheckLogDao;
import com.erui.order.dao.OrderDao;
import com.erui.order.entity.CheckLog;
import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import com.erui.order.service.CheckLogService;
import com.erui.order.service.CheckLogV2Service;
import com.erui.order.service.OrderService;
import com.erui.order.util.BpmUtils;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author:SHIGS
 * @Description
 * @Date Created in 15:50 2018/8/27
 * @modified By
 */
@Service
public class CheckLogV2ServiceImpl implements CheckLogV2Service {
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

    /**
     *  直接从业务流获取审核日志
     * @param orderId
     * @return
     *          {
     *                 "executionId": "017ff134-56b2-11e9-8b8c-72d8a874a2b8",
     *                 "taskName": "结算审批",
     *                 "taskDefKey": "task_fa",
     *                 "assigneeId": "017340",
     *                 "createTime": "2019-04-04 16:16:54",
     *                 "endTime": "2019-04-04 16:17:19",
     *                 "duration": "00:00:25",
     *                 "priority": 50
     *             }
     * @throws Exception
     */
    @Override
    public List<Object> findListByOrderId(Integer orderId) throws Exception {
        if (orderId != null) {
            String eruitoken = (String) ThreadLocalUtil.getObject();
            Order order = orderService.findById(orderId);
            String processId = order.getProcessId();
            if (StringUtils.isBlank(processId)) {
                return null;
            }
            JSONObject jsonObject = BpmUtils.processLogs(processId, eruitoken, null);

            return jsonObject.getJSONArray("instanceLogs");
        }
        return null;
    }

    @Override
    public List<CheckLog> findListByOrderIdAndType(Integer orderId, Integer type) {
        List<CheckLog> checkLogList = null;
        if (orderId != null) {
            checkLogList = checkLogDao.findByOrderIdAndType(orderId, type);
        }
        if (checkLogList == null) {
            checkLogList = new ArrayList<>();
        }
        return checkLogList;
    }

    @Override
    public List<CheckLog> findListByJoinId(String category, Integer joinId, Integer type) {
        List<CheckLog> checkLogList = null;
        if (joinId != null) {
            checkLogList = checkLogDao.findAll(new Specification<CheckLog>() {
                @Override
                public Predicate toPredicate(Root<CheckLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<>();
                    list.add(cb.equal(root.get("category").as(String.class), category));
                    list.add(cb.equal(root.get("type").as(Integer.class), type));
                    list.add(cb.equal(root.get("joinId").as(Integer.class), joinId));
                    Predicate[] predicates = new Predicate[list.size()];
                    predicates = list.toArray(predicates);
                    return cb.and(predicates);
                }
            }, new Sort(Sort.Direction.DESC, "createTime"));
        }
        if (checkLogList == null) {
            checkLogList = new ArrayList<>();
        }
        return checkLogList;
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
                    cb.lessThanOrEqualTo(root.get("type").as(Integer.class), 2);
                    return cb.equal(root.get("orderId").as(Integer.class), orderId);
                }
            }, new Sort(Sort.Direction.DESC, "type", "auditingProcess"));
        }
        if (checkLogList == null) {
            checkLogList = new ArrayList<>();
        }
        return checkLogList;
    }


    /**
     * 根据类型和审核流程顺序从前到后排序订单的所有审核
     *
     * @param orderId
     * @return
     */
    private List<CheckLog> findListByOrderIdOrderByTypeAndAuditSeq(Integer orderId) {
        List<CheckLog> checkLogList = null;
        if (orderId != null) {
            checkLogList = checkLogDao.findAll(new Specification<CheckLog>() {
                @Override
                public Predicate toPredicate(Root<CheckLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    cb.lessThanOrEqualTo(root.get("type").as(Integer.class), 2);
                    return cb.equal(root.get("orderId").as(Integer.class), orderId);
                }
            }, new Sort(Sort.Direction.DESC, "type", "auditSeq"));
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
        Order order = null;
        if (orderId != null) {
            order = orderService.findById(orderId);
            Integer orderAuditingStatus = order.getAuditingStatus(); // 订单审核状态，如果为空说明没有任何审核进度
            if (orderAuditingStatus == null) {
                return resultCheckLogs;
            }
            List<CheckLog> checkLogList = findListByOrderIdOrderByTypeAndAuditSeq(orderId);
//            List<CheckLog> checkLogList = findListByOrderIdOrderByTypeAndAuditingProcess(orderId);
//            List<CheckLog> checkLogList = findListByOrderIdOrderByCreateTimeDesc(orderId);

            Project project = order.getProject();
            // 订单当前的审核进度
            CheckLog.AuditProcessingEnum orderCurAuditProcess = null;
            if (order != null && !StringUtils.isBlank(order.getAuditingProcess())) {
                String[] split = order.getAuditingProcess().split(",");
                int maxAuditingProcess = 0;
                for (String s : split) {
                    int i = Integer.parseInt(s);
                    if (i > maxAuditingProcess) {
                        maxAuditingProcess = i;
                    }
                }
                orderCurAuditProcess = CheckLog.AuditProcessingEnum.findEnum(1, maxAuditingProcess);
            }

            // 项目当前的审核进度
            CheckLog.AuditProcessingEnum projectCurAuditProcess = null;
            if (project != null && !StringUtils.isBlank(project.getAuditingProcess())) {
                String[] split = project.getAuditingProcess().split(",");
                int maxAuditingProcess = 0;
                for (String s : split) {
                    int i = Integer.parseInt(s);
                    if (i > maxAuditingProcess) {
                        maxAuditingProcess = i;
                    }
                }
                projectCurAuditProcess = CheckLog.AuditProcessingEnum.findEnum(2, maxAuditingProcess);
            }

            // 通过项目和订单判断可以驳回到之前的步骤列表
            for (CheckLog checkLog : checkLogList) {
                // 只查找通过和立项的审核
                if (StringUtils.equals("-1", checkLog.getOperation())) {
                    continue;
                }
                if (orderAuditingStatus == 4 && checkLog.getType() == 1) {
                    // 如果订单审核通过，则所有订单的日志需要返回
                    resultCheckLogs.add(checkLog);
                } else if (orderAuditingStatus < 4) {
                    // 如果订单还没有审核通过，则只能返回订单的审核列表
                    if (checkLog.getType() == 1 && orderCurAuditProcess != null && checkLog.getAuditSeq() < orderCurAuditProcess.getAuditSeq()) {
                        resultCheckLogs.add(checkLog);
                    }
                    continue;
                }
                if (projectCurAuditProcess != null && checkLog.getType() == 2) {
                    if (checkLog.getAuditSeq() < projectCurAuditProcess.getAuditSeq()) {
                        resultCheckLogs.add(checkLog);
                    }
                }
            }
        }
        Map<String, CheckLog> map = new LinkedMap<>();
        for (CheckLog cLog : resultCheckLogs) {
            if (map.containsKey(cLog.getAuditingProcess() + "_" + cLog.getType())) {
                map.remove(cLog.getAuditingProcess() + "_" + cLog.getType());
            }
            map.put(cLog.getAuditingProcess() + "_" + cLog.getType(), cLog);
        }
        List<CheckLog> cList = map.values().stream().collect(Collectors.toList());
        List<CheckLog> collect = cList.stream().sorted(Comparator.comparing(CheckLog::getCreateTime).reversed()).collect(Collectors.toList());
        return collect;
    }

    @Deprecated
    @Transactional(readOnly = true)
    public List<CheckLog> findPassed2(Integer orderId) {
        List<CheckLog> resultCheckLogs = new ArrayList<>();
        if (orderId != null) {
            List<CheckLog> checkLogList = findListByOrderIdOrderByTypeAndAuditingProcess(orderId);
            Order order = orderService.findById(orderId);
            Integer orderAuditingStatus = order.getAuditingStatus(); // 订单审核状态，如果为空说明没有任何审核进度
            if (orderAuditingStatus == null) {
                return resultCheckLogs;
            }
            if (order.getProject().getAuditingStatus() == 4 && "999".equals(order.getProject().getAuditingProcess())) {
                for (CheckLog checkLog : checkLogList) {
                    // 只查找通过和立项的审核
                    if (!checkLog.getOperation().equals("-1")) {
                        resultCheckLogs.add(checkLog);
                    }
                }
            } else {
                return resultCheckLogs;
            }
            Map<String, CheckLog> map = new LinkedMap<>();
            for (CheckLog cLog : resultCheckLogs) {
                if (map.containsKey(cLog.getAuditingProcess())) {
                    map.remove(cLog.getAuditingProcess());
                    map.put(cLog.getAuditingProcess().toString(), cLog);
                } else {
                    map.put(cLog.getAuditingProcess().toString(), cLog);
                }
            }
            List<CheckLog> cList = map.values().stream().collect(Collectors.toList());
            List<CheckLog> collect = cList.stream().sorted(Comparator.comparing(CheckLog::getCreateTime).reversed()).collect(Collectors.toList());
            return collect;
        }
        return null;
    }

    @Override
    public List<CheckLog> findCheckLogsByPurchId(int purchId) {
        return checkLogDao.findByJoinIdAndCategoryOrderByCreateTime(purchId, "PURCH");
    }
}