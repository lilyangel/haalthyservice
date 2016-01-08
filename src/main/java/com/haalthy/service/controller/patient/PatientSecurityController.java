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

import com.haalthy.service.controller.Interface.ImageInfo;
import com.haalthy.service.controller.Interface.OSSFile;
import com.haalthy.service.controller.Interface.patient.AddPatientStatusRequest;
import com.haalthy.service.controller.Interface.patient.AddPatientStatusResponse;
import com.haalthy.service.controller.Interface.patient.AddTreatmentResponse;
import com.haalthy.service.controller.Interface.patient.AddTreatmentsRequest;
import com.haalthy.service.controller.Interface.patient.DeleteTreatmentByIdResponse;
import com.haalthy.service.controller.Interface.patient.UpdateTreatmentResponse;
import com.haalthy.service.domain.ClinicReport;
import com.haalthy.service.domain.PatientStatus;
import com.haalthy.service.domain.Post;
import com.haalthy.service.domain.Treatment;
import com.haalthy.service.openservice.OssService;
import com.haalthy.service.openservice.PatientService;
import com.haalthy.service.openservice.PostService;
import com.haalthy.service.openservice.UserService;
import com.haalthy.service.configuration.*;
import com.haalthy.service.domain.ClinicData;

@Controller
@RequestMapping("/security/patient")

public class PatientSecurityController {
	PostType postType;
	
	@Autowired
	private transient PatientService patientService;
	
	@Autowired
	private transient PostService postService;
	
	@Autowired
	private transient UserService userService;
	
	@Autowired
	private transient OssService ossService;

	@RequestMapping(value = "/treatment/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {
			"application/json" })
	@ResponseBody
	public AddTreatmentResponse addTreatment(@RequestBody AddTreatmentsRequest addTreatmentsRequest) {
		AddTreatmentResponse addTreatmentResponse = new AddTreatmentResponse();
		try {
			List<Treatment> treatmentList = addTreatmentsRequest.getTreatments();
			int insertCount = 0;
			int isPosted = 0;
			String postBody = "";
			String highlight = "";
			Authentication a = SecurityContextHolder.getContext().getAuthentication();
			Iterator<Treatment> treatmentItr = treatmentList.iterator();
			while (treatmentItr.hasNext()) {
				Treatment treatment = treatmentItr.next();
				treatment.setUsername(addTreatmentsRequest.getInsertUsername());
				insertCount = patientService.insertTreatment(treatment);
				isPosted = treatment.getIsPosted();
				highlight += treatment.getTreatmentName() + " ";
				if (treatmentList.size() > 1) {
					postBody += treatment.getTreatmentName() + ":" + treatment.getDosage() + " ";
				} else {
					postBody += treatment.getDosage();
				}
			}
			if (isPosted > 0) {
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
				// post.setType(postType.TREATMENT.getValue());
				post.setType(1);
				post.setIsBroadcast(0);
				post.setIsActive(1);
				addTreatmentResponse.setContent(postService.addPost(post));
			}
			addTreatmentResponse.setResult(1);
			addTreatmentResponse.setResultDesp("返回成功");
		} catch (Exception e) {
			addTreatmentResponse.setResult(-1);
			addTreatmentResponse.setResultDesp("数据库连接错误");
		}
		return addTreatmentResponse;
	}
	
	@RequestMapping(value = "/treatment/update", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public UpdateTreatmentResponse updateTreatment(@RequestBody Treatment treatment) {
		UpdateTreatmentResponse updateTreatmentResponse = new UpdateTreatmentResponse();
		try{
			updateTreatmentResponse.setContent(patientService.updateTreatmentById(treatment));
			updateTreatmentResponse.setResult(1);
			updateTreatmentResponse.setResultDesp("返回成功");
		}catch(Exception e){
			updateTreatmentResponse.setResult(-1);
			updateTreatmentResponse.setResultDesp("数据库连接错误");	
		}
		return updateTreatmentResponse;
	}
    
    @RequestMapping(value = "/patientStatus/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
	public AddPatientStatusResponse addPatientStatus(@RequestBody AddPatientStatusRequest addPatientStatusRequest) {
		AddPatientStatusResponse addPatientStatusResponse = new AddPatientStatusResponse();
		try {
			Authentication a = SecurityContextHolder.getContext().getAuthentication();
			java.util.Date today = new java.util.Date();
			Timestamp now = new java.sql.Timestamp(today.getTime());

			PatientStatus patientStatus = addPatientStatusRequest.getPatientStatus();

			patientStatus.setUsername(addPatientStatusRequest.getInsertUsername());
			patientStatus.setStatusDesc(patientStatus.getStatusDesc());
			int insertCount = patientService.insertPatientStatus(patientStatus);

			ClinicReport clinicReport = addPatientStatusRequest.getClinicReport();
			String[] cliniReprotItem = clinicReport.getClinicReport().split("\\]", -1);
			List<ClinicData> clinicDataList = new ArrayList();

			for (int i = 0; i < cliniReprotItem.length; i++) {
				if (cliniReprotItem[i].length() > 0) {
					if (cliniReprotItem[i].charAt(0) == '[') {
						cliniReprotItem[i] = cliniReprotItem[i].substring(1);
					}
					ClinicData clinicData = new ClinicData();
					String[] clinicItemNameAndValue = cliniReprotItem[i].split("\\:", -1);
					if (clinicItemNameAndValue.length > 1) {
						clinicData.setClinicItemName(clinicItemNameAndValue[0]);
						clinicData.setClinicItemValue(Float.valueOf(clinicItemNameAndValue[1]));
						clinicData.setStatusID(patientStatus.getStatusID());
						clinicData.setInsertUsername(addPatientStatusRequest.getInsertUsername());
						clinicData.setInsertDate(addPatientStatusRequest.getPatientStatus().getInsertedDate());
					}
					clinicDataList.add(clinicData);
				}
			}
			if (clinicDataList.size() > 0) {
				patientService.insertClinicData(clinicDataList);
			}

			if (patientStatus.getIsPosted() == 1) {
				String[] postHightlightAndBody = patientStatus.getStatusDesc().split("\\:\\:");
				Post post = new Post();
				String postStr = "";
				post.setHighlight(postHightlightAndBody[0]);
				post.setClinicReport(patientStatus.getClinicReport());
				post.setClosed(0);
				if (postHightlightAndBody.length > 1) {
					postStr = postHightlightAndBody[1];
				}
				if ((patientStatus.getScanData() != null) && (patientStatus.getScanData() != "")) {
					postStr += "/n" + patientStatus.getScanData();
				}
				post.setBody(postStr);
				post.setPatientStatusID(patientStatus.getStatusID());
				post.setCountBookmarks(0);
				post.setCountComments(0);
				post.setCountViews(0);
				post.setDateInserted(now);
				post.setInsertUsername(addPatientStatusRequest.getInsertUsername());
				post.setType(2);
				post.setIsBroadcast(0);
				post.setIsActive(1);
				postService.addPost(post);
			}
			List<OSSFile> ossFileList = new ArrayList();
			for(ImageInfo imageInfo: addPatientStatusRequest.getImageInfos()){
				OSSFile ossFile = new OSSFile();
				ossFile.setFileType(imageInfo.getType());
				ossFile.setFunctionType("patient");
				ossFile.setId(String.valueOf(patientStatus.getStatusID()));
				ossFile.setImg(imageInfo.getData());
				ossFile.setModifyType("append");
				ossFileList.add(ossFile);
			}
			ossService.ossUploadFile(ossFileList);
			
			addPatientStatusResponse.setResult(1);
			addPatientStatusResponse.setResultDesp("返回成功");
			addPatientStatusResponse.setContent(patientStatus.getStatusID());
		} catch (Exception e) {
			addPatientStatusResponse.setResult(-1);
			addPatientStatusResponse.setResultDesp("数据库连接错误");
		}
		return addPatientStatusResponse;
	}


    @RequestMapping(value = "/treatment/delete", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
	public DeleteTreatmentByIdResponse deleteTreatmentById(@RequestBody Treatment treatment) {
		DeleteTreatmentByIdResponse deleteTreatmentByIdResponse = new DeleteTreatmentByIdResponse();
		try {
			if (treatment.getTreatmentID() == 0) {
				deleteTreatmentByIdResponse.setResult(-2);
				deleteTreatmentByIdResponse.setResultDesp("treatmentId不能为空");
			} else {
				Authentication a = SecurityContextHolder.getContext().getAuthentication();
				String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest()
						.getAuthorizationParameters().get("username");
				int returnValue = 0;
				if ((treatment.getUsername().equals(currentSessionUsername)) || (treatment.getUsername()
						.equals(userService.getUserByEmail(currentSessionUsername).getUsername()))) {
					returnValue = patientService.deleteTreatmentById(treatment.getTreatmentID());
				}
				deleteTreatmentByIdResponse.setResult(1);
				deleteTreatmentByIdResponse.setResultDesp("返回成功");
			}
		} catch (Exception e) {
			deleteTreatmentByIdResponse.setResult(-1);
			deleteTreatmentByIdResponse.setResultDesp("数据库连接错误");
		}
		return deleteTreatmentByIdResponse;
	}
}
