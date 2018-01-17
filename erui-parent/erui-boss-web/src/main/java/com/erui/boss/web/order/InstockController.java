package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.*;
import com.erui.order.service.InstockService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/24.
 */
@RestController
@RequestMapping("order/instock")
public class InstockController {
    private static Logger logger = LoggerFactory.getLogger(InstockController.class);

    @Autowired
    private InstockService instockService;

    /**
     * 分页查询入库列表信息
     *
     * @param condition {"page":"0","pageSize":"20","inspectApplyNo":"报检单号","contractNo":"销售合同号","projectNo":"项目号",
     *                  "supplierName":"供应商名称","instockDate":"入库日期","name":"仓库经办人"}
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> list(@RequestBody Map<String, String> condition) {

        int page = getStrNumber(condition.get("page"), DEFAULT_PAGE);
        int pageSize = getStrNumber(condition.get("pageSize"), DEFAULT_PAGESIZE);
        if (page < 1) {
            return new Result<>(ResultStatusEnum.PAGE_ERROR);
        }
        Page<Map<String, Object>> data = instockService.listByPage(condition, page - 1, pageSize);

        return new Result<>(data);
    }

    /**
     * 保存入库信息
     *
     * @param instock
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> save(@RequestBody Instock instock) {
        Result<Object> result = new Result<>();
        boolean continueFlag = true;
        Integer status = instock.getStatus();
        if (!(status ==2  || status ==3 )) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("入库信息状态错误");
        }else if (instock.getId() == null || instock.getId() <= 0) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("入库信息id不能为空");
        }else if (StringUtils.isBlank(instock.getUname()) || StringUtils.equals(instock.getUname(), "")) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("仓库经办人名字不能为空");
        }else if (instock.getInstockDate() == null) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("入库日期不能为空");
        }else{
            try {
                if (instockService.save(instock)) {
                    return new Result<>();
                }
            } catch (Exception ex) {
                logger.error("异常错误", ex);
                result.setCode(ResultStatusEnum.FAIL.getCode());
                result.setMsg(ex.toString());
            }
        }


        return result;
    }


    /**
     * 入库详情信息
     *
     * @param instocks 入库信息ID
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public Result<Object> detail(@RequestBody Instock instocks) {
        if(instocks == null || instocks.getId() == null){
            return new Result<>(ResultStatusEnum.PARAM_TYPE_ERROR);
        }
        Instock instock = instockService.detail(instocks.getId());
        if (instock == null) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        // 转换成前端需要的数据
        Map<String, Object> data = new HashMap<>();
        data.put("id", instock.getId());
        data.put("uid", instock.getUid());
        data.put("uname", instock.getUname());
        data.put("instockDate", instock.getInstockDate() != null ? new SimpleDateFormat("yyyy-MM-dd").format(instock.getInstockDate()) : null);
        data.put("remarks", instock.getRemarks());
        data.put("attachmentList", instock.getAttachmentList());

        // 商品信息数据转换
        List<Map<String, Object>> goodsInfoList = new ArrayList<>();
        List<InstockGoods> instockGoodsList = instock.getInstockGoodsList();
        for (InstockGoods instockGoods : instockGoodsList) {
            InspectApplyGoods inspectApplyGoods = instockGoods.getInspectApplyGoods();
            PurchGoods purchGoods = inspectApplyGoods.getPurchGoods();
            Goods goods = inspectApplyGoods.getGoods();
            Map<String, Object> map = new HashMap();
            map.put("id", instockGoods.getId());
            map.put("contractNo", goods.getContractNo()); // 销售合同号
            map.put("projectNo", goods.getProjectNo()); // 项目号
            map.put("sku", goods.getSku()); // 平台SKU
            map.put("proType", goods.getProType()); // 产品分类
            map.put("nameEn", goods.getNameEn()); // 外文品名
            map.put("nameZh", goods.getNameZh()); // 中文品名
            map.put("inspectNum", inspectApplyGoods.getInspectNum()); // 报检数量
            map.put("unqualified", inspectApplyGoods.getUnqualified()); // 不合格数量
            map.put("instockNum", instockGoods.getInstockNum()); // 入库数量
            map.put("unit", goods.getUnit()); // 单位
            map.put("nonTaxPrice", purchGoods.getNonTaxPrice()); // 不含税单价
            map.put("taxPrice", purchGoods.getTaxPrice()); // 含税单价
            map.put("totalPrice", purchGoods.getTotalPrice()); // 总价款
            map.put("model", goods.getModel()); // 规格型号
            map.put("brand", goods.getBrand()); // 品牌
            map.put("height", inspectApplyGoods.getHeight()); // 重量（kg）
            map.put("lwh", inspectApplyGoods.getLwh()); // 长*宽*高
            map.put("instockStock", instockGoods.getInstockStock()); // 货物存放地
            map.put("remark", inspectApplyGoods.getRemarks()); // 备注

            goodsInfoList.add(map);
        }
        data.put("instockGoodsList", goodsInfoList);

        return new Result<>(data);
    }


    protected static int getStrNumber(String numStr, int defaultNum) {
        if (StringUtils.isNumeric(numStr)) {
            return Integer.parseInt(numStr);
        }
        return defaultNum;
    }

    protected final static int DEFAULT_PAGE = 0;
    protected final static int DEFAULT_PAGESIZE = 20;

}
