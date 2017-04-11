package com.howbuy.fp.product;

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
import com.howbuy.fp.utils.RespResult;
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
	 * updateProductStatus 更新产品的状态
	 * isVaild=0 上架状态 isVaild=1 下架状态
	 * @param product
	 * @return 创建时间：2017年3月29日 下午3:05:20
	 */
	public RespResult<String> updateProductStatus(int prdId, int status) {

		RespResult<String> respResult = new RespResult<String>();
		
		List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("updateProductShelf");
		
		parameters.add(status);
		parameters.add(prdId);
		try {
			this.sqlHelper.executeUpdate(sql, parameters);
		} catch (Exception e) {
			e.printStackTrace();
			respResult.setSuccess(false);
			respResult.setDesc(e.getMessage());
		}
		return respResult;
	}
	
	/**
	 * queryProductList 分页查询商品列表
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
		String sql = context.getScript("getproducts"); //包含所有状态
		log.debug("execute sql:" + sql );
		parameters.add(prdName+"%");
		parameters.add(start);
		parameters.add(limit);
		
		log.debug("parameters:" + JSON.toJSONString(parameters));
		
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql, parameters);
		return hst;
	}
	
	public List<Hashtable<String, Object>> queryAllProductList(String prdName ,int start ,int limit){
		
		//List<Product> products = new ArrayList<>();
		List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getAllproducts"); //包含所有状态
		log.debug("execute sql:" + sql );
		parameters.add(prdName+"%");
		parameters.add(start);
		parameters.add(limit);
		
		log.debug("parameters:" + JSON.toJSONString(parameters));
		
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql, parameters);
		return hst;
	}
	
	/**
	 * queryProductCatalog 查询所有产品分类
	 *
	 * @return 创建时间：2017年3月29日 下午3:06:45
	 */
	public List<Hashtable<String, Object>> queryProductCatalog(){
		//List<Product> products = new ArrayList<>();
		//List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("productcatalog");
		
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql, null);
		return hst;
	}
	
	public List<Hashtable<String, Object>> autoProductList(String prdName){
		List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("autoproduct");
		parameters.add("%"+prdName+"%");
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
	public int getAllProductsCount(String prdName){
		List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getallproductsCount");
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
			prd.setMtlQty(Constants.isBlank(hst.get("mtlQty"))?"":hst.get("mtlQty").toString());
			prd.setPrdSmallImg(hst.get("prdSmallImg").toString());
			prd.setPrdImg(hst.get("prdImg").toString());
			prd.setPrdName(hst.get("prdName").toString());
			prd.setPrdNo(hst.get("prdNo").toString());
			prd.setPrdType(hst.get("prdType").toString());
			prd.setPrice(Constants.toDouble(hst.get("price")));
			prd.setRemark(Constants.isBlank(hst.get("remark"))?"":hst.get("remark").toString());
			prd.setShelf(Constants.isBlank(hst.get("isVaild"))?"":hst.get("isVaild").toString());
			prd.setItems(getProductColorById(prdId,0)); //查询可用状态的产品
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
				pItem.setRemark(ht.get("remark").toString());
				pItem.setStatus(Constants.toDouble(ht.get("isVaild")).intValue());
				items.add(pItem);
			}
		}
		return items;
	}
	
	/**
	 * getProductColorById
	 *
	 * @param prdId
	 * @param isVaild
	 * @return 创建时间：2017年3月31日 下午7:29:55
	 */
	public List<ProductColorItem> getProductColorById(int prdId,int isVaild){
		List<ProductColorItem> items = new ArrayList<ProductColorItem>();
		List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getProductColorByIdStatus");
		log.debug("execute sql:" + sql );
		parameters.add(prdId);
		parameters.add(isVaild);
		log.debug("parameters:" + JSON.toJSONString(parameters));
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql, parameters);
		if(hst !=null && hst.size() >0){
			for (Iterator iterator = hst.iterator(); iterator.hasNext();) {
				Hashtable<String, Object> ht = (Hashtable<String, Object>) iterator.next();
				ProductColorItem pItem = new ProductColorItem();
				pItem.setId(Constants.toDouble(ht.get("Id")).intValue());
				pItem.setColorName(ht.get("colorName").toString());
				pItem.setColorNo(ht.get("colorNo").toString());
				pItem.setRemark(ht.get("remark").toString());
				pItem.setStatus(Constants.toDouble(ht.get("isVaild")).intValue());
				items.add(pItem);
			}
		}
		return items;
	}
	
	/**
	 * addProduct
	 *
	 * @param product
	 * @return 创建时间：2017年3月27日 下午2:36:45
	 */
	public RespResult<String> addProduct(Product product) {
		//prdNo,prdName,catalog,prdSamllImg,prdImg,matWgt,price,
		//batchPrice,remark,sampleTime,sampleWgt,colorRatio,isVaild
		
		RespResult<String> respResult = new RespResult<String>();
		
		List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("addProduct");
		parameters.add(product.getPrdNo());
		parameters.add("".equals(product.getPrdName())?product.getPrdNo():product.getPrdName());
		parameters.add(product.getCatalog());
		parameters.add(product.getPrdSmallImg());
		parameters.add(product.getPrdImg());
		parameters.add(product.getMatWgt());
		parameters.add(product.getPrice());
		parameters.add(product.getBatchPrice());
		parameters.add(product.getRemark());
		parameters.add(product.getSampleTime());
		parameters.add(product.getSampleWgt());
		parameters.add(product.getColorRatio());
		parameters.add("on".equals(product.getShelf())?0:1);
		try {
			this.sqlHelper.executeUpdate(sql, parameters);
		} catch (Exception e) {
			e.printStackTrace();
			respResult.setSuccess(false);
			respResult.setDesc(e.getMessage());
		}
		return respResult;
	}

	/**
	 * updateProduct
	 *
	 * @param product
	 * @return 创建时间：2017年3月29日 上午11:18:01
	 */
	public RespResult<String> updateProduct(Product product) {
		RespResult<String> respResult = new RespResult<String>();
		List<Object> parameters = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		parameters.add("".equals(product.getPrdName())?product.getPrdNo():product.getPrdName());
		parameters.add(product.getCatalog());
		sb.append("UPDATE c_product SET prdName=?,catalog=?,");
		if(!Constants.isBlank(product.getPrdSmallImg())){
			sb.append("prdSmallImg=?,");
			parameters.add(product.getPrdSmallImg());
		}
		if(!Constants.isBlank(product.getPrdImg())){
			sb.append("prdImg=?,");
			parameters.add(product.getPrdImg());
		}
		sb.append("matWgt=?,price=?,");
		sb.append("batchPrice=?,remark=?,mtlQty=?,sampleTime=?,sampleWgt=?,colorRatio=?,isVaild=? WHERE id = ?");
		
		//ConfContext context = this.sqlHelper.getSqlContext();
		//String sql = context.getScript("addProduct");
		//parameters.add(product.getPrdNo());
		parameters.add(product.getMatWgt());
		parameters.add(product.getPrice());
		parameters.add(product.getBatchPrice());
		parameters.add(product.getRemark());
		parameters.add(product.getMtlQty());
		parameters.add(product.getSampleTime());
		parameters.add(product.getSampleWgt());
		parameters.add(product.getColorRatio());
		parameters.add("on".equals(product.getShelf())?0:1);
		parameters.add(product.getId());
		try {
			this.sqlHelper.executeUpdate(sb.toString(), parameters);
		} catch (Exception e) {
			e.printStackTrace();
			respResult.setSuccess(false);
			respResult.setDesc(e.getMessage());
		}
		return respResult;
	}


	/**
	 * updateProductItem
	 *
	 * @param item 创建时间：2017年3月29日 下午4:01:44
	 */
	public void updateProductItem(ProductColorItem item) {
		RespResult<String> respResult = new RespResult<String>();
		
		List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("update_c_product_color");
		
		parameters.add(item.getStatus());
		parameters.add(item.getRemark());
		parameters.add(item.getId());
		try {
			this.sqlHelper.executeUpdate(sql, parameters);
		} catch (Exception e) {
			e.printStackTrace();
			respResult.setSuccess(false);
			respResult.setDesc(e.getMessage());
		}
		
	}


	/**
	 * insertProductItem
	 *
	 * @param item 创建时间：2017年3月29日 下午4:01:50
	 */
	public void insertProductItem(ProductColorItem item) {
		//prdId,colorNo,colorName,remark
		List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("add_c_product_color");
		
		parameters.add(item.getPrdId());
		parameters.add(item.getColorNo());
		parameters.add(item.getColorName());
		parameters.add(item.getRemark());
		try {
			this.sqlHelper.executeUpdate(sql, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
