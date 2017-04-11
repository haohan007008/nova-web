package com.howbuy.fp.order;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.howbuy.fp.product.Product;
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
	
	
	@RequestMapping("/productorders")
	public  String productorders(HttpServletRequest request,HttpServletResponse response){
		String prdNo = "";
	
		if(request.getParameter("prdNo") != null)
			prdNo = request.getParameter("prdNo");
		
		RespResult<List<Product>> prdResp = orderService.queryProductOrder(prdNo);
		
		request.setAttribute("prdNo", prdNo);
		request.setAttribute("list", prdResp.getObj()); 
		request.setAttribute("user", getCurrentUser(request,response));
        return "/page/order/prd_order";
	}
	
	@RequestMapping("/productorders1")
	public  String productorders1(HttpServletRequest request,HttpServletResponse response){
		String prdNo = "";
	
		if(request.getParameter("prdNo") != null)
			prdNo = request.getParameter("prdNo");
		
		RespResult<List<Product>> prdResp = orderService.queryProductOrder1(prdNo);
		
		request.setAttribute("prdNo", prdNo);
		request.setAttribute("list", prdResp.getObj()); 
		request.setAttribute("user", getCurrentUser(request,response));
        return "/page/order/prd_order_store_vw_bk";
	}
	
	@RequestMapping("/orders")
	public  String orders(HttpServletRequest request,HttpServletResponse response){
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
			staffId = this.getCurrentUser(request,response).getUserId();
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
	public  String addorder(@RequestBody Order order,HttpServletRequest request,HttpServletResponse response){
		RespResult<String> respResult = new RespResult<String>();
		UserVO userVO = getCurrentUser(request,response);
		if(userVO!= null){
			order.setStaffId(userVO.getUserId());
			order.setFlowId(1);
			respResult = orderService.addOrder(order);
			if(respResult.isSuccess())
				request.getSession().removeAttribute("mycart");
		}else {
			respResult.setSuccess(false);
			respResult.setDesc("用户登录已经过期，请重新登录！");
		}
        return respResult.toString();
	}
	
	/*
	 * 经理审核
	 * */
	@RequestMapping(value ="/order/auditorder",method = {RequestMethod.POST })
	@ResponseBody
	public  String auditorder(@RequestBody Order order,HttpServletRequest request,HttpServletResponse response){
		
		order.setStaffId(getCurrentUser(request,response).getUserId());
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
	public  String buzaudit(@RequestBody Order order,HttpServletRequest request,HttpServletResponse response){
		
		order.setStaffId(getCurrentUser(request,response).getUserId());
		order.setFlowId(1);
		order.setCurNodeId(4);
		RespResult<String> respResult = orderService.buzAduit(order);
		
		log.info(respResult.toString());
        return respResult.toString();
	}
	
	@RequestMapping(value ="/order/cancelOrder",method = {RequestMethod.POST })
	@ResponseBody
	public  String cancelOrder(@RequestBody Order order,HttpServletRequest request,HttpServletResponse response){
		
		order.setStaffId(getCurrentUser(request,response).getUserId());
		order.setFlowId(1);
		order.setCurNodeId(4);
		RespResult<String> respResult = orderService.cancelOrder(order);
		
		log.info(respResult.toString());
        return respResult.toString();
	}
	
	/*
	 * 财务审核后归档
	 * */
	@RequestMapping(value ="/order/finaudit",method = {RequestMethod.POST })
	@ResponseBody
	public  String finaudit(@RequestBody Order order,HttpServletRequest request,HttpServletResponse response){
		
		order.setStaffId(getCurrentUser(request,response).getUserId());
		order.setFlowId(1);
		order.setCurNodeId(5);
		RespResult<String> respResult = orderService.finAduit(order);
		
		log.info(respResult.toString());
        return respResult.toString();
	}
	
	@RequestMapping(value ="/order/myordercount",method = {RequestMethod.POST })
	@ResponseBody
	public  String myordercount(HttpServletRequest request,HttpServletResponse response){
		
		UserVO userVO = this.getCurrentUser(request,response);
		
		int myId  = 0 ;
		if(userVO  != null )
			myId = userVO.getUserId();
		
		RespResult<String> respResult = orderService.getmyOrdersCount(myId);
		
        return respResult.toString();
	}
	
	@RequestMapping(value ="/order/getorder",method = {RequestMethod.GET })
	//@ResponseBody
	public  String getorder(HttpServletRequest request,HttpServletResponse response){
		
		int orderId = 0;
		if(request.getParameter("rid") != null){
			orderId = Integer.parseInt(request.getParameter("rid"));
		}
		
		RespResult<Order> respResult = orderService.getOrderByID(orderId);
		
		request.setAttribute("order", respResult.getObj());
		
		UserVO userVO = this.getCurrentUser(request,response);
		
		if(userVO == null){
			request.setAttribute("errorcode", "SessionTimeOut");
			request.setAttribute("message", "登录信息过期！");
			return "/page/error";
		}
		request.setAttribute("user", getCurrentUser(request,response));
		if(respResult.getObj().getCurNodeId() ==2 && 
				userVO.getUserId() == respResult.getObj().getCurOperatorId())
			return "/page/order/ord_detail"; //主管审核需要修改订单数据挑战到detail
		else if(respResult.getObj().getCurNodeId() !=2 && 
				userVO.getUserId() == respResult.getObj().getCurOperatorId())
			return "/page/order/ord_view";
		else return "/page/order/ord_vw";
	}
	
	private UserVO getCurrentUser(HttpServletRequest request,HttpServletResponse response){
		RespResult<UserVO> userResp = (RespResult<UserVO>)request.getSession().getAttribute("USER_KEY");
		if(userResp == null){
			request.setAttribute("errorcode", "SessionTimeOut");
			request.setAttribute("message", "登录信息过期！");
			try {
				request.getRequestDispatcher("/page/error.jsp").forward(request,response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return userResp.getObj();
	}
	
	
	@RequestMapping(value ="/order/orderstat",method = {RequestMethod.GET })
	//@ResponseBody
	public  String orderstat(HttpServletRequest request){
		request.setAttribute("orders", orderService.reportOrdersDay("").getObj());
		return "/page/order/sale_order";
	}
}
