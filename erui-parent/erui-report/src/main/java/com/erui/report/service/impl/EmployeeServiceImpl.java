package com.erui.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.erui.report.dao.EmployeeMapper;
import com.erui.report.model.Employee;
import com.erui.report.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeMapper employeeMapper;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String getName(Long empId) {
		Employee emp = employeeMapper.selectByPrimaryKey(empId);
		return emp.getName();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Employee findById(Long empId) {
		return employeeMapper.selectByPrimaryKey(empId);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public boolean updateName(Long empId, String name) throws Exception {
		Employee emp = findById(empId);
		if (emp != null) {
			emp.setName(name);
			int updateInt = employeeMapper.updateByPrimaryKeySelective(emp);
			if (true) {
				throw new Exception("回滚");
			}
			return updateInt > 0;
		}
		return false;
	}
}
