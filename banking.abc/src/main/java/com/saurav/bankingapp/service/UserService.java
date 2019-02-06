package com.saurav.bankingapp.service;

import com.saurav.bankingapp.exceptions.UserNotFoundException;
import com.saurav.bankingapp.model.User;
import com.saurav.bankingapp.model.enums.UserType;

public interface UserService {
	
	public void add(String name, String email, String password, String phone, String address, UserType type);

	public void delete(long id) throws UserNotFoundException;

	public void update(long id, User user) throws UserNotFoundException;
	
	public User get(String phone) throws UserNotFoundException;

	public User getById(long id) throws UserNotFoundException;

	public boolean hasUser(String phone);

	public boolean hasUser(long id);

}
