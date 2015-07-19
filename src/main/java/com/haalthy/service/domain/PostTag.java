package com.haalthy.service.domain;

import java.io.Serializable;

public class PostTag implements Serializable{
	private static final long serialVersionUID = 8751282105532159742L;
	private int postID;
	private String tagName;
	private String createTime;
	
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}

	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
}
