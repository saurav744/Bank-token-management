package com.saurav.bankingapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.enums.CounterPriority;
import com.saurav.bankingapp.repository.CounterRepository;
import com.saurav.bankingapp.service.CounterService;

@Service("counterService")
@Transactional
public class CounterServiceImpl implements CounterService{
	
	@Autowired
	private CounterRepository counterRepository;

	@Override
	public void add(Counter counter) {
		counterRepository.save(counter);
	}

	@Override
	public void delete(long id) {
		counterRepository.deleteById(id);	
	}

	@Override
	public Counter get(int number) {
		return counterRepository.findByNumber(number);
	}

	@Override
	public List<Counter> getAll() {
		return counterRepository.findAll();
	}

	@Override
	public List<Counter> getAllByPriority(CounterPriority priority) {
		return counterRepository.findByPriority(priority);
	}

	@Override
	public synchronized void incrementQueueSize(int number) {
		Counter counter = counterRepository.findByNumber(number);
		int len = counter.getQueueSize();
		counter.setQueueSize(len + 1);
		counterRepository.save(counter);
		
	}

	@Override
	public synchronized void decrementQueueSize(int number) {
		Counter counter = counterRepository.findByNumber(number);
		int len = counter.getQueueSize();
		if(len > 0) {
			counter.setQueueSize(len - 1);
			counterRepository.save(counter);
		}	
	}

}
