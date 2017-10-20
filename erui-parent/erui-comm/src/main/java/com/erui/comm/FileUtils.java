package com.erui.comm;

import org.apache.commons.lang3.StringUtils;

public class FileUtils {
	private final static String[] EXCEL_CONTENT_TYPE = new String[] {
			"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/octet-stream" };
	private final static String[] EXCEL_SUFFIX = new String[] { ".xls", ".xlsx" };

	/**
	 * 判断文件名称是否是excel的文件后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isExcelSuffixFile(String fileName) {
		for (String suffix : EXCEL_SUFFIX) {
			if (StringUtils.endsWithIgnoreCase(fileName, suffix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断文件名称类型是否是excel的文件类型
	 * 
	 * @param contentType
	 * @return
	 */
	public static boolean isExcelContentType(String contentType) {
		for (String ct : EXCEL_CONTENT_TYPE) {
			if (StringUtils.equalsIgnoreCase(contentType, ct)) {
				return true;
			}
		}
		return false;
	}

}
