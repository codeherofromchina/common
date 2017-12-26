package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.Goods;
import com.erui.order.entity.InspectApply;
import com.erui.order.entity.InspectApplyGoods;
import com.erui.order.entity.PurchGoods;
import com.erui.order.service.InspectApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 报检单控制器
 * Created by wangxiaodan on 2017/12/13.
 */
@RestController
@RequestMapping("order/inspectApply")
public class InspectApplyController {
    private final static Logger logger = LoggerFactory.getLogger(InspectApplyController.class);

    @Autowired
    private InspectApplyService inspectApplyService;


    /**
     * 获取采购纬度的质检申请信息列表
     *
     * @param parchId 采购ID
     * @return
     */
    @RequestMapping("listByParch")
    public Result<Object> listByParch(@RequestParam(name = "parchId", required = true) Integer parchId) {
        List<InspectApply> inspectApplyList = inspectApplyService.findMasterListByParchId(parchId);
        if (inspectApplyList != null) {
            // 转换数据
            List<Map<String, Object>> data = inspectApplyList.stream().map(vo -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", vo.getId()); // 报检单ID
                map.put("inspectApplyNo", vo.getInspectApplyNo()); // 采购合同号
                map.put("purchNo", vo.getPurchNo()); // 到货报检单号
                map.put("department", vo.getDepartment()); // 下发部门
                map.put("purchaseName", vo.getPurchaseName()); // 采购经办人
                map.put("supplierName", vo.getSupplierName()); // 供应商名称
                map.put("inspectDate", vo.getInspectDate()); // 报检日期
                map.put("num", vo.getNum()); //报检次数
                map.put("status", vo.getStatus()); // 质检结果
                map.put("history", vo.isHistory()); //
                return map;
            }).collect(Collectors.toList());

            return new Result<>(data);
        }

        return new Result<>(ResultStatusEnum.FAIL);

    }


    /**
     * 获取报检单信息详情
     *
     * @param id 报检单ID
     * @return
     */
    @RequestMapping("detail")
    public Result<Object> detail(@RequestParam(name = "id", required = true) Integer id) {
        InspectApply inspectApply = inspectApplyService.findDetail(id);
        if (inspectApply != null) {
            // 组织数据格式
            inspectApply.setpId(inspectApply.getPurch().getId());
            List<InspectApplyGoods> inspectApplyGoodsList = inspectApply.getInspectApplyGoodsList();
            for (InspectApplyGoods inspectApplyGoods : inspectApplyGoodsList) {
                Goods goods = inspectApplyGoods.getGoods();
                PurchGoods purchGoods = inspectApplyGoods.getPurchGoods();
                inspectApplyGoods.setHasInspectNum(purchGoods.getInspectNum());
                inspectApplyGoods.setPurchGoods(null);
                inspectApplyGoods.setgId(goods.getId());
            }


            return new Result<>(inspectApply);
        }

        return new Result<>(ResultStatusEnum.FAIL);
    }

    /**
     * 到货报检单历史记录
     *
     * @param id 报检单ID
     * @return
     */
    @RequestMapping("history")
    public Result<Object> history(@RequestParam(name = "id", required = true) Integer id) {
        // 查询多次相同报检提交的报检单
        InspectApply masterInspectApply = inspectApplyService.findById(id);

        if (masterInspectApply != null && masterInspectApply.isMaster()) {
            List<InspectApply> list = inspectApplyService.findByParentId(masterInspectApply.getId());
            list.add(0, masterInspectApply);

            list.parallelStream().forEach(vo -> {
                vo.setAttachmentList(null);
                vo.setInspectApplyGoodsList(null);
                vo.setpId(vo.getPurch().getId());
            });

            return new Result<>(list);
        }
        return new Result<>(ResultStatusEnum.FAIL);
    }


    /**
     * 新增/更新到货报检单
     *
     * @param inspectApply
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> save(@RequestBody InspectApply inspectApply) {

        InspectApply.StatusEnum statusEnum = InspectApply.StatusEnum.fromCode(inspectApply.getStatus());
        boolean checkoutContinue = true;
        // 必须是保存、提交、重新报检的一种，这里将NO_EDIT设置为重新报检类型复用
        if (statusEnum == null || (statusEnum != InspectApply.StatusEnum.SAVED && statusEnum != InspectApply.StatusEnum.SUBMITED && statusEnum != InspectApply.StatusEnum.NO_EDIT)) {
            checkoutContinue = false;
        }
        if (inspectApply.getInspectApplyGoodsList() == null || inspectApply.getInspectApplyGoodsList().size() <= 0) {
            checkoutContinue = false;
        }

        if (checkoutContinue) {

            boolean flag = false;
            try {

                if (statusEnum != InspectApply.StatusEnum.NO_EDIT) {
                    if (inspectApply.getId() != null) {
                        flag = inspectApplyService.save(inspectApply);
                    } else {
                        // 插入新报检单操作
                        flag = inspectApplyService.insert(inspectApply);
                    }
                } else {
                    // 此处将NO_EDIT临时使用为重新报检状态
                    // 重新报检
                    flag = inspectApplyService.againApply(inspectApply);
                }
                if (flag) {
                    return new Result<>();
                }
            } catch (Exception ex) {
                logger.error("异常报错", ex);
            }
        }

        return new Result<>(ResultStatusEnum.FAIL);
    }

}
