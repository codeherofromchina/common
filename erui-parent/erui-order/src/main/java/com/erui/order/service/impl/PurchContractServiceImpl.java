package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.PurchContractService;
import com.erui.order.util.excel.ExcelUploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    public boolean updateStatus(PurchContract purchContract) {
        purchContract.setStatus(4);
        purchContractDao.save(purchContract);
        return true;
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

        if (dbPurchContract.getPurchContractSimple() != null) {//简易合同
            purchContract.getPurchContractSimple().setId(dbPurchContract.getPurchContractSimple().getId());
            purchContract.getPurchContractSimple().setUpdateTime(now);
            dbPurchContract.setPurchContractSimple(purchContract.getPurchContractSimple());
        }
        if (dbPurchContract.getPurchContractStandard() != null) {//标准合同
            purchContract.getPurchContractStandard().setId(dbPurchContract.getPurchContractStandard().getId());
            purchContract.getPurchContractStandard().setUpdateTime(now);
            dbPurchContract.setPurchContractStandard(purchContract.getPurchContractStandard());
        }
        if (dbPurchContract.getPurchContractSignatoriesList() != null && purchContract.getPurchContractSignatoriesList() != null) {//合同双方信息
            for (PurchContractSignatories dbpcs : dbPurchContract.getPurchContractSignatoriesList()) {
                for (PurchContractSignatories pcs : purchContract.getPurchContractSignatoriesList()) {
                    if (dbpcs.getType() == pcs.getType()) {
                        pcs.setId(dbpcs.getId());
                        pcs.setUpdateTime(now);
                    }
                }
            }
            dbPurchContract.setPurchContractSignatoriesList(purchContract.getPurchContractSignatoriesList());
        }
        if (dbPurchContract.getPurchContractGoodsList() != null && purchContract.getPurchContractGoodsList() != null) {//合同商品信息
            for (PurchContractGoods dbpgs : dbPurchContract.getPurchContractGoodsList()) {
                for (PurchContractGoods pgs : purchContract.getPurchContractGoodsList()) {
                    if (dbpgs.getGoods().getId() - pgs.getgId() == 0) {
                        pgs.setId(dbpgs.getId());
                    }
                }
                dbpgs.setUpdateTime(now);
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
                pg.setCreateTime(now);
                purchContractGoodsList.add(pg);
                if (son != null) {
                    purchContractGoodsList.add(son);
                }
                Integer intPurchaseNum = pg.getPurchaseNum();
                goods.setPrePurchsedNum(goods.getPrePurchsedNum() + intPurchaseNum);
                goodsDao.save(goods);
            } else if (dbPurchContractGoodsMap.containsKey(pgId)) {
                Integer paramPurchaseNum = pg.getPurchaseNum();
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
        if (purchContract.getPurchContractSimple() != null) {
            PurchContractSimple purchContractSimple = purchContract.getPurchContractSimple();
            purchContractSimple.setPurchContract(save);
            purchContractSimpleDao.save(purchContractSimple);
        }
        // 添加标准合同信息
        if (purchContract.getPurchContractStandard() != null) {
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
        attachmentService.updateAttachments(attachmentList, dbAttahmentsMap, dbPurchContract.getId(), Attachment.AttachmentCategory.PURCHCONTRACT.getCode());

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean insert(PurchContract purchContract) throws Exception {
        Date now = new Date();
        String lastedByPurchNo = purchContractDao.findLastedByPurchContractNo();
        if (lastedByPurchNo == null) {
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
            purchContractGoods.setCreateTime(now);
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
        if (purchContract.getPurchContractSimple() != null) {
            PurchContractSimple purchContractSimple = purchContract.getPurchContractSimple();
            purchContractSimple.setPurchContract(save);
            purchContractSimple.setCreateTime(now);
            purchContractSimpleDao.save(purchContractSimple);
        }
        // 添加标准合同信息
        if (purchContract.getPurchContractStandard() != null) {
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
        if (id != null && id > 0) {
            PurchContract purchContract = purchContractDao.findOne(id);
            if (purchContract != null) {
                if (purchContract.getType() == 3) {// 获取非标采购合同的附件信息
                    List<Attachment> attachments = attachmentDao.findByRelObjIdAndCategory(purchContract.getId(), Attachment.AttachmentCategory.PURCHCONTRACT.getCode());
                    if (attachments != null && attachments.size() > 0) {
                        purchContract.setAttachments(attachments);
                        purchContract.getAttachments().size();
                    }
                }
                if (purchContract.getType() == 1) { // 合同约定到货日期
                    purchContract.setAgreedArrivalDate(purchContract.getPurchContractSimple().getShippingDate());
                }
                if (purchContract.getType() == 2) { // 合同约定到货日期
                    purchContract.setAgreedArrivalDate(purchContract.getPurchContractStandard().getDeliveryDate());
                }
                List<PurchContractGoods> purchContractGoodsList = purchContract.getPurchContractGoodsList();
                Set<String> projectIdSet = new HashSet<>();
                if (purchContractGoodsList.size() > 0) {
                    for (PurchContractGoods purchContractGoods : purchContractGoodsList) {
                        projectIdSet.add(purchContractGoods.getProject().getId().toString());
                        purchContractGoods.setgId(purchContractGoods.getGoods().getId());
                        purchContractGoods.setPcId(purchContract.getId());
                        purchContractGoods.setPcgId(purchContractGoods.getId());

                    }
                    List<String> projectIdList = new ArrayList<>(projectIdSet);
                    purchContract.setProjectId(StringUtils.join(projectIdList, ","));
                }
                return purchContract;
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Map<String, Object>> purchAbleByPage(String agentId, int pageNum, int pageSizeString, String purchContractNo, Integer supplierId, String supplierName, Integer type) throws Exception {

        List<Integer> purchContractIds = findAllPurchAblePurchContract(Integer.parseInt(agentId));

        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSizeString, new Sort(Sort.Direction.DESC, "id"));
        Page<Map<String, Object>> result = null;
        if (purchContractIds.size() > 0) {
            Page<PurchContract> pageList = purchContractDao.findAll(new Specification<PurchContract>() {
                @Override
                public Predicate toPredicate(Root<PurchContract> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<>();
                    // 根据合同号过滤
                    if (purchContractNo != null) {
                        list.add(cb.like(root.get("purchContractNo").as(String.class), "%" + purchContractNo + "%"));
                    }
                    // 根据供应商ID过滤条件
                    if (supplierId != null) {
                        list.add(cb.equal(root.get("supplierId").as(Integer.class), supplierId));
                    }
                    // 根据供应商名称过滤条件
                    if (supplierName != null) {
                        list.add(cb.like(root.get("supplierName").as(String.class), "%" + supplierName + "%"));
                    }
                    // 根据合同类型查询
                    if (type != null) {
                        list.add(cb.equal(root.get("type").as(Integer.class), type));
                    }
                    // 根据采购经办人查询
                    if (agentId != null) {
                        list.add(cb.equal(root.get("agentId").as(Integer.class), agentId));
                    }

                    // 根据采购状态过滤条件
                    Predicate status01 = cb.equal(root.get("status").as(Integer.class), PurchContract.StatusEnum.BEING.getCode());
                    Predicate status02 = cb.equal(root.get("status").as(Integer.class), PurchContract.StatusEnum.EXECUTED.getCode());
                    list.add(cb.or(status01, status02));

                    // 根据商品未采购完成的
                    list.add(root.get("id").in(purchContractIds.toArray(new Integer[purchContractIds.size()])));

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
                map.put("lowercasePrice", purchContract.getLowercasePrice());
                map.put("capitalizedPrice", purchContract.getCapitalizedPrice());
                map.put("type", purchContract.getType());
                map.put("currencyBn", purchContract.getCurrencyBn());
                list.add(map);
            }
            result = new PageImpl<Map<String, Object>>(list, pageRequest, pageList.getTotalElements());
        } else {
            result = new PageImpl<Map<String, Object>>(new ArrayList<>(), pageRequest, 0);
        }

        return result;
    }

    /**
     * 查询所有可采购合同的id列表
     *
     * @param agentId
     * @return
     */
    private List<Integer> findAllPurchAblePurchContract(Integer agentId) {
        List<PurchContract> list = purchContractDao.findAll(new Specification<PurchContract>() {
            @Override
            public Predicate toPredicate(Root<PurchContract> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据采购经办人查询
                if (agentId != null) {
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
        });

        return list.parallelStream().map(po -> po.getId()).collect(Collectors.toList());
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
        newPurchContractGoods.setUpdateTime(new Date());

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
        son.setUpdateTime(new Date());
    }

    /**
     * 填充导出简易采购合同模板
     *
     * @param workbook
     * @param purchContractId
     * @throws Exception
     */
    @Override
    public void simpleContractExcelData(XSSFWorkbook workbook, Integer purchContractId) throws Exception {
        PurchContract purchContract = purchContractDao.findOne(purchContractId);
        if(purchContract == null) return;

        Date aa = new Date();
        Calendar c = Calendar.getInstance();

        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(1);
        Cell cell = row.getCell(0);
        int start = 0; // 下划线的起始位置
        int end = 0; // 下划线的结束位置
        String content = "";

        XSSFRichTextString richString = null;
        // 合同编号
        if(purchContract.getPurchContractNo() != null){
            cell.setCellValue(cell.getStringCellValue().replace("purchContractNo", purchContract.getPurchContractNo()));
        }
        // 签订地点
        if(purchContract.getSigningPlace() != null){
            cell.setCellValue(cell.getStringCellValue().replace("signingPlace", purchContract.getSigningPlace()));
        }
        // 签订日期
        if(purchContract.getSigningDate() != null){
            Date signingDate = purchContract.getSigningDate();
            c.setTime(signingDate);
            cell.setCellValue(cell.getStringCellValue().replace("year", c.get(Calendar.YEAR) + ""));
            cell.setCellValue(cell.getStringCellValue().replace("month", c.get(Calendar.MONTH)+1 + ""));
            cell.setCellValue(cell.getStringCellValue().replace("day", c.get(Calendar.DAY_OF_MONTH) + ""));
        }

        row = sheet.getRow(4);
        // 合计（含16%增值税专用发票）小写：
        if(purchContract.getLowercasePrice() != null){
            cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("lowercasePrice", purchContract.getLowercasePrice().toString()));
        }
        // （大写）
        if(purchContract.getCapitalizedPrice() != null){
            cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("capitalizedPrice", purchContract.getCapitalizedPrice()));
        }

        row = sheet.getRow(5);
        // 1、货物皆为符合__的合格产品   1、质保期自__ 。
        if(purchContract.getPurchContractSimple().getProductRequirement() != null && purchContract.getPurchContractSimple().getWarrantyPeriod() != null){
            cell = row.getCell(1);
            content = cell.getStringCellValue().replace("productRequirement", purchContract.getPurchContractSimple().getProductRequirement());
            content = content.replace("warrantyPeriod", purchContract.getPurchContractSimple().getWarrantyPeriod());
            richString = new XSSFRichTextString(content);

            start = "质量标准: 出卖人保证提供的货物皆为符合".length();
            end = start + purchContract.getPurchContractSimple().getProductRequirement().length() + 2;
            richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 productRequirement 字段加下划线

            start = end + "的合格产品，质保期自".length();
            end = start + purchContract.getPurchContractSimple().getWarrantyPeriod().length() + 2;
            richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 warrantyPeriod 字段加下划线

            cell.setCellValue(richString);
        }

        row = sheet.getRow(7);
        // 3、将货物于__前运送至   3、指定的地点：__。
        if(purchContract.getPurchContractSimple().getShippingDate() != null && purchContract.getPurchContractSimple().getDesignatedLocation() != null){
            cell = row.getCell(1);
            Date shippingDate = purchContract.getPurchContractSimple().getShippingDate();
            c.setTime(shippingDate);
            content = cell.getStringCellValue().replace("year", c.get(Calendar.YEAR) + "");
            content = content.replace("month", c.get(Calendar.MONTH)+1 + "");
            content = content.replace("day", c.get(Calendar.DAY_OF_MONTH) + "");
            content = content.replace("designatedLocation", " "+purchContract.getPurchContractSimple().getDesignatedLocation());
            richString = new XSSFRichTextString(content);

            start = "交货时间、方式、地点：出卖人负责将货物于".length();
            end = start + (c.get(Calendar.YEAR)+"").length() + 2;
            richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 year 字段加下划线

            start = end + "年".length();
            end = start + (c.get(Calendar.MONTH)+1+"").length() + 2;
            richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 month 字段加下划线

            start = end + "月".length();
            end = start + (c.get(Calendar.DAY_OF_MONTH)+"").length() + 2;
            richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 day 字段加下划线

            start = end + "日前运送至买受人指定的地点：".length();
            end = start + purchContract.getPurchContractSimple().getDesignatedLocation().length() + 2;
            richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 designatedLocation 字段加下划线

            cell.setCellValue(richString);
        }

        row = sheet.getRow(8);
        // 4、费用负担：__运
        if(purchContract.getPurchContractSimple().getCostBurden() != null){
            cell = row.getCell(1);
            content = cell.getStringCellValue().replace("costBurden", purchContract.getPurchContractSimple().getCostBurden());
            richString = new XSSFRichTextString(content);

            start = "运输方式及到达站（港）和费用负担：".length();
            end = start + purchContract.getPurchContractSimple().getCostBurden().length() + 2;
            richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 costBurden 字段加下划线

            cell.setCellValue(richString);
        }

        row = sheet.getRow(9);
        // 5、合同第1条在__处检验  5、并在__日内提出异议
        if(purchContract.getPurchContractSimple().getInspectionAt()!= null && purchContract.getPurchContractSimple().getWithinDays()!= null){
            cell = row.getCell(1);
            content = cell.getStringCellValue().replace("inspectionAt", purchContract.getPurchContractSimple().getInspectionAt());
            content = content.replace("withinDays", purchContract.getPurchContractSimple().getWithinDays());
            richString = new XSSFRichTextString(content);

            start = "产品检验以及所有权转移： 按本合同第1条在".length();
            end = start + purchContract.getPurchContractSimple().getInspectionAt().length() + 4;
            richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 inspectionAt 字段加下划线

            int redStart = end + "处检验，".length();
            int redEnd = redStart + purchContract.getPurchContractSimple().getWithinDays().length() + 4 + "日内提出异议".length();
            Font redFont = ExcelUploadUtil.getFont(workbook, 10, "宋体");
            richString.applyFont(redEnd, content.length(), redFont);
            redFont.setColor(Font.COLOR_RED);
            richString.applyFont(redStart, redEnd, redFont);// 给并在 withinDays 日内提出异议字段设置红色字体

            start = end + "处检验，并在".length();
            end = start + purchContract.getPurchContractSimple().getWithinDays().length() + 2;
            richString.applyFont(end, redEnd, redFont);
            redFont.setUnderline(Font.U_SINGLE);
            richString.applyFont(start, end, redFont);// 给 withinDays 字段加下划线

            cell.setCellValue(richString);
        }

        row = sheet.getRow(10);
        // 6、结算方式及时间：__。
        if(purchContract.getPurchContractSimple().getMethodAndTime()!= null){
            cell = row.getCell(1);
            content = cell.getStringCellValue().replace("methodAndTime", purchContract.getPurchContractSimple().getMethodAndTime());
            richString = new XSSFRichTextString(content);

            start = "结算方式及时间：".length();
            end = start + purchContract.getPurchContractSimple().getMethodAndTime().length() + 2;
            richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 methodAndTime 字段加下划线

            cell.setCellValue(richString);
        }

        row = sheet.getRow(13);
        // 9、附《__技术协议》
        if(purchContract.getPurchContractSimple().getAgreementName()!= null){
            cell = row.getCell(1);
            content = cell.getStringCellValue().replace("agreementName", purchContract.getPurchContractSimple().getAgreementName());
            richString = new XSSFRichTextString(content);

            start = ("其他：\n" +
                    "1、本合同一式 肆 份，具有同等法律效力，买受人执 贰 份，出卖人执 贰 份。\n" +
                    "2、本合同自双方签字盖章之日起生效到双方完全履行完各自的义务为止。\n" +
                    "附《").length();
            end = start + purchContract.getPurchContractSimple().getAgreementName().length() + 2;
            richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 agreementName 字段加下划线

            cell.setCellValue(richString);
        }


        row = sheet.getRow(14);
        // 出卖人：
        if(purchContract.getPurchContractSignatoriesList().get(0).getSellerBuyer()!= null){
            cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("sellerBuyer", purchContract.getPurchContractSignatoriesList().get(0).getSellerBuyer()));
        }
        // 买受人：
        if(purchContract.getPurchContractSignatoriesList().get(1).getSellerBuyer()!= null){
            cell = row.getCell(4);
            cell.setCellValue(cell.getStringCellValue().replace("sellerBuyer", purchContract.getPurchContractSignatoriesList().get(1).getSellerBuyer()));
        }

        row = sheet.getRow(15);
        // 法定代表人或授权代表：
        if(purchContract.getPurchContractSignatoriesList().get(0).getLegalRepresentative()!= null){
            cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("legalRepresentative", purchContract.getPurchContractSignatoriesList().get(0).getLegalRepresentative()));
        }
        // 法定代表人或授权代表：
        if(purchContract.getPurchContractSignatoriesList().get(1).getLegalRepresentative()!= null){
            cell = row.getCell(4);
            cell.setCellValue(cell.getStringCellValue().replace("legalRepresentative", purchContract.getPurchContractSignatoriesList().get(1).getLegalRepresentative()));
        }

        row = sheet.getRow(16);
        // 地址：
        if(purchContract.getPurchContractSignatoriesList().get(0).getAddress()!= null){
            cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("address", purchContract.getPurchContractSignatoriesList().get(0).getAddress()));
        }
        // 地址：
        if(purchContract.getPurchContractSignatoriesList().get(1).getAddress()!= null){
            cell = row.getCell(4);
            cell.setCellValue(cell.getStringCellValue().replace("address", purchContract.getPurchContractSignatoriesList().get(1).getAddress()));
        }

        row = sheet.getRow(17);
        // 开户行：
        if(purchContract.getPurchContractSignatoriesList().get(0).getOpeningBank()!= null){
            cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("openingBank", purchContract.getPurchContractSignatoriesList().get(0).getOpeningBank()));
        }
        // 开户行：
        if(purchContract.getPurchContractSignatoriesList().get(1).getOpeningBank()!= null){
            cell = row.getCell(4);
            cell.setCellValue(cell.getStringCellValue().replace("openingBank", purchContract.getPurchContractSignatoriesList().get(1).getOpeningBank()));
        }

        row = sheet.getRow(18);
        // 账号：
        if(purchContract.getPurchContractSignatoriesList().get(0).getAccountNumber()!= null){
            cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("accountNumber", purchContract.getPurchContractSignatoriesList().get(0).getAccountNumber()));
        }
        // 账号：
        if(purchContract.getPurchContractSignatoriesList().get(1).getAccountNumber()!= null){
            cell = row.getCell(4);
            cell.setCellValue(cell.getStringCellValue().replace("accountNumber", purchContract.getPurchContractSignatoriesList().get(1).getAccountNumber()));
        }

        row = sheet.getRow(19);
        // 统一社会信用代码证：
        if(purchContract.getPurchContractSignatoriesList().get(0).getCreditCode()!= null){
            cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("creditCode", purchContract.getPurchContractSignatoriesList().get(0).getCreditCode()));
        }
        // 统一社会信用代码证：
        if(purchContract.getPurchContractSignatoriesList().get(1).getCreditCode()!= null){
            cell = row.getCell(4);
            cell.setCellValue(cell.getStringCellValue().replace("creditCode", purchContract.getPurchContractSignatoriesList().get(1).getCreditCode()));
        }

        row = sheet.getRow(20);
        // 电话/传真
        if(purchContract.getPurchContractSignatoriesList().get(0).getTelephoneFax()!= null){
            cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("telephoneFax", purchContract.getPurchContractSignatoriesList().get(0).getTelephoneFax()));
        }
        // 电话/传真
        if(purchContract.getPurchContractSignatoriesList().get(1).getTelephoneFax()!= null){
            cell = row.getCell(4);
            cell.setCellValue(cell.getStringCellValue().replace("telephoneFax", purchContract.getPurchContractSignatoriesList().get(1).getTelephoneFax()));
        }

        List<PurchContractGoods> goodsList = purchContract.getPurchContractGoodsList();

        row = sheet.getRow(3);
        // 商品信息
        if(goodsList.get(0) != null){
            row.getCell(1).setCellValue(goodsList.get(0).getGoods().getNameZh());
            row.getCell(2).setCellValue(goodsList.get(0).getGoods().getModel());
            row.getCell(3).setCellValue(goodsList.get(0).getPurchaseNum());
            row.getCell(4).setCellValue(goodsList.get(0).getGoods().getUnit());
            row.getCell(5).setCellValue(goodsList.get(0).getPurchasePrice() + "");
            row.getCell(6).setCellValue(goodsList.get(0).getPurchaseTotalPrice() + "");
            row.getCell(7).setCellValue(purchContract.getGoodsRemarks() != null?purchContract.getGoodsRemarks():"");
        }
        if(goodsList.size() > 1){
            for (int i = 1; i< goodsList.size(); i++){
                ExcelUploadUtil.insertRow(sheet, i+2, 1);
                row = sheet.getRow(3+i);
                row.getCell(0).setCellValue(i+1);
                row.getCell(1).setCellValue(goodsList.get(i).getGoods().getNameZh());
                row.getCell(2).setCellValue(goodsList.get(i).getGoods().getModel());
                row.getCell(3).setCellValue(goodsList.get(i).getPurchaseNum());
                row.getCell(4).setCellValue(goodsList.get(i).getGoods().getUnit());
                row.getCell(5).setCellValue(goodsList.get(i).getPurchasePrice() + "");
                row.getCell(6).setCellValue(goodsList.get(i).getPurchaseTotalPrice() + "");

            }
            // 合并备注字段
            CellRangeAddress region = new CellRangeAddress(3, 2+goodsList.size(), 7, 7);
            sheet.addMergedRegion(region);
        }

    }
}
