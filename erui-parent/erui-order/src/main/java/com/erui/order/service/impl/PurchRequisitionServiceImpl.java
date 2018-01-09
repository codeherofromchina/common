package com.erui.order.service.impl;

import com.erui.order.dao.AreaDao;
import com.erui.order.dao.GoodsDao;
import com.erui.order.dao.ProjectDao;
import com.erui.order.dao.PurchRequisitionDao;
import com.erui.order.entity.Area;
import com.erui.order.entity.Goods;
import com.erui.order.entity.Project;
import com.erui.order.entity.PurchRequisition;
import com.erui.order.service.AreaService;
import com.erui.order.service.PurchRequisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Transactional
    @Override
    public PurchRequisition findById(Integer id) {
        PurchRequisition purchRequisition = purchRequisitionDao.findOne(id);
        if (purchRequisition != null) {
            purchRequisition.getGoodsList().size();
            purchRequisition.getAttachmentSet().size();
            purchRequisition.setProject(null);
            purchRequisition.getGoodsList().iterator().next().setOrder(null);
        }
        return purchRequisition;
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
        purchRequisitionUpdate.setDepartment(project.getOrder().getTechnicalId());
        purchRequisitionUpdate.setPmUid(project.getManagerUid());
        purchRequisitionUpdate.setSubmitDate(purchRequisition.getSubmitDate());
        purchRequisitionUpdate.setTradeMethod(purchRequisition.getTradeMethod());
        purchRequisitionUpdate.setTransModeBn(project.getOrder().getTradeTerms());
        purchRequisitionUpdate.setDeliveryPlace(purchRequisition.getDeliveryPlace());
        purchRequisitionUpdate.setFactorySend(purchRequisition.isFactorySend());
        purchRequisitionUpdate.setRequirements(purchRequisition.getRequirements());
        purchRequisitionUpdate.setRemarks(purchRequisition.getRemarks());
        purchRequisitionUpdate.setAttachmentSet(purchRequisition.getAttachmentSet());
        purchRequisitionUpdate.setGoodsList(purchRequisition.getGoodsList());
        Map<Integer, Goods> goodList = project.getOrder().getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        purchRequisition.getGoodsList().parallelStream().forEach(dcGoods -> {
            Integer gid = dcGoods.getId();
            Goods goods = goodList.get(gid);
            goods.setCheckType(dcGoods.getCheckType());
            goods.setCheckMethod(dcGoods.getCheckMethod());
            goods.setCertificate(dcGoods.getCertificate());
            goods.setRequirePurchaseDate(dcGoods.getRequirePurchaseDate());
            goods.setTechAudit(dcGoods.getTechAudit());
            goods.setTechRequire(dcGoods.getTechRequire());
        });
        purchRequisitionUpdate.setStatus(purchRequisition.getStatus());
        goodsDao.flush();
        purchRequisitionDao.flush();
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
        purchRequisitionAdd.setContractNo(purchRequisition.getContractNo());
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
        purchRequisitionAdd.setAttachmentSet(purchRequisition.getAttachmentSet());
        //purchRequisitionAdd.setGoodsList(purchRequisition.getGoodsList());
        ArrayList<Goods> list = new ArrayList<>();
        Map<Integer, Goods> goodsMap = project.getOrder().getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        purchRequisition.getGoodsList().stream().forEach(dcGoods -> {
            Integer gid = dcGoods.getId();
            Goods goods = goodsMap.get(gid);
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
