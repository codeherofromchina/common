package com.erui.order.service.impl;

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
    public boolean insert(InspectApply inspectApply) {
        // 处理基本信息
        inspectApply.setInspectApplyNo(RandomStringUtils.randomAlphanumeric(32));
        Purch purch = purchDao.findOne(inspectApply.getpId());
        if (purch == null || purch.getStatus() != Purch.StatusEnum.BEING.getCode()) {
            // 采购为空或采购已完成，则返回报检失败
            return false;
        }
        // 基本信息设置
        inspectApply.setPurch(purch);
        inspectApply.setPurchNo(purch.getPurchNo());
        inspectApply.setDirect(inspectApply.getDirect() != null ? inspectApply.getDirect() : false);
        inspectApply.setOutCheck(inspectApply.getOutCheck() != null ? inspectApply.getOutCheck() : true);
        inspectApply.setMaster(true);
        inspectApply.setNum(1);
        inspectApply.setHistory(false);
        Date now = new Date();
        inspectApply.setCreateTime(new Date());

        //  厂家发货且不检查
        boolean directInstockGoods = inspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode() &&
                inspectApply.getDirect() && !inspectApply.getOutCheck();

        Map<Integer, PurchGoods> purchGoodsMap = purch.getPurchGoodsList().parallelStream().collect(Collectors.toMap(vo -> vo.getGoods().getId(), vo -> vo));
        List<Goods> updateInspectNumGoods = new ArrayList<>();
        // 处理报检商品信息
        for (InspectApplyGoods iaGoods : inspectApply.getInspectApplyGoodsList()) {
            iaGoods.setId(null);
            iaGoods.setCreateTime(now);
            iaGoods.setInspectApply(inspectApply);

            PurchGoods purchGoods = purchGoodsMap.get(iaGoods.getgId());
            Goods goods = purchGoods.getGoods();
            iaGoods.setGoods(goods);
            iaGoods.setPurchGoods(purchGoods);
            iaGoods.setPurchaseNum(purchGoods.getPurchaseNum());
            iaGoods.setSamples(0);
            iaGoods.setInstockNum(0);
            iaGoods.setUnqualified(0);

            // 修改采购单中的已报检数量
            if (purchGoods.getInspectNum() + iaGoods.getInspectNum() > purchGoods.getPurchaseNum()) {
                // 报检的总数量不能大于采购数量
                return false;
            }
            purchGoods.setInspectNum(purchGoods.getInspectNum() + iaGoods.getInspectNum());
            // 修改商品的已报检数量
            if (purchGoods.getExchanged()) {
                goods = goodsDao.findOne(goods.getParentId());
            }
            goods.setInspectNum(goods.getInspectNum() + iaGoods.getInspectNum());
            if (directInstockGoods) {
                // 厂家发货且不检查，则增加商品的入库数量
                goods.setInstockNum(goods.getInstockNum() + iaGoods.getInspectNum());
            }
            updateInspectNumGoods.add(goods);
        }
        // 更新采购单中的已报检数量到数据库
        purchGoodsDao.save(purchGoodsMap.values());
        // 更新商品中的已报检数量到数据库
        goodsDao.save(updateInspectNumGoods);

        // 处理附件信息
        List<Attachment> attachmentlist = attachmentService.handleParamAttachment(null, inspectApply.getAttachmentList(), inspectApply.getCreateUserId(), inspectApply.getCreateUserName());
        inspectApply.setAttachmentList(attachmentlist);

        inspectApplyDao.save(inspectApply);

        if (inspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode() && !directInstockGoods) {
            // 推送数据到入库质检中
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
        Purch purch = dbInspectApply.getPurch();
        if (purch.getStatus() != Purch.StatusEnum.BEING.getCode()) {
            // 采购其他状态，则不存在报检质检等商品，返回错误
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

        //  厂家发货且不检查
        boolean directInstockGoods = inspectApply.getStatus() == InspectApply.StatusEnum.SUBMITED.getCode() &&
                inspectApply.getDirect() && !inspectApply.getOutCheck();

        // 处理附件信息
        List<Attachment> attachmentlist = attachmentService.handleParamAttachment(
                dbInspectApply.getAttachmentList(),
                inspectApply.getAttachmentList(),
                inspectApply.getCreateUserId(),
                inspectApply.getCreateUserName());
        dbInspectApply.setAttachmentList(attachmentlist);

        // 处理商品信息
        Map<Integer, PurchGoods> purchGoodsMap = purch.getPurchGoodsList().parallelStream().collect(Collectors.toMap(vo -> vo.getGoods().getId(), vo -> vo));
        Map<Integer, InspectApplyGoods> inspectApplyGoodsMap = dbInspectApply.getInspectApplyGoodsList().parallelStream()
                .collect(Collectors.toMap(InspectApplyGoods::getId, vo -> vo));
        // 生成本次最终的报检商品信息
        List<InspectApplyGoods> inspectApplyGoodsList = new ArrayList<>();
        for (InspectApplyGoods iaGoods : inspectApply.getInspectApplyGoodsList()) {
            Integer id = iaGoods.getId();
            Goods goods = null;
            PurchGoods purchGoods = null;
            int inspectNum = iaGoods.getInspectNum();

            InspectApplyGoods applyGoods = inspectApplyGoodsMap.remove(id);
            if (applyGoods != null) {
                inspectNum -= applyGoods.getInspectNum();
                applyGoods.setInspectNum(iaGoods.getInspectNum());
                goods = applyGoods.getGoods();
                purchGoods = purchGoodsMap.get(goods.getId());
                applyGoods.setPurchaseNum(purchGoods.getPurchaseNum());
            } else {
                applyGoods = iaGoods;
                applyGoods.setCreateTime(new Date());
                applyGoods.setInspectApply(inspectApply);
                purchGoods = purchGoodsMap.get(applyGoods.getgId());
                goods = purchGoods.getGoods();
                applyGoods.setPurchaseNum(purchGoods.getPurchaseNum());
                applyGoods.setGoods(goods);
                applyGoods.setSamples(0);
                applyGoods.setUnqualified(0);
            }
            iaGoods.setPurchaseNum(purchGoods.getPurchaseNum());
            applyGoods.setInspectApply(dbInspectApply);
            applyGoods.setPurchGoods(purchGoods);

            // 修改采购单中的已报检数量
            if (purchGoods.getInspectNum() + inspectNum > purchGoods.getPurchaseNum()) {
                throw new Exception("报检数量不能大于采购数量");
            }
            purchGoods.setInspectNum(purchGoods.getInspectNum() + inspectNum);
            // 修改商品的已报检数量
            if (goods.getParentId() != null) {
                goods = goodsDao.findOne(goods.getParentId()); // 修改主商品的报检数量
            }
            goods.setInspectNum(goods.getInspectNum() + inspectNum);
            if (directInstockGoods) {
                // 提交的是厂家发货且不检查，则增加商品的入库数量
                goods.setInstockNum(goods.getInstockNum() + applyGoods.getInspectNum());
            }
            goodsDao.save(goods);
            purchGoodsDao.save(purchGoods);
            inspectApplyGoodsList.add(applyGoods);
        }
        // 报检商品中存在删除的情况操作
        for (InspectApplyGoods inspectApplyGoods : inspectApplyGoodsMap.values()) {
            Integer inspectNum = inspectApplyGoods.getInspectNum();
            Goods goods = inspectApplyGoods.getGoods();
            PurchGoods purchGoods = purchGoodsMap.get(goods.getId());
            if (goods.getParentId() != null) {
                goods = goodsDao.findOne(goods.getParentId());
            }

            goods.setInspectNum(goods.getInspectNum() - inspectNum);
            purchGoods.setInspectNum(purchGoods.getInspectNum() - inspectNum);
            goodsDao.save(goods);
            purchGoodsDao.save(purchGoods);
        }

        inspectApplyGoodsDao.delete(inspectApplyGoodsMap.values());
        // 更新采购单中的已报检数量到数据库
        purchGoodsDao.save(purchGoodsMap.values());
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
        Map<Integer, InspectApplyGoods> inspectApplyGoodsMap = inspectApplyGoodsDao.findByInspectApplyId(lastInspectApply.getId()).parallelStream()
                .filter(vo -> vo.getUnqualified() > 0).collect(Collectors.toMap(InspectApplyGoods::getId, vo -> vo));
        List<InspectApplyGoods> goodsDataList = new ArrayList<>();
        List<InspectApplyGoods> inspectApplyGoodsList = inspectApply.getInspectApplyGoodsList();
        for (InspectApplyGoods inspectApplyGoods : inspectApplyGoodsList) {
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

        //新插入报检单，并设置上级报检单
        newInspectApply.setInspectApplyNo(parentInspectApply.getInspectApplyNo());
        newInspectApply.setParent(parentInspectApply);
        newInspectApply.setMaster(false);
        newInspectApply.setPurch(parentInspectApply.getPurch());

        // 设置下发部门
        newInspectApply.setDepartment(lastInspectApply.getDepartment());
        // 设置采购经办人
        newInspectApply.setPurchaseName(lastInspectApply.getPurchaseName());
        // 设置供应商名称
        newInspectApply.setSupplierName(lastInspectApply.getSupplierName());
        // 设置国外分公司
        newInspectApply.setAbroadCoName(lastInspectApply.getAbroadCoName());
        // 设置报检时间
        newInspectApply.setInspectDate(lastInspectApply.getInspectDate());
        newInspectApply.setDirect(lastInspectApply.getDirect());
        newInspectApply.setOutCheck(lastInspectApply.getOutCheck());
        newInspectApply.setMsg(inspectApply.getMsg());
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

        InspectReport report02 = inspectReportDao.save(report);

        List<InspectApplyGoods> inspectApplyGoodsList = inspectApply.getInspectApplyGoodsList();
        inspectApplyGoodsList.forEach(vo -> {
            vo.setInspectReportId(report02.getId());
        });
        inspectApplyGoodsDao.save(inspectApplyGoodsList);
    }

}
