package com.haalthy.service.controller.Interface.patient;

import java.sql.Timestamp;

public class GetPostedTreatmentListRequest {
	private String beginIndexDate;
	private String endIndexDate;
	public String getBeginIndexDate() {
		return beginIndexDate;
	}
	public void setBeginIndexDate(String beginIndexDate) {
		this.beginIndexDate = beginIndexDate;
	}
	public String getEndIndexDate() {
		return endIndexDate;
	}
	public void setEndIndexDate(String endIndexDate) {
		this.endIndexDate = endIndexDate;
	}

}
