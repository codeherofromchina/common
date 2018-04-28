package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.EruitokenUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.service.AreaService;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.PurchRequisitionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
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
    OrderDao orderDao;
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
            Order order = project1.getOrder();
            order.setProjectNo(purchRequisition1.getProjectNo());
            orderDao.save(order);
            projectDao.save(project1);

/*
            try {
                //TODO 采购申请通知：采购申请单下达后通知采购经办人
              sendSms(project1);
            } catch (Exception e) {
                e.printStackTrace();
            }*/

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
            project1.setPurchReqCreate(Project.PurchReqCreateEnum.SUBMITED.getCode());
            project1.setProjectNo(purchRequisition1.getProjectNo());
            Order order = project1.getOrder();
            order.setProjectNo(purchRequisition1.getProjectNo());
            orderDao.save(order);
            projectDao.save(project1);
         /*   try {
                // TODO 采购申请通知：采购申请单下达后通知采购经办人
                sendSms(project1);
            } catch (Exception e) {
                e.printStackTrace();
            }
*/
        }
        return true;
    }


    //采购申请通知：采购申请单下达后通知采购经办人
    public void sendSms(Project Project1) throws Exception {

        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (StringUtils.isNotBlank(eruiToken)) {
            try {
                // 根据id获取采购经办人信息
                String jsonParam = "{\"id\":\"" + Project1.getPurchaseUid() + "\"}";
                Map<String, String> header = new HashMap<>();
                header.put(EruitokenUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                String s = HttpRequest.sendPost(memberInformation, jsonParam, header);
                logger.info("人员详情返回信息：" + s);

                // 获取商务经办人手机号
                JSONObject jsonObject = JSONObject.parseObject(s);
                Integer code = jsonObject.getInteger("code");
                String mobile = null;  //采购经办人手机号
                if (code == 1) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    mobile = data.getString("mobile");
                    //发送短信
                    Map<String, String> map = new HashMap();
                    map.put("areaCode", "86");
                    map.put("to", "[\"" + mobile + "\"]");
                    map.put("content", "您好，项目号：" + Project1.getProjectNo() + "，商务技术经办人：" + Project1.getBusinessName() + "，已申请采购，请及时处理。感谢您对我们的支持与信任！");
                    map.put("subType", "0");
                    map.put("groupSending", "0");
                    map.put("useType", "订单");
                    String s1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                    logger.info("发送短信返回状态：" + s1);
                }

            } catch (Exception e) {
                throw new Exception(String.format("%s%s%s","发送短信失败", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL,"Failure to send SMS"));
            }

        }
    }


    /**
     * @param condition { 销售合同号：contractNo,项目号：projectNo,项目名称：projectName,项目开始日期：startDate,下发采购日期：submitDate,
     *                  要求采购到货日期：requirePurchaseDate,商务技术经办人：businessName,页码：page,页大小：rows}
     * @return {
     * contractNo:销售合同号,projectNo:项目号,projectName:项目名称,
     * businessName:商务技术经办人,startDate:项目开始日期,
     * submitDate:下发采购日期,requirePurchaseDate:要求采购到货日期,status:状态
     * }
     */
    @Transactional(readOnly = true)
    @Override
    public Page<Map<String, Object>> listByPage(Map<String, String> condition) {
        String pageStr = condition.get("page");
        String rowsStr = condition.get("rows");
        int page = 1;
        int rows = 50;
        if (StringUtils.isNumeric(pageStr)) {
            if ((page = Integer.parseInt(pageStr)) < 1) {
                page = 1;
            }
        }
        if (StringUtils.isNumeric(rowsStr)) {
            if ((rows = Integer.parseInt(rowsStr)) < 1) {
                rows = 50;
            }
        }
        Pageable pageRequest = new PageRequest(page - 1, rows, Sort.Direction.DESC,"id");
        Page<PurchRequisition> pageList = purchRequisitionDao.findAll(new Specification<PurchRequisition>() {
            @Override
            public Predicate toPredicate(Root<PurchRequisition> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                Join<PurchRequisition, Project> projectPath = root.join("project");
                // 提交了
                list.add(cb.equal(root.get("status").as(Integer.class), PurchRequisition.StatusEnum.SUBMITED.getCode()));
                list.add(cb.equal(projectPath.get("purchDone").as(Boolean.class), Boolean.FALSE));
                // 销售合同号查询
                String contractNo = condition.get("contractNo");
                if (StringUtils.isNotBlank(contractNo)) {
                    list.add(cb.like(projectPath.get("contractNo").as(String.class), "%" + contractNo + "%"));
                }
                // 项目号查询
                String projectNo = condition.get("projectNo");
                if (StringUtils.isNotBlank(projectNo)) {
                    list.add(cb.like(projectPath.get("projectNo").as(String.class), "%" + projectNo + "%"));
                }
                // 项目名称查询
                String projectName = condition.get("projectName");
                if (StringUtils.isNotBlank(projectName)) {
                    list.add(cb.like(projectPath.get("projectName").as(String.class), "%" + projectName + "%"));
                }
                Date date = null;
                // 项目开始日期查询
                String startDate = condition.get("startDate");
                if (StringUtils.isNotBlank(startDate) &&
                        (date = DateUtil.parseString2DateNoException(startDate, DateUtil.SHORT_FORMAT_STR)) != null) {
                    list.add(cb.equal(projectPath.get("startDate").as(Date.class), date));
                }
                // 下发采购日期查询
                String submitDate = condition.get("submitDate");
                if (StringUtils.isNotBlank(submitDate) &&
                        (date = DateUtil.parseString2DateNoException(submitDate, DateUtil.SHORT_FORMAT_STR)) != null) {
                    list.add(cb.equal(root.get("submitDate").as(Date.class), date));
                }
                // 要求采购到货日期查询
                String requirePurchaseDate = condition.get("requirePurchaseDate");
                if (StringUtils.isNotBlank(requirePurchaseDate) &&
                        (date = DateUtil.parseString2DateNoException(requirePurchaseDate, DateUtil.SHORT_FORMAT_STR)) != null) {
                    list.add(cb.equal(projectPath.get("requirePurchaseDate").as(Date.class), date));
                }
                // 商务技术经办人查询
                String businessName = condition.get("businessName");
                if (StringUtils.isNotBlank(businessName)) {
                    list.add(cb.like(projectPath.get("businessName").as(String.class), "%" + businessName + "%"));
                }
                // 采购经办人过滤
                String purchaseUid = condition.get("purchaseUid");
                if (StringUtils.isNotBlank(purchaseUid)) {
                    list.add(cb.equal(projectPath.get("purchaseUid"), purchaseUid));
                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, pageRequest);

        List<Map<String, Object>> dataList = new ArrayList<>();
        for (PurchRequisition pr : pageList) {
            Project project = pr.getProject();
            Map<String, Object> map = new HashMap<>();
            map.put("projectId", project.getId()); // 项目ID
            map.put("projectName", project.getProjectName()); // 项目名称
            map.put("projectNo", project.getProjectNo()); // 项目号
            map.put("contractNo", project.getContractNo()); // 销售合同号
            map.put("businessName", project.getBusinessName()); // 商务技术经办人
            map.put("startDate", project.getStartDate()); // 项目开始日期
            map.put("submitDate", pr.getSubmitDate()); // 下发采购日期
            map.put("requirePurchaseDate", project.getRequirePurchaseDate()); // 要求采购到货日期
            map.put("id", pr.getId()); // 采购申请ID
            map.put("status", pr.getStatus()); // 采购申请状态 1:未编辑 2:待确认/已保存 3:已提交
            dataList.add(map);
        }

        Page<Map<String, Object>> result = new PageImpl<Map<String, Object>>(dataList, pageRequest, pageList.getTotalElements());
        return result;
    }
}
