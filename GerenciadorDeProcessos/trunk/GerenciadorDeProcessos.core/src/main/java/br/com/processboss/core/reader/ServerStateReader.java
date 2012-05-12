package br.com.processboss.core.reader;

import br.com.processboss.core.model.ServerState;
import br.com.processboss.core.reader.impl.ServerStateLinuxReader;
import br.com.processboss.core.reader.impl.ServerStateWindowsReader;

/**
 * Interface que obtem as informacoes de hardware
 * do servidor no momento solicitado
 * 
 * @author Felipe Ribeiro
 *
 */
public abstract class ServerStateReader {

	/**
	 * Retorna uma instancia de ServerStateReader de 
	 * acordo com o sistema operacional
	 * 
	 * @return
	 */
	public static ServerStateReader getReader(){
		String osName = System.getProperty("os.name");

		if(osName.toLowerCase().startsWith("windows")){
			return new ServerStateWindowsReader();
		}
		
		return new ServerStateLinuxReader();
	}
	
	/**
	 * Le as informacoes de hardware do servidor
	 * 
	 * @return
	 */
	public abstract ServerState read();
	
}
