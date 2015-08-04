package com.haalthy.service.domain;

import java.io.Serializable;
//import java.sql.Date;
import java.sql.Timestamp;

public class Post implements Serializable{
	private static final long serialVersionUID = 8751282105532159742L;
	private int postID;
	private String type;
	private String insertUsername;
	private String body;
	private String tags;
	private int countComments;
	private int countBookmarks;
	private int countViews;
	private int closed;
	private int isBroadcast;
	private Timestamp dateInserted;
	private Timestamp dateUpdated;
	private int isActive;
	private byte[] image;
	
	
	public String getInsertUsername() {
		return insertUsername;
	}
	public void setInsertUsername(String insertUsername) {
		this.insertUsername = insertUsername;
	}
	public Timestamp getDateUpdated() {
		return dateUpdated;
	}
	public void setDateUpdated(Timestamp dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	public Timestamp getDateInserted() {
		return dateInserted;
	}
	public void setDateInserted(Timestamp dateInserted) {
		this.dateInserted = dateInserted;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBody() {
		return this.body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTags() {
		return this.tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public int getCountComments() {
		return this.countComments;
	}
	public void setCountComments(int countComments) {
		this.countComments = countComments;
	}
	public int getCountBookmarks() {
		return this.countBookmarks;
	}
	public void setCountBookmarks(int countBookmarks) {
		this.countBookmarks = countBookmarks;
	}
	public int getCountViews() {
		return this.countViews;
	}
	public void setCountViews(int countViews) {
		this.countViews = countViews;
	}
	public int getClosed() {
		return this.closed;
	}
	public void setClosed(int closed) {
		this.closed = closed;
	}
	public int getIsBroadcast() {
		return this.isBroadcast;
	}
	public void setIsBroadcast(int isBroadcast) {
		this.isBroadcast = isBroadcast;
	}
}
