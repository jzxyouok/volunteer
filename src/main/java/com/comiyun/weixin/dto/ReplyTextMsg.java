package com.comiyun.weixin.dto;

import com.comiyun.weixin.enums.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 接收Text消息
 *
 * @author david
 */
public class ReplyTextMsg extends BaseReplyMsg {

    @XStreamAlias("Content")
    private String content = "";

    /**
     * 文本回复消息
     *
     * @param fromUserName
     * @param toUserName
     * @param content
     */
    public ReplyTextMsg(String fromUserName, String toUserName, String content) {
        this.setMsgType(MsgType.text.toString());
        this.setFromUserName(fromUserName);
        this.setToUserName(toUserName);
        this.setContent(content);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
