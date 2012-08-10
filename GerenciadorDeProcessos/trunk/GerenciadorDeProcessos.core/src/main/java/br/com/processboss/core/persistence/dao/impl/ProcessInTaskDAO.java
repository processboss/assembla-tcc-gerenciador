package br.com.processboss.core.persistence.dao.impl;

import org.hibernate.Hibernate;

import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.persistence.dao.IProcessInTaskDAO;

public class ProcessInTaskDAO extends GenericDAO<ProcessInTask, Long> implements IProcessInTaskDAO {

	@Override
	public ProcessInTask loadExecutionDetails(ProcessInTask processInTask) {
		processInTask = findById(processInTask.getId());
		if(processInTask != null){
			Hibernate.initialize(processInTask.getExecutionDetails());
		}
		return processInTask;
	}

}
