package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.Goods;
import com.erui.order.entity.Purch;
import com.erui.order.entity.PurchGoods;
import com.erui.order.requestVo.PGoods;
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

                List<String> projectNoList = new ArrayList<String>();
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

        // TODO 参数检查略过
        Purch.StatusEnum statusEnum = Purch.StatusEnum.fromCode(purch.getStatus());
        if (statusEnum != null && statusEnum == Purch.StatusEnum.BEING || statusEnum == Purch.StatusEnum.READY) {
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
     * @param id 采购ID
     * @return
     */
    @Deprecated
    @RequestMapping("getInfoForInspectApply")
    public Result<Object> getInfoForInspectApply(@RequestParam(name = "id", required = true) Integer id) {

        Purch purch = purchService.findBaseInfo(id);

        List<PurchGoods> goodsList = purchService.findInspectGoodsByPurch(id);

        // 整合数据
        Map<String, Object> data = new HashMap<>();
        data.put("agentId", purch.getAgentId()); // 采购经办人ID
        data.put("supplierId", purch.getSupplierId()); // 供应商ID
        data.put("afterDept", "采购部"); // 下发部门就是采购部 -- 苗德宇说的

        List<PGoods> list = goodsList.stream().map(vo -> {
            PGoods pg = new PGoods();
            pg.setId(vo.getId());
            Goods goods = vo.getGoods();
            pg.setGoodsId(goods.getId());
            pg.setContractNo(goods.getContractNo());
            pg.setProjectNo(goods.getProjectNo());
            pg.setSku(goods.getSku());
            pg.setProType(goods.getProType());
            pg.setNameEn(goods.getNameEn());
            pg.setNameZh(goods.getNameZh());
            pg.setBrand(goods.getBrand());
            pg.setModel(goods.getModel());
            pg.setPurchaseNum(vo.getPurchaseNum()); // 采购数量，这里在页面显示成数量
            pg.setInspectNum(vo.getInspectNum());
            pg.setUnit(goods.getUnit());
            // TODO 这里先不返回不含税单价 含税单价、总价款、重量（kg）、长*宽*高(cm)、备注 等信息，还没有找到来源

            return pg;
        }).collect(Collectors.toList());
        data.put("goodsList", list);

        return new Result<>(data);
    }

}
