package com.comiyun.weixin.enums;

public enum MenuType {
    click("点击推事件"),
    view("跳转URL"),
    scancode_push("扫码推事件"),
    scancode_waitmsg("扫码推接受信息事件"),
    pic_sysphoto("弹出系统拍照发图"),
    pic_photo_or_album("弹出拍照或者相册发图"),
    pic_weixin("弹出微信相册发图器"),
    location_select("弹出地理位置选择器"),
    media_id("下发消息"),
    view_limited("跳转图文消息URL"),
    extend("扩展功能");

    private String text;

    private MenuType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
