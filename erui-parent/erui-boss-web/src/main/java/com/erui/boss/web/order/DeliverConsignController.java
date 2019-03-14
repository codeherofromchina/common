package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.entity.DeliverConsign;
import com.erui.order.entity.Order;
import com.erui.order.entity.Project;
import com.erui.order.requestVo.DeliverConsignListCondition;
import com.erui.order.service.DeliverConsignService;
import com.erui.order.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

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
                data.put("deptId", order.getBusinessUnitId());
                data.put("deptName", order.getBusinessUnitName());
                data.put("createUserId", order.getAgentId());
                data.put("coId", order.getSigningCo());
                data.put("contractNo", order.getContractNo());
                data.put("totalPriceUsd", order.getTotalPriceUsd());
                data.put("receivablePriceUsd", order.getTotalPriceUsd());
                data.put("perLiableRepay", order.getPerLiableRepay());
                data.put("currencyBn", order.getCurrencyBn());//order表货币类型
                //    data.put("coName",order.getId());
                data.put("goodsList", order.getGoodsList());

                return new Result<>(data);
            } catch (Exception e) {
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
    @RequestMapping(value = "addOrUpdateDeliverConsign", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> addOrUpdateDeliverConsign(@RequestBody DeliverConsign deliverConsign, HttpServletRequest request) {
        // TODO 参数检查略过
        String errMsg = null;

        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);

        try {
            boolean flag = false;
            if (deliverConsign.getId() != null) {
                flag = deliverConsignService.updateDeliverConsign(deliverConsign);
            } else {
                flag = deliverConsignService.addDeliverConsign(deliverConsign);
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
            } catch (Exception e) {
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

    /**
     * 获取订舱列表
     *
     * @return
     */
    @RequestMapping(value = "deliverConsignManage", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> deliverConsignManage(@RequestBody DeliverConsignListCondition condition, HttpServletRequest request) {
        //页数不能小于1
        if (condition.getPage() < 1) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);
        // 设置请求语言
        String lang = CookiesUtil.getLang(request);
        condition.setLang(lang);

        Page<DeliverConsign> deliverConsignPage = deliverConsignService.findByPage(condition);
        if (deliverConsignPage.hasContent()) {
            deliverConsignPage.getContent().forEach(vo -> {
                vo.setoId(vo.getOrder().getId());//order表id
                vo.setContractNo(vo.getOrder().getContractNo());//销售合同号
                vo.setTotalPriceUsd(vo.getOrder().getTotalPriceUsd());//合同总价
                vo.setReceivablePriceUsd(vo.getOrder().getTotalPriceUsd());//应收账款金额
                vo.setPerLiableRepay(vo.getOrder().getPerLiableRepay());//回款责任人
                vo.setCurrencyBn(vo.getOrder().getCurrencyBn());//order表货币类型
                vo.setAttachmentSet(null);
                vo.setDeliverDetail(null);
                vo.setDeliverConsignPayments(null);
                vo.setDeliverConsignGoodsSet(null);
                vo.setDeliverConsignBookingSpace(null);
            });
        }
        return new Result<>(deliverConsignPage);
    }


    /**
     * 审核出口发货通知单
     * param  params type 审核类型：-1：驳回（驳回必须存在驳回原因参数） 其他或空：正常审核
     * param  params auditingReason 审核或驳回原因参数
     * param  params id 要审核或驳回的订舱ID
     * param  params checkLogId 驳回到的流程ID
     *
     * @return
     */
    @RequestMapping(value = "auditDeliverConsign", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> auditDeliverConsign(HttpServletRequest request, @RequestBody DeliverConsign rDeliverConsign) throws Exception {
        //获取token并写入
        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);

        Integer deliverConsignId = rDeliverConsign.getId(); // 出口发货通知单ID
        String reason = rDeliverConsign.getAuditingReason(); // 驳回原因
        String type = rDeliverConsign.getAuditingType(); // 驳回or审核
        Integer checkLogId = rDeliverConsign.getCheckLogId();
        // 判断出口发货通知单是否存在，
        DeliverConsign deliverConsign = deliverConsignService.findById(deliverConsignId);
        if (deliverConsign == null) {
            return new Result<>(ResultStatusEnum.DELIVER_CONSIGN_NOT_EXIST);
        }
        // 获取当前登录用户ID并比较是否是当前用户审核
        Object userId = request.getSession().getAttribute("userid");
        Object realname = request.getSession().getAttribute("realname");
        String auditingUserIds = deliverConsign.getAuditingUserId();
        if (auditingUserIds == null || !equalsAny(String.valueOf(userId), auditingUserIds)) {
            return new Result<>(ResultStatusEnum.NOT_NOW_AUDITOR);
        }

        // 判断是否是驳回并判断原因参数
        boolean rejectFlag = "-1".equals(type);
        if (rejectFlag && (StringUtils.isBlank(reason))) {
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR).setMsg("驳回原因和驳回步骤为必填信息");
        }
        deliverConsign.setCheckLogId(checkLogId);

        // 判断通过，审核项目并返回是否审核成功
        boolean flag = deliverConsignService.audit(deliverConsign, String.valueOf(userId), String.valueOf(realname), rDeliverConsign);
        if (flag) {
            return new Result<>();
        }
        return new Result<>(ResultStatusEnum.FAIL);
    }

    private boolean equalsAny(String src, String searchStr) {
        String[] strings = searchStr.split(",");
        for (String search : strings) {
            if (StringUtils.equals(src, search)) {
                return true;
            }
        }
        return false;
    }
}
