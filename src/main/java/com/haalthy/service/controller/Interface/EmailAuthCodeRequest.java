package com.haalthy.service.controller.Interface;

/**
 * Created by Ken on 2016-01-06.
 */
public class EmailAuthCodeRequest {
    private String eMail;
    private String authCode;

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
