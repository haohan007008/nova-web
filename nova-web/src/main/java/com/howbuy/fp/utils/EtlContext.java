package com.howbuy.fp.utils;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;



public class EtlContext {
	private static Logger log = Logger.getLogger(EtlContext.class);
	private static EtlContext context;
	private LinkedHashMap<String, ScriptEl> scripts;
	public LinkedHashMap<String, ScriptEl> getScripts() {
		return scripts;
	}

	private HashMap<String, String> variables;
	private String etlPath = "/conf/config.xml";
	public String getEtlPath() {
		return etlPath;
	}

	public void setEtlPath(String etlPath) {
		this.etlPath = etlPath;
	}

	private String startTaskName = null; 
	
	public String getStartTaskName() {
		return startTaskName;
	}

	public void setStartTaskName(String startTaskName) {
		this.startTaskName = startTaskName;
	}

	private EtlContext(String confPath){
		scripts = new LinkedHashMap<String, ScriptEl>();
		variables = new HashMap<String, String>();
		init(confPath);
	}
	
	private EtlContext(){
		scripts = new LinkedHashMap<String, ScriptEl>();
		variables = new HashMap<String, String>();
		init(etlPath);
	}
	
	private void init(String confPath){
		SAXReader reader = new SAXReader();
		String path= new File(C3P0Connection.class.getResource("/").getPath()).getParentFile().getPath();
		try {
			Document document = reader.read(new File(path+confPath));
			Element root = document.getRootElement();
			if(root.attributeValue("target") !=null)
				startTaskName = root.attributeValue("target");
			@SuppressWarnings("unchecked")
			List<Element> Elements = root.elements();
			for (Element element : Elements) {
				@SuppressWarnings("unchecked")
				List<Element> childElements = element.elements();
				for (Element child : childElements) {
					if("script".equals(child.getName())){
						scripts.put(child.attributeValue("name"), new ScriptEl(child.attributeValue("name"),
								child.attributeValue("description"),
								child.attributeValue("next"),
								child.getText().trim(),
								child.attributeValue("if")
								));
					}
					if("variable".equals(child.getName())){
						variables.put(child.attributeValue("name"), child.getText().trim());
					}
				}
			}
		} catch ( DocumentException e) {
			e.printStackTrace();
			log.error(e);
		}
	}
	
	public String getScriptText(String name){
		return scripts.get(name).getText();
	}
	
	public ScriptEl getScriptEl(String name){
		return scripts.get(name);
	}
	
	public ScriptEl getStartTask(){
		return scripts.get(startTaskName);
	}
	
	public String getVar(String name){
		return variables.get(name);
	}
	
	public static EtlContext getInstance(){
		if(context == null )
			context = new EtlContext();
		return context;
	}
	
	public static EtlContext getInstance(String path){
		if(context == null )
			context = new EtlContext(path);
		return context;
	}
	
	public boolean parseIfCondition(SqlHelper sqlHelper,ScriptEl el){
		//!existsTable('T_FU_GM_JJFXPG_V')
		//existsTable('T_FU_GM_JJFXPG_V')
		boolean bl = false;
		String condition = el.getIfcondition();
		
		if(condition.startsWith("!")){
			bl = true;
			condition = condition.substring(1);
		}
		
		//boolean result = ((Boolean)CalcEngine.getEngine().executeo(condition,null)).booleanValue();
		
		if(condition.startsWith("existsTable")){
			Pattern pat = Pattern.compile("\'(.*?)\'");
			Matcher mat = pat.matcher(condition);
			if (mat.find()) {
				if(bl)
				return !sqlHelper.existTable(mat.group(1));
				else return sqlHelper.existTable(mat.group(1));
			} 
		}
		return true;
		
		
	}
	
	public static void main(String[] args) {
		EtlContext.getInstance();
		
	}

}
