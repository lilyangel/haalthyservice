package com.haalthy.service.openservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haalthy.service.controller.Interface.patient.GetPostedTreatmentListRequest;
import com.haalthy.service.domain.ClinicData;
import com.haalthy.service.domain.ClinicReport;
import com.haalthy.service.domain.ClinicReportFormat;
import com.haalthy.service.domain.PatientStatus;
import com.haalthy.service.domain.PatientStatusFormat;
import com.haalthy.service.domain.Treatment;
import com.haalthy.service.domain.TreatmentContent;
import com.haalthy.service.domain.TreatmentFormat;
import com.haalthy.service.persistence.PatientMapper;

@Service
public class PatientService {
	@Autowired
	private PatientMapper patientMapper;
	
	public List<Treatment> getTreatmentsByUser(String username){
		return patientMapper.getTreatmentsByUser(username);
	}
	
	public List<PatientStatus> getPatientStatusByTreatment(int treatmentID){
		return patientMapper.getPatientStatusByTreatment(treatmentID);
	}
	
	public int insertTreatment(Treatment treatment){
		return patientMapper.insertTreatment(treatment);
	}
	
	public int insertPatientStatus(PatientStatus patientStatus){
		return patientMapper.insertPatientStatus(patientStatus);
	}
	
	public List<TreatmentFormat> getTreatmentFormat(){
		return patientMapper.getTreatmentFormat();
	}
	
	public List<PatientStatusFormat> getPatientStatusFormat(){
		return patientMapper.getPatientStatusFormat();
	}
	
	public List<PatientStatus> getPatientStatusByUser(String username){
		return patientMapper.getPatientStatusByUser(username);
	}
	
	public List<ClinicReportFormat> getClinicReportFormat(){
		return patientMapper.getClinicReportFormat();
	}
	
	public List<Treatment> getPostedTreatmentsByUser(String username){
		return patientMapper.getPostedTreatmentsByUser(username);
	}

	public List<PatientStatus> getPostedPatientStatusByUser(String username){
		return patientMapper.getPostedPatientStatusByUser(username);
	}
	
	public int updateTreatmentById(Treatment treatment){
		return patientMapper.updateTreatmentById(treatment);
	}

	public int deleteTreatmentById(int treatmentId){
		return patientMapper.deleteTreatmentById(treatmentId);
	}
	
	public int insertClinicData(List<ClinicData> clinicData){
		return patientMapper.insertClinicData(clinicData);
	}

	public List<ClinicData> getClinicDataByUsername(String insertUsername){
		return patientMapper.getClinicDataByUsername(insertUsername);
	}
	public List<TreatmentContent> getAllPostedTreatmentList(GetPostedTreatmentListRequest getPostedTreatmentListRequest){
		return patientMapper.getAllPostedTreatmentList(getPostedTreatmentListRequest);
	}

	public int updatePatientImg(String id,String filePath)
	{
		PatientStatus patientStatus = new PatientStatus();
		patientStatus.setStatusID(Integer.parseInt(id));
		//
		patientStatus.setImageURL(filePath);
		return patientMapper.updatePatientImg(patientStatus);
	}

	public int appendPatientImg(String id,String filePath){

		PatientStatus patientStatus = new PatientStatus();
		patientStatus.setStatusID(Integer.parseInt(id));
		//
		patientStatus.setImageURL(filePath);
		return patientMapper.appendPatientImg(patientStatus);
	}
	
	public PatientStatus getPatientStatusById(int statusId){
		return patientMapper.getPatientStatusById(statusId);
	}

}
