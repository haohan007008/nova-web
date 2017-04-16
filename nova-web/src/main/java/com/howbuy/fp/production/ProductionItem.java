package com.howbuy.fp.production;

import com.howbuy.fp.store.ProductItem;

/** 
 * @author LvMeng
 * @version 2017年4月5日 下午1:26:03
 */
public class ProductionItem extends ProductItem{
	private int pId;
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	private double amount;
	private double price;
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	private String deliveryTime;
	
}
