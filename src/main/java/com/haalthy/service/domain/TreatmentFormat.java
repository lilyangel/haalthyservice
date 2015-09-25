package com.haalthy.service.domain;
import java.io.Serializable;

public class TreatmentFormat implements Serializable {
	private static final long serialVersionUID = 8751282105532159742L;
	private int treatmentFormatID;
	private String name;
	private String type;
	
	public int getTreatmentFormatID() {
		return treatmentFormatID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setTreatmentFormatID(int treatmentFormatID) {
		this.treatmentFormatID = treatmentFormatID;
	}
	public String getTreatmentName() {
		return name;
	}
	public void setTreatmentName(String treatmentName) {
		this.name = treatmentName;
	}
}
