package com.erui.order.service.impl;

import com.erui.order.dao.AreaDao;
import com.erui.order.dao.DeliverConsignDao;
import com.erui.order.dao.GoodsDao;
import com.erui.order.dao.OrderDao;
import com.erui.order.entity.*;
import com.erui.order.service.AreaService;
import com.erui.order.service.DeliverConsignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class DeliverConsignServiceImpl implements DeliverConsignService {

    @Autowired
    private DeliverConsignDao deliverConsignDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private GoodsDao goodsDao;
    @Transactional
    @Override
    public DeliverConsign findById(Integer id) {
        DeliverConsign deliverConsign = deliverConsignDao.findOne(id);
        if (deliverConsign != null) {
            deliverConsign.getDeliverConsignGoodsSet().size();
            deliverConsign.getAttachmentSet().size();
        }
        return deliverConsign;
    }

    @Transactional
    @Override
    public boolean updateDeliverConsign(DeliverConsign deliverConsign) {
        Order order = orderDao.findOne(deliverConsign.getOrderId());
        DeliverConsign deliverConsignUpdate = deliverConsignDao.findOne(deliverConsign.getId());
        deliverConsignUpdate.setOrderId(deliverConsign.getOrderId());
        deliverConsignUpdate.setDeptId(order.getExecCoId());
        deliverConsignUpdate.setCreateUserId(order.getAgentId());
        deliverConsignUpdate.setWriteDate(deliverConsign.getWriteDate());
        deliverConsignUpdate.setArrivalDate(deliverConsign.getArrivalDate());
        deliverConsignUpdate.setBookingDate(deliverConsign.getBookingDate());
        deliverConsignUpdate.setCreateUserId(deliverConsign.getCreateUserId());
        deliverConsignUpdate.setRemarks(deliverConsign.getRemarks());
        deliverConsignUpdate.setStatus(deliverConsign.getStatus());
        deliverConsignUpdate.setDeliverConsignGoodsSet(deliverConsign.getDeliverConsignGoodsSet());
        deliverConsignUpdate.setAttachmentSet(deliverConsign.getAttachmentSet());
        Map<Integer, Goods> goodsList = order.getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        deliverConsign.getDeliverConsignGoodsSet().parallelStream().forEach(dcGoods -> {
            Integer gid = dcGoods.getgId();
            Goods goods = goodsList.get(gid);
            //商品需增加发货数量 = 要修改的数量-原发货数量
            Integer outStockNum = dcGoods.getSendNum() - goods.getOutstockNum();
            dcGoods.setGoods(goods);
            dcGoods.setCreateTime(new Date());
            goods.setOutstockNum(outStockNum + goods.getOutstockNum());
        });
        goodsDao.save(goodsList.values());
        deliverConsignDao.saveAndFlush(deliverConsignUpdate);
        return true;
    }

    @Transactional
    @Override
    public boolean addDeliverConsign(DeliverConsign deliverConsign) {
        Order order = orderDao.findOne(deliverConsign.getOrderId());
        DeliverConsign deliverConsignAdd = new DeliverConsign();
        deliverConsignAdd.setOrderId(deliverConsign.getOrderId());
        deliverConsignAdd.setDeptId(order.getExecCoId());
        deliverConsignAdd.setCreateUserId(order.getAgentId());
        deliverConsignAdd.setWriteDate(deliverConsign.getWriteDate());
        deliverConsignAdd.setArrivalDate(deliverConsign.getArrivalDate());
        deliverConsignAdd.setBookingDate(deliverConsign.getBookingDate());
        deliverConsignAdd.setCreateUserId(deliverConsign.getCreateUserId());
        deliverConsignAdd.setRemarks(deliverConsign.getRemarks());
        deliverConsignAdd.setStatus(deliverConsign.getStatus());
        deliverConsignAdd.setDeliverConsignGoodsSet(deliverConsign.getDeliverConsignGoodsSet());
        deliverConsignAdd.setAttachmentSet(deliverConsign.getAttachmentSet());
        Map<Integer, Goods> goodsList = order.getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        deliverConsign.getDeliverConsignGoodsSet().parallelStream().forEach(dcGoods -> {
            Integer gid = dcGoods.getgId();
            Goods goods = goodsList.get(gid);
            dcGoods.setGoods(goods);
            dcGoods.setCreateTime(new Date());
            goods.setOutstockNum(goods.getOutstockNum() + dcGoods.getSendNum());
        });
        goodsDao.save(goodsList.values());
        deliverConsignDao.save(deliverConsignAdd);
        return true;
    }

    @Override
    public List<DeliverConsign> findByOrderId(Integer orderId) {
        List<DeliverConsign> deliverConsignList = deliverConsignDao.findByOrderId(orderId);
        return deliverConsignList;
    }
}
