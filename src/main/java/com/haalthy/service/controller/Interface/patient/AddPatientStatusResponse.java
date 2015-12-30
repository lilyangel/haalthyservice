package com.haalthy.service.controller.Interface.patient;

public class AddPatientStatusResponse {
	private int result;
	private String resultDesp;
	private int patientStatusId;
	
	public int getPatientStatusId() {
		return patientStatusId;
	}
	public void setPatientStatusId(int patientStatusId) {
		this.patientStatusId = patientStatusId;
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
