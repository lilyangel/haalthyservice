package com.haalthy.service.controller.Interface.user;

import java.util.List;

import com.haalthy.service.domain.User;

public class FollowUsersLists {
	private List<User> friends;
	private List<User> followingUsers;
	private List<User> followerUsers;
	public List<User> getFriends() {
		return friends;
	}
	public void setFriends(List<User> friends) {
		this.friends = friends;
	}
	public List<User> getFollowingUsers() {
		return followingUsers;
	}
	public void setFollowingUsers(List<User> followingUsers) {
		this.followingUsers = followingUsers;
	}
	public List<User> getFollowerUsers() {
		return followerUsers;
	}
	public void setFollowerUsers(List<User> followerUsers) {
		this.followerUsers = followerUsers;
	}
}
