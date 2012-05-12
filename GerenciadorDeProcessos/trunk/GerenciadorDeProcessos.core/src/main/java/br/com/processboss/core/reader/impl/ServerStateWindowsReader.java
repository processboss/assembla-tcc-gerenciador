package br.com.processboss.core.reader.impl;

import oshi.software.os.windows.nt.GlobalMemory;
import br.com.processboss.core.model.ServerState;
import br.com.processboss.core.reader.ServerStateReader;

/**
 * Obtem as informacoes de hardware de um servidor windows
 * 
 * @author Felipe Ribeiro
 *
 */
public class ServerStateWindowsReader extends ServerStateReader {

	public ServerState read() {
		ServerState serverState = new ServerState();
		
		GlobalMemory memory = new GlobalMemory();
		
		serverState.setFreeMemory(memory.getAvailable());
		serverState.setTotalMemory(memory.getTotal());
		
		return serverState;
	}

}
