package com.haalthy.service.persistence;

import com.haalthy.service.domain.User;

public interface UserMapper {
	  User getUserByUsername(String username);
	  
	  User getUserByEmail(String email);
	  
	  int addUser(User user);
	  
	  int updateUser(User user);
}
