package com.haalthy.service.controller.Interface;

public class GetUsersResponse {
	private String Email;
	private String Username;
	private String Displayname;
	private String CreateDate;
	
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
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}
	
}
