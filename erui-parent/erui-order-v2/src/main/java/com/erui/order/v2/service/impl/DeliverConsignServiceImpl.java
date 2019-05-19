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
import com.erui.order.v2.model.*;
import com.erui.order.v2.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String audiRemark = deliverConsign.getAudiRemark();
        if (StringUtils.isNotBlank(auditingProcess2)) {
            if (auditingProcess2.equals(auditingProcess)) {
                auditingProcess2 = "";
            } else {
                auditingProcess2 = auditingProcess2.replace(auditingProcess, "");
                while (auditingProcess2.indexOf(",,") != -1) {
                    auditingProcess2 = auditingProcess2.replace(",,", ",");
                }
                auditingProcess2 = StringUtils.strip(auditingProcess2, ",");
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
        if (StringUtils.isNotBlank(auditingProcess2)) {
            auditingProcess2 = auditingProcess2 + "," + auditingProcess;
        } else {
            auditingProcess2 = auditingProcess;
        }
        // 更新修正后的状态
        DeliverConsign deliverConsignSelective = new DeliverConsign();
        deliverConsignSelective.setId(deliverConsign.getId());
        deliverConsignSelective.setAuditingStatus(auditingStatus);
        deliverConsignSelective.setAuditingProcess(auditingProcess2);
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


}
