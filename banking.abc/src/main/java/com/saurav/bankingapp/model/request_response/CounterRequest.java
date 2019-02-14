package com.saurav.bankingapp.model.request_response;

import com.saurav.bankingapp.model.enums.CounterPriority;

public class CounterRequest {

	private int number;

	private CounterPriority priority;

	public CounterRequest(int number, CounterPriority priority) {
		super();
		this.number = number;
		this.priority = priority;
	}

	public CounterRequest() {
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public CounterPriority getPriority() {
		return priority;
	}

	public void setPriority(CounterPriority priority) {
		this.priority = priority;
	}

}
