package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.order.entity.*;
import com.erui.order.service.InspectReportService;
import com.erui.order.service.PurchContractService;
import com.erui.order.service.PurchService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 质检报告控制器
 * Created by wangxiaodan on 2017/12/13.
 */
@RestController
@RequestMapping("order/inspectReport")
public class InspectReportController {
    private final static Logger logger = LoggerFactory.getLogger(InspectApplyController.class);

    @Autowired
    private InspectReportService inspectReportService;
    @Autowired
    private PurchService purchService;
    @Autowired
    private PurchContractService purchContractService;

    /**
     * 获取质检报告单列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> list(@RequestBody InspectReport condition) {

        Page<InspectReport> page = inspectReportService.listByPage(condition);
        if (page.hasContent()) {
            // 转换数据
            page.getContent().parallelStream().forEach(inspectReport -> {
                InspectApply inspectApply = inspectReport.getInspectApply();
                inspectReport.setPurchNo(inspectApply.getPurchNo());
                inspectReport.setInspectDate(inspectApply.getInspectDate());
                if (inspectReport.getLastDoneDate() != null) {
                    inspectReport.setDoneDate(inspectReport.getLastDoneDate()); // 兼容前端字段，使用doneDate
                }
                inspectReport.setDirect(inspectApply.getDirect());
                inspectReport.setAttachments(null);
                inspectReport.setInspectGoodsList(null);
            });
        }
        return new Result<>(page);
    }


    /**
     * 查看质检单详情信息
     *
     * @param params {"id":"质检单ID"}
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> detail(@RequestBody Map<String, Integer> params) {
        Integer id = params.get("id");
        if (id == null || id <= 0) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }


        InspectReport inspectReport = inspectReportService.detail(id);
        if (inspectReport == null) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        // 数据转换
        Map<String, Object> data = new HashMap<>();
        data.put("id", inspectReport.getId());
        // 检验员
        data.put("checkUserId", inspectReport.getCheckUserId());
        data.put("checkUserName", inspectReport.getCheckUserName());
        // 质检部门
        data.put("checkDeptId", inspectReport.getCheckDeptId());
        data.put("checkDeptName", inspectReport.getCheckDeptName());
        // NCR编号
        data.put("ncrNo", inspectReport.getNcrNo());
        // 检验日期
        data.put("checkDate", inspectReport.getCheckDate());
        // 检验完成日期
        data.put("doneDate", inspectReport.getDoneDate());
        // 备注
        data.put("reportRemarks", inspectReport.getReportRemarks());
        // 整改意见
        data.put("msg", inspectReport.getMsg());
        // 采购号
        data.put("purchNo", inspectReport.getPurchNo());
        // 附件
        data.put("attachments", inspectReport.getAttachments());
        // 商品列表信息
        List<InspectApplyGoods> inspectGoodsList = inspectReport.getInspectGoodsList();
        List<Map<String, Object>> goodsList = inspectGoodsList.stream().map(vo -> {
            Goods goods = vo.getGoods();

            Map<String, Object> map = new HashedMap();
            map.put("id", vo.getId());
            map.put("gId", goods.getId());
            map.put("sku", goods.getSku());
            map.put("purchNo", inspectReport.getPurchNo());
            map.put("contractNo", goods.getContractNo());
            map.put("projectNo", goods.getProjectNo());
            map.put("proType", goods.getProType());
            map.put("nameEn", goods.getNameEn());
            map.put("nameZh", goods.getNameZh());
            map.put("model", goods.getModel());
            map.put("inspectNum", vo.getInspectNum());
            map.put("samples", vo.getSamples());
            map.put("unqualified", vo.getUnqualified());
            map.put("unqualifiedDesc", vo.getUnqualifiedDesc()); // 不合格描述
            map.put("unqualifiedType", vo.getUnqualifiedType()); // 不合格类型
            map.put("qualityInspectType", vo.getQualityInspectType()); // 质量检验类型
            map.put("checkResult", vo.getUnqualified() == 0);
            map.put("qualityInspectType", vo.getQualityInspectType()); // 质检类型
            return map;
        }).collect(Collectors.toList());
        data.put("inspectGoodsList", goodsList);


        return new Result<>(data);
    }


    /**
     * 质检单历史记录
     *
     * @param params {"id":质检单ID}
     * @return
     */
    @RequestMapping(value = "history", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> history(@RequestBody Map<String, Integer> params) {
        Integer id = params.get("id");
        if (id == null || id <= 0) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }

        List<InspectReport> list = inspectReportService.history(id);
        if (list == null) {
            return new Result<>(ResultStatusEnum.FAIL);
        }

        List<Map<String, Object>> data = list.stream().map(vo -> {
            Map<String, Object> map = new HashMap<>();
            InspectApply inspectApply = vo.getInspectApply();
            Purch purch = inspectApply.getPurch();

            map.put("id", vo.getId());
            map.put("inspectApplyNo", vo.getInspectApplyNo());
            map.put("purchNo", inspectApply.getPurchNo());
            map.put("agentName", purch.getAgentName());
            map.put("supplierName", purch.getSupplierName());
            map.put("inspectDate", inspectApply.getInspectDate());
            map.put("department", inspectApply.getDepartment());
            map.put("checkUserId", vo.getCheckUserId());
            InspectApply.StatusEnum statusEnum = InspectApply.StatusEnum.fromCode(inspectApply.getStatus());
            String checkStatus = null;
            if (statusEnum == InspectApply.StatusEnum.QUALIFIED) {
                checkStatus = "通过";
            } else if (statusEnum == InspectApply.StatusEnum.UNQUALIFIED) {
                checkStatus = "未通过";
            }
            map.put("checkStatus", checkStatus);
            map.put("status", inspectApply.getStatus());

            return map;
        }).collect(Collectors.toList());

        return new Result<>(data);
    }


    /**
     * 保存质检单
     *
     * @param inspectReport
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> save(@RequestBody InspectReport inspectReport, HttpServletRequest request) {
        String errorMsg = null;
        try {
            String eruiToken = CookiesUtil.getEruiToken(request);
            ThreadLocalUtil.setObject(eruiToken);
            //当所有采购合同的采购订单完成时设置采购合同状态为已完成
            if (inspectReportService.save(inspectReport)) {
                InspectReport detail = inspectReportService.detail(inspectReport.getId());
                if (StringUtils.isNotBlank(detail.getPurchNo())) {
                    List<Purch> byPurchNo = purchService.findByPurchNo(detail.getPurchNo());
                    boolean doneFlag = true;
                    if (byPurchNo != null && byPurchNo.size() > 0) {
                        for (Purch purch : byPurchNo) {
                            if (purch.getStatus() != 3) {
                                doneFlag = false;
                                break;
                            }
                        }
                        if (doneFlag) {
                            if (byPurchNo != null && byPurchNo.size() > 0) {
                                Integer purchContractId = byPurchNo.get(0).getPurchContractId() == null ? 0 : byPurchNo.get(0).getPurchContractId();
                                if (purchContractId > 0) {
                                    PurchContract purchContract = purchContractService.findDetailInfo(purchContractId);
                                    purchContractService.updateStatus(purchContract);
                                }
                            }
                        }
                    }
                }
                return new Result<>();
            }
        } catch (Exception e) {
            errorMsg = e.getMessage();
            logger.error("异常错误", e);
        }

        return new Result<>(ResultStatusEnum.FAIL).setMsg(errorMsg);
    }


}
