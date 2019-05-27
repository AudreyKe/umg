package com.weshare.umg.service.entity;

import com.weshare.umg.dao.AppRepository;
import com.weshare.umg.entity.AppEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/14 11:38
 * @Description:
 */
@Service
public class AppService {

    @Autowired
    AppRepository appRepository;

    public AppEntity getAppByCode(String code) {
        AppEntity entity = new AppEntity();
        entity.setCode(code);
        Example<AppEntity> example = Example.of(entity);
        Optional<AppEntity> result =  appRepository.findOne(example);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }
}
