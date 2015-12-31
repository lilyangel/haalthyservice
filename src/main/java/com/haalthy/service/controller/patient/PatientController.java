package com.haalthy.service.controller.patient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haalthy.service.controller.Interface.TreatmentWithPatientStatus;
import com.haalthy.service.domain.ClinicReportFormat;
import com.haalthy.service.domain.PatientStatus;
import com.haalthy.service.domain.PatientStatusFormat;
import com.haalthy.service.domain.Treatment;
import com.haalthy.service.domain.TreatmentFormat;
import com.haalthy.service.openservice.PatientService;

import com.haalthy.service.configuration.*;

import com.haalthy.service.domain.ClinicReportFormat;

@Controller
@RequestMapping("/open/patient")
public class PatientController {
	@Autowired
	private transient PatientService patientService;
	
    @RequestMapping(value = "/treatments/{username}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<Treatment> getTreatmentsByUser(@PathVariable String username){
    	return patientService.getTreatmentsByUser(username);
    }
    
    @RequestMapping(value = "/patientstatus/{treatmentID}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<PatientStatus> getPatientStatusByTreatment(@PathVariable int treatmentID){
    	return patientService.getPatientStatusByTreatment(treatmentID);
    }
    
    @RequestMapping(value = "/patientinfomation/username", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<TreatmentWithPatientStatus> getPatientInfoByUser(@PathVariable String username){
    	List<TreatmentWithPatientStatus> treatmentWithStatusList = new ArrayList<TreatmentWithPatientStatus>();
    	List<Treatment> treatmentList = patientService.getTreatmentsByUser(username);
    	Iterator<Treatment> treatmentItr = treatmentList.iterator();
    	while(treatmentItr.hasNext()){
    		Treatment treatment = treatmentItr.next();
    		TreatmentWithPatientStatus treatmentWithPatientStatus = new TreatmentWithPatientStatus();
    		treatmentWithPatientStatus.setTreatment(treatment);
    		treatmentWithPatientStatus.setPatientStatusList(patientService.getPatientStatusByTreatment(treatment.getTreatmentID()));
    		treatmentWithStatusList.add(treatmentWithPatientStatus);
    	}
    	return treatmentWithStatusList;
    }
    
    @RequestMapping(value = "/treatmentformat", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<TreatmentFormat> getTreatmentFormat(){
    	return patientService.getTreatmentFormat();
    }
    
    @RequestMapping(value = "/clinicreportformat", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<ClinicReportFormat> getClinicReportFormat(){
    	return patientService.getClinicReportFormat();
    }
    
    @RequestMapping(value = "/patientstatusformat", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<PatientStatusFormat> getPatientStatusFormat(){
    	return patientService.getPatientStatusFormat();
    }
}
