package com.saurav.bankingapp.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saurav.bankingapp.exceptions.ResourceNotFoundException;
import com.saurav.bankingapp.model.BankService;
import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.enums.CounterPriority;
import com.saurav.bankingapp.repository.BankServiceRepository;
import com.saurav.bankingapp.service.BankServiceService;

/**
 *
 * @author Saurav Sharma
 *
 */
@Service("bankServiceService")
@Transactional
public class BankServiceServiceImpl implements BankServiceService {
	
	@Autowired
	private BankServiceRepository bankServiceRepository;

	@Override
	public void add(BankService bankService) {
		if(bankService == null) {
			throw new IllegalArgumentException("The bankservice parameter cannot be null");
		}
		
		bankServiceRepository.save(bankService);
		
	}

	@Override
	public void delete(long id) {
		
		bankServiceRepository.deleteById(id);
		
	}

	@Override
	public BankService get(long id) {
		
		Optional<BankService> opService = bankServiceRepository.findById(id);
		
		if (!opService.isPresent())
			throw new ResourceNotFoundException(Long.toString(id), "Bank service not found");
	
		return opService.get();
	}

	@Override
	public BankService getByName(String name) {
		
		BankService service = bankServiceRepository.findByName(name);
		
		if (service == null)
			throw new ResourceNotFoundException(name, "Bank service not found");

		return service;
	}

	@Override
	public List<BankService> getAll() {
		
		return bankServiceRepository.findAll();
	}

	@Override
	public Counter allocateCounter(String name, CounterPriority priority) {
		
		BankService service = bankServiceRepository.findByName(name);
		int min = Integer.MAX_VALUE;
		
		if (service == null)
			throw new ResourceNotFoundException(name, "Bank service not found");
		
		Counter allocated = null;
		
		for(Counter counter : service.getCounters()) {
			if(counter.getPriority() == priority && counter.getQueueSize() < min) {
				min = counter.getQueueSize();
				allocated = counter;		
			}
		}
		if (allocated == null) {
			throw new RuntimeException("Cannot allocate a counter");
		}
		return allocated;		
	}

	@Override
	public List<BankService> getUserServices(List<String> nameList) {
		
		if (nameList == null || nameList.isEmpty())
			return null;
		
		List<BankService> services = new LinkedList<BankService>();
		
		for (String name : nameList) {
			BankService bankService = bankServiceRepository.findByName(name);
			services.add(bankService);
			
			while (!bankService.getNextService().isEmpty() && bankService.getNextService() != null) {
                BankService nextService = bankServiceRepository.findByName(bankService.getNextService());
                if(nextService != null) {
                	services.add(nextService);
                }
                bankService = nextService;
			}
		}
		return services;		
	}

	@Override
	public void update(long id, BankService newService) {
		BankService bankService = bankServiceRepository.getOne(id);
		
		if (bankService == null)
			throw new ResourceNotFoundException(Long.toString(id), "Bank service not found");
		
		bankService.setNextService(newService.getNextService());
		bankService.setCounters(newService.getCounters());
		
		bankServiceRepository.save(bankService);		
	}

}
