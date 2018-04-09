package com.erui.order.service;


import com.erui.order.entity.Iogistics;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface IogisticsService {


    Page<Iogistics> queIogistics(Iogistics iogistics) throws Exception;

    Iogistics queryById(Iogistics iogistics) throws Exception;

    boolean mergeData(Map<String, String> params) throws Exception;
}
