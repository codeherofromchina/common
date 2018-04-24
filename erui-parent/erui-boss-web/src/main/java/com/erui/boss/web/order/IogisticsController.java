package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.*;
import com.erui.order.service.IogisticsService;
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
@RequestMapping(value = "/order/iogistics")
public class IogisticsController {

    private final static Logger logger = LoggerFactory.getLogger(DeliverDetailsController.class);


    @Autowired
    private IogisticsService iogisticsService;

    /**
     * 出库信息管理（V 2.0）   查询列表页
     *
     * @param
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> list(@RequestBody Iogistics iogistics) {
        Page<Iogistics> iogisticsList ;
        try {
            iogisticsList = iogisticsService.queIogistics(iogistics);
            for (Iogistics iogistics1 : iogisticsList){
                DeliverDetail deliverDetail = iogistics1.getDeliverDetail();
                iogistics1.setHandleDepartment(deliverDetail.getHandleDepartment()); //经办部门
                iogistics1.setBillingDate(deliverDetail.getBillingDate());  //开单日期
                iogistics1.setReleaseDate(deliverDetail.getReleaseDate());   //放行日期
                iogistics1.setWareHousemanName(deliverDetail.getWareHousemanName()); //仓库经办人姓名
                iogistics1.setLeaveDate(deliverDetail.getLeaveDate());  //出库时间
            }

        }catch (Exception e){
            String message = e.getMessage();
            return new Result<>(ResultStatusEnum.FAIL).setMsg(message);
        }
        for (Iogistics d : iogisticsList){
            d.getDeliverDetail().setDeliverConsignGoodsList(null);
        }
        return new Result<>(iogisticsList);
    }

    /**
     * 出库信息管理（V 2.0）   出库详情信息（查看）
     *
     * @param
     * @return
     */
    @RequestMapping(value = "queryById", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> queryById(@RequestBody Iogistics iogistics) {
        if(iogistics.getId() == null || iogistics.getId() == 0){
            return new Result<>(ResultStatusEnum.FAIL).setMsg("id不能为空");
        }
        Iogistics iogisticsList;
        try {
            iogisticsList = iogisticsService.queryById(iogistics);
        }catch (Exception e){
            String message = e.getMessage();
            return new Result<>(ResultStatusEnum.FAIL).setMsg(message);
        }
        Map<String, Object> data = new HashMap<>();

        //出库基本信息
        DeliverDetail deliverDetail = iogisticsList.getDeliverDetail();     // 出库信息
        data.put("deliverDetailInfo", deliverDetail); // 基本信息
        DeliverConsign deliverConsign = deliverDetail.getDeliverConsign();  //出口通知单
        if(deliverConsign == null ){
            return new Result<>("无出库发货通知单关系");
        }
        Map<String, Object> deliverNoticeInfo = new HashMap<>();

        deliverNoticeInfo.put("toPlace",deliverConsign.getOrder().getToPort()); // 目的港
        deliverNoticeInfo.put("tradeTerms",deliverConsign.getOrder().getTradeTerms()); // 贸易术语
        //TODO 看货通知信息无法推送
       /* deliverNoticeInfo.put("numers", deliverNotice.getNumers()); // 总包装件数
        deliverNoticeInfo.put("id", deliverNotice.getId()); // 发货通知单ID
        deliverNoticeInfo.put("prepareReq", deliverNotice.getPrepareReq()); // 备货要求
        deliverNoticeInfo.put("packageReq", deliverNotice.getPackageReq()); // 包装要求*/

        //处理商品信息
        List<Map<String, Object>> goodsInfoList = DeliverDetailsController.goodsMessage(deliverDetail.getDeliverConsignGoodsList());
        deliverNoticeInfo.put("goodsInfo", goodsInfoList);

        //附件信息
        Map<String, Object> map = DeliverDetailsController.attachmentLists(deliverDetail.getAttachmentList());
        deliverNoticeInfo.put("attachmentList2", map.get("attachmentList2"));    //品控部
        deliverNoticeInfo.put("attachmentList", map.get("attachmentList"));    //仓储物流部

        data.put("deliverNoticeInfo", deliverNoticeInfo);// 出库信息
        return new Result<>(data);
    }


    /**
     * 出库信息管理（V 2.0）   合并出库信息，分单员
     *
     * @param
     * @return
     */
    @RequestMapping(value = "mergeData", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> mergeData(@RequestBody Map<String, String> params) {
        boolean flag = false;
        String message =null;
       try {
            flag = iogisticsService.mergeData(params);
        } catch (Exception ex) {
            message=ex.getMessage();
            logger.error("出库详情页操作失败：{}",  ex);
        }
        if (flag) {
            return new Result<>();
        }
        return new Result<>(ResultStatusEnum.FAIL).setMsg(message);
    }



}
