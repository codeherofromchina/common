package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.order.entity.*;
import com.erui.order.requestVo.DeliverW;
import com.erui.order.service.DeliverDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangxiaodan on 2017/12/11.
 */

/**
 * 物流跟踪管理
 *
 */
@RestController
@RequestMapping(value = "/order/logisticsManage")
public class DeliverDetailwController {
    private final static Logger logger = LoggerFactory.getLogger(DeliverDetailwController.class);

    @Autowired
    private DeliverDetailService deliverDetailService;


    /**
     * 物流跟踪管理
     *
     * @param deliverW
     * @return
     */
    @RequestMapping(value = "logisticsTraceManage", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> logisticsTraceManage(@RequestBody DeliverW deliverW){
        int page = deliverW.getPage();
        if (page < 1) {
            return new Result<>(ResultStatusEnum.PAGE_ERROR);
        }
        Page<DeliverDetail> deliverDetail=deliverDetailService.logisticsTraceManage(deliverW);
        return new Result<>(deliverDetail);
    }


    /**
     * 物流动态跟踪 - 物流信息
     *
     * @param deliverW    物流数据id
     * @return
     */
    @RequestMapping(value = "logisticsMoveFollow", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> logisticsMoveFollow(@RequestBody DeliverW deliverW){
        if(deliverW == null || deliverW.getId() == null){
            return new Result<>(ResultStatusEnum.FAIL);
        }
        DeliverDetail deliverDetail =deliverDetailService.logisticsMoveFollow(deliverW.getId());
        if (deliverDetail == null) {
            return new Result<>(ResultStatusEnum.FAIL);
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Map<String,Object> map = new HashMap<>();
            List<String> tradeTermsList = new ArrayList<>();    //贸易术语
            List<String> fromPlaceList = new ArrayList<>();    //货物起运地
            List<String> fromPortList = new ArrayList<>();    //起运港
            List<String> toCountryList = new ArrayList<>();    //目的国
            List<String> toPortList = new ArrayList<>();    //目的港
            List<String> toPlaceList = new ArrayList<>();    //目的地
            List<Goods> goodsList = new ArrayList<>();    //商品信息
            List<DeliverConsign> deliverConsigns = deliverDetail.getDeliverNotice().getDeliverConsigns();
            for (DeliverConsign deliverConsign : deliverConsigns){
                Order order = deliverConsign.getOrder();
                tradeTermsList.add(order.getTradeTerms());
                fromPlaceList.add(order.getFromPlace());
                fromPortList.add(order.getFromPort());
                toCountryList.add(order.getToCountry());
                toPortList.add(order.getToPort());
                toPlaceList.add(order.getToPlace());

                List<DeliverConsignGoods> deliverConsignGoodsSet = deliverConsign.getDeliverConsignGoodsSet();
                for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsSet){
                    Goods goods = deliverConsignGoods.getGoods();
                    if(deliverConsignGoods.getSendNum() != 0){        //物流跟踪 商品信息 数量不能为0
                        goods.setRemarks(deliverConsignGoods.getOutboundRemark());     //备注
                        goods.setPackRequire(deliverDetail.getDeliverNotice().getPackageReq());  //包装要求
                        goods.setOutstockNum(deliverConsignGoods.getSendNum()); // 数量
                        goodsList.add(goods);
                    }
                }
            }
            List<Attachment> attachmentList =new ArrayList(deliverDetail.getAttachmentList());  //物流跟踪附件信息
            Iterator<Attachment> iterator = attachmentList.iterator();
            while (iterator.hasNext()) {
                Attachment next = iterator.next();
                if (!"国际物流部".equals(next.getGroup())) {
                    iterator.remove();
                }
            }
            map.put("id",deliverDetail.getId());    //id
            map.put("logisticsUserId",deliverDetail.getLogisticsUserId());  //物流经办人id
            map.put("logisticsUserName",deliverDetail.getLogisticsUserName());  //物流经办人名字
            map.put("logisticsDate",deliverDetail.getLogisticsDate());      //经办日期
            map.put("tradeTermsList",tradeTermsList);    //贸易术语
            map.put("fromPlaceList",fromPlaceList);   //货物起运地
            map.put("fromPortList",fromPortList);  //起运港
            map.put("toCountryList",toCountryList);    //目的国
            map.put("toPortList",toPortList);    //目的港
            map.put("toPlaceList",toPlaceList);    //目的地
            map.put("goodsList",goodsList);    //商品信息
            map.put("bookingTime",deliverDetail.getBookingTime());   //下发订舱时间
            map.put("logiInvoiceNo",deliverDetail.getLogiInvoiceNo());   //物流发票号
            map.put("packingTime",deliverDetail.getPackingTime());   //通知市场箱单时间
            map.put("leaveFactory",deliverDetail.getLeaveFactory());   //离厂时间
            map.put("sailingDate",deliverDetail.getSailingDate());   //船期或航班
            map.put("customsClearance",deliverDetail.getCustomsClearance());   //报关放行时间
            map.put("leavePortTime",deliverDetail.getLeavePortTime());   //实际离港时间
            map.put("arrivalPortTime",deliverDetail.getArrivalPortTime());   //预计抵达时间
            map.put("logs",deliverDetail.getLogs());   //动态描述
            map.put("remarks",deliverDetail.getRemarks());   //备注
            map.put("status",deliverDetail.getStatus());    //状态
            map.put("attachmentList",attachmentList);   //物流跟踪附件信息

            return new Result<>(map);
        }
    }





    /**
     * 物流动态跟踪 ：动态更新-项目完结
     *
     * @param deliverDetail
     * @return
     */
    @RequestMapping(value = "logisticsActionAddOrSave", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> logisticsActionAddOrSave(HttpServletRequest request, @RequestBody DeliverDetail deliverDetail){
        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);
       deliverDetailService.logisticsActionAddOrSave(deliverDetail);
        return new Result<>();
    }
/*
    @RequestMapping(value = "a", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> a(@RequestBody DeliverDetail deliverDetail){
        Boolean statusAndNumber = deliverDetailService.findStatusAndNumber(deliverDetail.getId());
        return new Result<>(statusAndNumber);
    }*/


    /**
     * 物流管理 - 查看页面
     *
     * @param deliverW    物流数据id
     * @return
     */
    @RequestMapping(value = "queryLogisticsTrace", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> queryLogisticsTrace(@RequestBody DeliverW deliverW){
        if(deliverW == null || deliverW.getId() == null){
            return new Result<>(ResultStatusEnum.FAIL);
        }
        DeliverDetail deliverDetail =deliverDetailService.queryLogisticsTrace(deliverW.getId());

            if(deliverDetail!=null){

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Map<String,Object> map = new HashMap<>();
                List<String> tradeTermsList = new ArrayList<>();    //贸易术语
                List<String> fromPlaceList = new ArrayList<>();    //货物起运地
                List<String> fromPortList = new ArrayList<>();    //起运港
                List<String> toCountryList = new ArrayList<>();    //目的国
                List<String> toPortList = new ArrayList<>();    //目的港
                List<String> toPlaceList = new ArrayList<>();    //目的地
                List<Goods> goodsList = new ArrayList<>();    //商品信息
                List<DeliverConsign> deliverConsigns = deliverDetail.getDeliverNotice().getDeliverConsigns();
                for (DeliverConsign deliverConsign : deliverConsigns){
                    Order order = deliverConsign.getOrder();
                    tradeTermsList.add(order.getTradeTerms());
                    fromPlaceList.add(order.getFromPlace());
                    fromPortList.add(order.getFromPort());
                    toCountryList.add(order.getToCountry());
                    toPortList.add(order.getToPort());
                    toPlaceList.add(order.getToPlace());

                    List<DeliverConsignGoods> deliverConsignGoodsSet = deliverConsign.getDeliverConsignGoodsSet();
                    for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsSet){
                        Goods goods = deliverConsignGoods.getGoods();
                        if(deliverConsignGoods.getSendNum() != 0){        //物流跟踪 商品信息 数量不能为0
                            goods.setRemarks(deliverConsignGoods.getOutboundRemark());     //备注
                            goods.setPackRequire(deliverDetail.getDeliverNotice().getPackageReq());  //包装要求
                            goods.setOutstockNum(deliverConsignGoods.getSendNum()); // 数量
                            goodsList.add(goods);
                        }
                    }

                }
                List<Attachment> attachmentList =new ArrayList(deliverDetail.getAttachmentList());  //物流跟踪附件信息
                Iterator<Attachment> iterator = attachmentList.iterator();
                while (iterator.hasNext()) {
                    Attachment next = iterator.next();
                    if (!"国际物流部".equals(next.getGroup())) {
                        iterator.remove();
                    }
                }
                map.put("id",deliverDetail.getId());    //id
                map.put("logisticsUserId",deliverDetail.getLogisticsUserId());  //物流经办人id
                map.put("logisticsUserName",deliverDetail.getLogisticsUserName());  //物流经办人名字
                map.put("logisticsDate",sdf.format(deliverDetail.getLogisticsDate()));      //经办日期
                map.put("tradeTermsList",tradeTermsList);    //贸易术语
                map.put("fromPlaceList",fromPlaceList);   //货物起运地
                map.put("fromPortList",fromPortList);  //起运港
                map.put("toCountryList",toCountryList);    //目的国
                map.put("toPortList",toPortList);    //目的港
                map.put("toPlaceList",toPlaceList);    //目的地
                map.put("goodsList",goodsList);    //商品信息
                map.put("bookingTime",sdf.format(deliverDetail.getBookingTime()));   //下发订舱时间
                map.put("logiInvoiceNo",deliverDetail.getLogiInvoiceNo());   //物流发票号
                map.put("packingTime",sdf.format(deliverDetail.getPackingTime()));   //通知市场箱单时间
                map.put("leaveFactory",sdf.format(deliverDetail.getLeaveFactory()));   //离厂时间
                map.put("sailingDate",sdf.format(deliverDetail.getSailingDate()));   //船期或航班
                map.put("customsClearance",sdf.format(deliverDetail.getCustomsClearance()));   //报关放行时间
                map.put("leavePortTime",sdf.format(deliverDetail.getLeavePortTime()));   //实际离港时间
                map.put("arrivalPortTime",sdf.format(deliverDetail.getArrivalPortTime()));   //预计抵达时间
                map.put("logs",deliverDetail.getLogs());   //动态描述
                map.put("remarks",deliverDetail.getRemarks());   //备注
                map.put("status",deliverDetail.getStatus());    //状态
                map.put("attachmentList",attachmentList);   //物流跟踪附件信息

                return new Result<>(map);
            }
            return new Result<>(ResultStatusEnum.FAIL);
    }





}
