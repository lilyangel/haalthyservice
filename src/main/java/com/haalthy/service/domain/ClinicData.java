package com.haalthy.service.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class ClinicData  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 839721623985548997L;
	
	private int clinicDataID;
	private String clinicItemName;
	private float clinicItemValue;
	private int statusID;
	private Timestamp insertDate;
	private String insertUsername;
	
	public Timestamp getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}
	public String getInsertUsername() {
		return insertUsername;
	}
	public void setInsertUsername(String insertUsername) {
		this.insertUsername = insertUsername;
	}
	public int getClinicDataID() {
		return clinicDataID;
	}
	public void setClinicDataID(int clinicDataID) {
		this.clinicDataID = clinicDataID;
	}
	public int getStatusID() {
		return statusID;
	}
	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}
	public String getClinicItemName() {
		return clinicItemName;
	}
	public void setClinicItemName(String clinicItemName) {
		this.clinicItemName = clinicItemName;
	}
	public float getClinicItemValue() {
		return clinicItemValue;
	}
	public void setClinicItemValue(float clinicItemValue) {
		this.clinicItemValue = clinicItemValue;
	}
	
}
