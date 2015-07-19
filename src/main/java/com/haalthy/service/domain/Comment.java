package com.haalthy.service.domain;

import java.io.Serializable;

public class Comment  implements Serializable{
	private static final long serialVersionUID = 8751282105532159742L;
	private int commentID;
	private int postID;
	private String insertUserName;
	private String body;
	private int countBookmarks;
	private String dateInserted;
	private int isActive;
	public int getCommentID() {
		return commentID;
	}
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
	public String getInsertUserName() {
		return insertUserName;
	}
	public void setInsertUserName(String insertUserName) {
		this.insertUserName = insertUserName;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public int getCountBookmarks() {
		return countBookmarks;
	}
	public void setCountBookmarks(int countBookmarks) {
		this.countBookmarks = countBookmarks;
	}
	public String getDateInserted() {
		return dateInserted;
	}
	public void setDateInserted(String dateInserted) {
		this.dateInserted = dateInserted;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	
}
