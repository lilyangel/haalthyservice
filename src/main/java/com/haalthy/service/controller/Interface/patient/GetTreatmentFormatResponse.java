package com.haalthy.service.controller.Interface.patient;

import java.util.List;

import com.haalthy.service.domain.TreatmentFormat;

public class GetTreatmentFormatResponse {
	private int result;
	private String resultDesp;
	private List<TreatmentFormat> treatmentFormats;
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
	public List<TreatmentFormat> getTreatmentFormats() {
		return treatmentFormats;
	}
	public void setTreatmentFormats(List<TreatmentFormat> treatmentFormats) {
		this.treatmentFormats = treatmentFormats;
	}
}
