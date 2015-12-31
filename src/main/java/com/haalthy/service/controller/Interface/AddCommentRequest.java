package com.haalthy.service.controller.Interface;

public class AddCommentRequest {
	private String body;
	private int postID;
	
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
