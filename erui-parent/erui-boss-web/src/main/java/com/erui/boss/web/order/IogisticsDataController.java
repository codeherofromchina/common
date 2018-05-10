package com.erui.boss.web.order;
import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.entity.*;
import com.erui.order.service.IogisticsDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 *
 * 物流
 *
 * Created by wangxiaodan on 2017/12/11.
 */
@RestController
@RequestMapping(value = "/order/iogisticsData")
public class IogisticsDataController {

    private final static Logger logger = LoggerFactory.getLogger(DeliverDetailsController.class);

    @Autowired
    private IogisticsDataService iogisticsDataService;

    /**
     * 物流跟踪管理（V 2.0）   列表页查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "trackingList", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> trackingList(@RequestBody IogisticsData iogisticsData) {
        Page<IogisticsData> iogisticsDataList = iogisticsDataService.trackingList(iogisticsData);   //查询合并信息

        for(IogisticsData v :iogisticsDataList){    //查询列表展示信息
            v.setIogistics(null);
        }
        return new Result<>(iogisticsDataList);
    }

    /**
     * 物流跟踪管理（V 2.0）  查询物流详情id
     *
     * @param
     * @return
     */
    @RequestMapping(value = "iogisticsDataById", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> iogisticsDataById(@RequestBody IogisticsData iogisticsData) throws Exception {
        String message =null;
            IogisticsData iogisticsDataById = iogisticsDataService.iogisticsDataById(iogisticsData.getId());

        List<Iogistics> iogisticsList = iogisticsDataById.getIogistics();   //出库分单信息

        Map<String,Object> map = new HashMap<>();
        Set<String> tradeTermsList = new HashSet<>();    //贸易术语
        Set<String> fromPlaceList = new HashSet<>();    //货物起运地
        Set<String> fromPortList = new HashSet<>();    //起运港
        Set<String> toCountryList = new HashSet<>();    //目的国
        Set<String> toPortList = new HashSet<>();    //目的港
        Set<String> toPlaceList = new HashSet<>();    //目的地
        List<Goods> goodsList = new ArrayList<>();    //商品信息
        Map<Integer,Goods> idSet = new HashMap<>();
        
        for (Iogistics iogistics : iogisticsList){
            Order order = iogistics.getDeliverDetail().getDeliverConsign().getOrder();//获取 出库信息  出口发货通知单  订单
            tradeTermsList.add(order.getTradeTerms());   //贸易术语
            fromPlaceList.add(order.getFromPlace());    //货物起运地
            fromPortList.add(order.getFromPort());      //起运港
            toCountryList.add(order.getToCountry());    //目的国
            toPortList.add(order.getToPort());          //目的港
            toPlaceList.add(order.getToPlace());        //目的地

            Integer outCheck = iogistics.getOutCheck(); //是否外键（ 0：否   1：是）

            List<DeliverConsignGoods> deliverConsignGoodsList = iogistics.getDeliverDetail().getDeliverConsignGoodsList();
            for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsList){
                if(deliverConsignGoods.getSendNum() != 0){  //本批次发货数量为0的商品不展示
                    Integer dcgId = deliverConsignGoods.getId();

                    if (idSet.containsKey(dcgId)) {
                        Goods existsGoods = idSet.get(dcgId);
                        if (outCheck == 0) {
                            existsGoods.setStraightNum(existsGoods.getStraightNum() + deliverConsignGoods.getStraightNum()); //厂家直发数量
                        } else {
                            existsGoods.setOutboundNum(existsGoods.getOutboundNum() + deliverConsignGoods.getOutboundNum());   //出库数量
                        }

                    } else {
                        Goods newGoods = new Goods();
                        Goods goods = deliverConsignGoods.getGoods();
                        if (outCheck == 0) {
                            newGoods.setOutboundNum(0);   //出库数量
                            newGoods.setStraightNum(deliverConsignGoods.getStraightNum()); //厂家直发数量
                        } else {
                            newGoods.setOutboundNum(deliverConsignGoods.getOutboundNum());   //出库数量
                            newGoods.setStraightNum(0); //厂家直发数量
                        }
                        newGoods.setContractNo(goods.getContractNo()); //销售合同号
                        newGoods.setDeliverDetailNo(iogistics.getDeliverDetailNo()); //产品放行单号
                        newGoods.setNameZh(goods.getNameZh()) ; //商品名称
                        newGoods.setClientDesc(goods.getClientDesc()); //描述
                        newGoods.setModel(goods.getModel());    //规格型号
                        newGoods.setOutstockNum(deliverConsignGoods.getSendNum()); // 本批次发货数量
                        newGoods.setRemarks(deliverConsignGoods.getOutboundRemark());     //备注  出口商品备注


                        newGoods.setUnit(goods.getUnit());  //单位

                        goodsList.add(newGoods);
                        idSet.put(dcgId,newGoods);

                    }
                }
            }

        }

        List<Attachment> attachmentList =new ArrayList(iogisticsDataById.getAttachmentList());  //物流跟踪附件信息
        Iterator<Attachment> iterator = attachmentList.iterator();
        while (iterator.hasNext()) {
            Attachment next = iterator.next();
            if (!"国际物流部".equals(next.getGroup())) {
                iterator.remove();
            }
        }

        map.put("id",iogisticsDataById.getId());    //id
        map.put("logisticsUserId",iogisticsDataById.getLogisticsUserId());  //物流经办人id
        map.put("logisticsUserName",iogisticsDataById.getLogisticsUserName());  //物流经办人名字
        map.put("logisticsDate",iogisticsDataById.getLogisticsDate());      //经办日期
        map.put("tradeTermsList",tradeTermsList);    //贸易术语
        map.put("fromPlaceList",fromPlaceList);   //货物起运地
        map.put("fromPortList",fromPortList);  //起运港
        map.put("toCountryList",toCountryList);    //目的国
        map.put("toPortList",toPortList);    //目的港
        map.put("toPlaceList",toPlaceList);    //目的地
        map.put("goodsList",goodsList);    // TODO 商品信息
        map.put("bookingTime",iogisticsDataById.getBookingTime());   //下发订舱时间
        map.put("logiInvoiceNo",iogisticsDataById.getLogiInvoiceNo());   //物流发票号
        map.put("packingTime",iogisticsDataById.getPackingTime());   //通知市场箱单时间
        map.put("leaveFactory",iogisticsDataById.getLeaveFactory());   //离厂时间
        map.put("sailingDate",iogisticsDataById.getSailingDate());   //船期或航班
        map.put("customsClearance",iogisticsDataById.getCustomsClearance());   //报关放行时间
        map.put("leavePortTime",iogisticsDataById.getLeavePortTime());   //实际离港时间
        map.put("arrivalPortTime",iogisticsDataById.getArrivalPortTime());   //预计抵达时间
        map.put("logs",iogisticsDataById.getLogs());   //动态描述
        map.put("remarks",iogisticsDataById.getRemarks());   //备注
        map.put("status",iogisticsDataById.getStatus());    //状态
        map.put("attachmentList",attachmentList);   //物流跟踪附件信息

        return new Result<>(map);

    }



    /**
     * 物流跟踪管理（V 2.0）物流动态跟踪 ：动态更新-项目完结
     *
     * @param
     * @return
     */
    @RequestMapping(value = "logisticsActionAddOrSave", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> logisticsActionAddOrSave(@RequestBody IogisticsData iogisticsData){
        iogisticsDataService.logisticsActionAddOrSave(iogisticsData);
        return new Result<>();
    }





    /**
     * 订单执行跟踪(V2.0)  根据运单号（物流单号）查询物流信息
     * @param
     * @return
     */
    @RequestMapping(value = "/queryByTheAwbNo", method = RequestMethod.POST)
    public Result<Object> queryByTheAwbNo(@RequestBody Map<String, String> iogisticsDatas) {
        String errMsg = null;
        String theAwbNo = iogisticsDatas.get("theAwbNo");
        if(StringUtil.isEmpty(theAwbNo)){
            errMsg = "运单号不能为空";
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR).setMsg(errMsg);
        }

        IogisticsData iogisticsData = iogisticsDataService.queryByTheAwbNo(theAwbNo);
        if (iogisticsData == null){
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }

        Map<String, Object> goodsInfoMap = new HashMap<>();
        goodsInfoMap.put("deliverDetailNo",iogisticsData.getDeliverDetailNo()); //运单号
        goodsInfoMap.put("bookingTime",iogisticsData.getBookingTime()); //下发订舱时间
        goodsInfoMap.put("packingTime",iogisticsData.getPackingTime()); //通知市场箱单时间
        goodsInfoMap.put("leaveFactory",iogisticsData.getLeaveFactory()); //离厂时间
        goodsInfoMap.put("sailingDate",iogisticsData.getSailingDate()); //船期或航班
        goodsInfoMap.put("customsClearance",iogisticsData.getCustomsClearance()); //报关放行时间
        goodsInfoMap.put("leavePortTime",iogisticsData.getLeavePortTime()); //实际离港时间
        goodsInfoMap.put("arrivalPortTime",iogisticsData.getArrivalPortTime()); //预计抵达时间
        goodsInfoMap.put("accomplishDate",iogisticsData.getAccomplishDate()); //商品已到达目的地
        goodsInfoMap.put("confirmTheGoods",iogisticsData.getConfirmTheGoods()); //确认收货
        return new Result<>(goodsInfoMap);
    }


    /**
     *  V2.0 订单执行跟踪  根据运单号（物流单号）查询物流信息   确认收货
     * @param
     * @return
     */
    @RequestMapping(value = "/confirmTheGoodsByTheAwbNo", method = RequestMethod.POST)
    public Result<Object> confirmTheGoodsByTheAwbNo(@RequestBody IogisticsData iogisticsData) {
        String errMsg = null;
        if(StringUtil.isEmpty(iogisticsData.getTheAwbNo())){
            errMsg = "运单号不能为空";
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR).setMsg(errMsg);
        }
        if(iogisticsData.getConfirmTheGoods() == null){
            errMsg = "确认收货时间不能为空";
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR).setMsg(errMsg);
        }

        try {
            iogisticsDataService.confirmTheGoodsByTheAwbNo(iogisticsData);
        }catch (Exception ex){
            errMsg = ex.getMessage();
            logger.error("异常错误", ex);
        }

        return new Result<>(ResultStatusEnum.NULL_DATA).setMsg(errMsg);

    }


    /*

    确认收货按钮  测试用（避免出问题）
    @RequestMapping(value = "/aaa", method = RequestMethod.POST)
    public Result<Object> a(@RequestBody IogisticsData iogisticsData) {
        Boolean statusAndNumber = iogisticsDataService.findStatusAndNumber(iogisticsData.getId());
        return new Result<>(statusAndNumber);

    }*/


}
