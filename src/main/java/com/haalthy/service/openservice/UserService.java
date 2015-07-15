package com.haalthy.service.openservice;

import com.haalthy.service.domain.User;
import com.haalthy.service.persistence.UserMapper;
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
}
