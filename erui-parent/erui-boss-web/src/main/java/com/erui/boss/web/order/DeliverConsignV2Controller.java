package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.order.entity.DeliverConsign;
import com.erui.order.entity.Order;
import com.erui.order.requestVo.DeliverConsignListCondition;
import com.erui.order.service.DeliverConsignService;
import com.erui.order.service.DeliverConsignV2Service;
import com.erui.order.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@RestController
@RequestMapping(value = "/order/v2/deliverConsign")
public class DeliverConsignV2Controller {
    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private DeliverConsignV2Service deliverConsignV2Service;



    /**
     * 新增办理出口发货单
     *
     * @param deliverConsign
     * @return
     */
    @RequestMapping(value = "addOrUpdateDeliverConsign", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> addOrUpdateDeliverConsign(@RequestBody DeliverConsign deliverConsign, HttpServletRequest request) {
        String errMsg = null;

        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);

        try {
            boolean flag = false;
            if (deliverConsign.getId() != null) {
                flag = deliverConsignV2Service.updateDeliverConsign(deliverConsign);
            } else {
                flag = deliverConsignV2Service.addDeliverConsign(deliverConsign);
            }
            if (flag) {
                return new Result<>();
            }
        } catch (Exception ex) {
            errMsg = ex.getMessage();
            logger.error("出口发货通知单操作失败：{}", deliverConsign, ex);
        }
        return new Result<>(ResultStatusEnum.FAIL).setMsg(errMsg);
    }


}
