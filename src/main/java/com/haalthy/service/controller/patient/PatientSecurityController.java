package com.haalthy.service.controller.patient;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haalthy.service.controller.Interface.AddPatientStatusRequest;
import com.haalthy.service.controller.Interface.AddTreatmentsRequest;
import com.haalthy.service.domain.ClinicReport;
import com.haalthy.service.domain.PatientStatus;
import com.haalthy.service.domain.Post;
import com.haalthy.service.domain.Treatment;
import com.haalthy.service.openservice.PatientService;
import com.haalthy.service.openservice.PostService;
import com.haalthy.service.openservice.UserService;
import com.haalthy.service.configuration.*;
import com.haalthy.service.domain.ClinicData;

@Controller
@RequestMapping("/security/patient")

public class PatientSecurityController {
	PostType postType;
//	private Authentication a = SecurityContextHolder.getContext().getAuthentication();
	
	@Autowired
	private transient PatientService patientService;
	
	@Autowired
	private transient PostService postService;
	
	@Autowired
	private transient UserService userService;

	@RequestMapping(value = "/treatment/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {
			"application/json" })
	@ResponseBody
	public int addTreatment(@RequestBody AddTreatmentsRequest addTreatmentsRequest) {
		List<Treatment> treatmentList = addTreatmentsRequest.getTreatments();
		int insertCount = 0;
		int isPosted = 0;
		String postBody = "";
		String highlight = "";
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
//		String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
		Iterator<Treatment> treatmentItr = treatmentList.iterator();
		while (treatmentItr.hasNext()) {
			Treatment treatment = treatmentItr.next();
			treatment.setUsername(addTreatmentsRequest.getInsertUsername());
			insertCount = patientService.insertTreatment(treatment);
			isPosted = treatment.getIsPosted();
			highlight += treatment.getTreatmentName() + " ";
			if(treatmentList.size() > 1){
				postBody += treatment.getTreatmentName()+":"+treatment.getDosage()+" ";
			}else{
				postBody += treatment.getDosage();
			}
		}
		if (isPosted > 0 ) {
			java.util.Date today = new java.util.Date();
			Timestamp now = new java.sql.Timestamp(today.getTime());
			Post post = new Post();
			post.setClosed(0);
			post.setHighlight(highlight);
			post.setBody(postBody);
			post.setCountBookmarks(0);
			post.setCountComments(0);
			post.setCountViews(0);
			post.setDateInserted(now);
			post.setInsertUsername(addTreatmentsRequest.getInsertUsername());
//			post.setType(postType.TREATMENT.getValue());
			post.setType(1);
			post.setIsBroadcast(0);
			post.setIsActive(1);
			postService.addPost(post);
		}
		return insertCount;
	}
	
	@RequestMapping(value = "/treatment/update", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public int updateTreatment(@RequestBody Treatment treatment) {
		return patientService.updateTreatmentById(treatment);
	}
    
    @RequestMapping(value = "/patientStatus/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public int addPatientStatus(@RequestBody AddPatientStatusRequest addPatientStatusRequest){
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
// 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
        java.util.Date today = new java.util.Date();
    	Timestamp now = new java.sql.Timestamp(today.getTime());
    	
    	PatientStatus patientStatus = addPatientStatusRequest.getPatientStatus();
    	
 	    patientStatus.setUsername(addPatientStatusRequest.getInsertUsername());
 	    patientStatus.setStatusDesc(patientStatus.getStatusDesc());
// 	    patientStatus.setClinicReport(clinicReport.getClinicReport());
    	int insertCount = patientService.insertPatientStatus(patientStatus);
    	
    	ClinicReport clinicReport = addPatientStatusRequest.getClinicReport();
    	String[] cliniReprotItem= clinicReport.getClinicReport().split("\\]",-1);
		List<ClinicData> clinicDataList = new ArrayList();

		for (int i = 0; i < cliniReprotItem.length; i++) {
			if (cliniReprotItem[i].length() > 0) {
				if (cliniReprotItem[i].charAt(0) == '[') {
					cliniReprotItem[i] = cliniReprotItem[i].substring(1);
				}
				ClinicData clinicData = new ClinicData();
				String[] clinicItemNameAndValue = cliniReprotItem[i].split("\\:", -1);
				if (clinicItemNameAndValue.length > 1){
					clinicData.setClinicItemName(clinicItemNameAndValue[0]);
					clinicData.setClinicItemValue(Float.valueOf(clinicItemNameAndValue[1]));
					clinicData.setStatusID(patientStatus.getStatusID());
					clinicData.setInsertUsername(addPatientStatusRequest.getInsertUsername());
					clinicData.setInsertDate(addPatientStatusRequest.getPatientStatus().getInsertedDate());
				}
				clinicDataList.add(clinicData);
//				if (clinicItemNameAndValue[0].equals("CEA") && (clinicItemNameAndValue.length > 1)) {
//					System.out.println(clinicItemNameAndValue[1]);
//					clinicReport.setCEA(Float.valueOf(clinicItemNameAndValue[1]));
//				}else{
//					clinicReport.setCEA(-1);
//				}
//				if (clinicItemNameAndValue[0].equals("CT/MRI") && (clinicItemNameAndValue.length > 1)) {
//					System.out.println(clinicItemNameAndValue.length);
//					clinicReport.setCT(clinicItemNameAndValue[1]);
//				}
//				if (clinicItemNameAndValue[0].equals("SCC") && (clinicItemNameAndValue.length > 1)) {
//					clinicReport.setSCC(Float.valueOf(clinicItemNameAndValue[1]));
//				}else{
//					clinicReport.setSCC(-1);
//				}
//				if (clinicItemNameAndValue[0].equals("CYFRA21-1") && (clinicItemNameAndValue.length > 1)) {
//					clinicReport.setCYFRA21(Float.valueOf(clinicItemNameAndValue[1]));
//				}else{
//					clinicReport.setCYFRA21(-1);
//				}
//				if (clinicItemNameAndValue[0].equals("NSE") && (clinicItemNameAndValue.length > 1)) {
//					clinicReport.setNSE(Float.valueOf(clinicItemNameAndValue[1]));
//				}else{
//					clinicReport.setNSE(-1);
//				}
//				if (clinicItemNameAndValue[0].equals("ProGRP") && (clinicItemNameAndValue.length > 1)) {
//					clinicReport.setProGRP(Float.valueOf(clinicItemNameAndValue[1]));
//				}else{
//					clinicReport.setProGRP(-1);
//				}
				
			}
		}
		if (clinicDataList.size() > 0){
			patientService.insertClinicData(clinicDataList);
		}
//    	clinicReport.setUsername(addPatientStatusRequest.getInsertUsername());
//		if (clinicReport.getClinicReport() != "") {
//			int insertClinicReportCount = patientService.insertClinicReport(clinicReport);
//		}
//    	patientStatus.setInsertedDate(now);
    	
    	if(patientStatus.getIsPosted()==1){
    		String[] postHightlightAndBody = patientStatus.getStatusDesc().split("\\:\\:");
//    		String postBodyStr = patientStatus.getStatusDesc() ＋ " " + clinicReport.getClinicReport();
    		Post post = new Post();
    		String postStr = "";
    		post.setHighlight(postHightlightAndBody[0]);
    		post.setClinicReport(patientStatus.getClinicReport());
    		post.setClosed(0);
    		if (postHightlightAndBody.length > 1){
    			postStr = postHightlightAndBody[1];
    		}
    		if((patientStatus.getScanData() != null) && (patientStatus.getScanData() != "")){
    			postStr += "/n" + patientStatus.getScanData();
    		}
    		post.setBody(postStr);
    		post.setPatientStatusID(patientStatus.getStatusID());
    		post.setCountBookmarks(0);
    		post.setCountComments(0);
    		post.setCountViews(0);
    		post.setDateInserted(now);
    		post.setInsertUsername(addPatientStatusRequest.getInsertUsername());
//    		post.setType(postType.PATIENTSTATUS.getValue());
    		post.setType(2);
    		post.setIsBroadcast(0);
    		post.setIsActive(1);
    		postService.addPost(post);
    	}
    	return insertCount;
    }
    
//    @RequestMapping(value = "/clinicreport/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
//    @ResponseBody
//    public int addClinicReport(@RequestBody ClinicReport clinicReport){
//		Authentication a = SecurityContextHolder.getContext().getAuthentication();
// 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
// 	   	clinicReport.setUsername(currentSessionUsername);
// 	   	
// 	   	return 0;
//    }
    
    @RequestMapping(value = "/treatment/delete", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public int deleteTreatmentById(@RequestBody Treatment treatment){
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
 	   	int returnValue = 0;
		if ((treatment.getUsername().equals(currentSessionUsername))
				|| (treatment.getUsername().equals(userService.getUserByEmail(currentSessionUsername).getUsername()))) {
			returnValue = patientService.deleteTreatmentById(treatment.getTreatmentID());
		}
 	   	return returnValue;
    }
}
