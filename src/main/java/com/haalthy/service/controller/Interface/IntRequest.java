package com.haalthy.service.controller.Interface;

import java.sql.Timestamp;

public class IntRequest {
	private int id;
	private Timestamp begin;
	private Timestamp end;
	
	public Timestamp getBegin() {
		return begin;
	}

	public void setBegin(Timestamp begin) {
		this.begin = begin;
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
