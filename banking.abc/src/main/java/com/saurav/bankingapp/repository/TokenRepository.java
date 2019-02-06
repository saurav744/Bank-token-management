package com.saurav.bankingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.Token;
import com.saurav.bankingapp.model.enums.TokenState;

public interface TokenRepository extends JpaRepository<Token, Long> {
	
	List<Token> findByStatusOrderByCreatedAtAsc(TokenState status);
	
	List<Token> findByCurrentCounterAndStatusOrderByCreatedAtAsc(Counter counter, TokenState status);
	

}
