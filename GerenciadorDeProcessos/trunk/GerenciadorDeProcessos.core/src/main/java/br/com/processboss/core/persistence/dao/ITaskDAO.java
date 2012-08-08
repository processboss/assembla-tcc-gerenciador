package br.com.processboss.core.persistence.dao;

import br.com.processboss.core.model.Task;

public interface ITaskDAO extends IDAO<Task, Long> {

	Task loadProcesses(Task task);

}
