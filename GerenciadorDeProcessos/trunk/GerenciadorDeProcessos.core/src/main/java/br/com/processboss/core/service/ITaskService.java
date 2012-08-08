package br.com.processboss.core.service;

import br.com.processboss.core.model.Task;

public interface ITaskService extends IService<Task, Long> {

	Task loadProcesses(Task task);
	
}
