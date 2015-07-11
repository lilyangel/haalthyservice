package com.haalthy.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.haalthy.service.domain.User;
import com.haalthy.service.openservice.UserService;

@Controller
@RequestMapping("/open/user")
public class UserController {
	 
	@Autowired
	private transient UserService userService;
    @RequestMapping(value = "/{username}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public User getUser(@PathVariable String username) {
    	return userService.getUser(username);
    }
}
