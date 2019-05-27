package com.weshare.umg.service.rocketmq;

import com.weshare.umg.config.RocketMqConfig;
import com.weshare.umg.exception.UmgException;
import com.weshare.umg.service.sms.SmsPolicy;
import com.weshare.umg.service.sms.SmsService;
import com.weshare.umg.util.CodeEnum;
import com.weshare.wsmq.api.destination.Destination;
import com.weshare.wsmq.api.handler.WsmqMessageHandler;
import com.weshare.wsmq.api.message.Message;
import com.weshare.wsmq.core.IWsmqClient;
import com.weshare.wsmq.core.WsmqClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/7 11:37
 * @Description:
 */

@Component
public class UmgConsumer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(UmgProducer.class);
    private static final Logger STORE_LOG = LoggerFactory.getLogger("store-info");
    private static final Logger STORE_ERROR_LOG = LoggerFactory.getLogger("store-error");

    @Autowired
    private SmsService smsService;


    @Autowired
    @Qualifier("wsmqClient")
    private IWsmqClient client;

    public void run(String... args) throws Exception {
        Destination destination = RocketMqConfig.destinationMap.get(RocketMqConfig.SMS_REQUEST_DEST);
        try {
            ((WsmqClient) client).subscribe(destination, new WsmqMessageHandler() {
                @Override
                public void handle(Message message) {
                    try {
                        STORE_LOG.info("pull MQ|destination={},body={}",destination.toString(),message.getContent());
                        smsService.acceptMQSms(message.getContent());
                    }catch (Exception e){
                        logger.error("pull MQ sms error!destination={},id={}",destination.toString(),message.getMessageHeader().getBizSeqNo(),e);
                        STORE_ERROR_LOG.info("pull MQ|destination={},body={}",destination.toString(),message.getContent());
                    }
                }
            });

            Destination smsDetailsDest = RocketMqConfig.destinationMap.get(RocketMqConfig.SMS_DETAILS_DEST);
            ((WsmqClient) client).subscribe(smsDetailsDest, new WsmqMessageHandler() {
                @Override
                public void handle(Message message) {
                    try {
                        STORE_LOG.info("pull MQ|destination={},body={}",smsDetailsDest.toString(),message.getContent());
                        smsService.acceptMQSmsDetailsDest(message.getContent());
                    }catch (Exception e){
                        logger.error("pull MQ error!destination={},id={}",smsDetailsDest.toString(),message.getMessageHeader().getBizSeqNo(),e);
                        STORE_ERROR_LOG.info("pull MQ|destination={},body={}",destination.toString(),message.getContent());
                    }
                }
            });

            client.start();
            logger.info("UMG mq client Started!");
        } catch (Exception e) {
            logger.error("UMG mq client destination={} pull fail!",destination.toString(),e);
            throw new UmgException(CodeEnum.RMQ_PULL_ERROR);
        }
    }

}
