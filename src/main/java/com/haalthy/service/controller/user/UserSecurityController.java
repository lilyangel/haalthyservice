package com.haalthy.service.controller.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import com.haalthy.service.controller.Interface.TagList;
import com.haalthy.service.domain.Follow;
import com.haalthy.service.domain.Tag;
import com.haalthy.service.domain.User;
import com.haalthy.service.domain.UserTag;
import com.haalthy.service.openservice.FollowService;
import com.haalthy.service.openservice.UserService;
import com.haalthy.service.controller.Interface.AddUpdateUserRequest;
import com.haalthy.service.controller.Interface.AddUpdateUserResponse;
import com.haalthy.service.controller.Interface.AddUserTagsRequest;

@Controller
@RequestMapping("/security/user")
public class UserSecurityController {
	 
	@Autowired
	private transient UserService userService;
	
	@Autowired
	private transient FollowService followService;
	
	
	@RequestMapping(value = "/{username}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json" })
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
    	userResponse.setImage(user.getImage());

		return userResponse;
	}
   
   @RequestMapping(value = "/update",method = RequestMethod.PUT, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
   @ResponseBody
   public AddUpdateUserResponse updateUser(@RequestBody AddUpdateUserRequest updateUserRequest) {
	   String username = updateUserRequest.getUsername();
	   AddUpdateUserResponse updateUserResponse = new AddUpdateUserResponse();
	   Authentication a = SecurityContextHolder.getContext().getAuthentication();
	   String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
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
   
	@RequestMapping(value="/followings", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public List<Follow> getFollowingsByUsername(){
		List<Follow> follows = new ArrayList<Follow>();
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
		System.out.println(currentSessionUsername);
		follows = followService.getFollowingsByUsername(currentSessionUsername);
		return follows;
	}
	
    @RequestMapping(value = "/follow/add/{followingusername}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public AddUpdateUserResponse addFollowing(@PathVariable String followingusername){
    	AddUpdateUserResponse addUpdateUserResponse = new AddUpdateUserResponse();
    	Follow follow = new Follow();
    	follow.setFollowingUser(followingusername);
    	follow.setIsActive(1);
    	
    	Date now = new Date();
    	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String currentDt = sdf.format(now);
    	follow.setDateInserted(currentDt);
    	follow.setDateUpdated(currentDt);
    	
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
 	   	
 	   follow.setUsername(currentSessionUsername);
 	   List<Follow> follows = followService.getFollowingsByUsernameAndFollowingname(follow);
 	   if(follows.size()>0){
 		   addUpdateUserResponse.setStatus("following exist");
 	   }else if(followService.addFollowing(follow)>0){
 	 	   userService.addUserFollowCount(followingusername);
 		  addUpdateUserResponse.setStatus("add Following Successful!");
 	   }
 	   return addUpdateUserResponse;
    }
    
    @RequestMapping(value = "/follow/inactive/{followingusername}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public int inactiveFollowing(@PathVariable String followingusername){
    	Follow follow = new Follow();
    	
    	follow.setFollowingUser(followingusername);
    	follow.setIsActive(0);
    	
    	Date now = new Date();
    	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String currentDt = sdf.format(now);
    	follow.setDateUpdated(currentDt);
    	
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
 	    follow.setUsername(currentSessionUsername);
 	   userService.deleteUserFollowCount(followingusername);
 	    return followService.inactiveFollowship(follow);
    }
    //input: [1,2]
    @RequestMapping(value = "/tag/update", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public int updateUserTags(@RequestBody TagList tags){
    	List<UserTag> userTagList = new ArrayList<UserTag>();
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
        java.util.Date today = new java.util.Date();
    	Timestamp now = new java.sql.Timestamp(today.getTime());
    	Iterator<Tag> tagItr = tags.getTags().iterator();
    	while(tagItr.hasNext()){
    		UserTag userTag = new UserTag();
    		userTag.setTagID(tagItr.next().getTagId());
    		userTag.setUsername(currentSessionUsername);
    		userTag.setDateInserted(now);
    		userTagList.add(userTag);
    	}
    	userService.deleteUserTags(currentSessionUsername);
    	return userService.addUserTags(userTagList);
    }
    
//    @RequestMapping(value = "/tag/delete", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
//    @ResponseBody
//    public int deleteUserTags(@RequestBody int tag){
//    	UserTag userTag = new UserTag();
//    	userTag.setTagID(tag);
//    	Authentication a = SecurityContextHolder.getContext().getAuthentication();
//    	userTag.setUsername(((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username"));
//    	Date now = new Date();
//    	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    	userTag.setDateInserted(sdf.format(now));
//    	return userService.deleteUserTag(userTag);
//    }
    
    @RequestMapping(value = "/tags", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<Tag> getTagsByUsername(){
    	Authentication a = SecurityContextHolder.getContext().getAuthentication();
    	return userService.getTagsByUsername(((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username"));
    }
    
}
