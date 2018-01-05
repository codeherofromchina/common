package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.*;
import com.erui.order.service.ProjectService;
import com.erui.order.service.PurchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/12.
 */
@RestController
@RequestMapping("order/purch")
public class PurchController {
    private final static Logger logger = LoggerFactory.getLogger(PurchController.class);

    @Autowired
    private PurchService purchService;
    @Autowired
    private ProjectService projectService;

    /**
     * 获取采购单列表
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> list(@RequestBody Purch condition) {
        Page<Purch> purchPage = purchService.findByPage(condition);
        return new Result<>(purchPage);
    }


    /**
     * 新增采购单
     *
     * @param purch
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> save(@RequestBody Purch purch) {

        boolean continueFlag = true;
        // 状态检查
        Purch.StatusEnum statusEnum = Purch.StatusEnum.fromCode(purch.getStatus());
        // 不是提交也不是保存
        if (statusEnum != Purch.StatusEnum.BEING && statusEnum != Purch.StatusEnum.READY) {
            continueFlag = false;
        }

        if (continueFlag) {
            try {
                boolean flag = false;
                if (purch.getId() != null) {
                    flag = purchService.update(purch);
                } else {
                    flag = purchService.insert(purch);
                }
                if (flag) {
                    return new Result<>();
                }
            } catch (Exception ex) {
                logger.error("采购单操作失败：{}", purch, ex);
            }
        }

        return new Result<>(ResultStatusEnum.FAIL);
    }


    /**
     * 获取采购详情信息
     *
     * @param id 采购ID
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public Result<Object> detail(@RequestParam(name = "id", required = true) Integer id) {
        Purch data = purchService.findDetailInfo(id);
        if (data != null) {
            return new Result<>(data);
        }

        return new Result<>(ResultStatusEnum.FAIL);
    }


    /**
     * 为添加报检单而获取采购信息
     *
     * @param purchId 采购ID
     * @return
     */
    @RequestMapping("getInfoForInspectApply")
    public Result<Object> getInfoForInspectApply(@RequestParam(name = "purchId", required = true) Integer purchId) {
        Purch purch = purchService.findPurchAndGoodsInfo(purchId);

        // 只有进行中的采购才可以新增报检单信息
        if (purch != null && purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
            // 整合数据
            Map<String, Object> data = new HashMap<>();
            data.put("purchId", purch.getId());
            data.put("purchaseId", purch.getAgentId()); // 采购经办人ID
            data.put("purchaseName", purch.getAgentName()); // 采购经办人名称
            data.put("supplierId", purch.getSupplierId()); // 供应商ID
            data.put("supplierName", purch.getSupplierName()); // 供应商名称
            data.put("department", purch.getDepartment()); // 下发部门

            List<PurchGoods> purchGoodsList = purch.getPurchGoodsList();
            List<Map<String,Object>> list = purchGoodsList.stream().filter(vo -> {
                // 只要已报检数量小于采购数量的商品显示
                return vo.getInspectNum() < vo.getPurchaseNum();
            }).map(vo -> {
                Map<String,Object> map = new HashMap<>();
                Goods goods = vo.getGoods();

                map.put("purchGid",vo.getId());
                map.put("contractNo",goods.getContractNo());
                map.put("projectNo",goods.getProjectNo());
                map.put("sku",goods.getSku());
                map.put("proType",goods.getProType());
                map.put("nameEn",goods.getNameEn());
                map.put("nameZh",goods.getNameZh());
                map.put("brand",goods.getBrand());
                map.put("model",goods.getModel());
                map.put("purchaseNum",vo.getPurchaseNum());
                map.put("hasInspectNum",vo.getInspectNum());
                map.put("inspectNum",vo.getPurchaseNum() - vo.getInspectNum()); // 报检数量
                map.put("unit",goods.getUnit());
                map.put("nonTaxPrice",vo.getNonTaxPrice());
                map.put("taxPrice",vo.getTaxPrice());
                map.put("totalPrice",vo.getTotalPrice());
                map.put("purchaseRemark",vo.getPurchaseRemark());

                return map;
            }).collect(Collectors.toList());
            data.put("goodsList", list);

            return new Result<>(data);
        }

        return new Result<>(ResultStatusEnum.FAIL);
    }

}
