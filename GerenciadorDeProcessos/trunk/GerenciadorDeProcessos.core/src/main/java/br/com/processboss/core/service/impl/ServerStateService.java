package br.com.processboss.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.processboss.core.bean.Resources;
import br.com.processboss.core.bean.ServerState;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.service.IServerStateService;

/**
 * Servico que obtem as informacoes de hardware
 * do servidor no momento solicitado
 */
public class ServerStateService implements IServerStateService {

	private static final Logger LOG = LoggerFactory.getLogger(ServerStateService.class); 
	
	private static final Map<String, ProcessInTask> IN_PROGRESS = new HashMap<String, ProcessInTask>();
	private static final List<ProcessInTask> WAITING = new ArrayList<ProcessInTask>();
	/**
	 * Le as informacoes de hardware do servidor
	 * 
	 * @return
	 */
	@Override
	public ServerState read() {
		ServerState serverState = new ServerState();
		try {
			Sigar.load();
			Sigar sigar = new Sigar();

			/**
			 * Informacoes da CPU
			 */
			serverState.setCpuInfo(sigar.getCpuInfoList());
			serverState.setCpuPerc(sigar.getCpuPerc());
			
			/**
			 * Informacoes da Memoria
			 */
			serverState.setMemory(sigar.getMem());
			
		
		} catch (SigarException e) {
			LOG.error("Ocorreu um erro ao obter as informacoes do servidor.");
			e.printStackTrace();
		}
		
		return serverState;
	}

	@Override
	public boolean canExecute(ProcessInTask processInTask) {
		ServerState serverState = read();
		long serverMemory = serverState.getMemory().getTotal();
		
		Resources alocatedResources = getAlocatedResources();
		
		Resources requiredResources = new Resources(processInTask.getExecutionDetails());
		
		Double memoryTotal = alocatedResources.getMemory() + requiredResources.getMemory();
		Double cpuTotal = alocatedResources.getCpu() + requiredResources.getCpu();
		
		if(memoryTotal > serverMemory || cpuTotal > Resources.MAX_CPU){
			return false; 
		}
		
		return true;
	}
	
	protected Resources getAlocatedResources(){
		double memory = 0.0D;
		double cpu = 0.0D;
		for (ProcessInTask process : IN_PROGRESS.values()) {
			Resources resources = new Resources(process.getExecutionDetails());
			memory += resources.getMemory();
			cpu += resources.getCpu();
		}
		return new Resources(memory, cpu);
	}

	@Override
	public String addProcessExecution(ProcessInTask processInTask) {
		String key = processInTask.getId() + "_" + new Date().getTime();
		
		IN_PROGRESS.put(key, processInTask);
		
		return key;
	}

	@Override
	public void removeProcessExecution(String processExecutionKey) {
		IN_PROGRESS.remove(processExecutionKey);
	}
	
	@Override
	public void addProcessWaiting(ProcessInTask processInTask) {
		WAITING.add(processInTask);
		
	}

	@Override
	public void removeProcessWaiting(ProcessInTask processInTask) {
		WAITING.remove(processInTask);
	}
	
	@Override
	public List<ProcessInTask> getInProgressProcess(){
		List<ProcessInTask> list = new ArrayList<ProcessInTask>();
		for (ProcessInTask process : IN_PROGRESS.values()) {
			list.add(process);
		}
		return list;
	}
	
	@Override
	public List<ProcessInTask> getWaitingProcess(){
		List<ProcessInTask> list = new ArrayList<ProcessInTask>();
		for (ProcessInTask process : WAITING) {
			list.add(process);
		}
		return list;
	}
	
}
