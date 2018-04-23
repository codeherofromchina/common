package com.erui.report.service.impl;

import com.erui.comm.util.data.string.StringUtil;
import com.erui.report.dao.InqRtnReasonMapper;
import com.erui.report.model.InqRtnReasonExample;
import com.erui.report.service.InqRtnReasonService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class InqRtnReasonServiceImpl extends  BaseService<InqRtnReasonMapper> implements InqRtnReasonService {

    @Override
    public Map<String, Object> selectCountGroupByRtnSeason(Map<String, Object> params) {
        return readMapper.selectCountGroupByRtnSeason(params);
    }



    @Override
    public List<Map<String, Object>> selectCountGroupByRtnSeasonAndArea(Map<String, Object> params) {
        return readMapper.selectCountGroupByRtnSeasonAndArea(params);
    }

    @Override
    public List<Map<String, Object>> selectCountGroupByRtnSeasonAndOrg(Map<String, Object> params) {
        return  readMapper.selectCountGroupByRtnSeasonAndOrg(params);
    }
}
