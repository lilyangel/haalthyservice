package com.haalthy.service.domain;

import java.io.Serializable;

public class PatientStatusFormat implements Serializable{

	private static final long serialVersionUID = 1L;
	private int statusFormatID;
	private String statusName;
	public int getStatusFormatID() {
		return statusFormatID;
	}
	public void setStatusFormatID(int statusFormatID) {
		this.statusFormatID = statusFormatID;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}	
}
