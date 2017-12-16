package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.Goods;
import com.erui.order.entity.Purch;
import com.erui.order.entity.PurchGoods;
import com.erui.order.requestVo.PGoods;
import com.erui.order.requestVo.PurchListCondition;
import com.erui.order.requestVo.PurchSaveVo;
import com.erui.order.service.PurchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
     * 获取采购信息
     *
     * @param id 采购ID
     * @return
     */
    @RequestMapping("get")
    public Result<Object> get(@RequestParam(name = "id", required = true) Integer id) {

        PurchSaveVo data = purchService.findByIdForDetailPage(id);
        if (data != null) {
            return new Result<>(data);
        }

        return new Result<>(ResultStatusEnum.FAIL);
    }

    /**
     * 获取采购单列表
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> list(@RequestBody PurchListCondition condition) {
        Page<Purch> purchPage = purchService.findByPage(condition);
        return new Result<>(purchPage);
    }


    /**
     * 新增采购单
     *
     * @param purchSaveVo
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> save(@RequestBody PurchSaveVo purchSaveVo) {

        // TODO参数检查略过

        try {
            boolean flag = false;
            if (purchSaveVo.getId() != null) {
                flag = purchService.update(purchSaveVo);
            } else {
                flag = purchService.insert(purchSaveVo);
            }
            if (flag) {
                return new Result<>();
            }
        } catch (Exception ex) {
            logger.error("采购单操作失败：{}", purchSaveVo, ex);
        }


        return new Result<>(ResultStatusEnum.FAIL);
    }

    /**
     * 为添加报检单而获取采购信息
     *
     * @param id 采购ID
     * @return
     */
    @RequestMapping("getInfoForInspectApply")
    public Result<Object> getInfoForInspectApply(@RequestParam(name = "id", required = true) Integer id) {

        Purch purch = purchService.findById(id);

        List<PurchGoods> goodsList = purchService.findInspectGoodsByPurch(id);

        // 整合数据
        Map<String,Object> data = new HashMap<>();
        data.put("agentId",purch.getAgentId()); // 采购经办人ID
        data.put("supplierId",purch.getSupplierId()); // 供应商ID
        data.put("afterDept","采购部") ; // 下发部门就是采购部 -- 苗德宇说的

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
        data.put("goodsList",list) ;

        return new Result<>(data);
    }

}
