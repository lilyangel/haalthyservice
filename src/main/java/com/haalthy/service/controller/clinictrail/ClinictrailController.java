package com.haalthy.service.controller.clinictrail;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    		getCilnicTrailInfoResponse.setClinicTrailInfoList(clinicTrailService.getClinicTrailInfo(clinicTrailInfo));
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
        	getCilnicTrailDrugTypeResponse.setClinicTrailDrugTypeList(clinicTrailService.getClinicTrailDrugTypes());
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
    		getClinicTrailSubGroupsResponse.setSubGroups(clinicTrailService.getClinicTrailSubGroups());
    		getClinicTrailSubGroupsResponse.setResult(1);
    		getClinicTrailSubGroupsResponse.setResultDesp("返回成功");
    	} catch (Exception e) {
    		getClinicTrailSubGroupsResponse.setResult(-1);
    		getClinicTrailSubGroupsResponse.setResultDesp("数据库返回异常");
    	}
    	return getClinicTrailSubGroupsResponse;
    }
}
