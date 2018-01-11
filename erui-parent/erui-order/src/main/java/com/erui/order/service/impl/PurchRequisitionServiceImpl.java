package com.erui.order.service.impl;

import com.erui.order.dao.AreaDao;
import com.erui.order.dao.GoodsDao;
import com.erui.order.dao.ProjectDao;
import com.erui.order.dao.PurchRequisitionDao;
import com.erui.order.entity.*;
import com.erui.order.service.AreaService;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.PurchRequisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class PurchRequisitionServiceImpl implements PurchRequisitionService {

    @Autowired
    private PurchRequisitionDao purchRequisitionDao;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private AttachmentService attachmentService;
    @Transactional
    @Override
    public PurchRequisition findById(Integer id,Integer orderId) {
        PurchRequisition purchRequisition = purchRequisitionDao.findByIdOrOrderId(id,orderId);
        if (purchRequisition != null) {
            purchRequisition.setProId(purchRequisition.getProject().getId());
            purchRequisition.getGoodsList().size();
            purchRequisition.getAttachmentSet().size();
            return purchRequisition;
        }
        return null;
    }

    @Transactional
    @Override
    public boolean updatePurchRequisition(PurchRequisition purchRequisition) {
        Project project = projectDao.findOne(purchRequisition.getProId());
        if (project != null) {
            project.getOrder().getGoodsList().size();
        }
        PurchRequisition purchRequisitionUpdate = purchRequisitionDao.findOne(purchRequisition.getId());
        purchRequisitionUpdate.setProject(project);
        purchRequisitionUpdate.setContractNo(purchRequisition.getContractNo());
       /* purchRequisitionUpdate.setDepartment(project.getSendDeptId());
        purchRequisitionUpdate.setPmUid(project.getManagerUid());*/
        purchRequisitionUpdate.setSubmitDate(purchRequisition.getSubmitDate());
        purchRequisitionUpdate.setTradeMethod(purchRequisition.getTradeMethod());
        purchRequisitionUpdate.setTransModeBn(project.getOrder().getTradeTerms());
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
            goodsDao.save(goods);
            list.add(goods);
        });
        purchRequisitionUpdate.setGoodsList(list);
        purchRequisitionUpdate.setStatus(purchRequisition.getStatus());
        purchRequisitionDao.save(purchRequisitionUpdate);
        return true;
    }

    @Transactional
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
        purchRequisitionAdd.setDepartment(project.getOrder().getTechnicalId());
        purchRequisitionAdd.setPmUid(project.getManagerUid());
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
            goodsDao.save(goods);
           list.add(goods);
        });
        purchRequisitionAdd.setGoodsList(list);
        purchRequisitionAdd.setStatus(purchRequisition.getStatus());
        purchRequisitionDao.save(purchRequisitionAdd);
        return true;
    }
}
