package com.howbuy.fp.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/** 
 * @author LvMeng
 * @version 2017年3月6日 下午6:50:21
 */
public class Product implements Comparable{
	private int id;
	private String prdNo ;
	private String prdName ;
	private String catalog ;
	private String prdType;
	private String prdImg;
	private String prdSmallImg;
	
	private CommonsMultipartFile prdImg1;
	public CommonsMultipartFile getPrdImg1() {
		return prdImg1;
	}

	public void setPrdImg1(CommonsMultipartFile prdImg1) {
		this.prdImg1 = prdImg1;
	}

	public CommonsMultipartFile getPrdSmallImg1() {
		return prdSmallImg1;
	}

	public void setPrdSmallImg1(CommonsMultipartFile prdSmallImg1) {
		this.prdSmallImg1 = prdSmallImg1;
	}
	private CommonsMultipartFile prdSmallImg1;
	
	private BigDecimal matWgt;
	private BigDecimal price;
	private BigDecimal batchPrice;
	private String remark;
	
	private String shelf;
	public String getShelf() {
		return shelf;
	}

	public void setShelf(String shelf) {
		this.shelf = shelf;
	}

	public String getSampleTime() {
		return sampleTime;
	}

	public void setSampleTime(String sampleTime) {
		this.sampleTime = sampleTime;
	}

	public double getSampleWgt() {
		if(sampleWgt == null) return 0;
		return sampleWgt.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setSampleWgt(BigDecimal sampleWgt) {
		this.sampleWgt = sampleWgt;
	}

	public String getMtlQty() {
		return mtlQty;
	}

	public void setMtlQty(String mtlQty) {
		this.mtlQty = mtlQty;
	}

	public String getColorRatio() {
		return colorRatio;
	}

	public void setColorRatio(String colorRatio) {
		this.colorRatio = colorRatio;
	}
	private String sampleTime;
	private BigDecimal sampleWgt;
	private String mtlQty;
	private String colorRatio;
	
	
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
	
	
	public int getPrdNum(){
		int prdNum = 0;
		if(items != null && items.size()>0){
			for (int i = 0; i < items.size(); i++) {
				prdNum += items.get(i).getPrdNum();
			}
		}
		return prdNum;
	}
	
	public double getTotal(){
		double total = 0;
		if(items != null && items.size()>0){
			for (int i = 0; i < items.size(); i++) {
				total += items.get(i).getSubTotal();
			}
		}
		return total;
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
	
	public double getMatWgt() {
		if(matWgt == null) return 0;
		return matWgt.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public void setMatWgt(BigDecimal matWgt) {
		this.matWgt = matWgt;
	}
	public double getPrice() {
		if(price == null) return 0;
		return price.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
		
	}
	public double getBatchPrice() {
		//return batchPrice;
		if(batchPrice == null) return 0;
		return batchPrice.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();
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

	@Override
	public int compareTo(Object o) {
		Product product = (Product) o;
		return this.getPrdNo().replace("16", "99").compareTo(
				product.getPrdNo().replace("16", "99"));
	}
	
	
	
}
