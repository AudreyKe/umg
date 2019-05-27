package com.weshare.umg.service.entity;

import com.weshare.umg.dao.AbstractBatchDao;
import com.weshare.umg.dao.SmsDetailsRepository;
import com.weshare.umg.dao.SmsSendDetailsRepository;
import com.weshare.umg.entity.AppEntity;
import com.weshare.umg.entity.SmsDetails;
import com.weshare.umg.entity.SmsSendDetails;
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
public class SmsSendDetailsService extends AbstractBatchDao<SmsSendDetails> {
    private static final Logger logger = LoggerFactory.getLogger(SmsSendDetailsService.class);

    @Autowired
    SmsSendDetailsRepository amsSendDetailsRepository;

    public List<SmsSendDetails> findbyCode(int code){
        SmsSendDetails entity = new SmsSendDetails();
        entity.setSpCode(code);
        Example<SmsSendDetails> example = Example.of(entity);
        List<SmsSendDetails> results =  amsSendDetailsRepository.findAll(example);
        return results;
    }


    public List<SmsSendDetails> findSmsSendDetails(String custId,String appcode){
        SmsSendDetails entity = new SmsSendDetails();
        entity.setCustId(custId);
        entity.setAppCode(appcode);
        Example<SmsSendDetails> example = Example.of(entity);
        List<SmsSendDetails> results =  amsSendDetailsRepository.findAll(example);
        return results;
    }

    public SmsSendDetails getOne(int spCode,String resId,String mobile){
        SmsSendDetails entity = new SmsSendDetails();
        entity.setSpCode(spCode);
        entity.setResId(resId);
        entity.setMobile(mobile);
        Example<SmsSendDetails> example = Example.of(entity);
        Optional<SmsSendDetails> result = amsSendDetailsRepository.findOne(example);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }



}
