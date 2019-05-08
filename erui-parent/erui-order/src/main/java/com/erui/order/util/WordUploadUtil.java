package com.erui.order.util;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class WordUploadUtil {

    public static void setfont(XWPFRun run, int font, String family, String text){
        run.setFontSize(font);
        run.setFontFamily(family);
        run.setText(text);
    }


    /**
     *
     * @Description 导出word
     * @Fcunction exportWord
     * @param value_columns
     * @param list
     * @return XWPFDocument
     *
     */
    public static XWPFDocument exportWord(XWPFDocument doc,  XWPFTable comTable, String[] value_columns, List<List<String>> list, int[] colWidths) {
        //表格
        int font = 9; // 字号
        String family = "宋体"; // 字体
        int val = 9; // 设置两行之间的行间
        //表头
        XWPFTableRow rowHead = comTable.getRow(0);
        XWPFParagraph cellParagraph = rowHead.getCell(0).getParagraphs().get(0);
        cellParagraph.setAlignment(ParagraphAlignment.CENTER); //设置表头单元格居中
        XWPFRun cellParagraphRun  = cellParagraph.createRun();
        cellParagraphRun.setFontSize(font); //设置表头单元格字号
        cellParagraphRun.setFontFamily(family);
        cellParagraphRun.setTextPosition(val);
//        cellParagraphRun.setBold(true); //设置表头单元格加粗
        cellParagraphRun.setText(value_columns[0]);
        for (int i = 1; i < value_columns.length; i++) {
            if(value_columns[i].indexOf("增减")>-1) {
                cellParagraph = rowHead.addNewTableCell().getParagraphs().get(0);
                cellParagraph.setAlignment(ParagraphAlignment.CENTER); //设置表头单元格居中
                cellParagraphRun  = cellParagraph.createRun();
                cellParagraphRun.setFontSize(font); //设置表头单元格居中
                cellParagraphRun.setFontFamily(family);
                cellParagraphRun.setTextPosition(val);
//                cellParagraphRun.setBold(true);
                cellParagraphRun.setText("增减(+ / -)");
            }else {
                cellParagraph = rowHead.addNewTableCell().getParagraphs().get(0);
                cellParagraph.setAlignment(ParagraphAlignment.CENTER); //设置表头单元格居中
                cellParagraphRun  = cellParagraph.createRun();
                cellParagraphRun.setFontSize(font); //设置表头单元格居中
                cellParagraphRun.setFontFamily(family);
                cellParagraphRun.setTextPosition(val);
//                cellParagraphRun.setBold(true); //设置表头单元格加粗
                cellParagraphRun.setText(value_columns[i]);
            }
        }
        exchange(doc, list.get(0).size());
        int rows = list.size();
        //表格内容
        for (int i = 0; i < rows; i++) {
            XWPFTableRow rowsContent = comTable.createRow();
            for (int j = 0; j < list.get(i).size(); j++) {
                XWPFParagraph cellParagraphC = rowsContent.getCell(j).getParagraphs().get(0);
                cellParagraphC.setAlignment(ParagraphAlignment.CENTER); //设置表头单元格居中
                XWPFRun cellParagraphRunC  = cellParagraphC.createRun();
                cellParagraphRunC.setFontSize(font); //设置表格内容字号
                cellParagraphRunC.setFontFamily(family);
                cellParagraphRunC.setTextPosition(val);
                cellParagraphRunC.setText(list.get(i).get(j)+""); //单元格段落加载内容
            }
        }
        if(rows==0) {
            for (int i = 0; i < value_columns.length; i++) {
                XWPFTableCell cell = comTable.getRow(0).getCell(i);
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER); //垂直居中
            }
        }else {
            //设置居中
            for (int i = 0; i <= rows; i++) {
                for (int j = 0; j < list.get(0).size(); j++) {
                    XWPFTableCell cell = comTable.getRow(i).getCell(j);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER); //垂直居中
                }
            }
        }
        return doc;
    }
    //统一设置表格样式和宽度
    public static void exchange(XWPFDocument doc, int cells){
        List<XWPFTable> list = doc.getTables();
        for (XWPFTable xwpfTable : list) {
            CTTblBorders border = CTTblBorders.Factory.newInstance();
            CTBorder left = border.isSetLeft()?border.getLeft():CTBorder.Factory.newInstance();
            left.setSz(BigInteger.ONE);
            CTBorder right = border.isSetRight()?border.getRight():CTBorder.Factory.newInstance();
            right.setSz(BigInteger.ONE);
            CTBorder bottom = border.isSetBottom()?border.getBottom():CTBorder.Factory.newInstance();
            bottom.setSz(BigInteger.TEN);
            CTBorder top = border.isSetTop()?border.getTop():CTBorder.Factory.newInstance();
            top.setSz(BigInteger.ONE);
            //内部横线
            CTBorder insideH = border.isSetInsideH()?border.getInsideH():CTBorder.Factory.newInstance();
            insideH.setSz(BigInteger.ONE);
            //内部竖线
            CTBorder insideV = border.isSetInsideV()?border.getInsideV():CTBorder.Factory.newInstance();
            insideV.setSz(BigInteger.ONE);

            insideH.setVal(STBorder.Enum.forInt(STBorder.INT_PENCILS));
            insideV.setVal(STBorder.Enum.forInt(STBorder.INT_PENCILS));
            top.setVal(STBorder.Enum.forInt(STBorder.INT_PENCILS));
            bottom.setVal(STBorder.Enum.forInt(STBorder.INT_PENCILS));
            left.setVal(STBorder.Enum.forInt(STBorder.INT_PENCILS));
            right.setVal(STBorder.Enum.forInt(STBorder.INT_PENCILS));

            border.setLeft(left);
            border.setRight(right);
            border.setBottom(bottom);
            border.setTop(top);
            border.setInsideH(insideH);
            border.setInsideV(insideV);

            xwpfTable.getCTTbl().getTblPr().setTblBorders(border);
            //表格属性
            CTTbl ttbl = xwpfTable.getCTTbl();
            CTTblPr tablePr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
            //表格宽度
            CTTblWidth tblWidth = tablePr.isSetTblW()?tablePr.getTblW():tablePr.addNewTblW();
            tblWidth.setW(BigInteger.valueOf(8500));
            //设置表格宽度为非自动
            tblWidth.setType(STTblWidth.DXA);

            CTJc jc = tablePr.isSetJc()?tablePr.getJc():tablePr.addNewJc();
            jc.setVal(STJc.Enum.forInt(ParagraphAlignment.CENTER.getValue()));

        }
    }


    //横向合并单元格
    public static void mergeCellsHorizontal(XWPFTable table, int row, int fromCell,int toCell){
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if (cellIndex == fromCell) {
                getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.RESTART);
            } else {
                getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
    //纵向合并单元格
    public static void mergeCellsVertically(XWPFTable table, int col, int fromRow,int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if (rowIndex == fromRow) {
                getCellCTTcPr(cell).addNewVMerge().setVal(STMerge.RESTART);
            } else {
                getCellCTTcPr(cell).addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
    public static CTTcPr getCellCTTcPr(XWPFTableCell cell) {
        CTTc cttc = cell.getCTTc();
        CTTcPr tcPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
        return tcPr;
    }
    // 导出
    public static void export(XWPFDocument doc){
        // 导出
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("E:/报告.docx");
            doc.write(out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg){
        XWPFDocument doc = new XWPFDocument();
        List<List<String>> list = new ArrayList<>();
        List<String> listC = null;
        for(int i = 0; i<10; i++){
            listC = new ArrayList<>();
            for(int j = 0; j<7; j++){
                listC.add(j+"");
            }
            list.add(listC);
        }
        int[] colWidths = new int[] { 600, 3000, 500, 500, 800, 800 ,800};
        String[] value_columns = new String[]{"序号","项目名称","单位","数量","总概算","至2018年完成","2019年计划"};
        XWPFTable comTable = doc.createTable();
        doc = exportWord(doc, comTable, value_columns, list, colWidths);
        export(doc);
    }
}
