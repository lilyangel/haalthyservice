package com.haalthy.service.openservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haalthy.service.domain.Follow;
import com.haalthy.service.domain.NewFollowerCount;
import com.haalthy.service.domain.User;
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
	
	public List<Follow> getFollowingsByUsernameAndFollowingname(Follow follow){
		return followMapper.getFollowingsByUsernameAndFollowingname(follow);		
	}
	
	public List<User> getFollowerUsersByUsername(String username){
		return followMapper.getFollowerUsersByUsername(username);
	}
	
	public List<User> getFollowingUsersByUsername(String username){
		return followMapper.getFollowingUsersByUsername(username);
	}
	
	public int updateNewFollowerCount(String username){
		return followMapper.updateNewFollowerCount(username);
	}
	
	public int insertNewFollowerCount(String username){
		return followMapper.insertNewFollowerCount(username);
	}
	
	public NewFollowerCount selectNewFollowerCount(String username){
		return followMapper.selectNewFollowerCount(username);
	}
	
	public int refreshNewFollowerCount(String username){
		return followMapper.refreshNewFollowerCount(username);
	}
	
	public int isFollowingUser(Follow follow){
		return followMapper.isFollowingUser(follow);
	}
}
