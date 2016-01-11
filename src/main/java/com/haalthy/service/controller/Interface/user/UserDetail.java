package com.haalthy.service.controller.Interface.user;

import java.util.List;

import com.haalthy.service.controller.Interface.patient.ClinicDataType;
import com.haalthy.service.domain.PatientStatus;
import com.haalthy.service.domain.Treatment;
import com.haalthy.service.domain.User;

public class UserDetail {
	private User userProfile;
	private List<Treatment> treatments;
	private List<PatientStatus> patientStatus;
	private List<ClinicDataType> clinicReport;
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
	public List<ClinicDataType> getClinicReport() {
		return clinicReport;
	}
	public void setClinicReport(List<ClinicDataType> clinicReport) {
		this.clinicReport = clinicReport;
	}
	
}
