package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.order.entity.InspectReport;
import com.erui.order.requestVo.InspectReportVo;
import com.erui.order.service.InspectReportService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private InspectReportService inspectReportService;


    /**
     * 获取质检报告单列表
     * @param  {"报检单编号":"报检单编号"，"supplierName"："供应商名称"}
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> list(@RequestBody InspectReportVo condition) {


        Page<InspectReport> page = inspectReportService.listByPage(condition);

        // TODO 待转换数据



        return new Result<>(page);
    }


    /**
     * 保存质检单
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> save(@RequestBody Map<String, Object> params) {


        return new Result<>();
    }

}
