package com.haalthy.service.persistence;

import java.util.List;

import com.haalthy.service.domain.ClinicReport;
import com.haalthy.service.domain.ClinicReportFormat;
import com.haalthy.service.domain.PatientStatus;
import com.haalthy.service.domain.PatientStatusFormat;
import com.haalthy.service.domain.Treatment;
import com.haalthy.service.domain.TreatmentFormat;
import org.apache.ibatis.annotations.Param;

public interface PatientMapper {
	List<Treatment> getTreatmentsByUser(String username);
	
	List<PatientStatus> getPatientStatusByTreatment(int treatmentID);
	
	int insertTreatment(Treatment treatment);
	
	int insertPatientStatus(PatientStatus patientStatus);
	
	List<TreatmentFormat> getTreatmentFormat();
	
	List<PatientStatusFormat> getPatientStatusFormat();
	
	List<PatientStatus> getPatientStatusByUser(String username);
	
	List<ClinicReportFormat> getClinicReportFormat();
	
	int insertClinicReport(ClinicReport clinicReport);
	
	List<ClinicReport> getClinicReportByUser(String username);
	
	List<Treatment> getPostedTreatmentsByUser(String username);

	List<PatientStatus> getPostedPatientStatusByUser(String username);
	
	List<ClinicReport> getPostedClinicReportByUser(String username);
	
	int updateTreatmentById(Treatment treatment);
	
	int deleteTreatmentById(int treatmentId);


	int updatePatientImg(@Param(value = "statusID") int statusId, @Param(value = "filename") String fileName);
	int appendPatientImg(@Param(value = "statusID") int statusId, @Param(value = "filename") String fileName);
}
