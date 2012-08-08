package br.com.processboss.core.scheduling.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import br.com.processboss.core.executor.TaskExecutor;
import br.com.processboss.core.model.Task;

/**
 * Job responsavel por disparar a execucao da tarefa
 */
public class TaskJob extends QuartzJobBean {

	private TaskExecutor taskExecutor;
	
	public void setTask(Task task) {
		this.taskExecutor = new TaskExecutor(task);
	}
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		taskExecutor.execute();
		
	}
	
}
