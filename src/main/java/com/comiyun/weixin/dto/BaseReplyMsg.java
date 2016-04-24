package com.comiyun.weixin.dto;

import com.comiyun.weixin.utils.XStreamUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAliasType;

import java.util.Date;

/**
 * 被动回复消息基类
 *
 * @author david
 */
@XStreamAliasType("xml")
public abstract class BaseReplyMsg {
    @XStreamAlias("ToUserName")
    private String toUserName = "";

    @XStreamAlias("FromUserName")
    private String fromUserName = "";

    @XStreamAlias("CreateTime")
    private long createTime = new Date().getTime();

    @XStreamAlias("MsgType")
    private String msgType = "";

    public String serializeToXml() {
        return XStreamUtil.serializeToXml(this);
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
