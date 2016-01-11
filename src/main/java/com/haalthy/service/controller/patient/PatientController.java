package com.haalthy.service.controller.patient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haalthy.service.controller.Interface.InputUsernameRequest;
import com.haalthy.service.controller.Interface.patient.GetClinicReportFormatResponse;
import com.haalthy.service.controller.Interface.patient.GetPatientstatusFormatResponse;
import com.haalthy.service.controller.Interface.patient.GetTreatmentFormatResponse;
import com.haalthy.service.controller.Interface.patient.GetTreatmentsByUserResponse;

import com.haalthy.service.domain.ClinicReportFormat;
import com.haalthy.service.domain.PatientStatus;
import com.haalthy.service.domain.PatientStatusFormat;
import com.haalthy.service.domain.Treatment;
import com.haalthy.service.domain.TreatmentFormat;
import com.haalthy.service.domain.User;
import com.haalthy.service.openservice.PatientService;
import com.haalthy.service.openservice.UserService;
import com.haalthy.service.configuration.*;

import com.haalthy.service.domain.ClinicReportFormat;

@Controller
@RequestMapping("/open/patient")
public class PatientController {
	@Autowired
	private transient PatientService patientService;
	@Autowired
	private transient UserService userService;
    @RequestMapping(value = "/treatments", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public GetTreatmentsByUserResponse getTreatmentsByUser(@RequestBody InputUsernameRequest username){
    	GetTreatmentsByUserResponse getTreatmentsByUserResponse = new GetTreatmentsByUserResponse();
    	try{
    		getTreatmentsByUserResponse.setResult(1);
    		getTreatmentsByUserResponse.setResultDesp("返回成功");
    		getTreatmentsByUserResponse.setContent(patientService.getTreatmentsByUser(username.getUsername()));
    	}catch(Exception e){
    		getTreatmentsByUserResponse.setResult(-1);
    		getTreatmentsByUserResponse.setResultDesp("数据库连接错误");
    	}
    	return getTreatmentsByUserResponse;
    }
    
    @RequestMapping(value = "/treatmentformat", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public GetTreatmentFormatResponse getTreatmentFormat(){
    	GetTreatmentFormatResponse getTreatmentFormatResponse = new GetTreatmentFormatResponse();
    	try{
    		getTreatmentFormatResponse.setResult(1);
    		getTreatmentFormatResponse.setResultDesp("返回成功");
    		getTreatmentFormatResponse.setContent((patientService.getTreatmentFormat()));
    	}catch(Exception e){
    		getTreatmentFormatResponse.setResult(-1);
    		getTreatmentFormatResponse.setResultDesp("数据库连接错误");
    	}
    	return getTreatmentFormatResponse;
    }
    
    @RequestMapping(value = "/clinicreportformat", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public GetClinicReportFormatResponse getClinicReportFormat(@RequestBody InputUsernameRequest inputUsernameRequest){
		GetClinicReportFormatResponse getClinicReportFormatResponse = new GetClinicReportFormatResponse();
		try {
			List<ClinicReportFormat> clinicReportFormatList = patientService.getClinicReportFormat();
			if ((inputUsernameRequest.getUsername() == null) || (inputUsernameRequest.getUsername() == "")) {
				getClinicReportFormatResponse.setResult(-2);
				getClinicReportFormatResponse.setResultDesp("用户名不能为空");
			} else {
				User user = userService.getUserByUsername(inputUsernameRequest.getUsername());
				Iterator<ClinicReportFormat> clinicReportFormatItr = clinicReportFormatList.iterator();
				List<ClinicReportFormat> newClinicReprotFormatList = new ArrayList();
				while (clinicReportFormatItr.hasNext()) {
					ClinicReportFormat clinicReportFormat = clinicReportFormatItr.next();
					if (user.getCancerType().equals(clinicReportFormat.getCancerType()) == false) {
						clinicReportFormatList.remove(clinicReportFormat);
					} else {
						String pathologicalStr = clinicReportFormat.getPathological();
						if (pathologicalStr != null) {
							String[] pathologicalList = pathologicalStr.split(" ");
							for (int i = 0; i < pathologicalList.length; i++) {
								if (pathologicalList[i].equals(user.getPathological()) == true) {
									newClinicReprotFormatList.add(clinicReportFormat);
									break;
								}
							}
						}
					}
				}
				getClinicReportFormatResponse.setResult(1);
				getClinicReportFormatResponse.setResultDesp("返回成功");
				getClinicReportFormatResponse.setContent(newClinicReprotFormatList);
			}
		} catch (Exception e) {
			getClinicReportFormatResponse.setResult(-1);
			getClinicReportFormatResponse.setResultDesp("数据库连接错误");
		}
    	return getClinicReportFormatResponse;
    }
    
    @RequestMapping(value = "/patientstatusformat", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public GetPatientstatusFormatResponse getPatientStatusFormat(){
    	GetPatientstatusFormatResponse getPatientstatusFormatResponse = new GetPatientstatusFormatResponse();
    	try{
    		getPatientstatusFormatResponse.setContent(patientService.getPatientStatusFormat());
    		getPatientstatusFormatResponse.setResult(1);
    		getPatientstatusFormatResponse.setResultDesp("返回成功");
    	}catch(Exception e){
    		getPatientstatusFormatResponse.setResult(-1);
    		getPatientstatusFormatResponse.setResultDesp("数据库连接错误");
    	}
    	return getPatientstatusFormatResponse;
    }
}
