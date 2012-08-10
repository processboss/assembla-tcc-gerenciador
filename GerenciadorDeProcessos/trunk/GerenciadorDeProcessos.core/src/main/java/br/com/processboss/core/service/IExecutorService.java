package br.com.processboss.core.service;

import br.com.processboss.core.exception.ProcessBossException;
import br.com.processboss.core.exception.ProcessExecutionException;
import br.com.processboss.core.model.ProcessExecutionDetail;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.model.Task;
import br.com.processboss.core.scheduling.executor.TaskExecutationManager;

public interface IExecutorService {

	void executeTask(Task task) throws ProcessBossException;
	void executeProcess(ProcessInTask processInTask, TaskExecutationManager manager) throws ProcessExecutionException;
	ProcessExecutionDetail saveOrUpdate(ProcessExecutionDetail processExecutionDetail);
	
}
