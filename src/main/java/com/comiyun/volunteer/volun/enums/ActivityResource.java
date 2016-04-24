package com.comiyun.volunteer.volun.enums;

public enum ActivityResource {

    weixin("微信报名"), sys("手动报名");

    private String text;

    private ActivityResource(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
