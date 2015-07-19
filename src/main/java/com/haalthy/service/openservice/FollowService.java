package com.haalthy.service.openservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haalthy.service.domain.Follow;
import com.haalthy.service.persistence.FollowMapper;

public class FollowService {
	@Autowired
	private FollowMapper followMapper;
	
	public List<Follow> getFollowingsByUsername(String username){
		return followMapper.getFollowingsByUsername(username);
	}
	
	public int addFollowing(Follow follow){
		return followMapper.addFollowing(follow);
	}
	
	public int inactiveFollowship(Follow follow){
		return followMapper.inactiveFollowship(follow);
	}
}
