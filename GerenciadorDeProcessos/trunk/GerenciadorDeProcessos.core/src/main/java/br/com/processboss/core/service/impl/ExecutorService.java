package br.com.processboss.core.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.processboss.core.exception.ProcessExecutionException;
import br.com.processboss.core.model.Process;
import br.com.processboss.core.service.IExecutorService;
import br.com.processboss.core.thread.ProcessExecutorThread;

public class ExecutorService implements IExecutorService {

	private static final Log LOG = LogFactory.getLog(ExecutorService.class);
	
	@Override
	public void executeProcess(Process process) throws ProcessExecutionException {
		String uuid = String.valueOf(process.getId());
		ProcessExecutorThread executor = new ProcessExecutorThread(uuid, process);
		executor.start();
		
		try {
			while(executor.isRunning()){
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			LOG.error("Ocorreu um erro ao executar o processo " + process.getName());
			throw new ProcessExecutionException("Ocorreu um erro ao executar o processo " + process.getName(), e);
		}
	}

}
