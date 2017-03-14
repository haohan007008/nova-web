package com.howbuy.fp.order;

import java.util.Hashtable;
import java.util.List;

import com.howbuy.fp.product.Product;
import com.howbuy.fp.product.ProductColorItem;

/** 
 * @author LvMeng
 * @version 2017年3月9日 下午2:07:48
 */
public class Order {
	private int id;
	private String orderNo;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	private int custId ;
	private String custName;
	private String custAddr;
	private String remark;
	private int staffId;
	private String shipAddress;
	private int flowId;
	private int creatorId;
	private String createDate;
	private int oprState;
	private int curNodeId;
	private String curNodeName;
	private int totalPrd;
	private double totalPay;
	private double prePay;
	private String prePayTime;
	private double lastPay;
	private String payAccount;
	private String expressNo;
	private String expressName;
	private int curOperatorId;
	private String creatorName;
	private String curOperatorName;
	private List<ProductColorItem> items;
	private List<Product> prds;
	private List<Hashtable<String, Object>> logs;
	public List<Hashtable<String, Object>> getLogs() {
		return logs;
	}
	public void setLogs(List<Hashtable<String, Object>> logs) {
		this.logs = logs;
	}
	public List<Product> getPrds() {
		return prds;
	}
	public void setPrds(List<Product> prds) {
		this.prds = prds;
	}
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	public int getCurOperatorId() {
		return curOperatorId;
	}
	public void setCurOperatorId(int curOperatorId) {
		this.curOperatorId = curOperatorId;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public int getOprState() {
		return oprState;
	}
	public void setOprState(int oprState) {
		this.oprState = oprState;
	}
	public int getCurNodeId() {
		return curNodeId;
	}
	public void setCurNodeId(int curNodeId) {
		this.curNodeId = curNodeId;
	}
	public String getCurNodeName() {
		return curNodeName;
	}
	public void setCurNodeName(String curNodeName) {
		this.curNodeName = curNodeName;
	}
	public int getTotalPrd() {
		return totalPrd;
	}
	public void setTotalPrd(int totalPrd) {
		this.totalPrd = totalPrd;
	}
	public double getTotalPay() {
		return totalPay;
	}
	public void setTotalPay(double totalPay) {
		this.totalPay = totalPay;
	}
	public double getPrePay() {
		return prePay;
	}
	public void setPrePay(double prePay) {
		this.prePay = prePay;
	}
	public String getPrePayTime() {
		return prePayTime;
	}
	public void setPrePayTime(String prePayTime) {
		this.prePayTime = prePayTime;
	}
	public double getLastPay() {
		return lastPay;
	}
	public void setLastPay(double lastPay) {
		this.lastPay = lastPay;
	}
	public String getPayAccount() {
		return payAccount;
	}
	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
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
	private int curNode;
	
	public String getShipAddress() {
		return shipAddress;
	}
	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustAddr() {
		return custAddr;
	}
	public void setCustAddr(String custAddr) {
		this.custAddr = custAddr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStaffId() {
		return staffId;
	}
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	public List<ProductColorItem> getItems() {
		return items;
	}
	public void setItems(List<ProductColorItem> items) {
		this.items = items;
	}

}
