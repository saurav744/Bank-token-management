package com.saurav.bankingapp.service;

import java.util.List;

import com.saurav.bankingapp.exceptions.TokenNotFoundException;
import com.saurav.bankingapp.model.BankService;
import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.Token;
import com.saurav.bankingapp.model.User;
import com.saurav.bankingapp.model.enums.TokenState;

public interface TokenService {
	
	public Token create(User user, Counter counter, List<BankService> services);
	
	public void delete(long id) throws TokenNotFoundException;
	
	public Token get(long id) throws TokenNotFoundException;
	
	public List<Token> getValidTokens();
	
	public boolean isValid(long id) throws TokenNotFoundException;
	
	public void setState(long id, TokenState state) throws TokenNotFoundException;
	
	public void updateComment(long id, String comment) throws TokenNotFoundException;
	
	public Token completeCurrentJob(long id) throws TokenNotFoundException;
	
	public List<Token> getTokensByCounter(Counter counter);
	
	public void updateToken(Token token);

}
