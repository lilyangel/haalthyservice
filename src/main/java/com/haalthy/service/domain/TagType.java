package com.haalthy.service.domain;

import java.util.List;

public class TagType {
	private String typeName;
	private int typeRank;
	private List<Tag> tags;
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public int getTypeRank() {
		return typeRank;
	}
	public void setTypeRank(int typeRank) {
		this.typeRank = typeRank;
	}
	
}
