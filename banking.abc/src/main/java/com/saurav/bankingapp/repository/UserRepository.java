package com.saurav.bankingapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saurav.bankingapp.model.User;

/**
 * This class extends JpaRepository interface to perform CRUD operations on User entity
 * 
 * @author Saurav Sharma
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	/**
	 * Returns an Optional User object by given email. 
	 * @param email email of the user
	 * @return Optional object corresponding to given user email
	 */
	Optional<User> findByEmail(String email);
	
	/**
	 * Returns an Optional User object by given phone number
	 * @param phone phone number of the user
	 * @return Optional object corresponding to given user phone number
	 */
	Optional<User> findByPhone(String phone);

}
