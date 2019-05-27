package com.weshare.umg.service.entity;

import com.weshare.json.JsonUtils;
import com.weshare.umg.dao.AbstractBatchDao;
import com.weshare.umg.dao.SmsDetailsRepository;
import com.weshare.umg.entity.SmsDetails;
import com.weshare.umg.entity.SmsSendDetails;
import com.weshare.umg.entity.SmsSpReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/30 10:36
 * @Description:
 */
@Service
public class SmsDetailsService extends AbstractBatchDao<SmsDetails> {
    private static final Logger logger = LoggerFactory.getLogger(SmsDetailsService.class);

    @Autowired
    SmsDetailsRepository smsDetailsRepository;
    @Autowired
    SmsSendDetailsService smsSendDetailsService;
    @Autowired
    SmsReportService smsReportService;

    public List<SmsDetails> findbyCode(int code){
        SmsDetails entity = new SmsDetails();
        entity.setSpCode(code);
        Example<SmsDetails> example = Example.of(entity);
        List<SmsDetails> results =  smsDetailsRepository.findAll(example);
        return results;
    }

    public List<SmsDetails> findSmsSendDetails(String custId,String appcode){
        SmsDetails entity = new SmsDetails();
        entity.setCustId(custId);
        entity.setAppCode(appcode);
        Example<SmsDetails> example = Example.of(entity);
        List<SmsDetails> results =  smsDetailsRepository.findAll(example);
        return results;
    }

    public void mergeSmsSendDetailsByReport(SmsSpReport smsReport){
        try {
            SmsSendDetails smsSendDetails = smsSendDetailsService.getOne(smsReport.getSpCode(),smsReport.getResId(),smsReport.getMobile());
            merge(smsSendDetails,smsReport);
        }catch (Exception e){
            logger.error("mergeSmsSendDetailsByReport error!smsReport={}",JsonUtils.convertObjectToJSON(smsReport),e);
        }
    }

    public void mergeReportBySmsSendDetails(SmsSendDetails smsSendDetails){
        try {
            SmsSpReport smsSpReport = smsReportService.getOne(smsSendDetails.getSpCode(),smsSendDetails.getResId(),smsSendDetails.getMobile());
            merge(smsSendDetails,smsSpReport);
        }catch (Exception e){
            logger.error("mergeReportBySmsSendDetails error!smsSendDetails={}",JsonUtils.convertObjectToJSON(smsSendDetails),e);
        }
    }


    public void merge(SmsSendDetails smsSendDetails,SmsSpReport smsReport){
        if(smsSendDetails == null || smsReport== null){
            logger.debug("merge fail! smsSendDetails={}  || smsReport={}",JsonUtils.convertObjectToJSON(smsSendDetails),JsonUtils.convertObjectToJSON(smsReport));
            return;
        }
        SmsDetails details = new SmsDetails();
        details.setSpCode(smsSendDetails.getSpCode());
        details.setContent(smsSendDetails.getContent());
        details.setSentDate(smsSendDetails.getSentDate());
        details.setResId(smsSendDetails.getResId());
        details.setMobile(smsSendDetails.getMobile());
        details.setUniqueId(smsSendDetails.getUniqueId());
        details.setAppCode(smsSendDetails.getAppCode());
        details.setAcceptDate(smsSendDetails.getAcceptDate());
        details.setDesc(smsReport.getDesc());
        details.setErrmsg(smsReport.getErrmsg());
        details.setStatus(smsReport.getStatus());
        details.setUserAcceptDate(smsReport.getUserAcceptDate());
        details.setCustId(smsSendDetails.getCustId());
        smsDetailsRepository.save(details);
    }






}
