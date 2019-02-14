package com.saurav.bankingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saurav.bankingapp.model.enums.UserType;
import com.saurav.bankingapp.model.request_response.UserRequest;
import com.saurav.bankingapp.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "API to signup new user")
@RestController
public class SignupController {
		
	@Autowired
	private UserService userService;

	@ApiOperation(value = "Create a new User")
	@PostMapping("/signup")
	public void createUser(
			@ApiParam(value = "User request object to be stored in Db", required = true) @RequestBody UserRequest newUser) {
		UserType type = newUser.getType() == UserType.REGULAR ? UserType.REGULAR : UserType.PREMIUM;
		userService.add(newUser.getName(), newUser.getEmail(), newUser.getPassword(), newUser.getPhone(), newUser.getAddress(), type);
	}

}
