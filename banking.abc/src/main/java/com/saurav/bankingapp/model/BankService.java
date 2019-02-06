package com.saurav.bankingapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;

@Entity
public class BankService {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@Column(unique = true)
	private String name;

	@Column(name = "next_service_id")
	private Long nextId;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "service_counter", joinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "counter_id", referencedColumnName = "id"))
	private List<Counter> counters;

	public BankService() {
		
		counters = new ArrayList<>();
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

	public Long getNextId() {
		return nextId;
	}

	public void setNextId(Long nextId) {
		this.nextId = nextId;
	}

	public List<Counter> getCounters() {
		return counters;
	}

	public void setCounters(List<Counter> counters) {
		this.counters = counters;
	}

}
