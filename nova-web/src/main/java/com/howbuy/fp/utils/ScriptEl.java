/**
 * @author meng.lv Email:meng.lv@howbuy.com
 *
 * FundRiskAssessmentCalc 创建时间：2017年1月12日 下午1:21:50
 */
package com.howbuy.fp.utils;


/**
 * @author meng.lv Email:meng.lv@howbuy.com
 *
 * FundRiskAssessmentCalc 创建时间：2017年1月12日 下午1:21:50
 */
public class ScriptEl {
	private String name;
	private String description;
	private String next;
	private String text;
	private String ifcondition;
	
	public ScriptEl() {
		
	}
	
	
	public ScriptEl(String name, String description, String text) {
		this.name = name;
		this.description = description;
		this.text = text;
	}
	
	public ScriptEl(String name, String description, String next,
			String text, String ifcondition) {
		this.name = name;
		this.description = description;
		this.next = next;
		this.text = text;
		this.ifcondition = ifcondition;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIfcondition() {
		return ifcondition;
	}
	public void setIfcondition(String ifcondition) {
		this.ifcondition = ifcondition;
	}
	
	public String toString(){
		return "name:"+name + ", description : " + description+ 
				"["+text+"]";
	}
}
