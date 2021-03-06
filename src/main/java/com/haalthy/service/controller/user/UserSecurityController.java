package com.haalthy.service.controller.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haalthy.service.JPush.JPushService;
import com.haalthy.service.common.ProcessImageURL;
import com.haalthy.service.configuration.ImageService;
import com.haalthy.service.controller.Interface.ContentIntEapsulate;
import com.haalthy.service.controller.Interface.ContentStringEapsulate;
import com.haalthy.service.controller.Interface.GetMentionedUsernameRequest;
import com.haalthy.service.controller.Interface.GetSuggestUsersByProfileRequest;
import com.haalthy.service.controller.Interface.GetUserDetailResponse;
import com.haalthy.service.controller.Interface.InputUsernameRequest;
import com.haalthy.service.controller.Interface.OSSFile;
import com.haalthy.service.controller.Interface.ResetPasswordRequest;
import com.haalthy.service.controller.Interface.Response;
import com.haalthy.service.controller.Interface.patient.ClinicDataType;
import com.haalthy.service.controller.Interface.tag.GetTagsResponse;
import com.haalthy.service.controller.Interface.user.AddUpdateUserResponse;
import com.haalthy.service.controller.Interface.user.FollowUsersLists;
import com.haalthy.service.controller.Interface.user.GetUsersResponse;
import com.haalthy.service.controller.Interface.user.IsFollowingUserResponse;
import com.haalthy.service.controller.Interface.user.NewFollowerCountResponse;
import com.haalthy.service.controller.Interface.user.UpdateNewFollowerResponse;
import com.haalthy.service.controller.Interface.user.UpdateUserTagsRequest;
import com.haalthy.service.controller.Interface.user.UserDetail;
import com.haalthy.service.domain.ClinicData;
import com.haalthy.service.domain.Follow;
import com.haalthy.service.domain.PatientStatus;
import com.haalthy.service.domain.SuggestedUserPair;
import com.haalthy.service.domain.Tag;
import com.haalthy.service.domain.Treatment;
import com.haalthy.service.domain.User;
import com.haalthy.service.domain.UserTag;
import com.haalthy.service.openservice.FollowService;
import com.haalthy.service.openservice.OssService;
import com.haalthy.service.openservice.PatientService;
import com.haalthy.service.openservice.UserService;

@Controller
@RequestMapping("/security/user")
public class UserSecurityController {
	 
	@Autowired
	private transient UserService userService;
	
	@Autowired
	private transient FollowService followService;
	
	@Autowired
	private transient PatientService patientService;
	
	@Autowired
	private transient OssService ossService;
	
    @Autowired
    private transient JPushService jPushService;
	
//	@RequestMapping(value = "/{username}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json" })
//	@ResponseBody
//	public User getUser(@PathVariable String username) throws IOException {
//		ImageService imageService = new ImageService();
//		User user = userService.getUserByUsername(username);
//		if(user!=null && user.getImage()!=null){
//			user.setImage(imageService.scale(user.getImage(), 64, 64));			
//		}
//		return user;
//	}
	
//	@RequestMapping(value = "/updatejpushcode", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json" })
//	@ResponseBody
//	public AddUpdateUserResponse updateJpushDeviceToken(@RequestBody JpushPair jpushPair){
//		AddUpdateUserResponse addUpdateUserResponse = new AddUpdateUserResponse();
//		
//		return addUpdateUserResponse;
//	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public GetUserDetailResponse getUserDetail(@RequestBody InputUsernameRequest inputUsernameRequest)  {
		GetUserDetailResponse getUserDetailResponse = new GetUserDetailResponse();
		try {
			ImageService imageService = new ImageService();
			String username = inputUsernameRequest.getUsername();
			User user = userService.getUserByUsername(username);

			List<Treatment> treatments = null;
			List<PatientStatus> patientStatus = null;
			List<ClinicDataType> clinicReport = new ArrayList();
			treatments = patientService.getTreatmentsByUser(username);
			patientStatus = patientService.getPatientStatusByUser(username);
			for (PatientStatus patientStatusItem : patientStatus) {
				if (patientStatusItem.getImageURL() != null) {
					ProcessImageURL processImageURL = new ProcessImageURL();
					patientStatusItem.setImageURL(processImageURL.processImageURL(patientStatusItem.getImageURL()));
				}
			}
			List<ClinicData> clinicDataList = patientService.getClinicDataByUsername(username);
			Iterator<ClinicData> clinicDataItr = clinicDataList.iterator();
			while (clinicDataItr.hasNext()) {
				ClinicData clinicData = clinicDataItr.next();
				Iterator<ClinicDataType> clinicDataTypeItr = clinicReport.iterator();
				Boolean hasClinicItemName = false;
				while (clinicDataTypeItr.hasNext()) {
					ClinicDataType clinicDataType = clinicDataTypeItr.next();
					if (clinicDataType.getClinicItemName().equals(clinicData.getClinicItemName())) {
						clinicDataType.getClinicDataList().add(clinicData);
						hasClinicItemName = true;
						break;
					}
				}
				if (hasClinicItemName == false) {
					ClinicDataType clinicDataType = new ClinicDataType();
					clinicDataType.setClinicItemName(clinicData.getClinicItemName());
					// clinicDataType.getClinicDataList().add(clinicData);
					List<ClinicData> clinicDataListInType = new ArrayList();
					clinicDataListInType.add(clinicData);
					clinicDataType.setClinicDataList(clinicDataListInType);
					clinicReport.add(clinicDataType);
				}
			}
			UserDetail userDetail = new UserDetail();
			userDetail.setUserProfile(user);
			userDetail.setTreatments(treatments);
			userDetail.setPatientStatus(patientStatus);
			userDetail.setClinicReport(clinicReport);
			getUserDetailResponse.setResult(1);
			getUserDetailResponse.setResultDesp("返回成功");
			getUserDetailResponse.setContent(userDetail);
		} catch (Exception e) {
			e.printStackTrace();
			getUserDetailResponse.setResult(-1);
			getUserDetailResponse.setResultDesp("数据库连接错误");
		}
 	   	return getUserDetailResponse;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, headers = "Accept=application/json", produces = {
			"application/json" }, consumes = { "application/json" })
	@ResponseBody
	public AddUpdateUserResponse updateUser(@RequestBody User updateUser) {
		AddUpdateUserResponse updateUserResponse = new AddUpdateUserResponse();
		try {
			String username = updateUser.getUsername();
			Authentication a = SecurityContextHolder.getContext().getAuthentication();
			String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest()
					.getAuthorizationParameters().get("username");
			User user = userService.getUserByUsername(username);
			if (user == null) {
				updateUserResponse.setResult(-2);
				updateUserResponse.setResultDesp("找不到此用户");
			}
			if (currentSessionUsername.equals(username) == false) {
				User userEdit = userService.getUserByEmail(currentSessionUsername);
				if (userEdit == null) {
					userEdit = userService.getUserByPhone(currentSessionUsername);
				}
				if (userEdit.getUsername().equals(username) == false) {
					updateUserResponse.setResult(-3);
					updateUserResponse.setResultDesp("无权限更新此用户信息");
				}
			}

			if (updateUser.getEmail() != null && updateUser.getEmail() != "")
				user.setEmail(updateUser.getEmail());
			if (updateUser.getDisplayname() != null && updateUser.getDisplayname() != "") {
				user.setDisplayname(updateUser.getDisplayname());
			}
			if (updateUser.getImageInfo() != null) {
//				user.setImage(updateUser.getImage());
//				userService.updateUserPhoto("user", photoPath)
				List<OSSFile> ossFileList = new ArrayList();
				OSSFile ossFile = new OSSFile();
				ossFile.setFileType(updateUser.getImageInfo().getType());
				ossFile.setFunctionType("user");
				ossFile.setImg(updateUser.getImageInfo().getData());
				ossFile.setModifyType("update");
				ossFile.setId(username);
				ossFileList.add(ossFile);
				ossService.ossUploadFile(ossFileList);
			}
			if (updateUser.getGender() != null && updateUser.getGender() != "") {
				user.setGender(updateUser.getGender());
			}
			user.setIsSmoking(updateUser.getIsSmoking());

			if (updateUser.getPathological() != null && updateUser.getPathological() != "") {
				user.setPathological(updateUser.getPathological());
			}
			if (updateUser.getStage() != null) {
				user.setStage(updateUser.getStage());
			}
			if (updateUser.getCancerType() != null && updateUser.getCancerType() != "") {
				user.setCancerType(updateUser.getCancerType());
			}
			if (updateUser.getMetastasis() != null && updateUser.getMetastasis() != "") {
				user.setMetastasis(updateUser.getMetastasis());
			}
			if (updateUser.getGeneticMutation() != null && updateUser.getGeneticMutation() != "") {
				user.setGeneticMutation(updateUser.getGeneticMutation());
			}
			/*
			20160316 原来未判断age是否为空，改为如果有传大于0的值则修改
			* */
			if(updateUser.getAge() > 0)
			{
				user.setAge(updateUser.getAge());
			}
			if (userService.updateUser(user) == 1) {
				updateUserResponse.setResult(1);
				updateUserResponse.setResultDesp("返回成功");
				ContentStringEapsulate contentStringEapsulate = new ContentStringEapsulate();
				contentStringEapsulate.setResult(user.getUsername());
				updateUserResponse.setContent(contentStringEapsulate);

			} else {
				updateUserResponse.setResult(-4);
				updateUserResponse.setResultDesp("更新失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			updateUserResponse.setResult(-1);
			updateUserResponse.setResultDesp("数据库连接错误");
		}
		return updateUserResponse;
	}
	
	@RequestMapping(value="/newfollow/count", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public NewFollowerCountResponse selectNewFollowerCount(@RequestBody InputUsernameRequest inputUsernameRequest){
		NewFollowerCountResponse newFollowerCountResponse = new NewFollowerCountResponse();
		try{
			ContentIntEapsulate contentIntEapsulate = new ContentIntEapsulate();
			contentIntEapsulate.setCount(followService.selectNewFollowerCount(inputUsernameRequest.getUsername()).getCount());
			newFollowerCountResponse.setContent(contentIntEapsulate);
			newFollowerCountResponse.setResult(1);
			newFollowerCountResponse.setResultDesp("返回成功");
		}catch(Exception e){
			newFollowerCountResponse.setResult(-1);
			newFollowerCountResponse.setResultDesp("数据库连接错误");
		}
		return newFollowerCountResponse;
	}
	
	@RequestMapping(value="/newfollow/increase", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public UpdateNewFollowerResponse increaseNewFollowerCount(@RequestBody InputUsernameRequest username){
		UpdateNewFollowerResponse updateNewFollowerCountResponse = new UpdateNewFollowerResponse();
		try {
			int returnValue = 0;
			returnValue = followService.updateNewFollowerCount(username.getUsername());
			if (returnValue == 0)
				returnValue = followService.insertNewFollowerCount(username.getUsername());
			updateNewFollowerCountResponse.setResult(1);
			updateNewFollowerCountResponse.setResultDesp("返回成功");
		} catch (Exception e) {
			updateNewFollowerCountResponse.setResult(-1);
			updateNewFollowerCountResponse.setResultDesp("数据库连接错误");
		}
		return updateNewFollowerCountResponse;
	}
	
	@RequestMapping(value="/newfollow/refresh", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public UpdateNewFollowerResponse refreshNewFollowerCount(@RequestBody InputUsernameRequest inputUsernameRequest){
		UpdateNewFollowerResponse updateNewFollowerCountResponse = new UpdateNewFollowerResponse();
		try {
			followService.refreshNewFollowerCount(inputUsernameRequest.getUsername());
			updateNewFollowerCountResponse.setResult(1);
			updateNewFollowerCountResponse.setResultDesp("返回成功");
		} catch (Exception e) {
			updateNewFollowerCountResponse.setResult(-1);
			updateNewFollowerCountResponse.setResultDesp("数据库连接错误");
		}
		return updateNewFollowerCountResponse;
	}
	
	@RequestMapping(value="/followingusers", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public GetUsersResponse getFollowingusersByUsername(@RequestBody InputUsernameRequest inputUsernameRequest){
		GetUsersResponse getUsersResponse = new GetUsersResponse();
		try {
			getUsersResponse.setContent(followService.getFollowingUsersByUsername(inputUsernameRequest.getUsername()));
			getUsersResponse.setResult(1);
			getUsersResponse.setResultDesp("返回成功");
		} catch (Exception e) {
			getUsersResponse.setResult(-1);
			getUsersResponse.setResultDesp("数据库连接错误");
		}
		return getUsersResponse;
	}
	
	@RequestMapping(value="/followusers", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public Response getFollowUsers(@RequestBody InputUsernameRequest inputUsernameRequest){
		Response response = new Response();
		List<User> followingUsers = followService.getFollowingUsersByUsername(inputUsernameRequest.getUsername());
		List<User> followerUsers = followService.getFollowerUsersByUsername(inputUsernameRequest.getUsername());
		List<User> newFollowingUsers = new ArrayList();
		List<User> friends = new ArrayList();
		
		List<Integer> followingUserIDList = new ArrayList();
		List<Integer> followerUserIDList =  new ArrayList();
		List<Integer> newfollowingUserIDList = new ArrayList();
		List<Integer> newfollowerUserIDList =  new ArrayList();
		List<Integer> friendIDList = new ArrayList();
		for( User followingUser : followingUsers){
			followingUserIDList.add(followingUser.getUserID());
		}
		
		for( User followerUser : followerUsers){
			followerUserIDList.add(followerUser.getUserID());
		}
		
		for( Integer followingUserID : followingUserIDList){
			if (followerUserIDList.contains(followingUserID)){
				friendIDList.add(followingUserID);
				followerUserIDList.remove(followingUserID);
			}else{
				newfollowingUserIDList.add(followingUserID);
			}
		}
		
		for( User followingUser : followingUsers){
//			followingUserIDList.add(followingUser.getUserID());
			if (newfollowingUserIDList.contains(followingUser.getUserID())){
				newFollowingUsers.add(followingUser);
			}
			if (friendIDList.contains(followingUser.getUserID())){
				friends.add(followingUser);
			}
		}
		List<User> newFollowerUsers = new ArrayList();
		for( User followerUser : followerUsers){
//			followerUserIDList.add(followerUser.getUserID());
			if (followerUserIDList.contains(followerUser.getUserID())){
				newFollowerUsers.add(followerUser);
			}
		}
		
//		for( User followingUser : followingUsers){
//			if (followerUsers.contains(followingUser)){
//				friends.add(followingUser);
//				followerUsers.remove(followingUser);
//			}else{
//				newFollowingUsers.add(followingUser);
//			}
//		}
//		followerUsers;
		FollowUsersLists followUsersLists = new FollowUsersLists();
		followUsersLists.setFollowerUsers(newFollowerUsers);
		followUsersLists.setFollowingUsers(newFollowingUsers);
		followUsersLists.setFriends(friends);
		response.setContent(followUsersLists);
		response.setResult(1);
		response.setResultDesp("返回成功");
		return response;
	}
	
	@RequestMapping(value="/followerusers", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public GetUsersResponse getFollowersByUsername(@RequestBody InputUsernameRequest inputUsernameRequest){
		GetUsersResponse getUsersResponse = new GetUsersResponse();
		try {
			getUsersResponse.setContent(followService.getFollowerUsersByUsername(inputUsernameRequest.getUsername()));
			getUsersResponse.setResult(1);
			getUsersResponse.setResultDesp("返回成功");
		} catch (Exception e) {
			getUsersResponse.setResult(-1);
			getUsersResponse.setResultDesp("数据库连接错误");
		}
		return getUsersResponse;
	}
	
	
    @RequestMapping(value = "/follow/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
	public AddUpdateUserResponse addFollowing(@RequestBody Follow follow) {
		AddUpdateUserResponse addUpdateUserResponse = new AddUpdateUserResponse();
		try {
			follow.setIsActive(1);

			Date now = new Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentDt = sdf.format(now);
			follow.setDateInserted(currentDt);
			follow.setDateUpdated(currentDt);

			List<Follow> follows = followService.getFollowingsByUsernameAndFollowingname(follow);
			if (follows.size() > 0) {
				addUpdateUserResponse.setResult(-2);
				addUpdateUserResponse.setResultDesp("已关注此用户");	
			} else if (followService.addFollowing(follow) > 0) {
				userService.addUserFollowCount(follow.getFollowingUser());
				addUpdateUserResponse.setResult(1);
				addUpdateUserResponse.setResultDesp("返回成功");		
			}

			// delete user from suggested user table
			SuggestedUserPair suggestedUserPair = new SuggestedUserPair();
			suggestedUserPair.setSuggestedUsername(follow.getUsername());
			suggestedUserPair.setUsername(follow.getFollowingUser());
			userService.deleteFromSuggestUserByProfile(suggestedUserPair);
			Map<String,String> extras = new HashMap();
			extras.put("type", "followed");
			extras.put("username", follow.getUsername());
			int newFollowerCount = followService.selectNewFollowerCount(follow.getFollowingUser()).getCount();
			extras.put("count", String.valueOf(newFollowerCount));
			jPushService.SendMessageToUser(follow.getFollowingUser(), follow.getUsername(), "您被用户关注",extras);

			// increase
			int updateNewFollowerCountResult = followService.updateNewFollowerCount(follow.getFollowingUser());
			if (updateNewFollowerCountResult == 0)
				updateNewFollowerCountResult = followService.insertNewFollowerCount(follow.getFollowingUser());
			
		} catch (Exception e) {
			e.printStackTrace();
			addUpdateUserResponse.setResult(-1);
			addUpdateUserResponse.setResultDesp("数据库连接错误");
		}
		return addUpdateUserResponse;
	}
    
    @RequestMapping(value = "/follow/inactive", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public AddUpdateUserResponse inactiveFollowing(@RequestBody Follow follow){
		AddUpdateUserResponse addUpdateUserResponse = new AddUpdateUserResponse();
		try {
			follow.setIsActive(0);
			Date now = new Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentDt = sdf.format(now);
			follow.setDateUpdated(currentDt);
			userService.deleteUserFollowCount(follow.getFollowingUser());
			followService.inactiveFollowship(follow);
			addUpdateUserResponse.setResult(1);
			addUpdateUserResponse.setResultDesp("返回成功");
		} catch (Exception e) {
			addUpdateUserResponse.setResult(-1);
			addUpdateUserResponse.setResultDesp("数据库连接错误");
		}
    	return addUpdateUserResponse;
    }
    
    @RequestMapping(value = "/follow/isfollowing", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public IsFollowingUserResponse isFollowingUser(@RequestBody Follow follow){
    	IsFollowingUserResponse isFollowingUserResponse = new IsFollowingUserResponse();
    	try{
    		ContentIntEapsulate contentIntEapsulate= new ContentIntEapsulate();
    		contentIntEapsulate.setCount(followService.isFollowingUser(follow));
    		isFollowingUserResponse.setContent(contentIntEapsulate);
    		isFollowingUserResponse.setResult(1);
    		isFollowingUserResponse.setResultDesp("返回成功");
    	}catch(Exception e){
    		isFollowingUserResponse.setResult(-1);
    		isFollowingUserResponse.setResultDesp("数据库连接错误");
    	}
 	   	return isFollowingUserResponse;
    }
    
    //input: [1,2]
    @RequestMapping(value = "/tag/update", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public AddUpdateUserResponse updateUserTags(@RequestBody UpdateUserTagsRequest updateUserTagsRequest){
		AddUpdateUserResponse addUpdateUserResponse = new AddUpdateUserResponse();
		try {
			if ((updateUserTagsRequest == null) || (updateUserTagsRequest.getTags() == null)
					|| (updateUserTagsRequest.getTags().size() == 0)) {
				addUpdateUserResponse.setResult(-2);
				addUpdateUserResponse.setResultDesp("输入参数错误");
			} else {
				List<UserTag> userTagList = new ArrayList<UserTag>();
				Authentication a = SecurityContextHolder.getContext().getAuthentication();
				java.util.Date today = new java.util.Date();
				Timestamp now = new java.sql.Timestamp(today.getTime());
				Iterator<Tag> tagItr = updateUserTagsRequest.getTags().iterator();
				while (tagItr.hasNext()) {
					UserTag userTag = new UserTag();
					userTag.setTagID(tagItr.next().getTagId());
					userTag.setUsername(updateUserTagsRequest.getUsername());
					userTag.setDateInserted(now);
					userTagList.add(userTag);
				}
				userService.deleteUserTags(updateUserTagsRequest.getUsername());
		    	userService.addUserTags(userTagList);
				addUpdateUserResponse.setResult(1);
				addUpdateUserResponse.setResultDesp("返回成功");
			}
		} catch (Exception e) {
			addUpdateUserResponse.setResult(-1);
			addUpdateUserResponse.setResultDesp("数据库连接错误");
		}
		return addUpdateUserResponse;
    }
    
    @RequestMapping(value = "/tags", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public GetTagsResponse getTagsByUsername(@RequestBody InputUsernameRequest inputUsernameRequest){
    	GetTagsResponse getTagsResponse = new GetTagsResponse();
    	try{
    		getTagsResponse.setContent(userService.getTagsByUsername(inputUsernameRequest.getUsername()));
    		getTagsResponse.setResult(1);
    		getTagsResponse.setResultDesp("返回成功");
    	}catch(Exception e){
    		getTagsResponse.setResult(-1);
    		getTagsResponse.setResultDesp("数据库连接错误");
    	}
    	return getTagsResponse;
    }
    
    @RequestMapping(value = "/suggestedusers",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public GetUsersResponse getSuggestUsersByProfile(@RequestBody GetSuggestUsersByProfileRequest getSuggestUsersByProfileRequest) {
    	GetUsersResponse getUsersResponse = new GetUsersResponse();
    	try{
    		getSuggestUsersByProfileRequest.setBeginIndex(getSuggestUsersByProfileRequest.getCount() * getSuggestUsersByProfileRequest.getPage());
    		getUsersResponse.setContent(userService.selectSuggestUsersByProfile(getSuggestUsersByProfileRequest));
    		getUsersResponse.setResult(1);
    		getUsersResponse.setResultDesp("返回成功");
    	}catch(Exception e){
    		e.printStackTrace();
    		getUsersResponse.setResult(-1);
    		getUsersResponse.setResultDesp("数据库连接错误");
    	}
    	return getUsersResponse;
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
    
    @RequestMapping(value = "/updatedevicetoken",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public AddUpdateUserResponse updateDeviceToken(@RequestBody User user) {
    	AddUpdateUserResponse addUpdateUserResponse = new AddUpdateUserResponse();
    	try{
    		userService.resetDeviceToken(user);
			addUpdateUserResponse.setResult(1);
			addUpdateUserResponse.setResultDesp("返回成功");
    	}catch(Exception e){
			addUpdateUserResponse.setResult(-1);
			addUpdateUserResponse.setResultDesp("数据库连接错误");
    	}
    	return addUpdateUserResponse;
    }
    
    @RequestMapping(value = "/resetpassword",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
	public AddUpdateUserResponse resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
    	AddUpdateUserResponse addUpdateUserResponse = new AddUpdateUserResponse();
		ContentStringEapsulate contentStringEapsulate = new ContentStringEapsulate();
		try {
			String password = resetPasswordRequest.getPassword();
			User user = new User();
			Authentication a = SecurityContextHolder.getContext().getAuthentication();
			String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest()
					.getAuthorizationParameters().get("username");
			if (userService.getUserByUsername(currentSessionUsername) == null)
				user = userService.getUserByEmail(currentSessionUsername);
			if (user == null){
				user = userService.getUserByPhone(currentSessionUsername);
			}
			if (password != null && password != "") {
				password = decodePassword(password);
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = passwordEncoder.encode(password);
				user.setPassword(hashedPassword);
			}
			if(userService.resetPassword(user)>0){
				addUpdateUserResponse.setResult(1);
				addUpdateUserResponse.setResultDesp("返回成功");
				contentStringEapsulate.setResult("1");
			}else{
				addUpdateUserResponse.setResult(-2);
				addUpdateUserResponse.setResultDesp("更新失败");
				contentStringEapsulate.setResult("-2");
			}
		} catch (Exception e) {
			addUpdateUserResponse.setResult(-1);
			addUpdateUserResponse.setResultDesp("数据库连接错误");
			contentStringEapsulate.setResult("-1");
		}
		addUpdateUserResponse.setContent(contentStringEapsulate);
		return addUpdateUserResponse;
	}
    
    @RequestMapping(value = "/resetpasswordwithoriginalpwd",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
	public AddUpdateUserResponse resetPasswordWithOriginalPwd(@RequestBody ResetPasswordRequest resetPasswordRequest) {
		AddUpdateUserResponse addUpdateUserResponse = new AddUpdateUserResponse();
		ContentStringEapsulate contentStringEapsulate = new ContentStringEapsulate();
		try {
			String originalPwd = decodePassword(resetPasswordRequest.getOriginalPwd());
			String password = resetPasswordRequest.getPassword();
			User user = new User();
			Authentication a = SecurityContextHolder.getContext().getAuthentication();
			String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest()
					.getAuthorizationParameters().get("username");
			if (userService.getUserByUsername(currentSessionUsername) == null)
				user = userService.getUserByEmail(currentSessionUsername);
			if (user == null) {
				user = userService.getUserByPhone(currentSessionUsername);
			}
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (passwordEncoder.matches(originalPwd, user.getPassword()) == false) {
				//
				addUpdateUserResponse.setResult(-3);
				addUpdateUserResponse.setResultDesp("原密码错误");
				contentStringEapsulate.setResult("-3");
			} else {
				if (password != null && password != "") {
					password = decodePassword(password);
					String hashedPassword = passwordEncoder.encode(password);
					user.setPassword(hashedPassword);
				}
				if (userService.resetPassword(user) > 0) {
					addUpdateUserResponse.setResult(1);
					addUpdateUserResponse.setResultDesp("返回成功");
					contentStringEapsulate.setResult("1");
				} else {
					addUpdateUserResponse.setResult(-2);
					addUpdateUserResponse.setResultDesp("更新失败");
					contentStringEapsulate.setResult("-2");
				}
			}
		} catch (Exception e) {
			addUpdateUserResponse.setResult(-1);
			addUpdateUserResponse.setResultDesp("数据库连接错误");
			contentStringEapsulate.setResult("-1");
		}
		addUpdateUserResponse.setContent(contentStringEapsulate);
		return addUpdateUserResponse;
	}
    
    @RequestMapping(value = "/resetpasswordbysuperUser",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
	public AddUpdateUserResponse resetpasswordBySuperUser(@RequestBody ResetPasswordRequest resetPasswordRequest) {
    	AddUpdateUserResponse addUpdateUserResponse = new AddUpdateUserResponse();
		try {
			String password = resetPasswordRequest.getPassword();
			User user = new User();
			Authentication a = SecurityContextHolder.getContext().getAuthentication();
			String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest()
					.getAuthorizationParameters().get("username");
			if (userService.getUserByUsername(currentSessionUsername) == null)
				user = userService.getUserByEmail(currentSessionUsername);
			if (user == null){
				user = userService.getUserByPhone(currentSessionUsername);
			}
			if (user.getIsSuperUser() == 1) {
				User updateUser = new User();
				updateUser.setUsername(resetPasswordRequest.getId());

				if (password != null && password != "") {
					password = decodePassword(password);
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					String hashedPassword = passwordEncoder.encode(password);
					updateUser.setPassword(hashedPassword);
				}
				if (userService.resetPassword(updateUser) > 0) {
					addUpdateUserResponse.setResult(1);
					addUpdateUserResponse.setResultDesp("返回成功");
				} else {
					addUpdateUserResponse.setResult(-2);
					addUpdateUserResponse.setResultDesp("更新失败");
				}
			}
		} catch (Exception e) {
			addUpdateUserResponse.setResult(-1);
			addUpdateUserResponse.setResultDesp("数据库连接错误");
		}
		return addUpdateUserResponse;
	}
    
    @RequestMapping(value = "/getusername",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public AddUpdateUserResponse getUsernameByEmailOrPhone(@RequestBody InputUsernameRequest inputUsernameReqeust){
		AddUpdateUserResponse addUpdateUserResponse = new AddUpdateUserResponse();
		try {
			User user = userService.getUserByEmail(inputUsernameReqeust.getUsername());
			if (user != null) {
				addUpdateUserResponse.setResult(1);
				addUpdateUserResponse.setResultDesp("返回成功");
				ContentStringEapsulate contentStringEapsulate = new ContentStringEapsulate();
				contentStringEapsulate.setResult(user.getUsername());
				addUpdateUserResponse.setContent(contentStringEapsulate);
			}else{
				user = userService.getUserByPhone(inputUsernameReqeust.getUsername());
				if (user != null) {
					addUpdateUserResponse.setResult(1);
					addUpdateUserResponse.setResultDesp("返回成功");
					ContentStringEapsulate contentStringEapsulate = new ContentStringEapsulate();
					contentStringEapsulate.setResult(user.getUsername());
					addUpdateUserResponse.setContent(contentStringEapsulate);
				}else{
					addUpdateUserResponse.setResult(-2);
					addUpdateUserResponse.setResultDesp("找不到该用户");
				}
			}
		} catch (Exception e) {
			addUpdateUserResponse.setResult(-1);
			addUpdateUserResponse.setResultDesp("数据库连接错误");
		}
		return addUpdateUserResponse;
    }

    @RequestMapping(value = "/getusersbydisplayname",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public GetUsersResponse getUsersByDisplayname(@RequestBody GetMentionedUsernameRequest getMentionedUsernameRequest){
    	GetUsersResponse getUsersResponse = new GetUsersResponse();
		try {
			List<Follow> followings = followService.getFollowingsByUsername(getMentionedUsernameRequest.getUsername());
			List<User> users  = userService.getUsersByDisplayname(getMentionedUsernameRequest.getMentionedDisplayname());
			List<User> selectedUsers = new ArrayList();
			Iterator<Follow> followingItr = followings.iterator();
			List<String> followingUsernames = new ArrayList();
			while (followingItr.hasNext()) {
				Follow following = followingItr.next();
				followingUsernames.add(following.getFollowingUser());
			}
			for (User user: users) {
				if (!followingUsernames.contains(user.getUsername())) {
					selectedUsers.add(user);
				}
			}
//			Iterator<User> userItr = users.iterator();
//			while (userItr.hasNext()) {
//				User user = userItr.next();
//
//			}
			if (selectedUsers.size() == 0) {
				getUsersResponse.setContent(users);
			} else {
				getUsersResponse.setContent(selectedUsers);
			}
			getUsersResponse.setResult(1);
			getUsersResponse.setResultDesp("返回成功");
		} catch (Exception e) {
			e.printStackTrace();
			getUsersResponse.setResult(-1);
			getUsersResponse.setResultDesp("数据库连接错误");
		}
    	return getUsersResponse;
    }
    
    @RequestMapping(value = "/list",method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public GetUsersResponse getSuperUserList(){
		GetUsersResponse getUsersResponse = new GetUsersResponse();
		try {
			getUsersResponse.setContent(userService.getSuperUserList());
			getUsersResponse.setResult(1);
			getUsersResponse.setResultDesp("返回成功");
		} catch (Exception e) {
			getUsersResponse.setResult(-1);
			getUsersResponse.setResultDesp("数据库连接错误");
		}
		return getUsersResponse;
    }
    
}
