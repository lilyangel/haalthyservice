package com.haalthy.service.controller.Interface.post;

import java.sql.Timestamp;
import java.util.List;

import com.haalthy.service.domain.Tag;

public class GetPostsByTagsRequest {
	private List<Tag> tags;
//	private Timestamp begin;
//	private Timestamp end;
	private int since_id;
	private int max_id;
	private int page;
	private int count;
	private int beginIndex;
	
	public int getBeginIndex() {
		return beginIndex;
	}
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
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
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
//	public Timestamp getBegin() {
//		return begin;
//	}
//	public void setBegin(Timestamp begin) {
//		this.begin = begin;
//	}
//	public Timestamp getEnd() {
//		return end;
//	}
//	public void setEnd(Timestamp end) {
//		this.end = end;
//	}
}
