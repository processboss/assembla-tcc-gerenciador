package br.com.processboss.core.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.processboss.core.model.Task;
import br.com.processboss.core.persistence.dao.ITaskDAO;
import br.com.processboss.core.service.ITaskService;


@Transactional(propagation=Propagation.REQUIRED)
public class TaskService implements ITaskService {

	private ITaskDAO taskDAO;

	@Override
	public Task findById(Long id) {
		return taskDAO.findById(id);
	}

	@Override
	public List<Task> listAll() {
		return taskDAO.listAll();
	}

	@Override
	public Task save(Task entity) {
		return taskDAO.save(entity);
	}
	
	@Override
	public Task update(Task entity) {
		return taskDAO.save(entity);
	}

	@Override
	public Task saveOrUpdate(Task entity) {
		return taskDAO.saveOrUpdate(entity);
	}

	@Override
	public void delete(Task entity) {
		taskDAO.delete(entity);
	}

	public ITaskDAO getTaskDAO() {
		return taskDAO;
	}

	public void setTaskDAO(ITaskDAO taskDAO) {
		this.taskDAO = taskDAO;
	}


}
