package com.saurav.bankingapp.model.dto;

import java.util.List;

public class CounterDto {
	
	private int counterNumber;
	
	private List<Long> tokens;

	public CounterDto(int counterNumber, List<Long> tokens) {
		super();
		this.counterNumber = counterNumber;
		this.tokens = tokens;
	}

	public CounterDto() {
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
