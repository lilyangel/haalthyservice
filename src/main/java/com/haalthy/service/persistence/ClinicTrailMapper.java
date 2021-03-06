package com.haalthy.service.persistence;

import java.util.List;

import com.haalthy.service.controller.Interface.ClinicTrail.GetClinicTrailInfoRequest;
import com.haalthy.service.domain.ClinicTrailInfo;

public interface ClinicTrailMapper {
	public List<ClinicTrailInfo> getClinicTrailInfo();
	
	public List<String> getClinicTrailDrugTypes();
	
	public List<String> getSubGroups();
}
