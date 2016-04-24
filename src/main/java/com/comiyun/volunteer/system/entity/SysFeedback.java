package com.comiyun.volunteer.system.entity;

import com.comiyun.core.entity.BaseEntity;

/**
 * 问题反馈
 *
 * @author david
 */
public class SysFeedback extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 微信号
     */
    private String wxNum;
    /**
     * QQ
     */
    private String qq;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 反馈内容
     */
    private String content;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWxNum() {
        return wxNum;
    }

    public void setWxNum(String wxNum) {
        this.wxNum = wxNum;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
