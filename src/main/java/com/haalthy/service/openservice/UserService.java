package com.haalthy.service.openservice;

import com.haalthy.service.domain.User;
import com.haalthy.service.persistence.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
    public User getUser(@PathVariable String username) {
    	return userMapper.getUserByUsername(username);
    }

}
