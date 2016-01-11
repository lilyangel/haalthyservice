package com.haalthy.service.controller.Interface.comment;

public class MarkCommentsAsReadByUsernameResponse {
	private int result;
	private String resultDesp;
	private int content;
	
	public int getContent() {
		return content;
	}
	public void setContent(int content) {
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
