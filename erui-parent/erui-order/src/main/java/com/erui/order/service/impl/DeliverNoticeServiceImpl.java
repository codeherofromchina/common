package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.DeliverConsignDao;
import com.erui.order.dao.DeliverDetailDao;
import com.erui.order.dao.DeliverNoticeDao;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.DeliverNoticeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.out;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class DeliverNoticeServiceImpl implements DeliverNoticeService {

    @Autowired
    private DeliverNoticeDao deliverNoticeDao;
    @Autowired
    private DeliverConsignDao deliverConsignDao;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private DeliverDetailDao deliverDetailDao;


    @Override
    public DeliverNotice findById(Integer id) {
        return deliverNoticeDao.findOne(id);
    }




    /**
     * 看货通知管理
     * @param condition
     * @return
     */
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
                }
                notice.setDeliverConsignNo(StringUtils.join(deliverConsignNos,","));
                notice.setContractNo(StringUtils.join(contractNos,","));
            });
        }
        return page;
    }


    //添加
    @Override
    public boolean addexitRequisition(DeliverNotice deliverNotice) {

        String[] split = deliverNotice.getDeliverConsignNo().split(",");
        DeliverConsign deliverConsign = null;
        Set<DeliverConsign> list = new HashSet<DeliverConsign>();
        for (String s :split){
            deliverConsign = new DeliverConsign();
            deliverConsign.setId(Integer.parseInt(s));
            list.add(deliverConsign);
            DeliverConsign one = deliverConsignDao.findOne(Integer.parseInt(s));    //改变出口单状态
            one.setDeliverYn(2);
            deliverConsignDao.saveAndFlush(one);
        }

        deliverNotice.setDeliverConsigns(list);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmdd");
        String format = simpleDateFormat.format(new Date());
        deliverNotice.setDeliverNoticeNo(format);

        // 处理附件信息
        List<Attachment> attachmentlist = attachmentService.handleParamAttachment(null, new ArrayList(deliverNotice.getAttachmentSet()), deliverNotice.getCreateUserId(), deliverNotice.getCreateUserName());
        deliverNotice.setAttachmentSet(new HashSet<>(attachmentlist));

        deliverNoticeDao.saveAndFlush(deliverNotice);

        //推送到出库管理
        if (deliverNotice.getStatus() == 2){
            DeliverDetail deliverDetail = new DeliverDetail();

            deliverDetail.setDeliverNotice(deliverNotice);

            SimpleDateFormat simpleDateFormats = new SimpleDateFormat("yyyymmdd");
            String formats = simpleDateFormats.format(new Date());
            deliverDetail.setProductDischargedNo(formats);   //产品放行单

            deliverDetailDao.saveAndFlush(deliverDetail);
        }

        return true;
    }


    //编辑/保存
    @Override
    @Transactional
    public boolean updateexitRequisition(DeliverNotice deliverNotice) {
        DeliverNotice one = deliverNoticeDao.findOne(deliverNotice.getId());
        if(one == null){
            return false;
        }
           try {
                if (StringUtil.isNotBlank(deliverNotice.getContractNo())){
                    one.setContractNo(deliverNotice.getContractNo());
                }
                if(StringUtil.isNotBlank(deliverNotice.getDeliverConsignNo())){


                    Set<DeliverConsign> deliverConsigns = one.getDeliverConsigns(); //已存在的关联关系
                    for (DeliverConsign deliverConsign : deliverConsigns){
                        DeliverConsign ones = deliverConsignDao.findOne(deliverConsign.getId());    //改变出口单状态
                        ones.setDeliverYn(1);
                        deliverConsignDao.saveAndFlush(ones);
                    }

                    String[] split = deliverNotice.getDeliverConsignNo().split(",");    //选中的关联关系
                    DeliverConsign deliverConsign = null;
                    Set<DeliverConsign> list = new HashSet<DeliverConsign>();
                    for (String s :split){
                        list.add(deliverConsignDao.findOne(Integer.parseInt(s)));
                        DeliverConsign ones = deliverConsignDao.findOne(Integer.parseInt(s));   //改变出口单状态
                        ones.setDeliverYn(2);
                        deliverConsignDao.saveAndFlush(ones);
                    }
                    one.setDeliverConsigns(list);

                }
                if(deliverNotice.getSenderId() != null){
                    one.setSenderId(deliverNotice.getSenderId());
                }
                if(StringUtil.isNotBlank(deliverNotice.getSenderName())){
                    one.setSenderName(deliverNotice.getSenderName());
                }
                if(deliverNotice.getUrgency() != null){
                    one.setUrgency(deliverNotice.getUrgency());
                }
                if(deliverNotice.getDeliveryDate() != null){
                    one.setDeliveryDate(deliverNotice.getDeliveryDate());
                }

                one.setStatus(deliverNotice.getStatus());

                // 处理附件信息
                List<Attachment> attachmentlist = attachmentService.handleParamAttachment(new ArrayList<>(one.getAttachmentSet()), new ArrayList(deliverNotice.getAttachmentSet()), deliverNotice.getCreateUserId(), deliverNotice.getCreateUserName());
                one.setAttachmentSet(new HashSet<>(attachmentlist));

                deliverNoticeDao.saveAndFlush(one);

                //推送到出库管理
                if (deliverNotice.getStatus() == 2){
                    DeliverDetail deliverDetail = new DeliverDetail();
                    deliverDetail.setDeliverNotice(deliverNotice);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmdd");
                    String format = simpleDateFormat.format(new Date());
                    deliverDetail.setProductDischargedNo(format);   //产品放行单

                    deliverDetailDao.saveAndFlush(deliverDetail);
                }

                return true;
            }catch (Exception ex){
                return false;
            }
    }



    /**
     *  看货通知单号id    查询看货信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public DeliverNotice exitRequisitionQuery(Integer id) {
       DeliverNotice deliverNotice= deliverNoticeDao.findOne(id);
        deliverNotice.getDeliverConsigns().size();
       for (DeliverConsign deliverConsign : deliverNotice.getDeliverConsigns()){
           deliverConsign.getAttachmentSet().size();
           deliverConsign.getDeliverConsignGoodsSet().size();
           for (DeliverConsignGoods deliverConsignGoods:deliverConsign.getDeliverConsignGoodsSet()){
                deliverConsignGoods.getGoods().getId();
            }
            deliverConsign.getOrder().getId();
       }
        deliverNotice.getAttachmentSet().size();
        return deliverNotice;
    }


}
