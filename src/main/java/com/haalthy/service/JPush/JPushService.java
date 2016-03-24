package com.haalthy.service.JPush;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.report.ReceivedsResult;
import com.haalthy.service.common.ConfigLoader;
import com.haalthy.service.common.SerializeUtil;
import com.haalthy.service.common.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ken on 2016-01-06.
 */
@Service
public class JPushService {

    protected Logger logger=Logger.getLogger(this.getClass());

//    private static String JPushAPPKEY ="659e4023ae5d594b3ab2cf81";
//    private static String JPushSECRETKEY = "6f766c0cfdfae04f3c29f8ed";
    @Autowired
    private JPushRegister jPushRegister;
    @Autowired
    private JPushMessageCache jPushMessageCache;

    protected static JPushClient jpushClient = null;

//    private static JPushService jPushService;
//
//    public static synchronized JPushService getInstance()
//    {
//        if(jPushService == null)
//            jPushService = new JPushService();
//        return jPushService;
//    }
//
//    private JPushService()
//    {
//        ConfigLoader configLoader = ConfigLoader.getInstance();
//        JPushAPPKEY = configLoader.getProperty("JPush.APPKEY");
//        JPushSECRETKEY = configLoader.getProperty("JPush.SECRETKEY");
//    }

    private JPushService()
    {
        if(jpushClient == null ) {
            ConfigLoader configLoader = ConfigLoader.getInstance();
            jpushClient = new JPushClient(configLoader.getConfigProperty("JPush.SECRETKEY"),
                    configLoader.getConfigProperty("JPush.APPKEY"));
        }
    }
    /**
     *发送系统消息
     *
     * */
    public void SendSystemMessage(String msg)
    {
        //PushPayload pushPayload = PushPayload.alertAll(msg);

        PushResult result = push(PushPayload.alertAll(msg));
    }

    /*
    * 给指定用户发送系统消息
    * */
    public void SendSystemMessageToUser(String userName,String Message)
    {
        try {
            String pushID = GetJPushID(userName);
            logger.info("pushID:"+pushID);
            logger.info("jpushClient:"+jpushClient);
            ArrayList<String> registrationIds =new ArrayList<String>();
            registrationIds.add(pushID);
            logger.info("registrationIds:"+registrationIds);
            logger.info("Message:"+Message);
            if(!StringUtils.IsEmpty(pushID))
            {
                logger.info("SendSystemMessageToUser logger before new PushPayload");
                PushPayload pushPayload =
                PushPayload.newBuilder()
                        .setPlatform(Platform.all())
                        .setAudience(Audience.registrationId(registrationIds))
                        .setNotification(Notification.alert(Message))
                        .build();

                logger.info("SendSystemMessageToUser logger after new PushPayload");

                logger.info("pushPayload:"+pushPayload);
                PushResult result = push(pushPayload);
                if(result != null) {
                    ReceivedsResult reportResult = jpushClient.getReportReceiveds(Long.toString(result.msg_id));
                    ReceivedsResult.Received reportReceived = reportResult.received_list.get(0);
                    if (reportReceived.android_received == 0 &&
                            reportReceived.ios_apns_sent == 0 &&
                            reportReceived.wp_mpns_sent == 0) {
                        SavaMassge(userName, null, Message);
                    }
                }
            }
            else
                logger.info("指定用户没有注册!username:"+userName);

        } catch (Exception e) {
            logger.info("SendSystemMessageToUser Exception :" + e + e.getMessage());
        }
    }



    /*
    * 给指定用户发送系统消息
    * */
    public void SendMessageToUser(String userName,String fromUserName,String Message)
    {
        try {
            String pushID = GetJPushID(userName);
            logger.info("pushID:"+pushID);
            ArrayList<String> registrationIds =new ArrayList<String>();
            registrationIds.add(pushID);

            PushPayload pushPayload =
                    PushPayload.newBuilder()
                            .setPlatform(Platform.all())
                            .setAudience(Audience.registrationId(registrationIds))
                            //.setAudience(Audience.alias("Test"))
                            .setNotification(Notification.alert(Message))
                            /*.setOptions(Options.newBuilder().setTimeToLive(0L).build())*/
                            .build();
            logger.info("pushPayload:"+pushPayload);
            PushResult result = push(pushPayload);
            if(result != null) {
                ReceivedsResult reportResult = jpushClient.getReportReceiveds(Long.toString(result.msg_id));
                ReceivedsResult.Received reportReceived = reportResult.received_list.get(0);
                if (reportReceived.android_received == 0 &&
                        reportReceived.ios_apns_sent == 0 &&
                        reportReceived.wp_mpns_sent == 0) {
                    SavaMassge(userName, fromUserName, Message);
                }
            }
        } catch (Exception e) {
            logger.info("SendMessageToUser Exception: " + e.getMessage());
        }
    }

    /*
    * 登录用户，同时发送该用户的所有的离线消息
    * */
    public void Login(String userName, String jPushID)
    {
        try {
            //String pushID = GetJPushID(userName);
            Map<String,JPushMessageContent> map = Regersiter(userName, jPushID);

            ArrayList<String> registrationIds =new ArrayList<String>();
            registrationIds.add(jPushID);
            if(map != null)
            {
                for (Map.Entry<String,JPushMessageContent> entry:map.entrySet()
                     ) {

                    PushPayload pushPayload =
                            PushPayload.newBuilder()
                                    .setPlatform(Platform.all())
                                    .setAudience(Audience.registrationId(registrationIds))
                                    .setNotification(Notification.alert(entry.getValue().getContent().toString()))
                                    /*.setOptions(Options.newBuilder().setTimeToLive(0L).build())*/
                                    .build();
                    PushResult result = push(pushPayload);
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
            logger.info("Exception" + e.getMessage());
        }
    }
    /*
    *
    * */
    private PushResult push(PushPayload payload)
    {
        try {

            PushResult result = jpushClient.sendPush(payload);
            logger.info("Got result - " + result);
            return result;

        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
            logger.info("Msg ID: " + e.getMsgId());
            return null;
        }
    }

    /*
    * 登录用户，同时取出该用户的所有的离线消息
    * */
    private Map<String,JPushMessageContent> Regersiter(String userName, String jPushID)
    {
        jPushRegister.Register(userName,jPushID);
        if(jPushMessageCache.ExitsOfflineMessage(userName))
        {
            return jPushMessageCache.getOfflineMessage(userName,true);
        }
        return null;
    }

    /*
    * 获取用户的JPUSH id
    *
    * */
    private String GetJPushID(String userName)
    {
        return jPushRegister.GetJPushID(userName).toString();
    }

    /*
    * 用户离线时保存该用户的消息
    *
    * */
    private void SavaMassge(String userName,String fromUserName,String msg)
    {
        JPushMessage pushMessage = new JPushMessage();
        JPushMessageContent messageContent= new JPushMessageContent();
        messageContent.setFromUserName(fromUserName);
        messageContent.setContent(SerializeUtil.serialize(msg));
        pushMessage.setToUserName(userName);
        pushMessage.setPushMessageContent(messageContent);
        jPushMessageCache.saveMessage(pushMessage);
    }
}
