package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.PurchService;
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
    private OrderDao orderDao;
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public Purch findPurchAndGoodsInfo(Integer id) {
        Purch puch = purchDao.findOne(id);
        puch.getPurchGoodsList().size(); //获取采购商品信息
        return puch;
    }

    /**
     * 根据订单id查询(进行中/已完成)采购列表
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> listByOrderId(Integer orderId) throws Exception {
        Order order = orderDao.findOne(orderId);
        if (order == null) {
            throw new Exception("不存在的订单");
        }

        List<Map<String, Object>> result = new ArrayList<>();
        List<Purch> list = purchDao.findAll(new Specification<Purch>() {
            @Override
            public Predicate toPredicate(Root<Purch> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                Join<Purch, Project> projectJoin = root.join("projects");
                Join<Project, Order> orderJoin = projectJoin.join("order");
                list.add(criteriaBuilder.equal(orderJoin.get("id").as(Integer.class), orderId));

                // (进行中/已完成)
                list.add(criteriaBuilder.notEqual(root.get("status").as(Integer.class), Purch.StatusEnum.READY.getCode()));

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        });

        if (list != null && list.size() > 0) {
            Set<Integer> idSet = new HashSet<>();
            for (Purch purch : list) {
                Integer id = purch.getId();
                if (idSet.contains(id)) {
                    continue;
                }
                idSet.add(id);
                Map<String, Object> map = new HashMap<>();


                List<String> contractNoList = new ArrayList<>();
                List<String> projectNoList = new ArrayList<>();
                purch.getProjects().stream().forEach(project -> {
                    projectNoList.add(project.getProjectNo());
                    contractNoList.add(project.getContractNo());
                });
                map.put("id", purch.getId());
                map.put("purchNo", purch.getPurchNo());
                map.put("projectNos", StringUtils.join(projectNoList, ","));
                map.put("contractNos", StringUtils.join(contractNoList, ","));
                map.put("agentName", purch.getAgentName());
                map.put("agentId", purch.getAgentId());
                map.put("signingDate", purch.getSigningDate());

                result.add(map);
            }
        }
        return result;
    }


    /**
     * 根据条件分页查询采购单信息
     *
     * @param condition
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Purch> findByPage(final Purch condition) {
        PageRequest request = new PageRequest(condition.getPage() - 1, condition.getRows(), Sort.Direction.DESC, "id");

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
                if (!(StringUtils.isBlank(condition.getProjectNos()) && StringUtils.isBlank(condition.getContractNos()))) {
                    Set<Purch> purchSet = findByProjectNoAndContractNo(condition.getProjectNos(), condition.getContractNos());
                    CriteriaBuilder.In<Object> idIn = cb.in(root.get("id"));
                    if (purchSet != null && purchSet.size() > 0) {
                        for (Purch p : purchSet) {
                            idIn.value(p.getId());
                        }
                    } else {
                        // 查找失败
                        idIn.value(-1);
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


                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);


        if (page.hasContent()) {
            page.getContent().stream().forEach(vo -> {
                List<String> projectNoList = new ArrayList<>();
                List<String> contractNoList = new ArrayList<>();
                vo.getProjects().stream().forEach(project -> {
                    projectNoList.add(project.getProjectNo());
                    contractNoList.add(project.getContractNo());
                });
                vo.setProjectNos(StringUtils.join(projectNoList, ","));
                vo.setContractNos(StringUtils.join(contractNoList, ","));
                vo.getPurchGoodsList().size();
            });
        }

        return page;
    }

    // 根据销售号和项目号查询采购列表信息
    private Set<Purch> findByProjectNoAndContractNo(String projectNo, String contractNo) {
        Set<Purch> result = null;
        List<Purch> list = purchDao.findAll(new Specification<Purch>() {
            @Override
            public Predicate toPredicate(Root<Purch> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                Join<Purch, Project> projects = root.join("projects");
                if (StringUtils.isNotBlank(projectNo)) {
                    list.add(cb.like(projects.get("projectNo").as(String.class), "%" + projectNo + "%"));
                }
                if (StringUtils.isNotBlank(contractNo)) {
                    list.add(cb.like(projects.get("contractNo").as(String.class), "%" + contractNo + "%"));
                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        });
        if (list != null && list.size() > 0) {
            result = new HashSet<>(list);
        }
        return result;
    }


    /**
     * 新增采购单
     *
     * @param purch
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(Purch purch) throws Exception {
        Date now = new Date();
        Long count = purchDao.findCountByPurchNo(purch.getPurchNo());
        if (count != null && count > 0) {
            throw new Exception("采购合同号重复");
        }
        // 设置基础数据
        purch.setSigningDate(NewDateUtil.getDate(purch.getSigningDate()));
        purch.setArrivalDate(NewDateUtil.getDate(purch.getArrivalDate()));
        purch.setCreateTime(now);

        // 处理结算方式,新增，所以讲所有id设置为null，并添加新增时间
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
            // 检查是否传入采购数量或者替换商品
            Integer purchaseNum = purchGoods.getPurchaseNum(); // 获取采购数量
            PurchGoods tSon = purchGoods.getSon(); // 获取替换商品
            if ((purchaseNum == null || purchaseNum <= 0) && tSon == null) {
                // 传入的商品没有数量，表示不采购此商品
                continue;
            }
            // 获取要采购的商品
            Goods goods = goodsDao.findOne(purchGoods.getgId());
            if (goods == null || goods.getExchanged()) {
                // 给定的商品不存在或者是被替换的商品，则错误
                throw new Exception("商品不存在");
            }
            Project project = goods.getProject();
            // 必须是已创建采购申请单并未完成采购的项目
            if (Project.PurchReqCreateEnum.valueOfCode(project.getPurchReqCreate()) != Project.PurchReqCreateEnum.SUBMITED) {
                throw new Exception("项目必须提交采购申请");
            }
            if (project.getPurchDone()) {
                throw new Exception("项目采购已完成，不能再次采购");
            }
            projectSet.add(project);
            PurchGoods son = handleAddNewPurchGoods(project, purch, goods, purchGoods);
            purchGoodsList.add(purchGoods);
            if (son != null) {
                purchGoodsList.add(son);
            }
            int intPurchaseNum = purchGoods.getPurchaseNum();
            if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
                // 如果是提交则设置商品的已采购数量并更新
                goods.setPurchasedNum(goods.getPurchasedNum() + intPurchaseNum);
                // 完善商品的项目执行跟踪信息
                setGoodsTraceData(goods, purch);
            }
            // 增加预采购数量
            goods.setPrePurchsedNum(goods.getPrePurchsedNum() + intPurchaseNum);
            // 直接更新商品，放置循环中存在多次修改同一个商品错误
            goodsDao.save(goods);
        }
        if (purchGoodsList.size() == 0) {
            throw new Exception("必须存在要采购的商品");
        }
        purch.setPurchGoodsList(purchGoodsList);
        purch.setProjects(projectSet);
        // 保存采购单
        purchDao.save(purch);
        // 检查项目是否已经采购完成
        List<Integer> projectIds = projectSet.parallelStream().map(Project::getId).collect(Collectors.toList());
        checkProjectPurchDone(projectIds);

        return true;
    }


    private void setGoodsTraceData(Goods goods, Purch purch) {
        // 完善商品的项目执行跟踪信息 (只设置第一次的信息)
        if (goods.getPurChgDate() == null) {
            goods.setPurChgDate(purch.getPurChgDate());
        }
        if (goods.getSigningDate() == null) {
            goods.setSigningDate(purch.getSigningDate());
        }
        if (goods.getAgentId() == null) {
            goods.setAgentId(purch.getAgentId());
        }
        if (goods.getArrivalDate() == null) {
            goods.setArrivalDate(purch.getArrivalDate());
        }
    }


    /**
     * 更新采购单
     *
     * @param purch
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Purch purch) throws Exception {
        Purch dbPurch = purchDao.findOne(purch.getId());
        // 之前的采购必须不能为空且未提交状态
        if (dbPurch == null || dbPurch.getDeleteFlag()) {
            throw new Exception("采购信息不存在");
        } else if (dbPurch.getStatus() != Purch.StatusEnum.READY.getCode()) {
            throw new Exception("采购信息已提交不能修改");
        }
        final Date now = new Date();
        // 设置基本信息
        dbPurch.setBaseInfo(purch);
        dbPurch.setUpdateTime(now);

        // 处理结算方式
        Map<Integer, PurchPayment> collect = dbPurch.getPurchPaymentList().parallelStream().collect(Collectors.toMap(PurchPayment::getId, vo -> vo));
        List<PurchPayment> paymentList = purch.getPurchPaymentList().parallelStream().filter(vo -> {
            Integer payId = vo.getId();
            // 过滤掉可能是其他采购信息的结算方式
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
        Set<Project> projectSet = new HashSet<>(); // 声明项目的容器
        // 数据库现在的采购商品信息
        Map<Integer, PurchGoods> dbPurchGoodsMap = dbPurch.getPurchGoodsList().parallelStream().collect(Collectors.toMap(PurchGoods::getId, vo -> vo));
        Set<Integer> existId = new HashSet<>();
        // 处理参数中的采购商品信息
        for (PurchGoods pg : purch.getPurchGoodsList()) {
            Integer pgId = pg.getId();
            if (pgId == null) { // 新增加的采购商品信息
                // 检查是否传入采购数量或者替换商品
                Integer purchaseNum = pg.getPurchaseNum(); // 获取采购数量
                PurchGoods tSon = pg.getSon(); // 获取替换商品
                if ((purchaseNum == null || purchaseNum <= 0) && tSon == null) {
                    // 传入的商品没有数量，表示不采购此商品
                    continue;
                }
                // 获取要采购的商品
                Goods goods = goodsDao.findOne(pg.getgId());
                if (goods == null || goods.getExchanged()) {
                    throw new Exception("商品不存在");
                }
                Project project = goods.getProject();
                // 必须是已创建采购申请单并未完成采购的项目
                if (Project.PurchReqCreateEnum.valueOfCode(project.getPurchReqCreate()) != Project.PurchReqCreateEnum.SUBMITED) {
                    throw new Exception("项目必须提交采购申请");
                }
                if (project.getPurchDone()) {
                    throw new Exception("项目采购已完成，不能再次采购");
                }
                projectSet.add(project);
                // 查看是否存在替换商品
                PurchGoods son = handleAddNewPurchGoods(project, dbPurch, goods, pg);
                purchGoodsList.add(pg);
                if (son != null) {
                    purchGoodsList.add(son);
                }
                Integer intPurchaseNum = pg.getPurchaseNum();
                // 更新商品的采购数量和预采购数量
                if (purch.getStatus() == Purch.StatusEnum.DONE.getCode()) {
                    // 更新已采购数量
                    goods.setPurchasedNum(goods.getPurchasedNum() + intPurchaseNum);
                    // 设置商品的项目跟踪信息
                    setGoodsTraceData(goods, purch);
                }
                goods.setPrePurchsedNum(goods.getPrePurchsedNum() + intPurchaseNum);
                goodsDao.save(goods);
            } else if (dbPurchGoodsMap.containsKey(pgId)) {
                Integer paramPurchaseNum = pg.getPurchaseNum();
                // 验证是删除还是修改，传入的商品采购数量0也作为删除
                if (paramPurchaseNum == null || paramPurchaseNum <= 0) {
                    PurchGoods purchGoods = dbPurchGoodsMap.get(pgId);
                    if (purchGoods.getExchanged()) {
                        // 是替换后的商品，则直接删除（跳过处理后在循环外删除）
                        continue;
                    } else if (pg.getSon() == null) { // 不是替换商品，查看是否存在替换后商品，如果不存在，则删除
                        PurchGoods sonPurchGoods = purchGoodsDao.findByPurchIdAndParentId(dbPurch.getId(), pgId);
                        if (sonPurchGoods == null) {
                            continue;
                        } else {
                            // 查找参数中的替换后商品
                            PurchGoods paramSonPurchGoods = purch.getPurchGoodsList().parallelStream().filter(vo -> {
                                Integer id = vo.getId();
                                if (id != null && id.intValue() == sonPurchGoods.getId()) {
                                    return true;
                                }
                                return false;
                            }).findFirst().get();
                            if (paramSonPurchGoods == null) {
                                continue;
                            }
                            Integer paramSonPurchaseNum = paramSonPurchGoods.getPurchaseNum();
                            if (paramSonPurchaseNum == null || paramSonPurchaseNum <= 0) {
                                continue;
                            }
                        }
                    } else {
                        Integer purchaseNum = pg.getSon().getPurchaseNum();
                        if (purchaseNum == null || purchaseNum <= 0) {
                            continue;
                        }
                    }
                }
                // 编辑原来的采购商品
                PurchGoods purchGoods = dbPurchGoodsMap.remove(pgId);
                existId.add(pgId);
                Project project = purchGoods.getProject();

                boolean hasSon = false;
                if (purchGoods.getExchanged()) {
                    // 是替换商品，查看父商品是否存在
                    PurchGoods parentPurchGoods = purchGoods.getParent();
                    Integer pId = parentPurchGoods.getId();
                    if (!existId.contains(pId)) {
                        // 查找替换前的商品先处理
                        PurchGoods paramParentPurchGoods = purch.getPurchGoodsList().parallelStream().filter(vo -> vo.getId().intValue() == pId).findFirst().get();
                        if (paramParentPurchGoods == null) {
                            throw new Exception("父采购商品信息不存在");
                        }
                    }
                } else {
                    // 不是替换商品，查看是否添加了替换商品
                    hasSon = pg.getSon() != null;
                }
                // 正常添加
                projectSet.add(project);

                int oldPurchaseNum = purchGoods.getPurchaseNum();
                purchGoods.setPurchaseNum(pg.getPurchaseNum() == null && pg.getPurchaseNum() < 0 ? 0 : pg.getPurchaseNum()); // 采购商品数量
                purchGoods.setPurchasePrice(pg.getPurchasePrice()); // 采购单价
                purchGoods.setPurchaseTotalPrice(pg.getPurchaseTotalPrice()); //  采购总金额
                purchGoods.setPurchaseRemark(pg.getPurchaseRemark()); // 采购说明
                // 计算含税价格和不含税单价以及总价款
                String currencyBn = purch.getCurrencyBn();
                if (purchGoods.getPurchaseNum() > 0) {
                    if (purchGoods.getPurchasePrice() == null || purchGoods.getPurchasePrice().compareTo(BigDecimal.ZERO) != 1) {
                        throw new Exception("商品的采购价格错误");
                    }
                    if ("CNY".equals(currencyBn)) {
                        // 人民币是默认含税价格
                        purchGoods.setTaxPrice(purchGoods.getPurchasePrice());
                        purchGoods.setNonTaxPrice(purchGoods.getPurchasePrice().divide(new BigDecimal("1.17"), 4, BigDecimal.ROUND_DOWN));
                    } else {
                        purchGoods.setNonTaxPrice(purchGoods.getPurchasePrice());
                    }
                    // 总价款
                    purchGoods.setTotalPrice(purchGoods.getPurchasePrice().multiply(new BigDecimal(purchGoods.getPurchaseNum().intValue())));
                }
                purchGoodsList.add(purchGoods);

                int purchaseNum = purchGoods.getPurchaseNum();
                // 从数据库查询一次商品做修改
                Goods goods = goodsDao.findOne(purchGoods.getGoods().getId());
                if (hasSon) {
                    // 处理替换商品
                    PurchGoods son = pg.getSon();
                    handleExchangedPurchGoods(project, goods, dbPurch, purchGoods, son);
                    purchGoodsList.add(son);
                }
                // 提交则修改商品的已采购数量
                if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
                    goods.setPurchasedNum(goods.getPurchasedNum() + purchaseNum);
                    // 设置商品的项目跟踪信息
                    setGoodsTraceData(goods, purch);
                }
                goods.setPrePurchsedNum(goods.getPrePurchsedNum() + purchaseNum - oldPurchaseNum);
                goodsDao.save(goods);
            } else {
                throw new Exception("不存在的采购商品信息");
            }
        }
        if (purchGoodsList.size() == 0) {
            throw new Exception("必须存在要采购的商品");
        }
        dbPurch.setPurchGoodsList(purchGoodsList);
        dbPurch.setProjects(projectSet);

        // 删除不关联的商品信息
        if (dbPurchGoodsMap.size() > 0) {
            Collection<PurchGoods> values = dbPurchGoodsMap.values();
            // 修改商品的预采购数量然后再删除
            for (PurchGoods pg : values) {
                Integer purchaseNum = pg.getPurchaseNum();
                Goods one = goodsDao.findOne(pg.getGoods().getId());
                if (one.getExchanged()) {
                    // 是替换后的商品，则将此商品删除，并增加父商品的合同数量
                    Goods parentOne = goodsDao.findOne(one.getParentId());
                    parentOne.setContractGoodsNum(parentOne.getContractGoodsNum() + purchaseNum);
                    goodsDao.save(parentOne);
                    goodsDao.delete(one);
                } else {
                    one.setPrePurchsedNum(one.getPrePurchsedNum() - purchaseNum);
                    goodsDao.save(one);
                }
            }
            purchGoodsDao.delete(values);
        }
        // 更新采购单
        purchDao.save(dbPurch);
        // 检查项目是否已经采购完成
        List<Integer> projectIdList = projectSet.parallelStream().map(Project::getId).collect(Collectors.toList());
        checkProjectPurchDone(projectIdList);
        return true;
    }

    // 处理新增采购信息，如果采购信息有替换的商品，则返回处理后的替换信息
    private PurchGoods handleAddNewPurchGoods(Project project, Purch purch, Goods goods, PurchGoods newPurchGoods) throws Exception {
        // 设置新采购的基本信息
        String contractNo = project.getContractNo();
        String projectNo = project.getProjectNo();
        newPurchGoods.setId(null);
        newPurchGoods.setProject(project);
        newPurchGoods.setContractNo(contractNo);
        newPurchGoods.setProjectNo(projectNo);
        newPurchGoods.setPurch(purch);
        newPurchGoods.setGoods(goods);
        Integer purchaseNum = newPurchGoods.getPurchaseNum();
        purchaseNum = purchaseNum != null && purchaseNum > 0 ? purchaseNum : 0;
        newPurchGoods.setPurchaseNum(purchaseNum);
        // 判断采购是否超限,预采购数量大于合同数量，则错误
        if (goods.getPrePurchsedNum() + purchaseNum > goods.getContractGoodsNum()) {
            throw new Exception("要采购的商品数量超合同数量【sku :" + goods.getSku() + "】");
        }
        if (purchaseNum > 0 &&
                (newPurchGoods.getPurchasePrice() == null || newPurchGoods.getPurchasePrice().compareTo(BigDecimal.ZERO) != 1)) {
            throw new Exception("要采购的商品单价错误【sku :" + goods.getSku() + "】");
        }
        newPurchGoods.setExchanged(false);
        newPurchGoods.setInspectNum(0); // 设置已报检数量
        newPurchGoods.setPreInspectNum(0); // 设置预报检数量
        newPurchGoods.setGoodNum(0); // 设置检验合格商品数量
        // 计算含税价格和不含税单价以及总价款
        String currencyBn = purch.getCurrencyBn();
        if (newPurchGoods.getPurchaseNum() > 0) {
            // 只有商品采购数量不为空才计算价格等
            if (newPurchGoods.getPurchasePrice() == null) {
                // 有采购数量，但采购价格为空，则错误
                throw new Exception("商品的采购价格错误");
            }
            // 人民币币种默认是含税价格
            if ("CNY".equals(currencyBn)) {
                // 人民币是默认含税价格
                newPurchGoods.setTaxPrice(newPurchGoods.getPurchasePrice());
                newPurchGoods.setNonTaxPrice(newPurchGoods.getPurchasePrice().divide(new BigDecimal("1.17"), 4, BigDecimal.ROUND_DOWN));
            } else {
                newPurchGoods.setNonTaxPrice(newPurchGoods.getPurchasePrice());
            }
            // 总价款
            newPurchGoods.setTotalPrice(newPurchGoods.getPurchasePrice().multiply(new BigDecimal(newPurchGoods.getPurchaseNum().intValue())));
        }
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
        Integer purchaseNum = son.getPurchaseNum();
        // 检查采购数量、采购单价
        if (purchaseNum == null || purchaseNum <= 0) {
            throw new Exception("替换商品采购数量错误");
        }
        BigDecimal purchasePrice = son.getPurchasePrice();
        if (purchasePrice == null || purchasePrice.compareTo(BigDecimal.ZERO) != 1) {
            throw new Exception("替换商品采购单价错误");
        }
        // 检查采购数量是否超合同数量
        if (purchaseNum + beforeGoods.getPrePurchsedNum() > beforeGoods.getContractGoodsNum()) {
            throw new Exception("替换商品采购超限【父SKU:" + beforeGoods.getSku() + "】");
        }
        //  插入替换后的新商品
        Goods sonGoods = son.getGoods();
        if (sonGoods == null) {
            throw new Exception("替换采购的商品信息不完整【父SKU:" + beforeGoods.getSku() + "】");
        }
        /*else if (StringUtils.isAnyBlank(sonGoods.getSku(),sonGoods.getNameEn(),sonGoods.getNameZh())) {
            throw new Exception("替换采购的商品信息不正确（sku/名称）【父SKU:" + beforeGoods.getSku() + "】");
        }*/
        sonGoods.setParentId(beforeGoods.getId());
        sonGoods.setExchanged(true);
        sonGoods.setOrder(beforeGoods.getOrder());
        sonGoods.setProject(project);
        sonGoods.setContractNo(beforeGoods.getContractNo());
        sonGoods.setProjectNo(beforeGoods.getProjectNo());
        // 设置商品合同数量，同时将父商品的合同数量减少
        sonGoods.setContractGoodsNum(purchaseNum);
        beforeGoods.setContractGoodsNum(beforeGoods.getContractGoodsNum() - purchaseNum);
        // 设置客户需求描述信息
        sonGoods.setClientDesc(beforeGoods.getClientDesc());
        // 其他字段
        sonGoods.setRequirePurchaseDate(beforeGoods.getRequirePurchaseDate());
        if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
            // 如果是提交则设置商品的已采购数量并更新
            sonGoods.setPurchasedNum(purchaseNum);
            // 完善商品的项目执行跟踪信息
            setGoodsTraceData(sonGoods, purch);
        } else {
            sonGoods.setPurchasedNum(0);
        }
        sonGoods.setPrePurchsedNum(purchaseNum);
        sonGoods.setInspectNum(0);
        sonGoods.setInstockNum(0);
        sonGoods.setOutstockApplyNum(0);
        sonGoods.setOutstockNum(0);
        sonGoods = goodsDao.save(sonGoods);

        // 处理替换后的采购信息
        son.setProject(project);
        son.setProjectNo(project.getProjectNo());
        son.setParent(beforePurchGoods);
        son.setContractNo(beforeGoods.getContractNo());
        son.setPurch(purch);
        son.setGoods(sonGoods);
        son.setExchanged(true);
        // 计算含税价格和不含税单价以及总价款
        String currencyBn = purch.getCurrencyBn();
        // 人民币默认是含税价格
        if ("CNY".equals(currencyBn)) {
            // 人民币是默认含税价格
            son.setTaxPrice(purchasePrice);
            son.setNonTaxPrice(purchasePrice.divide(new BigDecimal("1.17"), 4, BigDecimal.ROUND_DOWN));
        } else {
            son.setNonTaxPrice(purchasePrice);
        }
        // 总价款
        son.setTotalPrice(purchasePrice.multiply(new BigDecimal(purchaseNum.intValue())));
        son.setGoodNum(0);
        son.setInspectNum(0);
        son.setPreInspectNum(0);
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
