package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.order.entity.DeliverDetail;
import com.erui.order.requestVo.DeliverD;
import com.erui.order.service.DeliverDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

        Page<DeliverDetail> deliverDetail=deliverDetailService.outboundManage(deliverD);


        return new Result<>(deliverDetail);
    }



    /**
     * 出库详情页 - 查看
     *
     * @return
     */
    //TODO 待掉接口

    @RequestMapping(value = "outboundDetailsPage", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> outboundDetailsPage(){

        return new Result<>(null);
    }



    /**
     * 出库详情页 保存 or 提交质检
     *
     * @param deliverDetail
     * @return
     */
    @RequestMapping(value = "outboundSaveOrAdd", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> outboundSaveOrAdd(@RequestBody DeliverDetail deliverDetail){
        try {
            boolean flag = false;
                flag = deliverDetailService.outboundSaveOrAdd(deliverDetail);
            if (flag) {
                return new Result<>();
            }
        } catch (Exception ex) {
            logger.error("订单操作失败：{}", deliverDetail, ex);
        }
        return new Result<>(ResultStatusEnum.FAIL);
    }






}
