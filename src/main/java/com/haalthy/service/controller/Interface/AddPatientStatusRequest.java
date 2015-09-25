package com.haalthy.service.controller.Interface;

import com.haalthy.service.domain.ClinicReport;
import com.haalthy.service.domain.PatientStatus;

public class AddPatientStatusRequest {
	private PatientStatus patientStatus;
	private ClinicReport clinicReport;
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
