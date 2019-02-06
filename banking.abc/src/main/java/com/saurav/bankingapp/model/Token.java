package com.saurav.bankingapp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;

import com.saurav.bankingapp.model.enums.TokenState;

@Entity
public class Token {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName="id")
	private User user;

	@OrderColumn(name = "job_sequence")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "token")
	private List<Job> tokenJobs;

	@ManyToOne
	@JoinColumn(name = "current_counter_id", referencedColumnName="id")
	private Counter currentCounter;

	@ManyToOne
	@JoinColumn(name = "current_job_id", referencedColumnName="id")
	private Job currentJob;
	
	private TokenState status;
	
	private Date createdAt;
	
	String comment;

	public Token() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Job> getTokenJobs() {
		return tokenJobs;
	}

	public void setTokenJobs(List<Job> tokenJobs) {
		this.tokenJobs = tokenJobs;
	}

	public Counter getCurrentCounter() {
		return currentCounter;
	}

	public void setCurrentCounter(Counter currentCounter) {
		this.currentCounter = currentCounter;
	}

	public Job getCurrentJob() {
		return currentJob;
	}

	public void setCurrentJob(Job currentJob) {
		this.currentJob = currentJob;
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
