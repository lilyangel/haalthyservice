package com.haalthy.service.controller.Interface.tag;

import java.util.List;

import com.haalthy.service.domain.TagType;

public class GetTagTypesResponse {
	private int result;
	private String resultDesp;
	private List<TagType> content;
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
	public List<TagType> getContent() {
		return content;
	}
	public void setContent(List<TagType> content) {
		this.content = content;
	}
}
