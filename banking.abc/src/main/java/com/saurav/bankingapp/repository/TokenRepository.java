package com.saurav.bankingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.Token;
import com.saurav.bankingapp.model.enums.TokenState;

/**
 * 
 * This class extends JpaRepository interface to perform CRUD operations on Token entity
 * 
 * @author Saurav Sharma
 *
 */
public interface TokenRepository extends JpaRepository<Token, Long> {
	/**
	 * Returns a list of tokens with given status and order by date of creation
	 * @param status status of the tokens
	 * @return list of token with given status in order of creation date
	 */
	List<Token> findByStatusOrderByCreatedAtAsc(TokenState status);
	
	/**
	 * Returns a list of tokens with given status assigned to a given counter order by creation date
	 * @param counter counter assigned to tokens
	 * @param status status of the tokens required
	 * @return list of tokens assigned to counter and given status order by date
	 */
	List<Token> findByCurrentCounterAndStatusOrderByCreatedAtAsc(Counter counter, TokenState status);
	
}
