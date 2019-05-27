package com.weshare.umg.entity;

import javax.persistence.*;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/18 10:12
 * @Description:
 */
@Entity
@Table(name = "template_dict")
public class TemplateDict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "template_id")
    private Integer templateId;
    @Column(name = "sp_template_id")
    private String spTemplateId;
    @Column(name = "`sp`")
    private Integer sp;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getSpTemplateId() {
        return spTemplateId;
    }

    public void setSpTemplateId(String spTemplateId) {
        this.spTemplateId = spTemplateId;
    }

    public Integer getSp() {
        return sp;
    }

    public void setSp(Integer sp) {
        this.sp = sp;
    }

}
