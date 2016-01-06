package com.haalthy.service.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/open/authcode")
public class AuthCodeController {

    @RequestMapping(value ="/emailsend", method = RequestMethod.POST, headers = "Accept=application/json",
            produces = {"application/json"})
    @ResponseBody
    public int sendEmailAuthCode(@RequestBody EmailAuthCodeRequest emailAuthCodeRequest) throws Exception {
    	return 0;
    }
}
