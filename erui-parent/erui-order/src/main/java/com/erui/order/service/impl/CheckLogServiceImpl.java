package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.ThreadLocalUtil;
import com.erui.order.dao.CheckLogDao;
import com.erui.order.dao.OrderDao;
import com.erui.order.entity.CheckLog;
import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import com.erui.order.service.CheckLogService;
import com.erui.order.service.OrderService;
import com.erui.order.v2.model.DeliverConsign;
import com.erui.order.v2.model.Purch;
import com.erui.order.v2.service.DeliverConsignService;
import com.erui.order.v2.service.PurchService;
import com.erui.order.util.BpmUtils;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * @Author:SHIGS
 * @Description
 * @Date Created in 15:50 2018/8/27
 * @modified By
 */
@Service
@Transactional
public class CheckLogServiceImpl implements CheckLogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckLogServiceImpl.class);
    @Autowired
    private CheckLogDao checkLogDao;
    @Autowired
    private OrderService orderService;
    @Autowired
    private com.erui.order.v2.service.OrderService orderService2;
    @Autowired
    private PurchService purchService;
    @Autowired
    private DeliverConsignService deliverConsignService;

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
            List<CheckLog> checkLogList = checkLogDao.findByOrderIdOrderByCreateTimeDesc(orderId);
            if (checkLogList != null && checkLogList.size() > 0) {
                List<CheckLog> collect = checkLogList.stream().filter(vo -> vo.getType() == 1 || vo.getType() == 2).map(vo -> {
                    Integer auditingProcess = vo.getAuditingProcess();
                    CheckLog.AuditProcessingEnum anEnum = CheckLog.AuditProcessingEnum.findEnum(vo.getType(), auditingProcess);
                    if (anEnum != null) {
                        vo.setProcessName(anEnum.getName());
                    }
                    return vo;
                }).collect(Collectors.toList());
                return collect;
            }
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

    @Override
    public List<CheckLog> findAdapterListByProcessId(String processId, Integer type) {
        String eruiToken = (String) ThreadLocalUtil.getObject();
        List<CheckLog> result = null;
        if (StringUtils.isBlank(eruiToken)) {
            return result;
        }
        try {
            com.erui.order.v2.model.Order order2 = orderService2.findOrderByProcessId(processId);
            Integer orderId = null;
            Integer joinId = null;
            if (order2 != null) {
                orderId = order2.getId();
                joinId = orderId;
            }
            Purch purch = purchService.findPurchByProcessId(processId);
            if (purch != null) {
                joinId = purch.getId();
            }
            DeliverConsign deliverConsign = deliverConsignService.findByProcessInstanceId(processId);
            if (deliverConsign != null) {
                joinId = deliverConsign.getId();
            }
            // 查询业务流中的审核日志信息
            JSONObject jsonObject = BpmUtils.processLogs(processId, eruiToken, null);
            JSONArray data = jsonObject.getJSONArray("instanceLogs");
            if (data != null && data.size() > 0) {
                result = new ArrayList<>();
                for (int n = 0; n < data.size(); ++n) {
                    CheckLog tmp = coverBpmLog2CheckLog(data.getJSONObject(n), type);
                    tmp.setOrderId(orderId);
                    tmp.setJoinId(joinId);
                    result.add(tmp);
                }
                Collections.reverse(result);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("调用BPM业务流数据审核日志失败processId:{},eruiToken:{}", processId, eruiToken);
        }
        return result;
    }

    private static CheckLog coverBpmLog2CheckLog(JSONObject bpmLog, Integer type) {
        CheckLog checkLog = new CheckLog();
        checkLog.setCreateTime(bpmLog.getDate("createTime"));
        Integer auditProcess = null;
        switch (type) {
            case 1: // 订单
                auditProcess = newCheckLog2oldCheckLogOrderMap.get(bpmLog.getString("taskDefKey"));
                break;
            case 2:// 采购订单
                auditProcess = newCheckLog2oldCheckLogPurchMap.get(bpmLog.getString("taskDefKey"));
                break;
        }
        checkLog.setAuditingProcess(auditProcess);
        if (auditProcess != null) {
            checkLog.setType(auditProcess / 100);
        }
        checkLog.setAuditingUserName(bpmLog.getString("userName"));
        checkLog.setOperation(StringUtils.equalsIgnoreCase("APPROVED", bpmLog.getString("approvalResult")) ? "2" : "-1");
        return checkLog;
    }

    private static Map<String, Integer> newCheckLog2oldCheckLogOrderMap = new HashMap<String, Integer>() {{
        put("task_mm", Integer.valueOf(100)); // '完善订单信息'
        put("task_cm", Integer.valueOf(101)); // '国家负责人审核'
        put("task_rm", Integer.valueOf(102)); // '地区总经理审核'
        put("task_vp", Integer.valueOf(103)); // '分管领导审核'
        put("task_fn", Integer.valueOf(104)); // '融资负责人审核'
        put("task_la", Integer.valueOf(105)); // '法务负责人审核'
        put("task_fa", Integer.valueOf(106)); // '结算负责人审核'
        put("task_pm", Integer.valueOf(201)); // '事业部项目负责人审核'
        put("task_lg", Integer.valueOf(202)); // '物流经办人审核'
        put("task_pu", Integer.valueOf(204)); // '采购经办人审核'
        put("task_pc", Integer.valueOf(205)); // '品控经办人审核'
        put("task_gm", Integer.valueOf(206)); // '事业部总经理审核'
        put("task_ceo", Integer.valueOf(207)); // '总裁审核'
        put("task_ed", Integer.valueOf(208)); // '董事长审核'
        put("task_alg",Integer.valueOf(209)); // '分配物流经办人'
    }};

    private static Map<String, Integer> newCheckLog2oldCheckLogPurchMap = new HashMap<String, Integer>(){{
        put("task_pc",Integer.valueOf(20)); // '完善订单信息'
        put("task_pu",Integer.valueOf(21)); // '采购经理审核'
        put("task_pm",Integer.valueOf(22)); // '事业部项目负责人'
        put("task_la",Integer.valueOf(23)); // '法务审核'
        put("task_fa",Integer.valueOf(24)); // '财务审核'
        put("task_sm",Integer.valueOf(25)); // '供应链中心总经理审核'
        put("task_ceo",Integer.valueOf(26)); // '总裁审核'
        put("task_ed",Integer.valueOf(27)); // '董事长审核'
    }};

    /**
     * 签约主体 英文转中文
     *
     * @param signingCo 签约主体
     */
    @Override
    public String getSigningCoCn(String signingCo){
        String signingCoCn = signingCo;
        if(signingCo != null){
            switch (signingCo) {
                case "Erui International Electronic Commerce Co., Ltd.":
                    signingCoCn = "易瑞国际电子商务有限公司";
                    break;
                case "Erui International USA, LLC":
                    signingCoCn = "易瑞国际（美国）有限公司";
                    break;
                case "Erui International (Canada) Co., Ltd.":
                    signingCoCn = "易瑞国际（加拿大）有限公司";
                    break;
                case "Erui Intemational Electronic Commerce (HK) Co., Lirnited":
                    signingCoCn = "易瑞國際電子商務（香港）有限公司";
                    break;
                case "PT ERUI INTERNATIONAL INDONESIA":
                    signingCoCn = "易瑞国际印尼有限公司";
                    break;
                case "Erui Intemational Electronic Commerce (Peru) S.A.C":
                    signingCoCn = "易瑞国际电子商务（秘鲁）有限公司";
                    break;
                case "Domestic Sales Department":
                    signingCoCn = "国内销售部";
                    break;
            }
        }
        return signingCoCn;
    }
}