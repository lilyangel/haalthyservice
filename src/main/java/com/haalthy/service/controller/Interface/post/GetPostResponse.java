package com.haalthy.service.controller.Interface.post;

import com.haalthy.service.domain.Post;

public class GetPostResponse {
	private int result;
	private String resultDesp;
	private Post post;
	
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
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
