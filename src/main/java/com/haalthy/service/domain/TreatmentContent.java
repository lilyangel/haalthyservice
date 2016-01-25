package com.haalthy.service.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class TreatmentContent  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private int age;
	private int stage;
	private String cancerType;
	private String pathological;
	private String metastasis;
	private String geneticMutation;
	private String treatmentName;
	private String dosage;
	private Timestamp beginDate;
	private Timestamp endDate;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
	public String getCancerType() {
		return cancerType;
	}
	public void setCancerType(String cancerType) {
		this.cancerType = cancerType;
	}
	public String getPathological() {
		return pathological;
	}
	public void setPathological(String pathological) {
		this.pathological = pathological;
	}
	public String getMetastasis() {
		return metastasis;
	}
	public void setMetastasis(String metastasis) {
		this.metastasis = metastasis;
	}
	public String getGeneticMutation() {
		return geneticMutation;
	}
	public void setGeneticMutation(String geneticMutation) {
		this.geneticMutation = geneticMutation;
	}
	public String getTreatmentName() {
		return treatmentName;
	}
	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName;
	}
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	public Timestamp getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Timestamp beginDate) {
		this.beginDate = beginDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	
}
