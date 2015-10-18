package com.haalthy.service.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Comment  implements Serializable{
	private static final long serialVersionUID = 8751282105532159742L;
	private int commentID;
	private int postID;
	private String insertUsername;
	private String body;
	private int countBookmarks;
	private Timestamp dateInserted;
	private int isActive;
	private byte[] image;
	
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
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

	public Timestamp getDateInserted() {
		return dateInserted;
	}
	public void setDateInserted(Timestamp dateInserted) {
		this.dateInserted = dateInserted;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public String getInsertUsername() {
		return insertUsername;
	}
	public void setInsertUsername(String insertUsername) {
		this.insertUsername = insertUsername;
	}
	
}
