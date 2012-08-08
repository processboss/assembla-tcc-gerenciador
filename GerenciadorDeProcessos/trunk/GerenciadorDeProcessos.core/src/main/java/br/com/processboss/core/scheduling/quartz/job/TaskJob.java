package br.com.processboss.core.scheduling.quartz.job;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import br.com.processboss.core.exception.ProcessExecutionException;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.model.Task;
import br.com.processboss.core.service.IExecutorService;
import br.com.processboss.core.service.ITaskService;

/**
 * Job responsavel por disparar a execucao da tarefa
 */
public class TaskJob extends QuartzJobBean {
	
	private final static Log LOG = LogFactory.getLog(TaskJob.class);

	private Task task;
	private ITaskService taskService;
	private IExecutorService executorService;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		LOG.debug("Iniciando a tarefa " + task.getName());
		
		/**
		 * Busca os processos da tarefa
		 */
		task = taskService.loadProcesses(task);
		List<ProcessInTask> processes = task.getProcesses();
		
		//TODO: Organizar a ordem de execucao dos processos
		
		try {

			for (ProcessInTask processInTask : processes) {
			
				executorService.executeProcess(processInTask);
			
			}

		} catch (ProcessExecutionException e) {
			throw new JobExecutionException(e);
		}
			
		
		LOG.debug("Tarefa " + task.getName() + " finalizada!");
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
