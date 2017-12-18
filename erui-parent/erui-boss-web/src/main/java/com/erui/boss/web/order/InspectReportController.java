package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.Goods;
import com.erui.order.entity.InspectApply;
import com.erui.order.entity.InspectApplyGoods;
import com.erui.order.entity.InspectReport;
import com.erui.order.requestVo.InspectReportVo;
import com.erui.order.requestVo.PGoods;
import com.erui.order.service.InspectReportService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    /**
     * 查看质检单详情信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public Result<Object> detail(@RequestParam(name = "id") Integer id) {
        InspectReport inspectReport = inspectReportService.detail(id);
        if (inspectReport == null) {
            return new Result<>(ResultStatusEnum.FAIL);
        }

        InspectReportVo data = new InspectReportVo();
        // 基本信息
        data.setId(inspectReport.getId());
        data.setCheckUserId(inspectReport.getCheckUserId());
        data.setCheckDeptId(inspectReport.getCheckDeptId());
        data.setReportRemarks(inspectReport.getReportRemarks());

        // 商品信息
        List<InspectApplyGoods> inspectApplyGoodsList = inspectReport.getInspectApply().getInspectApplyGoodsList();
        List<PGoods> goodsList = inspectApplyGoodsList.stream().map(vo -> {
            Goods goods = vo.getGoods();
            PGoods pGoods = new PGoods();
            pGoods.setId(vo.getId());
            pGoods.setGoodsId(goods.getId());
            pGoods.setSku(goods.getSku());
            pGoods.setInspectNum(vo.getInspectNum());
            pGoods.setSamples(vo.getSamples());
            pGoods.setUnqualified(vo.getUnqualified());
            pGoods.setUnqualifiedDesc(vo.getUnqualifiedDesc());
            return pGoods;
        }).collect(Collectors.toList());
        data.setpGoodsList(goodsList);

        // 附件信息
        data.setAttachments(inspectReport.getAttachmentSet());


        return new Result<>(data);
    }

    /**
     * 获取质检报告单列表
     *
     * @param {"报检单编号":"报检单编号"，"supplierName"："供应商名称"}
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> list(@RequestBody InspectReportVo condition) {


        Page<InspectReport> page = inspectReportService.listByPage(condition);

        // 数据转换
        List<InspectReportVo> newContentList = null;
        if (page.hasContent()) {
            List<InspectReport> content = page.getContent();
            newContentList = content.stream().map(vo -> {
                InspectReportVo inv = new InspectReportVo();
                inv.setId(vo.getId());
                inv.setCheckUserId(vo.getCheckUserId());

                return inv;
            }).collect(Collectors.toList());
        } else {
            newContentList = new ArrayList<>();
        }

        Page<InspectReportVo> data = new PageImpl<InspectReportVo>(newContentList, new PageRequest(page.getNumber(), page.getNumberOfElements()), page.getTotalElements());


        return new Result<>(data);
    }


    /**
     * 保存质检单
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> save(@RequestBody InspectReportVo params) {

        // TODO 验证参数需完善
        try {
            boolean flag = inspectReportService.save(params);
            if (flag) {
                return new Result<>(ResultStatusEnum.FAIL);
            }
        } catch (Exception e) {
            logger.error("异常错误", e);
        }

        return new Result<>(ResultStatusEnum.FAIL);
    }


    /**
     * 保存质检单
     *
     * @param id 质检单ID
     * @return
     */
    @RequestMapping(value = "history", method = RequestMethod.POST)
    public Result<Object> history(@RequestParam(name = "id") Integer id) {

        List<InspectReport> list = inspectReportService.history(id);

        List<InspectReportVo> data = list.stream().map(vo -> {
            InspectApply inspectApply = vo.getInspectApply();
            InspectReportVo inv = new InspectReportVo();
            inv.setId(vo.getId());
            inv.setCheckUserId(vo.getCheckUserId());
            inv.setCheckDeptId(vo.getCheckDeptId());
            inv.setApplyDate(inspectApply.getInspectDate());
            int status = vo.getStatus();
            inv.setStatus(status);
            if (status == 2) { // 质检完成后才有是否合格的状态
                inv.setUnqualifiedFlag(inspectApply.getStatus() == 4);
            }

            return inv;
        }).collect(Collectors.toList());

        return new Result<>(data);
    }

}
