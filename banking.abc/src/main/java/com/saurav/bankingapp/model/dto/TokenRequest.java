package com.saurav.bankingapp.model.dto;

import java.util.List;

public class TokenRequest {
	
	private String phone;
	
	private List<String> serviceNames;
	
	public TokenRequest(String phone, List<String> serviceNames) {
		this.phone = phone;
		this.serviceNames = serviceNames;
	}
	
	public TokenRequest() {	
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<String> getServiceNames() {
		return serviceNames;
	}

	public void setServiceNames(List<String> serviceNames) {
		this.serviceNames = serviceNames;
	}	

}
