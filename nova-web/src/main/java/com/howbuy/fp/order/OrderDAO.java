package com.howbuy.fp.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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
	
	//product_order_query
	public List<Product> queryProductOrder(String prdNo){
		ConfContext context = this.sqlHelper.getSqlContext();
		List<Object> parameters = new ArrayList<>();
		String sql = context.getScript("product_order_query");
		parameters.add(prdNo);
		log.info("sql:"+sql);
		Map<String, Product> prds = new HashMap<>();
		
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql, parameters);
		if(hst != null && hst.size() > 0){
			for(Hashtable<String, Object> ht : hst){
				Product prd = prds.get(ht.get("prdNo").toString());
				if(prd != null ){
					//已存在对象
					ProductColorItem pItem = new ProductColorItem();
					//pItem.setId(Constants.toDouble(ht.get("Id")).intValue());
					pItem.setColorName(ht.get("colorName").toString());
					pItem.setColorNo(ht.get("colorNo").toString());
					pItem.setSubTotal(Constants.toDouble(ht.get("subTotal")).doubleValue());
					pItem.setPrdNum((Constants.toDouble(ht.get("prdNum"))).intValue());
					pItem.setPrdingNum((Constants.toDouble(ht.get("prdingNum"))).intValue());
					pItem.setNs((Constants.toDouble(ht.get("ns"))).intValue());
					pItem.setNm((Constants.toDouble(ht.get("nm"))).intValue());
					pItem.setNl((Constants.toDouble(ht.get("nl"))).intValue());
					pItem.setNxl((Constants.toDouble(ht.get("nxl"))).intValue());
					pItem.setNxxl((Constants.toDouble(ht.get("nxxl"))).intValue());
					prd.getItems().add(pItem);
				}else{
					Product pd = new Product();
					pd.setId(Constants.toDouble(ht.get("id")).intValue());	
					pd.setPrdSmallImg(ht.get("prdSmallImg").toString());
					pd.setPrdName(ht.get("prdName").toString());
					pd.setPrdNo(ht.get("prdNo").toString());
					pd.setMtlQty(ht.get("mtlQty")==null?"":ht.get("mtlQty").toString());
					ProductColorItem pItem = new ProductColorItem();
					//pItem.setId(Constants.toDouble(ht.get("Id")).intValue());
					pItem.setColorName(ht.get("colorName").toString());
					pItem.setColorNo(ht.get("colorNo").toString());
					pItem.setSubTotal(Constants.toDouble(ht.get("subTotal")).doubleValue());
					pItem.setPrdNum((Constants.toDouble(ht.get("prdNum"))).intValue());
					pItem.setPrdingNum((Constants.toDouble(ht.get("prdingNum"))).intValue());
					pItem.setNs((Constants.toDouble(ht.get("ns"))).intValue());
					pItem.setNm((Constants.toDouble(ht.get("nm"))).intValue());
					pItem.setNl((Constants.toDouble(ht.get("nl"))).intValue());
					pItem.setNxl((Constants.toDouble(ht.get("nxl"))).intValue());
					pItem.setNxxl((Constants.toDouble(ht.get("nxxl"))).intValue());
					List<ProductColorItem> items = new ArrayList<>();
					items.add(pItem);
					pd.setItems(items);
					
					prds.put(pd.getPrdNo(), pd);
				};
			}
			
		}
		
		List<Product> valuesList = new ArrayList<Product>(prds.values());
		Collections.sort(valuesList);
		return valuesList;
	}
	
	
	//product_order_query
		public List<Product> queryProductOrderStore(String prdNo){
			ConfContext context = this.sqlHelper.getSqlContext();
			List<Object> parameters = new ArrayList<>();
			String sql = context.getScript("product_order_store_query");
			parameters.add(prdNo);
			log.info("sql:"+sql);
			Map<String, Product> prds = new HashMap<>();
			
			List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql, parameters);
			if(hst != null && hst.size() > 0){
				for(Hashtable<String, Object> ht : hst){
					Product prd = prds.get(ht.get("prdNo").toString());
					if(prd != null ){
						//已存在对象
						ProductColorItem pItem = new ProductColorItem();
						pItem.setId(Constants.toDouble(ht.get("colorId")).intValue());
						pItem.setPrdId(Constants.toDouble(ht.get("id")).intValue());
						pItem.setColorName(ht.get("colorName").toString());
						//pItem.setColorNo(ht.get("colorNo").toString());
						//pItem.setSubTotal(Constants.toDouble(ht.get("subTotal")).doubleValue());
						pItem.setPrdNum((Constants.toDouble(ht.get("prdNum"))).intValue());
						//pItem.setNxs((Constants.toDouble(ht.get("nxs"))).intValue());
						pItem.setNs((Constants.toDouble(ht.get("ns"))).intValue());
						pItem.setNm((Constants.toDouble(ht.get("nm"))).intValue());
						pItem.setNl((Constants.toDouble(ht.get("nl"))).intValue());
						pItem.setNxl((Constants.toDouble(ht.get("nxl"))).intValue());
						pItem.setNxxl((Constants.toDouble(ht.get("nxxl"))).intValue());
						pItem.setIts(ht.get("its").toString());
						//pItem.setNxxxl((Constants.toDouble(ht.get("nxxXl"))).intValue());
						//pItem.setNunno((Constants.toDouble(ht.get("nunno"))).intValue());
						//pItem.setOns((Constants.toDouble(ht.get("ons"))).intValue());
						//pItem.setOnm((Constants.toDouble(ht.get("onm"))).intValue());
						//pItem.setOnl((Constants.toDouble(ht.get("onl"))).intValue());
						//pItem.setOnxl((Constants.toDouble(ht.get("onxl"))).intValue());
						//pItem.setOnxxl((Constants.toDouble(ht.get("onxxl"))).intValue());
						prd.getItems().add(pItem);
					}else{
						Product pd = new Product();
						pd.setId(Constants.toDouble(ht.get("id")).intValue());	
						pd.setPrdSmallImg(ht.get("prdSmallImg").toString());
						pd.setPrdName(ht.get("prdName").toString());
						pd.setPrdNo(ht.get("prdNo").toString());
						
						ProductColorItem pItem = new ProductColorItem();
						pItem.setPrdId(Constants.toDouble(ht.get("id")).intValue());
						pItem.setId(Constants.toDouble(ht.get("colorId")).intValue());
						pItem.setColorName(ht.get("colorName").toString());
						//pItem.setColorNo(ht.get("colorNo").toString());
						//pItem.setSubTotal(Constants.toDouble(ht.get("subTotal")).doubleValue());
						pItem.setPrdNum((Constants.toDouble(ht.get("prdNum"))).intValue());
						//pItem.setNxs((Constants.toDouble(ht.get("nxs"))).intValue());
						pItem.setNs((Constants.toDouble(ht.get("ns"))).intValue());
						pItem.setNm((Constants.toDouble(ht.get("nm"))).intValue());
						pItem.setNl((Constants.toDouble(ht.get("nl"))).intValue());
						pItem.setNxl((Constants.toDouble(ht.get("nxl"))).intValue());
						pItem.setNxxl((Constants.toDouble(ht.get("nxxl"))).intValue());
						pItem.setIts(ht.get("its").toString());
						//pItem.setNxxxl((Constants.toDouble(ht.get("nxxl"))).intValue());
						//pItem.setNunno((Constants.toDouble(ht.get("nunno"))).intValue());
						//pItem.setOns((Constants.toDouble(ht.get("ons"))).intValue());
						//pItem.setOnm((Constants.toDouble(ht.get("onm"))).intValue());
						//pItem.setOnl((Constants.toDouble(ht.get("onl"))).intValue());
						//pItem.setOnxl((Constants.toDouble(ht.get("onxl"))).intValue());
						//pItem.setOnxxl((Constants.toDouble(ht.get("onxxl"))).intValue());
						List<ProductColorItem> items = new ArrayList<>();
						items.add(pItem);
						pd.setItems(items);
						
						prds.put(pd.getPrdNo(), pd);
					};
				}
				
			}
			
			List<Product> valuesList = new ArrayList<Product>(prds.values());
			Collections.sort(valuesList);
			return valuesList;
		}
	
		
		//product_order_query
	    public List<Product> queryProductOrderbyIts(String its){
	    		StringBuffer sb =new StringBuffer();
		    		sb.append("SELECT c.id,c.`prdNo`,c.`prdSmallImg`,c.`prdName`,d.id colorId,");
		    		sb.append("d.`colorName`,d.`colorNo`,c.`mtlQty`,SUM(b.`prdNum`) prdNum,SUM(b.`subTotal`) subTotal,SUM(b.`ns`) ns,");
		    		sb.append("SUM(b.`nm`) nm,SUM(b.`nl`) nl,SUM(b.`nxl`) nxl,SUM(b.`nxxl`) nxxl,GROUP_CONCAT(b.id) its");
		    		sb.append(" FROM r_order a,r_orderitem b,c_product c,c_product_color d");
		    		sb.append(" WHERE a.id = b.orderId");
		    		sb.append(" AND b.`prdId` = c.`Id`");
		    		sb.append(" AND b.`prdColorId` = d.`Id`");
		    		sb.append(" AND d.`isVaild` = 0");
		    		sb.append(" AND a.`oprState` = 0");
		    		sb.append(" AND a.`curNode` = 5");
		    		sb.append(" AND b.oprState = 0");
		    		sb.append(" AND b.mntState = 0");
		    		sb.append(" AND b.`Id` IN ( "+its+" )");
		    		sb.append(" GROUP BY c.id,c.`prdNo`,c.`prdSmallImg`,c.`prdName`,d.`colorName`,d.`colorNo`,c.`mtlQty`");
		    		sb.append(" ORDER BY REPLACE(c.`prdNo`,'16','99') ASC");
				
				log.info("sql:"+sb.toString());
				Map<String, Product> prds = new HashMap<>();
					
				List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sb.toString(), null);
				if(hst != null && hst.size() > 0){
						for(Hashtable<String, Object> ht : hst){
							Product prd = prds.get(ht.get("prdNo").toString());
							if(prd != null ){
								//已存在对象
								ProductColorItem pItem = new ProductColorItem();
								pItem.setId(Constants.toDouble(ht.get("colorId")).intValue());
								pItem.setPrdId(Constants.toDouble(ht.get("id")).intValue());
								pItem.setColorName(ht.get("colorName").toString());
								//pItem.setColorNo(ht.get("colorNo").toString());
								//pItem.setSubTotal(Constants.toDouble(ht.get("subTotal")).doubleValue());
								pItem.setPrdNum((Constants.toDouble(ht.get("prdNum"))).intValue());
								//pItem.setNxs((Constants.toDouble(ht.get("nxs"))).intValue());
								pItem.setNs((Constants.toDouble(ht.get("ns"))).intValue());
								pItem.setNm((Constants.toDouble(ht.get("nm"))).intValue());
								pItem.setNl((Constants.toDouble(ht.get("nl"))).intValue());
								pItem.setNxl((Constants.toDouble(ht.get("nxl"))).intValue());
								pItem.setNxxl((Constants.toDouble(ht.get("nxxl"))).intValue());
								pItem.setIts(ht.get("its").toString());
								//pItem.setNxxxl((Constants.toDouble(ht.get("nxxXl"))).intValue());
								//pItem.setNunno((Constants.toDouble(ht.get("nunno"))).intValue());
								//pItem.setOns((Constants.toDouble(ht.get("ons"))).intValue());
								//pItem.setOnm((Constants.toDouble(ht.get("onm"))).intValue());
								//pItem.setOnl((Constants.toDouble(ht.get("onl"))).intValue());
								//pItem.setOnxl((Constants.toDouble(ht.get("onxl"))).intValue());
								//pItem.setOnxxl((Constants.toDouble(ht.get("onxxl"))).intValue());
								prd.getItems().add(pItem);
							}else{
								Product pd = new Product();
								pd.setId(Constants.toDouble(ht.get("id")).intValue());	
								pd.setPrdSmallImg(ht.get("prdSmallImg").toString());
								pd.setPrdName(ht.get("prdName").toString());
								pd.setPrdNo(ht.get("prdNo").toString());
								pd.setMtlQty(ht.get("mtlQty")==null?"":ht.get("mtlQty").toString());
								
								ProductColorItem pItem = new ProductColorItem();
								pItem.setPrdId(Constants.toDouble(ht.get("id")).intValue());
								pItem.setId(Constants.toDouble(ht.get("colorId")).intValue());
								pItem.setColorName(ht.get("colorName").toString());
								//pItem.setColorNo(ht.get("colorNo").toString());
								//pItem.setSubTotal(Constants.toDouble(ht.get("subTotal")).doubleValue());
								pItem.setPrdNum((Constants.toDouble(ht.get("prdNum"))).intValue());
								//pItem.setNxs((Constants.toDouble(ht.get("nxs"))).intValue());
								pItem.setNs((Constants.toDouble(ht.get("ns"))).intValue());
								pItem.setNm((Constants.toDouble(ht.get("nm"))).intValue());
								pItem.setNl((Constants.toDouble(ht.get("nl"))).intValue());
								pItem.setNxl((Constants.toDouble(ht.get("nxl"))).intValue());
								pItem.setNxxl((Constants.toDouble(ht.get("nxxl"))).intValue());
								pItem.setIts(ht.get("its").toString());
								//pItem.setNxxxl((Constants.toDouble(ht.get("nxxl"))).intValue());
								//pItem.setNunno((Constants.toDouble(ht.get("nunno"))).intValue());
								//pItem.setOns((Constants.toDouble(ht.get("ons"))).intValue());
								//pItem.setOnm((Constants.toDouble(ht.get("onm"))).intValue());
								//pItem.setOnl((Constants.toDouble(ht.get("onl"))).intValue());
								//pItem.setOnxl((Constants.toDouble(ht.get("onxl"))).intValue());
								//pItem.setOnxxl((Constants.toDouble(ht.get("onxxl"))).intValue());
								List<ProductColorItem> items = new ArrayList<>();
								items.add(pItem);
								pd.setItems(items);
								
								prds.put(pd.getPrdNo(), pd);
							};
						}
						
					}
					
					List<Product> valuesList = new ArrayList<Product>(prds.values());
					Collections.sort(valuesList);
					return valuesList;
				}
		
	/**
	 * cancelOrder 取消订单
	 *
	 * @param order
	 * @return 创建时间：2017年3月14日 上午11:04:33
	 */
	public RespResult<String> cancelOrder(Order order){
		RespResult<String> resp = new RespResult<String>();
		ConfContext context = this.sqlHelper.getSqlContext();
		try {
			this.sqlHelper.executeUpdate(context.getScript("canelOrder"), 
					new ArrayList<Object>(Arrays.asList(order.getId())));
			//this.sqlHelper.executeUpdate(context.getScript("canelOrderItem"), 
			//		new ArrayList<Object>(Arrays.asList(order.getId())));
			this.addorderlog(order.getId(), order.getStaffId(), "7", order.getRemark());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setSuccess(false);
			resp.setDesc("cancel Order error , ="
					+ " please contact system administrator !" + e.getMessage());
		}
		return resp;
	}
	
	/**
	 * insertOrder
	 *
	 * @param order
	 * @return 创建时间：2017年3月14日 上午8:55:15
	 */
	public RespResult<String> insertOrder(Order order){
		RespResult<String> resp = new RespResult<String>();
		List<Object> parameters = new ArrayList<>();
		int lastId = -1;
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("addorder");
		//(name,flowId,curNode,custName,staffId,totalPay,prePay,totalPrd,shipAddress,curOperator)
		
		lastId = this.newOrderId();
		parameters.add(lastId);
		parameters.add(this.getOrderNo());
		parameters.add(1);
		parameters.add(2);
		parameters.add(order.getCustName());
		parameters.add(order.getStaffId());
		parameters.add(0); //totalPay
		parameters.add(0); //prePay
		parameters.add(0); //totalPrd
		parameters.add(order.getShipAddress()); //totalPrd
		parameters.add(this.getNextOperator(1, 2,lastId)); //curOperator
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
				this.sqlHelper.executeUpdate(context.getScript("updateorderitems1"), new ArrayList<Object>(Arrays.asList(lastId,lastId)));
				this.sqlHelper.executeUpdate(context.getScript("updateorderitems2"), new ArrayList<Object>(Arrays.asList(lastId)));
				this.sqlHelper.executeUpdate(context.getScript("updateorder"), new ArrayList<Object>(Arrays.asList(lastId)));
				this.addorderlog(lastId, order.getStaffId(), "1", order.getRemark());
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
	
	
	public RespResult<String> updateOrder(Order order){
		RespResult<String> resp = new RespResult<String>();
		List<Object> parameters = new ArrayList<>();
		//int lastId = -1;
		ConfContext context = this.sqlHelper.getSqlContext();
		this.sqlHelper.executeUpdate(context.getScript("bakOrder"), 
				new ArrayList<Object>(Arrays.asList(order.getId())));
		this.sqlHelper.executeUpdate(context.getScript("disableorderitem"), 
				new ArrayList<Object>(Arrays.asList(order.getId())));
		
		String sql = context.getScript("aduitOrder");
		//id, shipAddress, remark,totalPay,prePay,curNode
		//lastId = this.newOrderId();
		
		//parameters.add(order.getCustName());
		//parameters.add(order.getShipAddress()); 
		parameters.add(order.getRemark());
		//parameters.add(order.getCustName());
		//parameters.add(order.getStaffId());
		parameters.add(order.getTotalPay()); //totalPay
		parameters.add(order.getPrePay()); //prePay
		parameters.add(order.getTotalPrd()); //totalPrd
		parameters.add(3); //curNode
		parameters.add(this.getNextOperator(1, 3,order.getId())); //curOperator
		parameters.add(order.getId());
		log.info("updateOrder sql:" + sql);
		log.info("updateOrder parameters:" + JSON.toJSONString(parameters));
		
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
					p.add(order.getId());
					p.add(item.getPrdId());
					p.add(item.getId());
					p.add(item.getColorNo());
					p.add(item.getColorName());
					p.add(item.getNxxl());
					p.add(item.getNxl());
					p.add(item.getNl());
					p.add(item.getNm());
					p.add(item.getNs());
					p.add(item.getNxxl()+ item.getNxl()+ item.getNl()+ item.getNm()+ item.getNs());
					p.add(item.getPrice());
					p.add(item.getSubTotal());
					params.add(p);
				}
				
				this.sqlHelper.executeBatch(context.getScript("addorderitem"), params, "");
				this.addorderlog(order.getId(), order.getStaffId(), "2", order.getRemark());
			} catch (Exception e) {
				e.printStackTrace();
				//this.sqlHelper.executeUpdate(context.getScript("deleteorderitem"), new ArrayList<Object>(Arrays.asList(lastId)));
				//this.sqlHelper.executeUpdate(context.getScript("deleteorder"), new ArrayList<Object>(Arrays.asList(lastId)));
				resp.setSuccess(false);
				resp.setDesc(e.getMessage());
			}
		}else {
			resp.setSuccess(false);
			resp.setDesc("update order error , [order.getItems] is null or effect < 0 ,"
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
	
	public int getNextOperator(int folwId,int curNode,int orderId){
		ConfContext context = this.sqlHelper.getSqlContext();
		List<Object> parameters = new ArrayList<>();
		//String sql = context.getScript("lastId");
		int nextOperatorId = -1;
		
		if(curNode == 3) {
			//如果是经理审批环节，下发到业务确认，之前是谁下的单子就返回给谁确认
			parameters.add(orderId);
			String createId = this.sqlHelper.query4OneVal(context.getScript("gerOrderCreatorBYId"),	parameters);
			if(createId != null ){
				nextOperatorId = Integer.parseInt(createId);
			}else {
				parameters.clear();
				parameters.add(folwId);
				parameters.add(curNode);
				nextOperatorId = Integer.parseInt(this.sqlHelper.query4OneVal(context.getScript("getNextOperator"),
						parameters));
			}
		}else {
			parameters.add(folwId);
			parameters.add(curNode);
			nextOperatorId = Integer.parseInt(this.sqlHelper.query4OneVal(context.getScript("getNextOperator"),
					parameters));
		}
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
	 * addorderlog
	 *
	 * @param orderId
	 * @param userId
	 * @param remark 创建时间：2017年3月14日 下午9:05:06
	 */
	public void addorderlog(int orderId,int userId,String action,String remark){
		ConfContext context = this.sqlHelper.getSqlContext();
		List<Object> parameters = new ArrayList<>();
		parameters.add(orderId);
		parameters.add(userId);
		parameters.add(action);
		parameters.add(remark);
		this.sqlHelper.executeUpdate(context.getScript("addorderlog"), parameters);
	}
	
	public List<Hashtable<String, Object>> getOrderLogs(int orderId){
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getorderlog");
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql, new ArrayList<Object>(Arrays.asList(orderId)));
		
		return hst;
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
	 * queryOrderList
	 *
	 * @param map
	 * @return 创建时间：2017年3月13日 下午2:24:05
	 */
	public List<Hashtable<String, Object>> queryOrderListHanlded(HashMap<String, Object> map,int userid){
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getOrders") 
				+ " and EXISTS (select 1 from r_order_log s "
				+ "where s.orderId = a.id and s.userid = "+userid+")"
				+ getDySql(map) ;
		log.info("sql:"+sql);
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql, null);
		return hst;
	}
	
	public int queryOrderListHanldedCount(HashMap<String, Object> map,int userid){
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getOrdersCount") + " and EXISTS (select 1 from r_order_log s "
				+ "where s.orderId = a.id and s.userid = "+userid+")"+ getDySql(map);
		String str = this.sqlHelper.query4OneVal( sql, null);
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * queryOrderCount
	 *
	 * @param map
	 * @return 创建时间：2017年3月13日 下午2:23:16
	 */
	public int queryOrderCount(HashMap<String, Object> map ){
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
	 * queryOrderCount
	 *
	 * @param map
	 * @return 创建时间：2017年3月13日 下午2:23:16
	 */
	public synchronized String getOrderNo(){
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getorderNo");
		String orderNo = this.sqlHelper.query4OneVal( sql, null);
		
		if(Constants.isBlank(orderNo) || orderNo.length() != 12){
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			//String dateString = formatter.format(currentTime);
			orderNo = formatter.format(currentTime) + "0001";
		}else{
			orderNo = orderNo.substring(0,8) + 
					Constants.lpad(String.valueOf(Integer.parseInt(orderNo.substring(9))+1),4,"0");
		}
		return orderNo;
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
		System.out.println(JSON.toJSONString(hst));
		if(hst != null && hst.size() > 0){
			order.setCreateDate(nvl(hst.get("timestamp")));
			order.setCreatorId(Integer.parseInt(nvl(hst.get("staffId"))));
			order.setCreatorName(nvl(hst.get("creator")));
			//order.setCurNode(Integer.parseInt(nvl(hst.get("curNode"))));
			order.setCurNodeName(nvl(hst.get("nodeName")));
			order.setCurNodeId(Integer.parseInt(nvl(hst.get("curNode"))));
			order.setOrderNo(nvl(hst.get("name")));
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
			order.setLogs(getOrderLogs(orderId));
			
		}
		
		return order;
	}
	
	
	/**
	 * getOrderItemsByOrderId
	 *
	 * @param orderId
	 * @return 创建时间：2017年3月14日 上午11:01:21
	 */
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
				pItem.setId(Constants.toDouble(ht.get("prdColorId")).intValue());
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
		Map<Integer, Product> list = new HashMap<>();
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
		
		
		List<Product> prds = new ArrayList<Product>(list.values());
		
//		List<Product> prds = new ArrayList<>();
//		if(list != null && list.size() > 0){
//			Iterator it = list.keySet().iterator();
//	        while (it.hasNext()) {
//	            Integer key = (Integer)it.next();
//	            prds.add(list.get(key));
//	        }
//		}
		return prds;
	}
	public String nvl(Object obj){
		if(obj!= null){
			return obj.toString();
		}else return "";
		
	}

	/**
	 * buzAduitOrder
	 *
	 * @param order
	 * @return 创建时间：2017年3月14日 下午5:10:53
	 */
	public RespResult<String> buzAduitOrder(Order order) {
		RespResult<String> resp = new RespResult<String>();
		ConfContext context = this.sqlHelper.getSqlContext();
		List<Object> parameters = new ArrayList<>();
		try {
			parameters.add(order.getCurNodeId());
			parameters.add(this.getNextOperator(order.getFlowId(), order.getCurNodeId(),order.getId()));
			//parameters.add(order.getRemark());
			parameters.add(order.getId());
			this.sqlHelper.executeUpdate(context.getScript("buzAduit"), parameters);
			this.addorderlog(order.getId(), order.getStaffId(), "3",
					order.getRemark());
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.setSuccess(false);
			resp.setDesc("buzAduitOrder error , ="
					+ " please contact system administrator !" + e.getMessage());
		}
		return resp;
	}

	/**
	 * finAduitOrder
	 *
	 * @param order
	 * @return 创建时间：2017年3月14日 下午5:11:10
	 */
	public RespResult<String> finAduitOrder(Order order) {
		RespResult<String> resp = new RespResult<String>();
		ConfContext context = this.sqlHelper.getSqlContext();
		List<Object> parameters = new ArrayList<>();
		try {
			parameters.add(order.getCurNodeId());
			parameters.add(this.getNextOperator(order.getFlowId(), order.getCurNodeId(),order.getId()));
			//parameters.add(order.getRemark());
			parameters.add(order.getPrePayTime());
			parameters.add(order.getPayAccount());
			parameters.add(order.getId());
			this.sqlHelper.executeUpdate(context.getScript("finAduit"), parameters);
			this.addorderlog(order.getId(), order.getStaffId(), "4",order.getRemark());
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.setSuccess(false);
			resp.setDesc("finAduitOrder error , ="
					+ " please contact system administrator !" + e.getMessage());
		}
		return resp;
	}
	
	
	public List<Hashtable<String, Object>> reportOrdersDay(String month ){
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("report_orders_day");
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql, null);
		return hst;
	}
}
