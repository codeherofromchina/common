package com.erui.comm.util.excel;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;


/**
 * excel样式定义
 * 
 * @author lenovo
 *
 */
public class ExcelCustomStyle {

	/**
	 * 设置表格头样式（默认第一列为表格头） 设置字体宋体、10号字，灰色背景、行高500、水平垂直对齐,设置边框（窄，黑色）
	 * 
	 * @param workbook
	 *            要设置的excel
	 * @param sheetIndex
	 *            要设置的的工作簿索引
	 * @param headRowIndex
	 *            列头所在行索引
	 */
	public static void setHeadStyle(HSSFWorkbook workbook, int sheetIndex,
			int headRowIndex) {
		if (workbook != null) {
			try {
				HSSFSheet sheet = workbook.getSheetAt(sheetIndex < 0 ? 0
						: sheetIndex);

				HSSFRow row = sheet.getRow(headRowIndex < 0 ? 0 : headRowIndex);

				HSSFCellStyle style = row.getRowStyle();
				if (style == null) {
					style = workbook.createCellStyle();
				}
				// 设置背景色
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

				// 设置水平垂直居中对齐方式
				style.setAlignment(HorizontalAlignment.CENTER);
				style.setVerticalAlignment(VerticalAlignment.CENTER);
				
				// 设置边框
				style.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色
				style.setBorderLeft(BorderStyle.THIN);// 边框的大小
				style.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色
				style.setBorderRight(BorderStyle.THIN);// 边框的大小
				style.setTopBorderColor(HSSFColor.BLACK.index); // 上边框的颜色
				style.setBorderTop(BorderStyle.THIN); // 上边框的大小
				style.setBottomBorderColor(HSSFColor.BLACK.index); // 下边框的颜色
				style.setBorderBottom(BorderStyle.THIN); // 下边框的大小
				
				// 设置字体
				HSSFFont headfont = workbook.createFont();
				headfont.setFontName("宋体");
				headfont.setFontHeightInPoints((short) 10);// 字体大小
				// headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				style.setFont(headfont);

				short cellNum = row.getLastCellNum();
				for (int i = 0; i < cellNum; ++i) {
					HSSFCell cell = row.getCell(i);
					cell.setCellStyle(style);
				}

				// 设置标题的高度
				row.setHeight((short) 500);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("setHeadStyle 设置Excel表格头样式异常");
			}
		}
	}

	/**
	 * 设置excel内容样式
	 * 
	 * @param workbook
	 *            要设置的excel
	 * @param sheetIndex
	 *            要设置的工作簿 如果小于0则操作地0个工作簿
	 * @param startRowIndex
	 *            开始的行，默认为0
	 * @param endRowIndex
	 *            结束的行，默认最后一行
	 */
	public static void setContextStyle(HSSFWorkbook workbook, int sheetIndex,
			int startRowIndex, int endRowIndex) {
		if (workbook != null) {
			try {
				// / 检查前提条件
				// 如果参数不存在，则设置新值
				if (sheetIndex < 0) {
					sheetIndex = 0;
				}

				HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
				int lastRowNum = sheet.getLastRowNum();
				if (startRowIndex < 0) {
					startRowIndex = 0;
				} else if (startRowIndex > lastRowNum) {
					return;
				}
				if (endRowIndex < 0 || endRowIndex > lastRowNum) {
					endRowIndex = lastRowNum;
				}
				if (lastRowNum < startRowIndex) {
					return;
				}

				// 调整样式
				short rowHeight = 400;
				HSSFCellStyle style = workbook.createCellStyle();

				// 设置水平居中对齐方式
				style.setAlignment(HorizontalAlignment.CENTER);
				style.setVerticalAlignment(VerticalAlignment.CENTER);
				
				
				// 设置边框
				style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 左边框的颜色
				style.setBorderLeft(BorderStyle.THIN);// 边框的大小
				style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 右边框的颜色
				style.setBorderRight(BorderStyle.THIN);// 边框的大小
				style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex()); // 上边框的颜色
				style.setBorderTop(BorderStyle.THIN); // 上边框的大小
				style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex()); // 下边框的颜色
				style.setBorderBottom(BorderStyle.THIN); // 下边框的大小
				//style.setWrapText(true); // 允许换行

				// 设置字体
				// HSSFFont headfont = workbook.createFont();
				// headfont.setFontName("宋体");
				// headfont.setFontHeightInPoints((short) 10);// 字体大小
				// style.setFont(headfont);
				for (int i = startRowIndex; i <= endRowIndex; ++i) {
					HSSFRow row = sheet.getRow(i);
					short lastCellNum = row.getLastCellNum();
					for (int n = 0; n < lastCellNum; ++n) {
						HSSFCell cell = row.getCell(n);
						HSSFCellStyle cellStyle = cell.getCellStyle();
						short dataFormat = cellStyle.getDataFormat();
						HSSFCellStyle style02 = workbook.createCellStyle();
						style02.cloneStyleFrom(style);
						style02.setDataFormat(dataFormat);
						cell.setCellStyle(style02);
					}
					short height = row.getHeight();
					
					row.setHeight(height>rowHeight?height:rowHeight);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * 插入标题 合并单元格(合并整行单元格)，设置样式(字体微软雅黑、14号、加粗；水平垂直居中，高度800)，设置边框（窄，蓝色），设置标题内容
	 * 
	 * @param workbook
	 * @param sheetIndex
	 */
	public static void insertTitle(HSSFWorkbook workbook, int sheetIndex,
			int row, int column, String title) {
		if (workbook != null) {
			try {
				HSSFSheet sheet = workbook.getSheetAt(sheetIndex < 0 ? 0
						: sheetIndex);

				// 合并单元格
				HSSFRow hssfRow = sheet.getRow(row);
				hssfRow.setHeight((short) 800);
				short lastCellNum = hssfRow.getLastCellNum();
				// 新插入的行可能长度为-1，如果是-1的话，检测标题下一行的单元格数量
				if (lastCellNum == -1) {
					HSSFRow nextRow = sheet.getRow(row + 1);
					if (nextRow != null) {
						lastCellNum = nextRow.getLastCellNum();
					}
				}
				mergedCell(sheet, row, row, column, lastCellNum - 1);

				// 设置样式
				HSSFCellStyle titleCellStyle = workbook.createCellStyle();
				// 设置背景色
				titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				titleCellStyle
						.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);

				// 设置水平垂直居中对齐方式
				titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
				titleCellStyle
						.setVerticalAlignment(VerticalAlignment.CENTER);

				// 设置边框
				titleCellStyle.setLeftBorderColor(HSSFColor.GREEN.index);// 左边框的颜色
				titleCellStyle.setBorderLeft(BorderStyle.THIN);// 边框的大小
				titleCellStyle.setRightBorderColor(HSSFColor.GREEN.index);// 右边框的颜色
				titleCellStyle.setBorderRight(BorderStyle.THIN);// 边框的大小
				titleCellStyle.setTopBorderColor(HSSFColor.GREEN.index); // 上边框的颜色
				titleCellStyle.setBorderTop(BorderStyle.THIN); // 上边框的大小
				titleCellStyle.setBottomBorderColor(HSSFColor.GREEN.index); // 下边框的颜色
				titleCellStyle.setBorderBottom(BorderStyle.THIN); // 下边框的大小

				// 设置字体
				HSSFFont headfont = workbook.createFont();
				headfont.setFontName("微软雅黑");
				headfont.setFontHeightInPoints((short) 14);// 字体大小
				headfont.setBold(true);
				titleCellStyle.setFont(headfont);

				hssfRow = sheet.getRow(row);

				for (int n = column; n < lastCellNum; ++n) {

					//HSSFCell cell2 = HSSFCellUtil.getCell(hssfRow, n);
					HSSFCell cell2 = hssfRow.getCell(n, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cell2.setCellStyle(titleCellStyle);
				}

				// 设置标题内容
				hssfRow.getCell(column).setCellValue(
						StringUtils.isNotEmpty(title) ? title : sheet
								.getSheetName());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 在excel的工作簿中插入新行
	 * 
	 * @param workbook
	 * @param sheetIndex
	 * @param insertRowIndex
	 */
	public static void insertRow(HSSFWorkbook workbook, int sheetIndex,
			int insertRowIndex, int insertRowNum) {

		if (workbook != null) {
			try {
				HSSFSheet sheet = workbook.getSheetAt(sheetIndex);

				int lastRowNum = sheet.getLastRowNum();

				if (insertRowIndex <= lastRowNum) {
					sheet.shiftRows((insertRowIndex < 0 ? 0 : insertRowIndex),
							lastRowNum, 1, true, false);
				}

				sheet.createRow(insertRowIndex < 0 ? 0 : insertRowIndex);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * 合并单元格
	 * 
	 * @param workbook
	 * @param sheetIndex
	 * @param startRowIndex
	 * @param endRowIndex
	 * @param startColumnIndex
	 * @param endColumnIndex
	 */
	public static void mergedCell(HSSFWorkbook workbook, int sheetIndex,
			int startRowIndex, int endRowIndex, int startColumnIndex,
			int endColumnIndex) {
		if (workbook != null) {
			try {
				HSSFSheet sheet = workbook.getSheetAt(sheetIndex < 0 ? 0
						: sheetIndex);
				mergedCell(sheet, startRowIndex, endRowIndex, startColumnIndex,
						endColumnIndex);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 * @param startRowIndex
	 * @param endRowIndex
	 * @param startColumnIndex
	 * @param endColumnIndex
	 */
	public static void mergedCell(HSSFSheet sheet, int startRowIndex,
			int endRowIndex, int startColumnIndex, int endColumnIndex) {
		if (sheet != null) {
			try {
				if (endRowIndex < 0) {
					endRowIndex = sheet.getLastRowNum();
				}

				if (endColumnIndex < 0) {
					HSSFRow row = sheet.getRow(startRowIndex);
					endColumnIndex = row.getLastCellNum();
				}
				if (endColumnIndex < startColumnIndex) {
					endColumnIndex = startColumnIndex;
				}

				CellRangeAddress range = new CellRangeAddress(startRowIndex,
						endRowIndex, startColumnIndex, endColumnIndex);
				sheet.addMergedRegion(range);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
	
	/**
	 * 将某列设置为数字格式
	 */
	public static void setColumnToNumber(HSSFWorkbook workbook, int sheetIndex,
			int columnIndex) {
		if (workbook != null) {
			try {
				HSSFSheet sheet = workbook.getSheetAt(sheetIndex < 0 ? 0
						: sheetIndex);
				int lastRowNum = sheet.getLastRowNum();
				for(int i = 0;i<lastRowNum;i++){
					HSSFRow row = sheet.getRow(i);
					HSSFCell cell = row.getCell(columnIndex);
					String stringCellValue = cell.getStringCellValue();
					try{
						double parseDouble = Double.parseDouble(stringCellValue);
						cell.setCellValue(parseDouble);
					}catch(NumberFormatException ex){}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		
		
	}
}
