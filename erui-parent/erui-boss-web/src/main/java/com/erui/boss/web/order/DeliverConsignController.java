package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.entity.DeliverConsign;
import com.erui.order.entity.Order;
import com.erui.order.service.DeliverConsignService;
import com.erui.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@RestController
@RequestMapping(value = "/order/deliverConsign")
public class DeliverConsignController {
    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private DeliverConsignService deliverConsignService;

    @Autowired
    private OrderService orderService;

    /**
     * 根据ID获取出口通知单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "get")
    public Result<Object> get(@RequestParam(name = "id") Integer id) throws Exception {
        DeliverConsign deliverConsign = deliverConsignService.findById(id);
        return new Result<>(deliverConsign);
    }


    /**
     * 根据订单ID获取出口通知单所需信息
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "orderInfoForAdd", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public Result<Object> orderInfoForAdd(@RequestBody Map<String, Integer> map, HttpServletRequest request) {

        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);

        if (map.get("orderId") != null) {
            try {
                Order order = orderService.detail(map.get("orderId"));
                Map<String, Object> data = new HashMap<>();
                data.put("orderId", order.getId());
                data.put("deptId", order.getExecCoId());
                data.put("deptName", order.getExecCoName());
                data.put("createUserId", order.getAgentId());
                data.put("coId", order.getSigningCo());
                //    data.put("coName",order.getId());
                data.put("goodsList", order.getGoodsList());
                return new Result<>(data);
            }catch (Exception e){
                return new Result<>(ResultStatusEnum.DATA_NULL).setMsg(e.getMessage());
            }
        }
        return new Result<>(ResultStatusEnum.DATA_NULL);
    }

    /**
     * 新增办理出口发货单
     *
     * @param deliverConsign
     * @return
     */
    @RequestMapping(value = "addDeliverConsign", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> addDeliverConsign(@RequestBody DeliverConsign deliverConsign, HttpServletRequest request) {
        // TODO 参数检查略过
        String errMsg = null;

        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);

        try {
            boolean flag = false;
            if (deliverConsign.getId() != null) {
                flag = deliverConsignService.updateDeliverConsign(deliverConsign);
            } else {
                flag = deliverConsignService.addDelgitiverConsign(deliverConsign);
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

    /**
     * 获取出口发货单详情
     *
     * @return
     */
    @RequestMapping(value = "queryDeliverConsignDesc", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> queryOrderDesc(@RequestBody Map<String, Integer> map, HttpServletRequest request) {

        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);

        if (map.get("id") != null) {
            try {
                DeliverConsign deliverConsign = deliverConsignService.findById(map.get("id"));
                return new Result<>(deliverConsign);
            }catch (Exception e){
                return new Result<>(ResultStatusEnum.DATA_NULL).setMsg(e.getMessage());
            }
        }
        return new Result<>(ResultStatusEnum.DATA_NULL);
    }

    /**
     * 获取出口发货单列表
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "queryExportList", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<List<DeliverConsign>> queryExportList(@RequestBody Map<String, Integer> map) {
        if (map.get("orderId") != null) {
            List<DeliverConsign> deliverList = deliverConsignService.findByOrderId(map.get("orderId"));
            return new Result<>(deliverList);
        }
        return new Result<>(ResultStatusEnum.DATA_NULL);
    }
}
