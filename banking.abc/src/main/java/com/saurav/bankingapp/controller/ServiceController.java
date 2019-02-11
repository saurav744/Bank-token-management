package com.saurav.bankingapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saurav.bankingapp.model.BankService;
import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.request_response.BankServiceRequest;
import com.saurav.bankingapp.service.BankServiceService;
import com.saurav.bankingapp.service.CounterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "APIs to manage bank services")
@RestController
public class ServiceController {
	
	@Autowired
	private BankServiceService bankServiceService;
	@Autowired
	private CounterService counterService;
	
	@ApiOperation(value = "View list all services", response = List.class)
	@GetMapping("/services")
	public List<String> getServices() {
		List<BankService> bankServices = bankServiceService.getAll();	
		List<String> serviceNames = bankServices.stream().map(BankService::getName).collect(Collectors.toList());
		
		return serviceNames;	
	}
	
	@ApiOperation(value = "View a service by name", response = BankService.class)
	@GetMapping("/services/{name}")
	public BankService getServiceByName(
			@ApiParam(value = "Unique name of the service", required = true) @PathVariable String name) {
		return bankServiceService.getByName(name);
	}
	
	@ApiOperation(value = "Create a new service")
	@PostMapping("/services")
	public void createService(
			@ApiParam(value = "Service request object", required = true) @RequestBody BankServiceRequest newServiceRequest) {
		List<Counter> counters = counterService.getCountersFromList(newServiceRequest.getCounters());	
		BankService newService = new BankService(newServiceRequest.getName(), newServiceRequest.getNextId(), counters);		
		bankServiceService.add(newService);
	}
	
	@ApiOperation(value = "Delete service by id")
	@DeleteMapping("/services/{id}")
	public void deleteService(
			@ApiParam(value = "Unique Id of the service", required = true) @PathVariable Long id) {
		bankServiceService.delete(id);
	}
	
	@ApiOperation(value = "Update a service")
	@PutMapping("/services/{id}")
	public void updateService(
			@ApiParam(value = "Unique id of the service to be updated", required = true) @PathVariable Long id, 
			@ApiParam(value = "Service request object containing updated values", required = true) @RequestBody BankServiceRequest newServiceRequest) {
		List<Counter> counters = counterService.getCountersFromList(newServiceRequest.getCounters());
		BankService newService = new BankService(newServiceRequest.getName(), newServiceRequest.getNextId(), counters);
		bankServiceService.update(id, newService);
	}

}
