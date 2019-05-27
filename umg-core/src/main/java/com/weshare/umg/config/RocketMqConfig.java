package com.weshare.umg.config;

import ch.qos.logback.classic.Level;
import com.weshare.umg.util.DestinationUtils;
import com.weshare.wsmq.api.destination.Destination;
import com.weshare.wsmq.config.MqConfig;
import com.weshare.wsmq.core.IWsmqClient;
import com.weshare.wsmq.core.WsmqClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/7 11:09
 * @Description:
 */
@Configuration
public class RocketMqConfig implements InitializingBean {

    public static  ConcurrentHashMap<String,Destination> destinationMap = new ConcurrentHashMap<>();
    public static final String SMS_REQUEST_DEST = "SMS_REQUEST_DEST";
    public static final String SMS_DETAILS_DEST = "SMS_DETAILS_DEST";

    @Value("${rmq.namesrvaddr}")
    private String namesrvaddr;
    @Value("${rmq.subsystemcode}")
    private String subsystemcode;
    @Value("${rmq.orgcode}")
    private String orgcode;
    @Value("${rmq.sms-request-dest}")
    private String smsRequestDest;
    @Value("${rmq.sms-details-dest}")
    private String smsDetailsDest;



    @Bean
    public MqConfig mqConfig(){
        MqConfig mqConfig = new MqConfig();
        mqConfig.setNamesrvAddr(namesrvaddr);
        mqConfig.setSubSystemCode(subsystemcode);
        mqConfig.setOrgCode(orgcode);
        mqConfig.setRocketmqLogLevel(Level.ERROR);
        return mqConfig;
    }


    @Bean(name={"wsmqClient"},destroyMethod= "stop")
    public IWsmqClient client(MqConfig mqConfig){
        IWsmqClient client = new WsmqClient();
        ((WsmqClient) client).setMqConfig(mqConfig);
        return client;
    }


    @Override
    public void afterPropertiesSet(){
        Destination smsRequestDest = DestinationUtils.buildDestination(this.smsRequestDest);
        destinationMap.put(SMS_REQUEST_DEST,smsRequestDest);

        Destination smsDetailsDest = DestinationUtils.buildDestination(this.smsDetailsDest);
        destinationMap.put(SMS_DETAILS_DEST,smsDetailsDest);
    }
}
