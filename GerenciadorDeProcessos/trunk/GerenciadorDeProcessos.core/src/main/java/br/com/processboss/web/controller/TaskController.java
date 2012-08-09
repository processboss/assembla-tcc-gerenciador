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
	
	private Task entity;
	private DualListModel<Process> processes = null;
	
	private Schedule entitySchedule = new Schedule();
	private int decisionOption;
	
	private boolean mon;
	private boolean tue;
	private boolean wed;
	private boolean thu;
	private boolean fri;
	private boolean sat;
	private boolean sun;

	/*
	 * CONSTRUTORES
	 */
	public TaskController() {
	}

	
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
	
	public Schedule getEntitySchedule() {
		return entitySchedule;
	}


	public void setEntitySchedule(Schedule entitySchedule) {
		this.entitySchedule = entitySchedule;
	}


	public IScheduleService getScheduleService() {
		return scheduleService;
	}
	
	public void setScheduleService(IScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
	
	public int getDecisionOption() {
		return decisionOption;
	}
	
	public void setDecisionOption(int decisionOption) {
		this.decisionOption = decisionOption;
	}
	
	public boolean isMon() {
		return mon;
	}
	public void setMon(boolean mon) {
		this.mon = mon;
	}
	public boolean isTue() {
		return tue;
	}
	public void setTue(boolean tue) {
		this.tue = tue;
	}
	public boolean isWed() {
		return wed;
	}
	
	public void setWed(boolean wed) {
		this.wed = wed;
	}

	public boolean isThu() {
		return thu;
	}

	public void setThu(boolean thu) {
		this.thu = thu;
	}

	public boolean isFri() {
		return fri;
	}

	public void setFri(boolean fri) {
		this.fri = fri;
	}

	public boolean isSat() {
		return sat;
	}

	public void setSat(boolean sat) {
		this.sat = sat;
	}

	public boolean isSun() {
		return sun;
	}

	public void setSun(boolean sun) {
		this.sun = sun;
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
	
	public String scheduleEntity(){
		entity = (Task)getJsfParam("entity");
		entitySchedule = new Schedule();
		return "scheduleTask";
	}
	
	private String saveOrUpdateSchedule(String minutes, String hours, String dayOfMonth, String month, String dayOfWeek){
		entitySchedule.setSeconds("0");
		entitySchedule.setMinutes(minutes);
		entitySchedule.setHours(hours);
		entitySchedule.setDayOfMonth(dayOfMonth);
		entitySchedule.setMonth(month);
		entitySchedule.setDayOfWeek(dayOfWeek);
		entitySchedule.setYear("*");
		entitySchedule.setTask(entity);
		
		System.out.println("Resultado do cron: [" + 
							entitySchedule.getSeconds() 		+ " " +
							entitySchedule.getMinutes() 		+ " " +
							entitySchedule.getHours() 			+ " " +
							entitySchedule.getDayOfMonth() 		+ " " +
							entitySchedule.getMonth() 			+ " " +
							entitySchedule.getDayOfWeek() 		+ " " +
							entitySchedule.getYear() 			+ " " +
							"]");
		
		scheduleService.saveOrUpdate(entitySchedule);
		addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Tarefa agendada com sucesso", ""));
		
		return "index";
	}
	
	public String saveOrUpdateMinutes(){
		return saveOrUpdateSchedule("0/" + entitySchedule.getMinutes(), "*", "1/1", "*", "?");
	}

	public String saveOrUpdateHours(){
		if(decisionOption == 1)
			return saveOrUpdateSchedule("0", "0/" + entitySchedule.getHours(), "1/1", "*", "?");
		else
			return saveOrUpdateSchedule(entitySchedule.getMinutes(), entitySchedule.getHours(), "1/1", "*", "?");
	}

	public String saveOrUpdateDaily(){
		if(decisionOption == 1)
			return saveOrUpdateSchedule(entitySchedule.getMinutes(), entitySchedule.getHours(), "1/" + entitySchedule.getDayOfMonth(), "*", "?");
		else
			return saveOrUpdateSchedule(entitySchedule.getMinutes(), entitySchedule.getHours(), "?", "*", "MON-FRI");
	}

	public String saveOrUpdateWeekly(){
		StringBuffer week = new StringBuffer();
		if(mon) week.append("MON,");
		if(tue) week.append("TUE,");
		if(wed) week.append("WED,");
		if(thu) week.append("THU,");
		if(fri) week.append("FRI,");
		if(sat) week.append("SAT,");
		if(sun) week.append("SUN,");
		
		return saveOrUpdateSchedule(entitySchedule.getMinutes(), entitySchedule.getHours(), "?", "*", week.toString());
	}
	
	
}
