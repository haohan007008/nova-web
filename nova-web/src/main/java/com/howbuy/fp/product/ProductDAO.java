package com.howbuy.fp.product;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.howbuy.fp.user.UserDAO;
import com.howbuy.fp.user.UserVO;
import com.howbuy.fp.utils.ConfContext;
import com.howbuy.fp.utils.Constants;
import com.howbuy.fp.utils.SqlHelper;

/** 
 * @author LvMeng
 * @version 2017年3月6日 下午6:48:41
 */
@Service
public class ProductDAO {
	private static Logger log = Logger.getLogger(ProductDAO.class);
	
	@Autowired
	private SqlHelper sqlHelper;

	public void setSqlHelper(SqlHelper sqlHelper) {
		this.sqlHelper = sqlHelper;
	}
	
	/**
	 * queryProductList
	 *
	 * @param prdName
	 * @param start
	 * @param limit
	 * @return 创建时间：2017年3月8日 上午9:48:38
	 */
	public List<Hashtable<String, Object>> queryProductList(String prdName ,int start ,int limit){
		
		//List<Product> products = new ArrayList<>();
		List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getproducts");
		log.debug("execute sql:" + sql );
		parameters.add(prdName+"%");
		parameters.add(start);
		parameters.add(limit);
		
		log.debug("parameters:" + JSON.toJSONString(parameters));
		
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql, parameters);
		return hst;
	}
	
	/**
	 * getProductsCount
	 *
	 * @param prdName
	 * @return 创建时间：2017年3月7日 下午8:48:06
	 */
	public int getProductsCount(String prdName){
		List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getproductsCount");
		log.debug("execute sql:" + sql );
		parameters.add(prdName+"%");
		log.debug("parameters:" + JSON.toJSONString(parameters));
		String str = this.sqlHelper.query4OneVal( sql, parameters);
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		 
	}
	
	/**
	 * getProductById
	 *
	 * @param prdId
	 * @return 创建时间：2017年3月7日 下午8:40:46
	 */
	public Product getProductById(int prdId){
		Product prd = new Product();
		List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getproductById");
		log.debug("execute sql:" + sql );
		parameters.add(prdId);
		log.debug("parameters:" + JSON.toJSONString(parameters));
		Hashtable<String, Object> hst = this.sqlHelper.query4OneObject(sql, parameters);
		if(hst != null ){
			prd.setBatchPrice(Constants.toDouble(hst.get("batchPrice")));
			prd.setCatalog(hst.get("catalog_name").toString());
			prd.setId(prdId);	
			prd.setMatWgt(Constants.toDouble(hst.get("matWgt")));
			prd.setPrdSmallImg(hst.get("prdSmallImg").toString());
			prd.setPrdImg(hst.get("prdImg").toString());
			prd.setPrdName(hst.get("prdName").toString());
			prd.setPrdNo(hst.get("prdNo").toString());
			prd.setPrdType(hst.get("prdType").toString());
			prd.setPrice(Constants.toDouble(hst.get("price")));
			prd.setRemark(Constants.isBlank(hst.get("remark"))?"":hst.get("remark").toString());
			prd.setItems(getProductColorById(prdId));
		}
		return prd;
	}
	
	/**
	 * getProductById
	 *
	 * @param prdId
	 * @return 创建时间：2017年3月7日 下午8:40:46
	 */
	public Product getProductByIdNoItems(int prdId){
		Product prd = new Product();
		List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getproductById");
		log.debug("execute sql:" + sql );
		parameters.add(prdId);
		log.debug("parameters:" + JSON.toJSONString(parameters));
		Hashtable<String, Object> hst = this.sqlHelper.query4OneObject(sql, parameters);
		if(hst != null ){
			prd.setBatchPrice(Constants.toDouble(hst.get("batchPrice")));
			prd.setCatalog(hst.get("catalog_name").toString()); 
			prd.setId(prdId);	
			prd.setMatWgt(Constants.toDouble(hst.get("matWgt")));
			prd.setPrdSmallImg(hst.get("prdSmallImg").toString());
			prd.setPrdImg(hst.get("prdImg").toString());
			prd.setPrdName(hst.get("prdName").toString());
			prd.setPrdNo(hst.get("prdNo").toString());
			prd.setPrdType(hst.get("prdType").toString());
			prd.setPrice(Constants.toDouble(hst.get("price")));
			prd.setRemark(Constants.isBlank(hst.get("remark"))?"":hst.get("remark").toString());
			//prd.setItems(getProductColorById(prdId));
		}
		return prd;
	}
	
	/**
	 * getProductColorById
	 *
	 * @param prdId
	 * @return 创建时间：2017年3月7日 下午8:47:56
	 */
	public List<ProductColorItem> getProductColorById(int prdId){
		List<ProductColorItem> items = new ArrayList<ProductColorItem>();
		List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getProductColorById");
		log.debug("execute sql:" + sql );
		parameters.add(prdId);
		log.debug("parameters:" + JSON.toJSONString(parameters));
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql, parameters);
		if(hst !=null && hst.size() >0){
			for (Iterator iterator = hst.iterator(); iterator.hasNext();) {
				Hashtable<String, Object> ht = (Hashtable<String, Object>) iterator.next();
				ProductColorItem pItem = new ProductColorItem();
				pItem.setId(Constants.toDouble(ht.get("Id")).intValue());
				pItem.setColorName(ht.get("colorName").toString());
				pItem.setColorNo(ht.get("colorNo").toString());
				items.add(pItem);
			}
		}
		return items;
	}
	
}
