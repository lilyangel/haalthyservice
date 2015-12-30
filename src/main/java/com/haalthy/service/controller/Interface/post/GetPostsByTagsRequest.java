package com.haalthy.service.controller.Interface.post;

import java.sql.Timestamp;
import java.util.List;

import com.haalthy.service.domain.Tag;

public class GetPostsByTagsRequest {
	private List<Tag> tags;
	private Timestamp begin;
	private Timestamp end;
	private int count;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public Timestamp getBegin() {
		return begin;
	}
	public void setBegin(Timestamp begin) {
		this.begin = begin;
	}
	public Timestamp getEnd() {
		return end;
	}
	public void setEnd(Timestamp end) {
		this.end = end;
	}
}
