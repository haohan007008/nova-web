package com.howbuy.fp.product;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.howbuy.fp.utils.RespResult;

/** 
 * @author LvMeng
 * @version 2017��3��2�� ����8:01:18
 */
@Service
public class ProductService {
	@Autowired
	private ProductDAO productDAO;

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	/**
	 * getProducts 查询产品清单
	 *
	 * @param prdName
	 * @param start
	 * @param limit
	 * @return 创建时间：2017年3月29日 下午3:19:44
	 */
	public RespResult<List<Hashtable<String, Object>>> getProducts(
				String prdName,int start,int limit){
		RespResult<List<Hashtable<String, Object>>> resp = new RespResult<>();
		List<Hashtable<String, Object>> hst = productDAO.queryProductList(prdName,start,limit);
		resp.setObj(hst);
		resp.setTotal(productDAO.getProductsCount(prdName));
		return resp;
	}
	public RespResult<List<Hashtable<String, Object>>> getAllProducts(
			String prdName,int start,int limit){
		RespResult<List<Hashtable<String, Object>>> resp = new RespResult<>();
		List<Hashtable<String, Object>> hst = productDAO.queryAllProductList(prdName,start,limit);
		resp.setObj(hst);
		resp.setTotal(productDAO.getAllProductsCount(prdName));
		return resp;
	}
	/**
	 * updateShelf 商品上架
	 *
	 * @param prdId
	 * @return 创建时间：2017年3月29日 下午3:13:32
	 */
	public RespResult<String> upShelf(int prdId){
		return productDAO.updateProductStatus(prdId,0);
	}
	
	/**
	 * downShelf 商品下架
	 *
	 * @param prdId
	 * @return 创建时间：2017年3月29日 下午3:14:10
	 */
	public RespResult<String> downShelf(int prdId){
		return productDAO.updateProductStatus(prdId,1);
	}
	
	
	/**
	 * addProduct 添加一个商品
	 *
	 * @param product
	 * @return 创建时间：2017年3月29日 下午3:14:27
	 */
	public RespResult<String> addProduct(Product product){
		
		return productDAO.addProduct(product);
	}
	
	/**
	 * updateProduct 更新商品信息
	 *
	 * @param product
	 * @return 创建时间：2017年3月29日 下午3:14:46
	 */
	public RespResult<String> updateProduct(Product product){
		
		return productDAO.updateProduct(product);
	}
	
	/**
	 * queryProductCatalogList 查询产品分类信息
	 *
	 * @return 创建时间：2017年3月29日 下午3:16:31
	 */
	public RespResult<List<Hashtable<String, Object>>> queryProductCatalogList(){
		RespResult<List<Hashtable<String, Object>>> resp = new RespResult<>();
		List<Hashtable<String, Object>> hst = productDAO.queryProductCatalog();
		resp.setObj(hst);
		//resp.setTotal(productDAO.getProductsCount(prdName));
		return resp;
	}
	
	public RespResult<List<Hashtable<String, Object>>> autoProductList(
			String prdName){
		RespResult<List<Hashtable<String, Object>>> resp = new RespResult<>();
		List<Hashtable<String, Object>> hst = productDAO.autoProductList(prdName);
		resp.setObj(hst);
		resp.setTotal(productDAO.getProductsCount(prdName));
		return resp;
	}
	
	/**
	 * getProductById 通过产品ID查询产品详细信息
	 *
	 * @param prdId
	 * @return 创建时间：2017年3月29日 下午3:16:51
	 */
	public Product getProductById(int prdId){
		return productDAO.getProductById(prdId);
	}
	
	/**
	 * getProductListDetail 补充商品详情信息
	 * 
	 * @param products
	 * @return 创建时间：2017年3月29日 下午3:18:05
	 */
	public List<Product> getProductListDetail(List<Product> products){
		if(products!=null && products.size() > 0){
			List<Product> prds = new ArrayList<>();
			for (int i = 0; i < products.size(); i++) {
				Product prd = (Product)products.get(i);
				Product pd = productDAO.getProductById(prd.getId());
				if(pd != null){
					pd.setItems(prd.getItems());
					prds.add(pd);
				}
			}
			return prds;
		}
		return products;
	}
	
	/**
	 * delCart
	 *
	 * @param list
	 * @param prdId
	 * @param cid
	 * @return 创建时间：2017年3月8日 下午9:23:35
	 */
	public List<Product> delCart(List<Product> list,int prdId,int cid){
		if(list!=null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				
				Product prd = (Product)list.get(i);
				int s = 0;
				
				if(prd.getId() == prdId && 
						prd.getItems() != null && prd.getItems().size() > 0){
					
						List<ProductColorItem> items = prd.getItems();
						for (int j = 0; j < items.size(); j++) {
							ProductColorItem item = items.get(j);
							if(item.getId() == cid){
								s = prd.getItems().size();
								list.get(i).getItems().remove(j);
								if(s == 1) list.remove(i);
							}
						}
				}
			}
		}
		return list;
	}

	/**
	 * getProductItems
	 *
	 * @param pid
	 * @return 创建时间：2017年3月29日 下午3:42:02
	 */
	public RespResult<List<ProductColorItem>> getProductItems(int pid) {
		RespResult<List<ProductColorItem>> resp = new RespResult<>();
		List<ProductColorItem> hst = productDAO.getProductColorById(pid);
		resp.setObj(hst);
		return resp;
	}

	/**
	 * updateItems
	 *
	 * @param list
	 * @return 创建时间：2017年3月29日 下午3:57:49
	 */
	public RespResult<String> updateItems(List<ProductColorItem> list) {
		RespResult<String> resp = new RespResult<>();
		if(list != null && list.size() > 0){
			for(ProductColorItem item : list){
				if(item.getId() > 0 ){
					//do udpate
					productDAO.updateProductItem(item);
				}else {
					//do insert 
					productDAO.insertProductItem(item);
				}
			}
		}else {
			resp.setSuccess(false);
			resp.setDesc("款式数据为空!");
		}
		return resp;
	}
}
