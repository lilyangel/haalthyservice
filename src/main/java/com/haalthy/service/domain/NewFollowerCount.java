package com.haalthy.service.domain;

import java.io.Serializable;

public class NewFollowerCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7727520528664450221L;
	private String username;
	private int count;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}
