package com.haalthy.service.controller.Interface.ClinicTrail;

import java.util.List;

public class GetClinicTrailDrugTypeResponse {
	private String resultDesp;
	private int result;
	private List<String> clinicTrailDrugTypeList;
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
	public List<String> getClinicTrailDrugTypeList() {
		return clinicTrailDrugTypeList;
	}
	public void setClinicTrailDrugTypeList(List<String> clinicTrailDrugTypeList) {
		this.clinicTrailDrugTypeList = clinicTrailDrugTypeList;
	}

}
