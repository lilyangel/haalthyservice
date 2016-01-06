package com.haalthy.service.controller.Interface;

public class ImageInfo {
	private byte[] data;
	private String type;
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
