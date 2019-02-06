package com.saurav.bankingapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saurav.bankingapp.exceptions.TokenNotFoundException;
import com.saurav.bankingapp.exceptions.UserNotFoundException;
import com.saurav.bankingapp.model.BankService;
import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.Token;
import com.saurav.bankingapp.model.User;
import com.saurav.bankingapp.model.dto.CounterDto;
import com.saurav.bankingapp.model.dto.TokenRequest;
import com.saurav.bankingapp.model.enums.CounterPriority;
import com.saurav.bankingapp.model.enums.TokenState;
import com.saurav.bankingapp.model.enums.UserType;
import com.saurav.bankingapp.service.BankServiceService;
import com.saurav.bankingapp.service.CounterService;
import com.saurav.bankingapp.service.TokenService;
import com.saurav.bankingapp.service.UserService;

@RestController
public class BankingAppController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CounterService counterService;
	@Autowired
	private BankServiceService bankServiceService;
	
	@GetMapping("/users/id/{id}")
	public User getUser(@PathVariable Long id) throws UserNotFoundException {
		return userService.getById(id);
	}
	
	@GetMapping("/users/phone/{phone}")
	public User getUser(@PathVariable String phone) throws UserNotFoundException {	
		return userService.get(phone);
	}
	
	
	@PostMapping("/users")
	public void createUser(@RequestBody User newUser) {
		userService.add(newUser.getName(), newUser.getEmail(), newUser.getPassword(), newUser.getPhone(), newUser.getAddress(), newUser.getType());
	}
	
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable Long id) throws UserNotFoundException {

		userService.delete(id);
	
	}
	
	@PostMapping("/services")
	public void createService(@RequestBody BankService newService) {
		bankServiceService.add(newService);
	}
	
	@PostMapping("/counters")
	public void createCounter(@RequestBody Counter counter) {
		counterService.add(counter);
	}
	
	@GetMapping("/tokens")
	public List<CounterDto> getCounterTokenMapping() throws Exception {
		
		List<CounterDto> counterTokenMap = new ArrayList<CounterDto>();
		List<Counter> counters = counterService.getAll();
		
		for (Counter counter : counters) {
			
			List<Token> tokens = tokenService.getTokensByCounter(counter);
			List<Long> tokenIds = tokens.stream().map(Token::getId).collect(Collectors.toList());
			CounterDto counterDto = new CounterDto(counter.getNumber(), tokenIds);
			counterTokenMap.add(counterDto);	
		}
		return counterTokenMap;
	}
	
	@GetMapping("/tokens/{counterNumber}")
	public CounterDto getTokensByCounter(@PathVariable int counterNumber) throws Exception {
		
		Counter counter = counterService.get(counterNumber);
		List<Token> tokens = tokenService.getTokensByCounter(counter);
		List<Long> tokenIds = tokens.stream().map(Token::getId).collect(Collectors.toList());
		CounterDto counterDto = new CounterDto(counterNumber, tokenIds);
		
		return counterDto;	
	}
	
	@Transactional
	@PostMapping("/tokens")
	public long create(@RequestBody TokenRequest tokenRequest) throws Exception {
		
		List<BankService> services = bankServiceService.getUserServices(tokenRequest.getServiceNames());
		User user = userService.get(tokenRequest.getPhone());
		CounterPriority priority = (user.getType() == UserType.PREMIUM) ? CounterPriority.HIGH : CounterPriority.NORMAL;
		Counter counter = bankServiceService.allocateCounter(services.get(0).getName(), priority);
		counterService.incrementQueueSize(counter.getNumber());
		
		return tokenService.create(user, counter, services);	
	}
	
	@Transactional
	@PutMapping("/tokens/{id}/complete")
	public void completeToken(@PathVariable Long id) throws TokenNotFoundException {

		if(!tokenService.isValid(id)) {	
			throw new TokenNotFoundException();
		}

		Token token = tokenService.completeCurrentJob(id);
		if(token.getStatus() == TokenState.COMPLETED) {
			counterService.decrementQueueSize(token.getCurrentCounter().getNumber());
		} else {
			if(!token.getCurrentJob().getService().getCounters().contains(token.getCurrentCounter()) ) {
				CounterPriority priority = (token.getUser().getType() == UserType.PREMIUM) ? CounterPriority.HIGH : CounterPriority.NORMAL;
				Counter counter = bankServiceService.allocateCounter(token.getCurrentJob().getService().getName(), priority);
				
				counterService.decrementQueueSize(token.getCurrentCounter().getNumber());
				counterService.incrementQueueSize(counter.getNumber());
				token.setCurrentCounter(counter);
			}
		}
		
		tokenService.updateToken(token);
	}
	
	@Transactional
	@PutMapping("/tokens/{id}/comment")
	public void comment(@PathVariable Long id, @RequestBody String comment) throws TokenNotFoundException {

		if(!tokenService.isValid(id)) {	
			throw new TokenNotFoundException();
		}
		
		tokenService.updateComment(id, comment);
	}
	
	@Transactional
	@PutMapping("/tokens/{id}/cancel")
	public void cancelToken(@PathVariable Long id) throws TokenNotFoundException {

		if(!tokenService.isValid(id)) {	
			throw new TokenNotFoundException();
		}
		
		tokenService.setState(id, TokenState.CANCELLED);
	}
	
	@GetMapping("/services")
	public List<String> getServices() {
		List<BankService> bankServices = bankServiceService.getAll();	
		List<String> serviceNames = bankServices.stream().map(BankService::getName).collect(Collectors.toList());
		
		return serviceNames;	
	}
	
	
}
