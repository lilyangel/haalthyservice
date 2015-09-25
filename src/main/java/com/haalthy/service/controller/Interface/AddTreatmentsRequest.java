package com.haalthy.service.controller.Interface;

import java.util.List;

import com.haalthy.service.domain.Treatment;

public class AddTreatmentsRequest {
	private List<Treatment> treatments;

	public List<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}
	
}
