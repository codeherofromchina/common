package com.erui.report.service;

import com.erui.report.model.HrCount;

import java.util.List;

/**
 * Created by lirb on 2017/10/19.
 */
public interface HrCountService {
    /**
     * 人力数据列表
     */
    List<HrCount> findAll();
}
