package com.saurav.bankingapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saurav.bankingapp.exceptions.TokenNotFoundException;
import com.saurav.bankingapp.model.BankService;
import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.Token;
import com.saurav.bankingapp.model.User;
import com.saurav.bankingapp.model.dto.CounterResponse;
import com.saurav.bankingapp.model.dto.TokenRequest;
import com.saurav.bankingapp.model.enums.CounterPriority;
import com.saurav.bankingapp.model.enums.TokenState;
import com.saurav.bankingapp.model.enums.UserType;
import com.saurav.bankingapp.service.BankServiceService;
import com.saurav.bankingapp.service.CounterService;
import com.saurav.bankingapp.service.TokenService;
import com.saurav.bankingapp.service.UserService;

@RestController
public class TokenController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CounterService counterService;
	@Autowired
	private BankServiceService bankServiceService;
	
	
	@GetMapping("/tokens")
	public List<CounterResponse> getCounterTokenMapping() throws Exception {
		
		List<CounterResponse> counterTokenMap = new ArrayList<CounterResponse>();
		List<Counter> counters = counterService.getAll();
		
		for (Counter counter : counters) {
			
			List<Token> tokens = tokenService.getTokensByCounter(counter);
			List<Long> tokenIds = tokens.stream().map(Token::getId).collect(Collectors.toList());
			CounterResponse counterResponse = new CounterResponse(counter.getNumber(), tokenIds);
			counterTokenMap.add(counterResponse);	
		}
		return counterTokenMap;
	}
	
	@GetMapping("/tokens/{counterNumber}")
	public CounterResponse getTokensByCounter(@PathVariable int counterNumber) throws Exception {
		
		Counter counter = counterService.get(counterNumber);
		List<Token> tokens = tokenService.getTokensByCounter(counter);
		List<Long> tokenIds = tokens.stream().map(Token::getId).collect(Collectors.toList());
		CounterResponse counterResponse = new CounterResponse(counterNumber, tokenIds);
		
		return counterResponse;	
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
	
}
