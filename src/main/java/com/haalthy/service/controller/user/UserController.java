package com.haalthy.service.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.haalthy.service.controller.Interface.AddUpdateUserRequest;
import com.haalthy.service.controller.Interface.AddUpdateUserResponse;
import com.haalthy.service.controller.Interface.GetSuggestUsersByTagsRequest;
import com.haalthy.service.controller.Interface.GetUsersResponse;
import com.haalthy.service.domain.Follow;
import com.haalthy.service.domain.SelectUserByTagRange;
import com.haalthy.service.domain.User;
import com.haalthy.service.openservice.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

@Controller
@RequestMapping("/open/user")
public class UserController {
	 
	@Autowired
	private transient UserService userService;

    @RequestMapping(value = "/{username}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public User getUser(@PathVariable String username) {
    	User user = userService.getUserByUsername(username);
    	user.setPassword(null);
    	return user;
//    	if (user == null)
//    		return null;
//    	GetUsersResponse userResponse = new GetUsersResponse();
//    	userResponse.setUsername(user.getUsername());
//    	userResponse.setDisplayname(user.getDisplayname());
//    	userResponse.setEmail(user.getEmail());
//    	userResponse.setCreateDate(user.getCreateDate());
//    	userResponse.setImage(user.getImage());
//    	return userResponse;
    }
    
    //{"gender":"M","password":"password","pathological":"adenocarcinoma","metastasis":"bone;其他","age":"61","isSmoking":1,"cancerType":"lung","email":"user3@qq.com","username":"user3","stage":1}
    @RequestMapping(value = "/add",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public AddUpdateUserResponse addUser(@RequestBody User user) {
    	
    	System.out.println(user.getMetastasis());
    	//set encoded password
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	String hashedPassword = passwordEncoder.encode(user.getPassword());
    	user.setPassword(hashedPassword);
    	
    	//set create date and update date
    	Date now = new Date();
    	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String currentDt = sdf.format(now);
    	user.setCreateDate(currentDt);
    	user.setUpdateDate(currentDt);
   
    	user.setFollowCount(0);
    	
    	AddUpdateUserResponse addUserResponse = new AddUpdateUserResponse();
		if (userService.getUserByEmail(user.getEmail()) != null)
			addUserResponse.setStatus("this email has been registed, please login");
		else if (userService.getUserByUsername(user.getUsername()) != null)
			addUserResponse.setStatus("this name has been registed, please login");
		else if (userService.addUser(user) == 1)
			addUserResponse.setStatus("create successful!");
		else
			addUserResponse.setStatus("insert db error");
		return addUserResponse;
	}
    
    @RequestMapping(value = "/suggestedusers",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public List<User> getSuggestUsersByTags(@RequestBody GetSuggestUsersByTagsRequest getSuggestUsersByTagsRequest) {
    	int[] tags = getSuggestUsersByTagsRequest.getTags();
    	int rangeBegin = getSuggestUsersByTagsRequest.getRangeBegin();
    	int rangeEnd = getSuggestUsersByTagsRequest.getRangeEnd();
    	List<User> users = new ArrayList<User>();
    	Set <String> set = new HashSet <String>();
    	for(int i = 0; i<tags.length; i++){
    		SelectUserByTagRange selectUserByTagRange = new SelectUserByTagRange();
    		selectUserByTagRange.setBeginIndex(rangeBegin/tags.length);
    		selectUserByTagRange.setEndIndex(rangeEnd/tags.length + 1);
    		selectUserByTagRange.setTagID(tags[i]);
    		List<User> suggestUsers = userService.selectSuggestUsersByTags(selectUserByTagRange);
    		Iterator<User> userItr = suggestUsers.iterator();
    		while(userItr.hasNext()){
    			User newSuggestUser = userItr.next();
    			if(set.add(newSuggestUser.getUsername())){
    				users.add(newSuggestUser);
    			}
    		}
    	}
    	return users;
    }
}
