package br.com.processboss.core.service;

import br.com.processboss.core.bean.ServerState;
import br.com.processboss.core.model.ProcessInTask;

public interface IServerStateService {

	/**
	 * Le as informacoes de hardware do servidor
	 * 
	 * @return ServerState o estado atual do servidor
	 */
	ServerState read();
	
	boolean canExecute(ProcessInTask processInTask);
	
	String addProcessExecution(ProcessInTask processInTask);
	
	void removeProcessExecution(String processExecutionKey);
}
