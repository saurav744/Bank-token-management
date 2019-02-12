package com.saurav.bankingapp.service;

import java.util.List;

import com.saurav.bankingapp.exceptions.TokenNotFoundException;
import com.saurav.bankingapp.model.BankService;
import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.Token;
import com.saurav.bankingapp.model.User;
import com.saurav.bankingapp.model.enums.TokenState;

/**
 * 
 * @author Saurav sharma
 *
 */
public interface TokenService {
	
	/**
	 * Creates a new token based on services selected by user
	 * @param user user requesting for the token
	 * @param counter counter number assigned to token
	 * @param services list of services linked to the token
	 * @return newly created token object
	 */
	public Token create(User user, Counter counter, List<BankService> services);
	
	/**
	 * Deletes a token with given id
	 * @param id unique id of token
	 * @throws TokenNotFoundException
	 */
	public void delete(long id) throws TokenNotFoundException;
	
	/**
	 * Returns a token with given id
	 * @param id id if the token
	 * @return token object with given id
	 * @throws TokenNotFoundException
	 */
	public Token get(long id) throws TokenNotFoundException;
	
	/**
	 * Returns all valid tokens
	 * @return list of token objects
	 */
	public List<Token> getValidTokens();
	
	/**
	 * Checks if given token is valid
	 * @param id id of the token
	 * @return true if token is valid else false
	 * @throws TokenNotFoundException
	 */
	public boolean isValid(long id) throws TokenNotFoundException;
	
	/**
	 * Updates the status of the token
	 * @param id id of the token to be updated
	 * @param state new status to be updated
	 * @throws TokenNotFoundException
	 */
	public void setState(long id, TokenState state) throws TokenNotFoundException;
	
	/**
	 * Updates the comment on the token
	 * @param id id of the token
	 * @param comment updated comment string
	 * @throws TokenNotFoundException
	 */
	public void updateComment(long id, String comment) throws TokenNotFoundException;
	
	/**
	 * Completes the current job of the token. Marks the token as completed if all jobs are complete.
	 * @param id id of the token
	 * @return the token with given id
	 * @throws TokenNotFoundException
	 */
	public Token completeCurrentJob(long id) throws TokenNotFoundException;
	
	/**
	 * Returns all tokens assigned to a given counter
	 * @param counter counter object 
	 * @return list of tokens of a particular counter
	 */
	public List<Token> getTokensByCounter(Counter counter);
	
	/**
	 * Updates a token
	 * @param token token object having info to be updated
	 */
	public void updateToken(Token token);

}
