package com.haalthy.service.openservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haalthy.service.controller.Interface.ClinicTrail.GetClinicTrailInfoRequest;
import com.haalthy.service.domain.ClinicTrailInfo;
import com.haalthy.service.persistence.ClinicTrailMapper;

public class ClinicTrailService {
	@Autowired 
	private ClinicTrailMapper clinicTrailMapper;
	
	public List<ClinicTrailInfo> getClinicTrailInfo(){
		return clinicTrailMapper.getClinicTrailInfo();
	}
	public List<String> getClinicTrailDrugTypes(){
		return clinicTrailMapper.getClinicTrailDrugTypes();
	}
	
	public List<String> getClinicTrailSubGroups(){
		return clinicTrailMapper.getSubGroups();
	}
}
