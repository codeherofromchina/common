package com.erui.order.service.impl;

import com.erui.order.dao.CheckLogDao;
import com.erui.order.entity.CheckLog;
import com.erui.order.service.CheckLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
     *
     * @param type  1:订单  2：项目
     * @param refId 项目或订单ID，根据type
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

    @Override
    public List<CheckLog> findListByOrderId(Integer orderId) {
        if (orderId != null && checkLogDao.findByOrderId(orderId).size() > 0) {
            return checkLogDao.findByOrderId(orderId);
        }
        return null;
    }

    @Override
    public List<CheckLog> findPassed(Integer orderId) {
        PageRequest request = new PageRequest(1, 10, Sort.Direction.DESC, "createTime");
        CheckLog example = new CheckLog();
        example.setOrderId(orderId);
        example.setOperation("2");
        Page<CheckLog> all = checkLogDao.findAll(Example.of(example), request);
        List<CheckLog> checkLogList = all.getContent();
        if (checkLogList != null && checkLogList.size() > 0) {
            return checkLogList;
        }
        return null;
    }
}
