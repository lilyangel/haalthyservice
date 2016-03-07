package com.haalthy.service.controller.Interface;

public class GetSuggestUsersByProfileRequest {
	private int beginIndex;
	private int page;
	private int count;
	private String username;
	public int getBeginIndex() {
		return beginIndex;
	}
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
