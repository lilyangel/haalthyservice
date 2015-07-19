package com.haalthy.service.persistence;

import java.util.List;

import com.haalthy.service.domain.Tag;
import com.haalthy.service.domain.User;
import com.haalthy.service.domain.UserTag;


public interface UserMapper {
	  User getUserByUsername(String username);
	  
	  User getUserByEmail(String email);
	  
	  int addUser(User user);
	  
	  int updateUser(User user);
	  
	  int addUserTags(List<UserTag> userTagList);
	  
	  int deleteUserTag(UserTag userTag);
	  
	  List<Tag> getTagsByUsername(String username);
}
