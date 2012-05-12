package br.com.processboss.core.service;

import org.springframework.beans.factory.InitializingBean;

import br.com.processboss.core.reader.ServerStateReader;

public class ServerStateService implements InitializingBean {

	private ServerStateReader serverStateReader;
	
	public void afterPropertiesSet() throws Exception {
		serverStateReader = ServerStateReader.getReader();
	}

	public ServerStateReader getServerStateReader() {
		return serverStateReader;
	}

	
}
