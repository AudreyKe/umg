package com.weshare.umg.dao;

import com.weshare.umg.config.SmsConfig;
import com.weshare.umg.exception.UmgException;
import com.weshare.umg.util.CodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/9 17:38
 * @Description:
 */
public abstract class AbstractBatchDao<T>  {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    protected EntityManager em;

    @Autowired
    protected SmsConfig smsConfig;

    @Transactional
    public void batchInsert(List<T> list)throws UmgException {
        if (list == null || list.size() == 0) {
            return;
        }
        try {
            for (int i = 1; i <= list.size(); i++) {
                em.persist(list.get(i-1));
                if (i % smsConfig.getReportNum() == 0) {
                    em.flush();
                    em.clear();
                }
            }
            //判断 是否有剩余未flush的 最后flush
            if(list.size() % smsConfig.getReportNum() != 0){
                em.flush();
                em.clear();
            }
            logger.info("batch insert to <{}> db success!",list.get(0).getClass());
        } catch (Exception e) {
            logger.error("batch insert to <{}> db fail!.",list.get(0).getClass());
            throw new UmgException(CodeEnum.DB_ERROR);
        }
    }
}
