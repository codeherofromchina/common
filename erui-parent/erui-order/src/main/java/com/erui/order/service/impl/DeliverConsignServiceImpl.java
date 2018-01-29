package com.erui.order.service.impl;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.DeliverConsignDao;
import com.erui.order.dao.DeliverNoticeDao;
import com.erui.order.dao.GoodsDao;
import com.erui.order.dao.OrderDao;
import com.erui.order.entity.*;
import com.erui.order.service.DeliverConsignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private DeliverNoticeDao deliverNoticeDao;

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
    public boolean updateDeliverConsign(DeliverConsign deliverConsign) {
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
        deliverConsignUpdate.setDeliverConsignGoodsSet(deliverConsign.getDeliverConsignGoodsSet());
        deliverConsignUpdate.setAttachmentSet(deliverConsign.getAttachmentSet());
        Map<Integer, Goods> goodsList = order.getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        deliverConsign.getDeliverConsignGoodsSet().parallelStream().forEach(dcGoods -> {
            Integer gid = dcGoods.getgId();
            Goods goods = goodsList.get(gid);
            //商品需增加发货数量 = 要修改的数量-原发货数量
            //Integer outStockNum = dcGoods.getSendNum() - goods.getOutstockNum();
            if (goods.getOutstockNum() < goods.getContractGoodsNum()) {
                dcGoods.setGoods(goods);
                dcGoods.setCreateTime(new Date());
                if (deliverConsign.getStatus() == 3){
                    goods.setOutstockNum(goods.getOutstockNum() + dcGoods.getSendNum());
                }
            }
        });
        if (deliverConsign.getStatus() == 3) {
            goodsDao.save(goodsList.values());
        }
        deliverConsignDao.saveAndFlush(deliverConsignUpdate);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addDeliverConsign(DeliverConsign deliverConsign) {
        Order order = orderDao.findOne(deliverConsign.getoId());
        DeliverConsign deliverConsignAdd = new DeliverConsign();
        String deliverConsignNo = deliverConsignDao.findDeliverConsignNo();
        String deliverConsignNostring = deliverConsignNo.substring(deliverConsignNo.length() - 4, deliverConsignNo.length());
        deliverConsignAdd.setDeliverConsignNo("CKFH" + DateUtil.format("yyyyMM", new Date()) + StringUtil.SerialNumber(4, 1, deliverConsignNostring));
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
        deliverConsignAdd.setAttachmentSet(deliverConsign.getAttachmentSet());
        Map<Integer, Goods> goodsList = order.getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        deliverConsign.getDeliverConsignGoodsSet().parallelStream().forEach(dcGoods -> {
            Integer gid = dcGoods.getgId();
            Goods goods = goodsList.get(gid);
            if (goods.getOutstockNum() < goods.getContractGoodsNum()) {
                dcGoods.setGoods(goods);
                dcGoods.setCreateTime(new Date());
                if (deliverConsign.getStatus() == 3) {
                    goods.setOutstockNum(goods.getOutstockNum() + dcGoods.getSendNum());
                }
            }
        });
        if (deliverConsign.getStatus() == 3) {
            goodsDao.save(goodsList.values());
        }
        deliverConsignDao.save(deliverConsignAdd);
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
           /* DeliverConsign deliverConsign2 = new DeliverConsign();
            deliverConsign2.setId(deliverConsign.getoId());
            deliverConsign2.setDeliverConsignNo(deliverConsign.getDeliverConsignNo());
            deliverConsign2.setDeptId(deliverConsign.getDeptId());
            deliverConsign2.setWriteDate(deliverConsign.getWriteDate());
            deliverConsign2.setStatus(deliverConsign.getStatus());
            deliverConsign2.setCoId(deliverConsign.getCoId());
            deliverConsign2.setCreateUserId(deliverConsign.getCreateUserId());*/

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
    public List<DeliverConsign> queryExitAdvice(DeliverNotice deliverNotice) {

        if (deliverNotice.getId() == null) {
            if (StringUtil.isNotBlank(deliverNotice.getCountry())){
                List<DeliverConsign> lsit = deliverConsignDao.findByStatusAndDeliverYnAndCountryAndDeliverConsignNo(3, 1,deliverNotice.getCountry(),deliverNotice.getDeliverConsignNo());
                return lsit;
            }else{
                List<DeliverConsign> lsit = deliverConsignDao.findByStatusAndDeliverYnAndDeliverConsignNo(3, 1,deliverNotice.getDeliverConsignNo());
                return lsit;
            }
        } else {
            if (StringUtil.isNotBlank(deliverNotice.getCountry())){
                List<DeliverConsign> lsit = deliverConsignDao.findByStatusAndDeliverYnAndCountryAndDeliverConsignNo(3, 1,deliverNotice.getCountry(),deliverNotice.getDeliverConsignNo()); //获取未选择
                DeliverNotice one = deliverNoticeDao.findOne(deliverNotice.getId());
                List<DeliverConsign> deliverConsigns = one.getDeliverConsigns();//查询已选择
                Integer[] arr = new Integer[deliverConsigns.size()];    //获取id
                int i = 0;
                for (DeliverConsign deliverConsign : deliverConsigns) {
                    arr[i] = (deliverConsign.getId());
                    i++;
                }
                List<DeliverConsign> lists = deliverConsignDao.findByIdIn(arr);
                for (DeliverConsign deliverConsign : lists) {
                    lsit.add(deliverConsign);
                }
                return lsit;
            }else{
                List<DeliverConsign> lsit = deliverConsignDao.findByStatusAndDeliverYnAndDeliverConsignNo(3, 1,deliverNotice.getDeliverConsignNo());  //获取未选择
                DeliverNotice one = deliverNoticeDao.findOne(deliverNotice.getId());
                List<DeliverConsign> deliverConsigns = one.getDeliverConsigns();//查询已选择
                Integer[] arr = new Integer[deliverConsigns.size()];    //获取id
                int i = 0;
                for (DeliverConsign deliverConsign : deliverConsigns) {
                    arr[i] = (deliverConsign.getId());
                    i++;
                }
                List<DeliverConsign> lists = deliverConsignDao.findByIdIn(arr);
                for (DeliverConsign deliverConsign : lists) {
                    lsit.add(deliverConsign);
                }
                return lsit;
            }

        }

    }

}
