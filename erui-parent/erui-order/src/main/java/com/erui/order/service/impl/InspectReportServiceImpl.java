package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONArray;
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
import com.erui.order.event.OrderProgressEvent;
import com.erui.order.event.TasksAddEvent;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.BackLogService;
import com.erui.order.service.InspectReportService;
import org.apache.commons.lang3.StringUtils;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class InspectReportServiceImpl implements InspectReportService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private InspectReportDao inspectReportDao;

    @Autowired
    private InspectApplyDao inspectApplyDao;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private PurchDao purchDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private InstockDao instockDao;

    @Autowired
    private PurchGoodsDao purchGoodsDao;

    @Autowired
    private PurchServiceImpl purchServiceImpl;

    @Autowired
    private BackLogService backLogService;

    @Autowired
    private PurchContractDao purchContractDao;

    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口


    @Value("#{orderProp[SEND_SMS]}")
    private String sendSms;  //发短信接口

    @Value("#{orderProp[MEMBER_LIST]}")
    private String memberList;  //查询人员信息调用接口

    @Override
    @Transactional(readOnly = true)
    public InspectReport findById(Integer id) {
        return inspectReportDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public InspectReport detail(Integer id) {
        InspectReport inspectReport = inspectReportDao.findOne(id);
        if (inspectReport != null) {
            if (inspectReport.getId() != null) {
                List<Attachment> attachments = attachmentService.queryAttachs(inspectReport.getId(), Attachment.AttachmentCategory.INSPECTREPORT.getCode());
                if (attachments != null && attachments.size() > 0) {
                    inspectReport.setAttachments(attachments);
                    inspectReport.getAttachments().size();
                }
            }
            inspectReport.getInspectGoodsList().size();
            InspectApply inspectApply = inspectReport.getInspectApply();
            inspectReport.setPurchNo(inspectApply.getPurchNo());
        }

        return inspectReport;
    }

    /**
     * 按照条件分页查找
     *
     * @param condition
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InspectReport> listByPage(InspectReport condition) {

        PageRequest request = new PageRequest(condition.getPage() - 1, condition.getPageSize(), Sort.Direction.DESC, "createTime");

        Page<InspectReport> page = inspectReportDao.findAll(new Specification<InspectReport>() {
            @Override
            public Predicate toPredicate(Root<InspectReport> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 报检单单号模糊查询
                if (StringUtils.isNotBlank(condition.getInspectApplyNo())) {
                    list.add(cb.like(root.get("inspectApplyNo").as(String.class), "%" + condition.getInspectApplyNo() + "%"));
                }

                Join<InspectReport, InspectApply> inspectApply = root.join("inspectApply");
                list.add(cb.equal(inspectApply.get("master").as(Boolean.class), Boolean.TRUE)); // 只查询主质检单
                if (!(StringUtils.isBlank(condition.getProjectNo()) && StringUtils.isBlank(condition.getContractNo()))) {
                    CriteriaBuilder.In<Object> idIn = cb.in(inspectApply.get("id"));
                    Set<InspectApply> inspectApplySet = findByProjectNoAndContractNo(condition.getProjectNo(), condition.getContractNo());
                    if (inspectApplySet != null && inspectApplySet.size() > 0) {
                        for (InspectApply p : inspectApplySet) {
                            idIn.value(p.getId());
                        }
                    } else {
                        idIn.value(-1);
                    }
                    list.add(idIn);
                }

                // 最后质检完成时间过滤
                if (condition.getDoneDate() != null) {
                    list.add(cb.equal(root.get("lastDoneDate").as(Date.class), NewDateUtil.getDate(condition.getDoneDate())));
                }

                // 报检时间过滤
                if (condition.getInspectDate() != null) {
                    list.add(cb.equal(inspectApply.get("inspectDate").as(Date.class), NewDateUtil.getDate(condition.getInspectDate())));
                }

                // 质检检验时间过滤
                if (condition.getCheckDate() != null) {
                    list.add(cb.equal(root.get("checkDate").as(Date.class), NewDateUtil.getDate(condition.getCheckDate())));
                }

                // 质检次数过滤
                if (condition.getCheckTimes() != null) {
                    list.add(cb.equal(root.get("checkTimes").as(Integer.class), condition.getCheckTimes()));
                }


                // 根据采购合同号模糊查询
                if (StringUtils.isNotBlank(condition.getPurchNo())) {
                    list.add(cb.like(inspectApply.get("purchNo").as(String.class), "%" + condition.getPurchNo() + "%"));
                }

                // 供应商名称模糊查询
                if (StringUtils.isNotBlank(condition.getSupplierName())) {
                    list.add(cb.like(root.get("supplierName").as(String.class), "%" + condition.getSupplierName() + "%"));
                }

                // 检验员查询
                if (condition.getCheckUserId() != null) {
                    list.add(cb.equal(root.get("checkUserId").as(Integer.class), condition.getCheckUserId()));
                }

                // 是否外检查询
                if (condition.getDirect() != null) {
                    list.add(cb.equal(inspectApply.get("direct"), condition.getDirect()));
                }

                // 质检状态查询
                if (condition.getProcess() != null) {
                    list.add(cb.equal(root.get("process").as(Boolean.class), condition.getProcess()));
                }
                // 不显示不需要质检的QRL1
                list.add(cb.equal(root.get("isShow").as(Integer.class), 1));

                // 只查询是第一次报检单的质检信息
                list.add(cb.equal(root.get("reportFirst"), Boolean.TRUE));

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);
        if (page.hasContent()) {
            // 获取报检单和商品信息
            page.getContent().stream().forEach(inspectReport -> {
                inspectReport.getInspectApply().getPurchNo();
                // 销售合同号,保持顺序用list
                List<String> contractNoList = new ArrayList<String>();
                // 项目号,保持顺序用list
                List<String> projectNoList = new ArrayList<String>();
                inspectReport.getInspectGoodsList().forEach(vo -> {
                    Goods goods = vo.getGoods();
                    String contractNo = goods.getContractNo();
                    String projectNo = goods.getProjectNo();
                    if (!contractNoList.contains(contractNo)) {
                        contractNoList.add(contractNo);
                    }
                    if (!projectNoList.contains(projectNo)) {
                        projectNoList.add(projectNo);
                    }
                });
                inspectReport.setContractNo(StringUtils.join(contractNoList, ","));
                inspectReport.setProjectNo(StringUtils.join(projectNoList, ","));
                inspectReport.setInspectApplyID(inspectReport.getInspectApply().getId());
            });
        }


        return page;
    }


    // 根据销售号和项目号查询报检列表信息
    private Set<InspectApply> findByProjectNoAndContractNo(String projectNo, String contractNo) {
        Set<InspectApply> result = null;
        if (!(StringUtils.isBlank(projectNo) && StringUtils.isBlank(contractNo))) {
            List<InspectApply> list = inspectApplyDao.findAll(new Specification<InspectApply>() {

                @Override
                public Predicate toPredicate(Root<InspectApply> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<>();


                    Join<InspectApply, InspectApplyGoods> inspectApplyGoods = root.join("inspectApplyGoodsList");
                    Join<InspectApplyGoods, Goods> goods = inspectApplyGoods.join("goods");

                    // 销售合同号模糊查询
                    if (StringUtils.isNotBlank(contractNo)) {
                        list.add(cb.like(goods.get("contractNo").as(String.class), "%" + contractNo + "%"));
                    }

                    // 根据项目号模糊查询
                    if (StringUtils.isNotBlank(projectNo)) {
                        list.add(cb.like(goods.get("projectNo").as(String.class), "%" + projectNo + "%"));
                    }


                    Predicate[] predicates = new Predicate[list.size()];
                    predicates = list.toArray(predicates);
                    return cb.and(predicates);
                }
            });
            if (list != null) {
                result = new HashSet<>(list);
            }
        }


        return result;
    }


    /**
     * 保存质检单
     *
     * @param inspectReport
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(InspectReport inspectReport) throws Exception {
        InspectReport dbInspectReport = detail(inspectReport.getId());
        if (dbInspectReport == null) {
            throw new Exception(String.format("%s%s%s", "质检单不存在", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The quality check list does not exist"));
        }
        InspectReport.StatusEnum statusEnum = InspectReport.StatusEnum.fromCode(inspectReport.getStatus());
        if (statusEnum == null || statusEnum == InspectReport.StatusEnum.INIT) {
            throw new Exception(String.format("%s%s%s", "状态提交错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "State submission error"));

        }
        if (dbInspectReport.getStatus() == InspectReport.StatusEnum.DONE.getCode()) {
            throw new Exception(String.format("%s%s%s", "质检单已提交，不可修改", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The quality check list has been submitted and can not be modified"));
        }

        // 处理基本数据
        dbInspectReport.setCheckUserId(inspectReport.getCheckUserId());
        dbInspectReport.setCheckUserName(inspectReport.getCheckUserName());
        dbInspectReport.setCheckDeptId(inspectReport.getCheckDeptId());
        dbInspectReport.setCheckDeptName(inspectReport.getCheckDeptName());
        dbInspectReport.setCheckDate(inspectReport.getCheckDate());
        dbInspectReport.setDoneDate(inspectReport.getDoneDate());
        dbInspectReport.setLastDoneDate(inspectReport.getDoneDate());
        dbInspectReport.setReportRemarks(inspectReport.getReportRemarks());
        dbInspectReport.setStatus(statusEnum.getCode());

        // 处理附件信息
        //List<Attachment> attachments = attachmentService.handleParamAttachment(dbInspectReport.getAttachments(), inspectReport.getAttachments(), inspectReport.getCreateUserId(), inspectReport.getCreateUserName());
        //dbInspectReport.setAttachments(attachments);

        // 处理商品信息
        Map<Integer, InspectApplyGoods> inspectGoodsMap = inspectReport.getInspectGoodsList().parallelStream().
                collect(Collectors.toMap(InspectApplyGoods::getId, vo -> vo)); // 参数的质检商品
        List<InspectApplyGoods> inspectGoodsList = dbInspectReport.getInspectGoodsList(); // 数据库原来报检商品
        if (inspectGoodsMap.size() != inspectGoodsList.size()) {
            throw new Exception(String.format("%s%s%s", "传入质检商品数量不正确", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The number of commodities introduced to the quality inspection is incorrect"));
        }
        boolean hegeFlag = true;
        int hegeNum = 0;    // 合格商品总数量

        int sum = 0;  // 不合格商品总数量
        Project project = null; // 项目信息
        Integer count = 0;
        for (InspectApplyGoods applyGoods : inspectGoodsList) {
            InspectApplyGoods paramApplyGoods = inspectGoodsMap.get(applyGoods.getId());
            if (paramApplyGoods == null) {
                throw new Exception(String.format("%s%s%s", "传入质检商品不正确", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Incorrect introduction of quality inspection goods"));

            }

            PurchGoods purchGoods = applyGoods.getPurchGoods();
            Goods goods = purchGoods.getGoods();

            Integer samples = paramApplyGoods.getSamples();
            Integer unqualified = paramApplyGoods.getUnqualified();
            //如果有不合格数量时自动生成ncr编号
            if (statusEnum == InspectReport.StatusEnum.DONE && unqualified > 0) {
                if (count < 1) {
                    // 根据数据库中最后的发货通知单单号重新自动生成
                    String prefix = "20";
                    String oldNcrNo = inspectReportDao.findLaseNcrNo(prefix);
                    dbInspectReport.setNcrNo(StringUtil.genNCR(oldNcrNo));
                }
                count++;
            }
            if (samples == null || (samples <= 0 && (purchGoods.getQualityInspectType() == null || !"QRL1".equals(purchGoods.getQualityInspectType().trim())))) {
                throw new Exception(String.format("%s%s%s", "抽样数错误【SKU:" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Sampling error [SKU:" + goods.getSku() + "]"));

            }
            if (unqualified == null || unqualified < 0 || unqualified > samples) {
                throw new Exception(String.format("%s%s%s", "不合格数据错误【SKU:" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Unqualified data error [SKU:" + goods.getSku() + "]"));

            }
            if (unqualified > 0) {
                sum += unqualified;
                hegeFlag = false;
            }
            applyGoods.setSamples(samples);
            applyGoods.setUnqualified(unqualified);
            // 如果有不合格商品，则必须有不合格描述
            if (!hegeFlag && StringUtils.isBlank(paramApplyGoods.getUnqualifiedDesc()) && unqualified > 0) {
                throw new Exception(String.format("%s%s%s", "商品(SKU:" + goods.getSku() + ")的不合格描述不能为空", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The nonconforming description of a commodity (SKU:" + goods.getSku() + ") can not be empty"));
            }
            applyGoods.setUnqualifiedDesc(paramApplyGoods.getUnqualifiedDesc());
            // 如果有不合格商品，则必须有不合格类型
            if (!hegeFlag && (paramApplyGoods.getUnqualifiedType() == null || paramApplyGoods.getUnqualifiedType() == 0) && unqualified > 0) {
                throw new Exception(String.format("%s%s%s", "商品(SKU:" + goods.getSku() + ")的不合格类型不能为空", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Unqualified Types of Unqualified Commodities (SKU:" + goods.getSku() + ") can not be empty"));
            }
            applyGoods.setUnqualifiedType(paramApplyGoods.getUnqualifiedType());
            // 设置采购商品的已合格数量
            if (statusEnum == InspectReport.StatusEnum.DONE) { // 提交动作

                project = project == null ? goods.getProject() : project;
                // 合格数量
                int qualifiedNum = applyGoods.getInspectNum() - unqualified;
                hegeNum += qualifiedNum; // 统计合格总数量
                if (qualifiedNum < 0) {
                    throw new Exception(String.format("%s%s%s", "传入不合格数量参数不正确【SKU:" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Incoming unqualified quantity parameter is incorrect [SKU:" + goods.getSku() + "]"));

                }
                if (purchGoods.getInspectNum() < purchGoods.getGoodNum() + qualifiedNum) {
                    // 合格数量大于报检数量，数据错误
                    throw new Exception(String.format("%s%s%s", "采购的合格数量错误【purchGoodsId:" + purchGoods.getId() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Defective quantity of Procurement [purchGoodsId:" + purchGoods.getId() + "]"));

                }
                purchGoods.setGoodNum(purchGoods.getGoodNum() + qualifiedNum);
                purchGoodsDao.save(purchGoods);

                if (goods.getCheckUerId() == null) {
                    goods.setCheckUerId(dbInspectReport.getCheckUserId());
                }
                if (goods.getCheckDate() == null) {
                    // 检验时间
                    goods.setCheckDate(dbInspectReport.getCheckDate());
                }
                goods.setCheckUserName(dbInspectReport.getCheckUserName());
                if (dbInspectReport.getDoneDate() != null) {
                    // 检验完成时间
                    goods.setCheckDoneDate(dbInspectReport.getDoneDate());
                }
                goodsDao.save(goods);
            }
        }

        // 设置父质检单的最后检验完成日期
        InspectApply inspectApply = dbInspectReport.getInspectApply();
        InspectApply inspectApplyParent = inspectApply.getParent();
        if (!dbInspectReport.getReportFirst()) {
            InspectReport firstInspectReport = inspectReportDao.findByInspectApplyId(inspectApplyParent.getId());
            firstInspectReport.setLastDoneDate(dbInspectReport.getDoneDate());
            inspectReportDao.save(firstInspectReport);
        }


        // 提交动作 则设置第一次质检，和相应的报检信息
        if (statusEnum == InspectReport.StatusEnum.DONE) {

            //入库质检结果通知：质检人员将不合格商品通知采购经办人
            disposeData(hegeFlag, hegeNum, sum, dbInspectReport, project);

            dbInspectReport.setProcess(false);
            if (hegeFlag && !dbInspectReport.getReportFirst()) {
                // 将第一次质检设置为完成
                InspectReport firstInspectReport = inspectReportDao.findByInspectApplyId(inspectApplyParent.getId());
                firstInspectReport.setProcess(false);
                inspectReportDao.save(firstInspectReport);
            } else if (dbInspectReport.getReportFirst() && !hegeFlag) {
                dbInspectReport.setProcess(true);
            }
            if (inspectApplyParent != null) {
                inspectApplyParent.setPubStatus(hegeFlag ? InspectApply.StatusEnum.QUALIFIED.getCode() : InspectApply.StatusEnum.UNQUALIFIED.getCode());
                inspectApplyDao.save(inspectApplyParent);

            }
            inspectApply.setStatus(hegeFlag ? InspectApply.StatusEnum.QUALIFIED.getCode() : InspectApply.StatusEnum.UNQUALIFIED.getCode());
            inspectApply.setPubStatus(hegeFlag ? InspectApply.StatusEnum.QUALIFIED.getCode() : InspectApply.StatusEnum.UNQUALIFIED.getCode());
            inspectApplyDao.save(inspectApply);

        }
        InspectReport save1 = inspectReportDao.save(dbInspectReport);
        //附件处理
        List<Attachment> attachmentList = null;
        if (inspectReport.getAttachments() != null && inspectReport.getAttachments().size() > 0) {
            attachmentList = inspectReport.getAttachments();
        } else {
            attachmentList = new ArrayList<>();
        }
        Map<Integer, Attachment> dbAttahmentsMap = new HashMap<>();
        if (dbInspectReport.getAttachments() != null && dbInspectReport.getAttachments().size() > 0) {
            dbAttahmentsMap = dbInspectReport.getAttachments().parallelStream().collect(Collectors.toMap(Attachment::getId, vo -> vo));
        }
        attachmentService.updateAttachments(attachmentList, dbAttahmentsMap, dbInspectReport.getId(), Attachment.AttachmentCategory.INSPECTREPORT.getCode());

        if (statusEnum == InspectReport.StatusEnum.DONE) { // 提交动作
            //质检以后，删除   “办理入库质检”  待办提示
            BackLog backLog2 = new BackLog();
            backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.INSPECTREPORT.getNum());    //功能访问路径标识
            backLog2.setHostId(save1.getId());
            backLogService.updateBackLogByDelYn(backLog2);

        }

        // 最后判断采购是否完成，且存在合格的商品数量
        if (statusEnum == InspectReport.StatusEnum.DONE && hegeNum > 0) {
            Purch purch = dbInspectReport.getInspectApply().getPurch();
            List<PurchGoods> purchGoodsList = purch.getPurchGoodsList();
            boolean doneFlag = true;
            for (PurchGoods pg : purchGoodsList) {
                if (pg.getGoodNum() < pg.getPurchaseNum()) {
                    doneFlag = false;
                    break;
                }
            }
            //采购订单付款方式
            List<PurchPayment> purchPaymentList = purch.getPurchPaymentList();
            Date doneDate = dbInspectReport.getDoneDate();
            for (PurchPayment pay : purchPaymentList) {
                //当付款方式为质保金时回执付款日期 当前质检日期加上质保金天数
                if (pay.getType() == 5) {
                    //加的天数
                    int days = pay.getDays();
                    Date dateAfter = DateUtil.getDateAfter(doneDate, days);
                    pay.setReceiptDate(dateAfter);
                }
            }
            purch.setPurchPaymentList(purchPaymentList);
            if (doneFlag) {
                purch.setStatus(Purch.StatusEnum.DONE.getCode());
                purchDao.save(purch);
                //当全部采购完成时设置供应商状态为COMPLETED
                purchServiceImpl.updateSupplierStatus(purch.getId(), "COMPLETED");
                //全部质检合格以后，删除   “办理报检单”  待办提示
                BackLog backLog = new BackLog();
                backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.INSPECTAPPLY.getNum());    //功能访问路径标识
                backLog.setHostId(purch.getId());
                backLogService.updateBackLogByDelYn(backLog);

            } else {
                //当部分采购时设置供应商状态为PART_RECEIPT
                purchServiceImpl.updateSupplierStatus(purch.getId(), "PART_RECEIPT");
                purchDao.save(purch);
            }
            // 推送数据到入库部门
            Instock instock = new Instock();
            instock.setInspectReport(dbInspectReport);
            instock.setInspectApplyNo(dbInspectReport.getInspectApplyNo()); // 报检单号
            instock.setSupplierName(dbInspectReport.getInspectApply().getPurch().getSupplierName()); // 供应商
            instock.setStatus(Instock.StatusEnum.INIT.getStatus());
            instock.setCreateTime(new Date());
            List<InstockGoods> instockGoodsList = new ArrayList<>();

            // 入库商品
            for (InspectApplyGoods inspectGoods : dbInspectReport.getInspectGoodsList()) {
                int qualifiedNum = inspectGoods.getInspectNum() - inspectGoods.getUnqualified();
                Goods goods = inspectGoods.getGoods();
                if (qualifiedNum > 0) {
                    InstockGoods instockGoods = new InstockGoods();
                    instockGoods.setInstock(instock);
                    instockGoods.setContractNo(goods.getContractNo());
                    instockGoods.setProjectNo(goods.getProjectNo());
                    instockGoods.setInspectApplyGoods(inspectGoods);
                    instockGoods.setQualifiedNum(qualifiedNum);
                    instockGoods.setInstockNum(instockGoods.getQualifiedNum()); // 入库数量
                    Date date = new Date();
                    instockGoods.setCreateTime(date);
                    instockGoods.setUpdateTime(date);
                    instockGoods.setCreateUserId(dbInspectReport.getCreateUserId());
                    instockGoodsList.add(instockGoods);
                }
            }
            instock.setInstockGoodsList(instockGoodsList);
            instock.setOutCheck(1); //是否外检（ 0：否   1：是）
            Instock save = instockDao.save(instock);

            Set<String> projectNoSet = new HashSet<>();
            List<InstockGoods> instockGoodsLists = save.getInstockGoodsList();
            instockGoodsLists.stream().forEach(instockGoods -> {
                PurchGoods purchGoods = instockGoods.getInspectApplyGoods().getPurchGoods();
                Goods goods = purchGoods.getGoods();
                if (StringUtil.isNotBlank(goods.getProjectNo())) {
                    projectNoSet.add(goods.getProjectNo());
                }
            });

            if (hegeNum != 0) {
                //质检合格提交以后  通知分单员办理入库/分单
                List<Integer> listAll = new ArrayList<>(); //分单员id

                //获取token
                String eruiToken = (String) ThreadLocalUtil.getObject();
                if (StringUtils.isNotBlank(eruiToken)) {
                    Map<String, String> header = new HashMap<>();
                    header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                    header.put("Content-Type", "application/json");
                    header.put("accept", "*/*");
                    try {
                        //获取仓库分单员
                        String jsonParam = "{\"role_no\":\"O019\"}";
                        String s2 = HttpRequest.sendPost(memberList, jsonParam, header);
                        logger.info("人员详情返回信息：" + s2);

                        // 获取人员手机号
                        JSONObject jsonObjects = JSONObject.parseObject(s2);
                        Integer codes = jsonObjects.getInteger("code");
                        if (codes == 1) {    //判断请求是否成功
                            // 获取数据信息
                            JSONArray data1 = jsonObjects.getJSONArray("data");
                            for (int i = 0; i < data1.size(); i++) {
                                JSONObject ob = (JSONObject) data1.get(i);
                                listAll.add(ob.getInteger("id"));    //获取物流分单员id
                            }
                        } else {
                            throw new Exception("出库分单员待办事项推送失败");
                        }
                    } catch (Exception e) {
                        throw new Exception("出库分单员待办事项推送失败");
                    }
                }
                if (listAll.size() > 0) {
                    String inspectApplyNo = save1.getInspectApplyNo();  //报检单号
                    String supplierName = save1.getSupplierName();  //供应商名称
                    String infoContent = StringUtils.join(projectNoSet, ",") + " | " + supplierName;  //提示内容
                    Integer hostId = save.getId();
                    Integer[] hostIdArr = listAll.toArray(new Integer[listAll.size()]);
                    // 推送待办事件
                    applicationContext.publishEvent(new TasksAddEvent(applicationContext, backLogService,
                            BackLog.ProjectStatusEnum.INSTOCKSUBMENU,
                            inspectApplyNo,
                            infoContent,
                            hostId,
                            0,
                            hostIdArr));
                }
            }

        }

        // 流程进度推送
        if (statusEnum == InspectReport.StatusEnum.DONE) {
            String token = (String) ThreadLocalUtil.getObject();
            for (InspectApplyGoods inspectGoods : dbInspectReport.getInspectGoodsList()) {
                Goods goods = inspectGoods.getGoods();
                applicationContext.publishEvent(new OrderProgressEvent(goods.getOrder(), 5, token));
            }
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<InspectReport> history(Integer id) {
        List<InspectReport> result = null;
        InspectReport inspectReport = detail(id);
        // 质检多次的才有历史
        if (inspectReport != null && inspectReport.getReportFirst() != null && inspectReport.getReportFirst() && inspectReport.getCheckTimes() > 1) {
            InspectApply inspectApply = inspectReport.getInspectApply();
            Integer parentApplyId = inspectApply.getId();
            List<InspectApply> childInspectApplyList = inspectApplyDao.findByParentIdOrderByIdAsc(parentApplyId);
            List<Integer> inspectApplyIds = childInspectApplyList.parallelStream().map(InspectApply::getId).collect(Collectors.toList());
            inspectApplyIds.add(parentApplyId);
            result = inspectReportDao.findByInspectApplyIdInOrderByIdAsc(inspectApplyIds);
        }


        return result;
    }


    //质检结果通知：质检人员将不合格商品通知采购经办人
    public void sendSms(Map<String, Object> map1) throws Exception {
        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (StringUtils.isNotBlank(eruiToken)) {
            try {
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");

                Integer purchaseUid = (Integer) map1.get("purchaseUid");//采购经办人id
                Integer yn = (Integer) map1.get("yn");  //获取状态


                // 根据id获取人员信息
                String s = queryMessage(purchaseUid, eruiToken);    //将不合格发送给采购经办人

                //将合格发送给仓库分单员
                String jsonParam = "{\"role_no\":\"O019\"}";
                Map<String, String> headerList = new HashMap<>();
                headerList.put(CookiesUtil.TOKEN_NAME, eruiToken);
                headerList.put("Content-Type", "application/json");
                headerList.put("accept", "*/*");
                String s1 = HttpRequest.sendPost(memberList, jsonParam, headerList);
                logger.info("人员详情返回信息：" + s1);

                // 获取人员手机号
                JSONObject jsonObject = JSONObject.parseObject(s1);
                Integer code = jsonObject.getInteger("code");
                JSONArray smsarray = new JSONArray();   //手机号JSON数组
                if (code == 1) {    //判断请求是否成功
                    // 获取人员手机号
                    JSONArray data1 = jsonObject.getJSONArray("data");

                    //去除重复
                    Set<String> listAll = new HashSet<>();
                    for (int i = 0; i < data1.size(); i++) {
                        JSONObject ob = (JSONObject) data1.get(i);
                        String mobile = ob.getString("mobile");
                        if (StringUtils.isNotBlank(mobile)) {
                            listAll.add(mobile);    //获取人员手机号
                        }
                    }

                    listAll = new HashSet<>(new LinkedHashSet<>(listAll));
                    for (String me : listAll) {
                        smsarray.add(me);
                    }
                }


                Map<String, String> map = new HashMap();
                map.put("areaCode", "86");
                map.put("subType", "0");
                map.put("groupSending", "0");
                map.put("useType", "订单");

                String s2 = smsarray.toString();

                //判断状态
                if (yn == 1) {   //  1:  部分合格,部分不合格

                    if (s != null) {
                        //发送短信
                        map.put("to", "[\"" + s + "\"]");
                        map.put("content", "您好，采购合同号：" + map1.get("purchNo") + "，报检单号：" + map1.get("inspectApplyNo") + "，共计" + map1.get("sum") + "件商品出现不合格情况，请及时处理。感谢您对我们的支持与信任！");
                        String ss1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                        logger.info("发送短信返回状态" + ss1);
                    }

                    if (s2 != null) {
                        //发送短信
                        map.put("to", s2);
                        map.put("content", "您好，项目号：" + map1.get("purchaseNames") + "，报检单号：" + map1.get("inspectApplyNo") + "，共计" + map1.get("hegeNum") + "件商品已质检合格，请及时处理。感谢您对我们的支持与信任！");
                        String ss1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                        logger.info("发送短信返回状态" + ss1);
                    }
                } else {
                    if (yn == 2) {  // 2 全部不合格
                        // 根据id获取人员信息
                        if (s != null) {
                            //发送短信
                            map.put("to", "[\"" + s + "\"]");
                            map.put("content", "您好，采购合同号：" + map1.get("purchNo") + "，报检单号：" + map1.get("inspectApplyNo") + "，共计" + map1.get("sum") + "件商品出现不合格情况，请及时处理。感谢您对我们的支持与信任！");

                        }
                    } else {   // 3 全部合格
                        if (s2 != null) {
                            //发送短信
                            map.put("to", s2);
                            map.put("content", "您好，项目号：" + map1.get("purchaseNames") + "，报检单号：" + map1.get("inspectApplyNo") + "，共计" + map1.get("hegeNum") + "件商品已质检合格，请及时处理。感谢您对我们的支持与信任！");

                        }
                    }
                    String ss1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                    logger.info("发送短信返回状态" + ss1);
                }

            } catch (Exception e) {
                throw new Exception(String.format("%s%s%s", "发送短信失败", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Failure to send SMS"));

            }

        }
    }

    //查询人员信息
    public String queryMessage(Integer id, String eruiToken) {
        String eruiTokens = (String) ThreadLocalUtil.getObject();
        if (id != null) {
            String jsonParam = "{\"id\":\"" + id + "\"}";
            Map<String, String> header = new HashMap<>();
            header.put(CookiesUtil.TOKEN_NAME, eruiTokens);
            header.put("Content-Type", "application/json");
            header.put("accept", "*/*");
            String s = HttpRequest.sendPost(memberInformation, jsonParam, header);
            logger.info("人员详情返回信息：" + s);

            // 获取人员手机号
            JSONObject jsonObject = JSONObject.parseObject(s);
            Integer code = jsonObject.getInteger("code");
            if (code == 1) {
                JSONObject data = jsonObject.getJSONObject("data");
                return data.getString("mobile");    //获取手机号
            }
        }
        return null;
    }


    //处理数据信息
    public void disposeData(boolean hegeFlag, int hegeNum, int sum, InspectReport dbInspectReport, Project project) throws Exception {
        //yn    1:部分合格,部分不合格     2.全部不合格     3.全部合格

        Map<String, Object> map = new HashMap<>();
        map.put("inspectApplyNo", dbInspectReport.getInspectApplyNo());    //报检单号
        if (project != null) {
            map.put("purchaseUid", project.getPurchaseUid());       //采购经办人id
            map.put("purchaseNames", project.getProjectNo());      //项目号
        }
        map.put("purchNo", dbInspectReport.getInspectApply().getPurch().getPurchNo());      //采购合同号
        map.put("sum", sum);   //商品不合格数量
        map.put("hegeNum", hegeNum);   //商品合格数量

        if (!hegeFlag) {
            if (hegeNum != 0) {   //部分合格,部分不合格
                map.put("yn", 1);
            } else {//全部不合格
                map.put("yn", 2);
            }
        } else { // 全部合格
            map.put("yn", 3);
        }

        sendSms(map);
    }


}
