package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.EruitokenUtil;
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
        try {
            Page<DeliverNotice> pageList = deliverNoticeService.listByPage(condition);
            for (DeliverNotice deliverNotice : pageList){
                deliverNotice.setDeliverDetail(null);
            }
            return new Result<>(pageList);
        }catch (Exception e){
            logger.error("查询错误", e);
        }
        return new Result<>(ResultStatusEnum.DATA_NULL);
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
                    deliveryDateList.add(new SimpleDateFormat("yyyy-MM-dd").format(order1.getProject().getDeliveryDate()));
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



    /**
     * 看货通知详情 保存 or  提交
     *
     * @return
     */
    @RequestMapping(value = "exitRequisitionSaveOrAdd", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> exitRequisitionSaveOrAdd(@RequestBody DeliverNotice deliverNotice, HttpServletRequest request) {
        Result<Object> result = new Result<>();
        try {
            String eruiToken = EruitokenUtil.getEruiToken(request);
            ThreadLocalUtil.setObject(eruiToken);

            boolean flag = false;
            if (deliverNotice.getId()!= null) {
                flag = deliverNoticeService.updateexitRequisition(deliverNotice);
            } else {
                if(deliverNotice.getSenderId() == null){
                    result.setCode(ResultStatusEnum.FAIL.getCode());
                    result.setMsg("下单人id不能为空");
                    return result;
                }
                if(StringUtil.isBlank(deliverNotice.getSenderName())|| StringUtils.equals(deliverNotice.getSenderName(), "")){
                    result.setCode(ResultStatusEnum.FAIL.getCode());
                    result.setMsg("下单人名称不能为空");
                    return result;
                }
                 else if(deliverNotice.getSendDate() == null){
                    result.setCode(ResultStatusEnum.FAIL.getCode());
                    result.setMsg("下单日期不能为空");
                    return result;
                }
                else if(StringUtil.isBlank(deliverNotice.getDeliverConsignIds())|| StringUtils.equals(deliverNotice.getDeliverConsignIds(), "")){
                    result.setCode(ResultStatusEnum.FAIL.getCode());
                    result.setMsg("出口通知单不能为空");
                    return result;
                }else{
                    flag = deliverNoticeService.addexitRequisition(deliverNotice);
                }
            }
            if (flag) {
                return new Result<>();
            }
        } catch (Exception ex) {
            logger.error("看货通知详情操作失败：{}", deliverNotice, ex);
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("看货通知详情操作失败");
        }
        return result;
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
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }

        try {
            DeliverNotice page = deliverNoticeService.exitRequisitionQuery(deliverNotice.getId());
            List<Goods> goodsList = new ArrayList<>();  //商品信息
            List<DeliverConsign> deliverConsignNoList = new ArrayList<>();  //出口发货通知单号
            List<String> tradeTermsList = new ArrayList<>();  //贸易术语
            List<String> toPlaceList = new ArrayList<>();  //目的地
            List<String>  transportTypeList = new ArrayList<>();  //运输方式
            List<Integer>  agentNameList = new ArrayList<>();  //商务技术经办人名字
            List<String> deliveryDateList = new ArrayList<>();  //执行单约定交付日期
            List dcAttachmentSetList = new ArrayList<>();  //出口通知单附件

            List<DeliverConsign> deliverConsigns = page.getDeliverConsigns();
            for (DeliverConsign deliverConsign : deliverConsigns){
                dcAttachmentSetList = deliverConsign.getAttachmentSet();
                DeliverConsign deliverConsignS = new DeliverConsign();
                deliverConsignS.setId(deliverConsign.getId());
                deliverConsignS.setDeliverConsignNo(deliverConsign.getDeliverConsignNo());
                deliverConsignNoList.add(deliverConsignS);
                List<DeliverConsignGoods> deliverConsignGoodsSet = deliverConsign.getDeliverConsignGoodsSet();
                    for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsSet){
                        if(deliverConsignGoods.getSendNum() != 0){
                            Goods goods = deliverConsignGoods.getGoods();
                            goods.setSendNum(deliverConsignGoods.getSendNum());
                            goodsList.add(goods);
                        }
                    }
                Order order = deliverConsign.getOrder();
                tradeTermsList.add( order.getTradeTerms());
                toPlaceList.add(order.getToPlace());
                transportTypeList.add(order.getTransportType());
                agentNameList.add(order.getTechnicalId());
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
        } catch (Exception e) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }

    }

    /**
     * 看货通知管理   查询出口发货通知单
     * @return
     */
    @RequestMapping(value = "queryExitAdvice")
    public Result<Object> queryExitAdvice(@RequestBody DeliverNotice deliverNotice) {
        Page<DeliverConsign> list =deliverConsignService.queryExitAdvice(deliverNotice);

        Map<String,Object> map1 = new HashMap<>();
            List<Map<String,Object>> list1 = new ArrayList<>();
            for (DeliverConsign deliverConsign :list){
                Map<String,Object> map = new HashMap<>();
                deliverConsign.setAttachmentSet(null);
                deliverConsign.setDeliverConsignGoodsSet(null);
                map.put("id",deliverConsign.getId());   //出口发货通知单id
                map.put("deliverConsignNo",deliverConsign.getDeliverConsignNo()); //出口发货通知单号
                Order order = deliverConsign.getOrder();
                map.put("contractNo",order.getContractNo());    //销售合同号
                map.put("projectNo",order.getProject().getProjectNo());  //项目号
                list1.add(map);
            }
            map1.put("rows",list1);
            map1.put("total",list.getTotalElements());
            return new Result<>(map1);
    }




}
