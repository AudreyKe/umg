package com.weshare.umg.request;

import com.weshare.umg.system.Channel;
import com.weshare.umg.system.TranProtocol;
import com.weshare.umg.system.Operation;

/**
 * @Author: finleyli
 * @Date: Created in 2019/2/15
 * @Describe:
 **/
public class AbstractRequest {
    protected Channel channel;
    protected String custId;
    protected Operation operation;
    private String appCode;
    private String version;
    private String sign;
    private Long timestamp;


    public AbstractRequest() {
    }

    public Channel getChannel() {
        return channel;
    }

    public String getCustId() {
        return custId;
    }

    public AbstractRequest setCustId(String custId) {
        this.custId = custId;
        return this;
    }

    public Operation getOperation() {
        return operation;
    }

    public AbstractRequest setChannel(Channel channel) {
        this.channel = channel;
        return this;
    }

    public AbstractRequest setOperation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public String getAppCode() {
        return appCode;
    }

    public AbstractRequest setAppCode(String appCode) {
        this.appCode = appCode;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public AbstractRequest setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public AbstractRequest setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public AbstractRequest setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
