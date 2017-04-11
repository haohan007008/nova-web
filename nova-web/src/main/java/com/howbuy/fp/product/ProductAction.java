package com.howbuy.fp.product;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.howbuy.fp.utils.Constants;
import com.howbuy.fp.utils.ImageHelper;
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
	
	@RequestMapping(value ="/product/items",method = {RequestMethod.POST })
	@ResponseBody
	public  String updateItems(@RequestBody List<ProductColorItem> list,HttpServletRequest request){
		
		RespResult<String> respResult = productService.updateItems(list);
	
        return respResult.toString();
	}
	
	/**
	 * prdItems 查询产品所有款式
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @return 创建时间：2017年3月29日 下午3:44:57
	 */
	@RequestMapping(value="/prdItems", produces = "application/json; charset=utf-8")
	public  String prdItems(HttpServletRequest request, HttpServletResponse response,HttpSession session){
		
		int pid = 1;
		
		if(request.getParameter("pid") != null)
			pid = Integer.valueOf(request.getParameter("pid"));
		
		RespResult<List<ProductColorItem>> prdResp = productService.getProductItems(pid);
		request.setAttribute("items", prdResp.getObj());
		request.setAttribute("prdId", pid);
		return "/page/product/prdItems";
	}
	
	/**
	 * upDownShelf 产品上下架
	 *
	 * @param request
	 * @return 创建时间：2017年3月29日 下午3:35:50
	 */
	@RequestMapping(value ="/product/shelf",method = {RequestMethod.GET })
	@ResponseBody
	public  String upDownShelf(HttpServletRequest request){
		int prdId = 0;
		int status = -1;
		if(request.getParameter("pid") != null){
			prdId = Integer.valueOf(request.getParameter("pid"));
		}
		
		if(request.getParameter("status") != null){
			status = Integer.valueOf(request.getParameter("status"));
		}
		if(status == 1)
			return JSON.toJSONString(productService.upShelf(prdId));
		else if(status == 0)
			return JSON.toJSONString(productService.downShelf(prdId));
		else {
			RespResult<String> result = new RespResult<String>();
			result.setSuccess(false);
			result.setDesc("非法参数！");
			return JSON.toJSONString(result);
		}
       
	}
	
	/**
	 * getproducts 查询产品列表
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @return 创建时间：2017年3月29日 下午3:36:04
	 */
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
	
	/**
	 * products 产品列表，用于产品管理
	 *
	 * @param request
	 * @return 创建时间：2017年3月29日 下午3:36:47
	 */
	@RequestMapping(value="/products", produces = "application/json; charset=utf-8")
	public  String products(HttpServletRequest request){
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
		
		RespResult<List<Hashtable<String, Object>>> prdResp = productService.getAllProducts(prdName,limit*(page-1),limit);
		request.setAttribute("prdName", prdName);
		request.setAttribute("prdlist", prdResp.getObj()); 
		request.setAttribute("prdtotal", prdResp.getTotal()); 
		request.setAttribute("curpage", page);
		int pageCount = prdResp.getTotal() % limit == 0 ? prdResp.getTotal()/limit : prdResp.getTotal()/limit +1;
		request.setAttribute("pageCount", pageCount);
        return "/page/product/prdManage";
	}
	
	/**
	 * editproduct 产品编辑页面
	 *
	 * @param request
	 * @return 创建时间：2017年3月29日 下午3:37:12
	 */
	@RequestMapping("/product")
	public  String editproduct(HttpServletRequest request){
		
		int prdId = 0;
		if(request.getParameter("pid") != null){
			prdId = Integer.valueOf(request.getParameter("pid"));
		
			Product product = productService.getProductById(prdId);
			System.out.println(JSON.toJSONString(product));
			request.setAttribute("product", product);
		}
		RespResult<List<Hashtable<String, Object>>> respResult = 
				productService.queryProductCatalogList();
		request.setAttribute("catalogs", respResult.getObj());
        return "/page/product/prd_add";
	}
	
	 @ExceptionHandler({Exception.class})   
	 public String exception(HttpServletRequest request,   
	            HttpServletResponse response, Object handler, Exception e) {   
		
		request.setAttribute("errorcode", e.toString());
		
	    StringBuffer sb = new StringBuffer();
	    
	    for(StackTraceElement elem : e.getStackTrace()) {
	    	sb.append(elem).append("<br/>");
        }
	    request.setAttribute("message", sb.toString());
	    return "/page/exception";
	 }   
	
	/**
	 * AddOrUpdateproduct 新增或更新产品
	 *
	 * @param product
	 * @param request
	 * @param response
	 * @return 创建时间：2017年3月29日 下午3:37:35
	 */
	@SuppressWarnings("static-access")
	@RequestMapping("/AddOrUpdateproduct")
	public  String AddOrUpdateproduct(Product product,HttpServletRequest request,HttpServletResponse response){
		String path = request.getSession().getServletContext().getRealPath("/");
		
		//int i = 1 / 0 ;
		
		if(product.getPrdSmallImg1() != null && product.getPrdSmallImg1().getSize() > 0){
			
			
			String imageName = String.valueOf(System.currentTimeMillis());
			
			String suffix = product.getPrdSmallImg1().getOriginalFilename().substring(
					product.getPrdSmallImg1().getOriginalFilename().length()-3);
			
			try {
				product.getPrdSmallImg1().transferTo(new File(path + "images\\prd\\"+imageName+"."+suffix));
				
				ImageHelper.getImageHelper().scaleImageWithParams(
						path + "/images/prd/"+imageName+"."+suffix, 
						path + "/images/prd/"+imageName+"_60x60."+suffix, 60, 60, true, suffix);
				
				product.setPrdSmallImg("images/prd/"+imageName+"_60x60."+suffix);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(product.getPrdImg1() != null && product.getPrdImg1().getSize() > 0){
			String imageName = String.valueOf(System.currentTimeMillis());
			
			String suffix = product.getPrdImg1().getOriginalFilename().substring(
						product.getPrdImg1().getOriginalFilename().length()-3);
			
			try {
				product.getPrdImg1().transferTo(new File(path + "images\\prd\\"+imageName+"."+suffix));

				ImageHelper.getImageHelper().scaleImageWithParams(
						path + "images\\prd\\"+imageName+"."+suffix, 
						path + "/images/prd/"+imageName+"_300x300."+suffix, 300, 300, true, suffix);
				
				product.setPrdImg("images/prd/"+imageName+"_300x300."+suffix);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		RespResult<String> respResult = new RespResult<String>();
		if(product.getId() == -1){
			respResult = productService.addProduct(product);
		}else respResult = productService.updateProduct(product);
		
		request.setAttribute("status", respResult.toString());
		
		return "/page/product/prd_add";
		
	}
	
	/**
	 * productcart 购物篮请求
	 *
	 * @param request
	 * @return 创建时间：2017年3月29日 下午3:37:53
	 */
	@RequestMapping("/productcart")
	public  String productcart(HttpServletRequest request){
		
		List<Product> list = (List<Product>)request.getSession().getAttribute("mycart");
		request.setAttribute("products", productService.getProductListDetail(list));
		
        return "/page/product/prd_cart";
	}
	
	/**
	 * prddetail 产品详情
	 *
	 * @param request
	 * @return 创建时间：2017年3月29日 下午3:38:09
	 */
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
	
	
	/**
	 * addcart 将产品加入购物车
	 *
	 * @param product
	 * @param request
	 * @return 创建时间：2017年3月29日 下午3:38:21
	 */
	@RequestMapping(value ="/product/addcart",method = {RequestMethod.POST })
	@ResponseBody
	public  String addcart(@RequestBody Product product,HttpServletRequest request){
		
		RespResult<List<Product>> respResult = new RespResult<>();
		List<Product> prds = unionList(product,request);
		respResult.setObj(prds);
		respResult.setTotal(prds.size());
        return respResult.toString();
	}
	
	/**
	 * addcart 将商品从购物车移除
	 *
	 * @param request
	 * @return 创建时间：2017年3月29日 下午3:38:39
	 */
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
