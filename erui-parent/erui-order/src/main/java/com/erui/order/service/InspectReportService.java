package com.erui.order.service;

import com.erui.order.entity.Area;
import com.erui.order.entity.InspectReport;
import com.erui.order.entity.Purch;
import com.erui.order.requestVo.InspectReportVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface InspectReportService {
    /**
     * 根据id查询地区信息
     * @param id
     * @return
     */
    InspectReport findById(Integer id);

    /**
     * 根据条件分页查找质检单
     *  {}
     * @param condition
     * @return
     */
    Page<InspectReport> listByPage(InspectReportVo condition);
}
