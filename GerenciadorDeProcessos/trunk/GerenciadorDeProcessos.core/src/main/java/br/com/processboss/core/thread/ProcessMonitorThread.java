package br.com.processboss.core.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProcessMonitorThread implements Runnable {

	private static final Log LOG = LogFactory.getLog(ProcessMonitorThread.class);
	
	int pid;
	Thread runner;
	boolean canContinue;
	
	public ProcessMonitorThread(int pid) {
		this.pid = pid;
		runner = new Thread(this, "monitor_"+pid);
	}
	
	public void start(){
		canContinue = true;
		runner.start();
	}
	
	public void stop(){
		canContinue = false;
	}
	
	@Override
	public void run() {
			
		try {
			
			while(canContinue){
				//TODO MONITORAR O PROCESSO
				System.out.println("Monitorando o processo " + pid);
				
				Thread.sleep(1000);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			LOG.error("Ocorreu um erro ao monitorar o processo " + pid);
		}
		
	}

}
