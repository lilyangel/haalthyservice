package com.haalthy.service.controller.Interface.ClinicTrail;

import java.util.List;

import com.haalthy.service.controller.Interface.ContentStringsEapsulate;
 
public class GetClinicTrailDrugTypeResponse {
	private String resultDesp;
	private int result;
	private ContentStringsEapsulate content;
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
	public ContentStringsEapsulate getContent() {
		return content;
	}
	public void setContent(ContentStringsEapsulate content) {
		this.content = content;
	}
	
}
