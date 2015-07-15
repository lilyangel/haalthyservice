package com.haalthy.service.domain;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 8751282105532159742L;

	private int UserID;
	private String Email;
	private String Username;
	private String Displayname;
	private String Password;
	private String CreateDate;
	private String UpdateDate;
	
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
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
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
	public String getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}
	public String getUpdateDate() {
		return UpdateDate;
	}
	public void setUpdateDate(String updateDate) {
		UpdateDate = updateDate;
	}
}
