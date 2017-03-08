package com.howbuy.fp.product;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.taglibs.standard.tag.common.xml.IfTag;
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
	
	public RespResult<List<Hashtable<String, Object>>> getProducts(
				String prdName,int start,int limit){
		RespResult<List<Hashtable<String, Object>>> resp = new RespResult<>();
		List<Hashtable<String, Object>> hst = productDAO.queryProductList(prdName,start,limit);
		resp.setObj(hst);
		resp.setTotal(productDAO.getProductsCount(prdName));
		return resp;
	}
	
	public Product getProductById(int prdId){
		return productDAO.getProductById(prdId);
	}
	
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
					for (int j = 0; j < s; j++) {
						if(((ProductColorItem)prd.getItems().get(j)).getId() == cid){
							s = prd.getItems().size();
							prd.getItems().remove(j);
						}
					}
					if(s == 1) list.remove(i);
				}
			}
		}
		return list;
	}
}
