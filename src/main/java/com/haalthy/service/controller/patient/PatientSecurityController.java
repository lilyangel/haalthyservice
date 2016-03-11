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

import com.haalthy.service.controller.Interface.ContentIntEapsulate;
import com.haalthy.service.controller.Interface.ImageInfo;
import com.haalthy.service.controller.Interface.OSSFile;
import com.haalthy.service.controller.Interface.patient.AddPatientStatusRequest;
import com.haalthy.service.controller.Interface.patient.AddPatientStatusResponse;
import com.haalthy.service.controller.Interface.patient.AddTreatmentResponse;
import com.haalthy.service.controller.Interface.patient.AddTreatmentsRequest;
import com.haalthy.service.controller.Interface.patient.DeleteTreatmentByIdResponse;
import com.haalthy.service.controller.Interface.patient.UpdateTreatmentResponse;
import com.haalthy.service.controller.Interface.post.AddUpdatePostResponse;
import com.haalthy.service.controller.Interface.post.AppendImageRequest;
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
				insertCount += patientService.insertTreatment(treatment);
				isPosted = treatment.getIsPosted();
				highlight += treatment.getTreatmentName() + " ";
				if (treatmentList.size() > 1) {
					postBody += treatment.getTreatmentName() + ":" + treatment.getDosage() + " ";
				} else {
					postBody += treatment.getDosage();
				}
			}
			ContentIntEapsulate contentIntEapsulate = new ContentIntEapsulate();
			contentIntEapsulate.setCount(insertCount);
			addTreatmentResponse.setContent(contentIntEapsulate);
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
				postService.addPost(post);

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
			ContentIntEapsulate contentIntEapsulate = new ContentIntEapsulate();
			contentIntEapsulate.setCount(patientService.updateTreatmentById(treatment));
			updateTreatmentResponse.setContent(contentIntEapsulate);
			updateTreatmentResponse.setResult(1);
			updateTreatmentResponse.setResultDesp("返回成功");
		}catch(Exception e){
			e.printStackTrace();
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
			int insertCount = 0;
			Authentication a = SecurityContextHolder.getContext().getAuthentication();
			java.util.Date today = new java.util.Date();
			Timestamp now = new java.sql.Timestamp(today.getTime());

			PatientStatus patientStatus = addPatientStatusRequest.getPatientStatus();
			if(addPatientStatusRequest.getPatientStatus()!=null){
				patientStatus.setUsername(addPatientStatusRequest.getInsertUsername());
				patientStatus.setHasImage(addPatientStatusRequest.getHasImage());
				insertCount = patientService.insertPatientStatus(patientStatus);
			}

			ClinicReport clinicReport = addPatientStatusRequest.getClinicReport();
			if (clinicReport != null) {
				String clinicReportStr = clinicReport.getClinicReport();
				clinicReportStr.toUpperCase();
				String[] cliniReprotItem = clinicReportStr.split("\\]", -1);
				List<ClinicData> clinicDataList = new ArrayList();

				for (int i = 0; i < cliniReprotItem.length; i++) {
					if (cliniReprotItem[i].length() > 0) {
						if (cliniReprotItem[i].charAt(0) == ' ') {
							cliniReprotItem[i] = cliniReprotItem[i].substring(1);
						}
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
			}
			if ((patientStatus != null) && (patientStatus.getIsPosted() == 1)) {
				String[] postHightlightAndBody = patientStatus.getStatusDesc().split("\\:\\:");
				Post post = new Post();
				String postStr = "";
				post.setHighlight(postHightlightAndBody[0]);
				if(clinicReport != null){
					post.setClinicReport(clinicReport.getClinicReport());
				}
				post.setClosed(0);
				if (postHightlightAndBody.length > 1) {
					postStr = postHightlightAndBody[1];
				}
				if ((patientStatus.getScanData() != null) && (patientStatus.getScanData() != "")) {
					postStr += "/n" + patientStatus.getScanData();
				}
				post.setBody(postStr);
				System.out.println(patientStatus.getStatusID());
				post.setPatientStatusID(patientStatus.getStatusID());
				post.setCountBookmarks(0);
				post.setCountComments(0);
				post.setCountViews(0);
				post.setDateInserted(now);
				post.setInsertUsername(addPatientStatusRequest.getInsertUsername());
				post.setType(2);
				post.setIsBroadcast(0);
				post.setIsActive(1);
				post.setHasImage(addPatientStatusRequest.getHasImage());
				postService.addPost(post);
			}
			List<OSSFile> ossFileList = new ArrayList();
			if (addPatientStatusRequest.getImageInfos() != null) {
				for (ImageInfo imageInfo : addPatientStatusRequest.getImageInfos()) {
					OSSFile ossFile = new OSSFile();
					ossFile.setFileType(imageInfo.getType());
					ossFile.setFunctionType("patient");
					ossFile.setId(String.valueOf(patientStatus.getStatusID()));
					ossFile.setImg(imageInfo.getData());
					ossFile.setModifyType("append");
					ossFileList.add(ossFile);
				}
				ossService.ossUploadFile(ossFileList);
			}
			addPatientStatusResponse.setResult(1);
			addPatientStatusResponse.setResultDesp("返回成功");
			ContentIntEapsulate contentIntEapsulate = new ContentIntEapsulate();
			contentIntEapsulate.setCount(patientStatus.getStatusID());
			addPatientStatusResponse.setContent(contentIntEapsulate);
		} catch (Exception e) {
            e.printStackTrace();
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
				System.out.println(currentSessionUsername);
				System.out.println(treatment.getUsername());
				if ((treatment.getUsername().equals(currentSessionUsername)) || 
						((userService.getUserByEmail(currentSessionUsername) != null) &&
						(treatment.getUsername().equals(userService.getUserByEmail(currentSessionUsername).getUsername()))) ||
						((userService.getUserByPhone(currentSessionUsername) != null)
						 && (treatment.getUsername().equals(userService.getUserByPhone(currentSessionUsername).getUsername())))){
					returnValue = patientService.deleteTreatmentById(treatment.getTreatmentID());
				}
				deleteTreatmentByIdResponse.setResult(1);
				deleteTreatmentByIdResponse.setResultDesp("返回成功");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			deleteTreatmentByIdResponse.setResult(-1);
			deleteTreatmentByIdResponse.setResultDesp("数据库连接错误");
		}
		return deleteTreatmentByIdResponse;
	}
    
    @RequestMapping(value = "/appendimage", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
	public AddUpdatePostResponse appendImage(@RequestBody AppendImageRequest appendImageToPatientStatusRequest){
    	AddUpdatePostResponse addUpdatePostResponse = new AddUpdatePostResponse();
    	ContentIntEapsulate contentIntEapsulate = new ContentIntEapsulate();
    	try{
    		 PatientStatus patientStatus = patientService.getPatientStatusById(appendImageToPatientStatusRequest.getId());
    		if (patientStatus == null) {
    			contentIntEapsulate.setCount(-2);
    			addUpdatePostResponse.setContent(contentIntEapsulate);
    			addUpdatePostResponse.setResult(-2);
    			addUpdatePostResponse.setResultDesp("该statusId不存在");
    		}else{
				OSSFile ossFile = new OSSFile();
				ossFile.setFileType(appendImageToPatientStatusRequest.getImageInfo().getType());
				ossFile.setFunctionType("patient");
				ossFile.setImg(appendImageToPatientStatusRequest.getImageInfo().getData());
				ossFile.setId(String.valueOf(patientStatus.getStatusID()));
				if (patientStatus.getImageURL() == null){
					ossFile.setModifyType("update");
					ossService.ossUploadSingleFile(ossFile, appendImageToPatientStatusRequest.getImageIndex(), "update");
				}else{
					ossFile.setModifyType("append");
					ossService.ossUploadSingleFile(ossFile, appendImageToPatientStatusRequest.getImageIndex(), "append");
				}
    			contentIntEapsulate.setCount(1);
    			addUpdatePostResponse.setContent(contentIntEapsulate);
    			addUpdatePostResponse.setResult(1);
    			addUpdatePostResponse.setResultDesp("插入成功");
    		}
    	}catch(Exception e){
			contentIntEapsulate.setCount(-1);
			addUpdatePostResponse.setContent(contentIntEapsulate);
			addUpdatePostResponse.setResult(-1);
			addUpdatePostResponse.setResultDesp("系统异常");
    	}
    	return addUpdatePostResponse;
	}
    
}
