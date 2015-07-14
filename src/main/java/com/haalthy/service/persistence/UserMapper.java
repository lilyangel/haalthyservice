package com.haalthy.service.persistence;

import com.haalthy.service.domain.User;

public interface UserMapper {
	  User getUserByUsername(String username);
	  
	  int addUser(User user);
}
