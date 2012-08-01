package br.com.processboss.core.thread;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import br.com.processboss.core.model.ProcessExecutionDetail;

public class ProcessMonitorThread implements Runnable {

	private static final Log LOG = LogFactory.getLog(ProcessMonitorThread.class);
	
	int pid;
	Thread runner;
	boolean canContinue;
	ProcessExecutionDetail detail;
	
	public ProcessMonitorThread(int pid) {
		this.pid = pid;
		runner = new Thread(this, "monitor_"+pid);
		detail = new ProcessExecutionDetail();
	}
	
	public void start(){
		canContinue = true;
		detail.setStart(new Date());
		runner.start();
	}
	
	public void stop(){
		canContinue = false;
		detail.setEnd(new Date());
		
		System.out.println(detail.toString());
		// Persistir
	}
	
	@Override
	public void run() {
			
		try {
			Sigar.load();
			Sigar sigar = new Sigar();
			
			ProcMem mem;
			ProcCpu cpu;
			while(canContinue){
				cpu = sigar.getProcCpu(pid);
				mem = sigar.getProcMem(pid);
				
				detail.add(mem.getResident(), cpu.getPercent());
				
				System.out.println("Processo " + pid + "- Mem:" + (detail.getMemoryMean()/1024)/1024 + " CPU:" + detail.getCpuMean()*100);
				
				Thread.sleep(1000);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			LOG.error("Ocorreu um erro ao monitorar o processo " + pid);
		} catch (SigarException e) {
			e.printStackTrace();
			LOG.error("Ocorreu um erro ao monitorar o processo " + pid);
		}
		
	}

}
