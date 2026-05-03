package com.atlas.authservice2.service.dto;

public class LoginResponseDto {

	private Long userId;
	private String firstName;
	private String lastName;
	private String userName;
	private String token;
	private String contactNember;
	private String password;

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getContactNember() {
		return contactNember;
	}

	public void setContactNember(String contactNember) {
		this.contactNember = contactNember;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

}
