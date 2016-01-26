package com.haalthy.service.sms;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.haalthy.service.common.ConfigLoader;

import java.util.List;

/**
 * Created by Ken on 2016-01-18.
 */
public class SmsSDKClient {
    private static CCPRestSmsSDK restAPI;
    private static SmsSDKClient smsSDKClient;
    private static String smsTempID;

    private SmsSDKClient()
    {
        restAPI = new CCPRestSmsSDK();
        ConfigLoader configLoader = ConfigLoader.getInstance();
        restAPI.init(configLoader.getConfigProperty("sms.url"),configLoader.getConfigProperty("sms.port"));
        restAPI.setAccount(configLoader.getConfigProperty("sms.accountSid"),
                configLoader.getConfigProperty("sms.accountToken"));
        restAPI.setAppId(configLoader.getConfigProperty("sms.appId"));



        smsTempID = configLoader.getConfigProperty("sms.authcodetempId");
    }

    public static synchronized SmsSDKClient getInstance()
    {
        if(smsSDKClient == null)
        {
            smsSDKClient = new SmsSDKClient();
        }
        return smsSDKClient;
    }

    public void sendAuthSms(String phonenum, List<String> params)
    {
        restAPI.sendTemplateSMS(phonenum,smsTempID,params.toArray(new String[params.size()]));
    }
}
