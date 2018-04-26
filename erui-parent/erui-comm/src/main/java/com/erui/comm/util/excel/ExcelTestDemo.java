package com.erui.comm.util.excel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelTestDemo {
	
	
	public static void main(String[] args) throws SQLException {
		// 准备数据容器
		String[] header = new String[] { "订单号", "卖家账号", "店铺名称"};
		List<Object[]> datas = new ArrayList<Object[]>();
		

		Object[] rowData = new Object[header.length];
		rowData[0] = 1;
		rowData[1] = "string";
		rowData[2] = new Date();
		datas.add(rowData);


		BuildExcel buildExcel = new BuildExcelImpl();


		HSSFWorkbook workbook = buildExcel.buildExcel(datas, header, null,
				"test");
		ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
		ExcelCustomStyle.setContextStyle(workbook, 0, 1, -1);
		// 如果要加入标题
		ExcelCustomStyle.insertRow(workbook, 0, 0 , 1);
		ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "提现审核列表");
		FileOutputStream out = null;
		try {
			HSSFSheet sheet = workbook.getSheetAt(0);
			int lastRowNum = sheet.getLastRowNum();
			System.out.println(lastRowNum);
			out = new FileOutputStream("/Users/wangxiaodan/tmp/abc.xls");
			workbook.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}


	/**
	 * 如果是下载则设置response，并写入response的outputstream中就OK
	 */
	public void webDownExcel() {
/*
		//转换成excel工具类需要的数据格式
		HSSFWorkbook workbook = generateQueryListExcel(cashList);
		// 设置样式
		ExcelCustomStyle.setHeadStyle(workbook , 0 , 0 );
		ExcelCustomStyle.setContextStyle(workbook, 0, 1, -1);
		ExcelCustomStyle.insertRow(workbook, 0, 0 , 1);
		ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "提现审核列表");
		OutputStream out = null;
		try {
			response.setContentType("application/ms-excel;charset=UTF-8");
			String fn = new String(("提现审核列表_" + dt).getBytes("GBK"), "ISO-8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fn + ".xls");
			out = response.getOutputStream();
			if (workbook != null) {
				workbook.write(out);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}*/
	}
}
