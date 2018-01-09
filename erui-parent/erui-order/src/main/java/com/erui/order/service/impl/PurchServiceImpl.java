package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.PurchService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private ProjectDao projectDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private PurchGoodsDao purchGoodsDao;
    @Autowired
    private PurchPaymentDao purchPaymentDao;
    @Autowired
    private AttachmentDao attachmentDao;
    @Autowired
    private AttachmentService attachmentService;

    @Override
    public Purch findBaseInfo(Integer id) {
        return purchDao.findOne(id);
    }


    /**
     * 查询采购页面详情信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Purch findDetailInfo(Integer id) {
        if (id != null && id > 0) {
            Purch puch = purchDao.findOne(id);
            puch.getPurchPaymentList().size(); /// 获取合同结算类型信息
            puch.getAttachments().size(); // 获取采购的附件信息
            puch.getPurchGoodsList().size(); //获取采购商品信息
            List<String> projectNoList = new ArrayList<>();
            for (Project p : puch.getProjects()) {
                projectNoList.add(p.getProjectNo());
            }
            puch.setProjectNos(StringUtils.join(projectNoList, ","));

            return puch;
        }
        return null;
    }


    /**
     * 查询采购页面详情信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Purch findPurchAndGoodsInfo(Integer id) {
        Purch puch = purchDao.findOne(id);
        puch.getPurchGoodsList().size(); //获取采购商品信息
        return puch;
    }


    /**
     * 根据条件分页查询采购单信息
     *
     * @param condition
     * @return
     */
    @Override
    @Transactional
    public Page<Purch> findByPage(final Purch condition) {
        PageRequest request = new PageRequest(condition.getPage(), condition.getRows(), Sort.Direction.DESC, "id");

        Page<Purch> page = purchDao.findAll(new Specification<Purch>() {
            @Override
            public Predicate toPredicate(Root<Purch> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据采购合同号模糊查询
                if (StringUtil.isNotBlank(condition.getPurchNo())) {
                    list.add(cb.like(root.get("purchNo").as(String.class), "%" + condition.getPurchNo() + "%"));
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

                // 根据项目号和销售合同号查询
                Set<Purch> purchSet = findByProjectNoAndContractNo(condition.getProjectNos(), condition.getContractNos());
                CriteriaBuilder.In<Object> idIn = cb.in(root.get("id"));
                if (purchSet != null && purchSet.size() > 0) {
                    for (Purch p : purchSet) {
                        idIn.value(p.getId());
                    }
                    list.add(idIn);
                }

                //根据供应商过滤条件
                if (condition.getSupplierId() != null) {
                    list.add(cb.equal(root.get("supplierId").as(Integer.class), condition.getSupplierId()));
                }

                Purch.StatusEnum statusEnum = Purch.StatusEnum.fromCode(condition.getStatus());
                if (statusEnum != null) {
                    list.add(cb.equal(root.get("status").as(Integer.class), statusEnum.getCode()));
                }

                // 连接查询项目信息，应该fetch
                //Join<Object, Object> projectsJoin = root.join("projects");


                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);


        if (page.hasContent()) {
            page.getContent().parallelStream().forEach(vo -> {
                vo.getProjects().size();
            });
        }

        return page;
    }

    // 根据销售号和项目号查询采购列表信息
    private Set<Purch> findByProjectNoAndContractNo(String projectNo, String contractNo) {
        Set<Purch> result = null;
        if (!(StringUtils.isBlank(projectNo) && StringUtils.isBlank(contractNo))) {
            List<Purch> list = purchDao.findAll(new Specification<Purch>() {
                @Override
                public Predicate toPredicate(Root<Purch> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<>();
                    Join<Purch, Project> projects = root.join("projects");
                    if (StringUtils.isNotBlank(projectNo)) {
                        list.add(cb.equal(projects.get("projectNo").as(String.class), "%" + projectNo + "%"));
                    }

                    if (StringUtils.isNotBlank(contractNo)) {
                        list.add(cb.equal(projects.get("contractNo").as(String.class), "%" + contractNo + "%"));
                    }

                    Predicate[] predicates = new Predicate[list.size()];
                    predicates = list.toArray(predicates);
                    return cb.and(predicates);
                }
            });
            if (list != null && list.size() > 0) {
                result = new HashSet<>(list);
            }

        }


        return result;
    }


    /**
     * 新增采购单
     *
     * @param purch
     * @return
     */
    @Transactional
    public boolean insert(Purch purch) throws Exception {
        Date now = new Date();

        // 设置基础数据
        purch.setPurchNo(RandomStringUtils.randomAlphabetic(32));
        purch.setSigningDate(NewDateUtil.getDate(purch.getSigningDate()));
        purch.setArrivalDate(NewDateUtil.getDate(purch.getArrivalDate()));
        purch.setCreateTime(now);
        purch.setUpdateTime(now);
        purch.setDeleteFlag(false);

        // 处理结算方式
        purch.getPurchPaymentList().parallelStream().forEach(vo -> {
            vo.setId(null);
            vo.setCreateTime(now);
        });
        // 处理附件信息
        List<Attachment> attachments = attachmentService.handleParamAttachment(null, purch.getAttachments(), purch.getCreateUserId(), purch.getCreateUserName());
        purch.setAttachments(attachments);

        // 处理商品信息
        List<PurchGoods> purchGoodsList = new ArrayList<>();
        Set<Project> projectSet = new HashSet<>();
        List<Goods> updateGoods = new ArrayList<>();
        for (PurchGoods purchGoods : purch.getPurchGoodsList()) {
            Goods goods = goodsDao.findOne(purchGoods.getgId());
            if (goods == null || goods.getExchanged() || goods.getPurchasedNum() >= goods.getContractGoodsNum()) {
                // 商品的采购已经大于了合同数量
                return false;
            }

            Project project = goods.getProject();
            purchGoodsList.add(purchGoods);
            projectSet.add(project);
            // 处理新增的采购商品
            PurchGoods son = handleAddNewPurchGoods(project, purch, goods, purchGoods);

            Integer purchaseNum = purchGoods.getPurchaseNum();
            if (son != null) {
                purchGoodsList.add(son);
                purchaseNum += son.getPurchaseNum();
            }
            if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
                // 如果是提交则设置商品的已采购数量并更新
                goods.setPurchasedNum(purchaseNum);
                updateGoods.add(goods);
            }
        }

        if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
            // 如果是提交，则更新商品的已采购数量
            goodsDao.save(updateGoods);
        }

        purch.setPurchGoodsList(purchGoodsList);
        purch.setProjects(new ArrayList<>(projectSet));

        // 保存采购单
        purchDao.save(purch);

        List<Integer> projectIds = projectSet.parallelStream().map(Project::getId).collect(Collectors.toList());
        checkProjectPurchDone(projectIds);

        return true;
    }


    /**
     * 更新采购单
     *
     * @param purch
     * @return
     */
    @Override
    @Transactional
    public boolean update(Purch purch) throws Exception {

        Purch dbPurch = purchDao.findOne(purch.getId());
        // 之前的采购必须不能为空且未提交状态
        if (dbPurch == null || dbPurch.getStatus() != Purch.StatusEnum.READY.getCode()) {
            return false;
        }
        final Date now = new Date();

        // 设置基本信息
        dbPurch.setBaseInfo(purch);
        dbPurch.setUpdateTime(now);


        // 处理结算方式
        Map<Integer, PurchPayment> collect = dbPurch.getPurchPaymentList().parallelStream().collect(Collectors.toMap(PurchPayment::getId, vo -> vo));
        List<PurchPayment> paymentList = purch.getPurchPaymentList().parallelStream().filter(vo -> {
            Integer payId = vo.getId();
            return payId == null || collect.containsKey(payId);
        }).map(payment -> {
            Integer paymentId = payment.getId();
            if (paymentId == null) {
                payment.setCreateTime(now);
            } else {
                PurchPayment payment2 = collect.remove(paymentId);
                payment.setCreateTime(payment2.getCreateTime());
            }
            return payment;
        }).collect(Collectors.toList());
        dbPurch.setPurchPaymentList(paymentList);
        // 删除废弃的结算方式
        if (collect.size() > 0) {
            purchPaymentDao.delete(collect.values());
        }


        // 处理附件信息
        List<Attachment> attachmentlist = attachmentService.handleParamAttachment(dbPurch.getAttachments(), purch.getAttachments(), purch.getCreateUserId(), purch.getCreateUserName());
        dbPurch.setAttachments(attachmentlist);


        // 处理商品
        List<PurchGoods> purchGoodsList = new ArrayList<>(); // 声明最终采购商品容器
        Set<Project> projectSet = new HashSet<>();

        // 数据库现在的采购商品信息
        Map<Integer, PurchGoods> dbPurchGoodsMap = dbPurch.getPurchGoodsList().parallelStream().collect(Collectors.toMap(PurchGoods::getId, vo -> vo));
        Set<Integer> existId = new HashSet<>();

        // 处理参数中的采购商品信息
        for (PurchGoods pg : purch.getPurchGoodsList()) {
            Integer pgId = pg.getId();
            if (pgId == null) { // 新增加的采购商品信息
                Goods goods = goodsDao.findOne(pg.getgId());
                // 已采购数量
                Integer purchasedNum = goods.getPurchasedNum();
                if (goods == null || goods.getExchanged() || purchasedNum >= goods.getContractGoodsNum()) {
                    // 商品的采购已经大于了合同数量
                    return false;
                }

                Project project = goods.getProject();

                // 查看是否存在替换商品
                PurchGoods son = handleAddNewPurchGoods(project, dbPurch, goods, pg);
                purchasedNum += pg.getPurchaseNum();
                if (son != null) {
                    purchGoodsList.add(son);
                    purchasedNum += son.getPurchaseNum();
                }
                purchGoodsList.add(pg);
                projectSet.add(project);


                if (purch.getStatus() == Purch.StatusEnum.DONE.getCode()) {
                    // 更新已采购数量
                    goods.setPurchasedNum(purchasedNum);
                    goodsDao.save(goods);
                }
            } else if (dbPurchGoodsMap.containsKey(pgId)) {
                // 编辑原来的采购商品
                PurchGoods purchGoods = dbPurchGoodsMap.remove(pgId);
                existId.add(pgId);

                Goods goods = purchGoods.getGoods();
                Project project = purchGoods.getProject();

                boolean hasSon = false;
                Boolean exchanged = purchGoods.getExchanged();
                if (exchanged != null && exchanged) {
                    // 是替换商品，查看父商品是否存在，如果父商品不存在，则错误
                    Integer pId = purchGoods.getParent().getId();
                    if (!(existId.contains(pId) || dbPurchGoodsMap.containsKey(pId))) {
                        throw new Exception("替换商品没有相应的原商品");
                    }
                } else {
                    // 不是替换商品，查看是否添加了替换商品
                    hasSon = pg.getSon() != null;
                }
                // 正常添加
                projectSet.add(project);

                purchGoods.setPurchaseNum(pg.getPurchaseNum()); // 采购商品数量
                purchGoods.setPurchasePrice(pg.getPurchasePrice()); // 采购单价
                purchGoods.setPurchaseTotalPrice(pg.getPurchaseTotalPrice()); //  采购总金额
                purchGoods.setPurchaseRemark(pg.getPurchaseRemark()); // 采购说明
                // 计算含税价格和不含税单价以及总价款
                String currencyBn = purch.getCurrencyBn();
                if (purchGoods.getPurchasePrice() == null || purchGoods.getPurchaseNum() == null) {
                    throw new Exception("商品的采购价格和数量错误");
                }
                if ("人民币".equals(currencyBn)) {
                    // 人民币是默认含税价格
                    purchGoods.setTaxPrice(purchGoods.getPurchasePrice());
                    purchGoods.setNonTaxPrice(purchGoods.getPurchasePrice().divide(new BigDecimal("1.17"), 4, BigDecimal.ROUND_DOWN));
                } else {
                    purchGoods.setNonTaxPrice(purchGoods.getPurchasePrice());
                }
                // 总价款
                purchGoods.setTotalPrice(purchGoods.getPurchasePrice().multiply(new BigDecimal(purchGoods.getPurchaseNum().intValue())));

                purchGoodsList.add(purchGoods);

                int purchaseNum = purchGoods.getPurchaseNum();
                if (hasSon) {
                    // 处理替换商品
                    PurchGoods son = pg.getSon();
                    handleExchangedPurchGoods(project, goods, dbPurch, purchGoods, son);
                    purchGoodsList.add(son);
                    purchaseNum += son.getPurchaseNum();
                }
                // 提交则修改商品的已采购数量
                if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
                    if (goods.getParentId() != null) {
                        goods = goodsDao.findOne(goods.getParentId());
                    }
                    goods.setPurchasedNum(goods.getPurchasedNum() + purchaseNum);
                    goodsDao.save(goods);
                }
            }
        }

        dbPurch.setPurchGoodsList(purchGoodsList);
        dbPurch.setProjects(new ArrayList<>(projectSet));

        List<Integer> projectIdList = projectSet.parallelStream().map(Project::getId).collect(Collectors.toList());

        // 删除不关联的商品信息
        if (dbPurchGoodsMap.size() > 0) {
            Collection<PurchGoods> values = dbPurchGoodsMap.values();
            purchGoodsDao.delete(values);
        }
        purchDao.save(dbPurch);


        // 检查项目是否已经采购完成
        checkProjectPurchDone(projectIdList);

        return true;
    }

    // 处理新增采购信息，如果采购信息有替换的商品，则返回替换信息
    private PurchGoods handleAddNewPurchGoods(Project project, Purch purch, Goods goods, PurchGoods newPurchGoods) throws Exception {
        String contractNo = project.getContractNo();
        String projectNo = project.getProjectNo();
        newPurchGoods.setId(null);
        newPurchGoods.setProject(project);
        newPurchGoods.setContractNo(contractNo);
        newPurchGoods.setProjectNo(projectNo);
        newPurchGoods.setPurch(purch);
        newPurchGoods.setGoods(goods);
        Integer purchaseNum = newPurchGoods.getPurchaseNum();
        newPurchGoods.setPurchaseNum(purchaseNum != null && purchaseNum > 0 ? purchaseNum : 0);
        newPurchGoods.setExchanged(false);
        newPurchGoods.setInspectNum(0); // 设置已报检数量
        newPurchGoods.setGoodNum(0);
        // 计算含税价格和不含税单价以及总价款
        String currencyBn = purch.getCurrencyBn();
        if (newPurchGoods.getPurchasePrice() == null || newPurchGoods.getPurchaseNum() == null) {
            throw new Exception("商品的采购价格和数量错误");
        }
        if ("人民币".equals(currencyBn)) {
            // 人民币是默认含税价格
            newPurchGoods.setTaxPrice(newPurchGoods.getPurchasePrice());
            newPurchGoods.setNonTaxPrice(newPurchGoods.getPurchasePrice().divide(new BigDecimal("1.17"), 4, BigDecimal.ROUND_DOWN));
        } else {
            newPurchGoods.setNonTaxPrice(newPurchGoods.getPurchasePrice());
        }
        // 总价款
        newPurchGoods.setTotalPrice(newPurchGoods.getPurchasePrice().multiply(new BigDecimal(newPurchGoods.getPurchaseNum().intValue())));
        newPurchGoods.setCreateTime(new Date());

        // 查看是否存在替换商品
        PurchGoods son = newPurchGoods.getSon();
        if (son != null) {
            // 处理替换商品
            handleExchangedPurchGoods(project, goods, purch, newPurchGoods, son);
        }
        return son;
    }


    /**
     * 处理替换后的商品信息
     */
    private void handleExchangedPurchGoods(Project project, Goods beforeGoods, Purch purch, PurchGoods beforePurchGoods, PurchGoods son) throws Exception {
        String contractNo = beforeGoods.getContractNo();
        String projectNo = beforeGoods.getProjectNo();

        //  插入替换后的新商品
        Goods sonGoods = son.getGoods();
        sonGoods.setExchanged(true);
        sonGoods.setOrder(beforeGoods.getOrder());
        sonGoods.setProject(project);
        sonGoods.setContractNo(contractNo);
        sonGoods.setProjectNo(projectNo);
        sonGoods.setContractGoodsNum(0);
        sonGoods.setPurchasedNum(0);
        sonGoods.setInspectNum(0);
        sonGoods.setInstockNum(0);
        sonGoods.setOutstockApplyNum(0);
        sonGoods.setOutstockNum(0);

        sonGoods = goodsDao.save(sonGoods);

        // 处理替换后的采购信息
        son.setProject(project);
        son.setParent(beforePurchGoods);
        son.setContractNo(contractNo);
        son.setProjectNo(projectNo);
        son.setPurch(purch);
        son.setGoods(sonGoods);
        son.setExchanged(true);

        // 计算含税价格和不含税单价以及总价款
        String currencyBn = purch.getCurrencyBn();
        if (son.getPurchasePrice() == null || son.getPurchaseNum() == null) {
            throw new Exception("商品的采购价格和数量错误");
        }
        if ("人民币".equals(currencyBn)) {
            // 人民币是默认含税价格
            son.setTaxPrice(son.getPurchasePrice());
            son.setNonTaxPrice(son.getPurchasePrice().divide(new BigDecimal("1.17"), 4, BigDecimal.ROUND_DOWN));
        } else {
            son.setNonTaxPrice(son.getPurchasePrice());
        }
        // 总价款
        son.setTotalPrice(son.getPurchasePrice().multiply(new BigDecimal(son.getPurchaseNum().intValue())));
        son.setInspectNum(0);
        son.setGoodNum(0);
        son.setCreateTime(new Date());
    }


    /**
     * 检查采购项目的采购数量是否已完毕
     *
     * @param projectIds 项目ID列表
     */
    private void checkProjectPurchDone(List<Integer> projectIds) {
        if (projectIds.size() > 0) {
            List<Project> projectList = projectDao.findByIdIn(projectIds);
            projectList.parallelStream().forEach(project -> {
                List<Goods> goodsList = project.getOrder().getGoodsList();
                boolean purchDone = true;
                for (Goods goods : goodsList) {
                    if (!goods.getExchanged() && goods.getPurchasedNum() < goods.getContractGoodsNum()) {
                        purchDone = false;
                        break;
                    }
                }
                project.setPurchDone(purchDone);
            });
            projectDao.save(projectList);
        }
    }

}
