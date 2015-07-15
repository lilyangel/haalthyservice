package com.haalthy.service.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.haalthy.service.controller.Interface.AddUpdateUserRequest;
import com.haalthy.service.controller.Interface.AddUpdateUserResponse;
import com.haalthy.service.controller.Interface.GetUsersResponse;

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
    public GetUsersResponse getUser(@PathVariable String username) {
    	User user = userService.getUserByUsername(username);
    	if (user == null)
    		return null;
    	GetUsersResponse userResponse = new GetUsersResponse();
    	userResponse.setUsername(user.getUsername());
    	userResponse.setDisplayname(user.getDisplayname());
    	userResponse.setEmail(user.getEmail());
    	userResponse.setCreateDate(user.getCreateDate());
    	return userResponse;
    }
    
    @RequestMapping(value = "/add",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public AddUpdateUserResponse addUser(@RequestBody AddUpdateUserRequest addUserRequest) {
    	User user = new User();
    	user.setUsername(addUserRequest.getUsername());
    	user.setDisplayname(addUserRequest.getDisplayname());
    	user.setEmail(addUserRequest.getEmail());
    	
    	//set encoded password
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	String hashedPassword = passwordEncoder.encode(addUserRequest.getPassword());
    	user.setPassword(hashedPassword);
    	
    	//set create date and update date
    	Date now = new Date();
    	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String currentDt = sdf.format(now);
    	user.setCreateDate(currentDt);
    	user.setUpdateDate(currentDt);
    	
    	AddUpdateUserResponse addUserResponse = new AddUpdateUserResponse();
		if (userService.getUserByEmail(addUserRequest.getEmail()) != null)
			addUserResponse.setStatus("this email has been registed, please login");
		else if (userService.getUserByUsername(addUserRequest.getUsername()) != null)
			addUserResponse.setStatus("this name has been registed, please login");
		else if (userService.addUser(user) == 1)
			addUserResponse.setStatus("create successful!");
		else
			addUserResponse.setStatus("insert db error");
		return addUserResponse;
	}
}
