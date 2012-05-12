package br.com.processboss.core.model;

import java.io.Serializable;

/**
 * Classe que contem as informacoes atuais do servidor
 * 
 * @author Felipe Ribeiro
 *
 */
public class ServerState implements Serializable {

	private static final long serialVersionUID = -6744252510761261747L;

	private long freeMemory;
	private long totalMemory;

	public ServerState() {
		// TODO Auto-generated constructor stub
	}

	public long getFreeMemory() {
		return freeMemory;
	}

	public void setFreeMemory(long freeMemory) {
		this.freeMemory = freeMemory;
	}

	public long getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}

}
