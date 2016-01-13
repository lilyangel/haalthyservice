package com.haalthy.service.controller.clinictrail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haalthy.service.controller.Interface.ContentStringsEapsulate;
import com.haalthy.service.controller.Interface.ClinicTrail.GetCilnicTrailInfoResponse;
import com.haalthy.service.controller.Interface.ClinicTrail.GetClinicTrailDrugTypeResponse;
import com.haalthy.service.controller.Interface.ClinicTrail.GetClinicTrailSubGroupsResponse;
import com.haalthy.service.domain.ClinicTrailInfo;
import com.haalthy.service.openservice.ClinicTrailService;

@Controller
@RequestMapping("/open/clinictrail")
public class ClinictrailController {
    
	@Autowired
	private transient ClinicTrailService clinicTrailService;
	
    @RequestMapping(value = "/list", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public GetCilnicTrailInfoResponse getClinicTrailInfo(@RequestBody ClinicTrailInfo clinicTrailInfo)  {
    	GetCilnicTrailInfoResponse getCilnicTrailInfoResponse = new GetCilnicTrailInfoResponse();
    	try{
    		getCilnicTrailInfoResponse.setContent(clinicTrailService.getClinicTrailInfo(clinicTrailInfo));
    		getCilnicTrailInfoResponse.setResult(1);
    		getCilnicTrailInfoResponse.setResultDesp("返回成功");
    	} catch (Exception e) {
    		getCilnicTrailInfoResponse.setResult(-1);
    		getCilnicTrailInfoResponse.setResultDesp("数据库返回异常");
		}
    	return getCilnicTrailInfoResponse;
    }
    
    @RequestMapping(value = "/drugtype", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public GetClinicTrailDrugTypeResponse getClinicTrailDrugTypes(){
    	GetClinicTrailDrugTypeResponse getCilnicTrailDrugTypeResponse = new GetClinicTrailDrugTypeResponse();
    	try{
    		ContentStringsEapsulate clinicTrailDrugTypes = new ContentStringsEapsulate();
    		clinicTrailDrugTypes.setResults(clinicTrailService.getClinicTrailDrugTypes());
        	getCilnicTrailDrugTypeResponse.setContent(clinicTrailDrugTypes);
        	getCilnicTrailDrugTypeResponse.setResult(1);
        	getCilnicTrailDrugTypeResponse.setResultDesp("返回成功");
    	} catch (Exception e) {
    		getCilnicTrailDrugTypeResponse.setResult(-1);
    		getCilnicTrailDrugTypeResponse.setResultDesp("数据库返回异常");
		}
    	return getCilnicTrailDrugTypeResponse;
    }
    
    @RequestMapping(value = "/subgroup", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public GetClinicTrailSubGroupsResponse getClinicTrailSubGroup(){
    	GetClinicTrailSubGroupsResponse getClinicTrailSubGroupsResponse = new GetClinicTrailSubGroupsResponse();
    	try{
    		ContentStringsEapsulate clinicTrailSubgroups = new ContentStringsEapsulate();
    		clinicTrailSubgroups.setResults(clinicTrailService.getClinicTrailSubGroups());
    		getClinicTrailSubGroupsResponse.setContent(clinicTrailSubgroups);
    		getClinicTrailSubGroupsResponse.setResult(1);
    		getClinicTrailSubGroupsResponse.setResultDesp("返回成功");
    	} catch (Exception e) {
    		getClinicTrailSubGroupsResponse.setResult(-1);
    		getClinicTrailSubGroupsResponse.setResultDesp("数据库返回异常");
    	}
    	return getClinicTrailSubGroupsResponse;
    }
}
