package com.haalthy.service.persistence;

import java.util.List;

import com.haalthy.service.domain.Follow;
import com.haalthy.service.domain.User;

public interface FollowMapper {
	public List<Follow> getFollowingsByUsername(String username);
	
	public int addFollowing(Follow follow);
	
	public int inactiveFollowship(Follow follow);
	
	public List<Follow> getFollowingsByUsernameAndFollowingname(Follow follow);
	
	public List<User> getFollowerUsersByUsername(String username);
	
	public List<User> getFollowingUsersByUsername(String username);
}
