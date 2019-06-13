package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.NewDateUtil;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.PurchContractService;
import com.erui.order.util.WordUploadUtil;
import com.erui.order.util.excel.ExcelUploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.math.BigInteger;
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
    @Value("#{orderProp[GET_SKU]}")
    private String getSku;  //获取sku编码

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

        if (dbPurchContract.getPurchContractSimple() != null) { // 简易合同
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
        // 提交时商品数量为0的商品不保存
        if (purchContract.getStatus() == PurchContract.StatusEnum.BEING.getCode()) {
            List<PurchContractGoods> pcgList = new ArrayList<>();
            for (PurchContractGoods pcg : purchContract.getPurchContractGoodsList()) {
                if (pcg.getPurchaseNum() != null && pcg.getPurchaseNum() > 0) {
                    pcgList.add(pcg);
                }
            }
            if (pcgList.size() == 0) {
                throw new Exception(String.format("%s%s%s", "必须存在要采购的商品", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "There must be goods to be purchased"));
            }
            purchContract.setPurchContractGoodsList(pcgList);
        }
        // 处理商品
        List<PurchContractGoods> purchContractGoodsList = new ArrayList<>(); // 声明最终采购商品容器
        Set<Project> projectSet = new HashSet<>(); // 声明项目的容器
        List<Goods> goodsList = new ArrayList<>();
        // 数据库现在的采购商品信息
        Map<Integer, PurchContractGoods> dbPurchContractGoodsMap = dbPurchContract.getPurchContractGoodsList().parallelStream().collect(Collectors.toMap(PurchContractGoods::getId, vo -> vo));
        Set<Integer> existId = new HashSet<>();
        // 处理参数中的采购商品信息
        for (PurchContractGoods pg : purchContract.getPurchContractGoodsList()) {
            Integer pgId = pg.getId();
            if (pgId == null) { // 新增加的采购商品信息
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
                //采购合同保存时回写商品 供应商和品牌
                goods.setSupplier(purchContract.getSupplierName());
                goods.setBrand(pg.getBrand());
                goodsList.add(goods);

                goodsDao.save(goods);
            } else if (dbPurchContractGoodsMap.containsKey(pgId)) {
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
                // 判断采购是否超限,预采购数量大于合同数量，则错误
                if (goods.getPrePurchsedNum() + purchaseNum - oldPurchaseNum > goods.getContractGoodsNum()) {
                    throw new Exception(String.format("%s%s%s", "采购数量超过合同数量【sku :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Quantity of purchase exceeds the number of contracts [SKU: " + goods.getSku() + "]"));

                }
                if (purchaseNum > 0 && (purchContractGoods.getPurchasePrice() == null || purchContractGoods.getPurchasePrice().compareTo(BigDecimal.ZERO) != 1)) {
                    throw new Exception(String.format("%s%s%s", "要采购的商品单价错误【sku :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Unit price error to be purchased [SKU: " + goods.getSku() + "]"));

                }
                goods.setPrePurchsedNum(goods.getPrePurchsedNum() + purchaseNum - oldPurchaseNum);
                //采购合同保存时回写商品 供应商和品牌
                goods.setSupplier(purchContract.getSupplierName());
                goods.setBrand(purchContractGoods.getBrand());
                goodsList.add(goods);

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
        //校验商品sku回写销售订单商品sku
        if (purchContract.getStatus() == PurchContract.StatusEnum.BEING.getCode() && getGoodSku(goodsList) != null) {
            goodsDao.save(getGoodSku(goodsList));
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
        // 提交时商品数量为0的商品不保存
        if (purchContract.getStatus() == PurchContract.StatusEnum.BEING.getCode()) {
            List<PurchContractGoods> pcgList = new ArrayList<>();
            for (PurchContractGoods pcg : purchContract.getPurchContractGoodsList()) {
                if (pcg.getPurchaseNum() != null && pcg.getPurchaseNum() > 0) {
                    pcgList.add(pcg);
                }
            }
            if (pcgList.size() == 0) {
                throw new Exception(String.format("%s%s%s", "必须存在要采购的商品", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "There must be goods to be purchased"));
            }
            purchContract.setPurchContractGoodsList(pcgList);
        }
        List<PurchContractGoods> purchContractGoodsList = new ArrayList<>();
        // 处理商品信息
        Set<Project> projectSet = new HashSet<>();
        List<Goods> goodsList = new ArrayList<>();
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
            // 增加预采购数量
            goods.setPrePurchsedNum(goods.getPrePurchsedNum() + intPurchaseNum);
            //采购合同保存时回写商品 供应商和品牌
            goods.setSupplier(purchContract.getSupplierName());
            goods.setBrand(purchContractGoods.getBrand());
            goodsList.add(goods);
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
        //校验商品sku回写销售订单商品sku
        if (purchContract.getStatus() == PurchContract.StatusEnum.BEING.getCode() && getGoodSku(goodsList) != null) {
            goodsDao.save(getGoodSku(goodsList));
        }
        // 添加附件
        if (purchContract.getAttachments() != null && purchContract.getAttachments().size() > 0) {
            attachmentService.addAttachments(purchContract.getAttachments(), save.getId(), Attachment.AttachmentCategory.PURCHCONTRACT.getCode());
        }
        return true;
    }

    //验证获取sku
    public List<Goods> getGoodSku(List<Goods> goodsList) {
        final String eruiToken = (String) ThreadLocalUtil.getObject();
        List<Object> skus = new ArrayList<>();
        List<Object> attrs = new ArrayList<>();
        JSONObject params = new JSONObject();
        for (Goods gd : goodsList) {
            JSONObject gjson = new JSONObject();
            Object attrsJson = "";
            if (StringUtils.isNotBlank(gd.getAttrs())) {
                attrsJson = JSONObject.parse(gd.getAttrs());
            }
            gjson.put("nameZh", gd.getNameZh());
            gjson.put("department", gd.getDepartment());
            gjson.put("unit", gd.getUnit());
            gjson.put("brand", gd.getBrand());
            gjson.put("model", gd.getModel());
            gjson.put("meteType", gd.getMeteType());
            gjson.put("tplNo", gd.getTplNo());
            gjson.put("attrs", attrsJson);
            skus.add(attrs);
        }
        params.put("type", "O");
        params.put("source", "ORDE");
        params.put("skus", skus);
        Map<String, String> header = new HashMap<>();
        header.put("Cookie", "eruitoken=" + eruiToken);
        header.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        String resData = HttpRequest.sendPost(getSku, params.toJSONString(), header);
        JSONObject parseData = JSONObject.parseObject(resData);
        JSONArray datas = parseData.getJSONArray("data");
        if (datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                JSONObject goodJson = JSONObject.parseObject(datas.getString(i));
                for (Goods gd : goodsList) {
                    gd.setSku(goodJson.getString("sku"));
                }
            }
        }
        return goodsList;
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
        if (purchContract == null) return;

        PurchContractSimple purchContractSimple = purchContract.getPurchContractSimple();

        Calendar c = Calendar.getInstance();
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(1);
        Cell cell = row.getCell(0);
        int start = 0; // 下划线的起始位置
        int end = 0; // 下划线的结束位置
        String content = "";

        XSSFRichTextString richString = null;
        // 合同编号
        cell.setCellValue(cell.getStringCellValue().replace("purchContractNo", formatNullStr(purchContract.getPurchContractNo())));
        // 签订地点
        cell.setCellValue(cell.getStringCellValue().replace("signingPlace", formatNullStr(purchContract.getSigningPlace())));
        // 签订日期

        if (purchContract.getSigningDate() == null) {
            cell.setCellValue(cell.getStringCellValue().replace("year", " "));
            cell.setCellValue(cell.getStringCellValue().replace("month", " "));
            cell.setCellValue(cell.getStringCellValue().replace("day", " "));
        } else {
            Date signingDate = purchContract.getSigningDate();
            c.setTime(signingDate);
            cell.setCellValue(cell.getStringCellValue().replace("year", c.get(Calendar.YEAR) + ""));
            cell.setCellValue(cell.getStringCellValue().replace("month", c.get(Calendar.MONTH) + 1 + ""));
            cell.setCellValue(cell.getStringCellValue().replace("day", c.get(Calendar.DAY_OF_MONTH) + ""));
        }

        row = sheet.getRow(4);
        // 含16%增值税专用发票
        cell = row.getCell(0);
        cell.setCellValue(cell.getStringCellValue().replace("16", purchContract.getTaxPoint() == null ? "" : dropZero(purchContract.getTaxPoint().toString())));
        // 合计（含16%增值税专用发票）小写：
        cell = row.getCell(0);
        cell.setCellValue(cell.getStringCellValue().replace("lowercasePrice", purchContract.getLowercasePrice() == null ? "" : purchContract.getLowercasePrice().toString()));
        // （大写）
        cell = row.getCell(0);
        cell.setCellValue(cell.getStringCellValue().replace("capitalizedPrice", formatNullStr(purchContract.getCapitalizedPrice())));

        row = sheet.getRow(5);
        // 1、货物皆为符合__的合格产品   1、质保期自__ 。
        cell = row.getCell(1);
        content = cell.getStringCellValue().replace("productRequirement", formatNullStr(purchContractSimple.getProductRequirement()));
        content = content.replace("warrantyPeriod", formatNullStr(purchContractSimple.getWarrantyPeriod()));
        richString = new XSSFRichTextString(content);

        start = "质量标准: 出卖人保证提供的货物皆为符合".length();
        end = start + formatNullStr(purchContractSimple.getProductRequirement()).length() + 2;
        richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 productRequirement 字段加下划线

        start = end + "的合格产品，质保期自".length();
        end = start + formatNullStr(purchContractSimple.getWarrantyPeriod()).length() + 2;
        richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 warrantyPeriod 字段加下划线

        cell.setCellValue(richString);

        row = sheet.getRow(7);
        // 3、将货物于__前运送至   3、指定的地点：__。
        cell = row.getCell(1);
        if (purchContractSimple.getShippingDate() == null) {
            content = cell.getStringCellValue().replace("year", " ");
            content = content.replace("month", " ");
            content = content.replace("day", " ");
        } else {
            Date shippingDate = purchContractSimple.getShippingDate();
            c.setTime(shippingDate);
            content = cell.getStringCellValue().replace("year", c.get(Calendar.YEAR) + "");
            content = content.replace("month", c.get(Calendar.MONTH) + 1 + "");
            content = content.replace("day", c.get(Calendar.DAY_OF_MONTH) + "");
        }
        content = content.replace("designatedLocation", " " + formatNullStr(purchContractSimple.getDesignatedLocation()));
        richString = new XSSFRichTextString(content);

        start = "交货时间、方式、地点：出卖人负责将货物于".length();
        end = start + (purchContractSimple.getShippingDate() == null ? " " : c.get(Calendar.YEAR) + "").length() + 2;
        richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 year 字段加下划线

        start = end + "年".length();
        end = start + (purchContractSimple.getShippingDate() == null ? " " : c.get(Calendar.MONTH) + 1 + "").length() + 2;
        richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 month 字段加下划线

        start = end + "月".length();
        end = start + (purchContractSimple.getShippingDate() == null ? " " : c.get(Calendar.DAY_OF_MONTH) + "").length() + 2;
        richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 day 字段加下划线

        start = end + "日前运送至买受人指定的地点：".length();
        end = start + formatNullStr(purchContractSimple.getDesignatedLocation()).length() + 2;
        richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 designatedLocation 字段加下划线

        cell.setCellValue(richString);

        row = sheet.getRow(8);
        // 4、费用负担：__运
        cell = row.getCell(1);
        content = cell.getStringCellValue().replace("costBurden", formatNullStr(purchContractSimple.getCostBurden()));
        richString = new XSSFRichTextString(content);

        start = "运输方式及到达站（港）和费用负担：".length();
        end = start + formatNullStr(purchContractSimple.getCostBurden()).length() + 2;
        richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 costBurden 字段加下划线

        cell.setCellValue(richString);

        row = sheet.getRow(9);
        // 5、合同第1条在__处检验  5、并在__日内提出异议
        cell = row.getCell(1);
        content = cell.getStringCellValue().replace("inspectionAt", formatNullStr(purchContractSimple.getInspectionAt()));
        content = content.replace("withinDays", formatNullStr(purchContractSimple.getWithinDays()));
        richString = new XSSFRichTextString(content);

        start = "产品检验以及所有权转移： 按本合同第1条在".length();
        end = start + formatNullStr(purchContractSimple.getInspectionAt()).length() + 4;
        richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 inspectionAt 字段加下划线

        int redStart = end + "处检验，".length();
        int redEnd = redStart + formatNullStr(purchContractSimple.getWithinDays()).length() + 4 + "日内提出异议".length();
        Font redFont = ExcelUploadUtil.getFont(workbook, 10, "宋体");
        richString.applyFont(redEnd, content.length(), redFont);
        redFont.setColor(Font.COLOR_RED);
        richString.applyFont(redStart, redEnd, redFont);// 给并在 withinDays 日内提出异议字段设置红色字体

        start = end + "处检验，并在".length();
        end = start + formatNullStr(purchContractSimple.getWithinDays()).length() + 2;
        richString.applyFont(end, redEnd, redFont);
        redFont.setUnderline(Font.U_SINGLE);
        richString.applyFont(start, end, redFont);// 给 withinDays 字段加下划线

        cell.setCellValue(richString);

        row = sheet.getRow(10);
        // 6、结算方式及时间：__。
        cell = row.getCell(1);
        content = cell.getStringCellValue().replace("methodAndTime", formatNullStr(purchContractSimple.getMethodAndTime()));
        richString = new XSSFRichTextString(content);

        start = "结算方式及时间：".length();
        end = start + purchContractSimple.getMethodAndTime().length() + 2;
        richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 methodAndTime 字段加下划线

        cell.setCellValue(richString);

        row = sheet.getRow(13);
        // 9、附《__技术协议》
        cell = row.getCell(1);
        content = cell.getStringCellValue().replace("agreementName", formatNullStr(purchContractSimple.getAgreementName()));
        richString = new XSSFRichTextString(content);

        start = ("其他：\n" +
                "1、本合同一式 肆 份，具有同等法律效力，买受人执 贰 份，出卖人执 贰 份。\n" +
                "2、本合同自双方签字盖章之日起生效到双方完全履行完各自的义务为止。\n" +
                "附《").length();
        end = start + purchContractSimple.getAgreementName().length() + 2;
        richString = ExcelUploadUtil.setSingle(richString, ExcelUploadUtil.getFont(workbook, 10, "宋体"), start, end, content.length());// 给 agreementName 字段加下划线

        cell.setCellValue(richString);


        row = sheet.getRow(14);
        // 出卖人：
        if (purchContract.getPurchContractSignatoriesList().get(0).getSellerBuyer() != null) {
            cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("sellerBuyer", formatNullStr(purchContract.getPurchContractSignatoriesList().get(0).getSellerBuyer())));
        }
        // 买受人：
        if (purchContract.getPurchContractSignatoriesList().get(1).getSellerBuyer() != null) {
            cell = row.getCell(4);
            cell.setCellValue(cell.getStringCellValue().replace("sellerBuyer", formatNullStr(purchContract.getPurchContractSignatoriesList().get(1).getSellerBuyer())));
        }

        row = sheet.getRow(15);
        // 法定代表人或授权代表：
        if (purchContract.getPurchContractSignatoriesList().get(0).getLegalRepresentative() != null) {
            cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("legalRepresentative", formatNullStr(purchContract.getPurchContractSignatoriesList().get(0).getLegalRepresentative())));
        }
        // 法定代表人或授权代表：
        if (purchContract.getPurchContractSignatoriesList().get(1).getLegalRepresentative() != null) {
            cell = row.getCell(4);
            cell.setCellValue(cell.getStringCellValue().replace("legalRepresentative", formatNullStr(purchContract.getPurchContractSignatoriesList().get(1).getLegalRepresentative())));
        }

        row = sheet.getRow(16);
        // 地址：
        if (purchContract.getPurchContractSignatoriesList().get(0).getAddress() != null) {
            cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("address", formatNullStr(purchContract.getPurchContractSignatoriesList().get(0).getAddress())));
        }
        // 地址：
        if (purchContract.getPurchContractSignatoriesList().get(1).getAddress() != null) {
            cell = row.getCell(4);
            cell.setCellValue(cell.getStringCellValue().replace("address", formatNullStr(purchContract.getPurchContractSignatoriesList().get(1).getAddress())));
        }

        row = sheet.getRow(17);
        // 开户行：
        if (purchContract.getPurchContractSignatoriesList().get(0).getOpeningBank() != null) {
            cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("openingBank", formatNullStr(purchContract.getPurchContractSignatoriesList().get(0).getOpeningBank())));
        }
        // 开户行：
        if (purchContract.getPurchContractSignatoriesList().get(1).getOpeningBank() != null) {
            cell = row.getCell(4);
            cell.setCellValue(cell.getStringCellValue().replace("openingBank", formatNullStr(purchContract.getPurchContractSignatoriesList().get(1).getOpeningBank())));
        }

        row = sheet.getRow(18);
        // 账号：
        if (purchContract.getPurchContractSignatoriesList().get(0).getAccountNumber() != null) {
            cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("accountNumber", formatNullStr(purchContract.getPurchContractSignatoriesList().get(0).getAccountNumber())));
        }
        // 账号：
        if (purchContract.getPurchContractSignatoriesList().get(1).getAccountNumber() != null) {
            cell = row.getCell(4);
            cell.setCellValue(cell.getStringCellValue().replace("accountNumber", formatNullStr(purchContract.getPurchContractSignatoriesList().get(1).getAccountNumber())));
        }

        row = sheet.getRow(19);
        // 统一社会信用代码证：
        if (purchContract.getPurchContractSignatoriesList().get(0).getCreditCode() != null) {
            cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("creditCode", formatNullStr(purchContract.getPurchContractSignatoriesList().get(0).getCreditCode())));
        }
        // 统一社会信用代码证：
        if (purchContract.getPurchContractSignatoriesList().get(1).getCreditCode() != null) {
            cell = row.getCell(4);
            cell.setCellValue(cell.getStringCellValue().replace("creditCode", formatNullStr(purchContract.getPurchContractSignatoriesList().get(1).getCreditCode())));
        }

        row = sheet.getRow(20);
        // 电话/传真
        if (purchContract.getPurchContractSignatoriesList().get(0).getTelephoneFax() != null) {
            cell = row.getCell(0);
            cell.setCellValue(cell.getStringCellValue().replace("telephoneFax", formatNullStr(purchContract.getPurchContractSignatoriesList().get(0).getTelephoneFax())));
        }
        // 电话/传真
        if (purchContract.getPurchContractSignatoriesList().get(1).getTelephoneFax() != null) {
            cell = row.getCell(4);
            cell.setCellValue(cell.getStringCellValue().replace("telephoneFax", formatNullStr(purchContract.getPurchContractSignatoriesList().get(1).getTelephoneFax())));
        }

        List<PurchContractGoods> goodsList = purchContract.getPurchContractGoodsList();

        row = sheet.getRow(3);
        // 商品信息
        if (goodsList.get(0) != null) {
            row.getCell(1).setCellValue(goodsList.get(0).getGoods().getNameZh());
            row.getCell(2).setCellValue(goodsList.get(0).getGoods().getModel());
            row.getCell(3).setCellValue((goodsList.get(0).getPurchaseNum() == null ? "" : goodsList.get(0).getPurchaseNum()) + "");
            row.getCell(4).setCellValue(goodsList.get(0).getGoods().getUnit());
            row.getCell(5).setCellValue((goodsList.get(0).getPurchasePrice() == null ? "" : goodsList.get(0).getPurchasePrice()) + "");
            row.getCell(6).setCellValue((goodsList.get(0).getPurchaseTotalPrice() == null ? "" : goodsList.get(0).getPurchaseTotalPrice()) + "");
            row.getCell(7).setCellValue(purchContract.getGoodsRemarks() != null ? purchContract.getGoodsRemarks() : "");
        }
        if (goodsList.size() > 1) {
            for (int i = 1; i < goodsList.size(); i++) {
                ExcelUploadUtil.insertRow(sheet, i + 2, 1);
                row = sheet.getRow(3 + i);
                row.getCell(0).setCellValue(i + 1);
                row.getCell(1).setCellValue(goodsList.get(i).getGoods().getNameZh());
                row.getCell(2).setCellValue(goodsList.get(i).getGoods().getModel());
                row.getCell(3).setCellValue((goodsList.get(i).getPurchaseNum() == null ? "" : goodsList.get(i).getPurchaseNum()) + "");
                row.getCell(4).setCellValue(goodsList.get(i).getGoods().getUnit());
                row.getCell(5).setCellValue((goodsList.get(i).getPurchasePrice() == null ? "" : goodsList.get(i).getPurchasePrice()) + "");
                row.getCell(6).setCellValue((goodsList.get(i).getPurchaseTotalPrice() == null ? "" : goodsList.get(i).getPurchaseTotalPrice()) + "");

            }
            // 合并备注字段
            CellRangeAddress region = new CellRangeAddress(3, 2 + goodsList.size(), 7, 7);
            sheet.addMergedRegion(region);
        }

    }

    /**
     * 查询采购合同详情信息
     *
     * @param doc
     * @param purchContractId 采购合同ID
     * @throws Exception
     */
    @Override
    public void standardContractWordData(XWPFDocument doc, Integer purchContractId) throws Exception {
        PurchContract purchContract = purchContractDao.findOne(purchContractId);
        if (purchContract == null) return;

        List<PurchContractSignatories> signatoriesList = purchContract.getPurchContractSignatoriesList(); // 签约双方信息
        PurchContractStandard purchContractStandard = purchContract.getPurchContractStandard(); // 标准合同信息
        List<PurchContractGoods> goodsList = purchContract.getPurchContractGoodsList(); // 商品信息
        Calendar c = Calendar.getInstance();

        XWPFParagraph page = doc.createParagraph();
        XWPFRun run = page.createRun();

        int font = 9; // 字号
        String family = "宋体"; // 字体
        int val = 9; // 设置两行之间的行间
        int val1 = 18; // 设置两行之间的行间
        int width = 45; //距离

        // 标题 采购合同
        run.setTextPosition(val1);
        run.setBold(true);//字体加粗
        page.setAlignment(ParagraphAlignment.CENTER);//居中
        WordUploadUtil.setfont(run, 22, "宋体", "采购合同");

        // 出卖人：  德州博儒石油机械制造有限公司               合同编号：YRC201900034
        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "出卖人：");

        run = page.createRun();
        run.setBold(true);// 字体加粗
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, formatNullStr(signatoriesList.get(0).getSellerBuyer())); // 出卖人

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, String.format("%1$-" + (width - (("出卖人：" + signatoriesList.get(0).getSellerBuyer()).length()) * 2) + "s", "签").replace("签", "  ") + "合同编号：");

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, formatNullStr(purchContract.getPurchContractNo())); // 合同编号

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "买受人：");

        run = page.createRun();
        run.setBold(true);// 字体加粗
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, formatNullStr(signatoriesList.get(1).getSellerBuyer())); // 买受人

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, String.format("%1$-" + (width - (("买受人：" + signatoriesList.get(1).getSellerBuyer()).length()) * 2) + "s", "签").replace("签", "  ") + "签订地点：");

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, formatNullStr(purchContract.getSigningPlace())); // 签订地点

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, String.format("%1$-" + width + "s", "签").replace("签", "  ") + "签订时间：");

        run = page.createRun();
        run.setTextPosition(val);
        if (purchContract.getSigningDate() == null) {
            WordUploadUtil.setfont(run, font, family, " 年 月 日"); // 签订时间
        } else {
            Date signingDate = purchContract.getSigningDate();
            c.setTime(signingDate);
            WordUploadUtil.setfont(run, font, family, c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日"); // 签订时间
        }

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    鉴于双方均是依法成立并持续经营的公司，而本合同项下的标的物拟用于买受人");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getUsedForBuyer()) + " "); // 基础信息、用于买受人__，且出卖人

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "，且出卖人具有前述相关工作资质及经验，愿意并有能力承担该项工作。为保证该项目如期顺利实施，特向出卖人为此次采购。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "第一条、标的、规格型号、数量、价款及备注");

        //--------------商品信息--------------------------

        XWPFTable comTable = doc.createTable();
        int[] colWidths = new int[]{200, 300, 225, 144, 200, 206, 194};
        String[] value_columns = new String[]{"标的名称", "规格型号", "数量", "单位", "单价(元)", "金额(元)", "备注"};
        List<List<String>> list = new ArrayList<>();
        List<String> listChild = new ArrayList<>();
        for (int i = 0; i < goodsList.size(); i++) {
            listChild.add(goodsList.get(i).getGoods().getNameZh());// 标的名称
            listChild.add(goodsList.get(i).getGoods().getModel());// 规格型号
            listChild.add((goodsList.get(i).getPurchaseNum() == null ? "" : goodsList.get(i).getPurchaseNum()) + "");// 数量
            listChild.add(goodsList.get(i).getGoods().getUnit());// 单位
            listChild.add((goodsList.get(i).getPurchasePrice() == null ? "" : goodsList.get(i).getPurchasePrice()) + "");// 单价(元)
            listChild.add((goodsList.get(i).getPurchaseTotalPrice() == null ? "" : goodsList.get(i).getPurchaseTotalPrice()) + "");// 金额(元)
            if (i == 0) {
                listChild.add(purchContract.getGoodsRemarks() != null ? purchContract.getGoodsRemarks() : "");// 备注
            } else {
                listChild.add("");
            }
            list.add(listChild);
            listChild = new ArrayList<>();
        }
        // 生成表格
        doc = WordUploadUtil.exportWord(doc, comTable, value_columns, list, colWidths);

        XWPFTableRow rowsContent = comTable.createRow();
        XWPFParagraph cellParagraphC = rowsContent.getCell(0).getParagraphs().get(0);
        cellParagraphC.setAlignment(ParagraphAlignment.CENTER); //设置表头单元格居中
        XWPFRun cellParagraphRunC = cellParagraphC.createRun();
        cellParagraphRunC.setFontSize(font); //设置表格内容字号
        cellParagraphRunC.setFontFamily(family);
        cellParagraphRunC.setTextPosition(val);
        cellParagraphRunC.setText("合计人民币金额:（大写） " + formatNullStr(purchContract.getCapitalizedPrice()) + "       （含" + (purchContract.getTaxPoint() == null ? "" : dropZero(purchContract.getTaxPoint().toString())) + "%增值税）；小写:￥" + (purchContract.getLowercasePrice() == null ? "" : purchContract.getLowercasePrice())); //单元格段落加载内容
        WordUploadUtil.mergeCellsHorizontal(comTable, goodsList.size() + 1, 0, 6);
        WordUploadUtil.mergeCellsVertically(comTable, 6, 1, goodsList.size());

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "第二条、质量标准及要求");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    ");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getStandardAndRequire())); // 第二条、质量标准及要求__

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    如出卖人提供的产品质量不符合上述规定，买受人有权拒收部分甚至整批货物、要求降价、换货，出卖人对此应承担全部责任并应赔偿买受人因此所遭受的损失。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "第三条、禁止外包");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    该合同项下的产品或服务必须由出卖人制造和提供，未经买受人事先书面同意， 出卖人不得将产品或服务部分或全部分包/转包给第三方，否则买受人有权拒绝接受分包/转包部分的产品或服务。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "第四条、产品包装标准、包装费用与包装物回收");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    ");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, formatNullStr(purchContractStandard.getMeetRequire())); // 第四条、产品包装标准、包装费用与包装物回收__

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    除合同另有规定外，出卖人提供的全部货物均应采用国家标准或专业标准的保护措施进行包装，或者采取足以保护货物的包装方式，使货物包装能适应于长途运输、适应气候变化、防潮、防震、防锈和防粗暴装卸，确保货物安全无损的抵运交货地点。包装物不回收，包装费用由出卖人承担。由于包装不善所引起的货物锈蚀、损坏和损失均由出卖人承担责任。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "第五条、质保与售后");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    1.质保期内实行三包（包修、包换、包退），产品的质保期");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, formatNullStr(purchContractStandard.getWarrantyPeriod()) + "。"); // 第五条、质保与售后__

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    在质保期内，若发现出卖人提供的货物有缺陷/或不符合本合同规定时，买受人有权安排有关机构进行检验，并依据检验报告或直接要求且出卖人必须按买受人要求的时间采取以下补救措施：修理、更换、退货并返还全部货款、减价，由此所产生的费用由出卖人承担。买受人因货物质量问题造成其他损失的，出卖人应予赔偿。该项损失难以计算的，损失赔偿额不少于该合同总价款的10%。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    出卖人修理、更换有缺陷的设备产品时，质量保证期自出卖人消除该缺陷后重新计算。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "第六条、货物的运输与交付");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    1.出卖人负责运输并承担运费。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    2.出卖人负责于");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        if (purchContractStandard.getDeliveryDate() == null) {
            WordUploadUtil.setfont(run, font, family, " " + "年 月 日" + " "); // 第六条、出卖人负责于__（前）
        } else {
            Date deliveryDate = purchContractStandard.getDeliveryDate();
            c.setTime(deliveryDate);
            WordUploadUtil.setfont(run, font, family, " " + c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日" + " "); // 第六条、出卖人负责于__（前）
        }

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "（前）将货物全部交付给买受人。交货地点：");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getDeliveryPlace()) + " 。 "); // 第六条、交货地点:__。

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "第七条、货物的验收及所有权转移");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    1.产品验收地点为在买受人处验收。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    2.货物须同时完全符合下列各项标准及要求方为合格：(1)装箱单、质量合格证书及其它应当随箱的中（俄）文技术资料；(2)合同条款或者技术协议中涉及质量、技术、服务、 鉴定、检验及验收的全部相关内容或其所指引的内容。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    3. 货物所有权及损毁、灭失风险自验收合格时起转移给买受人。交付时有关质量、规格、 性能、数量或重量的检验不为最终检验。检验期为");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getInspectionPeriod()) + " "); // 第七条、检验期为__天(月)

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "天(月)，出卖人在接到买受方的书面异议后，应在5天内负责调查处理，否则视为违约，出卖人应支付买受人合同总价款10%的违约金。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    4.出卖人保证向买受人提供的技术资料包括");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getDataVersion()) + " "); // 第七条、技术资料包括__版本

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "版本是清晰的、正确的、完整的，否则视为未完成交货。出卖人应在买受人送达通知之日起7日内将需补足的资料交付到买受人指定地点，按本合同前述各条款项规定交付及验收。如因出卖人没有提供相应的资料及证明造成买受人任何损失的，出卖人还应承担相应的赔偿责任。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    5.如果发现产品不符合约定，买受人应在5天内提出异议，并有权拒付货款。在接到买受人书面异议后，出卖人应在5天内(另有规定或当事人另行商定期限者除外)负责处理，否则，即视为默认买受人提出的异议和处理意见，并按照买受人指定期限内返还全额预付款。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    6.无论出卖人提供的产品是否通过验收，如买受人在使用过程中发现出卖人提供的产品存在任何质量问题的，出卖人仍应按照本合同第九条的约定采取相应补救措施。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "第八条、结算方式及时间");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    ");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, formatNullStr(purchContractStandard.getMethodAndTime()) + " "); // 第八条、结算方式及时间__

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "第九条、违约责任");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    1. 出卖人的违约责任：");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    (1) 如果出卖人逾期交货的，每逾期一天扣除合同总价款的1%作为违约金；如果出卖人逾期交货超过两周以上的，买受人有权解除合同，出卖人除退还买受人已付款项外，还应支付相应的违约金，违约金最高为相应合同总价款的20%。如果买受人所遭受的经济损失大于约定的违约金数额的，出卖人应向买受人支付超过约定违约金数额的差额部分。如果出卖人于合同签订后或收到预付款后单方解除合同，应支付买受人合同总价款50%的违约金，预付款一并返还，出卖人应在买受人通知中指定的期限内向买受人支付违约金。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    (2) 买受人使用或验收时如发现货物的质量、规格、性能、数量或重量与合同不符的，或者质量低劣和不符合该同类常用途的，或技术资料不全、有错误的，或者包装不符合合同规定及破损的，买受人有权拒付相应货款、退货、解除合同，出卖人应根据买受人的要求负责更换、补齐、重作、修理、减价、退还已收到的货款，由此所产生的一切费用包括但不限于仓储费、运输费等由出卖人承担。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    (3) 因发生上述(2) 种情形导致出卖人逾期交货，造成买受人损失的，出卖人除承担该损失外，还应按照本条第(1) 项的规定向买受人支付逾期交货的违约金。另外，如果出卖人不依据合同约定交付标的物导致合同解除的，买受人可另行购买与合同标的物相同或相似的货物，如果该购买价格高于合同规定价格的，出卖人应就价格差额部分以及其他费用和损失向买受人赔偿，并且还应支付相应的违约金，违约金为相应合同总价款的20%。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    (4)如出卖人不履行合同义务或者履行合同义务不符合约定，给买受人造成损失的，损失赔偿额应当相当于因出卖人违约而给买受人所造成的损失，包括合同履行后可以获得的利益；如买受人告知出卖人合同标的物的特定用途或出卖人应知买受人购买合同标的物的特定用途的，出卖人应向买受人赔偿因货物不符合合同或因质量问题而对买受人导致的直接损失和间接损失。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    2. 买受人的违约责任：");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    (1) 买受人逾期付款的，应按照中国人民银行有关延期付款的规定，向出卖人偿付逾期付款的违约金。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    (2) 买受人无正当理由中途退货的，应向出卖人偿付为此而造成的损失。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "第十条、合同的解除");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    在出卖人发生下列违约的情况下，买受人可向出卖人发出书面通知解除本合同，同时保留向出卖人追索损失的权利。合同自买受人通知到达对方时解除。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    1. 出卖人未能在合同规定的限期或买受人同意延长的限期内履行合同的；");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    2. 出卖人未能履行合同规定的主要义务的；");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    3. 出卖人具有《合同法》第94 条所规定情形之一的；");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    4. 第三方对出卖人工厂质量体系进行检测后，发现出卖人不符合质量体系的；");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    5. 在本合同签订及履行过程中有腐败和欺诈行为的。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    “腐败行为”和“欺诈行为”定义如下：“腐败行为”是指提供/给予/接受或索取任何有价值的东西来影响买受人在合同签订、履行过程中的行为。“欺诈行为”是指为了影响合同签订、履行过程,以谎报事实的方法,损害买受人的利益的行为。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    6.本合同其他条款中买受人有解除权的情形。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "第十一条、不可抗力");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    1. 合同一方因火灾、战争、罢工、自然灾害等不可抗力因素不能履行合同的，应立即通知另一方，并在不可抗力事件发生之日起5日内，将当地市级以上有关政府部门或公证机构出具的证明不可抗力发生的书面文件当面递交或快速邮递给另一方，并于事件发生之日起10日内，向另一方提交导致本合同全部或部分不能履行或迟延履行的证明。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    2.合同双方应采取一切必要措施减少损失，并在不可抗力事件消除后立即恢复本合同的履行，除非此履行已不需要或已不可能。一旦不可抗力事件的影响持续一周以上，买受人有权解除合同。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "第十二条、通知方式");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    1. 本合同任何一方给另一方的通知，都应以信函传递或电子邮件的形式发送，而另一方应以书面形式确认并发送到对方明确的地址。通过特快专递的形式送达作为送达的主要形式。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    2.一方变更通讯地址或通讯方式地址的，应提前5个工作日，将变更后的地址及联系方式通知另一方，否则变更方应对此造成的一切后果承担责任。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    3.使用电子邮件送达之时，邮件中所附的“附件”以扫描件形式发送，具有相同于原件的效力。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    4.双方确认各自的通知方式如下所示，并且其对双方具有完全的法律效力：");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    (1)出卖人指定的通知方式为：信函快递：收信地址为“");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getInboxAddressS()) + " "); // 第十二条、收信地址为__

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "”收信人为“");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getAddresseeS()) + " "); // 第十二条、收信人为__

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "”，电话为“");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getTelephoneS()) + " "); // 第十二条、电话为__

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "”；电子邮件：电子邮件收件邮箱地址为“");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getInboxAddressS()) + " "); // 第十二条、电子邮件收件邮箱地址为__

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "”；");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    (2)买受人指定的通知方式为：信函快递：收信地址为“");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getInboxAddressB()) + " "); // 第十二条、收信地址为__

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "”收信人为“");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getAddresseeB()) + " "); // 第十二条、收信人为__

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "”，收信人电话为“");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getTelephoneB()) + " "); // 第十二条、电话为__

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "”；电子邮件：电子邮件收件邮箱地址为“");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getInboxAddressB()) + " "); // 第十二条、电子邮件收件邮箱地址为__

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "”；");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "第十三条、知识产权和HSE");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    出卖人保证所交付货物不侵犯第三方的权利,若发生侵害第三方权利的情况, 出卖人应负责与第三方交涉, 并承担由此产生的全部法律和经济责任, 并对因为该侵权行为给买受方造成的损失承担赔偿责任。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    出卖人应保证产品设计、生产、制造、包装、运输、交付和售后的整个过程符合HSE标准准则。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "第十四条、保密");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    双方保证对在讨论、签订、执行本协议过程中所获悉的属于对方的且无法自公开渠道获得的文件及资料(包括商业秘密、公司计划、运营活动、财务信息、技术信息、经营信息及其他商业秘密)予以保密。未经该资料和文件的原提供方同意，另一方不得向任何第三方泄露该商业秘密的全部或部分内容。法律、法规另有规定或双方另有约定的除外。一方违反该条款给对方造成损失的，应承担相应的赔偿责任。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "第十五条、合同争议解决方式");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    本合同履行过程中发生争议时，双方应本着真诚合作的精神，通过友好协商解决。协商不成的，按下列第");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getSolution()) + " "); // 第十五条、按下列第__种方式解决

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "种方式解决：");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    (1)提交");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getBoardArbitration()) + " "); // 第十五条、提交__仲裁委员会仲裁

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "仲裁委员会仲裁；");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    (2)依法向买受人所在地人民法院起诉。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    另外，在诉讼期间，合同中未涉及争议部分的条款仍须执行。另外，在诉讼期间，合同中未涉及争议部分的条款仍须履行。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        run.setBold(true);// 字体加粗
        WordUploadUtil.setfont(run, font, family, "第十六条、其他条款");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    1、本合同自双方法定代表人或委托代理人签字并盖章之日起生效。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    2、本合同一式");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getFewCopies()) + " "); // 第十六条、本合同一式__份

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "份，出卖人执");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getSellerFewCopies()) + " "); // 第十六条、出卖人执__份

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "份，买受人执");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getBuyerFewCopies()) + " "); // 第十六条、买受人执__份

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "份，各份具有同等的法律效力。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    3、合同如有未尽事宜，应经双方共同协商，可做出补充规定，补充规定与本合同具有同等法律效力。");

        page = doc.createParagraph();// 换行
        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "    4、合同附件：1.");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getAppendicesName1()) + " "); // 第十六条、合同附件：1.__

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "；2 .");

        run = page.createRun();
        run.setTextPosition(val);
        run.setUnderline(UnderlinePatterns.SINGLE);// 加下划线
        WordUploadUtil.setfont(run, font, family, " " + formatNullStr(purchContractStandard.getAppendicesName2()) + " "); // 第十六条、合同附件：2.__

        run = page.createRun();
        run.setTextPosition(val);
        WordUploadUtil.setfont(run, font, family, "。");

        XWPFTable tb = doc.createTable(1, 2);
        XWPFParagraph p1 = tb.getRow(0).getCell(0).getParagraphs().get(0);
        p1.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun r1 = p1.createRun();
        r1.setTextPosition(val);
        WordUploadUtil.setfont(r1, font, family, "                    出卖人");
        r1.addBreak();//换行

        r1 = p1.createRun();
        r1.setTextPosition(val);
        WordUploadUtil.setfont(r1, font, family, "单位名称（章）：" + formatNullStr(signatoriesList.get(0).getSellerBuyer()));
        r1.addBreak();//换行

        r1 = p1.createRun();
        r1.setTextPosition(val);
        WordUploadUtil.setfont(r1, font, family, "地址：" + formatNullStr(signatoriesList.get(0).getAddress()));
        r1.addBreak();//换行

        r1 = p1.createRun();
        r1.setTextPosition(val);
        WordUploadUtil.setfont(r1, font, family, "邮政编码：" + formatNullStr(signatoriesList.get(0).getPostalCode()));
        r1.addBreak();//换行

        r1 = p1.createRun();
        r1.setTextPosition(val);
        WordUploadUtil.setfont(r1, font, family, "法定代表人：" + formatNullStr(signatoriesList.get(0).getLegalRepresentative()));
        r1.addBreak();//换行

        r1 = p1.createRun();
        r1.setTextPosition(val);
        WordUploadUtil.setfont(r1, font, family, "委托代理人：" + formatNullStr(signatoriesList.get(0).getAgent()));
        r1.addBreak();//换行

        r1 = p1.createRun();
        r1.setTextPosition(val);
        WordUploadUtil.setfont(r1, font, family, "电话：" + formatNullStr(signatoriesList.get(0).getTelephoneFax()));
        r1.addBreak();//换行

        r1 = p1.createRun();
        r1.setTextPosition(val);
        WordUploadUtil.setfont(r1, font, family, "开户行：" + formatNullStr(signatoriesList.get(0).getOpeningBank()));
        r1.addBreak();//换行

        r1 = p1.createRun();
        r1.setTextPosition(val);
        WordUploadUtil.setfont(r1, font, family, "账号：" + formatNullStr(signatoriesList.get(0).getAccountNumber()));
        r1.addBreak();//换行

        r1 = p1.createRun();
        r1.setTextPosition(val);
        WordUploadUtil.setfont(r1, font, family, "税号：" + formatNullStr(signatoriesList.get(0).getCreditCode()));

        XWPFParagraph p2 = tb.getRow(0).getCell(1).getParagraphs().get(0);
        p2.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun r2 = p2.createRun();
        r2.setTextPosition(val);
        WordUploadUtil.setfont(r2, font, family, "                    买受人");
        r2.addBreak();//换行

        r2 = p2.createRun();
        r2.setTextPosition(val);
        WordUploadUtil.setfont(r2, font, family, "单位名称（章）：" + formatNullStr(signatoriesList.get(1).getSellerBuyer()));
        r2.addBreak();//换行

        r2 = p2.createRun();
        r2.setTextPosition(val);
        WordUploadUtil.setfont(r2, font, family, "地址：" + formatNullStr(signatoriesList.get(1).getAddress()));
        r2.addBreak();//换行

        r2 = p2.createRun();
        r2.setTextPosition(val);
        WordUploadUtil.setfont(r2, font, family, "邮政编码：" + formatNullStr(signatoriesList.get(1).getPostalCode()));
        r2.addBreak();//换行

        r2 = p2.createRun();
        r2.setTextPosition(val);
        WordUploadUtil.setfont(r2, font, family, "法定代表人：" + formatNullStr(signatoriesList.get(1).getLegalRepresentative()));
        r2.addBreak();//换行

        r2 = p2.createRun();
        r2.setTextPosition(val);
        WordUploadUtil.setfont(r2, font, family, "委托代理人：" + formatNullStr(signatoriesList.get(1).getAgent()));
        r2.addBreak();//换行

        r2 = p2.createRun();
        r2.setTextPosition(val);
        WordUploadUtil.setfont(r2, font, family, "电话：" + formatNullStr(signatoriesList.get(1).getTelephoneFax()));
        r2.addBreak();//换行

        r2 = p2.createRun();
        r2.setTextPosition(val);
        WordUploadUtil.setfont(r2, font, family, "开户行：" + formatNullStr(signatoriesList.get(1).getOpeningBank()));
        r2.addBreak();//换行

        r2 = p2.createRun();
        r2.setTextPosition(val);
        WordUploadUtil.setfont(r2, font, family, "账号：" + formatNullStr(signatoriesList.get(1).getAccountNumber()));
        r2.addBreak();//换行

        r2 = p2.createRun();
        r2.setTextPosition(val);
        WordUploadUtil.setfont(r2, font, family, "统一社会信用代码证：" + formatNullStr(signatoriesList.get(1).getCreditCode()));

        //表格属性
        CTTbl ttbl = tb.getCTTbl();
        CTTblPr tablePr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        //表格宽度
        CTTblWidth tblWidth = tablePr.isSetTblW() ? tablePr.getTblW() : tablePr.addNewTblW();
        tblWidth.setW(BigInteger.valueOf(8500));
        //设置表格宽度为非自动
        tblWidth.setType(STTblWidth.DXA);

    }

    // null转""
    private String formatNullStr(String str) {
        if (str == null) {
            str = "";
        }
        return str;
    }

    // 去掉后面无用的零
    private String dropZero(String str) {
        if (str.indexOf(".") > 0) {
            str = str.replaceAll("0+?$", "");//去掉后面无用的零
            str = str.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return str;
    }


}
