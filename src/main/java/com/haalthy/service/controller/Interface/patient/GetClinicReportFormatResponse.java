package com.haalthy.service.controller.Interface.patient;

import java.util.List;

import com.haalthy.service.domain.ClinicReportFormat;

public class GetClinicReportFormatResponse {
	private int result;
	private String resultDesp;
	private List<ClinicReportFormat> clinicReportFormats;
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
	public List<ClinicReportFormat> getClinicReportFormats() {
		return clinicReportFormats;
	}
	public void setClinicReportFormats(List<ClinicReportFormat> clinicReportFormats) {
		this.clinicReportFormats = clinicReportFormats;
	}
	
}
