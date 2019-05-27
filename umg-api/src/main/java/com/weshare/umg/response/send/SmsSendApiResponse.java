package com.weshare.umg.response.send;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/15 16:23
 * @Description:
 */
public class SmsSendApiResponse {
    private String custId;
    private String uniqueId;
    private Integer status;
    private String errorMsg;
    private String content;
    private String appCode;
    private Date acceptDate;
    private Integer spCode;
    private List<SmsSendApiResponseDTO> dtos = new ArrayList<>();



    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public Integer getSpCode() {
        return spCode;
    }

    public void setSpCode(Integer spCode) {
        this.spCode = spCode;
    }

    public List<SmsSendApiResponseDTO> getDtos() {
        return dtos;
    }

    public void setDtos(List<SmsSendApiResponseDTO> dtos) {
        this.dtos = dtos;
    }
}
