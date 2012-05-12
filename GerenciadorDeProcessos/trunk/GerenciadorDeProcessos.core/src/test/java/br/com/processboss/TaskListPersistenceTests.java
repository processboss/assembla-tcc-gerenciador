package br.com.processboss;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.processboss.core.model.Task;
import br.com.processboss.core.model.TaskList;
import br.com.processboss.core.persistence.dao.TaskListDAO;

@ContextConfiguration(value = "classpath:br/com/processboss/tests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskListPersistenceTests {

	@Autowired
	private TaskListDAO taskListDAO;

	@Test
	public void testSaveTaskList() throws Exception {
		TaskList taskList = new TaskList();
		taskList.setName("First Task List");
		
		taskListDAO.save(taskList);

		assertNotNull(taskList.getId());
	}

	@Test
	public void testSaveTaskListWithTasksAndGet() throws Exception {
		TaskList taskList = new TaskList();
		taskList.setName("Second Task List");
		
		Task task1 = new Task();
		task1.setName("Task1");
		task1.setDateCreation(new Date());
		task1.setDescription("Task to be executed");
		task1.setPath("/batch/task1.bat");
		
		Task task2 = new Task();
		task2.setName("Task1");
		task2.setDateCreation(new Date());
		task2.setDescription("Task to be executed");
		task2.setPath("/batch/task1.bat");
		
		taskList.addTask(task1);
		taskList.addTask(task2);
		
		taskListDAO.save(taskList);
		
		assertNotNull(taskList.getId());
		
		TaskList other = taskListDAO.findById(taskList.getId());
		
		assertNotNull(other);
		Assert.assertEquals(taskList.getTasks().size(), other.getTasks().size());
		
	}

}
