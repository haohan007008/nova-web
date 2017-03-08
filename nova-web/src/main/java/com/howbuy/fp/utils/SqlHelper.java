package com.howbuy.fp.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
@Repository
public class SqlHelper {
	
	private static Logger log = Logger.getLogger(SqlHelper.class);
	private ConfContext sqlContext = ConfContext.getInstance();
	
	public ConfContext getSqlContext() {
		return sqlContext;
	}

	public void setSqlContext(ConfContext sqlContext) {
		this.sqlContext = sqlContext;
	}

	/**
     * 鏂规硶锛屼负sql璇彞璁剧疆鍙傛暟锛�
     * @param pstmt 鎸囦护
     * @param parameters 鍙傛暟闆嗗悎锛�
     * @throws SQLException 
     */
    private void setParameters(PreparedStatement pstmt,List<Object> parameters) throws SQLException{
        for (int i = 0; i < parameters.size(); i++) {
            Object v = parameters.get(i);
            pstmt.setObject(i+1, v);
        }
    }
    
    public boolean existTable(String tableName){
    	String sql = "select count(1) cnt from user_tables a where a.table_name=upper('"+tableName+"')";
    	
    	int cnt = Integer.parseInt(this.query4OneVal(sql, null));
    	if(cnt >= 1 ) 
    		return true;
    	else return false;
    }
    
    public boolean existIndex(String idxName){
    	String sql = "select count(1) cnt from user_indexes a where a.index_name=upper('"+idxName+"')";
    	
    	int cnt = Integer.parseInt(this.query4OneVal(sql, null));
    	if(cnt >= 1 ) 
    		return true;
    	else return false;
    }
    
    /**
     * executeBatch
     *
     * @param sql
     * @param parameters 鍒涘缓鏃堕棿锛�2016骞�8鏈�3鏃� 涓嬪崍1:31:34
     */
    public void executeBatch(String sql , List<List<Object>> parameters,String presql){
    	Connection conn = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        try {
        	//conn = DBConnection.getInstance().getConnection();
        	//DBConnection.getInstance().startTransaction();
        	conn = C3P0Connection.getConnection();
        	if(presql !=null){
	        	stmt = conn.createStatement(); 
        	}
        	if(parameters !=null && parameters.size() > 0){
        		pstmt = conn.prepareStatement(sql);
        		for (int j = 0; j < parameters.size(); j++) {
	        		List<Object> parameter = parameters.get(j);
		            if(parameter != null && parameter.size() > 0){
		                setParameters(pstmt, parameter);
		            }
		            pstmt.addBatch();
        		}
        		if(presql != null && presql.length() > 0)
        			stmt.executeUpdate(presql);
        		pstmt.executeBatch();
        	}
            
        } catch (SQLException e) {
        	log.error(e);
        	System.out.println(JSON.toJSONString(parameters));
        	e.printStackTrace();
            //DBConnection.getInstance().rollbackTransaction();
        }finally{
        	if(null != pstmt){
        		try {
					pstmt.close();
				} catch (SQLException e) {
					log.error(e);
				}
			}
        	if(null != stmt){
        		try {
        			stmt.close();
				} catch (SQLException e) {
					log.error(e);
				}
			}
        	if(null != conn){
        		try {
        			conn.close();
				} catch (SQLException e) {
					log.error(e);
				}
			}           	
        	//DBConnection.getInstance().commitTransaction();
        	//DBConnection.getInstance().closeConnection();
        }
    }
    
    /**
     * executeUpdate
     *
     * @param sql
     * @param parameters
     * @return 鍒涘缓鏃堕棿锛�2016骞�8鏈�3鏃� 涓嬪崍1:20:57
     */
    public int executeUpdate(String sql , List<Object> parameters){
    	Connection conn = null;
        PreparedStatement pstmt = null;
        int rows = -1 ;
        try {
        	//conn = DBConnection.getInstance().getConnection();
        	//DBConnection.getInstance().startTransaction();
        	conn = C3P0Connection.getConnection();
        	conn.setAutoCommit(false);
            //濡傛灉sql璇彞鏈夊弬鏁帮紱
            if(parameters != null && parameters.size() > 0){
                pstmt = conn.prepareStatement(sql);
                setParameters(pstmt, parameters);
                rows = pstmt.executeUpdate();
            }
            //濡傛灉娌℃湁鍙傛暟锛�
            else{
                pstmt = conn.prepareStatement(sql);
                rows = pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
        	log.error("ExecuteUpdate Error:"+sql);
        	log.error(e);
            //DBConnection.getInstance().rollbackTransaction();
        }finally{
        	if(null != pstmt){
        		try {
					pstmt.close();
				} catch (SQLException e) {
					log.error(e);
				}
			}
        	if(null != conn){
        		try {
        			conn.close();
				} catch (SQLException e) {
					log.error(e);
				}
			}           	
        	//DBConnection.getInstance().commitTransaction();
        	//DBConnection.getInstance().closeConnection();
        }
        return rows;
    }
    
	/**
     * 鎵ц鏌ヨ锛�
     * @return
     */
    public List<Hashtable<String, Object>> executeQuery(String sql , List<Object> parameters){
    	Connection conn = null;
    	List<Hashtable<String, Object>> list = new ArrayList<Hashtable<String, Object>>();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        
        try {
        	//conn = DBConnection.getInstance().getConnection();
        	conn = C3P0Connection.getConnection();
            //濡傛灉sql璇彞鏈夊弬鏁帮紱
            if(parameters != null && parameters.size() > 0){
                pstmt = conn.prepareStatement(sql);
                setParameters(pstmt, parameters);
                rs = pstmt.executeQuery();
            }
            //濡傛灉娌℃湁鍙傛暟锛�
            else{
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
            }
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
            	Hashtable<String, Object> htData = new Hashtable<String, Object>(columnCount);
            	for (int i = 1; i <= columnCount; i++) {
            		htData.put(metaData.getColumnName(i), rs.getObject(i)==null ? "Null":rs.getObject(i));   
            	} 
            	list.add(htData);
            }
        } catch (SQLException e) {
        	log.error(e);
        }finally{
        	if(null != pstmt){
        		try {
					pstmt.close();
				} catch (SQLException e) {
					log.error(e);
				}
			}
        	if(null != conn){
        		try {
        			conn.close();
				} catch (SQLException e) {
					log.error(e);
				}
			}        	
        	//DBConnection.getInstance().closeConnection();
        }
        return list;
    }
    public List<Hashtable<String, Object>> executeQuery(String sql){
    	
    	return executeQuery(sql , null);
    }
    
    /**
     * query4OneVal
     * 鏌ヨ杩斿洖涓�涓��
     * @param sql
     * @param parameters
     * @return 鍒涘缓鏃堕棿锛�2016骞�8鏈�4鏃� 涓婂崍10:06:57
     */
    public String query4OneVal(String sql , List<Object> parameters){
    	Connection conn = null;
    	String retVal = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        
        try {
        	//conn = DBConnection.getInstance().getConnection();
        	conn = C3P0Connection.getConnection();
            //濡傛灉sql璇彞鏈夊弬鏁帮紱
            if(parameters != null && parameters.size() > 0){
                pstmt = conn.prepareStatement(sql);
                setParameters(pstmt, parameters);
                rs = pstmt.executeQuery();
            }
            //濡傛灉娌℃湁鍙傛暟锛�
            else{
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
            }
            if(rs.next())
            	retVal = rs.getObject(1).toString();
        } catch (SQLException e) {
            log.error(e);
        }finally{
        	if(null != pstmt){
        		try {
					pstmt.close();
				} catch (SQLException e) {
					log.error(e);
				}
			}
        	if(null != conn){
        		try {
        			conn.close();
				} catch (SQLException e) {
					log.error(e);
				}
			}            	
        	//DBConnection.getInstance().closeConnection();
        }
        return retVal;
    }
    
    
    
    /**
     * 鎵ц鏌ヨ杩斿洖涓�涓璞★紱
     * @return
     */
    public Hashtable<String, Object> query4OneObject(String sql , List<Object> parameters){
    	Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Hashtable<String, Object> htData = null;
        try {
        	//conn = DBConnection.getInstance().getConnection();
        	conn = C3P0Connection.getConnection();
            //濡傛灉sql璇彞鏈夊弬鏁帮紱
            if(parameters != null && parameters.size() > 0){
                pstmt = conn.prepareStatement(sql);
                setParameters(pstmt, parameters);
                rs = pstmt.executeQuery();
            }
            //濡傛灉娌℃湁鍙傛暟锛�
            else{
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
            }
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            if (rs.next()) {
            	htData = new Hashtable<String, Object>(columnCount);
            	for (int i = 1; i <= columnCount; i++) {
            		htData.put(metaData.getColumnName(i), rs.getObject(i)==null ? new String("Null"):rs.getObject(i));   
            	} 
            }
        } catch (SQLException e) {
        	log.error(e);
        }finally{
        	if(null != pstmt){
        		try {
					pstmt.close();
				} catch (SQLException e) {
					log.error(e);
				}
			}
        	if(null != conn){
        		try {
        			conn.close();
				} catch (SQLException e) {
					log.error(e);
				}
			}            	
        	//DBConnection.getInstance().closeConnection();
        }
        return htData;
    }
    
	public static void main(String[] args) {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();  
		
		for (int i = 0; i < 10; i++) {
			//SqlHelper dao = new SqlHelper();
			//List<Hashtable<String, Object>> ls = dao.executeQuery("select * from tmp_lm_test order by table_name");
			cachedThreadPool.execute(new Runnable() {  
				 public void run() {  
					 SqlHelper dao = new SqlHelper();
				     List<Hashtable<String, Object>> ls = 
				    		 dao.executeQuery("select * from tmp_lm_test order by table_name");
				     
				     System.out.println(dao.hashCode() + ":====" + ls.size());
				 }  
			});  

		}
		
	}

}
