package com.haalthy.service.controller.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
import com.haalthy.service.controller.Interface.ContentStringEapsulate;
import com.haalthy.service.controller.Interface.EmailAuthCodeRequest;
import com.haalthy.service.controller.Interface.GetSuggestUsersByTagsRequest;
import com.haalthy.service.controller.Interface.InputUsernameRequest;
import com.haalthy.service.controller.Interface.OSSFile;
import com.haalthy.service.controller.Interface.PostResponse;
import com.haalthy.service.controller.Interface.ResetPasswordRequest;
import com.haalthy.service.controller.Interface.user.AddUpdateUserResponse;
import com.haalthy.service.controller.Interface.user.GetUsersResponse;
import com.haalthy.service.domain.SelectUserByTagRange;
import com.haalthy.service.domain.Tag;
import com.haalthy.service.domain.User;
import com.haalthy.service.openservice.AuthCodeService;
import com.haalthy.service.openservice.OssService;
import com.haalthy.service.openservice.UserService;

import com.haalthy.service.common.CheckUserType;
import com.haalthy.service.openservice.AuthCodeService;

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
	
	@Autowired
	private transient OssService ossService;
	
    @Autowired
    private transient JPushService jPushService;
    
    @Autowired
    private transient AuthCodeService authCodeService;

    @RequestMapping(value = "/add",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public AddUpdateUserResponse addUser(@RequestBody User user) throws Exception {
		AddUpdateUserResponse addUserResponse = new AddUpdateUserResponse();
		try {
			user.setPassword(decodePassword(user.getPassword()));
			if (user.getUserType().equals("AY")){
				String username = generateUsername("AY");
				user.setUsername(username);
			}
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
			if (user.getEmail() == "")
				user.setEmail(null);
			if (user.getPhone() == "")
				user.setPhone(null);
			
			if (((userService.getUserByEmail(user.getEmail()) != null) && (user.getEmail()) != "") ||
					((userService.getUserByPhone(user.getPhone()) != null) && (user.getPhone()) != "")) {
				addUserResponse.setResultDesp("该邮箱/手机已被注册");
				addUserResponse.setResult(-2);
			}else if (userService.addUser(user) == 1) {
//				String username = userService.getUserByEmail(user.getEmail()).getUsername();
				String username = user.getUsername();
				addUserResponse.setResult(1);
				addUserResponse.setResultDesp("返回成功");
				ContentStringEapsulate contentStringEapsulate = new ContentStringEapsulate();
				contentStringEapsulate.setResult(username);
				addUserResponse.setContent(contentStringEapsulate);
				//
				if (user.getDeviceToken() != null){
					jPushService.Login(username,user.getDeviceToken());
				}

				//upload image
				List<OSSFile> ossFileList = new ArrayList();
				OSSFile ossFile = new OSSFile();
				if ((user.getImageInfo() != null) && (user.getImageInfo().getType() != null)
						&& (user.getImageInfo().getData() != null)) {
					ossFile.setFileType(user.getImageInfo().getType());
					ossFile.setFunctionType("user");
					ossFile.setImg(user.getImageInfo().getData());
					ossFile.setModifyType("append");
					ossFile.setId(username);
					ossFileList.add(ossFile);
					ossService.ossUploadFile(ossFileList);
				}else{
					addUserResponse.setResult(-4);
					addUserResponse.setResultDesp("添加用户头像失败");
				}
			} else {
				addUserResponse.setResult(-3);
				addUserResponse.setResultDesp("数据库插入错误");
			}
		} catch (Exception e) {
			addUserResponse.setResult(-1);
			System.out.println(e.getMessage());
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
    
    private String generateUsername(String prefix){
    	String timestamp = String.valueOf(System.currentTimeMillis());
    	String randomInt = String.valueOf(getRandom());
    	return prefix+timestamp+"."+randomInt;
    }
    
    @RequestMapping(value = "/suggestedusers",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public GetUsersResponse getSuggestUsersByTags(@RequestBody GetSuggestUsersByTagsRequest getSuggestUsersByTagsRequest) {
		GetUsersResponse getUsersResponse = new GetUsersResponse();
		try {
			List<Tag> tags = getSuggestUsersByTagsRequest.getTags();
			int rangeBegin = getSuggestUsersByTagsRequest.getPage() * getSuggestUsersByTagsRequest.getCount();
			int rangeEnd = (getSuggestUsersByTagsRequest.getPage() + 1) * getSuggestUsersByTagsRequest.getCount();
			List<User> users = new ArrayList<User>();
			Set<String> set = new HashSet<String>();
//			for (int i = 0; i < tags.length; i++) {
				SelectUserByTagRange selectUserByTagRange = new SelectUserByTagRange();
				selectUserByTagRange.setBeginIndex(rangeBegin);
				selectUserByTagRange.setEndIndex(rangeEnd);
				selectUserByTagRange.setTags(tags);
				List<User> suggestUsers = userService.selectSuggestUsersByTags(selectUserByTagRange);
				Iterator<User> userItr = suggestUsers.iterator();
				while (userItr.hasNext()) {
					User newSuggestUser = userItr.next();
					if (set.add(newSuggestUser.getUsername())) {
						users.add(newSuggestUser);
					}
				}
//			}
			getUsersResponse.setResult(1);
			getUsersResponse.setResultDesp("返回成功");
			getUsersResponse.setContent(users);
		} catch (Exception e) {
			e.printStackTrace();
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
			getUsersResponse.setContent(userService.searchUsers(keyword));
		} catch (Exception e) {
			getUsersResponse.setResult(-1);
			getUsersResponse.setResultDesp("数据库连接错误");
		}
		return getUsersResponse;
	}
    
	@RequestMapping(value = "/resetpasswordwithauthcode", method = RequestMethod.POST, headers = "Accept=application/json", produces = {
			"application/json" }, consumes = { "application/json" })
	@ResponseBody
	public AddUpdateUserResponse resetPasswordWithAuthCode(@RequestBody ResetPasswordRequest resetPasswordRequest) {
		AddUpdateUserResponse addUpdateUserResponse = new AddUpdateUserResponse();
		try {
			String password = resetPasswordRequest.getPassword();
			String id = resetPasswordRequest.getId();
			String authCode = resetPasswordRequest.getAuthCode();
			CheckUserType checkUserType = new CheckUserType();
			int userType = checkUserType.checkUserType(id);
			String username = "";
			User user = new User();
			// AuthCodeController authCodeController = new AuthCodeController();
			// EmailAuthCodeRequest emailAuthCodeRequest = new
			// EmailAuthCodeRequest();
			// emailAuthCodeRequest.setAuthCode(authCode);
			// emailAuthCodeRequest.seteMail(id);
			int postResponse = 1;
			if (userType == 0) {
				postResponse = authCodeService.authEmailAuthCode(id, authCode);
				if (postResponse == 0)
					user = userService.getUserByEmail(id);
			} else if (userType == 1) {
				postResponse = authCodeService.authMobileAuthCode(id, authCode);
				if (postResponse == 0)
					user = userService.getUserByPhone(id);
			}
			if (postResponse != 0) {
				addUpdateUserResponse.setResult(-3);
				addUpdateUserResponse.setResultDesp("验证失败");
			} else {
				username = user.getUsername();
				System.out.println(username);
				if (username == "") {
					addUpdateUserResponse.setResult(-4);
					addUpdateUserResponse.setResultDesp("该用户不存在");
				} else {
					if (password != null && password != "") {
						password = decodePassword(password);
						BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
						String hashedPassword = passwordEncoder.encode(password);
						user.setPassword(hashedPassword);
					}
					if (userService.resetPassword(user) > 0) {
						addUpdateUserResponse.setResult(1);
						addUpdateUserResponse.setResultDesp("返回成功");
						ContentStringEapsulate result = new ContentStringEapsulate();
						result.setResult(user.getUsername());
						addUpdateUserResponse.setContent(result);
					} else {
						addUpdateUserResponse.setResult(-2);
						addUpdateUserResponse.setResultDesp("更新失败");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addUpdateUserResponse.setResult(-1);
			addUpdateUserResponse.setResultDesp("数据库连接错误");
		}
		return addUpdateUserResponse;
	}
}
