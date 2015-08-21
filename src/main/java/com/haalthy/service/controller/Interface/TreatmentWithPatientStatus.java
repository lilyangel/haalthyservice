package com.haalthy.service.controller.Interface;

import java.util.List;

import com.haalthy.service.domain.PatientStatus;
import com.haalthy.service.domain.Treatment;

public class TreatmentWithPatientStatus {
	private Treatment treatment;
	private List<PatientStatus> patientStatusList;
	public Treatment getTreatment() {
		return treatment;
	}
	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}
	public List<PatientStatus> getPatientStatusList() {
		return patientStatusList;
	}
	public void setPatientStatusList(List<PatientStatus> patientStatusList) {
		this.patientStatusList = patientStatusList;
	}
	
}
