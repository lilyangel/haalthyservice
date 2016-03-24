package com.haalthy.service.controller.Interface;

/**
 * Created by Ken on 2016-01-08.
 */
public class JPushMassageRequest {
    private String userName;
    private String fromUserName;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
