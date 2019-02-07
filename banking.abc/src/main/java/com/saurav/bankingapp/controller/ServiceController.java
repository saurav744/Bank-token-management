package com.saurav.bankingapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saurav.bankingapp.model.BankService;
import com.saurav.bankingapp.service.BankServiceService;

@RestController
public class ServiceController {
	
	@Autowired
	private BankServiceService bankServiceService;
	
	@GetMapping("/services")
	public List<String> getServices() {
		List<BankService> bankServices = bankServiceService.getAll();	
		List<String> serviceNames = bankServices.stream().map(BankService::getName).collect(Collectors.toList());
		
		return serviceNames;	
	}
	
	@GetMapping("/services/{name}")
	public BankService getServiceByName(@PathVariable String name) {
		return bankServiceService.getByName(name);
	}
		
	@PostMapping("/services")
	public void createService(@RequestBody BankService newService) {
		bankServiceService.add(newService);
	}
	
	@DeleteMapping("/services/{id}")
	public void deleteUser(@PathVariable Long id) {
		bankServiceService.delete(id);
	}

}
