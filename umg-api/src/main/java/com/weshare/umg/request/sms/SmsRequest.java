package com.weshare.umg.request.sms;

import com.weshare.umg.request.AbstractRequest;
import com.weshare.umg.system.*;
import com.weshare.umg.util.UniqueIdUtils;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class SmsRequest extends AbstractRequest {

    private ServicePro servicePro;

    /**
     * sms sp 服务商通道码
     **/
    private Integer availSpCode;

    /**
     * 电话号码
     */
    private List<String> mobiles = new ArrayList<>();
    /**
     * 国家 默认中国
     */
    private Nation nation;
    /**
     * 短信模板code
     */
    private String tplCode;
    /**
     * 短信模板内容
     */
    private List<String> params = new ArrayList<>();

    /**
     * 扩展号：长度不能超过6位
     */
    private String exno;
    /**
     * 业务类型
     */
    private String svrtype;
    /**
     * 自定义扩展数据
     */
    private String exdata;


    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }

    public String getTplCode() {
        return tplCode;
    }

    public SmsRequest setTplCode(String tplCode) {
        this.tplCode = tplCode;
        return this;
    }

    public List<String> getParams() {
        return params;
    }

    public SmsRequest setParams(List<String> params) {
        this.params = params;
        return this;
    }

    public String getExno() {
        return exno;
    }

    public SmsRequest setExno(String exno) {
        this.exno = exno;
        return this;
    }

    public String getSvrtype() {
        return svrtype;
    }

    public SmsRequest setSvrtype(String svrtype) {
        this.svrtype = svrtype;
        return this;
    }

    public String getExdata() {
        return exdata;
    }

    public SmsRequest setExdata(String exdata) {
        this.exdata = exdata;
        return this;
    }

    public Nation getNation() {
        return nation;
    }

    public SmsRequest setNation(Nation nation) {
        this.nation = nation;
        return this;
    }

    public ServicePro getServicePro() {
        return servicePro;
    }

    public SmsRequest setServicePro(ServicePro servicePro) {
        this.servicePro = servicePro;
        return this;
    }

    public Integer getAvailSpCode() {
        return availSpCode;
    }

    public SmsRequest setAvailSpCode(Integer availSpCode) {
        this.availSpCode = availSpCode;
        return this;
    }
}
