package com.howbuy.fp.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * 
 * @author jinli.liu 20151226
 *
 */
@SuppressWarnings( { "rawtypes", "unchecked" })
public class FundDao {
	
	private static Logger log = Logger.getLogger(FundDao.class);
	
	public static List query(String sql, Object... params) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			//conn =DBUtils.getConnection("jdbc/howbuyqa");
			//conn = DBConnection.getInstance().getConnection();
			conn = C3P0Connection.getConnection();
//			ps = conn.prepareStatement(sql.toString());
			ps = (PreparedStatement) conn.prepareStatement(sql.toString(),ResultSet.TYPE_FORWARD_ONLY,  
                    ResultSet.CONCUR_READ_ONLY);  
			ps.setFetchDirection(ResultSet.FETCH_REVERSE); 
			ps.setFetchSize(1000);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			rs = ps.executeQuery();
			ResultSetMetaData resultSetMetaData = rs.getMetaData();
			int columns = resultSetMetaData.getColumnCount();
			while (rs.next()) {
				Map m = new HashMap();
				for (int i = 0; i < columns; i++) {
					Object obj = null;
					int type = resultSetMetaData.getColumnType(i + 1);
					int scale = resultSetMetaData.getScale(i + 1);
					String columnName = resultSetMetaData.getColumnName(i + 1);
					switch (type) {
					case Types.LONGVARCHAR:
						obj = rs.getLong(columnName);
						break;
					case Types.CHAR:
						obj = rs.getCharacterStream(columnName);
						break;
					case Types.BIGINT:
						obj = rs.getLong(columnName);
						break;
					case Types.NUMERIC:
						switch (scale) {
						case 0:
							obj = rs.getBigDecimal(columnName);
							break;
						case -127:
							obj = rs.getBigDecimal(columnName);
							break;
						default:
							obj = rs.getBigDecimal(columnName);
						}
						break;
					case Types.VARCHAR:
						obj = rs.getString(columnName);
						break;
					case Types.DATE:
						obj = rs.getDate(columnName);
						break;
					case Types.TIMESTAMP:
						obj = rs.getDate(columnName);
						break;
					case Types.BLOB:
						obj = rs.getBlob(columnName);
						break;
					case Types.CLOB:
						obj = rs.getClob(columnName);
						break;
					default:
						obj = rs.getString(columnName);
					}
					m.put(columnName.toUpperCase(), obj);
				}
				list.add(m);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			if(null != rs){
				rs.close();
			}
			if(null != ps){
				ps.close();
			}
			if(null != conn){
				conn.close();
			}			
			//DBConnection.getInstance().closeConnection();
			//DBUtils.closeConnection(conn);
		}
		return list;
	}
	
	public static void save(String[] sqls) throws Exception {
		Connection conn = null;
		Statement stat = null;
		try {
			//conn =DBUtils.getConnection("jdbc/howbuyqa");
			//conn = DBConnection.getInstance().getConnection();
			conn = C3P0Connection.getConnection();
			stat = (Statement) conn.createStatement();
			conn.setAutoCommit(false);
			for(String sql :sqls){
				stat.addBatch(sql);
			}
			stat.executeBatch();
			conn.commit();
		} catch (Exception e) {
			if(null != conn)
				conn.rollback();
			log.error(e);
		} finally {
			if(null != stat){
				stat.close();
			}
			if(null != conn){
				conn.close();
			}	
			//DBConnection.getInstance().closeConnection();
			//DBUtils.closeConnection(conn);
		}
	}

//	public static void main(String[] args) {
//		
//		String sql = "select DISTINCT a.jjdm, a.jjjc, a.jjfl, a.ejfl, b.fysx from funddb.jjxx1 a left join funddb.flxx b on a.jjdm = b.jjdm where  b.flzx = '1001'";
//		try {
//			List<Map<String, Object>> list = query(sql);
//			System.out.println("====:"+list.size());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}