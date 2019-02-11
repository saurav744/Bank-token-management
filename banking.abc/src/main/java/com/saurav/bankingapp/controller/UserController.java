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
import com.saurav.bankingapp.model.request_response.UserRequest;
import com.saurav.bankingapp.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@Api(value = "User Management APIs")
public class UserController {
	
	@Autowired
	private UserService userService;

	@ApiOperation(value = "View a User by id", response = User.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved User"),
			@ApiResponse(code = 401, message = "You are not authorized to view this resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping("/users/id/{id}")
	public User getUser(
			@ApiParam(value = "Unique Id of user", required = true) @PathVariable Long id) throws UserNotFoundException {
		return userService.getById(id);
	}

	
	@ApiOperation(value = "View a User by Phone number", response = User.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved User"),
			@ApiResponse(code = 401, message = "You are not authorized to view this resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})   
	@GetMapping("/users/phone/{phone}")
	public User getUser(
			@ApiParam(value = "Phone number of the user", required = true) @PathVariable String phone) throws UserNotFoundException {	
		return userService.get(phone);
	}
	
	@ApiOperation(value = "Add a User")
	@PostMapping("/users")
	public void createUser(
			 @ApiParam(value = "User request object to be stored in Db", required = true) @RequestBody UserRequest newUserRequest) {
		userService.add(newUserRequest.getName(), newUserRequest.getEmail(), newUserRequest.getPassword(), newUserRequest.getPhone(), newUserRequest.getAddress(), newUserRequest.getType());
	}
		
	@ApiOperation(value = "Delete a user")
	@DeleteMapping("/users/{id}")
	public void deleteUser(
			@ApiParam(value = "Unique Id of the user to be deleted", required = true) @PathVariable Long id) throws UserNotFoundException {
		userService.delete(id);
	}

}
