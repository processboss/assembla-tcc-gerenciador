package br.com.processboss.core.service;

import br.com.processboss.core.exception.ProcessExecutionException;
import br.com.processboss.core.model.Process;

public interface IExecutorService {

	void executeProcess(Process process) throws ProcessExecutionException;
	
}
