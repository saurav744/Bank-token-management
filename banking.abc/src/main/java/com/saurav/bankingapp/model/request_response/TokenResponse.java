package com.saurav.bankingapp.model.request_response;

import java.util.Date;
import java.util.List;

import com.saurav.bankingapp.model.enums.TokenState;

public class TokenResponse {
	
	private long id;

	private long userId;

	private List<String> serviceRequests;

	private int currentCounter;

	private long currentJobId;
	
	private TokenState status;
	
	private Date createdAt;
	
	String comment;

	public TokenResponse(long id, long userId, List<String> services, int currentCounter, long currentJobId,
			TokenState status, Date createdAt, String comment) {
		this.id = id;
		this.userId = userId;
		this.serviceRequests= services;
		this.currentCounter = currentCounter;
		this.currentJobId = currentJobId;
		this.status = status;
		this.createdAt = createdAt;
		this.comment = comment;
	}
	
}
