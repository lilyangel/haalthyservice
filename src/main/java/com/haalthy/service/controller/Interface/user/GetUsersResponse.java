package com.haalthy.service.controller.Interface.user;

import java.util.List;

import com.haalthy.service.domain.User;

public class GetUsersResponse {
	private int result;
	private String resultDesp;
	private List<User> content;
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
	public List<User> getContent() {
		return content;
	}
	public void setContent(List<User> content) {
		this.content = content;
	}
}
