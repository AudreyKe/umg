package com.weshare.umg.service.entity;

import com.weshare.json.JsonUtils;
import com.weshare.umg.dao.SmsRepository;
import com.weshare.umg.entity.SmsEntity;
import com.weshare.umg.response.SmsSendResponse;
import com.weshare.umg.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/30 10:36
 * @Description:
 */
@Service
public class SmsEntityService {
    private static final Logger logger = LoggerFactory.getLogger(SmsEntityService.class);


    @Autowired
    SmsRepository smsRepository;

    public SmsEntity querySmsEntity(String uniqueId){
        SmsEntity entity = new SmsEntity();
        entity.setUniqueId(uniqueId);
        Example<SmsEntity> example = Example.of(entity);
        Optional<SmsEntity> result =  smsRepository.findOne(example);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    public Long countSmsEntity(String uniqueId){
        SmsEntity entity = new SmsEntity();
        entity.setUniqueId(uniqueId);
        Example<SmsEntity> example = Example.of(entity);
        long count =  smsRepository.count(example);
        return count;
    }

    public void save(SmsEntity entity){
        long count = countSmsEntity(entity.getUniqueId());
        if(count == 0){
            entity.setStatus(SendStatus.READY.getStatus());
            smsRepository.save(entity);
        }
    }

    public Boolean exists(String uniqueId){
        SmsEntity entity = new SmsEntity();
        entity.setUniqueId(uniqueId);
        Example<SmsEntity> example = Example.of(entity);
        return smsRepository.exists(example);
    }

    public void updateExce(String uniqueId,Integer status, String msg){
        smsRepository.updateExce(status,msg,uniqueId);
    }

    public void updateSendSmsRes(SmsSendResponse smsSendResponse){
        String uniqueId = smsSendResponse.getUniqueId();
        Integer code = smsSendResponse.getCode();
        Integer status = smsSendResponse.getStatus()? SendStatus.SUEECSS.getStatus():SendStatus.FAIL.getStatus();
        String errMsg = smsSendResponse.getErrMap().isEmpty() ? null : JsonUtils.convertObjectToJSON(smsSendResponse.getErrMap());
        smsRepository.updateSendSmsRes(status,errMsg,code,uniqueId);
    }


    public int incSmsRetry(String uniqueId){
        return smsRepository.incSmsRetry(uniqueId);
    }


    public List<SmsEntity> findSmsEntity(String custId,String appcode){
        SmsEntity entity = new SmsEntity();
        entity.setCustId(custId);
        entity.setAppCode(appcode);
        Example<SmsEntity> example = Example.of(entity);
        List<SmsEntity> list = smsRepository.findAll(example);
        return list;
    }


}
