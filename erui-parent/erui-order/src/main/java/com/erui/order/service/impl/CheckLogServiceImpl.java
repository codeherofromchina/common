package com.erui.order.service.impl;

import com.erui.order.dao.CheckLogDao;
import com.erui.order.entity.CheckLog;
import com.erui.order.service.CheckLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
public class CheckLogServiceImpl implements CheckLogService {
    @Autowired
    private CheckLogDao checkLogDao;

    /**
     * 查找最近的一个审核记录
     *
     * @param type  1:订单  2：项目
     * @param refId 项目或订单ID，根据type
     * @return
     */
    @Override
    public CheckLog findLastLog(int type, Integer refId) {
        PageRequest request = new PageRequest(0, 1, Sort.Direction.DESC, "createTime");
        CheckLog example = new CheckLog();
        example.setType(type);
        example.setOrderId(refId);
        Page<CheckLog> all = checkLogDao.findAll(Example.of(example), request);
        if (all != null && all.getSize() > 0) {
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
        if (orderId != null && checkLogDao.findByOrderId(orderId).size() > 0) {
            return checkLogDao.findByOrderId(orderId);
        }
        return null;
    }

    @Override
    public List<CheckLog> findPassed(Integer orderId) {
        PageRequest request = new PageRequest(0, 100, Sort.Direction.DESC, "createTime");
        Page<CheckLog> all = checkLogDao.findAll(new Specification<CheckLog>() {
            List<Predicate> list = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<CheckLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                // 根据销售同号模糊查询
                if (orderId != null) {
                    list.add(cb.equal(root.get("orderId").as(Integer.class), orderId));
                }
                list.add(cb.equal(root.get("type").as(Integer.class), 1));
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
