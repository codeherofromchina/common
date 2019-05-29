package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.event.OrderProgressEvent;
import com.erui.order.event.TasksAddEvent;
import com.erui.order.requestVo.DeliverD;
import com.erui.order.requestVo.DeliverDetailVo;
import com.erui.order.requestVo.DeliverW;
import com.erui.order.service.BackLogService;
import com.erui.order.service.DeliverDetailService;
import com.erui.order.service.StatisticsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
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
    private ApplicationContext applicationContext;
    @Autowired
    private DeliverDetailDao deliverDetailDao;

    @Autowired
    private AttachmentServiceImpl attachmentService;


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

    @Autowired
    IogisticsDao iogisticsDao;

    @Autowired
    private InstockServiceImpl instockServiceImpl;

    @Autowired
    private InstockServiceImpl getInstockServiceImpl;

    @Autowired
    private BackLogService backLogService;

    @Autowired
    private StatisticsService statisticsService;

    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口

    @Value("#{orderProp[SEND_SMS]}")
    private String sendSms;  //发短信接口

    @Value("#{orderProp[MEMBER_LIST]}")
    private String memberList;  //查询人员信息调用接口

    @Value("#{orderProp[SSO_USER]}")
    private String ssoUser;  //从SSO获取登录用户

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
        /*deliverDetail.getDeliverNotice().getId();*/
        List<Attachment> attachments = null;
        if (deliverDetail != null && deliverDetail.getId() > 0) {
            attachments = attachmentService.queryAttachs(deliverDetail.getId(), Attachment.AttachmentCategory.DELIVERDETAIL.getCode());

            if (attachments != null && attachments.size() > 0) {
                deliverDetail.setAttachmentList(attachments);
                deliverDetail.getAttachmentList().size();
            }
            List<DeliverConsignGoods> deliverConsignGoodsList = null;
            if (deliverDetail.getDeliverConsignGoodsList() != null && deliverDetail.getDeliverConsignGoodsList().size() > 0) {
                deliverConsignGoodsList = deliverDetail.getDeliverConsignGoodsList();
            }
            if (deliverConsignGoodsList.size() > 0) {
                for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsList) {
                    deliverConsignGoods.getGoods().setPurchGoods(null);
                }
            }
        }
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
            deliverDetail = findDetailById(deliverDetailId);
        } else {
            deliverDetail = new DeliverDetail();
        }

        deliverDetail.setStatus(deliverDetail.getStatus());


        deliverDetailDao.save(deliverDetail);


        return true;
    }


    /**
     * 出库管理
     * <p>
     * 订单V2.0
     * <p>
     * 根据出口通知单查询出库信息
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

                //根据销售合同号   根据项目号   出口通知单号
                if (StringUtil.isNotBlank(deliverD.getContractNo()) || StringUtil.isNotBlank(deliverD.getProjectNo()) || StringUtil.isNotBlank(deliverD.getDeliverConsignNo())) {
                    Join<DeliverDetail, DeliverConsign> deliverConsign = root.join("deliverConsign");   //关联出库通知单
                    if (StringUtil.isNotBlank(deliverD.getDeliverConsignNo())) {
                        list.add(cb.like(deliverConsign.get("deliverConsignNo").as(String.class), "%" + deliverD.getDeliverConsignNo() + "%"));
                    }
                    Join<DeliverConsign, Order> order = deliverConsign.join("order");   //关联订单
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
                //根据出库日期
                if (deliverD.getLeaveDate() != null) {
                    list.add(cb.equal(root.get("leaveDate"), deliverD.getLeaveDate()));
                }
                //根据仓库经办人
                if (deliverD.getWareHouseman() != null) {
                    list.add(cb.equal(root.get("wareHouseman").as(Integer.class), deliverD.getWareHouseman()));
                }
                //根据出库状态   status    1：未质检    2：质检中   3：质检完成   4：已出库 9： 已变更
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
                    } else if (deliverD.getStatus() == 9) {
                        list.add(cb.equal(root.get("status").as(Integer.class), 9));//已变更
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


                DeliverConsign deliverConsign = notice.getDeliverConsign();    //出口发货通知单
                if (deliverConsign == null) {
                    throw new Exception(String.format("%s%s%s", "产品放行单号(" + notice.getDeliverDetailNo() + ")无出口发货通知单关系", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Product release number (" + notice.getDeliverDetailNo() + ") without export notification"));
                }
                Order order = deliverConsign.getOrder();    //获取订单关系
                String deliverConsignNo = deliverConsign.getDeliverConsignNo(); //出口发货通知单号
                if (order == null) {
                    throw new Exception(String.format("%s%s%s", "出口发货通知单号(" + deliverConsignNo + ")缺少订单关系", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Export shipping notification number (" + deliverConsignNo + ") lacks order relationship."));
                }
                notice.setDeliverConsignNo(deliverConsignNo);//出口发货通知单号

                Project project = order.getProject();   //获取项目信息
                if (project == null) {
                    throw new Exception(String.format("%s%s%s", "订单(" + order.getContractNo() + ")号缺少项目信息", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Order (" + order.getContractNo() + ") lacks project information"));
                }
                notice.setContractNo(order.getContractNo());     //销售合同号
                notice.setProjectNo(project.getProjectNo());//项目号
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
                    } else if (deliverW.getStatus() == 9) {
                        list.add(cb.equal(root.get("status").as(Integer.class), 9));
                    }
                } else {
                    list.add(cb.greaterThan(root.get("status").as(Integer.class), 9));
                }


                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);


        if (page.hasContent()) {
            for (DeliverDetail notice : page.getContent()) {
                List<String> contractNos = new ArrayList<String>();    //销售合同号
                DeliverConsign deliverConsign1 = notice.getDeliverConsign();
                Order order = deliverConsign1.getOrder();
                contractNos.add(order.getContractNo());  //销售合同号
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

        //状态
        Integer status = deliverDetail.getStatus();

        //商品备注      出库备注
        List<DeliverConsignGoods> deliverConsignGoodsList = deliverDetail.getDeliverConsignGoodsList();

        Integer outboundNums = 0; //出库总数量
        Integer straightNums = 0;   //厂家直发总数量
        if (deliverConsignGoodsList.size() != 0) {
            for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsList) {
                DeliverConsignGoods one = deliverConsignGoodsDao.findOne(deliverConsignGoods.getId());

                Integer outboundNum = deliverConsignGoods.getOutboundNum() == null ? 0 : deliverConsignGoods.getOutboundNum();  //出库数量
                Integer straightNum = deliverConsignGoods.getStraightNum() == null ? 0 : deliverConsignGoods.getStraightNum();    //厂家直发数量

                if (one.getSendNum() != outboundNum + straightNum) {    //出库数量和厂家直发数量是否相等
                    throw new Exception(String.format("%s%s%s", "出库商品数量不正确", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Quantity of goods without Treasury"));
                }
                one.setOutboundRemark(deliverConsignGoods.getOutboundRemark()); // 出库备注
                if (status == 2 || status == 1) {
                    one.setOutboundNum(outboundNum); //出库数量
                    one.setStraightNum(straightNum); //厂家直发数量
                }

                deliverConsignGoodsDao.saveAndFlush(one);
                if (outboundNum != null && straightNum != null) {
                    outboundNums = outboundNums + outboundNum; //出库数量累加
                    straightNums = straightNums + straightNum; //厂家直发数量累加
                }

                //V2.0  减少商品入库数量
                if (status == 2) {

                    Goods goods = one.getGoods();

                    if (outboundNum == 0 && straightNum == 0) {
                        throw new Exception(String.format("%s%s%s", "商品的出库数量不能为0", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The goods cannot be zero"));
                    }

                    if (outboundNum != null && outboundNum != 0) {
                        goods.setInspectInstockNum(goods.getInspectInstockNum() - outboundNum);     //质检入库总数量  -  出库数量
                    }
                    if (straightNum != null && straightNum != 0) {
                        goods.setNullInstockNum(goods.getNullInstockNum() - straightNum);    //厂家直发总数量 - 厂家直发数量
                    }
                    goodsDao.save(goods);

                }
            }
        }

        DeliverDetail one = findDetailById(deliverDetail.getId());

        if (one == null) {
            throw new Exception(String.format("%s%s%s", "查询不到出库信息", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Query no outgoing information"));
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

        one.setStatus(status);// 状态

        if (status == 2) one.setOutCheck(1); // 默认是否外检为1

        Project project = null; //项目信息


      /*  // 只接受仓储物流部的附件
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
        }*/

        //List<Attachment> attachments = attachmentService.handleParamAttachment(attachmentList, collect, deliverDetail.getCreateUserId(), deliverDetail.getCreateUserName());
        //attachmentList02.addAll(attachments);
        //one.setAttachmentList(attachmentList02);

        DeliverDetail deliverDetail1 = deliverDetailDao.saveAndFlush(one);
        // 处理附件信息 attachmentList 库里存在附件列表 dbAttahmentsMap前端传来参数附件列表
        //deliverConsign1.setAttachmentList(deliverConsign1.getAttachmentList());
        List<Attachment> attachmentList = deliverDetail.getAttachmentList();
        Map<Integer, Attachment> dbAttahmentsMap = one.getAttachmentList().parallelStream().collect(Collectors.toMap(Attachment::getId, vo -> vo));
        if (attachmentList != null && attachmentList.size() > 0) {
            attachmentService.updateAttachments(attachmentList, dbAttahmentsMap, one.getId(), Attachment.AttachmentCategory.DELIVERDETAIL.getCode());
        }


        //获取项目信息
        DeliverConsign deliverConsign1 = one.getDeliverConsign();

        project = deliverConsign1.getOrder().getProject();
        String contractNo = project.getContractNo();//销售合同号
        //V2.0  推送信息到物流表
        String projectNo = project.getProjectNo();//项目号
        Integer logisticsUid = project.getLogisticsUid();//物流经办人id
        String logisticsName = project.getLogisticsName();//物流经办人名称
        String deliverConsignNo = deliverDetail1.getDeliverConsign().getDeliverConsignNo();//出口通知单号


        //获取分单人id
        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();

        List<Integer> listAll = new ArrayList<>(); //分单员id

        if (StringUtils.isNotBlank(eruiToken)) {
            Map<String, String> header = new HashMap<>();
            header.put(CookiesUtil.TOKEN_NAME, eruiToken);
            header.put("Content-Type", "application/json");
            header.put("accept", "*/*");
            try {
                //获取物流分单员
                String jsonParam = "{\"role_no\":\"O020\"}";
                String s2 = HttpRequest.sendPost(memberList, jsonParam, header);
                logger.info("人员详情返回信息：" + s2);

                // 获取人员手机号
                JSONObject jsonObjects = JSONObject.parseObject(s2);
                Integer codes = jsonObjects.getInteger("code");
                if (codes == 1) {    //判断请求是否成功
                    // 获取数据信息
                    JSONArray data1 = jsonObjects.getJSONArray("data");
                    for (int i = 0; i < data1.size(); i++) {
                        JSONObject ob = (JSONObject) data1.get(i);
                        listAll.add(ob.getInteger("id"));    //获取物流分单员id
                    }
                } else {
                    throw new Exception("出库分单员查询失败");
                }
            } catch (Exception e) {
                throw new Exception("出库分单员查询失败");
            }
        }


        if (status == 2 || status == 1) {

            //出库提交的时候不管有没有经办人都去判断删除一下   删除分单员的信息推送  办理分单
            BackLog backLog2 = new BackLog();
            backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.INSTOCKSUBMENUDELIVER.getNum());    //功能访问路径标识
            backLog2.setHostId(one.getId());
            backLog2.setFollowId(1);  // 1：为办理和分单    4：为确认出库
            backLogService.updateBackLogByDelYn(backLog2);

        }


        //出库通知：通知质检经办人办理质检
        if (status == 2) {

          /*  //如果是厂家直接发货    推送  出库经办人 出库日期 到商品表
            pushWareHouseman(one,1);*/

            if (outboundNums != 0) { //出库总数量不等于0  才发送信息

                Map<String, Object> map = new HashMap<>();
                map.put("qualityUid", project.getQualityUid());       //检质检经办人id
                map.put("projectNo", project.getProjectNo());        //项目号
                map.put("deliverDetailNo", one.getDeliverDetailNo());        //产品放行单号
                map.put("status", 2);        //发送短信标识

                sendSms(map);
            }


            //出库提交删除  办理出库 代办
            BackLog backLog = new BackLog();
            backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.TRANSACTDELIVER.getNum());    //功能访问路径标识
            backLog.setHostId(one.getId());
            backLog.setFollowId(1);  // 1：为办理和分单    4：为确认出库
            backLogService.updateBackLogByDelYn(backLog);


            //如果不外检  是厂家直发的话，直接修改状态
            if (outboundNums == 0) {  //判断出库总数量

                if (straightNums == 0) {      //判断厂家直发总数量   没有出库商品的时候不让出库
                    throw new Exception(String.format("%s%s%s", "没有出库商品数量", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "No quantity of goods out of the Treasury"));
                }

                one.setLeaveDate(new Date());   //出库时间
                one.setStatus(5);   //出库状态
                one.setOutCheck(0); //设置不外检
                DeliverDetail deliverDetail2 = deliverDetailDao.saveAndFlush(one);

                //如果是厂家直接发货    推送  出库经办人 出库日期 到商品表
                pushWareHouseman(deliverDetail2, 2);

                //  V2.0订单执行跟踪   推送商品出库
                pushGoodsLeaverDate(deliverConsign1, deliverDetail2);

                //推送信息到出库信息管理
                Iogistics iogistics = new Iogistics();  //物流信息
                iogistics.setDeliverDetailId(deliverDetail1);   //出库信息
                iogistics.setDeliverDetailNo(deliverDetail1.getDeliverDetailNo()); //产品放行单号
                iogistics.setContractNo(contractNo);    //销售合同号
                iogistics.setDeliverConsignNo(deliverConsignNo);  //出口通知单号
                iogistics.setProjectNo(projectNo);   //项目号
                iogistics.setLogisticsUserId(logisticsUid); //物流经办人id
                iogistics.setLogisticsUserName(logisticsName);   //物流经办人名称
                iogistics.setOutCheck(0);
                Iogistics save = iogisticsDao.save(iogistics);

                // 如果是厂家直发 在出库提交的时候直接推送到出库信息管理      出库通知：出库单下达后通知物流分单员
                Map<String, Object> map = new HashMap<>();
                map.put("projectNo", contractNo);        //销售合同号
                map.put("deliverDetailNo", deliverDetail1.getDeliverDetailNo());        //产品放行单号
                map.put("wareHousemanName", deliverDetail1.getWareHousemanName());        //仓储经办人名字
                map.put("status", 5);        //发送短信标识
                sendSms(map);

                //如果是厂家直发，直接将待办信息提示到出库信息管理
                if (listAll.size() > 0) {
                    for (Integer in : listAll) { //分单员有几个人推送几条
                        BackLog newBackLog = new BackLog();
                        newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.LOGISTICS.getMsg());  //功能名称
                        newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.LOGISTICS.getNum());    //功能访问路径标识
                        newBackLog.setReturnNo(deliverDetail1.getDeliverDetailNo());  //返回单号    产品放行单号
                        newBackLog.setInformTheContent(deliverConsignNo + " | " + contractNo);  //提示内容
                        newBackLog.setHostId(save.getId());    //父ID，列表页id
                        newBackLog.setUid(in);   ////经办人id
                        backLogService.addBackLogByDelYn(newBackLog);
                    }
                }


            } else {
                //如果不是厂家直发添加   质检待办信息
                //出库提交以后添加  办理出库质检
                DeliverConsign deliverConsign = one.getDeliverConsign();
                Order order = deliverConsign.getOrder();
                Project project1 = order.getProject();
                BackLog newBackLog = new BackLog();
                newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.DELIVERDETAIL.getMsg());  //功能名称
                newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.DELIVERDETAIL.getNum());    //功能访问路径标识
                newBackLog.setReturnNo(deliverConsign.getDeliverConsignNo());  //返回单号  出口通知单号
                newBackLog.setInformTheContent(order.getContractNo() + " | " + project1.getProjectNo());  //提示内容  销售合同号，项目号
                newBackLog.setHostId(deliverDetail1.getId());    //父ID，列表页id
                newBackLog.setUid(project1.getQualityUid());   //经办人id   经办人id
                backLogService.addBackLogByDelYn(newBackLog);

            }
        }


        //确认出库
        if (status == 5) {

            deliverDetail1.setLeaveDate(new Date());  //出库时间   点击确认出库的时候
            DeliverDetail deliverDetail2 = deliverDetailDao.saveAndFlush(deliverDetail1);


            //如果是厂家直接发货    推送  出库日期 到商品表
            pushWareHouseman(one, 2);

            //已出库
            applicationContext.publishEvent(new OrderProgressEvent(deliverConsign1.getOrder(), 8, eruiToken));

            //  V2.0订单执行跟踪   推送商品出库
            pushGoodsLeaverDate(deliverConsign1, deliverDetail2);


            //出库通知：出库单下达后通知物流分单员（确认出库）
            Map<String, Object> map = new HashMap<>();
            map.put("projectNo", contractNo);        //销售合同号
            map.put("deliverDetailNo", one.getDeliverDetailNo());        //产品放行单号
            map.put("wareHousemanName", one.getWareHousemanName());        //仓储经办人名字
            map.put("status", 5);        //发送短信标识
            sendSms(map);


            if (outboundNums == 0 && straightNums == 0) { //没有出库商品的时候不让出库
                throw new Exception(String.format("%s%s%s", "没有出库商品数量", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "No quantity of goods out of the Treasury"));
            }

            //确认出库提交删除 确认出库  待办信息
            BackLog backLog2 = new BackLog();
            backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.NOTARIZEDELIVER.getNum());    //功能访问路径标识
            backLog2.setHostId(deliverDetail1.getId());
            backLog2.setFollowId(4);
            backLogService.updateBackLogByDelYn(backLog2);


            if (outboundNums > 0) {    //外检
                Iogistics iogistics = new Iogistics();  //物流信息

                iogistics.setDeliverDetailId(deliverDetail1);   //出库信息
                iogistics.setDeliverDetailNo(deliverDetail1.getDeliverDetailNo()); //产品放行单号
                iogistics.setContractNo(contractNo);    //销售合同号
                iogistics.setDeliverConsignNo(deliverConsignNo);  //出口通知单号
                iogistics.setProjectNo(projectNo);   //项目号
                iogistics.setLogisticsUserId(logisticsUid); //物流经办人id
                iogistics.setLogisticsUserName(logisticsName);   //物流经办人名称
                iogistics.setOutCheck(1);
                Iogistics save = iogisticsDao.save(iogistics);

                //确认出库添加  办理物流分单  待办
                if (listAll.size() > 0) {
                    for (Integer in : listAll) { //分单员有几个人推送几条
                        BackLog newBackLog = new BackLog();
                        newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.LOGISTICS.getMsg());  //功能名称
                        newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.LOGISTICS.getNum());    //功能访问路径标识
                        newBackLog.setReturnNo(deliverDetail1.getDeliverDetailNo());  //返回单号    产品放行单号
                        newBackLog.setInformTheContent(deliverConsignNo + " | " + contractNo);  //提示内容
                        newBackLog.setHostId(save.getId());    //父ID，列表页id
                        newBackLog.setUid(in);   ////经办人id
                        backLogService.addBackLogByDelYn(newBackLog);
                    }
                }


            }
            if (straightNums > 0) {   //不外检
                Iogistics iogistics = new Iogistics();  //物流信息

                iogistics.setDeliverDetailId(deliverDetail1);   //出库信息
                iogistics.setDeliverDetailNo(deliverDetail1.getDeliverDetailNo()); //产品放行单号
                iogistics.setContractNo(contractNo);    //销售合同号
                iogistics.setDeliverConsignNo(deliverConsignNo);  //出口通知单号
                iogistics.setProjectNo(projectNo);   //项目号
                iogistics.setLogisticsUserId(logisticsUid); //物流经办人id
                iogistics.setLogisticsUserName(logisticsName);   //物流经办人名称
                iogistics.setOutCheck(0);
                Iogistics save = iogisticsDao.save(iogistics);


                //确认出库添加  办理物流分单  待办
                if (listAll.size() > 0) {
                    for (Integer in : listAll) { //分单员有几个人推送几条
                        BackLog newBackLog = new BackLog();
                        newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.LOGISTICS.getMsg());  //功能名称
                        newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.LOGISTICS.getNum());    //功能访问路径标识
                        newBackLog.setReturnNo(deliverDetail1.getDeliverDetailNo());  //返回单号    产品放行单号
                        newBackLog.setInformTheContent(deliverConsignNo + " | " + contractNo);  //提示内容
                        newBackLog.setHostId(save.getId());    //父ID，列表页id
                        newBackLog.setUid(in);   ////经办人id
                        backLogService.addBackLogByDelYn(newBackLog);
                    }
                }

            }

        }

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
        List<Attachment> attachments = attachmentService.queryAttachs(one.getId(), Attachment.AttachmentCategory.DELIVERDETAIL.getCode());
        if (attachments != null && attachments.size() > 0) {
            one.setAttachmentList(attachments);
        }
        one.getAttachmentList().size();
        List<DeliverConsign> deliverConsigns = one.getDeliverNotice().getDeliverConsigns();
        for (DeliverConsign deliverConsign : deliverConsigns) {
            List<DeliverConsignGoods> deliverConsignGoodsSet = deliverConsign.getDeliverConsignGoodsSet();
            if (deliverConsignGoodsSet.size() > 0) {
                for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsSet) {
                    deliverConsignGoods.getGoods().setPurchGoods(null);
                }
            }
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
    public void logisticsActionAddOrSave(DeliverDetail deliverDetail) throws Exception {
        String eruiToken = (String) ThreadLocalUtil.getObject();
        DeliverDetail one = findDetailById(deliverDetail.getId());
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
            if (orderLog == null) {   //如果等于空，新增

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
            } else {  //不等于空，更新时间
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
            if (deliverDetail.getStatus() == 6 && deliverDetail.getLeaveFactory() != null) {
                //已发运
                applicationContext.publishEvent(new OrderProgressEvent(goods.getOrder(), 9, eruiToken));
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


       /* // 只接受国际物流部的附件
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
        one.setAttachmentList(attachmentList02);*/

        deliverDetailDao.saveAndFlush(one);
        // 处理附件信息 attachmentList 库里存在附件列表 dbAttahmentsMap前端传来参数附件列表
        //deliverConsign1.setAttachmentList(deliverConsign1.getAttachmentList());
        List<Attachment> attachmentList = deliverDetail.getAttachmentList();
        Map<Integer, Attachment> dbAttahmentsMap = one.getAttachmentList().parallelStream().collect(Collectors.toMap(Attachment::getId, vo -> vo));
        if (attachmentList != null && attachmentList.size() > 0) {
            attachmentService.updateAttachments(attachmentList, dbAttahmentsMap, one.getId(), Attachment.AttachmentCategory.DELIVERDETAIL.getCode());
        }
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
                List<DeliverConsignGoods> deliverConsignGoodsSet = deliverConsign.getDeliverConsignGoodsSet();
                if (deliverConsignGoodsSet.size() > 0) {
                    for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsSet) {
                        deliverConsignGoods.getGoods().setPurchGoods(null);
                    }
                }
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
     * 出库详情页  转交经办人
     *
     * @param deliverDetail
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean storehouseManageDeliverAgent(DeliverDetail deliverDetail) throws Exception {

        //获取经办分单人信息
        String eruiToken = (String) ThreadLocalUtil.getObject();
        Map<String, String> stringStringMap = getInstockServiceImpl.ssoUser(eruiToken);
        String name = stringStringMap.get("name");
        String id = stringStringMap.get("id");


        //保存经办人信息
        DeliverDetail one = deliverDetailDao.findOne(deliverDetail.getId());
        one.setWareHouseman(deliverDetail.getWareHouseman());   //出库经办人ID
        one.setWareHousemanName(deliverDetail.getWareHousemanName());   //出库经办人姓名
        if (StringUtil.isBlank(one.getSubmenuName())) {
            one.setSubmenuName(name); //分单员经办人姓名
        }
        if (one.getSubmenuId() == null) {
            one.setSubmenuId(Integer.parseInt(id));   //入库分单人Id
        }
        DeliverDetail save = deliverDetailDao.save(one);


        //V2.0出库转交经办人：出库分单员转交推送给出库经办人
        Map<String, Object> map = new HashMap();
        map.put("projectNo", one.getDeliverConsign().getOrder().getContractNo());  //销售合同号
        map.put("inspectApplyNo", one.getDeliverDetailNo()); //产品放行单号查询
        map.put("submenuName", one.getSubmenuName());   //出库分单员名称
        map.put("logisticsUserId", one.getWareHouseman());   //出库经办人id
        map.put("yn", 2); //出库
        try {
            instockServiceImpl.sendSms(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //指派分单人的时候   删除分单员的信息推送  办理分单
        BackLog backLog2 = new BackLog();
        backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.INSTOCKSUBMENUDELIVER.getNum());    //功能访问路径标识
        backLog2.setHostId(one.getId());
        backLog2.setFollowId(1);  // 1：为办理和分单    4：为确认出库
        backLogService.updateBackLogByDelYn(backLog2);

        //指派分单人以后  添加分单人的待办信息  办理办理出库
        DeliverConsign deliverConsign = save.getDeliverConsign();
        Order order = deliverConsign.getOrder();
        BackLog newBackLog = new BackLog();
        newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.TRANSACTDELIVER.getMsg());  //功能名称
        newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.TRANSACTDELIVER.getNum());    //功能访问路径标识
        newBackLog.setReturnNo(deliverConsign.getDeliverConsignNo());  //返回单号   出口通知单号
        newBackLog.setInformTheContent(order.getContractNo() + " | " + order.getProjectNo());  //提示内容
        newBackLog.setHostId(one.getId());    //父ID，列表页id
        newBackLog.setFollowId(1);  // 1：为办理和分单    4：为确认出库
        newBackLog.setUid(save.getWareHouseman());   ////经办人id
        backLogService.addBackLogByDelYn(newBackLog);

        return true;
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

                //查询需要外检
                list.add(cb.equal(root.get("outCheck").as(Integer.class), 1));

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
        String eruiToken = (String) ThreadLocalUtil.getObject();
        DeliverDetail dbDeliverDetail = findDetailById(deliverDetail.getId());

        if (dbDeliverDetail == null ||
                (dbDeliverDetail.getStatus() != DeliverDetail.StatusEnum.SAVED_OUT_INSPECT.getStatusCode() &&
                        dbDeliverDetail.getStatus() != DeliverDetail.StatusEnum.SUBMITED_OUTSTOCK.getStatusCode())) {
            throw new Exception(String.format("%s%s%s", "入库质检当前状态不可修改", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The current state of the warehouse quality inspection cannot be modified"));
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
                if (deliverConsignGoods.getSendNum() != 0) {  //本批次发货数量为0的商品不推送信息
                    Goods goods = deliverConsignGoods.getGoods();
                    Goods one1 = goodsDao.findOne(goods.getId());
                    one1.setReleaseDate(deliverDetail.getReleaseDate());//推送   放行日期    到商品表
                    goodsDao.save(one1);
                    //出库质检
                    applicationContext.publishEvent(new OrderProgressEvent(goods.getOrder(), 7, eruiToken));
                }
            }

            //出库质检结果通知：将合格商品通知仓库经办人（合格）（如果仓库经办人不是徐健，那么还要单独发给徐健）
            Project project = null; //项目信息
            //获取项目信息
            DeliverConsign deliverConsign1 = dbDeliverDetail.getDeliverConsign();
            project = deliverConsign1.getOrder().getProject();

            Map<String, Object> map = new HashMap<>();
            map.put("qualityUid", dbDeliverDetail.getWareHouseman());       //仓库经办人id
            map.put("projectNo", project.getProjectNo());        //项目号
            map.put("deliverDetailNo", dbDeliverDetail.getDeliverDetailNo());        //产品放行单号
            map.put("status", 4);        //发送短信标识
            sendSms(map);


            //出库质检提交删除 办理出库质检  待办信息
            BackLog backLog2 = new BackLog();
            backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.DELIVERDETAIL.getNum());    //功能访问路径标识
            backLog2.setHostId(dbDeliverDetail.getId());
            backLogService.updateBackLogByDelYn(backLog2);


            //出库质检提交  添加 确认出库 待办
            DeliverConsign deliverConsign = dbDeliverDetail.getDeliverConsign();
            Order order = deliverConsign.getOrder();
            Project project1 = order.getProject();
            BackLog newBackLog = new BackLog();
            newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.NOTARIZEDELIVER.getMsg());  //功能名称
            newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.NOTARIZEDELIVER.getNum());    //功能访问路径标识
            newBackLog.setReturnNo(deliverConsign.getDeliverConsignNo());  //返回单号
            newBackLog.setInformTheContent(order.getContractNo() + " | " + project1.getProjectNo());  //提示内容
            newBackLog.setHostId(dbDeliverDetail.getId());    //父ID，列表页id
            newBackLog.setFollowId(4);  // 1：为办理和分单    4：为确认出库
            newBackLog.setUid(dbDeliverDetail.getWareHouseman());   //经办人id  仓库经办人id
            backLogService.addBackLogByDelYn(newBackLog);
        }


        // 只接受品控部的附件
      /*  List<Attachment> collect = deliverDetail.getAttachmentList().stream().filter(attachment -> {
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

        dbDeliverDetail.setAttachmentList(attachmentList02);*/

        deliverDetailDao.save(dbDeliverDetail);
        // 处理附件信息 attachmentList 库里存在附件列表 dbAttahmentsMap前端传来参数附件列表
        //deliverConsign1.setAttachmentList(deliverConsign1.getAttachmentList());
        List<Attachment> attachmentList = deliverDetail.getAttachmentList();
        Map<Integer, Attachment> dbAttahmentsMap = dbDeliverDetail.getAttachmentList().parallelStream().collect(Collectors.toMap(Attachment::getId, vo -> vo));
        if (attachmentList != null && attachmentList.size() > 0) {
            attachmentService.updateAttachments(attachmentList, dbAttahmentsMap, dbDeliverDetail.getId(), Attachment.AttachmentCategory.DELIVERDETAIL.getCode());
        }


        return true;
    }

    //出库通知
    public void sendSms(Map<String, Object> map1) throws Exception {

        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (StringUtils.isNotBlank(eruiToken)) {
            try {
                Integer status = (Integer) map1.get("status");  //出库or出库质检or确认出库状态
                // 根据id获取人员信息
                if (status == 2 || status == 4) {   // status 是2的时候  检质检经办人id      status 是4的时候 //仓库经办人id
                    String jsonParam = "{\"id\":\"" + map1.get("qualityUid") + "\"}";
                    Map<String, String> header = new HashMap<>();
                    header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                    header.put("Content-Type", "application/json");
                    header.put("accept", "*/*");
                    String s = HttpRequest.sendPost(memberInformation, jsonParam, header);
                    logger.info("人员详情返回信息：" + s);
                    JSONObject jsonObject = JSONObject.parseObject(s);
                    Integer code = jsonObject.getInteger("code");
                    String mobile = null;  //手机号

                    if (code == 1) {

                        JSONObject data = jsonObject.getJSONObject("data");
                        mobile = data.getString("mobile");
                        if (StringUtil.isBlank(mobile)) {
                            throw new Exception(data.getString("name") + ": 手机号为空");
                        }

                        //发送短信
                        Map<String, String> map = new HashMap();
                        map.put("areaCode", "86");
                        map.put("to", "[\"" + mobile + "\"]");

                        if (status == 2) {
                            map.put("content", "您好，项目号：" + map1.get("projectNo") + "，产品放行单号：" + map1.get("deliverDetailNo") + "，已申请出库报检，请及时处理。感谢您对我们的支持与信任！");
                        } else if (status == 4) {
                            map.put("content", "您好，项目号：" + map1.get("projectNo") + "，产品放行单号：" + map1.get("deliverDetailNo") + "，出库质检已合格，请及时处理。感谢您对我们的支持与信任！");
                        }
                        map.put("subType", "0");
                        map.put("groupSending", "0");
                        map.put("useType", "订单");
                        String s1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                        logger.info("发送短信返回状态" + s1);
                    }
                } else {  // status 是5的时候  是确认出库

                    String jsonParam = "{\"role_no\":\"O020\"}";    //物流分单员
                    Map<String, String> header = new HashMap<>();
                    header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                    header.put("Content-Type", "application/json");
                    header.put("accept", "*/*");
                    String s = HttpRequest.sendPost(memberList, jsonParam, header);
                    logger.info("人员详情返回信息：" + s);
                    JSONObject jsonObject = JSONObject.parseObject(s);
                    Integer code = jsonObject.getInteger("code");
                    if (code == 1) {
                        JSONArray smsarray = new JSONArray();   //手机号JSON数组
                        // 获取人员手机号
                        JSONArray data1 = jsonObject.getJSONArray("data");
                        //去除重复
                        Set<String> listAll = new HashSet<>();
                        for (int i = 0; i < data1.size(); i++) {
                            JSONObject ob = (JSONObject) data1.get(i);
                            String mobile = ob.getString("mobile");
                            if (StringUtil.isNotBlank(mobile)) {
                                listAll.add(mobile);    //获取人员手机号
                            }
                        }
                        listAll = new HashSet<>(new LinkedHashSet<>(listAll));
                        for (String me : listAll) {
                            smsarray.add(me);
                        }
                        //发送短信
                        Map<String, String> map = new HashMap();
                        map.put("areaCode", "86");
                        map.put("to", smsarray.toString());
                        map.put("content", "您好，销售合同号：" + map1.get("projectNo") + "，产品放行单号：" + map1.get("deliverDetailNo") + "，仓储经办人：" + map1.get("wareHousemanName") + "，已出库并上传箱单，请及时处理。感谢您对我们的支持与信任！");
                        map.put("subType", "0");
                        map.put("groupSending", "0");
                        map.put("useType", "订单");
                        String s1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                        logger.info("发送短信返回状态" + s1);
                    }

                }


            } catch (Exception e) {
                throw new Exception(String.format("%s%s%s", "发送短信失败", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Failure to send SMS"));
            }

        }
    }

    //如果是厂家直接发货    推送  出库经办人  到商品表
    public void pushWareHouseman(DeliverDetail one, Integer yn) {
        List<DeliverConsignGoods> deliverConsignGoodsList1 = one.getDeliverConsignGoodsList();
        for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsList1) {
            if (deliverConsignGoods.getSendNum() != 0) {
                Goods one1 = goodsDao.findOne(deliverConsignGoods.getGoods().getId());   //查询到商品信息
                if (one.getWareHouseman() != null) {
                    one1.setWareHouseman(one.getWareHouseman());//推送   出库经办人  到商品表
                }
                if (yn != 1) {    //不等1的时候是确认出库推送  出库日期
                    if (one.getLeaveDate() != null) {
                        one1.setLeaveDate(one.getLeaveDate());//推送   出库日期  到商品表
                    }
                }
                goodsDao.save(one1);
            }
        }
    }


    //推送商品出库时间到订单执行跟踪
    public void pushGoodsLeaverDate(DeliverConsign deliverConsign1, DeliverDetail deliverDetail2) {
        //  V2.0订单执行跟踪   推送商品出库
        OrderLog orderLog = new OrderLog();
        try {
            orderLog.setOrder(orderDao.findOne(deliverConsign1.getOrder().getId()));
            orderLog.setLogType(OrderLog.LogTypeEnum.GOODOUT.getCode());
            orderLog.setOperation(StringUtils.defaultIfBlank(null, OrderLog.LogTypeEnum.GOODOUT.getMsg()));
            orderLog.setEnoperation(StringUtils.defaultIfBlank(null, OrderLog.LogTypeEnum.GOODOUT.getEnMsg()));
            orderLog.setCreateTime(new Date());
            orderLog.setBusinessDate(deliverDetail2.getLeaveDate()); //确认出库时间
            orderLog.setOrdersGoodsId(null);
            orderLogDao.save(orderLog);
        } catch (Exception ex) {
            logger.error("日志记录失败 {}", orderLog.toString());
            logger.error("错误", ex);
            ex.printStackTrace();
        }
    }


}
