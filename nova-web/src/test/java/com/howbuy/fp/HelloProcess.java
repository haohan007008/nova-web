package com.howbuy.fp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/** 
 * @author LvMeng
 * @version 2017年3月20日 下午3:08:13
 */
public class HelloProcess {
	ProcessEngine engine=ProcessEngines.getDefaultProcessEngine();
	@Test
	public void deployment(){
		
		 RepositoryService rs=engine.getRepositoryService();
		 rs.createDeployment()
		 	.addClasspathResource("hello.bpmn")
		 	.addClasspathResource("hello.png")
		 	.deploy();
	}
	
	@Test
	public void startprocess(){
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("days", "3");
		 map.put("reason", "blebelebelebeke");
		 RuntimeService rse = engine.getRuntimeService();
		 rse.startProcessInstanceByKey("myProcess","10000",map);
	}
	
	@Test
	public void queryTask(){
		String assignee = "李四";
		List<Task> tasks = engine.getTaskService()
				.createTaskQuery()
				.taskAssignee(assignee)
				.list();
		if(tasks!=null && tasks.size() >0){
			System.out.println("我的任务：");
			for(Task task:tasks){
				System.out.println("taskid:"+task.getId());
				System.out.println("taskname:"+task.getName());
				Map<String, Object> mp = engine.getTaskService().getVariables(task.getId());
				Iterator iter = mp.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					Object key = entry.getKey();
					Object val = entry.getValue();
					System.out.println("key=" + key + ":: val = " + val);
				}
				System.out.println("####################################");
				
			}
		}else {
			System.out.println("没有我的任务");
		}
	}
	
	@Test
	public void compeleteStep(){
		String taskId  = "20002";
		Map<String, Object> mp = engine.getTaskService().getVariables(taskId);
		Iterator iter = mp.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			System.out.println("key=" + key + ":: val = " + val);
		}
		engine.getTaskService().complete(taskId);
		System.out.println("完成任务！");
	}
	@Test
	public void queryHistoricProcessInstance(){
		List<HistoricProcessInstance> hsitList = engine.getHistoryService()
			.createHistoricProcessInstanceQuery()
			.processDefinitionKey("myProcess")
			.orderByProcessInstanceStartTime().desc()
			.list();
		
		if(hsitList != null && hsitList.size() >0){
		for (HistoricProcessInstance hpi : hsitList) {
			System.out.println("pid : "+ hpi.getId());
			System.out.println("pdid : "+ hpi.getProcessDefinitionId());
			System.out.println("starttime : "+ hpi.getStartTime());
			System.out.println("EndTime : "+ hpi.getEndTime());
			System.out.println("Duration : "+ hpi.getDurationInMillis());
			System.out.println("======================================");
		}
		}else {
			System.out.println("好像没有啊");
		}
	}
	
	@Test
	public void queryHistoricActivityInstance(){
		
		String processInstaceId = "17501";
		List<HistoricActivityInstance> hsitList = engine.getHistoryService()
				.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstaceId)
				.orderByHistoricActivityInstanceStartTime().desc()
				.list();
		
		for (HistoricActivityInstance hpi : hsitList) {
			System.out.println("ActivityId : "+ hpi.getActivityId());
			System.out.println("ActivityName : "+ hpi.getActivityName());
			System.out.println("ActivityType : "+ hpi.getActivityType());
			System.out.println("Assignee : "+ hpi.getAssignee());
			System.out.println("starttime : "+ hpi.getStartTime());
			System.out.println("EndTime : "+ hpi.getEndTime());
			System.out.println("Duration : "+ hpi.getDurationInMillis());
			System.out.println("======================================");
		}
			
	}
	@Test
	public void queryHistoricTask() {
		String processInstaceId = "17501";
		List<HistoricTaskInstance> hsitList = engine.getHistoryService()
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstaceId)
				.orderByHistoricTaskInstanceStartTime().asc()
				.list();
		
		for (HistoricTaskInstance hpi : hsitList) {
			System.out.println("id : "+ hpi.getId());
			System.out.println("name : "+ hpi.getName());
			System.out.println("ProcessDefinitionId : "+ hpi.getProcessDefinitionId());
			System.out.println("ProcessInstanceId : "+ hpi.getProcessInstanceId());
			System.out.println("Assignee : "+ hpi.getAssignee());
			System.out.println("starttime : "+ hpi.getStartTime());
			System.out.println("EndTime : "+ hpi.getEndTime());
			System.out.println("Duration : "+ hpi.getDurationInMillis());
			System.out.println("======================================");
		}
	}
}
