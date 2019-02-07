package com.saurav.bankingapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Job {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
    @ManyToOne
    @JoinColumn(name = "service_id")
	private BankService service;
	
	@NotNull
    @ManyToOne
    @JoinColumn(name = "token_id")
	private Token token;

	public Job() {

	}

	public Job(BankService bankService, Token token) {
		super();
		this.service = bankService;
		this.token = token;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BankService getService() {
		return service;
	}

	public void setService(BankService bankService) {
		this.service = bankService;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
