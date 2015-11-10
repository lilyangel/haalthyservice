package com.haalthy.service.openservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haalthy.service.controller.Interface.GetClinicTrailInfoRequest;
import com.haalthy.service.domain.ClinicTrailInfo;
import com.haalthy.service.persistence.ClinicTrailMapper;

public class ClinicTrailService {
	@Autowired 
	private ClinicTrailMapper clinicTrailMapper;
	
	public List<ClinicTrailInfo> getClinicTrailInfo(ClinicTrailInfo clinicTrailInfo){
		return clinicTrailMapper.getClinicTrailInfo(clinicTrailInfo);
	}
}
