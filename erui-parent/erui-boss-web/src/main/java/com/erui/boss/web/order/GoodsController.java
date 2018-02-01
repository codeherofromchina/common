package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.Goods;
import com.erui.order.entity.Project;
import com.erui.order.requestVo.PGoods;
import com.erui.order.service.GoodsService;
import com.erui.order.service.ProjectService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 商品控制器
 * Created by wangxiaodan on 2017/12/13.
 */
@RestController
@RequestMapping("order/goods/")
public class GoodsController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 根据项目号查询可以添加采购的商品列表
     *
     * @param projectIds 项目ID列表
     * @return
     */
    @RequestMapping("listByProjectForPurch")
    public Result<Object> listByProjectForPurch(@RequestBody List<Integer> projectIds) {

        List<Project> projects = projectService.findByIds(projectIds);
        List<Integer> ids = projects.parallelStream().filter(project -> {
            return !project.getPurchDone();
        }).map(Project::getId).collect(Collectors.toList());

        List<Goods> goodsList = goodsService.findByProjectIds(ids);
        // 数据过滤并转换
        List<Map<String, Object>> data = goodsList.stream().filter(goods -> {
            return goods.getPrePurchsedNum() < goods.getContractGoodsNum();
        }).map(goods -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", goods.getId()); // 商品ID
            map.put("contractNo", goods.getContractNo()); //销售合同号
            map.put("projectNo", goods.getProjectNo()); // 项目号
            map.put("sku", goods.getSku()); // SKU
            map.put("proType", goods.getProType()); //产品分类
            map.put("nameEn", goods.getNameEn()); //外文品名
            map.put("nameZh", goods.getNameZh()); // 中文品名
            map.put("unit", goods.getUnit()); //单位
            map.put("brand", goods.getBrand()); //品牌
            map.put("model", goods.getModel()); //规格型号
            map.put("contractGoodsNum", goods.getContractGoodsNum()); //合同数量
            map.put("prePurchsedNum", goods.getPrePurchsedNum()); //预采购数量（当前锁定数量）
            map.put("purchasedNum", goods.getPurchasedNum()); //已采购数量
            return map;
        }).collect(Collectors.toList());

        return new Result<>(data);
    }


}
