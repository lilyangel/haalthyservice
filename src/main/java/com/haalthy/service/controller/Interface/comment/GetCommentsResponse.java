package com.haalthy.service.controller.Interface.comment;

import java.util.List;

import com.haalthy.service.domain.Comment;

public class GetCommentsResponse {
	private int result;
	private String resultDesp;
	private List<Comment> content;
	
	public List<Comment> getContent() {
		return content;
	}
	public void setContent(List<Comment> content) {
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
