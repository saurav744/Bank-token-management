package com.saurav.bankingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saurav.bankingapp.exceptions.UserNotFoundException;
import com.saurav.bankingapp.model.User;
import com.saurav.bankingapp.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users/id/{id}")
	public User getUser(@PathVariable Long id) throws UserNotFoundException {
		return userService.getById(id);
	}
	
	@GetMapping("/users/phone/{phone}")
	public User getUser(@PathVariable String phone) throws UserNotFoundException {	
		return userService.get(phone);
	}
	
	@PostMapping("/users")
	public void createUser(@RequestBody User newUser) {
		userService.add(newUser.getName(), newUser.getEmail(), newUser.getPassword(), newUser.getPhone(), newUser.getAddress(), newUser.getType());
	}
		
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable Long id) throws UserNotFoundException {
		userService.delete(id);
	}

}
