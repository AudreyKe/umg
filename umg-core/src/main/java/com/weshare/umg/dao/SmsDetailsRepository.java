package com.weshare.umg.dao;

import com.weshare.umg.entity.SmsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/8 17:09
 * @Description:
 */
@Component
public interface SmsDetailsRepository extends JpaRepository<SmsDetails, Long> {
}
