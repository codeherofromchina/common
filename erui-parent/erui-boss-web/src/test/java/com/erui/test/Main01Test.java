package com.erui.test;

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
            System.out.println("-----------" + tableName + "-------------------" + tableRemarks);
            colRet = dbMetaData.getColumns(null, "%", tableName, "%");
            while (colRet.next()) {
                columnName = colRet.getString("COLUMN_NAME");
                columnType = colRet.getString("TYPE_NAME");
                int datasize = colRet.getInt("COLUMN_SIZE");
                int digits = colRet.getInt("DECIMAL_DIGITS");
                int nullable = colRet.getInt("NULLABLE");
                columnRemarks = colRet.getString("REMARKS");
                System.out.println(columnName + " \t" + columnType + " \t" + datasize + " \t" + digits + " \t" + nullable + " \t" + columnRemarks);
            }
            colRet.close();
            colRet = null;
            System.out.println("-----------------------------------------");
        }
        tables.close();
        conn.close();
    }
}
