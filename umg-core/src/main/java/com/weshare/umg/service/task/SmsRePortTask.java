package com.weshare.umg.service.task;

import com.weshare.umg.service.sms.SmsPolicy;
import com.weshare.umg.service.entity.SmsSpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/22 15:45
 * @Description:
 */
@Component
public class SmsRePortTask {
    private static final Logger logger = LoggerFactory.getLogger(SmsRePortTask.class);

    @Autowired
    SmsSpService smsSpService;
    @Autowired
    SmsPolicy smsPolicy;

//    public void smsRePort(){
//        logger.info("*短信下行报告*任务！start");
//        Boolean flag = true;
//        Integer code = null;
//        try {
//            List<SmsSpEntity> list = smsSpService.fandEnableAll();
//            for (SmsSpEntity sp : list) {
//                code = sp.getCode();
//                smsPolicy.reportPolicy(ExecuterStore.EXECUSTORE.get(code),code);
//            }
//        }catch (Exception e){
//            flag = false;
//            logger.error("获取报告异常!code={}",code,e);
//        }
//        logger.info("*短信下行报告*任务！{}!",flag);
//    }



}
