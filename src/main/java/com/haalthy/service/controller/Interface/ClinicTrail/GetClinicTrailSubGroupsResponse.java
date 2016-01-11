package com.haalthy.service.controller.Interface.ClinicTrail;

import java.util.List;

public class GetClinicTrailSubGroupsResponse {
	private List<String> content;
	private String resultDesp;
	private int result;

	public List<String> getContent() {
		return content;
	}
	public void setContent(List<String> content) {
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
