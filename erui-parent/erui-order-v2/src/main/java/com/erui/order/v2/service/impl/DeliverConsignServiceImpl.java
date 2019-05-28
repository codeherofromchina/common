package com.erui.order.v2.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.v2.dao.DeliverConsignGoodsMapper;
import com.erui.order.v2.dao.DeliverConsignMapper;
import com.erui.order.v2.dao.OrderMapper;
import com.erui.order.v2.model.*;
import com.erui.order.v2.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 新的出口通知单业务类
 *
 * @Auther 王晓丹
 * @Date 2019/4/28 下午2:17
 */
@Service("deliverConsignServiceImplV2")
@Transactional(timeout = 500)
public class DeliverConsignServiceImpl implements DeliverConsignService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeliverConsignServiceImpl.class);
    @Autowired
    private DeliverConsignMapper deliverConsignMapper;
    @Autowired
    private DeliverConsignGoodsMapper deliverConsignGoodsMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private DeliverdetailService deliverdetailService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private BacklogService backlogService;
    @Value("#{orderV2Prop[MEMBER_LIST]}")
    private String memberList;  //查询人员信息调用接口
    @Value("#{orderV2Prop[SEND_SMS]}")
    private String sendSms;  //发短信接口
    @Autowired
    private OrderMapper orderMapper;
    @Value("#{orderProp[CREDIT_EXTENSION]}")
    private String creditExtension;  //授信服务器地址


    /**
     * 根据业务流的实例ID查找出口通知单信息
     *
     * @param processId
     * @return
     */
    public DeliverConsign findDeliverConsignByProcessId(String processId) {
        DeliverConsignExample example = new DeliverConsignExample();
        DeliverConsignExample.Criteria criteria = example.createCriteria();
        criteria.andProcessIdEqualTo(processId);
        List<DeliverConsign> deliverConsigns = deliverConsignMapper.selectByExample(example);
        if (deliverConsigns != null || deliverConsigns.size() > 0) {
            return deliverConsigns.get(0);
        }
        return null;
    }

    @Override
    public void updateAuditProcessDone(String processInstanceId, String auditingProcess, String assignee) {
        // 查询出口通知单
        DeliverConsign deliverConsign = findDeliverConsignByProcessId(processInstanceId);
        if (deliverConsign == null) {
            return;
        }
        // 更新审核进度，如果审核进度为空，则更新审核状态为通过
        String auditingProcess2 = deliverConsign.getAuditingProcess();
        String auditingUserName = deliverConsign.getAuditingUser();
        String auditingUserId = deliverConsign.getAuditingUserId();
        String audiRemark = deliverConsign.getAudiRemark();
        if (StringUtils.isNotBlank(auditingProcess2)) {
            List<String> auditingProcessList = Arrays.asList(auditingProcess2.split(","));
            String[] auditingUserIdArr = null;
            String[] auditingUserNameArr = null;
            if (StringUtils.isNotBlank(auditingUserId)) {
                auditingUserIdArr = auditingUserId.split(",");
            } else {
                auditingUserIdArr = new String[auditingProcessList.size()];
            }
            if (StringUtils.isNotBlank(auditingUserName)) {
                auditingUserNameArr = auditingUserName.split(",");
            } else {
                auditingUserNameArr = new String[auditingProcessList.size()];
            }

            String[] auditingUserIdArr02 = new String[auditingProcessList.size() -1];
            String[] auditingUserNameArr02 = new String[auditingProcessList.size() -1];
            Iterator<String> iterator = auditingProcessList.iterator();
            int i = 0;
            int n = 0;
            boolean removed = false;
            while (iterator.hasNext()) {
                String next = iterator.next();
                if (StringUtils.equals(next, auditingProcess)) {
                    iterator.remove();
                    removed = true;
                } else {
                    auditingUserIdArr02[n] = auditingUserIdArr[i];
                    auditingUserNameArr02[n] = auditingUserNameArr[i];
                    ++n;
                }
                ++i;
            }

            auditingProcess2 = StringUtils.join(auditingProcessList, ",");
            if (removed) {
                auditingUserId = StringUtils.join(auditingUserIdArr02, ",");
                auditingUserName = StringUtils.join(auditingUserNameArr02, ",");
            } else {
                auditingUserId = StringUtils.join(auditingUserIdArr, ",");
                auditingUserName = StringUtils.join(auditingUserNameArr02, ",");
            }
        }
        // 设置审核人
        // 通过工号查找用户ID
        Long userId = userService.findIdByUserNo(assignee);
        if (StringUtils.isNotBlank(audiRemark)) {
            if (userId != null && !audiRemark.contains("," + userId + ",")) {
                audiRemark += "," + userId + ",";
            }
        } else if (userId != null) {
            audiRemark = "," + userId + ",";
        }
        // 更新修正后的状态
        DeliverConsign deliverConsignSelective = new DeliverConsign();
        deliverConsignSelective.setId(deliverConsign.getId());

        deliverConsignSelective.setAuditingProcess(auditingProcess2);
        deliverConsignSelective.setAuditingUser(auditingUserName);
        deliverConsignSelective.setAuditingUserId(auditingUserId);
        deliverConsignSelective.setAudiRemark(audiRemark);
        deliverConsignMapper.updateByPrimaryKeySelective(deliverConsignSelective);
    }

    @Override
    public void updateAuditProcessDoing(String processInstanceId, String auditingProcess, String taskId) {
        // 查询出口通知单
        DeliverConsign deliverConsign = findDeliverConsignByProcessId(processInstanceId);
        if (deliverConsign == null) {
            return;
        }
        // 处理出口通知单的审核状态和审核进度
        Integer auditingStatus = 2; // 2:审核中
        String auditingProcess2 = deliverConsign.getAuditingProcess();
        String auditingUserName = deliverConsign.getAuditingUser();
        String auditingUserId = deliverConsign.getAuditingUserId();
        if (StringUtils.isNotBlank(auditingProcess2)) {
            Set<String> set = new HashSet<>(Arrays.asList(auditingProcess2.split(",")));
            if (!set.contains(auditingProcess)) {
                auditingProcess2 = auditingProcess2 + "," + auditingProcess;
                auditingUserName += ",";
                auditingUserId += ",";
            }
        } else {
            auditingProcess2 = auditingProcess;
        }
        // 更新修正后的状态
        DeliverConsign deliverConsignSelective = new DeliverConsign();
        deliverConsignSelective.setId(deliverConsign.getId());
        deliverConsignSelective.setAuditingStatus(auditingStatus);
        deliverConsignSelective.setAuditingProcess(auditingProcess2);
        deliverConsignSelective.setAuditingUser(auditingUserName);
        deliverConsignSelective.setAuditingUserId(auditingUserId);
        deliverConsignSelective.setTaskId(taskId);
        deliverConsignMapper.updateByPrimaryKeySelective(deliverConsignSelective);
    }


    /**
     * 通过业务流实例ID查找出口通知单
     *
     * @param processInstanceId
     * @return
     */
    private DeliverConsign findByProcessInstanceId(String processInstanceId) {
        DeliverConsignExample example = new DeliverConsignExample();
        example.createCriteria().andProcessIdEqualTo(processInstanceId);

        List<DeliverConsign> deliverConsigns = deliverConsignMapper.selectByExample(example);
        if (deliverConsigns != null && deliverConsigns.size() > 0) {
            return deliverConsigns.get(0);
        }
        return null;
    }

    @Override
    public void updateProcessCompleted(String processInstanceId) {
        // 订舱审核结束后动作
        DeliverConsign deliverConsign = findByProcessInstanceId(processInstanceId);
        if (deliverConsign == null) {
            return;
        }
        Integer orderId = deliverConsign.getOrderId();
        Order order = orderService.findOrderById(orderId);
        order.setDeliverConsignHas(2); // 设置订单已生成出口通知单
        // 判断订单商品是否已经全部创建出口发货通知单
        boolean flag = goodsService.isAllOrderGoodsOutstock(orderId);
        if (flag) {
            order.setDeliverConsignC(Boolean.FALSE); // 设置为不可创建出口通知单
        }
        orderService.updateById(orderId, order);

        //推送出库信息
        String deliverDetailNo = createDeliverDetailNo();   //产品放行单号
        DeliverDetail deliverDetail = pushOutbound(deliverConsign, deliverDetailNo);

        // 更新修正后的状态
        DeliverConsign deliverConsignSelective = new DeliverConsign();
        deliverConsignSelective.setId(deliverConsign.getId());
        deliverConsignSelective.setAuditingStatus(4);
        deliverConsignSelective.setAuditingProcess("999"); // 999为审核进度审核完成
        deliverConsignMapper.updateByPrimaryKeySelective(deliverConsignSelective);

        // 出口发货通知单：出口发货通知单提交推送信息到出库，需要通知仓库分单员(根据分单员来发送短信)
        Map<String, Object> map = new HashMap<>();
        map.put("deliverConsignNo", deliverConsign.getDeliverConsignNo());  //出口通知单号
        map.put("deliverDetailNo", deliverDetailNo);  //产品放行单号
        map.put("contractNoOs", order.getContractNo());     //销售合同号
        try {
            sendSms(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //出口发货通知单提交的时候，推送给出库分单员  办理分单
            addBackLog(order, deliverDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 推送出库信息
     * <p>
     * 订单V2.0
     * <p>
     * 根据出口通知单，推送出库信息
     */
    public DeliverDetail pushOutbound(DeliverConsign deliverConsign, String deliverDetailNo) {
        // 1:未编辑 2：保存/草稿 3:已提交'        当状态为已提交的时候，推送到出库管理
        DeliverDetailWithBLOBs deliverDetail = new DeliverDetailWithBLOBs();
        deliverDetail.setTenant("erui");
        deliverDetail.setDeliverConsignId(deliverConsign.getId());  //出库通知单
        deliverDetail.setDeliverDetailNo(deliverDetailNo);   //产品放行单

        //推送仓库经办人   物流经办人
        Project project = projectService.findProjectByOrderId(deliverConsign.getOrderId());
        if (project.getQualityUid() != null) {
            deliverDetail.setCheckerUid(project.getQualityUid());    //  检验工程师(品控经办人) ID
        }
        if (StringUtil.isNotBlank(project.getQualityName())) {
            deliverDetail.setCheckerName(project.getQualityName()); //  检验工程师名称(品控经办人名称)
        }
        deliverDetail.setStatus(DeliverDetail.StatusEnum.SAVED_OUTSTOCK.getStatusCode());


        deliverdetailService.insert(deliverDetail);

        // 设置出库信息的商品信息
        List<DeliverConsignGoods> deliverConsignGoods = findDeliverConsignGoods(deliverConsign.getId());
        if (deliverConsignGoods.size() > 0) {
            List<Integer> deliverConsignGoodsIds = deliverConsignGoods.stream().map(vo -> vo.getId()).collect(Collectors.toList());
            deliverdetailService.insertDeliverGoodsRelation(deliverDetail.getId(), deliverConsignGoodsIds);
        }

        return deliverDetail;
    }

    /**
     * 查询出口通知单相关商品信息
     *
     * @param deliverConsignId
     * @return
     */
    public List<DeliverConsignGoods> findDeliverConsignGoods(Integer deliverConsignId) {
        DeliverConsignGoodsExample example = new DeliverConsignGoodsExample();
        example.createCriteria().andDeliverConsignIdEqualTo(deliverConsignId);

        List<DeliverConsignGoods> deliverConsignGoods = deliverConsignGoodsMapper.selectByExample(example);
        if (deliverConsignGoods == null) {
            deliverConsignGoods = new ArrayList<>();
        }
        return deliverConsignGoods;
    }


    /**
     * 生成产品放行单单号
     *
     * @return
     */
    public String createDeliverDetailNo() {
        SimpleDateFormat simpleDateFormats = new SimpleDateFormat("yyyy");
        //查询最近插入的产品放行单
        String deliverDetailNo = deliverdetailService.findNewestDeliverDetailNo();
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


    public void addBackLog(Order order, DeliverDetail deliverDetai) throws Exception {

        //出口发货通知单提交的时候，推送给出库分单员  办理分单
        List<Integer> listAll = new ArrayList<>(); //分单员id

        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(eruiToken)) {
            Map<String, String> header = new HashMap<>();
            header.put(CookiesUtil.TOKEN_NAME, eruiToken);
            header.put("Content-Type", "application/json");
            header.put("accept", "*/*");
            try {
                //获取仓库分单员
                String jsonParam = "{\"role_no\":\"O019\"}";
                String s2 = HttpRequest.sendPost(memberList, jsonParam, header);
                LOGGER.info("人员详情返回信息：" + s2);

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
                Backlog newBackLog = new Backlog();
                newBackLog.setFunctionExplainName(Backlog.ProjectStatusEnum.INSTOCKSUBMENUDELIVER.getMsg());  //功能名称
                newBackLog.setFunctionExplainId(Backlog.ProjectStatusEnum.INSTOCKSUBMENUDELIVER.getNum());    //功能访问路径标识
                newBackLog.setReturnNo(order.getContractNo());  //返回单号
                String region = order.getRegion();   //所属地区
                String country = order.getCountry();  //国家
                newBackLog.setInformTheContent(region + " | " + country);  //提示内容
                newBackLog.setHostId(deliverDetai.getId());    //父ID，列表页id
                newBackLog.setFollowId(1);  // 1：为办理和分单    4：为确认出库
                newBackLog.setUid(in);   ////经办人id
                backlogService.addBackLogByDelYn(newBackLog);
            }
        }


    }

    @Override
    public DeliverConsign selectById(Integer id) {
        return deliverConsignMapper.selectByPrimaryKey(id);
    }

    @Override
    public Map<Integer, String> findDeliverConsignNoByDeliverConsignIds(List<Integer> deliverConsignIds) {
        DeliverConsignExample example = new DeliverConsignExample();
        DeliverConsignExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(deliverConsignIds);
        List<DeliverConsign> deliverConsigns = deliverConsignMapper.selectByExample(example);

        Map<Integer, String> result = null;
        if (deliverConsigns != null && deliverConsigns.size() > 0) {
            result = deliverConsigns.stream().collect(Collectors.toMap(vo -> vo.getId(), vo -> vo.getDeliverConsignNo()));
        }
        if (result == null) {
            result = new HashMap<>();
        }
        return result;
    }

    //  出口发货通知单：出口发货通知单提交推送信息到出库，需要通知仓库分单员(根据分单员来发送短信)
    public void sendSms(Map<String, Object> map1) throws Exception {
        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (StringUtils.isNotBlank(eruiToken)) {
            try {
                // 根据id获取人员信息
                String jsonParam = "{\"role_no\":\"O019\"}";
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                String s = HttpRequest.sendPost(memberList, jsonParam, header);
                LOGGER.info("人员详情返回信息：" + s);

                JSONObject jsonObject = JSONObject.parseObject(s);
                Integer code = jsonObject.getInteger("code");

                if (code == 1) {
                    // 获取人员手机号
                    JSONArray data1 = jsonObject.getJSONArray("data");

                    //去除重复
                    Set<String> listAll = new HashSet<>();
                    for (int i = 0; i < data1.size(); i++) {
                        JSONObject ob = (JSONObject) data1.get(i);
                        String mobile = ob.getString("mobile");
                        if (StringUtils.isNotBlank(mobile)) {
                            listAll.add(mobile);    //获取人员手机号
                        }
                    }

                    listAll = new HashSet<>(new LinkedHashSet<>(listAll));
                    JSONArray smsarray = new JSONArray();   //手机号JSON数组
                    for (String me : listAll) {
                        smsarray.add(me);
                    }

                    //发送短信
                    Map<String, String> map = new HashMap();
                    map.put("areaCode", "86");
                    map.put("to", smsarray.toString());

                    map.put("content", " 您好，销售合同号：" + map1.get("contractNoOs") + "，已生成出口通知单号：" + map1.get("deliverConsignNo") + "，产品放行单号：" + map1.get("deliverDetailNo") + "，请及时处理。感谢您对我们的支持与信任！");
                    map.put("subType", "0");
                    map.put("groupSending", "0");
                    map.put("useType", "订单");
                    String s1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                    LOGGER.info("发送短信返回状态" + s1);
                }
            } catch (Exception e) {
                throw new Exception(String.format("%s%s%s", "发送短信失败", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Failure to send SMS"));
            }

        }
    }

    /**
     * 驳回出口通知单补偿授信额度
     *
     * @param id
     */
    @Override
    public void rejectDeliverConsign(Integer id) throws Exception {
        DeliverConsign deliverConsign = deliverConsignMapper.selectByPrimaryKey(id);
        Order order = orderMapper.selectByPrimaryKey(deliverConsign.getOrderId());
        //（1）当“本批次发货金额”≤“预收金额”+“可用授信额度/汇率”时，系统判定可以正常发货。
        //（2）当“本批次发货金额”＞“预收金额”+“可用授信额度/汇率”时，系统判定不允许发货
        BigDecimal advanceMoney = order.getAdvanceMoney() == null ? BigDecimal.valueOf(0) : order.getAdvanceMoney();//预收金额      /应收账款余额
        BigDecimal thisShipmentsMoney = deliverConsign.getThisShipmentsMoney() == null ? BigDecimal.valueOf(0.00) : deliverConsign.getThisShipmentsMoney();//本批次发货金额
        BigDecimal exchangeRate = order.getExchangeRate() == null ? BigDecimal.valueOf(1) : order.getExchangeRate();//订单中利率
        //获取授信额度信息
        DeliverConsign deliverConsignByCreditData = null;
        try {
            if (StringUtils.isNotBlank(order.getCrmCode())) {
                deliverConsignByCreditData = queryCreditData(order);
            }

        } catch (Exception e) {
            throw new Exception(e);
        }

        if (deliverConsignByCreditData != null) {
            BigDecimal lineOfCredit = deliverConsignByCreditData.getLineOfCredit() == null ? BigDecimal.valueOf(0) : deliverConsignByCreditData.getLineOfCredit(); //授信额度
            if (lineOfCredit.compareTo(BigDecimal.valueOf(0)) == 1) {   // 判断是否有授信额度
                BigDecimal subtract1 = advanceMoney.subtract(thisShipmentsMoney); //预收  减去  本次发货金额
                if (subtract1.compareTo(BigDecimal.valueOf(0)) == -1) {   //先判断是否有预收，预收够不够本次发货的
                    //判断授信额度够不够
                    BigDecimal subtract = thisShipmentsMoney.subtract(advanceMoney);    // 本次发货金额  -  预收金额  = 需要使用授信的额度
                    BigDecimal multiply = subtract.multiply(exchangeRate);  //需要使用授信的额度 * 汇率
                    if (multiply.compareTo(BigDecimal.valueOf(0)) == 1) {  //本批次发货金额 大于 预收金额时，调用授信接口，修改授信额度
                        BigDecimal creditAvailable1 = deliverConsignByCreditData.getCreditAvailable().divide(exchangeRate, 2, BigDecimal.ROUND_HALF_DOWN);// 可用授信额度
                        BigDecimal lineOfCredit1 = deliverConsignByCreditData.getLineOfCredit().divide(exchangeRate, 2, BigDecimal.ROUND_HALF_DOWN);    //授信额度
                        BigDecimal subtract2 = lineOfCredit1.subtract(creditAvailable1);   // 所欠授信额度
                        BigDecimal subtract3 = multiply; //可以返还的授信额度
                        if(subtract2.subtract(multiply).compareTo(BigDecimal.valueOf(0)) == -1){ // 所欠授信额度小于可以返还的授信额度
                            subtract3 = subtract2; // 返还所欠授信额度
                        }
                        try {
                            JSONObject jsonObject = buyerCreditPaymentByOrder(order, 2, subtract3);
                            JSONObject data = jsonObject.getJSONObject("data");//获取查询数据
                            if (data == null) {  //查询数据正确返回 1
                                throw new Exception("同步授信额度失败");
                            }
                        } catch (Exception e) {
                            throw new Exception(e);
                        }
                    } else {
                        throw new Exception("预收金额和可用授信额度不足");
                    }
                }
            }
        }
    }


    @Override
    public void updateAuditUser(Long deliverConsignId, Long userId, String userName, String actId) {
        DeliverConsign deliverConsign = deliverConsignMapper.selectByPrimaryKey(deliverConsignId.intValue());
        // 获取原来的审核进度和相应审核人
        String auditingProcess = deliverConsign.getAuditingProcess();
        String auditingUserId = deliverConsign.getAuditingUserId();
        String auditingUser = deliverConsign.getAuditingUser();
        if (StringUtils.isBlank(auditingProcess)) {
            return;
        }
        // 处理审核人到审核进度的相应索引上
        String[] split = auditingProcess.split(",");
        String[] userIds ;
        String[] userNames ;
        if (StringUtils.isNotBlank(auditingUserId)) {
            userIds = auditingUserId.split(",");
            userNames = auditingUser.split(",");
        } else {
            userIds = new String[split.length];
            userNames = new String[split.length];
        }
        for (int i=0; i< split.length; ++i) {
            if (StringUtils.equals(split[i], actId) && userIds.length > i) {
                userIds[i] = String.valueOf(userId);
                if (userNames.length > i) {
                    userNames[i] = userName;
                }
                break;
            }
        }

        // 更新
        DeliverConsign selectiveDeliverConsign = new DeliverConsign();
        selectiveDeliverConsign.setId(deliverConsign.getId());
        selectiveDeliverConsign.setAuditingUserId(StringUtils.join(userIds));
        selectiveDeliverConsign.setAuditingUser(StringUtils.join(userNames));
        deliverConsignMapper.updateByPrimaryKeySelective(selectiveDeliverConsign);
    }

    /**
     * 根据订单中crm编码，查询授信信息
     *
     * @param order
     * @return
     * @throws Exception
     */
    public DeliverConsign queryCreditData(Order order) throws Exception {
        //拿取局部返回信息
        String returnMassage;
        //获取当前订单用户crm客户码
        String crmCode = order.getCrmCode();
        if (crmCode != null && crmCode != "") {
            try {
                //拼接查询授信路径
                String url = creditExtension + "V2/Buyercredit/getCreditInfoByCrmCode";
                //获取token
                String eruiToken = (String) ThreadLocalUtil.getObject();

                // 根据id获取人员信息
                String jsonParam = "{\"crm_code\":\"" + crmCode + "\"}";
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                returnMassage = HttpRequest.sendPost(url, jsonParam, header);
            } catch (Exception ex) {
                throw new Exception(String.format("获取客户授信信息失败"));
            }

            JSONObject jsonObject = JSONObject.parseObject(returnMassage);
            Integer code = jsonObject.getInteger("code");   //获取查询状态
           /* if(code != 1  &&  code != -401 ){  //查询数据正确返回 1
                String message = jsonObject.getString("message");
                throw new Exception(message);
            }*/
            if (code == 1) {
                JSONObject data = jsonObject.getJSONObject("data");//获取查询数据

                BigDecimal nolcGranted = BigDecimal.valueOf(0);
                BigDecimal lcgranted = BigDecimal.valueOf(0);
                String accountSettle = null;
                BigDecimal creditAvailable = null;
                if (data != null) {
                    nolcGranted = data.getBigDecimal("nolc_granted") == null ? BigDecimal.valueOf(0) : data.getBigDecimal("nolc_granted"); //非信用证授信额度
                    lcgranted = data.getBigDecimal("lc_granted") == null ? BigDecimal.valueOf(0) : data.getBigDecimal("lc_granted"); // 信用证授信额度
                    accountSettle = data.getString("account_settle"); // OA",(OA非信用证;L/C信用证)
                    creditAvailable = data.getBigDecimal("credit_available"); // 可用授信额度
                }

                //收款方式：
                //L/C:信用证，授信使用信用证
                //OA:托收，电汇，信汇，票汇，授信使用非信用证
                String paymentModeBn = order.getPaymentModeBn();    //获取订单收款方式
                String accountSettles = null;   //收款方式属于什么授信类型

                if (paymentModeBn != null) {
                    if (paymentModeBn.equals("1")) { //  1:信用证          //['1' => '信用证','2' => '托收','3'=>"电汇",'4'=>"信汇",'5'=>"票汇"];
                        accountSettles = "L/C";
                    } else if (paymentModeBn.equals("2") || paymentModeBn.equals("3") || paymentModeBn.equals("4") || paymentModeBn.equals("5")) {
                        accountSettles = "OA";
                    }
                }

                DeliverConsign deliverConsign = new DeliverConsign();

                if (accountSettle != null && accountSettles != null) {
                    if (accountSettle.equals(accountSettles) && accountSettle.equals("L/C")) {    //信用证
                        deliverConsign.setLineOfCredit(lcgranted);   //信用证授信额度
                        deliverConsign.setCreditAvailable(creditAvailable);    // 可用授信额度

                    } else if (accountSettle.equals(accountSettles) && accountSettle.equals("OA")) {  //非信用证
                        deliverConsign.setLineOfCredit(nolcGranted);   //非信用证授信额度
                        deliverConsign.setCreditAvailable(creditAvailable);    // 可用授信额度
                    } else {
                        deliverConsign.setLineOfCredit(BigDecimal.valueOf(0));   //授信额度
                        deliverConsign.setCreditAvailable(BigDecimal.valueOf(0));    // 可用授信额度
                    }
                } else {
                    deliverConsign.setLineOfCredit(BigDecimal.valueOf(0));   //授信额度
                    deliverConsign.setCreditAvailable(BigDecimal.valueOf(0));    // 可用授信额度
                }

                return deliverConsign;
            } else {
                DeliverConsign deliverConsign = new DeliverConsign();

                deliverConsign.setLineOfCredit(BigDecimal.valueOf(0));   //授信额度
                deliverConsign.setCreditAvailable(BigDecimal.valueOf(0));    // 可用授信额度

                return deliverConsign;
            }
        } else {
            DeliverConsign deliverConsign = new DeliverConsign();

            deliverConsign.setLineOfCredit(BigDecimal.valueOf(0));   //授信额度
            deliverConsign.setCreditAvailable(BigDecimal.valueOf(0));    // 可用授信额度

            return deliverConsign;
        }

    }

    /**
     * 处理授信额度
     *
     * @param order      订单信息
     * @param flag       支出还是回款标识   1：支出   2：回款
     * @param orderMoney 支出OR回款金额
     */
    public JSONObject buyerCreditPaymentByOrder(Order order, Integer flag, BigDecimal orderMoney) throws Exception {
        String contractNo = order.getContractNo();  //销售合同号
        String crmCode = order.getCrmCode();    //crm编码

        //拿取局部返回信息
        String returnMassage;
        try {
            //拼接查询授信路径
            String url = creditExtension + "V2/Buyercredit/buyerCreditPaymentByOrder";
            //获取token
            String eruiToken = (String) ThreadLocalUtil.getObject();

            // 根据id获取人员信息
            String jsonParam = "{\"contract_no\":\"" + contractNo + "\",\"order_money\":\"" + orderMoney + "\",\"order_type\":\"" + flag + "\",\"crm_code\":\"" + crmCode + "\"}";
            Map<String, String> header = new HashMap<>();
            header.put(CookiesUtil.TOKEN_NAME, eruiToken);
            header.put("Content-Type", "application/json");
            header.put("accept", "*/*");
            returnMassage = HttpRequest.sendPost(url, jsonParam, header);

            JSONObject jsonObject = JSONObject.parseObject(returnMassage);
            Integer code = jsonObject.getInteger("code");   //获取查询状态
            if (code != 1) {  //查询数据正确返回 1
                String message = jsonObject.getString("message");
                throw new Exception(message);
            }
            if (code == 1) {
                JSONObject data = jsonObject.getJSONObject("data");//获取查询数据
                return data;
            }
        } catch (Exception ex) {
            throw new Exception(String.format("查询授信信息失败"));
        }
        return null;
    }


}
