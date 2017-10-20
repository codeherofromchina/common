package com.erui.boss.web.report;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erui.boss.web.report.vo.Student;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.EmployeeService;
import com.erui.report.service.InquiryCountService;

@Controller
@RequestMapping("/abc")
public class EmployeeController {
	private final static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private Student student;
	@Autowired
	private InquiryCountService inquiryCountService;

	@RequestMapping(value = "/name", method = RequestMethod.GET)
	@ResponseBody
	public Object name(@RequestParam("empId") Long empId) {
		logger.info("日志使用");

		Map<String, Object> result = new HashMap<>();
		String name = employeeService.getName(empId);
		logger.info("name is {}", name);
		result.put("name", name);
		result.put("name2", student.getName());
		return result;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public Object test() throws ParseException {
		Date date = new Date();
		Date parseStringToDate = DateUtil.parseStringToDate("2016-10-10", "yyyy-MM-dd");
		double totalPrice = inquiryCountService.inquiryAmountByTime(parseStringToDate, date);
		Map<String,Object> result = new HashMap<>();
		result.put("to", totalPrice);
		return result;
	}

	@RequestMapping(value = "/updateName", method = RequestMethod.GET)
	@ResponseBody
	public Object updateName(@RequestParam(value = "empId", required = true) Long empId,
			@RequestParam(value = "name", required = true) String name) {
		logger.info("更改员工名称empId:{},name:{}", empId, name);

		Map<String, Object> result = new HashMap<>();
		boolean flag = false;
		try {
			flag = employeeService.updateName(empId, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("员工名称修改结果:{}", flag);
		result.put("success", flag);
		result.put("name", name);
		return result;
	}

}
