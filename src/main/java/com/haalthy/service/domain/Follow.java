package com.haalthy.service.domain;

import java.io.Serializable;

public class Follow  implements Serializable{
	private static final long serialVersionUID = 8751282105532159742L;
	public int followId;
	private String username;
	private String followingUser;
	private int isActive;
	private String dateInserted;
	private String dateUpdated;
	
	public int getFollowId() {
		return followId;
	}
	public void setFollowId(int followId) {
		this.followId = followId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFollowingUser() {
		return followingUser;
	}
	public void setFollowingUser(String followingUser) {
		this.followingUser = followingUser;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public String getDateInserted() {
		return dateInserted;
	}
	public void setDateInserted(String dateInserted) {
		this.dateInserted = dateInserted;
	}
	public String getDateUpdated() {
		return dateUpdated;
	}
	public void setDateUpdated(String dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

}
