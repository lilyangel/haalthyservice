package com.haalthy.service.openservice;

import com.haalthy.service.domain.User;
import com.haalthy.service.persistence.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Service
@RequestMapping("/security/user")
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
    @RequestMapping(value = "/{username}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public User getUser(@PathVariable String username) {
    	return userMapper.getUserByUsername(username);
    }

}
