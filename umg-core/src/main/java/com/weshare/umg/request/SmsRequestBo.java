package com.weshare.umg.request;

import com.weshare.umg.entity.TemplateDict;
import com.weshare.umg.entity.TemplateEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/7 15:52
 * @Description:
 */
public class SmsRequestBo extends SmsAcRequest {

    private TemplateEntity templateEntity;

    private Map<Integer, TemplateDict> dictMap;

    private List<Integer> SPList = new ArrayList<>();

    private String content;

    private String body;


    public TemplateEntity getTemplateEntity() {
        return templateEntity;
    }

    public SmsRequestBo setTemplateEntity(TemplateEntity templateEntity) {
        this.templateEntity = templateEntity;
        return this;
    }

    public List<Integer> getSPList() {
        return SPList;
    }

    public SmsRequestBo setSPList(List<Integer> SPList) {
        this.SPList = SPList;
        return this;
    }

    public String getContent() {
        return content;
    }

    public SmsRequestBo setContent(String content) {
        this.content = content;
        return this;
    }

    public String getBody() {
        return body;
    }

    public SmsRequestBo setBody(String body) {
        this.body = body;
        return this;
    }

    public Map<Integer, TemplateDict> getDictMap() {
        return dictMap;
    }

    public SmsRequestBo setDictMap(Map<Integer, TemplateDict> dictMap) {
        this.dictMap = dictMap;
        return this;
    }
}
