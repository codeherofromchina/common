package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.erui.order.service.InspectApplyService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class InspectApplyServiceImpl implements InspectApplyService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private InspectApplyDao inspectApplyDao;

    @Autowired
    private InspectApplyGoodsDao inspectApplyGoodsDao;

    @Autowired
    private PurchDao purchDao;

    @Autowired
    private PurchGoodsDao purchGoodsDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private InspectReportDao inspectReportDao;

    @Autowired
    private InspectApplyTmpAttachDao inspectApplyTmpAttachDao;

    @Autowired
    private InstockDao instockDao;

    @Autowired
    private InspectReportServiceImpl inspectReportServiceImpl;

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
    private String memberList;  //用户列表

    @Override
    @Transactional(readOnly = true)
    public InspectApply findById(Integer id) {
        InspectApply inspectApply = inspectApplyDao.findOne(id);
        if (inspectApply != null) {
            List<Attachment> attachments = attachmentService.queryAttachs(inspectApply.getId(), Attachment.AttachmentCategory.INSPECTAPPLY.getCode());
            inspectApply.setAttachmentList(attachments);
        }
        return inspectApply;
    }

    @Override
    @Transactional(readOnly = true)
    public List<InspectApply> findMasterListByPurchaseId(Integer[] purchaseIds) {
        List<Purch> purchList = purchDao.findByIdIn(purchaseIds);
        List<InspectApply> list = new ArrayList<>();
        for (Purch purch : purchList) {
            if (purch != null &&
                    (purch.getStatus() == Purch.StatusEnum.DONE.getCode()
                            || purch.getStatus() == Purch.StatusEnum.BEING.getCode())) {
                List<InspectApply> inspects = inspectApplyDao.findByPurchIdAndMasterOrderByCreateTimeAsc(purch.getId(), Boolean.TRUE);
                if (inspects.size() > 0) {
                    list.addAll(inspects);
                }
            }
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }


    /**
     * 新插入报检单
     *
     * @param inspectApply
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(InspectApply inspectApply) throws Exception {
        String eruiToken = (String) ThreadLocalUtil.getObject();
        Purch purch = purchDao.findOne(inspectApply.getpId());
        if (purch == null || purch.getStatus() != Purch.StatusEnum.BEING.getCode()) {
            // 采购为空或采购已完成，则返回报检失败
            throw new Exception(String.format("%s%s%s", "采购信息不正确", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Incorrect procurement information"));
        }
        final Date now = new Date();
        // 基本信息设置
        inspectApply.setPubStatus(inspectApply.getStatus());
        inspectApply.setDepartment(purch.getDepartment()); // 下发部门
        inspectApply.setPurchaseName(purch.getAgentName()); // 采购经办人
        inspectApply.setSupplierName(purch.getSupplierName()); // 采购商
        inspectApply.setPurch(purch);
        inspectApply.setPurchNo(purch.getPurchNo());
        inspectApply.setMaster(true);
        inspectApply.setDirect(inspectApply.getDirect() != null ? inspectApply.getDirect() : false);
        inspectApply.setOutCheck(inspectApply.getOutCheck() != null ? inspectApply.getOutCheck() : true);
        inspectApply.setNum(1);
        inspectApply.setHistory(false);
        inspectApply.setCreateTime(new Date());
        // 处理附件信息
        //List<Attachment> attachmentlist = attachmentService.handleParamAttachment(null, inspectApply.getAttachmentList(), inspectApply.getCreateUserId(), inspectApply.getCreateUserName());
        //inspectApply.setAttachmentList(attachmentlist);
        // 处理商品信息处理商品信息处理商品信息
        //  厂家发货且不检查
//        boolean directInstockGoods = inspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode() &&
//                inspectApply.getDirect() && !inspectApply.getOutCheck();

        // 去掉采购报检的那块空入判断↑代码2019-05-28
        boolean directInstockGoods = false;
        // 获取本次报检采购中的商品
        Map<Integer, PurchGoods> purchGoodsMap = purch.getPurchGoodsList().parallelStream().collect(Collectors.toMap(PurchGoods::getId, vo -> vo));
        // 处理报检商品信息
        List<InspectApplyGoods> handledApplyGoods = new ArrayList<>();
        for (InspectApplyGoods iaGoods : inspectApply.getInspectApplyGoodsList()) {
            PurchGoods purchGoods = purchGoodsMap.get(iaGoods.getPurchGid());
            Goods goods = purchGoods.getGoods();
            iaGoods.setId(null);
            iaGoods.setInspectApply(inspectApply);
            iaGoods.setGoods(goods);
            iaGoods.setPurchGoods(purchGoods);
            iaGoods.setPurchaseNum(purchGoods.getPurchaseNum());
            iaGoods.setQualityInspectType(purchGoods.getQualityInspectType() == null ? purchGoods.getQualityInspectType():purchGoods.getQualityInspectType().trim()); // 质量检验类型
            // 报检数量
            Integer inspectNum = iaGoods.getInspectNum();
            if (inspectNum == null || inspectNum == 0) {
                continue;
            }
            if (inspectNum < 0 || purchGoods.getPurchaseNum() < inspectNum + purchGoods.getPreInspectNum()) {
                throw new Exception(String.format("%s%s%s", "报检数量错误【sku:" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Error in number of inspection [sku: " + goods.getSku() + "]"));
            }
            iaGoods.setSamples(0);
            iaGoods.setUnqualified(0);
            iaGoods.setInstockNum(0);
            iaGoods.setCreateTime(now);
            handledApplyGoods.add(iaGoods);
            // 如果是提交，则修改采购商品（父采购商品）中的已报检数量和商品（父商品）中的已报检数量
            if (inspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode()) {
                purchGoods.setInspectNum(purchGoods.getInspectNum() + inspectNum);
                // 从数据库重新加载商品
                goods = goodsDao.findOne(goods.getId());
                goods.setInspectNum(goods.getInspectNum() + inspectNum);
                if (directInstockGoods) {
                    // 增加采购商品检验合格数量
                    purchGoods.setGoodNum(purchGoods.getGoodNum() + inspectNum);
                    // 厂家发货且不检查，则增加商品的已入库数量
                    goods.setInstockNum(goods.getInstockNum() + inspectNum);
                    /*goods.setNullInstockNum(goods.getNullInstockNum() + inspectNum);  //厂家直发数量*/
                }
                // 设置商品的报检日期,项目的商品跟踪信息
                if (goods.getInspectDate() == null) {
                    goods.setInspectDate(inspectApply.getInspectDate());
                }
                goodsDao.save(goods);
                //已报检
                applicationContext.publishEvent(new OrderProgressEvent(goods.getOrder(), 4, eruiToken));
            }
            // 设置预报检商品数量
            purchGoods.setPreInspectNum(purchGoods.getPreInspectNum() + inspectNum);
            purchGoodsDao.save(purchGoods);
        }
        List<Goods> gList = handledApplyGoods.stream().map(InspectApplyGoods::getGoods).collect(Collectors.toList());
        List<Project> proNoList = gList.stream().map(Goods::getProject).collect(Collectors.toList());
        List<Integer> qualityList = proNoList.stream().map(Project::getQualityUid).collect(Collectors.toList());
        Set<Integer> setQuality = new HashSet<>(qualityList);
        Map<Integer, List<InspectApplyGoods>> mapGoods = new HashMap<>();
        if (setQuality.size() > 0) {
            for (Integer qualityId : setQuality) {
                if (qualityId != null) {
                    List<InspectApplyGoods> listIGood = handledApplyGoods.stream().filter(vo -> qualityId.equals(vo.getGoods().getProject().getQualityUid())).collect(Collectors.toList());
                    mapGoods.put(qualityId, listIGood);
                }
            }
        }
        String lastApplyNo = null;
        String applyNo = null;
        for (Map.Entry<Integer, List<InspectApplyGoods>> mapIGoods : mapGoods.entrySet()) {
            InspectApply inspectApplyAdd = new InspectApply();
            BeanUtils.copyProperties(inspectApply, inspectApplyAdd);
            // 设置报检单号
            if (lastApplyNo == null) {
                lastApplyNo = inspectApplyDao.findLastApplyNo();
            } else {
                lastApplyNo = applyNo;
            }
            applyNo = StringUtil.genInsepctApplyNo(lastApplyNo);
            inspectApplyAdd.setId(null);
            inspectApplyAdd.setInspectApplyNo(applyNo);
            //根据质检人把商品分批质检
            for (InspectApplyGoods inspectApplyGoods : mapIGoods.getValue()) {
                inspectApplyGoods.setInspectApply(inspectApplyAdd);
            }
            inspectApplyAdd.setInspectApplyGoodsList(mapIGoods.getValue());
            Instock instock = null;
            if (directInstockGoods) {
                // 厂家直接发货且是提交，则直接设置为合格状态
                inspectApplyAdd.setPubStatus(InspectApply.StatusEnum.QUALIFIED.getCode());
                inspectApplyAdd.setStatus(InspectApply.StatusEnum.QUALIFIED.getCode());
                //推送数据到入库部门
                instock = pushInspectApply(inspectApplyAdd);
                //入库质检结果通知：质检人员将合格商品通知仓库经办人(质检申请 厂家直接发货    空入)
                disposeData(inspectApplyAdd);

            }
            // 保存报检单信息
            InspectApply save = inspectApplyDao.save(inspectApplyAdd);
            //取消第三方关联表 添加附件
            if (inspectApply.getAttachmentList() != null && inspectApply.getAttachmentList().size() > 0) {
                attachmentService.addAttachments(inspectApply.getAttachmentList(), save.getId(), Attachment.AttachmentCategory.INSPECTAPPLY.getCode());
            }
            // 推送数据到入库质检中
            if (inspectApplyAdd.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode() && !directInstockGoods) {
                InspectReport inspectReport = pushDataToInspectReport(save);
                //到货报检通知：到货报检单下达后同时通知质检经办人
                Set<Integer> qualityNameList = new HashSet<>(); //质检经办人
                Set<String> purchaseNameList = new HashSet<>(); //采购经办人
                for (InspectApplyGoods inspectApplyGoods : mapIGoods.getValue()) {
                    if (StringUtil.isNotBlank(inspectApplyGoods.getGoods().getProject().getQualityName())) {
                        qualityNameList.add(inspectApplyGoods.getGoods().getProject().getQualityUid());
                    }
                    if (StringUtil.isNotBlank(inspectApplyGoods.getGoods().getProject().getPurchaseName())) {
                        purchaseNameList.add(inspectApplyGoods.getGoods().getProject().getPurchaseName());
                    }
                }
                String qualityNames = StringUtils.join(qualityNameList, ",");  //质检经办人
                String purchaseNames = StringUtils.join(purchaseNameList, ",");  //采购经办人
                String inspectApplyNo = inspectApplyAdd.getInspectApplyNo();           //报检单号

                Map<String, Object> map = new HashMap<>();
                map.put("qualityNames", qualityNames);
                map.put("projectNos", purch.getPurchNo());  //采购合同号
                map.put("purchaseNames", purchaseNames);
                map.put("inspectApplyNo", inspectApplyNo);
                sendSms(map);

                //当有报检单提交的时候，通知办理入库质检
                List<Project> projects = purch.getProjects();
                if (projects.size() > 0) {
                    Set<String> projectNoSet = new HashSet<>(); //项目号
                    for (Project project : projects) {
                        String projectNo = project.getProjectNo();
                        if (projectNo != null) {
                            projectNoSet.add(projectNo);
                        }
                    }
                    for (Project project : projects) {
                        BackLog newBackLog = new BackLog();
                        newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.INSPECTREPORT.getMsg());  //功能名称
                        newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.INSPECTREPORT.getNum());    //功能访问路径标识
                        newBackLog.setReturnNo(save.getInspectApplyNo());  //返回单号    返回空，两个标签
                        String purchNo = purch.getPurchNo();//采购合同号
                        String join = StringUtils.join(projectNoSet, ",");
                        newBackLog.setInformTheContent(join + " | " + purchNo);  //提示内容
                        newBackLog.setHostId(inspectReport.getId());    //父ID，列表页id (入库质检id)
                        newBackLog.setUid(project.getQualityUid());   ////经办人id
                        backLogService.addBackLogByDelYn(newBackLog);
                    }
                }
            } else if (directInstockGoods) {
                //  判断采购是否已经完成并修正
                checkPurchHasDone(inspectApplyAdd.getPurch());
            }

            if (directInstockGoods) {
                // 厂家直接发货添加 入库办理 事项
                //推送给分单人待办事项  办理分单
                instocksubmenu(instock);

            }
        }
        return true;
    }


    @Transactional
    public void checkPurchHasDone(Purch purch) {
        List<PurchGoods> purchGoodsList = purch.getPurchGoodsList();
        boolean doneFlag = true;
        for (PurchGoods pg : purchGoodsList) {
            if (pg.getGoodNum() < pg.getPurchaseNum()) {
                doneFlag = false;
                break;
            }
        }
        if (doneFlag) {
            purch.setStatus(Purch.StatusEnum.DONE.getCode());
            purchDao.save(purch);
            //当全部采购完成时设置供应商状态为COMPLETED
            purchServiceImpl.updateSupplierStatus(purch.getId(), "COMPLETED");
            try {
                // 删除办理报检单待办事项列表
                //全部质检合格以后，删除   “办理报检单”  待办提示
                BackLog backLog = new BackLog();
                backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.INSPECTAPPLY.getNum());    //功能访问路径标识
                backLog.setHostId(purch.getId());
                backLogService.updateBackLogByDelYn(backLog);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            //当部分采购时设置供应商状态为PART_RECEIPT
            purchServiceImpl.updateSupplierStatus(purch.getId(), "PART_RECEIPT");
        }
    }

    /**
     * 更新报检单
     *
     * @param inspectApply
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(InspectApply inspectApply) throws Exception {
        String eruiToken = (String) ThreadLocalUtil.getObject();
        InspectApply dbInspectApply = findById(inspectApply.getId());
        if (dbInspectApply == null || dbInspectApply.getStatus() != InspectApply.StatusEnum.SAVED.getCode()) {
            throw new Exception(String.format("%s%s%s", "报检信息不存在", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Inspection information does not exist"));
        }
        // 处理基本信息
        dbInspectApply.setAbroadCoName(inspectApply.getAbroadCoName());
        dbInspectApply.setInspectDate(inspectApply.getInspectDate());
        dbInspectApply.setDirect(inspectApply.getDirect() != null ? inspectApply.getDirect().booleanValue() : false);
        dbInspectApply.setOutCheck(inspectApply.getOutCheck() != null ? inspectApply.getOutCheck() : true);
        dbInspectApply.setRemark(inspectApply.getRemark());
        dbInspectApply.setStatus(inspectApply.getStatus());
        dbInspectApply.setPubStatus(inspectApply.getStatus()); // 设置报检单的全局状态
        /*// 处理附件信息
        List<Attachment> attachmentlist = attachmentService.handleParamAttachment(
                dbInspectApply.getAttachmentList(),
                inspectApply.getAttachmentList(),
                inspectApply.getCreateUserId(),
                inspectApply.getCreateUserName());
        dbInspectApply.setAttachmentList(attachmentlist);*/
        //  厂家发货且不检查
//       boolean directInstockGoods = inspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode() &&
//                inspectApply.getDirect() && !inspectApply.getOutCheck();
        // 去掉采购报检的那块空入判断↑代码2019-05-28
        boolean directInstockGoods = false;
        // 处理商品信息
        Purch purch = dbInspectApply.getPurch();
        Map<Integer, InspectApplyGoods> inspectApplyGoodsMap = dbInspectApply.getInspectApplyGoodsList().parallelStream()
                .collect(Collectors.toMap(InspectApplyGoods::getId, vo -> vo));
        // 生成本次最终的报检商品信息
        List<InspectApplyGoods> inspectApplyGoodsList = new ArrayList<>();
        for (InspectApplyGoods iaGoods : inspectApply.getInspectApplyGoodsList()) {
            InspectApplyGoods applyGoods = inspectApplyGoodsMap.remove(iaGoods.getId());
            if (applyGoods == null) { // 修改的商品不存在
                throw new Exception(String.format("%s%s%s", "报检商品信息错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Misinformation for commodity inspection"));
            }
            Integer oldInspectNum = applyGoods.getInspectNum();
            applyGoods.setInspectNum(iaGoods.getInspectNum());
            applyGoods.setHeight(iaGoods.getHeight());
            applyGoods.setLwh(iaGoods.getLwh());
            // 保证每次从数据库获取
            PurchGoods purchGoods = purchGoodsDao.findOne(applyGoods.getPurchGoods().getId());
            // 报检数量大于采购数量
            Integer inspectNum = applyGoods.getInspectNum();
            if (inspectNum == null || inspectNum == 0) {
                continue;
            }
            if (inspectNum < 0 || inspectNum - oldInspectNum > purchGoods.getPurchaseNum() - purchGoods.getPreInspectNum()) {
                throw new Exception(String.format("%s%s%s", "报检数量错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Error in number of inspection"));
            }
            applyGoods.setQualityInspectType(purchGoods.getQualityInspectType()==null?purchGoods.getQualityInspectType():purchGoods.getQualityInspectType().trim()); // 质量检验类型
            // 如果是提交，则修改采购商品（父采购商品）中的已报检数量和商品（父商品）中的已报检数量
            if (dbInspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode()) {
                purchGoods.setInspectNum(purchGoods.getInspectNum() + inspectNum);
                // 修改商品的已报检数量
                Goods goods = goodsDao.findOne(applyGoods.getGoods().getId());
                goods.setInspectNum(goods.getInspectNum() + inspectNum);
                if (directInstockGoods) {
                    // 增加采购商品检验合格数量
                    purchGoods.setGoodNum(purchGoods.getGoodNum() + inspectNum);
                    // 厂家发货且不检查，则增加商品的已入库数量
                    goods.setInstockNum(goods.getInstockNum() + inspectNum);
                    /*goods.setNullInstockNum(goods.getNullInstockNum() + inspectNum);  //厂家直发数量*/
                }
                if (goods.getInspectDate() == null) {
                    goods.setInspectDate(dbInspectApply.getInspectDate());
                }
                goodsDao.save(goods);
                // 已报检
                applicationContext.publishEvent(new OrderProgressEvent(goods.getOrder(), 4, eruiToken));
            }
            // 更新预报检数量
            purchGoods.setPreInspectNum(purchGoods.getPreInspectNum() + inspectNum - oldInspectNum);
            purchGoodsDao.save(purchGoods);
            // 加入报检商品列表容器
            inspectApplyGoodsList.add(applyGoods);
        }
        // 删除没有传入的报检商品信息
        Collection<InspectApplyGoods> values = inspectApplyGoodsMap.values();
        if (values != null && values.size() > 0) {
            // 删除掉预报检商品的数量
            for (InspectApplyGoods applyGoods : values) {
                PurchGoods purchGoods = applyGoods.getPurchGoods();
                purchGoods = purchGoodsDao.findOne(purchGoods.getId());
                // 更新预报检数量
                purchGoods.setPreInspectNum(purchGoods.getPreInspectNum() - applyGoods.getInspectNum());
                purchGoodsDao.save(purchGoods);
            }
            inspectApplyGoodsDao.delete(inspectApplyGoodsMap.values());
        }
        // 设置报检商品信息
        dbInspectApply.setInspectApplyGoodsList(inspectApplyGoodsList);
        Instock instock = null;
        if (directInstockGoods) {
            // 厂家直接发货且是提交，则直接设置为合格状态
            dbInspectApply.setPubStatus(InspectApply.StatusEnum.QUALIFIED.getCode());
            dbInspectApply.setStatus(InspectApply.StatusEnum.QUALIFIED.getCode());

            //推送数据到入库部门
            instock = pushInspectApply(dbInspectApply);

            //入库质检结果通知：质检人员将合格商品通知仓库经办人(质检申请 厂家直接发货    空入)
            disposeData(dbInspectApply);

        }
        // 保存报检单
        InspectApply save = inspectApplyDao.save(dbInspectApply);
        // 处理附件信息 attachmentList 库里存在附件列表 dbAttahmentsMap前端传来参数附件列表
        //deliverConsign1.setAttachmentList(deliverConsign1.getAttachmentList());
        List<Attachment> attachmentList = inspectApply.getAttachmentList();
        if (attachmentList != null && attachmentList.size() > 0) {
            Map<Integer, Attachment> dbAttahmentsMap = dbInspectApply.getAttachmentList().parallelStream().collect(Collectors.toMap(Attachment::getId, vo -> vo));
            attachmentService.updateAttachments(attachmentList, dbAttahmentsMap, dbInspectApply.getId(), Attachment.AttachmentCategory.INSPECTAPPLY.getCode());
        }
        // 完善提交后的后续操作
        if (dbInspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode() && !directInstockGoods) {
            // 推送数据到入库质检中
            InspectReport inspectReport = pushDataToInspectReport(save);

            //到货报检通知：到货报检单下达后同时通知质检经办人
            disposeSmsDate(dbInspectApply, inspectApply);

            //当有报检单提交的时候，通知办理入库质检
            List<Project> projects = purch.getProjects();
            if (projects.size() > 0) {
                Set<String> projectNoSet = new HashSet<>(); //项目号
                for (Project project : projects) {
                    String projectNo = project.getProjectNo();
                    if (projectNo != null) {
                        projectNoSet.add(projectNo);
                    }
                }
                for (Project project : projects) {
                    BackLog newBackLog = new BackLog();
                    newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.INSPECTREPORT.getMsg());  //功能名称
                    newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.INSPECTREPORT.getNum());    //功能访问路径标识
                    newBackLog.setReturnNo(save.getInspectApplyNo());  //返回单号    报检单号
                    String purchNo = purch.getPurchNo();//采购合同号
                    newBackLog.setInformTheContent(StringUtils.join(projectNoSet, ",") + " | " + purchNo);  //提示内容
                    newBackLog.setHostId(inspectReport.getId());    //父ID，列表页id (入库质检id)
                    newBackLog.setUid(project.getQualityUid());   ////经办人id
                    backLogService.addBackLogByDelYn(newBackLog);
                }
            }

        } else if (directInstockGoods) {
            //  判断采购是否已经完成并修正
            checkPurchHasDone(dbInspectApply.getPurch());
        }

        if (directInstockGoods) {
            // 厂家直接发货添加 入库办理 事项
            //推送给分单人待办事项  办理分单
            instocksubmenu(instock);

        }

        return true;
    }

    /**
     * 重新报检报检单中不合格的商品
     * 1：判断每个商品的报检数量是否等于最后一次报检不合格数量
     * 2：主报检单的报检数量+1
     * 3：新插入报检单，并设置上级报检单
     * 4: 插入整改意见
     * 5：插入新报检单附件信息
     * 6：插入新的报检商品信息
     *
     * @param inspectApply
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean againApply(InspectApply inspectApply) throws Exception {
        InspectApply lastInspectApply = findDetail(inspectApply.getId());
        if (lastInspectApply == null) {
            throw new Exception(String.format("%s%s%s", "不存在的报检单", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "A nonexistent check list"));
        }
        if (lastInspectApply.getStatus() != InspectApply.StatusEnum.UNQUALIFIED.getCode()) {
            throw new Exception(String.format("%s%s%s", "当期报检单没有未合格商品", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "There is no unqualified commodity in the current inspection list"));
        }
        InspectApply parentInspectApply = lastInspectApply.getParent(); // 主报检单
        if (parentInspectApply == null) {
            parentInspectApply = lastInspectApply;
        }
        // 检查是否是最后一次报检信息
        if (parentInspectApply.getNum().intValue() != lastInspectApply.getNum()) {
            throw new Exception(String.format("%s%s%s", "重新报检的不是最后一次质检结果", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The result of the re inspection is not the result of the last quality inspection"));
        }
        InspectApply lastInspectApplyTest = inspectApplyDao.findByInspectApplyNo(String.format("%s-%d", parentInspectApply.getInspectApplyNo(), (parentInspectApply.getNum() - 1)));
        if (parentInspectApply.getNum() != 1 && lastInspectApplyTest.getId() != lastInspectApply.getId().intValue()) {
            throw new Exception(String.format("%s%s%s", "重新报检的不是最后一次质检结果", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The result of the re inspection is not the result of the last quality inspection"));
        }

        // 声明要插入的报检单
        InspectApply newInspectApply = new InspectApply();
        // 判断每个商品的报检数量是否等于最后一次报检不合格数量
        List<InspectApplyGoods> inspectApplyGoodsList = lastInspectApply.getInspectApplyGoodsList();
        List<InspectApplyGoods> goodsDataList = new ArrayList<>();
        for (InspectApplyGoods applyGoods : inspectApplyGoodsList) {
            if (applyGoods.getUnqualified() == 0) {
                continue;
            }
            InspectApplyGoods inspectApplyGoods = new InspectApplyGoods();
            inspectApplyGoods.setId(null);
            inspectApplyGoods.setInspectApply(newInspectApply);
            inspectApplyGoods.setGoods(applyGoods.getGoods());
            inspectApplyGoods.setPurchaseNum(applyGoods.getPurchaseNum());
            inspectApplyGoods.setPurchGoods(applyGoods.getPurchGoods());
            inspectApplyGoods.setHeight(applyGoods.getHeight());
            inspectApplyGoods.setLwh(applyGoods.getLwh());
            inspectApplyGoods.setInspectNum(applyGoods.getUnqualified());
            inspectApplyGoods.setSamples(0);
            inspectApplyGoods.setUnqualified(0);
            inspectApplyGoods.setInstockNum(0);
            inspectApplyGoods.setCreateTime(new Date());
            goodsDataList.add(inspectApplyGoods);
        }
        // 检验完毕，做正式操作
        // 主报检单的报检数量+1
        Integer num = parentInspectApply.getNum();
        parentInspectApply.setNum(num + 1);
        parentInspectApply.setHistory(true);
        parentInspectApply.setPubStatus(InspectApply.StatusEnum.SUBMITED.getCode()); // 设置全局状态为待审核
        inspectApplyDao.save(parentInspectApply);

        //新插入报检单，并设置上级报检单
        newInspectApply.setInspectApplyNo(parentInspectApply.getInspectApplyNo() + "-" + num);
        newInspectApply.setPurch(parentInspectApply.getPurch());
        newInspectApply.setPurchNo(parentInspectApply.getPurchNo());
        newInspectApply.setMaster(false);
        newInspectApply.setParent(parentInspectApply);
        // 设置下发部门
        newInspectApply.setDepartment(lastInspectApply.getDepartment());
        // 设置采购经办人
        newInspectApply.setPurchaseName(lastInspectApply.getPurchaseName());
        // 设置供应商名称
        newInspectApply.setSupplierName(lastInspectApply.getSupplierName());
        // 设置国外分公司
        newInspectApply.setAbroadCoName(lastInspectApply.getAbroadCoName());
        // 设置报检时间 - 如果传入则用传入报检时间，否则使用上一次的报检时间
        newInspectApply.setInspectDate(
                inspectApply.getInspectDate() != null ?
                        inspectApply.getInspectDate() : lastInspectApply.getInspectDate());
        newInspectApply.setDirect(lastInspectApply.getDirect());
        newInspectApply.setOutCheck(lastInspectApply.getOutCheck());
        newInspectApply.setMsg(inspectApply.getMsg());
        newInspectApply.setHistory(false);
        newInspectApply.setNum(parentInspectApply.getNum());
        newInspectApply.setCreateTime(new Date());
        //插入新报检单附件信息
        newInspectApply.setStatus(InspectApply.StatusEnum.SUBMITED.getCode());
        newInspectApply.setPubStatus(InspectApply.StatusEnum.SUBMITED.getCode());
        //List<Attachment> attachmentList = attachmentService.handleParamAttachment(null, inspectApply.getAttachmentList(), inspectApply.getCreateUserId(), inspectApply.getCreateUserName());
        //newInspectApply.setAttachmentList(attachmentList);
        // 处理附件信息 attachmentList 库里存在附件列表 dbAttahmentsMap前端传来参数附件列表
        //deliverConsign1.setAttachmentList(deliverConsign1.getAttachmentList());
        List<Attachment> attachmentList = inspectApply.getAttachmentList();
        Map<Integer, Attachment> dbAttahmentsMap = lastInspectApply.getAttachmentList().parallelStream().collect(Collectors.toMap(Attachment::getId, vo -> vo));
        if (attachmentList != null && attachmentList.size() > 0) {
            attachmentService.updateAttachments(attachmentList, dbAttahmentsMap, lastInspectApply.getId(), Attachment.AttachmentCategory.INSPECTAPPLY.getCode());
        }
        newInspectApply.setInspectApplyGoodsList(goodsDataList);

        newInspectApply = inspectApplyDao.save(newInspectApply);

        // 推送数据到入库质检中
        pushDataToInspectReport(newInspectApply);

        //到货报检通知：到货报检单下达后同时通知质检经办人
        disposeSmsDate(newInspectApply, inspectApply);

        return true;
    }

    @Override
    public List<InspectApply> findByParentId(Integer parentId) {
        // 存在多次报检
        List<InspectApply> inspectApplyList = inspectApplyDao.findByParentIdOrderByIdAsc(parentId);
        if (inspectApplyList != null) {
            return inspectApplyList;
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional(readOnly = true)
    public InspectApply findDetail(Integer id) {
        InspectApply inspectApply = inspectApplyDao.findOne(id);
        if (inspectApply != null) {
            if (inspectApply.getId() != null) {
                List<Attachment> attachments = attachmentService.queryAttachs(inspectApply.getId(), Attachment.AttachmentCategory.INSPECTAPPLY.getCode());
                if (attachments != null && attachments.size() > 0) {
                    inspectApply.setAttachmentList(attachments);
                }
            }
            inspectApply.getAttachmentList();
            inspectApply.getInspectApplyGoodsList().size();
        }

        return inspectApply;
    }


    /**
     * 向入库质检推送数据
     */
    private InspectReport pushDataToInspectReport(InspectApply inspectApply) throws Exception {
        // 报检单商品是否全部为QRL1商品
        boolean isAllQRL1 = inspectApply.getInspectApplyGoodsList().parallelStream().allMatch(applygoods -> applygoods.getQualityInspectType() != null && "QRL1".equals(applygoods.getQualityInspectType().trim()));
        // 新建质检信息并完善
        InspectReport report = new InspectReport();
        report.setInspectApply(inspectApply);
        report.setInspectApplyNo(inspectApply.getInspectApplyNo());
        report.setReportFirst(inspectApply.isMaster());
        report.setSupplierName(inspectApply.getSupplierName());

        // 判断是不是第一次报检并设置相应信息
        if (inspectApply.isMaster()) {
            report.setCheckTimes(1);
            List<InspectApplyGoods> inspectApplyGoodsList = inspectApply.getInspectApplyGoodsList();
            List<Project> projects = new ArrayList<>();
            for (InspectApplyGoods iag : inspectApplyGoodsList) {
                projects.add(iag.getGoods().getProject());
            }
            if (projects != null && projects.size() > 0) {
                Project project = projects.parallelStream().findFirst().get();
                report.setCheckUserId(project.getQualityUid());
                report.setCheckUserName(project.getQualityName());
            }
        } else {
            // 设置父质检的报检次数
            InspectApply parent = inspectApply.getParent();
            InspectReport parentInspectReport = inspectReportDao.findByInspectApplyId(parent.getId());
            parentInspectReport.setCheckTimes(parent.getNum());
            inspectReportDao.save(parentInspectReport);
            report.setCheckTimes(parent.getNum());
            // 取父级的质检人和质检部门
            report.setCheckDeptId(parentInspectReport.getCheckDeptId());
            report.setCheckDeptName(parentInspectReport.getCheckDeptName());
            report.setCheckUserId(parentInspectReport.getCheckUserId());
            report.setCheckUserName(parentInspectReport.getCheckUserName());
        }
        report.setProcess(true);
        report.setMsg(inspectApply.getMsg());
        report.setStatus(InspectReport.StatusEnum.INIT.getCode());
        report.setCreateTime(new Date());
        report.setIsShow(1);

        if(isAllQRL1){ // 质检类型全部为QRL1时，不需要入库质检了，直接在入库管理里面加入一条记录，入库质检不显示该记录，质检状态显示完成
            report.setNcrNo("");
            report.setReportRemarks("质检商品全部都是QRL1，所以不需要质检员质检商品。");
            report.setStatus(InspectReport.StatusEnum.DONE.getCode());
            report.setProcess(false); // 是否还在质检中 true:进行中 false:已结束
            report.setIsShow(0);

            for (InspectApplyGoods applyGoods : inspectApply.getInspectApplyGoodsList()) {
                PurchGoods purchGoods = applyGoods.getPurchGoods();
                applyGoods.setSamples(0);
                applyGoods.setUnqualified(0);
                // 设置采购商品的已合格数量
                purchGoods.setGoodNum(purchGoods.getPurchaseNum());
                purchGoodsDao.save(purchGoods);
            }

            inspectApply.setStatus(InspectApply.StatusEnum.QUALIFIED.getCode());
            inspectApply.setPubStatus(InspectApply.StatusEnum.QUALIFIED.getCode());
            inspectApplyDao.save(inspectApply);
        }

        List<InspectApplyGoods> inspectApplyGoodsList = new ArrayList<>();
        // 设置质检关联到报检商品信息
        inspectApply.getInspectApplyGoodsList().forEach(vo -> {
            InspectApplyGoods iag = new InspectApplyGoods();
            iag.setId(vo.getId());
            inspectApplyGoodsList.add(iag);
            Goods goods = vo.getGoods();
            //质检中
            //applicationContext.publishEvent(new OrderProgressEvent(goods.getOrder(),5));
        });
        report.setInspectGoodsList(inspectApplyGoodsList);

        // 保存推送的质检信息并等待人工质检
        InspectReport save = inspectReportDao.save(report);
        if (save != null) {
            if(isAllQRL1){ // 质检类型全部为QRL1时，不需要入库质检了，直接在入库管理里面加入一条记录。
                pushInstock(save, inspectApply.getInspectApplyGoodsList());
            }
            return save;
        }

        return null;
    }

    private void pushInstock(InspectReport inspectReport, List<InspectApplyGoods> inspectApplyGoodsList) throws Exception {
        // 最后判断采购是否完成，且存在合格的商品数量
        Purch purch = inspectReport.getInspectApply().getPurch();
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
        Date doneDate = inspectReport.getCreateTime();
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
        instock.setInspectReport(inspectReport);
        instock.setInspectApplyNo(inspectReport.getInspectApplyNo()); // 报检单号
        instock.setSupplierName(inspectReport.getInspectApply().getPurch().getSupplierName()); // 供应商
        instock.setStatus(Instock.StatusEnum.INIT.getStatus());
        instock.setCreateTime(new Date());
        List<InstockGoods> instockGoodsList = new ArrayList<>();

        // 入库商品
        for (InspectApplyGoods inspectGoods : inspectApplyGoodsList) {
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
                instockGoods.setCreateUserId(inspectReport.getCreateUserId());
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
            if (listAll.size() > 0) {
                String inspectApplyNo = inspectReport.getInspectApplyNo();  //报检单号
                String supplierName = inspectReport.getSupplierName();  //供应商名称
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

        // 流程进度推送
        String token = (String) ThreadLocalUtil.getObject();
        for (InspectApplyGoods inspectGoods : inspectApplyGoodsList) {
            Goods goods = inspectGoods.getGoods();
            applicationContext.publishEvent(new OrderProgressEvent(goods.getOrder(), 5, token));
        }
    }

    @Override
    @Transactional
    public void fullTmpMsg(InspectApply inspectApply) {
        Integer id = inspectApply.getId();
        // 保存整改意见
        InspectApply one = inspectApplyDao.findOne(id);
        one.setTmpMsg(inspectApply.getMsg());
        inspectApplyDao.save(one);
        // 删除原来的临时附件
        List<InspectApplyTmpAttach> byInspectApplyId = inspectApplyTmpAttachDao.findByInspectApplyId(id);
        inspectApplyTmpAttachDao.delete(byInspectApplyId);
//        inspectApplyTmpAttachDao.deleteByInspectApplyId(id);
        // 保存附件
        List<Attachment> attachmentList = inspectApply.getAttachmentList();
        if (attachmentList != null && attachmentList.size() > 0) {
            List<InspectApplyTmpAttach> list = attachmentList.stream().map(attachment -> {
                InspectApplyTmpAttach tmpAttach = new InspectApplyTmpAttach();
                tmpAttach.setInspectApplyId(id);
                attachment.setId(null);
                attachment.setCreateTime(new Date());
                tmpAttach.setAttachment(attachment);
                return tmpAttach;
            }).collect(Collectors.toList());
            inspectApplyTmpAttachDao.save(list);
        }
    }

    // 获取重新报检时，保存的临时附件
    @Override
    public List<Attachment> findTmpAttachmentByInspectApplyId(Integer inspectApplyId) {
        List<Attachment> result = new ArrayList<>();
        List<InspectApplyTmpAttach> tmpAttaches = inspectApplyTmpAttachDao.findByInspectApplyId(inspectApplyId);
        if (tmpAttaches != null && tmpAttaches.size() > 0) {
            for (InspectApplyTmpAttach vo : tmpAttaches) {
                Attachment attachment = vo.getAttachment();
                attachment.setId(null);
                result.add(attachment);
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public InspectApply findSonFailDetail(Integer parentId) {
        InspectApply parentInspectApply = inspectApplyDao.findOne(parentId);
        if (parentInspectApply == null || parentInspectApply.getNum() < 2) {
            return null;
        }
        InspectApply inspectApply = inspectApplyDao.findByInspectApplyNo(String.format("%s-%d", parentInspectApply.getInspectApplyNo(), (parentInspectApply.getNum() - 1)));
        if (inspectApply == null || inspectApply.getStatus() != InspectApply.StatusEnum.UNQUALIFIED.getCode()) {
            return null;
        }
        inspectApply.getAttachmentList().size();
        inspectApply.getInspectApplyGoodsList().size();

        return inspectApply;
    }


    /**
     * 处理短信信息
     *
     * @param dbInspectApply
     * @param inspectApply
     * @throws Exception
     */
    public void disposeSmsDate(InspectApply dbInspectApply, InspectApply inspectApply) throws Exception {

        //到货报检通知：到货报检单下达后同时通知质检经办人
        Set<Integer> qualityNameList = new HashSet<>(); //质检经办人
        Set<String> purchaseNameList = new HashSet<>(); //采购经办人
        Purch purch = dbInspectApply.getPurch();
        for (Project project : purch.getProjects()) {
            if (StringUtil.isNotBlank(project.getQualityName())) {
                qualityNameList.add(project.getQualityUid());
            }
            if (StringUtil.isNotBlank(project.getPurchaseName())) {
                purchaseNameList.add(project.getPurchaseName());
            }
        }
        String qualityNames = StringUtils.join(qualityNameList, ",");  //质检经办人
        String purchaseNames = StringUtils.join(purchaseNameList, ",");  //采购经办人
        String inspectApplyNo1 = dbInspectApply.getInspectApplyNo();//报检单号

        Map<String, Object> map = new HashMap<>();
        map.put("qualityNames", qualityNames);
        map.put("projectNos", purch.getPurchNo()); // 采购合同号
        map.put("purchaseNames", purchaseNames);
        map.put("inspectApplyNo", inspectApplyNo1);
        sendSms(map);
    }


    //到货报检通知：到货报检单下达后同时通知质检经办人
    public void sendSms(Map<String, Object> map1) throws Exception {

        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (StringUtils.isNotBlank(eruiToken)) {
            try {
                String mobile = null;  //质检经办人
                Set<String> qualityNameSet = new HashSet();    //质检经办人  手机号
                String qualityNames = (String) map1.get("qualityNames");
                String[] split = qualityNames.split(",");

                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");

                //质检经办人 手机号
                for (String s : split) {
                    String jsonParam = "{\"id\":\"" + s + "\"}";
                    String s1 = HttpRequest.sendPost(memberInformation, jsonParam, header);
                    logger.info("人员详情返回信息：" + s1);
                    // 获取手机号
                    JSONObject jsonObject = JSONObject.parseObject(s1);
                    Integer code = jsonObject.getInteger("code");
                    if (code == 1) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        qualityNameSet.add(data.getString("mobile"));
                    }
                }

                //去除重复
                Set<String> listAll = new HashSet<>();
                listAll.addAll(qualityNameSet);

                listAll = new HashSet<>(new LinkedHashSet<>(listAll));
                JSONArray smsarray = new JSONArray();
                for (String me : listAll) {
                    smsarray.add(me);
                }
                //发送短信
                if (smsarray.size() > 0) {
                    Map<String, String> map = new HashMap();
                    map.put("areaCode", "86");
                    map.put("to", smsarray.toString());
                    map.put("content", "您好，采购合同号：" + map1.get("projectNos") + "，报检单号：" + map1.get("inspectApplyNo") + "，采购经办人：" + map1.get("purchaseNames") + "，已申请报检，请及时处理。感谢您对我们的支持与信任！");
                    map.put("subType", "0");
                    map.put("groupSending", "0");
                    map.put("useType", "订单");
                    String s1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                    logger.info("发送短信返回状态" + s1);
                }

            } catch (Exception e) {
                throw new Exception(String.format("%s%s%s", "发送短信失败", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Failure to send SMS"));
            }

        }
    }

    /**
     * 推送数据到入库部门
     *
     * @param inspectApply
     */
    public Instock pushInspectApply(InspectApply inspectApply) {
        // 推送数据到入库部门
        Instock instock = new Instock();
        instock.setInspectReport(null);


        Project project = null; //项目信息
        List<Project> projects = inspectApply.getPurch().getProjects();
        for (Project project2 : projects) {
            project = project == null ? project2 : project;
        }

      /*  if(project != null){
            instock.setUid(project.getWarehouseUid());      //仓库经办人ID
            instock.setUname(project.getWarehouseName());   //仓库经办人名字
        }*/
        instock.setInspectApplyNo(inspectApply.getInspectApplyNo()); // 报检单号
        instock.setSupplierName(inspectApply.getPurch().getSupplierName()); // 供应商
        instock.setStatus(Instock.StatusEnum.INIT.getStatus());
        instock.setCreateTime(new Date());
        List<InstockGoods> instockGoodsList = new ArrayList<>();
        // 添加入库商品

        List<InspectApplyGoods> inspectApplyGoodsList = inspectApply.getInspectApplyGoodsList();    //报检商品信息
        for (InspectApplyGoods applyGoods : inspectApplyGoodsList) { //报检商品

            InstockGoods instockGoods = new InstockGoods(); //添加出库商品

            instockGoods.setInstock(instock);
            instockGoods.setContractNo(applyGoods.getGoods().getContractNo());
            instockGoods.setProjectNo(applyGoods.getGoods().getProjectNo());
            instockGoods.setInspectApplyGoods(applyGoods);
            instockGoods.setQualifiedNum(applyGoods.getInspectNum());   //合格数量  (报检数量)
            instockGoods.setInstockNum(applyGoods.getInspectNum()); // 入库数量  (报检数量)
            Date date = new Date();
            instockGoods.setCreateTime(date);
            instockGoods.setUpdateTime(date);
            instockGoods.setCreateUserId(inspectApply.getCreateUserId());       //获取报检人

            instockGoodsList.add(instockGoods);


        }

        instock.setInstockGoodsList(instockGoodsList);
        instock.setOutCheck(0); //是否外检（ 0：否   1：是）

        Instock save = instockDao.save(instock);

        return save;

    }

    //入库质检结果通知：质检人员将合格商品通知仓库经办人(质检申请 厂家直接发货    空入)
    //处理数据信息                                            合格数量（报检数量）
    public void disposeData(InspectApply inspectApply) throws Exception {

        int hegeNum = 0;

        //计算合格总数量
        List<InspectApplyGoods> inspectApplyGoodsList = inspectApply.getInspectApplyGoodsList();    //报检商品信息
        for (InspectApplyGoods applyGoods : inspectApplyGoodsList) {//报检商品
            hegeNum += applyGoods.getInspectNum(); //合格数量  (报检数量)
        }
        List<Project> projectSet = inspectApply.getPurch().getProjects();
        Project project = null; //项目信息
        for (Project projects : projectSet) {
            project = project == null ? projects : project;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("inspectApplyNo", inspectApply.getInspectApplyNo());    //报检单号
        map.put("purchaseUid", project.getPurchaseUid());       //采购经办人id
        map.put("warehouseUid", project.getWarehouseUid());       //仓库经办人id
        map.put("purchaseNames", project.getProjectNo());      //项目号
        map.put("purchNo", inspectApply.getPurchNo());      //采购合同号
        map.put("hegeNum", hegeNum);   //商品合格数量
        map.put("yn", 3);   //yn    1:部分合格,部分不合格     2.全部不合格     3.全部合格
        inspectReportServiceImpl.sendSms(map);
    }


    public void instocksubmenu(Instock instock) throws Exception {

        // 厂家直接发货添加 入库办理 事项
        //推送给分单人待办事项  办理分单
        Set<String> projectNoSet = new HashSet<>();
        List<InstockGoods> instockGoodsLists = instock.getInstockGoodsList();
        instockGoodsLists.stream().forEach(instockGoods -> {
            PurchGoods purchGoods = instockGoods.getInspectApplyGoods().getPurchGoods();
            Goods goods = purchGoods.getGoods();
            if (StringUtil.isNotBlank(goods.getProjectNo())) {
                projectNoSet.add(goods.getProjectNo());
            }
        });
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
            for (Integer in : listAll) { //分单员有几个人推送几条
                BackLog newBackLog = new BackLog();
                newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.INSTOCKSUBMENU.getMsg());  //功能名称
                newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.INSTOCKSUBMENU.getNum());    //功能访问路径标识
                String inspectApplyNo = instock.getInspectApplyNo();  //报检单号
                newBackLog.setReturnNo(inspectApplyNo);  //返回单号
                String supplierName = instock.getSupplierName();  //供应商名称
                newBackLog.setInformTheContent(StringUtils.join(projectNoSet, ",") + " | " + supplierName);  //提示内容
                newBackLog.setHostId(instock.getId());    //父ID，列表页id
                newBackLog.setUid(in);   ////经办人id
                backLogService.addBackLogByDelYn(newBackLog);
            }
        }
    }
}
