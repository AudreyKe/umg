package com.weshare.umg.service.entity;

import com.weshare.umg.dao.TemplateRepository;
import com.weshare.umg.entity.TemplateEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * @Author: mudong.xiao
 * @Date: 2019/4/30 10:36
 * @Description:
 */
@Service
public class TemplateService {
    private static final Logger logger = LoggerFactory.getLogger(TemplateService.class);

    @Autowired
    TemplateRepository templateRepository;

    public TemplateEntity getTemplateByCode(String code) {
        TemplateEntity entity = new TemplateEntity();
        entity.setCode(code);
        Example<TemplateEntity> example = Example.of(entity);
        Optional<TemplateEntity> result =  templateRepository.findOne(example);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }




}
