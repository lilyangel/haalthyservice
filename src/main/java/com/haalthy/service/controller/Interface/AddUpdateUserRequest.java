package com.haalthy.service.controller.Interface;

public class AddUpdateUserRequest {
	private String Email;
	private String Username;
	private String Displayname;
	private String Password;
	
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getDisplayname() {
		return Displayname;
	}
	public void setDisplayname(String displayname) {
		Displayname = displayname;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
}
