package com.erui.comm.util.excel;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.excel.graphbuilder.math.Expression;
import com.erui.comm.util.excel.graphbuilder.math.ExpressionTree;
import com.erui.comm.util.excel.graphbuilder.math.VarMap;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BuildExcelImpl implements BuildExcel {
	DecimalFormat df = new DecimalFormat("0.00");
	DecimalFormat df1 = new DecimalFormat("0.00%");
	private Map<String,Map<String,String>> special = new HashMap<String,Map<String,String>>();     // 需要转换的值      x=y1_z1%y2_z2     x:变量   y1:x对应的值   z1:需要转换的值
	/**
	 * 创建excel头
	 * 默认头为一行，并且是第一行
	 * @param headers   表头名
	 * @param sheet   sheet页
	 */
	public void createHead(String[] headers,HSSFSheet sheet,HSSFCellStyle style) {
		//在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row1 = sheet.createRow((int) 0);
		HSSFCell cell	=	null;
		for(int i=0;i<headers.length;i++){
			 cell = row1.createCell(i);
			 cell.setCellValue(headers[i]);
			 if(style!=null){
				 cell.setCellStyle(style);
			 }
		}
	}
	
	/**
	 * 
	 */
	public void createBody(List datas, HSSFSheet sheet,HSSFWorkbook hssfWorkBook, int start) {
		createBody(datas,null,sheet,hssfWorkBook,start);
	}
	
	/**
	 * 创建excel内容
	 * @param datas   数据内容
	 * @param sheet   sheet页
	 * @param start   开始行
	 */
	public void createBody(List datas,String[] keys,HSSFSheet sheet,HSSFWorkbook hssfWorkBook,int start) {
		start = sheet.getLastRowNum()+1;
		if(keys == null || keys.length<=0){     // 数组格式
			HSSFCellStyle cellStyle = hssfWorkBook.createCellStyle();
			for(int i=0;i<datas.size();i++){
				Object[] objs = (Object[])datas.get(i);
				HSSFRow row = sheet.createRow(i+start);  
				for(int j=0;j<objs.length;j++){
					HSSFCell cell = row.createCell(j);
					if(objs[j] instanceof BigDecimal){
						HSSFDataFormat format= hssfWorkBook.createDataFormat();
						//cellStyle.setDataFormat(format.getFormat("#,##0.00"));
						cell.setCellStyle(cellStyle);
						cell.setCellValue(((BigDecimal)objs[j]).doubleValue());
					} else if (objs[j] instanceof Double){
			            HSSFDataFormat format= hssfWorkBook.createDataFormat();
			            //cellStyle.setDataFormat(format.getFormat("#,##0.00"));
						cell.setCellStyle(cellStyle);
						cell.setCellValue(((Double)objs[j]));
					}else if (objs[j] instanceof Date){
						cell.setCellValue(DateUtil.formatDate2String((Date)objs[j],DateUtil.SHORT_FORMAT_STR));
					}else{
						cell.setCellValue(objs[j]==null?"":objs[j].toString());
					}
				}
			}
		}else{       // List<Map> or List<List<Map>>....
			HSSFCellStyle cellStyle = hssfWorkBook.createCellStyle();
			for(int i=0;i<datas.size();i++){
				if(datas.get(i) instanceof List){    // 若是List<List<Map>> 递归
					createBody((List)datas.get(i),keys,sheet,hssfWorkBook,sheet.getLastRowNum());
				}else{
					Map m = (Map)datas.get(i);
					HSSFRow row = sheet.createRow(i+start); 
					for(int j=0;j<keys.length;j++){
						if(keys[j].startsWith("[") && keys[j].contains("]")){       // [id]name格式
							String strId =  keys[j].substring(1, keys[j].indexOf("]"));
							String strName = keys[j].substring(keys[j].indexOf("]")+1);
							String id = m.get(strId)==null?"":m.get(strId).toString();
							String name = m.get(strName)==null?"":m.get(strName).toString();
							row.createCell(j).setCellValue("["+id+"]"+name);
						}else if(keys[j].contains("/") || keys[j].contains("*") || keys[j].contains("+") || keys[j].contains("-")){        // 四则运算
							String[] ss = keys[j].split(",");    //  获取表达式
							Expression e = ExpressionTree.expression(ss[0]);
							VarMap vm = new VarMap(false);
							for(int k=1; k<ss.length ;k++){
								double val = m.get(ss[k])==null?0:Double.parseDouble(m.get(ss[k]).toString());
								vm.setValue(ss[k], val);
							}
							double r = e.eval(vm, null);
							if(Double.POSITIVE_INFINITY != r){
								row.createCell(j).setCellValue(df.format(r));
							}
						}else if(keys[j].contains("=")){        // 值 ，但需要转换    
							String[] k = keys[j].split("=");
							String v = m.get(k[0])==null?"":m.get(k[0]).toString();       // 获取变量对应的值
							if(special.get(k[0])!=null){     //  已经解析
								row.createCell(j).setCellValue(special.get(k[0]).get(v));     // 获取需要转换成的值
							}else{      // 初次解析    放入special中
								String[] k1 = k[1].split("%");
								Map<String,String> sm = new HashMap<String,String>();
								for(String k11 : k1){
									String[] k111 = k11.split("_");
									sm.put(k111[0], k111[1]);
									if(v.equals(k111[0])){
										row.createCell(j).setCellValue(k111[1]);
									}
								}
								special.put(k[0], sm);
							}
						}else{         // value
							Object obj = m.get(keys[j]);
							HSSFCell cell = row.createCell(j);
							if(obj instanceof BigDecimal){
								HSSFDataFormat format= hssfWorkBook.createDataFormat();
								//cellStyle.setDataFormat(format.getFormat("#,##0.00"));
								cell.setCellStyle(cellStyle);
								cell.setCellValue(((BigDecimal)obj).doubleValue());
							} else if (obj instanceof Double){
					            HSSFDataFormat format= hssfWorkBook.createDataFormat();  
					            //cellStyle.setDataFormat(format.getFormat("#,##0.00"));
								cell.setCellStyle(cellStyle);
								cell.setCellValue(((Double)obj));
							}else if (obj instanceof Date){
								cell.setCellValue(DateUtil.formatDate2String((Date)obj,DateUtil.SHORT_FORMAT_STR));
							}else{
								cell.setCellValue(obj==null?"":obj.toString());
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 创建表尾 一般是统计
	 */
	public void createButtomTotal(List totalCC,HSSFSheet sheet,
			int lastRowNumber) {
		createButtomTotal(totalCC,null,sheet,lastRowNumber);
	}
	
	/**
	 * 创建表尾 一般是统计
	 */
	public void createButtomTotal(List totalCC,String keys[],HSSFSheet sheet,
			int lastRowNumber) {
		if(keys == null || keys.length<=0){
			for(int i=0;i<totalCC.size();i++){
				Object[] objs = (Object[])totalCC.get(i);
				HSSFRow row = sheet.createRow(i+lastRowNumber);  
				for(int j=0;j<objs.length;j++){
					HSSFCell cell = row.createCell(j);
					if(objs[j] instanceof BigDecimal){
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(((BigDecimal)objs[j]).doubleValue());
					}else{
						cell.setCellValue(objs[j]==null?"":objs[j].toString());
					}
				}
			}
		}else{
			for(int i=0;i<totalCC.size();i++){
				Map m = (Map)totalCC.get(i);
				HSSFRow row = sheet.createRow(i+lastRowNumber); 
				for(int j=0;j<keys.length;j++){
					
					if(keys[j].startsWith("[") && keys[j].contains("]")){       // [id]name格式
						String strId =  keys[j].substring(1, keys[j].indexOf("]"));
						String strName = keys[j].substring(keys[j].indexOf("]")+1);
						String id = m.get(strId)==null?"":m.get(strId).toString();
						String name = m.get(strName)==null?"":m.get(strName).toString();
						row.createCell(j).setCellValue("["+id+"]"+name);
					}else if(keys[j].contains("/") || keys[j].contains("*") || keys[j].contains("+") || keys[j].contains("-")){        // 四则运算表达式
						String[] ss = keys[j].split(",");    //  获取表达式
						Expression e = ExpressionTree.expression(ss[0]);
						VarMap vm = new VarMap(false);
						for(int k=1; k<ss.length ;k++){
							double val = m.get(ss[k])==null?0:Double.parseDouble(m.get(ss[k]).toString());
							vm.setValue(ss[k], val);
						}
						double v = e.eval(vm, null);
						double r = e.eval(vm, null);
						if(Double.POSITIVE_INFINITY != r){
							row.createCell(j).setCellValue(df.format(r));
						}
					}else{         // value
						Object value = m.get(keys[j]);
						if(j==0 && (value == null || "".equals(value.toString()))){
							row.createCell(j).setCellValue("合计");
						}else{
							HSSFCell cell = row.createCell(j);
							if(value instanceof BigDecimal){
								cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
								cell.setCellValue(((BigDecimal)value).doubleValue());
							}else{
								cell.setCellValue(value==null?"":value.toString());
							}
						}
					}
				}
			}
		}
	}
	
	public HSSFSheet createSheet(HSSFWorkbook hssfworkbook,int page,String sheetname) {
		HSSFSheet sheet1 = hssfworkbook.createSheet(sheetname);   // 创建sheet页
		return sheet1;   // 创建sheet页
	}
	
	public HSSFWorkbook buildExcel(List datas, String[] headers,String[] keys,String sheetname) {
		return buildExcel(datas,null,headers,keys,sheetname);
	}
	
	public HSSFWorkbook buildExcel(List datas,List total, String[] headers,String[] keys,String sheetname) {
		HSSFWorkbook hssfWorkBook = new HSSFWorkbook(); // 创建工作簿
		HSSFSheet sheet1 = createSheet(hssfWorkBook,0,sheetname);   // 创建sheet页
		hssfWorkBook.setSheetName(0	, sheetname);
		//创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = hssfWorkBook.createCellStyle();
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.YELLOW.index);    // 设置背景颜色
		// 设置字体    
	    HSSFFont headfont = hssfWorkBook.createFont();    
	    headfont.setFontName("宋体");    
	    headfont.setFontHeightInPoints((short) 11);// 字体大小    
		style.setFont(headfont);
		createHead(headers,sheet1,style);   // 创建表头
		createBody(datas,keys,sheet1,hssfWorkBook,1);     // 创建表内容
		if(total!=null&&total.size()>0){
			createButtomTotal(total,keys,sheet1,sheet1.getLastRowNum() + 1);
		}
		for(int i=0;i<headers.length;i++){
			sheet1.setColumnWidth(i, sheet1.getColumnWidth(i) * 2);
			//sheet1.autoSizeColumn(i,true);   // 自适应宽度
		}
		return hssfWorkBook;
	}


}
