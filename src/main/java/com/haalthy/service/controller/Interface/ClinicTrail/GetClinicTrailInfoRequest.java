package com.haalthy.service.controller.Interface.ClinicTrail;

public class GetClinicTrailInfoRequest {
	private String drugType;
	private String subGroup;
	public String getDrugType() {
		return drugType;
	}
	public void setDrugType(String drugType) {
		this.drugType = drugType;
	}
	public String getSubGroup() {
		return subGroup;
	}
	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}
}
