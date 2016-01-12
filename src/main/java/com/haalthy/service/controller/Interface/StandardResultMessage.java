package com.haalthy.service.controller.Interface;

/**
 * Created by Ken on 2016-01-06.
 */
public class StandardResultMessage {
    private int code;
    private String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
