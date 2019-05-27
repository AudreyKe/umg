package com.weshare.umg.dao;

import com.weshare.umg.entity.SmsDetails;
import com.weshare.umg.entity.SmsSendDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/8 17:09
 * @Description:
 */
@Component
public interface SmsSendDetailsRepository extends JpaRepository<SmsSendDetails, Long> {
}
