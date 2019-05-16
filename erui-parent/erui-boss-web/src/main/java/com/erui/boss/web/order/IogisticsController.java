package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.data.string.StringUtil;
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

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 出库信息
 * <p>
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
        Page<Iogistics> iogisticsList;
        try {
            iogisticsList = iogisticsService.queIogistics(iogistics);
            for (Iogistics iogistics1 : iogisticsList) {
                DeliverDetail deliverDetail = iogistics1.getDeliverDetail();
                //设置出库信息管理状态 5为已变更
                iogistics1.setStatus(deliverDetail.getStatus());
                iogistics1.setHandleDepartment(deliverDetail.getHandleDepartment()); //经办部门
                iogistics1.setBillingDate(deliverDetail.getBillingDate());  //开单日期
                iogistics1.setReleaseDate(deliverDetail.getReleaseDate());   //放行日期
                iogistics1.setWareHousemanName(deliverDetail.getWareHousemanName()); //仓库经办人姓名
                iogistics1.setLeaveDate(deliverDetail.getLeaveDate());  //出库时间
            }

        } catch (Exception e) {
            String message = e.getMessage();
            return new Result<>(ResultStatusEnum.FAIL).setMsg(message);
        }
        for (Iogistics d : iogisticsList) {
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
        if (iogistics.getId() == null || iogistics.getId() == 0) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg("id不能为空");
        }
        Iogistics iogisticsList;
        try {
            iogisticsList = iogisticsService.queryById(iogistics);
        } catch (Exception e) {
            String message = e.getMessage();
            return new Result<>(ResultStatusEnum.FAIL).setMsg(message);
        }
        Map<String, Object> data = new HashMap<>();

        //出库基本信息
        DeliverDetail deliverDetail = iogisticsList.getDeliverDetail();     // 出库信息

        DeliverConsign deliverConsign = deliverDetail.getDeliverConsign();  //出口通知单
        if (deliverConsign == null) {
            return new Result<>("无出库发货通知单关系");
        }
        Map<String, Object> deliverNoticeInfo = new HashMap<>();

        deliverNoticeInfo.put("toPlace", deliverConsign.getOrder().getToPort()); // 目的港
        deliverNoticeInfo.put("tradeTerms", deliverConsign.getOrder().getTradeTerms()); // 贸易术语
        //处理商品信息
        List<DeliverConsignGoods> deliverConsignGoodsList = deliverDetail.getDeliverConsignGoodsList();
        List<Map<String, Object>> goodsInfoList = DeliverDetailsController.goodsMessage(deliverConsignGoodsList);
        deliverNoticeInfo.put("goodsInfo", goodsInfoList);
        //附件信息
        Map<String, Object> map = DeliverDetailsController.attachmentLists(deliverDetail.getAttachmentList());
        deliverNoticeInfo.put("attachmentList2", map.get("attachmentList2"));    //品控部
        deliverNoticeInfo.put("attachmentList", map.get("attachmentList"));    //仓储物流部
        deliverDetail.setIogistics(null);
        deliverDetail.setDeliverNotice(null);
        deliverDetail.setDeliverConsign(null);
        deliverDetail.setDeliverConsignGoodsList(null);
        data.put("deliverDetailInfo", deliverDetail); // 基本信息
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
    public Result<Object> mergeData(@RequestBody Map<String, String> params, HttpServletRequest request) {
        boolean flag = false;
        String message = null;

        if (StringUtil.isBlank(params.get("logisticsUserId"))) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg("物流经办人id不能为空");
        }
        if (StringUtil.isBlank(params.get("logisticsUserName"))) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg("/物流经办人名称不能为空");
        }

        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);

        try {
            flag = iogisticsService.mergeData(params);
        } catch (Exception ex) {
            message = ex.getMessage();
            logger.error("出库详情页操作失败：{}", ex);
        }
        if (flag) {
            return new Result<>();
        }
        return new Result<>(ResultStatusEnum.FAIL).setMsg(message);
    }


}
