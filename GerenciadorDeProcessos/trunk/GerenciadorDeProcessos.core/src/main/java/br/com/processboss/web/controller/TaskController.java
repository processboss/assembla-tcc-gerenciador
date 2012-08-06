package br.com.processboss.web.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import br.com.processboss.core.model.Task;
import br.com.processboss.core.service.IProcessService;
import br.com.processboss.core.service.ITaskService;

@ManagedBean(name="taskController")
@SessionScoped
public class TaskController extends _Bean {

	private static final long serialVersionUID = -7864504279655890739L;

	@ManagedProperty(name="taskService", value="#{taskService}")
	private ITaskService taskService;
	@ManagedProperty(name="processService", value="#{processService}")
	private IProcessService processService;
	
	private Task entity;
	
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
		entity = new Task();
		return "newTask";
	}
	
	public String cancel(){
		return "index"; 
	}
	
	public String saveOrUpdate(){
		if(entity != null){
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
	
}
