package com.haalthy.service.controller.Interface.patient;

import java.util.List;

import com.haalthy.service.domain.TreatmentFormat;

public class GetTreatmentFormatResponse {
	private int result;
	private String resultDesp;
	private List<TreatmentFormat> content;
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
	public List<TreatmentFormat> getContent() {
		return content;
	}
	public void setContent(List<TreatmentFormat> content) {
		this.content = content;
	}
}
