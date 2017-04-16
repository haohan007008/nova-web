package com.howbuy.fp.store;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.howbuy.fp.utils.ConfContext;
import com.howbuy.fp.utils.Constants;
import com.howbuy.fp.utils.SqlHelper;

/** 
 * @author LvMeng
 * @version 2017年3月6日 下午6:48:41
 */
@Service
public class StoreDAO {
	private static Logger log = Logger.getLogger(StoreDAO.class);
	
	@Autowired
	private SqlHelper sqlHelper;

	public void setSqlHelper(SqlHelper sqlHelper) {
		this.sqlHelper = sqlHelper;
	}
	
	public void inStore(ProductItem item){
		ConfContext context = this.sqlHelper.getSqlContext();
		List<Object> parameters = new ArrayList<>();
		String sql = context.getScript("prd_in_store");
		parameters.add(item.getPrdId());
		parameters.add(item.getPrdColorId());
		int cnt = Integer.parseInt(this.sqlHelper.query4OneVal(sql,parameters));
		
		if(cnt > 0){
			parameters.clear();
			sql = context.getScript("update_store");
			parameters.add(item.getNxs());
			parameters.add(item.getNs());
			parameters.add(item.getNm());
			parameters.add(item.getNl());
			parameters.add(item.getNxl());
			parameters.add(item.getNxxl());
			parameters.add(item.getNxxxl());
			parameters.add(item.getNunno());
			parameters.add(item.getPrdId());
			parameters.add(item.getPrdColorId());
			this.sqlHelper.executeUpdate(sql, parameters);
		}else {
			parameters.clear();
			parameters.add(item.getPrdId());
			parameters.add(item.getPrdColorId());
			parameters.add(item.getNxs());
			parameters.add(item.getNs());
			parameters.add(item.getNm());
			parameters.add(item.getNl());
			parameters.add(item.getNxl());
			parameters.add(item.getNxxl());
			parameters.add(item.getNxxxl());
			parameters.add(item.getNunno());
			sql = context.getScript("in_store");
			this.sqlHelper.executeUpdate(sql, parameters);
		}
	}
	
	public void outStore(ProductItem item){
		ConfContext context = this.sqlHelper.getSqlContext();
		List<Object> parameters = new ArrayList<>();
		String sql = context.getScript("update_store");
		parameters.add(0-item.getNxs());
		parameters.add(0-item.getNs());
		parameters.add(0-item.getNm());
		parameters.add(0-item.getNl());
		parameters.add(0-item.getNxl());
		parameters.add(0-item.getNxxl());
		parameters.add(0-item.getNxxxl());
		parameters.add(0-item.getNunno());
		parameters.add(item.getPrdId());
		parameters.add(item.getPrdColorId());
		this.sqlHelper.executeUpdate(sql, parameters);
	}
	
	
	/**
	 * getProductStoreInfo
	 *
	 * @param prdId
	 * @return 创建时间：2017年4月5日 下午2:06:49
	 */
	public List<ProductItem> getProductStoreInfo(String prdId){
		List<ProductItem> items = new ArrayList<ProductItem>();
		List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("store_query");
		if(prdId != null ){
			parameters.add(prdId);
			sql = sql + " AND a.`prdId` = ?";
			log.debug("execute sql:" + sql );
		}else {
			sql = sql + " limit 0,50";
		}
		
		log.debug("parameters:" + JSON.toJSONString(parameters));
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql, parameters);
		if(hst !=null && hst.size() >0){
			for (Iterator iterator = hst.iterator(); iterator.hasNext();) {
				Hashtable<String, Object> ht = (Hashtable<String, Object>) iterator.next();
				ProductItem pItem = new ProductItem();
				//pItem.setId(Constants.toDouble(ht.get("Id")).intValue());
				pItem.setPrdId(Constants.toDouble(ht.get("prdId")).intValue());
				pItem.setPrdNo(ht.get("prdNo").toString());
				pItem.setPrdId(Constants.toDouble(ht.get("prdColorId")).intValue());
				pItem.setColorName(ht.get("colorName").toString());
				pItem.setColorNo(ht.get("colorNo").toString());
				pItem.setNs((Constants.toDouble(ht.get("ns"))).intValue());
				pItem.setNxs((Constants.toDouble(ht.get("nxs"))).intValue());
				pItem.setNm((Constants.toDouble(ht.get("nm"))).intValue());
				pItem.setNl((Constants.toDouble(ht.get("nl"))).intValue());
				pItem.setNxl((Constants.toDouble(ht.get("nxl"))).intValue());
				pItem.setNxxl((Constants.toDouble(ht.get("nxxl"))).intValue());
				pItem.setNxxxl((Constants.toDouble(ht.get("nxxxl"))).intValue());
				pItem.setNunno((Constants.toDouble(ht.get("nunno"))).intValue());
				//pItem.setRemark(ht.get("remark").toString());
				//pItem.setStatus(Constants.toDouble(ht.get("isVaild")).intValue());
				items.add(pItem);
			}
		}
		return items;
	}
	
}
