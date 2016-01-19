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
    private String smsTempID;

    private SmsSDKClient()
    {
        if(restAPI == null)
            restAPI = new CCPRestSmsSDK();
        ConfigLoader configLoader = ConfigLoader.getInstance();
        restAPI.init(configLoader.getProperty("sms.url"),configLoader.getProperty("sms.port"));
        restAPI.setAccount(configLoader.getProperty("sms.accountSid"),
                configLoader.getProperty("sms.accountToken"));
        restAPI.setAppId(configLoader.getProperty("sms.appId"));
        smsTempID = configLoader.getProperty("sms.authcodetempId");
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
