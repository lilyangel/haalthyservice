package com.haalthy.service.controller.Interface.ClinicTrail;

import java.util.List;

import com.haalthy.service.domain.ClinicTrailInfo;

public class GetCilnicTrailInfoResponse {
	private String resultDesp;
	private int result;
	private List<ClinicTrailInfo> content;
	
	public List<ClinicTrailInfo> getContent() {
		return content;
	}
	public void setContent(List<ClinicTrailInfo> content) {
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
