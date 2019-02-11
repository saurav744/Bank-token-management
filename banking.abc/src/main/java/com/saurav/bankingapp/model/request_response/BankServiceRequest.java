package com.saurav.bankingapp.model.request_response;

import java.util.List;

public class BankServiceRequest {

	private String name;

	private Long nextId;

	private List<Integer> counters;

	public BankServiceRequest(String name, Long nextId, List<Integer> counters) {
		super();
		this.name = name;
		this.nextId = nextId;
		this.counters = counters;
	}

	public BankServiceRequest() {
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

	public List<Integer> getCounters() {
		return counters;
	}

	public void setCounters(List<Integer> counters) {
		this.counters = counters;
	}

}
