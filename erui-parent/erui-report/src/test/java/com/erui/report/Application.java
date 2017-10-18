package com.erui.report;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.erui.report.service.EmployeeService;
import com.erui.report.service.impl.EmployeeServiceImpl;

public class Application {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-report.xml");
		EmployeeService bean = applicationContext.getBean(EmployeeService.class);
		System.out.println(bean.getName(26361L));
	}
}
