package com.haalthy.service.controller.Interface.ClinicTrail;

import java.util.List;

public class GetClinicTrailSubGroupsResponse {
	private List<String> subGroups;
	private String resultDesp;
	private int result;
	public List<String> getSubGroups() {
		return subGroups;
	}
	public void setSubGroups(List<String> subGroups) {
		this.subGroups = subGroups;
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
