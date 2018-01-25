package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.DeliverNoticeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
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
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.out;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class DeliverNoticeServiceImpl implements DeliverNoticeService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private DeliverNoticeDao deliverNoticeDao;
    @Autowired
    private DeliverConsignDao deliverConsignDao;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private DeliverDetailDao deliverDetailDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    OrderLogDao orderLogDao;

    @Autowired
    OrderDao orderDao;

    @Override
    @Transactional(readOnly = true)
    public DeliverNotice findById(Integer id) {
        return deliverNoticeDao.findOne(id);
    }




    /**
     * 看货通知管理
     * @param condition
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeliverNotice> listByPage(DeliverNotice condition) throws Exception {
        PageRequest request = new PageRequest(condition.getPage()-1, condition.getRows(), Sort.Direction.DESC, "id");

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
            for(DeliverNotice notice:page.getContent()) {
                List<String>  deliverConsignNos = new ArrayList<String>();
                List<String>  contractNos = new ArrayList<String>();

                Set<DeliverConsign> deliverConsignSet = notice.getDeliverConsigns();
                if (deliverConsignSet.size() == 0){
                    throw new Exception("无出口发货通知单关系");
                }
                for (DeliverConsign dc : deliverConsignSet) {
                    String deliverConsignNo = dc.getDeliverConsignNo();
                    if (!StringUtil.isNotBlank(deliverConsignNo)){
                        throw new Exception("无出口通知单号");
                    }
                    deliverConsignNos.add(deliverConsignNo);
                    Order order = dc.getOrder();
                    if (order == null){
                        throw new Exception("无订单关系");
                    }
                    contractNos.add(order.getContractNo());
                }
                notice.setDeliverConsignNo(StringUtils.join(deliverConsignNos,","));
                notice.setContractNo(StringUtils.join(contractNos,","));
            }
        }
        return page;
    }


    //添加
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addexitRequisition(DeliverNotice deliverNotice) {

        String[] split = deliverNotice.getDeliverConsignIds().split(",");

        List<DeliverConsignGoods> deliverConsignGoodsLists = new ArrayList<>();
        DeliverConsign deliverConsign = null;
        Set<DeliverConsign> list = new HashSet<DeliverConsign>();
        for (String s :split){
            deliverConsign = new DeliverConsign();
            deliverConsign.setId(Integer.parseInt(s));
            list.add(deliverConsign);
            DeliverConsign one = deliverConsignDao.findOne(Integer.parseInt(s));    //改变出口单状态

            Set<DeliverConsignGoods> deliverConsignGoodsSet = one.getDeliverConsignGoodsSet();
            for (DeliverConsignGoods deliverConsignGoods :deliverConsignGoodsSet){
                deliverConsignGoodsLists.add(deliverConsignGoods);
            }

            for (Goods goods : one.getOrder().getGoodsList()) {
                Goods one1 = goodsDao.findOne(goods.getId());
                one1.setSendDate(deliverNotice.getSendDate());//发送看货通知日期
                one1.setSenderId(deliverNotice.getSenderId());//订舱人id
                goodsDao.save(one1);
            };
            one.setDeliverYn(2);
            deliverConsignDao.saveAndFlush(one);
        }

        deliverNotice.setDeliverConsigns(list);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(new Date());

            //查询当天看货通知单
            List<DeliverNotice> lsit = deliverNoticeDao.findAll(new Specification<DeliverNotice>() {
                @Override
                public Predicate toPredicate(Root<DeliverNotice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<>();
                    // 根据日期查询  当天看货通知单
                    if (StringUtil.isNotBlank(format)) {
                        list.add(cb.like(root.get("deliverNoticeNo").as(String.class),  format + "%"));
                    }
                    Predicate[] predicates = new Predicate[list.size()];
                    predicates = list.toArray(predicates);
                    return cb.and(predicates);
                }
            });

            //看货通知单
            String[] str = new String[lsit.size()];
            for (int i =0 ;i<lsit.size() ;i++){
                str[i]= lsit.get(i).getDeliverNoticeNo();
            }

            if(str.length !=0){
                //最大值
                 Integer max = Integer.parseInt(str[0].replaceFirst("^0*", "").substring(8)); //0为第一个数组下标
                for (int i = 0; i < str.length; i++) {   //开始循环一维数组
                    if (max < Integer.parseInt(str[i].replaceFirst("^0*", "").substring(8))) {  //循环判断数组元素
                        max = Integer.parseInt(str[i].replaceFirst("^0*", "").substring(8)); }  //赋值给num，然后再次循环
                }

                deliverNotice.setDeliverNoticeNo(format+String.format("%04d",(max+1)));
            }else{
                deliverNotice.setDeliverNoticeNo(format+String.format("%04d",1));
             }
        // 处理附件信息
        List<Attachment> attachmentlist = attachmentService.handleParamAttachment(null, new ArrayList(deliverNotice.getAttachmentSet()), deliverNotice.getCreateUserId(), deliverNotice.getCreateUserName());
        deliverNotice.setAttachmentSet(new HashSet<>(attachmentlist));
        DeliverNotice deliverNotice1=deliverNoticeDao.saveAndFlush(deliverNotice);

        //推送到出库管理
        if (deliverNotice.getStatus() == 2){
            DeliverDetail deliverDetail = new DeliverDetail();
            deliverDetail.setDeliverNotice(deliverNotice);
            SimpleDateFormat simpleDateFormats = new SimpleDateFormat("yyyy");
            String formats = simpleDateFormats.format(new Date());
            //查询当天产品放行单
            List<DeliverDetail> lits = deliverDetailDao.findAll(new Specification<DeliverDetail>() {
                @Override
                public Predicate toPredicate(Root<DeliverDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    List<Predicate> lista = new ArrayList<>();
                    // 根据日期查询  当天产品放行单
                    if (StringUtil.isNotBlank(formats)) {
                        lista.add(cb.like(root.get("deliverDetailNo").as(String.class),  formats + "%"));
                    }
                    Predicate[] predicates = new Predicate[lista.size()];
                    predicates = lista.toArray(predicates);
                    return cb.and(predicates);
                }
            });

            //产品放行单
            String[] strs = new String[lits.size()];
            for (int i =0 ;i<lits.size() ;i++){
                strs[i]= lits.get(i).getDeliverDetailNo();
            }

            if(strs.length !=0){
                //最大值
                Integer maxs = Integer.parseInt(strs[0].replaceFirst("^0*", "").substring(4)); //0为第一个数组下标
                for (int i = 0; i < strs.length; i++) {   //开始循环一维数组
                    if (maxs < Integer.parseInt(strs[i].replaceFirst("^0*", "").substring(4))) {  //循环判断数组元素
                        maxs = Integer.parseInt(strs[i].replaceFirst("^0*", "").substring(4)); }  //赋值给num，然后再次循环
                }
                deliverDetail.setDeliverDetailNo(formats+String.format("%04d",(maxs+1)));   //产品放行单
            }else{
                deliverDetail.setDeliverDetailNo(formats+String.format("%04d",1));
            }

            deliverDetail.setStatus(DeliverDetail.StatusEnum.SAVED_OUTSTOCK.getStatusCode());
            deliverDetail.setDeliverConsignGoodsList(deliverConsignGoodsLists);
            DeliverDetail deliverDetail1=deliverDetailDao.saveAndFlush(deliverDetail);

            //  订单执行跟踪   推送运单号
            OrderLog orderLog = new OrderLog();
            Set<DeliverConsign> deliverConsigns = deliverNotice1.getDeliverConsigns();
            for (DeliverConsign deliverConsign1 : deliverConsigns){
                try {
                    orderLog.setOrder(orderDao.findOne(deliverConsign1.getOrder().getId()));
                    orderLog.setOperation(deliverDetail.getDeliverDetailNo());
                    orderLog.setCreateTime(new Date());
                    orderLogDao.save(orderLog);
                } catch (Exception ex) {
                    logger.error("日志记录失败 {}", orderLog.toString());
                    logger.error("错误", ex);
                    ex.printStackTrace();
                }
            }

        }
        return true;
    }


        //编辑/保存
    @Override
    @Transactional(rollbackFor = Exception.class)
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
                    String[] split = deliverNotice.getDeliverConsignIds().split(",");    //选中的关联关系
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
                if(deliverNotice.getNumers() != null){
                    one.setNumers(deliverNotice.getNumers());
                }
                if(StringUtil.isNotBlank(deliverNotice.getPrepareReq())){
                    one.setPrepareReq(deliverNotice.getPrepareReq());
                }
                if (StringUtil.isNotBlank(deliverNotice.getPackageReq())){
                    one.setPackageReq(deliverNotice.getPackageReq());
                }
                if(deliverNotice.getSendDate() != null){
                    one.setSendDate(deliverNotice.getSendDate());
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
                    SimpleDateFormat simpleDateFormats = new SimpleDateFormat("yyyy");
                    String formats = simpleDateFormats.format(new Date());
                    //查询当天产品放行单
                    List<DeliverDetail> lits = deliverDetailDao.findAll(new Specification<DeliverDetail>() {
                        @Override
                        public Predicate toPredicate(Root<DeliverDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                            List<Predicate> lista = new ArrayList<>();
                            // 根据日期查询  当天产品放行单
                            if (StringUtil.isNotBlank(formats)) {
                                lista.add(cb.like(root.get("deliverDetailNo").as(String.class),  formats + "%"));
                            }
                            Predicate[] predicates = new Predicate[lista.size()];
                            predicates = lista.toArray(predicates);
                            return cb.and(predicates);
                        }
                    });

                    //产品放行单
                    String[] strs = new String[lits.size()];
                    for (int i =0 ;i<lits.size() ;i++){
                        strs[i]= lits.get(i).getDeliverDetailNo();
                    }

                    if(strs.length !=0){
                        //最大值
                        Integer maxs = Integer.parseInt(strs[0].replaceFirst("^0*", "").substring(4)); //0为第一个数组下标
                        for (int i = 0; i < strs.length; i++) {   //开始循环一维数组
                            if (maxs < Integer.parseInt(strs[i].replaceFirst("^0*", "").substring(4))) {  //循环判断数组元素
                                maxs = Integer.parseInt(strs[i].replaceFirst("^0*", "").substring(4)); }  //赋值给num，然后再次循环
                        }
                        deliverDetail.setDeliverDetailNo(formats+String.format("%04d",(maxs+1)));   //产品放行单
                    }else{
                        deliverDetail.setDeliverDetailNo(formats+String.format("%04d",1));
                    }
                    deliverDetailDao.saveAndFlush(deliverDetail);



                    //  订单执行跟踪   推送运单号
                    OrderLog orderLog = new OrderLog();
                    Set<DeliverConsign> deliverConsigns = one.getDeliverConsigns();
                    for (DeliverConsign deliverConsign1 : deliverConsigns){
                        try {
                            orderLog.setOrder(orderDao.findOne(deliverConsign1.getOrder().getId()));
                            orderLog.setOperation(deliverDetail.getDeliverDetailNo());
                            orderLog.setCreateTime(new Date());
                            orderLogDao.save(orderLog);
                        } catch (Exception ex) {
                            logger.error("日志记录失败 {}", orderLog.toString());
                            logger.error("错误", ex);
                            ex.printStackTrace();
                        }
                    }


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
    @Transactional(readOnly = true)
    public DeliverNotice exitRequisitionQuery(Integer id) {
       DeliverNotice deliverNotice= deliverNoticeDao.findOne(id);
        deliverNotice.getDeliverConsigns().size();
       for (DeliverConsign deliverConsign : deliverNotice.getDeliverConsigns()){
           deliverConsign.getAttachmentSet().size();
           deliverConsign.getDeliverConsignGoodsSet().size();
           for (DeliverConsignGoods deliverConsignGoods:deliverConsign.getDeliverConsignGoodsSet()){
                deliverConsignGoods.getGoods().getId();
            }
            deliverConsign.getOrder().getProject().getId();
       }
        deliverNotice.getAttachmentSet().size();
        return deliverNotice;
    }


}
