package com.haalthy.service.controller.Interface;

public class SearchRequest {
	private String searchString;
	private int since_id;
	private int max_id;
	private int page;
	private int count;
	private int beginIndex;

	public int getSince_id() {
		return since_id;
	}

	public void setSince_id(int since_id) {
		this.since_id = since_id;
	}

	public int getMax_id() {
		return max_id;
	}

	public void setMax_id(int max_id) {
		this.max_id = max_id;
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

	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
}
