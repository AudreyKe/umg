package com.weshare.umg.dao;

import com.weshare.umg.entity.SmsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/8 17:09
 * @Description:
 */
@Component
public interface SmsRepository extends JpaRepository<SmsEntity, Long> {

    @Modifying
    @Transactional
    @Query(value="update SmsEntity set status=:status,error_msg=:errorMsg,sp_code=:spCode where unique_id=:uniqueId")
    public int updateSendSmsRes(@Param("status") Integer status,
                                      @Param("errorMsg") String errorMsg,
                                      @Param("spCode") Integer spCode,
                                      @Param("uniqueId") String uniqueId);

    @Modifying
    @Transactional
    @Query(value="update SmsEntity set retry=retry+1 where unique_id=:uniqueId")
    public int incSmsRetry(@Param("uniqueId") String uniqueId);

    @Modifying
    @Transactional
    @Query(value="update SmsEntity set status=:status,error_msg=:errorMsg where unique_id=:uniqueId")
    public int updateExce(@Param("status") Integer status,
                                    @Param("errorMsg") String errorMsg,
                                    @Param("uniqueId") String uniqueId);

}
