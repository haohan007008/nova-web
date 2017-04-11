package com.howbuy.fp.order;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.howbuy.fp.product.Product;
import com.howbuy.fp.utils.RespResult;

/** 
 * @author LvMeng
 * @version 2017��3��2�� ����8:01:18
 */
/**
 *
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
	
	
	public RespResult<List<Product>> queryProductOrder(String prdNo) {
		RespResult<List<Product>> resp = new RespResult<>();
		List<Product> hst = orderDAO.queryProductOrder(prdNo+"%");
		resp.setObj(hst);
		return resp;
	}
	
	public RespResult<List<Product>> queryProductOrder1(String prdNo) {
		RespResult<List<Product>> resp = new RespResult<>();
		List<Product> hst = orderDAO.queryProductOrderStore(prdNo+"%");
		resp.setObj(hst);
		return resp;
	}

	public RespResult<String> addOrder(Order order){
		//this.addorderlog(order, order.getStaffId(), "1", order.getRemark());
		return orderDAO.insertOrder(order);
	}
	
	public RespResult<List<Hashtable<String, Object>>> getOrders(
			String custName,int start,int limit,int staffId){
		RespResult<List<Hashtable<String, Object>>> resp = new RespResult<>();
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("custName*",custName);
		if(staffId > 0)
			map.put("staffId", staffId);
		map.put("start",start);
		map.put("limit",limit);
		
		List<Hashtable<String, Object>> hst = orderDAO.queryOrderList(map);
		resp.setObj(hst);
		resp.setTotal(orderDAO.queryOrderCount(map));
		return resp;
	}
	
	public RespResult<List<Hashtable<String, Object>>> getMyOrders(
			String custName,int start,int limit,int staffId){
		RespResult<List<Hashtable<String, Object>>> resp = new RespResult<>();
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("custName*",custName);
		if(staffId > 0)
			map.put("curOperator", staffId);
		map.put("start",start);
		map.put("limit",limit);
		
		List<Hashtable<String, Object>> hst = orderDAO.queryOrderList(map);
		resp.setObj(hst);
		resp.setTotal(orderDAO.queryOrderCount(map));
		return resp;
	}
	
	
	public void addorderlog(int orderId,int userId,String action,String remark){
		orderDAO.addorderlog(orderId, userId, action, remark);
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
	
	/**
	 * updateOrder 经理审核
	 *
	 * @param order
	 * @return 创建时间：2017年3月14日 下午5:06:32
	 */
	public RespResult<String> updateOrder(Order order){
		//this.addorderlog(order.getStaffId(), order.getStaffId(), "2", order.getRemark());
		return orderDAO.updateOrder(order);
	}
	
	/**
	 * buzAduit 业务审核
	 *
	 * @param order
	 * @return 创建时间：2017年3月14日 下午5:06:32
	 */
	public RespResult<String> buzAduit(Order order){
		return orderDAO.buzAduitOrder(order);
	}
	
	/**
	 * finAduit 财务审核
	 *
	 * @param order
	 * @return 创建时间：2017年3月14日 下午5:06:32
	 */
	public RespResult<String> finAduit(Order order){
		return orderDAO.finAduitOrder(order);
	}

	/**
	 * getMyHanldedOrders
	 *
	 * @param custName
	 * @param i
	 * @param limit
	 * @param staffId
	 * @return 创建时间：2017年3月14日 下午7:16:38
	 */
	public RespResult<List<Hashtable<String, Object>>> getMyHanldedOrders(
			String custName, int start, int limit, int staffId) {
		RespResult<List<Hashtable<String, Object>>> resp = new RespResult<>();
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("custName*",custName);
		map.put("start",start);
		map.put("limit",limit);
		
		List<Hashtable<String, Object>> hst = orderDAO.queryOrderListHanlded(map, staffId);
		resp.setObj(hst);
		resp.setTotal(orderDAO.queryOrderListHanldedCount(map,staffId));
		return resp;
	}

	/**
	 * cancelOrder
	 *
	 * @param order
	 * @return 创建时间：2017年3月15日 下午6:13:32
	 */
	public RespResult<String> cancelOrder(Order order) {
		// TODO Auto-generated method stub
		return orderDAO.cancelOrder(order);
	}
	
	public RespResult<List<Hashtable<String, Object>>> reportOrdersDay(String month){
		RespResult<List<Hashtable<String, Object>>> resp = new RespResult<>();
		List<Hashtable<String, Object>> hst = orderDAO.reportOrdersDay(month);
		resp.setObj(hst);
		return resp;
	}
}
