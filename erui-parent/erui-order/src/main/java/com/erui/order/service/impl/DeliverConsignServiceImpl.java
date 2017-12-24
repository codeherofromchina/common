package com.erui.order.service.impl;

import com.erui.order.entity.DeliverConsign;

import com.erui.order.dao.DeliverConsignDao;
import com.erui.order.dao.GoodsDao;
import com.erui.order.dao.OrderDao;
import com.erui.order.entity.*;
import com.erui.order.service.DeliverConsignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
        Order order = orderDao.findOne(deliverConsign.getoId());
        DeliverConsign deliverConsignUpdate = deliverConsignDao.findOne(deliverConsign.getId());
        deliverConsignUpdate.setoId(deliverConsign.getoId());
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
        Order order = orderDao.findOne(deliverConsign.getoId());
        DeliverConsign deliverConsignAdd = new DeliverConsign();
        deliverConsignAdd.setoId(deliverConsign.getoId());
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


    /**
     * 根据出口发货通知单 查询信息
     *
     * @param deliverNoticeNos 看货通知单号  数组
     * @return
     */
    @Override
    @Transactional
    public List<DeliverConsign> querExitInformMessage(Integer[] deliverNoticeNos) {
        List<DeliverConsign> page = deliverConsignDao.findByIdInAndStatus(deliverNoticeNos, 3);

        for (DeliverConsign deliverConsign : page){
            deliverConsign.getOrder().getId();
            deliverConsign.getAttachmentSet().size();
            Set<DeliverConsignGoods> deliverConsignGoodsSet = deliverConsign.getDeliverConsignGoodsSet();
            for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsSet){
                deliverConsignGoods.getGoods().getId();
            }

        }

        return page;
    }



    /**
     * 看货通知管理   查询出口发货通知单
     * @return
     */
    @Override
    public List<DeliverConsign> queryExitAdvice() {
        List<DeliverConsign> lsit=deliverConsignDao.findByStatusAndDeliverYn(3,1);
        return lsit;
    }


}
