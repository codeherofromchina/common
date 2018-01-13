package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.*;
import com.erui.order.service.DeliverConsignService;
import com.erui.order.service.DeliverNoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 看货通知单控制器
 * Created by wangxiaodan on 2017/12/11.
 */
@RestController
@RequestMapping(value = "/order/logisticsManage")
public class DeliverNoticeController {

    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private DeliverNoticeService deliverNoticeService;

    @Autowired
    private DeliverConsignService deliverConsignService;


    /**
     * 看货通知管理
     * @param condition
     * @return
     */
    @RequestMapping(value = "lookMoneyInformManage")
    public Result<Object> lookMoneyInformManage(@RequestBody DeliverNotice condition) {
        int page = condition.getPage();
        if (page < 1) {
            return new Result<>(ResultStatusEnum.PAGE_ERROR);
        }
        Page<DeliverNotice> pageList = deliverNoticeService.listByPage(condition);
        return new Result<>(pageList);
    }

    /**
     * 根据出口发货通知单 查询信息
     * @param map  看货通知单号  数组
     * @return
     */
    @RequestMapping(value = "querExitInformMessage")
    public Result<Object> querExitInformMessage(@RequestBody Map<String,String> map) {
        String[] split = map.get("deliverNoticeIds").split(",");
        Integer[] intTemp = new Integer[split.length];
        for (int i = 0; i <split.length; i++) {
                intTemp[i] = Integer.parseInt(split[i]);
            }

        List<DeliverConsign> list= deliverConsignService.querExitInformMessage(intTemp);
            Map<String,Object> data = new HashMap<>();
            List<Goods> goodsList = new ArrayList<>();  //商品信息
            List<DeliverConsign> deliverConsignNoList = new ArrayList<>();  //出口发货通知单号
            List<String> tradeTermsList = new ArrayList<>();  //贸易术语
            List<String> toPlaceList = new ArrayList<>();  //目的地
            List<String>  transportTypeList = new ArrayList<>();  //运输方式
            List<String>  agentNameList = new ArrayList<>();  //商务技术经办人名字
            List<String> deliveryDateList = new ArrayList<>();  //执行单约定交付日期
            List dcAttachmentSetList = new ArrayList<>();  //出口通知单附件

            for (DeliverConsign deliverConsign : list){
                DeliverConsign deliverConsignS = new DeliverConsign();
                deliverConsignS.setId(deliverConsign.getId());
                deliverConsignS.setDeliverConsignNo(deliverConsign.getDeliverConsignNo());
                deliverConsignNoList.add(deliverConsignS);
                Set<Attachment> attachmentSet = deliverConsign.getAttachmentSet();
                for (Attachment attachment : attachmentSet){
                    dcAttachmentSetList.add(attachment);//出口通知单附件
                }
                Order order1 = deliverConsign.getOrder();
                tradeTermsList.add(order1.getTradeTerms());
                toPlaceList.add(order1.getToPlace());
                transportTypeList.add(order1.getTransportType());
                agentNameList.add(order1.getAgentName());
                deliveryDateList.add(new SimpleDateFormat("yyyy-MM-dd").format(order1.getProject().getDeliveryDate()));
                order1.setAttachmentSet(null);
                order1.setGoodsList(null);
                order1.setOrderPayments(null);
                Set<DeliverConsignGoods> deliverConsignGoodsSet = deliverConsign.getDeliverConsignGoodsSet();
                for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsSet){
                    Goods goods = deliverConsignGoods.getGoods();
             //       goods.setSendNum(deliverConsignGoods.getSendNum());
                    goodsList.add(goods);
                }
            }
            data.put("goodsList",goodsList);//商品信息
            data.put("deliverConsignNoList",deliverConsignNoList);//出口发货通知单号
            data.put("tradeTermsList",tradeTermsList);//贸易术语
            data.put("toPlaceList",toPlaceList);//目的地
            data.put("transportTypeList",transportTypeList);//运输方式
            data.put("agentNameList",agentNameList);//商务技术经办人名字
            data.put("deliveryDateList",deliveryDateList);//执行单约定交付日期
            data.put("dcAttachmentSetList",dcAttachmentSetList);//出口通知单附件

            return new Result<>(data);
    }



    /**
     * 看货通知详情 保存 or  提交
     *
     * @return
     */
    @RequestMapping(value = "exitRequisitionSaveOrAdd", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> exitRequisitionSaveOrAdd(@RequestBody DeliverNotice deliverNotice) {
        try {
            boolean flag = false;
            if (deliverNotice.getId()!= null) {
                flag = deliverNoticeService.updateexitRequisition(deliverNotice);
            } else {
                flag = deliverNoticeService.addexitRequisition(deliverNotice);
            }
            if (flag) {
                return new Result<>();
            }
        } catch (Exception ex) {
            logger.error("订单操作失败：{}", deliverNotice, ex);
        }
        return new Result<>(ResultStatusEnum.FAIL);
    }


    /**
     *看货通知详情 - 查看
     *
     * @param deliverNotice    看货通知单号id
     * @return
     */
    @RequestMapping(value = "exitRequisitionQuery", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> exitRequisitionQuery(@RequestBody DeliverNotice deliverNotice) {
        if(deliverNotice == null || deliverNotice.getId() == null){
            return new Result<>(ResultStatusEnum.FAIL);
        }
        DeliverNotice page = deliverNoticeService.exitRequisitionQuery(deliverNotice.getId());

        List<Goods> goodsList = new ArrayList<>();  //商品信息
        List<DeliverConsign> deliverConsignNoList = new ArrayList<>();  //出口发货通知单号
        List<String> tradeTermsList = new ArrayList<>();  //贸易术语
        List<String> toPlaceList = new ArrayList<>();  //目的地
        List<String>  transportTypeList = new ArrayList<>();  //运输方式
        List<String>  agentNameList = new ArrayList<>();  //商务技术经办人名字
        List<String> deliveryDateList = new ArrayList<>();  //执行单约定交付日期
        List dcAttachmentSetList = new ArrayList<>();  //出口通知单附件

        Set<DeliverConsign> deliverConsigns = page.getDeliverConsigns();
        for (DeliverConsign deliverConsign : deliverConsigns){
            Set<Attachment> attachmentSet = deliverConsign.getAttachmentSet();
            for (Attachment attachment : attachmentSet){
                dcAttachmentSetList.add(attachment);//出口通知单附件
            }
            DeliverConsign deliverConsignS = new DeliverConsign();
            deliverConsignS.setId(deliverConsign.getId());
            deliverConsignS.setDeliverConsignNo(deliverConsign.getDeliverConsignNo());
            deliverConsignNoList.add(deliverConsignS);
            Set<DeliverConsignGoods> deliverConsignGoodsSet = deliverConsign.getDeliverConsignGoodsSet();
                for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsSet){
                    Goods goods = deliverConsignGoods.getGoods();
                 //   goods.setSendNum(deliverConsignGoods.getSendNum());
                    goodsList.add(goods);
                }
            Order order = deliverConsign.getOrder();
            tradeTermsList.add( order.getTradeTerms());
            toPlaceList.add(order.getToPlace());
            transportTypeList.add(order.getTransportType());
            agentNameList.add(order.getAgentName());
            deliveryDateList.add(new SimpleDateFormat("yyyy-MM-dd").format(order.getProject().getDeliveryDate()));
        }
        Map<String,Object>  map = new HashMap<>();
        map.put("id",page.getId());  //看货通知单id
        map.put("senderId", page.getSenderId());//下单人
        map.put("sendDate", new SimpleDateFormat("yyyy-MM-dd").format(page.getSendDate()));//下单时间
        map.put("urgency",  page.getUrgency());//紧急程度
        map.put("numers",page.getNumers());//件数
        map.put("prepareReq",page.getPrepareReq());//备货要求
        map.put("packageReq",page.getPackageReq());//包装要求
        map.put("attachmentSet",page.getAttachmentSet());//看货通知单附件
        map.put("goodsList",goodsList);//商品信息
        map.put("deliverConsignNoList",deliverConsignNoList);//出口发货通知单号
        map.put("tradeTermsList",tradeTermsList);//贸易术语
        map.put("toPlaceList",toPlaceList);//目的地
        map.put("transportTypeList",transportTypeList);//运输方式
        map.put("agentNameList",agentNameList);//商务技术经办人名字
        map.put("deliveryDateList",deliveryDateList);//执行单约定交付日期
        map.put("dcAttachmentSetList",dcAttachmentSetList);//出口通知单附件
        map.put("senderName",page.getSenderName());//下单人名称

        return new Result<>(map);
    }

    /**
     * 看货通知管理   查询出口发货通知单
     * @return
     */
    @RequestMapping(value = "queryExitAdvice")
    public Result<Object> queryExitAdvice(@RequestBody DeliverNotice deliverNotice) {
        List<DeliverConsign> list =deliverConsignService.queryExitAdvice(deliverNotice.getId());
        if(list != null){
            list.parallelStream().forEach( v -> {
                v.setAttachmentSet(null);
                v.setDeliverConsignGoodsSet(null);
            });
            return new Result<>(list);
        }
        return new Result<>(ResultStatusEnum.DATA_NULL);
    }




}
