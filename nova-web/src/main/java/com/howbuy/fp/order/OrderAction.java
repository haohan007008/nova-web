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
import com.howbuy.fp.utils.Constants;
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
		int staffId = 0;
		RespResult<List<Hashtable<String, Object>>> prdResp = new RespResult<List<Hashtable<String, Object>>>();
		
		if(request.getParameter("m") != null && !"".equals(request.getParameter("m"))){
			String orderClz = request.getParameter("m");
			staffId = this.getCurrentUser(request).getUserId();
			if(Constants.ORDER_MY_CREATED.equals(orderClz)){
				//我创建的
				prdResp = orderService.getOrders(custName,limit*(page-1),limit,staffId);
			}else if(Constants.ORDER_MY_HANDED.equals(orderClz)){
				//我经手的
				prdResp = orderService.getMyHanldedOrders(custName,limit*(page-1),limit,staffId);
			}else if(Constants.ORDER_MY_HANDING.equals(orderClz)){
				//待我处理的
				prdResp = orderService.getMyOrders(custName,limit*(page-1),limit,staffId);
			}
			request.setAttribute("orderType", orderClz);
		}else 
			prdResp = orderService.getOrders(custName,limit*(page-1),limit,staffId);
		
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
	
	/*
	 * 经理审核
	 * */
	@RequestMapping(value ="/order/auditorder",method = {RequestMethod.POST })
	@ResponseBody
	public  String auditorder(@RequestBody Order order,HttpServletRequest request){
		
		order.setStaffId(getCurrentUser(request).getUserId());
		order.setFlowId(1);
		RespResult<String> respResult = orderService.updateOrder(order);
		
		log.info(respResult.toString());
        return respResult.toString();
	}
	
	/*
	 * 业务审核，提交财务审核
	 * */
	@RequestMapping(value ="/order/buzaudit",method = {RequestMethod.POST })
	@ResponseBody
	public  String buzaudit(@RequestBody Order order,HttpServletRequest request){
		
		order.setStaffId(getCurrentUser(request).getUserId());
		order.setFlowId(1);
		RespResult<String> respResult = new RespResult<String>();
		
		log.info(respResult.toString());
        return respResult.toString();
	}
	
	/*
	 * 财务审核后归档
	 * */
	@RequestMapping(value ="/order/finaudit",method = {RequestMethod.POST })
	@ResponseBody
	public  String finaudit(@RequestBody Order order,HttpServletRequest request){
		
		order.setStaffId(getCurrentUser(request).getUserId());
		order.setFlowId(1);
		RespResult<String> respResult = new RespResult<String>();
		
		log.info(respResult.toString());
        return respResult.toString();
	}
	
	@RequestMapping(value ="/order/myordercount",method = {RequestMethod.POST })
	@ResponseBody
	public  String myordercount(HttpServletRequest request){
		
		UserVO userVO = this.getCurrentUser(request);
		
		int myId  = 0 ;
		if(userVO  != null )
			myId = userVO.getUserId();
		
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
		
		UserVO userVO = this.getCurrentUser(request);
		
		if(userVO == null){
			request.setAttribute("errorcode", "SessionTimeOut");
			request.setAttribute("message", "登录信息过期！");
			return "/page/error";
		}
		if(respResult.getObj().getCurNodeId() ==2 && 
				userVO.getUserId() == respResult.getObj().getCurOperatorId())
			return "/page/order/ord_detail";
		else return "/page/order/ord_view";
	}
	
	private UserVO getCurrentUser(HttpServletRequest request){
		RespResult<UserVO> userResp = (RespResult<UserVO>)request.getSession().getAttribute("USER_KEY");
		return userResp.getObj();
	}
	
}
