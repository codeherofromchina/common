package com.erui.order.service.impl;

import com.erui.order.dao.CheckLogDao;
import com.erui.order.entity.BackLog;
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
import java.util.ArrayList;
import java.util.List;

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
     * @param type  1:订单  2：项目
     * @param refId   项目或订单ID，根据type
     * @return
     */
    @Override
    public CheckLog findLastLog(int type, Integer refId) {
        PageRequest request = new PageRequest(1, 1, Sort.Direction.DESC, "createTime");
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
}
