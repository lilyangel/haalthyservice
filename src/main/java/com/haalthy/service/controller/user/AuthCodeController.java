package com.haalthy.service.controller.user;

import com.haalthy.service.controller.Interface.EmailAuthCodeRequest;
import com.haalthy.service.controller.Interface.PostResponse;
import com.haalthy.service.openservice.AuthCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Ken on 2016-01-06.
 */

@Controller
@RequestMapping("/open/authcode")
public class AuthCodeController {
    @Autowired
    private transient AuthCodeService authCodeService;

    @RequestMapping(value ="/emailsend", method = RequestMethod.POST, headers = "Accept=application/json",
            produces = {"application/json"},consumes = {"application/json"})
    @ResponseBody
    public PostResponse sendEmailAuthCode(@RequestBody EmailAuthCodeRequest emailAuthCodeRequest) throws Exception {

        PostResponse postResponse = new PostResponse();
		try {
			int addAuthCode = authCodeService.addEmailAuthCode(emailAuthCodeRequest.geteMail());
			if (addAuthCode > 0) {
				postResponse.setResult(1);
				postResponse.setResultDesp("返回成功");
				postResponse.setContent(addAuthCode);
			}else{
				postResponse.setResult(-2);
				postResponse.setResultDesp("获取失败");
			}
		} catch (Exception e) {
            e.printStackTrace();
            postResponse.setResult(-1);
            postResponse.setResultDesp("数据库连接错误");
        }
        return postResponse;
    }

    @RequestMapping(value ="/emailcheck", method = RequestMethod.POST, headers = "Accept=application/json",
            produces = {"application/json"},consumes = {"application/json"})
    @ResponseBody
    public PostResponse checkEmailAuthCode(@RequestBody EmailAuthCodeRequest emailAuthCodeRequest) throws Exception {
        PostResponse postResponse = new PostResponse();
        try {
            if(authCodeService.authEmailAuthCode(emailAuthCodeRequest.geteMail(), emailAuthCodeRequest.getAuthCode())==0)
            {
                postResponse.setResult(1);
                postResponse.setResultDesp("返回成功");
            }
            else
            {
                postResponse.setResult(-2);
                postResponse.setResultDesp("验证失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            postResponse.setResult(-1);
            postResponse.setResultDesp("数据库连接错误");
        }
        return postResponse;
    }
}
