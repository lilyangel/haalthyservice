package com.haalthy.service.controller.Interface.patient;

import java.util.List;

import com.haalthy.service.controller.Interface.ImageInfo;
import com.haalthy.service.domain.ClinicReport;
import com.haalthy.service.domain.PatientStatus;

public class AddPatientStatusRequest {
	private PatientStatus patientStatus;
	private ClinicReport clinicReport;
	private String insertUsername;
	private List<ImageInfo> imageInfos;
	private int hasImage;
	
	
	public int getHasImage() {
		return hasImage;
	}
	public void setHasImage(int hasImage) {
		this.hasImage = hasImage;
	}
	public List<ImageInfo> getImageInfos() {
		return imageInfos;
	}
	public void setImageInfos(List<ImageInfo> imageInfos) {
		this.imageInfos = imageInfos;
	}
	public String getInsertUsername() {
		return insertUsername;
	}
	public void setInsertUsername(String insertUsername) {
		this.insertUsername = insertUsername;
	}
	public PatientStatus getPatientStatus() {
		return patientStatus;
	}
	public void setPatientStatus(PatientStatus patientStatus) {
		this.patientStatus = patientStatus;
	}
	public ClinicReport getClinicReport() {
		return clinicReport;
	}
	public void setClinicReport(ClinicReport clinicReport) {
		this.clinicReport = clinicReport;
	}
}
