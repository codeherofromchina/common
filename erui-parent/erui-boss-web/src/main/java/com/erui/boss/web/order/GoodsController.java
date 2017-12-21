package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.order.entity.Goods;
import com.erui.order.requestVo.PGoods;
import com.erui.order.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品控制器
 * Created by wangxiaodan on 2017/12/13.
 */
@RestController
@RequestMapping("order/goods/")
public class GoodsController {


    @Autowired
    private GoodsService goodsService;

    /**
     * 根据项目号查询可以添加采购的商品列表
     * @param projectIds    项目ID列表
     * @return
     */
    @RequestMapping("listByProjectForPurch")
    public Result<Object> listByProjectForPurch(@RequestBody List<Integer> projectIds) {

        List<Goods> goodsList = goodsService.findByProjectIds(projectIds);

        List<Goods> data = goodsList.stream().filter(goods -> {
            return goods.getPurchasedNum() < goods.getContractGoodsNum();
        }).collect(Collectors.toList());

        return new Result<>(data);
    }




}
