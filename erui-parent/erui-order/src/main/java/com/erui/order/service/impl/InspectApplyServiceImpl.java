package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.requestVo.PGoods;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.InspectApplyService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public InspectApply findById(Integer id) {
        return inspectApplyDao.findOne(id);
    }

    @Override
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
    @Transactional
    public boolean insert(InspectApply inspectApply) throws Exception {

        Purch purch = purchDao.findOne(inspectApply.getpId());
        if (purch == null || purch.getStatus() != Purch.StatusEnum.BEING.getCode()) {
            // 采购为空或采购已完成，则返回报检失败
            return false;
        }
        final Date now = new Date();

        // 基本信息设置
        inspectApply.setInspectApplyNo(RandomStringUtils.randomAlphanumeric(32));
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

        Map<Integer, PurchGoods> purchGoodsMap = purch.getPurchGoodsList().parallelStream().collect(Collectors.toMap(PurchGoods::getId, vo -> vo));
        // 处理报检商品信息
        for (InspectApplyGoods iaGoods : inspectApply.getInspectApplyGoodsList()) {
            PurchGoods purchGoods = purchGoodsMap.get(iaGoods.getPurchGid());
            PurchGoods parentPurchGoods = purchGoods.getParent();
            if (parentPurchGoods == null) {
                parentPurchGoods = purchGoods;
            }
            Goods goods = purchGoods.getGoods();

            iaGoods.setId(null);
            iaGoods.setInspectApply(inspectApply);
            iaGoods.setGoods(goods);
            iaGoods.setPurchGoods(purchGoods);
            iaGoods.setPurchaseNum(parentPurchGoods.getPurchaseNum());
            // 报检数量
            Integer inspectNum = iaGoods.getInspectNum();
            if (inspectNum == null || parentPurchGoods.getPurchaseNum() - parentPurchGoods.getInspectNum() < inspectNum) {
                throw new Exception("报检数量错误");
            }
            iaGoods.setSamples(0);
            iaGoods.setUnqualified(0);
            iaGoods.setInstockNum(0);
            iaGoods.setCreateTime(now);

            // 如果是提交，则修改采购商品（父采购商品）中的已报检数量和商品（父商品）中的已报检数量
            if (inspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode()) {
                parentPurchGoods.setInspectNum(parentPurchGoods.getInspectNum() + iaGoods.getInspectNum());

                // 修改商品的已报检数量
                Goods parentGoods = null;
                if (purchGoods.getExchanged()) {
                    parentGoods = goodsDao.findOne(goods.getParentId());
                }
                if (parentGoods == null) {
                    parentGoods = goods;
                }
                parentGoods.setInspectNum(parentGoods.getInspectNum() + iaGoods.getInspectNum());
                if (directInstockGoods) {
                    // 增加采购商品检验合格数量
                    parentPurchGoods.setGoodNum(parentPurchGoods.getGoodNum() + iaGoods.getInspectNum());
                    // 厂家发货且不检查，则增加商品的已入库数量
                    parentGoods.setInstockNum(parentGoods.getInstockNum() + iaGoods.getInspectNum());
                }
                goodsDao.save(parentGoods);
                purchGoodsDao.save(parentPurchGoods);
            }
        }
        // 保存报检单信息
        inspectApplyDao.save(inspectApply);
        // 推送数据到入库质检中
        if (inspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode() && !directInstockGoods) {
            pushDataToInspectReport(inspectApply);
        }
        return true;
    }

    /**
     * 更新报检单
     *
     * @param inspectApply
     * @return
     */
    @Override
    @Transactional
    public boolean save(InspectApply inspectApply) throws Exception {
        InspectApply dbInspectApply = inspectApplyDao.findOne(inspectApply.getId());
        if (dbInspectApply == null || dbInspectApply.getStatus() != InspectApply.StatusEnum.SAVED.getCode()) {
            return false;
        }

        // 处理基本信息
        dbInspectApply.setDepartment(inspectApply.getDepartment());
        dbInspectApply.setPurchaseName(inspectApply.getPurchaseName());
        dbInspectApply.setSupplierName(inspectApply.getSupplierName());
        dbInspectApply.setAbroadCoName(inspectApply.getAbroadCoName());
        dbInspectApply.setInspectDate(inspectApply.getInspectDate());
        dbInspectApply.setDirect(inspectApply.getDirect() != null ? inspectApply.getDirect().booleanValue() : false);
        dbInspectApply.setOutCheck(inspectApply.getOutCheck() != null ? inspectApply.getOutCheck() : true);
        dbInspectApply.setRemark(inspectApply.getRemark());
        dbInspectApply.setStatus(inspectApply.getStatus());

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
        Map<Integer, PurchGoods> purchGoodsMap = purch.getPurchGoodsList().parallelStream().collect(Collectors.toMap(PurchGoods::getId, vo -> vo));
        Map<Integer, InspectApplyGoods> inspectApplyGoodsMap = dbInspectApply.getInspectApplyGoodsList().parallelStream()
                .collect(Collectors.toMap(InspectApplyGoods::getId, vo -> vo));
        // 生成本次最终的报检商品信息
        List<InspectApplyGoods> inspectApplyGoodsList = new ArrayList<>();
        for (InspectApplyGoods iaGoods : inspectApply.getInspectApplyGoodsList()) {

            InspectApplyGoods applyGoods = inspectApplyGoodsMap.remove(iaGoods.getId());
            if (applyGoods == null) { // 修改的商品不存在
                return false;
            }

            applyGoods.setInspectNum(iaGoods.getInspectNum());
            applyGoods.setHeight(iaGoods.getHeight());
            applyGoods.setLwh(iaGoods.getLwh());


            PurchGoods purchGoods = applyGoods.getPurchGoods();
            PurchGoods parentPurchGoods = purchGoods.getParent();
            if (parentPurchGoods == null) {
                parentPurchGoods = purchGoods;
            }
            // 报检数量大于采购数量
            if (applyGoods.getInspectNum() > parentPurchGoods.getPurchaseNum() - parentPurchGoods.getInspectNum()) {
                throw new Exception("报检数量错误");
            }

            // 如果是提交，则修改采购商品（父采购商品）中的已报检数量和商品（父商品）中的已报检数量
            if (dbInspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode()) {

                parentPurchGoods.setInspectNum(purchGoods.getInspectNum() + applyGoods.getInspectNum());

                // 修改商品的已报检数量
                Goods parentGoods = applyGoods.getGoods();
                if (parentGoods.getParentId() != null) {
                    parentGoods = goodsDao.findOne(parentGoods.getParentId());
                }
                parentGoods.setInspectNum(parentGoods.getInspectNum() + applyGoods.getInspectNum());
                if (directInstockGoods) {
                    // 增加采购商品检验合格数量
                    parentPurchGoods.setGoodNum(parentPurchGoods.getGoodNum() + applyGoods.getInspectNum());
                    // 厂家发货且不检查，则增加商品的已入库数量
                    parentGoods.setInstockNum(parentGoods.getInstockNum() + applyGoods.getInspectNum());
                }
                goodsDao.save(parentGoods);
                purchGoodsDao.save(parentPurchGoods);
            }
            inspectApplyGoodsList.add(applyGoods);
        }

        inspectApplyGoodsDao.delete(inspectApplyGoodsMap.values());
        // 设置报检商品信息
        dbInspectApply.setInspectApplyGoodsList(inspectApplyGoodsList);
        // 保存报检单
        inspectApplyDao.save(dbInspectApply);

        if (dbInspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode() && !directInstockGoods) {
            // 推送数据到入库质检中
            pushDataToInspectReport(dbInspectApply);
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
    @Transactional
    public boolean againApply(InspectApply inspectApply) {

        InspectApply lastInspectApply = inspectApplyDao.findOne(inspectApply.getId());
        if (lastInspectApply == null || lastInspectApply.getStatus() != InspectApply.StatusEnum.UNQUALIFIED.getCode()) {
            return false; // 不存在原来的报检单或当期报检单没有未合格产品，返回错误
        }

        InspectApply parentInspectApply = lastInspectApply.getParent(); // 主报检单
        if (parentInspectApply == null) {
            parentInspectApply = lastInspectApply;
        }

        // 声明要插入的报检单
        InspectApply newInspectApply = new InspectApply();

        // 判断每个商品的报检数量是否等于最后一次报检不合格数量
        Map<Integer, InspectApplyGoods> inspectApplyGoodsMap = lastInspectApply.getInspectApplyGoodsList().parallelStream()
                .filter(vo -> vo.getUnqualified() > 0).collect(Collectors.toMap(InspectApplyGoods::getId, vo -> vo));
        List<InspectApplyGoods> goodsDataList = new ArrayList<>();
        for (InspectApplyGoods inspectApplyGoods : inspectApply.getInspectApplyGoodsList()) {
            Integer id = inspectApplyGoods.getId();
            InspectApplyGoods applyGoods = inspectApplyGoodsMap.get(id);

            if (applyGoods == null) {
                return false;
            }

            if (applyGoods.getUnqualified() != inspectApplyGoods.getInspectNum()) {
                return false;
            }

            inspectApplyGoods.setId(null);
            inspectApplyGoods.setInspectApply(newInspectApply);
            inspectApplyGoods.setGoods(applyGoods.getGoods());
            inspectApplyGoods.setPurchaseNum(applyGoods.getPurchaseNum());
            inspectApplyGoods.setPurchGoods(applyGoods.getPurchGoods());
            inspectApplyGoods.setHeight(applyGoods.getHeight());
            inspectApplyGoods.setLwh(inspectApplyGoods.getLwh());
            inspectApplyGoods.setSamples(0);
            inspectApplyGoods.setUnqualified(0);
            inspectApplyGoods.setInstockNum(0);
            inspectApplyGoods.setCreateTime(new Date());

            goodsDataList.add(inspectApplyGoods);
        }

        if (inspectApplyGoodsMap.size() != goodsDataList.size()) {
            return false;
        }

        // 检验完毕，做正式操作
        // 主报检单的报检数量+1
        parentInspectApply.setNum(parentInspectApply.getNum() + 1);
        parentInspectApply.setHistory(true);
        inspectApplyDao.save(parentInspectApply);

        // 获取是第几次报检
        int applyNum = parentInspectApply == lastInspectApply ? 2 : (lastInspectApply.getNum() + 1);
        //新插入报检单，并设置上级报检单
        newInspectApply.setInspectApplyNo(parentInspectApply.getInspectApplyNo() + "-" + applyNum);
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
        newInspectApply.setNum(applyNum);
        newInspectApply.setCreateTime(new Date());
        //插入新报检单附件信息
        newInspectApply.setStatus(InspectApply.StatusEnum.SUBMITED.getCode());
        List<Attachment> attachmentList = attachmentService.handleParamAttachment(null, inspectApply.getAttachmentList(), inspectApply.getCreateUserId(), inspectApply.getCreateUserName());
        newInspectApply.setAttachmentList(attachmentList);

        newInspectApply.setInspectApplyGoodsList(goodsDataList);

        inspectApplyDao.save(newInspectApply);

        // 推送数据到入库质检中
        pushDataToInspectReport(newInspectApply);

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
    @Transactional
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
        } else {
            InspectApply parent = inspectApply.getParent();
            report.setCheckTimes(parent.getNum());
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

}
