package com.haalthy.service.sms;

import com.haalthy.service.common.ConfigLoader;
import com.haalthy.service.common.EncodeUtil;

import java.net.URL;

/**
 * Created by Ken on 2016-01-06.
 */
public class SMSServiceClient {
    private static String addr;
    private static String addrWsdl;
    private static String loginName;
    private static String pwd;
    private static SMSServiceClient smsServiceClient;

    private SMSServiceClient() {
        ConfigLoader configLoader = ConfigLoader.getInstance();
        addr=configLoader.getProperty("addr");
        addrWsdl = configLoader.getProperty("addr_wsdl");
        loginName = configLoader.getProperty("login_name");
        pwd= configLoader.getProperty("pwd");

    }

    public static synchronized SMSServiceClient getInstance() {
        if (smsServiceClient == null) {
            smsServiceClient = new SMSServiceClient();
        }
        return smsServiceClient;
    }



    /**
     * 发送短信

     */
    public String sendSMS(String msg, String phoneNum, String otime) throws Exception {
        String result = null;
        try {
            if(EncodeUtil.IsEmpty(msg))
                throw new Exception("-1：无短信内容");
            if(EncodeUtil.IsEmpty(phoneNum))
                throw new Exception("-2：接收手机号码为空");
            if(EncodeUtil.IsEmpty(otime))
                otime = "";
            //post请求参数
            String postContent = "LoginName=" + loginName + "&pwd=" + pwd + "&Msg=" + msg + "&phoneNum=" + phoneNum + "&otime=" + otime;
            //取得发送结果
            System.out.println("结果是："+postContent);
            result = HttpUtil.httpPostRequest(new URL(addr), postContent);
            System.out.println("result是："+result);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
