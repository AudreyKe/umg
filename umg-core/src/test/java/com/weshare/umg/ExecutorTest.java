package com.weshare.umg;

import com.weshare.json.JsonUtils;
import com.weshare.umg.config.SmsConfig;
import com.weshare.umg.exception.UmgException;
import com.weshare.umg.service.sms.SmsExecuter;
import com.weshare.umg.service.sms.montnets.MonSmsExecuter;
import com.weshare.umg.service.sms.txc.TxcSmsExecuter;
import com.weshare.umg.util.SmsSp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/9 16:33
 * @Description:
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = UMGBootstrap.class)
public class ExecutorTest {
//    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testTxc() {
        SmsConfig smsConfig = new SmsConfig();
        smsConfig.setLength(10);
        smsConfig.setReportNum(50);
        smsConfig.setTxcAppkey("c96552c0248a5b0e4006d6ca0bc37394");
        smsConfig.setTxcAppid(1400207261);

        SmsExecuter executer =  TxcSmsExecuter.Builder.create()
                .setSmsConfig(smsConfig).build();

        try {
//            System.out.println(JsonUtils.convertObjectToJSON(executer.getBalance()));
            System.out.println(executer.getReport());
        } catch (UmgException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testMon() {
        SmsConfig smsConfig = new SmsConfig();
        smsConfig.setLength(10);
        smsConfig.setReportNum(50);
        smsConfig.setMonPaths("http://61.145.229.29:9002,http://112.91.147.37:9002");
        smsConfig.setMonEncryptPwd(true);
        smsConfig.setMonApikey("");
        smsConfig.setMonPwd("774122");
        smsConfig.setMonUserid("JC2363");


        SmsExecuter executer = MonSmsExecuter.Builder.create()
                .setSmsConfig(smsConfig).setRedisTemplate(redisTemplate)
                .build();

        try {
            System.out.println(JsonUtils.convertObjectToJSON(executer.getBalance()));
            System.out.println(executer.getReport());
        } catch (UmgException e) {
            e.printStackTrace();
        }

    }

}
