package com.weshare.umg.service.entity;

import com.weshare.umg.dao.AbstractBatchDao;
import com.weshare.umg.dao.SmsReportRepository;
import com.weshare.umg.entity.SmsDetails;
import com.weshare.umg.entity.SmsSendDetails;
import com.weshare.umg.entity.SmsSpReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/30 10:36
 * @Description:
 */
@Service
public class SmsReportService extends AbstractBatchDao<SmsSpReport> {
    private static final Logger logger = LoggerFactory.getLogger(SmsReportService.class);

    @Autowired
    SmsReportRepository smsReportRepository;

    public List<SmsSpReport> findbyCode(int code){
        SmsSpReport entity = new SmsSpReport();
        entity.setSpCode(code);
        Example<SmsSpReport> example = Example.of(entity);
        List<SmsSpReport> results =  smsReportRepository.findAll(example);
        return results;
    }

    public SmsSpReport getOne(int spCode, String resId, String mobile){
        SmsSpReport entity = new SmsSpReport();
        entity.setSpCode(spCode);
        entity.setResId(resId);
        entity.setMobile(mobile);
        Example<SmsSpReport> example = Example.of(entity);
        Optional<SmsSpReport> result = smsReportRepository.findOne(example);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }


}
