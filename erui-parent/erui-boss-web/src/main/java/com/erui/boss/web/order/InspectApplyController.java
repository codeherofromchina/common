package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.order.entity.*;
import com.erui.order.service.InspectApplyService;
import com.erui.order.service.PurchContractService;
import com.erui.order.service.PurchService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 报检单控制器
 * Created by wangxiaodan on 2017/12/13.
 */
@RestController
@RequestMapping("order/inspectApply")
public class InspectApplyController {
    private final static Logger logger = LoggerFactory.getLogger(InspectApplyController.class);

    @Autowired
    private InspectApplyService inspectApplyService;
    @Autowired
    private PurchService purchService;
    @Autowired
    private PurchContractService purchContractService;

    /**
     * 获取采购纬度的报检单信息列表
     *
     * @param params {"purchId":"采购ID"}
     * @return
     */
    @RequestMapping("listByParch")
    public Result<Object> listByParch(@RequestBody Map<String, String> params) {
        String purchId = params.get("purchId");
        String[] purchIds = purchId.split(",");
        List<Integer> purchIdList = new ArrayList<>();
        for (int i = 0; i < purchIds.length; i++) {
            purchIdList.add(new Integer(purchIds[i]));
        }
        int size = purchIdList.size();
        Integer[] integers = purchIdList.toArray(new Integer[size]);
        if (purchId == null) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg("采购主键为空");
        }
        List<InspectApply> inspectApplyList = inspectApplyService.findMasterListByPurchaseId(integers);
        if (inspectApplyList != null) {
            // 转换数据
            List<Map<String, Object>> data = inspectApplyList.stream().map(vo -> {
                return coverInspectApply2Map(vo);
            }).collect(Collectors.toList());
            return new Result<>(data);
        }
        return new Result<>(ResultStatusEnum.FAIL);
    }


    /**
     * 查看质检结果
     *
     * @param params {"id":"报检单ID"}
     * @return
     */
    @RequestMapping("reportResult")
    public Result<Object> reportResult(@RequestBody Map<String, Integer> params) {
        Integer id = params.get("id");
        if (id == null) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg("缺少参数");
        }
        InspectApply inspectApply = inspectApplyService.findById(id);
        if (inspectApply == null) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg("不存在的报检单");
        } else if (!inspectApply.isMaster()) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg("只接受主报检单");
        }
        if (inspectApply.getStatus() == InspectApply.StatusEnum.UNQUALIFIED.getCode() && inspectApply.getPubStatus() == InspectApply.StatusEnum.UNQUALIFIED.getCode()) {
            if (inspectApply.isHistory()) {
                // 返回子报检失败信息重新质检
                inspectApply = inspectApplyService.findSonFailDetail(id);
            } else {
                // 返回本身重新质检
                inspectApply = inspectApplyService.findDetail(id);
            }
            // 查看是否修改过整改意见
            if (inspectApply.getTmpMsg() != null) {
                inspectApply.setAttachmentList(inspectApplyService.findTmpAttachmentByInspectApplyId(inspectApply.getId()));
            }
            return new Result<>(handleApply(inspectApply));
        }
        return new Result<>(ResultStatusEnum.FAIL).setMsg("质检结果未出");
    }


    private Map<String, Object> handleApply(InspectApply inspectApply) {
        // 数据转换
        Map<String, Object> data = new HashMap<>();
        data.put("id", inspectApply.getId());
        data.put("purchNo", inspectApply.getPurchNo());
        data.put("department", inspectApply.getDepartment());
        data.put("purchaseName", inspectApply.getPurchaseName());
        data.put("supplierName", inspectApply.getSupplierName());
        data.put("abroadCoName", inspectApply.getAbroadCoName());
        data.put("inspectDate", inspectApply.getInspectDate());
        data.put("direct", inspectApply.getDirect());
        data.put("outCheck", inspectApply.getOutCheck());
        data.put("msg", inspectApply.getMsg());
        data.put("tmpMsg", inspectApply.getTmpMsg());
        data.put("inspectDate", inspectApply.getInspectDate());
        data.put("remark", inspectApply.getRemark());
        data.put("attachmentList", inspectApply.getAttachmentList());
        data.put("status", inspectApply.getStatus());
        data.put("pubStatus", inspectApply.getPubStatus());

        List<Map<String, Object>> inspectApplyGoodsList = new ArrayList<>();
        for (InspectApplyGoods vo : inspectApply.getInspectApplyGoodsList()) {
            Goods goods = vo.getGoods();
            Order order = goods.getOrder();
            PurchGoods purchGoods = vo.getPurchGoods();
            Map<String, Object> map = new HashMap<>();
            map.put("id", vo.getId());
            map.put("purchGid", purchGoods.getId());
            map.put("gid", goods.getId());
            map.put("contractNo", goods.getContractNo());
            map.put("projectNo", goods.getProjectNo());
            map.put("sku", goods.getSku());
            map.put("proType", goods.getProType());
            map.put("nameEn", goods.getNameEn());
            map.put("nameZh", goods.getNameZh());
            map.put("brand", goods.getBrand());
            map.put("model", goods.getModel());
            map.put("execCoName", order.getExecCoName()); // 执行分公司
            map.put("purchaseNum", purchGoods.getPurchaseNum());
            map.put("hasInspectNum", purchGoods.getPreInspectNum()); // 已报检数量
            map.put("inspectNum", vo.getInspectNum()); // 报检数量
            if (inspectApply.getStatus() > InspectApply.StatusEnum.SUBMITED.getCode()) {
                // 质检中的数据
                map.put("samples", vo.getSamples()); // 抽样数
                map.put("unqualified", vo.getUnqualified()); // 不合格数
                map.put("unqualifiedDesc", vo.getUnqualifiedDesc()); // 不合格描述
                map.put("conclusion", vo.getUnqualified() > 0 ? "不合格" : "合格"); // 商品质检结果结论
            }
            map.put("unit", goods.getUnit());
            map.put("nonTaxPrice", purchGoods.getNonTaxPrice());
            map.put("taxPrice", purchGoods.getTaxPrice());
            map.put("purchasePrice", purchGoods.getPurchasePrice());
            map.put("totalPrice", purchGoods.getPurchasePrice().multiply(new BigDecimal(vo.getInspectNum().intValue())));
            map.put("height", vo.getHeight());
            map.put("lwh", vo.getLwh());
            map.put("purchaseRemark", purchGoods.getPurchaseRemark());
            inspectApplyGoodsList.add(map);
        }
        data.put("inspectApplyGoodsList", inspectApplyGoodsList);
        return data;
    }


    /**
     * 获取报检单信息详情
     *
     * @param params {"id":"报检单ID"}
     * @return
     */
    @RequestMapping("detail")
    public Result<Object> detail(@RequestBody Map<String, Integer> params) {
        Integer id = params.get("id");
        if (id == null) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg("缺少参数");
        }
        InspectApply inspectApply = inspectApplyService.findDetail(id);
        if (inspectApply != null) {
            // 数据转换
            return new Result<>(handleApply(inspectApply));
        }
        return new Result<>(ResultStatusEnum.FAIL).setMsg("不存在的报检单");
    }

    /**
     * 到货报检单历史记录
     *
     * @param inspectApply 报检单ID
     * @return
     */
    @RequestMapping("history")
    public Result<Object> history(@RequestBody InspectApply inspectApply) {
        if (inspectApply == null || inspectApply.getId() == null) {
            return new Result<>(ResultStatusEnum.FAIL);
        }
        // 查询多次相同报检提交的报检单
        InspectApply masterInspectApply = inspectApplyService.findById(inspectApply.getId());
        if (masterInspectApply != null && masterInspectApply.isMaster()) {
            List<InspectApply> list = inspectApplyService.findByParentId(masterInspectApply.getId());
            // 在历史报检单列表中，num为第几次报检，所以设置主报检单为第一次
            masterInspectApply.setNum(1);
            list.add(0, masterInspectApply); // 在最后添加主报检单
            // 处理为页面需要数据
            List<Map<String, Object>> data = list.parallelStream().map(vo -> {
                return coverInspectApply2Map(vo);
            }).collect(Collectors.toList());

            return new Result<>(data);
        }
        return new Result<>(ResultStatusEnum.FAIL);
    }


    private Map<String, Object> coverInspectApply2Map(InspectApply inspectApply) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", inspectApply.getId()); // 报检单ID
        map.put("inspectApplyNo", inspectApply.getInspectApplyNo()); // 到货报检单号
        map.put("purchNo", inspectApply.getPurchNo()); // 采购合同号
        map.put("department", inspectApply.getDepartment()); // 下发部门
        map.put("purchaseName", inspectApply.getPurchaseName()); // 采购经办人
        map.put("supplierName", inspectApply.getSupplierName()); // 供应商名称
        map.put("inspectDate", inspectApply.getInspectDate()); // 报检日期
        map.put("num", inspectApply.getNum()); //报检次数
        map.put("status", inspectApply.getStatus()); // 质检结果
        map.put("pubStatus", inspectApply.getPubStatus()); // 全局质检结果
        map.put("history", inspectApply.isHistory()); // 是否存在历史记录
        return map;
    }


    /**
     * 完善报检单的整改意见
     *
     * @param inspectApply 主要参数 id、msg、attachmentList
     * @return
     */
    @RequestMapping(value = "fullMsg", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> fullMsg(@RequestBody InspectApply inspectApply) {
        Integer id = inspectApply.getId();
        InspectApply dbIA = null;
        if (id != null) {
            dbIA = inspectApplyService.findById(id);
        }
        if (dbIA == null || dbIA.getStatus() != InspectApply.StatusEnum.UNQUALIFIED.getCode()) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg("报检单信息不存在或状态错误");
        }
        if (StringUtils.isBlank(inspectApply.getMsg())) {
            return new Result<>(ResultStatusEnum.FAIL).setMsg("整改意见不可空");
        }
        inspectApplyService.fullTmpMsg(inspectApply);


        return new Result<>();
    }


    /**
     * 新增/更新到货报检单
     *
     * @param inspectApply
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> save(@RequestBody InspectApply inspectApply, HttpServletRequest request) {
        InspectApply.StatusEnum statusEnum = InspectApply.StatusEnum.fromCode(inspectApply.getStatus());
        boolean continueFlag = true;
        String errMsg = null;
        // 必须是保存、提交、重新报检的一种，这里将NO_EDIT设置为重新报检类型复用
        if (statusEnum == null || (statusEnum != InspectApply.StatusEnum.SAVED && statusEnum != InspectApply.StatusEnum.SUBMITED && statusEnum != InspectApply.StatusEnum.NO_EDIT)) {
            continueFlag = false;
            errMsg = "状态提交错误";
        }
        // 如果不是重新报检，则必须存在要报检的商品列表
        if (statusEnum != InspectApply.StatusEnum.NO_EDIT && (inspectApply.getInspectApplyGoodsList() == null || inspectApply.getInspectApplyGoodsList().size() <= 0)) {
            continueFlag = false;
            errMsg = "无商品信息";
        }


        if (continueFlag) {
            try {
                String eruiToken = CookiesUtil.getEruiToken(request);
                ThreadLocalUtil.setObject(eruiToken);
                boolean flag;
                if (statusEnum != InspectApply.StatusEnum.NO_EDIT) {
                    if (inspectApply.getId() != null) {
                        flag = inspectApplyService.save(inspectApply);
                    } else {
                        // 插入新报检单操作
                        Integer purchId = inspectApply.getpId();
                        if (purchId == null || purchId <= 0) {
                            flag = false;
                            errMsg = "采购ID参数不能为空";
                        } else {
                            flag = inspectApplyService.insert(inspectApply);
                        }
                    }
                } else {
                    // 此处将NO_EDIT临时使用为重新报检状态
                    // 重新报检
                    flag = inspectApplyService.againApply(inspectApply);
                }
                if (flag) {
                    return new Result<>();
                }
            } catch (Exception ex) {
                errMsg = ex.getMessage();
                logger.error("异常报错", ex);
            }
        }
        //当所有采购合同的采购订单完成时设置采购合同状态为已完成
        if (StringUtils.isNotBlank(inspectApply.getPurchNo())) {
            List<Purch> byPurchNo = purchService.findByPurchNo(inspectApply.getPurchNo());
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
                            purchContract.setStatus(4);
                            purchContractService.updateStatus(purchContract);
                        }
                    }
                }
            }
        }
        return new Result<>(ResultStatusEnum.FAIL).setMsg(errMsg);
    }

}
