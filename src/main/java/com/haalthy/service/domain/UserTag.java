package com.haalthy.service.domain;

import java.sql.Timestamp;

public class UserTag {
	private static final long serialVersionUID = 8751282105532159742L;
	private String username;
	private int tagID;
	private Timestamp dateInserted;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getTagID() {
		return tagID;
	}
	public void setTagID(int tagID) {
		this.tagID = tagID;
	}
	public Timestamp getDateInserted() {
		return dateInserted;
	}
	public void setDateInserted(Timestamp dateInserted) {
		this.dateInserted = dateInserted;
	}
	
}
