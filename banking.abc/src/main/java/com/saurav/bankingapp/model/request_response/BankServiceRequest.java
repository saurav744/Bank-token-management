package com.saurav.bankingapp.model.request_response;

import java.util.List;

public class BankServiceRequest {

	private String name;

	private String nextService;

	private List<Integer> counters;

	public BankServiceRequest(String name, String nextService, List<Integer> counters) {
		super();
		this.name = name;
		this.nextService = nextService;
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

	public String getNextService() {
		return nextService;
	}

	public void setNextService(String nextService) {
		this.nextService = nextService;
	}

	public List<Integer> getCounters() {
		return counters;
	}

	public void setCounters(List<Integer> counters) {
		this.counters = counters;
	}

}
