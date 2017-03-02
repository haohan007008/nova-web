package com.howbuy.fp.utils;

import java.io.File;
import java.io.FileInputStream;  
import java.sql.Connection;  
import java.sql.SQLException;  
import java.util.Properties;  
  

import javax.sql.DataSource;  
  

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.DataSources;  

public class C3P0Connection {
	private static Logger log = Logger.getLogger(C3P0Connection.class);
	
	public static String filePath="/classes/c3p0.properties";
    private static DataSource ds;  
    /** 
     * 初始化连接池代码块 
     */  
    
    static {  
    	File path= new File(C3P0Connection.class.getResource("/").getPath());
    	initDBSource(path.getParentFile().getPath()+filePath);
    }
  
    /** 
     * 初始化c3p0连接池 
     */  
    private static final void initDBSource(String path) {  
        Properties props = new Properties();  
        try {  
        	//File path= new File(DBConnection.class.getResource("/").getPath());
            FileInputStream in = new FileInputStream(path);  
            props.load(in);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        String drverClass = props.getProperty("driverClass");  
        if (drverClass != null) {  
            try {  
                // 加载驱动类  
                Class.forName(drverClass);  
            } catch (ClassNotFoundException e) {  
                e.printStackTrace();  
            }  
  
        }  
  
        Properties jdbcpropes = new Properties();  
        Properties c3propes = new Properties();  
        for (Object key : props.keySet()) {  
            String skey = (String) key;  
            if (skey.startsWith("c3p0.")) {  
                c3propes.put(skey, props.getProperty(skey));  
            } else {  
                jdbcpropes.put(skey, props.getProperty(skey));  
            }  
        }  
  
        try {  
            // 建立连接池  
            DataSource unPooled = DataSources.unpooledDataSource(props.getProperty("jdbcUrl"), jdbcpropes);  
            ds = DataSources.pooledDataSource(unPooled, c3propes);  
  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 获取数据库连接对象 
     *  
     * @return 数据连接对象 
     * @throws SQLException 
     */  
    public static synchronized Connection getConnection() throws SQLException {  
        final Connection conn = ds.getConnection();  
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);  
        return conn;  
    }  
    
	/**
	 * 系统调用
	 * @param filePath
	 */
	public static void init(String filePath) {
		// TODO Auto-generated method stub
		initDBSource(filePath);
	}
}
