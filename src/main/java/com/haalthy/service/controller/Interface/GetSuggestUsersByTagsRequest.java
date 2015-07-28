package com.haalthy.service.controller.Interface;

import java.util.List;

public class GetSuggestUsersByTagsRequest {
	int[] tags;
	int rangeBegin;
	int rangeEnd;

	public int[] getTags() {
		return tags;
	}
	public void setTags(int[] tags) {
		this.tags = tags;
	}
	public int getRangeBegin() {
		return rangeBegin;
	}
	public void setRangeBegin(int rangeBegin) {
		this.rangeBegin = rangeBegin;
	}
	public int getRangeEnd() {
		return rangeEnd;
	}
	public void setRangeEnd(int rangeEnd) {
		this.rangeEnd = rangeEnd;
	}
	
}
