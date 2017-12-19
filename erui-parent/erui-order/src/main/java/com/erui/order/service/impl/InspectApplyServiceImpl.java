package com.erui.order.service.impl;

import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.requestVo.InspectApplySaveVo;
import com.erui.order.requestVo.PGoods;
import com.erui.order.service.InspectApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    @Override
    public InspectApply findById(Integer id) {
        return inspectApplyDao.findOne(id);
    }


    @Override
    public List<InspectApply> findListByParchId(Integer parchId) {
        List<InspectApply> list = inspectApplyDao.findByPurchId(parchId);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 0：是更新还是提交
     * 1：查看是否厂家直发，如果直发且无检查，且是提交，则更新商品已入库数量
     * 1：修改采购商品的已报检数量
     * 1：检查采购是否已经报检完成
     * 1：更改报检单基本信息
     * 2：插入报检单商品信息
     * 3：插入报检单附件信息
     *
     * @param vo
     * @return
     */
    @Override
    @Transactional
    public boolean save(InspectApplySaveVo vo) {
        // 是更新还是提交
        boolean insertFlag = true;
        InspectApply inspectApply = null;
        Integer id = vo.getId();
        Integer purchId = vo.getPurchId();
        Purch purch = null;


        if (id != null) {
            inspectApply = inspectApplyDao.findOne(id);
            if (inspectApply == null) {
                return false; // 不存在的报检单，返回失败
            }
            insertFlag = false;
            purch = inspectApply.getPurch();
        } else {
            inspectApply = new InspectApply();
            purch = purchDao.findOne(purchId);
            if (purch == null) {
                return false; // 没有关联到任何的采购单信息
            }
            inspectApply.setCreateTime(new Date());
            inspectApply.setPurch(purch);
        }
        int status = vo.getStatus();
        boolean direct = vo.isDirect();
        boolean outCheck = vo.isOutCheck();
        List<InspectApplyGoods> inspectApplyGoodsList = new ArrayList<>();

        // 1.查看是否厂家直发，如果直发且无检查，且是提交，则更新商品已入库数量
        // 此种情况下商品不需要走下面的入库质检和入库流程
        // 2.修改采购商品的已报检数量
        // 3.检查采购是否已经报检完成
        List<PGoods> goodsList = vo.getGoodsList();
        // 整理商品信息，过程中操作上面的3步
        for (PGoods pGoods : goodsList) {
            Integer inspectApplyGoodsId = pGoods.getId();
            Integer goodsId = pGoods.getGoodsId();
            Integer inspectNum = pGoods.getInspectNum();
            int diff = inspectNum;

            Goods goods = goodsDao.findOne(goodsId);
            if (goods == null) {
                return false; // 要操作的商品不存在，返回失败
            }


            if (insertFlag && inspectApplyGoodsId != null) {
                // 修改商品
                InspectApplyGoods inspectApplyGoods = inspectApplyGoodsDao.findByIdAndGoodsIdAndInspectApplyId(inspectApply.getId(), goodsId, inspectApplyGoodsId);
                if (inspectApplyGoods == null) {
                    // 不存在要修改的商品，返回失败
                    return false;
                }
                // 上次未提交时报检的商品数量
                Integer inspectedNum = inspectApplyGoods.getInspectNum();
                diff = inspectNum - inspectedNum;
                inspectApplyGoods.setInspectNum(inspectNum);

                inspectApplyGoodsList.add(inspectApplyGoods);
            } else {
                // 新插入商品
                InspectApplyGoods inspectApplyGoods = new InspectApplyGoods();
                inspectApplyGoods.setInspectApply(inspectApply);
                inspectApplyGoods.setGoods(goods);
                inspectApplyGoods.setInspectNum(inspectNum);
                inspectApplyGoods.setCreateTime(new Date());

                inspectApplyGoodsList.add(inspectApplyGoods);
            }

            // 1.查看是否厂家直发，如果直发且无检查，且是提交，则更新商品已入库数量
            if (direct && !outCheck && status == 2) {
                goods.setInstockNum(goods.getInstockNum() + inspectNum);
            }
            goods.setPurchasedNum(goods.getPurchasedNum() + diff); // 修改商品的总报检数量
            goodsDao.save(goods);

            // 2.修改采购商品的已报检数量
            PurchGoods purchGoods = purchGoodsDao.findByPurchIdAndGoodsId(purch.getId(), goodsId);
            purchGoods.setInspectNum(purchGoods.getInspectNum() + diff);
            purchGoodsDao.save(purchGoods);
        }

        // 3.检查采购是否已经报检完成
        List<PurchGoods> purchGoodsList = purchGoodsDao.findByPurchId(purch.getId());
        // 采购中只要有一个商品的报检小于采购商品数量，则采购单还可以继续报检
        boolean inspectNotEnd = purchGoodsList.stream().anyMatch(obj -> obj.getInspectNum() < obj.getPurchaseNum());
        if (!inspectNotEnd) {
            purchDao.save(purch);
        }

        // 保存基本信息
        inspectApply.setDepartment(vo.getDepartment());
        inspectApply.setPurchaseName(vo.getPurchaseName());
        inspectApply.setSupplierName(vo.getSupplierName());
        inspectApply.setAbroadCoName(vo.getAbroadCoName());
        inspectApply.setInspectDate(vo.getInspectDate());
        inspectApply.setDirect(direct);
        if (direct) {
            inspectApply.setOutCheck(outCheck);
        } else {
            inspectApply.setOutCheck(null);
        }
        inspectApply.setRemark(vo.getRemark());
        inspectApply.setAttachmentList(vo.getAttachmentList());
        inspectApply.setStatus(vo.getStatus());

        // 保存报检单
        inspectApplyDao.save(inspectApply);

        // 保存报检单中的商品信息
        inspectApplyGoodsDao.save(inspectApplyGoodsList);


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
     * @param vo
     * @return
     */
    @Override
    @Transactional
    public boolean againApply(InspectApplySaveVo vo) {

        InspectApply lastInspectApply = inspectApplyDao.findOne(vo.getId());
        if (lastInspectApply == null || lastInspectApply.getStatus() != InspectApply.StatusEnum.UNQUALIFIED.getCode()) {
            return false; // 不存在原来的报检单或当期报检单没有未合格产品，返回错误
        }


        InspectApply parentInspectApply = lastInspectApply.getParent(); // 主报检单
        if (parentInspectApply == null) {
            parentInspectApply = lastInspectApply;
        }

        // 判断每个商品的报检数量是否等于最后一次报检不合格数量
        List<InspectApplyGoods> inspectApplyGoodsList = inspectApplyGoodsDao.findByInspectApplyId(lastInspectApply.getId());
        List<PGoods> goodsList = vo.getGoodsList();
        final Map<Integer, PGoods> pGoodsMap = goodsList.parallelStream().collect(Collectors.toMap(PGoods::getId, pg -> pg));
        // 是否有不合格数量和这次不相等的
        List<InspectApplyGoods> newApplyGoodsList = new ArrayList<>();
        boolean allEqual = inspectApplyGoodsList.stream().allMatch(ipg -> {
            // 判断商品是否存在不合格数量
            Integer unqualified = ipg.getUnqualified();
            PGoods pGoods = pGoodsMap.get(ipg.getId());
            if (unqualified > 0 && pGoods == null) {
                return false;
            }

            if (pGoods != null && pGoods.getInspectNum() != unqualified) {
                return false;
            }

            // 新建要插入的报检单商品
            InspectApplyGoods inspectApplyGoods = new InspectApplyGoods();
//            inspectApplyGoods.setInspectApply(inspectApply);
            inspectApplyGoods.setGoods(ipg.getGoods());
            inspectApplyGoods.setInspectNum(unqualified);
            inspectApplyGoods.setCreateTime(new Date());

            newApplyGoodsList.add(inspectApplyGoods);

            return true;
        });
        if (!allEqual) {
            return false; // 存在和上次不合格的商品数量不符的商品，返回失败
        }

        // 检验完毕，做正式操作
        // 主报检单的报检数量+1
        parentInspectApply.setNum(parentInspectApply.getNum() + 1);
        inspectApplyDao.save(parentInspectApply);

        //新插入报检单，并设置上级报检单
        InspectApply newInspectApply = new InspectApply();
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
        newInspectApply.setAbroadCoName(vo.getAbroadCoName());
        // 设置报检时间
        newInspectApply.setInspectDate(vo.getInspectDate());
        newInspectApply.setDirect(lastInspectApply.getDirect());
        newInspectApply.setOutCheck(lastInspectApply.getOutCheck());
        newInspectApply.setMsg(vo.getMsg());
        //插入新报检单附件信息
        newInspectApply.setStatus(InspectApply.StatusEnum.SUBMITED.getCode());
        vo.getAttachmentList().parallelStream().forEach(voAttch -> {
            voAttch.setId(null);
        });
        newInspectApply.setAttachmentList(vo.getAttachmentList());
        inspectApplyDao.save(newInspectApply);

        // 插入新的报检商品信息
        newApplyGoodsList.parallelStream().forEach(nag -> {
            nag.setInspectApply(newInspectApply);
        });

        inspectApplyGoodsDao.save(newApplyGoodsList);


        return true;
    }

    @Override
    public List<InspectApply> findSameApplyList(Integer id) {
        InspectApply masterInspectApply = inspectApplyDao.findOne(id);
        if (masterInspectApply != null && masterInspectApply.getNum() > 1) {
            // 存在多次报检
            List<InspectApply> inspectApplyList = inspectApplyDao.findByParentIdOrderByIdAsc(masterInspectApply.getId());

            inspectApplyList.add(0, masterInspectApply);
            return inspectApplyList;
        }
        // 如果不存在多次报检，则返回空列表
        return new ArrayList<>();
    }

    @Override
    public InspectApply findDetail(Integer id) {
        InspectApply inspectApply = inspectApplyDao.findOne(id);
        if (inspectApply != null) {
            inspectApply.getAttachmentList().size();
            inspectApply.getInspectApplyGoodsList();
        }

        return inspectApply;
    }


}
