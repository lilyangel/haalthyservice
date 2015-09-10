package com.haalthy.service.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class ClinicReport  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9032626704946459751L;
	private String username;
	private String clinicReport;
	private Timestamp dateInserted;
	private int isPosted;
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
	public Timestamp getDateInserted() {
		return dateInserted;
	}
	public void setDateInserted(Timestamp dateInserted) {
		this.dateInserted = dateInserted;
	}
	public int getIsPosted() {
		return isPosted;
	}
	public void setIsPosted(int isPosted) {
		this.isPosted = isPosted;
	}
	
}
