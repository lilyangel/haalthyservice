package com.haalthy.service.controller.Interface.patient;

import java.util.List;

import com.haalthy.service.domain.ClinicData;

public class ClinicDataType {
	private String clinicItemName;
	private List<ClinicData> clinicDataList;

	public String getClinicItemName() {
		return clinicItemName;
	}
	public void setClinicItemName(String clinicItemName) {
		this.clinicItemName = clinicItemName;
	}
	public List<ClinicData> getClinicDataList() {
		return clinicDataList;
	}
	public void setClinicDataList(List<ClinicData> clinicDataList) {
		this.clinicDataList = clinicDataList;
	}
}
