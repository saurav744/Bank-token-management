package com.saurav.bankingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saurav.bankingapp.model.BankService;

public interface BankServiceRepository extends JpaRepository<BankService, Long> {
	
	BankService findByName(String name);

}
