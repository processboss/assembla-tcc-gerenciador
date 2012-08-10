package br.com.processboss.core.persistence.dao;

import br.com.processboss.core.model.ProcessInTask;


public interface IProcessInTaskDAO extends IDAO<ProcessInTask, Long> {

	ProcessInTask loadExecutionDetails(ProcessInTask processInTask);
	
}
