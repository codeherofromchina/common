package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.GoodsDao;
import com.erui.order.dao.PurchDao;
import com.erui.order.dao.PurchGoodsDao;
import com.erui.order.entity.*;
import com.erui.order.requestVo.PGoods;
import com.erui.order.requestVo.PurchListCondition;
import com.erui.order.requestVo.PurchSaveVo;
import com.erui.order.service.PurchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class PurchServiceImpl implements PurchService {

    @Autowired
    private PurchDao purchDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private PurchGoodsDao purchGoodsDao;

    @Override
    public Purch findById(Integer id) {
        return purchDao.findOne(id);
    }


    /**
     * 查询采购页面详情信息
     *
     * @param id
     * @return
     */
    @Override
    public PurchSaveVo findByIdForDetailPage(Integer id) {
        Purch puch = purchDao.findOne(id);

        PurchSaveVo result = new PurchSaveVo();
        result.copyBaseInfoFrom(puch);

        List<PurchGoods> purchGoodsList = puch.getPurchGoodsList();
        Map<Integer, PGoods> purchGoodsMap = new HashMap<>();
        List<PGoods> psvgList = new ArrayList<>();
        Goods goods = null;
        for (PurchGoods pg : purchGoodsList) {
            goods = pg.getGoods();
            PGoods psvg = new PGoods();

            psvg.setId(pg.getId());
            psvg.setGoodsId(goods.getId());
            psvg.setPurchaseNum(pg.getPurchaseNum());
            psvg.setPurchasePrice(pg.getPurchasePrice());
            psvg.setPurchaseTotalPrice(pg.getPurchaseTotalPrice());
            psvg.setRemark(pg.getPurchaseRemark());
            psvg.setSku(goods.getSku());
            psvg.setMeteType(goods.getMeteType());
            psvg.setProType(goods.getProType());
            psvg.setNameEn(goods.getNameEn());
            psvg.setNameZh(goods.getNameZh());
            psvg.setUnit(goods.getUnit());
            psvg.setBrand(goods.getBrand());
            psvg.setModel(goods.getModel());

            if (pg.isSon()) {

                PGoods goods1 = purchGoodsMap.get(pg.getParent().getId());
                goods1.setSon(psvg);
            } else {
                purchGoodsMap.put(pg.getId(), psvg);
                psvgList.add(psvg);
            }
        }
        result.setPurchGoodsList(psvgList);

        result.setPurchPaymentList(puch.getPurchPaymentList());
        result.setAttachments(puch.getAttachments());

        return result;
    }

    @Override
    public List<PurchGoods> findInspectGoodsByPurch(Integer id) {
        List<PurchGoods> purchGoodsList = purchGoodsDao.findByPurchId(id);

        if (purchGoodsList == null) {
            return new ArrayList<>();
        }

        return purchGoodsList.stream().filter(vo -> {
            return vo.getPurchaseNum() > vo.getInspectNum();
        }).collect(Collectors.toList());
    }


    /**
     * 根据条件分页查询采购单信息
     *
     * @param condition
     * @return
     */
    @Override
    @Transactional
    public Page<Purch> findByPage(final PurchListCondition condition) {
        PageRequest request = new PageRequest(condition.getPage(), condition.getRows(), null);

        Page<Purch> page = purchDao.findAll(new Specification<Purch>() {
            @Override
            public Predicate toPredicate(Root<Purch> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据采购合同号模糊查询
                if (StringUtil.isNotBlank(condition.getContractNo())) {
                    list.add(cb.like(root.get("purchNo").as(String.class), "%" + condition.getContractNo() + "%"));
                }
                // 根据采购经办人查询
                if (condition.getAgentId() != null) {
                    list.add(cb.equal(root.get("agentId").as(Integer.class), condition.getAgentId()));
                }
                // 根据采购签约日期查询
                if (condition.getSigningDate() != null) {
                    list.add(cb.equal(root.get("signingDate").as(Date.class), NewDateUtil.getDate(condition.getSigningDate())));
                }
                // 根据采采购合同规定交货日期查询
                if (condition.getArrivalDate() != null) {
                    list.add(cb.equal(root.get("arrivalDate").as(Date.class), NewDateUtil.getDate(condition.getArrivalDate())));
                }
                //  根据销售合同号查询
                Join<Purch, PurchGoods> purchGoods = root.join("purchGoodsList");
                if (StringUtils.isNotBlank(condition.getContractNo())) {
                    list.add(cb.like(purchGoods.get("contractNo").as(String.class), "%" + condition.getContractNo() + "%"));
                }
                //根据项目号查找
                if (StringUtils.isNotBlank(condition.getProjectNo())) {
                    list.add(cb.like(purchGoods.get("projectNo").as(String.class), "%" + condition.getProjectNo() + "%"));
                }

                //根据供应商过滤条件
                if (condition.getSupplierId() != null) {
                    list.add(cb.equal(root.get("supplierId").as(Integer.class), condition.getSupplierId()));
                }

                Purch.StatusEnum statusEnum = Purch.StatusEnum.fromCode(condition.getStatus());
                if (statusEnum != null) {
                    list.add(cb.equal(root.get("status").as(Integer.class), statusEnum.getCode()));
                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);


        return page;
    }


    /**
     * 新增采购单
     *
     * @param purchSaveVo
     * @return
     */
    public boolean insert(PurchSaveVo purchSaveVo) {

        Purch purch = new Purch();
        purchSaveVo.copyBaseInfoTo(purch);

        List<PGoods> goodsList = purchSaveVo.getPurchGoodsList();
        List<PurchPayment> paymentList = purchSaveVo.getPurchPaymentList();
        List<Attachment> attachments = purchSaveVo.getAttachments();
        Date now = new Date();
        purch.setCreateTime(now);
        purch.setUpdateTime(now);
        purch.setDeleteFlag(false);

        // 处理商品
        List<PurchGoods> purchGoodsList = handleGoods(purch, goodsList);
        purch.setPurchGoodsList(purchGoodsList);

        // 处理结算方式
        paymentList = handlePayment(purch, paymentList);
        purch.setPurchPaymentList(paymentList);

        // 处理附件信息
        for (Attachment attachment : attachments) {
            attachment.setId(null); // 确保新增的采购一定是新增的附件
            attachment.setCreateTime(now);
            attachment.setDeleteFlag(false);
            attachment.setUserId(purchSaveVo.getUserId());
            attachment.setUserName(purchSaveVo.getUserName());
        }
        purch.setAttachments(attachments);

        // 保存采购单
        purchDao.save(purch);

        return true;
    }


    /**
     * 更新采购单
     *
     * @param purchSaveVo
     * @return
     */
    @Override
    @Transactional
    public boolean update(PurchSaveVo purchSaveVo) {

        Purch purch = purch = purchDao.findOne(purchSaveVo.getId());
        if (purch == null) {
            return false;
        }
        purchSaveVo.copyBaseInfoTo(purch);

        List<PGoods> goodsList = purchSaveVo.getPurchGoodsList();
        List<PurchPayment> paymentList = purchSaveVo.getPurchPaymentList();
        List<Attachment> attachments = purchSaveVo.getAttachments();

        List<PurchGoods> purchGoodsList = handleGoods(purch, goodsList);
        purch.setPurchGoodsList(purchGoodsList);

        // 处理结算方式
        paymentList = handlePayment(purch, paymentList);
        purch.setPurchPaymentList(paymentList);
        // 附件
        purch.setAttachments(attachments);


        return true;
    }


    /**
     * 处理采购单的支付方式
     *
     * @param paymentList
     * @return
     */
    private List<PurchPayment> handlePayment(Purch purch, List<PurchPayment> paymentList) {
        // 处理结算方式
        for (PurchPayment payment : paymentList) {
            payment.setPurch(purch);
            if (payment.getId() == null) {
                payment.setCreateTime(new Date());
            }
        }
        return paymentList;
    }

    /**
     * 处理采购单的商品信息
     *
     * @param goodsList
     * @return
     */
    private List<PurchGoods> handleGoods(Purch purch, List<PGoods> goodsList) {
        // 处理商品信息
        List<PurchGoods> purchGoodsList = new ArrayList<>();
        for (PGoods innerGoods : goodsList) {
            Integer goodsId = innerGoods.getGoodsId();
            Goods goods = goodsDao.findOne(goodsId);
            if (goods == null) {
                return null;
            }

            PurchGoods pGoods = purchGoodsDao.findOne(innerGoods.getId());
            if (pGoods == null) {
                pGoods = new PurchGoods();
                pGoods.setProject(goods.getProject());
                pGoods.setProjectNo(goods.getProjectNo());
                pGoods.setGoods(goods);
                pGoods.setCreateTime(new Date());
            } else {
                // 原来存在，说明已经在商品中提现了已采购
                goods.setPurchasedNum(goods.getPurchasedNum() - pGoods.getPurchaseNum());
            }
            pGoods.setPurchaseNum(innerGoods.getPurchaseNum());
            pGoods.setPurchasePrice(innerGoods.getPurchasePrice());
            pGoods.setPurchaseTotalPrice(innerGoods.getPurchaseTotalPrice());
            pGoods.setPurchaseRemark(innerGoods.getRemark());
            pGoods.setPurch(purch);
            purchGoodsList.add(pGoods);
            //商品修改已采购数量
            goods.setPurchasedNum(goods.getPurchasedNum() + pGoods.getPurchaseNum());

            PGoods sonGoods = innerGoods.getSon();
            if (sonGoods != null) {
                PurchGoods pGoods2 = purchGoodsDao.findOne(sonGoods.getId());
                if (pGoods2 == null) {
                    // 新增一个替换的商品操作
                    // 插入一个新的商品
                    Goods goods02 = new Goods();

                    goods02.setSku(sonGoods.getSku()); // 商品SKU
                    goods02.setMeteType(sonGoods.getMeteType()); //  物料分类
                    goods02.setProType(sonGoods.getProType()); //  产品分类
                    goods02.setNameEn(sonGoods.getNameEn()); // 外文品名
                    goods02.setNameZh(sonGoods.getNameZh()); //  中文品名
                    goods02.setUnit(sonGoods.getUnit()); //  单位
                    goods02.setBrand(sonGoods.getBrand()); //  品牌
                    goods02.setModel(sonGoods.getModel()); //  规格型号
                    goods02.setParentId(goods.getId());
                    goods02.setProjectNo(goods.getProjectNo());
                    goods02.setProject(goods.getProject());
                    goods02.setOrder(goods.getOrder());
                    goods02 = goodsDao.save(goods02);

                    pGoods2 = new PurchGoods();
                    pGoods2.setProject(goods.getProject());
                    pGoods2.setProjectNo(goods.getProjectNo());
                    pGoods2.setGoods(goods02);
                    pGoods2.setParent(pGoods);
                    pGoods2.setCreateTime(new Date());
                } else {
                    // 原来存在，说明已经在商品中提现了已采购
                    goods.setPurchasedNum(goods.getPurchasedNum() - pGoods2.getPurchaseNum());
                }

                pGoods2.setPurchaseNum(sonGoods.getPurchaseNum());
                pGoods2.setPurchasePrice(sonGoods.getPurchasePrice());
                pGoods2.setPurchaseTotalPrice(sonGoods.getPurchaseTotalPrice());
                pGoods2.setPurchaseRemark(sonGoods.getRemark());
                pGoods2.setPurch(purch);
                pGoods2.setSon(true);
                purchGoodsList.add(pGoods2);
                //商品修改已采购数量
                goods.setPurchasedNum(goods.getPurchasedNum() + pGoods2.getPurchaseNum());
                goods.setAbstracted(true);
            }
            goodsDao.save(goods);
        }
        return purchGoodsList;
    }


}
