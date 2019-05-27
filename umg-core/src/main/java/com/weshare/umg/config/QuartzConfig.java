package com.weshare.umg.config;

import com.weshare.umg.service.task.SmsBalanceTask;
import com.weshare.umg.service.task.SmsRePortTask;
import com.weshare.umg.service.task.UndeliveredMailTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/22 15:21
 * @Description:
 */
@Configuration
public class QuartzConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzConfig.class);

    @Autowired
    ApplicationContext context;

    @Autowired
    SmsBalanceTask smsBalanceTask;

    @Autowired
    SmsRePortTask smsRePortTask;


    @Scheduled(cron = "0 0 */1 * * ?")
    public void scanUndeliveredMail(){
        UndeliveredMailTask undeliveredMailTask = new UndeliveredMailTask();
        undeliveredMailTask.scanUndeliveredMail(context);
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void scanSmsBalance(){
        smsBalanceTask.smsBalance();
    }

//    @Scheduled(cron = "0 */5 * * * ?")
//    public void scanSmsRePortMail(){
//        smsRePortTask.smsRePort();
//    }
}
