package com.saurav.bankingapp.service;

import java.util.List;

import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.enums.CounterPriority;

public interface CounterService {
	
	public void add(Counter counter);
	
	public void delete(long id);
	
	public Counter get(int number);
	
	public List<Counter> getAll();
	
	public List<Counter> getAllByPriority(CounterPriority priority);
	
	public void incrementQueueSize(int number);
	
	public void decrementQueueSize(int number);
	
	public List<Counter> getCountersFromList(List<Integer> counterNumbers);

}
