package com.haalthy.service.domain;

import java.sql.Timestamp;

public class PatientStatus {
	private static final long serialVersionUID = 8751282105532159742L;

	private int statusID;
	private int treatmentID;
	private String username;
	private String clinicReport;
	private String SymptomDesc;
	private String SideEffect;
	private Timestamp insertedDate;
	private int isPosted;
	
	
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
	public int getTreatmentID() {
		return treatmentID;
	}
	public void setTreatmentID(int treatmentID) {
		this.treatmentID = treatmentID;
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
	public String getSymptomDesc() {
		return SymptomDesc;
	}
	public void setSymptomDesc(String symptomDesc) {
		SymptomDesc = symptomDesc;
	}
	public String getSideEffect() {
		return SideEffect;
	}
	public void setSideEffect(String sideEffect) {
		SideEffect = sideEffect;
	}
	public Timestamp getInsertedDate() {
		return insertedDate;
	}
	public void setInsertedDate(Timestamp insertedDate) {
		this.insertedDate = insertedDate;
	}
	
	
}
