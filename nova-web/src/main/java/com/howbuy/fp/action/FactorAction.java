package com.howbuy.fp.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.howbuy.fp.factor.Factor;
import com.howbuy.fp.factor.FactorDAO;
import com.howbuy.fp.utils.PagedResult;
import com.mchange.v1.util.ArrayUtils;

/** 
 * @author LvMeng
 * @version 2017年2月8日 上午9:56:09
 */
@Controller
@RequestMapping("/factor")
public class FactorAction {
	private static Logger log = Logger.getLogger(FactorAction.class);
	
	@Autowired
	private FactorDAO factorDAO;
	
	@RequestMapping("/list")
	public  String getFactors(HttpServletRequest request){
        return "/page/data/fundEstimate";
	}
	
	@RequestMapping("/query")
	@ResponseBody
	public  String queryFactors( HttpServletRequest request){
		
		String tradeDate = request.getParameter("tradeDate");
		String jjdm = request.getParameter("jjdm");
		int sEcho = Integer.valueOf(request.getParameter("sEcho"));
		int limit = Integer.valueOf(request.getParameter("iDisplayLength"));
		int start = Integer.valueOf(request.getParameter("iDisplayStart"));
		
		System.out.println("sEcho:"+sEcho);
		Map<String, Object> map = new HashMap<String, Object>(); 
		
		
		PagedResult<Factor> result = factorDAO.getFactors(tradeDate,jjdm,(start/limit)+1);
		
		map.put("sEcho", sEcho);
		map.put("data",result.getDataList());
		map.put("recordsTotal",result.getTotal());
		//map.put("recordsFiltered",result.getTotal());
		map.put("tradeDate",tradeDate);
		map.put("jjdm",jjdm);
		
        return JSON.toJSONString(map);
	}
	
	@RequestMapping("/hello")
    public String hello(){        
        return "hello";
    }
	
	@RequestMapping("/factorcompare")
	public  String factorcompare(HttpServletRequest request){
        return "/page/data/factorCompare";
	}
	
	@RequestMapping("/getfactordiff")
	@ResponseBody
	public  String getfactordiff(HttpServletRequest request){
		String jjdm = request.getParameter("jjdm");
		Map<String, Object> map = factorDAO.getFactorDiff(jjdm, 100);
		
        return JSON.toJSONString(map);
	}
}
