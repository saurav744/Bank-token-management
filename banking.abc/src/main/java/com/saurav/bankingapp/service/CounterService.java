package com.saurav.bankingapp.service;

import java.util.List;

import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.enums.CounterPriority;

/**
 * 
 * @author Saurav Sharma
 *
 */
public interface CounterService {
	
	/**
	 * Adds a new counter to repository
	 * @param counter counter object to be saved
	 */
	public void add(Counter counter);
	
	/**
	 * Deletes a counter from repository
	 * @param id unique id of the counter in db
	 */
	public void delete(long id);
	
	/**
	 * Returns a counter object by it's number
	 * @param number unique identifier of the counter
	 * @return the counter object
	 */
	public Counter get(int number);
	
	/**
	 * This method fetches a complete list of counters
	 * @return a list of all counters
	 */
	public List<Counter> getAll();
	
	/**
	 * This method fetches list of counters based on priority
	 * @param priority the priority of that counter: HIGH or NORMAL
	 * @return a list of counters with specific priority
	 */
	public List<Counter> getAllByPriority(CounterPriority priority);
	
	/**
	 * Increments the size of queue of given counter
	 * @param number unique identifier of counter
	 */
	public void incrementQueueSize(int number);
	
	/**
	 * Decrements the queue size of given counter
	 * @param number unique identifier of counter
	 */
	public void decrementQueueSize(int number);
	
	/**
	 * Convert a list of counter numbers to list of counter objects
	 * @param counterNumbers list of integers corresponding to counter numbers
	 * @return list of counter objects
	 */
	public List<Counter> getCountersFromList(List<Integer> counterNumbers);

}
