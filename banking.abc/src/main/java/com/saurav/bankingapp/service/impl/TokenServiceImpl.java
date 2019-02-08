package com.saurav.bankingapp.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saurav.bankingapp.exceptions.TokenNotFoundException;
import com.saurav.bankingapp.model.BankService;
import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.Job;
import com.saurav.bankingapp.model.Token;
import com.saurav.bankingapp.model.User;
import com.saurav.bankingapp.model.enums.TokenState;
import com.saurav.bankingapp.repository.TokenRepository;
import com.saurav.bankingapp.service.TokenService;

@Service("tokenService")
@Transactional
public class TokenServiceImpl implements TokenService {
	
	@Autowired
	private TokenRepository tokenRepository;

	@Override
	public Token create(User user, Counter counter, List<BankService> services) {
		
		Token token = new Token();
		
		List<Job> tokenJobs = new LinkedList<Job>();
		
		for(BankService service : services) {
			Job job = new Job(service, token);
			tokenJobs.add(job);
		}
		
		token.setUser(user);
		token.setTokenJobs(tokenJobs);
		token.setCurrentCounter(counter);
		token.setStatus(TokenState.VALID);
		token.setCurrentJob(tokenJobs.get(0));
		token.setCreatedAt(new Date());
		
		Token created = tokenRepository.save(token);
		
		return created;
	}

	@Override
	public void delete(long id) throws TokenNotFoundException {
		
		if(tokenRepository.findById(id).isPresent()) {
			tokenRepository.deleteById(id);
		}
		else
			throw new TokenNotFoundException();
		
	}

	@Override
	public Token get(long id) throws TokenNotFoundException {
		
	    Optional<Token> token = tokenRepository.findById(id);
		
		if(token.isPresent()) {
			return token.get();
		}
		else
			throw new TokenNotFoundException();
	}

	@Override
	public List<Token> getValidTokens() {
		return tokenRepository.findByStatusOrderByCreatedAtAsc(TokenState.VALID);
	}

	@Override
	public void setState(long id, TokenState state) throws TokenNotFoundException {
		
	    Optional<Token> token = tokenRepository.findById(id);
		
		if(token.isPresent()) {
			if(token.get().getStatus() == TokenState.VALID) {
				token.get().setStatus(state);;
				tokenRepository.save(token.get());
			}
			else
				throw new TokenNotFoundException();
		}
		else
			throw new TokenNotFoundException();
	}

	@Override
	public void updateComment(long id, String comment) throws TokenNotFoundException {
		
		Optional<Token> token = tokenRepository.findById(id);
		
		if(token.isPresent()) {
			if(token.get().getStatus() == TokenState.VALID) {
				token.get().setComment(comment);
				tokenRepository.save(token.get());
			}
			else
				throw new TokenNotFoundException();
		}
		else
			throw new TokenNotFoundException();	
	}

	@Override
	public List<Token> getTokensByCounter(Counter counter) {
		
		return tokenRepository.findByCurrentCounterAndStatusOrderByCreatedAtAsc(counter, TokenState.VALID);
	}

	@Override
	public boolean isValid(long id) throws TokenNotFoundException{
		
		Optional<Token> token = tokenRepository.findById(id);
		
		if(token.isPresent()) {
			if(token.get().getStatus() == TokenState.VALID)
				return true;
			else
				return false;
		}
		else
			throw new TokenNotFoundException();
	}

	@Override
	public Token completeCurrentJob(long id) throws TokenNotFoundException{
		
		Optional<Token> optionalToken = tokenRepository.findById(id);
		
		if(optionalToken.isPresent()) {
			
			Token token = optionalToken.get();
			int index = token.getTokenJobs().indexOf(token.getCurrentJob());
			
			if(index < token.getTokenJobs().size()-1) {
				token.setCurrentJob(token.getTokenJobs().get(index+1));	
			}
			else {
				token.setStatus(TokenState.COMPLETED);
			}
			
			return token;
		}
		else
			throw new TokenNotFoundException();
	}

	@Override
	public void updateToken(Token token) {
		
		tokenRepository.save(token);
		
	}

}
