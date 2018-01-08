package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.DeliverConsignGoods;
import com.erui.order.entity.DeliverDetail;
import com.erui.order.entity.DeliverNotice;
import com.erui.order.entity.Goods;
import com.erui.order.requestVo.DeliverD;
import com.erui.order.service.DeliverDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/11.
 */

/**
 * 仓库管理 - 出库管理
 *
 */
@RestController
@RequestMapping(value = "/order/storehouseManage")
public class DeliverDetailsController {
    private final static Logger logger = LoggerFactory.getLogger(DeliverDetailsController.class);

    @Autowired
    private DeliverDetailService deliverDetailService;


    /**
     * 出库管理
     *
     * @param deliverD
     * @return
     */
    @RequestMapping(value = "outboundManage", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> outboundManage(@RequestBody DeliverD deliverD){
        Page<DeliverDetail> deliverDetail=deliverDetailService.outboundManage(deliverD);
        return new Result<>(deliverDetail);
    }



    /**
     * 出库详情页 - 查看
     *
     * @return
     */
    //TODO 掉接口

    @RequestMapping(value = "outboundDetailsPage", method = RequestMethod.POST)
    public Result<Object> outboundDetailsPage(@RequestBody DeliverDetail deliverDetails){
        DeliverDetail deliverDetail = deliverDetailService.findDetailById(deliverDetails.getId());
        Map<String, Object> data = new HashMap<>();
        // 出库基本信息
        data.put("deliverDetailInfo", deliverDetail); // 出库基本信息
        DeliverNotice deliverNotice = deliverDetail.getDeliverNotice();

        Map<String, Object> deliverNoticeInfo = new HashMap<>();
        deliverNoticeInfo.put("id", deliverNotice.getId()); // 发货通知单ID
        deliverNoticeInfo.put("tradeTerms", deliverNotice.getTradeTerms()); // 贸易术语
        deliverNoticeInfo.put("toPlace", deliverNotice.getToPlace()); // 目的港
        deliverNoticeInfo.put("numers", deliverNotice.getNumers()); // 总包装件数

        List<DeliverConsignGoods> deliverConsignGoodsList = deliverDetail.getDeliverConsignGoodsList();
        List<Map<String, Object>> goodsInfoList = new ArrayList<>();
        for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsList) {
            Goods goods = deliverConsignGoods.getGoods();

            Map<String, Object> goodsInfoMap = new HashMap<>();
            goodsInfoMap.put("goodsId", goods.getId());
            goodsInfoMap.put("contractNo", goods.getContractNo()); // 销售合同号
            goodsInfoMap.put("projectNo", goods.getProjectNo()); // 项目号
            goodsInfoMap.put("sku", goods.getSku()); // 平台SKU
            goodsInfoMap.put("nameEn", goods.getNameEn()); // 英文品名
            goodsInfoMap.put("nameZh", goods.getNameZh()); // 中文品名
            goodsInfoMap.put("model", goods.getModel()); // 规格型号
            goodsInfoMap.put("unit", goods.getUnit()); // 单位
            goodsInfoMap.put("sendNum", deliverConsignGoods.getSendNum()); // 数量
            goodsInfoMap.put("packRequire", deliverConsignGoods.getPackRequire()); // 包装要求
            goodsInfoMap.put("clientDesc", goods.getClientDesc());  // 描述
            goodsInfoMap.put("remark", ""); // 备注 TODO

            goodsInfoList.add(goodsInfoMap);
        }

        deliverNoticeInfo.put("goodsInfo", goodsInfoList);
        data.put("deliverNoticeInfo", deliverNoticeInfo);// 出库通知单基本信息


        return new Result<>(data);
    }


    /**
     * 出库管理 - 查看（出库）
     *
     * @return
     */
    //TODO 掉接口

    @RequestMapping(value = "outboundDetails", method = RequestMethod.POST)
    public Result<Object> outboundDetails(@RequestBody DeliverDetail deliverDetails){
        DeliverDetail deliverDetail = deliverDetailService.findDetailById(deliverDetails.getId());
        Map<String, Object> data = new HashMap<>();
        // 出库基本信息
        data.put("deliverDetailInfo", deliverDetail); // 出库基本信息
        DeliverNotice deliverNotice = deliverDetail.getDeliverNotice();

        Map<String, Object> deliverNoticeInfo = new HashMap<>();
        deliverNoticeInfo.put("id", deliverNotice.getId()); // 发货通知单ID
        deliverNoticeInfo.put("tradeTerms", deliverNotice.getTradeTerms()); // 贸易术语
        deliverNoticeInfo.put("toPlace", deliverNotice.getToPlace()); // 目的港
        deliverNoticeInfo.put("numers", deliverNotice.getNumers()); // 总包装件数

        List<DeliverConsignGoods> deliverConsignGoodsList = deliverDetail.getDeliverConsignGoodsList();
        List<Map<String, Object>> goodsInfoList = new ArrayList<>();
        for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsList) {
            Goods goods = deliverConsignGoods.getGoods();

            Map<String, Object> goodsInfoMap = new HashMap<>();
            goodsInfoMap.put("goodsId", goods.getId());
            goodsInfoMap.put("contractNo", goods.getContractNo()); // 销售合同号
            goodsInfoMap.put("projectNo", goods.getProjectNo()); // 项目号
            goodsInfoMap.put("sku", goods.getSku()); // 平台SKU
            goodsInfoMap.put("nameEn", goods.getNameEn()); // 英文品名
            goodsInfoMap.put("nameZh", goods.getNameZh()); // 中文品名
            goodsInfoMap.put("model", goods.getModel()); // 规格型号
            goodsInfoMap.put("unit", goods.getUnit()); // 单位
            goodsInfoMap.put("sendNum", deliverConsignGoods.getSendNum()); // 数量
            goodsInfoMap.put("packRequire", deliverConsignGoods.getPackRequire()); // 包装要求
            goodsInfoMap.put("clientDesc", goods.getClientDesc());  // 描述
            goodsInfoMap.put("remark", ""); // 备注 TODO

            goodsInfoList.add(goodsInfoMap);
        }

        deliverNoticeInfo.put("goodsInfo", goodsInfoList);
        data.put("deliverNoticeInfo", deliverNoticeInfo);// 出库通知单基本信息


        return new Result<>(data);
    }



    /**
     * 出库详情页 保存 or 提交质检
     *
     * @param deliverDetail
     * @return
     */
    @RequestMapping(value = "outboundSaveOrAdd", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> outboundSaveOrAdd(@RequestBody DeliverDetail deliverDetail){
        try {
            boolean flag = false;
                flag = deliverDetailService.outboundSaveOrAdd(deliverDetail);
            if (flag) {
                return new Result<>();
            }
        } catch (Exception ex) {
            logger.error("订单操作失败：{}", deliverDetail, ex);
        }
        return new Result<>(ResultStatusEnum.FAIL);
    }






}
