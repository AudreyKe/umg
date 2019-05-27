package com.weshare.umg.service.task;

import com.weshare.date.DateUtils;
import com.weshare.mail.ReceiveMail;
import com.weshare.regex.RegexUtils;
import com.weshare.umg.config.MailConfig;
import com.weshare.umg.entity.EMailEntity;
import com.weshare.umg.service.mail.MailService;
import com.weshare.umg.util.SendStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.util.MimeMessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.activation.DataSource;
import javax.mail.search.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/22 15:45
 * @Description:
 */
public class UndeliveredMailTask {
    private static final Logger logger = LoggerFactory.getLogger(UndeliveredMailTask.class);
    private static final String MESSAGE_TYPE = "message/rfc822";
    private static final String MESSAGEID = "Message-ID: (.*?)\n";
    private static final String WARM_INFO = "<(.*?)>:";


    public void scanUndeliveredMail(ApplicationContext context){
        logger.info("扫描邮件回退任务！start");
        ReceiveMail receiveMail = context.getBean(ReceiveMail.class);
        MailService mailService = context.getBean(MailService.class);
        MailConfig mailConfig = context.getBean(MailConfig.class);


        try {
            List<SearchTerm> terms = new ArrayList<>();
            //TimeOffset 分前到现在
            Date timeOffsetAgo = DateUtils.getOffsiteDate(new Date(), Calendar.MINUTE,mailConfig.getTimeOffset() * -1);
            terms.add(new SentDateTerm(ComparisonTerm.GE, timeOffsetAgo));
            terms.add(new SentDateTerm(ComparisonTerm.LE, new Date()));
            // 大小限制 (kb)
            terms.add(new SizeTerm(IntegerComparisonTerm.LE, mailConfig.getMailSize() * 1000));
            terms.add(new FromStringTerm(mailConfig.getFromTrem()));

            List<MimeMessageParser> parser = receiveMail.start().searchInboxEmail(terms.toArray(new SearchTerm[terms.size()]));
            for (MimeMessageParser m : parser) {
                List<String> messageIds = extractUndeliveredMessageId(m);
                List<String> warmMsgs = extractUndeliveredWarmMsg(m);
                String warmMsg = StringUtils.join(warmMsgs,",");

                messageIds.forEach(id->{
                    EMailEntity entity = new EMailEntity();
                    entity.setUniqueId(id.trim());
                    entity.setStatus(SendStatus.EXCE.getStatus());
                    entity.setWarmMsg(warmMsg);
                    mailService.updateWarmMailEntity(entity);
                });

            }
        } catch (Exception e) {
            logger.error("扫描邮件回退任务！ error!",e);
        }

        logger.info("扫描邮件回退任务！success!");
    }

    public List<String> extractUndeliveredMessageId(MimeMessageParser parser) throws Exception {
        List<DataSource> attachments = parser.getAttachmentList(); // 获取附件，并写入磁盘
        StringBuffer sb = new StringBuffer();
        for (DataSource ds : attachments) {
            if(ds.getContentType().equals(MESSAGE_TYPE)){
                String result = new BufferedReader(new InputStreamReader(ds.getInputStream()))
                        .lines().collect(Collectors.joining("\n"));
                sb.append(result);
            }
        }
        List<String> list = RegexUtils.getMatches(MESSAGEID,sb.toString());
        return list;
    }

    public List<String> extractUndeliveredWarmMsg(MimeMessageParser parser) throws Exception {
        // 获取纯文本邮件内容（注：有些邮件不支持html）
        String plainContent = parser.getPlainContent();
        List<String> list = RegexUtils.getMatches(WARM_INFO,plainContent);
        return list;
    }


}
