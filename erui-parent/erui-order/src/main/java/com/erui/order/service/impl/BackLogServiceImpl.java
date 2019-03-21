package com.erui.order.service.impl;

import com.erui.order.dao.BackLogDao;
import com.erui.order.entity.BackLog;
import com.erui.order.service.BackLogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class BackLogServiceImpl implements BackLogService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private BackLogDao backLogDao;


    /**
     * 待办列表查询
     *
     * @param backLog
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BackLog> findBackLogByList(BackLog backLog) {
        Integer page = 0;
        Integer rows = 50;
        if (backLog.getPage() != null) {
            page = backLog.getPage() - 1;
        }
        if (backLog.getRows() != null) {
            rows = backLog.getRows();
        }

        PageRequest request = new PageRequest(page, rows, Sort.Direction.DESC, "id");

        Page<BackLog> pageBackLog = backLogDao.findAll(new Specification<BackLog>() {
            @Override
            public Predicate toPredicate(Root<BackLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if (backLog.getUid() != null) {
                    list.add(cb.equal(root.get("uid").as(Integer.class), backLog.getUid()));
                }

                //根据删除标识
                list.add(cb.equal(root.get("delYn").as(Integer.class), 1));

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);

            }
        }, request);

        return pageBackLog;
    }


    /**
     * 待办事项新增
     *
     * @param backLog
     * @return
     */
    @Override
    public void addBackLogByDelYn(BackLog backLog) throws Exception {

        backLog.setCreateDate(new SimpleDateFormat("yyyyMMdd").format(new Date())); //提交时间
        if (StringUtils.isBlank(backLog.getPlaceSystem())) {
            backLog.setPlaceSystem("订单");   //所在系统
        }
        backLog.setDelYn(1);

        String informTheContent = backLog.getInformTheContent(); //返回内容

        List<BackLog> backLogList = null;
        if (backLog.getFollowId() != null) {
            backLogList = backLogDao.findByFunctionExplainIdAndHostIdAndFollowIdAndUidAndDelYnAndInformTheContent(backLog.getFunctionExplainId(), backLog.getHostId(), backLog.getFollowId(), backLog.getUid(), 1, informTheContent);
        } else {
            backLogList = backLogDao.findByFunctionExplainIdAndHostIdAndUidAndDelYnAndInformTheContent(backLog.getFunctionExplainId(), backLog.getHostId(), backLog.getUid(), 1, informTheContent);
        }

        if (backLogList.size() <= 0) {
            try {
                BackLog save = backLogDao.save(backLog);
            } catch (Exception e) {
                logger.error("新增待办事项失败：" + e);
                throw new Exception(e);
            }
        }
    }


    /**
     * 待办事项逻辑删除
     *
     * @param backLog
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBackLogByDelYn(BackLog backLog) throws Exception {
        List<BackLog> backLogList = null;
        try {
            //backLog.getFunctionExplainId() == 999 时查找未删除待办订单
            if (backLog.getFunctionExplainId() == 999) {
                backLogList = backLogDao.findByHostIdAndDelYn(backLog.getHostId(), 1);
            } else {
                backLogList = backLogDao.findByFunctionExplainIdAndHostIdAndDelYn(backLog.getFunctionExplainId(), backLog.getHostId(), 1);
            }
        } catch (Exception e) {
            logger.error("逻辑删除 - 查询待办事项失败：" + e);
            throw new Exception(e);
        }
        if (backLogList.size() > 0) {
            for (BackLog backLog1 : backLogList) {
                backLog1.setDelYn(0);
                backLog1.setDeleteTime(new Date());
            }
            try {
                backLogDao.save(backLogList);
            } catch (Exception e) {
                logger.error("逻辑删除 - 修改待办事项失败：" + e);
                throw new Exception(e);
            }
        }

    }

    /**
     * 待办事项逻辑删除
     *
     * @param backLog1
     * @param backLog2
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBackLogByDelYns(BackLog backLog1, BackLog backLog2) throws Exception {
        List<BackLog> backLogList1 = null;
        List<BackLog> backLogList2 = null;
        try {
            if (backLog1.getFunctionExplainId() == 999) {
                backLogList1 = backLogDao.findByHostIdAndDelYn(backLog1.getHostId(), 1);
            } else {
                backLogList1 = backLogDao.findByFunctionExplainIdAndHostIdAndDelYn(backLog1.getFunctionExplainId(), backLog1.getHostId(), 1);
            }
            if (backLog2.getFunctionExplainId() == 999) {
                backLogList2 = backLogDao.findByHostIdAndDelYn(backLog2.getHostId(), 1);
            } else {
                backLogList2 = backLogDao.findByFunctionExplainIdAndHostIdAndDelYn(backLog2.getFunctionExplainId(), backLog2.getHostId(), 1);
            }

            if(backLogList1 == null){
                backLogList1 = new ArrayList<BackLog>();
            }

            if(backLogList2 != null && backLogList2.size() > 0){
                backLogList1.addAll(backLogList2);
            }
        } catch (Exception e) {
            logger.error("逻辑删除 - 查询待办事项失败：" + e);
            throw new Exception(e);
        }
        if (backLogList1.size() > 0) {
            for (BackLog backLog : backLogList1) {
                backLog.setDelYn(0);
                backLog.setDeleteTime(new Date());
            }
            try {
                backLogDao.save(backLogList1);
            } catch (Exception e) {
                logger.error("逻辑删除 - 修改待办事项失败：" + e);
                throw new Exception(e);
            }
        }

    }
    /**
     * 待办事项逻辑删除
     *
     * @param backLog
     * @param auditingUserId 当前审核人id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBackLogByDelYnNew(BackLog backLog, String auditingUserId) throws Exception {
        List<BackLog> backLogList = null;
        List<BackLog> removeBackLogList = null;
        try {
            backLogList = backLogDao.findByFunctionExplainIdAndHostIdAndDelYn(backLog.getFunctionExplainId(), backLog.getHostId(), 1);
        } catch (Exception e) {
            logger.error("逻辑删除 - 查询待办事项失败：" + e);
            throw new Exception(e);
        }
        if (backLogList.size() > 0) {
            removeBackLogList = new ArrayList<BackLog>();
            for (BackLog backLog1 : backLogList) {
                if(auditingUserId.indexOf(backLog1.getUid()+"") >= 0){
                    removeBackLogList.add(backLog1);
                }else{
                    backLog1.setDelYn(0);
                    backLog1.setDeleteTime(new Date());
                }
            }
            try {
                //同级未全部审核完成，之前已经存在待办，未审核所以不删除待办。
                if(removeBackLogList != null && removeBackLogList.size() >0)
                    backLogList.removeAll(removeBackLogList);
                backLogDao.save(backLogList);
            } catch (Exception e) {
                logger.error("逻辑删除 - 修改待办事项失败：" + e);
                throw new Exception(e);
            }
        }

    }


}
