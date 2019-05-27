package com.weshare.umg.request.mail;

import com.weshare.umg.request.AbstractRequest;
import com.weshare.umg.system.Channel;
import com.weshare.umg.system.Operation;
import com.weshare.umg.system.TranProtocol;
import com.weshare.umg.util.*;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/17 15:02
 * @Description:
 */
public class MailRequest extends AbstractRequest {
    private String replyTo;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String content;

    public MailRequest() {}

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
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



}
