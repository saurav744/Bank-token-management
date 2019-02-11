package com.saurav.bankingapp.model.request_response;

import java.util.List;

public class CounterResponse {
	
	private int counterNumber;
	
	private List<Long> tokens;

	public CounterResponse(int counterNumber, List<Long> tokens) {
		this.counterNumber = counterNumber;
		this.tokens = tokens;
	}

	public CounterResponse() {
	}

	public int getCounterNumber() {
		return counterNumber;
	}

	public void setCounterNumber(int counterNumber) {
		this.counterNumber = counterNumber;
	}

	public List<Long> getTokens() {
		return tokens;
	}

	public void setTokens(List<Long> tokens) {
		this.tokens = tokens;
	}
	
	
	

}
