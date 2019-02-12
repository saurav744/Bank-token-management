package com.saurav.bankingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.enums.CounterPriority;

/**
 * This class extends JpaRepository interface to perform CRUD operations on Counter entity
 * 
 * @author Saurav Sharma
 *
 */
public interface CounterRepository extends JpaRepository<Counter, Long> {
	/**
	 * Returns a list of Counters based on priority
	 * @param priority priority of the counter i.e. HIGH, NORMAL etc
	 * @return list of counters with given priority
	 */
	List<Counter> findByPriority(CounterPriority priority);
	
	/**
	 * Returns a counter with given number
	 * @param num number of the counter
	 * @return counter object with given number
	 */
	Counter findByNumber(int num);

}
