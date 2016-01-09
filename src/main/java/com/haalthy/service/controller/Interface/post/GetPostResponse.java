package com.haalthy.service.controller.Interface.post;

import com.haalthy.service.domain.Post;

public class GetPostResponse {
	private int result;
	private String resultDesp;
	private Post content;
	
	public Post getContent() {
		return content;
	}
	public void setContent(Post content) {
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
