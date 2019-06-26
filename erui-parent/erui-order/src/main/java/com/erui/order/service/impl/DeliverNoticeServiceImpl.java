package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.NewDateUtil;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.BackLogService;
import com.erui.order.service.DeliverNoticeService;
import com.erui.order.service.StatisticsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    OrderLogDao orderLogDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    OrderDao orderDao;
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

    @Override
    @Transactional(readOnly = true)
    public DeliverNotice findById(Integer id) {
        return deliverNoticeDao.findOne(id);
    }


    /**
     * 看货通知管理
     *
     * @param condition
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeliverNotice> listByPage(DeliverNotice condition) throws Exception {
        PageRequest request = new PageRequest(condition.getPage() - 1, condition.getRows(), Sort.Direction.DESC, "id");

        Page<DeliverNotice> page = deliverNoticeDao.findAll(new Specification<DeliverNotice>() {
            @Override
            public Predicate toPredicate(Root<DeliverNotice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据看货通知单号查询
                if (StringUtil.isNotBlank(condition.getDeliverNoticeNo())) {
                    list.add(cb.like(root.get("deliverNoticeNo").as(String.class), "%" + condition.getDeliverNoticeNo() + "%"));
                }
                // 根据销售合同号查询
                if (StringUtil.isNotBlank(condition.getContractNo())) {
                    list.add(cb.like(root.get("contractNo").as(String.class), "%" + condition.getContractNo() + "%"));
                }
                // 根据出口发货通知单号查询
                if (StringUtil.isNotBlank(condition.getDeliverConsignNo())) {
                    list.add(cb.like(root.get("deliverConsignNo").as(String.class), "%" + condition.getDeliverConsignNo() + "%"));
                }
                // 根据下单人查询
                if (condition.getSenderId() != null) {
                    list.add(cb.equal(root.get("senderId").as(Integer.class), condition.getSenderId()));
                }
                // 根据下单日期查询
                if (condition.getSendDate() != null) {
                    list.add(cb.equal(root.get("sendDate").as(Date.class), NewDateUtil.getDate(condition.getSendDate())));
                }
                // 根据客户代码或名称查询
                if (StringUtil.isNotBlank(condition.getCrmCodeOrName())) {
                    list.add(cb.like(root.get("crmCodeOrName").as(String.class), "%" + condition.getCrmCodeOrName() + "%"));
                }
                // 根据看货状态查询
                if (condition.getHandleStatus() != null && condition.getHandleStatus() != 0) {
                    list.add(cb.equal(root.get("handleStatus").as(Integer.class), condition.getHandleStatus()));
                } else {
                    list.add(cb.gt(root.get("status").as(Integer.class), DeliverNotice.StatusEnum.SAVE.getCode()));
                }
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);

        return page;
    }


    //添加
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addDeliverNotice(DeliverNotice deliverNotice) {

        DeliverConsign one = null; //出库通知单
        one = deliverConsignDao.findOne(deliverNotice.getDeliverConsignId());

        //看货通知单
        String deliverNoticeNo = createDeliverNoticeNo();
        deliverNotice.setDeliverNoticeNo(deliverNoticeNo);

        deliverNotice.setDeliverConsign(one);
        deliverNotice.setDeliverConsignNo(one.getDeliverConsignNo());
        deliverNotice.setProjectNo(one.getOrder().getGoodsList().get(0).getProjectNo());
        deliverNotice.setContractNo(one.getContractNo());
        deliverNotice.setOperationSpecialistId(one.getOperationSpecialistId()); // 单证操作 --> 取操作专员信息
        deliverNotice.setOperationSpecialist(one.getOperationSpecialist()); // 单证操作 --> 取操作专员信息
        deliverNotice.setBusinessSketch(one.getBusinessSketch()); // 货名 --> 取订舱中的业务项目简述及中英货物名称
        deliverNotice.setGoodsDepositPlace(one.getGoodsDepositPlace()); // 货物存放地 --> 取订舱中的货物存放地
        deliverNotice.setTradeTerms(one.getOrder().getTradeTerms() + " " + one.getOrder().getToCountry() + one.getOrder().getToPlace()); // 贸易术语/目的国/目的地 --> 取订单贸易术语/目的国/目的地
        int numbers = 0;
        if(one.getDeliverConsignGoodsSet() != null){
            for(DeliverConsignGoods dGoods : one.getDeliverConsignGoodsSet()){
                numbers = dGoods.getSendNum()==null? numbers : numbers + dGoods.getSendNum();
            }
        }
        deliverNotice.setNumbers(numbers); // 产品件数 --> 计算：订舱通知中所有商品的“本次发运数量”之和
        deliverNotice.setTechnicalUid(one.getOrder().getTechnicalId()); // 事业部项目负责人 --> 调取订单中的事业部项目负责人
        deliverNotice.setTransportType(one.getOrder().getTransportType()); // 运输方式 --> 取订舱中的运输方式
        deliverNotice.setArrivalDate(one.getDeliverConsignBookingSpace().getArrivalDate()); // 项目要求交付日期 --> 取订舱中的要求运抵目的地日期
        deliverNotice.setOrderEmergency(one.getDeliverConsignBookingSpace().getOrderEmergency()); // 紧急程度 --> 取订舱通知中的紧急程度

        deliverNotice.setCrmCodeOrName(one.getCrmCodeOrName()); // 客户代码或名称 --> 取订舱客户代码或名称
        deliverNotice.setDeptName(one.getDeptName()); // 发货申请部门 --> 取订舱发货申请部门

        if (deliverNotice.getStatus() == DeliverNotice.StatusEnum.SUBMIT.getCode()) {
            deliverNotice.setSendDate(new Date()); // 下单日期 --> 取点击“提交”按钮日期
            deliverNotice.setHandleStatus(1); // 未处理
        } else {
            deliverNotice.setHandleStatus(0);
        }

        deliverNotice.setCreateTime(new Date());
        deliverNotice.setTenant("erui");

        DeliverNotice deliverNotice1 = deliverNoticeDao.saveAndFlush(deliverNotice);
        if (deliverNotice.getAttachmentSet() != null && deliverNotice.getAttachmentSet().size() > 0) {
            List<Attachment> attachmentList = new ArrayList<>(deliverNotice.getAttachmentSet());
            attachmentService.addAttachments(attachmentList, deliverNotice1.getId(), Attachment.AttachmentCategory.DELIVERNOTICE.getCode());
        }
        //改变出口单状态
        one.setDeliverNoticeStatus(deliverNotice.getStatus());
        deliverConsignDao.saveAndFlush(one);

        return true;
    }


    //编辑/保存
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDeliverNotice(DeliverNotice deliverNotice) {
        DeliverNotice one = deliverNoticeDao.findOne(deliverNotice.getId());
        if (one == null) {
            return false;
        }
        try {
            if (StringUtil.isNotBlank(deliverNotice.getOtherReq())) {
                one.setOtherReq(deliverNotice.getOtherReq());
            }
            if (StringUtil.isNotBlank(deliverNotice.getPrepareReq())) {
                one.setPrepareReq(deliverNotice.getPrepareReq());
            }
            if (StringUtil.isNotBlank(deliverNotice.getPackageReq())) {
                one.setPackageReq(deliverNotice.getPackageReq());
            }
            one.setStatus(deliverNotice.getStatus());

            if (deliverNotice.getStatus() == DeliverNotice.StatusEnum.SUBMIT.getCode()) {
                deliverNotice.setSendDate(new Date()); // 下单日期 --> 取点击“提交”按钮日期
                deliverNotice.setHandleStatus(1); // 未处理
            }else if (deliverNotice.getStatus() == DeliverNotice.StatusEnum.DONE.getCode()) {
                one.setHandleStatus(2); // 已处理
            }
            deliverNotice.setUpdateTime(new Date());

            DeliverNotice save = deliverNoticeDao.saveAndFlush(one);
            // 附件处理
            List<Attachment> attachmentList = new ArrayList<>(deliverNotice.getAttachmentSet());
            if (attachmentList != null && attachmentList.size() > 0) {
                Map<Integer, Attachment> dbAttahmentsMap = one.getAttachmentSet().parallelStream().collect(Collectors.toMap(Attachment::getId, vo -> vo));
                attachmentService.updateAttachments(attachmentList, dbAttahmentsMap, one.getId(), Attachment.AttachmentCategory.DELIVERNOTICE.getCode());
            }

            // 出库通知单
            DeliverConsign deliverConsign = save.getDeliverConsign();
            // 改变出口单状态
            deliverConsign.setDeliverNoticeStatus(deliverNotice.getStatus());
            deliverConsignDao.saveAndFlush(deliverConsign);

            // 推送出库信息
            if (one.getStatus() == DeliverNotice.StatusEnum.DONE.getCode()) {
                pushOutStock(one.getDeliverConsign(), one);
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


    /**
     * 推送出库信息
     * <p>
     * 订单V2.0
     * <p>
     * 根据出口通知单，推送出库信息
     */
    private void pushOutStock(DeliverConsign deliverConsign, DeliverNotice deliverNotice) throws Exception {
        if (deliverConsign.getStatus() == DeliverConsign.StatusEnum.SUBMIT.getCode() && deliverConsign.getAuditingStatus() == 4) {
            Order order = deliverConsign.getOrder();
            order.setDeliverConsignHas(2);
            boolean flag = order.getGoodsList().parallelStream().allMatch(vo -> vo.getContractGoodsNum() == vo.getOutstockNum());
            if (flag) {
                order.setDeliverConsignC(Boolean.FALSE);
            }
            orderDao.save(order);
            //推送出库信息
            String deliverDetailNo = createDeliverDetailNo();   //产品放行单号
            DeliverDetail deliverDetail = pushOutbound(deliverConsign, deliverDetailNo, deliverNotice);
            // 出口发货通知单：出口发货通知单提交推送信息到出库，需要通知仓库分单员(根据分单员来发送短信)
            Map<String, Object> map = new HashMap<>();
            map.put("deliverConsignNo", deliverConsign.getDeliverConsignNo());  //出口通知单号
            map.put("deliverDetailNo", deliverDetailNo);  //产品放行单号
            map.put("contractNoOs", order.getContractNo());     //销售合同号
            try {
                sendSms(map);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
            //出口发货通知单提交的时候，推送给出库分单员  办理分单
            addBackLog(order, deliverDetail);

        }
    }

    /**
     * 推送出库信息
     * <p>
     * 订单V2.0
     * <p>
     * 根据出口通知单，推送出库信息
     */

    public DeliverDetail pushOutbound(DeliverConsign deliverConsign1, String deliverDetailNo, DeliverNotice deliverNotice) throws Exception {

        // 1:未编辑 2：保存/草稿 3:已提交'        当状态为已提交的时候，推送到出库管理
        DeliverDetail deliverDetail = new DeliverDetail();
        deliverDetail.setDeliverConsign(deliverConsign1);    //出库通知单
        deliverDetail.setDeliverDetailNo(deliverDetailNo);   //产品放行单

        //推送仓库经办人   物流经办人
        Order order1 = deliverConsign1.getOrder();
        Project project = order1.getProject();
        if (project.getWarehouseUid() != null) {
            deliverDetail.setWareHouseman(project.getWarehouseUid());   //仓库经办人id
        }
        if (StringUtil.isNotBlank(project.getWarehouseName())) {
            deliverDetail.setWareHousemanName(project.getWarehouseName());    //仓库经办人名字
        }
        if (project.getLogisticsUid() != null) {
            deliverDetail.setLogisticsUserId(project.getLogisticsUid());         //物流经办人id
        }
        if (StringUtil.isNotBlank(project.getLogisticsName())) {
            deliverDetail.setLogisticsUserName(project.getLogisticsName());   //物流经办人名字
        }
        if (project.getQualityUid() != null) {
            deliverDetail.setCheckerUid(project.getQualityUid());    //  检验工程师(品控经办人) ID
        }
        if (StringUtil.isNotBlank(project.getQualityName())) {
            deliverDetail.setCheckerName(project.getQualityName()); //  检验工程师名称(品控经办人名称)
        }

        deliverDetail.setStatus(DeliverDetail.StatusEnum.SAVED_OUTSTOCK.getStatusCode());
        deliverDetail.setDeliverConsignGoodsList(new ArrayList<>(deliverConsign1.getDeliverConsignGoodsSet()));
        deliverDetail.setOutCheck(1); // 是否外检 默认为1
        try {
            DeliverDetail deliverDetail1 = deliverDetailDao.saveAndFlush(deliverDetail);
            //项目执行跟踪：推送看货通知时间，订舱人
            for (Goods goods : order1.getGoodsList()) {
                Goods one1 = goodsDao.findOne(goods.getId());
                one1.setSendDate(deliverNotice.getSendDate());//发送看货通知日期
                one1.setSenderId(deliverNotice.getSenderId());//订舱人id
                goodsDao.save(one1);
            }
            return deliverDetail1;
        } catch (Exception e) {
            throw new Exception("推送出库信息失败");
        }
    }

    public void addBackLog(Order order, DeliverDetail deliverDetai) throws Exception {

        //出口发货通知单提交的时候，推送给出库分单员  办理分单

        List<Integer> listAll = new ArrayList<>(); //分单员id

        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (StringUtils.isNotBlank(eruiToken)) {
            Map<String, String> header = new HashMap<>();
            header.put(CookiesUtil.TOKEN_NAME, eruiToken);
            header.put("Content-Type", "application/json");
            header.put("accept", "*/*");
            try {
                //获取仓库分单员
                String jsonParam = "{\"role_no\":\"O019\"}";
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

        if (listAll.size() > 0) {
            for (Integer in : listAll) { //分单员有几个人推送几条
                BackLog newBackLog = new BackLog();
                newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.INSTOCKSUBMENUDELIVER.getMsg());  //功能名称
                newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.INSTOCKSUBMENUDELIVER.getNum());    //功能访问路径标识
                newBackLog.setReturnNo(order.getContractNo());  //返回单号
                String region = order.getRegion();   //所属地区
                Map<String, String> bnMapZhRegion = statisticsService.findBnMapZhRegion();
                String country = order.getCountry();  //国家
                Map<String, String> bnMapZhCountry = statisticsService.findBnMapZhCountry();
                newBackLog.setInformTheContent(bnMapZhRegion.get(region) + " | " + bnMapZhCountry.get(country));  //提示内容
                newBackLog.setHostId(deliverDetai.getId());    //父ID，列表页id
                newBackLog.setFollowId(1);  // 1：为办理和分单    4：为确认出库
                newBackLog.setUid(in);   ////经办人id
                backLogService.addBackLogByDelYn(newBackLog);
            }
        }


    }

    /**
     * 看货通知单id    查询看货信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public DeliverNotice queryDeliverNoticeDetail(Integer id) {
        DeliverNotice deliverNotice = deliverNoticeDao.findOne(id);
        deliverNotice.setDeliverConsignId(deliverNotice.getDeliverConsign().getId());
        List<Attachment> attachments = attachmentService.queryAttachs(deliverNotice.getId(), Attachment.AttachmentCategory.DELIVERNOTICE.getCode());
        if (attachments != null && attachments.size() > 0) {
            deliverNotice.setAttachmentSet(attachments.stream().collect(Collectors.toSet()));
        }
        deliverNotice.setDeliverConsignGoodsSet(deliverNotice.getDeliverConsign().getDeliverConsignGoodsSet());
        return deliverNotice;
    }

    /**
     *  出口通知单id  查询看货信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public DeliverNotice queryByDeliverConsignId(Integer id) {
        DeliverConsign one = null; //出库通知单
        one = deliverConsignDao.findOne(id);
        DeliverNotice deliverNotice = one.getDeliverNotice();
        deliverNotice.setDeliverConsignId(id);
        List<Attachment> attachments = attachmentService.queryAttachs(deliverNotice.getId(), Attachment.AttachmentCategory.DELIVERNOTICE.getCode());
        if (attachments != null && attachments.size() > 0) {
            deliverNotice.setAttachmentSet(attachments.stream().collect(Collectors.toSet()));
        }
        deliverNotice.setDeliverConsignGoodsSet(deliverNotice.getDeliverConsign().getDeliverConsignGoodsSet());
        return deliverNotice;
    }


    /**
     * 生成产品放行单
     *
     * @return
     */

    public String createDeliverDetailNo() {
        SimpleDateFormat simpleDateFormats = new SimpleDateFormat("yyyy");

        //查询最近插入的产品放行单
        String deliverDetailNo = deliverDetailDao.findDeliverDetailNo();
        if (deliverDetailNo == null) {
            String formats = simpleDateFormats.format(new Date());  //当前年份
            return formats + String.format("%04d", 1);     //第一个
        } else {
            String substring = deliverDetailNo.substring(0, 4); //获取到产品放行单的年份
            String formats = simpleDateFormats.format(new Date());  //当前年份
            if (substring.equals(formats)) {   //判断年份
                String substring1 = deliverDetailNo.substring(4);
                return formats + String.format("%04d", (Integer.parseInt(substring1) + 1));//最大的数值上加1
            } else {
                return formats + String.format("%04d", 1);     //第一个
            }
        }

    }

    /**
     * 生成看货通知单
     *
     * @return
     */

    public String createDeliverNoticeNo() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        //查询最近插入的看货通知单
        String DeliverNoticeNo = deliverNoticeDao.findDeliverNoticeNo();
        if (DeliverNoticeNo == null) {
            String format = simpleDateFormat.format(new Date());    //当前年月日
            return format + String.format("%04d", 1); //第一个
        } else {
            String substring = DeliverNoticeNo.substring(0, 8); //获取到产品放行单的年份
            String format = simpleDateFormat.format(new Date());    //当前年月日

            if (substring.equals(format)) {   //判断年份
                String substring1 = DeliverNoticeNo.substring(8);
                return format + String.format("%04d", (Integer.parseInt(substring1) + 1));//最大的数值上加1
            } else {
                return format + String.format("%04d", 1); //第一个
            }
        }

    }


    // 看货通知：看货通知单下达后通知仓库经办人（如果仓库经办人不是徐健，那么还要单独发给徐健）
    public void sendSms(Map<String, Object> map1) throws Exception {

        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (StringUtils.isNotBlank(eruiToken)) {
            try {
                // 根据id获取人员信息
                String jsonParam = "{\"id\":\"" + map1.get("warehouseUid") + "\"}";
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                String s = HttpRequest.sendPost(memberInformation, jsonParam, header);
                logger.info("人员详情返回信息：" + s);

                JSONObject jsonObject = JSONObject.parseObject(s);
                Integer code = jsonObject.getInteger("code");

                if (code == 1) {
                    // 获取人员手机号
                    JSONObject data = jsonObject.getJSONObject("data");
                    //去除重复
                    Set<String> listAll = new HashSet<>();
                    listAll.add(data.getString("mobile"));//采购人员手机号
                    //获取徐健 手机号

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
                        name = datas.getString("mobile");
                    }
                    /*listAll.add("15066060360");*/
                    listAll.add(name);

                    /*listAll.add("15066060360");*/
                    listAll = new HashSet<>(new LinkedHashSet<>(listAll));
                    JSONArray smsarray = new JSONArray();
                    for (String me : listAll) {
                        smsarray.add(me);
                    }

                    //发送短信
                    Map<String, String> map = new HashMap();
                    map.put("areaCode", "86");
                    map.put("to", smsarray.toString());
                    map.put("content", "您好，项目号：" + map1.get("projectNo") + "，出口通知单号：" + map1.get("deliverConsignNo") + "，产品放行单号：" + map1.get("deliverDetailNo") + "，物流经办人：" + map1.get("logisticsName") + "，已下发看货通知，请及时处理。感谢您对我们的支持与信任！");
                    map.put("subType", "0");
                    map.put("groupSending", "0");
                    map.put("useType", "订单");
                    String s1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                    logger.info("发送短信返回状态" + s1);
                }
            } catch (Exception e) {
                throw new Exception(String.format("%s%s%s", "发送短信失败", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Failure to send SMS"));
            }

        }
    }


}
