package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.AreaDao;
import com.erui.order.dao.DeliverNoticeDao;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.service.AreaService;
import com.erui.order.service.DeliverNoticeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class DeliverNoticeServiceImpl implements DeliverNoticeService {

    @Autowired
    private DeliverNoticeDao deliverNoticeDao;

    @Override
    public DeliverNotice findById(Integer id) {
        return deliverNoticeDao.findOne(id);
    }

    @Override
    @Transactional
    public Page<DeliverNotice> listByPage(DeliverNotice condition) {
        PageRequest request = new PageRequest(condition.getPage(), condition.getRows(), Sort.Direction.DESC, "id");

        Page<DeliverNotice> page = deliverNoticeDao.findAll(new Specification<DeliverNotice>() {
            @Override
            public Predicate toPredicate(Root<DeliverNotice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<>();

                // 根据看货通知单号查询
                if (StringUtil.isNotBlank(condition.getDeliverNoticeNo())) {
                    list.add(cb.like(root.get("deliverNoticeNo").as(String.class), "%" + condition.getDeliverNoticeNo() + "%"));
                }

                // 根据销售合同号查询 或 出口发货通知单号查询
                if (StringUtil.isNotBlank(condition.getContractNo()) || StringUtil.isNotBlank(condition.getDeliverConsignNo())) {
                    Join<DeliverNotice, DeliverConsign> deliverConsignRoot = root.join("deliverConsigns");

                    if (StringUtil.isNotBlank(condition.getDeliverConsignNo())) {
                        list.add(cb.like(deliverConsignRoot.get("deliverConsignNo").as(String.class), "%" + condition.getDeliverConsignNo() + "%"));
                    }

                    if (StringUtil.isNotBlank(condition.getContractNo())) {
                        Join<DeliverConsign, Order> orderRoot = deliverConsignRoot.join("order");
                        list.add(cb.like(orderRoot.get("contractNo").as(String.class), "%" + condition.getContractNo() + "%"));
                    }
                }
                // 根据下单人查询
                if (condition.getSenderId() != null) {
                    list.add(cb.equal(root.get("senderId").as(Integer.class), condition.getSenderId()));
                }
                // 根据下单日期查询
                if (condition.getSendDate() != null) {
                    list.add(cb.equal(root.get("sendDate").as(Date.class), NewDateUtil.getDate(condition.getSendDate())));
                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);

        if (page.hasContent()) {
            page.getContent().parallelStream().forEach(notice -> {
                List<String>  deliverConsignNos = new ArrayList<String>();
                List<String>  contractNos = new ArrayList<String>();

                Set<DeliverConsign> deliverConsignSet = notice.getDeliverConsigns();
                for (DeliverConsign dc : deliverConsignSet) {
                    deliverConsignNos.add(dc.getDeliverConsignNo());
                    Order order = dc.getOrder();
                    contractNos.add(order.getContractNo());
                }
                notice.setDeliverConsignNo(StringUtils.join(deliverConsignNos,","));
                notice.setContractNo(StringUtils.join(contractNos,","));
            });
        }


        return page;
    }


}
