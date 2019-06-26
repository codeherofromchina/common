package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.entity.*;
import com.erui.order.service.DeliverConsignService;
import com.erui.order.service.DeliverNoticeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 看货通知单控制器
 * Created by wangxiaodan on 2017/12/11.
 */
@RestController
@RequestMapping(value = "/order/deliverNotice")
public class DeliverNoticeController {

    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private DeliverNoticeService deliverNoticeService;

    @Autowired
    private DeliverConsignService deliverConsignService;


    /**
     * 看货通知管理列表
     * @param condition
     * @return
     */
    @RequestMapping(value = "list")
    public Result<Object> lookMoneyInformManage(@RequestBody DeliverNotice condition) {
        int page = condition.getPage();
        if (page < 1) {
            return new Result<>(ResultStatusEnum.PAGE_ERROR);
        }
        try {
            Page<DeliverNotice> pageList = deliverNoticeService.listByPage(condition);
            for (DeliverNotice deliverNotice : pageList){
                deliverNotice.setDeliverDetail(null);
                deliverNotice.setDeliverConsignId(deliverNotice.getDeliverConsign().getId());
            }
            return new Result<>(pageList);
        }catch (Exception e){
            logger.error("查询错误", e);
        }
        return new Result<>(ResultStatusEnum.DATA_NULL);
    }

    /**
     * 看货通知单 保存 or  提交
     *
     * @return
     */
    @RequestMapping(value = "saveOrAdd", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> exitRequisitionSaveOrAdd(@RequestBody DeliverNotice deliverNotice, HttpServletRequest request) {
        Result<Object> result = new Result<>();
        try {
            String eruiToken = CookiesUtil.getEruiToken(request);
            ThreadLocalUtil.setObject(eruiToken);

            Object userId = request.getSession().getAttribute("userid");
            Object userName = request.getSession().getAttribute("realname");
            deliverNotice.setSenderId(Integer.parseInt(userId.toString())); // 下单人 --> 取当前登录人信息
            deliverNotice.setSenderName(userName.toString()); // 下单人 --> 取当前登录人信息

            boolean flag = false;
            if (deliverNotice.getId()!= null) {
                flag = deliverNoticeService.updateDeliverNotice(deliverNotice);
            } else {
                if(deliverNotice.getDeliverConsignId() == null){
                    result.setCode(ResultStatusEnum.FAIL.getCode());
                    result.setMsg("出库通知单ID不能为空");
                    return result;
                }else{
                    flag = deliverNoticeService.addDeliverNotice(deliverNotice);
                }
            }
            if (flag) {
                return new Result<>();
            }
        } catch (Exception ex) {
            logger.error("看货通知单操作失败：{}", deliverNotice, ex);
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("看货通知单操作失败");
        }
        return result;
    }

    /**
     *看货通知详情 - 查看
     *
     * @param deliverNotice    看货通知单号id
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> exitRequisitionQuery(@RequestBody DeliverNotice deliverNotice) {
        if(deliverNotice == null || (deliverNotice.getId() == null && deliverNotice.getDeliverConsignId() == null)){
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        try {
            DeliverNotice detailNotice = new DeliverNotice();
            if(deliverNotice.getId() != null){
                detailNotice = deliverNoticeService.queryDeliverNoticeDetail(deliverNotice.getId());
            }else{
                detailNotice = deliverNoticeService.queryByDeliverConsignId(deliverNotice.getDeliverConsignId());
            }
            return new Result<>(detailNotice);
        } catch (Exception e) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }

    }

    /**
     * 看货通知信息 - 根据出口发货通知单 查询信息
     * @param map  看货通知单号  数组
     * @return
     */
    @RequestMapping(value = "querExitInformMessage")
    public Result<Object> querExitInformMessage(@RequestBody Map<String,String> map) {
        String deliverNoticeIds = map.get("deliverNoticeIds");
        Integer[] intTemp;
        if (StringUtil.isNotBlank(deliverNoticeIds)){
            String[] split = deliverNoticeIds.split(",");
            intTemp = new Integer[split.length];
            for (int i = 0; i <split.length; i++) {
                intTemp[i] = Integer.parseInt(split[i]);
            }
        }else{
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        try {
            List<DeliverConsign> list= deliverConsignService.querExitInformMessage(intTemp);
            Map<String,Object> data = new HashMap<>();
            List<Goods> goodsList = new ArrayList<>();  //商品信息
            List<DeliverConsign> deliverConsignNoList = new ArrayList<>();  //出口发货通知单号
            List<String> tradeTermsList = new ArrayList<>();  //贸易术语
            List<String> toPlaceList = new ArrayList<>();  //目的地
            List<String>  transportTypeList = new ArrayList<>();  //运输方式
            List<Integer>  agentNameList = new ArrayList<>();  //商务技术经办人名字
            List<String> deliveryDateList = new ArrayList<>();  //执行单约定交付日期
            List dcAttachmentSetList = new ArrayList<>();  //出口通知单附件

            for (DeliverConsign deliverConsign : list){
                DeliverConsign deliverConsignS = new DeliverConsign();
                deliverConsignS.setId(deliverConsign.getId());
                deliverConsignS.setDeliverConsignNo(deliverConsign.getDeliverConsignNo());
                deliverConsignNoList.add(deliverConsignS);
                dcAttachmentSetList = deliverConsign.getAttachmentSet();
                Order order1 = deliverConsign.getOrder();
                tradeTermsList.add(order1.getTradeTerms());
                toPlaceList.add(order1.getToPlace());
                transportTypeList.add(order1.getTransportType());
                agentNameList.add(order1.getTechnicalId());
                deliveryDateList.add(new SimpleDateFormat("yyyy-MM-dd").format(order1.getDeliveryDate()));
                order1.setAttachmentSet(null);
                order1.setGoodsList(null);
                order1.setOrderPayments(null);
                List<DeliverConsignGoods> deliverConsignGoodsSet = deliverConsign.getDeliverConsignGoodsSet();
                for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsSet){
                    if(deliverConsignGoods.getSendNum() != 0){
                        Goods goods = deliverConsignGoods.getGoods();
                        goods.setSendNum(deliverConsignGoods.getSendNum());
                        goodsList.add(goods);
                    }
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

        } catch (Exception e) {
            logger.error("根据出口发货通知单查询信息 - 异常错误", e.getMessage());
            return new Result<>(ResultStatusEnum.DATA_NULL).setMsg(e.getMessage());
        }
    }

}
