package com.howbuy.fp.order;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.howbuy.fp.user.UserVO;
import com.howbuy.fp.utils.RespResult;

/** 
 * @author LvMeng
 * @version 2017年2月8日 上午9:56:09
 */
@Controller
public class OrderAction {
	private static Logger log = Logger.getLogger(OrderAction.class);
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/orders")
	public  String orders(HttpServletRequest request){
		String custName = "";
		int page = 1;
		int limit = 20;
		if(request.getParameter("pg") != null)
			page = Integer.valueOf(request.getParameter("pg"));
		if(request.getParameter("limit") != null)
			limit = Integer.valueOf(request.getParameter("limit"));
		if(request.getParameter("custName") != null)
			custName = request.getParameter("custName");
		try {
			custName = new String(custName.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		RespResult<List<Hashtable<String, Object>>> prdResp = orderService.getOrders(custName,limit*(page-1),limit);
		request.setAttribute("custName", custName);
		request.setAttribute("ordlist", prdResp.getObj()); 
		request.setAttribute("ordtotal", prdResp.getTotal()); 
		request.setAttribute("curpage", page);
		int pageCount = prdResp.getTotal() % limit == 0 ? prdResp.getTotal()/limit : prdResp.getTotal()/limit +1;
		request.setAttribute("pageCount", pageCount);
        return "/page/order/order_list";
	}
	
	
	@RequestMapping(value ="/order/addorder",method = {RequestMethod.POST })
	@ResponseBody
	public  String addorder(@RequestBody Order order,HttpServletRequest request){
		
		order.setStaffId(getCurrentUser(request).getUserId());
		order.setFlowId(1);
		RespResult<String> respResult = orderService.addOrder(order);
		if(respResult.isSuccess())
			request.getSession().removeAttribute("mycart");
		
        return respResult.toString();
	}
	
	@RequestMapping(value ="/order/auditorder",method = {RequestMethod.POST })
	@ResponseBody
	public  String auditorder(@RequestBody Order order,HttpServletRequest request){
		
		order.setStaffId(getCurrentUser(request).getUserId());
		order.setFlowId(1);
		RespResult<Order> respResult = new RespResult<Order>();
		respResult.setObj(order);
		log.info(respResult.toString());
        return respResult.toString();
	}
	
	@RequestMapping(value ="/order/myordercount",method = {RequestMethod.POST })
	@ResponseBody
	public  String myordercount(HttpServletRequest request){
		
		int myId = this.getCurrentUser(request).getUserId();
		
		RespResult<String> respResult = orderService.getmyOrdersCount(myId);
		
        return respResult.toString();
	}
	
	@RequestMapping(value ="/order/getorder",method = {RequestMethod.GET })
	//@ResponseBody
	public  String getorder(HttpServletRequest request){
		
		int orderId = 0;
		if(request.getParameter("rid") != null){
			orderId = Integer.parseInt(request.getParameter("rid"));
		}
		
		RespResult<Order> respResult = orderService.getOrderByID(orderId);
		
		request.setAttribute("order", respResult.getObj());
		
        return "/page/order/ord_detail";
	}
	
	private UserVO getCurrentUser(HttpServletRequest request){
		RespResult<UserVO> userResp = (RespResult<UserVO>)request.getSession().getAttribute("USER_KEY");
		return userResp.getObj();
	}
	
}
