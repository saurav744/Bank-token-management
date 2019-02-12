package com.saurav.bankingapp.service;

import java.util.List;

import com.saurav.bankingapp.model.BankService;
import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.enums.CounterPriority;
/**
 *
 * @author Saurav Sharma
 *
 */
public interface BankServiceService {
	/**
	 * This saves a bankService object to Repository
	 * @param bank service object containing bank service info
	 */
	public void add(BankService bankService);
	
	/**
	 * Deletes bank service from the repository
	 * @param id unique id of the service
	 */
	public void delete(long id);
	
	/**
	 * Gets bank service object by its id
	 * @param id unique id of service 
	 * @return bank service object
	 */
	public BankService get(long id);
	
	/**
	 * Gets bank service object by its name
	 * @param name unique name of bank service
	 * @return bank service object
	 */
	public BankService getByName(String name);
	
	/**
	 * Lists all bank services from repository
	 * @return list of bank services 
	 */
	public List<BankService> getAll();
	
	/**
	 * Allocates a counter with minimum queue size to the service requested by token
	 * @param name name of the service
	 * @param priority priority of the counter : HIGH or NORMAL
	 * @return counter object that serves the requested service
	 */
	public Counter allocateCounter(String name, CounterPriority priority);
	
	/**
	 * This converts the list of bank services strings to list of bank service objects
	 * @param services list of bank service names strings
	 * @return list of bank service objects
	 */
	public List<BankService> getUserServices(List<String> services);
	
	/**
	 * Updates a service by its id
	 * @param id unique id of the service to be updated
	 * @param service new object containing updated values
	 */
	public void update(long id, BankService service);

}
