package com.haalthy.service.persistence;

import java.util.List;

import com.haalthy.service.controller.Interface.GetSuggestUsersByProfileRequest;
import com.haalthy.service.domain.SelectUserByTagRange;
import com.haalthy.service.domain.SuggestedUserPair;
import com.haalthy.service.domain.Tag;
import com.haalthy.service.domain.User;
import com.haalthy.service.domain.UserTag;
import org.apache.ibatis.annotations.Param;


public interface UserMapper {
	  User getUserByUsername(String username);
	  
	  User getUserByEmail(String email);
	  
	  User getUserByPhone(String phone);
	  
	  int addUser(User user);
	  
	  int updateUser(User user);
	  
	  int addUserTags(List<UserTag> userTagList);
	  
	  int deleteUserTags(String username);
	  
	  List<Tag> getTagsByUsername(String username);
	  
	  int addUserFollowCount(String username);
	  
	  int deleteUserFollowCount(String username);
	  
	  List<User> selectSuggestUsersByTags(SelectUserByTagRange selectUserByTagRange);
	  
	  List<User> searchUsers(String keyword);
	  
	  List<User> selectSuggestUsersByProfile(GetSuggestUsersByProfileRequest getSuggestUsersByProfileRequest);
	  
	  int resetPassword(User user);
	  
	  int deleteFromSuggestUserByProfile(SuggestedUserPair suggestedUserPair);
	  
	  List<User> getUsersByDisplayname(String displayname);
	  
	  List<User> getSuperUserList();
	  
	  int resetDeviceToken(User user);

	  int updateUserPhoto(User user);
	  int appendUserPhoto(User user);
	  
}
