package com.weshare.umg.config;

import com.weshare.umg.service.sms.SmsExecuter;
import com.weshare.umg.service.sms.montnets.MonSmsExecuter;
import com.weshare.umg.service.sms.txc.TxcSmsExecuter;
import com.weshare.umg.util.SmsSp;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/8 14:20
 * @Description:
 */
@Component
public class ExecuterStore implements InitializingBean {

    public static ConcurrentHashMap<Integer,SmsExecuter> EXECUSTORE= new ConcurrentHashMap<>();

    @Autowired
    SmsConfig smsConfig;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void afterPropertiesSet(){
        //txc
        SmsExecuter txcExecuter =  TxcSmsExecuter.Builder.create()
                .setSmsConfig(smsConfig).build();
        EXECUSTORE.put(SmsSp.TXC.getCode(),txcExecuter);
        //montnets
        SmsExecuter monExecuter = MonSmsExecuter.Builder.create()
                                    .setSmsConfig(smsConfig).setRedisTemplate(redisTemplate)
                                    .build();

        EXECUSTORE.put(SmsSp.MONTNETS.getCode(),monExecuter);

    }
}
