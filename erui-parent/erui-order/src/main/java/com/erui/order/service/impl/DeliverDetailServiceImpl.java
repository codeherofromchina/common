package com.erui.order.service.impl;

import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.DeliverDetailDao;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.requestVo.DeliverD;
import com.erui.order.service.DeliverDetailService;
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
import java.util.Set;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class DeliverDetailServiceImpl implements DeliverDetailService {

    @Autowired
    private DeliverDetailDao deliverDetailDao;

    @Override
    public DeliverDetail findById(Integer id) {
        return deliverDetailDao.findOne(id);
    }


    @Override
    @Transactional
    public DeliverDetail findDetailById(Integer id) {
        DeliverDetail deliverDetail = deliverDetailDao.findOne(id);
        deliverDetail.getDeliverNotice();
        return deliverDetail;
    }


    /**
     * 出库管理
     *
     * @param deliverD
     * @return
     */
    @Override
    public Page<DeliverDetail> outboundManage(DeliverD deliverD) {
        PageRequest request = new PageRequest(deliverD.getPage(), deliverD.getRows(), Sort.Direction.DESC, "id");

        Page<DeliverDetail> page = deliverDetailDao.findAll(new Specification<DeliverDetail>() {
            @Override
            public Predicate toPredicate(Root<DeliverDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<>();

                // 根据产品放行单号查询
                if (StringUtil.isNotBlank(deliverD.getProductDischargedNo())) {
                    list.add(cb.like(root.get("productDischargedNo").as(String.class), "%" + deliverD.getProductDischargedNo()+ "%"));
                }

                //根据销售合同号   根据项目号
                if (StringUtil.isNotBlank(deliverD.getContractNo()) || StringUtil.isNotBlank(deliverD.getProjectNo())){
                    Join<DeliverDetail, DeliverNotice> deliverNotice = root.join("deliverNotice");
                    Join<DeliverNotice, DeliverConsign> deliverConsigns = deliverNotice.join("deliverConsigns");
                    Join<DeliverConsign, Order> order = deliverConsigns.join("order");
                    if(StringUtil.isNotBlank(deliverD.getContractNo())){
                        list.add(cb.like(order.get("contractNo").as(String.class),"%" + deliverD.getContractNo() + "%"));
                    }
                    if(StringUtil.isNotBlank(deliverD.getProjectNo())){
                        Join<Order, Project> project = order.join("project");
                        list.add(cb.like(project.get("projectNo").as(String.class),"%" + deliverD.getProjectNo() + "%"));
                    }
                }
                //根据开单日期
                if(deliverD.getBillingDate()!=null){
                    list.add(cb.equal(root.get("BillingDate").as(Date.class), deliverD.getBillingDate()));
                }
                //根据放行日期
                if(deliverD.getReleaseDate() != null){
                    list.add(cb.equal(root.get("releaseDate").as(Date.class),deliverD.getReleaseDate()));
                }
                //根据仓库经办人
                if(deliverD.getWareHouseman() != null){
                    list.add(cb.equal(root.get("wareHouseman").as(Integer.class),deliverD.getWareHouseman()));
                }
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);




        page.getContent().parallelStream().forEach(notice -> {
            List<String>  deliverConsignNos = new ArrayList<String>();
            List<String>  contractNos = new ArrayList<String>();
            Set<DeliverConsign> deliverConsigns = notice.getDeliverNotice().getDeliverConsigns();


        });



        return page;
    }


}
