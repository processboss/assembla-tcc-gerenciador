package br.com.processboss.core.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.processboss.core.model.Process;

public class ProcessExecutorThread implements Runnable {

	private static final Log LOG = LogFactory.getLog(ProcessExecutorThread.class);
	
	Thread runner;
	Process process;
	String name;
	
	public ProcessExecutorThread(String name, br.com.processboss.core.model.Process process) {
		this.process = process;
		this.name = name;
		runner = new Thread(this, name);
	}
	
	public void start(){
		runner.start();
	}
	
	public boolean isRunning(){
		return runner.isAlive();
	}
	
	@Override
	public void run() {
		
		LOG.debug("Iniciando a execucao do processo: " + process.getName());
		
		try {
			ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c","echo $$ && " +process.getPath());
			java.lang.Process p = builder.start();
			
			int pid = getPID(p);

			ProcessMonitorThread monitor = new ProcessMonitorThread(pid);
			monitor.start();
			
			p.waitFor();
			
			monitor.stop();
			
			LOG.debug("Execucao do processo: " + process.getName() + " finalizada!");

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Ocorreu um erro ao executar o processo " + process.getName());
		}
		
	}
	
	protected int getPID(java.lang.Process p) throws NumberFormatException, IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		return Integer.parseInt(br.readLine()) + 1;
	}

}
