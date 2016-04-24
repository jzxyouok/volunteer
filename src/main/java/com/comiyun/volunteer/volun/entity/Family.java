package com.comiyun.volunteer.volun.entity;

import com.comiyun.core.entity.BaseEntity;

/**
 * 家庭实体
 *
 * @author david
 */
public class Family extends BaseEntity {
    private static final long serialVersionUID = 2095594721405183814L;

    private Long code;
    /**
     * 简称
     */
    private String name;
    /**
     * 地址
     */
    private String address;
    /**
     * 创建者
     */
    private Long ownerId;

    /**
     * 临时变量
     */
    private String ownerName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

}
