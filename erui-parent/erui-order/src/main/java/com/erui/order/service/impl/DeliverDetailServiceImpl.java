package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.EruitokenUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.requestVo.DeliverD;
import com.erui.order.requestVo.DeliverDetailVo;
import com.erui.order.requestVo.DeliverW;
import com.erui.order.service.DeliverDetailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class DeliverDetailServiceImpl implements DeliverDetailService {
    private static Logger logger = LoggerFactory.getLogger(DeliverDetailServiceImpl.class);

    @Autowired
    private DeliverDetailDao deliverDetailDao;

    @Autowired
    private AttachmentServiceImpl attachmentService;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    OrderLogDao orderLogDao;

    @Autowired
    DeliverConsignGoodsDao deliverConsignGoodsDao;

    @Autowired
    GoodsDao goodsDao;

    @Autowired
    DeliverConsignDao deliverConsignDao;

    @Autowired
    DeliverNoticeDao deliverNoticeDao;

    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口

    @Value("#{orderProp[SEND_SMS]}")
    private String sendSms;  //发短信接口

    @Override
    @Transactional(readOnly = true)
    public DeliverDetail findById(Integer id) {
        return deliverDetailDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeliverDetail> listByPage(DeliverDetailVo condition) {
        PageRequest request = new PageRequest(condition.getPage() - 1, condition.getPageSize(), Sort.Direction.DESC, "createTime");

        Page<DeliverDetail> page = deliverDetailDao.findAll(new Specification<DeliverDetail>() {
            @Override
            public Predicate toPredicate(Root<DeliverDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (condition.getOpType() == 2) { // 查询出库质检列表
                    list.add(cb.greaterThan(root.get("status").as(Integer.class), Integer.valueOf(1)));
                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);

        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public DeliverDetail findDetailById(Integer id) {
        DeliverDetail deliverDetail = deliverDetailDao.findOne(id);
        deliverDetail.getDeliverNotice().getId();
        deliverDetail.getAttachmentList().size();
        deliverDetail.getDeliverConsignGoodsList().size();
        return deliverDetail;
    }

    /**
     * @param deliverDetailVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(DeliverDetailVo deliverDetailVo) throws Exception {
        DeliverDetail deliverDetail = null;
        Integer deliverDetailId = deliverDetailVo.getId();

        if (deliverDetailId != null) {
            deliverDetail = deliverDetailDao.findOne(deliverDetailId);
        } else {
            deliverDetail = new DeliverDetail();
        }

        deliverDetail.setStatus(deliverDetail.getStatus());


        deliverDetailDao.save(deliverDetail);


        return true;
    }


    /**
     * 出库管理
     *
     * @param deliverD
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeliverDetail> outboundManage(DeliverD deliverD) throws Exception {
        PageRequest request = new PageRequest(deliverD.getPage() - 1, deliverD.getRows(), Sort.Direction.DESC, "id");

        Page<DeliverDetail> page = deliverDetailDao.findAll(new Specification<DeliverDetail>() {
            @Override
            public Predicate toPredicate(Root<DeliverDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<>();

                // 根据产品放行单号查询
                if (StringUtil.isNotBlank(deliverD.getDeliverDetailNo())) {
                    list.add(cb.like(root.get("deliverDetailNo").as(String.class), "%" + deliverD.getDeliverDetailNo() + "%"));
                }

                //根据销售合同号   根据项目号
                if (StringUtil.isNotBlank(deliverD.getContractNo()) || StringUtil.isNotBlank(deliverD.getProjectNo())) {
                    Join<DeliverDetail, DeliverNotice> deliverNotice = root.join("deliverNotice");
                    Join<DeliverNotice, DeliverConsign> deliverConsigns = deliverNotice.join("deliverConsigns");
                    Join<DeliverConsign, Order> order = deliverConsigns.join("order");
                    if (StringUtil.isNotBlank(deliverD.getContractNo())) {
                        list.add(cb.like(order.get("contractNo").as(String.class), "%" + deliverD.getContractNo() + "%"));
                    }
                    if (StringUtil.isNotBlank(deliverD.getProjectNo())) {
                        Join<Order, Project> project = order.join("project");
                        list.add(cb.like(project.get("projectNo").as(String.class), "%" + deliverD.getProjectNo() + "%"));
                    }
                }
                //根据开单日期
                if (deliverD.getBillingDate() != null) {
                    list.add(cb.equal(root.get("billingDate").as(Date.class), deliverD.getBillingDate()));
                }
                //根据放行日期
                if (deliverD.getReleaseDate() != null) {
                    list.add(cb.equal(root.get("releaseDate").as(Date.class), deliverD.getReleaseDate()));
                }
                //根据仓库经办人
                if (deliverD.getWareHouseman() != null) {
                    Join<DeliverDetail, DeliverNotice> deliverDetailRoot = root.join("deliverNotice");
                    Join<DeliverNotice, DeliverConsign> deliverConsignRoot = deliverDetailRoot.join("deliverConsigns");
                    Join<DeliverConsign, Order> orderRoot = deliverConsignRoot.join("order");
                    Join<Order, Project> projectRoot = orderRoot.join("project");
                    list.add(cb.equal(projectRoot.get("warehouseUid").as(Integer.class), deliverD.getWareHouseman()));
                }
                //根据出库状态   status    1：未质检    2：质检中   3：质检完成   4：已出库
                if (deliverD.getStatus() != null) {
                    if (deliverD.getStatus() == 1) {
                        list.add(cb.lessThan(root.get("status").as(Integer.class), 2)); //未质检
                    } else if (deliverD.getStatus() == 2) {
                        list.add(cb.greaterThan(root.get("status").as(Integer.class), 1));//质检中
                        list.add(cb.lessThan(root.get("status").as(Integer.class), 4));
                    } else if (deliverD.getStatus() == 3) {
                        list.add(cb.greaterThan(root.get("status").as(Integer.class), 3));//质检完成
                        list.add(cb.lessThan(root.get("status").as(Integer.class), 5));
                    } else if (deliverD.getStatus() == 4) {
                        list.add(cb.greaterThan(root.get("status").as(Integer.class), 4));//已出库
                    }
                }
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);


        if (page.hasContent()) {
            for (DeliverDetail notice : page.getContent()) {
                // 列表报错后添加
                notice.setAttachmentList(null);
                notice.setDeliverConsignGoodsList(null);

                List<String> contractNos = new ArrayList<String>();    //销售合同号
                List<String> projectNos = new ArrayList<String>();     //项目号
                DeliverNotice deliverNotice = notice.getDeliverNotice();
                if (deliverNotice == null) {
                    throw new Exception("产品放行单号:" + notice.getDeliverDetailNo() + " 无看货通知单关系");
                }
                List<DeliverConsign> deliverConsigns = deliverNotice.getDeliverConsigns();
                if (deliverConsigns.size() == 0) {
                    throw new Exception("看货通知单号:" + deliverNotice.getDeliverConsignNo() + " 无出口发货通知单关系");
                }
                for (DeliverConsign deliverConsign : deliverConsigns) {
                    Order order = deliverConsign.getOrder();
                    if (order == null) {
                        throw new Exception("出口发货通知单号：" + deliverConsign.getDeliverConsignNo() + " 缺少订单关系");
                    }
                    contractNos.add(order.getContractNo());  //销售合同号
                    Project project = order.getProject();
                    if (project == null) {
                        throw new Exception("订单：" + order.getContractNo() + " 号缺少项目信息");
                    }
                    projectNos.add(project.getProjectNo()); //项目号
                }
                notice.setContractNo(StringUtils.join(contractNos, ","));
                notice.setProjectNo(StringUtils.join(projectNos, ","));
            }
        }

        return page;
    }


    /**
     * 物流跟踪管理
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeliverDetail> logisticsTraceManage(DeliverW deliverW) {
        PageRequest request = new PageRequest(deliverW.getPage() - 1, deliverW.getRows(), Sort.Direction.DESC, "id");

        Page<DeliverDetail> page = deliverDetailDao.findAll(new Specification<DeliverDetail>() {
            @Override
            public Predicate toPredicate(Root<DeliverDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据产品放行单号查询
                if (StringUtil.isNotBlank(deliverW.getDeliverDetailNo())) {
                    list.add(cb.like(root.get("deliverDetailNo").as(String.class), "%" + deliverW.getDeliverDetailNo() + "%"));
                }
                //根据销售合同号
                if (StringUtil.isNotBlank(deliverW.getContractNo())) {
                    Join<DeliverDetail, DeliverNotice> deliverNotice = root.join("deliverNotice");
                    Join<DeliverNotice, DeliverConsign> deliverConsigns = deliverNotice.join("deliverConsigns");
                    Join<DeliverConsign, Order> order = deliverConsigns.join("order");

                    list.add(cb.like(order.get("contractNo").as(String.class), "%" + deliverW.getContractNo() + "%"));

                }
                //根据放行日期
                if (deliverW.getReleaseDate() != null) {
                    list.add(cb.equal(root.get("releaseDate").as(Date.class), deliverW.getReleaseDate()));
                }
                //根据物流经办人
                if (deliverW.getLogisticsUserId() != null) {
                    list.add(cb.equal(root.get("logisticsUserId").as(Integer.class), deliverW.getLogisticsUserId()));
                }
                //国际物流经办人(当前登录人id)
                if (deliverW.getLogisticsUid() != null) {
                    Join<DeliverDetail, DeliverNotice> deliverDetailRoot = root.join("deliverNotice");
                    Join<DeliverNotice, DeliverConsign> deliverConsignRoot = deliverDetailRoot.join("deliverConsigns");
                    Join<DeliverConsign, Order> orderRoot = deliverConsignRoot.join("order");
                    Join<Order, Project> projectRoot = orderRoot.join("project");
                    list.add(cb.equal(projectRoot.get("logisticsUserId").as(Integer.class), deliverW.getLogisticsUid()));
                }
                //根据经办日期
                if (deliverW.getLogisticsDate() != null) {
                    list.add(cb.equal(root.get("logisticsDate").as(Date.class), deliverW.getLogisticsDate()));
                }
                //根据状态    status:   1：待执行 5     2:执行中  6       3：已完成 7
                if (deliverW.getStatus() != null) {
                    if (deliverW.getStatus() == 1) {
                        list.add(cb.equal(root.get("status").as(Integer.class), 5));
                        /*  list.add(cb.greaterThan(root.get("status").as(Integer.class), 6));*/
                    } else if (deliverW.getStatus() == 2) {
                        list.add(cb.equal(root.get("status").as(Integer.class), 6));
                      /*  list.add(cb.greaterThan(root.get("status").as(Integer.class), 4));
                        list.add(cb.lessThan(root.get("status").as(Integer.class), 7));*/
                    } else if (deliverW.getStatus() == 3) {
                        list.add(cb.equal(root.get("status").as(Integer.class), 7));
                        /*list.add(cb.greaterThan(root.get("status").as(Integer.class), 6));*/
                    }
                } else {
                    list.add(cb.greaterThan(root.get("status").as(Integer.class), 4));
                }


                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);


        if (page.hasContent()) {
            for (DeliverDetail notice : page.getContent()) {
                List<String> contractNos = new ArrayList<String>();    //销售合同号
                List<DeliverConsign> deliverConsigns = notice.getDeliverNotice().getDeliverConsigns();
                for (DeliverConsign deliverConsign : deliverConsigns) {
                    Order order = deliverConsign.getOrder();
                    contractNos.add(order.getContractNo());  //销售合同号
                }
                notice.setContractNo(StringUtils.join(contractNos, ","));
            }
        }

        return page;
    }


    /**
     * 出库详情页 保存 or 提交质检
     *
     * @param deliverDetail
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean outboundSaveOrAdd(DeliverDetail deliverDetail) throws Exception {

        //商品备注
        List<DeliverConsignGoods> deliverConsignGoodsList = deliverDetail.getDeliverConsignGoodsList();
        if (deliverConsignGoodsList.size() != 0) {
            for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsList) {
                DeliverConsignGoods one = deliverConsignGoodsDao.findOne(deliverConsignGoods.getId());
                one.setOutboundRemark(deliverConsignGoods.getOutboundRemark());
                deliverConsignGoodsDao.saveAndFlush(one);
            }
        }

        DeliverDetail one = deliverDetailDao.findOne(deliverDetail.getId());

        if (one == null) {
            throw new Exception("查询不到出库信息");
        }
        //开单日期
        if (deliverDetail.getBillingDate() != null) {
            one.setBillingDate(deliverDetail.getBillingDate());
        }
        //承运单位名称
        if (StringUtil.isNotBlank(deliverDetail.getCarrierCo())) {
            one.setCarrierCo(deliverDetail.getCarrierCo());
        }
        //司机姓名
        if (StringUtil.isNotBlank(deliverDetail.getDriver())) {
            one.setDriver(deliverDetail.getDriver());
        }
        //车牌号码
        if (StringUtil.isNotBlank(deliverDetail.getPlateNo())) {
            one.setPlateNo(deliverDetail.getPlateNo());
        }
        //取货日期
        if (deliverDetail.getPickupDate() != null) {
            one.setPickupDate(deliverDetail.getPickupDate());
        }
        //联系电话
        if (StringUtil.isNotBlank(deliverDetail.getContactTel())) {
            one.setContactTel(deliverDetail.getContactTel());
        }
        //仓库经办人
        if (deliverDetail.getWareHouseman() != null) {
            one.setWareHouseman(deliverDetail.getWareHouseman());
               /* List<DeliverConsignGoods> deliverConsignGoodsList1 = one.getDeliverConsignGoodsList();
                for (DeliverConsignGoods deliverConsignGoods :deliverConsignGoodsList1){
                    Goods goods = deliverConsignGoods.getGoods();
                    Goods one1 = goodsDao.findOne(goods.getId());
                    one1.setUid(deliverDetail.getWareHouseman());//推送  仓库经办人  到商品表
                    goodsDao.save(one1);
                }*/
        }

        //推送  仓库经办人  到商品表
        List<DeliverConsignGoods> deliverConsignGoodsList1 = one.getDeliverConsignGoodsList();
        for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsList1) {
            Project project = deliverConsignGoods.getDeliverConsign().getOrder().getProject();  //获取到订单信息
            Goods one1 = goodsDao.findOne(deliverConsignGoods.getGoods().getId());   //查询到商品信息
            if (project.getWarehouseUid() == null) {
                one1.setUid(project.getWarehouseUid());//推送  仓库经办人  到商品表
                goodsDao.save(one1);
            }
        }

        //仓库经办人姓名
        if (StringUtil.isNotBlank(deliverDetail.getWareHousemanName())) {
            one.setWareHousemanName(deliverDetail.getWareHousemanName());
        }
        //发运日期
        if (deliverDetail.getSendDate() != null) {
            one.setSendDate(deliverDetail.getSendDate());
        }
        //发运人员id
        if (deliverDetail.getSender() != null) {
            one.setSender(deliverDetail.getSender());
        }
        //发运人员姓名
        if (StringUtil.isNotBlank(deliverDetail.getSenderName())) {
            one.setSenderName(deliverDetail.getSenderName());
        }
        //协办/复核人
        if (deliverDetail.getReviewer() != null) {
            one.setReviewer(deliverDetail.getReviewer());
        }
        //协办/复核人名字
        if (StringUtil.isNotBlank(deliverDetail.getReviewerName())) {
            one.setReviewerName(deliverDetail.getReviewerName());
        }
        //总包装件数
        if (deliverDetail.getPackTotal() != null) {
            one.setPackTotal(deliverDetail.getPackTotal());
        }
        //经办部门
        if (StringUtil.isNotBlank(deliverDetail.getHandleDepartment())) {
            one.setHandleDepartment(deliverDetail.getHandleDepartment());
        }

        DeliverNotice deliverNotice = one.getDeliverNotice();
        if (StringUtil.isNotBlank(deliverDetail.getPackageReq())) {
            deliverNotice.setPackageReq(deliverDetail.getPackageReq());
        }
        if (StringUtil.isNotBlank(deliverDetail.getPrepareReq())) {
            deliverNotice.setPrepareReq(deliverDetail.getPrepareReq());
        }
        //状态
        one.setStatus(deliverDetail.getStatus());
        Integer status = deliverDetail.getStatus(); //状态

        Project project = null; //项目信息

        //出库通知：通知质检经办人办理质检质检经办人
        if(status == 2){
            //获取项目信息
            List<DeliverConsign> deliverConsigns = one.getDeliverNotice().getDeliverConsigns();
            for(DeliverConsign deliverConsign : deliverConsigns){
                project=project==null?deliverConsign.getOrder().getProject():project;
            }

            Map<String,Object> map = new HashMap<>();
            map.put("qualityUid",project.getQualityUid());       //检质检经办人id
            map.put("projectNo", project.getProjectNo());        //项目号
            map.put("deliverDetailNo",one.getDeliverDetailNo());        //产品放行单号
            map.put("status",2);        //发送短信标识
            sendSms(map);

        }

        if (status == 5) {
            List<DeliverConsign> deliverConsigns = one.getDeliverNotice().getDeliverConsigns();
            for (DeliverConsign deliverConsign : deliverConsigns) {
                //推送商品出库
                   /* orderService.addLog(OrderLog.LogTypeEnum.GOODOUT,deliverConsign.getOrder().getId(),null,null);  */

                //  订单执行跟踪   推送运单号
                OrderLog orderLog = new OrderLog();
                try {
                    orderLog.setOrder(orderDao.findOne(deliverConsign.getOrder().getId()));
                    orderLog.setLogType(OrderLog.LogTypeEnum.GOODOUT.getCode());
                    orderLog.setOperation(StringUtils.defaultIfBlank(null, OrderLog.LogTypeEnum.GOODOUT.getMsg()));
                    orderLog.setCreateTime(new Date());
                    orderLog.setBusinessDate(one.getReleaseDate()); //放行日期
                    orderLog.setOrdersGoodsId(null);
                    orderLogDao.save(orderLog);
                } catch (Exception ex) {
                    logger.error("日志记录失败 {}", orderLog.toString());
                    logger.error("错误", ex);
                    ex.printStackTrace();
                }

            }

            //出库通知：出库单下达后通知物流经办人（确认出库）
            //获取项目信息
            for(DeliverConsign deliverConsign : deliverConsigns){
                project=project==null?deliverConsign.getOrder().getProject():project;
            }

            Map<String,Object> map = new HashMap<>();
            map.put("qualityUid",project.getLogisticsUid());       //物流经办人id
            map.put("projectNo", project.getContractNo());        //销售合同号
            map.put("deliverDetailNo",one.getDeliverDetailNo());        //产品放行单号
            map.put("wareHousemanName",one.getWareHousemanName());        //仓储经办人名字
            map.put("status",5);        //发送短信标识
            sendSms(map);


        }

        // 只接受仓储物流部的附件
        List<Attachment> collect = deliverDetail.getAttachmentList().stream().filter(attachment -> {
            return "仓储物流部".equals(attachment.getGroup());
        }).collect(Collectors.toList());

        List<Attachment> attachmentList = new ArrayList(one.getAttachmentList());

        List<Attachment> attachmentList02 = new ArrayList<>();
        Iterator<Attachment> iterator = attachmentList.iterator();
        while (iterator.hasNext()) {
            Attachment next = iterator.next();

            if (!"仓储物流部".equals(next.getGroup())) {
                attachmentList02.add(next);
                iterator.remove();
            }
        }

        List<Attachment> attachments = attachmentService.handleParamAttachment(attachmentList, collect, deliverDetail.getCreateUserId(), deliverDetail.getCreateUserName());
        attachmentList02.addAll(attachments);
        one.setAttachmentList(attachmentList02);

        deliverDetailDao.saveAndFlush(one);

        return true;

    }


    /**
     * 物流动态跟踪 - 物流信息
     *
     * @param id 物流数据id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public DeliverDetail logisticsMoveFollow(Integer id) {
        DeliverDetail one = deliverDetailDao.findOne(id);
        one.getDeliverConsignGoodsList().size();
        one.getAttachmentList().size();
        List<DeliverConsign> deliverConsigns = one.getDeliverNotice().getDeliverConsigns();
        for (DeliverConsign deliverConsign : deliverConsigns) {
            deliverConsign.getDeliverConsignGoodsSet().size();
            Order order = deliverConsign.getOrder();
            order.getGoodsList().size();
        }
//        if (one.getLogisticsUserId() != null) {
//            one.setLogisticsUserId(5);
//        } else {
//            one.setLogisticsUserId(4);
//        }
        return one;
    }


    /**
     * 物流动态跟踪 ：动态更新-项目完结
     *
     * @param deliverDetail
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void logisticsActionAddOrSave(DeliverDetail deliverDetail) {
        DeliverDetail one = deliverDetailDao.findOne(deliverDetail.getId());
        //物流经办人
        if (deliverDetail.getLogisticsUserId() != null) {
            one.setLogisticsUserId(deliverDetail.getLogisticsUserId());
        }
        //物流经办人姓名
        if (StringUtil.isNotBlank(deliverDetail.getLogisticsUserName())) {
            one.setLogisticsUserName(deliverDetail.getLogisticsUserName());
        }
        //经办日期
        if (deliverDetail.getLogisticsDate() != null) {
            one.setLogisticsDate(deliverDetail.getLogisticsDate());

            /**
             *  订单执行跟踪   推送运单号      经办日期
             */
            OrderLog orderLog = orderLogDao.findByDeliverDetailId(deliverDetail.getId());   //查询是否有记录
            if(orderLog == null){   //如果等于空，新增

            List<DeliverConsignGoods> deliverConsignGoodsList = one.getDeliverConsignGoodsList();
            Integer id = deliverConsignGoodsList.get(0).getGoods().getOrder().getId();  //获取到订单id

                OrderLog orderLog1 = new OrderLog();
                try {
                    orderLog1.setDeliverDetailId(deliverDetail.getId());
                    orderLog1.setOrder(orderDao.findOne(id));
                    orderLog1.setLogType(OrderLog.LogTypeEnum.OTHER.getCode());
                    orderLog1.setOperation(one.getDeliverDetailNo());
                    orderLog1.setCreateTime(new Date());
                    orderLog1.setBusinessDate(deliverDetail.getLogisticsDate());
                    orderLogDao.save(orderLog1);
                } catch (Exception ex) {
                    logger.error("日志记录失败 {}", orderLog1.toString());
                    logger.error("错误", ex);
                    ex.printStackTrace();
                }
            }else{  //不等于空，更新时间
                orderLog.setBusinessDate(deliverDetail.getLogisticsDate());
                orderLogDao.save(orderLog);
            }

        }
        //物流发票号
        if (StringUtil.isNotBlank(deliverDetail.getLogiInvoiceNo())) {
            one.setLogiInvoiceNo(deliverDetail.getLogiInvoiceNo());
        }
        //通知市场箱单时间
        if (deliverDetail.getPackingTime() != null) {
            one.setPackingTime(deliverDetail.getPackingTime());
        }
        //离厂时间          --
        if (deliverDetail.getLeaveFactory() != null) {
            one.setLeaveFactory(deliverDetail.getLeaveFactory());
        }

        //动态描述
        if (StringUtil.isNotBlank(deliverDetail.getLogs())) {
            one.setLogs(deliverDetail.getLogs());
        }
        //备注
        if (StringUtil.isNotBlank(deliverDetail.getRemarks())) {
            one.setRemarks(deliverDetail.getRemarks());
        }
        //实际创建时间 TODO
        if (deliverDetail.getStatus() == 4) {
            one.setAccomplishDate(new Date());
        }
        if (deliverDetail.getStatus() != null) {
            one.setStatus(deliverDetail.getStatus());
        }

        List<DeliverConsignGoods> deliverConsignGoodsList = one.getDeliverConsignGoodsList();
        for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsList) {
            Goods goods = deliverConsignGoods.getGoods();
            //下发订舱时间
            if (deliverDetail.getBookingTime() != null) {
                one.setBookingTime(deliverDetail.getBookingTime());//下发订舱时间
                goods.setBookingTime(deliverDetail.getBookingTime());//订舱日期
            }
            //船期或航班
            if (deliverDetail.getSailingDate() != null) {
                one.setSailingDate(deliverDetail.getSailingDate());//船期或航班
                goods.setSailingDate(deliverDetail.getSailingDate());//船期或航班
            }
            //报关放行时间
            if (deliverDetail.getCustomsClearance() != null) {
                one.setCustomsClearance(deliverDetail.getCustomsClearance());//报关放行时间
                goods.setCustomsClearance(deliverDetail.getCustomsClearance());//报关放行时间
            }
            //实际离港时间
            if (deliverDetail.getLeavePortTime() != null) {
                one.setLeavePortTime(deliverDetail.getLeavePortTime());//实际离港时间
                goods.setLeavePortTime(deliverDetail.getLeavePortTime());//实际离港时间
            }
            //预计抵达时间
            if (deliverDetail.getArrivalPortTime() != null) {
                one.setArrivalPortTime(deliverDetail.getArrivalPortTime());//预计抵达时间
                goods.setArrivalPortTime(deliverDetail.getArrivalPortTime());//预计抵达时间
            }
            if (deliverDetail.getStatus() == 7) {
                Date date = new Date();
                one.setAccomplishDate(date);
                List<DeliverConsignGoods> deliverConsignGoodsList1 = one.getDeliverConsignGoodsList();
                for (DeliverConsignGoods deliverConsignGoodss : deliverConsignGoodsList1) {
                    Goods one1 = goodsDao.findOne(deliverConsignGoodss.getGoods().getId());
                    one1.setAccomplishDate(date);
                    goodsDao.saveAndFlush(one1);
                }

            }


        }


        // 只接受国际物流部的附件
        List<Attachment> collect = deliverDetail.getAttachmentList().stream().filter(attachment -> {
            return "国际物流部".equals(attachment.getGroup());
        }).collect(Collectors.toList());

        List<Attachment> attachmentList = new ArrayList(one.getAttachmentList());

        List<Attachment> attachmentList02 = new ArrayList<>();
        Iterator<Attachment> iterator = attachmentList.iterator();
        while (iterator.hasNext()) {
            Attachment next = iterator.next();
            if (!"国际物流部".equals(next.getGroup())) {
                attachmentList02.add(next);
                iterator.remove();
            }
        }

        List<Attachment> attachments = attachmentService.handleParamAttachment(attachmentList, collect, deliverDetail.getCreateUserId(), deliverDetail.getCreateUserName());
        attachmentList02.addAll(attachments);
        one.setAttachmentList(attachmentList02);

        deliverDetailDao.saveAndFlush(one);
    }


    /**
     * 物流管理 - 查看页面
     *
     * @param id 物流数据id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public DeliverDetail queryLogisticsTrace(Integer id) {
        DeliverDetail one = deliverDetailDao.findOne(id);
        if (one.getStatus() == 7) {
            one.getDeliverConsignGoodsList().size();
            one.getAttachmentList().size();
            List<DeliverConsign> deliverConsigns = one.getDeliverNotice().getDeliverConsigns();
            for (DeliverConsign deliverConsign : deliverConsigns) {
                deliverConsign.getDeliverConsignGoodsSet().size();
                Order order = deliverConsign.getOrder();
                order.getGoodsList().size();
            }
            return one;
        }
        return null;
    }

    /**
     * 订单执行跟踪  根据运单号（产品放行单号）查询物流信息
     *
     * @param deliverDetailNo
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public DeliverDetail queryByDeliverDetailNo(String deliverDetailNo) {
        DeliverDetail deliverDetail = deliverDetailDao.findByDeliverDetailNo(deliverDetailNo);
        return deliverDetail;
    }


    /**
     * 订单执行跟踪  根据运单号（产品放行单号）查询物流信息   确认收货
     *
     * @param deliverDetail
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmTheGoodsByDeliverDetailNo(DeliverDetail deliverDetail) {
        DeliverDetail one = deliverDetailDao.findByDeliverDetailNo(deliverDetail.getDeliverDetailNo());
        one.setConfirmTheGoods(deliverDetail.getConfirmTheGoods());
        deliverDetailDao.saveAndFlush(one);
    }


    /**
     * 分页查询出库质检列表
     *
     * @param condition {
     *                  "deliverDetailNo": "产品放行单号",
     *                  "contractNo": "销售合同号",
     *                  "projectNo": "项目号",
     *                  "checkerName": "检验员",
     *                  "checkDate": "检验日期"
     *                  }
     * @param pageNum   页码
     * @param pageSize  页大小
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Map<String, Object>> listQualityByPage(Map<String, String> condition, int pageNum, int pageSize) {
        PageRequest request = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");
        Page<DeliverDetail> page = deliverDetailDao.findAll(new Specification<DeliverDetail>() {
            @Override
            public Predicate toPredicate(Root<DeliverDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据产品放行单号模糊查询
                if (StringUtil.isNotBlank(condition.get("deliverDetailNo"))) {
                    list.add(cb.like(root.get("deliverDetailNo").as(String.class), "%" + condition.get("deliverDetailNo") + "%"));
                }
                // 检验员名称模糊查询
                if (StringUtil.isNotBlank(condition.get("checkerName"))) {
                    list.add(cb.like(root.get("checkerName").as(String.class), "%" + condition.get("checkerName") + "%"));
                }
                // 检验员ID精确查询
                String checkerUid = condition.get("checkerUid");
                if (StringUtil.isNotBlank(checkerUid) && StringUtils.isNumeric(checkerUid)) {
                    list.add(cb.equal(root.get("checkerUid").as(Integer.class), Integer.parseInt(checkerUid)));
                }
                // 根据检验日期查询
                if (StringUtil.isNotBlank(condition.get("checkDate"))) {
                    try {
                        list.add(cb.equal(root.get("checkDate").as(Date.class), DateUtil.parseStringToDate(condition.get("checkDate"), "yyyy-MM-dd")));
                    } catch (ParseException e) {
                        logger.error("日期转换错误", e);
                    }
                }

                list.add(cb.greaterThan(root.get("status").as(Integer.class), DeliverDetail.StatusEnum.SAVED_OUTSTOCK.getStatusCode()));

                // 销售合同号 、 项目号查询
                String contractNo = condition.get("contractNo");
                String projectNo = condition.get("projectNo");
                if (StringUtils.isNotBlank(contractNo) || StringUtils.isNotBlank(projectNo)) {
                    List<DeliverDetail> deliverDetails = findDeliverDetailIdsByContractNoAndProjectNo(contractNo, projectNo);
                    CriteriaBuilder.In<Object> idIn = cb.in(root.get("id"));
                    if (deliverDetails != null && deliverDetails.size() > 0) {
                        for (DeliverDetail detail : deliverDetails) {
                            idIn.value(detail.getId());
                        }
                    } else {
                        // 一个出库质检也查询不到
                        idIn.value(-1);
                    }
                    list.add(idIn);
                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);

        // 转换为控制层需要的数据
        List<Map<String, Object>> list = new ArrayList<>();
        if (page.hasContent()) {
            for (DeliverDetail deliverDetail : page.getContent()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", deliverDetail.getId());
                map.put("deliverDetailNo", deliverDetail.getDeliverDetailNo()); // 产品放行单号
                map.put("checkerName", deliverDetail.getCheckerName()); // 检验员
                map.put("checkUserId", deliverDetail.getCheckerUid()); // 检验员id
                map.put("checkDate", deliverDetail.getCheckDate()); // 检验日期
                map.put("status", deliverDetail.getStatus()); // 状态
                map.put("checkDept", deliverDetail.getCheckDept()); // 质检部门

                List<String> contractNoList = new ArrayList<>();
                List<String> projectNoList = new ArrayList<>();
                // 销售合同号 和 项目号
                List<DeliverConsignGoods> deliverConsignGoodsList = deliverDetail.getDeliverConsignGoodsList();

                deliverConsignGoodsList.stream().forEach(deliverConsignGoods -> {
                    Goods goods = deliverConsignGoods.getGoods();
                    String contractNo = goods.getContractNo();
                    String projectNo = goods.getProjectNo();
                    if (StringUtils.isNotBlank(contractNo) && !contractNoList.contains(contractNo)) {
                        contractNoList.add(goods.getContractNo());
                    }
                    if (StringUtils.isNotBlank(projectNo) && !projectNoList.contains(projectNo)) {
                        projectNoList.add(projectNo);
                    }
                });
                map.put("contractNos", StringUtils.join(contractNoList, ","));
                map.put("projectNos", StringUtils.join(projectNoList, ","));


                list.add(map);
            }
        }
        PageImpl<Map<String, Object>> resultPage = new PageImpl<Map<String, Object>>(list, request, page.getTotalElements());

        return resultPage;
    }


    /**
     * 根据销售合同和项目号查询
     *
     * @param contractNos
     * @param projectNos
     * @return
     */
    @Transactional(readOnly = true)
    public List<DeliverDetail> findDeliverDetailIdsByContractNoAndProjectNo(String contractNos, String projectNos) {
        List<DeliverDetail> result = null;
        if (StringUtils.isNotBlank(contractNos) || StringUtils.isNotBlank(projectNos)) {
            result = deliverDetailDao.findAll(new Specification<DeliverDetail>() {
                @Override
                public Predicate toPredicate(Root<DeliverDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<>();

                    Join<DeliverDetail, DeliverConsignGoods> deliverConsignGoods = root.join("deliverConsignGoodsList");
                    Join<DeliverConsignGoods, Goods> goods = deliverConsignGoods.join("goods");
                    // 根据销售号过滤
                    if (StringUtils.isNotBlank(contractNos)) {
                        String[] split = contractNos.split(",");
                        for (String contractNo : split) {
                            list.add(cb.like(goods.get("contractNo").as(String.class), "%" + contractNo + "%"));
                        }
                    }

                    // 根据项目号过滤
                    if (StringUtils.isNotBlank(projectNos)) {
                        String[] split = projectNos.split(",");
                        for (String projectNo : split) {
                            list.add(cb.like(goods.get("projectNo").as(String.class), "%" + projectNo + "%"));
                        }
                    }


                    Predicate[] predicates = new Predicate[list.size()];
                    predicates = list.toArray(predicates);
                    return cb.and(predicates);
                }
            });
        }

        return result;
    }

    /**
     * 保存出库质检信息
     *
     * @param deliverDetail
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveQuality(DeliverDetail deliverDetail) throws Exception {
        DeliverDetail dbDeliverDetail = deliverDetailDao.findOne(deliverDetail.getId());

        if (dbDeliverDetail == null ||
                (dbDeliverDetail.getStatus() != DeliverDetail.StatusEnum.SAVED_OUT_INSPECT.getStatusCode() &&
                        dbDeliverDetail.getStatus() != DeliverDetail.StatusEnum.SUBMITED_OUTSTOCK.getStatusCode())) {
            throw new Exception("入库质检当前状态不可修改");
        }

        // 复制基本信息
        dbDeliverDetail.setGoodsChkStatus(deliverDetail.getGoodsChkStatus());
        dbDeliverDetail.setBillsChkStatus(deliverDetail.getBillsChkStatus());
        dbDeliverDetail.setCheckerUid(deliverDetail.getCheckerUid());
        dbDeliverDetail.setCheckerName(deliverDetail.getCheckerName());
        dbDeliverDetail.setCheckDept(deliverDetail.getCheckDept());
        dbDeliverDetail.setCheckDate(deliverDetail.getCheckDate());
        dbDeliverDetail.setReleaseUid(deliverDetail.getReleaseUid());
        dbDeliverDetail.setReleaseName(deliverDetail.getReleaseName());
        dbDeliverDetail.setReleaseDate(deliverDetail.getReleaseDate());
        dbDeliverDetail.setQualityLeaderId(deliverDetail.getQualityLeaderId());
        dbDeliverDetail.setQualityleaderName(deliverDetail.getQualityleaderName());
        dbDeliverDetail.setApplicant(deliverDetail.getApplicant());
        dbDeliverDetail.setApplicantName(deliverDetail.getApplicantName());
        dbDeliverDetail.setApplicantDate(deliverDetail.getApplicantDate());
        dbDeliverDetail.setApprover(deliverDetail.getApprover());
        dbDeliverDetail.setApproverName(deliverDetail.getApproverName());
        dbDeliverDetail.setApprovalDate(deliverDetail.getApprovalDate());
        dbDeliverDetail.setReason(deliverDetail.getReason());
        dbDeliverDetail.setOpinion(deliverDetail.getOpinion());
        dbDeliverDetail.setStatus(deliverDetail.getStatus());
        // 出库质检提交时推送放行信息数据到商品表
        if (deliverDetail.getReleaseDate() != null &&
                DeliverDetail.StatusEnum.fromStatusCode(dbDeliverDetail.getStatus()) == DeliverDetail.StatusEnum.SUBMITED_OUT_INSPECT) {
            List<DeliverConsignGoods> deliverConsignGoodsList1 = dbDeliverDetail.getDeliverConsignGoodsList();
            for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsList1) {
                Goods goods = deliverConsignGoods.getGoods();
                Goods one1 = goodsDao.findOne(goods.getId());
                one1.setReleaseDate(deliverDetail.getReleaseDate());//推送   放行日期    到商品表
                goodsDao.save(one1);
            }

            //出库质检结果通知：将合格商品通知仓库经办人（合格）（如果仓库经办人不是徐健，那么还要单独发给徐健）

            Project project = null; //项目信息
            //获取项目信息
            List<DeliverConsign> deliverConsigns = dbDeliverDetail.getDeliverNotice().getDeliverConsigns();
            for(DeliverConsign deliverConsign : deliverConsigns){
                project=project==null?deliverConsign.getOrder().getProject():project;
            }

            Map<String,Object> map = new HashMap<>();
            map.put("qualityUid",project.getWarehouseUid());       //仓库经办人id
            map.put("projectNo", project.getProjectNo());        //项目号
            map.put("deliverDetailNo",dbDeliverDetail.getDeliverDetailNo());        //产品放行单号
            map.put("status",4);        //发送短信标识
            sendSms(map);


        }


        // 只接受品控部的附件
        List<Attachment> collect = deliverDetail.getAttachmentList().stream().filter(attachment -> {
            return "品控部".equals(attachment.getGroup());
        }).collect(Collectors.toList());


        List<Attachment> attachmentList = dbDeliverDetail.getAttachmentList();

        List<Attachment> attachmentList02 = new ArrayList<>();
        Iterator<Attachment> iterator = attachmentList.iterator();
        while (iterator.hasNext()) {
            Attachment next = iterator.next();

            if (!"品控部".equals(next.getGroup())) {
                attachmentList02.add(next);
                iterator.remove();
            }
        }
        List<Attachment> attachments = attachmentService.handleParamAttachment(attachmentList, collect, deliverDetail.getCreateUserId(), deliverDetail.getCreateUserName());
        attachmentList02.addAll(attachments);

        dbDeliverDetail.setAttachmentList(attachmentList02);

        deliverDetailDao.save(dbDeliverDetail);


        return true;
    }





    /**
     *  订单列表增加确认收货按钮：
     *  2、所有出口发货通知单中的商品全部出库并在物流跟踪管理中“跟踪状态”为“执行中”。
     *
     * @param
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Boolean findStatusAndNumber(Integer orderId) {
        /**
         * 判断出库状态
         */
            // 看货通知单 查询信息
            List<DeliverNotice> companyList = deliverNoticeDao.findAll(new Specification<DeliverNotice>() {
                @Override
                public Predicate toPredicate(Root<DeliverNotice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<>();

                    //订单id查询
                    Join<DeliverNotice, DeliverConsign> deliverConsign = root.join("deliverConsigns");
                    Join<DeliverConsign, Order> order = deliverConsign.join("order");
                    list.add(cb.equal(order.get("id").as(Integer.class), orderId));

                    Predicate[] predicates = new Predicate[list.size()];
                    predicates = list.toArray(predicates);
                    return cb.and(predicates);
                }
            });
            //获取物流-出库单详情
        if(companyList.size() != 0 && companyList.size()== deliverConsignDao.findByOrderId(orderId).size()){
                for (DeliverNotice deliverNotice :companyList){

                    DeliverDetail deliverDetail = deliverNotice.getDeliverDetail();     //获取出库信息

                    //  1：出库保存/草稿 2：提交出库质检 3：出库质检保存  4：出库质检提交 5：确认出库 6：完善物流状态中 7：项目完结',
                    if(deliverDetail == null || deliverDetail.getStatus()<6){   //判断是否全部出库
                        return false;
                    }

                }
            }else{
                return false;
            }

        return true;
    }

    //出库通知
    public void sendSms(Map<String,Object> map1) throws  Exception {

        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (StringUtils.isNotBlank(eruiToken)) {
            try{
                // 根据id获取人员信息
                String jsonParam = "{\"id\":\"" +map1.get("qualityUid") + "\"}";
                Map<String, String> header = new HashMap<>();
                header.put(EruitokenUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                String s = HttpRequest.sendPost(memberInformation, jsonParam, header);
                logger.info("人员详情返回信息：" + s);

                // 获取人员手机号
                JSONObject jsonObject = JSONObject.parseObject(s);
                Integer code = jsonObject.getInteger("code");
                String mobile = null;  //手机号
                if(code == 1){
                    Integer status = (Integer) map1.get("status");  //状态

                    JSONObject data = jsonObject.getJSONObject("data");
                    mobile = data.getString("mobile");
                    //发送短信
                    Map<String,String> map= new HashMap();
                    map.put("areaCode","86");
                    if(status != 4){
                        map.put("to","[\""+mobile+"\"]");
                    }else{
                        //去除重复
                        Set<String> listAll = new HashSet<>();
                        listAll.add(mobile);

                        //获取徐健 手机号
                        String name = null;
                        String jsonParams = "{\"id\":\"29606\"}";
                        String s3 = HttpRequest.sendPost(memberInformation, jsonParams, header);
                        logger.info("人员详情返回信息：" + s3);
                        // 获取手机号
                        JSONObject jsonObjects = JSONObject.parseObject(s3);
                        Integer codes = jsonObjects.getInteger("code");
                        if (codes == 1) {
                            JSONObject datas = jsonObjects.getJSONObject("data");
                            name=datas.getString("mobile");
                        }
                         /*listAll.add("15066060360");*/
                        listAll.add(name);

                        /*listAll.add("15066060360");*/
                        listAll = new HashSet<>(new LinkedHashSet<>(listAll));
                        JSONArray smsarray = new JSONArray();
                        for (String me : listAll) {
                            smsarray.add(me);
                        }
                        map.put("to",smsarray.toString());
                    }

                    if(status == 2){
                        map.put("content","您好，项目号："+map1.get("projectNo")+"，产品放行单号："+map1.get("deliverDetailNo")+"，已申请出库报检，请及时处理。感谢您对我们的支持与信任！");
                    }else if(status == 5){
                        map.put("content","您好，销售合同号："+map1.get("projectNo")+"，产品放行单号："+map1.get("deliverDetailNo")+"，仓储经办人："+map1.get("wareHousemanName")+"，已出库并上传箱单，请及时处理。感谢您对我们的支持与信任！");
                    } else{
                        map.put("content","您好，项目号："+map1.get("projectNo")+"，产品放行单号："+map1.get("deliverDetailNo")+"，出库质检已合格，请及时处理。感谢您对我们的支持与信任！");
                    }
                    map.put("subType","0");
                    map.put("groupSending","0");
                    map.put("useType","订单");
                    String s1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                    logger.info("发送短信返回状态"+s1);
                }

            }catch (Exception e){
                throw new Exception("发送短信失败");
            }

        }
    }


}
