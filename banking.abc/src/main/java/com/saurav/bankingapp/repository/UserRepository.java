package com.saurav.bankingapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saurav.bankingapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByPhone(String phone);

}
