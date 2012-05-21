package br.com.processboss.core.reader.impl;


import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CurrentProcessSummary;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SysInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.processboss.core.model.ServerState;
import br.com.processboss.core.reader.ServerStateReader;

/**
 * Obtem as informacoes de hardware de um servidor linux
 * 
 * @author Felipe Ribeiro
 *
 */
public class ServerStateLinuxReader extends ServerStateReader {

	private static final Logger LOG = LoggerFactory.getLogger(ServerStateLinuxReader.class); 
	
	public ServerState read() {
		LOG.debug("Comecei");
		
		try {
			Sigar.load();
			Sigar sigar = new Sigar();
			CpuInfo[] cpuInfoList = sigar.getCpuInfoList();
			
			for (CpuInfo cpuInfo : cpuInfoList) {
				LOG.debug(cpuInfo.getModel());
			}
			
			
		
		} catch (SigarException e) {
			e.printStackTrace();
		}
		
		LOG.debug("Terminei");
		return new ServerState();
	}

}
