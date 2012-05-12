package br.com.processboss;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.processboss.core.model.Task;
import br.com.processboss.core.persistence.dao.TaskDAO;

@ContextConfiguration(value = "classpath:br/com/processboss/tests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskPersistenceTests {

	@Autowired
	private TaskDAO taskDAO;

	@Test
	public void testSaveTask() throws Exception {

		Task task = new Task();
		task.setName("Task1");
		task.setDateCreation(new Date());
		task.setDescription("Task to be executed");
		task.setPath("/batch/task1.bat");

		taskDAO.save(task);

		assertNotNull(task.getId());
	}

}
