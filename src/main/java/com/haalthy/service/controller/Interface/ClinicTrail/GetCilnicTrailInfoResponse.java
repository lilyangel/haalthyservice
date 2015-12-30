package com.haalthy.service.controller.Interface.ClinicTrail;

import java.util.List;

import com.haalthy.service.domain.ClinicTrailInfo;

public class GetCilnicTrailInfoResponse {
	private String resultDesp;
	private int result;
	private List<ClinicTrailInfo> clinicTrailInfoList;
	
	public List<ClinicTrailInfo> getClinicTrailInfoList() {
		return clinicTrailInfoList;
	}
	public void setClinicTrailInfoList(List<ClinicTrailInfo> clinicTrailInfoList){
		this.clinicTrailInfoList = clinicTrailInfoList;
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
