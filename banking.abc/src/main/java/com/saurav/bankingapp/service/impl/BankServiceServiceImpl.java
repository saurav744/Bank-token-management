package com.saurav.bankingapp.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		
		bankServiceRepository.save(bankService);
		
	}

	@Override
	public void delete(long id) {
		
		bankServiceRepository.deleteById(id);
		
	}

	@Override
	public BankService get(long id) {
	
		return bankServiceRepository.getOne(id);
	}

	@Override
	public BankService getByName(String name) {
		
		return bankServiceRepository.findByName(name);
	}

	@Override
	public List<BankService> getAll() {
		
		return bankServiceRepository.findAll();
	}

	@Override
	public Counter allocateCounter(String name, CounterPriority priority) {
		
		BankService service = bankServiceRepository.findByName(name);
		int min = Integer.MAX_VALUE;
		
		Counter allocated = null;
		
		for(Counter counter : service.getCounters()) {
			if(counter.getPriority() == priority && counter.getQueueSize() < min) {
				min = counter.getQueueSize();
				allocated = counter;		
			}
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
			
			while (bankService.getNextId() != null) {
                Optional<BankService> nextService = bankServiceRepository.findById(bankService.getNextId());
                if(nextService.isPresent()) {
                	services.add(nextService.get());
                }
                bankService = nextService.get();
			}
		}
		return services;		
	}

	@Override
	public void update(long id, BankService newService) {
		BankService bankService = bankServiceRepository.getOne(id);
		
		bankService.setNextId(newService.getNextId());
		bankService.setCounters(newService.getCounters());
		
		bankServiceRepository.save(bankService);		
	}

}
