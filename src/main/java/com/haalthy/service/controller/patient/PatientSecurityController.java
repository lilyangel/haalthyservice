package com.haalthy.service.controller.patient;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haalthy.service.configuration.*;
import com.haalthy.service.controller.Interface.AddTreatmentsRequest;
import com.haalthy.service.domain.PatientStatus;
import com.haalthy.service.domain.Post;
import com.haalthy.service.domain.Treatment;
import com.haalthy.service.openservice.PatientService;
import com.haalthy.service.openservice.PostService;

@Controller
@RequestMapping("/security/patient")

public class PatientSecurityController {
	PostType postType;
	
	@Autowired
	private transient PatientService patientService;
	
	@Autowired
	private transient PostService postService;

	@RequestMapping(value = "/treatment/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {
			"application/json" })
	@ResponseBody
	public int addTreatment(@RequestBody AddTreatmentsRequest addTreatmentsRequest) {
		List<Treatment> treatmentList = addTreatmentsRequest.getTreatments();
		int insertCount = 0;
		int isPosted = 0;
		String postBody = "";
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest()
				.getAuthorizationParameters().get("username");
		Iterator<Treatment> treatmentItr = treatmentList.iterator();
		while (treatmentItr.hasNext()) {
			Treatment treatment = treatmentItr.next();
			treatment.setUsername(currentSessionUsername);
			insertCount = patientService.insertTreatment(treatment);
			isPosted = treatment.getIsPosted();
			postBody += treatment.getTreatmentName()+"*"+treatment.getDosage()+"**";
		}
		if (isPosted > 0 ) {
			java.util.Date today = new java.util.Date();
			Timestamp now = new java.sql.Timestamp(today.getTime());
			Post post = new Post();
			post.setClosed(0);
			post.setBody(postBody);
			post.setCountBookmarks(0);
			post.setCountComments(0);
			post.setCountViews(0);
			post.setDateInserted(now);
			post.setInsertUsername(currentSessionUsername);
//			post.setType(postType.TREATMENT.getValue());
			post.setType(1);
			post.setIsBroadcast(0);
			post.setIsActive(1);
			postService.addPost(post);
		}
		return insertCount;
	}
    
    @RequestMapping(value = "/patientStatus/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public int addPatientStatus(@RequestBody PatientStatus patientStatus){
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
 	    patientStatus.setUsername(currentSessionUsername);
    	int insertCount = patientService.insertPatientStatus(patientStatus);
    	if(patientStatus.getIsPosted()==1){
            java.util.Date today = new java.util.Date();
        	Timestamp now = new java.sql.Timestamp(today.getTime());
    		Post post = new Post();
    		post.setClosed(0);
    		post.setBody(patientStatus.getStatusDesc());
    		post.setPatientStatusID(patientStatus.getStatusID());
    		post.setCountBookmarks(0);
    		post.setCountComments(0);
    		post.setCountViews(0);
    		post.setDateInserted(now);
    		post.setInsertUsername(currentSessionUsername);
//    		post.setType(postType.PATIENTSTATUS.getValue());
    		post.setType(2);
    		post.setIsBroadcast(0);
    		post.setIsActive(1);
    		postService.addPost(post);
    	}
    	return insertCount;
    }
    
}
