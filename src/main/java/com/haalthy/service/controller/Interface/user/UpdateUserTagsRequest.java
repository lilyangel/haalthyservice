package com.haalthy.service.controller.Interface.user;

import java.util.List;

import com.haalthy.service.domain.Tag;

public class UpdateUserTagsRequest {
	private List<Tag> tags;
	private String username;
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
