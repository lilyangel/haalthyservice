package com.haalthy.service.controller.Interface.ClinicTrail;

import java.util.List;

public class GetClinicTrailDrugTypeResponse {
	private String resultDesp;
	private int result;
	private List<String> content;
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
	public List<String> getContent() {
		return content;
	}
	public void setContent(List<String> content) {
		this.content = content;
	}
}
