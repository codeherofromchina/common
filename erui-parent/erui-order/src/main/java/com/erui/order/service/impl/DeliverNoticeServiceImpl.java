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
import com.erui.order.service.DeliverNoticeService;
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

    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口

    @Value("#{orderProp[SEND_SMS]}")
    private String sendSms;  //发短信接口

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
                //根据下单人（国际物流经办人）
                if(condition.getSenderId() != null){
                    Join<DeliverNotice, DeliverConsign> deliverConsignRoot = root.join("deliverConsigns");
                    Join<DeliverConsign, Order> orderRoot = deliverConsignRoot.join("order");
                    Join<Order, Project> projectRoot = orderRoot.join("project");
                    list.add(cb.equal(projectRoot.get("logisticsUid").as(Integer.class),condition.getSenderId()));
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

                List<DeliverConsign> deliverConsignSet = notice.getDeliverConsigns();
                if (deliverConsignSet.size() == 0){
                    throw new Exception(String.format("%s%s%s","无出口发货通知单关系", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL,"Notifications of no export delivery"));
                }
                for (DeliverConsign dc : deliverConsignSet) {
                    String deliverConsignNo = dc.getDeliverConsignNo();
                    if (!StringUtil.isNotBlank(deliverConsignNo)){
                        throw new Exception(String.format("%s%s%s","无出口通知单号", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL,"No export notice single number"));
                    }
                    deliverConsignNos.add(deliverConsignNo);
                    Order order = dc.getOrder();
                    if (order == null){
                        throw new Exception(String.format("%s%s%s","无订单关系", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL,"No order relationship"));
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
        List<DeliverConsign> list = new ArrayList<DeliverConsign>();
        DeliverConsign one = null; //出库通知单
        for (String s :split){
            one = deliverConsignDao.findOne(Integer.parseInt(s));    //改变出口单状态
            list.add(one);

            List<DeliverConsignGoods> deliverConsignGoodsSet = one.getDeliverConsignGoodsSet();
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
        //看货通知单
        String deliverNoticeNo = createDeliverNoticeNo();
        deliverNotice.setDeliverNoticeNo(deliverNoticeNo);

        // 处理附件信息
        List<Attachment> attachmentlist = attachmentService.handleParamAttachment(null, new ArrayList(deliverNotice.getAttachmentSet()), deliverNotice.getCreateUserId(), deliverNotice.getCreateUserName());
        deliverNotice.setAttachmentSet(new HashSet<>(attachmentlist));
        DeliverNotice deliverNotice1=deliverNoticeDao.saveAndFlush(deliverNotice);

        //推送到出库管理
        if (deliverNotice.getStatus() == 2){
            DeliverDetail deliverDetail = new DeliverDetail();
            deliverDetail.setDeliverNotice(deliverNotice);
            String deliverDetailNo = createDeliverDetailNo();
            deliverDetail.setDeliverDetailNo(deliverDetailNo);   //产品放行单

            //推送仓库经办人   物流经办人
            DeliverConsign deliverConsigns1 =  deliverConsignDao.findOne(Integer.parseInt(split[0]));
            Project project = deliverConsigns1.getOrder().getProject();
            if(project.getWarehouseUid() != null){
                deliverDetail.setWareHouseman(project.getWarehouseUid());   //仓库经办人id
            }
            if(StringUtil.isNotBlank(project.getWarehouseName())){
                deliverDetail.setWareHousemanName(project.getWarehouseName());    //仓库经办人名字
            }
            if(project.getLogisticsUid() != null){
                deliverDetail.setLogisticsUserId(project.getLogisticsUid());         //物流经办人id
            }
            if(StringUtil.isNotBlank(project.getLogisticsName())){
                deliverDetail.setLogisticsUserName(project.getLogisticsName());   //物流经办人名字
            }
            if(project.getQualityUid() != null){
                deliverDetail.setCheckerUid(project.getQualityUid());    //  检验工程师(品控经办人) ID
            }
            if(StringUtil.isNotBlank(project.getQualityName())){
                deliverDetail.setCheckerName(project.getQualityName()); //  检验工程师名称(品控经办人名称)
            }


            deliverDetail.setStatus(DeliverDetail.StatusEnum.SAVED_OUTSTOCK.getStatusCode());
            deliverDetail.setDeliverConsignGoodsList(deliverConsignGoodsLists);
            DeliverDetail deliverDetail1=deliverDetailDao.saveAndFlush(deliverDetail);


            //看货通知：看货通知单下达后通知仓库经办人（如果仓库经办人不是徐健，那么还要单独发给徐健）
            Map<String,Object> map = new HashMap<>();
            map.put("warehouseUid",project.getWarehouseUid());  //仓库经办人id
            map.put("projectNo",project.getProjectNo());   //项目号
            map.put("deliverConsignNo",one.getDeliverConsignNo());  //出口通知单号
            map.put("deliverDetailNo",deliverDetail.getDeliverDetailNo());  //产品放行单号
            map.put("logisticsName",project.getLogisticsName());  //国际物流经办人名字
           /* try {
                sendSms(map);
            } catch (Exception e) {
                e.printStackTrace();
            }*/

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
        String[] split = null;
        try {
                if (StringUtil.isNotBlank(deliverNotice.getContractNo())){
                    one.setContractNo(deliverNotice.getContractNo());
                }
            List<DeliverConsignGoods> deliverConsignGoodsLists =null; //出库详情 商品
                if(StringUtil.isNotBlank(deliverNotice.getDeliverConsignNo())){
                    List<DeliverConsign> deliverConsigns = one.getDeliverConsigns(); //已存在的关联关系
                    for (DeliverConsign deliverConsign : deliverConsigns){
                        DeliverConsign ones = deliverConsignDao.findOne(deliverConsign.getId());    //改变出口单状态
                        ones.setDeliverYn(1);
                        deliverConsignDao.saveAndFlush(ones);
                    }
                    split = deliverNotice.getDeliverConsignIds().split(",");    //选中的关联关系
                    List<DeliverConsign> list = new ArrayList<DeliverConsign>();
                    for (String s :split){
                        DeliverConsign one1 = deliverConsignDao.findOne(Integer.parseInt(s));
                        deliverConsignGoodsLists = new ArrayList<>();
                        List<DeliverConsignGoods> deliverConsignGoodsSet = one1.getDeliverConsignGoodsSet();
                        for (DeliverConsignGoods deliverConsignGoods :deliverConsignGoodsSet){
                            deliverConsignGoodsLists.add(deliverConsignGoods);
                        }

                        list.add(one1);
                        one1.setDeliverYn(2);   //改变出口单状态
                        deliverConsignDao.saveAndFlush(one1);
                    }
                    one.setDeliverConsigns(list);
                }
                if(deliverNotice.getSenderId() != null){
                    one.setSenderId(deliverNotice.getSenderId());
                }
                if(StringUtil.isNotBlank(deliverNotice.getSenderName())){
                    one.setSenderName(deliverNotice.getSenderName());
                }
                if(StringUtil.isNotBlank(deliverNotice.getUrgency())){
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
                    String deliverDetailNo = createDeliverDetailNo();
                    deliverDetail.setDeliverDetailNo(deliverDetailNo);   //产品放行单
                    //推送仓库经办人   物流经办人
                    DeliverConsign deliverConsigns1 =  deliverConsignDao.findOne(Integer.parseInt(split[0]));
                        Project project = deliverConsigns1.getOrder().getProject();
                        if(project.getWarehouseUid() != null){
                            deliverDetail.setWareHouseman(project.getWarehouseUid());   //仓库经办人id
                        }
                        if(StringUtil.isNotBlank(project.getWarehouseName())){
                            deliverDetail.setWareHousemanName(project.getWarehouseName());    //仓库经办人名字
                        }
                        if(project.getLogisticsUid() != null){
                            deliverDetail.setLogisticsUserId(project.getLogisticsUid());         //物流经办人id
                        }
                        if(StringUtil.isNotBlank(project.getLogisticsName())){
                            deliverDetail.setLogisticsUserName(project.getLogisticsName());   //物流经办人名字
                        }
                       if(project.getQualityUid() != null){
                         deliverDetail.setCheckerUid(project.getQualityUid());    //  检验工程师(品控经办人) ID
                       }
                       if(StringUtil.isNotBlank(project.getQualityName())){
                           deliverDetail.setCheckerName(project.getQualityName()); //  检验工程师名称(品控经办人名称)
                       }

                    deliverDetail.setStatus(DeliverDetail.StatusEnum.SAVED_OUTSTOCK.getStatusCode());
                    deliverDetail.setDeliverConsignGoodsList(deliverConsignGoodsLists);
                    deliverDetailDao.saveAndFlush(deliverDetail);


                    //看货通知：看货通知单下达后通知仓库经办人（如果仓库经办人不是徐健，那么还要单独发给徐健）
                    Map<String,Object> map = new HashMap<>();
                    map.put("warehouseUid",project.getWarehouseUid());  //仓库经办人id
                    map.put("projectNo",project.getProjectNo());   //项目号
                    map.put("deliverConsignNo",one.getDeliverConsigns().get(0).getDeliverConsignNo());  //出口通知单号
                    map.put("deliverDetailNo",deliverDetail.getDeliverDetailNo());  //产品放行单号
                    map.put("logisticsName",project.getLogisticsName());  //国际物流经办人名字
                  /*  try {
                        sendSms(map);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/

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


    /**
     * //生成产品放行单
     * @return
     */

    public String createDeliverDetailNo(){
        SimpleDateFormat simpleDateFormats = new SimpleDateFormat("yyyy");

        //查询最近插入的产品放行单
       String deliverDetailNo= deliverDetailDao.findDeliverDetailNo();
       if(deliverDetailNo == null){
           String formats = simpleDateFormats.format(new Date());  //当前年份
           return formats+String.format("%04d",1);     //第一个
       }else{
           String substring = deliverDetailNo.substring(0, 4); //获取到产品放行单的年份
           String formats = simpleDateFormats.format(new Date());  //当前年份
           if(substring.equals(formats)){   //判断年份
               String substring1 = deliverDetailNo.substring(4);
               return formats + String.format("%04d", (Integer.parseInt(substring1) + 1));//最大的数值上加1
           }else{
               return formats+String.format("%04d",1);     //第一个
           }
       }

    }

    /**
     * //生成看货通知单
     * @return
     */

    public String createDeliverNoticeNo(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        //查询最近插入的看货通知单
        String  DeliverNoticeNo  = deliverNoticeDao.findDeliverNoticeNo();
        if(DeliverNoticeNo==null){
            String format = simpleDateFormat.format(new Date());    //当前年月日
            return format+String.format("%04d",1); //第一个
        }else{
            String substring = DeliverNoticeNo.substring(0, 8); //获取到产品放行单的年份
            String format = simpleDateFormat.format(new Date());    //当前年月日

            if(substring.equals(format)){   //判断年份
                String substring1 = DeliverNoticeNo.substring(8);
                return format + String.format("%04d", (Integer.parseInt(substring1) + 1));//最大的数值上加1
            }else{
                return format+String.format("%04d",1); //第一个
            }
        }

    }



   // 看货通知：看货通知单下达后通知仓库经办人（如果仓库经办人不是徐健，那么还要单独发给徐健）
    public void sendSms(Map<String,Object> map1) throws  Exception {

        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (StringUtils.isNotBlank(eruiToken)) {
            try{
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

                if(code == 1){
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

                    //发送短信
                    Map<String,String> map= new HashMap();
                    map.put("areaCode","86");
                    map.put("to",smsarray.toString());
                    map.put("content","您好，项目号："+map1.get("projectNo")+"，出口通知单号："+map1.get("deliverConsignNo")+"，产品放行单号："+map1.get("deliverDetailNo")+"，物流经办人："+map1.get("logisticsName")+"，已下发看货通知，请及时处理。感谢您对我们的支持与信任！");
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
