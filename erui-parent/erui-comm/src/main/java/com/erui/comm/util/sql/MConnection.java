package com.erui.comm.util.sql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


public class MConnection {
    public static Connection connection;
    private static DBInfoModel dbInfoModel;
//    private static final Logger log = LoggerFactory.getLogger(MConnection.class);
    
    public static Connection getConnection(){
    	DBInfoModel dbInfoModel = getDBInfo();
        try{
            Class.forName(dbInfoModel.getDrivers());
            connection = DriverManager.getConnection(dbInfoModel.getUrl(),dbInfoModel.getUser(),dbInfoModel.getPassword());
        }catch(Exception e){
            e.printStackTrace();
        }
        return connection;
    }
    public static void closeConnection(){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static DBInfoModel getDBInfo()
    {
    	if(null==dbInfoModel || null==dbInfoModel.getDrivers() || dbInfoModel.getDrivers().equals(""))
    	{
	    	InputStream in = MConnection.class.getResourceAsStream("/comm.properties");
			Properties p = new Properties();
			dbInfoModel = new DBInfoModel();
			try {
				p.load(in);
				dbInfoModel.setDrivers(p.getProperty("db.master.drivers"));
				dbInfoModel.setUrl(p.getProperty("db.master.url"));
				dbInfoModel.setUser(p.getProperty("db.master.user"));
				dbInfoModel.setPassword(p.getProperty("db.master.password"));
			} catch (IOException e) {
//				log.debug("错误：初始化MConnection!。缺少配置文件。~! /config/comm.properties");
				e.printStackTrace();
			}
    	}
		return dbInfoModel;
    }

    
	public static List<Map> findResult(String sql)
	{
		String exsql = sql;
		List<Map> list = new ArrayList();
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement pstmt=con.prepareStatement(exsql.toString());
			ResultSet rs=pstmt.executeQuery();
			ResultSetMetaData data=rs.getMetaData();
			while(rs.next())
			{
				Map map = new HashMap();
				for(int i=1;i<=data.getColumnCount();i++)
				{
					String columnTypeName=data.getColumnTypeName(i);
					String beanValue = "";
					beanValue = rs.getString(i);
					String columnName=data.getColumnName(i);
					map.put(columnName.toLowerCase(), beanValue);
					//map.put(columnName.toUpperCase(), beanValue);
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	/**\
	 * <summary>
	 * 按照SQL语句执行
	 *
	 * </summary>
	 * @author Juzhihai
	 * @param sql SQL语句
	 * @Date 2013-09-16
	 */
	public static boolean execmdBySql(String sql) {
		Connection con=null;
		con = getConnection();
		int status= 0;
		try {
			PreparedStatement pstmt=con.prepareStatement(sql);
			status=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return status>0?true:false;
	}
}
