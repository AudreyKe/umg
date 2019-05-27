package com.weshare.umg.service.task;

import com.weshare.umg.config.ExecuterStore;
import com.weshare.umg.entity.SmsSpEntity;
import com.weshare.umg.response.BalanceResponse;
import com.weshare.umg.service.sms.SmsPolicy;
import com.weshare.umg.service.entity.SmsSpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/22 15:45
 * @Description:
 */
@Component
public class SmsBalanceTask {
    private static final Logger logger = LoggerFactory.getLogger(SmsBalanceTask.class);


    @Autowired
    SmsSpService smsSpService;
    @Autowired
    SmsPolicy smsPolicy;

    public void smsBalance(){
        logger.info("*短信服务商余额*任务！start");
        Boolean flag = true;
        try {
            List<SmsSpEntity> list = smsSpService.fandEnableAll();
            for (SmsSpEntity sp : list) {
                int code = sp.getCode();
                BalanceResponse response = ExecuterStore.EXECUSTORE.get(code).getBalance();
                response.setCode(code);
                smsPolicy.balancePolicy(response,sp);
            }
        }catch (Exception e){
            flag = false;
            logger.error("余额监控异常!",e);
        }
        logger.info("*短信服务商余额*任务！{}!",flag);
    }



}
