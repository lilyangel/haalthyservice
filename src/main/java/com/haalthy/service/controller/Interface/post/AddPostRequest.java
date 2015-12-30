package com.haalthy.service.controller.Interface.post;

import java.util.List;

import com.haalthy.service.domain.Tag;

public class AddPostRequest {

	private String insertUsername;
	private String body;
	private int closed;
	private int isBroadcast;
	private String type;
	private List<Tag> tags;
	private List<String> mentionUsers;
	private List<byte[]> images;
	
	public String getInsertUsername() {
		return insertUsername;
	}
	public void setInsertUsername(String insertUsername) {
		this.insertUsername = insertUsername;
	}
	public List<String> getMentionUsers() {
		return mentionUsers;
	}
	public void setMentionUsers(List<String> mentionUsers) {
		this.mentionUsers = mentionUsers;
	}
	public List<byte[]> getImages() {
		return images;
	}
	public void setImages(List<byte[]> images) {
		this.images = images;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
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
}
