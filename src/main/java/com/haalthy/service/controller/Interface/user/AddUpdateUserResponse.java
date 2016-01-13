package com.haalthy.service.controller.Interface.user;

import com.haalthy.service.controller.Interface.ContentStringEapsulate;

public class AddUpdateUserResponse {
	private int result;
	private String resultDesp;
	private ContentStringEapsulate content;
	
	public ContentStringEapsulate getContent() {
		return content;
	}
	public void setContent(ContentStringEapsulate content) {
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
