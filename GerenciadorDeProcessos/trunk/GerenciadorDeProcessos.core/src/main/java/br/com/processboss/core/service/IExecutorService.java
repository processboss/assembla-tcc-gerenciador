package br.com.processboss.core.service;

import br.com.processboss.core.exception.ProcessExecutionException;
import br.com.processboss.core.model.ProcessExecutionDetail;
import br.com.processboss.core.model.ProcessInTask;

public interface IExecutorService {

	void executeProcess(ProcessInTask processInTask) throws ProcessExecutionException;
	ProcessExecutionDetail saveOrUpdate(ProcessExecutionDetail processExecutionDetail);
	
}
