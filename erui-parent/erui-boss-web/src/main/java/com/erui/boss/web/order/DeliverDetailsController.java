package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.EruitokenUtil;
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

import javax.servlet.http.HttpServletRequest;
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
     * V2.0
     *
     * 从出口通知单获取信息
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

        Map<String, Object> deliverNoticeInfo = new HashMap<>();

        DeliverConsign deliverConsign = deliverDetail.getDeliverConsign();
        if(deliverConsign == null ){
            return new Result<>("无出库发货通知单关系");
        }
        deliverNoticeInfo.put("toPlace",deliverConsign.getOrder().getToPort()); // 目的港
        deliverNoticeInfo.put("tradeTerms",deliverConsign.getOrder().getTradeTerms()); // 贸易术语
        //TODO 看货通知信息无法推送
        deliverNoticeInfo.put("numers", deliverDetail.getPackTotal()); // 总包装件数
        /* deliverNoticeInfo.put("id", deliverNotice.getId()); // 发货通知单ID
        deliverNoticeInfo.put("prepareReq", deliverNotice.getPrepareReq()); // 备货要求
        deliverNoticeInfo.put("packageReq", deliverNotice.getPackageReq()); // 包装要求*/

        List<DeliverConsignGoods> deliverConsignGoodsList = deliverDetail.getDeliverConsignGoodsList();

        List<Map<String, Object>> goodsInfoList = goodsMessage(deliverConsignGoodsList);    //处理商品信息

        deliverNoticeInfo.put("goodsInfo", goodsInfoList);

        //附件信息
        Map<String, Object> map = attachmentLists(deliverDetail.getAttachmentList());

        deliverNoticeInfo.put("attachmentList2", map.get("attachmentList2"));    //品控部
        deliverNoticeInfo.put("attachmentList", map.get("attachmentList"));    //仓储物流部
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
    public Result<Object> outboundSaveOrAdd(@RequestBody DeliverDetail deliverDetail, HttpServletRequest request){
        Result<Object> result = new Result<>();
        String message =null;
        try {
            String eruiToken = EruitokenUtil.getEruiToken(request);
            ThreadLocalUtil.setObject(eruiToken);

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



    //处理商品信息
    public static List<Map<String, Object>> goodsMessage(List<DeliverConsignGoods> deliverConsignGoodsList){

        List<Map<String, Object>> goodsInfoList = new ArrayList<>();
        for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsList) {
            if(deliverConsignGoods.getSendNum() != 0){
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
                goodsInfoMap.put("packRequire", deliverConsignGoods.getPackRequire()); // 包装要求
                goodsInfoMap.put("clientDesc", goods.getClientDesc());  // 描述
                goodsInfoMap.put("outboundRemark", deliverConsignGoods.getOutboundRemark()); // 出库备注 TODO
                goodsInfoMap.put("sendNum", deliverConsignGoods.getSendNum()); // 出口通知单数量
                goodsInfoMap.put("outboundNum", deliverConsignGoods.getOutboundNum()); // 出厂数量
                goodsInfoMap.put("straightNum", deliverConsignGoods.getStraightNum()); // 厂家直发数量
                goodsInfoMap.put("inspectInstockNum", goods.getInspectInstockNum()); // 质检入库总数量
                goodsInfoMap.put("nullInstockNum", goods.getNullInstockNum()); // 厂家直发总数量

                goodsInfoList.add(goodsInfoMap);
            }
        }
        return  goodsInfoList;
    }


    //处理附件信息
    public static Map<String, Object> attachmentLists(List<Attachment> attachmentLists){
        Map<String, Object> deliverNoticeInfo = new HashMap<>();

        //仓储物流部
        List<Attachment> attachmentList = new ArrayList(attachmentLists);
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

        deliverNoticeInfo.put("attachmentList2",attachmentList03);//品控部
        deliverNoticeInfo.put("attachmentList", attachmentList);    //仓储物流部

        return  deliverNoticeInfo;
    }


}


