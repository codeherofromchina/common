package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.Goods;
import com.erui.order.entity.Purch;
import com.erui.order.entity.PurchGoods;
import com.erui.order.service.PurchService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    /**
     * 获取采购单列表
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> list(@RequestBody Purch condition) {
        Page<Purch> purchPage = purchService.findByPage(condition);

        if (purchPage.hasContent()) {
            purchPage.getContent().forEach(vo -> {
                vo.setAttachments(null);
                vo.setPurchPaymentList(null);
                vo.setPurchGoodsList(null);

                List<String> projectNoList = new ArrayList<>();
                List<String> contractNoList = new ArrayList<>();

                vo.getProjects().stream().forEach(project -> {
                    projectNoList.add(project.getProjectNo());
                    contractNoList.add(project.getContractNo());
                });
                vo.setProjectNos(StringUtils.join(projectNoList, ","));
                vo.setContractNos(StringUtils.join(contractNoList, ","));
            });
        }

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
            List<Goods> list = purchGoodsList.stream().filter(vo -> {
                // 只要已报检数量小于采购数量的商品显示
                return vo.getInspectNum() < vo.getPurchaseNum();
            }).map(vo -> {
                Goods goods = vo.getGoods();
                goods.setPurchasedNum(vo.getPurchaseNum()); // 采购数量
                goods.setInspectNum(vo.getInspectNum()); // 报检数量

                return goods;
            }).collect(Collectors.toList());
            data.put("goodsList", list);

            return new Result<>(data);
        }

        return new Result<>(ResultStatusEnum.FAIL);
    }

}
