package com.erui.order.service;

import com.erui.order.entity.Area;
import com.erui.order.entity.InspectReport;

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
}
