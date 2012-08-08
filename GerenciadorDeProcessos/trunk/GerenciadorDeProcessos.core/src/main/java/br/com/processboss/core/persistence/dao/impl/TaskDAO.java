package br.com.processboss.core.persistence.dao.impl;

import org.hibernate.Hibernate;

import br.com.processboss.core.model.Task;
import br.com.processboss.core.persistence.dao.ITaskDAO;

public class TaskDAO extends GenericDAO<Task, Long> implements ITaskDAO {

	@Override
	public Task loadProcesses(Task task) {
		task = findById(task.getId());
		Hibernate.initialize(task.getProcesses());
		return task;
	}

}
