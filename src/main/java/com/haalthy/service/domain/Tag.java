package com.haalthy.service.domain;

import java.io.Serializable;

public class Tag implements Serializable{
	private static final long serialVersionUID = 8751282105532159742L;
	private String Tagname;
	private String Description;
	public String getTagname() {
		return Tagname;
	}
	public void setTagname(String tagname) {
		Tagname = tagname;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	
}
