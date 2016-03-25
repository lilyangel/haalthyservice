package com.haalthy.service.controller.Interface;

import java.util.Map;

/**
 * Created by Ken on 2016-01-08.
 */
public class JPushMassageRequest {
    private String userName;
    private String fromUserName;
    private String content;
    private Map<String,String> extras;

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

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }
}
