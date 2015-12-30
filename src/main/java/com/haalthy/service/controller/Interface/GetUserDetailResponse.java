package com.haalthy.service.controller.Interface;

import com.haalthy.service.controller.Interface.user.UserDetail;

public class GetUserDetailResponse {
	private String resultDesp;
	private int result;
	private UserDetail userDetail;

	public UserDetail getUserDetail() {
		return userDetail;
	}
	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
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
