package com.weshare.umg.service.mail;

import com.weshare.id.UniqueIdUtils;
import com.weshare.json.JsonUtils;
import com.weshare.mail.SendMailUtils;
import com.weshare.umg.dao.EmailRepository;
import com.weshare.umg.entity.EMailEntity;
import com.weshare.umg.exception.UmgException;
import com.weshare.umg.request.MailAcRequest;
import com.weshare.umg.system.TranProtocol;
import com.weshare.umg.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;


/**
 * @Author: mudong.xiao
 * @Date: 2019/4/17 11:04
 * @Description:
 */
@Service
public class MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    EmailRepository emailRepository;


//    public void opt(EMailEntity entity) throws UmgException {
//        int type = entity.getTranProtocolType();
//        TranProtocol tp = TranProtocol.getByValue(type);
//        switch (tp){
//            case SYN:
//                sendMail(entity);
//                break;
//            case ASYN:
//                sendAsynMail(entity);
//                break;
//            default:
//                throw new UmgException(CodeEnum.TP_ERROR);
//        }
//    }

    public void sendMail(EMailEntity entity) throws UmgException {
        String subject = entity.getSubject();
        String content = entity.getContent();
        String to = entity.getTo();
        String cc = entity.getCc();
        String bcc = entity.getBcc();
        String uniqueId = entity.getUniqueId();
        try {
            SendMailUtils.send(subject,content,to,cc,bcc,javaMailSender.getUsername(),uniqueId,javaMailSender);
            entity.setStatus(SendStatus.SUEECSS.getStatus());
            entity.setSentDate(System.currentTimeMillis());
            updateStatusMailEntity(entity);
        }catch (Exception e) {
            logger.error("send mail error! uniqueId={}", entity.getUniqueId(), e);
            entity.setStatus(SendStatus.FAIL.getStatus());
            updateStatusMailEntity(entity);
            throw new UmgException(CodeEnum.SEND_MAIL_FAIL);
        }
    }

    public void sendAsynMail(EMailEntity entity) throws UmgException {
        //todo 异步功能缺少，暂定为先发送
        sendMail(entity);

    }

    public void acceptMail(String requestBody)throws UmgException {
        String uniqueId = UniqueIdUtils.nextId();
        String custId="";
        try {
            MailAcRequest acRequest = JsonUtils.convertJsonToObject(requestBody, MailAcRequest.class);
            acRequest.setUniqueId(uniqueId);
            custId = acRequest.getCustId();
            logger.info("receive http mail! uniqueId={},custId={}",uniqueId,custId);
            acceptMail(acRequest);
        }catch (UmgException ge){
            logger.error("accept http mail error! uniqueId={},custId={},object={}",uniqueId,custId,requestBody,ge);
            throw ge;
        }catch (Exception e){
            logger.error("accept http mail error! uniqueId={},custId={},object={}",uniqueId,custId,requestBody,e);
            throw new UmgException(CodeEnum.SYSTEM_ERROR);
        }
    }

    public void acceptMail(MailAcRequest request) throws UmgException {
        ValidaUtils.validateMailRequest(request);
        EMailEntity entity = RequestUtils.buildEMailEntity(request);
        entity.setStatus(SendStatus.READY.getStatus());
        saveMailEntity(entity);
        sendMail(entity);
    }

    public void saveMailEntity(EMailEntity entity){
        emailRepository.save(entity);
    }

    public void updateStatusMailEntity(EMailEntity entity) {
        emailRepository.updateStatusByUId(entity.getStatus(),entity.getSentDate(),entity.getUniqueId());
    }

    public void updateWarmMailEntity(EMailEntity entity) {
        emailRepository.updateWarmStatusByuUId(entity.getStatus(),entity.getWarmMsg(),entity.getUniqueId());
    }


}
