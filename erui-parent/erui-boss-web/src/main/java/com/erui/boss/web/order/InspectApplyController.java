package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.order.entity.InspectApply;
import com.erui.order.entity.Purch;
import com.erui.order.requestVo.InspectApplySaveVo;
import com.erui.order.requestVo.PGoods;
import com.erui.order.service.InspectApplyService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 质检申请单控制器
 * Created by wangxiaodan on 2017/12/13.
 */
@RestController
@RequestMapping("order/inspectApply")
public class InspectApplyController {
    private final static Logger logger = LoggerFactory.getLogger(InspectApplyController.class);

    @Autowired
    private InspectApplyService inspectApplyService;

    /**
     * 获取报检单信息详情
     *
     * @param id 质检申请ID
     * @return
     */
    @RequestMapping("get")
    public Result<Object> get(@RequestParam(name = "id", required = true) Integer id) {
        InspectApply inspectApply = inspectApplyService.findDetail(id);

        if (inspectApply != null) {
            InspectApplySaveVo data = new InspectApplySaveVo();
            data.setId(inspectApply.getId());
            data.setPurchId(inspectApply.getPurch().getId());
            data.setDepartment(inspectApply.getDepartment());
            data.setPurchaseName(inspectApply.getPurchaseName());
            data.setSupplierName(inspectApply.getSupplierName());
            data.setAbroadCoName(inspectApply.getAbroadCoName());
            data.setInspectDate(inspectApply.getInspectDate());
            data.setDirect(inspectApply.getDirect());
            data.setOutCheck(inspectApply.getOutCheck());
            data.setRemark(inspectApply.getRemark());
            data.setMsg(inspectApply.getMsg());
            data.setStatus(inspectApply.getStatus());


            data.setAttachmentList(inspectApply.getAttachmentList());

            List<PGoods> goodsList = new ArrayList<>();
            data.setGoodsList(goodsList);


            return new Result<>(data);
        }
        return new Result<>(ResultStatusEnum.FAIL);
    }

    /**
     * 到货报检单历史记录
     *
     * @param id 报检单ID
     * @return
     */
    @RequestMapping("history")
    public Result<Object> history(@RequestParam(name = "id", required = true) Integer id) {
        // 查询多次相同报检提交的报检单
        List<InspectApply> list = inspectApplyService.findSameApplyList(id);
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            InspectApply inspectApply = list.get(i);

            Map<String, Object> map = new HashedMap();
            map.put("department", inspectApply.getDepartment());
            map.put("purchaseName", inspectApply.getPurchaseName());
            map.put("supplierName", inspectApply.getSupplierName());
            map.put("inspectDate", DateUtil.format("yyyy-MM-dd", inspectApply.getInspectDate()));
            map.put("order", i + 1);
            //  3：合格 4:未合格 其他：无显示
            map.put("result", inspectApply.getStatus());

            data.add(map);
        }

        return new Result<>(data);
    }


    /**
     * 获取采购纬度的质检申请信息列表
     *
     * @param parchId 采购ID
     * @return
     */
    @RequestMapping("listByParch")
    public Result<Object> listByParch(@RequestParam(name = "parchId", required = true) Integer parchId) {

        List<InspectApply> inspectApplyList = inspectApplyService.findListByParchId(parchId);

        List<Map<String, Object>> data = inspectApplyList.stream().map(vo -> {
            Purch purch = vo.getPurch();

            Map<String, Object> map = new HashMap<>();
            map.put("purchNo", purch.getPurchNo()); //采购合同号
            map.put("department", vo.getDepartment()); // 下发部门
            map.put("purchaseName", vo.getPurchaseName()); // 采购经办人
            map.put("supplierName", vo.getSupplierName()); // 供应商名称
            map.put("createTime", DateUtil.format("yyyy-MM-dd", vo.getCreateTime())); // 报检日期
            map.put("applyTimes", vo.getNum()); // 报检次数
            map.put("status", vo.getStatus()); // 报检状态 2：合格 3：未合格 其他忽略
            return map;
        }).collect(Collectors.toList());

        return new Result<>(data);
    }


    /**
     * 新增/更新到货报检单
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> save(@RequestBody InspectApplySaveVo vo) {

        //TODO 参数检查略过

        // 存在两种种情况：一种是保存 ，一种是重新报检不合格商品
        boolean normal = true; // 如果true则是保存操作,false则重新报检
        if (vo.getId() != null && vo.getStatus() == 3) {
            normal = false;
        }

        boolean flag = false;
        try {
            if (normal) {
                flag = inspectApplyService.save(vo);
            } else {
                // 重新报检
                flag = inspectApplyService.againApply(vo);
            }

        } catch (Exception ex) {
            logger.error("异常报错", ex);
        }

        if (!flag) {
            return new Result<>(ResultStatusEnum.FAIL);
        }

        return new Result<>();
    }

}
