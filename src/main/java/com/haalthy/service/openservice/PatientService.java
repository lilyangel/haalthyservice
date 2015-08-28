package com.haalthy.service.openservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haalthy.service.domain.PatientStatus;
import com.haalthy.service.domain.PatientStatusFormat;
import com.haalthy.service.domain.Treatment;
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
}
