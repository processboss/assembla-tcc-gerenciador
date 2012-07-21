package br.com.processboss.core.bean;

import java.io.Serializable;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Mem;

/**
 * Classe que contem as informacoes atuais do servidor
 * 
 * @author Felipe Ribeiro
 * 
 */
public class ServerState implements Serializable {

	private static final long serialVersionUID = -6744252510761261747L;

	private Mem memory;
	private CpuInfo[] cpuInfo;

	public ServerState() {
		// TODO Auto-generated constructor stub
	}

	public Mem getMemory() {
		return memory;
	}

	public void setMemory(Mem memory) {
		this.memory = memory;
	}

	public CpuInfo[] getCpuInfo() {
		return cpuInfo;
	}

	public void setCpuInfo(CpuInfo[] cpuInfo) {
		this.cpuInfo = cpuInfo;
	}

}
