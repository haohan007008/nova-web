package com.howbuy.fp.user;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.howbuy.fp.utils.RespResult;

/** 
 * @author LvMeng
 * @version 2017年3月3日 下午1:30:19
 */
public class AppFilter implements Filter {
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		 	HttpServletRequest req = (HttpServletRequest)request;  
		    HttpServletResponse res = (HttpServletResponse)response;  
		      
		    HttpSession session = req.getSession(true);  
		    String path = req.getRequestURI();
		    
		    System.out.println("request:" + path);
		    
		    if(path.indexOf("login.do") > -1 || path.indexOf("authcode.do") > -1) {
		    	chain.doFilter(request, response);
		    	return ;
		    }
		    RespResult<UserVO> userResp = (RespResult<UserVO>)session.getAttribute("USER_KEY");
		    String root = req.getContextPath(); 
		    
		    if (userResp!= null) {        
		        chain.doFilter(request, response); 
		    } else{
		    	res.setHeader("Cache-Control", "no-store");  
		    	res.setDateHeader("Expires", 0);  
		    	res.setHeader("Prama", "no-cache");  
		        res.sendRedirect(root + "/login.do"); 
		    }  
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}
