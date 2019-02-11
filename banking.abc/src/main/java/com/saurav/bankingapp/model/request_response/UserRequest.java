package com.saurav.bankingapp.model.request_response;

import com.saurav.bankingapp.model.enums.UserType;

public class UserRequest {
	
	private String name;
	private String email;
	private String password;
	private String phone;
	private String address;
	private UserType type;
	
	public UserRequest(String name, String email, String password, String phone, String address, UserType type) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.type = type;
	}

	public UserRequest() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}
	

}
