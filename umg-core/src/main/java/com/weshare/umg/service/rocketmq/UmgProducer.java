package com.weshare.umg.service.rocketmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.weshare.id.UniqueIdUtils;
import com.weshare.umg.config.RocketMqConfig;
import com.weshare.umg.exception.UmgException;
import com.weshare.umg.util.CodeEnum;
import com.weshare.umg.util.MqOperation;
import com.weshare.umg.util.SerializeMQUtils;
import com.weshare.wsmq.api.destination.Destination;
import com.weshare.wsmq.api.handler.PublishCallback;
import com.weshare.wsmq.api.message.Message;
import com.weshare.wsmq.api.message.MessageHeader;
import com.weshare.wsmq.api.message.MessageUtils;
import com.weshare.wsmq.core.IWsmqClient;
import com.weshare.wsmq.core.WsmqClient;
import org.apache.rocketmq.client.producer.SendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/7 11:11
 * @Description:
 */

@Component
public class UmgProducer {

    private static final Logger logger = LoggerFactory.getLogger(UmgProducer.class);
    private static final Logger STORE_ERROR_LOG = LoggerFactory.getLogger("store-error");

    @Autowired
    @Qualifier("wsmqClient")
    private IWsmqClient client;

    public void pushMQ(Destination destination, String id, String body)throws UmgException{
        try {
            MessageHeader header = MessageUtils.createMessageHeader(id, ((WsmqClient) client).getMqConfig().getSubSystemCode());
            Message message = MessageUtils.createMessage(header, body, destination);
            ((WsmqClient) client).publish(message, new PublishCallback() {

                @Override
                public void onSuccess(SendResult sendResult) {
                    logger.debug("sendResult={},id={} push mq success!id={}",sendResult.toString(),id);
                }

                @Override
                public void onWarning(SendResult sendResult) {
                    logger.warn("sendResult={},id={} push mq warn!id={}",sendResult.toString(),id);
                }

                @Override
                public void onException(Throwable e) {
                    logger.error("push  MQ sms error!destination={},body={}",destination.toString(),body,e);
                    STORE_ERROR_LOG.info("push  MQ!destination={},body={}",destination.toString(),body);
                }
            });
        } catch (Exception e) {
            logger.error("destination={},id={} push  mq fail!body={}",destination.toString(),id,body,e);
            throw new UmgException(CodeEnum.RMQ_PUSH_ERROR);
        }
    }


    public void pushSmsDetailsDest(JSONArray array,MqOperation operation){
        try {
            String mqbody = SerializeMQUtils.smsDetailsSerialize(operation,array,null);
            Destination destination = RocketMqConfig.destinationMap.get(RocketMqConfig.SMS_DETAILS_DEST);
            pushMQ(destination,UniqueIdUtils.nextId(),mqbody);
        } catch (Exception e) {
            logger.error("pushSmsDetailsDest fail!body={}",array.toArray(),e);
        }
    }


}
