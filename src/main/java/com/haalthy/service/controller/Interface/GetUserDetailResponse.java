package com.haalthy.service.controller.Interface;

import java.util.List;

import com.haalthy.service.domain.ClinicReport;
import com.haalthy.service.domain.PatientStatus;
import com.haalthy.service.domain.Treatment;
import com.haalthy.service.domain.User;

public class GetUserDetailResponse {
	private User userProfile;
	private List<Treatment> treatments;
	private List<PatientStatus> patientStatus;
	private List<ClinicReport> clinicReport;
	
	public User getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(User userProfile) {
		this.userProfile = userProfile;
	}
	public List<Treatment> getTreatments() {
		return treatments;
	}
	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}
	public List<PatientStatus> getPatientStatus() {
		return patientStatus;
	}
	public void setPatientStatus(List<PatientStatus> patientStatus) {
		this.patientStatus = patientStatus;
	}
	public List<ClinicReport> getClinicReport() {
		return clinicReport;
	}
	public void setClinicReport(List<ClinicReport> clinicReport) {
		this.clinicReport = clinicReport;
	}
	
}
