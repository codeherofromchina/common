package com.erui.order.v2.service.impl;

import com.erui.order.v2.dao.EmployeeMapper;
import com.erui.order.v2.model.Employee;
import com.erui.order.v2.model.EmployeeExample;
import com.erui.order.v2.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangxiaodan on 2018/5/7.
 */
@Service("employeeServiceImplV2")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public Long findIdByUserNo(String userNo) {
        if (StringUtils.isBlank(userNo)) {
            return null;
        }
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andUserNoEqualTo(userNo).andDeletedFlagEqualTo("N")
                .andStatusEqualTo("NORMAL");

        List<Employee> employees = employeeMapper.selectByExample(example);
        if (employees != null && employees.size() > 0) {
            return employees.get(0).getId();
        }
        return null;
    }


    @Override
    public String findUserNoById(Long id) {
        if (id != null) {
            Employee employee = employeeMapper.selectByPrimaryKey(id);
            if (employee != null) {
                return employee.getUserNo();
            }
        }
        return null;
    }
}
