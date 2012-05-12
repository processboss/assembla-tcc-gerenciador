package br.com.processboss.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.CompareToBuilder;

@Entity
public class Task implements Serializable, Comparable<Task> {

	private static final long serialVersionUID = -5254344032568432179L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	private String description;
	
	@Column(name="date_creation")
	private Date dateCreation;
	
	private int executionOrder;
	
	@Column(length=1000)
	private String path;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="task_id")
	private List<TaskExecutionInfo> executions;
	
	public Task() {
		this.executions = new LinkedList<TaskExecutionInfo>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public int getExecutionOrder() {
		return executionOrder;
	}

	public void setExecutionOrder(int order) {
		this.executionOrder = order;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public List<TaskExecutionInfo> getExecutions() {
		return executions;
	}

	public void addExecution(TaskExecutionInfo execution) {
		this.executions.add(execution);
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int compareTo(Task o) {
		if(o == null){
			return 0;
		}
		return new CompareToBuilder().append(this.getExecutionOrder(), o.getExecutionOrder()).toComparison();
	}


}
