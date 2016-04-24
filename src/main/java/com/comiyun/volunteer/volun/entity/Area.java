package com.comiyun.volunteer.volun.entity;

import com.comiyun.core.entity.BaseEntity;

/**
 * 义工社区
 *
 * @author david
 */
public class Area extends BaseEntity {

    private static final long serialVersionUID = 8588400016988198054L;

    private String name;

    /**
     * 序号
     */
    private Integer sn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

}
