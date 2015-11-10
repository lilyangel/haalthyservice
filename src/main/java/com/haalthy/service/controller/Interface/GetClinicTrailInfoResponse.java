package com.haalthy.service.controller.Interface;

public class GetClinicTrailInfoResponse {
	private String drugType;
	private String drugName;
	private String subGroup;
	private String stage;
	private String effect;
	private String researchInfo;
	private String sideEffect;
	private String original;
	public String getDrugType() {
		return drugType;
	}
	public void setDrugType(String drugType) {
		this.drugType = drugType;
	}
	public String getDrugName() {
		return drugName;
	}
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	public String getSubGroup() {
		return subGroup;
	}
	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	public String getResearchInfo() {
		return researchInfo;
	}
	public void setResearchInfo(String researchInfo) {
		this.researchInfo = researchInfo;
	}
	public String getSideEffect() {
		return sideEffect;
	}
	public void setSideEffect(String sideEffect) {
		this.sideEffect = sideEffect;
	}
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
}
