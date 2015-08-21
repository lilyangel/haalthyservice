package com.haalthy.service.controller.patient;

import java.sql.Timestamp;

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
    @RequestMapping(value = "/treatment", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public int addTreatment(@RequestBody Treatment treatment){
    	int insertCount = patientService.insertTreatment(treatment);
    	if(treatment.getIsPosted() == 1){
            java.util.Date today = new java.util.Date();
        	Timestamp now = new java.sql.Timestamp(today.getTime());
     	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
     	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
    		Post post = new Post();
    		post.setClosed(0);
    		post.setBody(treatment.getName()+treatment.getDosage());
    		post.setCountBookmarks(0);
    		post.setCountComments(0);
    		post.setCountViews(0);
    		post.setDateInserted(now);
    		post.setInsertUsername(currentSessionUsername);
    		post.setType(postType.TREATMENT.getValue());
    		post.setIsBroadcast(0);
    		post.setIsActive(1);
    		post.setTreatmentID(treatment.getTreatmentID());
    		postService.addPost(post);
    	}
    	return insertCount;
    }
    
    @RequestMapping(value = "/patientStatus", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public int addPatientStatus(@RequestBody PatientStatus patientStatus){
    	int insertCount = patientService.insertPatientStatus(patientStatus);
    	if(patientStatus.getIsPosted()==1){
            java.util.Date today = new java.util.Date();
        	Timestamp now = new java.sql.Timestamp(today.getTime());
     	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
     	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
    		Post post = new Post();
    		post.setClosed(0);
    		post.setBody(patientStatus.getClinicReport()+"*"+patientStatus.getSideEffect()+"*"+patientStatus.getSymptomDesc());
    		post.setPatientStatusID(patientStatus.getStatusID());
    		post.setCountBookmarks(0);
    		post.setCountComments(0);
    		post.setCountViews(0);
    		post.setDateInserted(now);
    		post.setInsertUsername(currentSessionUsername);
    		post.setType(postType.PATIENTSTATUS.getValue());
    		post.setIsBroadcast(0);
    		post.setIsActive(1);
    		postService.addPost(post);
    	}
    	return insertCount;
    }
    
}
