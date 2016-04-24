package com.comiyun.weixin.entity;

import com.comiyun.core.entity.BaseEntity;
import com.comiyun.weixin.enums.QrBizType;

import java.io.Serializable;

/**
 * 微信二维码
 *
 * @author david
 */
public class WxQrCode extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 338586767006148525L;

    private QrBizType bizType;

    private Long bizId;

    private boolean qrLimit;

    private Long sceneId;

    private String ticket;

    private String url;

    public boolean isQrLimit() {
        return qrLimit;
    }

    public void setQrLimit(boolean qrLimit) {
        this.qrLimit = qrLimit;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public QrBizType getBizType() {
        return bizType;
    }

    public void setBizType(QrBizType bizType) {
        this.bizType = bizType;
    }

    public Long getSceneId() {
        return sceneId;
    }

    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }
}
