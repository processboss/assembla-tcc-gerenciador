package br.com.processboss.core.service.impl;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.processboss.core.bean.ServerState;
import br.com.processboss.core.service.IServerStateService;

/**
 * Servico que obtem as informacoes de hardware
 * do servidor no momento solicitado
 */
public class ServerStateService implements IServerStateService {

	private static final Logger LOG = LoggerFactory.getLogger(ServerStateService.class); 
	
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
	
}
