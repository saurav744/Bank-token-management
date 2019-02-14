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

	public TokenResponse() {
	}

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<String> getServiceRequests() {
		return serviceRequests;
	}

	public void setServiceRequests(List<String> serviceRequests) {
		this.serviceRequests = serviceRequests;
	}

	public int getCurrentCounter() {
		return currentCounter;
	}

	public void setCurrentCounter(int currentCounter) {
		this.currentCounter = currentCounter;
	}

	public long getCurrentJobId() {
		return currentJobId;
	}

	public void setCurrentJobId(long currentJobId) {
		this.currentJobId = currentJobId;
	}

	public TokenState getStatus() {
		return status;
	}

	public void setStatus(TokenState status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
}
