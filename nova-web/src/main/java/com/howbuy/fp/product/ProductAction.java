package com.howbuy.fp.product;

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
import com.howbuy.fp.utils.RespResult;

/** 
 * @author LvMeng
 * @version 2017年2月8日 上午9:56:09
 */
@Controller
public class ProductAction {
	private static Logger log = Logger.getLogger(ProductAction.class);
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value ="/product/autoprd",method = {RequestMethod.GET })
	@ResponseBody
	public  String autoprd(HttpServletRequest request){
		String prdName = "";
		
        return JSON.toJSONString(productService.autoProductList(prdName).getObj());
	}
	
	@RequestMapping(value="/getproducts", produces = "application/json; charset=utf-8")
	public  String getproducts(HttpServletRequest request, HttpServletResponse response,HttpSession session){
		
		String prdName = "";
		int page = 1;
		int limit = 20;
		int start = 0;
		if(request.getParameter("pg") != null)
			page = Integer.valueOf(request.getParameter("pg"));
		if(request.getParameter("limit") != null)
			limit = Integer.valueOf(request.getParameter("limit"));
		if(request.getParameter("prdName") != null)
			prdName = request.getParameter("prdName");
		try {
			prdName = new String(prdName.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		RespResult<List<Hashtable<String, Object>>> prdResp = productService.getProducts(prdName,limit*(page-1),limit);
		request.setAttribute("prdName", prdName);
		request.setAttribute("prdlist", prdResp.getObj()); 
		request.setAttribute("prdtotal", prdResp.getTotal()); 
		request.setAttribute("curpage", page);
		int pageCount = prdResp.getTotal() % limit == 0 ? prdResp.getTotal()/limit : prdResp.getTotal()/limit +1;
		request.setAttribute("pageCount", pageCount);
		
		return "/page/product/prd_list";
	}
	
	@RequestMapping("/products")
	public  String products(HttpServletRequest request){
        return "/page/product/prd_list";
	}
	
	@RequestMapping("/productcart")
	public  String productcart(HttpServletRequest request){
		
		List<Product> list = (List<Product>)request.getSession().getAttribute("mycart");
		request.setAttribute("products", productService.getProductListDetail(list));
		
        return "/page/product/prd_cart";
	}
	
	@RequestMapping("/prddetail")
	public  String prddetail(HttpServletRequest request){
		int prdId = 0;
		if(request.getParameter("pid") != null)
			prdId = Integer.valueOf(request.getParameter("pid"));
		
		Product product = productService.getProductById(prdId);
		System.out.println(JSON.toJSONString(product));
		request.setAttribute("product", product);
        return "/page/product/prd_detail";
	}
	
	
	@RequestMapping(value ="/product/addcart",method = {RequestMethod.POST })
	@ResponseBody
	public  String addcart(@RequestBody Product product,HttpServletRequest request){
		
		RespResult<List<Product>> respResult = new RespResult<>();
		List<Product> prds = unionList(product,request);
		respResult.setObj(prds);
		respResult.setTotal(prds.size());
        return respResult.toString();
	}
	
	@RequestMapping(value ="/product/delcart",method = {RequestMethod.GET })
	@ResponseBody
	public  String addcart(HttpServletRequest request){
		int prdId = 0,cid = 0;
		
		if(request.getParameter("pid") != null)
			prdId = Integer.valueOf(request.getParameter("pid"));
		
		if(request.getParameter("cid") != null)
			cid = Integer.valueOf(request.getParameter("cid"));
		
		@SuppressWarnings("unchecked")
		List<Product> list = (List<Product>)request.getSession().getAttribute("mycart");
		
		list = productService.delCart(list, prdId, cid);
		request.getSession().setAttribute("mycart",list);
		RespResult<List<Product>> respResult = new RespResult<>();
		respResult.setTotal(list.size());
        return respResult.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> unionList(Product product,
			HttpServletRequest request){
		
		List<Product> list = (List<Product>)request.getSession().getAttribute("mycart");
				
		if(list != null && list.size() > 0){
			boolean has = false;
			for (int i = 0; i < list.size(); i++) {
				Product prd = (Product)list.get(i);
				if(prd.getId() == product.getId()){
					has = true;
					list.get(i).unionItems(product.getItems());
					break;
				}
			}
			if(!has)
				list.add(product);
		}else{
			list = new ArrayList<>();
			list.add(product);
		};
		log.debug((JSON.toJSONString(list)));
		request.getSession().setAttribute("mycart",list);
		return list;
	}
}
