package com.haalthy.service.configuration;

public enum PostType {
	BROADCAST(0), TREATMENT(1), PATIENTSTATUS(2);
	private final int value;
	
	private PostType(int value){
		this.value = value;
	}
	public int getValue(){
		return value;
	}
}
