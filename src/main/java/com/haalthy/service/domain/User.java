package com.haalthy.service.domain;

public class User {
	private int UserID;
	private String Name;
	private Byte[] Password;
	
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Byte[] getPassword() {
		return Password;
	}
	public void setPassword(Byte[] password) {
		Password = password;
	}

}
