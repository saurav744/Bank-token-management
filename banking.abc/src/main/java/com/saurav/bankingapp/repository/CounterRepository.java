package com.saurav.bankingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.enums.CounterPriority;

public interface CounterRepository extends JpaRepository<Counter, Long> {
	
	List<Counter> findByPriority(CounterPriority priority);
	
	Counter findByNumber(int num);

}
