package com.howbuy.fp.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * @author meng.lv
 *
 */
public class Constants {
	
	
	public final static String NULL = "Null";
	
	public final static String ORDER_MY_HANDING = "1";
	public final static String ORDER_MY_HANDED = "2";
	public final static String ORDER_MY_CREATED = "3";
	
	
	
	public static String lpad(String str,int num,String pad){  
	    String n_str=str;  
	    if(str==null)  
	    n_str= " ";  
	    for(int i=str.length();i <num;i++){  
	    n_str=pad+n_str;  
	    }  
	    return n_str;  
	  }   
	 public static String getUUID(){ 
	        String s = UUID.randomUUID().toString(); 
	        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
	 } 
	 
	public static String ParsePageStr(String sql,String pgCol){
		return "select * from ("+ sql + ") t_  where t_."+pgCol+" >= ? and t_."+pgCol+" <= ?";
	}
	
	public static boolean isBlank(Object str){
		if(str == null || "".equals((str.toString()).trim()) || "Null".equals((str.toString()).trim()))
				return true;
		else return false;
	}
	
	public static BigDecimal toDouble(Object obj){
		
		if(Constants.isBlank(obj))
			return new BigDecimal(0);
		
		try {
			return new BigDecimal(Double.parseDouble(obj.toString())).setScale(6,BigDecimal.ROUND_HALF_UP);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static String parseDate(String date,String fromFormatStr,String toFormatStr){
		SimpleDateFormat formatter = new SimpleDateFormat (fromFormatStr); 
		
		try {
			SimpleDateFormat toformatter = new SimpleDateFormat (toFormatStr); 
			Date ctime = formatter.parse(date);
			return toformatter.format(ctime);
		} catch (ParseException e) {
			e.printStackTrace();
			return date;
		}
		
	}
	
	public static void main(String[] args){
		String str= Constants.parseDate("2017-01-05", "yyyy-MM-dd", "yyyyMMdd");
		System.out.println(str);
	}
	
	 public static String getIP(HttpServletRequest request) {
	        String ip = request.getHeader("x-forwarded-for");
	        if (!checkIP(ip)) {
	            ip = request.getHeader("Proxy-Client-IP");
	        }
	        if (!checkIP(ip)) {
	            ip = request.getHeader("WL-Proxy-Client-IP");
	        }
	        if (!checkIP(ip)) {
	            ip = request.getRemoteAddr();
	        }
	        return ip;
	    }
	    private static boolean checkIP(String ip) {
	        if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip)
	                || ip.split(".").length != 4) {
	            return false;
	        }
	        return true;
	    }
}
