package com.haalthy.service.domain;

import java.io.Serializable;

public class Mention implements Serializable{
	private static final long serialVersionUID = 970367395902725168L;
	private int mentionID;
	private int postID;
	private String username;
	private int isUnRead;
	
	public int getIsUnRead() {
		return isUnRead;
	}
	public void setIsUnRead(int isUnRead) {
		this.isUnRead = isUnRead;
	}
	public int getMentionID() {
		return mentionID;
	}
	public void setMentionID(int mentionID) {
		this.mentionID = mentionID;
	}
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
