package com.haalthy.service.controller.Interface.post;

import com.haalthy.service.controller.Interface.ImageInfo;

public class AppendImageRequest {
	private int id;
	private ImageInfo imageInfo;
	private int imageIndex;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ImageInfo getImageInfo() {
		return imageInfo;
	}
	public void setImageInfo(ImageInfo imageInfo) {
		this.imageInfo = imageInfo;
	}
	public int getImageIndex() {
		return imageIndex;
	}
	public void setImageIndex(int imageIndex) {
		this.imageIndex = imageIndex;
	}
}
