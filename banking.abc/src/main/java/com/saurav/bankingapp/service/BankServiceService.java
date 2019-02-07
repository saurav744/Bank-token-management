package com.saurav.bankingapp.service;

import java.util.List;

import com.saurav.bankingapp.model.BankService;
import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.enums.CounterPriority;

public interface BankServiceService {
	
	public void add(BankService bankService);
	
	public void delete(long id);
	
	public BankService get(long id);
	
	public BankService getByName(String name);
	
	public List<BankService> getAll();
	
	public Counter allocateCounter(String name, CounterPriority priority);
	
	public List<BankService> getUserServices(List<String> services);

}
