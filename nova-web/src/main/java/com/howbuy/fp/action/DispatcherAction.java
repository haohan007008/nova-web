package com.howbuy.fp.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dispatcherAction")
public class DispatcherAction {

	@RequestMapping("/query")
//	@ResponseBody
	public String query(HttpServletRequest request) {
		System.out.println(1);
		return "data/fundEstimate";
	}
}
