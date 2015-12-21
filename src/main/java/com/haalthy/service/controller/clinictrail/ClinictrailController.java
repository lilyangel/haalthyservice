package com.haalthy.service.controller.clinictrail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haalthy.service.domain.ClinicTrailInfo;
import com.haalthy.service.openservice.ClinicTrailService;

@Controller
@RequestMapping("/open/clinictrail")
public class ClinictrailController {
    
	@Autowired
	private transient ClinicTrailService clinicTrailService;
	
    @RequestMapping(value = "/list", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<ClinicTrailInfo> getClinicTrailInfo(@RequestBody ClinicTrailInfo clinicTrailInfo){
    	return clinicTrailService.getClinicTrailInfo(clinicTrailInfo);
    }
    
    @RequestMapping(value = "/drugtype", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<String> getClinicTrailDrugTypes(){
    	return clinicTrailService.getClinicTrailDrugTypes();
    }
    
    @RequestMapping(value = "/subgroup", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<String> getClinicTrailSubGroup(){
    	return clinicTrailService.getClinicTrailSubGroups();
    }
}
