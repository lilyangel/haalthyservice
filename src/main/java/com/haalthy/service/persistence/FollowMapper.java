package com.haalthy.service.persistence;

import java.util.List;

import com.haalthy.service.domain.Follow;

public interface FollowMapper {
	public List<Follow> getFollowingsByUsername(String username);
	
	public int addFollowing(Follow follow);
	
	public int inactiveFollowship(Follow follow);
}
