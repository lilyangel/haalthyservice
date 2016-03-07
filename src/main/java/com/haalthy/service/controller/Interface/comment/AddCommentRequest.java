package com.haalthy.service.controller.Interface.comment;

public class AddCommentRequest {
	private String body;
	private int postID;
	private String insertUsername;
	
	public String getInsertUsername() {
		return insertUsername;
	}
	public void setInsertUsername(String insertUsername) {
		this.insertUsername = insertUsername;
	}
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
}
