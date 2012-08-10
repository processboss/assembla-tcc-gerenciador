package br.com.processboss.core.scheduling.quartz.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import br.com.processboss.core.exception.ProcessExecutionException;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.model.Task;
import br.com.processboss.core.scheduling.executor.TaskExecutationManager;
import br.com.processboss.core.service.IExecutorService;
import br.com.processboss.core.service.ITaskService;

/**
 * Job responsavel por disparar a execucao da tarefa
 */
public class TaskJob extends QuartzJobBean implements TaskExecutationManager {
	
	private final static Log LOG = LogFactory.getLog(TaskJob.class);

	private Task task;
	private ITaskService taskService;
	private IExecutorService executorService;
	
	private List<ProcessInTask> toExecute;
	private Map<Long, ProcessInTask> executed = new HashMap<Long, ProcessInTask>();
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		LOG.debug("Iniciando a tarefa " + task.getName());
		
		/**
		 * Busca os processos da tarefa
		 */
		taskService.loadProcesses(task);
		List<ProcessInTask> processes = task.getProcesses();
		
		//TODO: Organizar a ordem de execucao dos processos
		organizeProcess(processes);
		
		try {
			while(!CollectionUtils.isEmpty(toExecute)){
				
				Iterator<ProcessInTask> iterator = toExecute.iterator();
				
				while(iterator.hasNext()){
					
					ProcessInTask processInTask = iterator.next();
					
					//TODO: VERIFICAR SE AS DEPENDENCIAS JAH FORAM EXECUTADAS
					executorService.executeProcess(processInTask, this);
					
					iterator.remove();
				}
				
				Thread.sleep(1000L);
			}
			
			while(processes.size() != executed.size()){
				Thread.sleep(1000L);
			}

		} catch (ProcessExecutionException e) {
			throw new JobExecutionException(e);
		} catch (InterruptedException e) {
			throw new JobExecutionException(e);
		}
			
		
		LOG.debug("Tarefa " + task.getName() + " finalizada!");
	}
	
	protected void organizeProcess(List<ProcessInTask> processes){
		toExecute = new ArrayList<ProcessInTask>();
		for (ProcessInTask processInTask : processes) {
			toExecute.add(processInTask);
		}
	}
	
	@Override
	public void processTerminated(ProcessInTask processInTask) {
		executed.put(processInTask.getId(), processInTask);
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}

	public void setExecutorService(IExecutorService executorService) {
		this.executorService = executorService;
	}

	
}
