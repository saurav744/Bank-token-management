package com.saurav.bankingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.User;
import com.saurav.bankingapp.model.enums.CounterPriority;
import com.saurav.bankingapp.model.request_response.CounterRequest;
import com.saurav.bankingapp.service.CounterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "Counter management APIs")
@RestController
public class CounterController {

	@Autowired
	private CounterService counterService;
	
	@ApiOperation(value = "View a Counter by number", response = Counter.class)
	@GetMapping("/counters/{number}")
	public Counter getCounter(
			@ApiParam(value = "Unique number of the Counter", required = true) @PathVariable int number) {	
		return counterService.get(number);
	}

	@ApiOperation(value = "View all Counters", response = List.class)
	@GetMapping("/counters")
	public List<Counter> getAllCounters() {	
		return counterService.getAll();
	}
	
	@ApiOperation(value = "View Counters by priority", response = List.class)
	@GetMapping("/counters/{priority}")
	public List<Counter> getCountersByPriority(
			@ApiParam(value = "Priority of the Counter", required = true) @PathVariable CounterPriority priority) {	
		return counterService.getAllByPriority(priority);
	}
	
	@ApiOperation(value = "Create a new Counter")
	@PostMapping("/counters")
	public void createCounter(
			@ApiParam(value = "Counter object to be stored", required = true) @RequestBody CounterRequest counterRequest) {
		Counter counter = new Counter(counterRequest.getNumber(), counterRequest.getPriority());
		counterService.add(counter);
	}
	
	@ApiOperation(value = "Delete Counter by number", response = User.class)
	@DeleteMapping("/counters/{number}")
	public void deleteCounter(
			@ApiParam(value = "Unique number of the Counter to be deleted", required = true) @PathVariable int number) {
		counterService.delete(number);
	}

}
