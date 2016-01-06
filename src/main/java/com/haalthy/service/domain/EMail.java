package com.haalthy.service.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ken on 2016-01-04.
 */
public class EMail  implements Serializable {

    public static final int EMAIL_TYPE_VALIDATE = 0;   // 验证类型 （最先发送）
    public static final int EMAIL_TYPE_NOTICE = 10;    // 通知类型 （最后发送）

    private long id;
    private String subject;     // 主题
    private String content;     // 内容
    private String toEmail;     // 接收人
    private int priority;       // 邮件类型，根据类型的不同，发送的优先级会有所不同
    private Date createTime;
    private Date sendTime;// 创建时间

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}