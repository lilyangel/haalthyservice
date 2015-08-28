package com.haalthy.service.domain;

import java.sql.Timestamp;
import java.io.Serializable;

public class PatientStatus {
	private static final long serialVersionUID = 8751282105532159742L;

	private int statusID;
	private String username;
	private String clinicReport;
	private String statusDesc;
	private Timestamp insertedDate;
	private int isPosted;
	
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public int getIsPosted() {
		return isPosted;
	}
	public void setIsPosted(int isPosted) {
		this.isPosted = isPosted;
	}
	public int getStatusID() {
		return statusID;
	}
	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getClinicReport() {
		return clinicReport;
	}
	public void setClinicReport(String clinicReport) {
		this.clinicReport = clinicReport;
	}
	public Timestamp getInsertedDate() {
		return insertedDate;
	}
	public void setInsertedDate(Timestamp insertedDate) {
		this.insertedDate = insertedDate;
	}
	
	
}
