package com.haalthy.service.persistence;

import com.haalthy.service.domain.User;

public interface UserMapper {
	  String getUserByUsername(String username);
}
