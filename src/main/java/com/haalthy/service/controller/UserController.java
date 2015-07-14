package com.haalthy.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.haalthy.service.domain.User;
import com.haalthy.service.openservice.UserService;

import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Controller
@RequestMapping("/open/user")
public class UserController {
	 
	@Autowired
	private transient UserService userService;

    @RequestMapping(value = "/{username}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public User getUser(@PathVariable String username) {
    	User user = userService.getUser(username);
//    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//    	System.out.println(passwordEncoder.matches("password", user.getPassword()));
    	return user;
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public String addUser(@RequestBody User user) {
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	String hashedPassword = passwordEncoder.encode(user.getPassword());
    	user.setPassword(hashedPassword);
    	Date now = new Date();
    	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String currentDt = sdf.format(now);
    	user.setCreateDate(currentDt);
    	
    	user.setUpdateDate(currentDt);
		if (userService.getUser(user.getName()) != null)
			return "duplicate username";
		if (userService.addUser(user) == 1)
			return "create successful!";
		return null;
	}
}
