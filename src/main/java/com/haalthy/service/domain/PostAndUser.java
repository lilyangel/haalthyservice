package com.haalthy.service.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import com.haalthy.service.configuration.PostType;

public class PostAndUser implements Serializable{
	private static final long serialVersionUID = 4L;
	private int postID;
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
	private int type;
	private int treatmentID;
	private int patientStatusID;
	private String gender;
	private int isSmoking;
	private String pathological;
	private int age;
	private String role;
	private String cancerType;
	private String metastasis;
	private int stage;

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
	public String getInsertUsername() {
		return insertUsername;
	}
	public void setInsertUsername(String insertUsername) {
		this.insertUsername = insertUsername;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public int getCountComments() {
		return countComments;
	}
	public void setCountComments(int countComments) {
		this.countComments = countComments;
	}
	public int getCountBookmarks() {
		return countBookmarks;
	}
	public void setCountBookmarks(int countBookmarks) {
		this.countBookmarks = countBookmarks;
	}
	public int getCountViews() {
		return countViews;
	}
	public void setCountViews(int countViews) {
		this.countViews = countViews;
	}
	public int getClosed() {
		return closed;
	}
	public void setClosed(int closed) {
		this.closed = closed;
	}
	public int getIsBroadcast() {
		return isBroadcast;
	}
	public void setIsBroadcast(int isBroadcast) {
		this.isBroadcast = isBroadcast;
	}
	public Timestamp getDateInserted() {
		return dateInserted;
	}
	public void setDateInserted(Timestamp dateInserted) {
		this.dateInserted = dateInserted;
	}
	public Timestamp getDateUpdated() {
		return dateUpdated;
	}
	public void setDateUpdated(Timestamp dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public int getTreatmentID() {
		return treatmentID;
	}
	public void setTreatmentID(int treatmentID) {
		this.treatmentID = treatmentID;
	}
	public int getPatientStatusID() {
		return patientStatusID;
	}
	public void setPatientStatusID(int patientStatusID) {
		this.patientStatusID = patientStatusID;
	}
}
