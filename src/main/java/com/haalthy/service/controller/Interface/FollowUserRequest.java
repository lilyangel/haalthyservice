package com.haalthy.service.controller.Interface;

public class FollowUserRequest {
	private String following;
	private String follower;
	public String getFollowing() {
		return following;
	}
	public void setFollowing(String following) {
		this.following = following;
	}
	public String getFollower() {
		return follower;
	}
	public void setFollower(String follower) {
		this.follower = follower;
	}
}
