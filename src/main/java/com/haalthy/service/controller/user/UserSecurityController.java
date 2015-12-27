package com.haalthy.service.controller.user;

import java.io.IOException;
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

import com.haalthy.service.controller.Interface.TagList;
import com.haalthy.service.controller.Interface.UpdateUserTagsRequest;
import com.haalthy.service.domain.ClinicData;
import com.haalthy.service.domain.ClinicReport;
import com.haalthy.service.domain.Follow;
import com.haalthy.service.domain.NewFollowerCount;
import com.haalthy.service.domain.PatientStatus;
import com.haalthy.service.domain.SuggestedUserPair;
import com.haalthy.service.domain.Tag;
import com.haalthy.service.domain.Treatment;
import com.haalthy.service.domain.User;
import com.haalthy.service.domain.UserTag;
import com.haalthy.service.openservice.FollowService;
import com.haalthy.service.openservice.PatientService;
import com.haalthy.service.openservice.UserService;

import com.haalthy.service.configuration.ImageService;

import com.haalthy.service.controller.Interface.AddUpdateUserRequest;
import com.haalthy.service.controller.Interface.AddUpdateUserResponse;
import com.haalthy.service.controller.Interface.ClinicDataType;
import com.haalthy.service.controller.Interface.FollowUserRequest;
import com.haalthy.service.controller.Interface.GetMentionedUsernameRequest;
import com.haalthy.service.controller.Interface.GetSuggestUsersByProfileRequest;
import com.haalthy.service.controller.Interface.GetUserDetailResponse;
import com.haalthy.service.controller.Interface.InputUsernameRequest;
import com.haalthy.service.controller.Interface.ResetPasswordRequest;
@Controller
@RequestMapping("/security/user")
public class UserSecurityController {
	 
	@Autowired
	private transient UserService userService;
	
	@Autowired
	private transient FollowService followService;
	
	@Autowired
	private transient PatientService patientService;
	
	@RequestMapping(value = "/{username}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public User getUser(@PathVariable String username) throws IOException {
		ImageService imageService = new ImageService();
		User user = userService.getUserByUsername(username);
		if(user!=null && user.getImage()!=null){
			user.setImage(imageService.scale(user.getImage(), 64, 64));			
		}
		return user;
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public GetUserDetailResponse getUserDetail(@RequestBody InputUsernameRequest inputUsernameRequest) throws IOException {
		ImageService imageService = new ImageService();
		String username = inputUsernameRequest.getUsername();
		User user = userService.getUserByUsername(username);
		
		if(user!=null && user.getImage()!=null){
			user.setImage(imageService.scale(user.getImage(), 88, 88));			
		}
		
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
// 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
 	   	
 	   	List<Treatment> treatments = null;
 	   	List<PatientStatus> patientStatus = null;
 	   	List<ClinicDataType> clinicReport = new ArrayList();
 	   	treatments = patientService.getTreatmentsByUser(username);
 	   	patientStatus = patientService.getPatientStatusByUser(username);
 	   	//			clinicReport = patientService.getClinicReportByUser(username);
 	   	List<ClinicData> clinicDataList = patientService.getClinicDataByUsername(username);
 	   	Iterator<ClinicData> clinicDataItr = clinicDataList.iterator();
 	   	while(clinicDataItr.hasNext()){
 	   		ClinicData clinicData = clinicDataItr.next();
 	   		Iterator<ClinicDataType> clinicDataTypeItr = clinicReport.iterator();
 	   		Boolean hasClinicItemName = false;
 	   		while(clinicDataTypeItr.hasNext()){
 	   			ClinicDataType clinicDataType = clinicDataTypeItr.next();
 	   			if(clinicDataType.getClinicItemName().equals(clinicData.getClinicItemName())){
 	   				clinicDataType.getClinicDataList().add(clinicData);
 	   				hasClinicItemName = true;
 	   				break;
 	   			}
 	   		}
 	   		if(hasClinicItemName == false){
 	   			ClinicDataType clinicDataType = new ClinicDataType();
 	   			clinicDataType.setClinicItemName(clinicData.getClinicItemName());
// 	   			clinicDataType.getClinicDataList().add(clinicData);
 	   			List<ClinicData> clinicDataListInType = new ArrayList();
 	   			clinicDataListInType.add(clinicData);
 	   			clinicDataType.setClinicDataList(clinicDataListInType);
 	   			clinicReport.add(clinicDataType);
 	   		}
 	   	}

 	   	GetUserDetailResponse getUserDetailResponse = new GetUserDetailResponse();
 	   	getUserDetailResponse.setUserProfile(user);
 	   	getUserDetailResponse.setTreatments(treatments);
 	   	getUserDetailResponse.setPatientStatus(patientStatus);
 	   	getUserDetailResponse.setClinicReport(clinicReport);
 	   	return getUserDetailResponse;
	}
   
//   @RequestMapping(value = "/update",method = RequestMethod.PUT, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
//   @ResponseBody
//   public AddUpdateUserResponse updateUser(@RequestBody AddUpdateUserRequest updateUserRequest) {
//	   String username = updateUserRequest.getUsername();
//	   AddUpdateUserResponse updateUserResponse = new AddUpdateUserResponse();
//	   Authentication a = SecurityContextHolder.getContext().getAuthentication();
//	   String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
//	   if(currentSessionUsername.equals(username)==false)
//		   updateUserResponse.setStatus("can't eidt this account");
//	   User user = userService.getUserByUsername(username);
//	   /*
//	    *       	
//	    *       EMAIL = #{email},
//      			DISPLAYNAME = #{displayname},
//		UpdateDate = #{updateDate}
//		Image = #{image}
//		Gender = #{gender}
//		IsSmoking = #{isSmoking}
//		Pathological = #{pathological}
//		Stage = #{stage}
//		CancerType = #{cancerType}
//		metastasis = #{metastasis}
//		Age = #{age}
//	    * */
//		if (user == null)
//			updateUserResponse.setStatus("couldn't find this user in Database");;
//		if(updateUserRequest.getEmail()!=null && updateUserRequest.getEmail()!="")
//			user.setEmail(updateUserRequest.getEmail());
//		if(updateUserRequest.getDisplayname()!=null && updateUserRequest.getDisplayname()!=""){
//			user.setDisplayname(updateUserRequest.getDisplayname());
//		}
//		if(updateUserRequest.getImage()!=null){
//			user.setImage(updateUserRequest.getImage());
//		}
//		if (userService.updateUser(user) == 1)
//			updateUserResponse.setStatus("update successful!");
//		else
//			updateUserResponse.setStatus("update db error");
//			
//       return updateUserResponse;
//   }

	@RequestMapping(value = "/update", method = RequestMethod.POST, headers = "Accept=application/json", produces = {
			"application/json" }, consumes = { "application/json" })
	@ResponseBody
	public AddUpdateUserResponse updateUser(@RequestBody User updateUser) {
		String username = updateUser.getUsername();
		AddUpdateUserResponse updateUserResponse = new AddUpdateUserResponse();
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest()
				.getAuthorizationParameters().get("username");
		if ((currentSessionUsername.equals(username) == false) || (username.equals(userService.getUserByEmail(currentSessionUsername).getUsername())))
			updateUserResponse.setStatus("can't eidt this account");
		User user = userService.getUserByUsername(username);
		/*
		 * EMAIL = #{email}, 
		 * DISPLAYNAME = #{displayname}, 
		 * UpdateDate = #{updateDate} 
		 * Image = #{image} 
		 * Gender = #{gender} 
		 * IsSmoking = #{isSmoking} 
		 * Pathological = #{pathological} 
		 * Stage = #{stage}
		 * CancerType = #{cancerType} 
		 * metastasis = #{metastasis} 
		 * Age = #{age}
		 */
		if (user == null)
			updateUserResponse.setStatus("couldn't find this user in Database");
		
		if (updateUser.getEmail() != null && updateUser.getEmail() != "")
			user.setEmail(updateUser.getEmail());
		if (updateUser.getDisplayname() != null && updateUser.getDisplayname() != "") {
			user.setDisplayname(updateUser.getDisplayname());
		}
		if (updateUser.getImage() != null) {
			user.setImage(updateUser.getImage());
		}
		if (updateUser.getGender() != null && updateUser.getGender() != ""){
			user.setGender(updateUser.getGender());
		}
		user.setIsSmoking(updateUser.getIsSmoking());
		
		if(updateUser.getPathological() != null && updateUser.getPathological() != ""){
			user.setPathological(updateUser.getPathological());
		}
		if(updateUser.getStage() != 0){
			user.setStage(updateUser.getStage());
		}
		if(updateUser.getCancerType() != null && updateUser.getCancerType() != ""){
			user.setCancerType(updateUser.getCancerType());
		}
		if(updateUser.getMetastasis() != null && updateUser.getMetastasis() != ""){
			user.setMetastasis(updateUser.getMetastasis());
		}
		if(updateUser.getGeneticMutation()!=null && updateUser.getGeneticMutation() != ""){
			user.setGeneticMutation(updateUser.getGeneticMutation());
		}
		user.setAge(updateUser.getAge());
		if (userService.updateUser(user) == 1)
			updateUserResponse.setStatus("update successful!");
		else
			updateUserResponse.setStatus("update db error");

		return updateUserResponse;
	}
	
	@RequestMapping(value="/newfollow/count", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public NewFollowerCount selectNewFollowerCount(@RequestBody InputUsernameRequest inputUsernameRequest){
//		Authentication a = SecurityContextHolder.getContext().getAuthentication();
//		String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
		return followService.selectNewFollowerCount(inputUsernameRequest.getUsername());
	}
	
	@RequestMapping(value="/newfollow/increase/{username}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public int increaseNewFollowerCount(@PathVariable String username){
//		Authentication a = SecurityContextHolder.getContext().getAuthentication();
//		String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
		int returnValue = 0;
		returnValue = followService.updateNewFollowerCount(username);
		if(returnValue == 0)
			returnValue = followService.insertNewFollowerCount(username);
		return returnValue;
	}
	
	@RequestMapping(value="/newfollow/refresh", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public int refreshNewFollowerCount(@RequestBody InputUsernameRequest inputUsernameRequest){
//		Authentication a = SecurityContextHolder.getContext().getAuthentication();
//		String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
		return followService.refreshNewFollowerCount(inputUsernameRequest.getUsername());
	}
	
//	@RequestMapping(value="/followings/", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json" })
//	@ResponseBody
//	public List<Follow> getFollowingsByUsername(){
//		List<Follow> follows = new ArrayList<Follow>();
//		Authentication a = SecurityContextHolder.getContext().getAuthentication();
//		String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
//		follows = followService.getFollowingsByUsername(currentSessionUsername);
//		return follows;
//	}
	
	@RequestMapping(value="/followingusers", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public List<User> getFollowingusersByUsername(@RequestBody InputUsernameRequest inputUsernameRequest){
//		List<User> users = new ArrayList<User>();
//		Authentication a = SecurityContextHolder.getContext().getAuthentication();
//		String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
		return followService.getFollowingUsersByUsername(inputUsernameRequest.getUsername());
	}
	
	
	@RequestMapping(value="/followerusers", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public List<User> getFollowersByUsername(@RequestBody InputUsernameRequest inputUsernameRequest){
//		List<User> users = new ArrayList<User>();
//		Authentication a = SecurityContextHolder.getContext().getAuthentication();
//		String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
		return followService.getFollowerUsersByUsername(inputUsernameRequest.getUsername());
	}
	
	
    @RequestMapping(value = "/follow/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public AddUpdateUserResponse addFollowing(@RequestBody Follow follow){
    	AddUpdateUserResponse addUpdateUserResponse = new AddUpdateUserResponse();
//    	Follow follow = new Follow();
//    	follow.setFollowingUser(followUserRequest.getFollowing());
    	follow.setIsActive(1);
    	
    	Date now = new Date();
    	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String currentDt = sdf.format(now);
    	follow.setDateInserted(currentDt);
    	follow.setDateUpdated(currentDt);
    	
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
// 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
 	   	
// 	   follow.setUsername(currentSessionUsername);
 	   List<Follow> follows = followService.getFollowingsByUsernameAndFollowingname(follow);
 	   if(follows.size()>0){
 		   addUpdateUserResponse.setStatus("following exist");
 	   }else if(followService.addFollowing(follow)>0){
 	 	   userService.addUserFollowCount(follow.getFollowingUser());
 		  addUpdateUserResponse.setStatus("add Following Successful!");
 	   }
 	   
 	   //delete user from suggested user table
 	   SuggestedUserPair suggestedUserPair = new SuggestedUserPair();
 	   suggestedUserPair.setSuggestedUsername(follow.getUsername());
 	   suggestedUserPair.setUsername(follow.getFollowingUser());
 	   userService.deleteFromSuggestUserByProfile(suggestedUserPair);
 	   
 	   //increase 
		int updateNewFollowerCountResult = followService.updateNewFollowerCount(follow.getFollowingUser());
		if(updateNewFollowerCountResult == 0)
			updateNewFollowerCountResult = followService.insertNewFollowerCount(follow.getFollowingUser());
 	   
 	   return addUpdateUserResponse;
    }
    
    @RequestMapping(value = "/follow/inactive", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public int inactiveFollowing(@RequestBody Follow follow){
//    	Follow follow = new Follow();
    	
//    	follow.setFollowingUser(followingusername);
    	follow.setIsActive(0);
    	
    	Date now = new Date();
    	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String currentDt = sdf.format(now);
    	follow.setDateUpdated(currentDt);
    	
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
// 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
// 	    follow.setUsername(currentSessionUsername);
 	    userService.deleteUserFollowCount(follow.getFollowingUser());
 	    return followService.inactiveFollowship(follow);
    }
    
    @RequestMapping(value = "/follow/isfollowing", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public int isFollowingUser(@RequestBody Follow follow){
// 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
// 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
// 	   	Follow follow = new Follow();
// 	   	follow.setFollowingUser(username);
// 	   	follow.setUsername(currentSessionUsername);
 	   	return followService.isFollowingUser(follow);
    }
    
    //input: [1,2]
    @RequestMapping(value = "/tag/update", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public int updateUserTags(@RequestBody UpdateUserTagsRequest updateUserTagsRequest){
    	if ((updateUserTagsRequest == null) || (updateUserTagsRequest.getTags() == null) || (updateUserTagsRequest.getTags().size() == 0)){
    		return 0;
    	}
    	List<UserTag> userTagList = new ArrayList<UserTag>();
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
// 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
        java.util.Date today = new java.util.Date();
    	Timestamp now = new java.sql.Timestamp(today.getTime());
    	Iterator<Tag> tagItr = updateUserTagsRequest.getTags().iterator();
    	while(tagItr.hasNext()){
    		UserTag userTag = new UserTag();
    		userTag.setTagID(tagItr.next().getTagId());
    		userTag.setUsername(updateUserTagsRequest.getUsername());
    		userTag.setDateInserted(now);
    		userTagList.add(userTag);
        	System.out.println(userTag.getUsername());
        	System.out.println(userTag.getTagID());
    	}
    	userService.deleteUserTags(updateUserTagsRequest.getUsername());
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
    
    @RequestMapping(value = "/tags", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<Tag> getTagsByUsername(@RequestBody InputUsernameRequest inputUsernameRequest){
//    	Authentication a = SecurityContextHolder.getContext().getAuthentication();
//    	return userService.getTagsByUsername(((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username"));
    	System.out.println(inputUsernameRequest.getUsername());
    	return userService.getTagsByUsername(inputUsernameRequest.getUsername());
    }
    
    @RequestMapping(value = "/suggestedusers",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public List<User> getSuggestUsersByProfile(@RequestBody GetSuggestUsersByProfileRequest getSuggestUsersByProfileRequest) {
//    	Authentication a = SecurityContextHolder.getContext().getAuthentication();
//    	getSuggestUsersByProfileRequest.setUsername(((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username"));
    	return userService.selectSuggestUsersByProfile(getSuggestUsersByProfileRequest);
    }
    
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
    
    @RequestMapping(value = "/resetpassword",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
	public int resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
		String password = resetPasswordRequest.getPassword();
		User user = new User();
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest()
				.getAuthorizationParameters().get("username");
		if (userService.getUserByUsername(currentSessionUsername) == null)
			currentSessionUsername = userService.getUserByEmail(currentSessionUsername).getUsername();
		user.setUsername(currentSessionUsername);
		if (password != null && password != "") {
			password = decodePassword(password);
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);
			user.setPassword(hashedPassword);
		}
		int result = userService.resetPassword(user);
		return result;
	}
    
    @RequestMapping(value = "/deletesuggesteduser",method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public int getSuggestUsersByProfile(@RequestBody SuggestedUserPair suggestedUserPair) {
//    	SuggestedUserPair suggestedUserPair = new SuggestedUserPair();
//    	Authentication a = SecurityContextHolder.getContext().getAuthentication();
//    	suggestedUserPair.setSuggestedUsername(((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username"));
//    	suggestedUserPair.setUsername(username);
    	return userService.deleteFromSuggestUserByProfile(suggestedUserPair);
    }
    
    @RequestMapping(value = "/getusername",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public String getUsernameByEmail(@RequestBody InputUsernameRequest inputUsernameReqeust){
    	String responseMessage = "no user in database";
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
		System.out.println(inputUsernameReqeust.getUsername());
		User user = userService.getUserByEmail(inputUsernameReqeust.getUsername());
		if (user != null){
			responseMessage = user.getUsername();
		}
		return responseMessage;
    }

    @RequestMapping(value = "/getusersbydisplayname",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public List<User> getUsersByDisplayname(@RequestBody GetMentionedUsernameRequest getMentionedUsernameRequest){
		List<Follow> followings = followService.getFollowingsByUsername(getMentionedUsernameRequest.getUsername());
		List<User> users = userService.getUsersByDisplayname(getMentionedUsernameRequest.getMentionedDisplayname());
		Iterator<Follow> followingItr = followings.iterator();
		List<String> followingUsernames = new ArrayList();
		System.out.println(followings.size());
		while(followingItr.hasNext()){
			Follow following = followingItr.next();
			followingUsernames.add(following.getFollowingUser());
			System.out.println(following.getFollowingUser());
		}
		Iterator<User> userItr = users.iterator();
		while(userItr.hasNext()){
			User user = userItr.next();
			if (!followingUsernames.contains(user.getUsername())){
				users.remove(user);
			}
		}
    	return users;
    }
}
