package com.haalthy.service.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.haalthy.service.controller.Interface.AddUpdateUserRequest;
import com.haalthy.service.controller.Interface.AddUpdateUserResponse;
import com.haalthy.service.controller.Interface.GetSuggestUsersByTagsRequest;
import com.haalthy.service.controller.Interface.GetUsersResponse;
import com.haalthy.service.controller.Interface.InputUsernameRequest;
import com.haalthy.service.domain.ClinicTrailInfo;
import com.haalthy.service.domain.Follow;
import com.haalthy.service.domain.SelectUserByTagRange;
import com.haalthy.service.domain.User;
import com.haalthy.service.openservice.ClinicTrailService;
import com.haalthy.service.openservice.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

@Controller
@RequestMapping("/open/user")
public class UserController {
    private String decodePassword(String password){
		System.out.println(password);
    	String[] codeUnits = password.split("a");
    	String passwordDecode = "";
    	for(int i = 0; i< codeUnits.length; i++){
    		if(!codeUnits[i].equals("")){
        		int intCode = Integer.valueOf(codeUnits[i]).intValue(); 
        		System.out.println(intCode);
        		char a = (char)intCode;
        		passwordDecode += a;
        	}
    	}
    	return passwordDecode;
    }
	@Autowired
	private transient UserService userService;

//    @RequestMapping(value = "/{username}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
//    @ResponseBody
//    public User getUser(@PathVariable String username) {
//    	User user = userService.getUserByUsername(username);
//    	user.setPassword(null);
//    	return user;
//    }
    
    //{"gender":"M","password":"password","pathological":"adenocarcinoma","metastasis":"bone;其他","age":"61","isSmoking":1,"cancerType":"lung","email":"user3@qq.com","username":"user3","stage":1}
    @RequestMapping(value = "/add",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public AddUpdateUserResponse addUser(@RequestBody User user) {
    	user.setPassword(decodePassword(user.getPassword()));
    	user.setUsername(generateUsername(user));
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
			addUserResponse.setStatus("this email has been registed, please use another name");
		else if (userService.getUserByUsername(user.getUsername()) != null)
			addUserResponse.setStatus("this name has been registed, please login");
		else if (userService.addUser(user) == 1){
			String username = userService.getUserByEmail(user.getEmail()).getUsername();
			addUserResponse.setStatus(username);
		}
		else
			addUserResponse.setStatus("insert db error");
		return addUserResponse;
	}
    
	public int getRandom() {
		int max = 999;
		int min = 100;
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}
    
    private String generateUsername(User user){
    	String timestamp = String.valueOf(System.currentTimeMillis());
    	String randomInt = String.valueOf(getRandom());
    	return user.getUserType()+timestamp+"."+randomInt;
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
    
    @RequestMapping(value = "/search", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public List<User> searchUsers(@RequestBody InputUsernameRequest inputUsernameRequest){
//    	String[] keyword = inputUsernameRequest.getUsername().split(" ");
//    	List<String> keywordList = java.util.Arrays.asList(keyword);
    	String keyword = "%" + inputUsernameRequest.getUsername() + "%";
    	return userService.searchUsers(keyword);
    }

}
