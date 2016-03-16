package com.haalthy.service.controller.Interface;

import java.util.List;

import com.haalthy.service.domain.Tag;

public class GetSuggestUsersByTagsRequest {
	List<Tag> tags;
	int page;
	int count;
	int beginIndex;

	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
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
}
