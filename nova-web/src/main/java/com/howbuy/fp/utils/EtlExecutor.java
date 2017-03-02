/**
 * @author meng.lv Email:meng.lv@howbuy.com
 *
 * FundRiskAssessmentCalc 创建时间：2017年1月12日 下午1:39:15
 */
package com.howbuy.fp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * @author meng.lv Email:meng.lv@howbuy.com
 *
 * FundRiskAssessmentCalc 创建时间：2017年1月12日 下午1:39:15
 */
public class EtlExecutor {
	
	static {
		String path= new File(EtlExecutor.class.getResource("/").getPath()).getParentFile().getPath();
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(path+"/conf/log4j.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PropertyConfigurator.configure(props);
	}
	//private static Logger log = LoggerFactory.getLogger("charge_log");  
	private static Logger log = Logger.getLogger(EtlExecutor.class);
	private EtlContext etlContext;
	private SqlHelper sqlHelper;
	private String execInstId;
	
	private EtlExecutor(String etlPath,String execInstId){
		etlContext = EtlContext.getInstance(etlPath);
		sqlHelper = new SqlHelper();
		if(execInstId == null)
			this.execInstId = Constants.getUUID();
		else {
			this.execInstId = execInstId;
		}
	}
	
	private EtlExecutor(String etlPath){
		etlContext = EtlContext.getInstance(etlPath);
		sqlHelper = new SqlHelper();
	}
	
	public static EtlExecutor newExecutor(String etlPath){
		return new EtlExecutor(etlPath);
	}
	
	public static EtlExecutor newExecutor(String etlPath,String execInstId){
		
		return new EtlExecutor(etlPath,execInstId);
	}
	
	public void execute(){
		log.debug("["+this.execInstId + "]开始执行：");
		Iterator<Map.Entry<String, ScriptEl>> iterator= etlContext.getScripts().entrySet().iterator(); 
		long start=System.currentTimeMillis();
		while(iterator.hasNext()) {
			Map.Entry<String, ScriptEl> entry = iterator.next();
			ScriptEl scriptEl = etlContext.getScriptEl(entry.getKey());
			
			long a=System.currentTimeMillis();
			int n = -1;
			String[] sql = scriptEl.getText().split(";");
			
		
			
			/*
			if(scriptEl.getIfcondition() != null 
					&& !((Boolean)CalcEngine.getEngine().executeo(scriptEl.getIfcondition(), null)).booleanValue()){
				continue;
			}
			
			log.debug("["+this.execInstId + "]开始执行：" + scriptEl.toString());
			
			if(sql.length > 0 ){
				for(int i=0;i<sql.length;i++){
					n = sqlHelper.executeUpdate(sql[i], null);
				}
			}
			*/
			
			
			if(scriptEl.getIfcondition() != null){
				if(etlContext.parseIfCondition(sqlHelper, scriptEl)){
					log.debug("["+this.execInstId + "]开始执行：" + scriptEl.toString() );
					if(sql.length > 0 ){
						for(int i=0;i<sql.length;i++){
							n = sqlHelper.executeUpdate(sql[i], null);
						}
					}
				}else {
					continue;
				}
			}else {
				log.debug("["+this.execInstId + "]开始执行：" + scriptEl.toString());
				if(sql.length > 0 ){
					for(int i=0;i<sql.length;i++){
						n = sqlHelper.executeUpdate(sql[i], null);
					}
				}
			}
			
			if(n >= 0){
				log.info("["+this.execInstId + "]执行完成：" + scriptEl.getName() + "完成，耗时：" + (System.currentTimeMillis()-a)/1000f+"s");
			}else{ 
				log.warn("["+this.execInstId + "]执行失败：" + scriptEl.getName() );
			}
		}
		
		log.info("["+this.execInstId + "]执行完成，总计耗时：" + (System.currentTimeMillis()-start)/1000f+"s");
	}
	
	public static void main(String[] args){
		EtlExecutor.newExecutor("/conf/howbuy.fp.RiskAssessmentCalc.xml",Constants.getUUID()).execute();
	}
}
