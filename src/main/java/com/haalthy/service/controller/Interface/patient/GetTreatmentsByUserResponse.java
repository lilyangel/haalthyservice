package com.haalthy.service.controller.Interface.patient;

import java.util.List;

import com.haalthy.service.domain.Treatment;

public class GetTreatmentsByUserResponse {
	private String resultDesp;
	private int result;
	private List<Treatment> content;
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
	public List<Treatment> getContent() {
		return content;
	}
	public void setContent(List<Treatment> content) {
		this.content = content;
	}
}
