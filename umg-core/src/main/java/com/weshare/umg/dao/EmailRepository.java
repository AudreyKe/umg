package com.weshare.umg.dao;

import com.weshare.umg.entity.EMailEntity;
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
public interface  EmailRepository extends JpaRepository<EMailEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value="update EMailEntity set status=:status,sent_date=:sentDate where unique_id=:uniqueId")
    public int updateStatusByUId(@Param("status") Integer status,@Param("sentDate") Long sentDate,@Param("uniqueId") String uniqueId);

    @Modifying
    @Query(value="update EMailEntity set status=:status,warm_msg=concat(:warmMsg,',',IFNULL(warm_msg,'')) where unique_id=:uniqueId")
    public int updateWarmStatusByuUId(@Param("status") Integer status,
                                      @Param("warmMsg") String warmMsg,
                                      @Param("uniqueId") String uniqueId);

}

