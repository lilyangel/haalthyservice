package com.haalthy.service.controller.Interface.post;

import java.util.List;

import com.haalthy.service.domain.Post;

public class GetPostsResponse {
	private int result;
	private String resultDesp;
	private List<Post> content;
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
	public List<Post> getContent() {
		return content;
	}
	public void setContent(List<Post> content) {
		this.content = content;
	}
}
