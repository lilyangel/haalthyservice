package com.haalthy.service.domain;

import java.io.Serializable;

public class Tag implements Serializable{
	private static final long serialVersionUID = 8751282105532159742L;
	private String name;
	private String description;
	private int tagId;
	private String typeName;
    private int typeRank;
    private int rankInType;
    
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getTypeRank() {
		return typeRank;
	}
	public void setTypeRank(int typeRank) {
		this.typeRank = typeRank;
	}
	public int getRankInType() {
		return rankInType;
	}
	public void setRankInType(int rankInType) {
		this.rankInType = rankInType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTagId() {
		return tagId;
	}
	public void setTagId(int tagId) {
		this.tagId = tagId;
	}
}
