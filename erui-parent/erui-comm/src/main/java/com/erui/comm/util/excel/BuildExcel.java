package com.erui.comm.util.excel;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface BuildExcel {
	public HSSFSheet createSheet(HSSFWorkbook hssfworkbook, int page, String sheetname);
	public void createHead(String[] headers, HSSFSheet sheet, HSSFCellStyle style);
	public void createBody(List datas, HSSFSheet sheet, HSSFWorkbook hssfWorkBook, int start);
	public void createBody(List<Map> datas, String[] keys, HSSFSheet sheet, HSSFWorkbook hssfWorkBook, int start);
	public void createButtomTotal(List totalCC, HSSFSheet sheet, int lastRowNumber);
	public void createButtomTotal(List totalCC, String[] keys, HSSFSheet sheet, int lastRowNumber);
	public HSSFWorkbook buildExcel(List datas, String[] headers, String[] keys, String sheetname);
	//public HSSFWorkbook buildExcel(List datas,List total, String[] headers,String sheetname);
	public HSSFWorkbook buildExcel(List datas, List total, String[] headers, String[] keys, String sheetname);
}
