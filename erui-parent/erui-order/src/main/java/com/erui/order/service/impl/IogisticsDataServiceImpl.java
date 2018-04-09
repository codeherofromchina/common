package com.erui.order.service.impl;

import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.IogisticsDao;
import com.erui.order.dao.IogisticsDataDao;
import com.erui.order.entity.DeliverDetail;
import com.erui.order.entity.Iogistics;
import com.erui.order.service.IogisticsDataService;
import com.erui.order.service.IogisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class IogisticsDataServiceImpl implements IogisticsDataService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private IogisticsDataDao iogisticsDataDao;

   /* *//**
     * 物流跟踪管理（V 2.0）   列表页查询
     *
     * @param
     * @return
     *//*
    @Override
    public Page<Iogistics> trackingList(Iogistics iogistics) {

        PageRequest request = new PageRequest(iogistics.getPage()-1, iogistics.getRows(), Sort.Direction.DESC, "id");

        Page<Iogistics> page = iogisticsDao.findAll(new Specification<Iogistics>() {
            @Override
            public Predicate toPredicate(Root<Iogistics> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();



                //根据 销售合同号
                if(StringUtil.isNotBlank(iogistics.getContractNo())){
                    list.add(cb.like(root.get("contractNo").as(String.class),"%"+iogistics.getContractNo()+"%"));
                }

              *//*  Join<Iogistics, DeliverDetail> deliverDetailRoot = root.join("deliverDetail"); //获取出库

                //根据放行日期
                if (iogistics.getReleaseDate() != null) {
                    list.add(cb.equal(deliverDetailRoot.get("releaseDate").as(Date.class), iogistics.getReleaseDate()));
                }
*//*
                //根据物流经办人id
                if (iogistics.getLogisticsUserId() != null) {
                    list.add(cb.equal(root.get("logisticsUserId").as(Integer.class), iogistics.getLogisticsUserId()));
                }

                //根据 物流经办日期
                if (iogistics.getLogisticsDate() != null) {
                    list.add(cb.equal(root.get("logisticsDate").as(Date.class), iogistics.getLogisticsDate()));
                }

                //根据 实际完成时间
                if (iogistics.getAccomplishDate() != null) {
                    list.add(cb.equal(root.get("accomplishDate").as(Date.class), iogistics.getAccomplishDate()));
                }

                //'物流的状态   ( 5：确认出库 )   6:合并物流信息(待执行) 7：完善物流状态中（执行中） 8：项目完结'（已完成）,
                if(iogistics.getStatus() != null){
                    list.add(cb.equal(root.get("status").as(Integer.class), iogistics.getStatus()));
                }else{  //查询 6,7,8
                    list.add(cb.greaterThan(root.get("status").as(Integer.class), Integer.valueOf(5)));  //大于5
                }


                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);

            }
        }, request);

        return page;
    }

    *//**
     * 物流跟踪管理（V 2.0）   列表页查询  （查询列表信息）
     *
     * @param id
     * @return
     *//*
    @Override
    public  List<Iogistics> findByPid(Integer id) {
        List<Iogistics> allByPid = iogisticsDao.findByPid(id);
        return allByPid;
    }*/


}
