package com.haalthy.service.controller.Interface;

public class AddUpdateUserRequest {
	private String email;
	private String username;
	private String displayname;
	private byte[] image;
	private String gender;
	private int isSmoking;
	private String pathological;
	private String stage;
	private int age;
	private String cancerType;
	private String metastasis;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getIsSmoking() {
		return isSmoking;
	}
	public void setIsSmoking(int isSmoking) {
		this.isSmoking = isSmoking;
	}
	public String getPathological() {
		return pathological;
	}
	public void setPathological(String pathological) {
		this.pathological = pathological;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCancerType() {
		return cancerType;
	}
	public void setCancerType(String cancerType) {
		this.cancerType = cancerType;
	}
	public String getMetastasis() {
		return metastasis;
	}
	public void setMetastasis(String metastasis) {
		this.metastasis = metastasis;
	}
	
}
