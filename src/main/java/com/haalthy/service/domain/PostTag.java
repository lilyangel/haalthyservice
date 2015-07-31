package com.haalthy.service.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class PostTag implements Serializable{
	private static final long serialVersionUID = 8751282105532159742L;
	private int postID;
	private int tagId;
	private Timestamp createTime;
	
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public int getTagId() {
		return tagId;
	}
	public void setTagId(int tagId) {
		this.tagId = tagId;
	}
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
}
