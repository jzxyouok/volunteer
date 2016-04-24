package com.comiyun.weixin.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信公众号
 *
 * @author david
 */
public class WxAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 应用ID
     */
    private String appId;

    /**
     * 应用密钥
     */
    private String appSecret;

    /**
     * 应用令牌
     */
    private String appToken;
    /**
     * 微信号
     */
    private String wxNumber;
    /**
     * 微信ID
     */
    private String wxId;
    /**
     * 授权回调页面域名
     */
    private String authCallBackUrl;
    /**
     * 访问令牌
     */
    @JsonIgnore
    private String accessToken;
    /**
     * 获取访问令牌时间
     */
    @JsonIgnore
    private Date addTokenTime;

    private String jsApiTicket;

    private Date addJsTicketTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public Date getAddTokenTime() {
        return addTokenTime;
    }

    public void setAddTokenTime(Date addTokenTime) {
        this.addTokenTime = addTokenTime;
    }

    public String getWxNumber() {
        return wxNumber;
    }

    public void setWxNumber(String wxNumber) {
        this.wxNumber = wxNumber;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getAuthCallBackUrl() {
        return authCallBackUrl;
    }

    public void setAuthCallBackUrl(String authCallBackUrl) {
        this.authCallBackUrl = authCallBackUrl;
    }

    public String getJsApiTicket() {
        return jsApiTicket;
    }

    public void setJsApiTicket(String jsApiTicket) {
        this.jsApiTicket = jsApiTicket;
    }

    public Date getAddJsTicketTime() {
        return addJsTicketTime;
    }

    public void setAddJsTicketTime(Date addJsTicketTime) {
        this.addJsTicketTime = addJsTicketTime;
    }

}
