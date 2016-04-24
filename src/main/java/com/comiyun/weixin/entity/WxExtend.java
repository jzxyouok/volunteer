package com.comiyun.weixin.entity;

import com.comiyun.core.entity.BaseEntity;

public class WxExtend extends BaseEntity {

    private static final long serialVersionUID = 7504439986130064469L;


    private String name;

    private String service;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

}
