package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.PurchContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
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
    private AttachmentService attachmentService;
    @Autowired
    private PurchContractGoodsDao purchContractGoodsDao;
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
                // 根据供应商过滤条件
                if (condition.getSupplierId() != null) {
                    list.add(cb.equal(root.get("supplierId").as(Integer.class), condition.getSupplierId()));
                }
                // 根据合同类型过滤条件
                if (condition.getType() != null) {
                    list.add(cb.equal(root.get("type").as(Integer.class), condition.getType()));
                }
                // 根据采购状态过滤条件
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
        } else if (dbPurchContract.getStatus() != PurchContract.StatusEnum.READY.getCode()) {
            throw new Exception(String.format("%s%s%s", "采购信息已提交不能修改", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Purchase information has been submitted and can not be modified"));
        }
        final Date now = new Date();
        // 设置基本信息
        dbPurchContract.setBaseInfo(purchContract);

        if(dbPurchContract.getPurchContractSimple() != null){//简易合同
            purchContract.getPurchContractSimple().setId(dbPurchContract.getPurchContractSimple().getId());
            purchContract.getPurchContractSimple().setUpdateTime(now);
            dbPurchContract.setPurchContractSimple(purchContract.getPurchContractSimple());
        }
        if(dbPurchContract.getPurchContractStandard() != null){//标准合同
            purchContract.getPurchContractStandard().setId(dbPurchContract.getPurchContractStandard().getId());
            purchContract.getPurchContractStandard().setUpdateTime(now);
            dbPurchContract.setPurchContractStandard(purchContract.getPurchContractStandard());
        }
        if(dbPurchContract.getPurchContractSignatoriesList() != null && purchContract.getPurchContractSignatoriesList() != null){//合同双方信息
            for(PurchContractSignatories dbpcs : dbPurchContract.getPurchContractSignatoriesList()){
                for(PurchContractSignatories pcs : purchContract.getPurchContractSignatoriesList()){
                    if(dbpcs.getType() == pcs.getType()){
                        pcs.setId(dbpcs.getId());
                        pcs.setUpdateTime(now);
                    }
                }
            }
            dbPurchContract.setPurchContractSignatoriesList(purchContract.getPurchContractSignatoriesList());
        }
        if(dbPurchContract.getPurchContractGoodsList() != null && purchContract.getPurchContractGoodsList() != null){//合同商品信息
            for(PurchContractGoods dbpgs : dbPurchContract.getPurchContractGoodsList()){
                for(PurchContractGoods pgs : purchContract.getPurchContractGoodsList()){
                    if(dbpgs.getGoods().getId() == pgs.getgId()){
                        pgs.setId(dbpgs.getId());
                        pgs.setUpdateTime(now);
                    }else{
                        if(pgs.getCreateTime() == null){
                            pgs.setCreateTime(now);
                        }
                    }
                }
            }
        }

        dbPurchContract.setUpdateTime(now);
        // 处理商品
        List<PurchContractGoods> purchContractGoodsList = new ArrayList<>(); // 声明最终采购商品容器
        Set<Project> projectSet = new HashSet<>(); // 声明项目的容器
        // 数据库现在的采购商品信息
        Map<Integer, PurchContractGoods> dbPurchContractGoodsMap = dbPurchContract.getPurchContractGoodsList().parallelStream().collect(Collectors.toMap(PurchContractGoods::getId, vo -> vo));
        Set<Integer> existId = new HashSet<>();
        // 处理参数中的采购商品信息
        for (PurchContractGoods pg : purchContract.getPurchContractGoodsList()) {
            Integer pgId = pg.getId();
            if (pgId == null) { // 新增加的采购商品信息
                // 检查是否传入采购数量或者替换商品
                Integer purchaseNum = pg.getPurchaseNum(); // 获取采购数量
                PurchContractGoods tSon = pg.getSon(); // 获取替换商品
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
                PurchContractGoods son = handleAddNewPurchContractGoods(project, dbPurchContract, goods, pg);
                purchContractGoodsList.add(pg);
                if (son != null) {
                    purchContractGoodsList.add(son);
                }
                Integer intPurchaseNum = pg.getPurchaseNum();
                goods.setPrePurchsedNum(goods.getPrePurchsedNum() + intPurchaseNum);
                goodsDao.save(goods);
            } else if (dbPurchContractGoodsMap.containsKey(pgId)) {
                Integer paramPurchaseNum = pg.getPurchaseNum();
                // 验证是删除还是修改，传入的商品采购数量0也作为删除
                if (paramPurchaseNum == null || paramPurchaseNum <= 0) {
                    PurchContractGoods purchContractGoods = dbPurchContractGoodsMap.get(pgId);
                    if (purchContractGoods.getExchanged()) {
                        // 是替换后的商品，则直接删除（跳过处理后在循环外删除）
                        continue;
                    } else if (pg.getSon() == null) { // 不是替换商品，查看是否存在替换后商品，如果不存在，则删除
                        PurchContractGoods sonPurchContractGoods = purchContractGoodsDao.findByPurchContractIdAndParentId(dbPurchContract.getId(), pgId);
                        if (sonPurchContractGoods == null) {
                            continue;
                        } else {
                            // 查找参数中的替换后商品
                            PurchContractGoods paramSonPurchContractGoods = purchContract.getPurchContractGoodsList().parallelStream().filter(vo -> {
                                Integer id = vo.getId();
                                if (id != null && id.intValue() == sonPurchContractGoods.getId()) {
                                    return true;
                                }
                                return false;
                            }).findFirst().get();
                            if (paramSonPurchContractGoods == null) {
                                continue;
                            }
                            Integer paramSonPurchaseNum = paramSonPurchContractGoods.getPurchaseNum();
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
                PurchContractGoods purchContractGoods = dbPurchContractGoodsMap.remove(pgId);
                existId.add(pgId);
                Project project = purchContractGoods.getProject();

                boolean hasSon = false;
                if (purchContractGoods.getExchanged()) {
                    // 是替换商品，查看父商品是否存在
                    PurchContractGoods parentPurchContractGoods = purchContractGoods.getParent();
                    Integer pId = parentPurchContractGoods.getId();
                    if (!existId.contains(pId)) {
                        // 查找替换前的商品先处理
                        PurchContractGoods paramParentPurchContractGoods = purchContract.getPurchContractGoodsList().parallelStream().filter(vo -> vo.getId().intValue() == pId).findFirst().get();
                        if (paramParentPurchContractGoods == null) {
                            throw new Exception(String.format("%s%s%s", "父采购商品信息不存在", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The parent purchase commodity information does not exist"));
                        }
                    }
                } else {
                    // 不是替换商品，查看是否添加了替换商品
                    hasSon = pg.getSon() != null;
                }
                // 正常添加
                projectSet.add(project);
                int oldPurchaseNum = purchContractGoods.getPurchaseNum();
                purchContractGoods.setPurchaseNum(pg.getPurchaseNum() == null && pg.getPurchaseNum() < 0 ? 0 : pg.getPurchaseNum()); // 采购商品数量
                purchContractGoods.setPurchasePrice(pg.getPurchasePrice()); // 采购单价
                purchContractGoods.setPurchaseTotalPrice(pg.getPurchaseTotalPrice()); //  采购总金额
                purchContractGoods.setPurchaseRemark(pg.getPurchaseRemark()); // 采购说明
                // 计算含税价格和不含税单价以及总价款
                String currencyBn = purchContract.getCurrencyBn();
                if (purchContractGoods.getPurchaseNum() > 0) {
                    if (purchContractGoods.getPurchasePrice() == null || purchContractGoods.getPurchasePrice().compareTo(BigDecimal.ZERO) != 1) {
                        throw new Exception(String.format("%s%s%s", "商品的采购价格错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Purchase price error of goods"));
                    }
                    if ("CNY".equals(currencyBn)) {
                        // 人民币是默认含税价格
                        purchContractGoods.setTaxPrice(purchContractGoods.getPurchasePrice());
                        purchContractGoods.setNonTaxPrice(purchContractGoods.getPurchasePrice().divide(new BigDecimal("1.17"), 4, BigDecimal.ROUND_DOWN));
                    } else {
                        purchContractGoods.setNonTaxPrice(purchContractGoods.getPurchasePrice());
                    }
                    // 总价款
                    purchContractGoods.setTotalPrice(purchContractGoods.getPurchasePrice().multiply(new BigDecimal(purchContractGoods.getPurchaseNum().intValue())));
                }
                purchContractGoodsList.add(purchContractGoods);

                int purchaseNum = purchContractGoods.getPurchaseNum();
                // 从数据库查询一次商品做修改
                Goods goods = goodsDao.findOne(purchContractGoods.getGoods().getId());
                if (hasSon) {
                    // 处理替换商品
                    PurchContractGoods son = pg.getSon();
                    handleExchangedPurchContractGoods(project, goods, dbPurchContract, purchContractGoods, son);
                    purchContractGoodsList.add(son);
                }
                // 提交则修改商品的已采购数量
                if (purchContract.getStatus() == PurchContract.StatusEnum.BEING.getCode()) {
                    goods.setPurchasedNum(goods.getPurchasedNum() + purchaseNum);
                }
                // 判断采购是否超限,预采购数量大于合同数量，则错误
                if (goods.getPrePurchsedNum() + purchaseNum - oldPurchaseNum > goods.getContractGoodsNum()) {
                    throw new Exception(String.format("%s%s%s", "采购数量超过合同数量【sku :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Quantity of purchase exceeds the number of contracts [SKU: " + goods.getSku() + "]"));

                }
                if (purchaseNum > 0 && (purchContractGoods.getPurchasePrice() == null || purchContractGoods.getPurchasePrice().compareTo(BigDecimal.ZERO) != 1)) {
                    throw new Exception(String.format("%s%s%s", "要采购的商品单价错误【sku :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Unit price error to be purchased [SKU: " + goods.getSku() + "]"));

                }

                goods.setPrePurchsedNum(goods.getPrePurchsedNum() + purchaseNum - oldPurchaseNum);
                goodsDao.save(goods);
            } else {
                throw new Exception(String.format("%s%s%s", "不存在的采购商品信息", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Non existent procurement of commodity information"));

            }
        }
        if (purchContractGoodsList.size() == 0) {
            throw new Exception(String.format("%s%s%s", "必须存在要采购的商品", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "There must be goods to be purchased"));

        }
        dbPurchContract.setPurchContractGoodsList(purchContractGoodsList);

        // 删除不关联的商品信息
        if (dbPurchContractGoodsMap.size() > 0) {
            Collection<PurchContractGoods> values = dbPurchContractGoodsMap.values();
            // 修改商品的预采购数量然后再删除
            List<Goods> deleteGoods = new ArrayList<>();
            for (PurchContractGoods pg : values) {
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
            purchContractGoodsDao.delete(values);
            if (deleteGoods.size() > 0) {
                // 后删除商品
                goodsDao.delete(deleteGoods);
            }
        }

        PurchContract save = purchContractDao.save(dbPurchContract);
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
        // 处理采购合同双方信息
        for (PurchContractSignatories purchContractSignatories : purchContract.getPurchContractSignatoriesList()) {
            purchContractSignatories.setCreateTime(now);
        }
        // 处理商品信息
        List<PurchContractGoods> purchContractGoodsList = new ArrayList<>();
        Set<Project> projectSet = new HashSet<>();
        for (PurchContractGoods purchContractGoods : purchContract.getPurchContractGoodsList()) {
            // 获取要采购的商品
            Goods goods = goodsDao.findOne(purchContractGoods.getgId());
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
            PurchContractGoods son = handleAddNewPurchContractGoods(project, purchContract, goods, purchContractGoods);
            purchContractGoodsList.add(purchContractGoods);
            if (son != null) {
                purchContractGoodsList.add(son);
            }
            int intPurchaseNum = purchContractGoods.getPurchaseNum();
            if (purchContract.getStatus() == PurchContract.StatusEnum.BEING.getCode()) {
                // 如果是提交则设置商品的已采购数量并更新
                goods.setPurchasedNum(goods.getPurchasedNum() + intPurchaseNum);
            }
            // 增加预采购数量
            goods.setPrePurchsedNum(goods.getPrePurchsedNum() + intPurchaseNum);
            // 直接更新商品，放置循环中存在多次修改同一个商品错误
            goodsDao.save(goods);

        }
        if (purchContractGoodsList.size() == 0) {
            throw new Exception(String.format("%s%s%s", "必须存在要采购的商品", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "There must be goods to be purchased"));
        }
        purchContract.setPurchContractGoodsList(purchContractGoodsList);
        PurchContract save = purchContractDao.save(purchContract);
        // 添加简易合同信息
        if(purchContract.getPurchContractSimple() != null){
            PurchContractSimple purchContractSimple = purchContract.getPurchContractSimple();
            purchContractSimple.setPurchContract(save);
            purchContractSimple.setCreateTime(now);
            purchContractSimpleDao.save(purchContractSimple);
        }
        // 添加标准合同信息
        if(purchContract.getPurchContractStandard() != null){
            PurchContractStandard purchContractStandard = purchContract.getPurchContractStandard();
            purchContractStandard.setPurchContract(save);
            purchContractStandard.setCreateTime(now);
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
                List<PurchContractGoods> purchContractGoodsList = purchContract.getPurchContractGoodsList();
                if (purchContractGoodsList.size() > 0) {
                    for (PurchContractGoods purchContractGoods : purchContractGoodsList) {
                        purchContract.setProjectId(purchContractGoods.getProject().getId().toString());
                        purchContractGoods.setgId(purchContractGoods.getGoods().getId());
                        purchContractGoods.setPcId(purchContract.getId());
                        purchContractGoods.setPcgId(purchContractGoods.getId());

                    }
                }
                return  purchContract;
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Map<String, Object>> purchAbleByPage(String agentId, int pageNum, int pageSizeString, String purchContractNo, Integer supplierId, Integer type) throws Exception {

        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSizeString, new Sort(Sort.Direction.DESC, "id"));
        Page<Map<String, Object>> result = null;
        Page<PurchContract> pageList = purchContractDao.findAll(new Specification<PurchContract>() {
            @Override
            public Predicate toPredicate(Root<PurchContract> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据合同号过滤
                if(purchContractNo != null){
                    list.add(cb.like(root.get("purchContractNo").as(String.class), "%" + purchContractNo + "%"));
                }
                // 根据供应商过滤条件
                if(supplierId != null){
                    list.add(cb.equal(root.get("supplierId").as(Integer.class), supplierId));
                }
                // 根据合同类型查询
                if(type != null){
                    list.add(cb.equal(root.get("type").as(Integer.class), type));
                }
                // 根据采购经办人查询
                if(agentId != null){
                    list.add(cb.equal(root.get("agentId").as(Integer.class), agentId));
                }

                // 根据采购状态过滤条件
                Predicate status01 = cb.equal(root.get("status").as(Integer.class), PurchContract.StatusEnum.BEING.getCode());
                Predicate status02 = cb.equal(root.get("status").as(Integer.class), PurchContract.StatusEnum.EXECUTED.getCode());
                list.add(cb.or(status01, status02));

                // 根据商品未采购完成的
                Join<PurchContract, PurchContractGoods> goodsJoin = root.join("purchContractGoodsList");
                list.add(cb.lt(goodsJoin.get("prePurchContractNum").as(Integer.class), goodsJoin.get("purchaseNum").as(Integer.class)));

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, pageRequest);

        List<Map<String, Object>> list = new ArrayList<>();
        for (PurchContract purchContract : pageList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", purchContract.getId());
            map.put("purchContractNo", purchContract.getPurchContractNo());
            map.put("supplierName", purchContract.getSupplierName());
            map.put("type", purchContract.getType());
            list.add(map);
        }
        result = new PageImpl<Map<String, Object>>(list, pageRequest, pageList.getTotalElements());

        return result;
    }

    // 处理新增采购信息，如果采购信息有替换的商品，则返回处理后的替换信息
    private PurchContractGoods handleAddNewPurchContractGoods(Project project, PurchContract purchContract, Goods goods, PurchContractGoods newPurchContractGoods) throws Exception {
        // 设置新采购的基本信息
        String contractNo = project.getContractNo();
        String projectNo = project.getProjectNo();
        newPurchContractGoods.setId(null);
        newPurchContractGoods.setProject(project);
        newPurchContractGoods.setContractNo(contractNo);
        newPurchContractGoods.setProjectNo(projectNo);
        newPurchContractGoods.setPurchContract(purchContract);
        newPurchContractGoods.setGoods(goods);
        Integer purchaseNum = newPurchContractGoods.getPurchaseNum();
        purchaseNum = purchaseNum != null && purchaseNum > 0 ? purchaseNum : 0;
        newPurchContractGoods.setPurchaseNum(purchaseNum);
        // 判断采购是否超限,预采购数量大于合同数量，则错误
        if (goods.getPrePurchsedNum() + purchaseNum > goods.getContractGoodsNum()) {
            throw new Exception(String.format("%s%s%s", "采购数量超过合同数量【sk" +
                    "u :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Quantity of purchase exceeds the number of contracts [SKU: " + goods.getSku() + "]"));

        }
        if (purchaseNum > 0 &&
                (newPurchContractGoods.getPurchasePrice() == null || newPurchContractGoods.getPurchasePrice().compareTo(BigDecimal.ZERO) != 1)) {
            throw new Exception(String.format("%s%s%s", "要采购的商品单价错误【sku :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Unit price error to be purchased [SKU: " + goods.getSku() + "]"));

        }
        newPurchContractGoods.setExchanged(false);
        // 计算含税价格和不含税单价以及总价款
        String currencyBn = purchContract.getCurrencyBn();
        if (newPurchContractGoods.getPurchaseNum() > 0) {
            // 只有商品采购数量不为空才计算价格等
            if (newPurchContractGoods.getPurchasePrice() == null) {
                // 有采购数量，但采购价格为空，则错误
                throw new Exception(String.format("%s%s%s", "商品的采购价格错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Purchase price error of goods"));
            }
            // 人民币币种默认是含税价格
            if ("CNY".equals(currencyBn)) {
                // 人民币是默认含税价格
                newPurchContractGoods.setTaxPrice(newPurchContractGoods.getPurchasePrice());
                newPurchContractGoods.setNonTaxPrice(newPurchContractGoods.getPurchasePrice().divide(new BigDecimal("1.17"), 4, BigDecimal.ROUND_DOWN));
            } else {
                newPurchContractGoods.setNonTaxPrice(newPurchContractGoods.getPurchasePrice());
            }
            // 总价款
            newPurchContractGoods.setTotalPrice(newPurchContractGoods.getPurchasePrice().multiply(new BigDecimal(newPurchContractGoods.getPurchaseNum().intValue())));
        }
        newPurchContractGoods.setCreateTime(new Date());

        // 查看是否存在替换商品
        PurchContractGoods son = newPurchContractGoods.getSon();
        if (son != null) {
            // 处理替换商品
            handleExchangedPurchContractGoods(project, goods, purchContract, newPurchContractGoods, son);
        }
        return son;
    }



    /**
     * 处理替换后的商品信息
     */
    private void handleExchangedPurchContractGoods(Project project, Goods beforeGoods, PurchContract purchContract, PurchContractGoods beforePurchContractGoods, PurchContractGoods son) throws Exception {
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
        son.setParent(beforePurchContractGoods);
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
        son.setCreateTime(new Date());
    }
}
