package com.haalthy.service.controller.Interface;

/**
 * Created by Ken on 2016-01-08.
 */
public class JPushMassageRequest {
    private String userName;
    private String fromUserName;
    private String MessageContent;

    public String getMessageContent() {
        return MessageContent;
    }

    public void setMessageContent(String messageContent) {
        MessageContent = messageContent;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
