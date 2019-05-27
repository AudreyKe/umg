package com.weshare.umg.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/18 10:12
 * @Description:
 */
@Entity
@Table(name = "template")
public class TemplateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "code")
    private String code;
    @Column(name = "msg")
    private String msg;
    @Column(name = "sign")
    private String sign;
    @Column(name = "name")
    private String name;
    @Column(name = "module_type")
    private Integer moduleType;
    @Column(name = "avail_sp_code")
    private Integer availSpCode;

    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "template_id")
    private List<TemplateDict> dicts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getModuleType() {
        return moduleType;
    }

    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
    }

    public Integer getAvailSpCode() {
        return availSpCode;
    }

    public void setAvailSpCode(Integer availSpCode) {
        this.availSpCode = availSpCode;
    }

    public List<TemplateDict> getDicts() {
        return dicts;
    }

    public void setDicts(List<TemplateDict> dicts) {
        this.dicts = dicts;
    }
}
