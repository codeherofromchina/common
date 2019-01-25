package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.order.entity.*;
import com.erui.order.requestVo.PurchParam;
import com.erui.order.service.CheckLogService;
import com.erui.order.service.PurchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.apache.commons.lang3.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/12.
 */
@RestController
@RequestMapping("order/purch")
public class PurchController {
    private final static Logger logger = LoggerFactory.getLogger(PurchController.class);

    @Autowired
    private PurchService purchService;


    /**
     * 根据订单id查询(进行中/已完成)采购列表
     *
     * @param params {orderId:"订单ID"}
     * @return
     */
    @RequestMapping(value = "listByOrderId", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> listByOrderId(@RequestBody Map<String, Integer> params) {

        List<Map<String, Object>> data = null;
        Integer orderId = params.get("orderId");
        if (orderId != null && orderId > 0) {
            try {
                data = purchService.listByOrderId(orderId);
            } catch (Exception e) {
                logger.error("错误", e);
                return new Result<>(ResultStatusEnum.FAIL);
            }
        } else {
            data = new ArrayList<>();
        }


        return new Result<>(data);
    }

    /**
     * 获取采购单列表
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> list(HttpServletRequest request, @RequestBody Purch condition) {
        // 获取当前用户ID
        Object userId = request.getSession().getAttribute("userid");
        if (userId != null) {
            String ui = String.valueOf(userId);
            if (StringUtils.isNotBlank(ui) && StringUtils.isNumeric(ui)) {
                condition.setAuditingUserId(Integer.parseInt(ui));
            }
        }

        int page = condition.getPage();
        if (page < 1) {
            return new Result<>(ResultStatusEnum.PAGE_ERROR);
        }
        Page<Purch> purchPage = purchService.findByPage(condition);
        // 转换为控制层需要的数据
        if (purchPage.hasContent()) {
            purchPage.getContent().forEach(vo -> {
                vo.setAttachments(null);
                vo.setPurchPaymentList(null);

                if (vo.getStatus() == Purch.StatusEnum.BEING.getCode()) {
                    if (vo.getPurchGoodsList().stream().anyMatch(purchGoods -> {
                        return purchGoods.getPreInspectNum() < purchGoods.getPurchaseNum();
                    })) {
                        vo.setInspected(Boolean.FALSE);
                    } else {
                        vo.setInspected(Boolean.TRUE);
                    }
                }
                vo.setPurchGoodsList(null);
            });
        }
        return new Result<>(purchPage);
    }


    /**
     * 新增采购单
     *
     * @param purch
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> save(HttpServletRequest request, @RequestBody Purch purch) {
        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);
        boolean continueFlag = true;
        String errorMsg = null;
        // 状态检查
        Purch.StatusEnum statusEnum = Purch.StatusEnum.fromCode(purch.getStatus());
        // 不是提交也不是保存
        if (statusEnum != Purch.StatusEnum.BEING && statusEnum != Purch.StatusEnum.READY) {
            continueFlag = false;
            errorMsg = "数据的状态不正确";
        }
        // 查看采购号是否存在
       /* if (StringUtils.isBlank(purch.getPurchNo())) {
            continueFlag = false;
            errorMsg = "采购合同号不能为空";
        }*/


        if (continueFlag) {
            try {
                boolean flag = false;
                if (purch.getId() != null) {
                    flag = purchService.update(purch);
                } else {
                    flag = purchService.insert(purch);
                }
                if (flag) {
                    return new Result<>();
                }
            } catch (Exception ex) {
                logger.error("采购单操作失败：{}", purch, ex);
                errorMsg = ex.getMessage();
            }
        }

        return new Result<>(ResultStatusEnum.FAIL).setMsg(errorMsg);
    }

    /**
     * 审核项目
     *
     * @param purchParam type 审核类型：-1：驳回（驳回必须存在驳回原因参数） 其他或空：正常审核
     * @param purchParam reason 驳回原因参数
     * @param purchParam orderId 要审核或驳回的项目ID
     * @return
     */
    @RequestMapping(value = "auditPurch", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> auditOrder(HttpServletRequest request, @RequestBody PurchParam purchParam) throws Exception {
        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);
        Integer purchId = purchParam.getId(); // 订单ID
        String reason = purchParam.getAuditingReason(); // 驳回原因
        String type = purchParam.getAuditingType(); // 驳回or审核
        Integer checkLogId = purchParam.getCheckLogId();
        // 判断采购订单是否存在
        Purch purch = purchService.findDetailInfo(purchId);
        if (purch == null) {
            return new Result<>(ResultStatusEnum.PROJECT_NOT_EXIST);
        }
        // 获取当前登录用户ID并比较是否是当前用户审核
        Object userId = request.getSession().getAttribute("userid");
        Object userName = request.getSession().getAttribute("realname");
        Integer auditingUserId = purch.getAuditingUserId();
        if (auditingUserId == null || !StringUtils.equals(String.valueOf(userId), auditingUserId.toString())) {
            return new Result<>(ResultStatusEnum.NOT_NOW_AUDITOR);
        }
        // 判断是否是驳回并判断原因参数
        boolean rejectFlag = "-1".equals(type);
        if (rejectFlag && (StringUtils.isBlank(reason))) {
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR).setMsg("驳回原因和驳回步骤为必填信息");
        }
        // 判断通过，审核项目并返回是否审核成功
        boolean flag = purchService.audit(purchId, String.valueOf(userId), String.valueOf(userName), purchParam);
        if (flag) {
            return new Result<>();
        }
        return new Result<>(ResultStatusEnum.FAIL);
    }

    /**
     * 获取采购详情信息
     *
     * @param purch {id:采购ID}
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> detail(@RequestBody Purch purch) {
        if (purch == null || purch.getId() == null) {
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR);
        }
        Purch data = purchService.findDetailInfo(purch.getId());
        if (data != null) {
            return new Result<>(data);
        }

        return new Result<>(ResultStatusEnum.FAIL);
    }


    /**
     * 为添加报检单而获取采购信息
     *
     * @param params {purchId:采购ID}
     * @return
     */
    @RequestMapping(value = "getInfoForInspectApply", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> getInfoForInspectApply(@RequestBody Map<String, Integer> params) {
        if (params.get("purchId") == null) {
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR);
        }
        Purch purch = purchService.findPurchAndGoodsInfo(params.get("purchId"));

        // 只有进行中的采购才可以新增报检单信息
        if (purch != null && purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
            // 整合数据
            Map<String, Object> data = new HashMap<>();
            data.put("purchId", purch.getId());
            data.put("purchNo", purch.getPurchNo()); // 采购号
            data.put("purchaseId", purch.getAgentId()); // 采购经办人ID
            data.put("purchaseName", purch.getAgentName()); // 采购经办人名称
            data.put("supplierId", purch.getSupplierId()); // 供应商ID
            data.put("supplierName", purch.getSupplierName()); // 供应商名称
            data.put("department", purch.getDepartment()); // 下发部门
            data.put("currencyBn", purch.getCurrencyBn()); // 币种

            List<PurchGoods> purchGoodsList = purch.getPurchGoodsList();

            if (purchGoodsList.stream().anyMatch(purchGoods -> {
                return purchGoods.getPreInspectNum() < purchGoods.getPurchaseNum();
            })) {
                data.put("inspected", Boolean.FALSE);
            } else {
                data.put("inspected", Boolean.TRUE);
            }
            if (!(Boolean) data.get("inspected")) {
                List<Map<String, Object>> list = purchGoodsList.stream().filter(vo -> {
                    // 只要已报检数量小于采购数量的商品显示
                    return vo.getInspectNum() < vo.getPurchaseNum();
                }).map(vo -> {
                    Map<String, Object> map = new HashMap<>();
                    Goods goods = vo.getGoods();
                    Order order = goods.getOrder();
                    map.put("id", goods.getId());
                    map.put("purchGid", vo.getId());
                    map.put("contractNo", goods.getContractNo());
                    map.put("projectNo", goods.getProjectNo());
                    map.put("sku", goods.getSku());
                    map.put("proType", goods.getProType());
                    map.put("nameEn", goods.getNameEn());
                    map.put("nameZh", goods.getNameZh());
                    map.put("brand", goods.getBrand());
                    map.put("model", goods.getModel());
                    map.put("execCoName", order.getExecCoName()); // 执行分公司
                    map.put("purchaseNum", vo.getPurchaseNum());
                    map.put("hasInspectNum", vo.getPreInspectNum());
                    map.put("inspectNum", vo.getPurchaseNum() - vo.getPreInspectNum()); // 报检数量
                    map.put("unit", goods.getUnit());
                    map.put("nonTaxPrice", vo.getNonTaxPrice());
                    map.put("taxPrice", vo.getTaxPrice());
                    map.put("purchasePrice", vo.getPurchasePrice());
                    map.put("totalPrice", vo.getTotalPrice());
                    map.put("purchaseRemark", vo.getPurchaseRemark());
                    return map;
                }).collect(Collectors.toList());
                data.put("goodsList", list);
            }

            return new Result<>(data);
        }

        return new Result<>(ResultStatusEnum.FAIL);
    }


}
