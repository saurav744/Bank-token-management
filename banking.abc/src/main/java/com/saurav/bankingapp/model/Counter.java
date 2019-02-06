package com.saurav.bankingapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.saurav.bankingapp.model.enums.CounterPriority;

@Entity
public class Counter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(unique = true)
	private int number;
	
	@Column(name = "queue_size")
	private int queueSize;
	
	private CounterPriority priority;

	public Counter() {
	
	}
	
	public Counter (int number, CounterPriority priority) {	
		this.number = number;
		this.priority = priority;
		this.queueSize = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	public CounterPriority getPriority() {
		return priority;
	}

	public void setPriority(CounterPriority priority) {
		this.priority = priority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Counter other = (Counter) obj;
		if (number != other.number)
			return false;
		return true;
	}
	
	
	
}
