package com.weshare.umg.dao;

import com.weshare.umg.entity.SmsSpEntity;
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
public interface SmsSpRepository extends JpaRepository<SmsSpEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value="update SmsSpEntity set cordon=:cordon,money=:money,number=:number where code=:code")
    public int updateSmsSpByCode(@Param("cordon") Integer cordon,
                                     @Param("money") Float money,
                                     @Param("number") Integer number,
                                     @Param("code") Integer code);
}
