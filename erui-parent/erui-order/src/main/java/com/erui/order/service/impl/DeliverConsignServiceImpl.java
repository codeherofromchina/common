package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.DeliverConsignService;
import com.erui.order.service.OrderService;
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
public class DeliverConsignServiceImpl implements DeliverConsignService {

    private static Logger logger = LoggerFactory.getLogger(DeliverDetailServiceImpl.class);

    @Autowired
    private DeliverConsignDao deliverConsignDao;

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private DeliverNoticeDao deliverNoticeDao;


    @Autowired
    private DeliverDetailDao deliverDetailDao;


    @Autowired
    ProjectDao projectDao;

    @Value("#{orderProp[SEND_SMS]}")
    private String sendSms;  //发短信接口

    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口

    @Value("#{orderProp[MEMBER_LIST]}")
    private String memberList;  //查询人员信息调用接口

    @Override
    @Transactional(readOnly = true)
    public DeliverConsign findById(Integer id) {
        DeliverConsign deliverConsign = deliverConsignDao.findOne(id);
        if (deliverConsign != null) {
            deliverConsign.getDeliverConsignGoodsSet().size();
            deliverConsign.getAttachmentSet().size();
        }
        return deliverConsign;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDeliverConsign(DeliverConsign deliverConsign) throws Exception {
        Order order = orderDao.findOne(deliverConsign.getoId());
        DeliverConsign deliverConsignUpdate = deliverConsignDao.findOne(deliverConsign.getId());
        deliverConsignUpdate.setOrder(order);
        deliverConsignUpdate.setDeptId(order.getExecCoId());
        deliverConsignUpdate.setCreateUserId(order.getAgentId());
        deliverConsignUpdate.setWriteDate(deliverConsign.getWriteDate());
        deliverConsignUpdate.setArrivalDate(deliverConsign.getArrivalDate());
        deliverConsignUpdate.setBookingDate(deliverConsign.getBookingDate());
        deliverConsignUpdate.setCreateUserId(deliverConsign.getCreateUserId());
        deliverConsignUpdate.setRemarks(deliverConsign.getRemarks());
        deliverConsignUpdate.setStatus(deliverConsign.getStatus());
        // 处理附件
        List<Attachment> attachments = attachmentService.handleParamAttachment(deliverConsignUpdate.getAttachmentSet(), deliverConsign.getAttachmentSet(), null, null);
        deliverConsignUpdate.setAttachmentSet(attachments);
        // 商品信息
        Map<Integer, DeliverConsignGoods> oldDcGoodsMap = deliverConsignUpdate.getDeliverConsignGoodsSet().parallelStream().collect(Collectors.toMap(DeliverConsignGoods::getId, vo -> vo));
        Map<Integer, Goods> goodsList = order.getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        Set<Integer> orderIds = new HashSet<>();
        for (DeliverConsignGoods dcGoods : deliverConsign.getDeliverConsignGoodsSet()) {
            DeliverConsignGoods deliverConsignGoods = oldDcGoodsMap.remove(dcGoods.getId());
            int oldSendNum = 0;
            if (deliverConsignGoods == null) {
                dcGoods.setId(null);
            } else {
                oldSendNum = deliverConsignGoods.getSendNum();
            }
            Integer gid = dcGoods.getgId();
            Goods goods = goodsList.get(gid);
            //商品需增加发货数量 = 要修改的数量-原发货数量
            //Integer outStockNum = dcGoods.getSendNum() - goods.getOutstockNum();
            if (goods.getOutstockApplyNum() - oldSendNum + dcGoods.getSendNum() <= goods.getContractGoodsNum()) {
                dcGoods.setGoods(goods);
                dcGoods.setCreateTime(new Date());
                if (deliverConsign.getStatus() == 3) {
                    goods.setOutstockNum(goods.getOutstockNum() + dcGoods.getSendNum());
                    orderIds.add(goods.getOrder().getId());
                }
                goods.setOutstockApplyNum(goods.getOutstockApplyNum() - oldSendNum + dcGoods.getSendNum());
                goodsDao.save(goods);
            } else {
                throw new Exception(String.format("%s%s%s", "发货总数量超过合同数量", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Total quantity of shipments exceeds the number of contracts"));
            }
        }
        deliverConsignUpdate.setDeliverConsignGoodsSet(deliverConsign.getDeliverConsignGoodsSet());
        // 被删除的发货通知单商品
        for (DeliverConsignGoods dcGoods : oldDcGoodsMap.values()) {
            Goods goods = dcGoods.getGoods();
            goods.setOutstockApplyNum(goods.getOutstockApplyNum() - dcGoods.getSendNum());
            goodsDao.save(goods);
        }
//        goodsDao.save(goodsList.values());
        deliverConsignDao.saveAndFlush(deliverConsignUpdate);
        if (deliverConsign.getStatus() == 3) {
            orderService.updateOrderDeliverConsignC(orderIds);

            //推送出库信息
            String deliverDetailNo = createDeliverDetailNo();   //产品放行单号
            pushOutbound(deliverConsignUpdate,deliverDetailNo);


            // 出口发货通知单：出口发货通知单提交推送信息到出库，需要通知仓库分单员(根据分单员来发送短信)
            Map<String,Object> map = new HashMap<>();
            map.put("deliverConsignNo",deliverConsignUpdate.getDeliverConsignNo());  //出口通知单号
            map.put("deliverDetailNo",deliverDetailNo);  //产品放行单号
            map.put("contractNoOs",order.getContractNo());     //销售合同号
            try {
                sendSms(map);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return true;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addDeliverConsign(DeliverConsign deliverConsign) throws Exception {
        Order order = orderDao.findOne(deliverConsign.getoId());
        DeliverConsign deliverConsignAdd = new DeliverConsign();
        // 根据数据库中最后的发货通知单单号重新自动生成
        String deliverConsignNo = deliverConsignDao.findLaseDeliverConsignNo();
        deliverConsignAdd.setDeliverConsignNo(StringUtil.genDeliverConsignNo(deliverConsignNo));
        deliverConsignAdd.setOrder(order);
        deliverConsignAdd.setCoId(order.getSigningCo());
        deliverConsignAdd.setDeptId(order.getExecCoId());
        deliverConsignAdd.setExecCoName(order.getExecCoName());
        deliverConsignAdd.setWriteDate(deliverConsign.getWriteDate());
        deliverConsignAdd.setArrivalDate(deliverConsign.getArrivalDate());
        deliverConsignAdd.setBookingDate(deliverConsign.getBookingDate());
        deliverConsignAdd.setCreateUserId(deliverConsign.getCreateUserId());
        deliverConsignAdd.setDeliverYn(deliverConsign.getDeliverYn());
        deliverConsignAdd.setRemarks(deliverConsign.getRemarks());
        deliverConsignAdd.setCountry(order.getCountry());
        deliverConsignAdd.setRegion(order.getRegion());
        deliverConsignAdd.setCreateTime(new Date());
        deliverConsignAdd.setStatus(deliverConsign.getStatus());
        deliverConsignAdd.setDeliverConsignGoodsSet(deliverConsign.getDeliverConsignGoodsSet());
        // 处理附件信息
        List<Attachment> attachments = attachmentService.handleParamAttachment(null, deliverConsign.getAttachmentSet(), null, null);
        deliverConsignAdd.setAttachmentSet(attachments);
        // 处理商品信息
        Map<Integer, Goods> goodsList = order.getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        Set<Integer> orderIds = new HashSet<>();
        for (DeliverConsignGoods dcGoods : deliverConsign.getDeliverConsignGoodsSet()) {
            Integer gid = dcGoods.getgId();
            Goods goods = goodsList.get(gid);
            if (goods.getOutstockApplyNum() + dcGoods.getSendNum() <= goods.getContractGoodsNum()) {
                dcGoods.setGoods(goods);
                dcGoods.setCreateTime(new Date());
                if (deliverConsign.getStatus() == 3) {
                    goods.setOutstockNum(goods.getOutstockNum() + dcGoods.getSendNum());
                    orderIds.add(goods.getOrder().getId());
                }
                goods.setOutstockApplyNum(goods.getOutstockApplyNum() + dcGoods.getSendNum());
                goodsDao.save(goods);
            } else {
                throw new Exception(String.format("%s%s%s", "发货总数量超过合同数量", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Total quantity of shipments exceeds the number of contracts"));
            }
        }
        DeliverConsign deliverConsign1 = deliverConsignDao.save(deliverConsignAdd);
        if (deliverConsign.getStatus() == 3) {
            Project project = order.getProject();
            order.setDeliverConsignHas(2);
            project.setDeliverConsignHas(2);
            orderDao.save(order);
            projectDao.save(project);
            orderService.updateOrderDeliverConsignC(orderIds);

            //推送出库信息
            String deliverDetailNo = createDeliverDetailNo();
            pushOutbound(deliverConsign1,deliverDetailNo);


            // 出口发货通知单：出口发货通知单提交推送信息到出库，需要通知仓库分单员(根据分单员来发送短信)
            Map<String,Object> map = new HashMap<>();
            map.put("deliverConsignNo",deliverConsign1.getDeliverConsignNo());  //出口通知单号
            map.put("deliverDetailNo",deliverDetailNo);  //产品放行单号
            map.put("contractNoOs",order.getContractNo());     //销售合同号
            try {
                sendSms(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliverConsign> findByOrderId(Integer orderId) {
        List<DeliverConsign> deliverConsignList = deliverConsignDao.findByOrderId(orderId);
        for (DeliverConsign deliverConsign : deliverConsignList) {
            deliverConsign.getId();
            deliverConsign.getCoId();
            deliverConsign.getDeliverConsignNo();
            deliverConsign.getWriteDate();
            deliverConsign.getStatus();
            deliverConsign.getDeptId();
            deliverConsign.getCreateUserId();
            deliverConsign.setDeliverConsignGoodsSet(null);
            deliverConsign.setAttachmentSet(null);
        }
        return deliverConsignList;
    }


    /**
     * 根据出口发货通知单 查询信息
     *
     * @param deliverNoticeNos 看货通知单号  数组
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<DeliverConsign> querExitInformMessage(Integer[] deliverNoticeNos) throws Exception {
        List<DeliverConsign> page = deliverConsignDao.findByIdInAndStatus(deliverNoticeNos, 3);
        for (DeliverConsign deliverConsign : page) {
            deliverConsign.getOrder().getId();
            deliverConsign.getAttachmentSet().size();
            List<DeliverConsignGoods> deliverConsignGoodsSet = deliverConsign.getDeliverConsignGoodsSet();
            if (deliverConsignGoodsSet.size() == 0) {
                throw new Exception("出口通知单号号" + deliverConsign.getDeliverConsignNo() + "无出口发货通知单商品信息");
            }
            for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsSet) {
                Goods goods = deliverConsignGoods.getGoods();
                if (goods == null) {
                    throw new Exception("出口发货通知单号" + deliverConsign.getDeliverConsignNo() + "无商品信息");
                }
                Project project = goods.getProject();
                if (project == null) {
                    throw new Exception("出口发货通知单号" + deliverConsign.getDeliverConsignNo() + "无项目信息");
                }
                project.getId();
            }
        }
        return page;

    }


    /**
     * 看货通知管理   查询出口发货通知单
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeliverConsign> queryExitAdvice(DeliverNotice deliverNotice) {

        PageRequest request = new PageRequest(deliverNotice.getPage() - 1, deliverNotice.getRows(), Sort.Direction.DESC, "createTime");

        Page<DeliverConsign> page = deliverConsignDao.findAll(new Specification<DeliverConsign>() {
            @Override
            public Predicate toPredicate(Root<DeliverConsign> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据国家查询
                String[] country = null;
                if (StringUtils.isNotBlank(deliverNotice.getCountry())) {
                    country = deliverNotice.getCountry().split(",");
                }
                if (country != null) {
                    list.add(root.get("country").in(country));
                }

                //根据下单人（国际物流经办人）      销售合同号    项目号
                if (deliverNotice.getSenderId() != null || StringUtil.isNotBlank(deliverNotice.getContractNo()) || StringUtil.isNotBlank(deliverNotice.getProjectNo())) {
                    Join<DeliverConsign, Order> orderRoot = root.join("order");
                    //根据 销售合同号
                    if (StringUtil.isNotBlank(deliverNotice.getContractNo())) {
                        list.add(cb.like(orderRoot.get("contractNo").as(String.class), "%" + deliverNotice.getContractNo() + "%"));
                    }
                    Join<Order, Project> projectRoot = orderRoot.join("project");
                    //根据下单人（国际物流经办人）
                    if (deliverNotice.getSenderId() != null) {
                        list.add(cb.equal(projectRoot.get("logisticsUid").as(Integer.class), deliverNotice.getSenderId()));
                    }
                    //根据 项目号
                    if (StringUtil.isNotBlank(deliverNotice.getProjectNo())) {
                        list.add(cb.like(projectRoot.get("projectNo").as(String.class), "%" + deliverNotice.getProjectNo() + "%"));
                    }
                }

                // 根据出口通知单号
                if (StringUtil.isNotBlank(deliverNotice.getDeliverConsignNo())) {
                    list.add(cb.like(root.get("deliverConsignNo").as(String.class), "%" + deliverNotice.getDeliverConsignNo() + "%"));
                }
                list.add(cb.equal(root.get("status").as(Integer.class), 3));    //已提交
                list.add(cb.equal(root.get("deliverYn").as(Integer.class), 1)); //未删除


                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);

                Predicate result = cb.and(predicates);
                if (deliverNotice.getId() != null) {
                    DeliverNotice one = deliverNoticeDao.findOne(deliverNotice.getId());
                    List<DeliverConsign> deliverConsigns = one.getDeliverConsigns();//查询已选择
                    Integer[] arr = new Integer[deliverConsigns.size()];    //获取id
                    int i = 0;
                    for (DeliverConsign deliverConsign : deliverConsigns) {
                        arr[i] = (deliverConsign.getId());
                        i++;
                    }
                    return cb.or(result, root.get("id").in(arr));
                } else {
                    return result;
                }

            }
        }, request);

       /* if (page != null && page.size() > 1) {
            // 反序排列
            Collections.reverse(page);
        }*/

        return page;

    }


    /**
     * 推送出库信息
     * <p>
     * 订单V2.0
     * <p>
     * 根据出口通知单，推送出库信息
     */

    public void pushOutbound(DeliverConsign deliverConsign1,String deliverDetailNo) throws Exception {

        // 1:未编辑 2：保存/草稿 3:已提交'        当状态为已提交的时候，推送到出库管理
        DeliverDetail deliverDetail = new DeliverDetail();
        deliverDetail.setDeliverConsign(deliverConsign1);    //出库通知单
        deliverDetail.setDeliverDetailNo(deliverDetailNo);   //产品放行单

        //推送仓库经办人   物流经办人
        Order order1 = deliverConsign1.getOrder();
        Project project = order1.getProject();
        /*if (project.getWarehouseUid() != null) {
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
        }*/
        if (project.getQualityUid() != null) {
            deliverDetail.setCheckerUid(project.getQualityUid());    //  检验工程师(品控经办人) ID
        }
        if (StringUtil.isNotBlank(project.getQualityName())) {
            deliverDetail.setCheckerName(project.getQualityName()); //  检验工程师名称(品控经办人名称)
        }

        deliverDetail.setStatus(DeliverDetail.StatusEnum.SAVED_OUTSTOCK.getStatusCode());
        deliverDetail.setDeliverConsignGoodsList(new ArrayList<>(deliverConsign1.getDeliverConsignGoodsSet()));
        try {
            deliverDetailDao.saveAndFlush(deliverDetail);
        } catch (Exception e) {
            throw new Exception("推送出库信息失败");
        }


        //TODO  项目执行跟踪：推送看货通知时间，订舱人
          /*  for (Goods goods : order1.getGoodsList()) {
                Goods one1 = goodsDao.findOne(goods.getId());
                one1.setSendDate(deliverNotice.getSendDate());//发送看货通知日期
                one1.setSenderId(deliverNotice.getSenderId());//订舱人id
                goodsDao.save(one1);
            }*/

    }


    /**
     * \//生成产品放行单
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



    //  出口发货通知单：出口发货通知单提交推送信息到出库，需要通知仓库分单员(根据分单员来发送短信)
    public void sendSms(Map<String,Object> map1) throws  Exception {

        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (StringUtils.isNotBlank(eruiToken)) {
            try{
                // 根据id获取人员信息
                String jsonParam = "{\"role_no\":\"O019\"}";
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                String s = HttpRequest.sendPost(memberList, jsonParam, header);
                logger.info("人员详情返回信息：" + s);

                JSONObject jsonObject = JSONObject.parseObject(s);
                Integer code = jsonObject.getInteger("code");

                if(code == 1){
                    // 获取人员手机号
                    JSONArray data1 = jsonObject.getJSONArray("data");

                    //去除重复
                    Set<String> listAll = new HashSet<>();
                    for (int i = 0; i < data1.size(); i++){
                        JSONObject ob  = (JSONObject)data1.get(i);
                        String mobile = ob.getString("mobile");
                        if(StringUtils.isNotBlank(mobile)){
                            listAll.add(mobile);    //获取人员手机号
                        }
                    }

                    listAll = new HashSet<>(new LinkedHashSet<>(listAll));
                    JSONArray smsarray = new JSONArray();   //手机号JSON数组
                    for (String me : listAll) {
                        smsarray.add(me);
                    }

                    //发送短信
                    Map<String,String> map= new HashMap();
                    map.put("areaCode","86");
                    map.put("to",smsarray.toString());

                    map.put("content"," 您好，销售合同号："+map1.get("contractNoOs")+"，已生成出口通知单号："+map1.get("deliverConsignNo")+"，产品放行单号："+map1.get("deliverDetailNo")+"，请及时处理。感谢您对我们的支持与信任！");
                    map.put("subType","0");
                    map.put("groupSending","0");
                    map.put("useType","订单");
                    String s1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                    logger.info("发送短信返回状态"+s1);
                }
            }catch (Exception e){
                throw new Exception(String.format("%s%s%s","发送短信失败", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL,"Failure to send SMS"));
            }

        }
    }

}
