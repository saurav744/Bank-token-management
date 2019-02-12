package com.saurav.bankingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saurav.bankingapp.model.BankService;
/**
 * This class extends JpaRepository interface to perform CRUD operations on BanksService entity
 * 
 * @author Saurav Sharma
 *
 */
public interface BankServiceRepository extends JpaRepository<BankService, Long> {
	/**
	 * Returns a BankService object from DB with given name
	 * @param name name of the service
	 * @return BankService object corresponding to given service name
	 */
	BankService findByName(String name);

}
