package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.requestVo.PGoods;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.InspectApplyService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class InspectApplyServiceImpl implements InspectApplyService {

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

    @Override
    @Transactional(readOnly = true)
    public InspectApply findById(Integer id) {
        return inspectApplyDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InspectApply> findMasterListByParchId(Integer purchId) {
        Purch purch = purchDao.findOne(purchId);
        if (purch != null &&
                (purch.getStatus() == Purch.StatusEnum.DONE.getCode()
                        || purch.getStatus() == Purch.StatusEnum.BEING.getCode())) {

            List<InspectApply> list = inspectApplyDao.findByPurchIdAndMasterOrderByCreateTimeDesc(purch.getId(), Boolean.TRUE);
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
            if (inspectNum == null || inspectNum <= 0 || purchGoods.getPurchaseNum() < inspectNum + purchGoods.getPreInspectNum()) {
                throw new Exception("报检数量错误【sku:" + goods.getSku() + "】");
            }
            iaGoods.setSamples(0);
            iaGoods.setUnqualified(0);
            iaGoods.setInstockNum(0);
            iaGoods.setCreateTime(now);
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
                if (goods.getInspectDate() != null) {
                    goods.setInspectDate(inspectApply.getInspectDate());
                }
                goodsDao.save(goods);
            }
            // 设置预报检商品数量
            purchGoods.setPreInspectNum(purchGoods.getPreInspectNum() + inspectNum);
            purchGoodsDao.save(purchGoods);
        }
        if (inspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode() && directInstockGoods) {
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
        } else if (directInstockGoods) {
            //  判断采购是否已经完成并修正
            checkPurchHasDone(inspectApply.getPurch());
        }
        return true;
    }


    @Transactional
    private void checkPurchHasDone(Purch purch) {
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
            if (inspectNum == null || inspectNum <= 0 || inspectNum - oldInspectNum > purchGoods.getPurchaseNum() - purchGoods.getPreInspectNum()) {
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
        // 保存报检单
        inspectApplyDao.save(dbInspectApply);
        // 完善提交后的后续操作
        if (dbInspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode() && !directInstockGoods) {
            // 推送数据到入库质检中
            pushDataToInspectReport(dbInspectApply);
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
        // 如果最后一次报检不是上级报检单，则修改上次报检的pubStatus =UNQUALIFIED
        if (parentInspectApply != lastInspectApply) {
            lastInspectApply.setPubStatus(InspectApply.StatusEnum.UNQUALIFIED.getCode());
            inspectApplyDao.save(lastInspectApply);
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
            inspectApplyGoods.setLwh(inspectApplyGoods.getLwh());
            inspectApplyGoods.setInspectNum(applyGoods.getUnqualified());
            inspectApplyGoods.setSamples(0);
            inspectApplyGoods.setUnqualified(0);
            inspectApplyGoods.setInstockNum(0);
            inspectApplyGoods.setCreateTime(new Date());
            goodsDataList.add(inspectApplyGoods);
        }
        // 检验完毕，做正式操作
        // 主报检单的报检数量+1
        parentInspectApply.setNum(parentInspectApply.getNum() + 1);
        parentInspectApply.setHistory(true);
        parentInspectApply.setPubStatus(InspectApply.StatusEnum.SUBMITED.getCode()); // 设置全局状态为待审核
        inspectApplyDao.save(parentInspectApply);

        //新插入报检单，并设置上级报检单
        newInspectApply.setInspectApplyNo(parentInspectApply.getInspectApplyNo() + "-" + parentInspectApply.getNum());
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
        // 设置报检时间
        newInspectApply.setInspectDate(NewDateUtil.getDate(new Date()));
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

        return true;
    }

    @Override
    public List<InspectApply> findByParentId(Integer parentId) {
        // 存在多次报检
        List<InspectApply> inspectApplyList = inspectApplyDao.findByParentIdOrderByIdDesc(parentId);
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
        InspectReport report = new InspectReport();
        report.setInspectApply(inspectApply);
        report.setInspectApplyNo(inspectApply.getInspectApplyNo());
        report.setReportFirst(inspectApply.isMaster());
        report.setSupplierName(inspectApply.getSupplierName());

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
        inspectApply.getInspectApplyGoodsList().forEach(vo -> {
            InspectApplyGoods iag = new InspectApplyGoods();
            iag.setId(vo.getId());
            inspectApplyGoodsList.add(iag);
        });
        report.setInspectGoodsList(inspectApplyGoodsList);

        inspectReportDao.save(report);

    }

    @Override
    @Transactional
    public void fullTmpMsg(Integer id, String tmpMsg) {
        InspectApply one = inspectApplyDao.findOne(id);
        one.setTmpMsg(tmpMsg);
        inspectApplyDao.save(one);
    }


    @Override
    @Transactional(readOnly = true)
    public InspectApply findSonFailDetail(Integer parentId) {
        InspectApply parentInspectApply = inspectApplyDao.findOne(parentId);
        if (parentInspectApply == null || parentInspectApply.getNum() < 2) {
            return null;
        }

        InspectApply inspectApply = inspectApplyDao.findByInspectApplyNo(String.format("%s-%d", parentInspectApply.getInspectApplyNo(), parentInspectApply.getNum()));
        if (inspectApply == null || inspectApply.getStatus() != InspectApply.StatusEnum.UNQUALIFIED.getCode()) {
            return null;
        }
        inspectApply.getAttachmentList().size();
        inspectApply.getInspectApplyGoodsList().size();

        return inspectApply;
    }

}
