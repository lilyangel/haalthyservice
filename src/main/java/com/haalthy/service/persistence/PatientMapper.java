package com.haalthy.service.persistence;

import java.util.List;

import com.haalthy.service.domain.PatientStatus;
import com.haalthy.service.domain.PatientStatusFormat;
import com.haalthy.service.domain.Treatment;
import com.haalthy.service.domain.TreatmentFormat;

public interface PatientMapper {
	List<Treatment> getTreatmentsByUser(String username);
	
	List<PatientStatus> getPatientStatusByTreatment(int treatmentID);
	
	int insertTreatment(Treatment treatment);
	
	int insertPatientStatus(PatientStatus patientStatus);
	
	List<TreatmentFormat> getTreatmentFormat();
	
	List<PatientStatusFormat> getPatientStatusFormat();
}
