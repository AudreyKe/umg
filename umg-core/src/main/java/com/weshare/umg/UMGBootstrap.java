package com.weshare.umg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @Author: finleyli
 * @Date: Created in 2019/2/13
 * @Describe:
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableAsync
public class UMGBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(UMGBootstrap.class);

    public static void main(String[] args) {
//        Boolean.parseBoolean(System.setProperty(CLIENT_LOG_USESLF4J, "true"));

        SpringApplication.run(UMGBootstrap.class, args);
        LOGGER.info("Begin To Start UMG Core UmgServer");
    }
}
