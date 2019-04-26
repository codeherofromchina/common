package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.erui.comm.NewDateUtil;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.event.OrderProgressEvent;
import com.erui.order.event.PurchDoneCheckEvent;
import com.erui.order.event.TasksAddEvent;
import com.erui.order.requestVo.PurchParam;
import com.erui.order.service.*;
import com.erui.order.util.BpmUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
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
public class PurchV2ServiceImpl implements PurchV2Service {
    private static Logger LOGGER = LoggerFactory.getLogger(PurchV2ServiceImpl.class);
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private PurchDao purchDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private PurchRequisitionDao purchRequisitionDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private PurchGoodsDao purchGoodsDao;
    @Autowired
    private PurchPaymentDao purchPaymentDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private AttachmentDao attachmentDao;
    @Autowired
    private BackLogService backLogService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CheckLogService checkLogService;
    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口
    @Value("#{orderProp[DING_SEND_SMS]}")
    private String dingSendSms;  //发钉钉通知接口


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
            List<Attachment> attachments = attachmentDao.findByRelObjIdAndCategory(puch.getId(), Attachment.AttachmentCategory.PURCH.getCode());
            if (attachments != null && attachments.size() > 0) {
                puch.setAttachments(attachments);
                puch.getAttachments().size(); // 获取采购的附件信息
            }
            List<PurchGoods> purchGoodsList = puch.getPurchGoodsList();
            if (purchGoodsList.size() > 0) {
                for (PurchGoods purchGoods : purchGoodsList) {
                    purchGoods.getGoods().setPurchGoods(null);
                }
            }
            List<String> projectNoList = new ArrayList<>();
            List<String> teachcals = new ArrayList<>();
            for (Project p : puch.getProjects()) {
                projectNoList.add(p.getProjectNo());
                teachcals.add(p.getBusinessUid().toString());
            }
            puch.setProjectNos(StringUtils.join(projectNoList, ","));
            return puch;
        }
        return null;
    }

    /**
     * 新增采购单
     *
     * @param purch
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(Purch purch) throws Exception {
        String eruiToken = (String) ThreadLocalUtil.getObject();
        Date now = new Date();
        String lastedByPurchNo = purchDao.findLastedByPurchNo();

        for (int n = 0; n < 20; n++) {
            lastedByPurchNo = StringUtil.genPurchNo(lastedByPurchNo);
            // 判断最多20此，如果还是重复则报错
            Long count = purchDao.findCountByPurchNo(lastedByPurchNo);
            if (count != null && count == 0) {
                break;
            } else if (n == 19) {
                throw new Exception(String.format("%s%s%s", "采购合同号重复", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Repeat purchase contract number"));
            }
            // 设置基础数据 自动生成采购合同号
        }
        purch.setPurchNo(lastedByPurchNo);
        purch.setSigningDate(NewDateUtil.getDate(purch.getSigningDate()));
        purch.setArrivalDate(NewDateUtil.getDate(purch.getArrivalDate()));
        purch.setCreateTime(now);

        // 处理结算方式,新增，所以将所有id设置为null，并添加新增时间
        purch.getPurchPaymentList().parallelStream().forEach(vo -> {
            vo.setId(null);
            vo.setCreateTime(now);
        });
        // 处理商品信息
        List<PurchGoods> purchGoodsList = new ArrayList<>();
        Set<Project> projectSet = new HashSet<>();
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
                if (!goods.getOrder().getOrderCategory().equals(6)) {
                    applicationContext.publishEvent(new OrderProgressEvent(goods.getOrder(), 3, eruiToken));
                }
            }
            // 增加预采购数量
            goods.setPrePurchsedNum(goods.getPrePurchsedNum() + intPurchaseNum);
            // 直接更新商品，放置循环中存在多次修改同一个商品错误
            goodsDao.save(goods);
        }
        if (purchGoodsList.size() == 0) {
            throw new Exception(String.format("%s%s%s", "必须存在要采购的商品", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "There must be goods to be purchased"));
        }
        purch.setPurchGoodsList(purchGoodsList);
        List<Project> projectList = new ArrayList<>(projectSet);
        purch.setProjects(projectList);
        // 采购审批添加部分
        if (purch.getStatus() == Purch.StatusEnum.READY.getCode()) {
            purch.setAuditingStatus(0);
        } else if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
            // 提交采购
            purch.setAuditingStatus(1);
        }
        CheckLog checkLog_i = null; //审批流日志

        Purch save = purchDao.save(purch);
        // 添加附件
        //purchRequisition1.setAttachmentList(purchRequisition.getAttachmentList());
        if (purch.getAttachments() != null && purch.getAttachments().size() > 0) {
            attachmentService.addAttachments(purch.getAttachments(), save.getId(), Attachment.AttachmentCategory.PURCH.getCode());
        }
        if (save.getStatus() == Purch.StatusEnum.BEING.getCode()) {
            if (save.getPurchAuditerId() != null) {
                sendDingtalk(purch, purch.getPurchAuditerId().toString(), false);
            }
            // 提交业务流的采购合同订单流程
            Map<String, Object> bpmInitVar = new HashMap<>();
            bpmInitVar.put("order_amount", purch.getTotalPrice().doubleValue()); // 总采购订单金额
            bpmInitVar.put("is_standard_version", StringUtils.equals("1", purch.getContractVersion()) ? "Y" : "N"); // 标准版本
            JSONObject processResp = BpmUtils.startProcessInstanceByKey("process_purchaseorder", null, eruiToken, "purch:" + purch.getId(), bpmInitVar);

            save.setProcessId(processResp.getString("instanceId"));
        }
        if (save.getStatus() == 2) {
            List<Project> projects = save.getProjects();
            Set<String> projectNoSet = new HashSet<>();
            if (projects.size() > 0) {
                for (Project project : projects) {
                    String projectNo = project.getProjectNo();
                    if (projectNo != null) {
                        projectNoSet.add(projectNo);
                    }
                }
            }
        }

        // 检查项目是否已经采购完成
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
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Purch purch) throws Exception {
        String eruiToken = (String) ThreadLocalUtil.getObject();
        Purch dbPurch = findDetailInfo(purch.getId());
        // 之前的采购必须不能为空且未提交状态
        if (dbPurch == null || dbPurch.getDeleteFlag()) {
            throw new Exception(String.format("%s%s%s", "采购信息不存在", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Procurement information does not exist"));
        } else if (dbPurch.getStatus() != Purch.StatusEnum.BEING.getCode() && dbPurch.getAuditingStatus() == 4) {
            throw new Exception(String.format("%s%s%s", "采购信息已提交不能修改", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Purchase information has been submitted and can not be modified"));
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
                return payment;
            }
            PurchPayment payment2 = collect.remove(paymentId);
            payment2.setReceiptDate(payment.getReceiptDate());
            payment2.setType(payment.getType());
            payment2.setMoney(payment.getMoney());
            payment2.setTitle(payment.getTitle());
            return payment2;
        }).collect(Collectors.toList());
        dbPurch.setPurchPaymentList(paymentList);
        // 删除废弃的结算方式
        if (collect.size() > 0) {
            purchPaymentDao.delete(collect.values());
        }
        // 处理附件信息
        //List<Attachment> attachmentlist = attachmentService.handleParamAttachment(dbPurch.getAttachments(), purch.getAttachments(), purch.getCreateUserId(), purch.getCreateUserName());
        //dbPurch.setAttachments(attachmentlist);

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
                    if (!goods.getOrder().getOrderCategory().equals(6)) {
                        applicationContext.publishEvent(new OrderProgressEvent(goods.getOrder(), 3, eruiToken));
                    }
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
                String currencyBn = purch.getCurrencyBn();
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
                    handleExchangedPurchGoods(project, goods, dbPurch, purchGoods, son);
                    purchGoodsList.add(son);
                }
                // 提交则修改商品的已采购数量
                if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
                    goods.setPurchasedNum(goods.getPurchasedNum() + purchaseNum);
                    // 设置商品的项目跟踪信息
                    setGoodsTraceData(goods, purch);
                    if (!goods.getOrder().getOrderCategory().equals(6)) {
                        applicationContext.publishEvent(new OrderProgressEvent(goods.getOrder(), 3, eruiToken));
                    }
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
        dbPurch.setPurchGoodsList(purchGoodsList);
        List<Project> projectList = new ArrayList<>(projectSet);
        dbPurch.setProjects(projectList);

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
        // 更新采购单
       /* if (dbPurch.getProjects().size() > 0 && dbPurch.getProjects().get(0).getOrderCategory().equals(6) && purch.getStatus() > 1) {
            dbPurch.setStatus(3);
        }*/
        // 采购审批添加部分
        if (purch.getStatus() == Purch.StatusEnum.READY.getCode()) {
            dbPurch.setAuditingStatus(0);
        } else if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
            dbPurch.setAuditingStatus(1);
        }
        CheckLog checkLog_i = null; //审批流日志

        Purch save = purchDao.save(dbPurch);
        // 处理附件信息 attachmentList 库里存在附件列表 dbAttahmentsMap前端传来参数附件列表
        //save.setAttachmentList(save.getAttachmentList());
        List<Attachment> attachmentList = new ArrayList<>();
        if (purch.getAttachments() != null) {
            attachmentList = purch.getAttachments();
        }
        Map<Integer, Attachment> dbAttahmentsMap = dbPurch.getAttachments().parallelStream().collect(Collectors.toMap(Attachment::getId, vo -> vo));
        attachmentService.updateAttachments(attachmentList, dbAttahmentsMap, dbPurch.getId(), Attachment.AttachmentCategory.PURCH.getCode());

        if (save.getStatus() == Purch.StatusEnum.BEING.getCode()) {
            checkLog_i = orderService.fullCheckLogInfo(null, CheckLog.checkLogCategory.PURCH.getCode(), save.getId(), 20, save.getCreateUserId(), save.getCreateUserName(), save.getAuditingProcess().toString(), save.getPurchAuditerId().toString(), save.getAuditingReason(), "1", 3);
            checkLogService.insert(checkLog_i);
            if (save.getPurchAuditerId() != null) {
                sendDingtalk(purch, purch.getPurchAuditerId().toString(), false);
            }
            // 启动采购合同订单流程实例（process_purchaseorder）
            Map<String, Object> bpmInitVar = new HashMap<>();
            bpmInitVar.put("order_amount", purch.getTotalPrice().doubleValue()); // 总采购订单金额
            bpmInitVar.put("is_standard_version", StringUtils.equals("1", purch.getContractVersion()) ? "Y" : "N"); // 标准版本
            JSONObject processResp = BpmUtils.startProcessInstanceByKey("process_purchaseorder", null, eruiToken, "purch:" + purch.getId(), bpmInitVar);

            save.setProcessId(processResp.getString("instanceId"));
        }
        if (save.getStatus() == 2) {
            List<Project> projects = save.getProjects();
            Set<String> projectNoSet = new HashSet<>();
            if (projects.size() > 0) {
                for (Project project : projects) {
                    String projectNo = project.getProjectNo();
                    if (projectNo != null) {
                        projectNoSet.add(projectNo);
                    }
                }

            }

        }

        // 检查项目是否已经采购完成
        List<Integer> projectIdList = projectSet.parallelStream().map(Project::getId).collect(Collectors.toList());
        checkProjectPurchDone(projectIdList);
        return true;
    }


    //钉钉通知 审批人
    private void sendDingtalk(Purch purch, String user, boolean rejectFlag) {
        //获取token
        final String eruiToken = (String) ThreadLocalUtil.getObject();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("发送短信的用户token:" + eruiToken);
                // 根据id获取商务经办人信息
                String jsonParam = "{\"id\":\"" + user + "\"}";
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                String userInfo = HttpRequest.sendPost(memberInformation, jsonParam, header);
                LOGGER.info("人员详情返回信息：" + userInfo);
                //钉钉通知接口头信息
                Map<String, String> header2 = new HashMap<>();
                header2.put("Cookie", eruiToken);
                header2.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                JSONObject jsonObject = JSONObject.parseObject(userInfo);
                Integer code = jsonObject.getInteger("code");
                String userNo = null;
                String userName = null;  //商务经办人手机号
                if (code == 1) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    //获取通知者姓名员工编号
                    //userName = data.getString("name");
                    userNo = data.getString("user_no");
                    Long startTime = System.currentTimeMillis();
                    Date sendTime = new Date(startTime);
                    String sendTime02 = DateUtil.format(DateUtil.FULL_FORMAT_STR, sendTime);
                    //发送钉钉通知
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("toUser=").append(userNo);
                    if (!rejectFlag) {
                        stringBuffer.append("&message=您好！" + purch.getAgentName() + "的采购合同，已申请合同审批。采购合同号:" + purch.getPurchNo() + "，请您登录BOSS系统及时处理。感谢您对我们的支持与信任！" +
                                "" + sendTime02 + "");
                    } else {
                        stringBuffer.append("&message=您好！" + purch.getAgentName() + "的采购合同，已申请的合同审核未通过。采购合同号:" + purch.getPurchNo() + "，请您登录BOSS系统及时处理。感谢您对我们的支持与信任！" +
                                "" + sendTime02 + "");
                    }
                    stringBuffer.append("&type=userNo");
                    String s1 = HttpRequest.sendPost(dingSendSms, stringBuffer.toString(), header2);
                    LOGGER.info("发送钉钉通知返回状态" + s1);
                }
            }
        }).start();

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
        String currencyBn = purch.getCurrencyBn();
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
     * 检查采购项目的采购数量是否已完毕
     *
     * @param projectIds 项目ID列表
     */
    private void checkProjectPurchDone(List<Integer> projectIds) throws Exception {
        applicationContext.publishEvent(new PurchDoneCheckEvent(this, projectIds, projectDao, backLogService, orderDao, purchRequisitionDao));
    }

}
