package br.com.processboss;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.processboss.core.model.TaskExecutionInfo;



@ContextConfiguration(value="classpath:br/com/processboss/tests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskExecutionInfoPersistenceTests {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Test
	@Transactional
	public void testSaveTaskExecutionInfo() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		TaskExecutionInfo executionInfo = new TaskExecutionInfo();
		executionInfo.setExecutionStart(new Date());
		
		Thread.sleep(1000);
		
		executionInfo.setMemoryConsumer(10000);
		executionInfo.setCpuConsumer(200);
		
		executionInfo.setExecutionEnd(new Date());

		executionInfo.calculateMeanMemoryConsumer();
		executionInfo.calculateMeanCpuConsumer();
		
		session.save(executionInfo);
		session.flush();
		assertNotNull(executionInfo.getId());
	}


}
