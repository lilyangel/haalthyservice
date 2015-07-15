package com.haalthy.service.controller.user;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haalthy.service.controller.Interface.GetUsersResponse;
import com.haalthy.service.domain.User;
import com.haalthy.service.openservice.UserService;
import com.haalthy.service.controller.Interface.AddUpdateUserRequest;
import com.haalthy.service.controller.Interface.AddUpdateUserResponse;

@Controller
@RequestMapping("/security/user")
public class UserSecurityController {
	 
	@Autowired
	private transient UserService userService;

	@RequestMapping(value = "/{username}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {
			"application/json" })
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
   
   @RequestMapping(value = "/update",method = RequestMethod.PUT, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
   @ResponseBody
   public AddUpdateUserResponse updateUser(@RequestBody AddUpdateUserRequest updateUserRequest) {
	   String username = updateUserRequest.getUsername();
	   AddUpdateUserResponse updateUserResponse = new AddUpdateUserResponse();
	   Authentication a = SecurityContextHolder.getContext().getAuthentication();
	   String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
	   System.out.println(username);
	   System.out.println(currentSessionUsername);
	   if(currentSessionUsername.equals(username)==false)
		   updateUserResponse.setStatus("can't eidt this account");
	   User user = userService.getUserByUsername(username);
		if (user == null)
			updateUserResponse.setStatus("couldn't find this user in Database");;
		if(updateUserRequest.getEmail()!=null && updateUserRequest.getEmail()!="")
			user.setEmail(updateUserRequest.getEmail());
		if(updateUserRequest.getPassword()!=null && updateUserRequest.getPassword()!=""){
	    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    	String hashedPassword = passwordEncoder.encode(updateUserRequest.getPassword());
	    	user.setPassword(hashedPassword);
		}
		if(updateUserRequest.getDisplayname()!=null && updateUserRequest.getDisplayname()!=""){
			user.setDisplayname(updateUserRequest.getDisplayname());
		}
		if (userService.updateUser(user) == 1)
			updateUserResponse.setStatus("update successful!");
		else
			updateUserResponse.setStatus("update db error");
			
       return updateUserResponse;
   }
}
