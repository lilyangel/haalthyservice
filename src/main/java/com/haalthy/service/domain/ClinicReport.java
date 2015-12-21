package com.haalthy.service.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class ClinicReport  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9032626704946459751L;
	private String username;
	private String clinicReport;
	private float CEA;
	private String CT;
	private float SCC;
	private float CYFRA21;
	private float NSE;
	private float ProGRP;
	
	public float getSCC() {
		return SCC;
	}
	public void setSCC(float sCC) {
		SCC = sCC;
	}
	public float getCYFRA21() {
		return CYFRA21;
	}
	public void setCYFRA21(float cYFRA21) {
		CYFRA21 = cYFRA21;
	}
	public float getNSE() {
		return NSE;
	}
	public void setNSE(float nSE) {
		NSE = nSE;
	}
	public float getProGRP() {
		return ProGRP;
	}
	public void setProGRP(float proGRP) {
		ProGRP = proGRP;
	}
	public float getCEA() {
		return CEA;
	}
	public void setCEA(float cEA) {
		CEA = cEA;
	}
	public String getCT() {
		return CT;
	}
	public void setCT(String cT) {
		CT = cT;
	}
	private Timestamp dateInserted;
	private int isPosted;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getClinicReport() {
		return clinicReport;
	}
	public void setClinicReport(String clinicReport) {
		this.clinicReport = clinicReport;
	}
	public Timestamp getDateInserted() {
		return dateInserted;
	}
	public void setDateInserted(Timestamp dateInserted) {
		this.dateInserted = dateInserted;
	}
	public int getIsPosted() {
		return isPosted;
	}
	public void setIsPosted(int isPosted) {
		this.isPosted = isPosted;
	}
	
}
