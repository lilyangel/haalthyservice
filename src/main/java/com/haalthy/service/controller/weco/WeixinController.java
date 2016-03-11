package com.haalthy.service.controller.weco;

import com.haalthy.service.controller.Interface.Response;
import com.haalthy.service.controller.Interface.StringRequest;
import com.haalthy.service.controller.Interface.WeixinConfig;
import com.haalthy.service.openservice.WeixinService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * Created by Ken on 2016-03-11.
 */

@Controller
@RequestMapping("/open/weixin")
public class WeixinController {
    @Autowired
    private transient WeixinService weixinService;


    protected Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping(value = "/getSign", method = RequestMethod.GET, headers = "Accept=application/json",
            produces = {"application/json"})
    @ResponseBody
    public Response getSignOpen(@RequestBody StringRequest request)
    {
        Response response = new Response();
        WeixinConfig wx = new WeixinConfig();
        String nonceStr = UUID.randomUUID().toString();
        String timeStamp = Long.toString(System.currentTimeMillis() / 1000);
        wx.setNonceStr(nonceStr);
        wx.setTimestamp(timeStamp);
        try {
            wx.setAppId(weixinService.getAppid());
            wx.setSignature(weixinService.getSignature(request.getContent(),timeStamp,nonceStr));
            response.setResult(1);
            response.setResultDesp("获取签名成功");
            response.setContent(wx);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult(-1);
            response.setResultDesp("获取签名失败");
            response.setContent(null);
        }
        return response;
    }
}
