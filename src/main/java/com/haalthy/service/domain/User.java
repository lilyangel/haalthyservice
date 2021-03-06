package com.haalthy.service.domain;

import com.haalthy.service.controller.Interface.ImageInfo;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 8751282105532159742L;

	private int UserID;
	private String email;
	private String username;
	private String displayname;
	private String password;
	private String createDate;
	private String updateDate;
	private ImageInfo imageInfo;
	private String gender;
	private int isSmoking;
	private String pathological;
	private String stage;
	private int age;
	private int followCount;
	private String cancerType;
	private String metastasis;
	private String userType;
	private String geneticMutation;
	private String deviceToken;
	private String imageURL;
	private String phone;
	private int isSuperUser;
	private String openid;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public int getIsSuperUser() {
		return isSuperUser;
	}
	public void setIsSuperUser(int isSuperUser) {
		this.isSuperUser = isSuperUser;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public ImageInfo getImageInfo() {
		return imageInfo;
	}
	public void setImageInfo(ImageInfo imageInfo) {
		this.imageInfo = imageInfo;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public String getGeneticMutation() {
		return geneticMutation;
	}
	public void setGeneticMutation(String geneticMutation) {
		this.geneticMutation = geneticMutation;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getMetastasis() {
		return metastasis;
	}
	public void setMetastasis(String metastasis) {
		this.metastasis = metastasis;
	}
	public String getCancerType() {
		return cancerType;
	}
	public void setCancerType(String cancerType) {
		this.cancerType = cancerType;
	}
	public int getFollowCount() {
		return followCount;
	}
	public void setFollowCount(int followCount) {
		this.followCount = followCount;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
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
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	
}
