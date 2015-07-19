package com.haalthy.service.controller.Interface;

import java.util.List;

public class AddPostRequest {

	private String body;
	private int closed;
	private int isBroadcast;
	private List<String> tags;
	
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
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
