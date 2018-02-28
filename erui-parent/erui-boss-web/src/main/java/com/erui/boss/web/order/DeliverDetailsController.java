package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.*;
import com.erui.order.requestVo.DeliverD;
import com.erui.order.service.DeliverDetailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
        Result<Object> result = new Result<>();
        int page = deliverD.getPage();
        if (page < 1) {
            return new Result<>(ResultStatusEnum.PAGE_ERROR);
        }
        Page<DeliverDetail> deliverDetail=null;
        try {
            deliverDetail=deliverDetailService.outboundManage(deliverD);
        }catch (Exception e){
            String message = e.getMessage();
            return new Result<>(ResultStatusEnum.FAIL).setMsg(message);
        }
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
        if(deliverDetails == null || deliverDetails.getId() == null){
            return new Result<>(ResultStatusEnum.FAIL);
        }
        DeliverDetail deliverDetail = deliverDetailService.findDetailById(deliverDetails.getId());
        Map<String, Object> data = new HashMap<>();
        // 出库基本信息
        data.put("deliverDetailInfo", deliverDetail); // 出库基本信息
        DeliverNotice deliverNotice = deliverDetail.getDeliverNotice();

        Map<String, Object> deliverNoticeInfo = new HashMap<>();

        List<DeliverConsign> deliverConsigns = deliverNotice.getDeliverConsigns();
        Set<String> toPlacList = null;
        Set<String> tradeTermsList = null;
        if (deliverConsigns.size() != 0){
            toPlacList = new HashSet<>();
            tradeTermsList = new HashSet<>();
            for (DeliverConsign deliverConsign :deliverConsigns){
                toPlacList.add(deliverConsign.getOrder().getToPort()); // 目的港
                tradeTermsList.add(deliverConsign.getOrder().getTradeTerms());  // 贸易术语
            }
        }
        deliverNoticeInfo.put("toPlace",StringUtils.join(toPlacList, ",")); // 目的港
        deliverNoticeInfo.put("tradeTerms",StringUtils.join(tradeTermsList, ",")); // 贸易术语
        deliverNoticeInfo.put("numers", deliverNotice.getNumers()); // 总包装件数
        deliverNoticeInfo.put("id", deliverNotice.getId()); // 发货通知单ID
        deliverNoticeInfo.put("prepareReq", deliverNotice.getPrepareReq()); // 备货要求
        deliverNoticeInfo.put("packageReq", deliverNotice.getPackageReq()); // 包装要求

        List<DeliverConsignGoods> deliverConsignGoodsList = deliverDetail.getDeliverConsignGoodsList();
        List<Map<String, Object>> goodsInfoList = new ArrayList<>();
        for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsList) {
            Goods goods = deliverConsignGoods.getGoods();

            Map<String, Object> goodsInfoMap = new HashMap<>();
            goodsInfoMap.put("id", deliverConsignGoods.getId());
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
            goodsInfoMap.put("outboundRemark", deliverConsignGoods.getOutboundRemark()); // 出库备注 TODO

            goodsInfoList.add(goodsInfoMap);
        }

        deliverNoticeInfo.put("goodsInfo", goodsInfoList);

        //附件信息
        //仓储物流部
        List<Attachment> attachmentList = new ArrayList(deliverDetail.getAttachmentList());
        List<Attachment> attachmentList02 = new ArrayList<>();
        Iterator<Attachment> iterator = attachmentList.iterator();
        while (iterator.hasNext()) {
            Attachment next = iterator.next();

            if (!"仓储物流部".equals(next.getGroup())) {
                attachmentList02.add(next);
                iterator.remove();
            }
        }

        //品控部
        List<Attachment> attachmentList03 = new ArrayList<>(attachmentList02);
        Iterator<Attachment> iterator3 = attachmentList03.iterator();
        while (iterator3.hasNext()) {
            Attachment next = iterator3.next();

            if (!"品控部".equals(next.getGroup())) {
                iterator3.remove();
            }
        }

        deliverNoticeInfo.put("attachmentList2", attachmentList03);    //品控部
        deliverNoticeInfo.put("attachmentList", attachmentList);    //仓储物流部
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
        if(deliverDetails == null || deliverDetails.getId() == null){
            return new Result<>(ResultStatusEnum.FAIL);
        }
        DeliverDetail deliverDetail = deliverDetailService.findDetailById(deliverDetails.getId());
        Map<String, Object> data = new HashMap<>();
        // 出库基本信息
        data.put("deliverDetailInfo", deliverDetail); // 出库基本信息
        DeliverNotice deliverNotice = deliverDetail.getDeliverNotice();

        Map<String, Object> deliverNoticeInfo = new HashMap<>();

        List<DeliverConsign> deliverConsigns = deliverNotice.getDeliverConsigns();
        Set<String> toPlacList = null;
        Set<String> tradeTermsList = null;
        if (deliverConsigns.size() != 0){
            toPlacList = new HashSet<>();
            tradeTermsList = new HashSet<>();
            for (DeliverConsign deliverConsign :deliverConsigns){
                toPlacList.add(deliverConsign.getOrder().getToPort()); // 目的港
                tradeTermsList.add(deliverConsign.getOrder().getTradeTerms());  // 贸易术语
            }
        }
        deliverNoticeInfo.put("toPlace",StringUtils.join(toPlacList, ",")); // 目的港
        deliverNoticeInfo.put("tradeTerms",StringUtils.join(tradeTermsList, ",")); // 贸易术语
        deliverNoticeInfo.put("numers", deliverDetail.getPackTotal()); // 总包装件数
        deliverNoticeInfo.put("id", deliverNotice.getId()); // 发货通知单ID
        deliverNoticeInfo.put("prepareReq", deliverNotice.getPrepareReq()); // 备货要求
        deliverNoticeInfo.put("packageReq", deliverNotice.getPackageReq()); // 包装要求

        List<DeliverConsignGoods> deliverConsignGoodsList = deliverDetail.getDeliverConsignGoodsList();
        List<Map<String, Object>> goodsInfoList = new ArrayList<>();
        for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsList) {
            Goods goods = deliverConsignGoods.getGoods();

            Map<String, Object> goodsInfoMap = new HashMap<>();
            goodsInfoMap.put("id", deliverConsignGoods.getId());
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
            goodsInfoMap.put("outboundRemark", deliverConsignGoods.getOutboundRemark()); // 出库备注 TODO

            goodsInfoList.add(goodsInfoMap);
        }

        deliverNoticeInfo.put("goodsInfo", goodsInfoList);

        //附件信息
        List<Attachment> attachmentList = new ArrayList(deliverDetail.getAttachmentList());
        List<Attachment> attachmentList02 = new ArrayList<>();
        Iterator<Attachment> iterator = attachmentList.iterator();
        while (iterator.hasNext()) {
            Attachment next = iterator.next();

            //仓储物流部
            if (!"仓储物流部".equals(next.getGroup())) {
                attachmentList02.add(next);
                iterator.remove();
            }
        }

        //品控部
        List<Attachment> attachmentList03 = new ArrayList<>(attachmentList02);
        Iterator<Attachment> iterator3 = attachmentList03.iterator();
        while (iterator3.hasNext()) {
            Attachment next = iterator3.next();

            if (!"品控部".equals(next.getGroup())) {
                iterator3.remove();
            }
        }

        deliverNoticeInfo.put("attachmentList", attachmentList);
        deliverNoticeInfo.put("attachmentList2", attachmentList03);    //仓储物流部
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
        Result<Object> result = new Result<>();
        String message =null;
        try {
            boolean flag = false;
            if(deliverDetail.getStatus() == 5){
                flag = deliverDetailService.outboundSaveOrAdd(deliverDetail);
            }else{
                if (deliverDetail.getBillingDate() == null) {
                    result.setCode(ResultStatusEnum.FAIL.getCode());
                    result.setMsg("开单日期不能为空");
                    return result;
                }else if (deliverDetail.getPackTotal() == null){
                    result.setCode(ResultStatusEnum.FAIL.getCode());
                    result.setMsg("总包装件数不能为空");
                    return result;
                }else{
                    flag = deliverDetailService.outboundSaveOrAdd(deliverDetail);
                }
            }
            if (flag) {
                return new Result<>();
            }
        } catch (Exception ex) {
            message=ex.getMessage();
            logger.error("出库详情页操作失败：{}", deliverDetail, ex);
        }
        return new Result<>(ResultStatusEnum.FAIL).setMsg(message);
    }



}
