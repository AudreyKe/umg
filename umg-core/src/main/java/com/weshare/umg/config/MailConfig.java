package com.weshare.umg.config;

import com.weshare.mail.ReceiveMail;
import com.weshare.mail.SendMailBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;


/**
 * @Author: mudong.xiao
 * @Date: 2019/4/17 11:05
 * @Description:
 */
@Configuration
public class MailConfig {

    @Value("${mail.host}")
    private String host;
    @Value("${mail.protocol}")
    private String protocol;
    @Value("${mail.port}")
    private Integer port;
    @Value("${mail.username}")
    private String userName;
    @Value("${mail.password}")
    private String passWord;
    @Value("${mail.smtp.starttls.enable}")
    private Boolean starttlsEnable;
    @Value("${mail.smtp.starttls.required}")
    private Boolean starttlsRequired;
    @Value("${mail.smtp.auth}")
    private Boolean auth = true;
    @Value("${mail.smtp.timeout}")
    private Integer timeout=25000;
    @Value("${mail.debug}")
    private Boolean debug;
    @Value("${mail.re_protocol}")
    private String reProtocol;
    @Value("${mail.re_port}")
    private String rePort;
    @Value("${mail.task_timeoffset}")
    private Integer timeOffset = 60;
    @Value("${mail.task_fromtrem}")
    private String fromTrem="Mail Delivery System";
    @Value("${mail.task_mailsize}")
    private Integer mailSize = 1024;



    @Bean
    public JavaMailSenderImpl javaMailSender(){
        JavaMailSenderImpl javaMailSender = SendMailBuilder.create().setProtocol(protocol)
                .setHost(host).setPort(port)
                .setUserName(userName).setPassWord(passWord)
                .setAuth(auth).setTimeout(timeout)
                .setStarttlsEnable(starttlsEnable)
                .setStarttlsRequired(starttlsRequired)
                .build();

        if(debug != null){
            javaMailSender.getSession().setDebug(debug);
        }

        return javaMailSender;
    }

    @Bean
    public ReceiveMail receiveMail(){
        ReceiveMail receiveMail  = ReceiveMail.Builder.create().setProtocol(reProtocol)
                .setHost(host).setPort(rePort)
                .setUserName(userName).setPassWord(passWord)
                .build();
        return receiveMail;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Boolean getStarttlsEnable() {
        return starttlsEnable;
    }

    public void setStarttlsEnable(Boolean starttlsEnable) {
        this.starttlsEnable = starttlsEnable;
    }

    public Boolean getStarttlsRequired() {
        return starttlsRequired;
    }

    public void setStarttlsRequired(Boolean starttlsRequired) {
        this.starttlsRequired = starttlsRequired;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }


    public Boolean getDebug() {
        return debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public String getReProtocol() {
        return reProtocol;
    }

    public void setReProtocol(String reProtocol) {
        this.reProtocol = reProtocol;
    }

    public String getRePort() {
        return rePort;
    }

    public void setRePort(String rePort) {
        this.rePort = rePort;
    }

    public Integer getTimeOffset() {
        return timeOffset;
    }

    public void setTimeOffset(Integer timeOffset) {
        this.timeOffset = timeOffset;
    }

    public String getFromTrem() {
        return fromTrem;
    }

    public void setFromTrem(String fromTrem) {
        this.fromTrem = fromTrem;
    }

    public Integer getMailSize() {
        return mailSize;
    }

    public void setMailSize(Integer mailSize) {
        this.mailSize = mailSize;
    }
}
