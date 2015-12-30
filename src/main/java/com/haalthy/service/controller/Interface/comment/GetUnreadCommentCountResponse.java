package com.haalthy.service.controller.Interface.comment;

public class GetUnreadCommentCountResponse {
	private int result;
	private String resultDesp;
	private int unreadCommentCount;
	
	public int getUnreadCommentCount() {
		return unreadCommentCount;
	}
	public void setUnreadCommentCount(int unreadCommentCount) {
		this.unreadCommentCount = unreadCommentCount;
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
