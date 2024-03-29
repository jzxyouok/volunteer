package com.comiyun.weixin.enums;

/**
 * 接受消息类型
 *
 * @author david
 */
public enum MsgType {
    text,       //文本消息
    image,      //图片消息
    voice,        //语音消息
    video,      //视频消息
    shortvideo, //小视频消息
    location,   //地理位置消息
    link,       //链接消息
    event       //事件推送
}
