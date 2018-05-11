package com.erui.power.service.impl;

import com.erui.power.dao.FuncPermMapper;
import com.erui.power.model.FuncPerm;
import com.erui.power.service.FuncPermService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncPermServiceImpl extends BaseService<FuncPermMapper> implements FuncPermService {

    @Override
    public List<FuncPerm> findByEmployee(Integer userId) {
        return readMapper.selectByEmployeeId(userId);
    }

    @Override
    public List<FuncPerm> findAll() {
        return readMapper.selectByExample(null);
    }
}
