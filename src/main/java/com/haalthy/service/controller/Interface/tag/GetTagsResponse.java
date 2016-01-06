package com.haalthy.service.controller.Interface.tag;

import java.util.List;

import com.haalthy.service.domain.Tag;

public class GetTagsResponse {
	private List<Tag> content;
	private int result;
	private String resultDesp;

	public List<Tag> getContent() {
		return content;
	}
	public void setContent(List<Tag> content) {
		this.content = content;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getResultDesp() {
		return resultDesp;
	}
	public void setResultDesp(String resultDesp) {
		this.resultDesp = resultDesp;
	}
	
}
