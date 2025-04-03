package com.qa.users;

public class User {

	public String name;
	public String job;
	public String id;
	public String createdAt;

	public User() {
		
	}
	
	public User(String name, String job) {
		this.name = name;
		this.job = job;
	}

	// getters and setters method
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

}
