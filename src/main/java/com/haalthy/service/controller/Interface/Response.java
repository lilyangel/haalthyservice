package com.haalthy.service.controller.Interface;

public class Response {
	private String resultDesp;
	private int result;
	private Object content;
	public String getResultDesp() {
		return resultDesp;
	}
	public void setResultDesp(String resultDesp) {
		this.resultDesp = resultDesp;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
}
