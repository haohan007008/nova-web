package com.howbuy.fp.production;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.howbuy.fp.utils.ConfContext;
import com.howbuy.fp.utils.Constants;
import com.howbuy.fp.utils.RespResult;
import com.howbuy.fp.utils.SqlHelper;

/** 
 * @author LvMeng
 * @version 2017年3月6日 下午6:48:41
 */
@Service
public class ProductionDAO {
	private static Logger log = Logger.getLogger(ProductionDAO.class);
	
	@Autowired
	private SqlHelper sqlHelper;

	public void setSqlHelper(SqlHelper sqlHelper) {
		this.sqlHelper = sqlHelper;
	}
	
	/**
	 * getProductionById
	 *
	 * @return 创建时间：2017年4月5日 下午3:07:23
	 */
	public Production getProductionById(int productionId){
		Production production = new Production();
		production.setId(productionId);
		List<Production> prodsList = this.getProductionList(production, -1, -1, true);
		
		if(prodsList !=null && prodsList.size() > 0)
			return prodsList.get(0);
		else {
			return null;
		}
	}
	
	public List<Production> getProductionList(Production production,int start,int limit,boolean hasItem){
		List<Production> prds = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("query_production") 
				+ this.getProductionListWhereStr(production, start, limit);
		
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql, null);
		if(hst !=null && hst.size() >0){
			for (Iterator iterator = hst.iterator(); iterator.hasNext();) {
				Hashtable<String, Object> ht = (Hashtable<String, Object>) iterator.next();
				Production prod = new Production();
				prod.setId(Constants.toDouble(ht.get("id")).intValue());
				prod.setContractNo(nvl(ht.get("contractNo").toString()));
				prod.setManufacturer(nvl(ht.get("manufacturer").toString()));
				prod.setAmount(Double.parseDouble(nvl(ht.get("amount"))));
				prod.setCreatorId((Constants.toDouble(ht.get("creatorId"))).intValue());
				prod.setCreatorName(ht.get("creatorName").toString());
				prod.setCurOperatorId((Constants.toDouble(ht.get("curOperatorId"))).intValue());
				prod.setCurOperatorName(ht.get("curOperatorName").toString());
				prod.setLinkStaff(nvl(ht.get("linkStaff").toString()));
				prod.setManufacturerAddress(nvl(ht.get("manufacturerAddress").toString()));
				prod.setPayBank(nvl(ht.get("payBank").toString()));
				prod.setPayNo(nvl(ht.get("payNo").toString()));
				prod.setPrdNum((Constants.toDouble(ht.get("prdNum"))).intValue());
				prod.setSignAddress(ht.get("signAddress").toString());
				prod.setSignTime(nvl(ht.get("signTime").toString()));
				prod.setTelphone(nvl(ht.get("telphone").toString()));
				prod.setCurNodeName(ht.get("nodeName")== null?"":ht.get("nodeName").toString());
				prod.setCurNode((Constants.toDouble(ht.get("curNode"))).intValue());
				prod.setTax((Constants.toDouble(ht.get("tax"))).intValue() == 0?true:false);
//				pItem.setPrdNo(ht.get("prdNo").toString());
//				pItem.setPrdId(Constants.toDouble(ht.get("prdColorId")).intValue());
//				pItem.setColorName(ht.get("colorName").toString());
//				pItem.setColorNo(ht.get("colorNo").toString());
//				pItem.setNs((Constants.toDouble(ht.get("ns"))).intValue());
//				pItem.setNxs((Constants.toDouble(ht.get("nxs"))).intValue());
//				pItem.setNm((Constants.toDouble(ht.get("nm"))).intValue());
//				pItem.setNl((Constants.toDouble(ht.get("nl"))).intValue());
//				pItem.setNxl((Constants.toDouble(ht.get("nxl"))).intValue());
//				pItem.setNxxl((Constants.toDouble(ht.get("nxxl"))).intValue());
//				pItem.setNxxxl((Constants.toDouble(ht.get("nxxxl"))).intValue());
//				pItem.setNunno((Constants.toDouble(ht.get("nunno"))).intValue());
				//pItem.setRemark(ht.get("remark").toString());
				prod.setStatus(Constants.toDouble(ht.get("status")).intValue());
				if(hasItem){
					//prod.setItems(this.getProductionItemByID(prod.getId()));
					prod.setProducts(this.getProductionItemByID(prod.getId()));
					prod.setLogs(this.getOrderLogs(prod.getId()));
				}
				prds.add(prod);
			}
		}
		return prds;
	}
	
	/**
	 * getProductionListCount
	 *
	 * @param production
	 * @return 创建时间：2017年4月5日 下午4:49:44
	 */
	public String getProductionListCount(Production production){
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("query_production_count") 
				+ this.getProductionListWhereStr(production, -1, -1);
		return this.sqlHelper.query4OneVal(sql, null);
	}
	
	public synchronized String getContractNo(){
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getcontractNo");
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
	 * addProduction 生产合同
	 *
	 * @param production
	 * @return 创建时间：2017年4月6日 上午9:00:38
	 */
	public RespResult<String> addProduction(Production production){
		//BeanUtils.copyProperties(source, target);
		
		RespResult<String> resp = new RespResult<String>();
		List<Object> parameters = new ArrayList<>();
		int lastId = -1;
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("insert_production");
		//contractNo,manufacturer,signTime,signAddress,prdNum,amount,linkStaff,payBank,payNo,
		//manufacturerAddress,telphone ,flowId,curNode,curOperatorId,creatorId,STATUS
		
		lastId = this.newProductionId();
		parameters.add(lastId);
		parameters.add(this.getContractNo());
		parameters.add(production.getManufacturer());
		parameters.add(production.getSignTime());
		parameters.add(production.getSignAddress());
		parameters.add(production.getPrdNum());
		parameters.add(production.getAmount()); 
		parameters.add(production.getLinkStaff());  
		parameters.add(production.getPayBank());  
		parameters.add(production.getPayNo());
		parameters.add(production.getManufacturerAddress());
		parameters.add(production.getTelphone());
		parameters.add(production.getFlowId());
		parameters.add(14);
		parameters.add(this.getNextOperator(2, 14,lastId)); //curOperator
		parameters.add(production.getCreatorId());
		parameters.add(production.getStatus());
		//arameters.add(production.getTax());
		parameters.add(production.getTax()?0:1);
		
		log.debug("parameters:" + JSON.toJSONString(parameters));
		
		int effect = this.sqlHelper.executeUpdate(sql, parameters);
		//log.info("effect:" + effect);
		
		if(effect >= 0 && production.getItems() != null 
				&& production.getItems().size() > 0){
			try {
				//lastId = Integer.parseInt(this.sqlHelper.query4OneVal(context.getScript("lastId"), null));
				//log.info("lastId:" + lastId);
				List<List<Object>> params = new ArrayList<>();
				for (int i = 0; i < production.getItems().size(); i++) {
					List<Object> p = new ArrayList<>();
					ProductionItem item = production.getItems().get(i);
					//pId,prdId,prdColorId,nxs,ns,nm,nl,nxl,nxxl,nxxxl,prdNum,amount,remark,deliveryTime,STATUS
					p.add(lastId);
					p.add(item.getPrdId());
					p.add(item.getPrdColorId());
					p.add(item.getNxs());
					p.add(item.getNs());
					p.add(item.getNm());
					p.add(item.getNl());
					p.add(item.getNxl());
					p.add(item.getNxxl());
					p.add(item.getNxxxl());
					p.add(item.getPrdNum());
					p.add(item.getAmount());
					p.add(item.getRemark());
					p.add(item.getDeliveryTime());
					p.add(item.getStatus());
					p.add(item.getPrice());
					p.add(item.getIts());
					params.add(p);
				}
				
				this.sqlHelper.executeBatch(context.getScript("insert_productionitem"), params, "");
				
				this.sqlHelper.executeUpdate(context.getScript("update_productionitem"), new ArrayList<Object>(Arrays.asList(lastId)));
				this.sqlHelper.executeUpdate(context.getScript("update_production"), new ArrayList<Object>(Arrays.asList(lastId)));
				this.sqlHelper.executeUpdate("UPDATE r_orderitem i SET i.mntstate = "+
						lastId+" WHERE i.id IN ("+production.getIts()+");", null);
				//this.sqlHelper.executeUpdate(context.getScript("updateorderitems2"), new ArrayList<Object>(Arrays.asList(lastId)));
				//this.sqlHelper.executeUpdate(context.getScript("updateorder"), new ArrayList<Object>(Arrays.asList(lastId)));
				//this.addorderlog(lastId, order.getStaffId(), "1", order.getRemark());
				this.addorderlog(lastId, production.getCreatorId(), "8",production.getRemark());
				resp.setObj(String.valueOf(lastId));
			} catch (Exception e) {
				e.printStackTrace();
				//this.sqlHelper.executeUpdate(context.getScript("deleteorderitem"), new ArrayList<Object>(Arrays.asList(lastId)));
				//this.sqlHelper.executeUpdate(context.getScript("deleteorder"), new ArrayList<Object>(Arrays.asList(lastId)));
				resp.setSuccess(false);
				resp.setDesc(e.getMessage());
			}
		}else {
			resp.setSuccess(false);
			resp.setDesc("insert Production error , [Production.getItems] is null or effect < 0 ,"
					+ " please contact system administrator !");
		}
		return resp;
		
	}
	
	public void insertItem(ProductionItem item){
		
	}
	
//	public List<ProductionItem> getProductionItemByID(int productionId){
//		
//		return null;
//	}
 	
	public List<Product> getProductionItemByID(int productionId){
		ConfContext context = this.sqlHelper.getSqlContext();
		List<Object> parameters = new ArrayList<>();
		String sql = context.getScript("queryProductionById");
		parameters.add(productionId);
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
					//pItem.setColorNo(ht.get("colorNo").toString());
					pItem.setSubTotal(Constants.toDouble(ht.get("amount")).doubleValue());
					pItem.setPrdNum((Constants.toDouble(ht.get("prdNum"))).intValue());
					pItem.setPrice(Constants.toDouble(ht.get("price")).doubleValue());
					pItem.setDeliveryTime(ht.get("deliveryTime").toString());
					pItem.setRemark(ht.get("remark").toString());
					pItem.setNs((Constants.toDouble(ht.get("ns"))).intValue());
					pItem.setNm((Constants.toDouble(ht.get("nm"))).intValue());
					pItem.setNl((Constants.toDouble(ht.get("nl"))).intValue());
					pItem.setNxl((Constants.toDouble(ht.get("nxl"))).intValue());
					pItem.setNxxl((Constants.toDouble(ht.get("nxxl"))).intValue());
					pItem.setIts(ht.get("its").toString());
					prd.getItems().add(pItem);
				}else{
					Product pd = new Product();
					pd.setId(Constants.toDouble(ht.get("prdId")).intValue());	
					pd.setPrdSmallImg(ht.get("prdSmallImg").toString());
					pd.setPrdName(ht.get("prdNo").toString());
					pd.setPrdNo(ht.get("prdNo").toString());
					pd.setMtlQty(ht.get("mtlQty")==null?"":ht.get("mtlQty").toString());
					ProductColorItem pItem = new ProductColorItem();
					//pItem.setId(Constants.toDouble(ht.get("Id")).intValue());
					pItem.setColorName(ht.get("colorName").toString());
					//pItem.setColorNo(ht.get("colorNo").toString());
					pItem.setSubTotal(Constants.toDouble(ht.get("amount")).doubleValue());
					pItem.setPrdNum((Constants.toDouble(ht.get("prdNum"))).intValue());
					pItem.setNs((Constants.toDouble(ht.get("ns"))).intValue());
					pItem.setNm((Constants.toDouble(ht.get("nm"))).intValue());
					pItem.setNl((Constants.toDouble(ht.get("nl"))).intValue());
					pItem.setNxl((Constants.toDouble(ht.get("nxl"))).intValue());
					pItem.setNxxl((Constants.toDouble(ht.get("nxxl"))).intValue());
					pItem.setPrice(Constants.toDouble(ht.get("price")).doubleValue());
					pItem.setDeliveryTime(ht.get("deliveryTime").toString());
					pItem.setRemark(ht.get("remark").toString());
					pItem.setIts(ht.get("its").toString());
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
	
	
	public RespResult<String> updateProduction(Production production){
		RespResult<String> resp = new RespResult<String>();
		List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("update_production_state");
		parameters.add(production.getCurNode());
		parameters.add(production.getCurOperatorId());
		parameters.add(production.getId());
		//System.out.println();
		try {
			this.sqlHelper.executeUpdate(sql, parameters);
		} catch (Exception e) {
			resp.setSuccess(false);
			resp.setDesc(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}
	
	public synchronized int newProductionId(){
		ConfContext context = this.sqlHelper.getSqlContext();
		//String sql = context.getScript("lastId");
		int lastId = -1;
		lastId = Integer.parseInt(this.sqlHelper.query4OneVal(context.getScript("last_productionId"), null));
		
		if(lastId <= 0){
			new Exception("newProductionId error!");
			log.error("newProductionId error!");
		} 
		
		return lastId;
	}
	
	public String nvl(Object obj){
		if(obj!= null){
			return obj.toString();
		}else return "";
		
	}
	public List<Hashtable<String, Object>> getOrderLogs(int orderId){
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getproductionlog");
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql, new ArrayList<Object>(Arrays.asList(orderId)));
		return hst;
	}
	
	public String getProductionListWhereStr(Production production,int start,int limit){
		String sql ="";
		if(production !=null ){
			if(production.getId() >0)
				sql += " and a.id = " + production.getId();
			if(production.getCreatorId() >0)
				sql += " and a.creatorId = " + production.getCreatorId();
			if(production.getCurOperatorId() >0)
				sql += " and a.curOperatorId = " + production.getCurOperatorId();
			if(production.getContractNo() != null && production.getContractNo().length() >0)
				sql += " and a.contractNo like '" + production.getContractNo()+"%'";
			if(production.getManufacturer() != null && production.getManufacturer().length() >0)
				sql += " and a.manufacturer like '" + production.getManufacturer()+"%'";
		}
		if(start >=0 && limit > 0){
			sql += " limit " + start + "," + limit;
		}
		
		return sql;
	}
	
	public int getNextOperator(int folwId,int curNode,int orderId){
		ConfContext context = this.sqlHelper.getSqlContext();
		List<Object> parameters = new ArrayList<>();
		//String sql = context.getScript("lastId");
		int nextOperatorId = -1;
		parameters.add(folwId);
		parameters.add(curNode);
		nextOperatorId = Integer.parseInt(this.sqlHelper.query4OneVal(context.getScript("getNextOperator"),
					parameters));
		
		if(nextOperatorId <= 0){
			new Exception("getNextOperator error!");
			log.error("getNextOperator error!");
		} 
		
		return nextOperatorId;
	}
	
	
	public void addorderlog(int orderId,int userId,String action,String remark){
		ConfContext context = this.sqlHelper.getSqlContext();
		List<Object> parameters = new ArrayList<>();
		parameters.add(orderId);
		parameters.add(userId);
		parameters.add(action);
		parameters.add(remark);
		this.sqlHelper.executeUpdate(context.getScript("addproductionlog"), parameters);
	}

	/**
	 * updateProductionState
	 *
	 * @param production
	 * @return 创建时间：2017年4月16日 上午11:01:45
	 */
	public RespResult<String> updateProductionState(Production production) {
		
		production.setCurOperatorId(this.getNextOperator(2, production.getCurNode(), production.getId()));
		return this.updateProduction(production);
		//return null;
	}

	/**
	 * editProduction
	 *
	 * @param production
	 * @return 创建时间：2017年4月17日 下午4:18:21
	 */
	public RespResult<String> editProduction(Production production) {
		RespResult<String> resp = new RespResult<String>();
		List<Object> parameters = new ArrayList<>();
		//int lastId = -1;
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("edit_production");
		//contractNo,manufacturer,signTime,signAddress,prdNum,amount,linkStaff,payBank,payNo,
		//manufacturerAddress,telphone ,flowId,curNode,curOperatorId,creatorId,STATUS
		
		//lastId = this.newProductionId();
		//parameters.add(lastId);
		//parameters.add(production.getContractNo());
		parameters.add(production.getManufacturer());
		parameters.add(production.getSignTime());
		parameters.add(production.getSignAddress());
		parameters.add(production.getPrdNum());
		parameters.add(production.getAmount()); 
		parameters.add(production.getLinkStaff());  
		parameters.add(production.getPayBank());  
		parameters.add(production.getPayNo());
		parameters.add(production.getManufacturerAddress());
		parameters.add(production.getTelphone());
		parameters.add(production.getFlowId());
		parameters.add(14);
		parameters.add(this.getNextOperator(2, 14,production.getId())); //curOperator
		parameters.add(production.getCreatorId());
		parameters.add(production.getStatus());
		//arameters.add(production.getTax());
		parameters.add(production.getTax()?0:1);
		
		parameters.add(production.getId());
		log.debug("parameters:" + JSON.toJSONString(parameters));
		
		this.sqlHelper.executeUpdate(context.getScript("back_productionitem"), new ArrayList<Object>(Arrays.asList(production.getId())));
		this.sqlHelper.executeUpdate(context.getScript("back_edit_production_order"), new ArrayList<Object>(Arrays.asList(production.getId())));
		
		int effect = this.sqlHelper.executeUpdate(sql, parameters);
		//log.info("effect:" + effect);
		
		if(effect >= 0 && production.getItems() != null 
				&& production.getItems().size() > 0){
			try {
				//lastId = Integer.parseInt(this.sqlHelper.query4OneVal(context.getScript("lastId"), null));
				//log.info("lastId:" + lastId);
				List<List<Object>> params = new ArrayList<>();
				for (int i = 0; i < production.getItems().size(); i++) {
					List<Object> p = new ArrayList<>();
					ProductionItem item = production.getItems().get(i);
					//pId,prdId,prdColorId,nxs,ns,nm,nl,nxl,nxxl,nxxxl,prdNum,amount,remark,deliveryTime,STATUS
					p.add(production.getId());
					p.add(item.getPrdId());
					p.add(item.getPrdColorId());
					p.add(item.getNxs());
					p.add(item.getNs());
					p.add(item.getNm());
					p.add(item.getNl());
					p.add(item.getNxl());
					p.add(item.getNxxl());
					p.add(item.getNxxxl());
					p.add(item.getPrdNum());
					p.add(item.getAmount());
					p.add(item.getRemark());
					p.add(item.getDeliveryTime());
					p.add(item.getStatus());
					p.add(item.getPrice());
					p.add(item.getIts());
					params.add(p);
				}
				
				this.sqlHelper.executeBatch(context.getScript("insert_productionitem"), params, "");
				
				this.sqlHelper.executeUpdate(context.getScript("update_productionitem"), new ArrayList<Object>(Arrays.asList(production.getId())));
				this.sqlHelper.executeUpdate(context.getScript("update_production"), new ArrayList<Object>(Arrays.asList(production.getId())));
				this.sqlHelper.executeUpdate("UPDATE r_orderitem i SET i.mntstate = "+
						production.getId()+" WHERE i.id IN ("+production.getIts()+");", null);
				//this.sqlHelper.executeUpdate(context.getScript("updateorderitems2"), new ArrayList<Object>(Arrays.asList(lastId)));
				//this.sqlHelper.executeUpdate(context.getScript("updateorder"), new ArrayList<Object>(Arrays.asList(lastId)));
				//this.addorderlog(lastId, order.getStaffId(), "1", order.getRemark());
				this.addorderlog(production.getId(), production.getCreatorId(), "8",production.getRemark());
				resp.setObj(String.valueOf(production.getId()));
			} catch (Exception e) {
				e.printStackTrace();
				//this.sqlHelper.executeUpdate(context.getScript("deleteorderitem"), new ArrayList<Object>(Arrays.asList(lastId)));
				//this.sqlHelper.executeUpdate(context.getScript("deleteorder"), new ArrayList<Object>(Arrays.asList(lastId)));
				resp.setSuccess(false);
				resp.setDesc(e.getMessage());
			}
		}else {
			resp.setSuccess(false);
			resp.setDesc("edit Production error , [Production.getItems] is null or effect < 0 ,"
					+ " please contact system administrator !");
		}
		return resp;
	}
}
