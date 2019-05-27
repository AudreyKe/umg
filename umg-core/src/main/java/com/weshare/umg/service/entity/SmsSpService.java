package com.weshare.umg.service.entity;

import com.weshare.base.Constant;
import com.weshare.umg.dao.SmsSpRepository;
import com.weshare.umg.entity.SmsSpEntity;
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
public class SmsSpService {
    private static final Logger logger = LoggerFactory.getLogger(SmsSpService.class);

    @Autowired
    SmsSpRepository smsSpRepository;

    public List<SmsSpEntity> fandEnableAll() {
        SmsSpEntity entity = new SmsSpEntity();
        entity.setStatus(Constant.ENABLE);
        Example<SmsSpEntity> example = Example.of(entity);
        List<SmsSpEntity> results =  smsSpRepository.findAll(example);
        return results;
    }

    public SmsSpEntity getSmsSpByCode(Integer code) {
        SmsSpEntity entity = new SmsSpEntity();
        entity.setCode(code);
        Example<SmsSpEntity> example = Example.of(entity);
        Optional<SmsSpEntity> result =  smsSpRepository.findOne(example);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    public Integer updateSmsSpByCode(Integer cordon,Float money, Integer number, Integer code) {
        Integer num = smsSpRepository.updateSmsSpByCode(cordon,money, number, code);
        return num;
    }





}
