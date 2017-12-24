package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.order.entity.DeliverConsignGoods;
import com.erui.order.entity.DeliverDetail;
import com.erui.order.entity.DeliverNotice;
import com.erui.order.entity.Goods;
import com.erui.order.service.DeliverDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@RestController
@RequestMapping(value = "/order/deliverDetail")
public class DeliverDetailController {
    private final static Logger logger = LoggerFactory.getLogger(DeliverDetailController.class);

    @Autowired
    private DeliverDetailService deliverDetailService;



    /**
     * 查询物流-出库单详情信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Result<Object> detail(@RequestParam(name = "id", required = true) Integer id) {
        DeliverDetail deliverDetail = deliverDetailService.findDetailById(id);


        Map<String,Object> data = new HashMap<>();
        // 出库基本信息
        data.put("deliverDetailInfo",deliverDetail); // 出库基本信息
        DeliverNotice deliverNotice = deliverDetail.getDeliverNotice();

        Map<String,Object> deliverNoticeInfo = new HashMap<>();
        deliverNoticeInfo.put("id",deliverNotice.getId()); // 发货通知单ID
        deliverNoticeInfo.put("tradeTerms",deliverNotice.getTradeTerms()); // 贸易术语
        deliverNoticeInfo.put("toPlace",deliverNotice.getToPlace()); // 目的港
        deliverNoticeInfo.put("numers",deliverNotice.getNumers()); // 总包装件数

        List<DeliverConsignGoods> deliverConsignGoodsList = deliverDetail.getDeliverConsignGoodsList();
        List<Map<String,Object>> goodsInfoList = new ArrayList<>();
        for (DeliverConsignGoods deliverConsignGoods :deliverConsignGoodsList) {
            Goods goods = deliverConsignGoods.getGoods();

            Map<String,Object> goodsInfoMap = new HashMap<>();
            goodsInfoMap.put("goodsId",goods.getId());
            goodsInfoMap.put("contractNo",goods.getContractNo()); // 销售合同号
            goodsInfoMap.put("projectNo",goods.getProjectNo()); // 项目号
            goodsInfoMap.put("sku",goods.getSku()); // 平台SKU
            goodsInfoMap.put("nameEn",goods.getNameEn()); // 英文品名
            goodsInfoMap.put("nameZh",goods.getNameZh()); // 中文品名
            goodsInfoMap.put("model",goods.getModel()); // 规格型号
            goodsInfoMap.put("unit",goods.getUnit()); // 单位
            goodsInfoMap.put("sendNum",deliverConsignGoods.getSendNum()); // 数量
            goodsInfoMap.put("packRequire",deliverConsignGoods.getPackRequire()); // 包装要求
            goodsInfoMap.put("clientDesc",goods.getClientDesc());  // 描述
            goodsInfoMap.put("remark",""); // 备注 TODO

            goodsInfoList.add(goodsInfoMap);
        }

        deliverNoticeInfo.put("goodsInfo",goodsInfoList);
        data.put("deliverNoticeInfo",deliverNoticeInfo);// 出库通知单基本信息


        return new Result<>(data);
    }


}
