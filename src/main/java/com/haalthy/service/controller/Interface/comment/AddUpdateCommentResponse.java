package com.haalthy.service.controller.Interface.comment;

import com.haalthy.service.controller.Interface.ContentIntEapsulate;

public class AddUpdateCommentResponse {
	private String resultDesp;
	private int result;
	private ContentIntEapsulate content;
	
	public ContentIntEapsulate getContent() {
		return content;
	}
	public void setContent(ContentIntEapsulate content) {
		this.content = content;
	}
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
	
}
