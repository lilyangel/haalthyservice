package com.haalthy.service.domain;

import java.io.Serializable;

public class ClinicReportFormat implements Serializable{

	private static final long serialVersionUID = 1L;
		private int reportFormatID;
		private String clinicItem;
		private String cancerType;
		private String pathological;
		
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
		public int getReportFormatID() {
			return reportFormatID;
		}
		public void setReportFormatID(int reportFormatID) {
			this.reportFormatID = reportFormatID;
		}
		public String getClinicItem() {
			return clinicItem;
		}
		public void setClinicItem(String clinicItem) {
			this.clinicItem = clinicItem;
		}
		
}
