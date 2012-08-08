package br.com.processboss.core.executor;

import br.com.processboss.core.model.Task;

/**
 * Classe responsavel pela execucao da tarefa
 *
 */
public class TaskExecutor {

	private Task task;
	
	public TaskExecutor(Task task) {
		this.task = task;
	}
	
	public void execute(){
		//TODO: Implementar a execucao da tarefa
		System.out.println("Vou executar a tarefa: " + task.getName());
	}

	public Task getTask() {
		return task;
	}
	
}
