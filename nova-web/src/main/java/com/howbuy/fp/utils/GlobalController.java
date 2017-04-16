package com.howbuy.fp.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** 
 * @author LvMeng
 * @version 2017年3月27日 下午4:56:11
 */
@Controller
public class GlobalController {
	
	 @ExceptionHandler({Exception.class})   
	 public String exception(HttpServletRequest request,   
	            HttpServletResponse response, Object handler, Exception e) {   
		
		request.setAttribute("errorcode", "500");
		request.setAttribute("message", e.getMessage());
	        
	    return "/page/exception";
	 } 
}
