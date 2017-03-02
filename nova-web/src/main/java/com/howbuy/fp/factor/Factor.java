package com.howbuy.fp.factor;

import java.math.BigDecimal;

/** 
 * @author LvMeng
 * @version 2017年2月9日 上午10:51:53
 */
public class Factor {
	private String jjdm;
	private String market = "";
	private BigDecimal  netvalue;
	private BigDecimal  changeratio;
	private BigDecimal  port10r;
	private BigDecimal  hs300r;
	private BigDecimal  zxbr;
	private BigDecimal  cybr;
	
	private BigDecimal  alpha;
	private BigDecimal  belta1;
	private BigDecimal  belta2;
	private BigDecimal  belta3;
	private BigDecimal  belta4;
	private String xgsj;
	private String tradeDate;
	
	public String getJjdm() {
		return jjdm;
	}
	public void setJjdm(String jjdm) {
		this.jjdm = jjdm;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public BigDecimal  getNetvalue() {
		return netvalue;
	}
	public void setNetvalue(BigDecimal  netvalue) {
		this.netvalue = netvalue;
	}
	public BigDecimal  getChangeratio() {
		return changeratio;
	}
	public void setChangeratio(BigDecimal  changeratio) {
		this.changeratio = changeratio;
	}
	public BigDecimal  getPort10r() {
		return port10r;
	}
	public void setPort10r(BigDecimal  port10r) {
		this.port10r = port10r;
	}
	public BigDecimal  getHs300r() {
		return hs300r;
	}
	public void setHs300r(BigDecimal  hs300r) {
		this.hs300r = hs300r;
	}
	public BigDecimal  getZxbr() {
		return zxbr;
	}
	public void setZxbr(BigDecimal  zxbr) {
		this.zxbr = zxbr;
	}
	public BigDecimal  getCybr() {
		return cybr;
	}
	public void setCybr(BigDecimal  cybr) {
		this.cybr = cybr;
	}
	public BigDecimal  getAlpha() {
		return alpha;
	}
	public void setAlpha(BigDecimal  alpha) {
		this.alpha = alpha;
	}
	public BigDecimal  getBelta1() {
		return belta1;
	}
	public void setBelta1(BigDecimal  belta1) {
		this.belta1 = belta1;
	}
	public BigDecimal  getBelta2() {
		return belta2;
	}
	public void setBelta2(BigDecimal  belta2) {
		this.belta2 = belta2;
	}
	public BigDecimal  getBelta3() {
		return belta3;
	}
	public void setBelta3(BigDecimal  belta3) {
		this.belta3 = belta3;
	}
	public BigDecimal  getBelta4() {
		return belta4;
	}
	public void setBelta4(BigDecimal  belta4) {
		this.belta4 = belta4;
	}
	public String getXgsj() {
		return xgsj;
	}
	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	
	
}
