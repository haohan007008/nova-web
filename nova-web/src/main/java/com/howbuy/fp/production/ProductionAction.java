package com.howbuy.fp.production;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
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

import com.howbuy.fp.order.Order;
import com.howbuy.fp.order.OrderService;
import com.howbuy.fp.product.Product;
import com.howbuy.fp.user.UserVO;
import com.howbuy.fp.utils.Constants;
import com.howbuy.fp.utils.RespResult;

/** 
 * @author LvMeng
 * @version 2017年2月8日 上午9:56:09
 */
@Controller
public class ProductionAction {
	private static Logger log = Logger.getLogger(ProductionAction.class);
	
	@Autowired
	private ProductionService productionService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/addProduction", produces = "application/json; charset=utf-8")
	public  String prdItems(HttpServletRequest request, HttpServletResponse response,HttpSession session){
		
		return "/page/production/production_plan";
	}
	
	@RequestMapping("/productionitems")
	public  String productorders1(HttpServletRequest request,HttpServletResponse response){
		String its = "";
	
		if(request.getParameter("its") != null)
			its = request.getParameter("its");
		
		RespResult<List<Product>> prdResp = orderService.queryProductOrderbyIts(its);
		//if(prdResp != null && prdResp.getObj() !=null && prdResp.getObj().size() >0){
		//	for(Product product : prdResp.getObj()){
		//		productionService.unionList(product, request);
		//	}
		
		//}
		
		request.setAttribute("its", its);
		request.setAttribute("list",prdResp.getObj()); 
		request.setAttribute("m3date",Constants.getAfter3MDate()); 
		//request.setAttribute("user", getCurrentUser(request,response));
		
        return "/page/production/production_plan";
	}
	
	@RequestMapping(value ="/production/add",method = {RequestMethod.POST })
	@ResponseBody
	public  String addorder(@RequestBody Production production,HttpServletRequest request,HttpServletResponse response){
		RespResult<String> respResult = new RespResult<String>();
		UserVO userVO = getCurrentUser(request,response);
		if(userVO!= null){
			production.setCreatorId(userVO.getUserId());
			production.setFlowId(2);
			if(production.getId() == 0)
				respResult = productionService.addProduction(production);
			else respResult = productionService.editProduction(production);
			//if(respResult.isSuccess())
			//	request.getSession().removeAttribute("mycart");
		}else {
			respResult.setSuccess(false);
			respResult.setDesc("用户登录已经过期，请重新登录！");
		}
        return respResult.toString();
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
	
	@RequestMapping("/plans")
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
		RespResult<List<Production>> prdResp = new RespResult<List<Production>>();
		
		//if(request.getParameter("m") != null && !"".equals(request.getParameter("m"))){
		//	String orderClz = request.getParameter("m");
		//	staffId = this.getCurrentUser(request,response).getUserId();
//			if(Constants.ORDER_MY_CREATED.equals(orderClz)){
//				//我创建的
//				prdResp = orderService.getOrders(custName,limit*(page-1),limit,staffId);
//			}else if(Constants.ORDER_MY_HANDED.equals(orderClz)){
//				//我经手的
//				prdResp = orderService.getMyHanldedOrders(custName,limit*(page-1),limit,staffId);
//			}else if(Constants.ORDER_MY_HANDING.equals(orderClz)){
//				//待我处理的
//				prdResp = orderService.getMyOrders(custName,limit*(page-1),limit,staffId);
//			}
//			request.setAttribute("orderType", orderClz);
		//}else 
			prdResp = productionService.getProductions(custName,limit*(page-1),limit,staffId);
		
		request.setAttribute("custName", custName);
		request.setAttribute("productionlist", prdResp.getObj()); 
		request.setAttribute("productiontotal", prdResp.getTotal()); 
		request.setAttribute("curpage", page);
		int pageCount = prdResp.getTotal() % limit == 0 ? prdResp.getTotal()/limit : prdResp.getTotal()/limit +1;
		request.setAttribute("pageCount", pageCount);
        return "/page/production/prdt_list";
	}
	
	@RequestMapping(value ="/production/getprodt",method = {RequestMethod.GET })
	//@ResponseBody
	public  String getorder(HttpServletRequest request,HttpServletResponse response){
		
		int pId = 0;
		if(request.getParameter("rid") != null){
			pId = Integer.parseInt(request.getParameter("rid"));
		}
		
		RespResult<Production> respResult = productionService.getProductionsById(pId);
		
		request.setAttribute("production", respResult.getObj());
		
		UserVO userVO = this.getCurrentUser(request,response);
		
		if(userVO == null){
			request.setAttribute("errorcode", "SessionTimeOut");
			request.setAttribute("message", "登录信息过期！");
			return "/page/error";
		}
		request.setAttribute("user", getCurrentUser(request,response));
		if(respResult.getObj().getCurNode() == 8 && 
				userVO.getUserId() == respResult.getObj().getCurOperatorId())
			return "/page/production/prodt_edit"; //修改合同
		else if(respResult.getObj().getCurNode() !=8 && 
				userVO.getUserId() == respResult.getObj().getCurOperatorId())
			return "/page/production/prodt_view";
		else return "/page/production/prodt_vw";
	}
	
	
	/*
	 * 经理审核
	 * */
	@RequestMapping(value ="/production/manageaudit",method = {RequestMethod.POST })
	@ResponseBody
	public  String auditorder(@RequestBody Production production,HttpServletRequest request,HttpServletResponse response){
		
		production.setCurOperatorId(getCurrentUser(request,response).getUserId());
		//order.setFlowId(1);
		RespResult<String> respResult = productionService.auditorder(production);
		log.info(respResult.toString());
        return respResult.toString();
	}
}
