package com.haalthy.service.controller.Interface.post;

import java.util.List;

import com.haalthy.service.controller.Interface.ImageInfo;
import com.haalthy.service.domain.Tag;

public class AddPostRequest {

	private String insertUsername;
	private String body;
	private int closed;
	private int isBroadcast;
	private String type;
	private int hasImage;
	private List<Tag> tags;
	private List<String> mentionUsers;
	private List<ImageInfo> imageInfos;
	
	public int getHasImage() {
		return hasImage;
	}
	public void setHasImage(int hasImage) {
		this.hasImage = hasImage;
	}
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
	public List<ImageInfo> getImageInfos() {
		return imageInfos;
	}
	public void setImageInfos(List<ImageInfo> imageInfos) {
		this.imageInfos = imageInfos;
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
