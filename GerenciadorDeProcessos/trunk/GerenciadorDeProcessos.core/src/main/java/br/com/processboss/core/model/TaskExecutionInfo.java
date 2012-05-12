package br.com.processboss.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.CompareToBuilder;

@Entity
public class TaskExecutionInfo implements Serializable,
		Comparable<TaskExecutionInfo> {

	private static final long serialVersionUID = -9093235787487341478L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private Date executionStart;
	private Date executionEnd;
	private long executionTime;
	private long topMemoryConsumer;
	private long memoryConsumer;
	private long meanMemoryConsumer;
	private long topCpuConsumer;
	private long cpuConsumer;
	private long meanCpuConsumer;

	public TaskExecutionInfo() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTopMemoryConsumer() {
		return topMemoryConsumer;
	}

	public void setTopMemoryConsumer(long topMemoryConsumer) {
		this.topMemoryConsumer = topMemoryConsumer;
	}

	public long getMemoryConsumer() {
		return memoryConsumer;
	}

	public void setMemoryConsumer(long memoryConsumer) {
		this.memoryConsumer = memoryConsumer;
	}

	public long getTopCpuConsumer() {
		return topCpuConsumer;
	}

	public void setTopCpuConsumer(long topCpuConsumer) {
		this.topCpuConsumer = topCpuConsumer;
	}

	public long getCpuConsumer() {
		return cpuConsumer;
	}

	public void setCpuConsumer(long cpuConsumer) {
		this.cpuConsumer = cpuConsumer;
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
		TaskExecutionInfo other = (TaskExecutionInfo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int compareTo(TaskExecutionInfo o) {
		if (o == null) {
			return 0;
		}
		return new CompareToBuilder().append(this.getExecutionStart(), o.getExecutionStart())
				.toComparison();
	}

	public long getMeanMemoryConsumer() {
		return meanMemoryConsumer;
	}

	public void calculateMeanMemoryConsumer() {
		calculateExecutionTime();
		if (executionTime != 0) {
			meanMemoryConsumer = memoryConsumer / executionTime;
		}
	}

	public long getMeanCpuConsumer() {
		return meanCpuConsumer;
	}

	public void calculateMeanCpuConsumer() {
		calculateExecutionTime();
		if (executionTime != 0) {
			meanCpuConsumer = cpuConsumer / executionTime;
		}
	}

	public void calculateExecutionTime() {
		if (executionStart == null || executionEnd == null) {
			return;
		}
		executionTime = executionEnd.getTime() - executionStart.getTime();
	}

	public long getExecutionTime() {
		return this.executionTime;
	}

	public Date getExecutionStart() {
		return executionStart;
	}

	public void setExecutionStart(Date executionStart) {
		this.executionStart = executionStart;
	}

	public Date getExecutionEnd() {
		return executionEnd;
	}

	public void setExecutionEnd(Date executionEnd) {
		this.executionEnd = executionEnd;
	}

}
