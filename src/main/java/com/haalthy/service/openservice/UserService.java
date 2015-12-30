package com.haalthy.service.openservice;

import com.haalthy.service.controller.Interface.GetSuggestUsersByProfileRequest;
import com.haalthy.service.domain.SelectUserByTagRange;
import com.haalthy.service.domain.SuggestedUserPair;
import com.haalthy.service.domain.Tag;
import com.haalthy.service.domain.User;
import com.haalthy.service.domain.UserTag;
import com.haalthy.service.persistence.UserMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
    public User getUserByUsername(String username) {
    	return userMapper.getUserByUsername(username);
    }
    
    public User getUserByEmail(String email) {
    	return userMapper.getUserByEmail(email);
    }

    public int addUser(User user){
    	return userMapper.addUser(user);
    }
    
    public int updateUser(User user){
    	return userMapper.updateUser(user);
    }
    
    public int addUserTags(List<UserTag> userTagList){
    	return userMapper.addUserTags(userTagList);
    }
    
    public int deleteUserTags(String username){
    	return userMapper.deleteUserTags(username);
    }
    
    public List<Tag> getTagsByUsername(String username){
    	return userMapper.getTagsByUsername(username);
    }
    
    public int addUserFollowCount(String username){
    	return userMapper.addUserFollowCount(username);
    }
    
    public int deleteUserFollowCount(String username){
    	return userMapper.deleteUserFollowCount(username);
    }
    
	public List<User> selectSuggestUsersByTags(SelectUserByTagRange selectUserByTagRange){
		return userMapper.selectSuggestUsersByTags(selectUserByTagRange);
	}
	public  List<User> searchUsers(String keyword){
		return userMapper.searchUsers(keyword);
	}
	public  List<User> selectSuggestUsersByProfile(GetSuggestUsersByProfileRequest getSuggestUsersByProfileRequest){
		return userMapper.selectSuggestUsersByProfile(getSuggestUsersByProfileRequest);
	}
	public int resetPassword(User user){
		return userMapper.resetPassword(user);
	}
	public int deleteFromSuggestUserByProfile(SuggestedUserPair suggestedUserPair){
		return userMapper.deleteFromSuggestUserByProfile(suggestedUserPair);
	}
	public List<User> getUsersByDisplayname(String displayname){
		return userMapper.getUsersByDisplayname(displayname);
	}
	public int resetDeviceToken(User user){
		return userMapper.resetDeviceToken(user);
	}
}
