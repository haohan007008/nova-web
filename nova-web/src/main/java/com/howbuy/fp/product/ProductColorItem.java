package com.howbuy.fp.product;
/** 
 * @author LvMeng
 * @version 2017年3月7日 下午8:21:08
 */
public class ProductColorItem {
	private int Id;
	private int prdId;
	private String colorName;
	private String colorNo;
	private int ns = 0;
	private int nm = 0;
	private int nl = 0;
	private int nxl = 0;
	private int nxxl = 0;
	
	public int getPrdId() {
		return prdId;
	}

	public void setPrdId(int prdId) {
		this.prdId = prdId;
	}
	
	public int getTotalCount(){
		return ns+nm+nl+nxl+nxxl;
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
	
	public ProductColorItem add(ProductColorItem item){
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
