package com.weshare.umg.service.sms;

import com.weshare.umg.entity.SmsSendDetails;
import com.weshare.umg.entity.SmsSpReport;
import com.weshare.umg.entity.SmsDetails;
import com.weshare.umg.exception.UmgException;
import com.weshare.umg.request.SmsRequestBo;
import com.weshare.umg.response.BalanceResponse;

import java.util.List;

/**
 * @Auther: mudong.xiao
 * @Date: 2019/5/8
 * @Description:
 */
public interface SmsExecuter {
    public List<SmsSendDetails> sendSms(SmsRequestBo smsRequestBo)throws UmgException;

    public BalanceResponse getBalance() throws UmgException;

    public List<SmsSpReport> getReport() throws UmgException;

    public List<SmsSpReport> serializeReport(String body);
}
