package com.howbuy.fp.production;

import java.util.Hashtable;
import java.util.List;

import com.howbuy.fp.product.Product;

/** 
 * @author LvMeng
 * @version 2017年4月5日 下午2:46:51
 */
public class Production {
	private int id;
	private String contractNo;
	private String manufacturer;
	private String signTime;
	private String signAddress;
	private int prdNum;
	private double amount;
	private String linkStaff;
	private String payBank;
	private String payNo;
	private String manufacturerAddress;
	private String telphone;
	private int curOperatorId;
	private int creatorId;
	private String creatorName;
	private String curOperatorName;
	private int status;
	private int flowId;
	private int curNode;
	private String curNodeName;
	private String its;
	private List<Product> products;
	private String remark;
	private String action;
	private List<Hashtable<String, Object>> logs;
	
	public List<Hashtable<String, Object>> getLogs() {
		return logs;
	}
	public void setLogs(List<Hashtable<String, Object>> logs) {
		this.logs = logs;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public String getIts() {
		return its;
	}
	public void setIts(String its) {
		this.its = its;
	}
	public int getFlowId() {
		return flowId;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	public int getCurNode() {
		return curNode;
	}
	public void setCurNode(int curNode) {
		this.curNode = curNode;
	}
	public String getCurNodeName() {
		return curNodeName;
	}
	public void setCurNodeName(String curNodeName) {
		this.curNodeName = curNodeName;
	}
	private List<ProductionItem> items;
	
	public List<ProductionItem> getItems() {
		return items;
	}
	public void setItems(List<ProductionItem> list) {
		this.items = list;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getSignTime() {
		return signTime;
	}
	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	public String getSignAddress() {
		return signAddress;
	}
	public void setSignAddress(String signAddress) {
		this.signAddress = signAddress;
	}
	public int getPrdNum() {
		return prdNum;
	}
	public void setPrdNum(int prdNum) {
		this.prdNum = prdNum;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getLinkStaff() {
		return linkStaff;
	}
	public void setLinkStaff(String linkStaff) {
		this.linkStaff = linkStaff;
	}
	public String getPayBank() {
		return payBank;
	}
	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}
	public String getPayNo() {
		return payNo;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	public String getManufacturerAddress() {
		return manufacturerAddress;
	}
	public void setManufacturerAddress(String manufacturerAddress) {
		this.manufacturerAddress = manufacturerAddress;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public int getCurOperatorId() {
		return curOperatorId;
	}
	public void setCurOperatorId(int curOperatorId) {
		this.curOperatorId = curOperatorId;
	}
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getCurOperatorName() {
		return curOperatorName;
	}
	public void setCurOperatorName(String curOperatorName) {
		this.curOperatorName = curOperatorName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
