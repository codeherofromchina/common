package com.erui.order.service.impl;

import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.DeliverConsignDao;
import com.erui.order.dao.DeliverNoticeDao;
import com.erui.order.dao.GoodsDao;
import com.erui.order.dao.OrderDao;
import com.erui.order.entity.*;
import com.erui.order.service.DeliverConsignService;
import com.erui.order.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    private OrderService orderService;
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
    public boolean updateDeliverConsign(DeliverConsign deliverConsign) throws Exception {
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

        deliverConsignUpdate.setAttachmentSet(deliverConsign.getAttachmentSet());
        Map<Integer, DeliverConsignGoods> oldDcGoodsMap = deliverConsignUpdate.getDeliverConsignGoodsSet().parallelStream().collect(Collectors.toMap(DeliverConsignGoods::getId, vo -> vo));
        Map<Integer, Goods> goodsList = order.getGoodsList().parallelStream().collect(Collectors.toMap(Goods::getId, vo -> vo));
        Set<Integer> orderIds = new HashSet<>();
        for (DeliverConsignGoods dcGoods : deliverConsign.getDeliverConsignGoodsSet()) {
            DeliverConsignGoods deliverConsignGoods = oldDcGoodsMap.remove(dcGoods.getId());
            int oldSendNum = 0;
            if (deliverConsignGoods == null) {
                dcGoods.setId(null);
            } else {
                oldSendNum = deliverConsignGoods.getSendNum();
            }
            Integer gid = dcGoods.getgId();
            Goods goods = goodsList.get(gid);
            //商品需增加发货数量 = 要修改的数量-原发货数量
            //Integer outStockNum = dcGoods.getSendNum() - goods.getOutstockNum();
            if (goods.getOutstockApplyNum() - oldSendNum + dcGoods.getSendNum() <= goods.getContractGoodsNum()) {
                dcGoods.setGoods(goods);
                dcGoods.setCreateTime(new Date());
                if (deliverConsign.getStatus() == 3) {
                    goods.setOutstockNum(goods.getOutstockNum() + dcGoods.getSendNum());
                    orderIds.add(goods.getOrder().getId());
                }
                goods.setOutstockApplyNum(goods.getOutstockApplyNum() - oldSendNum + dcGoods.getSendNum());
                goodsDao.save(goods);
            } else {
                throw new Exception("发货总数量超过合同数量");
            }
        }
        deliverConsignUpdate.setDeliverConsignGoodsSet(deliverConsign.getDeliverConsignGoodsSet());
        // 被删除的发货通知单商品
        for (DeliverConsignGoods dcGoods : oldDcGoodsMap.values()) {
            Goods goods = dcGoods.getGoods();
            goods.setOutstockApplyNum(goods.getOutstockApplyNum() - dcGoods.getSendNum());
            goodsDao.save(goods);
        }
//        goodsDao.save(goodsList.values());
        deliverConsignDao.saveAndFlush(deliverConsignUpdate);
        if (deliverConsign.getStatus() == 3) {
            orderService.updateOrderDeliverConsignC(orderIds);
        }
        return true;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addDeliverConsign(DeliverConsign deliverConsign) throws Exception {
        Order order = orderDao.findOne(deliverConsign.getoId());
        DeliverConsign deliverConsignAdd = new DeliverConsign();
        String deliverConsignNo = deliverConsignDao.findDeliverConsignNo();
        deliverConsignAdd.setDeliverConsignNo(StringUtil.genDeliverConsignNo(deliverConsignNo));
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
        Set<Integer> orderIds = new HashSet<>();
        for (DeliverConsignGoods dcGoods : deliverConsign.getDeliverConsignGoodsSet()) {
            Integer gid = dcGoods.getgId();
            Goods goods = goodsList.get(gid);
            if (goods.getOutstockApplyNum() + dcGoods.getSendNum() <= goods.getContractGoodsNum()) {
                dcGoods.setGoods(goods);
                dcGoods.setCreateTime(new Date());
                if (deliverConsign.getStatus() == 3) {
                    goods.setOutstockNum(goods.getOutstockNum() + dcGoods.getSendNum());
                    orderIds.add(goods.getOrder().getId());
                }
                goods.setOutstockApplyNum(goods.getOutstockApplyNum() + dcGoods.getSendNum());
                goodsDao.save(goods);
            } else {
                throw new Exception("发货总数量超过合同数量");
            }
        }
        deliverConsignDao.save(deliverConsignAdd);
        if (deliverConsign.getStatus() == 3) {
            orderService.updateOrderDeliverConsignC(orderIds);
        }
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
        List<DeliverConsign> page = deliverConsignDao.findAll(new Specification<DeliverConsign>() {
            @Override
            public Predicate toPredicate(Root<DeliverConsign> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据国家查询
                String[] country = null;
                if (StringUtils.isNotBlank(deliverNotice.getCountry())) {
                    country = deliverNotice.getCountry().split(",");
                }
                if (country != null) {
                    list.add(root.get("country").in(country));
                }
                // 根据出口通知单号
                if (StringUtil.isNotBlank(deliverNotice.getDeliverConsignNo())) {
                    list.add(cb.equal(root.get("deliverConsignNo").as(String.class), deliverNotice.getDeliverConsignNo()));
                }
                list.add(cb.equal(root.get("status").as(Integer.class), 3));    //已提交
                list.add(cb.equal(root.get("deliverYn").as(Integer.class), 1)); //未删除


                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);

                Predicate result = cb.and(predicates);
                if (deliverNotice.getId() != null) {
                    DeliverNotice one = deliverNoticeDao.findOne(deliverNotice.getId());
                    List<DeliverConsign> deliverConsigns = one.getDeliverConsigns();//查询已选择
                    Integer[] arr = new Integer[deliverConsigns.size()];    //获取id
                    int i = 0;
                    for (DeliverConsign deliverConsign : deliverConsigns) {
                        arr[i] = (deliverConsign.getId());
                        i++;
                    }
                    return cb.or(result, root.get("id").in(arr));
                } else {
                    return result;
                }

            }
        });

        return page;

    }


}
