package com.haalthy.service.controller.Interface;

/**
 * Created by Ken on 2016-03-15.
 */
public class OssStsToken {
    private String accessKeyID;
    private String accessKeySecert;
    private String securityToken;
    private String expiration;
    private String endpoint;
    private String objectKey;
    private String objectUrl;

    public String getAccessKeyID() {
        return accessKeyID;
    }

    public void setAccessKeyID(String accessKeyID) {
        this.accessKeyID = accessKeyID;
    }

    public String getAccessKeySecert() {
        return accessKeySecert;
    }

    public void setAccessKeySecert(String secertKeyID) {
        this.accessKeySecert = secertKeyID;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String secertToken) {
        this.securityToken = secertToken;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getObjectUrl() {
        return objectUrl;
    }

    public void setObjectUrl(String objectUrl) {
        this.objectUrl = objectUrl;
    }
}
