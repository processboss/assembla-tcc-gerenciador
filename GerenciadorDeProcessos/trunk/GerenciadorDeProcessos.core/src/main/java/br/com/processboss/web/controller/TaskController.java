package br.com.processboss.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.model.DualListModel;

import br.com.processboss.core.model.Process;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.model.Schedule;
import br.com.processboss.core.model.Task;
import br.com.processboss.core.service.IProcessInTaskService;
import br.com.processboss.core.service.IProcessService;
import br.com.processboss.core.service.IScheduleService;
import br.com.processboss.core.service.ITaskService;

@ManagedBean(name="taskController")
@SessionScoped
public class TaskController extends _Bean {

	private static final long serialVersionUID = -7864504279655890739L;

	@ManagedProperty(name="taskService", value="#{taskService}")
	private ITaskService taskService;
	
	@ManagedProperty(name="processService", value="#{processService}")
	private IProcessService processService;
	
	@ManagedProperty(name="processInTaskService", value="#{processInTaskService}")
	private IProcessInTaskService processInTaskService;
	
	@ManagedProperty(name="scheduleService", value="#{scheduleService}")
	private IScheduleService scheduleService;
	
	private Task entity = new Task();
	private DualListModel<Process> processes = null;
	
	private List<ProcessInTask> processList = new ArrayList<ProcessInTask>();
	
	/*
	 * CONSTRUTORES
	 */
	public TaskController() {}
	
	/*
	 * GETS E SETS
	 */
	public ITaskService getTaskService() {
		return taskService;
	}
	
	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}
	
	public IProcessService getProcessService() {
		return processService;
	}
	
	public void setProcessService(IProcessService processService) {
		this.processService = processService;
	}
	
	public Task getEntity() {
		return entity;
	}
	
	public void setEntity(Task entity) {
		this.entity = entity;
	}
	
	public DualListModel<Process> getProcesses() {
		return processes;
	}
	
	public void setProcesses(DualListModel<Process> processes) {
		this.processes = processes;
	}
	
	public IProcessInTaskService getProcessInTaskService() {
		return processInTaskService;
	}
	
	public void setProcessInTaskService(IProcessInTaskService processInTaskService) {
		this.processInTaskService = processInTaskService;
	}
	
	public IScheduleService getScheduleService() {
		return scheduleService;
	}
	
	public void setScheduleService(IScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
	
	public List<ProcessInTask> getProcessList() {
		return processList;
	}

	public void setProcessList(List<ProcessInTask> processList) {
		this.processList = processList;
	}

	/*
	 * DESENVOLVIMENTO
	 */
	public List<Task> getAllEntities(){
		return taskService.listAll();
	}
	
	public String updateEntity(){
		entity = (Task)getJsfParam("entity");
		return "updateTask";
	}
	
	public String newEntity(){
		loadProcess();
		entity = new Task();
		return "newTask";
	}
	
	public String cancel(){
		return "index"; 
	}
	
	public String saveOrUpdate(){
		if(entity != null){
			
			if (processes.getTarget() != null){
				List<ProcessInTask> processInTasks = new ArrayList<ProcessInTask>();
				for (Process process : processes.getTarget()) {
					ProcessInTask pit = new ProcessInTask();
					pit.setProcess(process);
					processInTasks.add(pit);
				}
				entity.setProcesses(processInTasks);
			}
			
			taskService.saveOrUpdate(entity);
			addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Tarefa inserido/alterado com suscesso", ""));
			return "index";
		}else{
			addMessage( new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nao foi possivel salvar/alterar a tarefa.", ""));
			return null;
		}
	}
	
	public void captureTask(ActionEvent action){
		entity = (Task)getJsfParam("entity");
	}
	
	public String delete(){
		if(entity != null){
			taskService.delete(entity);
			addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Tarefa removido com sucesso", ""));
			return "index";
		}else{
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nao foi possivel excluir a tarefa", ""));
			return null;
		}
	}
	
	private void loadProcess(){
		List<Process> source = processService.listAll();
		List<Process> target = new ArrayList<Process>();
		
		processes = new DualListModel<Process>(source, target);
	}
	
}
