package com.saurav.bankingapp.controller;

import java.util.ArrayList;
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
import com.saurav.bankingapp.model.Job;
import com.saurav.bankingapp.model.Token;
import com.saurav.bankingapp.model.User;
import com.saurav.bankingapp.model.enums.CounterPriority;
import com.saurav.bankingapp.model.enums.TokenState;
import com.saurav.bankingapp.model.enums.UserType;
import com.saurav.bankingapp.model.request_response.CounterResponse;
import com.saurav.bankingapp.model.request_response.TokenRequest;
import com.saurav.bankingapp.model.request_response.TokenResponse;
import com.saurav.bankingapp.service.BankServiceService;
import com.saurav.bankingapp.service.CounterService;
import com.saurav.bankingapp.service.TokenService;
import com.saurav.bankingapp.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "Token")
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
	
	@ApiOperation(value = "Returns mapping of tokens to counters", response = List.class)
	@GetMapping("/tokens")
	public List<CounterResponse> getCounterTokenMapping() throws Exception {
		
		List<CounterResponse> counterTokenMap = new ArrayList<CounterResponse>();
		List<Counter> counters = counterService.getAll();
		
		for (Counter counter : counters) {	
			counterTokenMap.add(createCounterReponse(counter));	
		}
		return counterTokenMap;
	}
	
	@ApiOperation(value = "Returns a list of tokens for a given counter", response = CounterResponse.class)
	@GetMapping("/tokens/counter/{counterNumber}")
	public CounterResponse getTokensByCounter(
			@ApiParam(value = "Unique Counter number", required = true) @PathVariable int counterNumber) {
		
		Counter counter = counterService.get(counterNumber);
		return createCounterReponse(counter);	
	}
	
	private CounterResponse createCounterReponse(Counter counter) {		
		List<Token> tokens = tokenService.getTokensByCounter(counter);
		List<Long> tokenIds = tokens.stream().map(Token::getId).collect(Collectors.toList());
		CounterResponse counterResponse = new CounterResponse(counter.getNumber(), tokenIds);
		
		return counterResponse;		
	}
	
	@ApiOperation(value = "Returns a token by id", response = TokenResponse.class)
	@GetMapping("/tokens/{id}")
	public TokenResponse getTokenById(
			@ApiParam(value = "Unique Id of the token", required = true) @PathVariable long id) {
		
		Token token = tokenService.get(id);
		return createTokenResponse(token);
	}
	
	@ApiOperation(value = "Create a new Token from token request", response = TokenResponse.class)
	@PostMapping("/tokens")
	public TokenResponse create(
			@ApiParam(value = "Token request object", required = true) @RequestBody TokenRequest tokenRequest) {
		
		List<BankService> services = bankServiceService.getUserServices(tokenRequest.getServiceNames());
		User user = userService.get(tokenRequest.getPhone());
		CounterPriority priority = (user.getType() == UserType.PREMIUM) ? CounterPriority.HIGH : CounterPriority.NORMAL;
		Counter counter = bankServiceService.allocateCounter(services.get(0).getName(), priority);
		counterService.incrementQueueSize(counter.getNumber());
		
		Token token = tokenService.create(user, counter, services);
		
		return createTokenResponse(token);
	}
	
	private TokenResponse createTokenResponse(Token token) {
		
		List<String> services = new ArrayList<String>();
		
		for(Job job : token.getTokenJobs()) {
			services.add(job.getService().getName());
		}
		
		return new TokenResponse(token.getId(), token.getUser().getId(), services, 
				token.getCurrentCounter().getNumber(), token.getCurrentJob().getId(), token.getStatus(),
				token.getCreatedAt(), token.getComment());	
	}
	
	@ApiOperation(value = "Marks the current job of a token completed")
	@PutMapping("/tokens/{id}/complete")
	public void completeToken(
			@ApiParam(value = "Unique Id of the token", required = true) @PathVariable Long id) {

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
	
	@ApiOperation(value = "Updates the comment of a token")
	@PutMapping("/tokens/{id}/comment")
	public void comment(
			@ApiParam(value = "Unique Id of the token", required = true) @PathVariable Long id, 
			@ApiParam(value = "Updated comment string", required = true) @RequestBody String comment) {
		
		tokenService.updateComment(id, comment);
	}
	
	@ApiOperation(value = "Marks a given token cancelled")
	@PutMapping("/tokens/{id}/cancel")
	public void cancelToken(
			@ApiParam(value = "Unique Id of the token", required = true) @PathVariable Long id) {
		
		tokenService.setState(id, TokenState.CANCELLED);
	}
	
	@ApiOperation(value = "Delete token by number", response = User.class)
	@DeleteMapping("/tokens/{id}")
	public void deleteToken(
			@ApiParam(value = "Unique id of the token to be deleted", required = true) @PathVariable Long id) {
		tokenService.delete(id);
	}
	
}
