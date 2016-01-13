package com.haalthy.service.JPush;

/**
 * Created by Ken on 2016-01-08.
 */
public class JPushMessage {

    private String toUserName;
    private JPushMessageContent pushMessageContent;


    public String getToUserName() {
        return toUserName;
    }


    public JPushMessageContent getPushMessageContent() {
        return pushMessageContent;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public void setPushMessageContent(JPushMessageContent pushMessageContent) {
        this.pushMessageContent = pushMessageContent;
    }
}
