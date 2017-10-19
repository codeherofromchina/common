package com.erui.report.service.impl;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.erui.report.dao.EmployeeMapper;
import com.erui.report.model.Employee;
import com.erui.report.service.EmployeeService;

@Service
public class EmployeeServiceImpl extends BaseService<EmployeeMapper> implements EmployeeService {
//	private EmployeeMapper writeMapper;
//	private EmployeeMapper readMapper;
//
//	@Resource(name = "sqlSessionMaster")
//	public void setWriteMapper(SqlSessionTemplate sqlSession) {
//		this.writeMapper = sqlSession.getMapper(EmployeeMapper.class);
//	}
//	@Resource(name = "sqlSessionSlave")
//	public void setReadMapper(SqlSessionTemplate sqlSession) {
//		this.readMapper = sqlSession.getMapper(EmployeeMapper.class);
//	}




	public String getName(Long empId) {
		Employee emp = readMapper.selectByPrimaryKey(empId);
		return emp.getName();
	}


	public Employee findById(Long empId) {
		return readMapper.selectByPrimaryKey(empId);
	}


	public boolean updateName(Long empId, String name) throws Exception {

		Employee emp = findById(empId);
		if (emp != null) {
			emp.setName(name);
			int updateInt = writeMapper.updateByPrimaryKeySelective(emp);
			if (true) {
				//throw new Exception("回滚");
			}
			return updateInt > 0;
		}
		return false;
	}


}
