package com.haalthy.service.controller.Interface.user;

import com.haalthy.service.domain.NewFollowerCount;

public class NewFollowerCountResponse {
	private int result;
	private String resultDesp;
	private NewFollowerCount newFollowerCount;
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
	public NewFollowerCount getNewFollowerCount() {
		return newFollowerCount;
	}
	public void setNewFollowerCount(NewFollowerCount newFollowerCount) {
		this.newFollowerCount = newFollowerCount;
	}
	
}
