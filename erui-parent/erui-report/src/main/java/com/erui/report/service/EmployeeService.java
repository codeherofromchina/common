package com.erui.report.service;

public interface EmployeeService {

	/**
	 * 获取员工姓名
	 * 
	 * @param empId
	 * @return
	 */
	public String getName(Long empId);

	/**
	 * 更新员工姓名
	 * 
	 * @param empId
	 * @param name
	 * @return
	 */
	public boolean updateName(Long empId, String name) throws Exception;
}
