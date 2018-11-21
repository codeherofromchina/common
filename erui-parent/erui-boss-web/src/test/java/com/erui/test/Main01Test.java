package com.erui.test;

import com.erui.comm.util.data.string.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiaodan on 2018/11/21.
 */
public class Main01Test {

    private static String user = "wxd";
    private static String password = "kRui#rds88";
    private static String url = "jdbc:mysql://rm-erui123.mysql.rds.aliyuncs.com:3306/erui_sys?characterEncoding=utf-8";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        List<String> tableNames = new ArrayList<>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);
        DatabaseMetaData dbMetaData = conn.getMetaData();
        ResultSet tables = dbMetaData.getTables(null, null, "", new String[]{"TABLE"});
        ResultSet colRet;
        String tableName;
        String tableRemarks;
        String columnName;
        String columnType;
        String columnRemarks;
        while (tables.next()) {
            tableName = tables.getString("TABLE_NAME");
            tableRemarks = tables.getString("REMARKS");
            String tableTitle = tableName;
            if (StringUtil.isNotBlank(tableRemarks)) {
                tableTitle = tableRemarks + "(" + tableTitle + ")";
            }

            System.out.println(tableTitle);
            System.out.println("名称 \t字段 \t数据类型 \t默认值 \t允许NULL \t说明");
            colRet = dbMetaData.getColumns(null, "%", tableName, "%");
            while (colRet.next()) {
                columnName = colRet.getString("COLUMN_NAME");
                columnType = colRet.getString("TYPE_NAME");
                String columnDef = colRet.getString("COLUMN_DEF");
                int datasize = colRet.getInt("COLUMN_SIZE");
                int digits = colRet.getInt("DECIMAL_DIGITS");
                int nullable = colRet.getInt("NULLABLE");
                columnRemarks = colRet.getString("REMARKS");
                System.out.println(" \t" + columnName + " \t" + columnType+"("+datasize+") \t" + (columnDef==null?"":columnDef) + " \t" + (nullable==1?"TRUE":"FALSE") + " \t" + columnRemarks);
            }
            colRet.close();
            colRet = null;
            System.out.println("-----------------------------------------");
        }
        tables.close();
        conn.close();
    }
}
