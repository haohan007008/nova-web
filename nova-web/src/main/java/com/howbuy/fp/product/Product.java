package com.howbuy.fp.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** 
 * @author LvMeng
 * @version 2017年3月6日 下午6:50:21
 */
public class Product {
	private int id;
	private String prdNo ;
	private String prdName ;
	private String catalog ;
	private String prdType;
	private String prdImg;
	private String prdSmallImg;
	
	private BigDecimal matWgt;
	private BigDecimal price;
	private BigDecimal batchPrice;
	private String remark;
	private List<ProductColorItem> items;
	public String getPrdSmallImg() {
		return prdSmallImg;
	}

	public void setPrdSmallImg(String prdSmallImg) {
		this.prdSmallImg = prdSmallImg;
	}
	public void unionItems(List<ProductColorItem> its){
		if(its != null && its.size()>0){
			for (int i = 0; i < its.size(); i++) {
				addItems(its.get(i));
			}
		}
	}
	
	public void addItems(ProductColorItem it){
		if(items != null && items.size() > 0){
			boolean has = false;
			for (int i = 0; i < items.size(); i++) {
				ProductColorItem prd = (ProductColorItem)items.get(i);
				if(prd.getId() == it.getId()){
					has = true;
					items.get(i).add(it);
					break;
				}
			}
			if(!has)
				items.add(it);
		}else{
			items = new ArrayList<>();
			items.add(it);
		};
	}
	
	public BigDecimal getMatWgt() {
		return matWgt;
	}
	public void setMatWgt(BigDecimal matWgt) {
		this.matWgt = matWgt;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getBatchPrice() {
		return batchPrice;
	}
	public void setBatchPrice(BigDecimal batchPrice) {
		this.batchPrice = batchPrice;
	}
	
	
	public List<ProductColorItem> getItems() {
		return items;
	}
	public void setItems(List<ProductColorItem> items) {
		this.items = items;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPrdNo() {
		return prdNo;
	}
	public void setPrdNo(String prdNo) {
		this.prdNo = prdNo;
	}
	public String getPrdName() {
		return prdName;
	}
	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String getPrdType() {
		return prdType;
	}
	public void setPrdType(String prdType) {
		this.prdType = prdType;
	}
	public String getPrdImg() {
		return prdImg;
	}
	public void setPrdImg(String prdImg) {
		this.prdImg = prdImg;
	}
	
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
