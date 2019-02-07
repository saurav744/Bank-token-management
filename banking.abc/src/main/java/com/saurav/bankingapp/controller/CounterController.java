package com.saurav.bankingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saurav.bankingapp.exceptions.UserNotFoundException;
import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.enums.CounterPriority;
import com.saurav.bankingapp.service.CounterService;

@RestController
public class CounterController {

	@Autowired
	private CounterService counterService;
	
	@GetMapping("/counters/{number}")
	public Counter getCounter(@PathVariable int number) {	
		return counterService.get(number);
	}
	
	@GetMapping("/counters")
	public List<Counter> getAllCounters(@PathVariable int number) {	
		return counterService.getAll();
	}
	
	@GetMapping("/counters/{priority}")
	public List<Counter> getCountersByPriority(@PathVariable CounterPriority priority) {	
		return counterService.getAllByPriority(priority);
	}
	
	@PostMapping("/counters")
	public void createCounter(@RequestBody Counter counter) {
		counterService.add(counter);
	}
	
	@DeleteMapping("/counters/{number}")
	public void deleteUser(@PathVariable int number) throws UserNotFoundException {
		counterService.delete(number);
	}

}
