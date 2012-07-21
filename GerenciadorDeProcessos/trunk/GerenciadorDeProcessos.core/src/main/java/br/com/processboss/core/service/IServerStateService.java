package br.com.processboss.core.service;

import br.com.processboss.core.bean.ServerState;

public interface IServerStateService {

	/**
	 * Le as informacoes de hardware do servidor
	 * 
	 * @return ServerState o estado atual do servidor
	 */
	ServerState read();
	
}
