package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.DeliverConsignGoods;
import com.erui.order.entity.DeliverDetail;
import com.erui.order.entity.DeliverNotice;
import com.erui.order.entity.Goods;
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
@RestController
@RequestMapping(value = "/order/deliverDetail")
public class DeliverDetailController {
    private final static Logger logger = LoggerFactory.getLogger(DeliverDetailController.class);

    @Autowired
    private DeliverDetailService deliverDetailService;


    /**
     * 出库质检列表
     *
     * @param condition {
     *                  "deliverDetailNo": "产品放行单号",
     *                  "contractNo": "销售合同号",
     *                  "projectNo": "项目号",
     *                  "checkerName": "检验员",
     *                  "checkDate": "检验日期",
     *                  "page": "0",
     *                  "pageSize": "20"
     *                  }
     * @return
     */
    @RequestMapping(value = "listQuality", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> listQuality(@RequestBody Map<String, String> condition) {
        int pageNum = InstockController.getStrNumber(condition.get("page"), InstockController.DEFAULT_PAGE);
        int pageSize = InstockController.getStrNumber(condition.get("rows"), InstockController.DEFAULT_PAGESIZE);

        Page<Map<String, Object>> data = deliverDetailService.listQualityByPage(condition, pageNum, pageSize);


        return new Result<>(data);

    }

    /**
     * 出库质检保存
     *
     * @param deliverDetail
     * @return
     */
    @RequestMapping(value = "saveQuality", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> saveQuality(@RequestBody DeliverDetail deliverDetail) {
        boolean continueFlag = true;
        String errMsg = null;
        if (deliverDetail.getId() == null || deliverDetail.getId() <= 0) {
            continueFlag = false;
            errMsg = "出库质检不存在";
        }
        DeliverDetail.StatusEnum statusEnum = DeliverDetail.StatusEnum.fromStatusCode(deliverDetail.getStatus());
        if (statusEnum != DeliverDetail.StatusEnum.SAVED_OUT_INSPECT && statusEnum != DeliverDetail.StatusEnum.SUBMITED_OUT_INSPECT) {
            continueFlag = false;
            errMsg = "出库质检参数状态不正确";
        }

        if (continueFlag) {
            try {
                if (deliverDetailService.saveQuality(deliverDetail)) {
                    return new Result<>();
                }
            } catch (Exception ex) {
                errMsg = ex.getMessage();
                logger.error("异常错误", ex);
            }
        }

        return new Result<>(ResultStatusEnum.FAIL).setMsg(errMsg);

    }


    /**
     * 查询物流-出库单详情信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Result<Object> detail(@RequestParam(name = "id", required = true) Integer id) {
        DeliverDetail deliverDetail = deliverDetailService.findDetailById(id);
        DeliverDetail.StatusEnum statusEnum = DeliverDetail.StatusEnum.fromStatusCode(deliverDetail.getStatus());
        if (statusEnum == null || statusEnum.getStatusCode() < DeliverDetail.StatusEnum.SUBMITED_OUTSTOCK.getStatusCode()) {
            return new Result<>(ResultStatusEnum.FAIL);
        }

        Map<String, Object> data = new HashMap<>();
        // 出库基本信息
        data.put("deliverDetailInfo", deliverDetail); // 出库基本信息
        DeliverNotice deliverNotice = deliverDetail.getDeliverNotice();

        Map<String, Object> deliverNoticeInfo = new HashMap<>();
        deliverNoticeInfo.put("id", deliverNotice.getId()); // 发货通知单ID
        deliverNoticeInfo.put("tradeTerms", deliverNotice.getTradeTerms()); // 贸易术语
        deliverNoticeInfo.put("toPlace", deliverNotice.getToPlace()); // 目的港
        deliverNoticeInfo.put("numers", deliverNotice.getNumers()); // 总包装件数
        deliverNoticeInfo.put("packageReq", deliverNotice.getPackageReq()); // 包装要求
        deliverNoticeInfo.put("prepareReq", deliverNotice.getPrepareReq()); // 备货要求

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


}
