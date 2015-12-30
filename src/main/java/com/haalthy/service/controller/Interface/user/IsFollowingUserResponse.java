package com.haalthy.service.controller.Interface.user;

public class IsFollowingUserResponse {
	private int result;
	private String resultDesp;
	private int isFollowing;
	
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
	public int getIsFollowing() {
		return isFollowing;
	}
	public void setIsFollowing(int isFollowing) {
		this.isFollowing = isFollowing;
	}
	
}
