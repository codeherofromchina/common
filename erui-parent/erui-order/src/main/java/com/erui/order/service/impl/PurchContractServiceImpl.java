package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.event.PurchDoneCheckEvent;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.BackLogService;
import com.erui.order.service.PurchContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PurchContractServiceImpl implements PurchContractService {

    private static Logger logger = LoggerFactory.getLogger(PurchContractServiceImpl.class);

    @Autowired
    private PurchContractDao purchContractDao;
    @Autowired
    private AttachmentDao attachmentDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private PurchContractSimpleDao purchContractSimpleDao;
    @Autowired
    private PurchContractStandardDao purchContractStandardDao;
    @Autowired
    private PurchContractSignatoriesDao purchContractSignatoriesDao;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private PurchGoodsDao purchGoodsDao;
    @Autowired
    private PurchDao purchDao;

    @Override
    @Transactional(readOnly = true)
    public Page<PurchContract> findByPage(PurchContract condition) {
        PageRequest request = new PageRequest(condition.getPage() - 1, condition.getRows(), Sort.Direction.DESC, "id");

        Page<PurchContract> page = purchContractDao.findAll(new Specification<PurchContract>() {
            @Override
            public Predicate toPredicate(Root<PurchContract> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据采购合同号模糊查询
                if (StringUtil.isNotBlank(condition.getPurchContractNo())) {
                    list.add(cb.like(root.get("purchContractNo").as(String.class), "%" + condition.getPurchContractNo() + "%"));
                }
                // 根据采购经办人查询
                if (condition.getAgentId() != null) {
                    list.add(cb.equal(root.get("agentId").as(Integer.class), condition.getAgentId()));
                }
                //根据供应商过滤条件
                if (condition.getSupplierId() != null) {
                    list.add(cb.equal(root.get("supplierId").as(Integer.class), condition.getSupplierId()));
                }
                //根据供应商过滤条件
                if (condition.getSupplierId() != null) {
                    list.add(cb.equal(root.get("supplierId").as(Integer.class), condition.getSupplierId()));
                }

                PurchContract.StatusEnum statusEnum = PurchContract.StatusEnum.fromCode(condition.getStatus());
                if (statusEnum != null) {
                    list.add(cb.equal(root.get("status").as(Integer.class), statusEnum.getCode()));
                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                Predicate and = cb.and(predicates);
                return and;
            }
        }, request);

        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean update(PurchContract purchContract) throws Exception {
        PurchContract dbPurchContract = findDetailInfo(purchContract.getId());
        // 之前的采购必须不能为空且未提交状态
        if (dbPurchContract == null || dbPurchContract.getStatus() == PurchContract.StatusEnum.DELETE.getCode()) {
            throw new Exception(String.format("%s%s%s", "采购信息不存在", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Procurement information does not exist"));
        } else if (dbPurchContract.getStatus() != PurchContract.StatusEnum.BEING.getCode()) {
            throw new Exception(String.format("%s%s%s", "采购信息已提交不能修改", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Purchase information has been submitted and can not be modified"));
        }
        final Date now = new Date();
        // 设置基本信息
        dbPurchContract.setBaseInfo(purchContract);
        dbPurchContract.setUpdateTime(now);
        // 处理商品
        List<PurchGoods> purchGoodsList = new ArrayList<>(); // 声明最终采购商品容器
        Set<Project> projectSet = new HashSet<>(); // 声明项目的容器
        // 数据库现在的采购商品信息
        Map<Integer, PurchGoods> dbPurchGoodsMap = dbPurchContract.getPurchGoodsList().parallelStream().collect(Collectors.toMap(PurchGoods::getId, vo -> vo));
        Set<Integer> existId = new HashSet<>();
        // 处理参数中的采购商品信息
        for (PurchGoods pg : purchContract.getPurchGoodsList()) {
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
                    throw new Exception(String.format("%s%s%s", "商品不存在", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Goods do not exist"));
                }
                Project project = goods.getProject();
                // 必须是已创建采购申请单并未完成采购的项目
                if (Project.PurchReqCreateEnum.valueOfCode(project.getPurchReqCreate()) != Project.PurchReqCreateEnum.SUBMITED) {
                    throw new Exception(String.format("%s%s%s", "项目必须提交采购申请", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The project must submit a purchase application"));
                }
                if (project.getPurchDone()) {
                    throw new Exception(String.format("%s%s%s", "项目采购已完成，不能再次采购", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Project procurement has been completed and can not be repurchased"));
                }
                projectSet.add(project);
                // 查看是否存在替换商品
                PurchGoods son = handleAddNewPurchGoods(project, dbPurchContract, goods, pg);
                purchGoodsList.add(pg);
                if (son != null) {
                    purchGoodsList.add(son);
                }
                Integer intPurchaseNum = pg.getPurchaseNum();
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
                        PurchGoods sonPurchGoods = purchGoodsDao.findByPurchIdAndParentId(dbPurchContract.getId(), pgId);
                        if (sonPurchGoods == null) {
                            continue;
                        } else {
                            // 查找参数中的替换后商品
                            PurchGoods paramSonPurchGoods = purchContract.getPurchGoodsList().parallelStream().filter(vo -> {
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
                        PurchGoods paramParentPurchGoods = purchContract.getPurchGoodsList().parallelStream().filter(vo -> vo.getId().intValue() == pId).findFirst().get();
                        if (paramParentPurchGoods == null) {
                            throw new Exception(String.format("%s%s%s", "父采购商品信息不存在", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The parent purchase commodity information does not exist"));
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
                String currencyBn = purchContract.getCurrencyBn();
                if (purchGoods.getPurchaseNum() > 0) {
                    if (purchGoods.getPurchasePrice() == null || purchGoods.getPurchasePrice().compareTo(BigDecimal.ZERO) != 1) {
                        throw new Exception(String.format("%s%s%s", "商品的采购价格错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Purchase price error of goods"));
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
                    handleExchangedPurchGoods(project, goods, dbPurchContract, purchGoods, son);
                    purchGoodsList.add(son);
                }
                // 提交则修改商品的已采购数量
                if (purchContract.getStatus() == PurchContract.StatusEnum.BEING.getCode()) {
                    goods.setPurchasedNum(goods.getPurchasedNum() + purchaseNum);
                }
                // 判断采购是否超限,预采购数量大于合同数量，则错误
                if (goods.getPrePurchsedNum() + purchaseNum - oldPurchaseNum > goods.getContractGoodsNum()) {
                    throw new Exception(String.format("%s%s%s", "采购数量超过合同数量【sku :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Quantity of purchase exceeds the number of contracts [SKU: " + goods.getSku() + "]"));

                }
                if (purchaseNum > 0 &&
                        (purchGoods.getPurchasePrice() == null || purchGoods.getPurchasePrice().compareTo(BigDecimal.ZERO) != 1)) {
                    throw new Exception(String.format("%s%s%s", "要采购的商品单价错误【sku :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Unit price error to be purchased [SKU: " + goods.getSku() + "]"));

                }

                goods.setPrePurchsedNum(goods.getPrePurchsedNum() + purchaseNum - oldPurchaseNum);
                goodsDao.save(goods);
            } else {
                throw new Exception(String.format("%s%s%s", "不存在的采购商品信息", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Non existent procurement of commodity information"));

            }
        }
        if (purchGoodsList.size() == 0) {
            throw new Exception(String.format("%s%s%s", "必须存在要采购的商品", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "There must be goods to be purchased"));

        }
        dbPurchContract.setPurchGoodsList(purchGoodsList);

        // 删除不关联的商品信息
        if (dbPurchGoodsMap.size() > 0) {
            Collection<PurchGoods> values = dbPurchGoodsMap.values();
            // 修改商品的预采购数量然后再删除
            List<Goods> deleteGoods = new ArrayList<>();
            for (PurchGoods pg : values) {
                Integer purchaseNum = pg.getPurchaseNum();
                Goods one = goodsDao.findOne(pg.getGoods().getId());
                if (one.getExchanged()) {
                    // 是替换后的商品，则将此商品删除，并增加父商品的合同数量
                    Goods parentOne = goodsDao.findOne(one.getParentId());
                    parentOne.setContractGoodsNum(parentOne.getContractGoodsNum() + purchaseNum);
                    goodsDao.save(parentOne);
                    //goodsDao.delete(one);
                    deleteGoods.add(one);
                } else {
                    one.setPrePurchsedNum(one.getPrePurchsedNum() - purchaseNum);
                    goodsDao.save(one);
                }
            }
            purchGoodsDao.delete(values);
            if (deleteGoods.size() > 0) {
                // 后删除商品
                goodsDao.delete(deleteGoods);
            }
        }

        PurchContract save = purchContractDao.save(dbPurchContract);
        // 处理附件信息 attachmentList 库里存在附件列表 dbAttahmentsMap前端传来参数附件列表
        List<Attachment> attachmentList = new ArrayList<>();
        if (purchContract.getAttachments() != null) {
            attachmentList = purchContract.getAttachments();
        }
        Map<Integer, Attachment> dbAttahmentsMap = dbPurchContract.getAttachments().parallelStream().collect(Collectors.toMap(Attachment::getId, vo -> vo));
        attachmentService.updateAttachments(attachmentList, dbAttahmentsMap, dbPurchContract.getId(), Attachment.AttachmentCategory.PURCH.getCode());

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean insert(PurchContract purchContract) throws Exception {
        Date now = new Date();
        String lastedByPurchNo = purchContractDao.findLastedByPurchContractNo();
        if(lastedByPurchNo == null){
            lastedByPurchNo = purchDao.findLastedByPurchNo();
        }
        Long count = purchContractDao.findCountByPurchContractNo(lastedByPurchNo);
        if (count != null && count > 1) {
            throw new Exception(String.format("%s%s%s", "采购合同号重复", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Repeat purchase contract number"));
        }
        // 设置基础数据 自动生成采购合同号
        purchContract.setPurchContractNo(StringUtil.genPurchNo(lastedByPurchNo));
        purchContract.setSigningDate(NewDateUtil.getDate(purchContract.getSigningDate()));
        purchContract.setCreateTime(now);
        // 处理商品信息
        List<PurchGoods> purchGoodsList = new ArrayList<>();
        Set<Project> projectSet = new HashSet<>();
        for (PurchGoods purchGoods : purchContract.getPurchGoodsList()) {
            // 获取要采购的商品
            Goods goods = goodsDao.findOne(purchGoods.getgId());
            if (goods == null || goods.getExchanged()) {
                // 给定的商品不存在或者是被替换的商品，则错误
                throw new Exception(String.format("%s%s%s", "商品不存在", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Goods do not exist"));
            }
            Project project = goods.getProject();
            // 必须是已创建采购申请单并未完成采购的项目
            if (Project.PurchReqCreateEnum.valueOfCode(project.getPurchReqCreate()) != Project.PurchReqCreateEnum.SUBMITED) {
                throw new Exception(String.format("%s%s%s", "项目必须提交采购申请", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The project must submit a purchase application"));
            }
            if (project.getPurchDone()) {
                throw new Exception(String.format("%s%s%s", "项目采购已完成，不能再次采购", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Project procurement has been completed and can not be repurchased"));
            }
            projectSet.add(project);
            PurchGoods son = handleAddNewPurchGoods(project, purchContract, goods, purchGoods);
            purchGoodsList.add(purchGoods);
            if (son != null) {
                purchGoodsList.add(son);
            }
            int intPurchaseNum = purchGoods.getPurchaseNum();
            if (purchContract.getStatus() == PurchContract.StatusEnum.BEING.getCode()) {
                // 如果是提交则设置商品的已采购数量并更新
                goods.setPurchasedNum(goods.getPurchasedNum() + intPurchaseNum);
            }
            // 增加预采购数量
            goods.setPrePurchsedNum(goods.getPrePurchsedNum() + intPurchaseNum);
            // 直接更新商品，放置循环中存在多次修改同一个商品错误
            goodsDao.save(goods);

        }
        if (purchGoodsList.size() == 0) {
            throw new Exception(String.format("%s%s%s", "必须存在要采购的商品", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "There must be goods to be purchased"));
        }
        purchContract.setPurchGoodsList(purchGoodsList);
        PurchContract save = purchContractDao.save(purchContract);
        // 添加简易合同信息
        if(purchContract.getPurchContractSimple() != null){
            PurchContractSimple purchContractSimple = purchContract.getPurchContractSimple();
            purchContractSimple.setPurchContract(save);
            purchContractSimpleDao.save(purchContractSimple);
        }
        // 添加标准合同信息
        if(purchContract.getPurchContractStandard() != null){
            PurchContractStandard purchContractStandard = purchContract.getPurchContractStandard();
            purchContractStandard.setPurchContract(save);
            purchContractStandardDao.save(purchContractStandard);
        }
        // 添加附件
        if (purchContract.getAttachments() != null && purchContract.getAttachments().size() > 0) {
            attachmentService.addAttachments(purchContract.getAttachments(), save.getId(), Attachment.AttachmentCategory.PURCHCONTRACT.getCode());
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public PurchContract findDetailInfo(Integer id) {
        if(id != null && id >0){
            PurchContract purchContract = purchContractDao.findOne(id);
            if(purchContract != null){
                if(purchContract.getType() == 3){// 获取非标采购合同的附件信息
                    List<Attachment> attachments = attachmentDao.findByRelObjIdAndCategory(purchContract.getId(), Attachment.AttachmentCategory.PURCHCONTRACT.getCode());
                    if (attachments != null && attachments.size() > 0) {
                        purchContract.setAttachments(attachments);
                        purchContract.getAttachments().size();
                    }
                }
                List<PurchGoods> purchGoodsList = purchContract.getPurchGoodsList();
                if (purchGoodsList.size() > 0) {
                    for (PurchGoods purchGoods : purchGoodsList) {
                        purchGoods.getGoods().setPurchGoods(null);
                    }
                }
                return  purchContract;
            }
        }
        return null;
    }

    // 处理新增采购信息，如果采购信息有替换的商品，则返回处理后的替换信息
    private PurchGoods handleAddNewPurchGoods(Project project, PurchContract purchContract, Goods goods, PurchGoods newPurchGoods) throws Exception {
        // 设置新采购的基本信息
        String contractNo = project.getContractNo();
        String projectNo = project.getProjectNo();
        newPurchGoods.setId(null);
        newPurchGoods.setProject(project);
        newPurchGoods.setContractNo(contractNo);
        newPurchGoods.setProjectNo(projectNo);
        newPurchGoods.setPurchContract(purchContract);
        newPurchGoods.setGoods(goods);
        Integer purchaseNum = newPurchGoods.getPurchaseNum();
        purchaseNum = purchaseNum != null && purchaseNum > 0 ? purchaseNum : 0;
        newPurchGoods.setPurchaseNum(purchaseNum);
        // 判断采购是否超限,预采购数量大于合同数量，则错误
        if (goods.getPrePurchsedNum() + purchaseNum > goods.getContractGoodsNum()) {
            throw new Exception(String.format("%s%s%s", "采购数量超过合同数量【sk" +
                    "u :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Quantity of purchase exceeds the number of contracts [SKU: " + goods.getSku() + "]"));

        }
        if (purchaseNum > 0 &&
                (newPurchGoods.getPurchasePrice() == null || newPurchGoods.getPurchasePrice().compareTo(BigDecimal.ZERO) != 1)) {
            throw new Exception(String.format("%s%s%s", "要采购的商品单价错误【sku :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Unit price error to be purchased [SKU: " + goods.getSku() + "]"));

        }
        newPurchGoods.setExchanged(false);
        newPurchGoods.setInspectNum(0); // 设置已报检数量
        newPurchGoods.setPreInspectNum(0); // 设置预报检数量
        newPurchGoods.setGoodNum(0); // 设置检验合格商品数量
        // 计算含税价格和不含税单价以及总价款
        String currencyBn = purchContract.getCurrencyBn();
        if (newPurchGoods.getPurchaseNum() > 0) {
            // 只有商品采购数量不为空才计算价格等
            if (newPurchGoods.getPurchasePrice() == null) {
                // 有采购数量，但采购价格为空，则错误
                throw new Exception(String.format("%s%s%s", "商品的采购价格错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Purchase price error of goods"));
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
            handleExchangedPurchGoods(project, goods, purchContract, newPurchGoods, son);
        }
        return son;
    }



    /**
     * 处理替换后的商品信息
     */
    private void handleExchangedPurchGoods(Project project, Goods beforeGoods, PurchContract purchContract, PurchGoods beforePurchGoods, PurchGoods son) throws Exception {
        Integer purchaseNum = son.getPurchaseNum();
        // 检查采购数量、采购单价
        if (purchaseNum == null || purchaseNum <= 0) {
            throw new Exception(String.format("%s%s%s", "替换商品采购数量错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Replacement of commodity purchase quantity error"));
        }
        BigDecimal purchasePrice = son.getPurchasePrice();
        if (purchasePrice == null || purchasePrice.compareTo(BigDecimal.ZERO) != 1) {
            throw new Exception(String.format("%s%s%s", "替换商品采购单价错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Replacement of commodity purchase unit price error"));
        }
        // 检查采购数量是否超合同数量
        if (purchaseNum + beforeGoods.getPrePurchsedNum() > beforeGoods.getContractGoodsNum()) {
            throw new Exception(String.format("%s%s%s", "替换商品采购超限【父SKU:" + beforeGoods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Replacement of commodity purchase overrun [father SKU:" + beforeGoods.getSku() + "]"));
        }
        //  插入替换后的新商品
        Goods sonGoods = son.getGoods();
        if (sonGoods == null) {
            throw new Exception(String.format("%s%s%s", "替换采购的商品信息不完整【父SKU:" + beforeGoods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Replacement purchase information is incomplete (parent SKU:" + beforeGoods.getSku() + ")"));
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
        if (purchContract.getStatus() == PurchContract.StatusEnum.BEING.getCode()) {
            // 如果是提交则设置商品的已采购数量并更新
            sonGoods.setPurchasedNum(purchaseNum);
            // 完善商品的项目执行跟踪信息
//            setGoodsTraceData(sonGoods, purchContract);
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
        son.setPurchContract(purchContract);
        son.setGoods(sonGoods);
        son.setExchanged(true);
        // 计算含税价格和不含税单价以及总价款
        String currencyBn = purchContract.getCurrencyBn();
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
}
