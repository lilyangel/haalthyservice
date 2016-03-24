package com.haalthy.service.controller.JPush;

import com.haalthy.service.JPush.JPushService;
import com.haalthy.service.controller.Interface.JPushMassageRequest;
import com.haalthy.service.controller.Interface.PostResponse;
import com.haalthy.service.controller.Interface.StandardResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Ken on 2016-01-08.
 */

@Controller
@RequestMapping("/open/push")
public class JPushController {
    @Autowired
    private transient JPushService jPushService;

    /*
    * 用户重新登录APP时，上传对应的JPUSH的DEVICE id到服务端，用于username与device id对应
    * */
    @RequestMapping(value ="/jpushlogin", method = RequestMethod.POST, headers = "Accept=application/json",
            produces = {"application/json"},consumes = {"application/json"})
    @ResponseBody
    public PostResponse JPushLogin(@RequestBody JPushMassageRequest request)
    {
        PostResponse postResponse = new PostResponse();
        try {
            jPushService.Login(request.getUserName(),request.getFromUserName());
            postResponse.setResult(1);
            postResponse.setResultDesp("登录成功");
        } catch (Exception e) {
            postResponse.setResult(-999);
            postResponse.setResultDesp("异常错误");
            e.printStackTrace();
        }
        postResponse.setContent(null);
        return postResponse;
    }

    /*
    * 向所有的用户发送系统消息，如果用户离线，离线消息保存1天
    * */
    @RequestMapping(value ="/systemall", method = RequestMethod.POST, headers = "Accept=application/json",
            produces = {"application/json"},consumes = {"application/json"})
    @ResponseBody
    public PostResponse SendSystemMessageAll(@RequestBody JPushMassageRequest request) throws Exception
    {
        PostResponse postResponse = new PostResponse();
        try {
            jPushService.SendSystemMessage(request.getContent());
            postResponse.setResult(1);
            postResponse.setResultDesp("推送成功");
        } catch (Exception e) {
            postResponse.setResult(-999);
            postResponse.setResultDesp("异常错误");
            e.printStackTrace();
        }
        postResponse.setContent(null);
        return postResponse;
    }

    /*
    * 向特定用户发送系统消息，如果用户离线，离线消息保存7天
    * */
    @RequestMapping(value ="/system", method = RequestMethod.POST, headers = "Accept=application/json",
            produces = {"application/json"},consumes = {"application/json"})
    @ResponseBody
    public PostResponse SendSystemMessage(@RequestBody JPushMassageRequest request) throws Exception
    {

        PostResponse postResponse = new PostResponse();
        try {
            jPushService.SendSystemMessageToUser(request.getUserName(),request.getContent());
            postResponse.setResult(1);
            postResponse.setResultDesp("推送成功");
        } catch (Exception e) {
            postResponse.setResult(-999);
            postResponse.setResultDesp("异常错误");
            e.printStackTrace();
        }
        postResponse.setContent(null);
        return postResponse;
    }

    /*
    * 向特定用户发送用户消息，如果用户离线，离线消息保存7天
    * 这里的用户消息是指一个用户发给另一个用户，比如A向B发送的消息
    * JPushMassageRequestzho中fromUserName为发送消息人的username
    * */
    @RequestMapping(value ="/Customer", method = RequestMethod.POST, headers = "Accept=application/json",
            produces = {"application/json"},consumes = {"application/json"})
    @ResponseBody
    public PostResponse SendCustomerMessage(@RequestBody JPushMassageRequest request) throws Exception
    {

        PostResponse postResponse = new PostResponse();
        StandardResultMessage standardResultMessage = new StandardResultMessage();
        try {
            jPushService.SendMessageToUser(request.getUserName(),request.getFromUserName(),request.getContent());
            postResponse.setResult(1);
            postResponse.setResultDesp("推送成功");
        } catch (Exception e) {
            postResponse.setResult(-999);
            postResponse.setResultDesp("异常错误");
            e.printStackTrace();
        }
        postResponse.setContent(null);
        return postResponse;
    }
}
