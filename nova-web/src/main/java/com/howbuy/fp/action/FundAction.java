package com.howbuy.fp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.howbuy.fp.factor.Fund;
import com.howbuy.fp.factor.FundDAO;

/** 
 * @author LvMeng
 * @version 2017年2月8日 上午9:56:09
 */
@Controller
@RequestMapping("/fund")
public class FundAction {
	private static Logger log = Logger.getLogger(FundAction.class);
	
	@Autowired
	private FundDAO fundDAO;
	
	@RequestMapping("/query")
	@ResponseBody
	public  String queryfunds( HttpServletRequest request){
		
		String jjdm = request.getParameter("jjdm");
		Map<String, Object> map = new HashMap<String, Object>(); 
		List<Fund> funds = fundDAO.getFundListTop10(jjdm);
		map.put("funds",funds);
		
        return JSON.toJSONString(funds);
	}
	
}
