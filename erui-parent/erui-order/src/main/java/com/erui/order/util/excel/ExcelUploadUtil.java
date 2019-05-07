package com.erui.order.util.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUploadUtil {
    /** 
     * 创建Font（）
     *
     */
    public static Font getFont(XSSFWorkbook workbook, int fontSize, String fontName) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)fontSize);
        font.setFontName(fontName);
        return font;
    }
    /** 
     *加入下划线
     *
     */
    private static XSSFRichTextString UnderLineIndex(XSSFRichTextString richString, Font font, int start, int end, int all) {
        richString.applyFont(end, all, font);
        font.setUnderline(Font.U_SINGLE); //下划线
        richString.applyFont(start, end, font); //下划线的起始位置，结束位置
        return richString;
    }
    /** 
     * 设定单元格
     */
    public static XSSFRichTextString setSingle(XSSFRichTextString richString, Font font, int start, int end, int all){
        return UnderLineIndex(richString, font, start, end, all);
    }
    /**
     * Excel 实现行的插入
     *
     */
    public static void insertRow(Sheet sheet, int starRow, int rows) {

        sheet.shiftRows(starRow + 1, sheet.getLastRowNum(), rows,true,false);
        starRow = starRow - 1;
        for (int i = 0; i < rows; i++) {

            Row sourceRow = null;
            Row targetRow = null;
            Cell sourceCell = null;
            Cell targetCell = null;
            short m;

            starRow = starRow + 1;
            sourceRow = sheet.getRow(starRow);
            targetRow = sheet.createRow(starRow + 1);
            targetRow.setHeight(sourceRow.getHeight());

            for (m = sourceRow.getFirstCellNum(); m < sourceRow.getLastCellNum(); m++) {

                sourceCell = sourceRow.getCell(m);
                targetCell = targetRow.createCell(m);

                targetCell.setCellStyle(sourceCell.getCellStyle());
                targetCell.setCellType(sourceCell.getCellType());

            }
        }
    }

}
