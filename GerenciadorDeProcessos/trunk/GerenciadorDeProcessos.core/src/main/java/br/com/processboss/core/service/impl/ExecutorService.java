package br.com.processboss.core.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.processboss.core.exception.ProcessExecutionException;
import br.com.processboss.core.model.ProcessExecutionDetail;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.persistence.dao.IProcessExecutionDetailDAO;
import br.com.processboss.core.service.IExecutorService;
import br.com.processboss.core.thread.ProcessExecutorThread;

@Transactional(propagation=Propagation.REQUIRED)
public class ExecutorService implements IExecutorService {

	private static final Log LOG = LogFactory.getLog(ExecutorService.class);
	
	private IProcessExecutionDetailDAO processExecutionDetailDAO;
	
	@Override
	public void executeProcess(ProcessInTask processInTask) throws ProcessExecutionException {
		String uuid = String.valueOf(processInTask.getId());
		ProcessExecutorThread executor = new ProcessExecutorThread(uuid, processInTask, this);
		executor.start();
		
		try {
			while(executor.isRunning()){
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			LOG.error("Ocorreu um erro ao executar o processo " + processInTask.getProcess().getName());
			throw new ProcessExecutionException("Ocorreu um erro ao executar o processo " + processInTask.getProcess().getName(), e);
		}
	}

	@Override
	public ProcessExecutionDetail saveOrUpdate(
			ProcessExecutionDetail processExecutionDetail) {
		return processExecutionDetailDAO.saveOrUpdate(processExecutionDetail);
	}

	public IProcessExecutionDetailDAO getProcessExecutionDetailDAO() {
		return processExecutionDetailDAO;
	}

	public void setProcessExecutionDetailDAO(
			IProcessExecutionDetailDAO processExecutionDetailDAO) {
		this.processExecutionDetailDAO = processExecutionDetailDAO;
	}

}
