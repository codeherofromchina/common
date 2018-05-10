package com.erui.power.service.impl;

import com.erui.power.model.FuncPerm;
import com.erui.power.service.AuthorityService;
import com.erui.power.service.FuncPermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
    private FuncPermService funcPermService;


    @Override
    public boolean validate(Integer userId, String url) {
        List<FuncPerm> empFuncPerm = funcPermService.findByEmployee(userId);

        boolean matchFlag = empFuncPerm.parallelStream().anyMatch(vo -> url.equals(vo.getUrl()));
        if (matchFlag) {
            return true;
        }


        List<FuncPerm> allFuncPerm = funcPermService.findAll();
        boolean result = allFuncPerm.parallelStream().allMatch(vo -> !url.equals(vo.getUrl()));

        return result;
    }
}
