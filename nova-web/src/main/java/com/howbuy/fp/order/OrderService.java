package com.howbuy.fp.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.howbuy.fp.product.Product;
import com.howbuy.fp.product.ProductColorItem;
import com.howbuy.fp.utils.RespResult;

/** 
 * @author LvMeng
 * @version 2017��3��2�� ����8:01:18
 */
@Service
public class OrderService {
	@Autowired
	private OrderDAO orderDAO;

	public OrderDAO getOrderDAO() {
		return orderDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public RespResult<String> addOrder(Order order){
		return orderDAO.insertOrder(order);
	}
	
	public RespResult<List<Hashtable<String, Object>>> getOrders(
			String custName,int start,int limit){
		RespResult<List<Hashtable<String, Object>>> resp = new RespResult<>();
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("custName*",custName);
		map.put("start",start);
		map.put("limit",limit);
		
		List<Hashtable<String, Object>> hst = orderDAO.queryOrderList(map);
		resp.setObj(hst);
		resp.setTotal(orderDAO.queryOrderCount(map));
		return resp;
	}
	
	public RespResult<String> getmyOrdersCount(int myId){
		RespResult<String> resp = new RespResult<>();
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("curOperator",myId);
		resp.setTotal(orderDAO.queryOrderCount(map));
		return resp;
	}
	
	public RespResult<Order> getOrderByID(int orderId){
		RespResult<Order> resp = new RespResult<>();
		Order order = orderDAO.getOrderById(orderId);
		if(order != null)
			resp.setObj(order);
		else {
			resp.setSuccess(false);
			resp.setDesc("未查询到该订单。");
		}
		return resp;
	}
	
	
	
}
