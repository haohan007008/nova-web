package com.howbuy.fp.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.howbuy.fp.product.Product;
import com.howbuy.fp.product.ProductColorItem;
import com.howbuy.fp.product.ProductDAO;
import com.howbuy.fp.utils.ConfContext;
import com.howbuy.fp.utils.Constants;
import com.howbuy.fp.utils.RespResult;
import com.howbuy.fp.utils.SqlHelper;

/** 
 * @author LvMeng
 * @version 2017年3月6日 下午6:48:41
 */
@Service
public class OrderDAO {
	private static Logger log = Logger.getLogger(OrderDAO.class);
	
	@Autowired
	private SqlHelper sqlHelper;
	@Autowired
	private ProductDAO productDAO;
	
	public void setSqlHelper(SqlHelper sqlHelper) {
		this.sqlHelper = sqlHelper;
	}
	
	public RespResult<String> insertOrder(Order order){
		RespResult<String> resp = new RespResult<String>();
		List<Object> parameters = new ArrayList<>();
		int lastId = -1;
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("addorder");
		//(name,flowId,curNode,custName,staffId,totalPay,prePay,totalPrd,shipAddress,curOperator)
		
		lastId = this.newOrderId();
		parameters.add(lastId);
		parameters.add(order.getCustName());
		parameters.add(1);
		parameters.add(2);
		parameters.add(order.getCustName());
		parameters.add(order.getStaffId());
		parameters.add(0); //totalPay
		parameters.add(0); //prePay
		parameters.add(0); //totalPrd
		parameters.add(order.getShipAddress()); //totalPrd
		parameters.add(this.getNextOperator(1, 2)); //curOperator
		log.debug("parameters:" + JSON.toJSONString(parameters));
		
		int effect = this.sqlHelper.executeUpdate(sql, parameters);
		//log.info("effect:" + effect);
		
		if(effect >= 0 && order.getItems() != null 
				&& order.getItems().size() > 0){
			try {
				//lastId = Integer.parseInt(this.sqlHelper.query4OneVal(context.getScript("lastId"), null));
				//log.info("lastId:" + lastId);
				List<List<Object>> params = new ArrayList<>();
				for (int i = 0; i < order.getItems().size(); i++) {
					List<Object> p = new ArrayList<>();
					ProductColorItem item = order.getItems().get(i);
					//orderId,prdId,prdColorId,prdColorNo,prdColorName,nxxl,nxl,nl,nm,ns,prdNum,price,subTotal)
					p.add(lastId);
					p.add(item.getPrdId());
					p.add(item.getId());
					p.add(item.getColorNo());
					p.add(item.getColorName());
					p.add(item.getNxxl());
					p.add(item.getNxl());
					p.add(item.getNl());
					p.add(item.getNm());
					p.add(item.getNs());
					p.add(0);
					p.add(0);
					p.add(0);
					params.add(p);
				}
				
				this.sqlHelper.executeBatch(context.getScript("addorderitem"), params, "");
				this.sqlHelper.executeUpdate(context.getScript("updateorderitems"), new ArrayList<Object>(Arrays.asList(lastId)));
				this.sqlHelper.executeUpdate(context.getScript("updateorder"), new ArrayList<Object>(Arrays.asList(lastId)));
			} catch (Exception e) {
				e.printStackTrace();
				this.sqlHelper.executeUpdate(context.getScript("deleteorderitem"), new ArrayList<Object>(Arrays.asList(lastId)));
				this.sqlHelper.executeUpdate(context.getScript("deleteorder"), new ArrayList<Object>(Arrays.asList(lastId)));
				resp.setSuccess(false);
				resp.setDesc(e.getMessage());
			}
		}else {
			resp.setSuccess(false);
			resp.setDesc("insert order error , [order.getItems] is null or effect < 0 ,"
					+ " please contact system administrator !");
		}
		return resp;
	}
	
	
	public synchronized int newOrderId(){
		ConfContext context = this.sqlHelper.getSqlContext();
		//String sql = context.getScript("lastId");
		int lastId = -1;
		lastId = Integer.parseInt(this.sqlHelper.query4OneVal(context.getScript("lastId"), null));
		
		if(lastId <= 0){
			new Exception("newOrderId error!");
			log.error("newOrderId error!");
		} 
		
		return lastId;
	}
	
	public int getNextOperator(int folwId,int curNode){
		ConfContext context = this.sqlHelper.getSqlContext();
		List<Object> parameters = new ArrayList<>();
		//String sql = context.getScript("lastId");
		int nextOperatorId = -1;
		parameters.add(folwId);
		parameters.add(curNode);
		nextOperatorId = Integer.parseInt(this.sqlHelper.query4OneVal(context.getScript("getNextOperator"), parameters));
		
		if(nextOperatorId <= 0){
			new Exception("getNextOperator error!");
			log.error("getNextOperator error!");
		} 
		
		return nextOperatorId;
	}
	
	/**
	 * getMyOperOrderCount
	 *
	 * @param staffId
	 * @return 创建时间：2017年3月13日 下午2:24:02
	 */
	public int getMyOperOrderCount(int staffId){
		ConfContext context = this.sqlHelper.getSqlContext();
		//String sql = context.getScript("lastId");
		int nextOperatorId = -1;
		try {
			nextOperatorId = Integer.parseInt(this.sqlHelper.query4OneVal(
					context.getScript("myOperOrdersCount"), new ArrayList<Object>(Arrays.asList(staffId))));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("getMyOperOrderCount error!");
			new Exception("getMyOperOrderCount error!");
		}
		
		return nextOperatorId;
	}
	
	/**
	 * queryOrderList
	 *
	 * @param map
	 * @return 创建时间：2017年3月13日 下午2:24:05
	 */
	public List<Hashtable<String, Object>> queryOrderList(HashMap<String, Object> map){
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getOrders") + getDySql(map);
		log.info("sql:"+sql);
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql, null);
		return hst;
	}
	
	/**
	 * queryOrderCount
	 *
	 * @param map
	 * @return 创建时间：2017年3月13日 下午2:23:16
	 */
	public int queryOrderCount(HashMap<String, Object> map){
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getOrdersCount") + getDySql(map);
		String str = this.sqlHelper.query4OneVal( sql, null);
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * getDySql
	 *
	 * @param map
	 * @return 创建时间：2017年3月13日 下午2:23:20
	 */
	public String getDySql(HashMap<String, Object> map){
		String sql = "";
		log.debug("execute sql:" + sql );
		
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String)entry.getKey();
			Object val = entry.getValue();
			if(!"start".equals(key) && !"limit".equals(key)){
				if(val instanceof Integer)
					sql += " and " + key +"=" + val.toString();
				else {
					if(val instanceof String && key.toString().endsWith("*")) 
						sql += " and " + key.substring(0,key.length()-1) +" like '" + 
									val.toString() +"%'";
					else sql += " and " + key +"='" + val.toString() +"'";
				}
			}
			
		}
		if(map.get("start")!= null && map.get("limit") != null )
			sql += " limit " + map.get("start") +","+map.get("limit");
		return sql;
	}
	
	public Order getOrderById(int orderId){
		Order order = new Order();
		List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getOrderByID");
		parameters.add(orderId);
		log.info("sql:"+sql);
		Hashtable<String, Object> hst = this.sqlHelper.query4OneObject(sql, parameters);
		
		if(hst != null && hst.size() > 0){
			order.setCreateDate(nvl(hst.get("timestamp")));
			order.setCreatorId(Integer.parseInt(nvl(hst.get("staffId"))));
			order.setCreatorName(nvl(hst.get("creator")));
			//order.setCurNode(Integer.parseInt(nvl(hst.get("curNode"))));
			order.setCurNodeId(Integer.parseInt(nvl(hst.get("curNode"))));
			order.setCurOperatorId(Integer.parseInt(nvl(hst.get("curOperator"))));
			order.setCurOperatorName(nvl(hst.get("curOper")));
			order.setCustAddr(nvl(hst.get("shipAddress")));
			order.setCustName(nvl(hst.get("custName")));
			order.setExpressName(nvl(hst.get("expressName")));
			order.setExpressNo(nvl(hst.get("expressNo")));
			order.setId(orderId);
			order.setLastPay(Double.parseDouble(nvl(hst.get("lastPay"))));
			order.setPayAccount(nvl(hst.get("payAccount")));
			order.setPrePay(Double.parseDouble(nvl(hst.get("prePay"))));
			order.setRemark(nvl(hst.get("remark")));
			order.setPrePayTime(nvl(hst.get("prePayTime")));
			order.setShipAddress(nvl(hst.get("shipAddress")));
			order.setStaffId(Integer.parseInt(nvl(hst.get("staffId"))));
			order.setTotalPay(Double.parseDouble(nvl(hst.get("totalPay"))));
			order.setTotalPrd(Integer.parseInt(nvl(hst.get("totalPrd"))));
			//order.setItems(getOrderItemsByOrderId(orderId));
			order.setPrds(parseList(getOrderItemsByOrderId(orderId)));
		}
		
		return order;
	}
	
	
	public List<ProductColorItem> getOrderItemsByOrderId(int orderId){
		ConfContext context = this.sqlHelper.getSqlContext();
		List<ProductColorItem> items = new ArrayList<ProductColorItem>();
		List<Object> parameters = new ArrayList<>();
		String sql = context.getScript("getOrderItemsByOrderId");
		log.info("sql:"+sql);
		parameters.add(orderId);
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql, parameters);
		if(hst !=null && hst.size() >0){
			for (Iterator iterator = hst.iterator(); iterator.hasNext();) {
				Hashtable<String, Object> ht = (Hashtable<String, Object>) iterator.next();
				ProductColorItem pItem = new ProductColorItem();
				pItem.setId(Constants.toDouble(ht.get("Id")).intValue());
				pItem.setColorName(nvl(ht.get("prdColorName")));
				pItem.setColorNo(nvl(ht.get("prdColorNo")));
				pItem.setPrdId(Constants.toDouble(ht.get("prdId")).intValue());
				pItem.setNl(Constants.toDouble(ht.get("nl")).intValue());
				pItem.setNs(Constants.toDouble(ht.get("ns")).intValue());
				pItem.setNm(Constants.toDouble(ht.get("nm")).intValue());
				pItem.setNxl(Constants.toDouble(ht.get("nxl")).intValue());
				pItem.setNxxl(Constants.toDouble(ht.get("nxxl")).intValue());
				pItem.setPrdNum(Constants.toDouble(ht.get("prdNum")).intValue());
				pItem.setPrice(Constants.toDouble(ht.get("price")).doubleValue());
				pItem.setSubTotal(Constants.toDouble(ht.get("subTotal")).doubleValue());
				items.add(pItem);
			}
		}
		return items;
	}
	public List<Product> parseList(List<ProductColorItem> items){
		HashMap<Integer, Product> list = new HashMap<>();
		if(items != null && items.size() > 0){
			for (int i = 0; i < items.size(); i++) {
				ProductColorItem item = items.get(i);
				Product prod = list.get(item.getPrdId());
				if(prod == null){
					prod = productDAO.getProductByIdNoItems(item.getPrdId());
					//prod.setItems(null);
					if(prod != null)
						list.put(item.getPrdId(), prod);
				}
				if(prod.getItems() !=null && prod.getItems().size() > 0){
					list.get(item.getPrdId()).getItems().add(item);
				}else {
					List<ProductColorItem> ls = new ArrayList<>();
					ls.add(item);
					list.get(item.getPrdId()).setItems(ls);
				}
			}
		}
		List<Product> prds = new ArrayList<>();
		if(list != null && list.size() > 0){
			Iterator it = list.keySet().iterator();
	        while (it.hasNext()) {
	            Integer key = (Integer)it.next();
	            prds.add(list.get(key));
	        }
		}
		return prds;
	}
	public String nvl(Object obj){
		if(obj!= null){
			return obj.toString();
		}else return "";
		
	}
}
