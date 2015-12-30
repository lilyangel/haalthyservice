package com.haalthy.service.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.haalthy.service.controller.Interface.GetSuggestUsersByTagsRequest;
import com.haalthy.service.controller.Interface.InputUsernameRequest;
import com.haalthy.service.controller.Interface.user.AddUpdateUserRequest;
import com.haalthy.service.controller.Interface.user.AddUpdateUserResponse;
import com.haalthy.service.controller.Interface.user.GetUsersResponse;
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
    	String[] codeUnits = password.split("a");
    	String passwordDecode = "";
    	for(int i = 0; i< codeUnits.length; i++){
    		if(!codeUnits[i].equals("")){
        		int intCode = Integer.valueOf(codeUnits[i]).intValue(); 
        		char a = (char)intCode;
        		passwordDecode += a;
        	}
    	}
    	return passwordDecode;
    }
	@Autowired
	private transient UserService userService;

    @RequestMapping(value = "/add",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public AddUpdateUserResponse addUser(@RequestBody User user) {
		AddUpdateUserResponse addUserResponse = new AddUpdateUserResponse();
		try {
			user.setPassword(decodePassword(user.getPassword()));
			user.setUsername(generateUsername(user));
			// set encoded password
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(hashedPassword);

			// set create date and update date
			Date now = new Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentDt = sdf.format(now);
			user.setCreateDate(currentDt);
			user.setUpdateDate(currentDt);
			user.setFollowCount(0);

			if (userService.getUserByEmail(user.getEmail()) != null){
				addUserResponse.setResultDesp("该邮箱/手机已被注册");
				addUserResponse.setResult(-2);
				
			}else if (userService.addUser(user) == 1) {
				String username = userService.getUserByEmail(user.getEmail()).getUsername();
				addUserResponse.setResult(1);
				addUserResponse.setResultDesp("返回成功");
				addUserResponse.setUsername(username);
			} else{
				addUserResponse.setResult(-3);
				addUserResponse.setResultDesp("数据库插入错误");
			}
		} catch (Exception e) {
			addUserResponse.setResult(-1);
			addUserResponse.setResultDesp("数据库连接错误");
		}
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
    public GetUsersResponse getSuggestUsersByTags(@RequestBody GetSuggestUsersByTagsRequest getSuggestUsersByTagsRequest) {
		GetUsersResponse getUsersResponse = new GetUsersResponse();
		try {
			int[] tags = getSuggestUsersByTagsRequest.getTags();
			int rangeBegin = getSuggestUsersByTagsRequest.getRangeBegin();
			int rangeEnd = getSuggestUsersByTagsRequest.getRangeEnd();
			List<User> users = new ArrayList<User>();
			Set<String> set = new HashSet<String>();
			for (int i = 0; i < tags.length; i++) {
				SelectUserByTagRange selectUserByTagRange = new SelectUserByTagRange();
				selectUserByTagRange.setBeginIndex(rangeBegin / tags.length);
				selectUserByTagRange.setEndIndex(rangeEnd / tags.length + 1);
				selectUserByTagRange.setTagID(tags[i]);
				List<User> suggestUsers = userService.selectSuggestUsersByTags(selectUserByTagRange);
				Iterator<User> userItr = suggestUsers.iterator();
				while (userItr.hasNext()) {
					User newSuggestUser = userItr.next();
					if (set.add(newSuggestUser.getUsername())) {
						users.add(newSuggestUser);
					}
				}
			}
			getUsersResponse.setResult(1);
			getUsersResponse.setResultDesp("返回成功");
			getUsersResponse.setUsers(users);
		} catch (Exception e) {
			getUsersResponse.setResult(-1);
			getUsersResponse.setResultDesp("数据库连接错误");
		}
    	return getUsersResponse;
    }
    
    @RequestMapping(value = "/search", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
	public GetUsersResponse searchUsers(@RequestBody InputUsernameRequest inputUsernameRequest) {
		GetUsersResponse getUsersResponse = new GetUsersResponse();
		try {
			String keyword = "%" + inputUsernameRequest.getUsername() + "%";
			getUsersResponse.setResult(1);
			getUsersResponse.setResultDesp("返回成功");
			getUsersResponse.setUsers(userService.searchUsers(keyword));
		} catch (Exception e) {
			getUsersResponse.setResult(-1);
			getUsersResponse.setResultDesp("数据库连接错误");
		}
		return getUsersResponse;
	}
}
