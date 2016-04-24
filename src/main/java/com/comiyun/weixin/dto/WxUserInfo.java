package com.comiyun.weixin.dto;

import java.io.Serializable;

/**
 * 微信账号信息
 *
 * @author david
 */
public class WxUserInfo implements Serializable {

    private static final long serialVersionUID = -1715554931982041818L;

    private boolean subscribe;

    private String openId;

    private String nickname;

    private int sex;

    public boolean isSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }


}
