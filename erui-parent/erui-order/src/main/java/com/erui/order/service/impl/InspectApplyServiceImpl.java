package com.erui.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.NewDateUtil;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.EruitokenUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.InspectApplyService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class InspectApplyServiceImpl implements InspectApplyService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

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

    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口

    @Value("#{orderProp[SEND_SMS]}")
    private String sendSms;  //发短信接口


    @Value("#{orderProp[MEMBER_LIST]}")
    private String memberList;  //用户列表

    @Override
    @Transactional(readOnly = true)
    public InspectApply findById(Integer id) {
        return inspectApplyDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InspectApply> findMasterListByPurchaseId(Integer purchaseId) {
        Purch purch = purchDao.findOne(purchaseId);
        if (purch != null &&
                (purch.getStatus() == Purch.StatusEnum.DONE.getCode()
                        || purch.getStatus() == Purch.StatusEnum.BEING.getCode())) {

            List<InspectApply> list = inspectApplyDao.findByPurchIdAndMasterOrderByCreateTimeAsc(purch.getId(), Boolean.TRUE);
            if (list == null) {
                list = new ArrayList<>();
            }
            return list;
        }

        return null;
    }


    /**
     * 新插入报检单
     *
     * @param inspectApply
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(InspectApply inspectApply) throws Exception {
        Purch purch = purchDao.findOne(inspectApply.getpId());
        if (purch == null || purch.getStatus() != Purch.StatusEnum.BEING.getCode()) {
            // 采购为空或采购已完成，则返回报检失败
            throw new Exception("采购信息不正确");
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
        List<Attachment> attachmentlist = attachmentService.handleParamAttachment(null, inspectApply.getAttachmentList(), inspectApply.getCreateUserId(), inspectApply.getCreateUserName());
        inspectApply.setAttachmentList(attachmentlist);
        // 处理商品信息处理商品信息处理商品信息
        //  厂家发货且不检查
        boolean directInstockGoods = inspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode() &&
                inspectApply.getDirect() && !inspectApply.getOutCheck();
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

            // 报检数量
            Integer inspectNum = iaGoods.getInspectNum();
            if (inspectNum == null || inspectNum == 0) {
                continue;
            }
            if (inspectNum < 0 || purchGoods.getPurchaseNum() < inspectNum + purchGoods.getPreInspectNum()) {
                throw new Exception("报检数量错误【sku:" + goods.getSku() + "】");
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
                }
                // 设置商品的报检日期,项目的商品跟踪信息
                if (goods.getInspectDate() == null) {
                    goods.setInspectDate(inspectApply.getInspectDate());
                }
                goodsDao.save(goods);
            }
            // 设置预报检商品数量
            purchGoods.setPreInspectNum(purchGoods.getPreInspectNum() + inspectNum);
            purchGoodsDao.save(purchGoods);
        }
        inspectApply.setInspectApplyGoodsList(handledApplyGoods);
        if (directInstockGoods) {
            // 厂家直接发货且是提交，则直接设置为合格状态
            inspectApply.setPubStatus(InspectApply.StatusEnum.QUALIFIED.getCode());
            inspectApply.setStatus(InspectApply.StatusEnum.QUALIFIED.getCode());
        }
        // 设置报检单号
        String lastApplyNo = inspectApplyDao.findLastApplyNo();
        inspectApply.setInspectApplyNo(StringUtil.genInsepctApplyNo(lastApplyNo));
        // 保存报检单信息
        inspectApplyDao.save(inspectApply);
        // 推送数据到入库质检中
        if (inspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode() && !directInstockGoods) {
            pushDataToInspectReport(inspectApply);

            //到货报检通知：到货报检单下达后同时通知质检经办人、仓库经办人

            /*Set<String> projectNoList = new HashSet<>(); //获取项目号 一对多*/
            Set<Integer> qualityNameList = new HashSet<>(); //质检经办人
            Set<Integer> warehouseNameList = new HashSet<>(); //仓库经办人
            Set<String> purchaseNameList = new HashSet<>(); //采购经办人
            for (Project project : purch.getProjects()) {
                /*if (StringUtil.isNotBlank(project.getProjectNo())) {
                    projectNoList.add(project.getProjectNo());
                }*/
                if (StringUtil.isNotBlank(project.getQualityName())) {
                    qualityNameList.add(project.getQualityUid());
                }
                if (StringUtil.isNotBlank(project.getWarehouseName())) {
                    warehouseNameList.add(project.getWarehouseUid());
                }
                if (StringUtil.isNotBlank(project.getPurchaseName())) {
                    purchaseNameList.add(project.getPurchaseName());
                }
            }
            String qualityNames = StringUtils.join(qualityNameList, ",");  //质检经办人
            String warehouseNames = StringUtils.join(warehouseNameList, ",");  //仓库经办人
           /* String projectNos = StringUtils.join(projectNoList, ",");  //项目号*/
            String purchaseNames = StringUtils.join(purchaseNameList, ",");  //采购经办人
            String inspectApplyNo = inspectApply.getInspectApplyNo();           //报检单号

            Map<String, Object> map = new HashMap<>();
            map.put("qualityNames", qualityNames);
            map.put("warehouseNames", warehouseNames);
            map.put("projectNos", purch.getPurchNo());  //采购合同号
            map.put("purchaseNames", purchaseNames);
            map.put("inspectApplyNo", inspectApplyNo);
            sendSms(map);

        } else if (directInstockGoods) {
            //  判断采购是否已经完成并修正
            checkPurchHasDone(inspectApply.getPurch());
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
        InspectApply dbInspectApply = inspectApplyDao.findOne(inspectApply.getId());
        if (dbInspectApply == null || dbInspectApply.getStatus() != InspectApply.StatusEnum.SAVED.getCode()) {
            throw new Exception("报检信息不存在");
        }
        // 处理基本信息
        dbInspectApply.setAbroadCoName(inspectApply.getAbroadCoName());
        dbInspectApply.setInspectDate(inspectApply.getInspectDate());
        dbInspectApply.setDirect(inspectApply.getDirect() != null ? inspectApply.getDirect().booleanValue() : false);
        dbInspectApply.setOutCheck(inspectApply.getOutCheck() != null ? inspectApply.getOutCheck() : true);
        dbInspectApply.setRemark(inspectApply.getRemark());
        dbInspectApply.setStatus(inspectApply.getStatus());
        dbInspectApply.setPubStatus(inspectApply.getStatus()); // 设置报检单的全局状态
        // 处理附件信息
        List<Attachment> attachmentlist = attachmentService.handleParamAttachment(
                dbInspectApply.getAttachmentList(),
                inspectApply.getAttachmentList(),
                inspectApply.getCreateUserId(),
                inspectApply.getCreateUserName());
        dbInspectApply.setAttachmentList(attachmentlist);
        //  厂家发货且不检查
        boolean directInstockGoods = inspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode() &&
                inspectApply.getDirect() && !inspectApply.getOutCheck();
        // 处理商品信息
        Purch purch = dbInspectApply.getPurch();
        Map<Integer, InspectApplyGoods> inspectApplyGoodsMap = dbInspectApply.getInspectApplyGoodsList().parallelStream()
                .collect(Collectors.toMap(InspectApplyGoods::getId, vo -> vo));
        // 生成本次最终的报检商品信息
        List<InspectApplyGoods> inspectApplyGoodsList = new ArrayList<>();
        for (InspectApplyGoods iaGoods : inspectApply.getInspectApplyGoodsList()) {
            InspectApplyGoods applyGoods = inspectApplyGoodsMap.remove(iaGoods.getId());
            if (applyGoods == null) { // 修改的商品不存在
                throw new Exception("报检商品信息错误");
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
                throw new Exception("报检数量错误");
            }
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
                }
                if (goods.getInspectDate() == null) {
                    goods.setInspectDate(dbInspectApply.getInspectDate());
                }
                goodsDao.save(goods);
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

        if (directInstockGoods) {
            // 厂家直接发货且是提交，则直接设置为合格状态
            dbInspectApply.setPubStatus(InspectApply.StatusEnum.QUALIFIED.getCode());
            dbInspectApply.setStatus(InspectApply.StatusEnum.QUALIFIED.getCode());
        }
        // 保存报检单
        inspectApplyDao.save(dbInspectApply);
        // 完善提交后的后续操作
        if (dbInspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode() && !directInstockGoods) {
            // 推送数据到入库质检中
            pushDataToInspectReport(dbInspectApply);

            //到货报检通知：到货报检单下达后同时通知质检经办人、仓库经办人
            disposeSmsDate(dbInspectApply,inspectApply);

        } else if (directInstockGoods) {
            //  判断采购是否已经完成并修正
            checkPurchHasDone(dbInspectApply.getPurch());
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
        InspectApply lastInspectApply = inspectApplyDao.findOne(inspectApply.getId());
        if (lastInspectApply == null) {
            throw new Exception("不存在的报检单");
        }
        if (lastInspectApply.getStatus() != InspectApply.StatusEnum.UNQUALIFIED.getCode()) {
            throw new Exception("当期报检单没有未合格商品");
        }
        InspectApply parentInspectApply = lastInspectApply.getParent(); // 主报检单
        if (parentInspectApply == null) {
            parentInspectApply = lastInspectApply;
        }
        // 检查是否是最后一次报检信息
        if (parentInspectApply.getNum().intValue() != lastInspectApply.getNum()) {
            throw new Exception("重新报检的不是最后一次质检结果");
        }
        InspectApply lastInspectApplyTest = inspectApplyDao.findByInspectApplyNo(String.format("%s-%d", parentInspectApply.getInspectApplyNo(), (parentInspectApply.getNum() - 1)));
        if (parentInspectApply.getNum() != 1 && lastInspectApplyTest.getId() != lastInspectApply.getId().intValue()) {
            throw new Exception("重新报检的不是最后一次质检结果");
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
        List<Attachment> attachmentList = attachmentService.handleParamAttachment(null, inspectApply.getAttachmentList(), inspectApply.getCreateUserId(), inspectApply.getCreateUserName());
        newInspectApply.setAttachmentList(attachmentList);

        newInspectApply.setInspectApplyGoodsList(goodsDataList);

        newInspectApply = inspectApplyDao.save(newInspectApply);

        // 推送数据到入库质检中
        pushDataToInspectReport(newInspectApply);

        //到货报检通知：到货报检单下达后同时通知质检经办人、仓库经办人
        disposeSmsDate(lastInspectApply,inspectApply);

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
            inspectApply.getAttachmentList().size();
            inspectApply.getInspectApplyGoodsList().size();
        }

        return inspectApply;
    }


    /**
     * 向入库质检推送数据
     */
    private void pushDataToInspectReport(InspectApply inspectApply) {
        // 新建质检信息并完善
        InspectReport report = new InspectReport();
        report.setInspectApply(inspectApply);
        report.setInspectApplyNo(inspectApply.getInspectApplyNo());
        report.setReportFirst(inspectApply.isMaster());
        report.setSupplierName(inspectApply.getSupplierName());

        // 判断是不是第一次报检并设置相应信息
        if (inspectApply.isMaster()) {
            report.setCheckTimes(1);
            Set<Project> projects = inspectApply.getPurch().getProjects();
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

        List<InspectApplyGoods> inspectApplyGoodsList = new ArrayList<>();
        // 设置质检关联到报检商品信息
        inspectApply.getInspectApplyGoodsList().forEach(vo -> {
            InspectApplyGoods iag = new InspectApplyGoods();
            iag.setId(vo.getId());
            inspectApplyGoodsList.add(iag);
        });
        report.setInspectGoodsList(inspectApplyGoodsList);
        // 保存推送的质检信息并等待人工质检
        inspectReportDao.save(report);

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
    public void disposeSmsDate(InspectApply dbInspectApply ,InspectApply inspectApply ) throws Exception {
        //到货报检通知：到货报检单下达后同时通知质检经办人、仓库经办人

        /*Set<String> projectNoList = new HashSet<>(); //获取项目号 一对多*/
        Set<Integer> qualityNameList = new HashSet<>(); //质检经办人
        Set<Integer> warehouseNameList = new HashSet<>(); //仓库经办人
        Set<String> purchaseNameList = new HashSet<>(); //采购经办人
        Purch purch = dbInspectApply.getPurch();
        for (Project project : purch.getProjects()){
            /*if(StringUtil.isNotBlank(project.getProjectNo())){
                projectNoList.add(project.getProjectNo());
            }*/
            if(StringUtil.isNotBlank(project.getQualityName())){
                qualityNameList.add(project.getQualityUid());
            }
            if(StringUtil.isNotBlank(project.getWarehouseName())){
                warehouseNameList.add(project.getWarehouseUid());
            }
            if(StringUtil.isNotBlank(project.getPurchaseName())){
                purchaseNameList.add(project.getPurchaseName());
            }
        }
        String qualityNames =  StringUtils.join(qualityNameList, ",");  //质检经办人
        String warehouseNames =  StringUtils.join(warehouseNameList, ",");  //仓库经办人
       /* String projectNos =  StringUtils.join(projectNoList, ",");  //项目号*/
        String purchaseNames =  StringUtils.join(purchaseNameList, ",");  //采购经办人
        /*String inspectApplyNo = inspectApply.getInspectApplyNo();    */
        String inspectApplyNo1 = dbInspectApply.getInspectApplyNo();//报检单号

        Map<String,Object> map = new HashMap<>();
        map.put("qualityNames",qualityNames);
        map.put("warehouseNames",warehouseNames);
        map.put("projectNos",purch.getPurchNo()); // 采购合同号
        map.put("purchaseNames",purchaseNames);
        /*if(StringUtil.isNotBlank(inspectApplyNo) && inspectApplyNo != null){
            map.put("inspectApplyNo",inspectApplyNo);
        }*/
        map.put("inspectApplyNo",inspectApplyNo1);
        sendSms(map);
    }





    //到货报检通知：到货报检单下达后同时通知质检经办人、仓库经办人
    public void sendSms(Map<String, Object> map1) throws Exception {

        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (StringUtils.isNotBlank(eruiToken)) {
            try {
                String mobile = null;  //质检经办人+采购经办人手机号
                Set<String> qualityNameSet = new HashSet();    //质检经办人  手机号
                Set<String> warehouseNameSet = new HashSet();    //采购经办人  手机号
                String qualityNames = (String) map1.get("qualityNames");
                String warehouseNames = (String) map1.get("warehouseNames");
                String[] split = qualityNames.split(",");
                String[] split2 = warehouseNames.split(",");

                Map<String, String> header = new HashMap<>();
                header.put(EruitokenUtil.TOKEN_NAME, eruiToken);
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

                //采购经办人  手机号
                for (String s2 : split2) {
                    String jsonParam = "{\"id\":\"" + s2 + "\"}";
                    String s3 = HttpRequest.sendPost(memberInformation, jsonParam, header);
                    logger.info("人员详情返回信息：" + s3);
                    // 获取手机号
                    JSONObject jsonObject = JSONObject.parseObject(s3);
                    Integer code = jsonObject.getInteger("code");
                    if (code == 1) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        warehouseNameSet.add(data.getString("mobile"));
                    }
                }

                
               
                //去除重复
                Set<String> listAll = new HashSet<>();
                listAll.addAll(qualityNameSet);
                listAll.addAll(warehouseNameSet);


                //获取徐健 手机号
                String name = null;
                String jsonParam = "{\"id\":\"29606\"}";
                String s3 = HttpRequest.sendPost(memberInformation, jsonParam, header);
                logger.info("人员详情返回信息：" + s3);
                // 获取手机号
                JSONObject jsonObject = JSONObject.parseObject(s3);
                Integer code = jsonObject.getInteger("code");
                if (code == 1) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    name=data.getString("mobile");
                }
                /*listAll.add("15066060360");*/
                listAll.add(name);

                listAll = new HashSet<>(new LinkedHashSet<>(listAll));
                JSONArray smsarray = new JSONArray();
                for (String me : listAll) {
                    smsarray.add(me);
                }
                //发送短信
                if (smsarray.size() > 0) {
                    Map<String,String> map= new HashMap();
                    map.put("areaCode","86");
                    map.put("to",smsarray.toString());
                    map.put("content","您好，采购合同号："+map1.get("projectNos")+"，报检单号："+map1.get("inspectApplyNo")+"，采购经办人："+map1.get("purchaseNames")+"，已申请报检，请及时处理。感谢您对我们的支持与信任！");
                    map.put("subType","0");
                    map.put("groupSending","0");
                    map.put("useType","订单");
                    String s1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                    logger.info("发送短信返回状态"+s1);
                }

            } catch (Exception e) {
                throw new Exception("发送短信失败");
            }

        }
    }



}
