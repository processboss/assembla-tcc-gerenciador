package br.com.processboss.core.scheduling.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.InitializingBean;

import br.com.processboss.core.exception.ProcessBossException;
import br.com.processboss.core.model.Schedule;
import br.com.processboss.core.model.Task;
import br.com.processboss.core.scheduling.quartz.job.TaskJob;
import br.com.processboss.core.service.IScheduleService;

public class ScheduleManagerImpl implements ScheduleManager, InitializingBean{

	private static final Log LOG = LogFactory.getLog(ScheduleManagerImpl.class);
	
	private IScheduleService scheduleService;

	protected Scheduler sched;
	
	protected Map<Long, CronTrigger> triggers = new HashMap<Long, CronTrigger>();
	
	
	public ScheduleManagerImpl(String quartzProperties) throws SchedulerException {
		SchedulerFactory sf = new StdSchedulerFactory(quartzProperties);
		sched = sf.getScheduler();
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		LOG.info("Iniciando os agendamentos do banco de dados");
		List<Schedule> schedules = scheduleService.listAll();
		addTriggers(schedules);
	}
	
	@Override
	public void addTrigger(Schedule schedule) throws ProcessBossException {
		
		try {
			
			JobDetail jobDetail = createJobDetail(schedule);
			CronTrigger trigger = createTrigger(schedule, jobDetail);
			
			sched.scheduleJob(jobDetail, trigger);
			
			triggers.put(schedule.getId(), trigger);

			sched.start();
			
		} catch (ProcessBossException e) {
			LOG.info("Ocorreu um erro ao iniciar a trigger.", e);
			throw e;
		} catch (SchedulerException e) {
			LOG.info("Ocorreu um erro ao iniciar a trigger.", e);
			throw new ProcessBossException(e);
		}
		
	}
	
	@Override
	public void addTriggers(List<Schedule> schedules) throws ProcessBossException {
		
		try {
			
			JobDetail jobDetail;
			CronTrigger trigger;
			
			for (Schedule schedule : schedules) {
				jobDetail = createJobDetail(schedule);
				trigger = createTrigger(schedule, jobDetail);
				
				sched.scheduleJob(jobDetail, trigger);
				
				triggers.put(schedule.getId(), trigger);
			}
		
			sched.start();
			
		} catch (ProcessBossException e) {
			LOG.info("Ocorreu um erro ao iniciar a triggers.", e);
			throw e;
		} catch (SchedulerException e) {
			LOG.info("Ocorreu um erro ao iniciar a triggers.", e);
			throw new ProcessBossException(e);
		}
	}
	
	public static JobDetail createJobDetail(Schedule schedule) throws ProcessBossException{
		try {
			Task task = schedule.getTask();
			
			JobDataMap jobDataMap = new JobDataMap();
			jobDataMap.put("task", task);
			
			JobDetail job = newJob(TaskJob.class)
				    .withIdentity(String.valueOf(task.getId()), Scheduler.DEFAULT_GROUP)
				    .storeDurably()
				    .requestRecovery()
				    .usingJobData(jobDataMap)
				    .build();
			
			return job;
			
		} catch (Exception e) {
			LOG.error("Ocorreu um erro ao criar o JobDetail.");
			throw new ProcessBossException("Ocorreu um errao ao criar o JobDetail");
		}
	}
	
	public static CronTrigger createTrigger(Schedule schedule, JobDetail jobDetail) throws ProcessBossException{
		try {
			Task task = schedule.getTask();
			
			TriggerBuilder<Trigger> triggerBuilder = newTrigger();
			
			triggerBuilder
					.withIdentity(String.valueOf(task.getId()), Scheduler.DEFAULT_GROUP)
				    .withSchedule(CronScheduleBuilder.cronSchedule(schedule.buildExpression()));
			
			if(jobDetail != null)
				triggerBuilder.forJob(jobDetail);
			
			return (CronTrigger) triggerBuilder.build();
			
		} catch (Exception e) {
			LOG.error("Ocorreu um erro ao criar a trigger.");
			throw new ProcessBossException("Ocorreu um errao ao criar a trigger");
		}
	}

	public IScheduleService getScheduleService() {
		return scheduleService;
	}

	public void setScheduleService(IScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

}
