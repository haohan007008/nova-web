package com.howbuy.fp.store;
/** 
 * @author LvMeng
 * @version 2017年3月7日 下午8:21:08
 */
public class ProductItem {
	private int Id;
	private int prdId;
	private String prdNo;
	public String getPrdNo() {
		return prdNo;
	}

	public void setPrdNo(String prdNo) {
		this.prdNo = prdNo;
	}

	private int prdColorId;
	private String colorName;
	private String colorNo;
	private int ns = 0;
	private int nm = 0;
	private int nl = 0;
	private int nxl = 0;
	private int nxxl = 0;
	private int nxs =0;
	private int nxxxl = 0;
	private int nunno =0;
	public int getNunno() {
		return nunno;
	}

	public void setNunno(int nunno) {
		this.nunno = nunno;
	}

	private int status;
	private int prdNum = 0;
	private String deliveryTime;
	
	public int getPrdColorId() {
		return prdColorId;
	}

	public void setPrdColorId(int prdColorId) {
		this.prdColorId = prdColorId;
	}
	public int getNxs() {
		return nxs;
	}

	public void setNxs(int nxs) {
		this.nxs = nxs;
	}

	public int getNxxxl() {
		return nxxxl;
	}

	public void setNxxxl(int nxxxl) {
		this.nxxxl = nxxxl;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String remark;
	
	
	
	public int getPrdNum() {
		return prdNum;
	}

	public void setPrdNum(int prdNum) {
		this.prdNum = prdNum;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	private double price = 0;
	private double subTotal = 0;
	
	
	
	public int getPrdId() {
		return prdId;
	}

	public void setPrdId(int prdId) {
		this.prdId = prdId;
	}
	
	public int getTotalCount(){
		return ns+nm+nl+nxl+nxxl+nxs+nxxxl;
	}
	
	public double getTotalCost(double price){
		return getTotalCount() * price;
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public String getColorNo() {
		return colorNo;
	}
	public void setColorNo(String colorNo) {
		this.colorNo = colorNo;
	}
	public int getNs() {
		return ns;
	}
	public void setNs(int ns) {
		this.ns = ns;
	}
	public int getNm() {
		return nm;
	}
	public void setNm(int nm) {
		this.nm = nm;
	}
	public int getNl() {
		return nl;
	}
	public void setNl(int nl) {
		this.nl = nl;
	}
	public int getNxl() {
		return nxl;
	}
	public void setNxl(int nxl) {
		this.nxl = nxl;
	}
	public int getNxxl() {
		return nxxl;
	}
	public void setNxxl(int nxxl) {
		this.nxxl = nxxl;
	}
	
	public ProductItem add(ProductItem item){
		if(this.Id == item.getId()){
			this.ns += item.getNs();
			this.nm += item.getNm();
			this.nl += item.getNl();
			this.nxl += item.getNxl();
			this.nxxl += item.getNxxl();
		}
		return this;
	}
}
