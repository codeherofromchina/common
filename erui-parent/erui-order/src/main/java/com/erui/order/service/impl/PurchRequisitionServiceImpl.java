package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.EruitokenUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.AreaDao;
import com.erui.order.dao.GoodsDao;
import com.erui.order.dao.ProjectDao;
import com.erui.order.dao.PurchRequisitionDao;
import com.erui.order.entity.*;
import com.erui.order.service.AreaService;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.PurchRequisitionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class PurchRequisitionServiceImpl implements PurchRequisitionService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Autowired
    private PurchRequisitionDao purchRequisitionDao;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private AttachmentService attachmentService;


    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口

    @Value("#{orderProp[SEND_SMS]}")
    private String sendSms;  //发短信接口


    @Transactional(readOnly = true)
    @Override
    public PurchRequisition findById(Integer id, Integer orderId) {
        PurchRequisition purchRequisition = purchRequisitionDao.findByIdOrOrderId(id, orderId);
        if (purchRequisition != null) {
            purchRequisition.setProId(purchRequisition.getProject().getId());
            purchRequisition.getGoodsList().size();
            purchRequisition.getAttachmentSet().size();
            return purchRequisition;
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updatePurchRequisition(PurchRequisition purchRequisition) {
        Project project = projectDao.findOne(purchRequisition.getProId());
        if (project != null) {
            project.getOrder().getGoodsList().size();
        }
        PurchRequisition purchRequisitionUpdate = purchRequisitionDao.findOne(purchRequisition.getId());
        purchRequisitionUpdate.setProject(project);
        purchRequisitionUpdate.setProjectNo(purchRequisition.getProjectNo());
        purchRequisitionUpdate.setSubmitDate(purchRequisition.getSubmitDate());
        purchRequisitionUpdate.setTradeMethod(purchRequisition.getTradeMethod());
        purchRequisitionUpdate.setDeliveryPlace(purchRequisition.getDeliveryPlace());
        purchRequisitionUpdate.setFactorySend(purchRequisition.isFactorySend());
        purchRequisitionUpdate.setRequirements(purchRequisition.getRequirements());
        purchRequisitionUpdate.setRemarks(purchRequisition.getRemarks());
        purchRequisitionUpdate.setAttachmentSet(purchRequisition.getAttachmentSet());
        ArrayList<Goods> list = new ArrayList<>();
        Map<Integer, Goods> goodsMap = project.getOrder().getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        purchRequisition.getGoodsList().stream().forEach(dcGoods -> {
            Integer gid = dcGoods.getId();
            Goods goods = goodsMap.get(gid);
            goods.setProType(dcGoods.getProType());
            goods.setCheckMethod(dcGoods.getCheckMethod());
            goods.setCheckType(dcGoods.getCheckType());
            goods.setCertificate(dcGoods.getCertificate());
            goods.setRequirePurchaseDate(dcGoods.getRequirePurchaseDate());
            goods.setTechAudit(dcGoods.getTechAudit());
            goods.setTechRequire(dcGoods.getTechRequire());
            goods.setProjectNo(purchRequisitionUpdate.getProjectNo());
            goodsDao.save(goods);
            list.add(goods);
        });
        purchRequisitionUpdate.setGoodsList(list);
        purchRequisitionUpdate.setStatus(purchRequisition.getStatus());
        PurchRequisition purchRequisition1 = purchRequisitionDao.save(purchRequisitionUpdate);
        if (purchRequisition1.getStatus() == PurchRequisition.StatusEnum.SUBMITED.getCode()) {
            Project project1 = purchRequisition1.getProject();
            project1.setPurchReqCreate(Project.PurchReqCreateEnum.SUBMITED.getCode());
            project1.setProjectNo(purchRequisition1.getProjectNo());
            projectDao.save(project1);

            try {
                //采购申请通知：采购申请单下达后通知采购经办人
                sendSms(project1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertPurchRequisition(PurchRequisition purchRequisition) {
        Project project = projectDao.findOne(purchRequisition.getProId());
        if (project != null) {
            project.getOrder().getGoodsList().size();
        }
        PurchRequisition purchRequisitionAdd = new PurchRequisition();
        purchRequisitionAdd.setContractNo(project.getContractNo());
        purchRequisitionAdd.setOrderId(project.getOrder().getId());
        purchRequisitionAdd.setProject(project);
        purchRequisitionAdd.setDepartment(project.getSendDeptId());
        if (project.getManagerUid() != null) {
            purchRequisitionAdd.setPmUid(project.getManagerUid());
        } else {
            purchRequisitionAdd.setPmUid(project.getBusinessUid());
        }
        purchRequisitionAdd.setProjectNo(purchRequisition.getProjectNo());
        purchRequisitionAdd.setSubmitDate(purchRequisition.getSubmitDate());
        purchRequisitionAdd.setTradeMethod(purchRequisition.getTradeMethod());
        purchRequisitionAdd.setTransModeBn(project.getOrder().getTradeTerms());
        purchRequisitionAdd.setDeliveryPlace(purchRequisition.getDeliveryPlace());
        purchRequisitionAdd.setFactorySend(purchRequisition.isFactorySend());
        purchRequisitionAdd.setRequirements(purchRequisition.getRequirements());
        purchRequisitionAdd.setRemarks(purchRequisition.getRemarks());
        // 处理附件信息
//        Set<Attachment> attachments = attachmentService.handleParamAttachment(null, purchRequisition.getAttachmentSet(), null, null);
        purchRequisitionAdd.setAttachmentSet(purchRequisition.getAttachmentSet());
        ArrayList<Goods> list = new ArrayList<>();
        Map<Integer, Goods> goodsMap = project.getOrder().getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        purchRequisition.getGoodsList().stream().forEach(dcGoods -> {
            Integer gid = dcGoods.getId();
            Goods goods = goodsMap.get(gid);
            goods.setProType(dcGoods.getProType());
            goods.setCheckMethod(dcGoods.getCheckMethod());
            goods.setCheckType(dcGoods.getCheckType());
            goods.setCertificate(dcGoods.getCertificate());
            goods.setRequirePurchaseDate(dcGoods.getRequirePurchaseDate());
            goods.setTechAudit(dcGoods.getTechAudit());
            goods.setTechRequire(dcGoods.getTechRequire());
            goods.setProjectNo(purchRequisitionAdd.getProjectNo());
            goodsDao.save(goods);
            list.add(goods);
        });
        purchRequisitionAdd.setGoodsList(list);
        purchRequisitionAdd.setStatus(purchRequisition.getStatus());
        PurchRequisition purchRequisition1 = purchRequisitionDao.save(purchRequisitionAdd);
        if (purchRequisition1.getStatus() == PurchRequisition.StatusEnum.SUBMITED.getCode()) {
            Project project1 = purchRequisition1.getProject();
            project1.setProjectNo(purchRequisition1.getProjectNo());
            project1.setPurchReqCreate(Project.PurchReqCreateEnum.SUBMITED.getCode());
            projectDao.save(project1);

            try {
                //采购申请通知：采购申请单下达后通知采购经办人
                sendSms(project1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return true;
    }


    //采购申请通知：采购申请单下达后通知采购经办人
    public void sendSms(Project Project1) throws  Exception {

        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (StringUtils.isNotBlank(eruiToken)) {
            try{
                // 根据id获取采购经办人信息
                String jsonParam = "{\"id\":\"" + Project1.getPurchaseUid() + "\"}";
                Map<String, String> header = new HashMap<>();
                header.put(EruitokenUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                String s = HttpRequest.sendPost(memberInformation, jsonParam, header);
                logger.info("CRM返回信息：" + s);

                // 获取商务经办人手机号
                JSONObject jsonObject = JSONObject.parseObject(s);
                Integer code = jsonObject.getInteger("code");
                String mobile = null;  //采购经办人手机号
                if(code == 1){
                    JSONObject data = jsonObject.getJSONObject("data");
                    mobile = data.getString("mobile");
                    //发送短信
                    Map<String,String> map= new HashMap();
                    map.put("areaCode","86");
                    map.put("to","[\""+mobile+"\"]");
                    map.put("content","您好，项目号："+Project1.getProjectNo()+"，商务技术经办人:"+Project1.getBusinessName()+"，已申请采购,请及时处理。感谢您对我们的支持与信任！");
                    map.put("subType","0");
                    map.put("groupSending","0");
                    map.put("useType","订单");
                    String s1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                    logger.info("发送手机号失败"+s1);
                }

            }catch (Exception e){
                throw new Exception("发送短信失败");
            }

        }
    }

}
